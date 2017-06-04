package mykhailenko.aleks.com.serverlist.rest;

import java.util.List;

import mykhailenko.aleks.com.serverlist.data.api.Server;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IVPNApi {

    @GET("v1/servers.json")
    Call<List<Server>> getServers();

}
