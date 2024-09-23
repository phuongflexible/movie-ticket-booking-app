package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddPaymentMethodActivity;
import com.example.bookticketapp.adapters.PaymentMethodAdapter;
import com.example.bookticketapp.dao.PaymentMethodQuery;
import com.example.bookticketapp.models.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodFragment extends Fragment {
    Activity context;
    Button btnAddPaymentMethod;
    ListView lVPaymentMethod;
    PaymentMethodQuery payQuery;
    PaymentMethodAdapter payAdapter;
    List<PaymentMethod> listPayments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_payment_method, container, false);
        lVPaymentMethod = view.findViewById(R.id.lVPaymentMethod);
        btnAddPaymentMethod = view.findViewById(R.id.btnAddPaymentMethod);
        payQuery = new PaymentMethodQuery(context);
        listPayments = new ArrayList<>();
        listPayments = payQuery.getAllMethods();
        payAdapter = new PaymentMethodAdapter(listPayments, context);
        lVPaymentMethod.setAdapter(payAdapter);

        btnAddPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context, AddPaymentMethodActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}