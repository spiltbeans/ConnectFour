import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Eyas Valdez
 * @version 01/11/17
 */

public class ConnectFour{
  public static boolean legalMove(int y){
    if(y < 0){
      return(false);
    }else{
      return(true);
    }
  }//legalMove
  public static void main(String[] args){
    boolean turn = false;
    boolean win = false;
    int randomTurn;
    int column = 0;
    int row = 0;
    int turns = 0;
    int pOneScore = 0;
    int pTwoScore = 0;
    String playerOneName;
    String playerTwoName;
    String winner;
    String colour;
    int [][]moves = new int [6][7];
    
    Board board = new Board(6, 7);
    
    //Deciding the which player will begin the game
    randomTurn = (int)((2 - 1 + 1) * Math.random() + 1);
    
    if(randomTurn == 1){
      turn = false;
      colour = "Red";
    }else{
      turn = true;
      colour = "Yellow";
    }

    //Gathering players name
    playerOneName = (String)JOptionPane.showInputDialog("Enter Player One's First Name:");
    playerTwoName = (String)JOptionPane.showInputDialog("Enter Player Two's First Name:");
    
   while(pOneScore < 3 && pTwoScore < 3){
    win = false;
    while(win == false && turns <= 41){
      
      //Displaying turns
      if(playerOneName.charAt(playerOneName.length() - 1) == 's' && turn == false){
        board.displayMessage("It is " + playerOneName + "' Turn  " + playerOneName + ": "+ pOneScore + "  " + playerTwoName + ": " + pTwoScore);
      }else if(turn == false){
        board.displayMessage("It is " + playerOneName + "'s Turn  " + playerOneName + ": "+ pOneScore + "  " + playerTwoName + ": " + pTwoScore);
      }
      
      if(playerTwoName.charAt(playerTwoName.length() - 1) == 's' && turn == true){
        board.displayMessage("It is " + playerTwoName + "' Turn  " + playerOneName + ": "+ pOneScore + "  " + playerTwoName + ": " + pTwoScore);
      }else if(turn == true){
        board.displayMessage("It is " + playerTwoName + "'s Turn  " + playerOneName + ": "+ pOneScore + "  " + playerTwoName + ": " + pTwoScore);
      }
      
      //Checking if move is legal
      //Placing pegs
      do{
        Coordinate c = board.getClick();
        column = c.getCol();
        row = c.getRow();    
        
        int r = 0;
        for(int i = 0; i < 6; i ++){
          if(moves[i][column] == 0){
            
            r++;
          }
        }
        row = r - 1;
        if(legalMove(row) == true){
          if(turn == false){
            moves[row][column] = 1;
          }else{
            moves[row][column] = 2;
          }
          
          board.putPeg(colour, row, column);
        }else{
          
          board.displayMessage("Move is out of the board, please try again");
        }
        
      }while(legalMove(row) == false);
      
      //Checking if move has won the game horizontally
      int winningHor = 0;
      for(int i = 0; i < 6; i ++){
        for(int j = 0; j < 4; j ++){
          if(moves[i][j] == moves[i][j + 1] && moves[i][j] == moves[i][j + 2] && moves[i][j] == moves[i][j + 3] && moves[i][j] != 0){
            winningHor ++;
          }
        }
      }
      if(winningHor > 0){
        win = true;
        board.drawLine(row, column, row, column + 3);
      }
      
      //Checking if move has won the game vertically
      int winningVert = 0;
      for(int s = 0; s < 7; s ++){
        for(int r = 0; r < 3; r ++){
          if(moves[r][s] == moves[r + 1][s] && moves[r][s] == moves[r + 2][s] && moves[r][s] == moves[r + 3][s] && moves[r][s] != 0){
            winningVert ++;
          }
        }
      }
      if(winningVert > 0){
        win = true;
        board.drawLine(row, column, row + 3, column);
      }
      
      //Checking if move has won the game diaginally(rightSlope)
      int winningDagR = 0;
      for(int f = 0; f < 4; f ++){
        for(int d = 0; d < 3; d ++){
          if(moves[d][f] == moves[d + 1][f + 1] && moves[d][f] == moves[d + 2][f + 2] && moves[d][f] == moves[d + 3][f + 3] && moves[d][f] != 0){
            winningDagR ++;
          }
        }
      }
      if(winningDagR > 0){
        win = true;
        board.drawLine(row, column, row + 3, column + 3);
      }
      
      //Checking if move has won the game diagnally(leftSlope)
      int winningDagL = 0;
      for(int t = 3; t < 7; t++){
        for(int g = 0; g < 3; g ++){
          if(moves[g][t] == moves[g + 1][t - 1] && moves[g][t] == moves[g + 2][t - 2] && moves[g][t] == moves[g + 3][t - 3] && moves[g][t] != 0){
            winningDagL ++;
          }
        }
      }
      if(winningDagL > 0){
        win = true;
        board.drawLine(row, column, row + 3, column - 3);
      }
        
      if(win == false){
        //Switching turns
        if(turn == false){
          turn = true;
          colour = "Yellow";
        }else{
          turn = false;
          colour = "Red";
        }

        turns ++;
      }
    }
    
    //Winner is declared with line drawn
    if(turn == false && win == true){
      pOneScore ++;
      board.displayMessage(playerOneName + " has won the game!   " + playerOneName + ": "+ pOneScore + "  " + playerTwoName + ": " + pTwoScore);
      winner = (String)JOptionPane.showInputDialog("Player One Has Won The Match");
    }else if(win == true){
      pOneScore ++;
      board.displayMessage(playerTwoName + " has won the game!   " + playerOneName + ": "+ pOneScore + "  " + playerTwoName + ": " + pTwoScore);
      winner = (String)JOptionPane.showInputDialog("Player Two Has Won The Match");
    }
    //If board is full with no win
    if(win == false){
      board.displayMessage("Draw. No player has won");
      winner = (String)JOptionPane.showInputDialog("The Game Is A Tie");
    }
    
    //Cleaning the board
    for(int k = 0; k < 7; k ++){
      for(int n = 0; n < 6; n++){
        board.removePeg(n, k);
        board.removeLine(row, column, n, k);
        moves[n][k] = 0;
      }
    } 
  }
    //Displaying final winner
    if(pOneScore == 3){
      winner = (String)JOptionPane.showInputDialog("Congradulations, " + playerOneName + " Has Won The Game!");
    }else{
      winner = (String)JOptionPane.showInputDialog("Congradulations, " + playerTwoName + " Has Won The Game!");
    }
    
  }//main
  
}//ConnectFour