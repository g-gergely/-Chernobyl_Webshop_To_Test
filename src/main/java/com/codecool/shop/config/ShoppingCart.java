package com.codecool.shop.config;


import com.codecool.shop.controller.Review;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.codecool.shop.dao.BasketDao;
import com.codecool.shop.dao.implementation.BasketDaoMem;
import com.codecool.shop.model.Basket;
import com.codecool.shop.util.Util;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;


@WebListener
public class ShoppingCart implements ServletContextListener{
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void contextInitialized(ServletContextEvent sce){
        logger.info(getClass().getSimpleName());
        BasketDao basket =  BasketDaoMem.getInstance();
        if(logger.isDebugEnabled()){
            logger.debug("Basket: {}", basket);
        }
        String items = Review.getItems();
        if(logger.isTraceEnabled()){
            logger.trace("Items: {}", items);
        }
        HashMap<String, Integer> inBasket = Util.generateMapfromStringRequestIds(items);
        basket.addAllProduct(inBasket);
        if(logger.isDebugEnabled()){
            logger.debug("{} added to {}", inBasket, basket);
        }



    }
}
