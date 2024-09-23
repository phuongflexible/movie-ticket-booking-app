package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.dao.LocationQuery;
import com.example.bookticketapp.dao.PaymentMethodQuery;
import com.example.bookticketapp.models.Location;
import com.example.bookticketapp.models.PaymentMethod;

public class UpdatePaymentMethodActivity extends AppCompatActivity {

    EditText editUpdatePaymentName;
    Button btnConfirmUpdatePayment;
    PaymentMethodQuery payQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_payment_method);

        editUpdatePaymentName = findViewById(R.id.editUpdatePaymentName);
        btnConfirmUpdatePayment = findViewById(R.id.btnConfirmUpdatePayment);

        int id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        editUpdatePaymentName.setText(name);

        payQuery = new PaymentMethodQuery(this);

        btnConfirmUpdatePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = editUpdatePaymentName.getText().toString().trim();
                Boolean checkName = payQuery.checkName(newName);
                if (checkName == true)
                {
                    PaymentMethod payment = new PaymentMethod(id, newName);
                    Boolean update = payQuery.updatePaymentMethod(payment);
                    if (update == true)
                    {
                        Toast.makeText(UpdatePaymentMethodActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdatePaymentMethodActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(UpdatePaymentMethodActivity.this, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(UpdatePaymentMethodActivity.this, "Đã có phương thức này", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}