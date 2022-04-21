/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FWD.SIG_Model;

import com.FWD.SIG_View.InvFrame_SIG;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Abdu Sayed
 */
public class InvItemsTbModel extends AbstractTableModel{

    private ArrayList<InvLine> inLine ;
    private String [] colNames = {"No." , "Item Name" , "Item Price" , "Count","Item Total"};
    
    public InvItemsTbModel(ArrayList<InvLine> inLine) {
        this.inLine = inLine;
    }
    @Override
    public int getRowCount() {
        return inLine.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvLine invL = inLine.get(rowIndex) ;
        switch(columnIndex){
            case 0 : return invL.getInvHeader().getInvNum();
            case 1 : return invL.getItemName();
            case 2 : return invL.getItemPrice();
            case 3 : return invL.getItemCount();
            case 4 : return invL.getTotalForItem();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column] ;
    }
    
    
}
