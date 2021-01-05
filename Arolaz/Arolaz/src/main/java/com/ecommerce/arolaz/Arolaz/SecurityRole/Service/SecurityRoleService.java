package com.ecommerce.arolaz.Arolaz.SecurityRole.Service;


import com.ecommerce.arolaz.Arolaz.SecurityRole.Model.SecurityRole;

public interface SecurityRoleService {
    SecurityRole addNewRole(SecurityRole role);
    SecurityRole findRoleByName(String roleName);
}
