package com.dexterbrains.db_bank.utils;

import java.time.Year;

public class AccountUtils {

    public static String generateAccountNumber(){

        /**
         * concat -> 2025 + randomDigits
         */
        Year currentYear = Year.now();
        int minNumber = 100000;
        int maxNumber = 999999;

//    Let's generate a random number between min and max
        int randomNum = (int)Math.floor(Math.random() * (maxNumber - minNumber + 1) + minNumber);

        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(currentYear).append(randomNum).toString();
    }
}
