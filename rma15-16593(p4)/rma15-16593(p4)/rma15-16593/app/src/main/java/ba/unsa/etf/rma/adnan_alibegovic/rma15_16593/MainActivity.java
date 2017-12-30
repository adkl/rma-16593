package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MusiciansListFragment.OnMusicianItemClick {
    private boolean wideLayout;
    private ArrayList<Musician> musicians = new ArrayList<>();

    public void seedData() {

        ArrayList<String> zeljkoSongs = new ArrayList<>();
        zeljkoSongs.add("Lane moje");
        zeljkoSongs.add("Ledja o ledja");
        zeljkoSongs.add("Ljubavi");
        zeljkoSongs.add("Telo vreteno");
        zeljkoSongs.add("Libero");
        zeljkoSongs.add("Devojka sa polja zelenih");

        ArrayList<String> toseSongs = new ArrayList<>();
        toseSongs.add("Igra bez granica");
        toseSongs.add("Boze brani je od zla");
        toseSongs.add("Lose ti stoji");
        toseSongs.add("Pratim te");
        toseSongs.add("Jedina");
        toseSongs.add("Soba za tugu");

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

        String toseBiography = "Todor \"Toše\" Proeski (Macedonian: Тодор \"Тоше\" Проески [ˈtɔʃɛ ˈprɔɛski] " +
                "( listen); 25 January 1981 – 16 October 2007) was a Macedonian multi-genre singer, songwriter and actor."+
                "He was popular across the entire Balkan area and was considered a top act of the local Macedonian music scene. ";

        musicians.add(new Musician("Zeljko Joksimovic", "Pop", "http://www.zeljko.com", zeljkoBiography, zeljkoSongs));
        musicians.add(new Musician("Joe Cocker", "Rock", "http://www.joecocker.com", cockerBiography, cockerSongs));
        musicians.add(new Musician("Eminem", "Rap", "http://www.eminem.com", eminemBiography, eminemSongs));
        musicians.add(new Musician("Tose Proeski", "Pop", "http://www.tose.com", toseBiography, toseSongs));
        musicians.get(0).setMusicians(musicians);
        musicians.get(1).setMusicians(musicians);
        musicians.get(2).setMusicians(musicians);
        musicians.get(3).setMusicians(musicians);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //seedData();

        if(savedInstanceState != null) {
            if(savedInstanceState.getParcelableArrayList("musicians") != null) {
                musicians = savedInstanceState.getParcelableArrayList("musicians");
            }
        }


        //looking for wide or normal layout
        wideLayout = (FrameLayout)findViewById(R.id.musician_details_frame) != null ? true : false;

        FragmentManager fragmentManager = getFragmentManager();
        if(wideLayout && musicians.size() > 0) {
            if(fragmentManager.findFragmentById(R.id.musician_details_frame) == null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MusicianDetailsFragment detailsFragment = new MusicianDetailsFragment();
                Bundle args = new Bundle();
                args.putParcelable("musician", musicians.get(0)); // we don't want that Details fragment is empty at the beginning
                detailsFragment.setArguments(args);
                fragmentTransaction.replace(R.id.musician_details_frame, detailsFragment)
                                   .commit();
            }
        }

        if(fragmentManager.findFragmentById(R.id.musicians_list_frame) == null) {
            MusiciansListFragment musiciansFragment = new MusiciansListFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList("musicians", musicians);
            musiciansFragment.setArguments(args);

            fragmentManager.beginTransaction()
                           .replace(R.id.musicians_list_frame, musiciansFragment)
                           .commit();
        }
        else {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            //pitati da objasni...
        }

        //region answersOnActionSend
//        Intent intent = getIntent();
//        if(intent.getAction() == Intent.ACTION_SEND ) {
//
//            if("text/plain".equals(intent.getType())) {
//                String text = intent.getStringExtra(Intent.EXTRA_TEXT);
//                EditText editText = (EditText) findViewById(R.id.input);
//                editText.setText(text);
//            }
//        }
        //endregion




    }

    @Override
    public void onMusicianItemClicked(int pos) {
        Bundle args = new Bundle();
        args.putParcelable("musician", musicians.get(pos));
        MusicianDetailsFragment detailsFragment = new MusicianDetailsFragment();
        detailsFragment.setArguments(args);
        if(wideLayout) {
            getFragmentManager().beginTransaction()
                                .replace(R.id.musician_details_frame, detailsFragment)
                                .commit();


        }
        else {
            getFragmentManager().beginTransaction()
                                .replace(R.id.musicians_list_frame, detailsFragment)
                                .addToBackStack(null)
                                .commit();
        }
    }
    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryCount() != 0){
            getFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("musicians", musicians);
    }


}
