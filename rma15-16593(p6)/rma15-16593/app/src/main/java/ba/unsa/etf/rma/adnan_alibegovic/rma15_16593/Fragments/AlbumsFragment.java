package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Adapters.AlbumArrayAdapter;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Musician;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.R;

/**
 * Created by Adnan on 07-May-16.
 */
public class AlbumsFragment extends Fragment {
    private Musician musician;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_albums, container, false);

        if(getArguments().containsKey("musician")) {
            musician = getArguments().getParcelable("musician");

            ArrayList<Pair<String, String>> albums = musician.getAlbums();

            ListView listView = (ListView)v.findViewById(R.id.albumsListView);
            listView.setDivider(null);
            listView.setAdapter(new AlbumArrayAdapter(getActivity(), R.layout.albumslist_item, albums));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pair<String, String> album = (Pair<String, String>)parent.getItemAtPosition(position);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(album.second));
                    startActivity(intent);
                }
            });
        }

        return v;
    }
}
