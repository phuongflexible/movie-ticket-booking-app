package com.example.bookticketapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.activities.AddPaymentMethodActivity;
import com.example.bookticketapp.activities.UpdatePaymentMethodActivity;
import com.example.bookticketapp.adapters.PaymentMethodAdapter;
import com.example.bookticketapp.dao.PaymentMethodQuery;
import com.example.bookticketapp.events.PaymentMethodSelectListener;
import com.example.bookticketapp.models.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodFragment extends Fragment implements PaymentMethodSelectListener {
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
        payAdapter = new PaymentMethodAdapter(listPayments, context, this);
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

    @Override
    public void updatePaymentMethod(PaymentMethod payment) {
        int id = payment.getId();
        String name = payment.getName();
        Intent intent = new Intent(context, UpdatePaymentMethodActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    public void deletePaymentMethod(PaymentMethod payment) {
        int id = payment.getId();
        Boolean delete = payQuery.deletePaymentMethod(id);
        if (delete == true)
        {
            Toast.makeText(context, "Xoá phương thức thành công", Toast.LENGTH_SHORT).show();
            reloadPaymentMethodFragment();
        }
        else
        {
            Toast.makeText(context, "Xoá phương thức thất bại", Toast.LENGTH_SHORT).show();

        }
    }

    private void reloadPaymentMethodFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new PaymentMethodFragment());
        fragmentTransaction.commit();
    }
}