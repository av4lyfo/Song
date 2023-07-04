package sg.edu.rp.c346.id22013834.song;
import androidx.annotation.NonNull;

public class Song {
    private int id;
    private String songTitle;
    private String songArtists;
    private int releaseYear;
    private int rating;

    public Song(String songTitle, String songArtists, int releaseYear, int rating) {
        this.songTitle = songTitle;
        this.songArtists = songArtists;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtists() {
        return songArtists;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getRating() {
        return rating;
    }

    @NonNull
    @Override
    public String toString() {
        return id + "\n" + songTitle + "\n" + songArtists + "\n" + releaseYear + "\n" + rating;
    }
}
