package mykhailenko.aleks.com.serverlist.fetcher;

public interface OnFindServerListener {
    void onFindServer(String serverId);
    void onError(Throwable throwable);
}
