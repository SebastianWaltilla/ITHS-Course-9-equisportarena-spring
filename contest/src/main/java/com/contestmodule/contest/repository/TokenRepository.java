package com.contestmodule.contest.repository;

import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
