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
public class InvHdrTbModel extends AbstractTableModel{

    private ArrayList<InvHeader> invHdr ;
    private String [] colNames = {"No." , "Date" , "Customer" , "Total"};
    public InvHdrTbModel(ArrayList<InvHeader> invHdr) {
        this.invHdr = invHdr;
    }

    
    
    @Override
    public int getRowCount() {
        return invHdr.size(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        return colNames.length ;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvHeader invH = invHdr.get(rowIndex) ;
        switch(columnIndex){
            case 0 : return invH.getInvNum();
            case 1 : return InvFrame_SIG.sDateFormat.format(invH.getInvDate());
            case 2 : return invH.getCustomerName();
            case 3 : return invH.getTotalForAll();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column] ;
    }
    
    
            
}
