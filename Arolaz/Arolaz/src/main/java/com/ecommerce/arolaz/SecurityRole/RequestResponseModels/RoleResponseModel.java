package com.ecommerce.arolaz.SecurityRole.RequestResponseModels;

public class RoleResponseModel {
    private String roleId;

    private String roleName;
    public RoleResponseModel(){}
    public RoleResponseModel(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
