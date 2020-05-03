package com.lukaslt1993;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.lukaslt1993.puzzle.Generator;
import com.lukaslt1993.puzzle.Puzzle;
import com.lukaslt1993.puzzle.Puzzle.DIRECTION;
import com.lukaslt1993.puzzle.Solver;

@SpringBootTest
public class PuzzleTest {
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    private MockMvc mvc;
    
    @BeforeEach     
    private void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void playerUnspecified() throws Exception {
       String uri = Constants.GET_PUZZLE;
       MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
          .accept(MediaType.TEXT_PLAIN_VALUE)).andReturn();
       int status = mvcResult.getResponse().getStatus();
       assertEquals(400, status);
    }
    
    @Test
    public void playerSpecified() throws Exception {
       String uri = Constants.GET_PUZZLE + "?player=testPlayer";
       MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
          .accept(MediaType.TEXT_PLAIN_VALUE)).andReturn();
       int status = mvcResult.getResponse().getStatus();
       assertEquals(200, status);
       assertNotEquals(mvcResult.getResponse().getContentAsString(), Constants.ERROR_GET_PUZZLE_PARAM);
    }
    
    @Test
    public void incorrectMove() throws Exception {
       String uri = Constants.GET_PUZZLE + "?player=testPlayer&move=uuuuu";
       MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
          .accept(MediaType.TEXT_PLAIN_VALUE)).andReturn();
       int status = mvcResult.getResponse().getStatus();
       assertEquals(400, status);
       assertEquals(mvcResult.getResponse().getContentAsString(), Constants.ERROR_GET_PUZZLE_PARAM);
    }
    
    @Test
    public void correctMove() throws Exception {
       String uri = Constants.GET_PUZZLE + "?player=testPlayer";
       MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
          .accept(MediaType.TEXT_PLAIN_VALUE)).andReturn();
       int status = mvcResult.getResponse().getStatus();
       String response = mvcResult.getResponse().getContentAsString();
       assertEquals(200, status);
       assertNotEquals(response, Constants.ERROR_GET_PUZZLE_PARAM);
       int[][] table = getTable(response, 4, 4);
       Puzzle puzzle = new Puzzle(table, table);
       
       if (puzzle.canMove(DIRECTION.LEFT)) {
           uri += "&move=l";
           puzzle.move(DIRECTION.LEFT);
       } else {
           uri += "&move=r";
           puzzle.move(DIRECTION.RIGHT);
       }
       
       mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
               .accept(MediaType.TEXT_PLAIN_VALUE)).andReturn();
       assertEquals(mvcResult.getResponse().getContentAsString(), puzzle.toString());
    }
    
    @Test
    public void solution() {
       int[][] table = getEasy4x4Table();
       Puzzle puzzle = new Puzzle(table, new Generator().generateCorrect(4));
       DIRECTION[] strategy = {DIRECTION.RIGHT, DIRECTION.DOWN, DIRECTION.UP, DIRECTION.LEFT};
       Puzzle solvedPuzzle = new Solver().solve(puzzle, strategy);
       assertTrue(solvedPuzzle.isSolved());
    }
    
    private int[][] getTable(String response, int rows, int cols) {
        List<String> numbers = new ArrayList<>(Arrays.asList(response.split("[^0-9]")));
        numbers.removeIf(string -> string.equals(""));
        Iterator<String> it = numbers.iterator();
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = Integer.parseInt(it.next());
            }
        }
        return result;
    }
    
    private int[][] getEasy4x4Table() {
        int[][] result = new int[4][4];
        result[0][0] = 6;
        result[0][1] = 1;
        result[0][2] = 3;
        result[0][3] = 4;
        result[1][0] = 5;
        result[1][1] = 2;
        result[1][2] = 11;
        result[1][3] = 7;
        result[2][0] = 0;
        result[2][1] = 9;
        result[2][2] = 10;
        result[2][3] = 8;
        result[3][0] = 13;
        result[3][1] = 14;
        result[3][2] = 15;
        result[3][3] = 12;
        return result;
    }

}
