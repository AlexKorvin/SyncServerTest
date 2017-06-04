package mykhailenko.aleks.com.serverlist.fetcher;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import mykhailenko.aleks.com.serverlist.data.api.Server;
import mykhailenko.aleks.com.serverlist.data.realm.ServerRealm;
import mykhailenko.aleks.com.serverlist.realm.RealmHelper;
import mykhailenko.aleks.com.serverlist.realm.RealmMapper;
import mykhailenko.aleks.com.serverlist.rest.RequestListener;
import mykhailenko.aleks.com.serverlist.rest.requests.ServersListRequest;

public class ServerFetcher {

    private Realm realm;

    public ServerFetcher() {
        realm = Realm.getDefaultInstance();
    }

    public void getConnectedServer(final OnFindServerListener listener) {
        String connectedServerId = getConnectedServerRealm();
        if (connectedServerId != null) {
            listener.onFindServer(connectedServerId);
            return;
        }
        requestServers(new RequestListener() {
            @Override
            public void onSuccess(List<Server> servers) {
                if (listener != null)
                    listener.onFindServer(getConnectedServerRealm());
            }

            @Override
            public void onError(Throwable throwable) {
                if (listener != null)
                    listener.onError(throwable);
            }
        });
    }

    private void requestServers(final RequestListener listener) {
        ServersListRequest request = new ServersListRequest();
        request.setListener(new RequestListener() {
            @Override
            public void onSuccess(List<Server> servers) {
                saveToRealm(servers);
                if (listener != null)
                    listener.onSuccess(servers);
            }

            @Override
            public void onError(Throwable throwable) {
                if (listener != null)
                    listener.onError(throwable);
            }
        });
        request.start();
    }

    public RealmResults<ServerRealm> getServers() {
        return RealmHelper.getAvailableServers(realm);
    }

    public void setServerAsConnected(ServerRealm serverRealm) {
        RealmHelper.setServerAsConnected(serverRealm);
    }

    public ServerRealm getServerById(String serverId) {
        return RealmHelper.getServerById(realm, serverId);
    }

    public void release() {
        realm.close();
        realm = null;
    }

    private void saveToRealm(List<Server> servers) {
        RealmHelper.updateAvailableServers(RealmMapper.from(servers));
    }

    private String getConnectedServerRealm() {
        return RealmHelper.getConnectedServer(realm);
    }
}
