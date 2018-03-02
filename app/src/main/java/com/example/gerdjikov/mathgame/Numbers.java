package com.example.gerdjikov.mathgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Gerdjikov on 27.2.2018 г..
 */

public class Numbers {

    private String difficulty;
    private int firstNumber, secondNumber, resultNumber, leftNumber, rightNumber;
    private int[] arr = new int[3];
    private String symbol, problem;

    public Numbers(String difficulty) {
        this.difficulty = difficulty;
    }

    public static int getRandInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
   /* public int getFirstNumber(){return firstNumber;}
    public int getSecondNumber(){return secondNumber;}*/

    public static void shuffleArray(int[] array) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }

        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
    }

    public String getProblem() {
        return problem;
    }

    public int[] getArr() {
        return arr;
    }

    public int getResultNumber() {
        return resultNumber;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void generateAddition() {
        symbol = "+";
        switch (difficulty) {
            case ("Easy"):
                firstNumber = getRandInt(10, 100);
                secondNumber = getRandInt(10, 100);
                resultNumber = firstNumber + secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Medium"):
                firstNumber = getRandInt(100, 350);
                secondNumber = getRandInt(100, 350);
                resultNumber = firstNumber + secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Hard"):
                firstNumber = getRandInt(350, 1000);
                secondNumber = getRandInt(350, 1000);
                resultNumber = firstNumber + secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Insane"):
                firstNumber = getRandInt(500, 1500);
                secondNumber = getRandInt(500, 1500);
                resultNumber = firstNumber + secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
        }
        problem = firstNumber + "" + symbol + "" + secondNumber;
    }

    public void generateSubtraction() {
        symbol = "-";
        switch (difficulty) {
            case ("Easy"):
                firstNumber = getRandInt(10, 100);
                secondNumber = getRandInt(10, 100);
                swapIfNeeded();
                resultNumber = firstNumber - secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Medium"):
                firstNumber = getRandInt(100, 350);
                secondNumber = getRandInt(100, 350);
                swapIfNeeded();
                resultNumber = firstNumber - secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Hard"):
                firstNumber = getRandInt(350, 1000);
                secondNumber = getRandInt(350, 1000);
                swapIfNeeded();
                resultNumber = firstNumber - secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Insane"):
                firstNumber = getRandInt(500, 1500);
                secondNumber = getRandInt(500, 1500);
                swapIfNeeded();
                resultNumber = firstNumber - secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
        }
        problem = firstNumber + "" + symbol + "" + secondNumber;
    }

    public void generateMultiplication() {
        symbol = "*";
        switch (difficulty) {
            case ("Easy"):
                firstNumber = getRandInt(5, 20);
                secondNumber = getRandInt(5, 20);
                resultNumber = firstNumber * secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Medium"):
                firstNumber = getRandInt(20, 50);
                secondNumber = getRandInt(20, 50);
                resultNumber = firstNumber * secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Hard"):
                firstNumber = getRandInt(50, 150);
                secondNumber = getRandInt(50, 150);
                resultNumber = firstNumber * secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Insane"):
                firstNumber = getRandInt(150, 500);
                secondNumber = getRandInt(150, 500);
                resultNumber = firstNumber * secondNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
        }
        problem = firstNumber + "" + symbol + "" + secondNumber;
    }

    public void generateRoot() {
        switch (difficulty) {
            case ("Easy"):
                firstNumber = getRandInt(2, 20);
                resultNumber = firstNumber;
                leftNumber = resultNumber - 1;
                rightNumber = resultNumber + 1;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Medium"):
                firstNumber = getRandInt(20, 50);
                resultNumber = firstNumber;
                leftNumber = resultNumber - 1;
                rightNumber = resultNumber + 1;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Hard"):
                firstNumber = getRandInt(50, 100);
                resultNumber = firstNumber;
                leftNumber = resultNumber - 1;
                rightNumber = resultNumber + 1;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
            case ("Insane"):
                firstNumber = getRandInt(50, 100);
                resultNumber = firstNumber;
                leftNumber = resultNumber - 10;
                rightNumber = resultNumber + 10;
                arr[0] = leftNumber;
                arr[1] = resultNumber;
                arr[2] = rightNumber;
                shuffleArray(arr);
                break;
        }
        problem = "√" + (resultNumber * resultNumber);
    }

    public void generateArcade() {
        int chooser = getRandInt(1, 4);
        switch (chooser) {
            case 1:
                generateAddition();
                break;
            case 2:
                generateSubtraction();
                break;
            case 3:
                generateMultiplication();
                break;
            case 4:
                generateRoot();
                break;
        }
    }

    private void swapIfNeeded() {
        if (firstNumber < secondNumber) {
            int temp = firstNumber;
            firstNumber = secondNumber;
            secondNumber = temp;
        }
    }

}
