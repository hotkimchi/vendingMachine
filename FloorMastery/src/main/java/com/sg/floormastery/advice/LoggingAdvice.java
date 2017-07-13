/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.floormastery.advice;

import com.sg.floormastery.dao.DaoPersistenceException;
import com.sg.floormastery.dao.FloorMasteryAudit;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author apprentice
 */
public class LoggingAdvice {
    
    private FloorMasteryAudit auditDao;
    
    public LoggingAdvice (FloorMasteryAudit auditDao) {
        this.auditDao = auditDao;
    }
    
    public void createAuditEntry(JoinPoint jp, Throwable exception){
        String auditEntry = jp.getSignature().getName() + ": ";
        
        auditEntry+= exception;
        try{
            auditDao.writeAuditEntry(auditEntry);
        } catch (DaoPersistenceException e) {
            System.err.println("ERROR: Could not log object to audit file "
                    + "in LoggingAdvice");
        }
    }
}
