package com.seliote.fr.model.mi.user;

import com.seliote.fr.annotation.validation.CountryCode;
import com.seliote.fr.annotation.validation.Gender;
import com.seliote.fr.annotation.validation.Nickname;
import com.seliote.fr.annotation.validation.TelNo;
import lombok.Data;

/**
 * 获取指定手机号码的注册数量 MI
 *
 * @author seliote
 */
@Data
public class InsertMi {

    // 国际电话呼叫码
    @CountryCode
    private String countryCode;

    // 手机号码
    @TelNo
    private String telNo;

    // 昵称
    @Nickname
    private String nickname;

    // 性别
    @Gender
    private Integer gender;
}
