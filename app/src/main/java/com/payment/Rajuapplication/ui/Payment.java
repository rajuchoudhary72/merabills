package com.payment.Rajuapplication.ui;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment {
    private String type;
    private int amount;
    private String provider;
    private String transactionReference;

    public Payment(String type, int amount, String provider, String transactionReference) {
        this.type = type;
        this.amount = amount;
        this.provider = provider;
        this.transactionReference = transactionReference;
    }

    public String getType() { return type; }
    public int getAmount() { return amount; }
    public String getProvider() { return provider; }
    public String getTransactionReference() { return transactionReference; }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("amount", amount);
        json.put("provider", provider);
        json.put("transactionReference", transactionReference);
        return json;
    }

    public static Payment fromJson(JSONObject json) throws JSONException {
        return new Payment(
                json.getString("type"),
                json.getInt("amount"),
                json.optString("provider", ""),
                json.optString("transactionReference", "")
        );
    }
}
