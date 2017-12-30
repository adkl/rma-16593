package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Adnan on 18-Mar-16.
 */
public class MusicianArrayAdapter extends ArrayAdapter<Musician> {
    private LayoutInflater inflater;
    int resourceID;

    public MusicianArrayAdapter(Context context, int _resource, List<Musician> musicians) {
        super(context, _resource, musicians);
        resourceID = _resource;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getImageResourseByGenre(String genre) {
        switch(genre) {
            case "Rock" :
                return R.drawable.rock;
            case "Pop" :
                return R.drawable.pop;
            case "Rap":
                return R.drawable.rap;
            default:
                return -1;
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
        Musician musician = getItem(position);

        ImageView imageView = (ImageView) newView.findViewById(R.id.imageViewGenreIcon);
        TextView genre = (TextView) newView.findViewById(R.id.textViewGenre);
        TextView musicianName = (TextView) newView.findViewById(R.id.textViewMusicianName);

        imageView.setImageResource(getImageResourseByGenre(musician.getGenre()));
        genre.setText(musician.getGenre());
        musicianName.setText(musician.getName());

        return newView;
    }
}
