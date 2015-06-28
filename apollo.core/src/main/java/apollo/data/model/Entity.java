package apollo.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by bourne on 2015/6/27.
 */
public abstract class Entity implements Serializable, Parcelable {

    private int id;
    private String guid;
    private String name;
    private String body;
    private String icon;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Entity() {

    }

    public Entity(Parcel in) {
        id = in.readInt();
        guid = in.readString();
        name = in.readString();
        body = in.readString();
        url = in.readString();
        icon = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(guid);
        dest.writeString(name);
        dest.writeString(body);
        dest.writeString(url);
        dest.writeString(icon);
    }
}
