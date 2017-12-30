package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Adnan on 23-Apr-16.
 */
public class SimilarMusiciansFragment extends Fragment {
    private Musician musician;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_musician_similar_musicians, containter, false);

        if(getArguments().containsKey("musician")) {

            musician = getArguments().getParcelable("musician");

            List<Musician> musicians = musician.getSimilarMusicians();
            ListView lv = (ListView)v.findViewById(R.id.similar_musicians);
            lv.setAdapter(new MusicianArrayAdapter(getActivity().getApplicationContext(), R.layout.list_item, musicians));
        }



        return v;
    }
}
