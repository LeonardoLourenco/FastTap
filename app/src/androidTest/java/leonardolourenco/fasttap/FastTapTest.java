package leonardolourenco.fasttap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FastTapTest {
    @Before
    public void setUp(){
        getContext().deleteDatabase(DbFastTapOpenHelper.DATABASE_NAME);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getContext();

        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(appContext);
        SQLiteDatabase db = dbFastTapOpenHelper.getReadableDatabase();

        assertTrue("Could not open/create FastTap database",db.isOpen());
        db.close();
    }

    @Test
    public void usersCRUDTest(){
        Context appContext = getContext();

        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(appContext);
        SQLiteDatabase db = dbFastTapOpenHelper.getReadableDatabase();

        DbTableUsers TableUsers = new DbTableUsers(db);

        Users user = new Users();
        user.setUserName("Xico");

        //Insert/Create (C)RUD
        long id = TableUsers.insert(DbTableUsers.getContentValues(user));

        assertNotEquals("Failed to insert a user",-1,id);

        //Read/Query C(R)UD
        user = ReadFirstUser(TableUsers,"Xico",id);

        //Update CR(U)D
        user.setUserName("Francisco");
        int rowsAffected = TableUsers.update(
                DbTableUsers.getContentValues(user),
                DbTableUsers._ID + "=?",
                new String[] { Long.toString(id) }
        );

        assertEquals("Failed to update user", 1, rowsAffected);

        user = ReadFirstUser(TableUsers,"Francisco",id);

        //Delete CRU(D)
        rowsAffected = TableUsers.delete(
                DbTableUsers._ID + "=?",
                new String[] { Long.toString(id) }
        );

        assertEquals("Failed to delete user", 1, rowsAffected);

        Cursor cursor = TableUsers.query(DbTableUsers.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Users found after delete ???",0, cursor.getCount());
    }

    @Test
    public void highScoresCRUDTest(){
        Context appContext = getContext();

        DbFastTapOpenHelper dbFastTapOpenHelper = new DbFastTapOpenHelper(appContext);
        SQLiteDatabase db = dbFastTapOpenHelper.getReadableDatabase();

        DbTableUsers TableUsers = new DbTableUsers(db);
        DbTableHighScores TableHighScores = new DbTableHighScores(db);

        Users user = new Users();
        user.setUserName("Xico");
        long idUser = TableUsers.insert(DbTableUsers.getContentValues(user));

        assertNotEquals("Failed to insert a user",-1,idUser);

        //Insert/Create (C)RUD
        HighScores highScores = new HighScores();

        highScores.setType(1); //1 - Reaction Time | 2 - Arcade Mode
        highScores.setHigh1("00:702");
        highScores.setHigh2("00:756");
        highScores.setHigh3("00:817");
        highScores.setHigh4("00:955");
        highScores.setHigh5("00:980");
        highScores.setIdUser((int) idUser);

        long id = TableHighScores.insert(DbTableHighScores.getContentValues(highScores));

        assertNotEquals("Failed to insert highScores",-1,id);

        //Read/Query C(R)UD
        highScores = ReadFirstHighScores(TableHighScores,1, "00:702", "00:756",
                                        "00:817", "00:955", "00:980", idUser, id);

        //Update CR(U)D
        highScores.setHigh5("00:960");
        int rowsAffected = TableHighScores.update(
                DbTableHighScores.getContentValues(highScores),
                DbTableHighScores._ID + "=?",
                new String[] { Long.toString(id) }
        );

        assertEquals("Failed to update highScores", 1, rowsAffected);

        highScores = ReadFirstHighScores(TableHighScores,1, "00:702", "00:756",
                "00:817", "00:955", "00:960", idUser, id);

        //Delete CRU(D)

        rowsAffected = TableHighScores.delete(
                DbTableHighScores._ID + "=?",
                new String[] { Long.toString(id) }
        );

        assertEquals("Failed to delete highScores", 1, rowsAffected);

        Cursor cursor = TableHighScores.query(DbTableHighScores.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("highScores found after delete ???",0, cursor.getCount());
    }


    @NonNull
    private HighScores ReadFirstHighScores(DbTableHighScores TableHighScores, int expectedtype, String expectedhigh1,
                                           String expectedhigh2, String expectedhigh3, String expectedhigh4, String expectedhigh5,
                                            long expectediduser, long expectedid) {
        Cursor cursor = TableHighScores.query(DbTableHighScores.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Failed to read highscores",1, cursor.getCount());

        assertTrue("Failed to read the first highscores",cursor.moveToNext());

        HighScores highScores = DbTableHighScores.getCurrentHighScoresFromCursor(cursor);
        assertEquals("Incorrect type",expectedtype,highScores.getType());
        assertEquals("Incorrect high1",expectedhigh1,highScores.getHigh1());
        assertEquals("Incorrect high2",expectedhigh2,highScores.getHigh2());
        assertEquals("Incorrect high3",expectedhigh3,highScores.getHigh3());
        assertEquals("Incorrect high4",expectedhigh4,highScores.getHigh4());
        assertEquals("Incorrect high5",expectedhigh5,highScores.getHigh5());
        assertEquals("Incorrect highscores user id",expectediduser,highScores.getIdUser());
        assertEquals("Incorrect highscores id",expectedid,highScores.getId());
        return highScores;
    }

    @NonNull
    private Users ReadFirstUser(DbTableUsers TableUsers, String expectedusername, long expectedid) {
        Cursor cursor = TableUsers.query(DbTableUsers.ALL_COLUMNS, null, null, null, null, null);
        assertEquals("Failed to read categories",1, cursor.getCount());

        assertTrue("Failed to read the first category",cursor.moveToNext());

        Users user = DbTableUsers.getCurrentUserFromCursor(cursor);
        assertEquals("Incorrect user name",expectedusername,user.getUserName());
        assertEquals("Incorrect user id",expectedid,user.getId());
        return user;
    }

    private Context getContext() {
        return InstrumentationRegistry.getTargetContext();
    }
}
