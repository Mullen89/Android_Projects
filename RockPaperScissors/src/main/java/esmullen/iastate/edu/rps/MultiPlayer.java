package esmullen.iastate.edu.rps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiPlayer extends AppCompatActivity {

    Button btnDiscover;
    Button sendRock;
    Button sendPaper;
    Button sendScissors;
    String tempMsg = "";
    TextView status;
    TextView opponentChoice;
    TextView gameResult;
    String playerChoice = "";
    ListView playerList;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    IntentFilter filter;
    Wifi_P2P_Receiver mReceiver;
    WifiManager wifi;

    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;

    static final int MESSAGE_READ = 1;

    ServerClass serverClass;
    ClientClass clientClass;
    SendReceive sendReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);


//        if (android.os.Build.VERSION.SDK_INT >= 23) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

        initialWork();
        exqListener();
//        opponentChoice.addTextChangedListener(this);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint("ResourceAsColor")
        @Override
        public boolean handleMessage(Message message) {
            switch(message.what){
                case MESSAGE_READ:
                    byte[] readBuff = (byte[]) message.obj;
                    tempMsg = new String(readBuff, 0, message.arg1);
                    opponentChoice.setText("Your opponent chooses " + tempMsg + "!");
                    break;
            }
            return true;
        }
    });

    private void exqListener() {
        status = (TextView) findViewById(R.id.connectStatusTV);
        opponentChoice = (TextView) findViewById(R.id.opponentChoiceTV);
        gameResult = (TextView) findViewById(R.id.gameResultTV);

        btnDiscover = (Button) findViewById(R.id.btnFindPlayers);
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
//                        Toast.makeText(getApplication(), "Discovery Started", Toast.LENGTH_LONG).show();
                        status.setText("Discovery Started");
                    }

                    @Override
                    public void onFailure(int i) {
//                        Toast.makeText(getApplication(), "Discovery Failed", Toast.LENGTH_LONG).show();
                        status.setText("Discovery Failed");
                    }
                });
            }
        });

        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final WifiP2pDevice device = deviceArray[i];
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;

                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplication(), "Connected to " + device.deviceName, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int i) {
                        Toast.makeText(getApplication(), "Not connected", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        sendRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Rock";
                playerChoice = "Rock";
                sendReceive.write(msg.getBytes());
                if(tempMsg.length() > 0){
                    colorChange();
                }
            }
        });

        sendPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Paper";
                playerChoice = "Paper";
                sendReceive.write(msg.getBytes());
                if(tempMsg.length() > 0){
                    colorChange();
                }
            }
        });

        sendScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "Scissors";
                playerChoice = "Scissors";
                sendReceive.write(msg.getBytes());
                if(tempMsg.length() > 0){
                    colorChange();
                }
            }
        });
    }

    public void initialWork() {
        btnDiscover = (Button) findViewById(R.id.btnFindPlayers);
        playerList = (ListView) findViewById(R.id.listPlayerView);

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

        mReceiver = new Wifi_P2P_Receiver(mManager, mChannel, this);

        filter = new IntentFilter();
        filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        sendRock = (Button) findViewById(R.id.btnSendRock);
        sendPaper = (Button) findViewById(R.id.btnSendPaper);
        sendScissors = (Button) findViewById(R.id.btnSendScissors);
    }

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if(!peerList.getDeviceList().equals(peers)){
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];

                int index = 0;

                for(WifiP2pDevice device : peerList.getDeviceList()) {
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                    index++;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, deviceNameArray);
            playerList.setAdapter(adapter);

            if(peers.size() == 0) {
                Toast.makeText(getApplication(), "No nearby devices found.", Toast.LENGTH_LONG).show();
                return;
            }
        }
    };

    WifiP2pManager.ConnectionInfoListener CIL = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            final InetAddress GOA = wifiP2pInfo.groupOwnerAddress;
            if(wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner){
                status.setText("Host");
                serverClass = new ServerClass();
                serverClass.start();
            } else if(wifiP2pInfo.groupFormed){
                status.setText("Client");
                clientClass = new ClientClass(GOA);
                clientClass.start();
            }
        }
    };

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

//    @Override
//    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        //nothing needed.
//    }
//
//    @Override
//    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        //nothing needed
//    }
//
//    @Override
//    public void afterTextChanged(Editable editable) {
//        if(playerChoice.length() > 0){
//            colorChange();
//        }
//    }

    public class ServerClass extends Thread {

        Socket socket;
        ServerSocket serverSocket;

        @Override
        public void run(){
//            super.run();
            try {
                serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                sendReceive = new SendReceive(socket);
                sendReceive.start();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SendReceive extends Thread {
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public SendReceive(Socket skt) {
            socket = skt;
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
//            super.run();
            byte[] buffer = new byte[1024];
            int bytes;

            while(socket != null){
                try {
                    bytes = inputStream.read(buffer);
                    if(bytes > 0){
                        handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ClientClass extends Thread {
        Socket socket;
        String hostAdd;

        public ClientClass(InetAddress hostAddress){
            hostAdd = hostAddress.getHostAddress();
            socket = new Socket();
        }

        @Override
        public void run(){
//            super.run();
            try {
                socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
                sendReceive = new SendReceive(socket);
                sendReceive.start();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void colorChange(){
        if(tempMsg.equals(playerChoice)){
            gameResult.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            gameResult.setText("DRAW");
        } else if(tempMsg.equals("Rock") && playerChoice.equals("Paper")){
            gameResult.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            gameResult.setText("YOU WIN!");

        } else if(tempMsg.equals("Rock") && playerChoice.equals("Scissors")){
            gameResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            gameResult.setText("YOU LOSE!");

        } else if(tempMsg.equals("Paper") && playerChoice.equals("Rock")){
            gameResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            gameResult.setText("YOU LOSE!");

        } else if(tempMsg.equals("Paper") && playerChoice.equals("Scissors")){
            gameResult.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            gameResult.setText("YOU WIN!");

        } else if(tempMsg.equals("Scissors") && playerChoice.equals("Rock")){
            gameResult.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            gameResult.setText("YOU WIN!");

        } else if(tempMsg.equals("Scissors") && playerChoice.equals("Paper")){
            gameResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            gameResult.setText("YOU LOSE!");
        }
    }
}
