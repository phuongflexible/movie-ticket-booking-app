package com.example.bookticketapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookticketapp.R;
import com.example.bookticketapp.adapters.PaymentMethodAdapter;
import com.example.bookticketapp.dao.PaymentMethodQuery;
import com.example.bookticketapp.models.PaymentMethod;

public class AddPaymentMethodActivity extends AppCompatActivity {

    EditText editPaymentName;
    Button btnConfirmAddPayment;
    PaymentMethodQuery payQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_method);

        editPaymentName = findViewById(R.id.editPaymentName);
        btnConfirmAddPayment = findViewById(R.id.btnConfirmAddPayment);
        payQuery = new PaymentMethodQuery(this);

        btnConfirmAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editPaymentName.getText().toString().trim();

                if (name.equals(""))
                {
                    Toast.makeText(AddPaymentMethodActivity.this, "Không thêm được do thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkName = payQuery.checkName(name);
                    if (checkName == true)
                    {
                        PaymentMethod payment = new PaymentMethod(name);
                        Boolean insert = payQuery.addPaymentMethod(payment);
                        if (insert == true)
                        {
                            Toast.makeText(AddPaymentMethodActivity.this, "Thêm phương thức thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddPaymentMethodActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(AddPaymentMethodActivity.this, "Thêm phương thức thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(AddPaymentMethodActivity.this, "Đã có phương thức này", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });
    }
}