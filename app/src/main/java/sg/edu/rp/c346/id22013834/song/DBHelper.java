package sg.edu.rp.c346.id22013834.song;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "song.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_ARTISTS = "artists";
    private static final String COLUMN_RELEASE_YEAR = "release_year";
    private static final String COLUMN_RATING = "rating";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_ARTISTS + " TEXT,"
                + COLUMN_RELEASE_YEAR + " INTEGER,"
                + COLUMN_RATING + " INTEGER"
                + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_SONG;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public long insertSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, song.getSongTitle());
        values.put(COLUMN_ARTISTS, song.getSongArtists());
        values.put(COLUMN_RELEASE_YEAR, song.getReleaseYear());
        values.put(COLUMN_RATING, song.getRating());

        long insertedId = db.insert(TABLE_SONG, null, values);
        db.close();

        return insertedId;
    }

    public ArrayList<Song> getSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SONG, null, null, null, null, null, null);

        int idIndex = cursor.getColumnIndex(COLUMN_ID);
        int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
        int artistsIndex = cursor.getColumnIndex(COLUMN_ARTISTS);
        int releaseYearIndex = cursor.getColumnIndex(COLUMN_RELEASE_YEAR);
        int ratingIndex = cursor.getColumnIndex(COLUMN_RATING);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(idIndex);
            String title = cursor.getString(titleIndex);
            String artists = cursor.getString(artistsIndex);
            int releaseYear = cursor.getInt(releaseYearIndex);
            int rating = cursor.getInt(ratingIndex);

            if (id != -1 && title != null && artists != null && releaseYear >= 0 && rating >= 0) {
                Song song = new Song(title, artists, releaseYear, rating);
                song.setId(id);
                songs.add(song);
            }
        }

        cursor.close();
        db.close();

        return songs;
    }
}

