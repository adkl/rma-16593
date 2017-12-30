package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Musician> musicians = new ArrayList<>();

    public void seedData() {

        ArrayList<String> zeljkoSongs = new ArrayList<>();
        zeljkoSongs.add("Lane moje");
        zeljkoSongs.add("Ledja o ledja");
        zeljkoSongs.add("Ljubavi");
        zeljkoSongs.add("Telo vreteno");
        zeljkoSongs.add("Libero");
        zeljkoSongs.add("Devojka sa polja zelenih");

        ArrayList<String> cockerSongs = new ArrayList<>();
        cockerSongs.add("Unchain my heart");
        cockerSongs.add("N'oubliez Jamais");
        cockerSongs.add("You are so beautiful");
        cockerSongs.add("Up where we belong");
        cockerSongs.add("When the night comes");

        ArrayList<String> eminemSongs = new ArrayList<>();
        eminemSongs.add("Cleaning out my closet");
        eminemSongs.add("Mockingbird");
        eminemSongs.add("When I'm gone");
        eminemSongs.add("The monster");
        eminemSongs.add("Not afraid");

        String zeljkoBiography = "Željko Joksimović, often credited as Zeljko Joksimovic, born 20 April 1972) is a Serbian singer, composer songwriter, " +
                "multi-instrumentalist and producer. He plays 12 different musical instruments including accordion, piano, guitar, and drums. " +
                "Joksimović is multi-lingual, being fluent in Greek, English, Russian, Polish and French as well as his native Serbian.";

        String cockerBiography = "John Robert Cocker, OBE (20 May 1944 – 22 December 2014) was an English singer and musician. " +
                "He was known for his gritty voice, spasmodic body movement in performance and definitive versions of popular songs.";

        String eminemBiography = "Marshall Bruce Mathers III (born October 17, 1972)," +
                " known professionally as Eminem, is an American rapper, songwriter, record producer, and actor from Detroit, Michigan. " +
                "In addition to his solo career, he is a member of D12 and half of the hip-hop duo Bad Meets Evil. " +
                "Eminem is the best-selling artist of the 2000s in the United States.";

        musicians.add(new Musician("Zeljko Joksimovic", "Pop", "http://www.zeljko.com", zeljkoBiography, zeljkoSongs));
        musicians.add(new Musician("Joe Cocker", "Rock", "http://www.joecocker.com", cockerBiography, cockerSongs));
        musicians.add(new Musician("Eminem", "Rap", "http://www.eminem.com", eminemBiography, eminemSongs));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region answersOnActionSend
        Intent intent = getIntent();
        if(intent.getAction() == Intent.ACTION_SEND ) {

            if("text/plain".equals(intent.getType())) {
                String text = intent.getStringExtra(Intent.EXTRA_TEXT);
                EditText editText = (EditText) findViewById(R.id.input);
                editText.setText(text);
            }
        }
        //endregion

        seedData();

        ListView lv = (ListView) findViewById(R.id.listView);

        MusicianArrayAdapter adapter = new MusicianArrayAdapter(this, R.layout.list_item, musicians);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Musician musician = (Musician) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), MusicianActivity.class);
                intent.putExtra("musician", musician);
                startActivity(intent);
            }
        });


    }


}
