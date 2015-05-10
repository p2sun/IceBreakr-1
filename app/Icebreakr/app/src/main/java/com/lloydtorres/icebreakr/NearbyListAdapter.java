package com.lloydtorres.icebreakr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.estimote.sdk.Utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Lloyd on 2015-05-09.
 */
public class NearbyListAdapter extends BaseAdapter {

    private ArrayList<User> users;
    private LayoutInflater inflater;

    public NearbyListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.users = new ArrayList<User>();
    }

    public void replaceWith(Collection<User> newUsers) {
        this.users.clear();
        this.users.addAll(newUsers);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflateIfRequired(view, position, parent);
        bind(getItem(position), view);
        return view;
    }

    private void bind(User user, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.nameTextView.setText(user.getName());
        holder.descTextView.setText(user.getDescription());
        holder.interestsTextView.setText(user.getInterests());
        holder.distanceTextView.setText(String.format("Distance: %.2f m",user.getDistance()));
    }

    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.user_item, null);
            view.setTag(new ViewHolder(view));
        }
        return view;
    }

    static class ViewHolder {
        final TextView nameTextView;
        final TextView descTextView;
        final TextView interestsTextView;
        final TextView distanceTextView;

        ViewHolder(View view) {
            nameTextView = (TextView) view.findViewWithTag("name");
            descTextView = (TextView) view.findViewWithTag("desc");
            interestsTextView = (TextView) view.findViewWithTag("interests");
            distanceTextView = (TextView) view.findViewWithTag("distance");
        }
    }
}
