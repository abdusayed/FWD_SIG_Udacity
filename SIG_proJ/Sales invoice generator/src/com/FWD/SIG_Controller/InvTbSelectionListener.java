/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FWD.SIG_Controller;

import com.FWD.SIG_Model.InvHeader;
import com.FWD.SIG_Model.InvItemsTbModel;
import com.FWD.SIG_Model.InvLine;
import com.FWD.SIG_View.InvFrame_SIG;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Abdu Sayed
 */
public class InvTbSelectionListener implements ListSelectionListener{

    private InvFrame_SIG invFrame ;

    public InvTbSelectionListener(InvFrame_SIG invFrame) {
        this.invFrame = invFrame;
    }

   /* public InvTbSelectionListener(InvFrame_SIG aThis) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    
    
    
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int scI = invFrame.getInvTable_T().getSelectedRow();
        if(scI != -1)
        {
        InvHeader scInv =  invFrame.getInvHdr().get(scI);
        ArrayList<InvLine> invLine = scInv.getInvLine();
        InvItemsTbModel invItemsTbM = new InvItemsTbModel(invLine);
        invFrame.setInvLine(invLine);
        invFrame.getInvItems_T().setModel(invItemsTbM);
        invFrame.getInvNumber_L().setText(scInv.getInvNum()+"");
        invFrame.getInvDate_L().setText(invFrame.sDateFormat.format(scInv.getInvDate()));  
        invFrame.getCustomerName_L().setText(scInv.getCustomerName());
        invFrame.getInvTotal_L().setText(scInv.getTotalForAll()+"");
        }  
        
        
    }
    
}
