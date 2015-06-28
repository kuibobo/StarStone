package apollo.data.model;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by bourne on 2015/6/28.
 */
public class ExtendedAttributes implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Properties props;

    public ExtendedAttributes() {
        props = new Properties();
    }
    public ExtendedAttributes(String json) {
        Gson gson = new Gson();
        ExtendedAttributes attrs = null;

        attrs = gson.fromJson(json, ExtendedAttributes.class);

        this.props = attrs.props;
    }

    public boolean getBooleanExtendedAttribute(String name) {
        String value = props.getProperty(name);
        boolean ret = false;

        if (!TextUtils.isEmpty(value)) {
            ret = Boolean.parseBoolean(value);
        }
        return ret;
    }

    public int getIntExtendedAttribute(String name) {
        String value = props.getProperty(name);
        int ret = 0;

        if (!TextUtils.isEmpty(value)) {
            ret = Integer.parseInt(value);
        }
        return ret;
    }

    public String getExtendedAttribute(String name) {
        return props.getProperty(name);
    }

    public void setExtendedAttribute(String name, int value) {
        setExtendedAttribute(name, Integer.toString(value));
    }

    public void setExtendedAttribute(String name, boolean value) {
        setExtendedAttribute(name, Boolean.toString(value));
    }

    public void setExtendedAttribute(String name, String value) {
        if (name.length() == 0 || value == null) {
            props.remove(name);
        } else {
            props.put(name, value);
        }
    }

    public int getExtendedAttributesCount() {
        return props.size();
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(props, props.getClass());
    }

    protected boolean getBool(String name, boolean defaultValue) {
        String value = getExtendedAttribute(name);

        if (value == null) {
            return defaultValue;
        }

        return Boolean.parseBoolean(value);
    }

    protected int getInt(String name, int defaultValue) {
        String value = getExtendedAttribute(name);

        if (value == null) {
            return defaultValue;
        }

        return Integer.parseInt(value);
    }

    protected String getString(String name, String defaultValue) {
        String value = getExtendedAttribute(name);

        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public int getSize() {
        return props.size();
    }

    public Collection getKeys() {
        return Collections.list(props.keys());
    }
}
