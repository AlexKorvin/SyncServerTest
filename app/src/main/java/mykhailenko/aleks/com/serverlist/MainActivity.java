package mykhailenko.aleks.com.serverlist;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import mykhailenko.aleks.com.serverlist.data.realm.ServerRealm;
import mykhailenko.aleks.com.serverlist.fetcher.OnFindServerListener;
import mykhailenko.aleks.com.serverlist.fetcher.ServerFetcher;
import mykhailenko.aleks.com.serverlist.servers.ServerListActivity;
import mykhailenko.aleks.com.serverlist.service.ServerSyncJobService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SERVER_SYNC_SERVICE_JOB_ID = 42;

    private ServerFetcher fetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetcher = new ServerFetcher();

        findViewById(R.id.try_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toServersScreen();
            }
        });
        startServerSyncJob();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetcher.getConnectedServer(new OnFindServerListener() {
            @Override
            public void onFindServer(String serverId) {
                applyConnectedServer(serverId);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG, "onError: " + throwable);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fetcher.release();
    }

    private void applyConnectedServer(final String serverId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showConnectedServer(fetcher.getServerById(serverId));
            }
        });
    }

    private void showConnectedServer(final ServerRealm server) {
        if (server == null) return;
        findViewById(R.id.footer_progress).setVisibility(View.GONE);
        findViewById(R.id.footer_content).setVisibility(View.VISIBLE);

        ((TextView) findViewById(R.id.connected_server)).setText(server.getCity()
                + ", " + server.getCountryCode());
    }

    private void toServersScreen() {
        Intent intent = new Intent(this, ServerListActivity.class);
        startActivity(intent);
    }

    private void startServerSyncJob() {
        Log.d(TAG, "startServerSyncJob: ");
        JobScheduler jobScheduler =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(new JobInfo.Builder(SERVER_SYNC_SERVICE_JOB_ID,
                new ComponentName(this, ServerSyncJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(15 * 60 * 1000)
                .build());
    }
}
