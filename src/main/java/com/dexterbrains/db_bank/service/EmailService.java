package com.dexterbrains.db_bank.service;

import com.dexterbrains.db_bank.dto.EmailDetailsDTO;

public interface EmailService {

    void sendEmailAlert(EmailDetailsDTO emailDetails);
}
