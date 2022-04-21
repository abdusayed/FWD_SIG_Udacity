/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FWD.SIG_Controller;

import com.FWD.SIG_Model.InvHdrTbModel;
import com.FWD.SIG_Model.InvHeader;
import com.FWD.SIG_Model.InvItemsTbModel;
import com.FWD.SIG_Model.InvLine;
import com.FWD.SIG_View.InvFrame_SIG;
import com.FWD.SIG_View.SIG_InvHdrDialog;
import com.FWD.SIG_View.SIG_InvLineDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Abdu Sayed
 */
public class SIG_ActionListener implements ActionListener{

    private InvFrame_SIG invFrame ;
    private SIG_InvHdrDialog invHdrD ;
    private SIG_InvLineDialog invLineD;
            
            
    public SIG_ActionListener(InvFrame_SIG invFrame) {
        this.invFrame = invFrame;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (e.getActionCommand())
        {
            case  "Save":
                save();
            break;
            
            case  "Delete Invoice":
                deleteInvoice();
            break;
            
            case  "Create New Invoice":
                createNewInvoice();
            break;
            
            case  "Save File":
                saveFile();
                printDataInConsol();
            break;
            
            case  "Load File":         
                loadFile();   
            break;
            
            case  "Cancel":
                cancel(); 
            break;
            
            case "InvNew_Cancel":
                invNewDialog_Cancel();
            break;
            
            case "InvNew_Ok":
                invNewDialog_Ok();
            break;
            
            case "itemNew_OK" :
                itemNewDialog_Ok();
            break;
            
            case "itemNew_Cancel":
                itemNewDialog_Cancel();
            break;
            
        }
        
    }

    private void save() {
        invLineD = new SIG_InvLineDialog(invFrame);
        invLineD.setVisible(true);
    }

    private void deleteInvoice() {
        int scInv = invFrame.getInvTable_T().getSelectedRow();
        if(scInv != -1)
        {
            invFrame.getInvHdr().remove(scInv);
            invFrame.getInvhdrmodel().fireTableDataChanged();
            invFrame.getInvItems_T().setModel(new InvItemsTbModel(new ArrayList<InvLine>()));
            
            invFrame.setInvLine(null);
            
            invFrame.getInvNumber_L().setText("");
            invFrame.getInvDate_L().setText("");  
            invFrame.getCustomerName_L().setText("");
            invFrame.getInvTotal_L().setText("");
        }
        
    }

    private void createNewInvoice() {
        
        invHdrD = new SIG_InvHdrDialog(invFrame);
        invHdrD.setVisible(true);
        
    }

    private void saveFile() {
        
        ArrayList<InvHeader> invH = invFrame.getInvHdr();
        JFileChooser fileChoosed = new JFileChooser();
        try {
            int option = fileChoosed.showSaveDialog(invFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File h_File = fileChoosed.getSelectedFile();
                FileWriter newInvHdrFile = new FileWriter(h_File);
                String textHeaders = "";
                String textLines = "";
                for (InvHeader inV : invH) {
                    textHeaders += inV.uploadHdrFormat();
                    textHeaders += "\n";
                    for (InvLine Ln : inV.getInvLine()) {
                        textLines += Ln.uploadLineFormat();
                        textLines += "\n";
                    }
                }
                textHeaders = textHeaders.substring(0, textHeaders.length()-1);
                textLines = textLines.substring(0, textLines.length()-1);
                option = fileChoosed.showSaveDialog(invFrame);
                File l_File = fileChoosed.getSelectedFile();
                FileWriter newInvLineFile = new FileWriter(l_File);
                newInvHdrFile.write(textHeaders);
                newInvLineFile.write(textLines);
                
                newInvHdrFile.close();
                newInvLineFile.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(invFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private void loadFile()  {
        
        JFileChooser selectFile = new JFileChooser();
        try{
        int option = selectFile.showOpenDialog(invFrame);
        if(option == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = selectFile.getSelectedFile();
            Path filePath = Paths.get(selectedFile.getAbsolutePath());
            List<String> fileLines = Files.readAllLines(filePath);
            ArrayList<InvHeader> invHeaders = new ArrayList<>();
            for(String fileLine : fileLines)
            {
                String [] lineSplit = fileLine.split(",");
                String numS = lineSplit[0];
                String dateS = lineSplit[1];
                String customerS = lineSplit[2];
                
                int id = Integer.parseInt(numS);
                Date date = invFrame.sDateFormat.parse(dateS);
                
                InvHeader invHeader = new InvHeader(id, date, customerS);
                invHeaders.add(invHeader);
            }
            
            invFrame.setInvHdr(invHeaders);
        option = selectFile.showOpenDialog(invFrame);
            if(option == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile2 = selectFile.getSelectedFile();
                Path filePath2 = Paths.get(selectedFile2.getAbsolutePath());
                List<String> itemLines = Files.readAllLines(filePath2);
               // ArrayList<InvLine> invLines = new ArrayList<>();
                for(String itemline : itemLines)
                {
                    String [] lineSplit = itemline.split(",");
                    String numS = lineSplit[0];
                    String itemNameS = lineSplit[1];
                    String priceS = lineSplit[2];
                    String amountS = lineSplit[3];
                    
                    int id = Integer.parseInt(numS);
                    double price = Double.parseDouble(priceS);
                    int amount = Integer.parseInt(amountS);
 
                    InvLine invline  = new InvLine(itemNameS , price ,amount , invFrame.matchedID(id));
                    invFrame.matchedID(id).getInvLine().add(invline);
                        
                }
            } 
            InvHdrTbModel hdrModel = new InvHdrTbModel(invHeaders);
            invFrame.setInvhdrmodel(hdrModel);
            invFrame.getInvTable_T().setModel(hdrModel);
           
        }
        
       // TODO
       }
        catch(IOException e) {
            JOptionPane.showMessageDialog(invFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch(ParseException e) {
            JOptionPane.showMessageDialog(invFrame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancel() {
        int scInv = invFrame.getInvItems_T().getSelectedRow();
        int index = invFrame.getInvTable_T().getSelectedRow();
        if(scInv != -1)
        {
            invFrame.getInvLine().remove(scInv);
            InvItemsTbModel itemmodel = (InvItemsTbModel)invFrame.getInvItems_T().getModel();
            itemmodel.fireTableDataChanged();
            invFrame.getInvTotal_L().setText(""+invFrame.getInvHdr().get(index).getTotalForAll());
            invFrame.getInvhdrmodel().fireTableDataChanged();
            invFrame.getInvTable_T().setRowSelectionInterval(index, index);
        }
    }

    private void invNewDialog_Cancel() {
        invHdrD.setVisible(false);
        invHdrD.dispose();
        invHdrD = null;
        
    }

    private void invNewDialog_Ok() {
        invHdrD.setVisible(false);
        int index =0 ;
        String [] S = {"",""};
        S[0] = invHdrD.getAeraOfCustomer().getText();
        S[1] = invHdrD.getAeraOfDate().getText();
        
        Date date = new Date();
        try{
        date = InvFrame_SIG.sDateFormat.parse(S[1]);
        } catch(ParseException e) {
            JOptionPane.showMessageDialog(invFrame,"Error Parsing", "Wrong Date Format", JOptionPane.ERROR_MESSAGE);
        }
        
        for(InvHeader hdr : invFrame.getInvHdr())
        {
            if(hdr.getInvNum() > index)
            {
                index = hdr.getInvNum() ;
                // max
            }
        }
        index++;
        
        InvHeader invH = new InvHeader(index, date, S[0]);
        invFrame.getInvHdr().add(invH);
        invFrame.getInvhdrmodel().fireTableDataChanged();
        
       
        invHdrD.dispose();
        invHdrD = null;
    }

    private void itemNewDialog_Cancel() {
         invLineD.setVisible(false);
         invLineD.dispose();
         invLineD = null;
    }

    
    private void itemNewDialog_Ok() {
         invLineD.setVisible(false);
          int scInv = invFrame.getInvTable_T().getSelectedRow();
         int cnt = 1 ; 
         double prc = 1 ;
        String [] S = {"","",""};
        S[0] = invLineD.getAreaOfItemName().getText();
        S[1] = invLineD.getAreaOfItemPrice().getText();
        S[2] = invLineD.getAreaOfItemCount().getText();
        
        try{
            cnt = Integer.parseInt(S[2]); // or price
            }
        catch(NumberFormatException e)
            { 
                JOptionPane.showMessageDialog(invFrame,"Error Converting", "Undefined Number Format", JOptionPane.ERROR_MESSAGE);
            }
        try{
            prc = Double.parseDouble(S[1]); 
            }
        catch(NumberFormatException e)
            { 
                JOptionPane.showMessageDialog(invFrame,"Error Converting", "Undefined Number Format", JOptionPane.ERROR_MESSAGE);
            }
        if(scInv != -1)
        {       
            InvLine invL = new InvLine(S[0], prc , cnt , invFrame.getInvHdr().get(scInv) );
            invFrame.getInvLine().add(invL);
            
            //invFrame.getInvitemmodel().fireTableDataChanged();
            InvItemsTbModel itemmodel = (InvItemsTbModel)invFrame.getInvItems_T().getModel();
            itemmodel.fireTableDataChanged();
            invFrame.getInvhdrmodel().fireTableDataChanged();

        }
        invFrame.getInvTable_T().setRowSelectionInterval(scInv, scInv);
         
         invLineD.dispose();
         invLineD = null;
         
         
    }
    
   private void printDataInConsol() {
	        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	        for (InvHeader hdr : invFrame.getInvHdr()) {
                    System.out.println("________________________________________________________");
	            System.out.println(hdr.toString());
	        }
	        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	    } 
}
