/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultaPlazoFijo;
import Modelo.PlazoFijo;
import View.frmPlazoFijo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class CtrlPlazoFijo implements ActionListener {
    
    
    Double totalretiroreinversion = 0.0;
    Double retiroactual = 0.0;
    Double saldo = 0.0;
    PlazoFijo mod;
    ConsultaPlazoFijo modC;
    frmPlazoFijo frmPlazoFijo;
    String fechaVencimiento;
    String fechaInicio;
    String[] prefijo = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "U", "V", "W", "X", "Y", "Z"};
    int posicionletra;
    int folio;
    int dias;
    Double monto;
    int plazo;
    Double interes;
    Double total;
    Double inversion;
    String folioReinversion;
    Double provision;
    Double provisiondiaria;
    Double tasainteres;
    Double tasainteresreal;
    Double totalarecibir;
    Double interesganado;
    Double tasatotal;
    int contartasajubilado;
    int porcentajesancion;
    int diaspasados;
    int diaspagados;

    public CtrlPlazoFijo(PlazoFijo mod, ConsultaPlazoFijo modC, frmPlazoFijo frmPlazoFijo) {
        this.mod = mod;
        this.modC = modC;
        this.frmPlazoFijo = frmPlazoFijo;
        this.frmPlazoFijo.btnAlta.addActionListener(this);
        this.frmPlazoFijo.btnBuscar.addActionListener(this);
        this.frmPlazoFijo.btnLimpiar.addActionListener(this);
        this.frmPlazoFijo.btnModificar.addActionListener(this);
        this.frmPlazoFijo.btnPagar.addActionListener(this);
        this.frmPlazoFijo.Btneliminar.addActionListener(this);
        this.frmPlazoFijo.btnCalcular.addActionListener(this);
        this.frmPlazoFijo.RbMontoAbono.addActionListener(this);
        this.frmPlazoFijo.RbMontoRetiro.addActionListener(this);
        
        this.frmPlazoFijo.txtnombre.addActionListener(this);
        this.frmPlazoFijo.btnEnviarPago.addActionListener(this);
        //this.frmPlazoFijo.btnEnviarPago.addActionListener(this);
        this.frmPlazoFijo.CheckReinvertir.addActionListener(this);
        this.frmPlazoFijo.btnCalcularReinversion.addActionListener(this);

        this.frmPlazoFijo.btnImprimirConstancias.addActionListener(this);
        this.frmPlazoFijo.btnImprimirReinversion.addActionListener(this);
        this.frmPlazoFijo.btnImprimirRecibo.addActionListener(this);
        this.frmPlazoFijo.btnImprimirReciboRetiro.addActionListener(this);
       
         this.frmPlazoFijo.txtMontoRetiro.addActionListener(this);
         this.frmPlazoFijo.txtMontoAbono.addActionListener(this);
       

    }
    
    
    public void onkeydown(ActionEvent e){
        if(e.getSource() == frmPlazoFijo.txtMontoRetiro){
        
        //totalretiroreinversion =  Double.parseDouble(frmPlazoFijo.txtMontoInicial.getText());
        //retiroactual =  Double.parseDouble(frmPlazoFijo.txtMontoRetiro.getText());
        //saldo = total - retiroactual;
        //frmPlazoFijo.txtCapital1.setText("hola");
        //frmPlazoFijo.txtCapital1.setText("que onda");
        JOptionPane.showMessageDialog(null, "Plazo Fijo");

        }
    
        /*if(e.getSource() == frmPlazoFijo.txtMontoAbono){
        
        totalretiroreinversion =  Double.parseDouble(frmPlazoFijo.txtMontoInicial.getText());
        retiroactual =  Double.parseDouble(frmPlazoFijo.txtMontoAbono.getText());
        saldo = total - retiroactual;
        frmPlazoFijo.txtCapital1.setText(String.valueOf(saldo));
        }*/
    
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        
        if(e.getSource() == frmPlazoFijo.txtMontoRetiro){
        
        totalretiroreinversion =  Double.parseDouble(frmPlazoFijo.txtMontoInicial.getText());
        retiroactual =  Double.parseDouble(frmPlazoFijo.txtMontoRetiro.getText());
        saldo = totalretiroreinversion - retiroactual;
        frmPlazoFijo.txtCapital1.setText(String.valueOf(saldo));
       
        
        }
        
        if(e.getSource() == frmPlazoFijo.txtMontoAbono){
        
        totalretiroreinversion =  Double.parseDouble(frmPlazoFijo.txtMontoInicial.getText());
        retiroactual =  Double.parseDouble(frmPlazoFijo.txtMontoAbono.getText());
        saldo = totalretiroreinversion + retiroactual;
        frmPlazoFijo.txtCapital1.setText(String.valueOf(saldo));
        }
        
        
        
        if (frmPlazoFijo.RbMontoAbono.isSelected()) {
            frmPlazoFijo.txtMontoRetiro.setEnabled(false);
            frmPlazoFijo.txtMontoRetiro.setText("0");
            frmPlazoFijo.txtMontoAbono.setEnabled(true);
                
            
        }

        if (frmPlazoFijo.RbMontoRetiro.isSelected()) {
            frmPlazoFijo.txtMontoRetiro.setEnabled(true);
            frmPlazoFijo.txtMontoAbono.setEnabled(false);
            frmPlazoFijo.txtMontoAbono.setText("0");
            
        }
        
        
        
        
        if (e.getSource() == frmPlazoFijo.btnImprimirConstancias) {
            mod.setFolio(frmPlazoFijo.txtFolio.getText());
            modC.imprimemandato(mod);
        }
        
        if (e.getSource() == frmPlazoFijo.btnImprimirRecibo) {
            mod.setFolio(frmPlazoFijo.txtFolio.getText());
            modC.imprimerecibo(mod);
        }
        if (e.getSource() == frmPlazoFijo.btnImprimirReciboRetiro) {
            mod.setFolio(frmPlazoFijo.txtFolio.getText());
            modC.imprimereciboretiro(mod);
        }
        
        
         if (e.getSource() == frmPlazoFijo.btnImprimirReinversion) {
            mod.setFolioReinversion(frmPlazoFijo.txtFolio1.getText());
            modC.imprimemandato(mod);
        }

        if (e.getSource() == frmPlazoFijo.CheckReinvertir) {

            if (frmPlazoFijo.CheckReinvertir.isSelected()) {
                frmPlazoFijo.jLabel18.setVisible(true);
                frmPlazoFijo.jLabel19.setVisible(true);
                frmPlazoFijo.jLabel20.setVisible(true);
                frmPlazoFijo.jLabel21.setVisible(true);
                frmPlazoFijo.jLabel22.setVisible(true);
                frmPlazoFijo.jLabel23.setVisible(true);
                frmPlazoFijo.jLabel24.setVisible(true);
                frmPlazoFijo.jLabel25.setVisible(true);
                frmPlazoFijo.jLabel26.setVisible(true);
                frmPlazoFijo.jLabel27.setVisible(true);
                frmPlazoFijo.jDFechaInicio1.setVisible(true);
                frmPlazoFijo.txtFechaVencimiento1.setVisible(true);
                frmPlazoFijo.txtTasaInteres1.setVisible(true);
                frmPlazoFijo.txtTasaTotal1.setVisible(true);
                frmPlazoFijo.txtCapital1.setVisible(true);
                frmPlazoFijo.txtPlazo1.setVisible(true);
                frmPlazoFijo.txtFolio1.setVisible(true);
                frmPlazoFijo.txtInteres1.setVisible(true);
                frmPlazoFijo.txtTotal1.setVisible(true);
                frmPlazoFijo.txtProvision1.setVisible(true);
                frmPlazoFijo.btnCalcularReinversion.setVisible(true);
                frmPlazoFijo.btnCalcular.setVisible(false);
                frmPlazoFijo.jLabel4.setVisible(true);
                frmPlazoFijo.txtMontoInicial.setVisible(true);
                frmPlazoFijo.txtMontoRetiro.setVisible(true);
                frmPlazoFijo.txtMontoAbono.setVisible(true);
                frmPlazoFijo.RbMontoRetiro.setVisible(true);
                frmPlazoFijo.RbMontoAbono.setVisible(true);
                frmPlazoFijo.txtMontoInicial.setText(frmPlazoFijo.txtTotalAPagar.getText());
                frmPlazoFijo.txtCapital1.setText(frmPlazoFijo.txtTotalAPagar.getText());
                frmPlazoFijo.txtPlazo1.setText("12");
               
                   } else {
                frmPlazoFijo.jLabel18.setVisible(false);
                frmPlazoFijo.jLabel19.setVisible(false);
                frmPlazoFijo.jLabel20.setVisible(false);
                frmPlazoFijo.jLabel21.setVisible(false);
                frmPlazoFijo.jLabel22.setVisible(false);
                frmPlazoFijo.jLabel23.setVisible(false);
                frmPlazoFijo.jLabel24.setVisible(false);
                frmPlazoFijo.jLabel25.setVisible(false);
                frmPlazoFijo.jLabel26.setVisible(false);
                frmPlazoFijo.jLabel27.setVisible(false);
                frmPlazoFijo.jDFechaInicio1.setVisible(false);
                frmPlazoFijo.txtFechaVencimiento1.setVisible(false);
                frmPlazoFijo.txtTasaInteres1.setVisible(false);
                frmPlazoFijo.txtTasaTotal1.setVisible(false);
                frmPlazoFijo.txtCapital1.setVisible(false);
                frmPlazoFijo.txtPlazo1.setVisible(false);
                frmPlazoFijo.txtFolio1.setVisible(false);
                frmPlazoFijo.txtInteres1.setVisible(false);
                frmPlazoFijo.txtTotal1.setVisible(false);
                frmPlazoFijo.txtProvision1.setVisible(false);
                frmPlazoFijo.btnCalcularReinversion.setVisible(false);
                frmPlazoFijo.btnCalcular.setVisible(true);
                frmPlazoFijo.jLabel4.setVisible(false);
                frmPlazoFijo.txtMontoInicial.setVisible(false);
                frmPlazoFijo.txtMontoRetiro.setVisible(false);
                frmPlazoFijo.txtMontoAbono.setVisible(false);
                frmPlazoFijo.RbMontoRetiro.setVisible(false);
                frmPlazoFijo.RbMontoAbono.setVisible(false);

            }

        }

        if (e.getSource() == frmPlazoFijo.txtnombre) {

            mod.setNombre(frmPlazoFijo.txtnombre.getText());

            modC.completarcampos(mod);
            frmPlazoFijo.txtrfc.setText(mod.getRfc());
            frmPlazoFijo.txtnombre.setText(mod.getNombre());
            //JOptionPane.showMessageDialog(null, "Presionaste Autocompletar Campos");

        }

        if (e.getSource() == frmPlazoFijo.btnEnviarPago) {

            if (frmPlazoFijo.txtfechacreacion.getText().isEmpty()
                    //|| frmPlazoFijo.txtrfc.getText().isEmpty()
                    || frmPlazoFijo.txtnombre.getText().isEmpty()
                    || frmPlazoFijo.txtQuincena.getText().isEmpty()
                    || frmPlazoFijo.txtFechaVencimiento.getText().isEmpty()
                    || frmPlazoFijo.txtTasaInteres.getText().isEmpty()
                    || frmPlazoFijo.txtCapital.getText().isEmpty()
                    || frmPlazoFijo.txtPlazo.getText().isEmpty()
                    || frmPlazoFijo.txtFolio.getText().isEmpty()
                    || frmPlazoFijo.txtInteres.getText().isEmpty()
                    || frmPlazoFijo.txtTotal.getText().isEmpty()
                    || frmPlazoFijo.txtReferencia.getText().isEmpty()
                    || frmPlazoFijo.txtQnaPago.getText().isEmpty()
                    || frmPlazoFijo.txtProvision.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenar primer if");

            } else {

                if (frmPlazoFijo.CheckReinvertir.isSelected()) {

                    if (frmPlazoFijo.txtFechaVencimiento1.getText().isEmpty()
                            || frmPlazoFijo.txtTasaInteres1.getText().isEmpty()
                            || frmPlazoFijo.txtTasaTotal1.getText().isEmpty()
                            || frmPlazoFijo.txtCapital1.getText().isEmpty()
                            || frmPlazoFijo.txtPlazo1.getText().isEmpty()
                            || frmPlazoFijo.txtFolio1.getText().isEmpty()
                            || frmPlazoFijo.txtInteres1.getText().isEmpty()
                            || frmPlazoFijo.txtTotal1.getText().isEmpty()
                            || frmPlazoFijo.txtProvision1.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenar");

                    } else {

                        //Datos de pago
                        mod.setInteresGanadoPagar(Double.parseDouble(frmPlazoFijo.txtIntegresGanado.getText()));
                        mod.setTotalPagadoPagar(Double.parseDouble(frmPlazoFijo.txtTotalAPagar.getText()));
                        mod.setQuincenaPago(frmPlazoFijo.txtQnaPago.getText());
                        mod.setFechaPago(frmPlazoFijo.txtfechacreacion.getText());
                        mod.setSancion(frmPlazoFijo.txtSancion.getText());
                        mod.setReferencia(frmPlazoFijo.txtReferencia.getText());
                        mod.setFolio(frmPlazoFijo.txtFolio.getText());
                        
                        ////Datos de Reinversión        
                        mod.setFechaInversion(frmPlazoFijo.txtfechacreacion.getText());
                        mod.setRfc(frmPlazoFijo.txtrfc.getText());
                        mod.setNombre(frmPlazoFijo.txtnombre.getText());
                        mod.setQuincena(frmPlazoFijo.txtQuincena.getText());
                        String formato = frmPlazoFijo.jDFechaInicio1.getDateFormatString();
                        Date date = frmPlazoFijo.jDFechaInicio1.getDate();
                        SimpleDateFormat sdf = new SimpleDateFormat(formato);
                        mod.setFechaInicio(String.valueOf(sdf.format(date)));
                        mod.setFechaVencimiento(frmPlazoFijo.txtFechaVencimiento1.getText());
                        mod.setTasaInteres(Double.parseDouble(frmPlazoFijo.txtTasaInteres1.getText()));
                        mod.setCapital(Double.parseDouble(frmPlazoFijo.txtCapital1.getText()));
                        mod.setPlazo(Integer.parseInt(frmPlazoFijo.txtPlazo1.getText()));
                        mod.setFolioReinversion(frmPlazoFijo.txtFolio1.getText());
                        mod.setInteres(Double.parseDouble(frmPlazoFijo.txtInteres1.getText()));
                        mod.setTasaTotal(Double.parseDouble(frmPlazoFijo.txtTasaTotal1.getText()));
                        mod.setTotal(Double.parseDouble(frmPlazoFijo.txtTotal1.getText()));
                        mod.setProvision(Double.parseDouble(frmPlazoFijo.txtProvision1.getText()));
                        mod.setMontoAbono(Double.parseDouble(frmPlazoFijo.txtMontoAbono.getText()));
                        mod.setMontoRetiro(Double.parseDouble(frmPlazoFijo.txtMontoRetiro.getText()));
                       
                        if (modC.pagarplazofijo(mod) && modC.registrar(mod)) {

                            JOptionPane.showMessageDialog(null, "Inversión Pagada Y Reinvertida");
                            //limpiar();
                            frmPlazoFijo.btnImprimirReinversion.setVisible(true);
                            frmPlazoFijo.btnEnviarPago.setVisible(false);
                            frmPlazoFijo.btnPagar.setVisible(true);
                    

                        } else {
                            JOptionPane.showMessageDialog(null, "Registro No Guardado Intente Mas Tarde");
                            limpiar();

                        }

                    }

                } else {

                    mod.setInteresGanadoPagar(Double.parseDouble(frmPlazoFijo.txtIntegresGanado.getText()));
                    mod.setTotalPagadoPagar(Double.parseDouble(frmPlazoFijo.txtTotalAPagar.getText()));
                    mod.setQuincenaPago(frmPlazoFijo.txtQnaPago.getText());
                    mod.setFechaPago(frmPlazoFijo.txtfechacreacion.getText());
                    mod.setSancion(frmPlazoFijo.txtSancion.getText());
                    mod.setReferencia(frmPlazoFijo.txtReferencia.getText());
                    mod.setFolio(frmPlazoFijo.txtFolio.getText());

                    if (modC.pagarplazofijo(mod)) {

                        JOptionPane.showMessageDialog(null, "Inversion Pagada Gracias");
                        //limpiar();
                        frmPlazoFijo.btnImprimirRecibo.setVisible(true);
                        frmPlazoFijo.btnImprimirReciboRetiro.setVisible(true);
                        
                        frmPlazoFijo.btnEnviarPago.setVisible(false);
                        frmPlazoFijo.btnPagar.setVisible(true);
                    
                        //limpiar();
                        // frmPlazoFijo.btnImprimirConstancias.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(null, "No se Pudo Pagar La inversión");
                        limpiar();

                    }

                }

            }

        }

        if (e.getSource() == frmPlazoFijo.btnAlta) {

            if (frmPlazoFijo.txtfechacreacion.getText().isEmpty()
                    || frmPlazoFijo.txtrfc.getText().isEmpty()
                    || frmPlazoFijo.txtnombre.getText().isEmpty()
                    || frmPlazoFijo.txtQuincena.getText().isEmpty()
                    || frmPlazoFijo.txtFechaVencimiento.getText().isEmpty()
                    || frmPlazoFijo.txtTasaInteres.getText().isEmpty()
                    || frmPlazoFijo.txtTasaTotal.getText().isEmpty()
                    || frmPlazoFijo.txtCapital.getText().isEmpty()
                    || frmPlazoFijo.txtPlazo.getText().isEmpty()
                    || frmPlazoFijo.txtFolio.getText().isEmpty()
                    || frmPlazoFijo.txtInteres.getText().isEmpty()
                    || frmPlazoFijo.txtTotal.getText().isEmpty()
                    || frmPlazoFijo.txtProvision.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenar");

            } else {

                mod.setFechaInversion(frmPlazoFijo.txtfechacreacion.getText());
                mod.setRfc(frmPlazoFijo.txtrfc.getText());
                mod.setNombre(frmPlazoFijo.txtnombre.getText());
                mod.setQuincena(frmPlazoFijo.txtQuincena.getText());
                String formato = frmPlazoFijo.jDFechaInicio.getDateFormatString();
                Date date = frmPlazoFijo.jDFechaInicio.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat(formato);
                mod.setFechaInicio(String.valueOf(sdf.format(date)));
                //mod.setFechaInicio(frmPlazoFijo.txtFechaInicio.getText());
                mod.setFechaVencimiento(frmPlazoFijo.txtFechaVencimiento.getText());
                mod.setTasaInteres(Double.parseDouble(frmPlazoFijo.txtTasaInteres.getText()));
                mod.setTasaTotal(Double.parseDouble(frmPlazoFijo.txtTasaTotal.getText()));
                mod.setCapital(Double.parseDouble(frmPlazoFijo.txtCapital.getText()));
                mod.setPlazo(Integer.parseInt(frmPlazoFijo.txtPlazo.getText()));
                mod.setFolio(frmPlazoFijo.txtFolio.getText());
                mod.setInteres(Double.parseDouble(frmPlazoFijo.txtInteres.getText()));
                mod.setTotal(Double.parseDouble(frmPlazoFijo.txtTotal.getText()));
                mod.setProvision(Double.parseDouble(frmPlazoFijo.txtProvision.getText()));
                mod.setTipoInversionista(String.valueOf(frmPlazoFijo.cbTipoInversionista.getSelectedItem()));
                if (modC.registrar(mod)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    //limpiar();
                    frmPlazoFijo.btnImprimirConstancias.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Guardado Intente Mas Tarde");
                    limpiar();

                }

            }

        }
        if (e.getSource() == frmPlazoFijo.Btneliminar) {
            if (frmPlazoFijo.txtFolio.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Faltan Campo Folio");

            } else {
                mod.setFolio(frmPlazoFijo.txtFolio.getText());

                if (modC.eliminar(mod)) {
                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Eliminado Intente Mas Tarde");
                    limpiar();
                }

            }

        }
        if (e.getSource() == frmPlazoFijo.btnModificar) {

            if (frmPlazoFijo.txtfechacreacion.getText().isEmpty()
                    || frmPlazoFijo.txtrfc.getText().isEmpty()
                    || frmPlazoFijo.txtnombre.getText().isEmpty()
                    || frmPlazoFijo.txtQuincena.getText().isEmpty()
                    || frmPlazoFijo.txtFechaVencimiento.getText().isEmpty()
                    || frmPlazoFijo.txtTasaInteres.getText().isEmpty()
                    || frmPlazoFijo.txtTasaTotal.getText().isEmpty()
                    || frmPlazoFijo.txtCapital.getText().isEmpty()
                    || frmPlazoFijo.txtPlazo.getText().isEmpty()
                    || frmPlazoFijo.txtFolio.getText().isEmpty()
                    || frmPlazoFijo.txtInteres.getText().isEmpty()
                    || frmPlazoFijo.txtTotal.getText().isEmpty()
                    || frmPlazoFijo.txtProvision.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Faltan Campos Obligatorios por llenar");

            } else {
                mod.setFechaInversion(frmPlazoFijo.txtfechacreacion.getText());
                mod.setRfc(frmPlazoFijo.txtrfc.getText());
                mod.setNombre(frmPlazoFijo.txtnombre.getText());
                mod.setQuincena(frmPlazoFijo.txtQuincena.getText());
                String formato = frmPlazoFijo.jDFechaInicio.getDateFormatString();
                Date date = frmPlazoFijo.jDFechaInicio.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat(formato);
                mod.setFechaInicio(String.valueOf(sdf.format(date)));
                mod.setFechaVencimiento(frmPlazoFijo.txtFechaVencimiento.getText());
                mod.setTasaInteres(Double.parseDouble(frmPlazoFijo.txtTasaInteres.getText()));
                mod.setTasaTotal(Double.parseDouble(frmPlazoFijo.txtTasaTotal.getText()));
                mod.setCapital(Double.parseDouble(frmPlazoFijo.txtCapital.getText()));
                mod.setPlazo(Integer.parseInt(frmPlazoFijo.txtPlazo.getText()));
                mod.setFolio(frmPlazoFijo.txtFolio.getText());
                mod.setInteres(Double.parseDouble(frmPlazoFijo.txtInteres.getText()));
                mod.setTotal(Double.parseDouble(frmPlazoFijo.txtTotal.getText()));
                mod.setProvision(Double.parseDouble(frmPlazoFijo.txtProvision.getText()));

                if (modC.modificar(mod)) {

                    JOptionPane.showMessageDialog(null, "Registro Modificado");
                    limpiar();

                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Modificado Intente Mas Tarde");
                    limpiar();

                }

            }

        }
        if (e.getSource() == frmPlazoFijo.btnBuscar) {
            if (frmPlazoFijo.txtFolio.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Faltan Campo Folio");

            } else {
                mod.setFolio(frmPlazoFijo.txtFolio.getText());
                if (modC.buscar(mod)) {

                    frmPlazoFijo.txtfechacreacion.setText(mod.getFechaInversion());
                    frmPlazoFijo.txtrfc.setText(mod.getRfc());
                    frmPlazoFijo.txtnombre.setText(mod.getNombre());
                    frmPlazoFijo.txtQuincena.setText(mod.getQuincena());

                    try {

                        String formato = mod.getFechaInicio();
                        
                        Date dateinicio = new SimpleDateFormat("yyyy-MM-dd").parse(formato);
                       
                        java.util.Date fecha = new Date();
                        //SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                        diaspasados = (int) ( (fecha.getTime() - dateinicio.getTime()) / 86400000 );
                        if (diaspasados <= 31) {
                            porcentajesancion = 100;
                        } else if (diaspasados > 31 && diaspasados <= 70) {
                            porcentajesancion = 80;

                        } else if (diaspasados > 70 && diaspasados <= 120) {
                            porcentajesancion = 70;

                        } else if (diaspasados > 120 && diaspasados <= 169) {
                            porcentajesancion = 60;

                        } else {
                            porcentajesancion = 0;

                        }

                    frmPlazoFijo.jDFechaInicio.setDate(dateinicio);
                    } catch (ParseException ex) {
                        Logger.getLogger(CtrlPlazoFijo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    inversion = mod.getCapital();
                    provisiondiaria = mod.getProvisionDiaria();
                    if(diaspasados > 180){
                    diaspasados = 180;
                    }
                    
                    interesganado = provisiondiaria * diaspasados;
                    
                    
                    if (porcentajesancion > 0) {
                        interesganado = interesganado - (((provisiondiaria * diaspasados) * porcentajesancion) / 100);
                    } else {
                        interesganado = provisiondiaria * diaspasados;

                    }
                    
                    if(diaspasados == 180){
                    interesganado = mod.getInteres();
                     }
                    
                    totalarecibir = inversion + interesganado;
                    frmPlazoFijo.txtFechaVencimiento.setText(mod.getFechaVencimiento());
                    frmPlazoFijo.txtTasaInteres.setText(String.valueOf(mod.getTasaInteres()));
                    //frmPlazoFijo.txtTasaTotal.setText(String.valueOf(mod.getTasaTotal()));
                    frmPlazoFijo.txtCapital.setText(String.valueOf(mod.getCapital()));
                    frmPlazoFijo.txtPlazo.setText(String.valueOf(mod.getPlazo()));
                    frmPlazoFijo.txtInteres.setText(String.valueOf(mod.getInteres()));
                    frmPlazoFijo.txtTotal.setText(String.valueOf(mod.getTotal()));
                    frmPlazoFijo.txtProvision.setText(String.valueOf(mod.getProvision()));
                    frmPlazoFijo.txtStatus.setText(String.valueOf(mod.getStatus()));
                    interesganado = Double.parseDouble(String.format("%.2f", interesganado));
                    totalarecibir = Double.parseDouble(String.format("%.2f", totalarecibir));

                    if(!frmPlazoFijo.txtStatus.getText().equals("A"))
                    {
                        try {
                             String formato = mod.getFechaInicio();
                            Date dateinicio = new SimpleDateFormat("yyyy-MM-dd").parse(formato);
                            String formatodiaspagados = mod.getFechaPago();
                            Date dateiniciopagados = new SimpleDateFormat("yyyy-MM-dd").parse(formatodiaspagados);
                            diaspagados = (int) ((dateiniciopagados.getTime() - dateinicio.getTime()) / 86400000);
                        } catch (ParseException ex) {
                            Logger.getLogger(CtrlPlazoFijo.class.getName()).log(Level.SEVERE, null, ex);
                        }  
                    frmPlazoFijo.txtIntegresGanado.setText(String.valueOf(mod.getInteres()));
                    frmPlazoFijo.txtTotalAPagar.setText(String.valueOf(mod.getTotal()));
                    frmPlazoFijo.txtSancion.setText(String.valueOf(mod.getSancion()));
                    frmPlazoFijo.txtDiasPagados.setText(String.valueOf(diaspagados));
                    frmPlazoFijo.txtQnaPago.setText(String.valueOf(mod.getQuincenaPago()));
                  
                    
                    }else{
                    diaspagados = 0;
                    frmPlazoFijo.txtIntegresGanado.setText(String.valueOf(interesganado));
                    frmPlazoFijo.txtTotalAPagar.setText(String.valueOf(totalarecibir));
                    frmPlazoFijo.txtSancion.setText(String.valueOf(porcentajesancion));
                    frmPlazoFijo.txtDiasPagados.setText(String.valueOf(diaspagados));
                    frmPlazoFijo.txtQnaPago.setText("");
                  
                    }
                    frmPlazoFijo.txtDias.setText(String.valueOf(diaspasados));
                    frmPlazoFijo.txtReferencia.setText(String.valueOf(mod.getReferencia()));
                    frmPlazoFijo.txtFechaPago.setText(String.valueOf(mod.getFechaPago()));
                    
                    frmPlazoFijo.btnImprimirConstancias.setVisible(true);
                    frmPlazoFijo.cbTipoInversionista.setSelectedItem(mod.getTipoInversionista());
                    if (frmPlazoFijo.cbTipoInversionista.getSelectedItem() == "JUBILADO") {
                        //contartasajubilado = 6;
                        contartasajubilado = 3;
                      //  frmPlazoFijo.cbTipoInversionista.setSelectedItem("JUBILADO");
                      //  frmPlazoFijo.cbTipoInversionista.setSelectedItem(mod.getTipoInversionista());
                      
                    } else {
                        //contartasajubilado = 5;
                         contartasajubilado = 3;
                      //  frmPlazoFijo.cbTipoInversionista.setSelectedItem(mod.getTipoInversionista());
                       // frmPlazoFijo.cbTipoInversionista.setSelectedItem("NO JUBILADO");

                    }
                    tasainteres = mod.getTasaInteres();
                    tasatotal = (tasainteres * contartasajubilado);
                    frmPlazoFijo.btnEnviarPago.setVisible(false);
                    frmPlazoFijo.btnPagar.setVisible(true);
                    
                    if(!frmPlazoFijo.txtStatus.getText().equals("A"))
                    {    
                    frmPlazoFijo.btnImprimirRecibo.setVisible(true);
                    //frmPlazoFijo.btnImprimirReciboRetiro.setVisible(true);
                    }
                       frmPlazoFijo.btnImprimirReciboRetiro.setVisible(true);
                 
                    //frmPlazoFijo.cbTipoInversionista.setSelectedItem("NO JUBILADO");
                    //frmPlazoFijo.txtTasaTotal1.setText(Stringtasatotal);

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro el folio");
                    limpiar();
                }

            }

        }
        if (e.getSource() == frmPlazoFijo.btnLimpiar) {

            limpiar();

        }

        if (e.getSource() == frmPlazoFijo.btnPagar) {
            if(!frmPlazoFijo.txtStatus.getText().equals("A"))
            {
            JOptionPane.showMessageDialog(null, "La inversion ya esta pagada o fue cancelada");
            }    
            
            else{
            frmPlazoFijo.btnPagar.setVisible(false);
            frmPlazoFijo.btnEnviarPago.setVisible(true);
            frmPlazoFijo.txtReferencia.setEditable(true);
            frmPlazoFijo.txtReferencia.setText("0");
           
            frmPlazoFijo.txtQnaPago.setEditable(true);
            frmPlazoFijo.CheckReinvertir.setEnabled(true);
            frmPlazoFijo.btnImprimirConstancias.setVisible(false);
            frmPlazoFijo.txtCapital.setEditable(false);
            frmPlazoFijo.txtPlazo.setEditable(false);
            frmPlazoFijo.txtFolio.setEditable(false);
            frmPlazoFijo.jDFechaInicio.setEnabled(false);
            //frmPlazoFijo.btnImprimirRecibo.setVisible(true);
            }
            /*frmPlazoFijo.btnPagar.setVisible(false);
            frmPlazoFijo.txtReferencia.enable(true);
            frmPlazoFijo.txtQnaPago.enable(true);
            frmPlazoFijo.CheckReinvertir.enable(true);*/
        }

        if (e.getSource() == frmPlazoFijo.btnCalcular) {
            Date date;
            date = frmPlazoFijo.jDFechaInicio.getDate();
            if (date == null) {

                JOptionPane.showMessageDialog(null, "Faltan Campo Fecha inicio");

            } else {

                String formato = frmPlazoFijo.jDFechaInicio.getDateFormatString();
                Date dates = frmPlazoFijo.jDFechaInicio.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat(formato);
                mod.setFechaInicio(String.valueOf(sdf.format(dates)));
                //mod.setFechaVencimiento(fechaVencimiento);

                if (modC.buscatasainteres(mod)) {

                    if (frmPlazoFijo.txtCapital.getText().isEmpty() || frmPlazoFijo.txtPlazo.getText().isEmpty()) {

                        JOptionPane.showMessageDialog(null, "Faltan Campo Monto o Plazo");

                    } else {

                        if (frmPlazoFijo.cbTipoInversionista.getSelectedItem() == "JUBILADO") {
                           // contartasajubilado = 6;
                           contartasajubilado = 3;
                        } else {
                           // contartasajubilado = 5;
                           contartasajubilado = 3;
                        }
                        tasainteres = mod.getTasaInteres();
                        tasainteresreal = mod.getTasaInteres();

                        tasatotal = (tasainteres * contartasajubilado);
                        tasainteres = (tasainteres * contartasajubilado) / 100;
                        monto = Double.parseDouble(frmPlazoFijo.txtCapital.getText());
                        plazo = Integer.parseInt(frmPlazoFijo.txtPlazo.getText());
                        interes = monto * tasainteres * plazo / 24;
                        provision = interes / plazo;
                        total = monto + interes;
                        dias = (plazo / 2) * 30;

                        interes = Double.parseDouble(String.format("%.2f", interes));
                        provision = Double.parseDouble(String.format("%.2f", provision));
                        tasatotal = Double.parseDouble(String.format("%.2f", tasatotal));
                        total = Double.parseDouble(String.format("%.2f", total));
                        Date fechafinal = sumarDiasAFecha(dates, dias);
                        String formatofinal = frmPlazoFijo.jDFechaInicio.getDateFormatString();
                        SimpleDateFormat sdffinal = new SimpleDateFormat(formatofinal);
                        mod.setFechaInicio(String.valueOf(sdffinal.format(fechafinal)));
                        frmPlazoFijo.txtFechaVencimiento.setText(String.valueOf(sdffinal.format(fechafinal)));

                        frmPlazoFijo.txtTasaInteres.setText(String.valueOf(tasainteresreal));
                        frmPlazoFijo.txtInteres.setText(String.valueOf(interes));
                        frmPlazoFijo.txtProvision.setText(String.valueOf(provision));
                        frmPlazoFijo.txtTasaTotal.setText(String.valueOf(tasatotal));
                        frmPlazoFijo.txtTotal.setText(String.valueOf(total));

                        if (modC.buscafoliomayor(mod)) {
                            frmPlazoFijo.txtFolio.setText(String.valueOf(mod.getFolio()));

                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontro folio mayor");

                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro la tasa del mes");

                }

            }
        }

        if (e.getSource() == frmPlazoFijo.btnCalcularReinversion) {
            Date date;
            date = frmPlazoFijo.jDFechaInicio1.getDate();
            if (date == null) {

                JOptionPane.showMessageDialog(null, "Faltan Campo Fecha inicio");

            } else {

                String formato = frmPlazoFijo.jDFechaInicio1.getDateFormatString();
                Date dates = frmPlazoFijo.jDFechaInicio1.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat(formato);
                mod.setFechaInicio(String.valueOf(sdf.format(dates)));
                //mod.setFechaVencimiento(fechaVencimiento);

                if (modC.buscatasainteres(mod)) {

                    if (frmPlazoFijo.txtCapital1.getText().isEmpty() || frmPlazoFijo.txtPlazo1.getText().isEmpty()) {

                        JOptionPane.showMessageDialog(null, "Faltan Campo Monto o Plazo");

                    } else {

                        if (frmPlazoFijo.cbTipoInversionista.getSelectedItem() == "JUBILADO") {
                            //contartasajubilado = 6;
                            contartasajubilado = 3;
                     
                        } else {
                            //contartasajubilado = 5;
                            contartasajubilado = 3;
                        }
                        tasainteres = mod.getTasaInteres();
                        tasainteresreal = mod.getTasaInteres();

                        tasatotal = (tasainteres * contartasajubilado);
                        tasainteres = (tasainteres * contartasajubilado) / 100;
                        monto = Double.parseDouble(frmPlazoFijo.txtCapital1.getText());
                        plazo = Integer.parseInt(frmPlazoFijo.txtPlazo1.getText());
                        interes = monto * tasainteres * plazo / 24;
                        provision = interes / plazo;
                        total = monto + interes;
                        dias = (plazo / 2) * 30;

                        interes = Double.parseDouble(String.format("%.2f", interes));
                        provision = Double.parseDouble(String.format("%.2f", provision));
                        tasatotal = Double.parseDouble(String.format("%.2f", tasatotal));
                        total = Double.parseDouble(String.format("%.2f", total));
                        Date fechafinal = sumarDiasAFecha(dates, dias);
                        String formatofinal = frmPlazoFijo.jDFechaInicio1.getDateFormatString();
                        SimpleDateFormat sdffinal = new SimpleDateFormat(formatofinal);
                        mod.setFechaInicio(String.valueOf(sdffinal.format(fechafinal)));
                        frmPlazoFijo.txtFechaVencimiento1.setText(String.valueOf(sdffinal.format(fechafinal)));

                        frmPlazoFijo.txtTasaInteres1.setText(String.valueOf(tasainteresreal));
                        frmPlazoFijo.txtInteres1.setText(String.valueOf(interes));
                        frmPlazoFijo.txtProvision1.setText(String.valueOf(provision));
                        frmPlazoFijo.txtTasaTotal1.setText(String.valueOf(tasatotal));
                        frmPlazoFijo.txtTotal1.setText(String.valueOf(total));
                        posicionletra = frmPlazoFijo.txtFolio.getText().indexOf('-');
                        if (posicionletra != -1) {
                            String letrafolio = frmPlazoFijo.txtFolio.getText().substring(posicionletra + 1, posicionletra + 2);
                            letrafolio = letrafolio.toUpperCase();
                            int posicionarreglo = Arrays.asList(prefijo).indexOf(letrafolio);
                            String folioreinversion = frmPlazoFijo.txtFolio.getText().substring(0, posicionletra) + "-" + prefijo[posicionarreglo + 1];
                            frmPlazoFijo.txtFolio1.setText(folioreinversion);

                        } else {

                            String folioreinversion = frmPlazoFijo.txtFolio.getText() + "-" + "A";
                            frmPlazoFijo.txtFolio1.setText(folioreinversion);

                        }
                        /*if (modC.buscafoliomayor(mod)) {
                            frmPlazoFijo.txtFolio1.setText(String.valueOf(mod.getFolio()));

                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontro folio mayor");

                        }*/

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro la tasa del mes");

                }

            }
        }
    }

    public void limpiar() {

        //frmPlazoFijo.txtfechacreacion.setText(null);
        frmPlazoFijo.txtrfc.setText(null);
        frmPlazoFijo.txtnombre.setText(null);
        frmPlazoFijo.txtQuincena.setText(null);
        frmPlazoFijo.jDFechaInicio.setCalendar(null);
        frmPlazoFijo.txtFechaVencimiento.setText(null);
        frmPlazoFijo.txtTasaInteres.setText(null);
        frmPlazoFijo.txtTasaTotal.setText(null);
        frmPlazoFijo.txtCapital.setText(null);
        frmPlazoFijo.txtPlazo.setText(null);
        frmPlazoFijo.txtFolio.setText(null);
        frmPlazoFijo.txtInteres.setText(null);
        frmPlazoFijo.txtTotal.setText(null);
        frmPlazoFijo.txtProvision.setText(null);
        frmPlazoFijo.txtReferencia.setEditable(false);
        frmPlazoFijo.txtFolio.setEditable(true);
        frmPlazoFijo.txtCapital.setEditable(true);
        frmPlazoFijo.txtPlazo.setEditable(true);
        //frmPlazoFijo.jDFechaInicio.setEnable(true);
        frmPlazoFijo.jDFechaInicio.setEnabled(true);
        frmPlazoFijo.btnImprimirConstancias.setVisible(false);
        frmPlazoFijo.btnEnviarPago.setVisible(false);
        frmPlazoFijo.btnPagar.setVisible(true);
                    
        frmPlazoFijo.txtQnaPago.setEditable(false);
        frmPlazoFijo.txtIntegresGanado.setText(null);
        frmPlazoFijo.txtSancion.setText(null);
        frmPlazoFijo.txtDias.setText(null);
        frmPlazoFijo.txtTotalAPagar.setText(null);
        frmPlazoFijo.jLabel18.setVisible(false);
                frmPlazoFijo.jLabel19.setVisible(false);
                frmPlazoFijo.jLabel20.setVisible(false);
                frmPlazoFijo.jLabel21.setVisible(false);
                frmPlazoFijo.jLabel22.setVisible(false);
                frmPlazoFijo.jLabel23.setVisible(false);
                frmPlazoFijo.jLabel24.setVisible(false);
                frmPlazoFijo.jLabel25.setVisible(false);
                frmPlazoFijo.jLabel26.setVisible(false);
                frmPlazoFijo.jLabel27.setVisible(false);
                frmPlazoFijo.jDFechaInicio1.setVisible(false);
                frmPlazoFijo.txtFechaVencimiento1.setVisible(false);
                frmPlazoFijo.txtTasaInteres1.setVisible(false);
                frmPlazoFijo.txtTasaTotal1.setVisible(false);
                frmPlazoFijo.txtCapital1.setVisible(false);
                frmPlazoFijo.txtPlazo1.setVisible(false);
                frmPlazoFijo.txtFolio1.setVisible(false);
                frmPlazoFijo.txtInteres1.setVisible(false);
                frmPlazoFijo.txtTotal1.setVisible(false);
                frmPlazoFijo.txtProvision1.setVisible(false);
                frmPlazoFijo.btnCalcularReinversion.setVisible(false);
                frmPlazoFijo.btnCalcular.setVisible(true);
                
                
                frmPlazoFijo.jLabel4.setVisible(false);
                frmPlazoFijo.txtMontoInicial.setVisible(false);
                frmPlazoFijo.txtMontoRetiro.setVisible(false);
                frmPlazoFijo.txtMontoAbono.setVisible(false);
                frmPlazoFijo.RbMontoRetiro.setVisible(false);
                frmPlazoFijo.RbMontoAbono.setVisible(false);
                frmPlazoFijo.btnImprimirRecibo.setVisible(false);
                frmPlazoFijo.CheckReinvertir.setEnabled(false);
            
                
        

    }

    public static Date sumarDiasAFecha(Date fecha, int dias) {
        if (dias == 0) {
            return fecha;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

}
