package com.example.ThreadPoolExecutor.controller;

import com.example.ThreadPoolExecutor.entity.User;
import com.example.ThreadPoolExecutor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            userService.saveUser(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers() {
        return userService.findAllUsers().thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "/getusers", produces = "application/json")
    public ResponseEntity getUsers() {
        CompletableFuture<List<User>> user = userService.findAllUsers();
        CompletableFuture<List<User>> user1 = userService.findAllUsers();
        CompletableFuture<List<User>> user2 = userService.findAllUsers();
        CompletableFuture.allOf(user, user1, user2).join();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
