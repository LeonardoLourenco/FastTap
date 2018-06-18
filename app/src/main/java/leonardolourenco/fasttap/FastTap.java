package leonardolourenco.fasttap;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class FastTap {
    public enum BoardPiece{    //This is majorly related to the Arcade Mode
        BOMB,                   //Bomb - on touch -1 Life                         35% chance of appearing
        ENEMY,                  //Enemy - on touch 10 Points                      50% chance of appearing   Implementing an hard mode will the percentages changed
        GENEMY,                 //Gold Enemy - on touch 25 Points + 1 Gold Star   15% chance of appearing
        EMPTY                   //Nothing
    }

    private BoardPiece [][] Board = new BoardPiece[4][4];   //Area of Play will have a 4x4 Layout
    private boolean gameOver = false;
    private boolean hardlevel = false;
    private int points = 0;                                 //Used in Arcade Mode
    private long currentSecs = 0;                           //Used in Reaction Time
    private long currentMilli = 0;                           //Used in Reaction Time
    private Random random = new Random();
    private int RandomSec;
    private int lives = 3;
    private int gStar = 0;                                  //Currency for Skins      //skin order Invisible,Enemy,Gold Enemy, Bomb, Silhouette
    private int [] skin0ids = {R.drawable.testenemy4,R.drawable.testenemy1,R.drawable.testenemy2,R.drawable.testenemy3,R.drawable.testenemy5};

    private int [] heartsid = {R.drawable.hearts1,R.drawable.hearts2,R.drawable.hearts3,R.drawable.hearts4};
    private long [][] enemyTimeMilli = new long[4][4];
    private long [][] enemyTimeSecs = new long[4][4];
    private int spawnInterval = 1000;           //0pt -> 1000ms | 100pt -> 500ms | 250pt -> 250ms  //time it takes for things to spawn
    private int lifeTime = 50;                  //0pt -> 6secs | 100pt -> 3secs | 250pt -> 1,5secs //time it takes for things to despawn
                                                //Using one more decimal to avoid using double which by themselves cannot be added to longs.
                                                //Im using these two variables to make arcade mode more interesting.
    private Timer firstTimer = new Timer();
    private Timer counterTimer = new Timer();



    public void newGame(){
        firstTimer = new Timer();
        counterTimer = new Timer();
        lives = 3;
        points = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                cleanSpot(r, c);
            }
        }

    }


    public void playReactionTime(){                    //After pressing Reaction Time on the Main Menu this method is called
        RandomSec = random.nextInt(6-1)+1; //Random secs, between 1 and 5 , that the enemy will take to appear on the screen.

        firstTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                spawnReaction();
            }
        }, RandomSec * 1000); //delay - Time the timer takes to begin doing the task

        counterTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cronometer();
                if(gameOver){
                    stop();
                }
            }
        },RandomSec*1000,1);

    }

    public void playArcade(){
        RandomSec = random.nextInt(4-1)+1; //Random secs, between 1 and 3 , that the enemy will take to appear on the screen.

        firstTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                spawnArcade();
            }
        }, RandomSec * 1000, spawnInterval); //delay - Time the timer takes to begin doing the task

        counterTimer.scheduleAtFixedRate(new TimerTask() { //do this verification every spawnInterval, if the enemy is on the board for lifeTime secs, clean spot and user lose a life.
            @Override
            public void run() {
                cronometerArcade();
                checkEnemytime();
                if(gameOver){
                    stop();
                }
                if(points >= 100){
                        spawnInterval = 500;
                        lifeTime = 30;
                        stop();
                        reScheduleArcade();
                }
            }
        },RandomSec * 1000, spawnInterval);

    }

    public void reScheduleArcade(){
        firstTimer = new Timer();
        counterTimer = new Timer();
        firstTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                spawnArcade();
            }
        }, 0, spawnInterval);

        counterTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cronometerArcade();
                checkEnemytime();
                if(gameOver){
                    stop();
                }
                if(points >= 350 && !hardlevel){
                        hardlevel=true;
                        spawnInterval = 250;
                        lifeTime = 15;
                        stop();
                        reScheduleArcade();
                }
            }
        },0, spawnInterval);
    }

    public void hitArcade(int row, int col) {         //this function is called when the users clicks a spot

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

        if(lives == 0){
            gameOver= true;
        }
    }

    public void hitReaction(int row, int col) {         //this function is called when the users clicks a spot
        switch (Board[row][col]) {
            case ENEMY:               // give 10 points and clean that spot
                gameOver = true;
                cleanSpot(row, col);
                break;

            case GENEMY:              // give 25 points and clean that spot
                gameOver = true;
                gStar ++;
                cleanSpot(row, col);
                break;

            case EMPTY:               // Penalization for hitting at random
                currentSecs++;
                break;
        }

    }

    private void spawnArcade() {                               // Things should spawn every 2 secs, if an enemy is not defeated in 3 secs the users loses 1 life
        int rrow = random.nextInt(4);
        int rcol = random.nextInt(4);
        if(Board[rrow][rcol] == BoardPiece.EMPTY) {
            int RandomChance = random.nextInt(101 - 1) + 1;      //chance of something appearing       |0|__15%__|15|__35%__|50|__50%__|100|

            enemyTimeSecs[rrow][rcol]= currentSecs+lifeTime/10;
            enemyTimeMilli[rrow][rcol]= currentMilli+lifeTime%10*100;

            if (RandomChance <= 15) {                                   //15% chance
                Board[rrow][rcol] = BoardPiece.GENEMY;
            } else if (RandomChance <= 50) {                            //35% chance
                Board[rrow][rcol] = BoardPiece.BOMB;
                enemyTimeSecs[rrow][rcol]= currentSecs+6;
                enemyTimeMilli[rrow][rcol]= currentMilli;
            } else if (RandomChance <= 100) {                           //50% chance
                Board[rrow][rcol] = BoardPiece.ENEMY;
            }
        }
    }

    private void spawnReaction() {                              //In reaction time the only things tha can appear are Enemies (60%) and Gold Enemies (40%)
        int rrow = random.nextInt(4);
        int rcol = random.nextInt(4);
            int RandomChance = random.nextInt(101 - 1) + 1;

            if (RandomChance <= 70) {                                   //70% chance
                Board[rrow][rcol] = BoardPiece.ENEMY;
            } else if (RandomChance <= 100) {                           //30% chance
                Board[rrow][rcol] = BoardPiece.GENEMY;
            }

    }

    private void cronometer(){  // 00 : 000  secs : milli
        currentMilli++;
        if(currentMilli == 1000){
            currentMilli=0;
            currentSecs++;
        }
    }

    private void cronometerArcade(){  // 00 : 000  secs : milli
        currentMilli+= spawnInterval;
        if(currentMilli == 1000){
            currentMilli=0;
            currentSecs++;
        }
    }

    private void checkEnemytime(){ //check every sec
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                switch (Board[row][col]){
                    case BOMB:
                        if(currentSecs == enemyTimeSecs[row][col]&& currentMilli == enemyTimeMilli[row][col]) {
                            cleanSpot(row, col);
                            break;
                        }
                    case ENEMY:
                        if(currentSecs == enemyTimeSecs[row][col]&& currentMilli == enemyTimeMilli[row][col]) {
                            cleanSpot(row, col);
                            loseLife();
                            break;
                        }
                    case GENEMY:
                        if(currentSecs == enemyTimeSecs[row][col]&& currentMilli == enemyTimeMilli[row][col]) {
                            cleanSpot(row, col);
                            loseLife();
                            break;
                        }
                        if(lives == 0){
                            gameOver= true;
                        }
                }
            }
        }
    }

    private void loseLife() {
        lives--;
    }

    private void cleanSpot(int row, int col) {  //cleans(Makes empty) the spot on the board
        Board[row][col] = BoardPiece.EMPTY;
    }

    public void stop(){     //cleans the timers schedules
        firstTimer.cancel();
        firstTimer.purge();
        counterTimer.cancel();
        counterTimer.purge();
    }

    public BoardPiece[][] getBoard() {
        return Board.clone();
    }

    public int getPoints() {
        return points;
    }

    public long getCurrentSecs() {
        return currentSecs;
    }

    public long getCurrentMilli() {
        return currentMilli;
    }

    public int getRandomSec(){
        return RandomSec;
    }

    public int getGStar() {
        return gStar;
    }

    public void setGStar(int gStar) {
        this.gStar = gStar;
    }

    public boolean getgameOver(){
        return gameOver;
    }

    public int[] getSelectedSkinIds(int selectedSkin) {
        switch (selectedSkin) {
            case 0:
                return skin0ids;
            case 1:
                //return ;
            case 2:

            case 3:

            case 4:

            case 5:

                //Other skins go here
        }

        return skin0ids;  //error situation
    }

    public int getHearts(){
        switch(lives){
            case 3:
                return heartsid[0];
            case 2:
                return heartsid[1];
            case 1:
                return heartsid[2];
            case 0:
                return heartsid[3];
        }
        return heartsid[0];
    }

}
