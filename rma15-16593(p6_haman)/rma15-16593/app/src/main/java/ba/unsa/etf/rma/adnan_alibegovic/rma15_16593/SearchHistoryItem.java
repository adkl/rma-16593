package ba.unsa.etf.rma.adnan_alibegovic.rma15_16593;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Adnan on 05-Jun-16.
 */
public class SearchHistoryItem implements Parcelable, Serializable {

    private String query;
    private Date time;
    private boolean successful;

    protected SearchHistoryItem(Parcel in) {
        query = in.readString();
        time = new Date(in.readLong());
        successful = in.readByte() != 0;
    }

    public SearchHistoryItem(String _query, Date _time, boolean _successful) {
        query = _query;
        time = _time;
        successful = _successful;
    }

    public static final Creator<SearchHistoryItem> CREATOR = new Creator<SearchHistoryItem>() {
        @Override
        public SearchHistoryItem createFromParcel(Parcel in) {
            return new SearchHistoryItem(in);
        }

        @Override
        public SearchHistoryItem[] newArray(int size) {
            return new SearchHistoryItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(query);
        dest.writeLong(time.getTime());
        dest.writeByte((byte) (successful ? 1 : 0));
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
