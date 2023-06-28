package com.scaffolding.appcuentas.Helper;

import java.math.BigInteger;
import java.util.Random;

public class IbanHelper {

    private final static String CODE_BANK = "ES74";
    private final static String ENTITY_BANK = "4040";
    private final static BigInteger MODULE_97 = new BigInteger("97");   
    private final static Long MOD_97 = 97L;
    //String regexIban = "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}";

    public static String getValidIbanNumber() {
        String numberIban = "";
		boolean isValid = false;
		int count = 0;
        
		while (!isValid) {
			System.out.println("\nvuelta: "+count);
			numberIban = IbanHelper.getIbanNumber();
			isValid = IbanHelper.validateIBAN2(numberIban);
			System.out.println("IBAN Number: "+numberIban);
			System.out.println("isValid: "+isValid);
			count++;
		}
        return numberIban;
    }

    public static String getIbanNumber() {
        return CODE_BANK +" "+ ENTITY_BANK +" "+ getRandomStringNumber();
    }

    public static String getRandomStringNumber() {
        Random random = new Random();
        String numbers = "";
        int numbersToGenerate = 16;
        int count = 1;

        for (int i = 0; i < numbersToGenerate; i++) {
            numbers += random.nextInt(10);
            numbers = addSpaceEach4Char(count, numbers);
            count++;
        }
        return numbers.trim();
    }

    private static String addSpaceEach4Char(int count, String numbers) {
        return count % 4 == 0 ? numbers+" " : numbers;
    }


    public static boolean validateIBAN (String iban) {
        String reformattedIBAN = getReformattedIban(iban);
        BigInteger ibanBigInteger = new BigInteger(reformattedIBAN);

        return ibanBigInteger.mod(MODULE_97).equals(BigInteger.ONE); // es lo mismo que ibanBigInteger % 97 == 1;
    }

    public static boolean validateIBAN2(String iban) {
        Long number; 
        Long mod = 0L;  
        String nineDigits = "";
        String reformattedIBAN = getReformattedIban(iban);

        for (int i = 0; i < reformattedIBAN.length(); i++) {
            if (nineDigits.length() < 10) {
                nineDigits += reformattedIBAN.charAt(i);    
            }
            
            if (nineDigits.length() == 9 || ((reformattedIBAN.length()-1) -i) < 9 ) {
                number = Long.parseLong(nineDigits);
                mod = number % MOD_97;
                nineDigits = mod.toString();
            }
        }
        return mod == 1;
    }

    public static String getReformattedIban(String iban) {
        iban = iban.trim().replace("IBAN", "").replaceAll(" ", "").toUpperCase();
        ValidationHelper.validateStringLength(iban, 15, 34);

        String charCode =  convertCharCodeToNumber(iban.substring(0, 4)); 
        iban = charCode + iban.substring(4);
        return iban.substring(charCode.length()) + charCode;
    }


    public static String quitIbanPrefix (String iban) {
        return iban.startsWith("IBAN")? iban.substring(4).replaceAll(" ", "") : iban;
    }

    public static String convertCharCodeToNumber (String code) {
        String number = "";
        
        for (int i = 0; i < code.length(); i++) {
            number += Character.getNumericValue(code.charAt(i));
        }
        return number;
    }
}
