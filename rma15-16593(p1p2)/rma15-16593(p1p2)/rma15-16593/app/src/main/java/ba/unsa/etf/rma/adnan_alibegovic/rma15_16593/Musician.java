package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adnan on 18-Mar-16.
 */
public class Musician implements Serializable {
    private String name;
    private String genre;
    private String webUrl;
    private String biography;
    private ArrayList<String> songs;

    public Musician(String _name, String _genre, String url, String bio, ArrayList<String> _songs) {
        name = _name;
        genre = _genre;
        webUrl = url;
        biography = bio;
        songs = _songs;
    }
    // getters
    public String getName() {
        return name;
    }
    public String getGenre() {
        return genre;
    }
    public String getWebUrl() {
        return webUrl;
    }
    public String getBiography() {
        return biography;
    }
    // setters
    public void setGenre(String _genre) {
        genre = _genre;
    }
    public void setWebUrl(String url) {
        webUrl = url;
    }
    public void setBiography(String bio) {
        biography = bio;
    }
    public void addSong(String song) {
        songs.add(song);
    }
    public List<String> getTop5Songs() {
        return songs.subList(0, 5);
    }
}
