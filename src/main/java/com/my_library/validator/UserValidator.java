package com.my_library.validator;

import com.my_library.exception.ValidationException;
import com.my_library.model.User;
import com.my_library.model.UserRole;

public class UserValidator {

    private static final int MAX_STRING_LENGTH = 255;
    private static final int IIN_LENGTH = 12;
    private static final int MAX_PHONE_LENGTH = 25;
    private static final String IIN_REGEX = "\\d+";
    private static final String PHONE_REGEX = "[0-9+\\-() ]+";

    public static void validate(User user) throws ValidationException {

        if (user == null) {
            throw new ValidationException("User is null");
        }

        String lastName = user.getLastName();
        String firstName = user.getFirstName();
        UserRole role = user.getRole();
        String iin = user.getIin();
        String address = user.getAddress();
        String phone = user.getPhone();

        if (lastName == null || lastName.isBlank()) {
            throw new ValidationException("Last name is null");
        }

        if (lastName.length() > MAX_STRING_LENGTH) {
            throw new ValidationException("Last name is too long");
        }

        if (firstName == null || firstName.isBlank()) {
            throw new ValidationException("First name is null");
        }

        if (firstName.length() > MAX_STRING_LENGTH) {
            throw new ValidationException("First name is too long");
        }

        if (role == null) {
            throw new ValidationException("Role is null");
        }

        if (iin == null || iin.isBlank()) {
            throw new ValidationException("Iin is null");
        }

        if (iin.length() != IIN_LENGTH || !iin.matches(IIN_REGEX)) {
            throw new ValidationException("Iin is invalid");
        }

        if (address != null && address.length() > MAX_STRING_LENGTH) {
            throw new ValidationException("Address is invalid");
        }

        if (phone != null && (phone.length() > MAX_PHONE_LENGTH
                || !phone.matches(PHONE_REGEX))) {
            throw new ValidationException("Phone is invalid");
        }
    }
}
