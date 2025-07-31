package com.dexterbrains.db_bank.service;

import com.dexterbrains.db_bank.dto.BankResponseDTO;
import com.dexterbrains.db_bank.dto.EnquiryRequest;
import com.dexterbrains.db_bank.dto.UserRequestDTO;

public interface UserService {

    BankResponseDTO createAccount(UserRequestDTO userRequest);

    BankResponseDTO balanceEnquiry(EnquiryRequest enquiryRequest);

//    BankResponseDTO

    BankResponseDTO nameEnquiry(EnquiryRequest enquiryRequest);
}
