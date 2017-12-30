package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adnan on 23-Apr-16.
 */
public class TopSongsFragment extends Fragment {
    private Musician musician;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_musician_top_songs, containter, false);

        if(getArguments().containsKey("musician")) {
            musician = getArguments().getParcelable("musician");

            List<String> songs = new ArrayList<>(musician.getTop5Songs());

            ListView lv_songs = (ListView) v.findViewById(R.id.songs);
            lv_songs.setDivider(null);
            lv_songs.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.songslist_item, R.id.songName, songs));

            lv_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent ytIntent = new Intent(Intent.ACTION_SEARCH);
                    ytIntent.setPackage("com.google.android.youtube");
                    ytIntent.putExtra("query", parent.getItemAtPosition(position).toString() + " " + musician.getName());
                    ytIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ytIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(ytIntent);
                    } else {
                        Toast.makeText(getActivity(), "Instalirajte YouTube aplikaciju.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return v;
    }
}
