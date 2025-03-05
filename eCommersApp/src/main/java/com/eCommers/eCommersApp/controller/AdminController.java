package com.eCommers.eCommersApp.controller;

import com.eCommers.eCommersApp.dto.AdminDTO;
import com.eCommers.eCommersApp.dto.UserDTO;
import com.eCommers.eCommersApp.model.User;
import com.eCommers.eCommersApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecom/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody AdminDTO user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User addUser = userService.addUserAdmin(user);
        return ResponseEntity.ok(addUser);
    }

    @PutMapping("/updatepassword/{adminId}")
    public ResponseEntity<User> updateUserPassword(
            @PathVariable("adminId") Integer customerId,
            @RequestBody UserDTO userdto
            ){
        User updatedUser = userService.changePassword(customerId,userdto);
        return ResponseEntity.ok(updatedUser);
    }
}
