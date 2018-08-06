/**
 *  @author flora jha on 27/05/17
 */

package application.android.flora.grewordmadeeasy.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import android.provider.BaseColumns;


public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "wordManager";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_WORDLIST = "wordList";
    public static final String KEY_INDEX = "index";
    public static final String KEY_WORD = "word";
    public static final String KEY_MEANING = "meaning";
    public static final String KEY_SCORE = "score";
    private static final String TAG = DatabaseOpenHelper.class.getSimpleName();

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating tables
        //TODO: This query gives error near KEY_INDEX
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WORDLIST + " ("
                + KEY_INDEX + " INTEGER PRIMARY KEY," + KEY_WORD + " TEXT,"
                + KEY_MEANING + " TEXT," + KEY_SCORE + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //called when database is upgraded like modifying the table structure,
        //adding constraints to database etc
        if (oldVersion == 1) {
            //alter current table or schema and execute the sql command using db.execSQL(sql)
            this.onUpgrade(db, ++oldVersion, newVersion);
        }
        /* Alternative way to handle this
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
         */
    }
}

// vim: set ts=4 sw=4 tw=0 et :
