package com.ecommerce.arolaz.Arolaz.SecurityRole.RequestResponseModels;

public class CreateRoleRequestModel {
    private String roleName;

    public CreateRoleRequestModel(String roleName) {
        this.roleName = roleName;
    }
    public CreateRoleRequestModel(){}
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
