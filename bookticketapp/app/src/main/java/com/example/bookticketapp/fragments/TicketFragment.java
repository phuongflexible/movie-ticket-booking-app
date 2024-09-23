package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddRoomActivity;
import com.example.bookticketapp.adapters.RoomAdapter;
import com.example.bookticketapp.adapters.TicketAdapter;
import com.example.bookticketapp.dao.RoomQuery;
import com.example.bookticketapp.dao.TicketQuery;
import com.example.bookticketapp.models.Ticket;

import java.util.List;

public class TicketFragment extends Fragment {
    Activity context;
    ListView lvTicket;
    TicketQuery ticketQuery;
    List<Ticket> listTickets;
    TicketAdapter ticketAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        lvTicket = view.findViewById(R.id.lvTicket);

        ticketQuery = new TicketQuery(context);
        listTickets = ticketQuery.getAllTicket();
        ticketAdapter = new TicketAdapter(listTickets, context);
        lvTicket.setAdapter(ticketAdapter);

        return view;
    }
}