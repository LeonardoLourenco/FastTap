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
    private Users user = new Users();
    private TextView textViewGStarCountMain;
    private TextView textViewUserName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewGStarCountMain = (TextView) findViewById(R.id.textViewGStarCountMain);
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
            user.setUserName("ZéNinguem");
            user.setGStar(0);
            user.setBoughtSkin0(1);
            user.setBoughtSkin1(0);
            user.setBoughtSkin2(0);
            user.setBoughtSkin3(0);
            user.setBoughtSkin4(0);
            user.setBoughtSkin5(0);
            final long id = tableUsers.insert(DbTableUsers.getContentValues(user));   //we need this id so we can update the correct one after the alertdialog

            //ask the user for his name
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Hey there");
            alertDialog.setMessage("Tell us your username so we can save your progress");

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
                        user.setUserName(input.getText().toString());
                        tableUsers.update(DbTableUsers.getContentValues(user),DbTableUsers._ID + "=?",
                                new String[] { Long.toString(id) });
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

        textViewGStarCountMain.setText(user.getGStar()+"");
        textViewUserName.setText(user.getUserName()+"");
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
}
