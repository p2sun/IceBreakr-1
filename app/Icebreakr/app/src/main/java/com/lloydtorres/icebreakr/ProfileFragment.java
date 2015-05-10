package com.lloydtorres.icebreakr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * Created by Lloyd on 2015-05-09.
 */
public class ProfileFragment extends Fragment {
    private static final String STORAGE_NAME = "IcebreakrStorage"; // file name for preferences
    private SharedPreferences storage;

    private RequestQueue mRequestQueue;

    private View view;
    private View.OnClickListener nameListener;
    private View.OnClickListener descListener;
    private View.OnClickListener interestListener;
    private View.OnClickListener signOutListener;

    private CardView nameCard;
    private CardView descCard;
    private CardView interestCard;
    private LinearLayout signOut;

    private TextView name_field;
    private TextView desc_field;
    private TextView interest_field;

    private String name;
    private String desc;
    private String interests;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = this.getActivity().getSharedPreferences(STORAGE_NAME, 0);

        name = storage.getString("name", null);
        desc = storage.getString("desc", null);
        interests = storage.getString("interest", null);

        // Instantiate request queue
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        String url ="http://icebreakr.herokuapp.com/users/";

        nameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                    .title(R.string.name_text)
                    .content(R.string.name_text)
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input(R.string.name_hint, R.string.blank, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            name = input.toString();
                            name_field.setText(name);
                            SharedPreferences.Editor editor = storage.edit();
                            editor.putString("name",name);
                            editor.commit();
                        }
                    }).show();
            }
        };

        descListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.desc_text)
                        .content(R.string.desc_text)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .inputMaxLengthRes(150, R.color.dgts__purple_pressed)
                        .input(R.string.desc_hint, R.string.blank, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                desc = input.toString();
                                desc_field.setText(desc);
                                SharedPreferences.Editor editor = storage.edit();
                                editor.putString("desc", desc);
                                editor.commit();
                            }
                        }).show();
            }
        };

        interestListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.interest_text)
                        .content(R.string.interest_text)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input(R.string.interest_hint, R.string.blank, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                interests = input.toString();
                                interest_field.setText(interests);
                                SharedPreferences.Editor editor = storage.edit();
                                editor.putString("interest",interests);
                                editor.commit();
                            }
                        }).show();
            }
        };

        signOutListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent launchApp = new Intent(getActivity(),LoginActivity.class);
                startActivity(launchApp);
                SharedPreferences.Editor editor = storage.edit();
                editor.putBoolean("authorized",false);
                editor.remove("twitterId");
                editor.remove("estimoteId");
                editor.commit();
                getActivity().finish();
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);

        nameCard = (CardView) view.findViewById(R.id.name_card);
        descCard = (CardView) view.findViewById(R.id.desc_card);
        interestCard = (CardView) view.findViewById(R.id.interest_card);
        signOut = (LinearLayout) view.findViewById(R.id.sign_out);

        nameCard.setOnClickListener(nameListener);
        descCard.setOnClickListener(descListener);
        interestCard.setOnClickListener(interestListener);
        signOut.setOnClickListener(signOutListener);

        name_field = (TextView) view.findViewById(R.id.name_field);
        if (name != null) {
            name_field.setText(storage.getString("name", ""));
        }

        desc_field = (TextView) view.findViewById(R.id.desc_field);

        if (desc != null) {
            desc_field.setText(storage.getString("desc", ""));
        }

        interest_field = (TextView) view.findViewById(R.id.interest_field);

        if (interests != null) {
            interest_field.setText(storage.getString("interest", ""));
        }

        return view;
    }


}
