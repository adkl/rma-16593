package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MusicianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician);

        final Musician musician = (Musician) getIntent().getSerializableExtra("musician");

        TextView name = (TextView)findViewById(R.id.textViewName);
        name.setText(musician.getName());

        TextView genre = (TextView) findViewById(R.id.genre);
        genre.setText(musician.getGenre());

        TextView webpage = (TextView)findViewById(R.id.webpage);
        webpage.setText(musician.getWebUrl());

        TextView biography = (TextView)findViewById(R.id.biography);
        biography.setText(musician.getBiography());

        webpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(musician.getWebUrl()));
                startActivity(intent);
            }
        });


        List<String> songs =  new ArrayList<>(musician.getTop5Songs());

        ListView lv_songs = (ListView)findViewById(R.id.songs);
        lv_songs.setDivider(null);
        lv_songs.setAdapter(new ArrayAdapter<>(this, R.layout.songslist_item, R.id.songName, songs));

        lv_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ytIntent = new Intent(Intent.ACTION_SEARCH);
                ytIntent.setPackage("com.google.android.youtube");
                ytIntent.putExtra("query", parent.getItemAtPosition(position).toString() + " " + musician.getName());
                ytIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(ytIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(ytIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Instalirajte YouTube aplikaciju.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button imageButton = (Button)findViewById(R.id.imageButton);
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


    }


}
