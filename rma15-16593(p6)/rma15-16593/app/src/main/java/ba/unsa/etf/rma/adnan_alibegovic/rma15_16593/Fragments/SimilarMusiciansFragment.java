package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Adapters.MusicianArrayAdapter;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Musician;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.R;

/**
 * Created by Adnan on 23-Apr-16.
 */
public class SimilarMusiciansFragment extends Fragment {
    private Musician musician;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_musician_similar_musicians, container, false);

        if(getArguments().containsKey("musician")) {

            musician = getArguments().getParcelable("musician");

            List<Musician> musicians = musician.getSimilarMusicians();
            ListView lv = (ListView)v.findViewById(R.id.similar_musicians);
            lv.setAdapter(new MusicianArrayAdapter(getActivity().getApplicationContext(), R.layout.list_item, musicians));
        }



        return v;
    }
}
