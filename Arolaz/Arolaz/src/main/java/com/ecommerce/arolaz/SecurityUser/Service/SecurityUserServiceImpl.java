package com.ecommerce.arolaz.SecurityUser.Service;

import com.ecommerce.arolaz.Utils.ExceptionHandlers.UserNotFoundException;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.UserWithEmailAlreadyExistsException;
import com.ecommerce.arolaz.Utils.ExceptionHandlers.UserWithPhoneNumberAlreadyExistsException;
import com.ecommerce.arolaz.Utils.Security.JwtProvider;
import com.ecommerce.arolaz.SecurityRole.Model.SecurityRole;
import com.ecommerce.arolaz.SecurityRole.Repository.SecurityRoleRepository;
import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.SecurityUser.Repository.SecurityUserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUserServiceImpl.class);

    private SecurityUserRepository securityUserRepository;

    private SecurityRoleRepository securityRoleRepository;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    private JwtProvider jwtProvider;

    @Autowired
    public SecurityUserServiceImpl(SecurityRoleRepository securityRoleRepository, SecurityUserRepository securityUserRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.securityUserRepository = securityUserRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.securityRoleRepository = securityRoleRepository;
    }

    @Override
    public boolean checkUniqueEmailAndPhoneNumber(String phone, String email){
        /**
         * user's EMAIL and PHONE must be unique
         * */
        if(securityUserRepository.findByPhoneNumber(phone).isPresent()){
            throw new UserWithPhoneNumberAlreadyExistsException(String.format("PHONE NUMBER ALREADY EXISTS + '%s'",phone));
        }

        if(securityUserRepository.findByEmail(email).isPresent()){
            throw new UserWithEmailAlreadyExistsException(String.format("EMAIL ALREADY EXISTS + '%s'",email));
        }

        return true;
    }

    /**
     * Create a new user in the database.
     *
     * @param email username
     * @param phone username
     * @param password password
     * @param fullName fullName
     * @return Optional of the Java Web Token, empty otherwise.
     */
    @Override
    public Optional<String> signUp(String email, String fullName, String phone, String password, String address) {

        LOGGER.info("New user is registering a new account ");
        Optional<SecurityUser> persistUser;
        Optional<String> token;
        SecurityRole role;

        //Checking phone and email
        checkUniqueEmailAndPhoneNumber(phone,email);

        /**
         * Checking user's fullName and explicitly assignin roles
         * */
        if(fullName.startsWith("Admin") || fullName.startsWith("ADMIN")){
            role = securityRoleRepository.findByRoleName("ADMIN");
        }
        else{
            role = securityRoleRepository.findByRoleName("USER");
        }

        SecurityUser user = new SecurityUser(email,fullName,phone
                ,passwordEncoder.encode(password),address, Arrays.asList(role));

        persistUser = Optional.of(securityUserRepository.save(user));
        token = Optional.of(jwtProvider.createToken(persistUser,persistUser.get().getRoles()));

        LOGGER.info("token generated {}", token);

        return token;
    }


    @Override
    public Optional<SecurityUser> signUpForTesting(String phone, String email, String fullName, String password) {
        Optional<SecurityUser> user = null;

        if (!securityUserRepository.findByPhoneNumber(phone).isPresent() &&
                !securityUserRepository.findByEmail(email).isPresent()){

            user = Optional.of(securityUserRepository.save(new SecurityUser(
                    phone, email, fullName, passwordEncoder.encode(password))));
        }
        return user;
    }

    @Override
    public Optional<SecurityUser> findByUserId(ObjectId userId){
        Optional<SecurityUser> tryFind = securityUserRepository.findById(userId);
        if(!tryFind.isPresent()){
            throw new UserNotFoundException(String.format("USER WITH '%s' NOT FOUND",userId.toString()));
        }
        return tryFind;
    }

    @Override
    public Page<SecurityUser> findAll(Pageable pageable){
        return securityUserRepository.findAll(pageable);
    }

    @Override
    public Optional<SecurityUser> addNewUser(SecurityUser securityUser){
        return Optional.of(securityUserRepository.save(securityUser));
    }

    @Override
    public Optional<SecurityUser> updateUser(SecurityUser securityUser){
        return Optional.of(securityUserRepository.save(securityUser));
    }

    @Override
    public Optional<SecurityUser> findByEmail(String email) {
        Optional<SecurityUser> tryFind = securityUserRepository.findByEmail(email);
        if(!tryFind.isPresent()){
            throw new UserNotFoundException(String.format("USER WITH %S NOT FOUND",email));
        }
        return tryFind;
    }

    @Override
    public Optional<SecurityUser> findByPhoneNumber(String phone) {

        Optional<SecurityUser> tryFind = securityUserRepository.findByPhoneNumber(phone);
        if(!tryFind.isPresent()){
            throw new UserNotFoundException(String.format("USER WITH %S NOT FOUND",phone));
        }
        return tryFind;
    }

    @Override
    public Optional<SecurityUser> findByFullName(String fullName) {
        return securityUserRepository.findByFullName(fullName);
    }

    /**
     * Sign in a user into the application, with JWT-enabled authentication
     *
     * @param requiredEntry username
     * @param password      password
     * @return Optional of the Java Web Token, empty otherwise
     */
    @Override
    public Optional<String> signIn(String requiredEntry, String password) {
        Optional<SecurityUser> user;

        if (isNumeric(requiredEntry)) {
            user = securityUserRepository.findByPhoneNumber(requiredEntry);
        } else {
            user = securityUserRepository.findByEmail(requiredEntry);
        }

        Optional<String> token = Optional.empty();

        if (user.isPresent()) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(requiredEntry, password));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            if(isNumeric(requiredEntry)){
                user = securityUserRepository.findByPhoneNumber(userDetails.getUsername());
            }
            else{
                user = securityUserRepository.findByEmail(userDetails.getUsername());
            }

            token = Optional.of(jwtProvider.createToken(user,user.get().getRoles()));

            LOGGER.info("token generated {}", token);
        }
        return token;
    }

    @Override
    public Optional<SecurityUser> signInForTesting(String requiredEntry, String password) {

        Optional<SecurityUser> user;

        if (isNumeric(requiredEntry)) {
            user = securityUserRepository.findByPhoneNumber(requiredEntry);
        } else {
            user = securityUserRepository.findByEmail(requiredEntry);
        }

        if (user.isPresent()) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(requiredEntry, password));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            if(isNumeric(requiredEntry)){
                user = securityUserRepository.findByPhoneNumber(userDetails.getUsername());
            }
            else{
                user = securityUserRepository.findByEmail(userDetails.getUsername());
            }
        }
        return user;
    }


    @Override
    public void delete(SecurityUser securityUser){
        securityUserRepository.delete(securityUser);
    }

    @Override
    public void deleteByUserId(ObjectId userId){
        securityUserRepository.deleteById(userId);
    }

    public String formatToken(Optional<String> token){
        /**
         * Remove the redundant "optional" part from the generated token string
         */
        return token.toString().substring("Optional[".length(),token.toString().length()-2);
    }

    @Override
    /**
     * The function will return true if the input is phone number
     */
    public boolean isNumeric (String requiredInput){
        if (requiredInput == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(requiredInput);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<SecurityUser> signInWithToken(String token) {
        Optional<SecurityUser> securityUser = null;
        String email = null;
        jwtProvider.isValidToken(token);
        email = jwtProvider.getEmail(token);
        if(securityUserRepository.findByEmail(email).isPresent()){
            securityUser = securityUserRepository.findByEmail(email);
        }
        else{
            return Optional.empty();
        }
        return securityUser;
    }

    @Override
    public List<SecurityUser> getAll() {
        return null;
    }

    @Override
    public String extractPhoneFromToken(String token) {
        String phone = jwtProvider.getUsername(token);
        return securityUserRepository.findByPhoneNumber(phone).get().getPhoneNumber();
    }

    @Override
    public void isValidToken(String token) {
        jwtProvider.isValidToken(token);
    }


}
