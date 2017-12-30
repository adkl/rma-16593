package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Adnan on 16-May-16.
 */
public class MusiciansResultReceiver extends ResultReceiver {

    private Receiver receiver;

    public MusiciansResultReceiver(Handler handler) {
        super(handler);
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    public void setReceiver(Receiver _receiver){
        receiver = _receiver;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(receiver != null){
            receiver.onReceiveResult(resultCode, resultData);
        }
    }
}
