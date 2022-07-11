/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasMovimientos;
import Modelo.Movimientos;
import View.frmMovimientos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class CtrlMovimientos implements ActionListener {

    private Movimientos mod;
    private ConsultasMovimientos modC;
    private frmMovimientos frmMovimientos;

    public CtrlMovimientos(Movimientos mod, ConsultasMovimientos modC, frmMovimientos frmMovimientos) {
        this.mod = mod;
        this.modC = modC;
        this.frmMovimientos = frmMovimientos;

        this.frmMovimientos.bntAlta.addActionListener(this);
        this.frmMovimientos.btnEliminar.addActionListener(this);
        this.frmMovimientos.btnBuscar.addActionListener(this);
        this.frmMovimientos.btnModificar.addActionListener(this);
        this.frmMovimientos.btnLimpiar.addActionListener(this);
        this.frmMovimientos.txtFolio.addActionListener(this);
        this.frmMovimientos.btnImprimeRecibo.addActionListener(this);
 
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
         if (e.getSource() == frmMovimientos.btnImprimeRecibo) {
            
             if (frmMovimientos.txtId.getText().isEmpty())
             {
                    JOptionPane.showMessageDialog(null, "Falta el campo Id");
                 
             }
             else{ 
            mod.setId(frmMovimientos.txtId.getText());
            modC.imprimeReciboIndebido(mod);
             }
        }
        

        if (e.getSource() == frmMovimientos.bntAlta) {
            
            if (frmMovimientos.txtAbono.getText().isEmpty() ||
                    frmMovimientos.txtQna.getText().isEmpty() ||  
                    frmMovimientos.txtFecha.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenar");

            } else {
            mod.setRfc(frmMovimientos.txtRfc.getText());
            mod.setNombre(frmMovimientos.txtNombre.getText());
            mod.setPlantel(frmMovimientos.txtPlantel.getText());
            mod.setFolio(Integer.parseInt(frmMovimientos.txtFolio.getText()));

            mod.setAbono(Double.parseDouble(frmMovimientos.txtAbono.getText()));
            mod.setNumeroQuincena(Integer.parseInt(frmMovimientos.txtQna.getText()));
            mod.setFechaAbono(frmMovimientos.txtFecha.getText());
            mod.setMovimiento(String.valueOf(frmMovimientos.cbTipo.getSelectedItem()));
            
            
            
            if (String.valueOf(frmMovimientos.cbTipo.getSelectedItem()) == "X") {
                mod.setInteres(Double.parseDouble(frmMovimientos.txtAbono.getText()));
                mod.setCapital(0.0);
            } else {
                mod.setCapital(Double.parseDouble(frmMovimientos.txtAbono.getText()));
                mod.setInteres(0.0);

            }

            // mod.setUsuarioMovimiento(Integer.parseInt(frmMovimientos.txt.getText()));
            // mod.setMovimiento(String.valueOf(frmMovimientos.cbTipo.getSelectedItem()));
            if (modC.registrarmovimiento(mod)) {

                JOptionPane.showMessageDialog(null, "Registro Guardado");
                //limpiar();
                  if (modC.buscaId(mod)) {
    
                  frmMovimientos.txtId.setText(mod.getId());
                  frmMovimientos.btnImprimeRecibo.setVisible(true);
                    }
                   else {
                     JOptionPane.showMessageDialog(null, "No se encontro el Id para imprimir el Recibo");

                 } 
                //frmCredito.btnImprimirMandato.setVisible(true);
                //frmImpresionMandato impresion = new frmImpresionMandato();
                //impresion.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Registro No Guardado Intente Mas Tarde");
                limpiar();

            }
            
            }
        }

        if (e.getSource() == frmMovimientos.btnEliminar) {
            
            if (frmMovimientos.txtId.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor Introduce el Id");

            } else {
            mod.setId(frmMovimientos.txtId.getText());
            if (modC.eliminarmovimiento(mod)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Registro No Eliminado Intente Mas Tarde");
                limpiar();
            }
            }

        }

        if (e.getSource() == frmMovimientos.btnBuscar) {
              if (frmMovimientos.txtId.getText().isEmpty()  
                 ) {

                JOptionPane.showMessageDialog(null, "Introduce el # de movimiento");

            } else {
            //mod.setFolio(Integer.parseInt(frmMovimientos.txtFolio.getText()));
            //mod.setFechaAbono(frmMovimientos.txtFecha.getText());
            //mod.setMovimiento(String.valueOf(frmMovimientos.cbTipo.getSelectedItem()));
             mod.setId(frmMovimientos.txtId.getText());
            if (modC.buscarmovimiento(mod)) {
                
                frmMovimientos.txtRfc.setText(mod.getRfc());
                frmMovimientos.txtNombre.setText(mod.getNombre());
                frmMovimientos.txtPlantel.setText(mod.getPlantel());
                frmMovimientos.txtFolio.setText(String.valueOf(mod.getFolio()));
                frmMovimientos.txtAbono.setText(String.valueOf(mod.getAbono()));
                frmMovimientos.txtQna.setText(String.valueOf(mod.getNumeroQuincena()));
                frmMovimientos.cbTipo.setSelectedItem(mod.getMovimiento());
                frmMovimientos.txtFecha.setText(mod.getFechaAbono());
                //frmMovimientos.txtId.setText(mod.getId());
                frmMovimientos.btnImprimeRecibo.setVisible(true);
                
                

            } else {
                JOptionPane.showMessageDialog(null, "No se encontro el movimiento");
                limpiar();
            }
            
             }
        }

        if (e.getSource() == frmMovimientos.btnLimpiar) {

            limpiar();
        }

        if (e.getSource() == frmMovimientos.btnModificar) {
            if (frmMovimientos.txtAbono.getText().isEmpty() ||
                    frmMovimientos.txtQna.getText().isEmpty() ||  
                    frmMovimientos.txtFecha.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenar");

            } else {
            mod.setRfc(frmMovimientos.txtRfc.getText());
            mod.setNombre(frmMovimientos.txtNombre.getText());
            mod.setPlantel(frmMovimientos.txtPlantel.getText());
            mod.setFolio(Integer.parseInt(frmMovimientos.txtFolio.getText()));
            mod.setAbono(Double.parseDouble(frmMovimientos.txtAbono.getText()));
            mod.setNumeroQuincena(Integer.parseInt(frmMovimientos.txtQna.getText()));
            mod.setMovimiento(String.valueOf(frmMovimientos.cbTipo.getSelectedItem()));
            mod.setFechaAbono(frmMovimientos.txtFecha.getText());
            if (modC.modificarmovimiento(mod)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Registro No Modificado Intente Mas Tarde");
                limpiar();
            }
            
        }

        }
        
        
        if (e.getSource() == frmMovimientos.txtFolio) {
                 //mod.setRfc(frmCredito.txtRfc.getText());
                 mod.setFolio(Integer.parseInt(frmMovimientos.txtFolio.getText()));
       
            if(modC.completarcampos(mod)){
                    //limpiar();
                    frmMovimientos.txtRfc.setText(mod.getRfc());
                    frmMovimientos.txtNombre.setText(mod.getNombre());
                    frmMovimientos.txtPlantel.setText(mod.getPlantel());
                   
            
            }else{
            
            JOptionPane.showMessageDialog(null, "No se encontro el folio");
                    limpiar();
            }
        }
        

    }

    public void limpiar() {
        frmMovimientos.txtAbono.setText(null);
       // frmMovimientos.txtFecha.setText(null);
        frmMovimientos.txtFolio.setText(null);
        frmMovimientos.txtNombre.setText(null);
        frmMovimientos.txtPlantel.setText(null);
        frmMovimientos.txtQna.setText(null);
        frmMovimientos.txtRfc.setText(null);
        frmMovimientos.txtId.setText(null);

    }

}
