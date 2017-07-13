/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.ui;

import com.sg.floormastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class View {

    private UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

    public void displayWelcome() {
        io.print("Tommy and Jake's Flooring Order Data Base");
        io.print("=========================================");
    }

    public void displayGoodBye() {
        io.print("Exiting Data Base");
        io.print("GOOD BYE");
    }

    public void displayMainMenu() {
        io.print("    <<Main Menu>>    ");
        io.print("*********************");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save Current Work");
        io.print("6. Quit");
        io.print("*********************");
    }

    public int getMenuChoice(int min, int max) {
        int choice = io.readInt("Select a menu option", min, max);
        return choice;
    }

    public void displayError(String message) {
        io.print(message);
    }

    public void displayDisplayMenu() {
        io.print("  <<Display Order Menu>>  ");
        io.print("**************************");
        io.print("1. Find Orders By Date");
        io.print("2. Return To Main Menu");
        io.print("**************************");
    }

    public void displayEditMenu() {
        io.print("   <<Edit Order Menu>>   ");
        io.print("*************************");
        io.print("1. Edit Order by date and order number");
        io.print("2. Return To Main Menu");
        io.print("**************************");
    }

    public void displayRemoveMenu() {
        io.print("    <<Remove Order Menu>>    ");
        io.print("*****************************");
        io.print("1. Remove Order by date and order number");
        io.print("2. Return To Main Menu");
        io.print("******************************");
    }

    public void displayAddOrderMenu() {
        io.print("   <<Add Order Menu>>   ");
        io.print("***Orders must have all of the following fields to be filed:");
        io.print("---Customer Name");
        io.print("---State");
        io.print("---Product Type");
        io.print("---Area of floor");
        io.print("***The computer will fill out the rest of the order");
        io.print("***************************************************");
        io.print("1. Add Order");
        io.print("2. Return To Main Menu");
        io.print("************************");
    }

    public Order getOrderInfo(List<String> states, List<String> products) {
        Order newOrder = new Order();

        String name = io.readString("What is the customer's name?");
        io.print("Here are the states we work in:");
        for (int i = 0; i < states.size(); i++) {
            io.print((i + 1) + ". " + states.get(i));
        }
        int stateChoice = io.readInt("Which State is the customer in?", 1, states.size());
        String stateOrder = states.get(stateChoice - 1);

        io.print("Here are the products we work with:");
        for (int i = 0; i < products.size(); i++) {
            io.print((i + 1) + ". " + products.get(i));
        }
        int productChoice = io.readInt("What is the product type?", 1, products.size());
        String productOrder = products.get(productChoice - 1);

        BigDecimal areaOrder = io.readBigDecimal("How big is the floor area", 2);

        newOrder.setCustomerName(name);
        newOrder.setState(stateOrder);
        newOrder.setProductType(productOrder);
        newOrder.setArea(areaOrder);

        return newOrder;
    }

    private void displayOrder(Order order) {
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("State Tax " + order.getTaxRate());
        io.print("Product Type: " + order.getProductType());
        io.print("Area: " + order.getArea());
        io.print("Cost Per Square Foot: " + order.getCostPerSqrFoot());
        io.print("Labor Cost Per Square Cost: " + order.getLaborCostPerSqrFoot());
        io.print("Material Cost: " + order.getMaterialCost());
        io.print("Labor Cost: " + order.getLaborCost());
        io.print("Total Tax: " + order.getTotalTax());
        io.print("Total Cost: " + order.getTotal());
    }

    public void displayNewOrUpdatedOrder(Order order) {
        io.print("Here is the order. It has been updated to memory:");
        io.print("*************************************************");
        displayOrder(order);
        io.print("*************************************************");
        io.print("Save work in main menu to write to file (production mode only)");
    }

    public void displaySuccessfulRemoval(Order order) {
        io.print("Order number " + order.getOrderNumber() + " has been removed from memory");
        io.print("Save work in main menu to commit the removal and write to file (production mode only)");
    }

    public LocalDate askForDate() {
        LocalDate getDate = io.readLocalDate("Please Enter Date MMDDYYYY");
        return getDate;
    }

    public void displaySaveChoice() {
        io.print("Would you like to save your current work?");
        io.print("Select 1 for yes or 2 for no");
    }

    public void displayOrdersByDate(List<Order> orders, LocalDate date) {
        io.print("Here are the orders for " + date + ": ");
        io.print("*************************************");
        int count = 1;
        for (Order order : orders) {
            io.print("Selection: " + count);
            io.print(order.getOrderNumber() + ", "
                    + order.getCustomerName() + ", "
                    + order.getState() + ", "
                    + order.getTaxRate() + ", "
                    + order.getProductType() + ", "
                    + order.getCostPerSqrFoot() + ", "
                    + order.getLaborCostPerSqrFoot() + ", "
                    + order.getArea() + ", "
                    + order.getMaterialCost() + ", "
                    + order.getLaborCost() + ", "
                    + order.getTotalTax() + ", "
                    + order.getTotal());
            count++;
        }
        io.print("**************************************");
    }

    public Order editTheOrder(List<Order> orders, int choice,
            List<String> states, List<String> products) {
        boolean backToEdit = true;

        io.print("   <<EDITOR>>   ");
        io.print("****************");
        Order editOrder = orders.get(choice - 1);
        displayOrder(editOrder);

        while (backToEdit) {
            io.print("***What field do you want edited?");
            io.print("***************************");
            io.print("1. Customer Name: " + editOrder.getCustomerName());
            io.print("2. State: " + editOrder.getState());
            io.print("3. Product Type: " + editOrder.getProductType());
            io.print("4. Area: " + editOrder.getArea());
            io.print("5. Exit Editor");
            io.print("***************************");
            int editorChoice = getMenuChoice(1, 5);
            int count = 0;
            switch (editorChoice) {
                case 1:
                    String customer = io.readString("What is the new name?");
                    editOrder.setCustomerName(customer);
                    break;
                case 2:
                    for (int i = 0; i < states.size(); i++) {
                        count = count + 1;
                        io.print(count + ". " + states.get(i));
                    }
                    int stateChoice = io.readInt("What is the new state?", 1, states.size());
                    editOrder.setState(states.get(stateChoice - 1));
                    break;
                case 3:
                    for (int i = 0; i < products.size(); i++) {
                        count = count + 1;
                        io.print(count + ". " + products.get(i));
                    }
                    int typeChoice = io.readInt("What is the new product?", 1, products.size());
                    editOrder.setProductType(products.get(typeChoice - 1));
                    break;
                case 4:
                    BigDecimal area = io.readBigDecimal("What is the new area", 2);
                    editOrder.setArea(area);
                    break;
                case 5:
                    backToEdit = false;
                    break;
                default:
                    io.print("(0.0) What the Fish? ERROR!!!");
            }
        }
        io.print("Exiting editor.");
        displaySaveChoice();
        int saveChoice = getMenuChoice(1, 2);
        if (saveChoice == 1) {
            return editOrder;
        } else {
            return orders.get(choice - 1);
        }
    }

    public boolean removeTheOrder(List<Order> orders, int choice) {

        io.print("   <<REMOVE ORDER>>   ");
        io.print("**********************");
        Order removeOrder = orders.get(choice - 1);
        displayOrder(removeOrder);

        int removeChoice = io.readInt("Press 1 to remove Order. Press 2 to leave order", 1, 2);
        if (removeChoice == 1) {
            return true;
        } else {
            return false;
        }
    }
}
