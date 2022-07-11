/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;


import Modelo.ConsultasMovimientosAhorro;
import Modelo.MovimientosAhorro;
import View.frmMovAhorros;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class CtrlMovimientosAhorro implements ActionListener {

    private MovimientosAhorro mod;
    private ConsultasMovimientosAhorro modC;
    private frmMovAhorros frmMovimientosAhorro;
    Double total = 0.0;
    Double retiroactual = 0.0;
    Double saldo = 0.0;
    Double saldoverifica = 0.0;
    

    public CtrlMovimientosAhorro(MovimientosAhorro mod, ConsultasMovimientosAhorro modC, frmMovAhorros frmMovimientosAhorro) {
        this.mod = mod;
        this.modC = modC;
        this.frmMovimientosAhorro = frmMovimientosAhorro;

        this.frmMovimientosAhorro.btnAlta.addActionListener(this);
        this.frmMovimientosAhorro.btnEliminar.addActionListener(this);
        this.frmMovimientosAhorro.btnBuscar.addActionListener(this);
        this.frmMovimientosAhorro.btnBuscarNuevoMov.addActionListener(this);
        this.frmMovimientosAhorro.btnModificar.addActionListener(this);
        this.frmMovimientosAhorro.btnLimpiar.addActionListener(this);
        this.frmMovimientosAhorro.btnImprimeRecibo.addActionListener(this);
        this.frmMovimientosAhorro.txtRetiroActual.addActionListener(this);
         this.frmMovimientosAhorro.txtNombre.addActionListener(this);
 
    }

   
    public void onkeydown(ActionEvent e){
        if(e.getSource() == frmMovimientosAhorro.txtRetiroActual){
        
        total =  Double.parseDouble(frmMovimientosAhorro.txtTotal.getText());
        retiroactual =  Double.parseDouble(frmMovimientosAhorro.txtRetiroActual.getText());
        saldo = total - retiroactual;
        frmMovimientosAhorro.txtSaldo.setText(String.valueOf(saldo));
        }
    }
     @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == frmMovimientosAhorro.txtRetiroActual){
        
        total =  Double.parseDouble(frmMovimientosAhorro.txtTotal.getText());
        retiroactual =  Double.parseDouble(frmMovimientosAhorro.txtRetiroActual.getText());
        saldo = total - retiroactual;
        saldo = Double.parseDouble(String.format("%.2f", saldo));

                  
        frmMovimientosAhorro.txtSaldo.setText(String.valueOf(saldo));
              
        
        }

        if (e.getSource() == frmMovimientosAhorro.btnAlta) {
             if (frmMovimientosAhorro.txtRfc.getText().isEmpty() ||
                    frmMovimientosAhorro.txtNombre.getText().isEmpty() ||  
                    frmMovimientosAhorro.txtPlantel.getText().isEmpty() ||
                    frmMovimientosAhorro.txtRetiroActual.getText().isEmpty() ||
                    frmMovimientosAhorro.txtCheque.getText().isEmpty() ||
                    frmMovimientosAhorro.txtTransferencia.getText().isEmpty() ||
                    frmMovimientosAhorro.txtNumeroQuincena.getText().isEmpty() 
                    ) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenas");

            } else {
                 saldoverifica = Double.parseDouble(frmMovimientosAhorro.txtSaldo.getText());
                 if(saldoverifica < 0){
                JOptionPane.showMessageDialog(null, "El saldo no puede ser menor a cero");

                 }
                 else {    
            mod.setRfc(frmMovimientosAhorro.txtRfc.getText());
            mod.setNombre(frmMovimientosAhorro.txtNombre.getText());
            mod.setPlantel(frmMovimientosAhorro.txtPlantel.getText());
            mod.setAbono(Double.parseDouble(frmMovimientosAhorro.txtRetiroActual.getText()));
            mod.setCheque(Integer.parseInt(frmMovimientosAhorro.txtCheque.getText()));
            mod.setTransferencia(Integer.parseInt(frmMovimientosAhorro.txtTransferencia.getText()));
            mod.setNumeroquincena(frmMovimientosAhorro.txtNumeroQuincena.getText());
            mod.setLiteral("DIV");
            mod.setUsuarioMovimiento("Admin");
            mod.setTotalAhorrado(Double.parseDouble(frmMovimientosAhorro.txtCapital.getText()));
            mod.setRetirosAnteriores(Double.parseDouble(frmMovimientosAhorro.txtRetiros.getText()));
            mod.setTotalEstado(Double.parseDouble(frmMovimientosAhorro.txtTotal.getText()));
            mod.setTotalSaldo(Double.parseDouble(frmMovimientosAhorro.txtSaldo.getText()));
           
            
            
            if (frmMovimientosAhorro.rbParcial.isSelected())
            {
               mod.setTipoRetiro("parcial");
            }
            else{
               mod.setTipoRetiro("total");
            }
            
            
           if (modC.registrarmovimientoahorro(mod)) {

                JOptionPane.showMessageDialog(null, "Registro Guardado");
                //limpiar();
                frmMovimientosAhorro.btnImprimeRecibo.setVisible(true);
               
            } else {
                JOptionPane.showMessageDialog(null, "Registro No Guardado Intente Mas Tarde");
                //limpiar();

            }
           
            }
        }
        }
    
        if (e.getSource() == frmMovimientosAhorro.btnImprimeRecibo) {
           
           mod.setAhorros(Double.parseDouble(frmMovimientosAhorro.txtCapital.getText()));
           mod.setRetiros(Double.parseDouble(frmMovimientosAhorro.txtRetiros.getText()));
           mod.setSaldo(Double.parseDouble(frmMovimientosAhorro.txtSaldo.getText()));
           mod.setNombre(frmMovimientosAhorro.txtNombre.getText());
           mod.setAbono(Double.parseDouble(frmMovimientosAhorro.txtRetiroActual.getText()));
           
             modC.imprimemandatoahorro(mod);
        }
        
        if (e.getSource() == frmMovimientosAhorro.btnEliminar) {
            
            if (frmMovimientosAhorro.txtId.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor Introduce el Id");

            } else {

            mod.setId(frmMovimientosAhorro.txtId.getText());
            
            if (modC.eliminarmovimientoahorro(mod)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Registro No Eliminado Intente Mas Tarde");
                limpiar();
            }
            
            }

        }

         if (e.getSource() == frmMovimientosAhorro.btnBuscarNuevoMov) {
               mod.setRfc(frmMovimientosAhorro.txtRfc.getText());
             if (modC.buscarmovimientoahorronuevo(mod)) {
                 
                 frmMovimientosAhorro.txtRfc.setText(mod.getRfc());
                 frmMovimientosAhorro.txtNombre.setText(mod.getNombre());
                 frmMovimientosAhorro.txtPlantel.setText(mod.getPlantel());
                 frmMovimientosAhorro.txtCapital.setText(String.valueOf(mod.getAhorros()));
                 frmMovimientosAhorro.txtRetiros.setText(String.valueOf(mod.getRetiros()));
                 frmMovimientosAhorro.txtTotal.setText(String.valueOf(mod.getSaldo()));
               
                 
             }
         }
        
        if (e.getSource() == frmMovimientosAhorro.btnBuscar) {
           
             if (frmMovimientosAhorro.txtId.getText().isEmpty() ) 
                     {

                JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenar");

            } else {
                 
            mod.setId(frmMovimientosAhorro.txtId.getText());
            mod.setRfc(frmMovimientosAhorro.txtRfc.getText());
           // mod.setNumeroquincena(frmMovimientosAhorro.txtNumeroQuincena.getText());
           // mod.setAbono(Double.parseDouble(frmMovimientosAhorro.txtRetiroActual.getText()));

            if (modC.buscarmovimientoahorro(mod)) {
                
                frmMovimientosAhorro.txtRfc.setText(mod.getRfc());
                frmMovimientosAhorro.txtNombre.setText(mod.getNombre());
                frmMovimientosAhorro.txtPlantel.setText(mod.getPlantel());
                frmMovimientosAhorro.txtRetiroActual.setText(String.valueOf(mod.getAbono()));
                frmMovimientosAhorro.txtCheque.setText(String.valueOf(mod.getCheque()));
                frmMovimientosAhorro.txtTransferencia.setText(String.valueOf(mod.getTransferencia()));
                frmMovimientosAhorro.txtNumeroQuincena.setText(mod.getNumeroquincena());
                frmMovimientosAhorro.txtFechaMovimiento.setText(mod.getFechaAbono());
                
                frmMovimientosAhorro.txtCapital.setText(String.valueOf(mod.getTotalAhorrado()));
                frmMovimientosAhorro.txtRetiros.setText(String.valueOf(mod.getRetirosAnteriores()));
                frmMovimientosAhorro.txtTotal.setText(String.valueOf(mod.getTotalEstado()));
                 frmMovimientosAhorro.txtSaldo.setText(String.valueOf(mod.getTotalSaldo()));
                if(mod.getTipoRetiro() == "total")
                {
                  frmMovimientosAhorro.rbTotal.setSelected(true);
                }
                else{
                  frmMovimientosAhorro.rbParcial.setSelected(true);
                
                }
                
                
                frmMovimientosAhorro.txtId.setText(mod.getId());
                frmMovimientosAhorro.btnImprimeRecibo.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "No se encontro el movimiento");
                limpiar();
            }
            
             }
            

        }

        if (e.getSource() == frmMovimientosAhorro.btnLimpiar) {

            limpiar();
        }

        if (e.getSource() == frmMovimientosAhorro.btnModificar) {

            if (frmMovimientosAhorro.txtRfc.getText().isEmpty() ||
                    frmMovimientosAhorro.txtNombre.getText().isEmpty() ||  
                    frmMovimientosAhorro.txtPlantel.getText().isEmpty() ||
                    frmMovimientosAhorro.txtRetiroActual.getText().isEmpty() ||
                    frmMovimientosAhorro.txtCheque.getText().isEmpty() ||
                    frmMovimientosAhorro.txtTransferencia.getText().isEmpty() ||
                    frmMovimientosAhorro.txtNumeroQuincena.getText().isEmpty() 
                    ) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenas");

            } else {
            mod.setRfc(frmMovimientosAhorro.txtRfc.getText());
            mod.setNombre(frmMovimientosAhorro.txtNombre.getText());
            mod.setPlantel(frmMovimientosAhorro.txtPlantel.getText());
            mod.setAbono(Double.parseDouble(frmMovimientosAhorro.txtRetiroActual.getText()));
            mod.setNumeroquincena(frmMovimientosAhorro.txtNumeroQuincena.getText());
            mod.setCheque(Integer.parseInt(frmMovimientosAhorro.txtCheque.getText()));
            mod.setTransferencia(Integer.parseInt(frmMovimientosAhorro.txtTransferencia.getText()));
            mod.setTipoRetiro(String.valueOf(frmMovimientosAhorro.rbgTipoRetiro.getSelection()));
            if (frmMovimientosAhorro.rbParcial.isSelected())
            {
               mod.setTipoRetiro("parcial");
            }
            else{
               mod.setTipoRetiro("total");
            }
            
            
            if (modC.modificarmovimientoahorro(mod)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Registro No Modificado Intente Mas Tarde");
                limpiar();
            }
            
        }

        }
        
        
          if (e.getSource() == frmMovimientosAhorro.txtNombre) {
                 //mod.setRfc(frmCredito.txtRfc.getText());
                 mod.setNombre(frmMovimientosAhorro.txtNombre.getText());
       
            if(modC.completarcampos(mod)){
                    //limpiar();
                    frmMovimientosAhorro.txtRfc.setText(mod.getRfc());
                    //frmCredito.txtNombre.setText(mod.getNombre());
                    frmMovimientosAhorro.txtPlantel.setText(mod.getPlantel());
                   
            
            
            }else{
            
            JOptionPane.showMessageDialog(null, "No se encontro el empleado");
                    limpiar();
            }
        }

    }
    
  
    

    public void limpiar() {
        frmMovimientosAhorro.txtCheque.setText(null);
        frmMovimientosAhorro.txtFechaMovimiento.setText(null);
        frmMovimientosAhorro.txtNombre.setText(null);
        frmMovimientosAhorro.txtNumeroQuincena.setText(null);
        frmMovimientosAhorro.txtPlantel.setText(null);
        frmMovimientosAhorro.txtRetiroActual.setText(null);
        frmMovimientosAhorro.txtRfc.setText(null);
        frmMovimientosAhorro.txtTransferencia.setText(null);
        frmMovimientosAhorro.txtSaldo.setText(null);
        frmMovimientosAhorro.txtRetiros.setText(null);
        frmMovimientosAhorro.txtTotal.setText(null);
        frmMovimientosAhorro.txtCapital.setText(null);

        
    }

}
