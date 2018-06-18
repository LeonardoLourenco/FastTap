package leonardolourenco.fasttap;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class playAreaActivity extends AppCompatActivity {

    private FastTap game = new FastTap();
    private Users user = new Users();
    private HighScores highScores = new HighScores();
    private ImageButton[][] buttons = new ImageButton[4][4];
    private int[] currentSkin = new int[5];
    private Timer timerUpdateDisplay = new Timer();             //Timer used to update the display each 0,04 secs -> 40 milisecs
    private TextView textViewScore;
    private TextView textViewGStarCountPlay;
    private ImageView imageViewLife;
    private int gameMode = 0;
    private String[] sorted = new String[6];
    private int[] sortedint = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_area);

        //Open DB
        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(getApplicationContext());
        //Read from the database
        SQLiteDatabase db = dbFastTapOpenHelper.getWritableDatabase();

        final DbTableUsers tableUsers = new DbTableUsers(db);
        final DbTableHighScores tableHighScores = new DbTableHighScores(db);

        textViewScore = (TextView) findViewById(R.id.textViewScore);
        imageViewLife = (ImageView) findViewById(R.id.imageViewLife);
        textViewGStarCountPlay = (TextView) findViewById(R.id.textViewGStarCountPlay);

        buttons[0][0] = (ImageButton) findViewById(R.id.imageButton00);
        buttons[0][1] = (ImageButton) findViewById(R.id.imageButton01);
        buttons[0][2] = (ImageButton) findViewById(R.id.imageButton02);
        buttons[0][3] = (ImageButton) findViewById(R.id.imageButton03);

        buttons[1][0] = (ImageButton) findViewById(R.id.imageButton10);
        buttons[1][1] = (ImageButton) findViewById(R.id.imageButton11);
        buttons[1][2] = (ImageButton) findViewById(R.id.imageButton12);
        buttons[1][3] = (ImageButton) findViewById(R.id.imageButton13);

        buttons[2][0] = (ImageButton) findViewById(R.id.imageButton20);
        buttons[2][1] = (ImageButton) findViewById(R.id.imageButton21);
        buttons[2][2] = (ImageButton) findViewById(R.id.imageButton22);
        buttons[2][3] = (ImageButton) findViewById(R.id.imageButton23);

        buttons[3][0] = (ImageButton) findViewById(R.id.imageButton30);
        buttons[3][1] = (ImageButton) findViewById(R.id.imageButton31);
        buttons[3][2] = (ImageButton) findViewById(R.id.imageButton32);
        buttons[3][3] = (ImageButton) findViewById(R.id.imageButton33);

        gameMode = getIntent().getIntExtra("gameMode",0);
        Cursor cursorUser = tableUsers.query(tableUsers.ALL_COLUMNS,null,null,null,null,null);
        cursorUser.moveToFirst();
        user = tableUsers.getCurrentUserFromCursor(cursorUser);
        //get row where user._id equal user_ID and type equals gamemode

        Cursor cursorHighScore = tableHighScores.query(null,DbTableHighScores.FIELD_ID_USER + "=? AND " + DbTableHighScores.FIELD_TYPE + "=?",
                new String[] { Long.toString(user.getId()) , Integer.toString(gameMode) },null,null,null);
        if(cursorHighScore.getCount() > 0){    //Search if the rows have been created, if yes get the row where type = gamemode and user id equals user id
            cursorHighScore.moveToFirst();
            highScores = tableHighScores.getCurrentHighScoresFromCursor(cursorHighScore);
        }else {
            //else create the row for game mode Arcade and Reaction | type 1 and 2
            if(gameMode ==1){
                highScores.setType(1);
                highScores.setHigh1("99:999");
                highScores.setHigh2("99:999");
                highScores.setHigh3("99:999");
                highScores.setHigh4("99:999");
                highScores.setHigh5("99:999");
                highScores.setIdUser(user.getId());
                tableHighScores.insert(DbTableHighScores.getContentValues(highScores));
            }else if(gameMode == 2) {
                highScores.setType(2);
                highScores.setHigh1("0");
                highScores.setHigh2("0");
                highScores.setHigh3("0");
                highScores.setHigh4("0");
                highScores.setHigh5("0");
                highScores.setIdUser(user.getId());
                tableHighScores.insert(DbTableHighScores.getContentValues(highScores));
            }

            cursorHighScore = tableHighScores.query(null,DbTableHighScores.FIELD_ID_USER + "=? AND " + DbTableHighScores.FIELD_TYPE + "=?",
                    new String[] { Long.toString(user.getId()) , Integer.toString(gameMode) },null,null,null);
            cursorHighScore.moveToFirst();
            highScores = tableHighScores.getCurrentHighScoresFromCursor(cursorHighScore);
        }

        placeScoresOnSorted();

        currentSkin = game.getSelectedSkinIds(user.getSelectedSkin());
        game.setGStar(user.getGStar());

        game.newGame();
        if (gameMode == 1) {
            game.playReactionTime();
        }else if(gameMode == 2){
            game.playArcade();
        }

        updateDisplay();
        displayUpdater();
        db.close();
        cursorHighScore.close();
        cursorUser.close();
    }

    public void hit(View view){
        if(game.getgameOver()){
            Toast.makeText(this, R.string.gameOver, Toast.LENGTH_SHORT).show();
            return;
        }
        ImageButton b = (ImageButton) view;

        String tag = b.getTag().toString();
        int pos = Integer.parseInt(tag);

        int row = pos / 10;
        int col = pos % 10;

        if (gameMode == 1) {
            game.hitReaction(row, col);
        }else if(gameMode == 2){
            game.hitArcade(row, col);
        }

    }

    private void displayUpdater(){


        timerUpdateDisplay.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {          //Had to Implement this, the updateDisplay() needs to be run in the UI Thread
                    @Override                           //       otherwise it will crash because he does not have access to the views
                    public void run() {
                        updateDisplay();
                    }
                });
            }
        }, game.getRandomSec(), 40);  //game.getRandomSec() is the delay used here because we dont want the timer to do
                                             // unwanted work.

    }


    private void updateDisplay() {
        FastTap.BoardPiece[][] board = game.getBoard();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                switch (board[row][col]) {
                    case EMPTY:
                        buttons[row][col].setImageResource(currentSkin[0]);
                        break;
                    case ENEMY:
                        buttons[row][col].setImageResource(currentSkin[1]);
                        break;
                    case GENEMY:
                        buttons[row][col].setImageResource(currentSkin[2]);
                        break;
                    case BOMB:
                        buttons[row][col].setImageResource(currentSkin[3]);
                        break;
                }
            }
        }

        if (gameMode == 1) {
            textViewScore.setText(game.getCurrentSecs() + ":" + game.getCurrentMilli());
            imageViewLife.setImageResource(currentSkin[0]);         //we want it to be invisible/non existent here
        }else if(gameMode == 2){
            textViewScore.setText(game.getPoints()+"");
            imageViewLife.setImageResource(game.getHearts());
        }
        textViewGStarCountPlay.setText(game.getGStar()+"");

        final Intent intent = new Intent(this,MainActivity.class);


        //AlertDialog where the only option will be to go back to the main menu.
        if(game.getgameOver()){
            timerUpdateDisplay.cancel();
            timerUpdateDisplay.purge();
            sorted[5] = ""+textViewScore.getText(); //place the new score in sorted sow e can sort the scores.
            bubbleSort();
            takeScoresOffSorted();
            updateHighScoreAndUser();
            AlertDialog alertDialog = new AlertDialog.Builder(playAreaActivity.this).create();
            alertDialog.setTitle(getString(R.string.nice));
            alertDialog.setMessage(getString(R.string.your_score_is) + textViewScore.getText());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    private void updateHighScoreAndUser(){

        //Open DB
        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(getApplicationContext());
        //Read from the database
        SQLiteDatabase db = dbFastTapOpenHelper.getWritableDatabase();

        final DbTableUsers tableUsers = new DbTableUsers(db);
        final DbTableHighScores tableHighScores = new DbTableHighScores(db);

        user.setGStar(game.getGStar());
        tableHighScores.update(DbTableHighScores.getContentValues(highScores),DbTableHighScores.FIELD_ID_USER + "=? AND " + DbTableHighScores.FIELD_TYPE + "=?",
                new String[] { Long.toString(user.getId()) , Integer.toString(highScores.getType()) });

        tableUsers.update(DbTableUsers.getContentValues(user),DbTableUsers._ID + "=?",
                new String[] { Long.toString(user.getId()) });
        db.close();
    }

    private void placeScoresOnSorted() {
            sorted[0] = highScores.getHigh1();
            sorted[1] = highScores.getHigh2();
            sorted[2] = highScores.getHigh3();
            sorted[3] = highScores.getHigh4();
            sorted[4] = highScores.getHigh5();
    }

    private void takeScoresOffSorted(){
            highScores.setHigh1(sorted[0]);
            highScores.setHigh2(sorted[1]);
            highScores.setHigh3(sorted[2]);
            highScores.setHigh4(sorted[3]);
            highScores.setHigh5(sorted[4]);
    }

    private void bubbleSort(){
        int n = sorted.length;
        String temp;
        int tempi;
        if(gameMode == 1){ //first highscore is the lowest number
            for(int i=0; i < n; i++){
                for(int j=1; j < (n-i); j++){
                    if(sorted[j-1].compareTo(sorted[j]) > 0){
                        temp = sorted[j-1];
                        sorted[j-1] = sorted[j];
                        sorted[j] = temp;
                    }
                }
            }
        }else if(gameMode ==2){ //first highscore is the highest number
            for (int i = 0; i < n; i++) {
                sortedint[i] = Integer.parseInt(sorted[i]);
            }

            for(int i=0; i < n; i++){
                for(int j=1; j < (n-i); j++){
                    if(sortedint[j-1] < sortedint[j]){
                        tempi = sortedint[j-1];
                        sortedint[j-1] = sortedint[j];
                        sortedint[j] = tempi;
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                sorted[i] = ""+sortedint[i];
            }

        }

    }

}
