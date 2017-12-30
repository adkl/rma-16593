package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Musician;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.R;

/**
 * Created by Adnan on 18-Mar-16.
 */
public class MusicianArrayAdapter extends ArrayAdapter<Musician> {
    private LayoutInflater inflater;
    int resourceID;

    public MusicianArrayAdapter(Context context, int _resource, List<Musician> _musicians) {
        super(context, _resource, _musicians);
        resourceID = _resource;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getImageResourceByGenre(String genre) {
        switch(genre.toLowerCase()) {
            case "rock" :
                return R.drawable.ic_rock;
            case "yugoslav rock" :
                return R.drawable.ic_rock;
            case "art rock" :
                return R.drawable.ic_rock;
            case "album rock" :
                return R.drawable.ic_rock;
            case "glam rock" :
                return R.drawable.ic_rock;
            case "pop" :
                return R.drawable.ic_pop;
            case "croatian pop" :
                return R.drawable.ic_pop;
            case "europop" :
                return R.drawable.ic_pop;
            case "dance pop" :
                return R.drawable.ic_pop;
            case "rap":
                return R.drawable.ic_rap;
            case "turbo folk":
                return R.drawable.ic_turbo_folk;
            case "soul":
                return R.drawable.ic_soul;
            case "disco":
                return R.drawable.ic_disco;
            default:
                return R.drawable.ic_default;
        }
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
        Musician musician = getItem(position);

        ImageView imageView = (ImageView) newView.findViewById(R.id.imageViewGenreIcon);
        TextView genre = (TextView) newView.findViewById(R.id.textViewGenre);
        TextView musicianName = (TextView) newView.findViewById(R.id.textViewMusicianName);

        imageView.setImageResource(getImageResourceByGenre(musician.getGenre()));
        genre.setText(musician.getGenre());
        musicianName.setText(musician.getName());

        return newView;
    }
}
