package sg.edu.rp.c346.id22013834.song;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editSongTitle, editSongArtists, editReleaseYear;
    RadioGroup ratingRadioGroup;
    RadioButton radio1, radio2, radio3, radio4, radio5;
    Button btnInsertSong, btnGetSongs;
    ListView lvSongList;
    ArrayAdapter<String> adapter;
    ArrayList<String> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSongTitle = findViewById(R.id.editSongTitle);
        editSongArtists = findViewById(R.id.editSongArtists);
        editReleaseYear = findViewById(R.id.editReleaseYear);
        ratingRadioGroup = findViewById(R.id.radioGroup);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio3 = findViewById(R.id.radio3);
        radio4 = findViewById(R.id.radio4);
        radio5 = findViewById(R.id.radio5);
        btnInsertSong = findViewById(R.id.btnInsertSong);
        btnGetSongs = findViewById(R.id.btnGetSongs);
        lvSongList = findViewById(R.id.lvSongList);

        songList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
        lvSongList.setAdapter(adapter);

        btnInsertSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String songTitle = editSongTitle.getText().toString();
                String songArtists = editSongArtists.getText().toString();
                int releaseYear = Integer.parseInt(editReleaseYear.getText().toString());
                int rating = getSelectedRating();

                Song song = new Song(songTitle, songArtists, releaseYear, rating);

                DBHelper dbHelper = new DBHelper(MainActivity.this);
                long insertedId = dbHelper.insertSong(song);
                dbHelper.close();

                if (insertedId != -1) {
                    Toast.makeText(MainActivity.this, "Song inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to insert song", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGetSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                ArrayList<Song> songs = dbHelper.getSongs();
                dbHelper.close();

                songList.clear();

                for (Song song : songs) {
                    String songDetails = "Title: " + song.getSongTitle()
                            + "\nArtists: " + song.getSongArtists()
                            + "\nRelease Year: " + song.getReleaseYear()
                            + "\nRating: " + song.getRating();

                    songList.add(songDetails);
                }

                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Song list retrieved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getSelectedRating() {
        int selectedId = ratingRadioGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.radio1) {
            return 1;
        } else if (selectedId == R.id.radio2) {
            return 2;
        } else if (selectedId == R.id.radio3) {
            return 3;
        } else if (selectedId == R.id.radio4) {
            return 4;
        } else if (selectedId == R.id.radio5) {
            return 5;
        } else {
            return 0;
        }
    }
}



