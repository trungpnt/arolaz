package com.ecommerce.arolaz.SecurityUser.Controller;

import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.SecurityUser.RequestResponseModels.*;
import com.ecommerce.arolaz.SecurityUser.Service.SecurityUserService;
import com.ecommerce.arolaz.Utils.CustomizedPagingResponseModel;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.UserNotFoundException;
import com.ecommerce.arolaz.Utils.PasswordValidator;
import com.ecommerce.arolaz.Utils.Security.JwtProvider;
import com.ecommerce.arolaz.Utils.TokenValidator;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@CrossOrigin
@RequestMapping("/api")
public class SecurityUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUserController.class);

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private TokenValidator tokenValidator;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordValidator passwordValidator;

    @PostMapping("/user/signin")
    public UserTokenResponseModel signIn(@RequestBody @Valid UserLoginRequestModel userLoginRequestModel) {

        Optional<String> token = securityUserService.signIn(userLoginRequestModel.getRequiredEntry(), userLoginRequestModel.getPassword());

        if(!token.isPresent()) {
            throw new HttpServerErrorException((HttpStatus.FORBIDDEN), "Login Failed");
        }

        UserTokenResponseModel userTokenResponseModel = new UserTokenResponseModel();
        userTokenResponseModel.setToken(token.get());

        return userTokenResponseModel;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<UserTokenResponseModel> signUp(@RequestBody @Valid CreateUserRequestModel createUserRequestModel) {

        if (!passwordValidator.validate(createUserRequestModel.getPassword())){
            throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "PASSWORD MUST CONTAIN AT LEAST ONE NUMBER, ONE LOWERCASE AND UPPERCASE LETTER, ONE SPECIAL CHARACTER, AND CONTAIN AT LEAST 8 CHARACTERS!");
        }

        Optional<String> token = securityUserService.signUp(createUserRequestModel.getEmail(), createUserRequestModel.getFullName(),createUserRequestModel.getPhone(), createUserRequestModel.getPassword(), createUserRequestModel.getAddress());

        if (!token.isPresent()){
            throw new HttpServerErrorException((HttpStatus.FORBIDDEN), "PLEASE CHOOSE A DIFFERENT ACCOUNT!");
        }

        UserTokenResponseModel userTokenResponseModel = new UserTokenResponseModel();
        userTokenResponseModel.setToken(token.get());

        return new ResponseEntity<>(userTokenResponseModel,HttpStatus.CREATED);
    }

//    @GetMapping("/users")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public CustomizedPagingResponseModel<SecurityUserResponseModel> getAllUsers(@RequestParam("page") Integer page,
//                                                                                @RequestParam("rows") Integer rows, Pageable pageable) {
//
//        Page<SecurityUser> securityUserPage = securityUserService.findAll(pageable);
//        CustomizedPagingResponseModel<SecurityUserResponseModel> securityUserResponseModelCustomizedPagingResponseModel =
//                new CustomizedPagingResponseModel<>();
//
//        List<SecurityUserResponseModel> securityUserList = securityUserPage.getContent().stream().map(
//                securityUser -> toDto(securityUser)).collect(Collectors.toList());
//        securityUserResponseModelCustomizedPagingResponseModel.setPagingData(securityUserList);
//        securityUserResponseModelCustomizedPagingResponseModel.setTotalPage(securityUserPage.getTotalPages());
//
//        return securityUserResponseModelCustomizedPagingResponseModel;
//    }

    private SecurityUserResponseModel toDto(SecurityUser securityUser) {
        return new SecurityUserResponseModel(securityUser.getId().toString(), securityUser.getFullName(),securityUser.getEmail(), securityUser.getPhoneNumber(), securityUser.getAddress(), securityUser.getRoles().get(0).getRoleId().toString());
    }

    @GetMapping("/user/self")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseModel signInUserByToken(@RequestHeader(value = "Authorization") String headerVal) {

        String token = headerVal.substring(headerVal.indexOf(" "));

        tokenValidator.validateTokenUserAuthorization(token);

        String phone = jwtProvider.getPhone(token);

        Optional<SecurityUser> userRepositoryByPhone = securityUserService.findByPhoneNumber(phone);

        if(!userRepositoryByPhone.isPresent()){
            throw new UserNotFoundException(String.format("USER WITH %s NOT FOUND",phone));
        }

        return new UserLoginResponseModel(userRepositoryByPhone.get().getEmail(), userRepositoryByPhone.get().getFullName(), userRepositoryByPhone.get().getPhoneNumber(), userRepositoryByPhone.get().getAddress());
    }

    @PutMapping("/user/edit")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<SecurityUserResponseModel> updateUserByToken(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal, @RequestBody @Valid EditUserRequestModel editUserRequestModel) {

        String token = headerVal.substring(headerVal.indexOf(" "));
        tokenValidator.validateTokenUserAuthorization(token);
        String phoneFromToken = jwtProvider.getPhone(token);
        String userId = jwtProvider.getUserId(token);

        Optional<SecurityUser> user = securityUserService.findByPhoneNumber(phoneFromToken);

        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("User with %id not exists",user.get().getId().toString()));
        }

        String newEmail = editUserRequestModel.getEmail();
        String newPhone = editUserRequestModel.getPhone();

        //check unique email and phone
        securityUserService.checkUniqueEmailAndPhoneNumber(newPhone,newEmail);

        Optional<SecurityUser> toUpdateUser = securityUserService.findByUserId(new ObjectId(userId));
        toUpdateUser.get().setPhoneNumber(newPhone);
        toUpdateUser.get().setEmail(newEmail);

        Optional<SecurityUser> newlyUpdatedUser = securityUserService.addNewUser(toUpdateUser.get());

        return new ResponseEntity<>(toDto(newlyUpdatedUser.get()),HttpStatus.CREATED);
    }

    private SecurityUser toSecurityUser(SecurityUser securityUser, EditUserRequestModel editUserRequestModel){
        securityUser.setEmail(editUserRequestModel.getEmail());
        securityUser.setFullName(editUserRequestModel.getFullName());
        securityUser.setPhoneNumber(editUserRequestModel.getPhone());
        securityUser.setAddress(editUserRequestModel.getAddress());
        return securityUser;
    }

    private SecurityUser toPersistUser(EditUserRequestModel editUserRequestModel){
        return new SecurityUser(editUserRequestModel.getFullName(), editUserRequestModel.getPhone(), editUserRequestModel.getAddress() , editUserRequestModel.getEmail());
    }


    @PutMapping("/user/edit/password")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SecurityUserResponseModel> updateUserPasswordByToken(HttpServletRequest request, @RequestHeader(value = "Authorization") String headerVal, @RequestBody @Valid EditUserPasswordRequestModel editUserPasswordRequestModel) {

        String token = headerVal.substring(headerVal.indexOf(" "));

        /**
         * Validate token and role claimed
         * */
        tokenValidator.validateTokenUserAuthorization(token);

        String userId = jwtProvider.getUserId(token);

        /**
         * Search for user's existence
         * */
        Optional<SecurityUser> user = securityUserService.findByUserId(new ObjectId(userId));

        /*
        * * Attempt login with current password
        * */
        String requiredEntry = jwtProvider.getPhone(token);
        String claimedPassword = editUserPasswordRequestModel.getCurrentPassword();
        Optional<String> attemptToken = securityUserService.signIn(requiredEntry,claimedPassword);

        if(!attemptToken.isPresent()) {
            throw new HttpServerErrorException((HttpStatus.FORBIDDEN), "Login Failed!");
        }

        /**
         * Update user's new password
         * */
        user.get().setPassword(passwordEncoder.encode(editUserPasswordRequestModel.getNewPassword()));

        Optional<SecurityUser> newlyUpdated = securityUserService.updateUser(user.get());

        return new ResponseEntity<>(toDto(newlyUpdated.get()),HttpStatus.CREATED);
    }
}
