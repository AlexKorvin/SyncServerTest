package mykhailenko.aleks.com.serverlist.servers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import mykhailenko.aleks.com.serverlist.R;
import mykhailenko.aleks.com.serverlist.data.realm.ServerRealm;

public class ServersRecyclerViewAdapter
        extends RealmRecyclerViewAdapter<ServerRealm, ServersRecyclerViewAdapter.ServerViewHolder> {

    private OnServerClickListener listener;
    private Random random;

    ServersRecyclerViewAdapter(OrderedRealmCollection<ServerRealm> servers) {
        super(servers, true);
        random = new Random();
        setHasStableIds(true);
    }

    public void setServerClickListener(OnServerClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ServerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new ServerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServerViewHolder holder, int position) {
        final ServerRealm server = getItem(position);
        holder.description.setText(server.getCity() + ", " + server.getCountryCode());
        holder.ping.setText(random.nextInt(250) + "ms");
    }

    class ServerViewHolder extends RecyclerView.ViewHolder {

        public TextView description;
        public TextView ping;

        ServerViewHolder(View parentView) {
            super(parentView);

            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onServerClick(getItem(ServerViewHolder.this.getAdapterPosition()));
                }
            });
            description = (TextView) parentView.findViewById(R.id.server_description);
            ping = (TextView) parentView.findViewById(R.id.server_ping);
        }
    }
}
