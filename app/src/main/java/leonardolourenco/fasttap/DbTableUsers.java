package leonardolourenco.fasttap;

import android.content.ContentValues;
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

    public static ContentValues getContentValues(Users users){
        ContentValues values = new ContentValues();

        values.put(_ID,users.getId());
        values.put(FIELD_USER_NAME,users.getUserName());

        return values;
    }


    public long insert(ContentValues values){
        return db.insert(TABLENAME,null,values);
    }

}
