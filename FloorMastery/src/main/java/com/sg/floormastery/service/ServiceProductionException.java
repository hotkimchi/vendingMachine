/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.service;

/**
 *
 * @author apprentice
 */
public class ServiceProductionException extends Exception{
    
    public ServiceProductionException(String message){
        super(message);
    }
    
    public ServiceProductionException(String message, Throwable cause){
        super(message, cause);
    }
    
}
