package com.qst.medical.service;

import com.qst.medical.domain.Permission;
import com.qst.medical.mapper.PermissionMapper;
import com.qst.medical.model.PermissionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户菜单管理服务
 */
@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 通过用户权限名获取用户菜单列表（一级菜单带 children）
     */
    public List<PermissionModel> getAllPermission(String roleName) {
        String finalRoleName = "ROLE_" + roleName;
        List<PermissionModel> allPermission = permissionMapper.getPermission(finalRoleName.trim());
        List<PermissionModel> finalPermission = new ArrayList<>();
        for (PermissionModel per : allPermission) {
            if (Objects.equals(per.getPid(), 0)) {
                finalPermission.add(selectChildren(per, allPermission, finalRoleName));
            }
        }
        return finalPermission;
    }

    /**
     * 为父节点添加 children
     */
    public PermissionModel selectChildren(PermissionModel father, List<PermissionModel> allPermission,
                                          String finalRoleName) {
        List<Permission> list = new ArrayList<>();
        allPermission.forEach(item -> {
            if (!"ROLE_1".equals(finalRoleName) && item.getMeta() != null
                    && item.getMeta().getTitle() != null) {
                String title = item.getMeta().getTitle().replace("管理", "查询");
                item.getMeta().setTitle(title);
            }
            if (Objects.equals(father.getId(), item.getPid())) {
                father.setChildren(list);
                father.getChildren().add(selectChildren(item, allPermission, finalRoleName));
            }
        });
        return father;
    }
}
