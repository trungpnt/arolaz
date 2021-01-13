package com.ecommerce.arolaz.SecurityRole.Controller;


import com.ecommerce.arolaz.SecurityRole.Model.SecurityRole;
import com.ecommerce.arolaz.SecurityRole.Repository.SecurityRoleRepository;
import com.ecommerce.arolaz.SecurityRole.RequestResponseModels.CreateRoleRequestModel;
import com.ecommerce.arolaz.SecurityRole.RequestResponseModels.RoleResponseModel;
import com.ecommerce.arolaz.SecurityRole.Service.SecurityRoleService;
import com.ecommerce.arolaz.SecurityUser.Service.SecurityUserService;
import com.ecommerce.arolaz.Utils.CustomizedPagingResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class SecurityRoleController {
    @Autowired
    private SecurityRoleService securityRoleService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path="/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SecurityRole> addNewRole(@Valid @RequestBody CreateRoleRequestModel createRoleRequestModel){
        SecurityRole persistRole = null;
        if(securityRoleService.findRoleByName(createRoleRequestModel.getRoleName()) != null ){
            throw new RuntimeException("The role with this name already exists".toUpperCase());
        }
        SecurityRole role = new SecurityRole(createRoleRequestModel.getRoleName());

        persistRole = securityRoleService.addNewRole(role);
        return new ResponseEntity<>(persistRole,HttpStatus.CREATED);
    }

    @GetMapping(path="/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public CustomizedPagingResponseModel<RoleResponseModel> getAllRoles(Pageable pageable)
    {
        Page<SecurityRole> securityRolePage = securityRoleService.findAll(pageable);

        CustomizedPagingResponseModel<RoleResponseModel> pagingResponseModel = new CustomizedPagingResponseModel<>();
        List<RoleResponseModel> roleResponseModelList = securityRolePage.getContent().stream().map(
                securityRole -> toDto(securityRole)).collect(Collectors.toList());

        CustomizedPagingResponseModel<RoleResponseModel> customizedPagingResponseModel = new CustomizedPagingResponseModel<>();
        customizedPagingResponseModel.setPagingData(roleResponseModelList);
        customizedPagingResponseModel.setTotalPage(securityRolePage.getTotalPages());

        return customizedPagingResponseModel;
    }
    private RoleResponseModel toDto (SecurityRole securityRole){
        return new RoleResponseModel(securityRole.getRoleId().toString(),securityRole.getRoleName());
    }

    @GetMapping(path="/role/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RoleResponseModel> findRoleByName(@RequestParam("roleName") String roleName){
        RoleResponseModel roleResponseModel = new RoleResponseModel();
        SecurityRole securityRole = securityRoleService.findRoleByName(roleName.toUpperCase());

        if(securityRole == null){
            throw new EntityNotFoundException(roleName);
        }

        roleResponseModel.setRoleId(securityRole.getRoleId().toString());
        roleResponseModel.setRoleName(securityRole.getRoleName());

        return new ResponseEntity<RoleResponseModel>(roleResponseModel,HttpStatus.OK);
    }

}
