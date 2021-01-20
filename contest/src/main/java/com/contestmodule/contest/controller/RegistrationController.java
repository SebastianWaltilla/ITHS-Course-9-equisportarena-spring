package com.contestmodule.contest.controller;

import com.contestmodule.contest.emailhandler.VerificationService;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.entity.VerificationToken;
import com.contestmodule.contest.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Calendar;
import java.util.Locale;

@RestController
public class RegistrationController {


    private final VerificationService service;


    private final UserService userService;

    public RegistrationController(VerificationService service, UserService userService){
        this.service = service;
        this.userService = userService;
    }


    @ApiOperation(value = "Confirmation of registered email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Verification successful."),
            @ApiResponse(code = 400, message = "Bad Token"),
            @ApiResponse(code = 400, message = "Verification token has expired.")
    })
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
