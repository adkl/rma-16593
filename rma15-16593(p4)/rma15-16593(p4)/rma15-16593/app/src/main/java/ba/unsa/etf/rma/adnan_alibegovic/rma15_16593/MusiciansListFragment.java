package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ETF-LAB-1-11 on 3/28/2016.
 */
public class MusiciansListFragment extends Fragment implements SearchMusician.OnSearchMusicianDone {
    private OnMusicianItemClick onMusicianItemClick;
    private ArrayList<Musician> musicians;
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_musicians_list, container, false);

        musicians = getArguments().getParcelableArrayList("musicians");

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

        final EditText searchInput = (EditText)v.findViewById(R.id.input);

        ((Button)v.findViewById(R.id.searchMusiciansButton))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new SearchMusician((SearchMusician.OnSearchMusicianDone) MusiciansListFragment.this)
                                .execute(searchInput.getText()
                                        .toString());

                    }
                });

        return v;
    }
    @Override
    public void onDone(ArrayList<Musician> _musicians) {
        //((MusicianArrayAdapter)listView.getAdapter()).clear();
        musicians.clear();
        for(Musician m : _musicians)
            musicians.add(m);
        ((MusicianArrayAdapter)listView.getAdapter()).notifyDataSetChanged();

        //listView.setAdapter(new MusicianArrayAdapter(getActivity(), R.layout.list_item, musicians));
    }

    public interface OnMusicianItemClick{
        public void onMusicianItemClicked(int pos);
    }
}
