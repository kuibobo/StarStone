package apollo.data.model;

import android.os.Parcel;
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

		//public static final String[] USER_QUERY_COLUMNS = {ID, NAME, PASSWORD, TICKET, SELECTED};
	}

	private String password;
	private String ticket;
	private boolean isOnline;
	private boolean approved;
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
		Cookie[] cookies = null;
		int id = -1;
		String name = null;

		this.ticket = ticket;

		cookies = CookieUtil.parse(ticket);
		id = CookieUtil.getIntValue(cookies, "user", "id");
		name = CookieUtil.getValue(cookies, "user", "w");

		super.setId(id);
		super.setName(name);
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public User(Parcel in) {
		super.setId(in.readInt());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(super.getId());
		dest.writeInt(approved ? 1 : 0);
		dest.writeInt(active ? 1 : 0);
		dest.writeString(super.getName());
		dest.writeString(password);
		dest.writeString(ticket);
	}
}
