package mykhailenko.aleks.com.serverlist.data.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ServerRealm extends RealmObject {

    @PrimaryKey
    private String gateway;
    private String countryCode;
    private String country;
    private String city;
    private boolean isConnected;
    private RealmList<RealmString> ipAddresses = null;

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public RealmList<RealmString> getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(RealmList<RealmString> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
