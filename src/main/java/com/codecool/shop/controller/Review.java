package com.codecool.shop.controller;

import com.codecool.shop.dao.BasketDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.BasketDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.codecool.shop.util.Util;

@WebServlet(urlPatterns = {"/review"})
public class Review extends HttpServlet {
    private static String items;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        items = req.getParameter("items");

        HashMap<String, Integer> inBasket = Util.generateMapfromStringRequestIds(items);
        System.out.println(inBasket.containsKey("2"));
        BasketDao basket = BasketDaoMem.getInstance();
        basket.addAllProduct(inBasket);
        //basket.clearBasket();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("basket", basket.allProductinBasket());
        context.setVariable("matyi", "matyi");
        context.setVariable("basketTotal", basket.getBasketSumm());
        engine.process("product/review.html", context, resp.getWriter());


    }

    public static String getItems() {
        return items;
    }


}
