package com.qst.medical.mapper;

import com.qst.medical.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 医师模块使用的账号数据访问接口
 * <p>
 * 说明：任务4 需要“重置密码、注册账号、更新账号、检查手机号”等 account 表操作，
 * 但编写约定要求不得修改已有的 AccountMapper 及其 XML，
 * 因此把这些 account 表操作单独放在本接口中实现（SQL 与规格一致）。
 * 对应映射文件：src/main/resources/mapper/DoctorAccountMapper.xml
 */
@Mapper
public interface DoctorAccountMapper {

    /*校验手机号是否已被医师使用；doctorId 不为空时排除该医师自身（修改场景）*/
    int checkPhone(@Param("phoneNumber") String phoneNumber, @Param("doctorId") Long doctorId);

    /*重置密码*/
    int resetPwd(@Param("id") Long id, @Param("pwd") String pwd);

    /*注册账号*/
    int regist(AccountEntity entity);

    /*更新账户信息*/
    int updateAccount(AccountEntity entity);

    /*根据医师id查询其关联的账号id（新增的辅助查询，用于修改/reset时定位账号）*/
    Long getAccountIdByDoctorId(@Param("doctorId") Long doctorId);
}
