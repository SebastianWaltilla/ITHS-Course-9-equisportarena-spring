package com.contestmodule.contest.repository;

import com.contestmodule.contest.dto.UserInfoForAdminDto;
import com.contestmodule.contest.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    List<User> findAll();

    @Query("SELECT new com.contestmodule.contest.dto.UserInfoForAdminDto(" +
            "u.id, u.firstname, u.lastname, u.address, u.email, u.registeredDate, u.lastLoggedIn, u.isEmailVerified) " +
            "FROM User u")
    List<UserInfoForAdminDto> findAllUsersAsInfoForAdminDto();

}

