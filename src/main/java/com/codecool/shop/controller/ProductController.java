package com.codecool.shop.controller;

import com.codecool.shop.dao.BasketDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.BasketDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.util.Util;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String product = req.getParameter("productCat");
        String supplier = req.getParameter("supplierCat");
        String productsInBasket = req.getParameter("cartProducts");
        HashMap<String, Integer> inBasket = Util.fillProductsMap(productsInBasket);
        System.out.println(inBasket.toString());
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDaoMem supplierCategory = SupplierDaoMem.getInstance();
        BasketDao basket = BasketDaoMem.getInstance();
        basket.addAllProduct(inBasket);
        


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("category", productCategoryDataStore.getAll());
        if ((supplier == null || product == null) || (supplier.equals("0") && product.equals("0")) ){
            context.setVariable("products", productDataStore.getAll());
        }else if (product.equals("0")){
            String cart = req.getParameter("filterBasket");
            HashMap<String, Integer> inbb = Util.generateMapfromStringRequestIds(cart);
            basket.addAllProduct(inbb);
            context.setVariable("products", supplierCategory.find(Integer.valueOf(supplier)).getProducts());
        }else if (supplier.equals("0")){
            String cart = req.getParameter("filterBasket");
            HashMap<String, Integer> inbb = Util.generateMapfromStringRequestIds(cart);
            basket.addAllProduct(inbb);
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(Integer.valueOf(product))));
        }
        context.setVariable("supplier", supplierCategory.getAll());
        context.setVariable("basketSize", basket.getSize());

        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());

    }
}
