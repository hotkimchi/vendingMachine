/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.dao;

import com.sg.floormastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class DaoFileImpl implements FloorMasteryDao {

    public static final String DELIMITER = "::";
    private List<Order> memOrders = new ArrayList();
    private List<Order> loadOrders = new ArrayList();
    private Map<LocalDate, List<Order>> editedOrders = new HashMap();
    private int currentOrderCount = 0;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdduuuu");
    private Map<String, BigDecimal> taxMap = new HashMap();
    private StateAndTaxDao taxDao;
    private Map<String, ArrayList<BigDecimal>> productMap = new HashMap();
    private ProductInfoDao productDao;
    
    public DaoFileImpl (StateAndTaxDao taxDao, ProductInfoDao productDao) throws 
            DaoPersistenceException{
        this.taxDao = taxDao;
        this.productDao = productDao;
        this.taxMap = taxDao.readStateAndTaxes();
        this.productMap = productDao.readProductInfo();
    }
    
    @Override
    public List<String> getStates(){
        final List<String> stateList= taxMap.keySet()
                .stream()
                .collect(Collectors.toList());
        return stateList;
    }
    
    @Override
    public List<String> getProductNames(){
        final List<String> productList= productMap.keySet()
                .stream()
                .collect(Collectors.toList());
        return productList;
    }
    
    private void loadOrders(LocalDate date) throws DaoPersistenceException {
        String orderDate = date.format(formatter);
        Scanner myScanner = new Scanner(System.in);
        try {
            myScanner = new Scanner(new BufferedReader(
                    new FileReader("Orders_" + orderDate + ".txt")));
        } catch (FileNotFoundException e) {
            throw new DaoPersistenceException("-_- Could not load Orders_" + orderDate + "'s order "
                    + "data into memory.", e);
        }

        String currentLine;
        String[] currentToken;
        while (myScanner.hasNextLine()) {
            currentLine = myScanner.nextLine();
            currentToken = currentLine.split(DELIMITER);
            if (!currentLine.startsWith("Orders")) {
                Order oldOrder = new Order();
                int orderNumber = Integer.parseInt(currentToken[0]);
                oldOrder.setOrderNumber(orderNumber);
                oldOrder.setCustomerName(currentToken[1]);
                oldOrder.setState(currentToken[2]);
                BigDecimal taxRate = new BigDecimal(currentToken[3]);
                oldOrder.setTaxRate(taxRate);
                oldOrder.setProductType(currentToken[4]);
                BigDecimal area = new BigDecimal(currentToken[5]);
                oldOrder.setArea(area);
                BigDecimal costPerSqrFoot = new BigDecimal(currentToken[6]);
                oldOrder.setCostPerSqrFoot(costPerSqrFoot);
                BigDecimal laborCostPerSqrFoot = new BigDecimal(currentToken[7]);
                oldOrder.setLaborCostPerSqrFoot(laborCostPerSqrFoot);
                BigDecimal materialCost = new BigDecimal(currentToken[8]);
                oldOrder.setMaterialCost(materialCost);
                BigDecimal laborCost = new BigDecimal(currentToken[9]);
                oldOrder.setLaborCost(laborCost);
                BigDecimal totalTax = new BigDecimal(currentToken[10]);
                oldOrder.setTotalTax(totalTax);
                BigDecimal total = new BigDecimal(currentToken[11]);
                oldOrder.setTotal(total);
                loadOrders.add(oldOrder);
            }
        }
        myScanner.close();

    }

    @Override
    public void saveAndWriteOrders(int operation) throws DaoPersistenceException {
        if (operation == 1) {
            writeNewOrders();
        } else if (operation == 3){
            memOrders.remove(memOrders.size() - 1);
        } else {
            writeNewOrders();
            writeOldOrders();
        }
        
    }

    private void writeNewOrders() throws DaoPersistenceException {
        LocalDate currentOriginalDate = LocalDate.now();
        String currentDate = currentOriginalDate.format(formatter);

        File f = new File("Orders_" + currentDate + ".txt");
        boolean fileThere = false;
        if (f.exists() && !f.isDirectory()) {
            fileThere = true;
            Scanner myScanner = new Scanner(System.in);
            try {
                myScanner = new Scanner(new BufferedReader(
                        new FileReader("Orders_" + currentDate + ".txt")));
            } catch (FileNotFoundException e) {
                throw new DaoPersistenceException("-_- Could not load today's order "
                        + "data into memory.", e);
            }

            String currentLine;
            String[] currentToken;
            while (myScanner.hasNextLine()) {
                currentLine = myScanner.nextLine();
                currentToken = currentLine.split(DELIMITER);
                if (!currentLine.startsWith("Orders")) {
                    int count = Integer.parseInt(currentToken[0]);
                    currentOrderCount = count;
                }
            }
            myScanner.close();
        }

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter("Orders_" + currentDate + ".txt", true));
        } catch (IOException e) {
            throw new DaoPersistenceException("Could not save order data.", e);
        }
        if (fileThere == false) {
            out.println("Orders for" + DELIMITER + currentDate);
        }
        for (Order order : memOrders) {
            currentOrderCount++;
            order.setOrderNumber(currentOrderCount);
            out.println(order.getOrderNumber() + DELIMITER
                    + order.getCustomerName() + DELIMITER
                    + order.getState() + DELIMITER
                    + order.getTaxRate() + DELIMITER
                    + order.getProductType() + DELIMITER
                    + order.getArea() + DELIMITER
                    + order.getCostPerSqrFoot() + DELIMITER
                    + order.getLaborCostPerSqrFoot() + DELIMITER
                    + order.getMaterialCost() + DELIMITER
                    + order.getLaborCost() + DELIMITER
                    + order.getTotalTax() + DELIMITER
                    + order.getTotal());

            out.flush();
        }
        out.close();
        currentOrderCount = 0;
        memOrders.clear();
    }

    private void writeOldOrders() throws DaoPersistenceException {
        if (!editedOrders.isEmpty()) {
            for (LocalDate date : editedOrders.keySet()) {
                String orderDate = date.format(formatter);
                PrintWriter out;

                try {
                    out = new PrintWriter(new FileWriter("Orders_" + orderDate + ".txt"));
                } catch (IOException e) {
                    throw new DaoPersistenceException("Could not save order data.", e);
                }

                out.println("Orders for" + DELIMITER + date);
                List<Order> orders = editedOrders.get(date);
                for (Order order : orders) {
                    order.setOrderNumber(order.getOrderNumber());
                    out.println(order.getOrderNumber() + DELIMITER
                            + order.getCustomerName() + DELIMITER
                            + order.getState() + DELIMITER
                            + order.getTaxRate() + DELIMITER
                            + order.getProductType() + DELIMITER
                            + order.getArea() + DELIMITER
                            + order.getCostPerSqrFoot() + DELIMITER
                            + order.getLaborCostPerSqrFoot() + DELIMITER
                            + order.getMaterialCost() + DELIMITER
                            + order.getLaborCost() + DELIMITER
                            + order.getTotalTax() + DELIMITER
                            + order.getTotal());

                    out.flush();
                    currentOrderCount++;
                }
                out.close();
            }
            currentOrderCount = 1;
            editedOrders.clear();
        }
    }

    @Override
    public List<Order> displayOrders(LocalDate orderDate) throws DaoPersistenceException {
        loadOrders.clear();
        List<Order> ordersByDate = new ArrayList();
        loadOrders(orderDate);
        ordersByDate = loadOrders;
        return ordersByDate;
    }

    @Override
    public Order addOrder(Order order) {
        order = getTaxAndCosts(order);
        order = doMath(order);
        memOrders.add(order);
        return order;
    }

    private Order doMath(Order order) {
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal percentage = order.getTaxRate().divide(hundred);

        BigDecimal materialCost = order.getArea()
                .multiply(order.getCostPerSqrFoot())
                .setScale(2, RoundingMode.HALF_UP);
        order.setMaterialCost(materialCost);

        BigDecimal laborCost = order.getArea()
                .multiply(order.getLaborCostPerSqrFoot())
                .setScale(2, RoundingMode.HALF_UP);
        order.setLaborCost(laborCost);

        BigDecimal total = materialCost.add(laborCost).setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = total.multiply(percentage).setScale(2, RoundingMode.HALF_UP);
        order.setTotalTax(tax);
        total = total.add(tax).setScale(2, RoundingMode.HALF_UP);
        order.setTotal(total);
        return order;
    }
    
    private Order getTaxAndCosts(Order order) {

        final String STATE = order.getState();

        List<BigDecimal> taxRate = taxMap.keySet()
                .stream()
                .filter((state) -> (STATE.equals(state)))
                .map(state -> taxMap.get(state))
                .collect(Collectors.toList());
        order.setTaxRate(taxRate.get(0));

        List<BigDecimal> costs = new ArrayList();
        for (String productType : productMap.keySet()) {
            if (productType.equals(order.getProductType())) {
                costs = productMap.get(productType);
                order.setCostPerSqrFoot(costs.get(0));
                order.setLaborCostPerSqrFoot(costs.get(1));
            }
        }
        return order;
    }

    @Override
    public Order editOrder(LocalDate orderDate, Order order) {
        int x = loadOrders.indexOf(order);
        order = getTaxAndCosts(order);
        order = doMath(order);
        loadOrders.set(x, order);
        if (editedOrders.isEmpty()) {
            editedOrders.put(orderDate, loadOrders);
        } else {
            List<Order> updateOrders = editedOrders.get(orderDate);
            updateOrders = loadOrders;
            editedOrders.put(orderDate, updateOrders);
        }
        return order;
    }

    @Override
    public List<Order> removeOrder(LocalDate orderDate, Order order) {
        loadOrders.remove(order);
        if (editedOrders.isEmpty()) {
            editedOrders.put(orderDate, loadOrders);
        } else {
            List<Order> updateOrders = editedOrders.get(orderDate);
            updateOrders = loadOrders;
            editedOrders.put(orderDate, updateOrders);
        }
        return loadOrders;
    }

}
