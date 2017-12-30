package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Adnan on 06-Jun-16.
 */
public class SearchHistoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_history, container, false);

        if(getArguments().containsKey("searchHistory")) {
            ArrayList<SearchHistoryItem> historyItems = getArguments().getParcelableArrayList("searchHistory");

            ((ListView)v.findViewById(R.id.searchHistoryListView))
                .setAdapter(new SearchHistoryArrayAdapter(getActivity(), R.layout.search_history_list_item, historyItems));
        }

        return v;
    }
}
