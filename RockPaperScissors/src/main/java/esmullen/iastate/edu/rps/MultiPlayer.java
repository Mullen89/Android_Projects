package esmullen.iastate.edu.rps;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

public class MultiPlayer extends AppCompatActivity {

    Button btnDiscover;
    RecyclerView playerList;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    IntentFilter filter;
    Wifi_P2P_Receiver mReceiver;
    WifiManager wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);

        initialWork();
    }

    public void initialWork() {
        btnDiscover = (Button) findViewById(R.id.btnFindPlayers);
        playerList = (RecyclerView) findViewById(R.id.listPlayerView);

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

        mReceiver = new Wifi_P2P_Receiver(mManager, mChannel, this);

        filter = new IntentFilter();
        filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}
