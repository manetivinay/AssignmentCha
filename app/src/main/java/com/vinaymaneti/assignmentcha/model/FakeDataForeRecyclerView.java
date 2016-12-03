package com.vinaymaneti.assignmentcha.model;

import java.util.ArrayList;

/**
 * Created by vinay on 02/12/16.
 */

public class FakeDataForeRecyclerView {

    private String productName;
    private String transcation;

    public FakeDataForeRecyclerView(String productName, String transcation) {
        this.productName = productName;
        this.transcation = transcation;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTranscation() {
        return transcation;
    }

    public void setTranscation(String transcation) {
        this.transcation = transcation;
    }

    public static ArrayList<FakeDataForeRecyclerView> createFakeDatProductList() {
        ArrayList<FakeDataForeRecyclerView> foreRecyclerViews = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            foreRecyclerViews.add(new FakeDataForeRecyclerView(i + " product", i + " transaction"));
        }
        return foreRecyclerViews;
    }
}
