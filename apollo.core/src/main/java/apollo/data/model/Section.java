package apollo.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class Section extends Entity {

	public static class Columns implements BaseColumns {
		public static String ID = "_id";
		public static String NAME = "name";
	}
	
    public static final Parcelable.Creator<Section> CREATOR = new Parcelable.Creator<Section>() {
		@Override
		public Section createFromParcel(Parcel p) {
			return new Section(p);
		}
		@Override
		public Section[] newArray(int size) {
			return new Section[size];
		}
    };

	public Section() {}

    
    public Section(Parcel in) {
    	super(in);
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
	}
	
	
}
