package com.juhi.amazonapi.Controllers;

import com.juhi.amazonapi.CustomizedResponse;
import com.juhi.amazonapi.Models.UserModel;
import com.juhi.amazonapi.Services.UserService;
import com.juhi.amazonapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@CrossOrigin
@RestController
public class AuthController {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserService userDetailsService;

    @PostMapping(value = "/auth",consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity login(@RequestBody UserModel user)  {
        CustomizedResponse customizedResponse;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));

        }catch (BadCredentialsException e){
            customizedResponse = new CustomizedResponse("Invalid Email or Password!",null);
            return new ResponseEntity(customizedResponse,HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        customizedResponse = new CustomizedResponse(jwt,null);
        return new ResponseEntity(customizedResponse,HttpStatus.OK);
    }

}
