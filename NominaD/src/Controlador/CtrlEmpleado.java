/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultaEmpleado;
import Modelo.ConsultaPension;
import Modelo.ConsultasExportar;
import Modelo.Empleado;
import Modelo.Issste;
import Modelo.Pension;
import Modelo.Prestamos;
import Modelo.Reporte;
import Modelo.SalarioMinimo;
import Modelo.TablaISR;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import Vista.MenuNotaANt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author victor
 */
public class CtrlEmpleado implements ActionListener {
    
    private Empleado mod;
    private ConsultaEmpleado modC;
    private MenuNotaANt frmPersona;
    private Issste cuotas;
    private SalarioMinimo minimo;
    private TablaISR isrtabla;
    private Pension pension;
    private ConsultaPension consultapension;
    private Prestamos prestamo;
    private Reporte reporte;
    private ConsultasExportar consultaReporte;

    
    public CtrlEmpleado(Empleado mod, ConsultaEmpleado modc, MenuNotaANt frmPersona,SalarioMinimo minimo,
            TablaISR isrtabla,Issste cuotas,Pension pension,ConsultaPension consultapension,Prestamos prestamo,
            Reporte reporte,ConsultasExportar consultaReporte) {

        this.consultaReporte = consultaReporte;
        this.prestamo = prestamo;
        this.pension = pension;
        this.consultapension = consultapension;
        this.frmPersona = frmPersona;
        this.mod = mod;
        this.modC = modc;
        this.minimo = minimo;
        this.isrtabla = isrtabla;
        this.cuotas = cuotas;
        this.reporte = reporte;
        this.frmPersona.BtnGuardar.addActionListener(this);
        this.frmPersona.BtnModificar.addActionListener(this);
        this.frmPersona.BtnBaja.addActionListener(this);
        this.frmPersona.BtnNuevo.addActionListener(this);
        this.frmPersona.BtnBuscar.addActionListener(this);
        this.frmPersona.BtnReactivar.addActionListener(this);
        
        this.frmPersona.BtnCalcular.addActionListener(this);
        this.frmPersona.cbPlantel.addActionListener(this);
        this.frmPersona.cbCategoria.addActionListener(this);
        this.frmPersona.cbTipo.addActionListener(this);
        this.frmPersona.BtnGuardarPension.addActionListener(this);
       this.frmPersona.BtnModificarPension.addActionListener(this);
       this.frmPersona.BtnBajaPension.addActionListener(this);
       this.frmPersona.BtnBuscarPension.addActionListener(this);
       
        this.frmPersona.BtnGuardarPrestamo.addActionListener(this);
        this.frmPersona.BtnCancelarPrestamo.addActionListener(this);
        this.frmPersona.BtnModificarPrestamo.addActionListener(this);
        this.frmPersona.BtnBuscarPrestamo.addActionListener(this);
        this.frmPersona.btnAbrirLayout.addActionListener(this);
        this.frmPersona.btnImportarLayout.addActionListener(this);
        this.frmPersona.btnCancelarLayout.addActionListener(this);
        this.frmPersona.BtnExportarReportes.addActionListener(this);
        this.frmPersona.cbcuotasindicato.addActionListener(this);
        this.frmPersona.cbAyudaMutua.addActionListener(this);

        this.frmPersona.BtnNuevaNomina.addActionListener(this);
        this.frmPersona.BtnGenerarIsste.addActionListener(this);
        this.frmPersona.BtnProcesoPrestamo.addActionListener(this);
        this.frmPersona.BtnProcesoAhorro.addActionListener(this);
        this.frmPersona.BtnProcesoAyuda.addActionListener(this);
        this.frmPersona.BtnProcesoCuota.addActionListener(this);
        this.frmPersona.BtnProcesoPension.addActionListener(this);
        this.frmPersona.BtnProcesoAntiguedad.addActionListener(this);
        this.frmPersona.BtnTimbrado.addActionListener(this);
        this.frmPersona.btnNotaTecnica.addActionListener(this);
        
        this.frmPersona.btnExportarNetos.addActionListener(this);
        this.frmPersona.btnEstadosDeOrigen.addActionListener(this);
        this.frmPersona.btnExportarHojaiCP.addActionListener(this);
        this.frmPersona.btnExportarRemoto.addActionListener(this);
        this.frmPersona.btnExportarFonsar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
        
         //<editor-fold defaultstate="collapsed" desc="variables"> 
        String cuotasindicalfuncion;
        Double cuotasindical = 0.0;
        Float montocuotasindical ;
        Double cuotasindicalporcentaje =  0.0;
        Double filatabla = 0.0;
        Double total = 0.0;
        Double totalmasactual = 0.0;
        int  numerodeplazas;
        int empleadoganamas;
        int empleadounaplaza;
        //Salario
        Double P_01 = 0.0;
        Double P_01A = 0.0;
        Double P_01B = 0.0;
        Double P_01C = 0.0;
        Double P_01D = 0.0;
        Double P_01E = 0.0;
        Double P_18ISR = 0.0;
        Double P_19ISR = 0.0;
        //Prima Antiguedad
        Double P_03 = 0.0;
        Double P_03R = 0.0;
        
        //Cuotas ISSSTE
        Double D_03 = 0.0;
        Double D_04 = 0.0;
        Double D_21 = 0.0;
        Double D_22 = 0.0;
        Double D_23 = 0.0;
        Double D_03R = 0.0;
        Double D_04R = 0.0;
        Double D_21R = 0.0;
        Double D_22R = 0.0;
        Double D_23R = 0.0;
      
        Double BaseIssste = 0.0;
        Double BaseIsr = 0.0;
        Double IsrCalculo = 0.0;
        Double SalarioBase = 0.0;
        Double SumaP18 = 0.0;
        Double SumaP19 = 0.0;
        Double SumaAntiguedad = 0.0;
        Double SalarioIsr = 0.0;
        Double UnSalarioIssste = 0.0;
        Double DiezSalariosIssste = 0.0;
        //Percepciones Docentes
        Double P_24 = 0.0;
        Double P_05 = 177.50;
        Double P_22 = 227.50;
        Double P_19 = 117.50;
        Double P_06 = 10.00;
        Double P_26DOCENTE = 157.50 ;
        //Percepciones Administrativas
        Double P_16 = 30.0;
        Double P_04OFICIAL = 224.70;
        Double P_04EMSAD = 171.50;
        Double P_04COOPERA = 62.5;
        Double P_26ADMINISTRATIVO = 112.50 ;
        String RfcEmpleado; 
        //</editor-fold>
        
        ///calcular issste isr antiguedad empleado
    //<editor-fold desc="botoncalculaempleado">
        if (e.getSource() == frmPersona.BtnCalcular) {
           
            
            Double salariom = 0.00 ;
            P_01 = Double.parseDouble(frmPersona.txtP_01.getText());
            P_01A = Double.parseDouble(frmPersona.txtP_01A.getText());
            P_01B = Double.parseDouble(frmPersona.txtP_01B.getText());
            P_01C = Double.parseDouble(frmPersona.txtP_01C.getText());
            P_01D = Double.parseDouble(frmPersona.txtP_01D.getText());
            P_01E = Double.parseDouble(frmPersona.txtP_01E.getText());
            P_03 = Double.parseDouble(frmPersona.txtP_3.getText());
            P_18ISR = Double.parseDouble(frmPersona.txtP_18.getText());
            P_19ISR = Double.parseDouble(frmPersona.txtP_19.getText());
            RfcEmpleado = frmPersona.txtrfc.getText();
           //mandar rfc de la persona
            if (modC.dobleplaza(mod)) {
                numerodeplazas = mod.getNumerodeplazas();
                frmPersona.txtNumeroDePlazas.setText(String.valueOf(numerodeplazas));
                if (numerodeplazas > 1) {
                    if (modC.sumadobleplaza(mod)) {
                        
                     SalarioBase = mod.getSalariosuma();
                     SumaP18 = mod.getSumap18();
                     SumaP19 = mod.getSumap19();
                     SumaAntiguedad = mod.getAntiguedadsuma();
                     SalarioIsr = (SalarioBase + SumaP18 + SumaP19)*2;
                     BaseIssste  = SalarioBase + SumaAntiguedad ;
                    if (modC.empleadoganamas(mod)) {
                    
                      empleadoganamas = mod.getNumerodeempleadoganamas();
                      frmPersona.txtempleadoganamas.setText(String.valueOf(empleadoganamas));
                      mod.setNumerodeempleadoganamas(empleadoganamas);
           
                      
              
                    }else{
                    
                    } 
                     
                    }
                }else{
                SalarioBase = P_01 + P_01A + P_01B + P_01C + P_01D + P_01E;
                SalarioIsr = (SalarioBase + P_18ISR + P_19ISR)*2;
                BaseIssste  = P_01 + P_01A + P_01B + P_01C + P_01D + P_01E + P_03;
                empleadounaplaza = Integer.parseInt(frmPersona.txtempleado.getText());
                mod.setNumerodeempleadoganamas(empleadounaplaza);
                
               }
            }
            
            
            //mod.setEmpleado(Integer.parseInt(frmPersona.txtempleado.getText()));
            //mod.setNumeroquincena(Integer.parseInt(frmPersona.txtnumeroquincena.getText()));
             mod.setEmpleado(Integer.parseInt(frmPersona.txtempleado.getText()));
             mod.setNumeroquincena(Integer.parseInt(frmPersona.txtnumeroquincena.getText()));
            if (modC.calcularneto(mod)) {
            frmPersona.txtsueldobase.setText(String.valueOf(mod.getSalarioBase()));
            frmPersona.txtsueldoneto.setText(String.valueOf(mod.getNetoEmpleado()));
            frmPersona.txtpercepciones.setText(String.valueOf(mod.getPercepcionesEmpleado()));
            frmPersona.txtdeducciones.setText(String.valueOf(mod.getDeduccionesEmpleado()));
            }
            
            if (modC.calcularantiguedad(mod)) {
                
                //frmPersona.txtP_3.setText(String.valueOf(mod.getP_03()));
                frmPersona.txtP_011.setText(String.valueOf(mod.getP_03()));
             if (modC.calcularcuotasindical(mod)){
                 
                 cuotasindicalfuncion = mod.getCveCuotaSindical();
                 montocuotasindical = mod.getMontocuotasindical();
                if(cuotasindicalfuncion.equals("SUTCOBACH"))
            {         
            frmPersona.txtD_11.setText(String.valueOf(montocuotasindical));
            frmPersona.txtD_19.setText("0");
            frmPersona.txtD_05.setText("0");
            frmPersona.txtD_29.setText("0");
            
            }else if (cuotasindicalfuncion.equals("SITCOBACH"))
            { 
            frmPersona.txtD_19.setText(String.valueOf(montocuotasindical));
            frmPersona.txtD_11.setText("0");
            frmPersona.txtD_05.setText("0");
            frmPersona.txtD_29.setText("0");
            }else if (cuotasindicalfuncion.equals("SECCION XXXI"))
            { 
            frmPersona.txtD_05.setText(String.valueOf(montocuotasindical));
            frmPersona.txtD_11.setText("0");
            frmPersona.txtD_19.setText("0");
            frmPersona.txtD_29.setText("0");
            }else if (cuotasindicalfuncion.equals("SECCION LXIII"))
            { 
            frmPersona.txtD_29.setText(String.valueOf(montocuotasindical));
            frmPersona.txtD_11.setText("0");
            frmPersona.txtD_19.setText("0");
            frmPersona.txtD_05.setText("0");
            } 
            
            
             }
             
             
             
            }
           int  periodosalario = 2017;
            minimo.setYear(periodosalario);
            if (modC.salariominimo(minimo)) {
                salariom = Double.parseDouble(minimo.getZonaa());
                UnSalarioIssste = salariom * 15;
                DiezSalariosIssste = UnSalarioIssste * 10;
            }
            
            if (BaseIssste > DiezSalariosIssste) {
                BaseIssste = DiezSalariosIssste;

            }
            if (BaseIssste < UnSalarioIssste) {
                BaseIssste = UnSalarioIssste;
            }
            
            
            if (modC.cuotasissste(cuotas)) {
                D_03 = Double.parseDouble(cuotas.getD_03());
                D_04 = Double.parseDouble(cuotas.getD_04());
                D_21 = Double.parseDouble(cuotas.getD_21());
                D_22 = Double.parseDouble(cuotas.getD_22());
                D_23 = Double.parseDouble(cuotas.getD_23());
                D_03R = BaseIssste * D_03;
                D_04R = BaseIssste * D_04;
                D_21R = BaseIssste * D_21;
                D_22R = BaseIssste * D_22;
                D_23R = BaseIssste * D_23;
                D_03R = Double.parseDouble(String.format("%.2f", D_03R));
                D_04R = Double.parseDouble(String.format("%.2f", D_04R));
                D_21R = Double.parseDouble(String.format("%.2f", D_21R));
                D_22R = Double.parseDouble(String.format("%.2f", D_22R));
                D_23R = Double.parseDouble(String.format("%.2f", D_23R));
                mod.setD_03masdeunaplaza(D_03R);
                mod.setD_04masdeunaplaza(D_04R);
                mod.setD_21masdeunaplaza(D_21R);
                mod.setD_22masdeunaplaza(D_22R);
                mod.setD_23masdeunaplaza(D_23R);
                 frmPersona.txtD_03.setText(String.valueOf(D_03R));
                 frmPersona.txtD_04.setText(String.valueOf(D_04R));
                 frmPersona.txtD_21.setText(String.valueOf(D_21R));
                 frmPersona.txtD_22.setText(String.valueOf(D_22R));
                 frmPersona.txtD_23.setText(String.valueOf(D_23R));
            }
             isrtabla.setCalculoIsr(SalarioIsr);    
            if (modC.calculoisr(isrtabla)) {
                
                
                   isrtabla.setCalculoIsr(SalarioIsr);    
                   isrtabla.setIntermedio(isrtabla.getIntermedio());
                if (modC.calculosubsidio(isrtabla)) {
                    IsrCalculo = isrtabla.getRCalculoIsr();
                    if(IsrCalculo < 0){
                    frmPersona.txtD_01.setText("0");
                    IsrCalculo = IsrCalculo * (-1);
                    frmPersona.txtP_14.setText(String.valueOf(IsrCalculo));
                    mod.setD_01masdeunaplaza(0.00);
                    mod.setP_14masdeunaplaza(IsrCalculo);
                    }else{
                    frmPersona.txtD_01.setText(String.valueOf(IsrCalculo));
                    frmPersona.txtP_14.setText("0");
                    mod.setD_01masdeunaplaza(IsrCalculo);
                    mod.setP_14masdeunaplaza(0.00);
                    }
           
                }
                
             }
            if (modC.actualizarempleadoganamas(mod)) {
               
                
                
                if (modC.actualizarotrosempleados(mod)) {
                } else {

                }
                
                
            } else {
            }
           
            
        }
        // </editor-fold>
        
    //<editor-fold desc="calcularcuotasindicalempleado">
        if (e.getSource() == frmPersona.cbcuotasindicato) {
        
             P_01 = Double.parseDouble(frmPersona.txtP_01.getText());
            P_01A = Double.parseDouble(frmPersona.txtP_01A.getText());
            P_01B = Double.parseDouble(frmPersona.txtP_01B.getText());
            P_01C = Double.parseDouble(frmPersona.txtP_01C.getText());
            P_01D = Double.parseDouble(frmPersona.txtP_01D.getText());
            P_01E = Double.parseDouble(frmPersona.txtP_01E.getText());
            cuotasindical = P_01 + P_01A + P_01B + P_01C + P_01D + P_01E;
            cuotasindicalporcentaje = cuotasindical/100;
            cuotasindicalporcentaje = Double.parseDouble(String.format("%.2f", cuotasindicalporcentaje));
            
            if(frmPersona.cbcuotasindicato.getSelectedItem() == "SUTCOBACH")
            {
            frmPersona.txtD_11.setText(String.valueOf(cuotasindicalporcentaje));
            frmPersona.txtD_19.setText("0");
            frmPersona.txtD_05.setText("0");
            frmPersona.txtD_29.setText("0");
            
            }else if (frmPersona.cbcuotasindicato.getSelectedItem() == "SITCOBACH")
            { 
            frmPersona.txtD_19.setText(String.valueOf(cuotasindicalporcentaje));
            frmPersona.txtD_11.setText("0");
            frmPersona.txtD_05.setText("0");
            frmPersona.txtD_29.setText("0");
            }else if (frmPersona.cbcuotasindicato.getSelectedItem() == "SECCION XXXI")
            { 
            frmPersona.txtD_05.setText(String.valueOf(cuotasindicalporcentaje));
            frmPersona.txtD_11.setText("0");
            frmPersona.txtD_19.setText("0");
            frmPersona.txtD_29.setText("0");
            }else if (frmPersona.cbcuotasindicato.getSelectedItem() == "SECCION LXIII")
            { 
            frmPersona.txtD_29.setText(String.valueOf(cuotasindicalporcentaje));
            frmPersona.txtD_11.setText("0");
            frmPersona.txtD_19.setText("0");
            frmPersona.txtD_05.setText("0");
            }
        }
        //</editor-fold>
        
        
    //<editor-fold defaultstate="collapsed" desc="elegirtipoplaza"> 
        if (e.getSource() == frmPersona.cbTipo) {
       if(frmPersona.cbTipo.getSelectedItem() == "DOCENTE")
            {
             frmPersona.txtP_5.setText(String.valueOf(P_05));
             frmPersona.txtP_22.setText(String.valueOf(P_22));
             frmPersona.txtP_19.setText(String.valueOf(P_19));
             frmPersona.txtP_6.setText(String.valueOf(P_06));
             frmPersona.txtP_26.setText(String.valueOf(P_26DOCENTE));
             frmPersona.txtP_16.setText("0");
             frmPersona.txtP_4.setText("0");
            }if(frmPersona.cbTipo.getSelectedItem() == "ADMINISTRATIVO")
            {
             frmPersona.txtP_5.setText("0");
             frmPersona.txtP_22.setText(String.valueOf("0"));
             frmPersona.txtP_19.setText(String.valueOf("0"));
             frmPersona.txtP_6.setText(String.valueOf("0"));
             frmPersona.txtP_26.setText(String.valueOf(P_26ADMINISTRATIVO));
             frmPersona.txtP_16.setText(String.valueOf(P_16));
             frmPersona.txtP_4.setText(String.valueOf(P_04OFICIAL));
            }
    
                
        }
        
         //</editor-fold > 
         ///
         
    //<editor-fold defaultstate="collapsed" desc="elegircategoria">  
        if (e.getSource() == frmPersona.cbCategoria) {
        mod.setCategoria(String.valueOf(frmPersona.cbCategoria.getSelectedItem()));
        if(modC.completarcamposcategoria(mod)){
         frmPersona.txtcvecategoria.setText(mod.getCveCategoria());
         frmPersona.cbNivel.setSelectedItem(mod.getNivel());
         frmPersona.txtSumaHoras.setText(mod.getHoras());
        
        }
        }
         //</editor-fold> 
        
         
    //<editor-fold defaultstate="collapsed" desc="elegirplantel"> 
        if (e.getSource() == frmPersona.cbPlantel) {
             //JOptionPane.showMessageDialog(null, "cambiaste algo");
            mod.setPlantel(String.valueOf(frmPersona.cbPlantel.getSelectedItem()));
            if(modC.completarcampos(mod)){
                    //limpiar();
                    frmPersona.txtnumeroplantel.setText(mod.getNumeroPlantel());
                    frmPersona.cbTipoPlantel.setSelectedItem(mod.getTPlantel());
                    frmPersona.txtnombreplantel.setText(mod.getNombrePlantel());
                    frmPersona.cbZona.setSelectedItem(mod.getZona());
                    
            
            
            }else{
            
            //JOptionPane.showMessageDialog(null, "No se encontro el plantel");
                  //  limpiar();
            }      
        }
         //</editor-fold > 
         ///
    //<editor-fold desc="bajaempleado">
     if (e.getSource() == frmPersona.BtnBaja) {
         
         if (frmPersona.txtempleado.getText().isEmpty()
                    || frmPersona.txtempleado.getText().isEmpty()
                    || frmPersona.txtnumeroquincena.getText().isEmpty()
                    )
            {   

                JOptionPane.showMessageDialog(null, "Faltan Campo Empleado o Numero Quincena");

            } else {
             
             Object [] baja = {"Fallecimiento","Retiro Voluntario","Enfermedad","Jubilacion","Baja Temporal","Permiso"
                     + "Sin Goce De Sueldo"};
            
            Object opcion = JOptionPane.showInputDialog(null,"Selecciona el motivo de baja","Elegir"
             ,JOptionPane.QUESTION_MESSAGE,null,baja,baja[0]);
            
             mod.setMotivoDeBaja(String.valueOf(opcion));
             mod.setNumeroquincena(Integer.parseInt(frmPersona.txtnumeroquincena.getText()));
              
               if (modC.bajaempleado(mod)) {
                JOptionPane.showMessageDialog(null, "El trabajador fue dado de Baja");
                //limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se puedo realizar la baja Intente Mas Tarde");
                //limpiar();
            }
            
         }
     
     
     }   
           //</editor-fold>
    
    //<editor-fold desc="guardarempleado">
    if (e.getSource() == frmPersona.BtnGuardar) {

            if (frmPersona.txtempleado.getText().isEmpty()
                    || frmPersona.txtap_pat.getText().isEmpty()
                    || frmPersona.txtap_mat.getText().isEmpty()
                    || frmPersona.txtnombre.getText().isEmpty()
                    || frmPersona.txtfechaingreso.getText().isEmpty()
                    || frmPersona.txtnombre.getText().isEmpty()
                    || frmPersona.txtrfc.getText().isEmpty()
                    ){
                    
                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
                
                          //  JOptionPane.showMessageDialog(null, "Registro Guardado");

        
               mod.setRfc(frmPersona.txtrfc.getText());
               mod.setEmpleado(Integer.parseInt(frmPersona.txtempleado.getText()));
               mod.setPeriodo(Integer.parseInt(frmPersona.txtnumeroquincena.getText()));
               mod.setCvemvto(String.valueOf(frmPersona.cbCveMovimiento.getSelectedItem()));
               mod.setP_01(Float.parseFloat(frmPersona.txtP_01.getText()));
               mod.setP_02(Float.parseFloat(frmPersona.txtP_2.getText()));
                mod.setP_03(Float.parseFloat(frmPersona.txtP_3.getText()));
                mod.setP_04(Float.parseFloat(frmPersona.txtP_4.getText()));
                mod.setP_05(Float.parseFloat(frmPersona.txtP_5.getText()));
                mod.setP_06(Float.parseFloat(frmPersona.txtP_6.getText()));
                mod.setP_07(Float.parseFloat(frmPersona.txtP_7.getText()));
                mod.setP_08(Float.parseFloat(frmPersona.txtP_8.getText()));
                mod.setP_09(Float.parseFloat(frmPersona.txtP_9.getText()));
                mod.setP_10(Float.parseFloat(frmPersona.txtP_10.getText()));
                mod.setP_11(Float.parseFloat(frmPersona.txtP_11.getText()));
                mod.setP_12(Float.parseFloat(frmPersona.txtP_12.getText()));
                mod.setP_13(Float.parseFloat(frmPersona.txtP_13.getText()));
                mod.setP_14(Float.parseFloat(frmPersona.txtP_14.getText()));
                mod.setP_15(Float.parseFloat(frmPersona.txtP_15.getText()));
                mod.setP_16(Float.parseFloat(frmPersona.txtP_16.getText()));
                mod.setP_17(Float.parseFloat(frmPersona.txtP_17.getText()));
                mod.setP_18(Float.parseFloat(frmPersona.txtP_18.getText()));
                mod.setP_19(Float.parseFloat(frmPersona.txtP_19.getText()));
                mod.setP_20(Float.parseFloat(frmPersona.txtP_20.getText()));
                mod.setP_21(Float.parseFloat(frmPersona.txtP_21.getText()));
                mod.setP_22(Float.parseFloat(frmPersona.txtP_22.getText()));
                mod.setP_23(Float.parseFloat(frmPersona.txtP_23.getText()));
                mod.setP_24(Float.parseFloat(frmPersona.txtP_24.getText()));
                mod.setP_25(Float.parseFloat(frmPersona.txtP_25.getText()));
                mod.setP_26(Float.parseFloat(frmPersona.txtP_26.getText()));
                mod.setP_27(Float.parseFloat(frmPersona.txtP_27.getText()));
                mod.setP_28(Float.parseFloat(frmPersona.txtP_28.getText()));
                mod.setP_29(Float.parseFloat(frmPersona.txtP_29.getText()));
                mod.setP_30(Float.parseFloat(frmPersona.txtP_30.getText()));
                mod.setD_01(Float.parseFloat(frmPersona.txtD_01.getText()));
                mod.setD_10(Float.parseFloat(frmPersona.txtD_10.getText()));
                mod.setP_DI(Float.parseFloat(frmPersona.txtP_D1.getText()));
                mod.setP_C1(Float.parseFloat(frmPersona.txtP_C1.getText()));
                mod.setP_C2(Float.parseFloat(frmPersona.txtP_C2.getText()));
                mod.setP_31(Float.parseFloat(frmPersona.txtP_31.getText()));
                mod.setP_32(Float.parseFloat(frmPersona.txtP_32.getText()));
                mod.setP_33(Float.parseFloat(frmPersona.txtP_33.getText()));
                mod.setD_CB(Float.parseFloat(frmPersona.txtD_CB.getText()));
                mod.setD_02(Float.parseFloat(frmPersona.txtD_02.getText()));
                mod.setD_03(Float.parseFloat(frmPersona.txtD_03.getText()));
                mod.setD_04(Float.parseFloat(frmPersona.txtD_04.getText()));
                mod.setD_05(Float.parseFloat(frmPersona.txtD_05.getText()));
                mod.setD_06(Float.parseFloat(frmPersona.txtD_06.getText()));
                mod.setD_07(Float.parseFloat(frmPersona.txtD_07.getText()));
                mod.setD_08(Float.parseFloat(frmPersona.txtD_08.getText()));
                mod.setD_09(Float.parseFloat(frmPersona.txtD_09.getText()));
               mod.setD_11(Float.parseFloat(frmPersona.txtD_11.getText()));
                mod.setD_12(Float.parseFloat(frmPersona.txtD_12.getText()));
                mod.setD_13(Float.parseFloat(frmPersona.txtD_13.getText()));
                mod.setD_14(Float.parseFloat(frmPersona.txtD_14.getText()));
                mod.setD_15(Float.parseFloat(frmPersona.txtD_15.getText()));
                mod.setD_16(Float.parseFloat(frmPersona.txtD_16.getText()));
                mod.setD_17(Float.parseFloat(frmPersona.txtD_17.getText()));
                mod.setD_18(Float.parseFloat(frmPersona.txtD_18.getText()));
                mod.setD_19(Float.parseFloat(frmPersona.txtD_19.getText()));
                mod.setD_20(Float.parseFloat(frmPersona.txtD_20.getText()));
                mod.setD_21(Float.parseFloat(frmPersona.txtD_21.getText()));
                mod.setD_22(Float.parseFloat(frmPersona.txtD_22.getText()));
                mod.setD_23(Float.parseFloat(frmPersona.txtD_23.getText()));
                mod.setD_24(Float.parseFloat(frmPersona.txtD_24.getText()));
                mod.setD_25(Float.parseFloat(frmPersona.txtD_25.getText()));
                mod.setD_26(Float.parseFloat(frmPersona.txtD_26.getText()));
                mod.setD_27(Float.parseFloat(frmPersona.txtD_27.getText()));
                mod.setD_28(Float.parseFloat(frmPersona.txtD_28.getText()));
                mod.setP_MC(Float.parseFloat(frmPersona.txtP_MC.getText()));
                mod.setP_PG(Float.parseFloat(frmPersona.txtP_PG.getText()));
                mod.setP_PE(Float.parseFloat(frmPersona.txtP_PE.getText()));
                mod.setP_36(Float.parseFloat(frmPersona.txtP_36.getText()));
                mod.setD_29(Float.parseFloat(frmPersona.txtD_29.getText()));
                mod.setP_37(Float.parseFloat(frmPersona.txtP_37.getText()));
                mod.setD_30(Float.parseFloat(frmPersona.txtD_30.getText()));
                mod.setD_31(Float.parseFloat(frmPersona.txtD_31.getText()));
                mod.setP_35(Float.parseFloat(frmPersona.txtP_35.getText()));
                mod.setP_38(Float.parseFloat(frmPersona.txtP_38.getText()));
                mod.setP_39(Float.parseFloat(frmPersona.txtP_39.getText()));
                mod.setD_32(Float.parseFloat(frmPersona.txtD_32.getText()));
                mod.setD_33(Float.parseFloat(frmPersona.txtD_33.getText()));
                mod.setD_34(Float.parseFloat(frmPersona.txtD_34.getText()));
                mod.setD_35(Float.parseFloat(frmPersona.txtD_35.getText()));
                mod.setP_40(Float.parseFloat(frmPersona.txtP_40.getText()));
                mod.setD_36(Float.parseFloat(frmPersona.txtD_36.getText()));
                mod.setD_37(Float.parseFloat(frmPersona.txtD_37.getText()));
                mod.setP_01A(Float.parseFloat(frmPersona.txtP_01A.getText()));
                mod.setP_01B(Float.parseFloat(frmPersona.txtP_01B.getText()));
                mod.setP_01C(Float.parseFloat(frmPersona.txtP_01C.getText()));
                mod.setP_01D(Float.parseFloat(frmPersona.txtP_01D.getText()));
                mod.setP_01E(Float.parseFloat(frmPersona.txtP_01E.getText()));
                mod.setP_34(Float.parseFloat(frmPersona.txtP_34.getText()));
                mod.setD_38(Float.parseFloat(frmPersona.txtD_38.getText()));
                mod.setP_41(Float.parseFloat(frmPersona.txtP_41.getText()));
                mod.setP_42(Float.parseFloat(frmPersona.txtP_42.getText()));
                mod.setP_43(Float.parseFloat(frmPersona.txtP_43.getText()));
                mod.setP_44(Float.parseFloat(frmPersona.txtP_44.getText()));
                mod.setP_45(Float.parseFloat(frmPersona.txtP_45.getText()));
                mod.setP_46(Float.parseFloat(frmPersona.txtP_46.getText()));
                mod.setP_47(Float.parseFloat(frmPersona.txtP_47.getText()));
                mod.setD_39(Float.parseFloat(frmPersona.txtD_39.getText()));
                mod.setD_40(Float.parseFloat(frmPersona.txtD_40.getText()));
                mod.setD_41(Float.parseFloat(frmPersona.txtD_41.getText()));
                mod.setD_42(Float.parseFloat(frmPersona.txtD_42.getText()));
                mod.setD_01A(Float.parseFloat(frmPersona.txtD_01A.getText()));
                
                mod.setD_43(Float.parseFloat(frmPersona.txtD_43.getText()));
                mod.setD_44(Float.parseFloat(frmPersona.txtD_44.getText()));
                mod.setD_45(Float.parseFloat(frmPersona.txtD_45.getText()));
                mod.setD_46(Float.parseFloat(frmPersona.txtD_46.getText()));
                mod.setD_47(Float.parseFloat(frmPersona.txtD_47.getText()));
                mod.setD_48(Float.parseFloat(frmPersona.txtD_48.getText()));
                mod.setD_49(Float.parseFloat(frmPersona.txtD_49.getText()));
                mod.setD_50(Float.parseFloat(frmPersona.txtD_50.getText()));
                mod.setTipo(String.valueOf(frmPersona.cbTipo.getSelectedItem()));
                mod.setQuincena(frmPersona.txtnumeroquincena.getText());
                mod.setLapso(frmPersona.txtnumeroquincena.getText());
                mod.setPlantel(String.valueOf(frmPersona.cbPlantel.getSelectedItem()));
                mod.setNoPuesto(String.valueOf(frmPersona.cbNumeroPuesto.getSelectedItem()));
                mod.setLeido("OK");
                 mod.setAP_PAT(frmPersona.txtap_pat.getText());
                mod.setAP_MAT(frmPersona.txtap_mat.getText());
                mod.setNombre(frmPersona.txtnombre.getText());
                mod.setCategoria(String.valueOf(frmPersona.cbCategoria.getSelectedItem()));
                mod.setHoras(frmPersona.txtSumaHoras.getText());
                mod.setNivel(String.valueOf(frmPersona.cbNivel.getSelectedItem()));
                mod.setFechaOld(frmPersona.txtfechaingreso.getText());
                mod.setTipoPer(String.valueOf(frmPersona.cbTipoPer.getSelectedItem()));
                mod.setZona(String.valueOf(frmPersona.cbZona.getSelectedItem()));
                mod.setSemestre(String.valueOf(frmPersona.cbSemestre.getSelectedItem()));
                mod.setTPlantel(String.valueOf(frmPersona.cbTipoPlantel.getSelectedItem()));
                mod.setTipoSin(String.valueOf(frmPersona.cbSindicato.getSelectedItem()));
                mod.setMutual(String.valueOf(frmPersona.cbMutual.getSelectedItem()));
                mod.setFacore(String.valueOf(frmPersona.txtporcentajed25.getText()));
                mod.setCedulaImss(frmPersona.txtImss.getText());
                mod.setRegimenPensionario(String.valueOf(frmPersona.cbRegimenPension.getSelectedItem()));
                mod.setNumeroquincena(Integer.parseInt(frmPersona.txtnumeroquincena.getText()));
                mod.setNumeroInasistencias(Integer.parseInt(frmPersona.txtinasistencias.getText()));
                mod.setCurp(frmPersona.txtcurp.getText());
                mod.setTotalDias(15 - Integer.parseInt(frmPersona.txtinasistencias.getText()));
                mod.setHorasBase(Integer.parseInt(frmPersona.txthorabase.getText()));
                mod.setHorasLimitadas(Integer.parseInt(frmPersona.txthoralimitada.getText()));
                mod.setHorasInterinas(Integer.parseInt(frmPersona.txthorainterina.getText()));
                mod.setCveCategoria(frmPersona.txtcvecategoria.getText());
                mod.setStatusissste("A");
                mod.setUsuario("Victor");
                mod.setFechaModificacionissste("No Utilizada");
                mod.setSaldoissste("No Utilizada");
                mod.setPorcentajeHipotecarioIssste(Float.parseFloat(frmPersona.txtporcentajehip.getText()));
                mod.setPorcentajeseccion31(Float.parseFloat(frmPersona.txtporcentajed32.getText()));
                mod.setPorcentajeissste(Float.parseFloat(frmPersona.txtporcentajed24.getText()));
                mod.setFechaInasistencias(frmPersona.txtfechainasistencias.getText());
                mod.setNumeroDePago("0");
                mod.setTotalDePago("0");
                mod.setFolioissste("0");
                mod.setNumeroDeContrato("0");
                mod.setTipoContrato("0");
                mod.setNombrePlantel(frmPersona.txtnombreplantel.getText());
                mod.setNumeroPlantel(frmPersona.txtnumeroplantel.getText());
                mod.setP_48(Float.parseFloat(frmPersona.txtP_48.getText()));
                mod.setP_49(Float.parseFloat(frmPersona.txtP_49.getText()));
                mod.setP_50(Float.parseFloat(frmPersona.txtP_50.getText()));
                mod.setObservacion("0");
                mod.setFechaDeBajaIssste("0");
                mod.setCveAyudaMutua(String.valueOf(frmPersona.cbAyudaMutua.getSelectedItem()));
                mod.setCveCuotaSindical(String.valueOf(frmPersona.cbcuotasindicato.getSelectedItem()));
                mod.setCajaSutcobach(frmPersona.txtcajasutcobach.getText());
                mod.setCajaSeccion(frmPersona.txtcajaseccion.getText());
                mod.setCajaSitcobach(frmPersona.txtcajasitcobach.getText());
                mod.setCajaSeccion63(frmPersona.txtcajaseccion63.getText());
               
            
                
                
                mod.setP_51(Float.parseFloat(frmPersona.txtP_51.getText()));
                mod.setP_52(Float.parseFloat(frmPersona.txtP_52.getText()));
                mod.setP_53(Float.parseFloat(frmPersona.txtP_53.getText()));
                mod.setP_54(Float.parseFloat(frmPersona.txtP_54.getText()));
                mod.setP_55(Float.parseFloat(frmPersona.txtP_55.getText()));
                mod.setP_56(Float.parseFloat(frmPersona.txtP_56.getText()));
                mod.setP_57(Float.parseFloat(frmPersona.txtP_57.getText()));
                mod.setP_58(Float.parseFloat(frmPersona.txtP_58.getText()));
                mod.setP_59(Float.parseFloat(frmPersona.txtP_59.getText()));
                mod.setP_60(Float.parseFloat(frmPersona.txtP_60.getText()));
                mod.setD_51(Float.parseFloat(frmPersona.txtD_51.getText()));
                mod.setD_52(Float.parseFloat(frmPersona.txtD_52.getText()));
                mod.setD_53(Float.parseFloat(frmPersona.txtD_53.getText()));
                mod.setD_54(Float.parseFloat(frmPersona.txtD_54.getText()));
                mod.setD_55(Float.parseFloat(frmPersona.txtD_55.getText()));
                mod.setD_56(Float.parseFloat(frmPersona.txtD_56.getText()));
                mod.setD_57(Float.parseFloat(frmPersona.txtD_57.getText()));
                mod.setD_58(Float.parseFloat(frmPersona.txtD_58.getText()));
                mod.setD_59(Float.parseFloat(frmPersona.txtD_59.getText()));
                mod.setD_60(Float.parseFloat(frmPersona.txtD_60.getText()));
                
               
                
                
                
               if (modC.registrar(mod)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
            
                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Guardado Intente Mas Tarde");
            
                }

            }

        }
           //</editor-fold>
    
    //<editor-fold desc="modificarempleado">
       if (e.getSource() == frmPersona.BtnModificar) {
           
           if (frmPersona.txtempleado.getText().isEmpty()
                    || frmPersona.txtap_pat.getText().isEmpty()
                    || frmPersona.txtap_mat.getText().isEmpty()
                    || frmPersona.txtnombre.getText().isEmpty()
                    || frmPersona.txtfechaingreso.getText().isEmpty()
                    || frmPersona.txtnombre.getText().isEmpty()
                    || frmPersona.txtrfc.getText().isEmpty()
                    ){
                    
                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
               
               
               mod.setRfc(frmPersona.txtrfc.getText());
               mod.setEmpleado(Integer.parseInt(frmPersona.txtempleado.getText()));
               mod.setPeriodo(Integer.parseInt(frmPersona.txtnumeroquincena.getText()));
               mod.setCvemvto(String.valueOf(frmPersona.cbCveMovimiento.getSelectedItem()));
               mod.setP_01(Float.parseFloat(frmPersona.txtP_01.getText()));
               mod.setP_02(Float.parseFloat(frmPersona.txtP_2.getText()));
                mod.setP_03(Float.parseFloat(frmPersona.txtP_3.getText()));
                mod.setP_04(Float.parseFloat(frmPersona.txtP_4.getText()));
                mod.setP_05(Float.parseFloat(frmPersona.txtP_5.getText()));
                mod.setP_06(Float.parseFloat(frmPersona.txtP_6.getText()));
                mod.setP_07(Float.parseFloat(frmPersona.txtP_7.getText()));
                mod.setP_08(Float.parseFloat(frmPersona.txtP_8.getText()));
                mod.setP_09(Float.parseFloat(frmPersona.txtP_9.getText()));
                mod.setP_10(Float.parseFloat(frmPersona.txtP_10.getText()));
                mod.setP_11(Float.parseFloat(frmPersona.txtP_11.getText()));
                mod.setP_12(Float.parseFloat(frmPersona.txtP_12.getText()));
                mod.setP_13(Float.parseFloat(frmPersona.txtP_13.getText()));
                mod.setP_14(Float.parseFloat(frmPersona.txtP_14.getText()));
                mod.setP_15(Float.parseFloat(frmPersona.txtP_15.getText()));
                mod.setP_16(Float.parseFloat(frmPersona.txtP_16.getText()));
                mod.setP_17(Float.parseFloat(frmPersona.txtP_17.getText()));
                mod.setP_18(Float.parseFloat(frmPersona.txtP_18.getText()));
                mod.setP_19(Float.parseFloat(frmPersona.txtP_19.getText()));
                mod.setP_20(Float.parseFloat(frmPersona.txtP_20.getText()));
                mod.setP_21(Float.parseFloat(frmPersona.txtP_21.getText()));
                mod.setP_22(Float.parseFloat(frmPersona.txtP_22.getText()));
                mod.setP_23(Float.parseFloat(frmPersona.txtP_23.getText()));
                mod.setP_24(Float.parseFloat(frmPersona.txtP_24.getText()));
                mod.setP_25(Float.parseFloat(frmPersona.txtP_25.getText()));
                mod.setP_26(Float.parseFloat(frmPersona.txtP_26.getText()));
                mod.setP_27(Float.parseFloat(frmPersona.txtP_27.getText()));
                mod.setP_28(Float.parseFloat(frmPersona.txtP_28.getText()));
                mod.setP_29(Float.parseFloat(frmPersona.txtP_29.getText()));
                mod.setP_30(Float.parseFloat(frmPersona.txtP_30.getText()));
                mod.setD_01(Float.parseFloat(frmPersona.txtD_01.getText()));
                mod.setD_10(Float.parseFloat(frmPersona.txtD_10.getText()));
                mod.setP_DI(Float.parseFloat(frmPersona.txtP_D1.getText()));
                mod.setP_C1(Float.parseFloat(frmPersona.txtP_C1.getText()));
                mod.setP_C2(Float.parseFloat(frmPersona.txtP_C2.getText()));
                mod.setP_31(Float.parseFloat(frmPersona.txtP_31.getText()));
                mod.setP_32(Float.parseFloat(frmPersona.txtP_32.getText()));
                mod.setP_33(Float.parseFloat(frmPersona.txtP_33.getText()));
                mod.setD_CB(Float.parseFloat(frmPersona.txtD_CB.getText()));
                mod.setD_02(Float.parseFloat(frmPersona.txtD_02.getText()));
                mod.setD_03(Float.parseFloat(frmPersona.txtD_03.getText()));
                mod.setD_04(Float.parseFloat(frmPersona.txtD_04.getText()));
                mod.setD_05(Float.parseFloat(frmPersona.txtD_05.getText()));
                mod.setD_06(Float.parseFloat(frmPersona.txtD_06.getText()));
                mod.setD_07(Float.parseFloat(frmPersona.txtD_07.getText()));
                mod.setD_08(Float.parseFloat(frmPersona.txtD_08.getText()));
                mod.setD_09(Float.parseFloat(frmPersona.txtD_09.getText()));
               mod.setD_11(Float.parseFloat(frmPersona.txtD_11.getText()));
                mod.setD_12(Float.parseFloat(frmPersona.txtD_12.getText()));
                mod.setD_13(Float.parseFloat(frmPersona.txtD_13.getText()));
                mod.setD_14(Float.parseFloat(frmPersona.txtD_14.getText()));
                mod.setD_15(Float.parseFloat(frmPersona.txtD_15.getText()));
                mod.setD_16(Float.parseFloat(frmPersona.txtD_16.getText()));
                mod.setD_17(Float.parseFloat(frmPersona.txtD_17.getText()));
                mod.setD_18(Float.parseFloat(frmPersona.txtD_18.getText()));
                mod.setD_19(Float.parseFloat(frmPersona.txtD_19.getText()));
                mod.setD_20(Float.parseFloat(frmPersona.txtD_20.getText()));
                mod.setD_21(Float.parseFloat(frmPersona.txtD_21.getText()));
                mod.setD_22(Float.parseFloat(frmPersona.txtD_22.getText()));
                mod.setD_23(Float.parseFloat(frmPersona.txtD_23.getText()));
                mod.setD_24(Float.parseFloat(frmPersona.txtD_24.getText()));
                mod.setD_25(Float.parseFloat(frmPersona.txtD_25.getText()));
                mod.setD_26(Float.parseFloat(frmPersona.txtD_26.getText()));
                mod.setD_27(Float.parseFloat(frmPersona.txtD_27.getText()));
                mod.setD_28(Float.parseFloat(frmPersona.txtD_28.getText()));
                mod.setP_MC(Float.parseFloat(frmPersona.txtP_MC.getText()));
                mod.setP_PG(Float.parseFloat(frmPersona.txtP_PG.getText()));
                mod.setP_PE(Float.parseFloat(frmPersona.txtP_PE.getText()));
                mod.setP_36(Float.parseFloat(frmPersona.txtP_36.getText()));
                mod.setD_29(Float.parseFloat(frmPersona.txtD_29.getText()));
                mod.setP_37(Float.parseFloat(frmPersona.txtP_37.getText()));
                mod.setD_30(Float.parseFloat(frmPersona.txtD_30.getText()));
                mod.setD_31(Float.parseFloat(frmPersona.txtD_31.getText()));
                mod.setP_35(Float.parseFloat(frmPersona.txtP_35.getText()));
                mod.setP_38(Float.parseFloat(frmPersona.txtP_38.getText()));
                mod.setP_39(Float.parseFloat(frmPersona.txtP_39.getText()));
                mod.setD_32(Float.parseFloat(frmPersona.txtD_32.getText()));
                mod.setD_33(Float.parseFloat(frmPersona.txtD_33.getText()));
                mod.setD_34(Float.parseFloat(frmPersona.txtD_34.getText()));
                mod.setD_35(Float.parseFloat(frmPersona.txtD_35.getText()));
                mod.setP_40(Float.parseFloat(frmPersona.txtP_40.getText()));
                mod.setD_36(Float.parseFloat(frmPersona.txtD_36.getText()));
                mod.setD_37(Float.parseFloat(frmPersona.txtD_37.getText()));
                mod.setP_01A(Float.parseFloat(frmPersona.txtP_01A.getText()));
                mod.setP_01B(Float.parseFloat(frmPersona.txtP_01B.getText()));
                mod.setP_01C(Float.parseFloat(frmPersona.txtP_01C.getText()));
                mod.setP_01D(Float.parseFloat(frmPersona.txtP_01D.getText()));
                mod.setP_01E(Float.parseFloat(frmPersona.txtP_01E.getText()));
                mod.setP_34(Float.parseFloat(frmPersona.txtP_34.getText()));
                mod.setD_38(Float.parseFloat(frmPersona.txtD_38.getText()));
                mod.setP_41(Float.parseFloat(frmPersona.txtP_41.getText()));
                mod.setP_42(Float.parseFloat(frmPersona.txtP_42.getText()));
                mod.setP_43(Float.parseFloat(frmPersona.txtP_43.getText()));
                mod.setP_44(Float.parseFloat(frmPersona.txtP_44.getText()));
                mod.setP_45(Float.parseFloat(frmPersona.txtP_45.getText()));
                mod.setP_46(Float.parseFloat(frmPersona.txtP_46.getText()));
                mod.setP_47(Float.parseFloat(frmPersona.txtP_47.getText()));
                mod.setD_39(Float.parseFloat(frmPersona.txtD_39.getText()));
                mod.setD_40(Float.parseFloat(frmPersona.txtD_40.getText()));
                mod.setD_41(Float.parseFloat(frmPersona.txtD_41.getText()));
                mod.setD_42(Float.parseFloat(frmPersona.txtD_42.getText()));
                mod.setD_01A(Float.parseFloat(frmPersona.txtD_01A.getText()));
                
                mod.setD_43(Float.parseFloat(frmPersona.txtD_43.getText()));
                mod.setD_44(Float.parseFloat(frmPersona.txtD_44.getText()));
                mod.setD_45(Float.parseFloat(frmPersona.txtD_45.getText()));
                mod.setD_46(Float.parseFloat(frmPersona.txtD_46.getText()));
                mod.setD_47(Float.parseFloat(frmPersona.txtD_47.getText()));
                mod.setD_48(Float.parseFloat(frmPersona.txtD_48.getText()));
                mod.setD_49(Float.parseFloat(frmPersona.txtD_49.getText()));
                mod.setD_50(Float.parseFloat(frmPersona.txtD_50.getText()));
                mod.setTipo(String.valueOf(frmPersona.cbTipo.getSelectedItem()));
                mod.setQuincena(frmPersona.txtnumeroquincena.getText());
                mod.setLapso(frmPersona.txtnumeroquincena.getText());
                mod.setPlantel(String.valueOf(frmPersona.cbPlantel.getSelectedItem()));
                mod.setNoPuesto(String.valueOf(frmPersona.cbNumeroPuesto.getSelectedItem()));
                mod.setLeido("OK");
                 mod.setAP_PAT(frmPersona.txtap_pat.getText());
                mod.setAP_MAT(frmPersona.txtap_mat.getText());
                mod.setNombre(frmPersona.txtnombre.getText());
                mod.setCategoria(String.valueOf(frmPersona.cbCategoria.getSelectedItem()));
                mod.setHoras(frmPersona.txtSumaHoras.getText());
                mod.setNivel(String.valueOf(frmPersona.cbNivel.getSelectedItem()));
                mod.setFechaOld(frmPersona.txtfechaingreso.getText());
                mod.setTipoPer(String.valueOf(frmPersona.cbTipoPer.getSelectedItem()));
                mod.setZona(String.valueOf(frmPersona.cbZona.getSelectedItem()));
                mod.setSemestre(String.valueOf(frmPersona.cbSemestre.getSelectedItem()));
                mod.setTPlantel(String.valueOf(frmPersona.cbTipoPlantel.getSelectedItem()));
                mod.setTipoSin(String.valueOf(frmPersona.cbSindicato.getSelectedItem()));
                mod.setMutual(String.valueOf(frmPersona.cbMutual.getSelectedItem()));
                mod.setFacore(String.valueOf(frmPersona.txtporcentajed25.getText()));
                mod.setCedulaImss(frmPersona.txtImss.getText());
                mod.setRegimenPensionario(String.valueOf(frmPersona.cbRegimenPension.getSelectedItem()));
                mod.setNumeroquincena(Integer.parseInt(frmPersona.txtnumeroquincena.getText()));
                mod.setNumeroInasistencias(Integer.parseInt(frmPersona.txtinasistencias.getText()));
                mod.setCurp(frmPersona.txtcurp.getText());
                mod.setTotalDias(15 - Integer.parseInt(frmPersona.txtinasistencias.getText()));
                mod.setHorasBase(Integer.parseInt(frmPersona.txthorabase.getText()));
                mod.setHorasLimitadas(Integer.parseInt(frmPersona.txthoralimitada.getText()));
                mod.setHorasInterinas(Integer.parseInt(frmPersona.txthorainterina.getText()));
                mod.setCveCategoria(frmPersona.txtcvecategoria.getText());
                mod.setStatusissste("A");
                mod.setUsuario("Victor");
                mod.setFechaModificacionissste("No Utilizada");
                mod.setSaldoissste("No Utilizada");
                mod.setPorcentajeHipotecarioIssste(Float.parseFloat(frmPersona.txtporcentajehip.getText()));
                mod.setPorcentajeseccion31(Float.parseFloat(frmPersona.txtporcentajed32.getText()));
                mod.setPorcentajeissste(Float.parseFloat(frmPersona.txtporcentajed24.getText()));
                mod.setFechaInasistencias(frmPersona.txtfechainasistencias.getText());
                mod.setNumeroDePago("0");
                mod.setTotalDePago("0");
                mod.setFolioissste("0");
                mod.setNumeroDeContrato("0");
                mod.setTipoContrato("0");
                mod.setNombrePlantel(frmPersona.txtnombreplantel.getText());
                mod.setNumeroPlantel(frmPersona.txtnumeroplantel.getText());
                mod.setP_48(Float.parseFloat(frmPersona.txtP_48.getText()));
                mod.setP_49(Float.parseFloat(frmPersona.txtP_49.getText()));
                mod.setP_50(Float.parseFloat(frmPersona.txtP_50.getText()));
                mod.setObservacion("0");
                mod.setFechaDeBajaIssste("0");
                mod.setCveAyudaMutua(String.valueOf(frmPersona.cbAyudaMutua.getSelectedItem()));
                mod.setCveCuotaSindical(String.valueOf(frmPersona.cbcuotasindicato.getSelectedItem()));
                mod.setCajaSutcobach(frmPersona.txtcajasutcobach.getText());
                mod.setCajaSeccion(frmPersona.txtcajaseccion.getText());
                mod.setCajaSitcobach(frmPersona.txtcajasitcobach.getText());
                mod.setCajaSeccion63(frmPersona.txtcajaseccion63.getText());
               
            
                
                
                mod.setP_51(Float.parseFloat(frmPersona.txtP_51.getText()));
                mod.setP_52(Float.parseFloat(frmPersona.txtP_52.getText()));
                mod.setP_53(Float.parseFloat(frmPersona.txtP_53.getText()));
                mod.setP_54(Float.parseFloat(frmPersona.txtP_54.getText()));
                mod.setP_55(Float.parseFloat(frmPersona.txtP_55.getText()));
                mod.setP_56(Float.parseFloat(frmPersona.txtP_56.getText()));
                mod.setP_57(Float.parseFloat(frmPersona.txtP_57.getText()));
                mod.setP_58(Float.parseFloat(frmPersona.txtP_58.getText()));
                mod.setP_59(Float.parseFloat(frmPersona.txtP_59.getText()));
                mod.setP_60(Float.parseFloat(frmPersona.txtP_60.getText()));
                mod.setD_51(Float.parseFloat(frmPersona.txtD_51.getText()));
                mod.setD_52(Float.parseFloat(frmPersona.txtD_52.getText()));
                mod.setD_53(Float.parseFloat(frmPersona.txtD_53.getText()));
                mod.setD_54(Float.parseFloat(frmPersona.txtD_54.getText()));
                mod.setD_55(Float.parseFloat(frmPersona.txtD_55.getText()));
                mod.setD_56(Float.parseFloat(frmPersona.txtD_56.getText()));
                mod.setD_57(Float.parseFloat(frmPersona.txtD_57.getText()));
                mod.setD_58(Float.parseFloat(frmPersona.txtD_58.getText()));
                mod.setD_59(Float.parseFloat(frmPersona.txtD_59.getText()));
                mod.setD_60(Float.parseFloat(frmPersona.txtD_60.getText()));
                
                if (modC.modificar(mod)) {
                    JOptionPane.showMessageDialog(null, "Registro Modificado");
                 //   limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Modificado Intente Mas Tarde");
                  //  limpiar();
                }
           
           }
       
       
       }
       //</editor-fold>
       
       
    //<editor-fold desc="buscarempleado">   
       if (e.getSource() == frmPersona.BtnBuscar) {
          
            if (frmPersona.txtempleado.getText().isEmpty()|| frmPersona.txtnumeroquincena.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Introduce el Numero de Empleado y Quincena");

            } else {
                 // mod.setRfc(frmPersona.txtRfc.getText());
                  mod.setEmpleado(Integer.parseInt(frmPersona.txtempleado.getText()));
                  pension.setEmpleado(Integer.parseInt(frmPersona.txtempleado.getText()));
                  prestamo.setEmpleado(Integer.parseInt(frmPersona.txtempleado.getText()));
             
                  mod.setNumeroquincena(Integer.parseInt(frmPersona.txtnumeroquincena.getText()));
                if (modC.buscar(mod)) {
                     //frmPersona.txtNombre.setText(mod.getNombre());
                     frmPersona.txtEmpleadoPension.setText(String.valueOf(mod.getEmpleado()));
                     frmPersona.txtEmpleadoPrestamo.setText(String.valueOf(mod.getEmpleado()));
                     frmPersona.txtrfc.setText(mod.getRfc());
                     frmPersona.cbCveMovimiento.setSelectedItem(mod.getCvemvto());
                     frmPersona.txtP_01.setText(String.valueOf(mod.getP_01()));
                     frmPersona.txtP_01A.setText(String.valueOf(mod.getP_01A()));
                     frmPersona.txtP_01B.setText(String.valueOf(mod.getP_01B()));
                     frmPersona.txtP_01C.setText(String.valueOf(mod.getP_01C()));
                     frmPersona.txtP_01D.setText(String.valueOf(mod.getP_01D()));
                     frmPersona.txtP_01E.setText(String.valueOf(mod.getP_01E()));
                    
                    frmPersona.txtP_MC.setText(String.valueOf(mod.getP_MC()));
                    frmPersona.txtP_PG.setText(String.valueOf(mod.getP_PG()));
                    frmPersona.txtP_PE.setText(String.valueOf(mod.getP_PE()));
                    
                    frmPersona.txtP_2.setText(String.valueOf(mod.getP_02()));
                     frmPersona.txtP_3.setText(String.valueOf(mod.getP_03()));
                     frmPersona.txtP_4.setText(String.valueOf(mod.getP_04()));
                     frmPersona.txtP_5.setText(String.valueOf(mod.getP_05()));
                     frmPersona.txtP_6.setText(String.valueOf(mod.getP_06()));
                     frmPersona.txtP_7.setText(String.valueOf(mod.getP_07()));
                     frmPersona.txtP_8.setText(String.valueOf(mod.getP_08()));
                     frmPersona.txtP_9.setText(String.valueOf(mod.getP_09()));
                     frmPersona.txtP_10.setText(String.valueOf(mod.getP_10()));
                     frmPersona.txtP_11.setText(String.valueOf(mod.getP_11()));
                     frmPersona.txtP_12.setText(String.valueOf(mod.getP_12()));
                     frmPersona.txtP_13.setText(String.valueOf(mod.getP_13()));
                     frmPersona.txtP_14.setText(String.valueOf(mod.getP_14()));
                     frmPersona.txtP_15.setText(String.valueOf(mod.getP_15()));
                     frmPersona.txtP_16.setText(String.valueOf(mod.getP_16()));
                     frmPersona.txtP_17.setText(String.valueOf(mod.getP_17()));
                     frmPersona.txtP_18.setText(String.valueOf(mod.getP_18()));
                     frmPersona.txtP_19.setText(String.valueOf(mod.getP_19()));
                     frmPersona.txtP_20.setText(String.valueOf(mod.getP_20()));
                     frmPersona.txtP_21.setText(String.valueOf(mod.getP_21()));
                     frmPersona.txtP_22.setText(String.valueOf(mod.getP_22()));
                     frmPersona.txtP_23.setText(String.valueOf(mod.getP_23()));
                     frmPersona.txtP_24.setText(String.valueOf(mod.getP_24()));
                     frmPersona.txtP_25.setText(String.valueOf(mod.getP_25()));
                     frmPersona.txtP_26.setText(String.valueOf(mod.getP_26()));
                     frmPersona.txtP_27.setText(String.valueOf(mod.getP_27()));
                     frmPersona.txtP_28.setText(String.valueOf(mod.getP_28()));
                     frmPersona.txtP_29.setText(String.valueOf(mod.getP_29()));
                     frmPersona.txtP_30.setText(String.valueOf(mod.getP_30()));
                     
                   frmPersona.txtD_01.setText(String.valueOf(mod.getD_01()));
                   frmPersona.txtD_10.setText(String.valueOf(mod.getD_10()));
                   frmPersona.txtP_D1.setText(String.valueOf(mod.getP_DI()));
                   frmPersona.txtP_C1.setText(String.valueOf(mod.getP_C1()));
                   frmPersona.txtP_C2.setText(String.valueOf(mod.getP_C2()));
                   frmPersona.txtP_31.setText(String.valueOf(mod.getP_31()));
                   frmPersona.txtP_31.setText(String.valueOf(mod.getP_31()));
                   frmPersona.txtP_32.setText(String.valueOf(mod.getP_32()));
                   frmPersona.txtP_33.setText(String.valueOf(mod.getP_33()));
                    frmPersona.txtP_34.setText(String.valueOf(mod.getP_34()));
                    frmPersona.txtP_35.setText(String.valueOf(mod.getP_35()));
                    frmPersona.txtP_36.setText(String.valueOf(mod.getP_36()));
                    frmPersona.txtP_37.setText(String.valueOf(mod.getP_37()));
                    frmPersona.txtP_38.setText(String.valueOf(mod.getP_38()));
                    frmPersona.txtP_39.setText(String.valueOf(mod.getP_39()));
                    frmPersona.txtP_40.setText(String.valueOf(mod.getP_40()));
                    frmPersona.txtP_41.setText(String.valueOf(mod.getP_41()));
                    frmPersona.txtP_42.setText(String.valueOf(mod.getP_42()));
                    frmPersona.txtP_43.setText(String.valueOf(mod.getP_43()));
                    frmPersona.txtP_44.setText(String.valueOf(mod.getP_44()));
                    frmPersona.txtP_45.setText(String.valueOf(mod.getP_45()));
                    frmPersona.txtP_46.setText(String.valueOf(mod.getP_46()));
                    frmPersona.txtP_47.setText(String.valueOf(mod.getP_47()));
                    frmPersona.txtP_48.setText(String.valueOf(mod.getP_48()));
                    frmPersona.txtP_49.setText(String.valueOf(mod.getP_49()));
                    frmPersona.txtP_50.setText(String.valueOf(mod.getP_50()));
                   
                   
                   
                   frmPersona.txtD_CB.setText(String.valueOf(mod.getD_CB()));
                  
                   
                   frmPersona.txtD_02.setText(String.valueOf(mod.getD_02()));
                   frmPersona.txtD_03.setText(String.valueOf(mod.getD_03()));
                   frmPersona.txtD_04.setText(String.valueOf(mod.getD_04()));
                   frmPersona.txtD_05.setText(String.valueOf(mod.getD_05()));
                   frmPersona.txtD_06.setText(String.valueOf(mod.getD_06()));
                   frmPersona.txtD_07.setText(String.valueOf(mod.getD_07()));
                   frmPersona.txtD_08.setText(String.valueOf(mod.getD_08()));
                   frmPersona.txtD_09.setText(String.valueOf(mod.getD_09()));
                   frmPersona.txtD_11.setText(String.valueOf(mod.getD_11()));
                   frmPersona.txtD_12.setText(String.valueOf(mod.getD_12()));
                   frmPersona.txtD_13.setText(String.valueOf(mod.getD_13()));
                   frmPersona.txtD_14.setText(String.valueOf(mod.getD_14()));
                   frmPersona.txtD_15.setText(String.valueOf(mod.getD_15()));
                   frmPersona.txtD_16.setText(String.valueOf(mod.getD_16()));
                   frmPersona.txtD_17.setText(String.valueOf(mod.getD_17()));
                   frmPersona.txtD_18.setText(String.valueOf(mod.getD_18()));
                   frmPersona.txtD_19.setText(String.valueOf(mod.getD_19()));
                   frmPersona.txtD_20.setText(String.valueOf(mod.getD_20()));
                   frmPersona.txtD_21.setText(String.valueOf(mod.getD_21()));
                   frmPersona.txtD_22.setText(String.valueOf(mod.getD_22()));
                   frmPersona.txtD_23.setText(String.valueOf(mod.getD_23()));
                   frmPersona.txtD_24.setText(String.valueOf(mod.getD_24()));
                   frmPersona.txtD_25.setText(String.valueOf(mod.getD_25()));
                   frmPersona.txtD_26.setText(String.valueOf(mod.getD_26()));
                   frmPersona.txtD_27.setText(String.valueOf(mod.getD_27()));
                   frmPersona.txtD_28.setText(String.valueOf(mod.getD_28()));
                   frmPersona.txtD_29.setText(String.valueOf(mod.getD_29()));
                   frmPersona.txtD_30.setText(String.valueOf(mod.getD_30()));
                   frmPersona.txtD_31.setText(String.valueOf(mod.getD_31()));
                   frmPersona.txtD_32.setText(String.valueOf(mod.getD_32()));
                   frmPersona.txtD_33.setText(String.valueOf(mod.getD_33()));
                   frmPersona.txtD_34.setText(String.valueOf(mod.getD_34()));
                   frmPersona.txtD_35.setText(String.valueOf(mod.getD_35()));
                   frmPersona.txtD_36.setText(String.valueOf(mod.getD_36()));
                   frmPersona.txtD_37.setText(String.valueOf(mod.getD_37()));
                   frmPersona.txtD_38.setText(String.valueOf(mod.getD_38()));
                   frmPersona.txtD_39.setText(String.valueOf(mod.getD_39()));
                   frmPersona.txtD_40.setText(String.valueOf(mod.getD_40()));
                   frmPersona.txtD_41.setText(String.valueOf(mod.getD_41()));
                   frmPersona.txtD_42.setText(String.valueOf(mod.getD_42()));
                   frmPersona.txtD_43.setText(String.valueOf(mod.getD_43()));
                   frmPersona.txtD_44.setText(String.valueOf(mod.getD_44()));
                   frmPersona.txtD_45.setText(String.valueOf(mod.getD_45()));
                   frmPersona.txtD_46.setText(String.valueOf(mod.getD_46()));
                   frmPersona.txtD_01A.setText(String.valueOf(mod.getD_01A()));
                   frmPersona.cbTipo.setSelectedItem(mod.getTipo());
                   frmPersona.cbPlantel.setSelectedItem(mod.getPlantel());
                   frmPersona.cbNumeroPuesto.setSelectedItem(mod.getNoPuesto());
                   frmPersona.txtap_pat.setText(mod.getAP_PAT());
                   frmPersona.txtap_mat.setText(mod.getAP_MAT());
                   frmPersona.txtnombre.setText(mod.getNombre());
                  frmPersona.cbCategoria.setSelectedItem(mod.getCategoria());
                  frmPersona.txtSumaHoras.setText(mod.getHoras());
                  frmPersona.cbNivel.setSelectedItem(mod.getNivel());
                  frmPersona.txtfechaingreso.setText(mod.getFechaOld());
                  frmPersona.cbTipoPer.setSelectedItem(mod.getTipoPer());
                  frmPersona.cbZona.setSelectedItem(mod.getZona());
                  frmPersona.cbSemestre.setSelectedItem(mod.getSemestre());
                  frmPersona.cbTipoPlantel.setSelectedItem(mod.getTPlantel());
                  frmPersona.cbSindicato.setSelectedItem(mod.getTipoSin());
                  frmPersona.cbMutual.setSelectedItem(mod.getMutual());
                  frmPersona.txtporcentajed25.setText(mod.getFacore());
                  frmPersona.txtImss.setText(mod.getCedulaImss());
                  frmPersona.cbRegimenPension.setSelectedItem(mod.getRegimenPensionario());
                  frmPersona.txtinasistencias.setText(String.valueOf(mod.getNumeroInasistencias()));
                  frmPersona.txtcurp.setText(mod.getCurp());
                  frmPersona.txtDiasPagados.setText(String.valueOf(mod.getTotalDias()));
                  frmPersona.txthorabase.setText(String.valueOf(mod.getHorasBase()));
                  frmPersona.txthoralimitada.setText(String.valueOf(mod.getHorasLimitadas()));
                  frmPersona.txthorainterina.setText(String.valueOf(mod.getHorasInterinas()));
                  frmPersona.txtcvecategoria.setText(mod.getCveCategoria());
                  frmPersona.txtStatusIssste.setText(mod.getStatusissste());
                  frmPersona.txtStatusIssste.setText(mod.getStatusissste());
                  frmPersona.txtUsuario.setText(mod.getUsuario());
                  frmPersona.txtporcentajehip.setText(String.valueOf(mod.getPorcentajeHipotecarioIssste()));
                  frmPersona.txtporcentajed32.setText(String.valueOf(mod.getPorcentajeseccion31()));
                  frmPersona.txtporcentajed24.setText(String.valueOf(mod.getPorcentajeissste()));
                  frmPersona.txtfechainasistencias.setText(mod.getFechaInasistencias());
                  frmPersona.txtUsuario.setText(mod.getUsuario());
                  frmPersona.txtNumeroContrato.setText(mod.getNumeroDeContrato());
                  frmPersona.txtTipoContrato.setText(mod.getTipoContrato());
                  frmPersona.txtnombreplantel.setText(mod.getNombrePlantel());
                  frmPersona.txtnumeroplantel.setText(mod.getNumeroPlantel());
                  frmPersona.cbAyudaMutua.setSelectedItem(mod.getCveAyudaMutua());
                  frmPersona.cbcuotasindicato.setSelectedItem(mod.getCveCuotaSindical());
                   frmPersona.txtcajasutcobach.setText(mod.getCajaSutcobach());
                  frmPersona.txtcajaseccion.setText(mod.getCajaSeccion());
                  frmPersona.txtcajasitcobach.setText(mod.getCajaSitcobach());
                  frmPersona.txtcajaseccion63.setText(mod.getCajaSeccion63());
                  dibujartablapension();
                  dibujartablaprestamo();

                  

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro el empleado");
                  //  limpiar();
                }
                

            }

        }
        //</editor-fold >
        ////////////////
        
    //<editor-fold desc="guardarpension">    
        if (e.getSource() == frmPersona.BtnGuardarPension) {
        if ( frmPersona.txtEmpleadoPension.getText().isEmpty()
                    || frmPersona.txtPlantelPension.getText().isEmpty()
                    || frmPersona.txtBeneficiarioPension.getText().isEmpty()
                    || frmPersona.txtPorcentajePension.getText().isEmpty()
                    || frmPersona.txtRfcPension.getText().isEmpty()
                    || frmPersona.txtOficioPension.getText().isEmpty()
                    || frmPersona.txtImportePension.getText().isEmpty()
                    || frmPersona.txtNumeroQuincenaPension.getText().isEmpty()
            
                                       ) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
        
            pension.setBeneficiario(frmPersona.txtBeneficiarioPension.getText());
            pension.setEmpleado(Integer.parseInt(frmPersona.txtEmpleadoPension.getText()));
            pension.setPorcentaje(Float.parseFloat(frmPersona.txtPorcentajePension.getText()));
            pension.setPlantel(frmPersona.txtPlantelPension.getText());
            pension.setOficio(frmPersona.txtOficioPension.getText());
            pension.setMontodescuento(Float.parseFloat(frmPersona.txtImportePension.getText()));
            pension.setRfc(frmPersona.txtRfcPension.getText());
            pension.setNumeroQuincena(frmPersona.txtNumeroQuincenaPension.getText());
            
            if(modC.secuenciaalta(pension)){
                pension.setSecuencia(pension.getSecuencia());
            }else{
            //         JOptionPane.showMessageDialog(null, "Beneficiario Guardado");
            }
            
            if (frmPersona.JTPension.getRowCount() > 0) {

                for (int i = 0; i < frmPersona.JTPension.getRowCount(); i++) {

                    filatabla = Double.parseDouble(frmPersona.JTPension.getValueAt(i, 1).toString());
                    total += filatabla;

                }

                totalmasactual = total + Double.parseDouble(frmPersona.txtPorcentajePension.getText());

            }
            
            if (totalmasactual > 100) {
                    JOptionPane.showMessageDialog(null, "Porcentaje es mayor 0 menor a 100 % ajuste los datos");

                } else {
            ////////desdeaqui
             if (modC.guardarpension(pension)) {

                        dibujartablapension();                 
                        JOptionPane.showMessageDialog(null, "Beneficiario Guardado");
                       // limpiarbeneficiario();

                    } else {
                        JOptionPane.showMessageDialog(null, "Beneficiario No Guardado Intente Mas Tarde");
                       // limpiarbeneficiario();

                    }////hasta aqui
            }
           }
    
    
    }
        //</editor-fold > 
        
        ///////////
    //<editor-fold desc="modificarpension">    
    if (e.getSource() == frmPersona.BtnModificarPension) {
        if ( frmPersona.txtEmpleadoPension.getText().isEmpty()
                    || frmPersona.txtPlantelPension.getText().isEmpty()
                    || frmPersona.txtBeneficiarioPension.getText().isEmpty()
                    || frmPersona.txtPorcentajePension.getText().isEmpty()
                    || frmPersona.txtRfcPension.getText().isEmpty()
                    || frmPersona.txtOficioPension.getText().isEmpty()
                    || frmPersona.txtImportePension.getText().isEmpty()
                    || frmPersona.txtNumeroQuincenaPension.getText().isEmpty()
            
                                       ) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
            pension.setIdPension(frmPersona.txtIdPension.getText());
            pension.setBeneficiario(frmPersona.txtBeneficiarioPension.getText());
            pension.setEmpleado(Integer.parseInt(frmPersona.txtEmpleadoPension.getText()));
            pension.setPorcentaje(Float.parseFloat(frmPersona.txtPorcentajePension.getText()));
            pension.setPlantel(frmPersona.txtPlantelPension.getText());
            pension.setOficio(frmPersona.txtOficioPension.getText());
            pension.setMontodescuento(Float.parseFloat(frmPersona.txtImportePension.getText()));
            pension.setRfc(frmPersona.txtRfcPension.getText());
            pension.setNumeroQuincena(frmPersona.txtNumeroQuincenaPension.getText());
           if (modC.modificarpension(pension)) {
                    JOptionPane.showMessageDialog(null, "Registro Modificado");
                    dibujartablapension();
                    //limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Modificado Intente Mas Tarde");
                    //limpiar();
                }
        
        
        }
    
    
    }
    ////</editor-fold>    
    ////
    
    //<editor-fold desc="bajapension">    
    if (e.getSource() == frmPersona.BtnBajaPension) {
        if ( frmPersona.txtIdPension.getText().isEmpty()) 
            {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
        
                pension.setIdPension(frmPersona.txtIdPension.getText());
             
            if (modC.bajapension(pension)) {
                JOptionPane.showMessageDialog(null, "La pension ha sido dada de baja");
                 dibujartablapension();
                //limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "la pension no se puedo dar de baja");
               // limpiar();
            }
            
        
          }
    
    
    }
    //</editor-fold >    
    ////
    
    //<editor-fold defaultstate="collapsed" desc="buscarpension">    
    if (e.getSource() == frmPersona.BtnBuscarPension) {
         if ( frmPersona.txtIdPension.getText().isEmpty()) 
            {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
                pension.setIdPension(frmPersona.txtIdPension.getText());
         
                 
              if (modC.buscarpension(pension)) {
             
            frmPersona.txtBeneficiarioPension.setText(pension.getBeneficiario());
            frmPersona.txtPorcentajePension.setText(String.valueOf(pension.getPorcentaje()));
            frmPersona.txtPlantelPension.setText(pension.getPlantel());
            frmPersona.txtOficioPension.setText(pension.getOficio());
            frmPersona.txtImportePension.setText(String.valueOf(pension.getMontodescuento()));
            frmPersona.txtRfcPension.setText(pension.getRfc());
            frmPersona.txtNumeroQuincenaPension.setText(pension.getNumeroQuincena());
              }else {
                    JOptionPane.showMessageDialog(null, "No se encontro la pension");
                   // limpiar();
                }
             
             
         }
        
    
    
    }
     //</editor-fold > 
    /////
    //<editor-fold defaultstate="collapsed" desc="guardarprestamos"> 
     if (e.getSource() == frmPersona.BtnGuardarPrestamo) {
        if ( frmPersona.txtEmpleadoPrestamo.getText().isEmpty()
                   || frmPersona.txtMonto.getText().isEmpty()
                    || frmPersona.txtInteres.getText().isEmpty()
                    || frmPersona.txtPlazo.getText().isEmpty()
                    || frmPersona.txtNumeroPrestamo.getText().isEmpty()
                    || frmPersona.txtDescuento.getText().isEmpty()
                    || frmPersona.txtMontoTotal.getText().isEmpty()
                    || frmPersona.txtSaldo.getText().isEmpty()
                    || frmPersona.txtQnaInicio.getText().isEmpty()
                    || frmPersona.txtNumeroQuincenaPrestamo.getText().isEmpty()
                    
                                       ) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
        
            prestamo.setEmpleado(Integer.parseInt(frmPersona.txtEmpleadoPrestamo.getText()));
            //prestamo.setNombrePrestamo(frmPersona.txtNombrePrestamos.getText());
            prestamo.setMonto(Float.parseFloat(frmPersona.txtMonto.getText()));
            prestamo.setInteres(Float.parseFloat(frmPersona.txtInteres.getText()));
            prestamo.setFolio(Integer.parseInt(frmPersona.txtNumeroPrestamo.getText()));
            prestamo.setDescuento(Float.parseFloat(frmPersona.txtDescuento.getText()));
            prestamo.setTotal(Float.parseFloat(frmPersona.txtMontoTotal.getText()));
            prestamo.setSaldo(Float.parseFloat(frmPersona.txtSaldo.getText()));
            prestamo.setNumeroquincenaInicio(frmPersona.txtQnaInicio.getText());
            prestamo.setNumeroquincenaPrestamo(frmPersona.txtNumeroQuincenaPrestamo.getText());
            prestamo.setPlantel(frmPersona.txtPlantelPrestamo.getText());
            prestamo.setClaveDescuento(String.valueOf(frmPersona.cbClavePrestamo.getSelectedItem()));
            prestamo.setPlazo(Integer.parseInt(frmPersona.txtPlazo.getText()));
               
            ////////desdeaqui
             if (modC.guardarprestamo(prestamo)) {

                        dibujartablaprestamo();                 
                        JOptionPane.showMessageDialog(null, "Prestamo Guardado");
                       // limpiarbeneficiario();

                    } else {
                        JOptionPane.showMessageDialog(null, "Prestamo No Guardado Intente Mas Tarde");
                       // limpiarbeneficiario();

                    }////hasta aqui
            
           }
    
    
    }
     //</editor-fold >  
    //// 
    
    //<editor-fold defaultstate="collapsed" desc="modificarprestamo"> 
    if (e.getSource() == frmPersona.BtnModificarPrestamo) {
        if ( frmPersona.txtEmpleadoPrestamo.getText().isEmpty()
                    || frmPersona.txtMonto.getText().isEmpty()
                    || frmPersona.txtInteres.getText().isEmpty()
                    || frmPersona.txtPlazo.getText().isEmpty()
                    || frmPersona.txtNumeroPrestamo.getText().isEmpty()
                    || frmPersona.txtDescuento.getText().isEmpty()
                    || frmPersona.txtMontoTotal.getText().isEmpty()
                    || frmPersona.txtSaldo.getText().isEmpty()
                    || frmPersona.txtQnaInicio.getText().isEmpty()
                    || frmPersona.txtNumeroQuincenaPrestamo.getText().isEmpty()
                    
                                       ) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
             
            prestamo.setEmpleado(Integer.parseInt(frmPersona.txtEmpleadoPrestamo.getText()));
            //prestamo.setNombrePrestamo(frmPersona.txtNombrePrestamos.getText());
            prestamo.setMonto(Float.parseFloat(frmPersona.txtMonto.getText()));
            prestamo.setInteres(Float.parseFloat(frmPersona.txtInteres.getText()));
            prestamo.setPlazo(Integer.parseInt(frmPersona.txtPlazo.getText()));
            prestamo.setFolio(Integer.parseInt(frmPersona.txtNumeroPrestamo.getText()));
            prestamo.setDescuento(Float.parseFloat(frmPersona.txtDescuento.getText()));
            prestamo.setTotal(Float.parseFloat(frmPersona.txtMontoTotal.getText()));
            prestamo.setSaldo(Float.parseFloat(frmPersona.txtSaldo.getText()));
            prestamo.setNumeroquincenaInicio(frmPersona.txtQnaInicio.getText());
            prestamo.setNumeroquincenaPrestamo(frmPersona.txtNumeroQuincenaPrestamo.getText());
            prestamo.setPlantel(frmPersona.txtPlantelPrestamo.getText());
            prestamo.setClaveDescuento(String.valueOf(frmPersona.cbClavePrestamo.getSelectedItem()));
            prestamo.setIdPrestamo(frmPersona.txtIdPrestamo.getText());
           
           if (modC.modificarprestamo(prestamo)) {
                    JOptionPane.showMessageDialog(null, "Prestamo Modificado");
                    dibujartablaprestamo();
                    //limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Prestamo No Modificado Intente Mas Tarde");
                    //limpiar();
                }
        
        
        }
    
    
    }
     //</editor-fold > 
     //////
     
    //<editor-fold defaultstate="collapsed" desc="buscarprestamo">  
    if (e.getSource() == frmPersona.BtnBuscarPrestamo) {
         if ( frmPersona.txtIdPrestamo.getText().isEmpty()) 
            {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
                prestamo.setIdPrestamo(frmPersona.txtIdPrestamo.getText());
         
                 
              if (modC.buscarprestamo(prestamo)) {
                  
                  
            frmPersona.txtMonto.setText(String.valueOf(prestamo.getMonto()));
            frmPersona.txtInteres.setText(String.valueOf(prestamo.getInteres()));
            frmPersona.txtPlazo.setText(String.valueOf(prestamo.getPlazo()));
            frmPersona.txtNumeroPrestamo.setText(String.valueOf(prestamo.getFolio()));
            frmPersona.txtDescuento.setText(String.valueOf(prestamo.getDescuento()));
            frmPersona.txtMontoTotal.setText(String.valueOf(prestamo.getTotal()));
            frmPersona.txtSaldo.setText(String.valueOf(prestamo.getSaldo()));
            frmPersona.txtQnaInicio.setText(String.valueOf(prestamo.getNumeroquincenaInicio()));
            frmPersona.txtNumeroQuincenaPrestamo.setText(String.valueOf(prestamo.getNumeroquincenaPrestamo()));
            frmPersona.txtPlantelPrestamo.setText(String.valueOf(prestamo.getPlantel()));
            frmPersona.cbClavePrestamo.setSelectedItem(prestamo.getClaveDescuento());
                           
          
            
            
              }else {
                    JOptionPane.showMessageDialog(null, "No se encontro el prestamo");
                   // limpiar();
                }
             
             
         }
        
    
    
    }
      //</editor-fold > 
      ////
      
    //<editor-fold defaultstate="collapsed" desc="cancelarprestamo"> 
    if (e.getSource() == frmPersona.BtnCancelarPrestamo) {
        if ( frmPersona.txtIdPrestamo.getText().isEmpty()) 
            {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
        
                prestamo.setIdPrestamo(frmPersona.txtIdPrestamo.getText());
             
            if (modC.bajaprestamo(prestamo)) {
                JOptionPane.showMessageDialog(null, "El Prestamo ha sido dada de baja");
                 dibujartablaprestamo();
                //limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "El Prestamo no se puedo dar de baja");
               // limpiar();
            }
            
        
          }
    
    
    }
     //</editor-fold > 
     
     ///
     
    //<editor-fold defaultstate="collapsed" desc="abrirlaoyout"> 
    if(e.getSource() == frmPersona.btnAbrirLayout){
          
          JFileChooser fc = new JFileChooser();
          fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
          FileFilter filt = new FileNameExtensionFilter("Archivo csv","csv");
          fc.addChoosableFileFilter(filt);
          fc.setFileFilter(filt);
          int result = fc.showOpenDialog(fc);
          
          if(result == JFileChooser.APPROVE_OPTION){
           fc.getSelectedFile().getAbsolutePath();
           //mod.setRuta(fc.getSelectedFile().getAbsolutePath());
           frmPersona.txtRuta.setText(fc.getSelectedFile().getAbsolutePath());
           frmPersona.btnAbrirLayout.setVisible(false);
           frmPersona.btnImportarLayout.setVisible(true);
           frmPersona.btnCancelarLayout.setVisible(true);
           
                    
          }if(result == JFileChooser.CANCEL_OPTION){
          }

          
          
          }
     //</editor-fold > 
     //////
     
    //<editor-fold defaultstate="collapsed" desc="importarlayout"> 
      if(e.getSource() == frmPersona.btnImportarLayout){
               
              if(frmPersona.txtRuta.getText().isEmpty())
              {
               
            
              }
              else{
               mod.setNumeroQuincenaImportar(String.valueOf(frmPersona.cbperiodo.getSelectedItem()) + String.valueOf(frmPersona.cbNumero.getSelectedItem()) );
               mod.setRuta(frmPersona.txtRuta.getText());
               mod.setClaveImportar(String.valueOf(frmPersona.cbConceptoImportar.getSelectedItem()));
              
               reporte.setRuta(frmPersona.txtRuta.getText());
               reporte.setPeriodo(String.valueOf(frmPersona.cbperiodo.getSelectedItem()) + String.valueOf(frmPersona.cbNumero.getSelectedItem()));
                      try {

                          
                         //  modC.importararchivolayout(mod);
                       
                      if (frmPersona.cbConceptoImportar.getSelectedItem() == "D_07") {
                        // JOptionPane.showMessageDialog(null, "ELEGISTE AHORROS");
                     //       modC.importararchivoquincenal(mod);
                       modC.importararchivolayoutprestamos(mod);
      
                      }else if(frmPersona.cbConceptoImportar.getSelectedItem() == "HojaiSp"){
                      
                      consultaReporte.importararchivolayouthojai(reporte);
                      } 
                      
                      else {
                      //JOptionPane.showMessageDialog(null, "ELEGISTE PRESTAMOS");
                     // modC.importararchivoquincenalprestamos(mod);
                      modC.importararchivolayout(mod);
                      }

                  } catch (IOException ex) {
                      Logger.getLogger(CtrlEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                  }

                 
              }
              
          }
      
       //</editor-fold> 
       ///
    //<editor-fold defaultstate="collapsed" desc="cancelarlayout"> 
      if(e.getSource() == frmPersona.btnCancelarLayout){
           frmPersona.btnAbrirLayout.setVisible(true);
           frmPersona.btnImportarLayout.setVisible(false);
           frmPersona.btnCancelarLayout.setVisible(false);
         }
       //</editor-fold > 
       ///
      
    //<editor-fold defaultstate="collapsed" desc="exportarreportes"> 
        if (e.getSource() == frmPersona.BtnExportarReportes) {
            
            // JOptionPane.showMessageDialog(null, "Enviar Reporte");
            reporte.setPeriodo(String.valueOf(frmPersona.cbperiodo1.getSelectedItem()) + String.valueOf(frmPersona.cbNumero1.getSelectedItem()));
            reporte.setNombreReporte(String.valueOf(frmPersona.cbNombreInforme.getSelectedItem()));
            reporte.setClaveReporte(String.valueOf(frmPersona.cbConceptoImportar1.getSelectedItem()));
            reporte.setPlantelReporte(String.valueOf(frmPersona.cbPlantelExportar.getSelectedItem()));
            reporte.setTipoPlantelReporte(String.valueOf(frmPersona.cbTipoPlantelExportar.getSelectedItem()));
            reporte.setTipoPlaza(String.valueOf(frmPersona.cbTipoPlaza.getSelectedItem()));
           
            if (frmPersona.cbNombreInforme.getSelectedItem() == "CuotasYAportacionesCP") {
                consultaReporte.exportarinformecuotasyaportaciones(reporte);
               // JOptionPane.showMessageDialog(null, "Enviar Reporte");
           
            }
              
              if (frmPersona.cbNombreInforme.getSelectedItem() == "TrabajadoresCotizantesCP") {
                consultaReporte.exportarinformetrabajadorescotizantes(reporte);
               // JOptionPane.showMessageDialog(null, "Enviar Reporte");
           
            }
              if (frmPersona.cbNombreInforme.getSelectedItem() == "DispersionDePensionCP") {
                consultaReporte.exportarinformedispersiondepension(reporte);
               // JOptionPane.showMessageDialog(null, "Enviar Reporte");
           
            }
              
            if (frmPersona.cbNombreInforme.getSelectedItem() == "CaratulaDeNominaCP") {
                consultaReporte.exportarinformedcaratuladenomina(reporte);
               // JOptionPane.showMessageDialog(null, "Enviar Reporte");
           
            }  
            
            if (frmPersona.cbNombreInforme.getSelectedItem() == "ListadoHorizontalCP") {
                consultaReporte.exportarinformelistadohorizontal(reporte);
               // JOptionPane.showMessageDialog(null, "Enviar Reporte");
           
            }  
            
            if (frmPersona.cbNombreInforme.getSelectedItem() == "InformeDeConceptosCP") {
                consultaReporte.exportarinformerelaciondeconceptos(reporte);
               // JOptionPane.showMessageDialog(null, "Enviar Reporte");
           
            } 
            if (frmPersona.cbNombreInforme.getSelectedItem() == "InformeResumenDeConceptosCP") {
                consultaReporte.exportarinformerelaciondeconceptosresumen(reporte);
           
            } 
            
             if (frmPersona.cbNombreInforme.getSelectedItem() == "Dispersion1") {
                consultaReporte.exportarinformedispersion1(reporte);
           
            } 
             if (frmPersona.cbNombreInforme.getSelectedItem() == "Dispersion2") {
                consultaReporte.exportarinformedispersion2(reporte);
           
            } 
              if (frmPersona.cbNombreInforme.getSelectedItem() == "CreditosHipotecariosCP") {
                consultaReporte.exportarinformesrelacioncreditoshipotecarios(reporte);
          
            } 
               if (frmPersona.cbNombreInforme.getSelectedItem() == "EstadisticasPorNombramientoSP") {
                consultaReporte.exportarinformesestadiscticaspornombramiento(reporte);
          
            } 
             if (frmPersona.cbNombreInforme.getSelectedItem() == "EstadisticasPorCategoriaSP") {
                consultaReporte.exportarinformesestadiscticasporcategoria(reporte);
          
            } 
              if (frmPersona.cbNombreInforme.getSelectedItem() == "ListadoDeNominaCP") {
                consultaReporte.exportarinformeslistadodenomina(reporte);
          
            } 
             if (frmPersona.cbNombreInforme.getSelectedItem() == "CostosPorTipoDePlantelCP") {
                consultaReporte.exportarinformescostosportipoplantel(reporte);
          
            } 
               

        }
         //</editor-fold > 
         ////
      
    //<editor-fold defaultstate="collapsed" desc="timbrado"> 
        if (e.getSource() == frmPersona.BtnTimbrado) {
          
            reporte.setPeriodo(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem()));
            reporte.setNumeroPeriodo(String.valueOf(frmPersona.cbNumero2.getSelectedItem()));
            reporte.setYearPeriodo(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()));
            reporte.setTipoNomina(String.valueOf(frmPersona.cbTipoNomina.getSelectedItem()));
            reporte.setTipoJornada(String.valueOf(frmPersona.cbTipoJornada.getSelectedItem()));
            reporte.setTipocontrato(String.valueOf(frmPersona.cbTipoContrato.getSelectedItem()));
            reporte.setTipoRegimen(String.valueOf(frmPersona.cbTipoRegimen.getSelectedItem()));
            reporte.setRiesgoDePuesto(String.valueOf(frmPersona.cbRiesgoPuesto.getSelectedItem()));
            reporte.setPeriodicidadDePago(String.valueOf(frmPersona.cbPeriodicidadPago.getSelectedItem()));
            reporte.setCodigoPostal(frmPersona.txtCodigoPostalCFDI.getText());
            reporte.setSerie(String.valueOf(frmPersona.cbSerieCFDI.getSelectedItem()));
            reporte.setRegistroPatronal(frmPersona.txtRegistroPatronal.getText());
            reporte.setOrigenRecursos(String.valueOf(frmPersona.cbOrigenRecurso.getSelectedItem()));
        
         
         if (frmPersona.txtFolioInicioCFDI.getText().isEmpty())
        {
          JOptionPane.showMessageDialog(null, "Por Favor Introduce el folio inicial de los CFDI");
        }
       else{
        reporte.setFolioinicialCFDI(Integer.parseInt(frmPersona.txtFolioInicioCFDI.getText()));
        consultaReporte.exportarinformeparatimbrado(reporte);
        }
        
        }
        //</editor-fold> 
        ///
        
    //<editor-fold defaultstate="collapsed" desc="nuevanomina"> 
        if (e.getSource() == frmPersona.BtnNuevaNomina) {
          
        reporte.setPeriodo(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()));
        reporte.setNumeroquincena(String.valueOf(frmPersona.cbNumero2.getSelectedItem()));
           
          consultaReporte.procesarnuevanomina(reporte);
          
        
        
        }
        
        //</editor-fold > 
      
        //////
    //<editor-fold defaultstate="collapsed" desc="ayudamutua"> 
        if (e.getSource() == frmPersona.BtnProcesoAyuda) {

            reporte.setPeriodo(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem()));

            consultaReporte.procesarayudamutua(reporte);

        }
        //</editor-fold > 
        ////
    //<editor-fold defaultstate="collapsed" desc="procesocuotasindicalglobal"> 
        if (e.getSource() == frmPersona.BtnProcesoCuota) {
            reporte.setPeriodo(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem()));
            consultaReporte.procesarcuotasindical(reporte);
        }
        
        //</editor-fold > 
        //////
        
    //<editor-fold defaultstate="collapsed" desc="procesoantiguedadglobal"> 
        if (e.getSource() == frmPersona.BtnProcesoAntiguedad) {
            reporte.setPeriodo(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem()));
            consultaReporte.procesarantiguedad(reporte);
        }
        //</editor-fold > 
        ///
    //<editor-fold defaultstate="collapsed" desc="procesopension"> 
        if (e.getSource() == frmPersona.BtnProcesoPension) {
            reporte.setPeriodo(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem()));
            consultaReporte.procesarpensiones(reporte);
        }
        //</editor-fold> 
        /////
        ////proceso genera issste
     //<editor-fold desc="genera proceso issste">
        if (e.getSource() == frmPersona.BtnGenerarIsste) {
               
                String rfcprueba;
               int empleadoglobal;
               int numeroquincena;
               int numerodeplazasglobal;
               Double salariobaseglobal = 0.0;
               Double SumaP18global = 0.0;
               Double SumaP19global = 0.0;
               Double SumaAntiguedadglobal = 0.0;
               Double baseissste = 0.0;
               Double SalarioIsrGlobal = 0.0;
               int EmpleadoGanamasGlobal;
               int EmpleadoUnaPlazaGlobal;
               Double salariominimoglobal = 0.0;
               Double UnSalarioIsssteGlobal = 0.0;
               Double DiezSalariosIsssteGlobal = 0.0;
                //Cuotas ISSSTE
                Double D_03Global = 0.0;
                Double D_04Global = 0.0;
                Double D_21Global = 0.0;
                Double D_22Global = 0.0;
                Double D_23Global = 0.0;
                Double D_03RGlobal = 0.0;
                Double D_04RGlobal = 0.0;
                Double D_21RGlobal = 0.0;
                Double D_22RGlobal = 0.0;
                Double D_23RGlobal = 0.0;
                Double IsrCalculoGlobal = 0.0;
                ArrayList Empleado = new ArrayList();
                reporte.setPeriodo(String.valueOf(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem())) );
                numeroquincena = Integer.parseInt((String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem())) );           
                 mod.setNumeroquincena(numeroquincena);      
                Empleado = consultaReporte.procesarIsrGlobal(reporte,mod);
                 Iterator it = Empleado.iterator();
                 while (it.hasNext()) {
                     Object objeto = it.next();
                      Empleado empleado = (Empleado) objeto;
                      rfcprueba = empleado.getRfc();
                      empleadoglobal = empleado.getEmpleado();
                      SumaAntiguedadglobal = empleado.getAntiguedadsuma();
                      SumaP18global = empleado.getSumap18();
                      SumaP19global = empleado.getSumap19();
                      salariobaseglobal = empleado.getSalarioBase();
                      baseissste = empleado.getBaseissste();
                       numerodeplazasglobal = empleado.getNumerodeplazas();
                      mod.setRfc(rfcprueba);
                     
                salariobaseglobal = empleado.getSalarioBase();
                  SalarioIsrGlobal = (salariobaseglobal + SumaP18global + SumaP19global)*2;
                  baseissste  = salariobaseglobal + SumaAntiguedadglobal ;
               
                    
                   if (numerodeplazasglobal > 1) {
                    if (modC.empleadoganamas(mod)) {
                    
                      EmpleadoGanamasGlobal = mod.getNumerodeempleadoganamas();
                     mod.setNumerodeempleadoganamas(EmpleadoGanamasGlobal);
                     }else{
                    
                    } 
                     
                }else{
                
                   EmpleadoUnaPlazaGlobal = empleadoglobal;
                  mod.setNumerodeempleadoganamas(EmpleadoUnaPlazaGlobal);
            
               }
                    
             int  periodosalario = 2017;
            minimo.setYear(periodosalario);
            if (modC.salariominimo(minimo)) {
                salariominimoglobal = Double.parseDouble(minimo.getZonaa());
                UnSalarioIsssteGlobal = salariominimoglobal * 15;
                DiezSalariosIsssteGlobal = UnSalarioIsssteGlobal * 10;
            }
            
            if (baseissste > DiezSalariosIsssteGlobal) {
                baseissste = DiezSalariosIsssteGlobal;

            }
            if (baseissste < UnSalarioIsssteGlobal) {
                baseissste = UnSalarioIsssteGlobal;
            }
            
            
            if (modC.cuotasissste(cuotas)) {
                D_03Global = Double.parseDouble(cuotas.getD_03());
                D_04Global = Double.parseDouble(cuotas.getD_04());
                D_21Global = Double.parseDouble(cuotas.getD_21());
                D_22Global = Double.parseDouble(cuotas.getD_22());
                D_23Global = Double.parseDouble(cuotas.getD_23());
                D_03RGlobal = baseissste * D_03Global;
                D_04RGlobal = baseissste * D_04Global;
                D_21RGlobal = baseissste * D_21Global;
                D_22RGlobal = baseissste * D_22Global;
                D_23RGlobal = baseissste * D_23Global;
                D_03RGlobal = Double.parseDouble(String.format("%.2f", D_03RGlobal));
                D_04RGlobal = Double.parseDouble(String.format("%.2f", D_04RGlobal));
                D_21RGlobal = Double.parseDouble(String.format("%.2f", D_21RGlobal));
                D_22RGlobal = Double.parseDouble(String.format("%.2f", D_22RGlobal));
                D_23RGlobal = Double.parseDouble(String.format("%.2f", D_23RGlobal));
                mod.setD_03masdeunaplaza(D_03RGlobal);
                mod.setD_04masdeunaplaza(D_04RGlobal);
                mod.setD_21masdeunaplaza(D_21RGlobal);
                mod.setD_22masdeunaplaza(D_22RGlobal);
                mod.setD_23masdeunaplaza(D_23RGlobal);
              }
             isrtabla.setCalculoIsr(SalarioIsrGlobal);    
            if (modC.calculoisr(isrtabla)) {
                
                
                   isrtabla.setCalculoIsr(SalarioIsrGlobal);    
                   isrtabla.setIntermedio(isrtabla.getIntermedio());
                if (modC.calculosubsidio(isrtabla)) {
                    IsrCalculoGlobal = isrtabla.getRCalculoIsr();
                    if(IsrCalculoGlobal < 0){
                   // frmPersona.txtD_01.setText("0");
                    IsrCalculoGlobal = IsrCalculoGlobal * (-1);
                   // frmPersona.txtP_14.setText(String.valueOf(IsrCalculo));
                    mod.setD_01masdeunaplaza(0.00);
                    mod.setP_14masdeunaplaza(IsrCalculoGlobal);
                    }else{
                   // frmPersona.txtD_01.setText(String.valueOf(IsrCalculo));
                   // frmPersona.txtP_14.setText("0");
                    mod.setD_01masdeunaplaza(IsrCalculoGlobal);
                    mod.setP_14masdeunaplaza(0.00);
                    }
           
                }
                
             }
            if (modC.actualizarempleadoganamas(mod)) {
               
                
                
                if (modC.actualizarotrosempleados(mod)) {
                } else {

                }
                
                
            } else {
            }
                 
                 
                 }//////////////termina while rs.hastnext
        
        
        }//</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="procesoahorros"> 
        if (e.getSource() == frmPersona.BtnProcesoAhorro) {
            reporte.setPeriodo(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem()));
            consultaReporte.procesarpensiones(reporte);
        }
        //</editor-fold>    

    
    //<editor-fold desc="genera proceso Nota Tecnica">
        if (e.getSource() == frmPersona.btnNotaTecnica) {
         
            reporte.setPeriodo(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()));
            reporte.setNumeroquincena(String.valueOf(frmPersona.cbNumero2.getSelectedItem()));

            if(consultaReporte.comprobarqnanota(reporte)){
             consultaReporte.procesarnuevanominanota(reporte);
            
            
            int numeroquincena;
            /*String rfcprueba;
            Double factor1 = 0.0;
            Double p14calculo = 0.0;
            Double d01calculo = 0.0;
            Double p01sinplan = 0.0;
            Double p03sinplan = 0.0;
            Double p01asinplan = 0.0;
            Double p01bsinplan = 0.0;
            Double p01csinplan = 0.0;
            Double p01dsinplan = 0.0;
            Double p01esinplan = 0.0;
            Double p18esinplan = 0.0;
            Double p19esinplan = 0.0;
            Double p14esinplan = 0.0;
            Double d01esinplan = 0.0;
            Double sumap01sinplan = 0.0;
            Double sumap01asinplan = 0.0;
            Double sumap01bsinplan = 0.0;
            Double sumap01csinplan = 0.0;
            Double sumap01dsinplan = 0.0;
            Double sumap01esinplan = 0.0;
            Double sumap18esinplan = 0.0;
            Double sumap19esinplan = 0.0;
            Double sumap03sinplan = 0.0;
            Double percepcionessinplan = 0.0;
            Double deduccionessinplan = 0.0;
            Double percepcionesnocalculosinplan = 0.0;
            Double deduccionesnocalculosinplan = 0.0;
            Double sumapercepcionessinplanfinal = 0.0;
            Double factor2 = 0.0;
            Double salarioconplan = 0.0;
            Double p18conplan = 0.0;
            Double p19conplan = 0.0;
            Double netosinplan = 0.0;
            Double netoconplan = 0.0;
            Double netofinal = 0.0;
            Double netoparaequilibrar = 0.0;
            Double diferencianetoparaequilibrar = 0.0;
            Double salarioporfactor2 = 0.0;
            Double diferencianetos = 0.0;
            int banderafactor = 0;
            int banderafactormenorasalario = 0;
            int empleadoglobal;
            int numeroquincena;
            int numerodeplazasglobal = 1;
            Double salariobaseglobal = 0.0;
            Double SumaP18global = 0.0;
            Double SumaP19global = 0.0;
            Double SumaAntiguedadglobal = 0.0;
            Double baseissste = 0.0;
            Double SalarioIsrGlobal = 0.0;
            Double SalarioIsrPrevision = 0.0;
            Double P_01conPlan = 0.0;
            int EmpleadoGanamasGlobal;
            int EmpleadoUnaPlazaGlobal;
            Double salariominimoglobal = 0.0;
            Double UnSalarioIsssteGlobal = 0.0;
            Double DiezSalariosIsssteGlobal = 0.0;
            Double SieteSalariosIsssteGlobal = 0.0;
            Double percepcionesmasprevision = 0.0;
            Double previsionexenta = 0.0;
            Double previsiontotalexentafinal = 0.0;
            Double previsiontotalexentafinalsubsidio = 0.0;
            Double subsidiofinal = 0.0;
            Double previsiongravada = 0.0;
            Double percepcionesmasprevisionexenta = 0.0;
            Double percepcionesconplan = 0.0;
            Double percepcionesparaequilibrar = 0.0;
            Double deduccionesparaequilibrar = 0.0;
            Double salarioconplanentrefactor = 0.0;
            Double comparanetosfinales = 0.0;
            Double D_03Global = 0.0;
            Double D_04Global = 0.0;
            Double D_21Global = 0.0;
            Double D_22Global = 0.0;
            Double D_23Global = 0.0;
            Double D_03RGlobal = 0.0;
            Double D_04RGlobal = 0.0;
            Double D_21RGlobal = 0.0;
            Double D_22RGlobal = 0.0;
            Double D_23RGlobal = 0.0;
            Double IsrCalculoGlobal = 0.0;
            Double IsrCalculoGlobalprueba = 0.0;*/
            ArrayList Empleado = new ArrayList();
            ArrayList EmpleadoRfc = new ArrayList();
            reporte.setPeriodo(String.valueOf(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem())));
            numeroquincena = Integer.parseInt((String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem())));
            mod.setNumeroquincena(numeroquincena);

            
            
            Empleado = consultaReporte.procesarDatosNotaTecnicaGlobales(reporte, mod);
            Iterator it = Empleado.iterator();
            while (it.hasNext()) {
            String rfcprueba;
            Double factor1 = 0.0;
            Double p14calculo = 0.0;
            Double d01calculo = 0.0;
            Double p01sinplan = 0.0;
            Double p03sinplan = 0.0;
            Double p01asinplan = 0.0;
            Double p01bsinplan = 0.0;
            Double p01csinplan = 0.0;
            Double p01dsinplan = 0.0;
            Double p01esinplan = 0.0;
            Double p18esinplan = 0.0;
            Double p19esinplan = 0.0;
            Double p14esinplan = 0.0;
            Double d01esinplan = 0.0;
            Double sumap01sinplan = 0.0;
            Double sumap01asinplan = 0.0;
            Double sumap01bsinplan = 0.0;
            Double sumap01csinplan = 0.0;
            Double sumap01dsinplan = 0.0;
            Double sumap01esinplan = 0.0;
            Double sumap18esinplan = 0.0;
            Double sumap19esinplan = 0.0;
            Double sumap03sinplan = 0.0;
            Double percepcionessinplan = 0.0;
            Double deduccionessinplan = 0.0;
            Double percepcionesnocalculosinplan = 0.0;
            Double deduccionesnocalculosinplan = 0.0;
            Double sumapercepcionessinplanfinal = 0.0;
            Double factor2 = 0.0;
            Double salarioconplan = 0.0;
            Double p18conplan = 0.0;
            Double p19conplan = 0.0;
            Double netosinplan = 0.0;
            Double netoconplan = 0.0;
            Double netofinal = 0.0;
            Double netoparaequilibrar = 0.0;
            Double netoparacomprobar = 0.0;
            Double diferencianetoparaequilibrar = 0.0;
            Double salarioporfactor2 = 0.0;
            Double diferencianetos = 0.0;
            int banderafactor = 0;
            int banderafactormenorasalario = 0;
            int empleadoglobal;
            //int numeroquincena;
            int numerodeplazasglobal = 1;
            Double salariobaseglobal = 0.0;
            Double SumaP18global = 0.0;
            Double SumaP19global = 0.0;
            Double SumaAntiguedadglobal = 0.0;
            Double baseissste = 0.0;
            Double SalarioIsrGlobal = 0.0;
            Double SalarioIsrPrevision = 0.0;
            Double P_01conPlan = 0.0;
            int EmpleadoGanamasGlobal;
            int EmpleadoUnaPlazaGlobal;
            Double salariominimoglobal = 0.0;
            Double UnSalarioIsssteGlobal = 0.0;
            Double DiezSalariosIsssteGlobal = 0.0;
            Double SieteSalariosIsssteGlobal = 0.0;
            Double percepcionesmasprevision = 0.0;
            Double previsionexenta = 0.0;
            Double previsiontotalexentafinal = 0.0;
            Double previsiontotalexentafinalsubsidio = 0.0;
            Double subsidiofinal = 0.0;
            Double previsiongravada = 0.0;
            Double percepcionesmasprevisionexenta = 0.0;
            Double percepcionesconplan = 0.0;
            Double percepcionesparaequilibrar = 0.0;
            Double deduccionesparaequilibrar = 0.0;
            Double salarioconplanentrefactor = 0.0;
            Double comparanetosfinales = 0.0;
            Double D_03Global = 0.0;
            Double D_04Global = 0.0;
            Double D_21Global = 0.0;
            Double D_22Global = 0.0;
            Double D_23Global = 0.0;
            Double D_03RGlobal = 0.0;
            Double D_04RGlobal = 0.0;
            Double D_21RGlobal = 0.0;
            Double D_22RGlobal = 0.0;
            Double D_23RGlobal = 0.0;
            Double IsrCalculoGlobal = 0.0;
            Double IsrCalculoGlobalprueba = 0.0;    
                
                
                
                
                Object objeto = it.next();
                Empleado empleado = (Empleado) objeto;
                rfcprueba = empleado.getRfc();
                empleadoglobal = empleado.getEmpleado();
                p01sinplan = (double) empleado.getP_01();
                p01asinplan = (double) empleado.getP_01A();
                p01bsinplan = (double) empleado.getP_01B();
                p01csinplan = (double) empleado.getP_01C();
                p01dsinplan = (double) empleado.getP_01D();
                p01esinplan = (double) empleado.getP_01E();
                p03sinplan = (double) empleado.getP_03();
                p03sinplan = Double.parseDouble(String.format("%.2f", p03sinplan));
                   
                p18esinplan = (double)empleado.getP_18();
                p19esinplan = (double)empleado.getP_19();
                p14esinplan = empleado.getP14sinplanNeto();
                d01esinplan = empleado.getD01sinplanNeto();
          
                salariobaseglobal = empleado.getSalarioBase();
                percepcionessinplan = empleado.getPercepcionesEmpleado();
                deduccionessinplan = empleado.getDeduccionesEmpleado();
                percepcionesnocalculosinplan = empleado.getPercepcionesSinCalculoEmpleado();
                percepcionesnocalculosinplan = Double.parseDouble(String.format("%.2f", percepcionesnocalculosinplan));
                 deduccionesnocalculosinplan = empleado.getDeduccionesSinCalculoEmpleado();
                 netosinplan = empleado.getNetoEmpleado();
                mod.setRfc(rfcprueba);
                
                
                EmpleadoRfc = consultaReporte.procesarDatosNotaTecnica(reporte, mod);
                Iterator itRfc = EmpleadoRfc.iterator();
                while (itRfc.hasNext()) {
                Object objetoRfc = itRfc.next();
                Empleado empleadoRfc = (Empleado) objetoRfc;
             
                    sumap01sinplan = (double) empleadoRfc.getSumap1();
                    sumap01asinplan = (double) empleadoRfc.getSumap1a();
                    sumap01bsinplan = (double) empleadoRfc.getSumap1b();
                    sumap01csinplan = (double) empleadoRfc.getSumap1c();
                    sumap01dsinplan = (double) empleadoRfc.getSumap1d();
                    sumap01esinplan = (double) empleadoRfc.getSumap1e();
                    sumap18esinplan = (double) empleadoRfc.getSumap18();
                    sumap19esinplan = (double) empleadoRfc.getSumap19();
                    sumap03sinplan = (double) empleadoRfc.getAntiguedadsuma();
                    numerodeplazasglobal = empleadoRfc.getNumerodeplazas();
                
                }
                ////////Obtener Datos De salario Minimo
                int periodosalario = 2018;
                minimo.setYear(periodosalario);
                if (modC.salariominimo(minimo)) {
                    salariominimoglobal = Double.parseDouble(minimo.getZonaa());
                    UnSalarioIsssteGlobal = salariominimoglobal * 15;
                    DiezSalariosIsssteGlobal = UnSalarioIsssteGlobal * 10;
                    SieteSalariosIsssteGlobal = UnSalarioIsssteGlobal * 7;
                }

                
                //////////////////Fin de datos Salario Minimo;
                //salariobaseglobal = empleado.getSalarioBase();

                if (numerodeplazasglobal > 1) {
                    
                salariobaseglobal = sumap01sinplan + sumap01asinplan + sumap01bsinplan + sumap01csinplan + sumap01dsinplan + sumap01esinplan;
                p18conplan = sumap18esinplan;
                p19conplan = sumap19esinplan;
                SumaAntiguedadglobal = sumap03sinplan;
                    if (modC.empleadoganamasnota(mod)) {

                        EmpleadoGanamasGlobal = mod.getNumerodeempleadoganamas();
                        mod.setNumerodeempleadoganamas(EmpleadoGanamasGlobal);
                    } else {

                    }

                } else {

                    EmpleadoUnaPlazaGlobal = empleadoglobal;
                    mod.setNumerodeempleadoganamas(EmpleadoUnaPlazaGlobal);
                    p18conplan = p18esinplan;
                    p19conplan = p19esinplan;
                    SumaAntiguedadglobal = p03sinplan;

                }

                if (salariobaseglobal < UnSalarioIsssteGlobal) {

                    salarioconplan = salariobaseglobal;
                    p18conplan = p18conplan;
                    p19conplan = p19conplan;
                    
                 
                 
                } else {
                    if (modC.cuotasissste(cuotas)) {
                        factor1 = Double.parseDouble(cuotas.getFactor());
                        factor2 = Double.parseDouble(cuotas.getFactor2());
                    }

                    salarioconplanentrefactor = salariobaseglobal / factor1;
                    p18conplan = p18conplan / factor1;
                    p19conplan = p19conplan / factor1;
                    p01sinplan = p01sinplan/factor1;
                    p01asinplan = p01asinplan/factor1;
                    p01bsinplan = p01bsinplan/factor1;
                    p01csinplan = p01csinplan/factor1;
                    p01dsinplan = p01dsinplan/factor1;
                    p01esinplan = p01esinplan/factor1;
                    p18esinplan =  p18esinplan/factor1;
                    p19esinplan =  p19esinplan/factor1;
                    
                    banderafactor = 1;

                }

                if (salarioconplanentrefactor < UnSalarioIsssteGlobal) {
                    salarioconplan = UnSalarioIsssteGlobal;
                    banderafactormenorasalario = 1;
                } else {
                    
                    salarioconplan = salarioconplanentrefactor;
                }

                SalarioIsrGlobal = (salarioconplan + p18conplan + p19conplan) * 2;

                isrtabla.setCalculoIsr(SalarioIsrGlobal);
                if (modC.calculoisr(isrtabla)) {
                    isrtabla.setCalculoIsr(SalarioIsrGlobal);
                    isrtabla.setIntermedio(isrtabla.getIntermedio());
                    if (modC.calculosubsidio(isrtabla)) {
                        IsrCalculoGlobal = isrtabla.getRCalculoIsr();
                        IsrCalculoGlobalprueba = isrtabla.getRCalculoIsr();
                        if (IsrCalculoGlobal < 0) {
                            IsrCalculoGlobal = IsrCalculoGlobal * (-1);
                            mod.setD_01masdeunaplaza(0.00);
                            mod.setP_14masdeunaplaza(IsrCalculoGlobal);

                        } else {
                            mod.setD_01masdeunaplaza(IsrCalculoGlobal);
                            mod.setP_14masdeunaplaza(0.00);
                        }

                    }

                }

                //////de aqui se suman los datos y se agrupan
                baseissste = salarioconplan + SumaAntiguedadglobal;
                if (baseissste > DiezSalariosIsssteGlobal) {
                    baseissste = DiezSalariosIsssteGlobal;

                }
                if (baseissste < UnSalarioIsssteGlobal) {
                    baseissste = UnSalarioIsssteGlobal;
                }

                if (modC.cuotasissste(cuotas)) {
                    D_03Global = Double.parseDouble(cuotas.getD_03());
                    D_04Global = Double.parseDouble(cuotas.getD_04());
                    D_21Global = Double.parseDouble(cuotas.getD_21());
                    D_22Global = Double.parseDouble(cuotas.getD_22());
                    D_23Global = Double.parseDouble(cuotas.getD_23());
                    D_03RGlobal = baseissste * D_03Global;
                    D_04RGlobal = baseissste * D_04Global;
                    D_21RGlobal = baseissste * D_21Global;
                    D_22RGlobal = baseissste * D_22Global;
                    D_23RGlobal = baseissste * D_23Global;
                    D_03RGlobal = Double.parseDouble(String.format("%.2f", D_03RGlobal));
                    D_04RGlobal = Double.parseDouble(String.format("%.2f", D_04RGlobal));
                    D_21RGlobal = Double.parseDouble(String.format("%.2f", D_21RGlobal));
                    D_22RGlobal = Double.parseDouble(String.format("%.2f", D_22RGlobal));
                    D_23RGlobal = Double.parseDouble(String.format("%.2f", D_23RGlobal));
                    mod.setD_03masdeunaplaza(D_03RGlobal);
                    mod.setD_04masdeunaplaza(D_04RGlobal);
                    mod.setD_21masdeunaplaza(D_21RGlobal);
                    mod.setD_22masdeunaplaza(D_22RGlobal);
                    mod.setD_23masdeunaplaza(D_23RGlobal);
                }

                
              
                netoconplan = (salarioconplan + p18conplan + p19conplan + mod.getP_14masdeunaplaza() + percepcionesnocalculosinplan+ sumap03sinplan) - (mod.getD_01masdeunaplaza() + D_03RGlobal + D_04RGlobal + D_21RGlobal + +D_22RGlobal + D_23RGlobal+deduccionesnocalculosinplan);
                diferencianetos = netosinplan - netoconplan;
                percepcionesmasprevision = salarioconplan + p18conplan + p19conplan + mod.getP_14masdeunaplaza() + diferencianetos +
                percepcionesnocalculosinplan + SumaAntiguedadglobal ;
                
                if(percepcionesmasprevision < SieteSalariosIsssteGlobal){
                  previsionexenta = diferencianetos;
                  previsiongravada = 0.0;
                }
                
                else{
                salarioporfactor2 = UnSalarioIsssteGlobal * factor2;
                previsionexenta = salarioporfactor2;
                previsiongravada = diferencianetos - salarioporfactor2;
                 if(UnSalarioIsssteGlobal > diferencianetos){
                 previsionexenta = diferencianetos;
                 previsiongravada = 0.0;
                 
                 }
                percepcionesmasprevisionexenta = salarioconplan + p18conplan + p19conplan + mod.getP_14masdeunaplaza()+
                percepcionesnocalculosinplan + SumaAntiguedadglobal + previsionexenta;
                 percepcionesmasprevisionexenta = Double.parseDouble(String.format("%.2f", percepcionesmasprevisionexenta));
                 
                percepcionesconplan = salarioconplan + p18conplan + p19conplan + mod.getP_14masdeunaplaza() + SumaAntiguedadglobal +
                        percepcionesnocalculosinplan;
                 if(percepcionesmasprevisionexenta < SieteSalariosIsssteGlobal){
                 previsionexenta = SieteSalariosIsssteGlobal - percepcionesconplan;
                 previsiongravada = diferencianetos - previsionexenta;
                 }
                 
                 
                 
                 
                }
                
                SalarioIsrPrevision = (salarioconplan + p18conplan + p19conplan + previsiongravada)*2;
                isrtabla.setCalculoIsr(SalarioIsrPrevision);
                if (modC.calculoisr(isrtabla)) {

                    isrtabla.setCalculoIsr(SalarioIsrGlobal);
                    isrtabla.setIntermedio(isrtabla.getIntermedio());
                    if (modC.calculosubsidio(isrtabla)) {
                        IsrCalculoGlobal = isrtabla.getRCalculoIsr();
                        if (IsrCalculoGlobal < 0) {
                            IsrCalculoGlobal = IsrCalculoGlobal * (-1);
                            mod.setD_01masdeunaplaza(0.00);
                            d01calculo = mod.getD_01masdeunaplaza();
                            mod.setP_14masdeunaplaza(IsrCalculoGlobal);
                            subsidiofinal = (double) mod.getP_14masdeunaplaza();
                        } else {
                            mod.setD_01masdeunaplaza(IsrCalculoGlobal);
                            d01calculo = mod.getD_01masdeunaplaza();
                            mod.setP_14masdeunaplaza(0.00);
                            subsidiofinal = (double) mod.getP_14masdeunaplaza();
                        }

                    }

                }
               
             sumapercepcionessinplanfinal = p01sinplan + p01asinplan +p01bsinplan + p01csinplan +p01dsinplan + p01esinplan + p18esinplan + p19esinplan;
                
              netofinal = (sumapercepcionessinplanfinal+ mod.getP_14masdeunaplaza() + percepcionesnocalculosinplan + p03sinplan+ previsiongravada) - (mod.getD_01masdeunaplaza() + D_03RGlobal + D_04RGlobal + D_21RGlobal + +D_22RGlobal + D_23RGlobal+deduccionesnocalculosinplan);
              netofinal = Double.parseDouble(String.format("%.2f", netofinal));
               
              previsiontotalexentafinal = netosinplan - netofinal;
              
              if(previsiontotalexentafinal < 0){
              
              previsiontotalexentafinal = previsiontotalexentafinal * (-1);
              
              if(previsiongravada <= 0){
              
                 previsiontotalexentafinalsubsidio = previsiontotalexentafinal;
                 subsidiofinal = subsidiofinal - previsiontotalexentafinal;
                 previsiontotalexentafinal = 0.0;
              }
              
              if(subsidiofinal < 0){
                subsidiofinal = subsidiofinal + previsiontotalexentafinal;
               
              }
              
              comparanetosfinales = (netofinal + previsiontotalexentafinal) - netosinplan;
              }
              
              if(diferencianetos == 0)
              {
              previsiongravada = 0.0;
              previsiontotalexentafinal = 0.0;
              }    
              
              
                previsiongravada = Double.parseDouble(String.format("%.2f", previsiongravada));
                previsiontotalexentafinal = Double.parseDouble(String.format("%.2f", previsiontotalexentafinal));
                percepcionesconplan = Double.parseDouble(String.format("%.2f", percepcionesconplan));
                if (empleadoglobal == mod.getNumerodeempleadoganamas()) 
                {
                     d01calculo = Double.parseDouble(String.format("%.2f", d01calculo));
                     subsidiofinal = Double.parseDouble(String.format("%.2f", subsidiofinal));
                     //+ percepcionesconplan
                percepcionesparaequilibrar = subsidiofinal + p03sinplan + p01sinplan + p01asinplan+p01bsinplan+p01csinplan+p01dsinplan+percepcionesnocalculosinplan  + previsiongravada + previsiontotalexentafinal + p18esinplan+ p19esinplan;
                deduccionesparaequilibrar =  d01calculo + D_03RGlobal + D_04RGlobal + D_21RGlobal + +D_22RGlobal + D_23RGlobal + deduccionesnocalculosinplan;
                percepcionesparaequilibrar = Double.parseDouble(String.format("%.2f", percepcionesparaequilibrar));
                deduccionesparaequilibrar = Double.parseDouble(String.format("%.2f", deduccionesparaequilibrar));
              
                   
                } else{
                
                percepcionesparaequilibrar =  p01sinplan + p03sinplan + p01asinplan+p01bsinplan+p01csinplan+p01dsinplan+percepcionesnocalculosinplan  + previsiongravada + previsiontotalexentafinal + p18esinplan+ p19esinplan;
                deduccionesparaequilibrar =   deduccionesnocalculosinplan;
                 percepcionesparaequilibrar = Double.parseDouble(String.format("%.2f", percepcionesparaequilibrar));
                deduccionesparaequilibrar = Double.parseDouble(String.format("%.2f", deduccionesparaequilibrar));
                 
                   
                }
                
                
                
                
               
               previsiontotalexentafinal = Double.parseDouble(String.format("%.2f", previsiontotalexentafinal));
               p01sinplan = Double.parseDouble(String.format("%.2f", p01sinplan));
               p01asinplan = Double.parseDouble(String.format("%.2f", p01asinplan));
               p01bsinplan = Double.parseDouble(String.format("%.2f", p01bsinplan));
               p01csinplan = Double.parseDouble(String.format("%.2f", p01csinplan));
               p01dsinplan = Double.parseDouble(String.format("%.2f", p01dsinplan));
               previsiongravada = Double.parseDouble(String.format("%.2f", previsiongravada));
               subsidiofinal = Double.parseDouble(String.format("%.2f", subsidiofinal));
               d01calculo = Double.parseDouble(String.format("%.2f", d01calculo));
               p18esinplan = Double.parseDouble(String.format("%.2f", p18esinplan));
               p19esinplan = Double.parseDouble(String.format("%.2f", p19esinplan));
               
              
               //float p01update = (float) p01sinplan;
             
               mod.setP01Update(p01sinplan);
               mod.setP01AUpdate(p01asinplan);
               mod.setP01BUpdate(p01bsinplan);
               mod.setP01CUpdate(p01csinplan);
               mod.setP01DUpdate(p01dsinplan);
               mod.setP01EUpdate(0.0);
             
               mod.setPPEUpdate(previsiontotalexentafinal);
               mod.setPPGUpdate(previsiongravada);
               mod.setP018Update(p18esinplan);
               mod.setP019Update(p19esinplan);
               mod.setEmpleado(empleadoglobal);
                if (empleadoglobal == mod.getNumerodeempleadoganamas()) {

                    mod.setP14Update(subsidiofinal);
                    mod.setD01Update(d01calculo);
                      
              

                } else {
                    mod.setP14Update(0.0);
                    mod.setD01Update(0.0);
                    mod.setD_03masdeunaplaza(0.0);
                    mod.setD_04masdeunaplaza(0.0);
                    mod.setD_21masdeunaplaza(0.0);
                    mod.setD_22masdeunaplaza(0.0);
                    mod.setD_23masdeunaplaza(0.0);
       
                }
                   /* p01sinplan = p01sinplan/factor1;
                    p01asinplan = p01asinplan/factor1;
                    p01bsinplan = p01bsinplan/factor1;
                    p01csinplan = p01csinplan/factor1;
                    p01dsinplan = p01dsinplan/factor1;
                    p01esinplan = p01esinplan/factor1; 
                    previsiongravada
                    subsidiofinal
                    d01calculo
                   
               */
               
              //previsiongravada = 
           if (modC.actualizarempleadoganamasnota(mod)) {
               
                
                
                /*if (modC.actualizarotrosempleados(mod)) {
                } else {

                }*/
                
                
            } else {
            }
           
              
              
              if(modC.calcularnetonota(mod)){
              
                  netoparacomprobar = mod.getNetoEmpleado();
              }
               netoparaequilibrar =  percepcionesparaequilibrar - deduccionesparaequilibrar;
               netoparaequilibrar = Double.parseDouble(String.format("%.2f", netoparaequilibrar));
               diferencianetoparaequilibrar = netosinplan - netoparacomprobar;
               //diferencianetoparaequilibrar = netosinplan - netoparaequilibrar;
               diferencianetoparaequilibrar = Double.parseDouble(String.format("%.2f", diferencianetoparaequilibrar));
                
               if(diferencianetoparaequilibrar > 0){
               
                   previsiontotalexentafinal = previsiontotalexentafinal + diferencianetoparaequilibrar;
                   previsiontotalexentafinal = Double.parseDouble(String.format("%.2f", previsiontotalexentafinal));
             
                   mod.setPPEUpdate(previsiontotalexentafinal);
                   if(modC.actualizarprevision(mod)){
                   
                   }else{
                   }
                   
               }else
               {
                   previsiontotalexentafinal = previsiontotalexentafinal + diferencianetoparaequilibrar;
                   previsiontotalexentafinal = Double.parseDouble(String.format("%.2f", previsiontotalexentafinal));
             
                   mod.setPPEUpdate(previsiontotalexentafinal);
                    if(modC.actualizarprevision(mod)){
                   
                   }else{
                   }
               }
            }//////////////termina while rs.hastnext

            
            //////////////////7
           }else
            {    
             JOptionPane.showMessageDialog(null, "La Qna Ya Esta Calculada");
            }
        }////////////////FIN DE CALCULAR NOTA
        
        //</editor-fold> 
     /////
     
    //<editor-fold desc="genera proceso Exportar Netos">
   
     /////////////////////////////////////   
     if (e.getSource() == frmPersona.btnExportarNetos) {
       
          reporte.setPeriodo(String.valueOf(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem())) );
          consultaReporte.exportarnetos(reporte);
               // JOptionPane.showMessageDialog(null, "Enviar Reporte");
           
      
     }
     //</editor-fold> 
     /////
     
     //<editor-fold desc="genera proceso Exportar Hojai">
   
     /////////////////////////////////////   
     if (e.getSource() == frmPersona.btnExportarHojaiCP) {
       
          reporte.setPeriodo(String.valueOf(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem())) );
          consultaReporte.exportarinformehojai(reporte);
               // JOptionPane.showMessageDialog(null, "Enviar Reporte");
           
      
     }
     //</editor-fold> 
     /////
     
     //<editor-fold desc="genera proceso Exportar EstadoDeOrigen">
   
     /////////////////////////////////////   
     if (e.getSource() == frmPersona.btnEstadosDeOrigen) {
       
          reporte.setPeriodo(String.valueOf(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem())) );
          consultaReporte.exportarinformeEstadoDeOrigen(reporte);
               // JOptionPane.showMessageDialog(null, "Enviar Reporte");
           
      
     }
     //</editor-fold> 
     /////
     
     //<editor-fold desc="genera proceso Exportar Hojai Remoto">
   
     /////////////////////////////////////   
     if (e.getSource() == frmPersona.btnExportarRemoto) {
       
          reporte.setPeriodo(String.valueOf(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem())) );
          consultaReporte.procesarnuevanominanotaRemoto(reporte);
              
      
     }
     //</editor-fold> 
     /////
     
      //<editor-fold desc="genera proceso ExportarFonsar">
   
     /////////////////////////////////////   
     if (e.getSource() == frmPersona.btnExportarFonsar) {
       
          reporte.setPeriodo(String.valueOf(String.valueOf(frmPersona.cbperiodo2.getSelectedItem()) + String.valueOf(frmPersona.cbNumero2.getSelectedItem())) );
          consultaReporte.procesarQnaFonsar(reporte);
      
     }
     //</editor-fold> 
     /////
     
    }
    
   
    
    public void dibujartablapension(){
    DefaultTableModel modeloinsertar = (DefaultTableModel) frmPersona.JTPension.getModel();
                 modeloinsertar.setRowCount(0);
                 ArrayList Pension = new ArrayList();
                 Pension = modC.buscarbeneficiariopension(pension);

                 Object[] filainsertar = new Object[modeloinsertar.getColumnCount()];

                 Iterator it = Pension.iterator();
                 while (it.hasNext()) {

                     Object objeto = it.next();
                     Pension pension = (Pension) objeto;
                     //System.out.println(benef.getNombre());
                     filainsertar[0] = pension.getBeneficiario();
                     filainsertar[1] = pension.getPorcentaje();
                     filainsertar[2] = pension.getOficio();
                     filainsertar[3] = pension.getMontodescuento();
                     filainsertar[4] = pension.getStatus();
                     filainsertar[5] = pension.getSecuencia();
                     filainsertar[6] = pension.getIdPension();
                     modeloinsertar.addRow(filainsertar);
                            

                        }
    
    }
    
    public void dibujartablaprestamo(){
    DefaultTableModel modeloinsertar = (DefaultTableModel) frmPersona.JTPrestamos.getModel();
                 modeloinsertar.setRowCount(0);
                 ArrayList Prestamos = new ArrayList();
                 Prestamos = modC.buscarbeneficiarioprestamos(prestamo);

                 Object[] filainsertar = new Object[modeloinsertar.getColumnCount()];

                 Iterator it = Prestamos.iterator();
                 while (it.hasNext()) {

                     Object objeto = it.next();
                     Prestamos prestamo = (Prestamos) objeto;
                     //System.out.println(benef.getNombre());
                     filainsertar[0] = prestamo.getClaveDescuento();
                     filainsertar[1] = prestamo.getFolio();
                     filainsertar[2] = prestamo.getMonto();
                     filainsertar[3] = prestamo.getInteres();
                     filainsertar[4] = prestamo.getPlazo();
                     filainsertar[5] = prestamo.getTotal();
                     filainsertar[6] = prestamo.getDescuento();
                     filainsertar[7] = prestamo.getSaldo();
                     filainsertar[8] = prestamo.getNumeroquincenaInicio();
                     filainsertar[9] = prestamo.getStatus();
                     filainsertar[10] = prestamo.getPrioridad();
                     filainsertar[11] = prestamo.getIdPrestamo();
                     
                     
                     
                     modeloinsertar.addRow(filainsertar);
                            

                       }
    
    }
    
}
