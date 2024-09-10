package com.example.bookticketapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.utils.ImageUtils;

import java.util.List;

public class CinemaListViewAdapter extends BaseAdapter {
    private Context context;
    private int layoutItem;
    private List<Cinema> cinemaList;

    public CinemaListViewAdapter(Context context, int layoutItem, List<Cinema> cinemaList) {
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
        TextView txtCinemaName, txtAddress;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutItem, null);

            holder.imgCinema = view.findViewById(R.id.imgCinema);
            holder.txtCinemaName = view.findViewById(R.id.txtCinemaName);
            holder.txtAddress = view.findViewById(R.id.txtAddress);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Cinema cinema = cinemaList.get(i);

        Bitmap bitmap = ImageUtils.byteArrayToBitmap(cinema.getImage());
        holder.imgCinema.setImageBitmap(bitmap);
        holder.txtCinemaName.setText(cinema.getName());
        holder.txtAddress.setText(cinema.getAddress());

        return view;
    }
}
