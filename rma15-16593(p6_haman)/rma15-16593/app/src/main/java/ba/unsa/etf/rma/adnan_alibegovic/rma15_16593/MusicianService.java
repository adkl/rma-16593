package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Adnan on 16-May-16.
 */
public class MusicianService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    public MusicianService() {
        super(null);
    }

    public MusicianService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null){
            final ResultReceiver receiver = intent.getParcelableExtra("receiver");
            Bundle bundle = new Bundle();

            // beggining
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            String query = intent.getStringExtra("query");
            ArrayList<Musician> musicians = getMusicians(query);

            if(musicians == null || musicians.size() == 0) {
                bundle.putString("error", "Sorry, but we can't find any musicians with the entered name :(");
                receiver.send(STATUS_ERROR, bundle);
                return;
            }


            bundle.putParcelableArrayList("musicians", musicians);
            bundle.putString("query", query);

            receiver.send(STATUS_FINISHED, bundle);

            //errors are already handled in the catch blocks of the methods below

        }
    }

    public ArrayList<Musician> getMusicians(String query) {
        ArrayList<Musician> musicians = new ArrayList<>();
        try {
            query = URLEncoder.encode(query, "utf-8");

            String urlString = "https://api.spotify.com/v1/search?q=" + query + "&type=artist&limit=5";
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                InputStream stream = new BufferedInputStream( connection.getInputStream());

                String result = convertStreamToString(stream);

                JSONArray items = new JSONObject(result).getJSONObject("artists")
                        .getJSONArray("items");

                for(int i = 0; i < items.length(); i++){
                    JSONObject artist = items.getJSONObject(i);
                    String name = artist.getString("name");
                    String webPage = artist.getJSONObject("external_urls")
                            .getString("spotify");
                    Integer popularity = artist.getInt("popularity");
                    Integer followers = artist.getJSONObject("followers")
                            .getInt("total");

                    String genre = "";

                    JSONArray genres = artist.getJSONArray("genres");
                    if(genres.length() != 0) {
                        genre = genres.getString(0);
                    }


                    Musician m = new Musician(name, genre, webPage, "", getTopSongs(artist.getString("id")));
                    m.setSpotifyPopularity(popularity);
                    m.setSpotifyFollowers(followers);
                    m.setMusicians(musicians);
                    m.setAlbums(getAlbums(artist.getString("id")));

                    musicians.add(m);

                }

                return musicians;


            }
            catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            catch (JSONException ex ) {
                ex.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getTopSongs(String id) {
        try {
            URL url = new URL("https://api.spotify.com/v1/artists/" + id +"/top-tracks?country=us");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            int c = connection.getResponseCode();
            InputStream stream = new BufferedInputStream( connection.getInputStream());

            String result = convertStreamToString(stream);

            JSONArray tracks = new JSONObject(result).getJSONArray("tracks");

            ArrayList<String> songs = new ArrayList<>();

            for(int i = 0; i < tracks.length(); i++){
                JSONObject track = tracks.getJSONObject(i);
                songs.add(track.getString("name"));
            }

            return songs;


        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }
    public ArrayList<Pair<String, String>> getAlbums(String id) {
        try {
            URL url = new URL("https://api.spotify.com/v1/artists/" + id +"/albums?album_type=album&market=DE&limit=10");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            int c = connection.getResponseCode();
            InputStream stream = new BufferedInputStream( connection.getInputStream());

            String result = convertStreamToString(stream);

            JSONArray albumsArr = new JSONObject(result).getJSONArray("items");

            ArrayList<Pair<String, String>> albums = new ArrayList<Pair<String, String>>();

            for(int i = 0; i < albumsArr.length(); i++){
                JSONObject track = albumsArr.getJSONObject(i);
                albums.add(new Pair<String, String>(track.getString("name"),
                        track.getJSONObject("external_urls")
                                .getString("spotify")));
            }

            return albums;

        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }

    private String convertStreamToString(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();

    }
}
