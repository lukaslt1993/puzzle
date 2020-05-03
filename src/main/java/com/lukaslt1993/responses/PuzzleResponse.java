package com.lukaslt1993.responses;

/**
 * This isn't currently used, because for the current task returning String is more convenient.
 * @author Lukas
 *
 */
public class PuzzleResponse {
    
    private String puzzleTable;

    public PuzzleResponse(String puzzleTable) {
        this.puzzleTable = puzzleTable;
    }

    public String getPuzzleTable() {
        return puzzleTable;
    }
    
}
