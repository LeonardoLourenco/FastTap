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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class skinsActivity extends AppCompatActivity {

    private String skinName;
    private Users user = new Users();
    private Cursor cursor;
    private TextView textViewSkinName;
    private TextView textViewGStarCountSkins;
    private TextView textViewSkin0;
    private TextView textViewSkin1;
    private TextView textViewSkin2;
    private TextView textViewSkin3;
    private TextView textViewSkin4;
    private TextView textViewSkin5;
    private ImageButton imageButtonSkin0;
    private ImageButton imageButtonSkin1;
    private ImageButton imageButtonSkin2;
    private ImageButton imageButtonSkin3;
    private ImageButton imageButtonSkin4;
    private ImageButton imageButtonSkin5;
    private int[] boughSkins = new int[6];
    private int selectedSkin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skins);

        textViewSkinName = (TextView) findViewById(R.id.textViewSkinName);
        textViewGStarCountSkins = (TextView) findViewById(R.id.textViewGStarCountSkins);
        textViewSkin0 = (TextView) findViewById(R.id.textViewSkin0);
        textViewSkin1 = (TextView) findViewById(R.id.textViewSkin1);
        textViewSkin2 = (TextView) findViewById(R.id.textViewSkin2);
        textViewSkin3 = (TextView) findViewById(R.id.textViewSkin3);
        textViewSkin4 = (TextView) findViewById(R.id.textViewSkin4);
        textViewSkin5 = (TextView) findViewById(R.id.textViewSkin5);

        imageButtonSkin0 = (ImageButton) findViewById(R.id.imageButtonSkin0);
        imageButtonSkin1 = (ImageButton) findViewById(R.id.imageButtonSkin1);
        imageButtonSkin2 = (ImageButton) findViewById(R.id.imageButtonSkin2);
        imageButtonSkin3 = (ImageButton) findViewById(R.id.imageButtonSkin3);
        imageButtonSkin4 = (ImageButton) findViewById(R.id.imageButtonSkin4);
        imageButtonSkin5 = (ImageButton) findViewById(R.id.imageButtonSkin5);


        //Open DB
        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(getApplicationContext());
        //Read from the database
        SQLiteDatabase db = dbFastTapOpenHelper.getWritableDatabase();

        final DbTableUsers tableUsers = new DbTableUsers(db);
        cursor = tableUsers.query(tableUsers.ALL_COLUMNS,null,null,null,null,null);
        cursor.moveToFirst();
        user = tableUsers.getCurrentUserFromCursor(cursor);

        updateDisplay();
    }



    public void goToMainActivity(View view){
        this.finish();
    }

    public void touchSkin(View view){
        ImageButton b = (ImageButton) view;
        String tag = b.getTag().toString();
        selectedSkin = Integer.parseInt(tag);

        switch (selectedSkin){
            case 0:
                skinName = getString(R.string.skinAlpha);
                break;
            case 1:
                skinName = getString(R.string.skinSlimes);
                break;
            case 2:
                skinName = getString(R.string.skinGhosts);
                break;
            case 3:
                skinName = getString(R.string.skinCookies);
                break;
            case 4:
                skinName = getString(R.string.skinKnights);
                break;
            case 5:
                skinName = getString(R.string.skinSamurais);
                break;
        }

        if(boughSkins[selectedSkin] == 1){          //if the user bought the skin , make that skin the selected one
            user.setSelectedSkin(selectedSkin);
            updateUser();
            updateDisplay();
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(skinsActivity.this).create();
            alertDialog.setTitle(getString(R.string.buying_skin) + skinName);
            alertDialog.setMessage(getString(R.string.buy_check));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.buttonBuy),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {       //check if the user has GStar to buy the skin
                            if(selectedSkin == 0 || selectedSkin == 1){
                                if(user.getGStar() >= 50){
                                    switch (selectedSkin){
                                        case 0:
                                            user.setBoughtSkin0(1);
                                            break;
                                        case 1:
                                            user.setBoughtSkin1(1);
                                            break;
                                    }
                                    user.setSelectedSkin(selectedSkin);
                                    user.setGStar(user.getGStar() - 50);
                                    updateUser();
                                    updateDisplay();
                                }else {
                                    makeToast();
                                }
                            }else {
                                if (user.getGStar() >= 100) {
                                    switch (selectedSkin) {
                                        case 2:
                                            user.setBoughtSkin2(1);
                                            break;
                                        case 3:
                                            user.setBoughtSkin3(1);
                                            break;
                                        case 4:
                                            user.setBoughtSkin4(1);
                                            break;
                                        case 5:
                                            user.setBoughtSkin5(1);
                                            break;
                                    }
                                    user.setSelectedSkin(selectedSkin);
                                    user.setGStar(user.getGStar() - 100);
                                    updateUser();
                                    updateDisplay();
                                } else {
                                    makeToast();
                                }
                            }
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.buttonCancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialog.show();
        }

    }

    private void updateDisplay(){
        boughSkins[0]=user.getBoughtSkin0();
        boughSkins[1]=user.getBoughtSkin1();
        boughSkins[2]=user.getBoughtSkin2();
        boughSkins[3]=user.getBoughtSkin3();
        boughSkins[4]=user.getBoughtSkin4();
        boughSkins[5]=user.getBoughtSkin5();


        switch (user.getSelectedSkin()){
            case 0:
                skinName = getString(R.string.skinAlpha);
                break;
            case 1:
                skinName = getString(R.string.skinSlimes);
                break;
            case 2:
                skinName = getString(R.string.skinGhosts);
                break;
            case 3:
                skinName = getString(R.string.skinCookies);
                break;
            case 4:
                skinName = getString(R.string.skinKnights);
                break;
            case 5:
                skinName = getString(R.string.skinSamurais);
                break;
        }
                                                //Change with correct skin resouces
        if(boughSkins[0] == 1){
            textViewSkin0.setText(R.string.bought);
            imageButtonSkin0.setImageResource(R.drawable.testenemy1);
        }else{
            textViewSkin0.setText(R.string.N50);
            imageButtonSkin0.setImageResource(R.drawable.testenemy5);
        }
        if(boughSkins[1] == 1){
            textViewSkin1.setText(R.string.bought);
            imageButtonSkin1.setImageResource(R.drawable.slimes1);
        }else{
            textViewSkin1.setText(R.string.N50);
            imageButtonSkin1.setImageResource(R.drawable.slimes3);
        }
        if(boughSkins[2] == 1){
            textViewSkin2.setText(R.string.bought);
            imageButtonSkin2.setImageResource(R.drawable.ghost1);
        }else{
            textViewSkin2.setText(R.string.N100);
            imageButtonSkin2.setImageResource(R.drawable.ghost3);
        }
        if(boughSkins[3] == 1){
            textViewSkin3.setText(R.string.bought);
            imageButtonSkin3.setImageResource(R.drawable.cookies1);
        }else{
            textViewSkin3.setText(R.string.N100);
            imageButtonSkin3.setImageResource(R.drawable.cookiest3);
        }
        if(boughSkins[4] == 1){
            textViewSkin4.setText(R.string.bought);
            imageButtonSkin4.setImageResource(R.drawable.knights1);
        }else{
            textViewSkin4.setText(R.string.N100);
            imageButtonSkin4.setImageResource(R.drawable.knights3);
        }
        if(boughSkins[5] == 1){
            textViewSkin5.setText(R.string.bought);
            imageButtonSkin5.setImageResource(R.drawable.testenemy1);
        }else{
            textViewSkin5.setText(R.string.N100);
            imageButtonSkin5.setImageResource(R.drawable.testenemy5);
        }

        textViewGStarCountSkins.setText(user.getGStar()+"");
        textViewSkinName.setText(skinName);
    }

    private void makeToast(){
        Toast.makeText(this, R.string.buy_check_failed,Toast.LENGTH_SHORT).show();
    }

    private void updateUser(){
        //Open DB
        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(getApplicationContext());
        //Read from the database
        SQLiteDatabase db = dbFastTapOpenHelper.getWritableDatabase();

        final DbTableUsers tableUsers = new DbTableUsers(db);

        tableUsers.update(DbTableUsers.getContentValues(user),DbTableUsers._ID + "=?",
                new String[] { Long.toString(user.getId()) });
        db.close();
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
