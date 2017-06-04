package mykhailenko.aleks.com.serverlist.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import java.util.List;

import mykhailenko.aleks.com.serverlist.data.api.Server;
import mykhailenko.aleks.com.serverlist.realm.RealmHelper;
import mykhailenko.aleks.com.serverlist.realm.RealmMapper;
import mykhailenko.aleks.com.serverlist.rest.RequestListener;
import mykhailenko.aleks.com.serverlist.rest.requests.ServersListRequest;

public class ServerSyncJobService extends JobService {

    private static final String TAG = ServerSyncJobService.class.getSimpleName();

    private ServersListRequest request;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: ");
        request = new ServersListRequest();
        request.setListener(new RequestListener() {
            @Override
            public void onSuccess(List<Server> servers) {
                Log.d(TAG, "onSuccess: ");
                RealmHelper.updateAvailableServers(RealmMapper.from(servers));
                jobFinished(jobParameters, true);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG, "onError: ");
                jobFinished(jobParameters, true);
            }
        });
        request.start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: ");
        request.cancel();
        return true;
    }
}
