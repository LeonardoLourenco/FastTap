package leonardolourenco.fasttap;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableHighScores implements BaseColumns {
    public static final String TABLENAME = "HighScores";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_HIGH1 = "high1";
    public static final String FIELD_HIGH2 = "high2";
    public static final String FIELD_HIGH3 = "high3";
    public static final String FIELD_HIGH4 = "high4";
    public static final String FIELD_HIGH5 = "high5";
    public static final String FIELD_ID_USER = "idUser";

    private SQLiteDatabase db;

    public DbTableHighScores(SQLiteDatabase db) {
        this.db = db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE " + TABLENAME + "(" +
                        _ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_TYPE + " TEXT NOT NULL," +
                        FIELD_HIGH1 + " TEXT," +
                        FIELD_HIGH2 + " TEXT," +
                        FIELD_HIGH3 + " TEXT," +
                        FIELD_HIGH4 + " TEXT," +
                        FIELD_HIGH5 + " TEXT," +
                        FIELD_ID_USER + " INTEGER," +
                        "FOREIGN KEY(" + FIELD_ID_USER + ") REFERENCES " +
                        DbTableUsers.TABLENAME + "(" + DbTableUsers._ID + ")" +
                        ")"
        );

    }

    public static ContentValues getContentValues (HighScores highScores){
        ContentValues values = new ContentValues();

        values.put(_ID,highScores.getId());
        values.put(FIELD_TYPE,highScores.getType());
        values.put(FIELD_HIGH1,highScores.getHigh1());
        values.put(FIELD_HIGH2,highScores.getHigh2());
        values.put(FIELD_HIGH3,highScores.getHigh3());
        values.put(FIELD_HIGH4,highScores.getHigh4());
        values.put(FIELD_HIGH5,highScores.getHigh5());
        values.put(FIELD_ID_USER,highScores.getIdUser());

        return values;
    }

    public long insert(ContentValues values){
        return db.insert(TABLENAME,null,values);
    }
}
