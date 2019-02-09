package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.CommonBussinessService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CommonController {

    @Autowired
    private CommonBussinessService commonBussinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("userId") final String userUuid ,
                                                 @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, UserNotFoundException {

        String [] bearerToken = authorization.split("Bearer ");

        UserEntity userEntity=commonBussinessService.getUser(userUuid,bearerToken[1]);

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).emailAddress(userEntity.getEmail())
                .contactNumber(userEntity.getContactNumber()).userName(userEntity.getUserName())
                .aboutMe(userEntity.getAboutMe()).country(userEntity.getCountry()).dob(userEntity.getDob());

        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);


    }
}
