package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Adnan on 30-May-16.
 */
public class MusicianCursorAdapter extends ResourceCursorAdapter {


    public MusicianCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView)view.findViewById(R.id.textViewMusicianName);
        TextView tvGenre = (TextView)view.findViewById(R.id.textViewGenre);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageViewGenreIcon);

        tvName.setText(cursor.getString(cursor.getColumnIndex(MusicianDBOpenHelper.MUSICIAN_NAME)));

        String genre = cursor.getString(cursor.getColumnIndex(MusicianDBOpenHelper.MUSICIAN_GENRE));
        tvGenre.setText(genre);

        imageView.setImageResource(MusicianArrayAdapter.getImageResourceByGenre(genre));
    }
}
