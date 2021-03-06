package leonardolourenco.fasttap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbTableUsers implements BaseColumns {
    public static final String TABLENAME = "Users";
    public static final String FIELD_USER_NAME = "UserName";
    public static final String FIELD_GSTAR = "GStar";
    public static final String FIELD_BOUGHT_SKIN0 = "BoughtSkin0";
    public static final String FIELD_BOUGHT_SKIN1 = "BoughtSkin1";
    public static final String FIELD_BOUGHT_SKIN2 = "BoughtSkin2";
    public static final String FIELD_BOUGHT_SKIN3 = "BoughtSkin3";
    public static final String FIELD_BOUGHT_SKIN4 = "BoughtSkin4";
    public static final String FIELD_BOUGHT_SKIN5 = "BoughtSkin5";
    public static final String FIELD_SELECTED_SKIN = "SelectedSkin";


    private SQLiteDatabase db;

    public static final String [] ALL_COLUMNS = new String[] {_ID,FIELD_USER_NAME,FIELD_GSTAR,
                            FIELD_BOUGHT_SKIN0,FIELD_BOUGHT_SKIN1,FIELD_BOUGHT_SKIN2,
                            FIELD_BOUGHT_SKIN3,FIELD_BOUGHT_SKIN4,FIELD_BOUGHT_SKIN5,
                            FIELD_SELECTED_SKIN};

    public DbTableUsers(SQLiteDatabase db){
        this.db= db;
    }

    public void create(){
        db.execSQL(
                "CREATE TABLE " + TABLENAME + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FIELD_USER_NAME + " TEXT NOT NULL," +
                        FIELD_GSTAR + " INTEGER," +
                        FIELD_BOUGHT_SKIN0 + " INTEGER," +
                        FIELD_BOUGHT_SKIN1 + " INTEGER," +
                        FIELD_BOUGHT_SKIN2 + " INTEGER," +
                        FIELD_BOUGHT_SKIN3 + " INTEGER," +
                        FIELD_BOUGHT_SKIN4 + " INTEGER," +
                        FIELD_BOUGHT_SKIN5 + " INTEGER," +
                        FIELD_SELECTED_SKIN + " INTEGER" +
                        ")"
        );

    }

    public static ContentValues getContentValues(Users users){
        ContentValues values = new ContentValues();

        values.put(_ID,users.getId());
        values.put(FIELD_USER_NAME,users.getUserName());
        values.put(FIELD_GSTAR,users.getGStar());
        values.put(FIELD_BOUGHT_SKIN0,users.getBoughtSkin0());
        values.put(FIELD_BOUGHT_SKIN1,users.getBoughtSkin1());
        values.put(FIELD_BOUGHT_SKIN2,users.getBoughtSkin2());
        values.put(FIELD_BOUGHT_SKIN3,users.getBoughtSkin3());
        values.put(FIELD_BOUGHT_SKIN4,users.getBoughtSkin4());
        values.put(FIELD_BOUGHT_SKIN5,users.getBoughtSkin5());
        values.put(FIELD_SELECTED_SKIN,users.getSelectedSkin());

        return values;
    }

    public static Users getCurrentUserFromCursor(Cursor cursor){
        final int posId = cursor.getColumnIndex(_ID);
        final int posUserName = cursor.getColumnIndex(FIELD_USER_NAME);
        final int posGStar = cursor.getColumnIndex(FIELD_GSTAR);
        final int posBoughtSkin0 = cursor.getColumnIndex(FIELD_BOUGHT_SKIN0);
        final int posBoughtSkin1 = cursor.getColumnIndex(FIELD_BOUGHT_SKIN1);
        final int posBoughtSkin2 = cursor.getColumnIndex(FIELD_BOUGHT_SKIN2);
        final int posBoughtSkin3 = cursor.getColumnIndex(FIELD_BOUGHT_SKIN3);
        final int posBoughtSkin4 = cursor.getColumnIndex(FIELD_BOUGHT_SKIN4);
        final int posBoughtSkin5 = cursor.getColumnIndex(FIELD_BOUGHT_SKIN5);
        final int posSelectedSkin = cursor.getColumnIndex(FIELD_SELECTED_SKIN);

        Users user = new Users();

        user.setId(cursor.getInt(posId));
        user.setUserName(cursor.getString(posUserName));
        user.setGStar(cursor.getInt(posGStar));
        user.setBoughtSkin0(cursor.getInt(posBoughtSkin0));
        user.setBoughtSkin1(cursor.getInt(posBoughtSkin1));
        user.setBoughtSkin2(cursor.getInt(posBoughtSkin2));
        user.setBoughtSkin3(cursor.getInt(posBoughtSkin3));
        user.setBoughtSkin4(cursor.getInt(posBoughtSkin4));
        user.setBoughtSkin5(cursor.getInt(posBoughtSkin5));
        user.setSelectedSkin(cursor.getInt(posSelectedSkin));


        return user;
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
