package com.bazigar.bulandawaaz.util;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Service
public class UsefulFunctions {

    public String OTPGenerator() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

}
