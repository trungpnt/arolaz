package com.ecommerce.arolaz.Arolaz.SecurityUser.Controller;


import com.ecommerce.arolaz.Arolaz.Security.JwtProvider;
import com.ecommerce.arolaz.Arolaz.SecurityRole.Repository.SecurityRoleRepository;
import com.ecommerce.arolaz.Arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.Arolaz.SecurityUser.Repository.SecurityUserRepository;
import com.ecommerce.arolaz.Arolaz.SecurityUser.RequestResponseModels.*;
import com.ecommerce.arolaz.Arolaz.SecurityUser.Service.SecurityUserService;
import com.ecommerce.arolaz.Arolaz.utils.CustomizedPagingResponseModel;
import com.ecommerce.arolaz.Arolaz.utils.PasswordValidator;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public UserTokenResponseModel signUp(@RequestBody @Valid CreateUserRequestModel createUserRequestModel) {

        if (!passwordValidator.validate(createUserRequestModel.getPassword())){
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Please choose another password!") ;
        }

        Optional<String> token = securityUserService.signUp(createUserRequestModel.getEmail(), createUserRequestModel.getFirstName(), createUserRequestModel.getLastName(), createUserRequestModel.getPhone(), createUserRequestModel.getPassword(), createUserRequestModel.getAddress());

        if (!token.isPresent()){
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        UserTokenResponseModel userTokenResponseModel = new UserTokenResponseModel();
        userTokenResponseModel.setToken(token.get());
        return userTokenResponseModel;
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
        return new SecurityUserResponseModel(securityUser.getId().toString(), securityUser.getFirstName(),securityUser.getLastName(), securityUser.getEmail(), securityUser.getPhone(), securityUser.getAddress(), securityUser.getRoles().get(0).getRoleId().toString());
    }

    @GetMapping("/self")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseModel signInUserByToken(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal) {
        String token = headerVal.substring(headerVal.indexOf(" "), headerVal.length());
        String phone = jwtProvider.getPhone(token);
        //String username =  jwtProvider.getUsername(token.get());

        Optional<SecurityUser> userRepositoryByPhone = securityUserRepository.findByPhone(phone);
        return new UserLoginResponseModel(userRepositoryByPhone.get().getEmail(), userRepositoryByPhone.get().getFirstName(), userRepositoryByPhone.get().getLastName(), userRepositoryByPhone.get().getPhone(), userRepositoryByPhone.get().getAddress());
    }

    @PutMapping("/self/edit")
    @PreAuthorize("hasAuthority('USER')")
    public HttpStatus updateUserByToken(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal, @RequestBody @Valid EditUserRequestModel editUserRequestModel) {
        String token = headerVal.substring(headerVal.indexOf(" "), headerVal.length());
        String phoneFromToken = jwtProvider.getPhone(token);
        String userId = jwtProvider.getUserId(token);
        if (!securityUserService.isValidToken(token)) {
            throw new RuntimeException("token is invalid");
        }
        Optional<SecurityUser> user = securityUserRepository.findByPhone(phoneFromToken);

        if (!user.isPresent()) {
            throw new RuntimeException("User not exists");
        }
        String email = editUserRequestModel.getEmail();
        String phone = editUserRequestModel.getPhone();

        if(securityUserRepository.findByEmail(email).isPresent() || securityUserRepository.findByPhone(phone).isPresent()){
            throw new RuntimeException("Email or Phone has already existed!");
        }

        ObjectId userIdToDelete = new ObjectId(userId);
        Optional<SecurityUser> preservedUser = securityUserRepository.findById(userIdToDelete);

        securityUserRepository.deleteById(userIdToDelete);
        SecurityUser newlyUpdatedUser = converter(preservedUser.get(),editUserRequestModel);
        securityUserService.updateUser(newlyUpdatedUser);

        return HttpStatus.ACCEPTED;
    }
    private SecurityUser converter(SecurityUser securityUser, EditUserRequestModel editUserRequestModel){
        securityUser.setEmail(editUserRequestModel.getEmail());
        securityUser.setFirstName(editUserRequestModel.getFirstName());
        securityUser.setLastName(editUserRequestModel.getLastName());
        securityUser.setPhone(editUserRequestModel.getPhone());
        securityUser.setAddress(editUserRequestModel.getAddress());
        return securityUser;
    }

    private SecurityUser toPersistUser(EditUserRequestModel editUserRequestModel){
        return new SecurityUser(editUserRequestModel.getEmail(), editUserRequestModel.getFirstName(), editUserRequestModel.getLastName(), editUserRequestModel.getPhone(), editUserRequestModel.getPassword(), editUserRequestModel.getAddress());
    }
    @PutMapping("/password")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus updateUserPasswordByToken(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal, @RequestBody @Valid EditUserPasswordRequestModel editUserPasswordRequestModel) {
        String token = headerVal.substring(headerVal.indexOf(" "), headerVal.length());
        String phone = jwtProvider.getPhone(token);

        if (!securityUserService.isValidToken(token)) {
            return HttpStatus.BAD_REQUEST;
        }

        Optional<SecurityUser> user = securityUserRepository.findByPhone(phone);
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
