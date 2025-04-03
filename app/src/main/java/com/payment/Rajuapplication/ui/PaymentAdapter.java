package com.payment.Rajuapplication.ui;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.payment.Rajuapplication.R;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private List<Payment> paymentList;
    private final OnPaymentDeleteListener deleteListener;

    public PaymentAdapter(List<Payment> paymentList, OnPaymentDeleteListener deleteListener) {
        this.paymentList = paymentList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = paymentList.get(position);
        holder.tvPaymentType.setText(payment.getType() + " - ₹" + payment.getAmount());

        // Show extra details for Bank Transfer & Credit Card
        if (!payment.getProvider().isEmpty()) {
            holder.tvProvider.setText("Provider: " + payment.getProvider());
            holder.tvTransactionId.setText("Txn ID: " + payment.getTransactionReference());
            holder.tvProvider.setVisibility(View.VISIBLE);
            holder.tvTransactionId.setVisibility(View.VISIBLE);
        } else {
            holder.tvProvider.setVisibility(View.GONE);
            holder.tvTransactionId.setVisibility(View.GONE);
        }

        // Delete button listener
        holder.btnDelete.setOnClickListener(v -> {
            deleteListener.onDelete(payment);
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public void updateList(List<Payment> newList) {
        this.paymentList = newList;
        notifyDataSetChanged();
    }

    static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView tvPaymentType, tvProvider, tvTransactionId;
        ImageView btnDelete;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPaymentType = itemView.findViewById(R.id.tvPaymentType);
            tvProvider = itemView.findViewById(R.id.tvProvider);
            tvTransactionId = itemView.findViewById(R.id.tvTransactionId);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnPaymentDeleteListener {
        void onDelete(Payment payment);
    }
}