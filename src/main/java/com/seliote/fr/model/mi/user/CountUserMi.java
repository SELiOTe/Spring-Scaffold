package com.seliote.fr.model.mi.user;

import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.TelNo;
import lombok.Data;

/**
 * 获取指定手机号码的注册数量 MI
 *
 * @author seliote
 */
@Data
public class CountUserMi {

    // 国际电话呼叫码
    @CountryCode
    private String countryCode;

    // 手机号码
    @TelNo
    private String telNo;
}
