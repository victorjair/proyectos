/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultaPension;
import Vista.MenuNotaANt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import Modelo.Pension;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author victor
 */
public class CtrlPension implements ActionListener{
    private MenuNotaANt frmPersona;
    private Pension pension;
    private ConsultaPension consultapension;
    
    public CtrlPension(Pension pension,ConsultaPension consultapension){
        this.pension = pension;
        this.consultapension = consultapension;
     
    }
    public CtrlPension(MenuNotaANt frmPersona, Pension pension,ConsultaPension consultapension) {
        this.frmPersona = frmPersona;
        this.pension = pension;
        this.consultapension = consultapension;
     
       this.frmPersona.BtnGuardarPension.addActionListener(this);
       this.frmPersona.BtnModificarPension.addActionListener(this);
       this.frmPersona.BtnBajaPension.addActionListener(this);
       this.frmPersona.BtnBuscarPension.addActionListener(this);

    }
    
     @Override
    public void actionPerformed(ActionEvent e) {
    
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
           ////////desdeaqui
             if (consultapension.guardarpension(pension)) {

                
                 DefaultTableModel modeloinsertar = (DefaultTableModel) frmPersona.JTPension.getModel();
                 modeloinsertar.setRowCount(0);
                 ArrayList Pension = new ArrayList();
                 Pension = consultapension.buscarbeneficiariopension(pension);

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

                        JOptionPane.showMessageDialog(null, "Beneficiario Guardado");
                       // limpiarbeneficiario();

                    } else {
                        JOptionPane.showMessageDialog(null, "Beneficiario No Guardado Intente Mas Tarde");
                       // limpiarbeneficiario();

                    }////hasta aqui
           }
    
    
    }
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

            } else {}
    
    
    }
    if (e.getSource() == frmPersona.BtnBajaPension) {
    
    
    }
    if (e.getSource() == frmPersona.BtnBuscarPension) {
    
    
    }
    
    
    
    
    }
    
}
