package leonardolourenco.fasttap;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class playAreaActivity extends AppCompatActivity {

    private FastTap game = new FastTap();
    private ImageButton[][] buttons = new ImageButton[4][4];
    private int[] currentSkin = game.getSelectedSkin();
    private Timer timerUpdateDisplay = new Timer();             //Timer used to update the display each 0,04 secs -> 40 milisecs
    private TextView textViewScore;
    private ImageView imageViewLife;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_area);

        textViewScore = (TextView) findViewById(R.id.textViewScore);
        imageViewLife = (ImageView) findViewById(R.id.imageViewLife);

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

        game.newGame();
        game.playReactionTime();
        updateDisplay();
        displayUpdater();

    }


    public void hit(View view){
        if(game.getgameOver()){
            Toast.makeText(this, "This game has ended", Toast.LENGTH_SHORT).show();
            return;
        }
        ImageButton b = (ImageButton) view;

        String tag = b.getTag().toString();
        int pos = Integer.parseInt(tag);

        int row = pos / 10;
        int col = pos % 10;

        //Put extra value will help solve this
        //Put condition here for each mode
        game.hitReaction(row,col);

        // game.hitArcade(row,col); For Arcade

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

        // update points / timer here aswell

        textViewScore.setText(game.getCurrentSecs() + ":" + game.getCurrentMilli());
        /* For Arcade
        textViewScore.setText("game.getPoints");
        */
        if(game.getgameOver()){
            timerUpdateDisplay.cancel();
            timerUpdateDisplay.purge();
            Toast.makeText(this, "Nice. Your score is " + textViewScore.getText(), Toast.LENGTH_LONG).show(); //Maybe change this to Snackbar
        }
    }
}
