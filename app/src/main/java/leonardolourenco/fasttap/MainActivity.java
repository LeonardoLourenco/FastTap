package leonardolourenco.fasttap;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Cursor cursor;
    private Users user;
    private int gStar;
    private String username = "NoName";
    private TextView textViewGStarCountMain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewGStarCountMain = (TextView) findViewById(R.id.textViewGStarCountMain);

        //Open DB
        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(getApplicationContext());
        //Read from the database
        SQLiteDatabase db = dbFastTapOpenHelper.getReadableDatabase();

        final DbTableUsers tableUsers = new DbTableUsers(db);
        cursor = tableUsers.query(tableUsers.ALL_COLUMNS,null,null,null,null,null);

        if(cursor.getCount() == 0){  //It didnt find any user so we need to creat one

            //ask the user for his name
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Hey there");
            alertDialog.setMessage("In order to play you need to tell us your username");

            final EditText input = new EditText(MainActivity.this);
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

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
                        input.setError("You haven't written anything yet.");
                    }else if(input.getText().toString().contains(" ")) {
                        input.setError("No spaces allowed.");
                    }else{
                        username = input.getText().toString();
                        user.setUserName(username);
                        user.setGStar(0);
                        user.setBoughtSkin0(1);
                        user.setBoughtSkin1(0);
                        user.setBoughtSkin2(0);
                        user.setBoughtSkin3(0);
                        user.setBoughtSkin4(0);
                        user.setBoughtSkin5(0);
                        tableUsers.insert(DbTableUsers.getContentValues(user));
                        cursor = tableUsers.query(tableUsers.ALL_COLUMNS,null,null,null,null,null);
                        wantToCloseDialog = true;
                    }
                    if(wantToCloseDialog)
                        alertDialog.dismiss();
                }
            });
        }

        user = tableUsers.getCurrentUserFromCursor(cursor);







        textViewGStarCountMain.setText(gStar+"");




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

    public void goToPlayAreaArcade (View  view){

        Intent intent = new Intent(this,playAreaActivity.class);
        intent.putExtra("gameMode",2);
        intent.putExtra("gStar",gStar);
        startActivity(intent);

    }

    public void goToPlayAreaReaction (View  view){

        Intent intent = new Intent(this,playAreaActivity.class);
        intent.putExtra("gameMode",1);
        intent.putExtra("gStar",gStar);
        startActivity(intent);

    }
}
