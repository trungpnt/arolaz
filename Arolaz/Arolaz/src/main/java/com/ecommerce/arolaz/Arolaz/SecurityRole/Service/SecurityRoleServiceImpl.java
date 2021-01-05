package com.ecommerce.arolaz.Arolaz.SecurityRole.Service;


import com.ecommerce.arolaz.Arolaz.SecurityRole.Model.SecurityRole;
import com.ecommerce.arolaz.Arolaz.SecurityRole.Repository.SecurityRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityRoleServiceImpl implements SecurityRoleService {
    @Autowired
    private SecurityRoleRepository securityRoleRepository;

    public SecurityRole addNewRole(SecurityRole securityRole) {
        return securityRoleRepository.save(securityRole);
    }

    public SecurityRole findRoleByName(String roleName) {
        SecurityRole toFind = securityRoleRepository.findByRoleName(roleName);
        return toFind;
    }
    
}
