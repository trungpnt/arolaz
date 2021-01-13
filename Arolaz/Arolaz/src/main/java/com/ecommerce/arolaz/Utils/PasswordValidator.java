package com.ecommerce.arolaz.Utils;

import org.springframework.context.annotation.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class PasswordValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    public PasswordValidator(){
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public Boolean validate(final String password){
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}


