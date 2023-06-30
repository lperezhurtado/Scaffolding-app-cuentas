package com.scaffolding.appcuentas.Helper;

import java.math.BigInteger;
import java.util.Random;

public class IbanHelper {

    private final static String CODE_BANK = "ES74";
    private final static String ENTITY_BANK = "4040";
    private final static BigInteger MODULE_97 = new BigInteger("97");   
    private final static Long MOD_97 = 97L;
    //String regexIban = "[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}";

    
    public static String getNextValidIbanNumber(String lastIban) {

        lastIban = getReformattedIban(lastIban);
        Long ibanPartToChange = Long.parseLong(lastIban.substring(4, 20));
        String newNumberIban = "";
        String aux = "";
		boolean isValid = false;
        int count = 0;
        
        while (!isValid) {
            System.out.println("vuelta: "+count);
            aux = String.valueOf(++ibanPartToChange);
            aux = addZerosIfNecessary(aux);
            
            newNumberIban = ENTITY_BANK + aux + lastIban.substring(20);
            isValid = validateIBAN(newNumberIban);
            System.out.println("NEW IBAN: "+ newNumberIban);
            System.out.println("isValid:" + isValid);
            count++;
        }

        return getIbanNumberFormatted(aux);
    }

    private static String addZerosIfNecessary(String iban) {
        while (iban.length() < 16) {
                iban = 0 + iban;
            }
        return iban;
    }

    public static String getIbanNumberFormatted(String number) {
        return CODE_BANK +" "+ ENTITY_BANK +" "+ addSpaceEach4Char(number);
    }


    private static String addSpaceEach4Char(String numbers) {
        String aux = "";
        int count = 1;
        for (int i = 0; i < numbers.length(); i++) {
            aux += count % 4 == 0 ? numbers.charAt(i) + " " : numbers.charAt(i);
            count++;
        }
        return aux.trim();
    }

    public static boolean validateIBAN (String iban) {
        //String reformattedIBAN = getReformattedIban(iban);
        BigInteger ibanBigInteger = new BigInteger(iban);
        return ibanBigInteger.mod(MODULE_97).equals(BigInteger.ONE); // es lo mismo que ibanBigInteger % 97 == 1;
    }

    //REVISAR
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

        String charToNumber =  convertCharCodeToNumber(iban.substring(0, 4)); 
        return iban.substring(4) + charToNumber;
    }

    public static String convertCharCodeToNumber (String code) {
        String number = "";
        
        for (int i = 0; i < code.length(); i++) {
            number += Character.getNumericValue(code.charAt(i));
        }
        return number;
    }

    public static String getValidIbanNumber() {
        String numberIban = "";
		boolean isValid = false;
        
		while (!isValid) {	
			numberIban = IbanHelper.getIbanNumber();
			isValid = IbanHelper.validateIBAN2(numberIban);	
		}
        return numberIban;
    }

    public static String getIbanNumber() {
        return CODE_BANK +" "+ ENTITY_BANK +" "+ addSpaceEach4Char(getIbanNumber());
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
        return "";
    }
}
