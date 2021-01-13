package com.ecommerce.arolaz.SecurityRole.Model;

import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.querydsl.core.annotations.QueryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@QueryEntity
@Document(collection = "security_role")
public class SecurityRole implements GrantedAuthority {
    //private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId roleId;

    private String roleName;

    public SecurityRole(){}

    private List<SecurityUser> users;

    public SecurityRole(String roleName, List<SecurityUser> users) {
        this.roleName = roleName;
        this.users = users;
    }

    public SecurityRole(String roleName) {
        this.roleName = roleName;
    }

    public SecurityRole(List<SecurityUser> users) {
        this.users = users;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<SecurityUser> getUsers() {
        return users;
    }

    public void setUsers(List<SecurityUser> users) {
        this.users = users;
    }



    @Override
    public String getAuthority() {
        return roleName;
    }

    public ObjectId getRoleId() {
        return this.roleId;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
