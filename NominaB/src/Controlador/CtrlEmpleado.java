/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultaEmpleado;
import Modelo.Empleado;
import Vista.MenuNota;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author victor
 */
public class CtrlEmpleado implements ActionListener {
    
    private Empleado mod;
    private ConsultaEmpleado modC;
    private MenuNota frmPersona;
    
    
    public CtrlEmpleado(Empleado mod, ConsultaEmpleado modc, MenuNota frmPersona) {

        this.frmPersona = frmPersona;
        this.mod = mod;
        this.modC = modc;
        this.frmPersona.BtnGuardar.addActionListener(this);
        this.frmPersona.BtnModificar.addActionListener(this);
        this.frmPersona.BtnBaja.addActionListener(this);
        this.frmPersona.BtnNuevo.addActionListener(this);
        this.frmPersona.BtnBuscar.addActionListener(this);
        this.frmPersona.BtnReactivar.addActionListener(this);
        this.frmPersona.BtnGuardarPrestamo.addActionListener(this);
        this.frmPersona.BtnGuardarPension.addActionListener(this);
        this.frmPersona.cbPlantel.addActionListener(this);
       
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == frmPersona.cbPlantel) {
             //JOptionPane.showMessageDialog(null, "cambiaste algo");
            mod.setPlantel(String.valueOf(frmPersona.cbPlantel.getSelectedItem()));
            if(modC.completarcampos(mod)){
                    //limpiar();
                    frmPersona.txtnumeroplantel.setText(mod.getNumeroPlantel());
                    frmPersona.cbTipoPlantel.setSelectedItem(mod.getTPlantel());
                    frmPersona.txtnombreplantel.setText(mod.getNombrePlantel());
                   
            
            
            }else{
            
            JOptionPane.showMessageDialog(null, "No se encontro el plantel");
                  //  limpiar();
            }      
        }
    
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
       
        if (e.getSource() == frmPersona.BtnBuscar) {
          
            if (frmPersona.txtempleado.getText().isEmpty()|| frmPersona.txtnumeroquincena.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Introduce el Numero de Empleado y Quincena");

            } else {
                 // mod.setRfc(frmPersona.txtRfc.getText());
                  mod.setEmpleado(Integer.parseInt(frmPersona.txtempleado.getText()));
                  mod.setNumeroquincena(Integer.parseInt(frmPersona.txtnumeroquincena.getText()));
                     
                if (modC.buscar(mod)) {
                     //frmPersona.txtNombre.setText(mod.getNombre());
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
                   
                  

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro el empleado");
                  //  limpiar();
                }
                

            }

        }
    
    }
    
}

