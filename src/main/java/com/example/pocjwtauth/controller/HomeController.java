package com.example.pocjwtauth.controller;

import com.example.pocjwtauth.dto.UserDTO;
import com.example.pocjwtauth.security.LoggedInUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    @ApiOperation(value = "say hello")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the user list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the user list"),
            @ApiResponse(code = 403, message = "Accessing the user list you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user list you were trying to reach is not found")
    })
    @GetMapping
    public String getWelcome(
            @ApiIgnore @LoggedInUser UserDTO applicationUser) {
        return "welcome: " + applicationUser.getEmail();
    }

}
