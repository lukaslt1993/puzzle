package com.lukaslt1993.puzzle;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solver {
    private final Queue<Puzzle> frontiers = new LinkedList<>();

    public Puzzle solve(Puzzle puzzleToSolve, Puzzle.DIRECTION[] strategy) {
        frontiers.add(puzzleToSolve);
        while (!frontiers.isEmpty()) {
            Puzzle puzzle = frontiers.poll();
            if (puzzle.isSolved()) {
                return puzzle;
            }
            for (int i = 0; i < strategy.length; i++) {
                if (puzzle.canMove(strategy[i])) {
                    Puzzle newPuzzle = new Puzzle(puzzle);
                    newPuzzle.move(strategy[i]);
                    frontiers.add(newPuzzle);
                }
            }
        }
        return null;
    }
    
    public boolean isSolvable(int[][] puzzle) {
        int[] arr = Stream.of(puzzle).flatMapToInt(IntStream::of).toArray();
        return isSolvable(arr);
    }

    private boolean isSolvable(int[] puzzle) {
        int parity = 0;
        int gridWidth = (int) Math.sqrt(puzzle.length);
        int row = 0; 
        int blankRow = 0; 

        for (int i = 0; i < puzzle.length; i++) {
            if (i % gridWidth == 0) { 
                row++;
            }
            if (puzzle[i] == 0) { 
                blankRow = row; 
                continue;
            }
            for (int j = i + 1; j < puzzle.length; j++) {
                if (puzzle[i] > puzzle[j] && puzzle[j] != 0) {
                    parity++;
                }
            }
        }

        if (gridWidth % 2 == 0) { 
            if (blankRow % 2 == 0) { 
                return parity % 2 == 0;
            } else { 
                return parity % 2 != 0;
            }
        } else {
            return parity % 2 == 0;
        }
    }
    
}
