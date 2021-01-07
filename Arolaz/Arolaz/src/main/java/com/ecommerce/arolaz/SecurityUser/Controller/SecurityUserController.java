package com.ecommerce.arolaz.SecurityUser.Controller;


import com.ecommerce.arolaz.utils.ExceptionHandlers.UserNotFoundException;
import com.ecommerce.arolaz.utils.Security.JwtProvider;
import com.ecommerce.arolaz.SecurityRole.Repository.SecurityRoleRepository;
import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.SecurityUser.Repository.SecurityUserRepository;
import com.ecommerce.arolaz.SecurityUser.RequestResponseModels.*;
import com.ecommerce.arolaz.SecurityUser.Service.SecurityUserService;
import com.ecommerce.arolaz.utils.CustomizedPagingResponseModel;
import com.ecommerce.arolaz.utils.PasswordValidator;
import com.ecommerce.arolaz.utils.TokenValidator;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
//@Slf4j
@CrossOrigin
@RequestMapping("/api")
public class SecurityUserController {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUserController.class);

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private TokenValidator tokenValidator;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Autowired
    private SecurityRoleRepository securityRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordValidator passwordValidator;

    @PostMapping("/auth/login")
    public UserTokenResponseModel login(@RequestBody @Valid UserLoginRequestModel userLoginRequestModel) {
        Optional<String> token = securityUserService.signIn(userLoginRequestModel.getRequiredEntry(), userLoginRequestModel.getPassword());
        if(!token.isPresent())
            throw new HttpServerErrorException((HttpStatus.FORBIDDEN), "Login Failed");
        UserTokenResponseModel userTokenResponseModel = new UserTokenResponseModel();
        userTokenResponseModel.setToken(token.get());
        return userTokenResponseModel;
    }


    @PostMapping("/auth/register")
    public ResponseEntity<UserTokenResponseModel> signUp(@RequestBody @Valid CreateUserRequestModel createUserRequestModel) {

        if (!passwordValidator.validate(createUserRequestModel.getPassword())){
            throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "Please choose another password!") ;
        }

        Optional<String> token = securityUserService.signUp(createUserRequestModel.getEmail(), createUserRequestModel.getFullName(),createUserRequestModel.getPhone(), createUserRequestModel.getPassword(), createUserRequestModel.getAddress());

        if (!token.isPresent()){
            throw new HttpServerErrorException((HttpStatus.FORBIDDEN), "Please choose a different account!");
        }
        UserTokenResponseModel userTokenResponseModel = new UserTokenResponseModel();
        userTokenResponseModel.setToken(token.get());
        return new ResponseEntity<>(userTokenResponseModel,HttpStatus.CREATED);
    }

//    @GetMapping("/users")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public Page<SecurityUserResponseModel> getAllUsers(@RequestParam("page") Integer page,
//                                                       @RequestParam("rows") Integer rows, Pageable pageable) {
//        Page<SecurityUser> securityUserPage = securityUserRepository.findAll(pageable);
//                List<SecurityUserResponseModel> securityUserList = securityUserPage.getContent().stream().map(
//                securityUser -> toDto(securityUser)).collect(Collectors.toList());
//        return new PageImpl<SecurityUserResponseModel>(securityUserList, pageable, securityUserPage.getTotalPages());
//    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CustomizedPagingResponseModel<SecurityUserResponseModel> getAllUsers(@RequestParam("page") Integer page,
                                                                                @RequestParam("rows") Integer rows, Pageable pageable) {
        Page<SecurityUser> securityUserPage = securityUserRepository.findAll(pageable);
        CustomizedPagingResponseModel<SecurityUserResponseModel> securityUserResponseModelCustomizedPagingResponseModel =
                new CustomizedPagingResponseModel<>();

        List<SecurityUserResponseModel> securityUserList = securityUserPage.getContent().stream().map(
                securityUser -> toDto(securityUser)).collect(Collectors.toList());
        securityUserResponseModelCustomizedPagingResponseModel.setPagingData(securityUserList);
        securityUserResponseModelCustomizedPagingResponseModel.setTotalPage(securityUserPage.getTotalPages());

        return securityUserResponseModelCustomizedPagingResponseModel;
    }

    private SecurityUserResponseModel toDto(SecurityUser securityUser) {
        return new SecurityUserResponseModel(securityUser.getId().toString(), securityUser.getFullName(),securityUser.getEmail(), securityUser.getPhoneNumber(), securityUser.getAddress(), securityUser.getRoles().get(0).getRoleId().toString());
    }

    @GetMapping("/self")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseModel signInUserByToken(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal) {
        String token = headerVal.substring(headerVal.indexOf(" "), headerVal.length());
        String phone = jwtProvider.getPhone(token);
        //String username =  jwtProvider.getUsername(token.get());

        Optional<SecurityUser> userRepositoryByPhone = securityUserRepository.findByPhoneNumber(phone);
        return new UserLoginResponseModel(userRepositoryByPhone.get().getEmail(), userRepositoryByPhone.get().getFullName(), userRepositoryByPhone.get().getPhoneNumber(), userRepositoryByPhone.get().getAddress());
    }

    @PutMapping("/self/edit")
    @PreAuthorize("hasAuthority('USER')")
    public HttpStatus updateUserByToken(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal, @RequestBody @Valid EditUserRequestModel editUserRequestModel) {
        String token = headerVal.substring(headerVal.indexOf(" "), headerVal.length());
        tokenValidator.validateTokenUserAuthorization(token);
        String phoneFromToken = jwtProvider.getPhone(token);
        String userId = jwtProvider.getUserId(token);

        Optional<SecurityUser> user = securityUserRepository.findByPhoneNumber(phoneFromToken);

        if (!user.isPresent()) {
            String notFoundUserId = user.get().getId().toString();
            throw new UserNotFoundException(String.format("User with %id not exists",notFoundUserId));
        }
        String email = editUserRequestModel.getEmail();
        String phone = editUserRequestModel.getPhoneNumber();

        if(securityUserRepository.findByEmail(email).isPresent() || securityUserRepository.findByPhoneNumber(phone).isPresent()){
            throw new RuntimeException("Email or Phone has already existed!");
        }

        ObjectId userIdToDelete = new ObjectId(userId);
        Optional<SecurityUser> preservedUser = securityUserRepository.findById(userIdToDelete);

        securityUserRepository.deleteById(userIdToDelete);
        SecurityUser newlyUpdatedUser = toSecurityUser(preservedUser.get(),editUserRequestModel);
        securityUserService.updateUser(newlyUpdatedUser);

        return HttpStatus.ACCEPTED;
    }
    private SecurityUser toSecurityUser(SecurityUser securityUser, EditUserRequestModel editUserRequestModel){
        securityUser.setEmail(editUserRequestModel.getEmail());
        securityUser.setFullName(editUserRequestModel.getFullName());
        securityUser.setPhoneNumber(editUserRequestModel.getPhoneNumber());
        securityUser.setAddress(editUserRequestModel.getAddress());
        return securityUser;
    }

    private SecurityUser toPersistUser(EditUserRequestModel editUserRequestModel){
        return new SecurityUser(editUserRequestModel.getFullName(), editUserRequestModel.getPhoneNumber(), editUserRequestModel.getAddress(), editUserRequestModel.getPassword(), editUserRequestModel.getEmail());
    }
    @PutMapping("/password")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus updateUserPasswordByToken(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal, @RequestBody @Valid EditUserPasswordRequestModel editUserPasswordRequestModel) {
        String token = headerVal.substring(headerVal.indexOf(" "), headerVal.length());
        tokenValidator.validateTokenUserAuthorization(token);
        String phone = jwtProvider.getPhone(token);

        Optional<SecurityUser> user = securityUserRepository.findByPhoneNumber(phone);
        if (!user.isPresent()) {
            return HttpStatus.CONFLICT;
        }
        String passwordToCheck = user.get().getPassword();
        if(passwordToCheck != editUserPasswordRequestModel.getPassword()){
            throw new RuntimeException("WRONG PASSWORD");
        }
        user.get().setPassword(passwordEncoder.encode(editUserPasswordRequestModel.getNewPassword()));
        SecurityUser newlyUpdatedUser = user.get();
        securityUserRepository.delete(securityUserRepository.findByEmail(user.get().getEmail()).get());
        securityUserService.updateUser(newlyUpdatedUser);
        return HttpStatus.OK;
    }
}
