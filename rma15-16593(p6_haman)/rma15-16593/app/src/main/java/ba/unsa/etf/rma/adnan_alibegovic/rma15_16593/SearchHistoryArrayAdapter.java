package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Adnan on 05-Jun-16.
 */
public class SearchHistoryArrayAdapter extends ArrayAdapter<SearchHistoryItem> {
    private LayoutInflater inflater;
    private int resourceID;

    public SearchHistoryArrayAdapter(Context context, int resource, List<SearchHistoryItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LinearLayout newView;
        if(convertView == null) {
            newView = new LinearLayout(getContext());
            inflater.inflate(resourceID, newView, true);
        }
        else {
            newView = (LinearLayout)convertView;
        }
        newView = new LinearLayout(getContext());
        inflater.inflate(resourceID, newView, true);

        SearchHistoryItem item = getItem(position);

        ((TextView)newView.findViewById(R.id.searchQueryTextView)).setText(item.getQuery());
        ((TextView)newView.findViewById(R.id.queryTimeTextView)).setText(item.getTime().toString().concat(" h"));

        newView.setBackgroundColor(item.isSuccessful()? Color.GREEN : Color.RED);

        return newView;
    }
}
