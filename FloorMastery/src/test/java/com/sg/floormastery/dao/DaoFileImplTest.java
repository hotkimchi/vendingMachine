/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.sg.floormastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author apprentice
 */
public class DaoFileImplTest {
    
    static FloorMasteryDao dao;
    private Order testOrder = new Order();
    private BigDecimal testArea = new BigDecimal("100.00");
    private BigDecimal testTaxRate = new BigDecimal("6.25");
    private BigDecimal testCostPerSqrFt = new BigDecimal("5.15");
    private BigDecimal testLaborCostPerSqrFt = new BigDecimal("4.75");
    private BigDecimal testMaterialCost = new BigDecimal("515.00");
    private BigDecimal testLaborCost = new BigDecimal("475.00");
    private BigDecimal testTotalTax = new BigDecimal("61.88");
    private BigDecimal testTotal = new BigDecimal("1051.88");
    private LocalDate testDate = LocalDate.parse("12311999", DateTimeFormatter.ofPattern("MMdduuuu"));
    public DaoFileImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = ctx.getBean("dao", DaoFileImpl.class);
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
     * Test of saveAndWriteOrders method, of class DaoFileImpl.
     */
    @Test
    public void testSaveAndWriteOrders() throws Exception {
    }

    /**
     * Test of displayOrders method, of class DaoFileImpl.
     */
    @Test
    public void testDisplayOrders() throws Exception {
        List<Order> testOrdersByDate = dao.displayOrders(testDate);
        assertTrue(testOrdersByDate.size() == 1);
        
        assertEquals(testOrder.getOrderNumber(), testOrdersByDate.get(0).getOrderNumber());
        assertEquals(testOrder.getCustomerName(), testOrdersByDate.get(0).getCustomerName());
        assertEquals(testOrder.getState(), testOrdersByDate.get(0).getState());
        assertEquals(testOrder.getTaxRate(), testOrdersByDate.get(0).getTaxRate());
        assertEquals(testOrder.getProductType(), testOrdersByDate.get(0).getProductType());
        assertEquals(testOrder.getArea(), testOrdersByDate.get(0).getArea());
        assertEquals(testOrder.getCostPerSqrFoot(), testOrdersByDate.get(0).getCostPerSqrFoot());
        assertEquals(testOrder.getLaborCostPerSqrFoot(), testOrdersByDate.get(0).getLaborCostPerSqrFoot());
        assertEquals(testOrder.getMaterialCost(), testOrdersByDate.get(0).getMaterialCost());
        assertEquals(testOrder.getLaborCost(), testOrdersByDate.get(0).getLaborCost());
        assertEquals(testOrder.getTotalTax(), testOrdersByDate.get(0).getTotalTax());
        assertEquals(testOrder.getTotal(), testOrdersByDate.get(0).getTotal());
    }

    /**
     * Test of addOrder method, of class DaoFileImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        Order testAdd = new Order();
        testAdd.setCustomerName("Wise");
        testAdd.setState("OH");
        testAdd.setProductType("Wood");
        testAdd.setArea(testArea);
        
        testAdd = dao.addOrder(testAdd);
        
        assertEquals(testOrder.getTaxRate(), testAdd.getTaxRate());
        assertEquals(testOrder.getCostPerSqrFoot(), testAdd.getCostPerSqrFoot());
        assertEquals(testOrder.getLaborCostPerSqrFoot(), testAdd.getLaborCostPerSqrFoot());
        assertEquals(testOrder.getMaterialCost(), testAdd.getMaterialCost());
        assertEquals(testOrder.getLaborCost(), testAdd.getLaborCost());
        assertEquals(testOrder.getTotalTax(), testAdd.getTotalTax());
        assertEquals(testOrder.getTotal(), testAdd.getTotal());
    }

    /**
     * Test of editOrder method, of class DaoFileImpl.
     */
    @Test
    public void testEditOrder() throws Exception{
        
        List<Order> testEdits = dao.displayOrders(testDate);
        Order testEdit = testEdits.get(0);
        assertEquals(testOrder.getCustomerName(), testEdit.getCustomerName());
        testEdit.setCustomerName("Bob");
        testEdit.setProductType("Tile");
        testEdit.setState("IN");
        BigDecimal editArea = new BigDecimal("150.00");
        testEdit.setArea(editArea);
        
        testEdit = dao.editOrder(testDate, testEdit);
        
        assertThat(testOrder.getTaxRate(), not(equalTo(testEdit.getTaxRate())));
        assertThat(testOrder.getTotal(), not(equalTo(testEdit.getTotal())));
    }

    /**
     * Test of removeOrder method, of class DaoFileImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception{
        List<Order> testLoad = dao.displayOrders(testDate);
        List<Order> testRemove = dao.removeOrder(testDate, testLoad.get(0));
        
        assertEquals(testLoad.size(), testRemove.size());
        assertTrue(testRemove.isEmpty());
    }
    
    @Test
    public void testStateList() throws Exception{
        List<String> testStates = new ArrayList();
        testStates.add("OH");
        testStates.add("PA");
        testStates.add("MI");
        testStates.add("IN");
        
        List<String> expectedStates = dao.getStates();
        assertTrue(expectedStates.containsAll(testStates));
    }
    
    @Test
    public void testProductList() throws Exception{
        List<String> testProducts = new ArrayList();
        testProducts.add("Carpet");
        testProducts.add("Laminate");
        testProducts.add("Tile");
        testProducts.add("Wood");
        
        List<String> expectedProducts = dao.getProductNames();
        assertTrue(expectedProducts.containsAll(testProducts));
    }
    
}
