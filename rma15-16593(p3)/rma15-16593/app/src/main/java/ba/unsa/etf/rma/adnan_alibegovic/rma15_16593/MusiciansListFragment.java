package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ETF-LAB-1-11 on 3/28/2016.
 */
public class MusiciansListFragment extends Fragment {
    private OnMusicianItemClick onMusicianItemClick;
    private ArrayList<Musician> musicians = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_musicians_list, container, false);

        if(getArguments().containsKey("musicians")) {
            musicians = getArguments().getParcelableArrayList("musicians");

            ListView lv = (ListView)v.findViewById(R.id.listView);
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
        }



        return v;
    }

    public interface OnMusicianItemClick{
        public void onMusicianItemClicked(int pos);
    }
}
