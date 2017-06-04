package mykhailenko.aleks.com.serverlist.data.realm;

import io.realm.RealmObject;

public class RealmString extends RealmObject {

    public static final String VALUE = "value";
    private String value;

    public RealmString() {
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
