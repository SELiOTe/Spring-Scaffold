package com.seliote.fr.mapper;

import com.seliote.fr.model.mi.user.CountUserMi;
import com.seliote.fr.model.mi.user.InsertMi;
import com.seliote.fr.model.mi.user.SelectIdMi;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 用户 Mapper
 *
 * @author seliote
 */
@Validated
public interface UserMapper {

    /**
     * 插入一条用户数据
     *
     * @param mi MI
     */
    void insert(@Valid @Param("mi") InsertMi mi);

    /**
     * 获取指定手机号码的注册数量
     *
     * @param mi MI
     * @return 注册数量
     */
    Long countUser(@Valid @Param("mi") CountUserMi mi);

    /**
     * 通过手机号码获取用户 ID
     *
     * @param mi MI
     * @return 存在用户信息时返回 ID，否则返回 null
     */
    String selectId(@Valid @Param("mi") SelectIdMi mi);
}
