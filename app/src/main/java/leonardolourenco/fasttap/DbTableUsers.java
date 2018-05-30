package leonardolourenco.fasttap;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableUsers implements BaseColumns {
    public static final String TABLENAME = "Users";
    public static final String FIELD_USER_NAME = "UserName";

    private SQLiteDatabase db;

    public DbTableUsers(SQLiteDatabase db){
        this.db= db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE " + TABLENAME + "(" +
                        _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_USER_NAME + " TEXT NOT NULL" +
                        ")"
        );

    }

}
