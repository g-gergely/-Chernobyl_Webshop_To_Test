package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.BasketDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.util.Util;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;

public class BasketDaoMem implements BasketDao {

    private static HashMap<Product, Integer> data = new HashMap<>();
    private static BasketDaoMem instance = null;

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());



    private BasketDaoMem() {
    }

    @Override
    public int getSize() {
        int counter = 0;
        Iterator hmIterator = data.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            counter += (int) mapElement.getValue();
        }
        if(logger.isTraceEnabled()){
            logger.trace("This is the size of the cart: {}", counter);
        }
        return counter;
    }

    public static BasketDaoMem getInstance() {
        if (instance == null) {
            instance = new BasketDaoMem();
        }
        return instance;
    }

    @Override
    public float getBasketSumm() {
        if(data.size() == 0) return 0;
        float result = 0;
        for (Map.Entry mapElement : data.entrySet()) {
            Product key = (Product) mapElement.getKey();
            float price = key.getDefaultPrice();
            result += price * (int) mapElement.getValue();
        }
        if (logger.isTraceEnabled()){
            logger.trace("Total: {}", result);
        }
        return result;
    }

    @Override
    public void addProduct(int id) {


    }

    @Override
    public HashMap<Product, Integer> allProductinBasket() {
        for (Product product : data.keySet()) {
            System.out.println("This is the key: " + product.toString());
            System.out.println("This is the value: " + data.get(product));

        }
        return data;
    }

    @Override
    public void removeProduct(int id) {

    }

    @Override
    public void addAllProduct(HashMap<String, Integer> products) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        clearBasket();
        Util.getFuckinkeys();
        if (products.size() > 0) {
            for (String key : products.keySet()) {
                data.put(productDataStore.find(Integer.parseInt(key)), products.get(key));

            }
        }

    }

    @Override
    public void clearBasket() {
        data.clear();
    }


}
