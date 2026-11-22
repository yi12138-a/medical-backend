package com.qst.medical.mapper;

import com.qst.medical.model.AccountModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper
 */
@Mapper
public interface AccountMapper {

    /**
     * 根据用户名或手机号查询登录用户信息
     *
     * @param uname 用户名或手机号
     * @return 用户信息，不存在返回 null
     */
    AccountModel securityLogin(@Param("uname") String uname);
}
