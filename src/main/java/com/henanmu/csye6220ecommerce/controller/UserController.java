package com.henanmu.csye6220ecommerce.controller;

import com.henanmu.csye6220ecommerce.dao.UserDAO;
import com.henanmu.csye6220ecommerce.dto.in.UpdatePasswordRequest;
import com.henanmu.csye6220ecommerce.pojo.User;
import com.henanmu.csye6220ecommerce.validator.UpdatePasswordRequestValidator;
import com.henanmu.csye6220ecommerce.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    UserValidator userValidator;

    @Autowired
    UpdatePasswordRequestValidator updatePasswordRequestValidator;

    @PostMapping
    public ResponseEntity createUser (@RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            FieldError nameError = bindingResult.getFieldError("name");
            if (nameError != null) {
                String errorMessage = nameError.getDefaultMessage();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }
            FieldError passwordError = bindingResult.getFieldError("password");
            if (passwordError != null) {
                String errorMessage = passwordError.getDefaultMessage();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            }
        }
        if (userDAO.readUserByName(user.getName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        try {
            if (user.getRole() == null) {
                user.setRole(0); // customer sign up
            }
            userDAO.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sign up failed");
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity readUserById (@PathVariable("id") Integer id) {
        User user = userDAO.readUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> readUserByName(@PathVariable("name") String name) {
        User user = userDAO.readUserByName(name);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User foundUser = userDAO.readUserByName(user.getName());
        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username");
        }
        if (!user.getPassword().equals(foundUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password");
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest, BindingResult bindingResult) {
        User foundUser = userDAO.readUserByName(updatePasswordRequest.getName());
        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username");
        }
        if (!foundUser.getPassword().equals(updatePasswordRequest.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid old password");
        }
        updatePasswordRequestValidator.validate(updatePasswordRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError("newPassword").getDefaultMessage());
        }
        foundUser.setPassword(updatePasswordRequest.getNewPassword());
        userDAO.updateUser(foundUser);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }
}
