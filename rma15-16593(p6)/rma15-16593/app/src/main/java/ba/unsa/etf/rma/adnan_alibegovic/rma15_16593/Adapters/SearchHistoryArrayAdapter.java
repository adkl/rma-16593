package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.R;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.SearchHistoryItem;

/**
 * Created by Adnan on 05-Jun-16.
 */
public class SearchHistoryArrayAdapter extends ArrayAdapter<SearchHistoryItem> {
    private LayoutInflater inflater;
    private int resourceID;

    public SearchHistoryArrayAdapter(Context context, int resource, List<SearchHistoryItem> objects) {
        super(context, resource, objects);
        resourceID = resource;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);

        String time = formatter.format(item.getTime());

        ((TextView)newView.findViewById(R.id.queryTimeTextView)).setText(time.concat(" h"));

        newView.setBackgroundColor(item.isSuccessful()? Color.GREEN : Color.RED);

        return newView;
    }
}
