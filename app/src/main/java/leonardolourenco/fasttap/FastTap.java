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
    private int gStar = 0;                                  //Currency for Skins



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

    public void hit(int row, int col) {         //this function is called when the users clicks a spot
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
                gStar ++;
                cleanSpot(row, col);
                break;

            case EMPTY:               // Maybe add a penalization for hitting nothing?
                break;
        }
    }

    private void spawn(int row, int col) {
        if(Board[row][col] != BoardPiece.EMPTY) {
            int RandomChance = random.nextInt(101 - 1) + 1;      //chance of something appearing       |0|__15%__|15|__35%__|50|__50%__|100|

            if (RandomChance <= 15) {                                   //15% chance
                Board[row][col] = BoardPiece.GENEMY;
            } else if (RandomChance <= 50) {                            //35% chance
                Board[row][col] = BoardPiece.BOMB;
            } else if (RandomChance <= 100) {                           //50% chance
                Board[row][col] = BoardPiece.ENEMY;
            }
        }
    }

    private void loseLife() {
        lives--;
    }

    private void cleanSpot(int row, int col) {  //cleans(Makes empty) the spot on the board
        Board[row][col] = BoardPiece.EMPTY;
    }

}
