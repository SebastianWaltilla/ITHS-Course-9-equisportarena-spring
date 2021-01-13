package com.contestmodule.contest.controller;

import com.contestmodule.contest.emailhandler.VerificationService;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.entity.VerificationToken;
import com.contestmodule.contest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.Locale;

@RestController
public class RegistrationController {

    @Autowired
    private VerificationService service;

    @Autowired
    private UserService userService;

    @GetMapping("/registrationconfirm")
    public String confirmRegistration
            (WebRequest request, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = service.getVerificationToken(token);
        if (verificationToken == null) {
            return "Bad token.";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "Verification token has expired.";
        }

        user.setEmailVerified(true);
        userService.saveWithoutEncryptingPassword(user, false);
        return "Verification successful.";
    }
}
