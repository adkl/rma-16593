package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.Musician;
import ba.unsa.etf.rma.adnan_alibegovic.rma15_16593.R;

/**
 * Created by ETF-LAB-1-11 on 3/28/2016.
 */
public class MusicianDetailsFragment extends Fragment {
    private Musician musician;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_musician_details, containter, false);

        if(getArguments().containsKey("musician")) {
            musician = getArguments().getParcelable("musician");

            TextView name = (TextView)v.findViewById(R.id.textViewName);
            name.setText(musician.getName());

            TextView genre = (TextView) v.findViewById(R.id.genre);
            genre.setText(musician.getGenre());

            TextView webpage = (TextView)v.findViewById(R.id.webpage);
            webpage.setText(musician.getWebUrl());

            TextView popularity = (TextView)v.findViewById(R.id.popularity);
            popularity.setText(musician.getSpotifyPopularity().toString());

            TextView followers = (TextView)v.findViewById(R.id.followers);
            followers.setText(musician.getSpotifyFollowers().toString());

            webpage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(musician.getWebUrl()));
                    startActivity(intent);
                }
            });

            Button imageButton = (Button)v.findViewById(R.id.imageButton);
            imageButton.setBackgroundResource(R.mipmap.share_icon);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, musician.getBiography());
                    intent.setType("text/plain");
                    startActivity(intent);
                }
            });


            //setting arguments
            Bundle args = new Bundle();
            args.putParcelable("musician", musician);

            //nested fragments
            final TopSongsFragment topSongsFragment = new TopSongsFragment();
            topSongsFragment.setArguments(args);

            final SimilarMusiciansFragment similarMusiciansFragment = new SimilarMusiciansFragment();
            similarMusiciansFragment.setArguments(args);

            final AlbumsFragment albumsFragment = new AlbumsFragment();
            albumsFragment.setArguments(args);

            //set top5Songs to be default fragment
            getChildFragmentManager().beginTransaction()
                                .replace(R.id.songs_similar_musicians_frame, topSongsFragment)
                                .commit();



            final Button topSongsButton = (Button)v.findViewById(R.id.topSongsButton);
            final Button similarMusiciansButton = (Button)v.findViewById(R.id.similarMusiciansButton);
            final Button albumsButton = (Button)v.findViewById(R.id.albumsButton);

            topSongsButton.setTextColor(getResources().getColor(R.color.blue));

            topSongsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.songs_similar_musicians_frame, topSongsFragment)
                            .commit();
                    topSongsButton.setTextColor(Color.BLUE);
                    similarMusiciansButton.setTextColor(Color.BLACK);
                    albumsButton.setTextColor(Color.BLACK);
                }
            });

            similarMusiciansButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.songs_similar_musicians_frame, similarMusiciansFragment)
                            .commit();

                    topSongsButton.setTextColor(Color.BLACK);
                    similarMusiciansButton.setTextColor(Color.BLUE);
                    albumsButton.setTextColor(Color.BLACK);
                }
            });


            albumsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.songs_similar_musicians_frame, albumsFragment)
                            .commit();

                    topSongsButton.setTextColor(Color.BLACK);
                    similarMusiciansButton.setTextColor(Color.BLACK);
                    albumsButton.setTextColor(Color.BLUE);
                }
            });


        }

        return v;
    }
}
