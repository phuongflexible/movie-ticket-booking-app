package com.example.bookticketapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.BookingActivity;
import com.example.bookticketapp.activities.MovieDetailsActivity;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Showtime;

import java.util.List;

public class ShowtimesExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Cinema> cinemaList;

    public ShowtimesExpandListAdapter(Context context, List<Cinema> cinemaList) {
        this.context = context;
        this.cinemaList = cinemaList;
    }

    @Override
    public int getGroupCount() {
        return cinemaList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return cinemaList.get(i).getShowtimes().size();
    }

    @Override
    public Object getGroup(int i) {
        return cinemaList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return cinemaList.get(i).getShowtimes().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_showtimes_group_expandlist, null);

        TextView txtCinemaName = view.findViewById(R.id.txtHeaderCinemaName);
        txtCinemaName.setText(cinemaList.get(i).getName());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_showtimes_child_expandlist, null);

        Button btnShowtime = view.findViewById(R.id.btnChildShowtime);
        Showtime showtime = cinemaList.get(i).getShowtimes().get(i1);
        btnShowtime.setText(showtime.getTime());

        btnShowtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Move to booking", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BookingActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
