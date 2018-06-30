package leonardolourenco.fasttap;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class leaderboardsActivity extends AppCompatActivity {

    private Users user = new Users();
    private HighScores highScores = new HighScores();
    private TextView textViewHigh1;
    private TextView textViewHigh2;
    private TextView textViewHigh3;
    private TextView textViewHigh4;
    private TextView textViewHigh5;
    private Button buttonHighReact;
    private Button buttonHighArcade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        textViewHigh1 = (TextView) findViewById(R.id.textViewHigh1);
        textViewHigh2 = (TextView) findViewById(R.id.textViewHigh2);
        textViewHigh3 = (TextView) findViewById(R.id.textViewHigh3);
        textViewHigh4 = (TextView) findViewById(R.id.textViewHigh4);
        textViewHigh5 = (TextView) findViewById(R.id.textViewHigh5);
        buttonHighReact = (Button) findViewById(R.id.buttonHighReact);
        buttonHighArcade = (Button) findViewById(R.id.buttonHighArcade);

        buttonHighReact.setTextColor(getResources().getColor(R.color.buttonselected));
        buttonHighArcade.setTextColor(getResources().getColor(R.color.buttondefault));

        //Open DB
        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(getApplicationContext());
        //Read from the database
        SQLiteDatabase db = dbFastTapOpenHelper.getWritableDatabase();

        final DbTableUsers tableUsers = new DbTableUsers(db);
        final DbTableHighScores tableHighScores = new DbTableHighScores(db);
        Cursor cursorUser = tableUsers.query(tableUsers.ALL_COLUMNS,null,null,null,null,null);
        cursorUser.moveToFirst();
        user = tableUsers.getCurrentUserFromCursor(cursorUser);
        //In case the user hasn't played this game mode we need to show that there are no values there.

        highScores.setHigh1("99:999");
        highScores.setHigh2("99:999");
        highScores.setHigh3("99:999");
        highScores.setHigh4("99:999");
        highScores.setHigh5("99:999");

        Cursor cursorHighScore = tableHighScores.query(null,DbTableHighScores.FIELD_ID_USER + "=? AND " + DbTableHighScores.FIELD_TYPE + "=?",
                new String[] { Long.toString(user.getId()) , Integer.toString(1) },null,null,null);
        cursorHighScore.moveToFirst();
        if(cursorHighScore.getCount()>0) {      //Verify if the user played this game mode at least 1 time.
            highScores = tableHighScores.getCurrentHighScoresFromCursor(cursorHighScore);
        }
        textViewHigh1.setText(highScores.getHigh1()+"");
        textViewHigh2.setText(highScores.getHigh2()+"");
        textViewHigh3.setText(highScores.getHigh3()+"");
        textViewHigh4.setText(highScores.getHigh4()+"");
        textViewHigh5.setText(highScores.getHigh5()+"");
        db.close();
    }


    public void highReact(View view){
        buttonHighReact.setTextColor(getResources().getColor(R.color.buttonselected));
        buttonHighArcade.setTextColor(getResources().getColor(R.color.buttondefault));
        //Open DB
        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(getApplicationContext());
        //Read from the database
        SQLiteDatabase db = dbFastTapOpenHelper.getWritableDatabase();

        final DbTableUsers tableUsers = new DbTableUsers(db);
        final DbTableHighScores tableHighScores = new DbTableHighScores(db);
        Cursor cursorUser = tableUsers.query(tableUsers.ALL_COLUMNS,null,null,null,null,null);
        cursorUser.moveToFirst();
        user = tableUsers.getCurrentUserFromCursor(cursorUser);

        highScores.setHigh1("99:999");
        highScores.setHigh2("99:999");
        highScores.setHigh3("99:999");
        highScores.setHigh4("99:999");
        highScores.setHigh5("99:999");

        Cursor cursorHighScore = tableHighScores.query(null,DbTableHighScores.FIELD_ID_USER + "=? AND " + DbTableHighScores.FIELD_TYPE + "=?",
                new String[] { Long.toString(user.getId()) , Integer.toString(1) },null,null,null);
        cursorHighScore.moveToFirst();
        if(cursorHighScore.getCount()>0) {
            highScores = tableHighScores.getCurrentHighScoresFromCursor(cursorHighScore);
        }

        textViewHigh1.setText(highScores.getHigh1()+"");
        textViewHigh2.setText(highScores.getHigh2()+"");
        textViewHigh3.setText(highScores.getHigh3()+"");
        textViewHigh4.setText(highScores.getHigh4()+"");
        textViewHigh5.setText(highScores.getHigh5()+"");
        db.close();
    }

    public void highArcade(View view){
        buttonHighReact.setTextColor(getResources().getColor(R.color.buttondefault));
        buttonHighArcade.setTextColor(getResources().getColor(R.color.buttonselected));
        //Open DB
        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(getApplicationContext());
        //Read from the database
        SQLiteDatabase db = dbFastTapOpenHelper.getWritableDatabase();

        final DbTableUsers tableUsers = new DbTableUsers(db);
        final DbTableHighScores tableHighScores = new DbTableHighScores(db);
        Cursor cursorUser = tableUsers.query(tableUsers.ALL_COLUMNS,null,null,null,null,null);
        cursorUser.moveToFirst();
        user = tableUsers.getCurrentUserFromCursor(cursorUser);

        highScores.setHigh1("0");
        highScores.setHigh2("0");
        highScores.setHigh3("0");
        highScores.setHigh4("0");
        highScores.setHigh5("0");

        Cursor cursorHighScore = tableHighScores.query(null,DbTableHighScores.FIELD_ID_USER + "=? AND " + DbTableHighScores.FIELD_TYPE + "=?",
                new String[] { Long.toString(user.getId()) , Integer.toString(2) },null,null,null);
        cursorHighScore.moveToFirst();
        if(cursorHighScore.getCount()>0) {
            highScores = tableHighScores.getCurrentHighScoresFromCursor(cursorHighScore);
        }

        textViewHigh1.setText(highScores.getHigh1()+"");
        textViewHigh2.setText(highScores.getHigh2()+"");
        textViewHigh3.setText(highScores.getHigh3()+"");
        textViewHigh4.setText(highScores.getHigh4()+"");
        textViewHigh5.setText(highScores.getHigh5()+"");
        db.close();
    }

    public void goToMainActivity(View view){
        this.finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
