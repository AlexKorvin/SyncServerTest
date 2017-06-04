package mykhailenko.aleks.com.serverlist.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import mykhailenko.aleks.com.serverlist.data.realm.ServerRealm;

public class RealmHelper {

    public static void updateAvailableServers(final List<ServerRealm> serverRealms) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<ServerRealm> serversRealm = realm.where(ServerRealm.class).findAll();
                    ServerRealm connectedServer = realm.where(ServerRealm.class).equalTo("isConnected", true).findFirst();
                    String gateWayId = connectedServer != null ? connectedServer.getGateway() : null;
                    for (ServerRealm serverRealm : serversRealm) {
                        serverRealm.getIpAddresses().deleteAllFromRealm();
                    }
                    serversRealm.deleteAllFromRealm();
                    for (ServerRealm server : serverRealms) {
                        if (gateWayId != null && server.getGateway().equals(gateWayId))
                            server.setConnected(true);
                        realm.insertOrUpdate(server);
                    }
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    public static RealmResults<ServerRealm> getAvailableServers(Realm realm) {
        RealmResults<ServerRealm> results;
        results = realm.where(ServerRealm.class).findAll();

        return results;
    }

    public static String getConnectedServer(Realm realm) {
        String serverId;
        if (!hasServers(realm)) return null;
        ServerRealm serverRealm = realm.where(ServerRealm.class).equalTo("isConnected", true).findFirst();
        serverId = serverRealm != null ? serverRealm.getGateway() : null;
        if (serverId == null)
            serverId = setFirstAsConnected(realm);

        return new String(serverId);
    }

    public static void setServerAsConnected(final ServerRealm server) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ServerRealm connectedServer = realm.where(ServerRealm.class).equalTo("isConnected", true).findFirst();
                    connectedServer.setConnected(false);
                    server.setConnected(true);
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    public static ServerRealm getServerById(Realm realm, String serverId) {
        ServerRealm serverRealm = null;
        serverRealm = realm.where(ServerRealm.class).equalTo("gateway", serverId).findFirst();
        return serverRealm;
    }

    private static String setFirstAsConnected(Realm realm) {
        final ServerRealm server = realm.where(ServerRealm.class).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                server.setConnected(true);
            }
        });
        return server.getGateway();
    }

    private static boolean hasServers(Realm realm) {
        RealmResults<ServerRealm> results = realm.where(ServerRealm.class).findAll();
        return results.size() != 0;
    }
}
