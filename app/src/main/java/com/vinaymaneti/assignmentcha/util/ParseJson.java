package com.vinaymaneti.assignmentcha.util;

import android.content.Context;

import com.vinaymaneti.assignmentcha.model.FirstSetTransactionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 04/12/16.
 */

public class ParseJson {
    public static List<FirstSetTransactionModel> loadJSONFromAsset(Context context, String jsonFileName) {
        List<FirstSetTransactionModel> firstSetTransactionModelArrayList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FirstSetTransactionModel firstSetTransactionData = new FirstSetTransactionModel();
                firstSetTransactionData.setAmount(jsonObject.getString("amount"));
                firstSetTransactionData.setSku(jsonObject.getString("sku"));
                firstSetTransactionData.setCurrency(jsonObject.getString("currency"));

                //add all values to ArrayList
                firstSetTransactionModelArrayList.add(firstSetTransactionData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return firstSetTransactionModelArrayList;
    }
}
