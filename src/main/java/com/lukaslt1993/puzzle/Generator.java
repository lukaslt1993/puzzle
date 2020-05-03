package com.lukaslt1993.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.lukaslt1993.Constants;

public class Generator {

    private Solver solver = new Solver();

    public int[][] generate(int n) throws IllegalArgumentException {
        
        verifyTable(n);
        
        int[][] result = new int[n][n];

        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < n * n; i++) {
            numbers.add(i);
        }

        do {
            Collections.shuffle(numbers);

            Iterator<Integer> it = numbers.iterator();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    result[i][j] = it.next();
                }
            }

        } while (!solver.isSolvable(result));

        return result;
    }

    public int[][] generateCorrect(int n) throws IllegalArgumentException {
        
        verifyTable(n);
        
        int[][] correctPuzzle = new int[n][n];
        int counter = 1;
        
        for (int y = 0; y < n; ++y) {
            for (int x = 0; x < n; ++x) {
                correctPuzzle[y][x] = counter;
                ++counter;
            }
        }
        
        correctPuzzle[n - 1][n - 1] = 0;
        return correctPuzzle;
    }
    
    private void verifyTable(int n) throws IllegalArgumentException {
        if (n < 2) {
            throw new IllegalArgumentException(Constants.ERROR_GENERATOR_ARGUMENT);
        }
    }

}
