package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDeleteResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AdminController {

    @RequestMapping(method = RequestMethod.DELETE , path = "/admin/user/{userId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <UserDeleteResponse> deleteUser(@PathVariable("userid") final String userid,
                                                          @RequestHeader("authorization") final String authorization) {


    }
}
