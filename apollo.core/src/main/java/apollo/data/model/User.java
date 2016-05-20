package apollo.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import apollo.net.Cookie;
import apollo.util.CookieUtil;

public class User extends Entity {

	public static class Columns implements BaseColumns {
		public static String ID = "_id";
		public static String NAME = "name";
		public static String PASSWORD = "password";
		public static String TICKET = "ticket";
		public static String ACTIVE = "active";
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel p) {
			return new User(p);
		}
		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	private String password;
	private String ticket;
	private boolean active;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public User() {}

	public User(Parcel in) {
		super.setId(in.readInt());
		active = in.readInt() == 1;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(super.getId());
		dest.writeString(super.getName());
		dest.writeString(password);
		dest.writeString(ticket);
	}
}
