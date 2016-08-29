package mx.lpalma.photomap.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lpalma on 27/08/2016.
 */
public class PhotoMapDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Photo.db";
    public static final String TABLE_NAME = "PICTURE";
    public static final int DATABASE_VERSION = 2;
    public static final String PID = "_id";
    public static final String PATH = "path";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String TYPE = "type";

    // private Context context;
    private static final String SQL = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " (" + PID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + PATH
            + " VARCHAR(150) NOT NULL UNIQUE , " + LAT + " DOUBLE, " + LNG
            + " DOUBLE," + NAME + " VARCHAR(50), " + DATE
            + " VARCHAR(50), " + TYPE + " INTEGER);";
    private static final String SQL_UPDATE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + ";";

    public PhotoMapDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL);
        } catch (SQLException e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_UPDATE);
        onCreate(db);
    }
}
