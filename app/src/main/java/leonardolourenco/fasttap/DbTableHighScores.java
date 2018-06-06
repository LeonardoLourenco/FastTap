package leonardolourenco.fasttap;

import android.content.ContentValues;
import android.database.Cursor;
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

    public static final String [] ALL_COLUMNS = new String[] {_ID,FIELD_TYPE,FIELD_HIGH1,FIELD_HIGH2,FIELD_HIGH3,FIELD_HIGH4,FIELD_HIGH5,FIELD_ID_USER};

    public DbTableHighScores(SQLiteDatabase db) {
        this.db = db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE " + TABLENAME + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_TYPE + " INTEGER NOT NULL," +
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

    public static HighScores getCurrentHighScoresFromCursor(Cursor cursor){
        final int posId = cursor.getColumnIndex(_ID);
        final int posType = cursor.getColumnIndex(FIELD_TYPE);
        final int posHigh1 = cursor.getColumnIndex(FIELD_HIGH1);
        final int posHigh2 = cursor.getColumnIndex(FIELD_HIGH2);
        final int posHigh3 = cursor.getColumnIndex(FIELD_HIGH3);
        final int posHigh4 = cursor.getColumnIndex(FIELD_HIGH4);
        final int posHigh5 = cursor.getColumnIndex(FIELD_HIGH5);
        final int posIdUser = cursor.getColumnIndex(FIELD_ID_USER);

        HighScores highScores = new HighScores();

        highScores.setId(cursor.getInt(posId));
        highScores.setType(cursor.getInt(posType));
        highScores.setHigh1(cursor.getString(posHigh1));
        highScores.setHigh2(cursor.getString(posHigh2));
        highScores.setHigh3(cursor.getString(posHigh3));
        highScores.setHigh4(cursor.getString(posHigh4));
        highScores.setHigh5(cursor.getString(posHigh5));
        highScores.setIdUser(cursor.getInt(posIdUser));


        return highScores;
    }

    /**
     * Convenience method for inserting a row into the categories table.
     *
     * @param values this map contains the initial column values for the
     *            row. The keys should be the column names and the values the
     *            column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(ContentValues values) {
        return db.insert(TABLENAME, null, values);
    }

    /**
     * Convenience method for updating rows in the categories table.
     *
     * @param values a map from column names to new column values. null is a
     *            valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *            Passing null will update all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected
     */
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(TABLENAME, values, whereClause, whereArgs);
    }

    /**
     * Convenience method for deleting rows in the categories table.
     *
     * @param whereClause the optional WHERE clause to apply when deleting.
     *            Passing null will delete all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
     */
    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(TABLENAME, whereClause, whereArgs);
    }

    /**
     * Query the categories table, returning a {@link Cursor} over the result set.
     *
     * @param columns A list of which columns to return. Passing null will
     *            return all columns, which is discouraged to prevent reading
     *            data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted as an
     *            SQL WHERE clause (excluding the WHERE itself). Passing null
     *            will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *         replaced by the values from selectionArgs, in order that they
     *         appear in the selection. The values will be bound as Strings.
     * @param groupBy A filter declaring how to group rows, formatted as an SQL
     *            GROUP BY clause (excluding the GROUP BY itself). Passing null
     *            will cause the rows to not be grouped.
     * @param having A filter declare which row groups to include in the cursor,
     *            if row grouping is being used, formatted as an SQL HAVING
     *            clause (excluding the HAVING itself). Passing null will cause
     *            all row groups to be included, and is required when row
     *            grouping is not being used.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
     *            (excluding the ORDER BY itself). Passing null will use the
     *            default sort order, which may be unordered.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     * {@link Cursor}s are not synchronized, see the documentation for more details.
     * @see Cursor
     */public Cursor query (String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(TABLENAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}
