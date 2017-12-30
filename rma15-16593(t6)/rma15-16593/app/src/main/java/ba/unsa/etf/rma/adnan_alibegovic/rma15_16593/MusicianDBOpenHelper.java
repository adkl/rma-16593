package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Adnan on 30-May-16.
 */
public class MusicianDBOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "musicians.db";
    public static final String DB_TABLE = "Musicians";
    public static final int DB_VERSION = 1;
    public static final String MUSICIAN_ID ="_id";
    public static final String MUSICIAN_NAME ="name";
    public static final String MUSICIAN_GENRE ="genre";
    // itd, ostale kolone, ali u ovom primjeru samo pokazujemo rad sa bazom

    public static final String DB_CREATE = "CREATE TABLE " + DB_TABLE + " (" + MUSICIAN_ID + " integer primary key autoincrement, " +
                                                                               MUSICIAN_NAME + " text not null, " +
                                                                               MUSICIAN_GENRE + " text not null)";

    public MusicianDBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}
