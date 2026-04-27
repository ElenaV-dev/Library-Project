package com.my_library.service.interfaces;

import com.my_library.exception.ServiceException;
import com.my_library.model.User;

import java.util.Optional;

public interface UserService extends GenericService<User, Long> {

    Optional<User> login(String email, String password) throws ServiceException;

    User register(String lastName, String firstName, String iin, String email, String phone,
                  String password) throws ServiceException;

}
