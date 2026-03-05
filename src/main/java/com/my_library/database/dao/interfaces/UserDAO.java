package com.my_library.database.dao.interfaces;

import com.my_library.model.User;
import com.my_library.model.UserRole;

import java.util.List;

public interface UserDAO extends GenericDAO<User, Long> {

    List<User> findByLastName(String lastName);

    List<User> findByLastNameAndFirstName(String lastName, String firstName);

    List<User> findByRole(UserRole role);

    User findByIin(String iin);

    User findByPhone(String phone);
}
