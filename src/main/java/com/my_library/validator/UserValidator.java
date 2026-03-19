package com.my_library.validator;

import com.my_library.model.User;
import com.my_library.model.UserRole;

public class UserValidator {

    private static final int MAX_STRING_LENGTH = 255;
    private static final int IIN_LENGTH = 12;
    private static final int MAX_PHONE_LENGTH = 25;
    private static final String IIN_REGEX = "\\d+";
    private static final String PHONE_REGEX = "[0-9+\\-() ]+";

    public static boolean isValid(User user) {

        if (user == null) {
            return false;
        }

        String lastName = user.getLastName();
        String firstName = user.getFirstName();
        UserRole role = user.getRole();
        String iin = user.getIin();
        String address = user.getAddress();
        String phone = user.getPhone();

        if (lastName == null || lastName.isBlank() || lastName.trim().length() > MAX_STRING_LENGTH) {
            return false;
        }

        if (firstName == null || firstName.isBlank() || firstName.trim().length() > MAX_STRING_LENGTH) {
            return false;
        }

        if (role == null) {
            return false;
        }

        if (iin == null || iin.isBlank() || iin.trim().length() != IIN_LENGTH
                || !iin.matches(IIN_REGEX)) {
            return false;
        }

        if (address != null && (address.trim().length() > MAX_STRING_LENGTH
                || address.isBlank())) {
            return false;
        }

        if (phone != null && (phone.trim().length() > MAX_PHONE_LENGTH
        || phone.isBlank() || !phone.matches(PHONE_REGEX))) {
            return false;
        }
            return true;
    }
}
