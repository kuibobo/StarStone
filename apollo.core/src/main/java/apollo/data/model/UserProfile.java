package apollo.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by bourne on 2015/6/28.
 */
public class UserProfile extends ExtendedAttributes implements Parcelable, Serializable {

    private static final long serialVersionUID = 1727852856401416380L;

    private int id;


    public static final Parcelable.Creator<UserProfile> CREATOR = new Parcelable.Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel p) {
            return new UserProfile(p);
        }
        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    public UserProfile() {
    }

    public UserProfile(Parcel in) {
        id = in.readInt();
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }
}
