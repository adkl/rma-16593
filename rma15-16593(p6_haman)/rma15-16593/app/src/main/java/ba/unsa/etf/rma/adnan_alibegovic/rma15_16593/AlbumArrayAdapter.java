package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Adnan on 07-May-16.
 */
public class AlbumArrayAdapter extends ArrayAdapter<Pair<String, String>> {
    private LayoutInflater inflater;
    int resourceID;

    public AlbumArrayAdapter(Context context, int _resource, List<Pair<String, String>> _albums) {
        super(context, _resource, _albums);

        resourceID = _resource;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        TextView albumName = (TextView)newView.findViewById(R.id.albumName);
        Pair<String, String> album = getItem(position);
        albumName.setText(album.first);

        return newView;
    }
}
