package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.invoke.MethodHandles;


@WebListener
public class Initializer implements ServletContextListener {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("{}", getClass().getSimpleName());
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductCategoryDao productCategoryDb = ProductCategoryDaoJdbc.getInstance();
        if (logger.isDebugEnabled()) {
            logger.debug("The productDataStore is: {}", productDataStore);
            logger.debug("The productDataStore is: {}", productCategoryDataStore);
            logger.debug("The supplierDataStore is: {}", supplierDataStore);
            logger.debug("The productCategoryDb is: {}", productCategoryDb);
        }


        //setting up a new supplier

        Supplier nonInfected = new Supplier("Non Infected", "These products are rescued before the explosion...");
        supplierDataStore.add(nonInfected);

        Supplier infected = new Supplier("Infected", "These products are rest in Chernobyl since the explosion");
        supplierDataStore.add(infected);

        //setting up a new product category
        ProductCategory vehicle = new ProductCategory("Vehicles", "Trucks & Cars", "These vehicles are try to prevent the catastrophe");
        //productCategoryDataStore.addCategory(vehicle);
        System.out.println(vehicle.getDepartment() + " department  ");
        //productCategoryDb.add(vehicle);
        productCategoryDataStore.addCategory(vehicle);

        ProductCategory objects = new ProductCategory("Relics", "Relic", "These relics are comes from the territory of Chernobyl.");
        productCategoryDataStore.addCategory(objects);

        ProductCategory suits = new ProductCategory("Protector Suits", "Suits", "These suits are help to defense the owner.");
        productCategoryDataStore.addCategory(suits);

        ProductCategory medicine = new ProductCategory("Medicines", "Medicine", "...");
        productCategoryDataStore.addCategory(medicine);

        ProductCategory humanResources = new ProductCategory("HR", "Human workers", "helps a lot near Chernobyl");
        productCategoryDataStore.addCategory(humanResources);

        //setting up products and printing it

        //non-infected products
        productDataStore.addProduct(new Product("Fire Engine", 9990, "RUB", "A fire engine is a vehicle designed primarily for firefighting operations", vehicle, nonInfected));
        productDataStore.addProduct(new Product("Police Car", 6690, "RUB", "A police car is a ground vehicle used by police for transportation during patrols and to enable them to respond to incidents and chases. ", vehicle, nonInfected));
        productDataStore.addProduct(new Product("Helicopter", 119999, "RUB", "Helicopter is used to help you to spray the mosquitoes above your garden!", vehicle, nonInfected));

        System.out.println(productDataStore);
        productDataStore.addProduct(new Product("Fire Extinguish", 119, "RUB", "Perfect protection if the party break loose", objects, nonInfected));
        productDataStore.addProduct(new Product("Gas Mask", 39, "RUB", "The gas mask is a mask used to protect the wearer from inhaling airborne pollutants and toxic gases.", objects, nonInfected));
        productDataStore.addProduct(new Product("Graphite Block", 89, "RUB", "The best flux moderator to your homemade reactor!", objects, nonInfected));
        productDataStore.addProduct(new Product("Radiation Detector", 99, "RUB", "Mete the radiation to 3.6!", objects, nonInfected));
        productDataStore.addProduct(new Product("Radiation Barrel", 249, "RUB", "Perfect decoration to the garden or perfect Party Beer Barrel", objects, nonInfected));


        productDataStore.addProduct(new Product("FireFighter Hat", 89, "RUB", "The ultra high temperature (TPR) edge trim will not melt or drip.", suits, nonInfected));
        productDataStore.addProduct(new Product("FireFighter Suit", 199, "RUB", "Permanent flame retardant, heat insulation, waterproof, breathable, flexible, well-adapted.", suits, nonInfected));
        productDataStore.addProduct(new Product("Radiation Protector Suit", 249, "RUB", "Perfect protection for every radiation effect! Supposedly....", suits, nonInfected));

        productDataStore.addProduct(new Product("Hair Fall Protector", 99, "RUB", "This product is meant to prevent hair loss, dandruff and premature graying.", medicine, nonInfected));
        productDataStore.addProduct(new Product("Iodine Capsule", 89, "RUB", "This powerful and exclusive formula delivers a strong boost to your body’s iodine supply.", medicine, nonInfected));

        productDataStore.addProduct(new Product("Miner Boii", 650, "RUB", "He can works more then 5 days along without rest. But sometimes works without clothes.", humanResources, nonInfected));
        productDataStore.addProduct(new Product("Mr. We did everything right guy", 999, "RUB", "He always do everything well! He do not know what is a mistake", humanResources, nonInfected));
        productDataStore.addProduct(new Product("'What's this on the floor?' Fireman", 890, "RUB", "He extinguish everything, anytime! Does not matter what is a cost.", humanResources, nonInfected));

        //infected products
        productDataStore.addProduct(new Product("Infected Fire Engine", 9990, "RUB", "A fire engine is a vehicle designed primarily for firefighting operations", vehicle, infected));
        productDataStore.addProduct(new Product("Infected Helicopter", 119999, "RUB", "Truck is used to help you to spray the mosquitoes above your garden!", vehicle, infected));

        productDataStore.addProduct(new Product("Infected FireFighter Suit", 199, "RUB", "Permanent flame retardant, heat insulation, waterproof, breathable, flexible, well-adapted.", suits, infected));
        productDataStore.addProduct(new Product("Infected Radiation Protector Suit", 249, "RUB", "Perfect protection for every radiation effect! Supposedly....", suits, infected));

        productDataStore.addProduct(new Product("Infected Miner Boii", 650, "RUB", "He can works more then 5 days along without rest. But sometimes works without clothes.", humanResources, infected));


    }
}
