package com.codecool.shop.dao;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {

    void addCategory(ProductCategory category);
    ProductCategory find(int id);
    void remove(int id);
    List<ProductCategory> getAll();
    List<ProductCategory> clear();

}
