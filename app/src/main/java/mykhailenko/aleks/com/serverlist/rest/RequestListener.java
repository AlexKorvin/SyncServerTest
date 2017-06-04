package mykhailenko.aleks.com.serverlist.rest;

import java.util.List;

import mykhailenko.aleks.com.serverlist.data.api.Server;

public interface RequestListener {
    void onSuccess(List<Server> servers);
    void onError(Throwable throwable);
}