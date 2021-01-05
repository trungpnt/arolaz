package com.ecommerce.arolaz.SecurityRole.Service;


import com.ecommerce.arolaz.SecurityRole.Model.SecurityRole;

public interface SecurityRoleService {
    SecurityRole addNewRole(SecurityRole role);
    SecurityRole findRoleByName(String roleName);
}
