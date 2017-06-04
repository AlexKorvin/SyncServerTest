package mykhailenko.aleks.com.serverlist.realm;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import mykhailenko.aleks.com.serverlist.data.api.Server;
import mykhailenko.aleks.com.serverlist.data.realm.RealmString;
import mykhailenko.aleks.com.serverlist.data.realm.ServerRealm;

public class RealmMapper {

    public static List<ServerRealm> from(List<Server> servers) {
        List<ServerRealm> realmServices = new ArrayList<>();
        ServerRealm serverRealm;
        for (Server server : servers) {
            serverRealm = new ServerRealm();
            serverRealm.setCity(server.getCity());
            serverRealm.setCountry(server.getCountry());
            serverRealm.setCountryCode(server.getCountryCode());
            serverRealm.setGateway(server.getGateway());
            serverRealm.setIpAddresses(fromStrings(server.getIpAddresses()));

            realmServices.add(serverRealm);
        }
        return realmServices;
    }

    private static RealmList<RealmString> fromStrings(List<String> strings) {
        RealmList<RealmString> realmStrings = new RealmList<>();
        RealmString realmString;
        for (String string : strings) {
            realmString = new RealmString();
            realmString.setValue(string);
            realmStrings.add(realmString);
        }
        return realmStrings;
    }
}
