package com.lukaslt1993.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukaslt1993.Constants;
import com.lukaslt1993.puzzle.Generator;
import com.lukaslt1993.puzzle.Puzzle;

@Controller
public class PuzzleController {

    private final Map<String, Puzzle> players = new HashMap<>();
    private final int tableLength = 4;

    @GetMapping(value = Constants.GET_PUZZLE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> respond(
            @RequestParam(name = Constants.GET_PUZZLE_PARAM_PLAYER, required = true) String player,
            @RequestParam(name = Constants.GET_PUZZLE_PARAM_MOVE, required = false) String move) {
        
        if (player == null) {
            return getError(Constants.ERROR_GET_PUZZLE_PARAM);
        }
        
        Puzzle puzzle = players.get(player);
        
        if (puzzle == null) {
            Generator gen = new Generator();
            int[][] table = gen.generate(tableLength);
            int[][] finishedTable = gen.generateCorrect(tableLength);
            puzzle = new Puzzle(table, finishedTable);
            players.put(player, puzzle);
        }
        
        if (move != null) {
            return getResponse(player, puzzle, move);
        }
        
        return ResponseEntity.ok(puzzle.toString());
    }
    
    private ResponseEntity<String> getError(String error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    private ResponseEntity<String> getResponse(String player, Puzzle puzzle, String move) {
        Puzzle initialPuzzle = new Puzzle(puzzle);
        String[] moves = move.split("");
        
        for (String s : moves) {
            
            Puzzle.DIRECTION dir = getMoveDirection(s);
            
            if (dir == null || !puzzle.canMove(dir)) {
                players.put(player, initialPuzzle);
                return getError(Constants.ERROR_GET_PUZZLE_PARAM);
            }
            
            puzzle.move(dir);
        }
        
        return ResponseEntity.ok(puzzle.toString());
    }
    
    private Puzzle.DIRECTION getMoveDirection(String letter) {
        Puzzle.DIRECTION dir = null;
        
        switch (letter) {
            case "u" :
                dir = Puzzle.DIRECTION.UP; // move empty tile up
                break;
            case "r" :
                dir = Puzzle.DIRECTION.RIGHT; // move empty tile right
                break;
            case "d" :
                dir = Puzzle.DIRECTION.DOWN; // move empty tile down
                break;
            case "l" :
                dir = Puzzle.DIRECTION.LEFT; // move empty tile left
        }
        
        return dir;
    }

}
