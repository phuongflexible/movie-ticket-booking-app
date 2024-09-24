package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.ReceiptQuery;
import com.example.bookticketapp.dao.SeatQuery;
import com.example.bookticketapp.dao.ShowtimeQuery;
import com.example.bookticketapp.models.Receipt;
import com.example.bookticketapp.models.Seat;
import com.example.bookticketapp.models.Showtime;
import com.example.bookticketapp.models.Ticket;
import com.example.bookticketapp.utils.DatetimeUtils;

import java.util.List;

public class TicketAdapter extends BaseAdapter {
    private List<Ticket> listTickets;
    private Context context;
    private LayoutInflater inflater;
    private SeatQuery seatQuery;
    private ShowtimeQuery showtimeQuery;
    private ReceiptQuery receiptQuery;

    public TicketAdapter(List<Ticket> listTickets, Context context) {
        this.listTickets = listTickets;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.seatQuery = new SeatQuery(context);
        this.showtimeQuery = new ShowtimeQuery(context);
        this.receiptQuery = new ReceiptQuery(context);
    }


    @Override
    public int getCount() {
        return listTickets.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = this.inflater.inflate(R.layout.item_ticket, null);
        TextView txtShowtime = view.findViewById(R.id.txtShowtime);
        Showtime showtime = showtimeQuery.getShowtimeById(listTickets.get(i).getShowtimeId());
        txtShowtime.setText(DatetimeUtils.calendarToString(showtime.getShowDate()));

        TextView txtSeat = view.findViewById(R.id.txtSeat);
        Seat seat = seatQuery.getSeatById(listTickets.get(i).getSeatId());
        txtSeat.setText(seat.getSeatNumber());

        TextView txtPrice = view.findViewById(R.id.txtPrice);
        txtPrice.setText(String.valueOf(listTickets.get(i).getPrice()));

        TextView txtReceipt = view.findViewById(R.id.txtReceipt);
        Receipt receipt = receiptQuery.getReceiptById(listTickets.get(i).getReceiptId());
        txtPrice.setText(String.valueOf(receipt.getTotal()));
        return view;
    }
}
