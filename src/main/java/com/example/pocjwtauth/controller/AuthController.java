package com.example.pocjwtauth.controller;

import com.example.pocjwtauth.dto.UserDTO;
import com.example.pocjwtauth.security.JwtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @ApiOperation(value = "Login for external users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in"),
            @ApiResponse(code = 401, message = "You are not authorized to log in the application"),
            @ApiResponse(code = 403, message = "loggin in is forbidden with this email"),
            @ApiResponse(code = 404, message = "The mail you were trying to log in with is not found")
    })
    @PostMapping("/externalLogin")
    public String authenticateUser(@RequestBody UserDTO authenticatedUser) {
        return jwtService.generateToken(authenticatedUser);
    }
}
