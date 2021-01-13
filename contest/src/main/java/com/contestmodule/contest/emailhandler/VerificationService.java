package com.contestmodule.contest.emailhandler;

import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.entity.VerificationToken;
import com.contestmodule.contest.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    @Autowired
    TokenRepository tokenRepository;

    public VerificationService() {
    }

    void createVerificationTokenForUser(User user, String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
}
