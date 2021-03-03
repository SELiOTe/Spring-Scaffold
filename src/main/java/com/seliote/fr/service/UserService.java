package com.seliote.fr.service;

import com.seliote.fr.model.si.user.IsRegisteredSi;
import com.seliote.fr.model.si.user.LoginSi;
import com.seliote.fr.model.si.user.RegisterSi;
import com.seliote.fr.model.si.user.UserIdSi;
import com.seliote.fr.model.so.user.LoginSo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 用户帐户 Service
 *
 * @author seliote
 */
@Validated
public interface UserService {

    /**
     * 登录用户帐户，未注册的账户将会自动注册
     *
     * @param si SI
     * @return SO
     */
    LoginSo login(@Valid LoginSi si);

    /**
     * 判断用户是否注册
     *
     * @return 是返回 true，否则返回 false
     */
    boolean isRegistered(@Valid IsRegisteredSi si);

    /**
     * 用户注册
     */
    void register(@Valid RegisterSi si);

    /**
     * 获取用户 ID
     *
     * @param si SI
     * @return 用户 ID，当用户不存在时返回 null
     */
    String userId(@Valid UserIdSi si);
}
