package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adnan on 18-Mar-16.
 */
public class Musician implements Parcelable {
    private String name;
    private String genre;
    private String webUrl;
    private String biography;
    private ArrayList<String> songs;
    private ArrayList<Musician> musicians;

    public Musician(String _name, String _genre, String url, String bio, ArrayList<String> _songs) {
        name = _name;
        genre = _genre;
        webUrl = url;
        biography = bio;
        songs = _songs;
    }
    protected Musician(Parcel source) {
        name = source.readString();
        genre = source.readString();
        webUrl = source.readString();
        biography = source.readString();
        songs = source.readArrayList(String.class.getClassLoader());
    }
    public static final Creator<Musician> CREATOR = new Creator<Musician>() {
        @Override
        public Musician createFromParcel(Parcel source) {
            return new Musician(source);
        }

        @Override
        public Musician[] newArray(int size) {
            return new Musician[size];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeString(genre);
        dest.writeString(webUrl);
        dest.writeString(biography);
        dest.writeList(songs);
    }
    @Override
    public int describeContents() {
        return 0;
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
    public void setName(String _name) { name = _name; }
    public void setGenre(String _genre) {
        genre = _genre;
    }
    public void setWebUrl(String _url) {
        webUrl = _url;
    }
    public void setBiography(String _bio) {
        biography = _bio;
    }
    public void setMusicians(List<Musician> _musicians) {
        musicians = new ArrayList<>(_musicians);
    }

    public void addSong(String _song) {
        songs.add(_song);
    }
    public List<String> getTop5Songs() {
        return songs.subList(0, 5);
    }
    
    public List<Musician> getSimilarMusicians() {
        List<Musician> similarMusicians = new ArrayList<Musician>();
        for (Musician m : musicians) {
            if(m.getGenre().equals(this.genre) && !m.getBiography().equals(this.biography)){
                similarMusicians.add(m);
            } // biografija jedini atribut koji je razlicit za muzicare sa istim imenom ukoliko ih ima
        }     // drugi uslov u if-u bi trebao biti preko ID - a, ali nemamo bazu, vec su podaci 'hard coded'
        return similarMusicians;
    }
}
