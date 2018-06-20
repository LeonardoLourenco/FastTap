package leonardolourenco.fasttap;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Cursor cursor;
    private Users user = new Users();
    private TextView textViewUserName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewUserName = (TextView) findViewById(R.id.textViewUserName);

        //Open DB
        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(getApplicationContext());
        //Read from the database
        SQLiteDatabase db = dbFastTapOpenHelper.getWritableDatabase();

        final DbTableUsers tableUsers = new DbTableUsers(db);
        cursor = tableUsers.query(tableUsers.ALL_COLUMNS,null,null,null,null,null);

        if(cursor.getCount() > 0){  //It found a user
            cursor.moveToFirst();
            user = tableUsers.getCurrentUserFromCursor(cursor);
        }else{
            //Initialize default user we need to do this because android doesnt know that the user will get stuck on the alertDialog
            final long id = tableUsers.insert(DbTableUsers.getContentValues(user));   //we need this id so we can update the correct one after the alertdialog

            //ask the user for his name
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle(getString(R.string.hey_there));
            alertDialog.setMessage(getString(R.string.ask_username));

            final EditText input = new EditText(MainActivity.this);
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {  //if the user presses the phone back button we want the app to close
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_BACK)
                        dialog.dismiss();
                        finish();
                    return false;
                }
            });
            alertDialog.show();
            // with this the dialog will not let user do other actions till the user presses ok
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;

                    if(input.getText().toString().compareTo("") == 0){      //user still hasn't wrote anything
                        input.setError(getString(R.string.no_chars));
                    }else if(input.getText().toString().contains(" ")) {
                        input.setError(getString(R.string.no_spaces));
                    }else{
                        user.setUserName(input.getText().toString());
                        user.setGStar(0);
                        user.setBoughtSkin0(1);
                        user.setBoughtSkin1(0);
                        user.setBoughtSkin2(0);
                        user.setBoughtSkin3(0);
                        user.setBoughtSkin4(0);
                        user.setBoughtSkin5(0);
                        user.setSelectedSkin(0);
                        tableUsers.insert(DbTableUsers.getContentValues(user));
                        cursor = tableUsers.query(tableUsers.ALL_COLUMNS,null,null,null,null,null);
                        cursor.moveToFirst();
                        user = tableUsers.getCurrentUserFromCursor(cursor);
                        textViewUserName.setText(user.getUserName()+"");
                        wantToCloseDialog = true;
                    }
                    if(wantToCloseDialog)
                        alertDialog.dismiss();
                }
            });

        }
        
        textViewUserName.setText(user.getUserName()+"");
        if(user.getUserName()==null){
            textViewUserName.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToPlayAreaReaction (View  view){

        Intent intent = new Intent(this,playAreaActivity.class);
        intent.putExtra("gameMode",1);
        startActivity(intent);

    }

    public void goToPlayAreaArcade (View  view){

        Intent intent = new Intent(this,playAreaActivity.class);
        intent.putExtra("gameMode",2);
        startActivity(intent);

    }

    public void goToLeaderboards (View  view){ //block the user if he hasnt played both arcade and reaction time
        //Open DB
        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(getApplicationContext());
        //Read from the database
        SQLiteDatabase db = dbFastTapOpenHelper.getWritableDatabase();
        final DbTableHighScores tableHighScores = new DbTableHighScores(db);

        Cursor cursorHighScore = tableHighScores.query(null,DbTableHighScores.FIELD_ID_USER + "=?",
                new String[] { Long.toString(user.getId())},null,null,null);
        if(cursorHighScore.getCount() > 0){
            Intent intent = new Intent(this,leaderboardsActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, R.string.leaderboard_check, Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    public void goToSkins (View  view){
        Intent intent = new Intent(this,skinsActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() { //in order to properly close the activity
        super.onBackPressed();
        this.finish();
    }
}
