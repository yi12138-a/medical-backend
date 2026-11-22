package com.qst.medical.mapper;

import com.qst.medical.model.PermissionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理数据访问接口
 */
@Mapper
public interface PermissionMapper {

    /**
     * 根据用户权限获取用户菜单列表
     *
     * @param roleName 角色名（形如 ROLE_1）
     */
    List<PermissionModel> getPermission(@Param("roleName") String roleName);
}
