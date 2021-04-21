package com.juhi.amazonapi.Controllers;

import com.juhi.amazonapi.CustomizedResponse;
import com.juhi.amazonapi.Models.UserModel;
import com.juhi.amazonapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService service;

    // public return_type name(){
    // }

    //To get list of all users
    @GetMapping("/user")
    public ResponseEntity getusers(){
        return new ResponseEntity(service.getusers(), HttpStatus.OK);
    }


    //To Create New User
    @PostMapping(value = "/user/createuser",consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity adduser(@RequestBody UserModel user)  {
        CustomizedResponse customizedResponse;
        try {
            service.adduser(user);
            customizedResponse = new CustomizedResponse("User Created!",null );
            return new ResponseEntity(customizedResponse,HttpStatus.OK);
        }catch (Exception e){
            customizedResponse = new CustomizedResponse(e.getMessage(),null);
            return new ResponseEntity(customizedResponse,HttpStatus.BAD_REQUEST);
        }

    }


    //To get user by userid
    @GetMapping("/user/getuser/{id}")
    public ResponseEntity getuser(@PathVariable("id") String id){
        CustomizedResponse customizedResponse;
        try{
            customizedResponse = new CustomizedResponse("User Found", Collections.singletonList(service.getuser(id)));
            return  new ResponseEntity(customizedResponse,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }


}
