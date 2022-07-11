/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasReportesMovimientos;
import Modelo.ReporteMovimientos;
import View.frmExportarMovimientos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author victor
 */
public class CtrlReportesMovimientos implements ActionListener{
    
    private String movimiento;
    private frmExportarMovimientos frmExportarMovimientos;
    private ConsultasReportesMovimientos modC;
    private ReporteMovimientos mod;
    public CtrlReportesMovimientos(ReporteMovimientos mod,ConsultasReportesMovimientos modC,frmExportarMovimientos frmExportarMovimientos) {
        this.frmExportarMovimientos = frmExportarMovimientos;
        this.modC = modC;
        this.mod = mod;
        this.frmExportarMovimientos.bntEnviar.addActionListener(this);

    }
    
    
     @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmExportarMovimientos.bntEnviar) {
            
            mod.setPeriodoi(String.valueOf(frmExportarMovimientos.cbperiodoinicial.getSelectedItem()));
            mod.setPeriodof(String.valueOf(frmExportarMovimientos.cbperiodofinal.getSelectedItem()));
            mod.setNumeroi(String.valueOf(frmExportarMovimientos.cbnumeroinicial.getSelectedItem()));
            mod.setNumerof(String.valueOf(frmExportarMovimientos.cbnumerofinal.getSelectedItem()));
            mod.setMovimiento(String.valueOf(frmExportarMovimientos.cbMovimientos.getSelectedItem()));
            if(mod.getMovimiento().equals("B"))
            
            {
             modC.ImprimeMovimientosAfiliados(mod);
            }
            else if(  mod.getMovimiento().equals("DIV")  ) 
            {
                modC.ImprimeMovimientosAhorro(mod);
            }else{
                modC.ImprimeMovimientosRecuperacion(mod);
            }
          
        }
    }
    
}
