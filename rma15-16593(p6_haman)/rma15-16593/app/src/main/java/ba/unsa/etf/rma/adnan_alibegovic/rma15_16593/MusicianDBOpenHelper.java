package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adnan on 31-May-16.
 */
public class MusicianDBOpenHelper extends SQLiteOpenHelper {
    // we will use SINGLETON pattern
    // because we want only one instance in application context
    private static MusicianDBOpenHelper helperInstance;

    public static final String DB_NAME = "RMA16593AA.db";
    public static final int DB_VERSION = 1;

    public static final String DB_MUSICIANS = "Musicians";
    public static final String DB_MUSICIANS_ID = "_id";
    public static final String DB_MUSICIANS_NAME = "name";
    public static final String DB_MUSICIANS_GENRE = "genre";
    public static final String DB_MUSICIANS_FOLLOWERS = "followers";
    public static final String DB_MUSICIANS_PAGE = "page";
    public static final String DB_MUSICIANS_POPULARITY = "popularity";
    public static final String DB_MUSICIANS_TIMEADDED = "time";

    public static final String DB_SONGS = "Songs";
    public static final String DB_SONGS_ID = "_id";
    public static final String DB_SONGS_NAME = "name";
    public static final String DB_SONGS_MUSICIAN_ID = "musicianId";

    public static final String DB_ALBUMS = "Albums";
    public static final String DB_ALBUMS_ID = "_id";
    public static final String DB_ALBUMS_NAME = "name";
    public static final String DB_ALBUMS_EXTERNALURL = "url";
    public static final String DB_ALBUMS_MUSICIAN_ID = "musicianId";

    public static final String DB_SEARCH_HISTORY = "SearchHistory";
    public static final String DB_SEARCH_HISTORY_ID = "_id";
    public static final String DB_SEARCH_HISTORY_QUERY = "query";
    public static final String DB_SEARCH_HISTORY_TIME = "time";
    public static final String DB_SEARCH_HISTORY_SUCCESSFUL = "successful";

    String CREATE_MUSICIANS_TABLE = "CREATE TABLE " + DB_MUSICIANS +
                                    "(" +
                                        DB_MUSICIANS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        DB_MUSICIANS_NAME + " TEXT NOT NULL, " +
                                        DB_MUSICIANS_GENRE + " TEXT, " +
                                        DB_MUSICIANS_FOLLOWERS + " INTEGER, " +
                                        DB_MUSICIANS_PAGE + " TEXT, " +
                                        DB_MUSICIANS_POPULARITY + " INTEGER, " +
                                        DB_MUSICIANS_TIMEADDED + " DATETIME " +
                                    ")";

    String CREATE_SONGS_TABLE = "CREATE TABLE " + DB_SONGS +
            "(" +
            DB_SONGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DB_SONGS_NAME + " TEXT NOT NULL," +
            DB_SONGS_MUSICIAN_ID + " INTEGER REFERENCES " + DB_MUSICIANS + //"" +
            ")";

    String CREATE_ALBUMS_TABLE = "CREATE TABLE " + DB_ALBUMS +
            "(" +
            DB_ALBUMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DB_ALBUMS_NAME + " TEXT NOT NULL," +
            DB_ALBUMS_EXTERNALURL + " TEXT," +
            DB_ALBUMS_MUSICIAN_ID + " INTEGER REFERENCES " + DB_MUSICIANS + //"" +
            ")";

    String CREATE_SEARCH_HISTORY_TABLE = "CREATE TABLE " + DB_SEARCH_HISTORY +
            "( " +
            DB_SEARCH_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DB_SEARCH_HISTORY_QUERY + " TEXT NOT NULL, " +
            DB_SEARCH_HISTORY_TIME + " DATETIME, " +
            DB_SEARCH_HISTORY_SUCCESSFUL + " INTEGER " +
            " )";

    public MusicianDBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public static synchronized MusicianDBOpenHelper getInstance(Context context){
        // always use applicationContext
        if (helperInstance == null){
            helperInstance = new MusicianDBOpenHelper(context);
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MUSICIANS_TABLE);
        db.execSQL(CREATE_SONGS_TABLE);
        db.execSQL(CREATE_ALBUMS_TABLE);
        db.execSQL(CREATE_SEARCH_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + DB_SONGS);
            db.execSQL("DROP TABLE IF EXISTS " + DB_ALBUMS);
            db.execSQL("DROP TABLE IF EXISTS " + DB_MUSICIANS);
            db.execSQL("DROP TABLE IF EXISTS " + DB_SEARCH_HISTORY);
            onCreate(db);
        }
    }
    private long getMusicianID(String name){
        long userId = -1;
        SQLiteDatabase db = getReadableDatabase();
        String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                DB_MUSICIANS_ID, DB_MUSICIANS, DB_MUSICIANS_NAME);
        Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{name});
        try {
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(0);
            }
        }
        catch (Exception e){
            Log.d("DATABASE_ERROR", e.getMessage());
        }
        finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return userId;
    }

    public ArrayList<Musician> getAllMusicians(Integer limit){
        if(limit < 1)
            return null;
        ArrayList<Musician> musicians = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] columns = new String[]{DB_MUSICIANS_NAME, DB_MUSICIANS_TIMEADDED};

        try {
            Cursor cursor = db.query(DB_MUSICIANS, columns, null, null, null, null, DB_MUSICIANS_TIMEADDED + " desc", limit.toString());

            if(cursor.moveToFirst()){
                do{
                    Musician musician = getMusician(cursor.getString(cursor.getColumnIndex(DB_MUSICIANS_NAME)));
                    musician.setMusicians(musicians);
                    musicians.add(musician);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e) {
            Log.d("DATABASE_ERROR", e.getMessage());
        }finally {
            return musicians;
        }
    }

    public Musician getMusician(String name) {
        Long musicianID = getMusicianID(name);
        if(musicianID == -1)
            return null;


        SQLiteDatabase db = getReadableDatabase();
        String[] columns = new String[]{DB_MUSICIANS_NAME, DB_MUSICIANS_GENRE, DB_MUSICIANS_FOLLOWERS,
                                        DB_MUSICIANS_POPULARITY, DB_MUSICIANS_PAGE};

        Musician musician = null;

        try {
            Cursor cursor = db.query(DB_MUSICIANS, columns, DB_MUSICIANS_NAME + " = ?", new String[]{name}, null, null, null);
            if(cursor.moveToFirst()) {
                do {
                    String genre = cursor.getString(cursor.getColumnIndex(DB_MUSICIANS_GENRE));
                    String page = cursor.getString(cursor.getColumnIndex(DB_MUSICIANS_PAGE));
                    int followers = cursor.getInt(cursor.getColumnIndex(DB_MUSICIANS_FOLLOWERS));
                    int popularity = cursor.getInt(cursor.getColumnIndex(DB_MUSICIANS_POPULARITY));

                    musician = new Musician(name, genre, page, null, new ArrayList<String>());
                    musician.setSpotifyFollowers(followers);
                    musician.setSpotifyPopularity(popularity);
                    musician.setAlbums(new ArrayList<Pair<String, String>>());

                } while (cursor.moveToNext());
            }
            cursor.close();

            // fetching songs
            cursor = db.query(DB_SONGS, new String[]{DB_SONGS_NAME}, DB_SONGS_MUSICIAN_ID + " = " + musicianID, null, null, null, null);
            if(cursor.moveToFirst()){
                do{
                    String song = cursor.getString(0);
                    musician.addSong(song);
                } while (cursor.moveToNext());
            }
            cursor.close();

            //fetching albums
            cursor = db.query(DB_ALBUMS, new String[]{DB_ALBUMS_NAME, DB_ALBUMS_EXTERNALURL}, DB_ALBUMS_MUSICIAN_ID + " = " + musicianID.toString(), null, null, null, null);
            if(cursor.moveToFirst()){
                do{
                    String albumName = cursor.getString(cursor.getColumnIndex(DB_ALBUMS_NAME));
                    String albumURL = cursor.getString(cursor.getColumnIndex(DB_ALBUMS_EXTERNALURL));

                    Pair<String, String> album = new Pair<>(albumName, albumURL);
                    musician.addAlbum(album);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (Exception e){
            Log.d("DATABASE_ERROR", e.getMessage());
        }
        finally {
            return musician;
        }

    }

    public ArrayList<SearchHistoryItem> getSearchHistory(){
        ArrayList<SearchHistoryItem> history = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = new String[]{DB_SEARCH_HISTORY_QUERY, DB_SEARCH_HISTORY_TIME, DB_SEARCH_HISTORY_SUCCESSFUL};

        try {
            Cursor cursor = db.query(DB_SEARCH_HISTORY, columns, null, null, null, null, DB_SEARCH_HISTORY_TIME + " desc");

            if(cursor.moveToFirst()){
                do {
                    String query = cursor.getString(cursor.getColumnIndex(DB_SEARCH_HISTORY_QUERY));
                    String time = cursor.getString(cursor.getColumnIndex(DB_SEARCH_HISTORY_TIME));
                    int successful = cursor.getInt(cursor.getColumnIndex(DB_SEARCH_HISTORY_SUCCESSFUL));


                    SearchHistoryItem item = new SearchHistoryItem(query, null, successful == 1);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e) {
            Log.d("DATABASE_ERROR", e.getMessage());
        }
        finally {
            return history;
        }
    }

    public void addSearchHistoryItem(SearchHistoryItem item) {
        if(item != null) {
            try {
                SQLiteDatabase db = getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DB_SEARCH_HISTORY_QUERY, item.getQuery());

                String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
                values.put(DB_SEARCH_HISTORY_TIME, formatter.format(item.getTime()));

                values.put(DB_SEARCH_HISTORY_SUCCESSFUL, item.isSuccessful()? 1 : 0);

                String whereClause = String.format("%s = ?", DB_SEARCH_HISTORY_QUERY);

                int updated = db.update(DB_SEARCH_HISTORY, values, whereClause, new String[]{item.getQuery()});

                if(updated != 1) {
                    db.insertOrThrow(DB_SEARCH_HISTORY, null, values);
                }
            }
            catch (Exception e) {
                Log.d("DATABASE_ERROR", e.getMessage());
            }

        }
    }

    private void addSongs(ArrayList<String> songs, Long musicianID){
        if(songs != null) {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            for(String song : songs){
                contentValues.put(DB_SONGS_NAME, song);
                contentValues.put(DB_SONGS_MUSICIAN_ID, musicianID);

                String whereClause = String.format("%s = ? AND %s = ?", DB_SONGS_NAME, DB_SONGS_MUSICIAN_ID);

                int updated = db.update(DB_SONGS, contentValues, whereClause, new String[]{song, musicianID.toString()});

                if(updated != 1) {
                    db.insertOrThrow(DB_SONGS, null, contentValues);
                }
            }
        }
    }
    private void addAlbums(ArrayList<Pair<String, String>> albums, Long musicianID) {
        if(albums != null) {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            for (Pair<String, String> album : albums){
                contentValues.put(DB_ALBUMS_NAME, album.first);
                contentValues.put(DB_ALBUMS_EXTERNALURL, album.second);
                contentValues.put(DB_ALBUMS_MUSICIAN_ID, musicianID);

                String whereClause = String.format("%s = ? AND %s = ?", DB_ALBUMS_NAME, DB_ALBUMS_MUSICIAN_ID);

                int updated = db.update(DB_ALBUMS, contentValues, whereClause, new String[]{album.first, musicianID.toString()});

                if(updated != 1) {
                    db.insertOrThrow(DB_ALBUMS, null, contentValues);
                }
            }
        }
    }

    public boolean addOrUpdateMusician(Musician m){
        SQLiteDatabase db = getWritableDatabase();

        long musicianID = -1;

        db.beginTransaction();
        try {
            ContentValues newValues = new ContentValues();
            newValues.put(DB_MUSICIANS_NAME, m.getName());
            newValues.put(DB_MUSICIANS_FOLLOWERS, m.getSpotifyFollowers());
            newValues.put(DB_MUSICIANS_GENRE, m.getGenre());
            newValues.put(DB_MUSICIANS_PAGE, m.getWebUrl());
            newValues.put(DB_MUSICIANS_POPULARITY, m.getSpotifyPopularity());

            Date currentDate = new Date();
            String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);

            newValues.put(DB_MUSICIANS_TIMEADDED, formatter.format(currentDate));


            int rowsUpdated = db.update(DB_MUSICIANS, newValues, DB_MUSICIANS_NAME + "= ?", new String[]{m.getName()} );

            if(rowsUpdated == 1 ) {
                musicianID = getMusicianID(m.getName());
            }
            else {
                musicianID = db.insertOrThrow(DB_MUSICIANS, null, newValues);
            }

            if(musicianID != -1) {
                addSongs(m.getSongs(), musicianID);
                addAlbums(m.getAlbums(), musicianID);
                db.setTransactionSuccessful();
                return true;
            }
            return false;
        }
        catch (Exception e){
            Log.d("DATABASE_ERROR", e.getMessage());
            return false;
        }
        finally {
            db.endTransaction();
        }
    }
}
