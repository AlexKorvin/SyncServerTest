package mykhailenko.aleks.com.serverlist.rest.requests;

import android.util.Log;

import java.util.List;

import mykhailenko.aleks.com.serverlist.data.api.Server;
import mykhailenko.aleks.com.serverlist.data.realm.ServerRealm;
import mykhailenko.aleks.com.serverlist.rest.APIUtils;
import mykhailenko.aleks.com.serverlist.rest.IVPNApi;
import mykhailenko.aleks.com.serverlist.rest.RequestListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServersListRequest implements Callback<List<Server>> {
    
    private static final String TAG = ServersListRequest.class.getSimpleName();

    private RequestListener listener;
    private Call<List<Server>> call;

    public void start() {
        IVPNApi ivpnApi = APIUtils.getAPIService();
        call = ivpnApi.getServers();
        call.enqueue(this);
    }

    public void setListener(RequestListener listener) {
        this.listener = listener;
    }

    public void cancel() {
        call.cancel();
    }

    @Override
    public void onResponse(Call<List<Server>> call, Response<List<Server>> response) {
        Log.d(TAG, "onResponse: ");
        if (response == null || !response.isSuccessful()) return;
        if (listener != null)
            listener.onSuccess(response.body());
    }

    @Override
    public void onFailure(Call<List<Server>> call, Throwable throwable) {
        Log.d(TAG, "onFailure: ");
        if (listener != null)
            listener.onError(throwable);
    }
}
