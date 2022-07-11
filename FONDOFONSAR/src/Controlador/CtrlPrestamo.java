/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasPrestamos;
import Modelo.Prestamo;
import View.frmPrestamos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author victor
 */
public class CtrlPrestamo implements ActionListener {

    private Prestamo mod;
    private ConsultasPrestamos modC;
    private frmPrestamos frmCredito;
    Double interest = 0.00;
    Double montot = 0.0;
    Double plazot = 0.0;
    Double plazot2 = 0.0;
    Double totalt = 0.0;
    Double totalmasfondot = 0.0;
    Double fondo_garantiat = 0.0;
    Double descuentot = 0.0;
    static final Double interesAnual = 0.07;
    static final int interesAnualEntero = 7;

    public CtrlPrestamo(Prestamo mod, ConsultasPrestamos modc, frmPrestamos frmCredito) {

        this.frmCredito = frmCredito;
        this.mod = mod;
        this.modC = modc;

        this.frmCredito.RbPlaza1.addActionListener(this);
        this.frmCredito.RbPlaza2.addActionListener(this);
        this.frmCredito.RbPlaza3.addActionListener(this);
        
        this.frmCredito.btnBuscar.addActionListener(this);
        this.frmCredito.btnCalcular.addActionListener(this);
        this.frmCredito.btnCancelar.addActionListener(this);
        this.frmCredito.btnEnviar.addActionListener(this);
        this.frmCredito.btnLimpiar.addActionListener(this);
        this.frmCredito.btnModificcar.addActionListener(this);
        this.frmCredito.btnImprimirMandato.addActionListener(this);
        this.frmCredito.txtRfc.addActionListener(this);
        this.frmCredito.txtNombre.addActionListener(this);
 
    }

    public void iniciar() {

        //frmCredito.setTitle("Afiliados Etiqueta");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmCredito.btnEnviar) {
            if (frmCredito.txtRfc.getText().isEmpty()
                    || frmCredito.txtNombre.getText().isEmpty()
                    || frmCredito.txtPlantel.getText().isEmpty()
                    || frmCredito.txtFolio.getText().isEmpty()
                    || frmCredito.txtPuesto.getText().isEmpty()
                    || frmCredito.txtCheque.getText().isEmpty()
                    || frmCredito.txtPlazo.getText().isEmpty()
                    || frmCredito.txtMonto.getText().isEmpty()
                    || frmCredito.txtInteres.getText().isEmpty()
                    || frmCredito.txtDescuento.getText().isEmpty()
                    || frmCredito.txtFechaAlta.getText().isEmpty()
                    || frmCredito.txtTasaInteres.getText().isEmpty()
                    || frmCredito.txtRfcAval.getText().isEmpty()
                    || frmCredito.txtNombreAval.getText().isEmpty()
                    || frmCredito.txtTotal.getText().isEmpty()
                    || frmCredito.txtFondoGarantia.getText().isEmpty()
                    || frmCredito.txtNeto.getText().isEmpty()
                    || frmCredito.txtDireccion.getText().isEmpty()
                    || frmCredito.txtQnaAlta.getText().isEmpty()
                    || frmCredito.txtQnaDescuento.getText().isEmpty()
                    || frmCredito.txtNumeroEmpleado.getText().isEmpty()
                    || frmCredito.txtClabe.getText().isEmpty()
                    || frmCredito.txtTransferencia.getText().isEmpty()
                    || frmCredito.txtFoliosReestructurados.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
               
                if(frmCredito.RbPlaza1.isSelected())
                {    
                 mod.setPlantel(frmCredito.txtPlantel.getText());
                 mod.setPuesto(frmCredito.txtPuesto.getText());
                 mod.setSueldo(Float.parseFloat(frmCredito.txtNeto.getText()));
                 mod.setNumeroEmpleado(frmCredito.txtNumeroEmpleado.getText());
                }else if(frmCredito.RbPlaza2.isSelected())
                {
                 mod.setPlantel(frmCredito.txtPlantel2.getText());
                 mod.setPuesto(frmCredito.txtPuesto2.getText());
                 mod.setSueldo(Float.parseFloat(frmCredito.txtNeto2.getText()));
                 mod.setNumeroEmpleado(frmCredito.txtNumeroEmpleado2.getText());
                }else if(frmCredito.RbPlaza3.isSelected())
                {
                 mod.setPlantel(frmCredito.txtPlantel3.getText());
                 mod.setPuesto(frmCredito.txtPuesto3.getText());
                 mod.setSueldo(Float.parseFloat(frmCredito.txtNeto3.getText()));
                 mod.setNumeroEmpleado(frmCredito.txtNumeroEmpleado3.getText());
                }    
                
                
                mod.setRfc(frmCredito.txtRfc.getText());
                mod.setNombre(frmCredito.txtNombre.getText());
                mod.setFolio(Integer.parseInt(frmCredito.txtFolio.getText()));
                mod.setNumCheque(Integer.parseInt(frmCredito.txtCheque.getText()));
                mod.setPlazo(Integer.parseInt(frmCredito.txtPlazo.getText()));
                mod.setMonto(Float.parseFloat(frmCredito.txtMonto.getText()));
                mod.setInteres(Float.parseFloat(frmCredito.txtInteres.getText()));
                mod.setDescuento(Float.parseFloat(frmCredito.txtDescuento.getText()));
                mod.setFechaAlta(frmCredito.txtFechaAlta.getText());
                mod.setInteresAnual(Integer.parseInt(frmCredito.txtTasaInteres.getText()));
                mod.setRfcAval(frmCredito.txtRfcAval.getText());
                mod.setNombreAval(frmCredito.txtNombreAval.getText());
                mod.setTotal(Float.parseFloat(frmCredito.txtTotal.getText()));
                mod.setFondo_Garantia(Float.parseFloat(frmCredito.txtFondoGarantia.getText()));
                mod.setDomicilio(frmCredito.txtDireccion.getText());
                mod.setQna(frmCredito.txtQnaAlta.getText());
                mod.setQnaDescuento(frmCredito.txtQnaDescuento.getText());
               mod.setClabe(Integer.parseInt(frmCredito.txtClabe.getText()));
                mod.setTransferencia(Integer.parseInt(frmCredito.txtTransferencia.getText()));
                mod.setObservaciones(frmCredito.txtFoliosReestructurados.getText());

                if (modC.registrarprestamo(mod)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    //limpiar();
                    frmCredito.btnImprimirMandato.setVisible(true);
                    //frmImpresionMandato impresion = new frmImpresionMandato();
                    //impresion.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Guardado Intente Mas Tarde");
                    limpiar();

                }

            }

        }

        if (e.getSource() == frmCredito.btnLimpiar) {

            limpiar();
        }
        if (e.getSource() == frmCredito.btnCancelar) {
           
            if (frmCredito.txtFolio.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor introduzca el folio del préstamo");

           } else {
                mod.setFolio(Integer.parseInt(frmCredito.txtFolio.getText()));
                mod.setFechaAlta(frmCredito.txtFechaAlta.getText());
                if (modC.cancelarprestamo(mod)) {
                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Eliminado Intente Mas Tarde");
                    limpiar();
                }

            }

        }

        if (e.getSource() == frmCredito.btnImprimirMandato) {
            
             if (frmCredito.txtFolio.getText().isEmpty())
             {
                    JOptionPane.showMessageDialog(null, "Falta el campo folio");
                 
             }
             else{ 
            mod.setFolio(Integer.parseInt(frmCredito.txtFolio.getText()));
            modC.imprimemandato(mod);
             }
        }

        if (e.getSource() == frmCredito.btnCalcular) {
            if (frmCredito.txtMonto.getText().isEmpty() ||
                frmCredito.txtPlazo.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor introduzca el monto o plazo");

           } else {
                        
            montot = Double.parseDouble(frmCredito.txtMonto.getText());
            plazot = Double.parseDouble(frmCredito.txtPlazo.getText()) / 24;
            plazot2 = Double.parseDouble(frmCredito.txtPlazo.getText());
            interest = (montot * (interesAnual) * plazot);
            interest = Double.parseDouble(String.format("%.2f", interest));
            totalt = montot + interest;
            fondo_garantiat = (2 * montot) / 100;
            totalmasfondot = totalt + fondo_garantiat;
            descuentot = totalt / plazot2;
            descuentot = Double.parseDouble(String.format("%.2f", descuentot));

            frmCredito.txtInteres.setText(String.valueOf(interest));
            frmCredito.txtTotal.setText(String.valueOf(totalt));
            frmCredito.txtFondoGarantia.setText(String.valueOf(fondo_garantiat));
            frmCredito.txtDescuento.setText(String.valueOf(descuentot));
            frmCredito.txtTotalmasFondo.setText(String.valueOf(totalmasfondot));
            frmCredito.txtTasaInteres.setText(String.valueOf(interesAnualEntero));
            if (modC.buscafoliomayor(mod)) {
                frmCredito.txtFolio.setText(String.valueOf(mod.getFolio()));

            }
            }

        }

        if (e.getSource() == frmCredito.btnBuscar) {

            if (frmCredito.txtFolio.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor introduzca el folio del préstamo");

            } else {
                mod.setFolio(Integer.parseInt(frmCredito.txtFolio.getText()));

                if (modC.buscarprestamo(mod)) {

                    frmCredito.txtRfc.setText(mod.getRfc());
                    frmCredito.txtNombre.setText(mod.getNombre());
                    frmCredito.txtDireccion.setText(mod.getDomicilio());
                    frmCredito.txtPlantel.setText(mod.getPlantel());
                    frmCredito.txtPuesto.setText(mod.getPuesto());
                    frmCredito.txtNumeroEmpleado.setText(mod.getNumeroEmpleado());
                    frmCredito.txtQnaAlta.setText(mod.getQna());
                    frmCredito.txtQnaDescuento.setText(mod.getQnaDescuento());
                    frmCredito.txtNeto.setText(String.valueOf(mod.getSueldo()));
                    frmCredito.txtMonto.setText(String.valueOf(mod.getMonto()));
                    frmCredito.txtPlazo.setText(String.valueOf(mod.getPlazo()));
                    //frmCredito.txtFolio.setText(String.valueOf(mod.getFolio()));
                    frmCredito.txtInteres.setText(String.valueOf(mod.getInteres()));
                    frmCredito.txtTotal.setText(String.valueOf(mod.getTotal()));
                    //frmCredito.txtTotalmasFondo.setText(mod.getT);
                    frmCredito.txtDescuento.setText(String.valueOf(mod.getDescuento()));
                    frmCredito.txtTasaInteres.setText(String.valueOf(mod.getInteresAnual()));
                    frmCredito.txtFondoGarantia.setText(String.valueOf(mod.getFondo_Garantia()));
                    //frmCredito.txtFoliosReestructurados.setText(mod.);
                    frmCredito.txtCheque.setText(String.valueOf(mod.getNumCheque()));
                    frmCredito.txtClabe.setText(String.valueOf(mod.getClabe()));
                    frmCredito.txtTransferencia.setText(String.valueOf(mod.getTransferencia()));
                    frmCredito.txtRfcAval.setText(mod.getRfcAval());
                    frmCredito.txtNombreAval.setText(mod.getNombreAval());
                    frmCredito.btnImprimirMandato.setVisible(true);
                  

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro el folio");
                    limpiar();
                }

            }

        }

        if (e.getSource() == frmCredito.txtNombre) {
                 //mod.setRfc(frmCredito.txtRfc.getText());
                 mod.setNombre(frmCredito.txtNombre.getText());
               
            if(modC.completarcampos(mod)){
                    //limpiar();
                    frmCredito.txtRfc.setText(mod.getRfc());
                    //frmCredito.txtNombre.setText(mod.getNombre());
                    frmCredito.txtDireccion.setText(mod.getDomicilio());
                  //  frmCredito.txtPlantel.setText(mod.getPlantel());
                  //  frmCredito.txtPuesto.setText(mod.getPuesto());
                 //   frmCredito.txtNumeroEmpleado.setText(mod.getNumeroEmpleado());
                 //   frmCredito.txtNeto.setText(String.valueOf(mod.getSueldo()));
                   
            
            
            }else{
            
            JOptionPane.showMessageDialog(null, "No se encontro el empleado");
                    limpiar();
            }
            
            if(modC.completarcamposnomina(mod)){
                    //frmCredito.txtRfc.setText(mod.getRfc());
                   //frmCredito.txtNombre.setText(mod.getNombre());
                  //frmCredito.txtDireccion.setText(mod.getDomicilio());
                    frmCredito.txtPlantel.setText(mod.getPlantel());
                    frmCredito.txtPuesto.setText(mod.getPuesto());
                    frmCredito.txtNumeroEmpleado.setText(mod.getNumeroEmpleado());
                    frmCredito.txtNeto.setText(String.valueOf(mod.getSueldo()));
                   
                    frmCredito.txtPlantel2.setText(mod.getPlantel2());
                    frmCredito.txtPuesto2.setText(mod.getPuesto2());
                    frmCredito.txtNumeroEmpleado2.setText(mod.getNumeroEmpleado2());
                    frmCredito.txtNeto2.setText(String.valueOf(mod.getSueldo2()));
                   
                    frmCredito.txtPlantel3.setText(mod.getPlantel3());
                    frmCredito.txtPuesto3.setText(mod.getPuesto3());
                    frmCredito.txtNumeroEmpleado3.setText(mod.getNumeroEmpleado3());
                    frmCredito.txtNeto3.setText(String.valueOf(mod.getSueldo3()));
            
            
            }else{
            
            JOptionPane.showMessageDialog(null, "No se encontro el empleado en la nomina");
                    limpiar();
            }
            
            
            if(modC.completarquincena(mod)){
                    //limpiar();
                    //frmCredito.txtQnaAlta.setText(mod.getQna());
                    frmCredito.txtQnaDescuento.setText(mod.getQnaDescuento());
            
            
            }else{
            
            JOptionPane.showMessageDialog(null, "No se encontro la quincena");
                    limpiar();
            }
            
            if(modC.completarquincenaactual(mod)){
                    //limpiar();
                    frmCredito.txtQnaAlta.setText(mod.getQna());
                    ///frmCredito.txtQnaDescuento.setText(mod.getQnaDescuento());
            
            
            }else{
            
            JOptionPane.showMessageDialog(null, "No se encontro la quincena");
                    limpiar();
            }
            
            
        }
       

    }

    public void limpiar() {
        frmCredito.txtRfc.setText(null);
        frmCredito.txtNombre.setText(null);
        frmCredito.txtDireccion.setText(null);
        frmCredito.txtPlantel.setText(null);
        frmCredito.txtPuesto.setText(null);
        frmCredito.txtNumeroEmpleado.setText(null);
        frmCredito.txtQnaAlta.setText(null);
        frmCredito.txtQnaDescuento.setText(null);
        frmCredito.txtNeto.setText(null);
        frmCredito.txtMonto.setText(null);
        frmCredito.txtPlazo.setText(null);
        frmCredito.txtFolio.setText(null);
        frmCredito.txtInteres.setText(null);
        frmCredito.txtTotal.setText(null);
        frmCredito.txtTotalmasFondo.setText(null);
        frmCredito.txtDescuento.setText(null);
        frmCredito.txtTasaInteres.setText(null);
        frmCredito.txtFondoGarantia.setText(null);
        frmCredito.txtFoliosReestructurados.setText("0");
        frmCredito.txtCheque.setText("0");
        frmCredito.txtClabe.setText("0");
        frmCredito.txtTransferencia.setText("0");
        frmCredito.txtRfcAval.setText("0");
        frmCredito.txtNombreAval.setText("0");

    }

}
