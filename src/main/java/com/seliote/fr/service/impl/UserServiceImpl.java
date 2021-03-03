package com.seliote.fr.service.impl;

import com.seliote.fr.config.auth.TokenFilter;
import com.seliote.fr.config.auth.TokenHandler;
import com.seliote.fr.config.auth.TokenModel;
import com.seliote.fr.exception.DataException;
import com.seliote.fr.exception.ServiceException;
import com.seliote.fr.mapper.UserMapper;
import com.seliote.fr.model.mi.user.CountUserMi;
import com.seliote.fr.model.mi.user.InsertMi;
import com.seliote.fr.model.mi.user.SelectIdMi;
import com.seliote.fr.model.si.sms.CheckLoginSi;
import com.seliote.fr.model.si.user.IsRegisteredSi;
import com.seliote.fr.model.si.user.LoginSi;
import com.seliote.fr.model.si.user.RegisterSi;
import com.seliote.fr.model.si.user.UserIdSi;
import com.seliote.fr.model.so.user.LoginSo;
import com.seliote.fr.service.RedisService;
import com.seliote.fr.service.SmsService;
import com.seliote.fr.service.UserService;
import com.seliote.fr.util.ReflectUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.Instant;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 用户帐户 Service 实现
 *
 * @author seliote
 */
@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final RedisService redisService;
    private final SmsService smsService;
    private final TokenHandler tokenHandler;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(RedisService redisService,
                           SmsService smsService,
                           TokenHandler tokenHandler,
                           UserMapper userMapper) {
        this.redisService = redisService;
        this.smsService = smsService;
        this.tokenHandler = tokenHandler;
        this.userMapper = userMapper;
        log.debug("Construct {}", getClassName(this));
    }

    @Override
    public LoginSo login(@Valid LoginSi si) {
        if (!smsService.checkLogin(ReflectUtils.copy(si, CheckLoginSi.class))) {
            log.warn("Check sms for login failed for {}", si);
            return new LoginSo(1, null);
        }
        // 未注册的自动注册
        if (!isRegistered(ReflectUtils.copy(si, IsRegisteredSi.class))) {
            log.info("Auto register for: {}", si);
            register(ReflectUtils.copy(si, RegisterSi.class));
        }
        var id = userId(ReflectUtils.copy(si, UserIdSi.class));
        if (id == null || id.length() <= 0) {
            log.error("Error when select user id for: {}", si);
            throw new DataException();
        }
        var token = tokenHandler.generateToken(new TokenModel(id, Instant.now()));
        redisService.set(redisService.formatKey(TokenFilter.REDIS_NAMESPACE, id), token);
        log.info("Login success: {}", si);
        return new LoginSo(0, token);
    }

    @Override
    public boolean isRegistered(IsRegisteredSi si) {
        var count = userMapper.countUser(ReflectUtils.copy(si, CountUserMi.class));
        log.info("Count for: {}, result: {}", si, count);
        if (count == null || count < 0) {
            throw new ServiceException("Error when count user for: " + si.toString());
        }
        return count > 0;
    }

    @Override
    public void register(RegisterSi si) {
        var mi = ReflectUtils.copy(si, InsertMi.class);
        var tmp = System.currentTimeMillis() + "";
        mi.setNickname("fr_" + tmp.substring(tmp.length() - 7));
        mi.setGender(0);
        log.info("Register for: {}", si);
        userMapper.insert(mi);
    }

    @Override
    public String userId(UserIdSi si) {
        return userMapper.selectId(ReflectUtils.copy(si, SelectIdMi.class));
    }
}
