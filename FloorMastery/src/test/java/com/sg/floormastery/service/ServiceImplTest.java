/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.service;

import com.sg.floormastery.dao.DaoPersistenceException;
import com.sg.floormastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class ServiceImplTest {
    
     private Order testOrder = new Order();
    private FloorMasteryService service;
    private BigDecimal testArea = new BigDecimal("100.00");
    private BigDecimal testTaxRate = new BigDecimal("6.25");
    private BigDecimal testCostPerSqrFt = new BigDecimal("5.15");
    private BigDecimal testLaborCostPerSqrFt = new BigDecimal("4.75");
    private BigDecimal testMaterialCost = new BigDecimal("515.00");
    private BigDecimal testLaborCost = new BigDecimal("475.00");
    private BigDecimal testTotalTax = new BigDecimal("61.88");
    private BigDecimal testTotal = new BigDecimal("1051.88");
    private LocalDate testDate = LocalDate.parse("12311999", DateTimeFormatter.ofPattern("MMdduuuu"));
    private LocalDate passThroughDate = LocalDate.now();
    private List<Order> testList = new ArrayList();
    
    public ServiceImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("service", ServiceImpl.class);
        this.testOrder.setOrderNumber(1);
        this.testOrder.setCustomerName("Wise");
        this.testOrder.setState("OH");
        this.testOrder.setTaxRate(testTaxRate);
        this.testOrder.setProductType("Wood");
        this.testOrder.setArea(testArea);
        this.testOrder.setCostPerSqrFoot(testCostPerSqrFt);
        this.testOrder.setLaborCostPerSqrFoot(testLaborCostPerSqrFt);
        this.testOrder.setMaterialCost(testMaterialCost);
        this.testOrder.setLaborCost(testLaborCost);
        this.testOrder.setTotalTax(testTotalTax);
        this.testOrder.setTotal(testTotal);
        testList.add(testOrder);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addOrder method, of class ServiceImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        assertEquals(testOrder.getCustomerName(), service.addOrder(testOrder).getCustomerName());
    }
    
    @Test
    public void testInvalidStates() throws Exception{
        Order wrongState = testOrder;
        wrongState.setState("MN");
        Order nullState = testOrder;
        nullState.setState("");
        
        try{
            service.addOrder(wrongState);
            fail("State was not thrown");
        } catch(ServiceValidateOrderException e){
            
        }
        try{
            service.addOrder(nullState);
            fail("Null was not thrown");
        } catch(ServiceValidateOrderException e){
            return;
        }
    }
    
    @Test
    public void testInvalidProducts() throws Exception{
        Order wrongProduct = testOrder;
        wrongProduct.setProductType("LightSaber");
        Order nullProduct = testOrder;
        nullProduct.setProductType("");
        
        try{
            service.addOrder(wrongProduct);
            fail("Product was not thrown");
        } catch (ServiceValidateOrderException e){
            
        }
        try{
            service.addOrder(nullProduct);
            fail("Null was not thrown");
        } catch (ServiceValidateOrderException e){
            return;
        }
    }

    /**
     * Test of getAllOrdersByDate method, of class ServiceImpl.
     */
    @Test
    public void testGetAllOrdersByDate() throws Exception {
        assertEquals(testList.size(),
                service.getAllOrdersByDate(passThroughDate).size());
        assertEquals(testList.get(0).getCustomerName(),
                service.getAllOrdersByDate(passThroughDate).get(0).getCustomerName());
    }
    
    @Test
    public void testInvalidDates() throws Exception {
        LocalDate zeroDate = null;
        LocalDate beforeDate = LocalDate.parse("06062017", DateTimeFormatter.ofPattern("MMdduuuu"));
        LocalDate afterDate = passThroughDate.plusDays(1);
        
        try{
            service.getAllOrdersByDate(zeroDate);
            fail("The null was passed through");
        } catch (ServiceLocalDateException e){
            
        }
        
        try{
            service.getAllOrdersByDate(beforeDate);
            fail("A before date was passed through");
        } catch (ServiceLocalDateException e){
            
        }
        
        try{
            service.getAllOrdersByDate(afterDate);
            fail("An after date was passed through");
        } catch (ServiceLocalDateException e){
            return;
        }
    }

    /**
     * Test of editAnOrder method, of class ServiceImpl.
     */
    @Test
    public void testEditAnOrder() throws Exception {
        assertEquals(testOrder.getCustomerName(),
                service.editAnOrder(passThroughDate, testOrder).getCustomerName());
    }

    /**
     * Test of removeAnOrder method, of class ServiceImpl.
     */
    @Test
    public void testRemoveAnOrder() throws Exception {
        List<Order> removeList = service.getAllOrdersByDate(passThroughDate);
        removeList = service.removeAnOrder(passThroughDate, removeList.get(0));
        assertTrue(removeList.size() == 0);
        assertTrue(removeList.isEmpty());
    }

    /**
     * Test of saveWork method, of class ServiceImpl.
     */
    @Test
    public void testSaveWork() throws Exception {
        try{
            service.saveWork(1);
            fail("Training mode got passed through!!!");
        } catch (ServiceProductionException e){
            return;
        }
    }
    
    @Test
    public void testgetStates() {
        assertEquals(testOrder.getState(), service.getStates().get(0));
    }
    
    @Test
    public void testgetProducts() {
        assertEquals(testOrder.getProductType(), service.getProducts().get(0));
    }
    
}
