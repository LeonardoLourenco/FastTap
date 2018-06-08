package leonardolourenco.fasttap;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class playAreaActivity extends AppCompatActivity {

    private FastTap game;
    private ImageButton[][] buttons = new ImageButton[4][4];
    private Bitmap[] currentSkin = game.getSelectedSkin();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_area);

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

    }


    private void updateDisplay() {
        FastTap.BoardPiece[][] board = game.getBoard();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                switch (board[row][col]) {
                    case EMPTY:
                        buttons[row][col].setImageBitmap(currentSkin[0]);
                        break;
                    case GENEMY:
                        buttons[row][col].setImageBitmap(currentSkin[1]);
                        break;
                    case ENEMY:
                        buttons[row][col].setImageBitmap(currentSkin[2]);
                    case BOMB:
                        buttons[row][col].setImageBitmap(currentSkin[3]);
                        break;
                }
            }
        }


    }
}
