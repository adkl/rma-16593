package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ETF-LAB-1-11 on 3/28/2016.
 */
public class MusiciansListFragment extends Fragment implements SearchMusician.OnSearchMusicianDone, MusiciansResultReceiver.Receiver {
    private OnMusicianItemClick onMusicianItemClick;
    private ArrayList<Musician> musicians;
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_musicians_list, container, false);

        musicians = getArguments().getParcelableArrayList("musicians");

        //<------ CURSOR ( bolje bi bilo da je kod za CRUD operacije u MusicianDBOpenHelperu, ali sada samo demonstriramo )
        MusicianDBOpenHelper dbOpenHelper = new MusicianDBOpenHelper(getActivity());
        String[] columns = new String[]{dbOpenHelper.MUSICIAN_ID, dbOpenHelper.MUSICIAN_NAME, dbOpenHelper.MUSICIAN_GENRE};
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(dbOpenHelper.DB_TABLE, columns, null, null, null ,null, null, null);
        //------>

        ListView lv = (ListView)v.findViewById(R.id.listView);
        listView = lv;
        //lv.setAdapter(new MusicianArrayAdapter(getActivity(), R.layout.list_item, musicians));
        lv.setAdapter(new MusicianCursorAdapter(getActivity(), R.layout.list_item, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));

        try {
            onMusicianItemClick = (OnMusicianItemClick)getActivity();
        }
        catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "Treba implementirati OnItemClick");
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onMusicianItemClick.onMusicianItemClicked(position);
            }
        });

        final EditText searchInput = (EditText)v.findViewById(R.id.input);

        ((Button)v.findViewById(R.id.searchMusiciansButton))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(((Button)v.findViewById(R.id.searchMusiciansButton)).getWindowToken(), 0);

                        // OVO SMO KORISTILI PRIJE
//                        new SearchMusician((SearchMusician.OnSearchMusicianDone) MusiciansListFragment.this)
//                                .execute(searchInput.getText()
//                                        .toString());

                        // preko intent servisa
                        // trajace malo duze jer radimo 3 requesta, jedan za muzicare i po jedan za top songs i albums
                        // improvement za ovaj task bi bio da se tek na odabir muzicara tek pucaju ova 2 requesta, sto bi malo ublazilo ovo cekanje

                        Intent serviceIntent = new Intent(Intent.ACTION_SYNC, null, getActivity(), MusicianService.class);
                        serviceIntent.putExtra("query", searchInput.getText().toString());

                        MusiciansResultReceiver receiver = new MusiciansResultReceiver(new Handler());
                        receiver.setReceiver(MusiciansListFragment.this);

                        serviceIntent.putExtra("receiver", receiver);

                        getActivity().startService(serviceIntent);


                    }
                });

        return v;
    }
    @Override
    public void onDone(ArrayList<Musician> _musicians) { //prakticno se ova metoda vise ne koristi ali smo je ostavili jer je funkcionalna u slucaju potrebe
        musicians.clear();
        for(Musician m : _musicians)
            musicians.add(m);
        ((MusicianArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode){
            case MusicianService.STATUS_RUNNING:
                Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_LONG).show();
                break;
            case MusicianService.STATUS_FINISHED:
                musicians.clear();
                ArrayList<Musician> _musicians = resultData.getParcelableArrayList("musicians");
                for(Musician m : _musicians){
                    musicians.add(m);
                }
                ((MusicianArrayAdapter)listView.getAdapter()).notifyDataSetChanged();
                break;
            case MusicianService.STATUS_ERROR:
                Toast.makeText(getActivity(), resultData.getString("error"), Toast.LENGTH_LONG).show();
                break;
        }
    }

    public interface OnMusicianItemClick{
        public void onMusicianItemClicked(int pos);
    }
}
