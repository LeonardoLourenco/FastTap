package leonardolourenco.fasttap;

import java.util.Random;

public class FastTap {
    private enum BoardPiece{     //This is majorly related to the Arcade Mode
        BOMB,                   //Bomb - on touch -1 Life
        ENEMY,                  //Enemy - on touch 10 Points
        GENEMY,                 //Gold Enemy - on touch 25 Points + 1 Gold Star
        EMPTY                   //Nothing
    }

    private BoardPiece [][] Board = new BoardPiece[4][4];   //Area of Play will have a 4x4 Layout
    private int points = 0;                                 //Used in Arcade Mode
    private long currentTime = 0;                           //Used in Reaction Time
    private Random random = new Random();                   //Used in Reaction Mode



    public void newGame(){

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                    Board[r][c] = BoardPiece.EMPTY;
            }
        }

    }


    public void playReactionTime(){
        int RandomSec = random.nextInt(6-1)+1; //Random secs, between 1 and 5 , that the enemy will take to appear on the screen.

    }

}
