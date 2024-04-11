package com.example.verification.emailverificationapi.controller;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.verification.emailverificationapi.ServiceEmailVerification;
import com.example.verification.emailverificationapi.model.ModelEmailUser;

@RestController
@RequestMapping("/api")
public class ControllerEmailAPI {

	@Autowired
    private ServiceEmailVerification emailVerificationService;

    @PostMapping("/verify-emails")
    public ResponseEntity<String> verifyEmails(@RequestBody ModelEmailUser request) throws NamingException {
    	
        if (emailVerificationService.verifyEmailFormat(request.getEmailAddresses())&&
        	emailVerificationService.verifyMXRecords(request.getEmailAddresses())&&
            emailVerificationService.verifySMTPConnection(request.getEmailAddresses())) {
        	
            return ResponseEntity.ok("Emails are valid.");  
        }
        
        else {
        	
        	return ResponseEntity.badRequest().body("There are some invalid emails.");
        }
    }
}
