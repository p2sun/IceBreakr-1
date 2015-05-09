package com.lloydtorres.icebreakr;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Lloyd on 2015-05-09.
 */
public class ProfileFragment extends Fragment {
    private static final String STORAGE_NAME = "IcebreakrStorage"; // file name for preferences
    private SharedPreferences storage;

    private View view;
    private View.OnClickListener nameListener;
    private View.OnClickListener descListener;
    private View.OnClickListener interestListener;

    private CardView nameCard;
    private CardView descCard;
    private CardView interestCard;

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
                            name_field.setText(input.toString());
                            SharedPreferences.Editor editor = storage.edit();
                            editor.putString("name",input.toString());
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
                        .input(R.string.desc_hint, R.string.blank, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                desc_field.setText(input.toString());
                                SharedPreferences.Editor editor = storage.edit();
                                editor.putString("desc",input.toString());
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
                                interest_field.setText(input.toString());
                                SharedPreferences.Editor editor = storage.edit();
                                editor.putString("interest",input.toString());
                            }
                        }).show();
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);

        nameCard = (CardView) view.findViewById(R.id.name_card);
        descCard = (CardView) view.findViewById(R.id.desc_card);
        interestCard = (CardView) view.findViewById(R.id.interest_card);

        nameCard.setOnClickListener(nameListener);
        descCard.setOnClickListener(descListener);
        interestCard.setOnClickListener(interestListener);

        name_field = (TextView) view.findViewById(R.id.name_field);
        if (name != null) {
            name_field.setText(storage.getString("name", null));
        }

        desc_field = (TextView) view.findViewById(R.id.desc_field);

        if (desc != null) {
            desc_field.setText(storage.getString("desc", null));
        }

        interest_field = (TextView) view.findViewById(R.id.interest_field);

        if (interests != null) {
            interest_field.setText(storage.getString("interest", null));
        }

        return view;
    }
}
