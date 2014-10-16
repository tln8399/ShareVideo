/**
 * 
 */
package com.example.sharevideo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.util.Log;

/**
 * @author Tushar
 *
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver{
	
	private WifiP2pManager svManager;
    private Channel svChannel;
    private ShareVideoMainActivity svActivity;
    private static final String TAG = WiFiDirectBroadcastReceiver.class
    		                          .getSimpleName();
    private static boolean isConnected = false;

    /**
     * 
     */
    public WiFiDirectBroadcastReceiver (WifiP2pManager manager, 
    									Channel channel,
    									ShareVideoMainActivity activity) {
        super();
        this.svManager = manager;
        this.svChannel = channel;
        this.svActivity = activity;
    }
    
    
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {

            // UI update to indicate wifi p2p status.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, 
            							   -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi Direct mode is enabled
                //svActivity.setIsWifiP2pEnabled(true);
            	Log.i(TAG, "Wifi P2P is enabled.");
            } else {
            	Log.i(TAG, "Wifi P2P is disabled.");
            	WifiManager wifiManager = (WifiManager) svActivity
            			.getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(false);
                wifiManager.setWifiEnabled(true);
                //activity.setIsWifiP2pEnabled(false);
                //activity.resetData();

            }
            Log.d(TAG, "P2P state changed - " + state);
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if (svManager != null) {
              //  svManager.requestPeers(svChannel, (PeerListListener) svActivity.getFragmentManager()
              //          .findFragmentById(R.id.frag_list));
              //svManager.requestPeers(svChannel, svActivity.peerListListner);
            }
            Log.d(TAG, "P2P peers changed");
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            if (svManager == null) {
                return;
            }

            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {
            	Log.d(TAG, "Network Conencted.");
                // we are connected with the other device, request connection
                // info to find group owner IP
            	if(!isConnected){
            		//svManager.requestConnectionInfo(svChannel, svActivity);
            		//svManager.requestConnectionInfo(svChannel, svActivity);
            		isConnected = true;
            	}
            } else {
                // It's a disconnect
            	isConnected = false;
                Log.d(TAG, "Network is not connected.");
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            //DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
            //        .findFragmentById(R.id.frag_list);
            //fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
            //        WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));

        }
	}

}
