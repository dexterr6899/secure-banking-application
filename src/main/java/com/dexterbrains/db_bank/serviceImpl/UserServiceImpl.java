package com.dexterbrains.db_bank.serviceImpl;

import com.dexterbrains.db_bank.constants.AccountConstants;
import com.dexterbrains.db_bank.dto.*;
import com.dexterbrains.db_bank.entity.User;
import com.dexterbrains.db_bank.repository.UserRepository;
import com.dexterbrains.db_bank.service.EmailService;
import com.dexterbrains.db_bank.service.UserService;
import com.dexterbrains.db_bank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Override
    public BankResponseDTO createAccount(UserRequestDTO userRequest) {
        /**
         * Check if user already has an account
         * Creating an account - saving a new user in DB
         * Creating a unique account number for each user
         */
        if (userRepository.existsByEmail(userRequest.getEmail())){
                BankResponseDTO response = BankResponseDTO.builder()
                        .responseCode(AccountConstants.ACCOUNT_EXISTS_CODE)
                        .responseMessage(AccountConstants.ACCOUNT_EXISTS_MESSAGE)
                        .build();
                return response;
        }

        User newUser = User.builder()
                .fisrtName(userRequest.getFisrtName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .otherName(userRequest.getOtherName())
//                .dateOfBirth(userRequest.getDateOfBirth())
                .gender(userRequest.getGender())
                .phoneNumber(userRequest.getPhoneNumber())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        User savedUser = userRepository.save(newUser);

        EmailDetailsDTO emailDetails = EmailDetailsDTO.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATED SUCCESSFULLY")
                .messageBody("Congratulation your account has been setup successfully. \nYour Account Details:\n"+
                "Account Number: "+savedUser.getAccountNumber() +"\n")
                .build();
        emailService.sendEmailAlert(emailDetails);

        return BankResponseDTO.builder()
                .responseCode(AccountConstants.ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(AccountConstants.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(savedUser.getAccountNumber())
                        .accountBalance(savedUser.getAccountBalance())
                        .accountName(savedUser.getFisrtName()+" "+ savedUser.getLastName()+ " " + savedUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public BankResponseDTO balanceEnquiry(EnquiryRequest enquiryRequest) {
        /**
         * Check if the account number exists
         *
         */
        boolean isAccountPresent = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountPresent){
            return BankResponseDTO.builder()
                    .responseCode(AccountConstants.ACCOUNT_DOES_NOT_EXISTS_CODE)
                    .responseMessage(AccountConstants.ACCOUNT_DOES_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userDetails = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        BankResponseDTO bankResponse = BankResponseDTO.builder()
                .responseCode(AccountConstants.ACCOUNT_FOUND_MESSAGE)
                .responseMessage(AccountConstants.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(userDetails.getAccountBalance())
                        .accountNumber(userDetails.getAccountNumber())
                        .accountName(userDetails.getFisrtName()+" "+userDetails.getOtherName()+" "+userDetails.getLastName())
                        .build())
                .build();

        return null;
    }

    @Override
    public BankResponseDTO nameEnquiry(EnquiryRequest enquiryRequest) {
        return null;
    }

}
