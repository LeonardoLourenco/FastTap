package leonardolourenco.fasttap;

import java.util.Random;

public class FastTap {
    private enum BoardPiece{    //This is majorly related to the Arcade Mode
        BOMB,                   //Bomb - on touch -1 Life                         35% chance of appearing
        ENEMY,                  //Enemy - on touch 10 Points                      50% chance of appearing   Implementing an hard mode will the percentages changed
        GENEMY,                 //Gold Enemy - on touch 25 Points + 1 Gold Star   15% chance of appearing
        EMPTY                   //Nothing
    }

    private BoardPiece [][] Board = new BoardPiece[4][4];   //Area of Play will have a 4x4 Layout
    private int points = 0;                                 //Used in Arcade Mode
    private long currentTime = 0;                           //Used in Reaction Time
    private Random random = new Random();                   //Used in Reaction Mode
    private int lives = 3;



    public void newGame(){
        lives = 3;
        points = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                cleanSpot(r, c);
            }
        }

    }


    public void playReactionTime(){
        int RandomSec = random.nextInt(6-1)+1; //Random secs, between 1 and 5 , that the enemy will take
                                                      // to appear on the screen.

    }

    public void hit(int row, int col) {
        switch (Board[row][col]) {
            case BOMB:                // lose life and clean that spot, verify if player got a gameover
                loseLife();
                cleanSpot(row, col);
                break;

            case ENEMY:               // give 10 points and clean that spot
                points += 10;
                cleanSpot(row, col);
                break;

            case GENEMY:              // give 25 points and clean that spot
                points += 25;
                cleanSpot(row, col);
                break;

            case EMPTY:               // Maybe add a penalization for hitting nothing?
                break;
        }
    }

    private void loseLife() {
        lives--;
    }

    private void cleanSpot(int row, int col) {  //cleans(Makes empty) the spot on the board
        Board[row][col] = BoardPiece.EMPTY;
    }

}
