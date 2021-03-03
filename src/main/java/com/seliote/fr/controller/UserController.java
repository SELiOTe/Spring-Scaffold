package com.seliote.fr.controller;

import com.seliote.fr.annotation.ApiFrequency;
import com.seliote.fr.annotation.stereotype.ApiController;
import com.seliote.fr.config.api.ApiFrequencyType;
import com.seliote.fr.exception.ApiException;
import com.seliote.fr.model.ci.user.LoginCi;
import com.seliote.fr.model.co.Co;
import com.seliote.fr.model.co.user.LoginCo;
import com.seliote.fr.model.si.user.LoginSi;
import com.seliote.fr.service.UserService;
import com.seliote.fr.util.ReflectUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static com.seliote.fr.util.ReflectUtils.getClassName;

/**
 * 用户帐户 Controller
 *
 * @author seliote
 */
@Log4j2
@ApiController
@RequestMapping(value = "user", method = {RequestMethod.POST})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        log.debug("Construct {}", getClassName(this));
    }

    /**
     * 登录用户帐户，未注册的账户将会自动注册
     *
     * @param ci CI
     * @return CO
     */
    @ApiFrequency(type = ApiFrequencyType.ARG, key = "countryCode&&telNo")
    @RequestMapping("login")
    @ResponseBody
    public Co<LoginCo> login(@Valid @RequestBody LoginCi ci) {
        var so = userService.login(ReflectUtils.copy(ci, LoginSi.class));
        if (so.getLoginResult() == 0 || so.getLoginResult() == 1) {
            return Co.cco(ReflectUtils.copy(so, LoginCo.class));
        } else {
            log.error("login for: {}, service return: {}", ci, so);
            throw new ApiException("service return value error");
        }
    }

    @ApiFrequency()
    @RequestMapping("info")
    @ResponseBody
    public Co<Void> info() {
        return Co.cco(null);
    }
}
