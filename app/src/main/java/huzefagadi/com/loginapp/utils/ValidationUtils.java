package huzefagadi.com.loginapp.utils;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import huzefagadi.com.loginapp.R;

/**
 * Created by huzefaasger on 02-11-2016.
 */
public class ValidationUtils {

    private String validString = "^[a-zA-Z0-9_]*$";
    Pattern stringPattern;

    public ValidationUtils() {
        stringPattern = Pattern.compile(validString);

    }

    public int isStringValid(String username) {
        try {
            if (username != null) {
                Matcher stringMatcher = stringPattern.matcher(username);
                if (stringMatcher.matches()) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                return 3;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 3;
        }

    }

}
