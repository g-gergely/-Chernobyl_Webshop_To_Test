package com.codecool.shop.dao.implementation;

import com.codecool.shop.config.SqlConnection;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import org.w3c.dom.ls.LSOutput;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {

    private static ProductCategoryDaoJdbc instance = null;

    public static ProductCategoryDaoJdbc getInstance(){
        if (instance == null){
            instance = new ProductCategoryDaoJdbc();
        }
        return instance;
    }

    SqlConnection Sqlconnection = new SqlConnection();


    @Override
    public void addCategory(ProductCategory category)  {
        String query = "INSERT INTO category (id, name, department) VALUES (?,?,?);";
        System.out.println(query);
        try (Connection connection = Sqlconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, category.getId());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setString(3, category.getDepartment());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    @Override
    public ProductCategory find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {

        return null;
    }

    @Override
    public List<ProductCategory> clear() { return null; }
}

