package com.payment.Rajuapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.payment.Rajuapplication.ui.AddPaymentDialog;
import com.payment.Rajuapplication.ui.Payment;
import com.payment.Rajuapplication.ui.PaymentAdapter;

public class MainActivity extends AppCompatActivity {
    private TextView tvTotalAmount,tvNoRecord;
    private RecyclerView recyclerView;
    private Button btnAddPayment, btnSave;
    private PaymentAdapter adapter;
    private List<Payment> paymentList = new ArrayList<>();
    private final List<String> allPaymentTypes = Arrays.asList("Cash", "Bank Transfer", "Credit Card");
    private final String FILE_NAME = "BackupPayment.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvNoRecord = findViewById(R.id.tv_no_record);
        recyclerView = findViewById(R.id.recyclerView);
        btnAddPayment = findViewById(R.id.btnAddPayment);
        btnSave = findViewById(R.id.btnSave);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PaymentAdapter(paymentList, this::removePayment);
        recyclerView.setAdapter(adapter);

        // Load saved payments
        loadSavedPayments();

        // Add Payment button click
        btnAddPayment.setOnClickListener(v -> openAddPaymentDialog());

        // Save button click
        btnSave.setOnClickListener(v -> savePaymentsToFile());
        if (paymentList.isEmpty()){
            btnSave.setEnabled(false);
            tvNoRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            btnSave.setEnabled(true);
            tvNoRecord.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }



    private void openAddPaymentDialog() {
        // Filter available payment types
        List<String> availableTypes = new ArrayList<>(allPaymentTypes);
        for (Payment p : paymentList) {
            availableTypes.remove(p.getType());
        }

        if (availableTypes.isEmpty()) {
            return; // No more payment types can be added
        }

        Dialog dialog = new AddPaymentDialog(this, availableTypes, payment -> {
            paymentList.add(payment);
            adapter.notifyDataSetChanged();
            updateTotalAmount();
        });
        dialog.show();
    }

    private void removePayment(Payment payment) {
        paymentList.remove(payment);
        adapter.notifyDataSetChanged();
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        int total = 0;
        for (Payment pdata : paymentList) {
            total += pdata.getAmount();
        }
        tvTotalAmount.setText("Total Amount = ₹ " + total);
        if (total>0){
            btnSave.setEnabled(true);
            tvNoRecord.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            tvNoRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

    }

    private void savePaymentsToFile() {
        File file = new File(getFilesDir(), FILE_NAME);
        try (FileWriter writer = new FileWriter(file)) {
            new Gson().toJson(paymentList, writer);
            Toast.makeText(this, "Payments backup File saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSavedPayments() {
        File file = new File(getFilesDir(), FILE_NAME);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                StringBuilder json = new StringBuilder();
                while (scanner.hasNextLine()) {
                    json.append(scanner.nextLine());
                }
                paymentList = new Gson().fromJson(json.toString(), new TypeToken<List<Payment>>(){}.getType());
                if (paymentList == null) paymentList = new ArrayList<>();
                adapter.updateList(paymentList);
                updateTotalAmount();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}