package com.seliote.fr.util;

import com.seliote.fr.model.si.user.IsRegisteredSi;
import com.seliote.fr.model.si.user.LoginSi;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author seliote
 */
@Log4j2
class ReflectUtilsTest {

    @Test
    void copy() {
        var a = new IsRegisteredSi();
        a.setCountryCode("86");
        a.setTelNo("13227753101");
        var b = ReflectUtils.copy(a, LoginSi.class);
        log.info(a);
        log.info(b);
    }
}