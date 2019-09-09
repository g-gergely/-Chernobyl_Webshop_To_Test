package com.codecool.shop.dao;


import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public interface BasketDao {


    void addProduct(int id);

    void removeProduct(int id);

    HashMap<Product, Integer> allProductinBasket();

    void addAllProduct(HashMap<String, Integer> idsAndPieces);

    void clearBasket();

    int getSize();

    float getBasketSumm();




}
