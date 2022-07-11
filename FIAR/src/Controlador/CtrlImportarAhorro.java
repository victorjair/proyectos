/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasImportarAhorro;
import Modelo.ImportarAhorro;
import View.frmImportarAhorros;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author victor
 */
public class CtrlImportarAhorro implements ActionListener {

    private ImportarAhorro mod;
    private ConsultasImportarAhorro modC;
    private frmImportarAhorros frmImportarAhorros;

    public CtrlImportarAhorro(ImportarAhorro mod, ConsultasImportarAhorro modC, frmImportarAhorros frmImportarAhorros) {

        this.mod = mod;
        this.modC = modC;
        this.frmImportarAhorros = frmImportarAhorros;
        this.frmImportarAhorros.btnAbrir.addActionListener(this);
        this.frmImportarAhorros.btnImportar.addActionListener(this);
        this.frmImportarAhorros.btnCancelar.addActionListener(this);
       
        //this.frmImportarAhorros.jFImportar.addActionListener(CANCEL_OPTION);
        
        
    }
    
      @Override
    public void actionPerformed(ActionEvent e) {
        
        
         if(e.getSource() == frmImportarAhorros.btnCancelar){
           frmImportarAhorros.btnAbrir.setVisible(true);
           frmImportarAhorros.btnImportar.setVisible(false);
           frmImportarAhorros.btnCancelar.setVisible(false);
         }
    
          if(e.getSource() == frmImportarAhorros.btnImportar){
               
              if(frmImportarAhorros.txtRuta.getText().isEmpty())
              {
               
            
              }
              else{
               mod.setNumeroQuincena(String.valueOf(frmImportarAhorros.cbperiodo.getSelectedItem()) + String.valueOf(frmImportarAhorros.cbNumero.getSelectedItem()) );
               mod.setRuta(frmImportarAhorros.txtRuta.getText());
                  try {

                      if (frmImportarAhorros.cbTipo.getSelectedItem() == "Ahorro") {
                        // JOptionPane.showMessageDialog(null, "ELEGISTE AHORROS");
                            modC.importararchivoquincenal(mod);
                       
      
                      } else {
                      //JOptionPane.showMessageDialog(null, "ELEGISTE PRESTAMOS");
                      modC.importararchivoquincenalprestamos(mod);
                      
                      }

                  } catch (IOException ex) {
                      Logger.getLogger(CtrlImportarAhorro.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
              
          }
          
        
          if(e.getSource() == frmImportarAhorros.btnAbrir){
          
          JFileChooser fc = new JFileChooser();
          fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
          FileFilter filt = new FileNameExtensionFilter("Archivo csv","csv");
          fc.addChoosableFileFilter(filt);
          fc.setFileFilter(filt);
          int result = fc.showOpenDialog(fc);
          
          if(result == JFileChooser.APPROVE_OPTION){
           fc.getSelectedFile().getAbsolutePath();
           //mod.setRuta(fc.getSelectedFile().getAbsolutePath());
           frmImportarAhorros.txtRuta.setText(fc.getSelectedFile().getAbsolutePath());
           frmImportarAhorros.btnAbrir.setVisible(false);
           frmImportarAhorros.btnImportar.setVisible(true);
           frmImportarAhorros.btnCancelar.setVisible(true);
           
                    
          }if(result == JFileChooser.CANCEL_OPTION){
          }

          
          
          }
        
       /*frmImportarAhorros.jFImportar = (JFileChooser) e.getSource();
        String command = e.getActionCommand();
        if (command.equals(JFileChooser.CANCEL_OPTION)) {
         JOptionPane.showMessageDialog(null, "CANCELADO");
                
        }*/
        /** if(e.getSource() == frmImportarAhorros.jFImportar){
             
             mod.setRuta(frmImportarAhorros.jFImportar.getSelectedFile().getAbsolutePath());
             mod.setNumeroQuincena(String.valueOf(frmImportarAhorros.cbperiodo.getSelectedItem()) + String.valueOf(frmImportarAhorros.cbNumero.getSelectedItem()) );
         
              try {
                  
                  if(frmImportarAhorros.cbTipo.getSelectedItem()== "Ahorro")
                  {
                   modC.importararchivoquincenal(mod);
                  }
                  else{
                   modC.importararchivoquincenalprestamos(mod);
                  
                  }
             
              
              } catch (IOException ex) {
                  Logger.getLogger(CtrlImportarAhorro.class.getName()).log(Level.SEVERE, null, ex);
              }
                      
                
            
          
        
        }*/
       
    
    }
}
