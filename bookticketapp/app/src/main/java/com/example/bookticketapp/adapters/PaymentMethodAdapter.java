package com.example.bookticketapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookticketapp.R;
import com.example.bookticketapp.events.PaymentMethodSelectListener;
import com.example.bookticketapp.models.PaymentMethod;

import org.w3c.dom.Text;

import java.util.List;

public class PaymentMethodAdapter extends BaseAdapter {
    private List<PaymentMethod> listPaymentMethods;
    private Context context;
    private LayoutInflater inflater;
    private PaymentMethodSelectListener listener;

    public PaymentMethodAdapter(List<PaymentMethod> listPaymentMethods, Context context, PaymentMethodSelectListener listener) {
        this.listPaymentMethods = listPaymentMethods;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return listPaymentMethods.size();
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
        view = this.inflater.inflate(R.layout.item_payment_method, null);
        TextView listItemPaymentMethod = view.findViewById(R.id.listItemPaymentMethod);
        listItemPaymentMethod.setText(listPaymentMethods.get(i).getName());

        Button btnUpdatePaymentMethod = view.findViewById(R.id.btnUpdatePaymentMethod);

        btnUpdatePaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updatePaymentMethod(listPaymentMethods.get(i));
            }
        });
        Button btnDeletePaymentMethod = view.findViewById(R.id.btnDeletePaymentMethod);
        btnDeletePaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.deletePaymentMethod(listPaymentMethods.get(i));
            }
        });

        return view;
    }
}
