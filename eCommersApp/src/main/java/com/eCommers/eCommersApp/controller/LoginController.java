package com.eCommers.eCommersApp.controller;

import com.eCommers.eCommersApp.dto.UserSignInDetail;
import com.eCommers.eCommersApp.exception.UserException;
import com.eCommers.eCommersApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ecom")
@RequiredArgsConstructor
public class LoginController {

    private UserService userService;

    @GetMapping("/signIn")
    public ResponseEntity<UserSignInDetail> getLoggedInCustomerDetailsHandler(Authentication auth) {
        try {var customer = userService.getUserByEmailId(auth.getName());
            UserSignInDetail signinSuceesData = new UserSignInDetail();
            signinSuceesData.setId(customer.getUserId());
            signinSuceesData.setFirstNAme(customer.getFirstName());
            signinSuceesData.setLastName(customer.getLastName());
            signinSuceesData.setSigninStatus("Success");

            return new ResponseEntity<>(signinSuceesData, HttpStatus.OK);}
        catch(UserException ex ){
            throw new UserException(" Invalid Password");
        }

    }
}
