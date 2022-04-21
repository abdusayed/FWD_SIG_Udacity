/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FWD.SIG_Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Abdu Sayed
 */
public class InvHeader {
    private SimpleDateFormat sDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private int invNum ;
    private Date inv_Date ;
    private String customerName ;
    private ArrayList<InvLine> invLine ;
    
    public InvHeader() {
    }

    public InvHeader(int invNum, Date invDate, String customerName) {
        this.invNum = invNum;
        this.inv_Date = invDate;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getInvNum() {
        return invNum;
    }

    public void setInvNum(int invNum) {
        this.invNum = invNum;
    }

    public Date getInvDate() {
        return inv_Date;
    }

    public void setInvDate(Date invDate) {
        this.inv_Date = invDate;
    }
    
    public ArrayList<InvLine> getInvLine() {
        //null obj
        if (invLine == null)
            invLine = new ArrayList<>();  // lazy creation
        
        return invLine;
    }

    public void setInvLine(ArrayList<InvLine> invLine) {
        
        this.invLine = invLine;
    }
    
    public double getTotalForAll ()
    {
        int i;
        double tAll = 0.0;
        for(i=0 ; i< getInvLine().size() ; i++)
        {
            tAll += getInvLine().get(i).getTotalForItem();
        }
        return tAll ;
    }
    
    public String uploadHdrFormat() {
            return invNum + "," + sDateFormat.format(inv_Date) + "," + customerName ;
        }

    @Override
    public String toString() {
        return "InvHeader{" + "sDateFormat=" + sDateFormat + ", invNum=" + invNum + ", inv_Date=" + inv_Date + ", customerName=" + customerName + '}' +"\n" + "invLine=" + invLine.toString() ;
    }
    
    
    
    
    
}
