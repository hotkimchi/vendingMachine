/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author apprentice
 */
public class UserIOImpl implements UserIO {

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {

        String answer = stringScanner(prompt);
        String checkAnswer = isValidDecimal(answer);
        double doubleAnswer = Double.parseDouble(checkAnswer);
        return doubleAnswer;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        String answer = stringScanner(prompt);
        String checkAnswer = isValidDecimal(answer);
        double doubleAnswer = Double.parseDouble(checkAnswer);
        if (doubleAnswer < min || doubleAnswer > max) {
            print("Invalid Input!!!");
            return readDouble(prompt, min, max);
        }
        return doubleAnswer;
    }

    @Override
    public float readFloat(String prompt) {
        String answer = stringScanner(prompt);
        String checkAnswer = isValidDecimal(answer);
        float floatAnswer = Float.parseFloat(checkAnswer);
        return floatAnswer;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        String answer = stringScanner(prompt);
        String checkAnswer = isValidDecimal(answer);
        float floatAnswer = Float.parseFloat(checkAnswer);
        if (floatAnswer < min || floatAnswer > max) {
            print("Invalid Input!!!");
            return readFloat(prompt, min, max);
        }
        return floatAnswer;
    }

    @Override
    public int readInt(String prompt) {
        String answer = stringScanner(prompt);
        String checkAnswer = isValidInt(answer);
        if (checkAnswer.equals("-1")) {
            print("Invalid Input!!!");
            return readInt(prompt);
        }
        int intAnswer = Integer.parseInt(checkAnswer);
        return intAnswer;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        String answer = stringScanner(prompt);
        String checkAnswer = isValidInt(answer);
        int intAnswer = Integer.parseInt(checkAnswer);
        if (intAnswer < min || intAnswer > max) {
            print("Invalid Input!!!");
            return readInt(prompt, min, max);
        }
        return intAnswer;
    }

    @Override
    public long readLong(String prompt) {
        String answer = stringScanner(prompt);
        long longAnswer = Long.parseLong(answer);
        return longAnswer;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        String answer = stringScanner(prompt);
        long longAnswer = Long.parseLong(answer);
        if (longAnswer < min || longAnswer > max) {
            readLong(prompt, min, max);
        }
        return longAnswer;
    }

    @Override
    public String readString(String prompt) {
        String answer = stringScanner(prompt);
        return answer;
    }

    @Override
    public String stringScanner(String prompt) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println(prompt + " ");
        String answer = myScanner.nextLine();
        return answer;
    }

    public String isValidInt(String input) {
        if (input.equals("")) {
            return "-1";
        }
        for (int i = 0; i < input.length(); i++) {
            Character x = input.charAt(i);
            if (!Character.isDigit(x)) {
                return "-1";
            }
        }
        return input;
    }

    public String isValidDecimal(String input) {
        if (input.equals("")) {
            return "-1.0";
        }
        if (!input.contains(".")) {
            return "-1.0";
        }
        for (int i = 0; i < input.length(); i++) {
            Character x = input.charAt(i);
            if (!Character.isDigit(x) ^ x.equals('.')) {
                return "-1.0";
            }
        }
        return input;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, int scale) {
        String answer = stringScanner(prompt);
        String checkAnswer = isValidDecimal(answer);
        if (checkAnswer.equals("-1.0")) {
            print("Invalid Input!");
            return readBigDecimal(prompt, scale);
        }
        BigDecimal myBD = new BigDecimal(checkAnswer);
        return myBD.setScale(scale, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, int scale, String min, String max) {
        String answer = stringScanner(prompt);
        String checkAnswer = isValidDecimal(answer);
        if (checkAnswer.compareTo(min) < 0 || checkAnswer.compareTo(max) > 0) {
            print("Invalid Input!!!");
            return readBigDecimal(prompt, scale, min, max);
        }
        BigDecimal myBD = new BigDecimal(checkAnswer);
        return myBD.setScale(scale, RoundingMode.HALF_UP);
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        String answer = stringScanner(prompt);
        String checkAnswer = isValidDate(answer);
        if (checkAnswer.equals("00000000")) {
            print("Invalid Input!!!");
            return readLocalDate(prompt);
        }
        LocalDate getDate = LocalDate.parse(checkAnswer, DateTimeFormatter
                .ofPattern("MMdduuuu"));
        return getDate;
    }

    public String isValidDate(String answer) {
        if (answer.contains("-") || answer.equals("")) {
            return "00000000";
        }
        if (answer.length() != 8) {
            return "00000000";
        }

        for (int i = 0; i < answer.length(); i++) {
            Character x = answer.charAt(i);
            if (!Character.isDigit(x)) {
                return "00000000";
            }
        }

        return answer;
    }

}
