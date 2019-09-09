package com.codecool.shop.model;

import java.util.ArrayList;

public class Basket {
    private ArrayList<Product> basketList = new ArrayList<>();

    public void addProductToBasket(Product product){
        basketList.add(product);
    }

    public void removeProductFromBasket(Product product){
        basketList.remove(product);
    }
}
