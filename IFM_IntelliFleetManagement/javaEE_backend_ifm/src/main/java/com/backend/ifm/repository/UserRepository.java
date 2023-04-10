package com.backend.ifm.repository;

import com.backend.ifm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /*
For the purpose of retrieving a user associated with an email,
we will create a DAO (Data Access Object) class using Spring Data by extending the JpaRepository interface
 */
    User findByEmail(String email);
}
