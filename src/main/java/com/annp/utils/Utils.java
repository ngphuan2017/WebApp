package com.annp.utils;

import com.annp.pojo.Cart;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static Map<String, String> cartStats(Map<Integer, Cart> cart) {
        int totalCount = 0;
        int totalQuantity = 0;
        long totalAmount = 0;

        if (cart != null)
            for (Cart c: cart.values()) {
                totalQuantity += c.getQuantity();
                totalAmount += c.getQuantity() * c.getPrice();
                totalCount += 1;
            }


        Map<String, String> re = new HashMap<>();
        re.put("totalQuantity", String.valueOf(totalQuantity));
        re.put("totalAmount", String.valueOf(totalAmount));
        re.put("totalCount", String.valueOf(totalCount));

        return re;
    }
}
