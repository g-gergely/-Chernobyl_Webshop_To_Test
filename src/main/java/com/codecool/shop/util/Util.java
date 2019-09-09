package com.codecool.shop.util;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class Util {

    private static HashMap<String, Integer> productIds = new HashMap<>();
    private static List<Product> productList = new ArrayList<>();

    public static HashMap<String, Integer> fillProductsMap(String data) {
        ProductDao products = ProductDaoMem.getInstance();
        productList = products.getAll();
        try {
            String[] commaSeparatedArr = data.split("\\s*,\\s*");
            System.out.println(commaSeparatedArr);
            if (commaSeparatedArr[0].equals("")) {
                productIds.clear();
                return productIds;}
            else {
                productIds.clear();
                for (String product : commaSeparatedArr) {
                    String[] row = product.split("\\s*:\\s*");
                    Product product1 = products.find(row[0]);
                    String productId = product1.getId() + "";
                    productIds.put(productId, Integer.parseInt(row[1]));
                }
                return productIds;
            }
        }
        catch (NullPointerException e){
            return productIds;
        }
    }


    public static void getFuckinkeys() {
        Iterator hmIterator = productIds.entrySet().iterator();

        // Iterate through the hashmap
        // and addProduct some bonus marks for every student
        System.out.println("HashMap after adding bonus marks:");

        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();

            System.out.println(mapElement.getKey());
        }
    }

    public static HashMap<String, Integer> generateMapfromStringRequestIds(String id) {
        //List<String> result = Arrays.stream(commaSeparatedArr).collect(Collectors.toList());
        try {
            String[] commaSeparatedArr = id.split("\\s*,\\s*");
            if (commaSeparatedArr[0].equals("")) return productIds;
            for (String num : commaSeparatedArr) {
                if (productIds.containsKey(num)) {
                    productIds.put(num, productIds.get(num) + 1);
                } else {
                    productIds.put(num, 1);
                }
            }

            return productIds;
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return productIds;
    }


}
