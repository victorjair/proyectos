/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasTasas;
import Modelo.Tasas;
import View.frmTasas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class CtrlTasas implements ActionListener{
    
    Tasas mod;
    ConsultasTasas modC;
    frmTasas frmTasas;
    Double santander;
    Double bancomer;
    Double hsbc;
    Double banamex;
    Double promedio;
    

    public CtrlTasas(Tasas mod, ConsultasTasas modC, frmTasas frmTasas) {
        this.mod = mod;
        this.modC = modC;
        this.frmTasas = frmTasas;
        this.frmTasas.btnAlta.addActionListener(this);
        this.frmTasas.btnEliminar.addActionListener(this);
        this.frmTasas.btnModificar.addActionListener(this);
        this.frmTasas.btnBuscar.addActionListener(this);
        this.frmTasas.btnLimpiar.addActionListener(this);
         this.frmTasas.btnCalcular.addActionListener(this);
   
   
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
    if (e.getSource() == frmTasas.btnCalcular) {
        
        if (frmTasas.txtBanamex.getText().isEmpty()
                || frmTasas.txtBancomer.getText().isEmpty() 
                || frmTasas.txtHsbc.getText().isEmpty() 
                || frmTasas.txtSantander.getText().isEmpty()
                ){
            
                    JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenar");

         
        
            }else{
            
            banamex = Double.parseDouble(frmTasas.txtBanamex.getText());
            bancomer = Double.parseDouble(frmTasas.txtBancomer.getText());
            santander = Double.parseDouble(frmTasas.txtSantander.getText());
            hsbc = Double.parseDouble(frmTasas.txtHsbc.getText());
            promedio = (banamex + bancomer + santander + hsbc)/4;
            //mod.setPromedio(promedio);
            frmTasas.txtPromedio.setText(String.valueOf(promedio));
                   
        
        
        }
        
    }
        
    
    if (e.getSource() == frmTasas.btnAlta) {
        
        if (frmTasas.txtBanamex.getText().isEmpty()
                || frmTasas.txtBancomer.getText().isEmpty() 
                || frmTasas.txtHsbc.getText().isEmpty() 
                || frmTasas.txtSantander.getText().isEmpty() || frmTasas.txtPromedio.getText().isEmpty()
                ){
            
                    JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenar");

         
        
            }else{
        
             mod.setTasaBanamex(Double.parseDouble(frmTasas.txtBanamex.getText()));
            mod.setTasaBancomer(Double.parseDouble(frmTasas.txtBancomer.getText()));
            mod.setTasaHsbc(Double.parseDouble(frmTasas.txtHsbc.getText()));
            mod.setTasaSantander(Double.parseDouble(frmTasas.txtSantander.getText()));
            mod.setPromedio(Double.parseDouble(frmTasas.txtPromedio.getText()));
            mod.setPeriodo(String.valueOf(frmTasas.cbperiodo.getSelectedItem()));
            mod.setMes(String.valueOf(frmTasas.cbmes.getSelectedItem()));
            
            if(modC.comprobar(mod)){
            
                    JOptionPane.showMessageDialog(null, "El registro ya existe no se puede duplicar ");
            
            }
            else{
                
   
            if (modC.registrar(mod)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    limpiar();

                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Guardado Intente Mas Tarde");
                    limpiar();

                }
            
        }
       
        
        }
            
    
    }
    
    if (e.getSource() == frmTasas.btnEliminar) {
        
        if (frmTasas.txtId.getText().isEmpty())
         {
             JOptionPane.showMessageDialog(null, "Primero busque el periodo para poderlo eliminar");
                
         }else{
        
            if (modC.eliminar(mod)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Registro No Eliminado Intente Mas Tarde");
                limpiar();
            }

        
        }
        
    
    }
    if (e.getSource() == frmTasas.btnModificar) {
        
        if (frmTasas.txtBanamex.getText().isEmpty()
                || frmTasas.txtBancomer.getText().isEmpty() 
                || frmTasas.txtHsbc.getText().isEmpty() 
                || frmTasas.txtSantander.getText().isEmpty() || frmTasas.txtPromedio.getText().isEmpty()
                ){
            
                    JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenar");

         
        
            }else{
        
             mod.setTasaBanamex(Double.parseDouble(frmTasas.txtBanamex.getText()));
            mod.setTasaBancomer(Double.parseDouble(frmTasas.txtBancomer.getText()));
            mod.setTasaHsbc(Double.parseDouble(frmTasas.txtHsbc.getText()));
            mod.setTasaSantander(Double.parseDouble(frmTasas.txtSantander.getText()));
            mod.setPromedio(Double.parseDouble(frmTasas.txtPromedio.getText()));
            mod.setId(Integer.parseInt(frmTasas.txtId.getText()));
           
            if (modC.modificar(mod)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Registro No Modificado Intente Mas Tarde");
                limpiar();
            }
        
        
        }
    
    }
    if (e.getSource() == frmTasas.btnBuscar) {
        
           mod.setPeriodo(String.valueOf(frmTasas.cbperiodo.getSelectedItem()));
            mod.setMes(String.valueOf(frmTasas.cbmes.getSelectedItem()));
            if (modC.buscar(mod)) {
                    frmTasas.txtBanamex.setText(String.valueOf(mod.getTasaBanamex()));
                    frmTasas.txtBancomer.setText(String.valueOf(mod.getTasaBancomer()));
                    frmTasas.txtHsbc.setText(String.valueOf(mod.getTasaHsbc()));
                    frmTasas.txtSantander.setText(String.valueOf(mod.getTasaSantander()));
                    frmTasas.txtPromedio.setText(String.valueOf(mod.getPromedio()));
                    frmTasas.txtId.setText(String.valueOf(mod.getId()));
                   

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro el registro del periodo");
                    limpiar();
                }
           
    
    }
    if (e.getSource() == frmTasas.btnLimpiar) {
      limpiar();
    }
    
    
    
    }
      
    
    
    public void limpiar() {
        frmTasas.txtBanamex.setText(null);
        frmTasas.txtBancomer.setText(null);
        frmTasas.txtHsbc.setText(null);
        frmTasas.txtSantander.setText(null);
        frmTasas.txtPromedio.setText(null);
         frmTasas.txtId.setText(null);
    }
    
}
