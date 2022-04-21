/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FWD.SIG_Model;

/**
 *
 * @author Abdu Sayed
 */
public class InvLine {
    
    private String itemName ;
    private double itemPrice ;
    private int itemCount;
    private InvHeader invHeader ;

    public InvLine(String itemName, double itemPrice, int itemCount, InvHeader invHeader) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.invHeader = invHeader;
    }
    
    public InvLine() {
    }

   

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public InvHeader getInvHeader() {
        return invHeader;
    }

    public void setInvHeader(InvHeader invHeader) {
        this.invHeader = invHeader;
    }

    
    public double getTotalForItem (){
        return itemCount * itemPrice ;
    }
    
    public String uploadLineFormat() {
            return invHeader.getInvNum()+ "," +itemName + "," + itemPrice + "," + itemCount  ;

        }

    @Override
    public String toString() {
        return "InvLine{" + "itemName=" + itemName + ", itemPrice=" + itemPrice + ", itemCount=" + itemCount +  '}'+"\n";
    }
    
    
    
    
    
    

    
}
