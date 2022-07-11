/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
 
import Modelo.ConsultasExportar;
import Modelo.Reporte;
import Vista.MenuNotaANt;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author victor
 */
public class CtrlExportarReportes implements ActionListener {
    
    private Reporte reporte;
    private ConsultasExportar ConsultaReporte;
    private MenuNotaANt frmExportarInforme;
    
     public CtrlExportarReportes(Reporte reporte , ConsultasExportar ConsultaReporte , MenuNotaANt frmExportarInforme) {

        this.reporte = reporte;
        this.ConsultaReporte = ConsultaReporte;
        this.frmExportarInforme = frmExportarInforme;

        this.frmExportarInforme.BtnExportarReportes.addActionListener(this);
     

    }
     
     @Override
    public void actionPerformed(ActionEvent e) {
        
          if (e.getSource() == frmExportarInforme.BtnExportarReportes) {
              
         reporte.setPeriodo(String.valueOf(frmExportarInforme.cbperiodo.getSelectedItem()) + String.valueOf(frmExportarInforme.cbNumero1.getSelectedItem()));
        reporte.setNombreReporte(String.valueOf(frmExportarInforme.cbNombreInforme.getSelectedItem()));
        reporte.setClaveReporte(String.valueOf(frmExportarInforme.cbConceptoImportar1.getSelectedItem()));
        reporte.setPlantelReporte(String.valueOf(frmExportarInforme.cbPlantelExportar.getSelectedItem()));
        reporte.setTipoPlantelReporte(String.valueOf(frmExportarInforme.cbTipoPlantelExportar.getSelectedItem()));
       
           if (frmExportarInforme.cbConceptoImportar1.getSelectedItem() == "CuotasYAportacionesCP") {
              ConsultaReporte.exportarinformecuotasyaportaciones(reporte);
           }
            /*if (frmExportarInforme.cbConceptoImportar1.getSelectedItem() == "CuotasYAportaciones") {
                try {
                    ConsultaReporte.exportarAhorroExcel(reporte);
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlExportar.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                modC.exportarRecuperacionExcel(mod);

            }*/

        }
        
    }
     

    
    
}
