package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Adapters.SearchHistoryArrayAdapter;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.MusicianDBOpenHelper;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.R;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.SearchHistoryItem;

/**
 * Created by Adnan on 06-Jun-16.
 */
public class SearchHistoryFragment extends Fragment {
    private OnSearchHistoryItemClick onSearchHistoryItemClick;
    ArrayList<SearchHistoryItem> historyItems;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_history, container, false);

        if(getArguments().containsKey("searchHistory")) {
            historyItems = getArguments().getParcelableArrayList("searchHistory");

            listView = ((ListView)v.findViewById(R.id.searchHistoryListView));


            listView.setAdapter(new SearchHistoryArrayAdapter(getActivity(), R.layout.search_history_list_item, historyItems));

            try {
                onSearchHistoryItemClick = (OnSearchHistoryItemClick)getParentFragment();
            }catch (ClassCastException e) {
                throw new ClassCastException(getActivity().toString() + "Treba implementirati OnSearchHistoryItemClick");
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onSearchHistoryItemClick.onSearchHistoryItemClicked(position);
                }
            });
        }

        return v;
    }

    public void reloadData(){
        MusicianDBOpenHelper helper = MusicianDBOpenHelper.getInstance(getActivity().getApplicationContext());
        historyItems = helper.getSearchHistory();
        listView.setAdapter(new SearchHistoryArrayAdapter(getActivity(), R.layout.search_history_list_item, historyItems));
    }

    public interface OnSearchHistoryItemClick{
        public void onSearchHistoryItemClicked(int pos);
    }

}
