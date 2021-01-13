package com.ecommerce.arolaz.SecurityRole.Service;


import com.ecommerce.arolaz.SecurityRole.Model.SecurityRole;
import com.ecommerce.arolaz.SecurityRole.Repository.SecurityRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SecurityRoleServiceImpl implements SecurityRoleService {
    @Autowired
    private SecurityRoleRepository securityRoleRepository;

    public SecurityRole addNewRole(SecurityRole securityRole) {
        return securityRoleRepository.save(securityRole);
    }

    public SecurityRole findRoleByName(String roleName) {
        //Handling
        SecurityRole toFind = securityRoleRepository.findByRoleName(roleName);
        return toFind;
    }

    public Page<SecurityRole> findAll(Pageable pageable){
        return securityRoleRepository.findAll(pageable);
    }
    
}
