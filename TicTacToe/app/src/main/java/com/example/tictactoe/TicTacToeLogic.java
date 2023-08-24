package com.example.tictactoe;

import java.util.Arrays;
import java.util.Random;

public class TicTacToeLogic {
    // 0 = nothing, 1 = X, 2 = O
    private int[][] gameBoard = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    public boolean computer = false;

    public int[][] getGameBoard (){
        return gameBoard;
    }

    public boolean update(int col, int row, int player){
        if(win() != 0){
            return false;
        }
        if (gameBoard[row][col] != 0) {
            return false;
        } else {
            if (computer){
                gameBoard[row][col] = 1;
                makeMove();
            } else {
                gameBoard[row][col] = player;
            }
            return true;
        }
    }

    private void makeMove() {
        int[][] ram = new int[3][];
        for (int i = 0; i < 3; i++) {
            ram[i] = Arrays.copyOf(gameBoard[i], gameBoard[i].length);
        }
        // no need to make a move if it is a tie or the human won already
        if (win(gameBoard) != 0){
            return;
        }

        // first check if making any move will win for the AI
        for(int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (ram[r][c] == 0) {
                    ram[r][c] = 2;
                    if (win(ram) == 2){
                        gameBoard[r][c] = 2;
                        return;
                    } else {
                        ram[r][c] = 0;
                    }
                }
            }
        }

        // then check if the AI needs to block
        for(int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (ram[r][c] == 0) {
                    ram[r][c] = 1;
                    if (win(ram) == 1){
                        gameBoard[r][c] = 2;
                        return;
                    } else {
                        ram[r][c] = 0;
                    }
                }
            }
        }

        // then just make a move
        if (gameBoard[1][1] == 0) {
            gameBoard[1][1] = 2;
            return;
        }
        Random rand = new Random();
        int ct = rand.nextInt(10);
        while(true) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (ram[r][c] == 0) {
                        if (ct == 0) {
                            gameBoard[r][c] = 2;
                            return;
                        } else {
                            ct--;
                        }
                    }
                }
            }
        }
    }

    public int win(){
        // 0 indicates the game is still ongoing and no one has won
        // 1 indicates player 1 has won
        // 2 indicates player 2 has won
        // 3 indicates no more moves are possible and it's a tie
        return win(gameBoard);
    }

    private int win(int[][] input){
        // 0 indicates the game is still ongoing and no one has won
        // 1 indicates player 1 has won
        // 2 indicates player 2 has won
        // 3 indicates no more moves are possible and it's a tie
        for (int i = 0; i < 3; i++){
            if (input[0][i] != 0 && input[0][i] == input[1][i] && input[0][i] == input[2][i]) {
                return input[0][i];
            }
            if (input[i][0] != 0 && input[i][0] == input[i][1] && input[i][0] == input[i][2]) {
                return input[i][0];
            }
        }
        if(input[0][0] != 0 && input[0][0] == input[1][1] && input[0][0] == input[2][2]) {
            return input[0][0];
        }
        if(input[2][0] != 0 && input[2][0] == input[1][1] && input[2][0] == input[0][2]) {
            return input[1][1];
        }
        for(int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (input[r][c] == 0) {
                    return 0;
                }
            }
        }
        return 3;
    }

    public void reset (){
        for(int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }
    }
}
