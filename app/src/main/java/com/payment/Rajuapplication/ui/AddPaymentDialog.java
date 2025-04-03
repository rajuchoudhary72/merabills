package com.payment.Rajuapplication.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.payment.Rajuapplication.R;

import java.util.List;

public class AddPaymentDialog extends Dialog {

    private EditText etAmount, etProvider, etTransactionReference;
    private Spinner spinnerPaymentType;
    private Button btnOk, btnCancel;
    private List<String> availablePaymentTypes;
    private OnPaymentAddedListener listener;

    public AddPaymentDialog(@NonNull Context context, List<String> availablePaymentTypes, OnPaymentAddedListener listener) {
        super(context);
        this.availablePaymentTypes = availablePaymentTypes;
        this.listener = listener;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_payment);

        etAmount = findViewById(R.id.etAmount);
        etProvider = findViewById(R.id.etProvider);
        etTransactionReference = findViewById(R.id.etTransactionReference);
        spinnerPaymentType = findViewById(R.id.spinnerPaymentType);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

        // Populate spinner with available payment types
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, availablePaymentTypes);
        spinnerPaymentType.setAdapter(adapter);

        // Show/hide additional fields based on payment type
        spinnerPaymentType.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedType = availablePaymentTypes.get(position);
                if (selectedType.equals("Bank Transfer") || selectedType.equals("Credit Card")) {
                    etProvider.setVisibility(View.VISIBLE);
                    etTransactionReference.setVisibility(View.VISIBLE);
                } else {
                    etProvider.setVisibility(View.GONE);
                    etTransactionReference.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        btnOk.setOnClickListener(v -> {
            String amountStr = etAmount.getText().toString();
            String paymentType = spinnerPaymentType.getSelectedItem().toString();
            String provider = etProvider.getText().toString();
            String transactionRef = etTransactionReference.getText().toString();

            if (!amountStr.isEmpty()) {
                int amount = Integer.parseInt(amountStr);
                listener.onPaymentAdded(new Payment(paymentType, amount, provider, transactionRef));
                dismiss();
            }
        });

        btnCancel.setOnClickListener(v -> dismiss());
    }

    public interface OnPaymentAddedListener {
        void onPaymentAdded(Payment payment);
    }
}