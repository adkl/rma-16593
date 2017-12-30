package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Adnan on 20-Mar-16.
 */
public class InternetConnectionBroadcastReceiver extends BroadcastReceiver {
    private static boolean connection = true;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        if(isConnected) {
            Toast.makeText(context, "You are connected to the internet!", Toast.LENGTH_SHORT).show();
            connection = true;
        }
        else {
            if(connection == true)
                Toast.makeText(context, "You have lost the internet connection!", Toast.LENGTH_SHORT).show();
            connection = false;
        }
    }

}
