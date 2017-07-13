/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.dao;

import com.sg.floormastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class DaoStubImpl implements FloorMasteryDao{
    
    private Order lonleyOrder = new Order();
    private List<Order> lonelyList = new ArrayList();
    private BigDecimal testArea = new BigDecimal("100.00");
    private BigDecimal testTaxRate = new BigDecimal("6.25");
    private BigDecimal testCostPerSqrFt = new BigDecimal("5.15");
    private BigDecimal testLaborCostPerSqrFt = new BigDecimal("4.75");
    private BigDecimal testMaterialCost = new BigDecimal("515.00");
    private BigDecimal testLaborCost = new BigDecimal("475.00");
    private BigDecimal testTotalTax = new BigDecimal("61.88");
    private BigDecimal testTotal = new BigDecimal("1051.88");
    private List<String> oneState = new ArrayList();
    private List<String> oneProduct = new ArrayList();
    
    public DaoStubImpl (){
        this.lonleyOrder.setOrderNumber(1);
        this.lonleyOrder.setCustomerName("Wise");
        this.lonleyOrder.setState("OH");
        this.lonleyOrder.setTaxRate(testTaxRate);
        this.lonleyOrder.setProductType("Wood");
        this.lonleyOrder.setArea(testArea);
        this.lonleyOrder.setCostPerSqrFoot(testCostPerSqrFt);
        this.lonleyOrder.setLaborCostPerSqrFoot(testLaborCostPerSqrFt);
        this.lonleyOrder.setMaterialCost(testMaterialCost);
        this.lonleyOrder.setLaborCost(testLaborCost);
        this.lonleyOrder.setTotalTax(testTotalTax);
        this.lonleyOrder.setTotal(testTotal);
        
        this.lonelyList.add(lonleyOrder);
        this.oneState.add("OH");
        this.oneProduct.add("Wood");
    }

    @Override
    public void saveAndWriteOrders(int operation) throws DaoPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> displayOrders(LocalDate orderDate) throws DaoPersistenceException {
        return lonelyList;
    }

    @Override
    public Order addOrder(Order order) {
        return lonleyOrder;
    }

    @Override
    public Order editOrder(LocalDate orderDate, Order order) {
        return lonleyOrder;
    }

    @Override
    public List<Order> removeOrder(LocalDate orderDate, Order order) {
        lonelyList.remove(order);
        return lonelyList;
    }

    @Override
    public List<String> getProductNames() {
        return oneProduct;
    }

    @Override
    public List<String> getStates() {
        return oneState;
    }
    
}
