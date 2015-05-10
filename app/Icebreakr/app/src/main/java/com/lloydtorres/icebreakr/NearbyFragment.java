package com.lloydtorres.icebreakr;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Lloyd on 2015-05-09.
 */
public class NearbyFragment extends Fragment {
    private View view;

    private static final String STORAGE_NAME = "IcebreakrStorage"; // file name for preferences
    private SharedPreferences storage;

    private RequestQueue mRequestQueue;
    private String twitterId;

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
    private BeaconManager beaconManager;
    private NearbyListAdapter adapter;

    private int counter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = this.getActivity().getSharedPreferences(STORAGE_NAME, 0);

        // Configure device list.
        adapter = new NearbyListAdapter(getActivity());

        twitterId = storage.getString("twitterId", null);

        // Instantiate request queue
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        String url ="http://icebreakr.herokuapp.com/users/";

        // Configure BeaconManager.
        beaconManager = new BeaconManager(getActivity());
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                // Note that results are not delivered on UI thread.
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        counter++;

                        List<User> tmpList = new ArrayList<User>();
                        for (Beacon b : beacons) {
                            double distance = Utils.computeAccuracy(b);
                            if (storage.getString("estimoteId", null) == null) {
                                if (distance <= 0.05) {
                                    String uuid = "" + b.getMajor() + b.getMinor();
                                    SharedPreferences.Editor editor = storage.edit();
                                    editor.putString("estimoteId", uuid);
                                    editor.commit();
                                }
                            }

                            if (distance > 0.8) {
                                Random coin = new Random();
                                String[] firstName = {"Josh", "Peter", "Lloyd", "Emmanuel", "Finn", "Twilight", "Marceline", "Karen", "Hatsune", "Jebediah"};
                                String[] lastName = {"Drake", "Sparkle", "Applejack", "Tour", "Samragi", "Mertens", "Nii", "Kerman", "Miku", "Drossel"};
                                String[] bios = {"Don't smile because it's over, cry because it happened.",
                                        "I have the same number of Oscars as Leonardo diCaprio.",
                                        "Proud #uWaterloo #Engineering student!!!",
                                        "If you can't handle me at my best, you don't deserve me at my worst.",
                                        "Thomas the Tank is my spirit animal. <3333",
                                        "Would you tell me why you are leaving me on hold???",
                                        "The light and the darkness and the light and the darkness #KingdomHearts",
                                        "Views are my own and don't reflect the opinions of Spadina Dry Cleaning."};
                                String[] interests = {"Painting", "Drawing", "Anime", "Sportsball", "Fencing", "Archery", "Programming", "Hackathons",
                                        "#SocialMedia", "Partyyyyy", "Video Games", "Imperial Governance", "The Argentinian Stock Crisis of the 90s",
                                        "Pokemons", "Adventure Time", "Myo Armbanding"};

                                String[] interest = new String[5];
                                for (int i = 0; i < 5; i++) {
                                    interest[i] = interests[coin.nextInt(interests.length)];
                                }

                                String name = firstName[coin.nextInt(firstName.length)] + " " + lastName[coin.nextInt(lastName.length)];

                                User tmpUser = new User("1234", "" + b.getMajor() + b.getMinor(), name, bios[coin.nextInt(bios.length)], TextUtils.join(", ", interest), b);
                                tmpList.add(tmpUser);
                            }
                        }

                        if (counter % 7 == 0) {
                            counter = 0;
                            adapter.replaceWith(tmpList);
                        }
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nearby_fragment, container, false);
        ListView list = (ListView) view.findViewById(R.id.user_list);
        list.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        beaconManager.disconnect();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if device supports Bluetooth Low Energy.
        if (!beaconManager.hasBluetooth()) {
            // lol
            return;
        }

        // If Bluetooth is not enabled, let user enable it.
        if (!beaconManager.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            connectToService();
        }
    }

    @Override
    public void onStop() {
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
        } catch (RemoteException e) {
            // lol
        }

        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                connectToService();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void connectToService() {
        adapter.replaceWith(Collections.<User>emptyList());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                } catch (RemoteException e) {
                    // lol
                }
            }
        });
    }

}
