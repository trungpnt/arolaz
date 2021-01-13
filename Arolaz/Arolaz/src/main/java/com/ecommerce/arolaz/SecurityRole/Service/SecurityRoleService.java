package com.ecommerce.arolaz.SecurityRole.Service;


import com.ecommerce.arolaz.SecurityRole.Model.SecurityRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SecurityRoleService {
    SecurityRole addNewRole(SecurityRole role);
    SecurityRole findRoleByName(String roleName);
    Page<SecurityRole> findAll(Pageable pageable);
}
