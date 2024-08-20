package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.fragments.CinemaFragment;
import com.example.bookticketapp.models.Cinema;

import java.util.List;

public class CinemaAdapter extends BaseAdapter {
    private Context context;
    private int layoutItem;
    private List<Cinema> cinemaList;

    public CinemaAdapter(Context context, int layoutItem, List<Cinema> cinemaList) {
        this.context = context;
        this.layoutItem = layoutItem;
        this.cinemaList = cinemaList;
    }

    @Override
    public int getCount() {
        return cinemaList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgCinema;
        TextView tvCinemaName, tvAddress;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutItem, null);

            holder.imgCinema = view.findViewById(R.id.imgCinema);
            holder.tvCinemaName = view.findViewById(R.id.tvCinemaName);
            holder.tvAddress = view.findViewById(R.id.tvAddress);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Cinema cinema = cinemaList.get(i);

        holder.imgCinema.setImageResource(cinema.getFakeImage());
        holder.tvCinemaName.setText(cinema.getName());
        holder.tvAddress.setText(cinema.getAddress());

        return view;
    }
}
