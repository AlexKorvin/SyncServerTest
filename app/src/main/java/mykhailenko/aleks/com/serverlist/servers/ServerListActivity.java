package mykhailenko.aleks.com.serverlist.servers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import mykhailenko.aleks.com.serverlist.R;
import mykhailenko.aleks.com.serverlist.data.realm.ServerRealm;
import mykhailenko.aleks.com.serverlist.fetcher.ServerFetcher;

public class ServerListActivity extends AppCompatActivity implements OnServerClickListener{

    private ServerFetcher fetcher;
    private RecyclerView recyclerView;
    private ServersRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers);
        fetcher = new ServerFetcher();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setUpRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
        fetcher.release();
    }

    private void setUpRecyclerView() {
        adapter = new ServersRecyclerViewAdapter(fetcher.getServers());
        adapter.setServerClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onServerClick(ServerRealm serverRealm) {
        fetcher.setServerAsConnected(serverRealm);
        finish();
    }
}
