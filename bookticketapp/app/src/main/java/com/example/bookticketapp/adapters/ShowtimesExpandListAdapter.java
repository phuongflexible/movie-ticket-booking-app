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
import com.example.bookticketapp.utils.DatetimeUtils;

import java.util.HashMap;
import java.util.List;

public class ShowtimesExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Cinema> cinemaList;
    private HashMap<Cinema, List<Showtime>> showtimeMap;

    public ShowtimesExpandListAdapter(Context context, List<Cinema> cinemaList, HashMap<Cinema, List<Showtime>> showtimeMap) {
        this.context = context;
        this.cinemaList = cinemaList;
        this.showtimeMap = showtimeMap;
    }

    @Override
    public int getGroupCount() {
        return cinemaList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return showtimeMap.get(cinemaList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return cinemaList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return showtimeMap.get(cinemaList.get(i)).get(i1);
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
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_showtimes_group_expandlist, null);

        TextView txtCinemaName = view.findViewById(R.id.txtHeaderCinemaName);
        txtCinemaName.setText(cinemaList.get(i).getName());

        if (isExpanded) {     // Mũi tên lên khi mở
            txtCinemaName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);
        } else {              // Mũi tên xuống khi đóng
            txtCinemaName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
        }


        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_showtimes_child_expandlist, null);

        Button btnShowtime = view.findViewById(R.id.btnChildShowtime);

        Showtime showtime = (Showtime) getChild(i, i1);
        // chuyển calendar "showtime" sang string
        String showtimeString = DatetimeUtils.timeToString(showtime.getShowtime());
        btnShowtime.setText(showtimeString);

        btnShowtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Move to booking", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra("showtime", showtime);
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
