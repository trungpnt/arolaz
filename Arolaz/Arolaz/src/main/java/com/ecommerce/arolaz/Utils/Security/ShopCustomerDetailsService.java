package com.ecommerce.arolaz.Utils.Security;

import com.ecommerce.arolaz.SecurityUser.Model.SecurityUser;
import com.ecommerce.arolaz.SecurityUser.Repository.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;


@Component
public class ShopCustomerDetailsService implements UserDetailsService {
    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Autowired
    JwtProvider jwtProvider;

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
    public UserDetails loadUserByUsername(String requiredEntry) throws UsernameNotFoundException {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        Optional<SecurityUser> user;

        if (isNumeric(requiredEntry)) {
            user = securityUserRepository.findByPhoneNumber(requiredEntry);

            return withUsername(user.get().getPhoneNumber())
                    .password(user.get().getPassword())
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();

        } else {
            user = securityUserRepository.findByEmail(requiredEntry);
            return withUsername(user.get().getEmail())
                    .password(user.get().getPassword())
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }

    }

    /**
     * Extract username and roles from a validated jwt string.
     *
     * @param jwtToken jwt string
     * @return UserDetails if valid, Empty otherwise
     */
    public Optional<UserDetails> loadUserByJwtToken(String jwtToken) {

        jwtProvider.isValidToken(jwtToken);

        return Optional.of(
                withUsername(jwtProvider.getUsername(jwtToken))
                        .authorities(jwtProvider.getRoles(jwtToken))
                        .password("") //token does not have password but field may not be empty
                        .accountExpired(false)
                        .accountLocked(false)
                        .credentialsExpired(false)
                        .disabled(false)
                        .build());

    }

}
