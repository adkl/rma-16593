package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Adapters.MusicianArrayAdapter;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Musician;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.MusicianDBOpenHelper;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.MusicianService;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.MusiciansResultReceiver;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.R;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.SearchHistoryItem;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.SearchMusician;

/**
 * Created by Adnan on 06-Jun-16.
 */
public class MusiciansListFragment extends Fragment implements SearchMusician.OnSearchMusicianDone, MusiciansResultReceiver.Receiver {
    private OnMusicianItemClick onMusicianItemClick;
    private ArrayList<Musician> musicians;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_musicians_list, container, false);

        if(getArguments().containsKey("musicians")) {
            musicians = getArguments().getParcelableArrayList("musicians");
        }

        ListView lv = (ListView)v.findViewById(R.id.listView);
        listView = lv;
        lv.setAdapter(new MusicianArrayAdapter(getActivity(), R.layout.list_item, musicians));

        try {
            onMusicianItemClick = (OnMusicianItemClick)getActivity();
        }
        catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "Treba implementirati OnItemClick");
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onMusicianItemClick.onMusicianItemClicked(position);
            }
        });

        return v;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode){
            case MusicianService.STATUS_RUNNING:
                Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_LONG).show();
                break;
            case MusicianService.STATUS_FINISHED:

                ArrayList<Musician> _musicians = resultData.getParcelableArrayList("musicians");

                if(_musicians == null || _musicians.size() == 0) {
                    Toast.makeText(getActivity(), "Sorry, but we can't find any musicians with the entered name :(", Toast.LENGTH_LONG).show();
                }
                else {
                    musicians.clear();
                    for(Musician m : _musicians){
                        musicians.add(m);
                    }
                    ((MusicianArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
                }
                String query = resultData.getString("query");
                SearchHistoryItem item = new SearchHistoryItem(query, new Date(), true);
                MusicianDBOpenHelper helper = MusicianDBOpenHelper.getInstance(getActivity());
                helper.addSearchHistoryItem(item);
                break;
            case MusicianService.STATUS_ERROR:
                Toast.makeText(getActivity(), resultData.getString("error"), Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onDone(ArrayList<Musician> _musicians) { //prakticno se ova metoda vise ne koristi ali smo je ostavili jer je funkcionalna u slucaju potrebe
        musicians.clear();
        for(Musician m : _musicians)
            musicians.add(m);
        ((MusicianArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
    }

    public interface OnMusicianItemClick{
        public void onMusicianItemClicked(int pos);
    }
}
