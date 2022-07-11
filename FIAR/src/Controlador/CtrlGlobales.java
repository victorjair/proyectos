/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasGlobales;
import Modelo.Globales;
import View.frmReportesGlobales;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author victor
 */
public class CtrlGlobales implements ActionListener{
    
    private String movimiento;
    private frmReportesGlobales frmReportesGlobales;
    private ConsultasGlobales modC;
    private Globales mod;

    public CtrlGlobales(Globales mod , ConsultasGlobales modC , frmReportesGlobales frmReportesGlobales ) 
    {
        this.frmReportesGlobales = frmReportesGlobales;
        this.modC = modC;
        this.mod = mod;
        this.frmReportesGlobales.btnPatrimonio.addActionListener(this);
        this.frmReportesGlobales.btnDeudores.addActionListener(this);
       this.frmReportesGlobales.btnInformacionXQna.addActionListener(this);
    
    }
    
        @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmReportesGlobales.btnInformacionXQna) {
        
            mod.setPeriodoi(String.valueOf(frmReportesGlobales.cbperiodoinicial.getSelectedItem()));
            mod.setPeriodof(String.valueOf(frmReportesGlobales.cbperiodofinal.getSelectedItem()));
            mod.setNumeroi(String.valueOf(frmReportesGlobales.cbnumeroinicial.getSelectedItem()));
            mod.setNumerof(String.valueOf(frmReportesGlobales.cbnumerofinal.getSelectedItem()));
             modC.imprimeinformacionxqna(mod);
        
        }
        
        
        if (e.getSource() == frmReportesGlobales.btnPatrimonio) {
            
            mod.setPeriodoi(String.valueOf(frmReportesGlobales.cbperiodoinicial.getSelectedItem()));
            mod.setPeriodof(String.valueOf(frmReportesGlobales.cbperiodofinal.getSelectedItem()));
            mod.setNumeroi(String.valueOf(frmReportesGlobales.cbnumeroinicial.getSelectedItem()));
            mod.setNumerof(String.valueOf(frmReportesGlobales.cbnumerofinal.getSelectedItem()));
             modC.imprimepatrimonio(mod);
           
        }
        
        if (e.getSource() == frmReportesGlobales.btnDeudores) {
            
            mod.setPeriodoi(String.valueOf(frmReportesGlobales.cbperiodoinicial.getSelectedItem()));
            mod.setPeriodof(String.valueOf(frmReportesGlobales.cbperiodofinal.getSelectedItem()));
            mod.setNumeroi(String.valueOf(frmReportesGlobales.cbnumeroinicial.getSelectedItem()));
            mod.setNumerof(String.valueOf(frmReportesGlobales.cbnumerofinal.getSelectedItem()));
             modC.imprimedeudoresxprestamos(mod);
           
        }
    }
    
    
    
    
}
