package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Fragments;

import android.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.InternetConnectionBroadcastReceiver;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Musician;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.MusicianDBOpenHelper;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.MusicianService;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.MusiciansResultReceiver;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.R;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.SearchHistoryItem;

/**
 * Created by ETF-LAB-1-11 on 3/28/2016.
 */
public class MainScreenFragment extends Fragment implements SearchHistoryFragment.OnSearchHistoryItemClick {
    private MusiciansListFragment musiciansListFragment;
    private SearchHistoryFragment searchHistoryFragment;
    private ArrayList<SearchHistoryItem> historyItems;
    private ArrayList<Musician> musicians;
    private EditText searchInput;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_screen, container, false);

        musicians = getArguments().getParcelableArrayList("musicians");

        final Button searchButton = (Button)v.findViewById(R.id.searchMusiciansButton);
        searchButton.setEnabled(false);
        final Button historyButton = (Button)v.findViewById(R.id.historyButton);
        searchInput = (EditText)v.findViewById(R.id.input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0) {
                    searchButton.setEnabled(false);
                }
                else {
                    searchButton.setEnabled(true);
                }
            }
        });


        musiciansListFragment = new MusiciansListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("musicians", musicians);
        musiciansListFragment.setArguments(args);

        //set the musicians list to be default fragment
        getChildFragmentManager().beginTransaction()
                .replace(R.id.musicians_list_search_history_frame, musiciansListFragment)
                .commit();

        searchHistoryFragment = new SearchHistoryFragment();

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getChildFragmentManager().findFragmentByTag("searchHistoryFragment") != null) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.musicians_list_search_history_frame, musiciansListFragment)
                            .commit();
                }
                else {

                    MusicianDBOpenHelper helper = MusicianDBOpenHelper.getInstance(getActivity().getApplicationContext());
                    historyItems = helper.getSearchHistory();
                    Bundle historyArgs = new Bundle();
                    historyArgs.putParcelableArrayList("searchHistory", historyItems);
                    searchHistoryFragment.setArguments(historyArgs);

                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.musicians_list_search_history_frame, searchHistoryFragment, "searchHistoryFragment")
                            .commit();
                }

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(((Button)v.findViewById(R.id.searchMusiciansButton)).getWindowToken(), 0);

                runSearch(searchInput.getText().toString());

            }
        });

        return v;
    }


    @Override
    public void onSearchHistoryItemClicked(int pos) {
        SearchHistoryItem item = historyItems.get(pos);
        searchInput.setText(item.getQuery());
        runSearch(item.getQuery());
    }

    private void runSearch(String query) {
        if(InternetConnectionBroadcastReceiver.isConnected(getActivity())) {
            Intent serviceIntent = new Intent(Intent.ACTION_SYNC, null, getActivity(), MusicianService.class);
            serviceIntent.putExtra("query", query);

            MusiciansResultReceiver receiver = new MusiciansResultReceiver(new Handler());
            receiver.setReceiver(musiciansListFragment);

            serviceIntent.putExtra("receiver", receiver);

            getActivity().startService(serviceIntent);

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.musicians_list_search_history_frame, musiciansListFragment)
                    .commit();
        }
        else {
            Toast.makeText(getActivity(), "Nema internet konekcije, pretraga ce biti spasena u listu pretraga", Toast.LENGTH_LONG).show();
            SearchHistoryItem item = new SearchHistoryItem(query,
                    new Date(),
                    false);

            MusicianDBOpenHelper helper = MusicianDBOpenHelper.getInstance(getActivity());
            helper.addSearchHistoryItem(item);

            if(getChildFragmentManager().findFragmentByTag("searchHistoryFragment") != null) {
                SearchHistoryFragment fragment = (SearchHistoryFragment)getChildFragmentManager().findFragmentByTag("searchHistoryFragment");
                fragment.reloadData();
            }

        }
    }
}
