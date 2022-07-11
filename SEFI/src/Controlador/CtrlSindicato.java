/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasSindicato;
import Modelo.Sindicato;
import View.frmSindicato;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class CtrlSindicato implements ActionListener {
    
    private Sindicato mod;
    private ConsultasSindicato modC;
    private frmSindicato frmSindicato;

    public CtrlSindicato(Sindicato mod, ConsultasSindicato modC, frmSindicato frmSindicato) {
        this.mod = mod;
        this.modC = modC;
        this.frmSindicato = frmSindicato;
        
        frmSindicato.btnAlta.addActionListener(this);
        frmSindicato.btnEliminar.addActionListener(this);
        frmSindicato.btnModificar.addActionListener(this);
        frmSindicato.btnBuscar.addActionListener(this);
        frmSindicato.btnLimpiar.addActionListener(this);
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
 
    if (e.getSource() == frmSindicato.btnAlta) {

            if (frmSindicato.txtFechaAfiliacion.getText().isEmpty()
                    || frmSindicato.txtQnaAfiliacion.getText().isEmpty()
                    || frmSindicato.txtRfcSindicato.getText().isEmpty()
                    || frmSindicato.txtTelefono.getText().isEmpty()
                    || frmSindicato.txtNombreSindicato.getText().isEmpty()
                    || frmSindicato.txtRfcRepresentante.getText().isEmpty()
                    || frmSindicato.txtPuesto.getText().isEmpty()
                    || frmSindicato.txtNombreRepresentante.getText().isEmpty()
                    || frmSindicato.txtNombreLargoSindicato.getText().isEmpty()
                    ) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
                mod.setFechaAfiliacion(frmSindicato.txtFechaAfiliacion.getText());
                mod.setQnaAfiliacion(frmSindicato.txtQnaAfiliacion.getText());
                mod.setRfcSindicato(frmSindicato.txtRfcSindicato.getText());
                mod.setTelefonoSindicato(frmSindicato.txtTelefono.getText());
                mod.setNombreSindicato(frmSindicato.txtNombreSindicato.getText());
                mod.setRfcRepresentante(frmSindicato.txtRfcRepresentante.getText());
                mod.setPuestoRepresentante(frmSindicato.txtPuesto.getText());
                mod.setNombreRepresentante(frmSindicato.txtNombreRepresentante.getText());
                mod.setDetalles(frmSindicato.txtNombreLargoSindicato.getText());
              
                
                if (modC.registrar(mod)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    limpiar();

                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Guardado Intente Mas Tarde");
                    limpiar();

                }

            }

        }
    
    
    
    if (e.getSource() == frmSindicato.btnModificar) {
       if (frmSindicato.txtFechaAfiliacion.getText().isEmpty()
                    || frmSindicato.txtQnaAfiliacion.getText().isEmpty()
                    || frmSindicato.txtRfcSindicato.getText().isEmpty()
                    || frmSindicato.txtTelefono.getText().isEmpty()
                    || frmSindicato.txtNombreSindicato.getText().isEmpty()
                    || frmSindicato.txtRfcRepresentante.getText().isEmpty()
                    || frmSindicato.txtPuesto.getText().isEmpty()
                    || frmSindicato.txtNombreRepresentante.getText().isEmpty()
                    || frmSindicato.txtNombreLargoSindicato.getText().isEmpty()
                    ) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {

               
                mod.setFechaAfiliacion(frmSindicato.txtFechaAfiliacion.getText());
                mod.setQnaAfiliacion(frmSindicato.txtQnaAfiliacion.getText());
                mod.setRfcSindicato(frmSindicato.txtRfcSindicato.getText());
                mod.setTelefonoSindicato(frmSindicato.txtTelefono.getText());
                mod.setNombreSindicato(frmSindicato.txtNombreSindicato.getText());
                mod.setRfcRepresentante(frmSindicato.txtRfcRepresentante.getText());
                mod.setPuestoRepresentante(frmSindicato.txtPuesto.getText());
                mod.setNombreRepresentante(frmSindicato.txtNombreRepresentante.getText());
                mod.setDetalles(frmSindicato.txtNombreLargoSindicato.getText());
              
             
                if (modC.modificar(mod)) {
                    JOptionPane.showMessageDialog(null, "Registro Modificado");
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Modificado Intente Mas Tarde");
                    limpiar();
                }

        }

        }
    
    if (e.getSource() == frmSindicato.btnLimpiar) {
        
        limpiar();
        
    }
    
     if (e.getSource() == frmSindicato.btnBuscar) {
          
            if (frmSindicato.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Introduce el Id Sindicato");

            } else {
                  mod.setId(frmSindicato.txtId.getText());

                if (modC.buscar(mod)) {
                    frmSindicato.txtFechaAfiliacion.setText(mod.getFechaAfiliacion());
                    frmSindicato.txtQnaAfiliacion.setText(mod.getQnaAfiliacion());
                    frmSindicato.txtRfcSindicato.setText(mod.getRfcSindicato());
                    frmSindicato.txtTelefono.setText(mod.getTelefonoSindicato());
                    frmSindicato.txtNombreSindicato.setText(mod.getNombreSindicato());
                    frmSindicato.txtRfcRepresentante.setText(mod.getRfcRepresentante());
                    frmSindicato.txtPuesto.setText(mod.getPuestoRepresentante());
                    frmSindicato.txtNombreRepresentante.setText(mod.getNombreRepresentante());
                    frmSindicato.txtNombreLargoSindicato.setText(mod.getDetalles());
                   
                     
               

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro el afiliado");
                    limpiar();
                }
                
                
               
                
               

            }

        }
     
      if (e.getSource() == frmSindicato.btnEliminar) {
            
            if (frmSindicato.txtId.getText().isEmpty())
            {   

                JOptionPane.showMessageDialog(null, "Faltan Campo Id");

            } else {
                
            mod.setId(frmSindicato.txtId.getText());

            if (modC.eliminar(mod)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Registro No Eliminado Intente Mas Tarde");
                limpiar();
            }
        }

        }
     }
    
    
    public void limpiar() {
        frmSindicato.txtFechaAfiliacion.setText(null);
        frmSindicato.txtQnaAfiliacion.setText(null);
        frmSindicato.txtRfcSindicato.setText(null);
        frmSindicato.txtTelefono.setText(null);
        frmSindicato.txtNombreSindicato.setText(null);
        frmSindicato.txtRfcRepresentante.setText(null);
        frmSindicato.txtPuesto.setText(null);
        frmSindicato.txtNombreRepresentante.setText(null);
        frmSindicato.txtNombreLargoSindicato.setText(null);
        

    }
    
}
