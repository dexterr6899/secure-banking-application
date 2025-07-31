package com.dexterbrains.db_bank.controller;

import com.dexterbrains.db_bank.dto.BankResponseDTO;
import com.dexterbrains.db_bank.dto.EnquiryRequest;
import com.dexterbrains.db_bank.dto.UserRequestDTO;
import com.dexterbrains.db_bank.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createAccount")
    public BankResponseDTO createAccount(@RequestBody UserRequestDTO userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/accountBalanceEnquiry")
    public BankResponseDTO getAccountBalance(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);
    }
}
