/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasRecuperacionDesglosada;
import Modelo.RecuperacionDesglosada;
import View.frmRecuperacionDesglosada;
import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author victor
 */
public class CtrlRecuperacionDesglosada implements ActionListener{
    
    private String movimiento;
    private frmRecuperacionDesglosada frmRecuperacionDesglosada;
    private ConsultasRecuperacionDesglosada modC;
    private RecuperacionDesglosada mod;
    
    
    public CtrlRecuperacionDesglosada(RecuperacionDesglosada mod,ConsultasRecuperacionDesglosada modC,frmRecuperacionDesglosada frmRecuperacionDesglosada) {
        this.frmRecuperacionDesglosada = frmRecuperacionDesglosada;
        this.modC = modC;
        this.mod = mod;
        this.frmRecuperacionDesglosada.bntDesgloXqna.addActionListener(this);
        this.frmRecuperacionDesglosada.btnDesgloGlobal.addActionListener(this);

    }
    
     @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmRecuperacionDesglosada.bntDesgloXqna) {
            
            mod.setPeriodoi(String.valueOf(frmRecuperacionDesglosada.cbperiodoinicial.getSelectedItem()));
            mod.setPeriodof(String.valueOf(frmRecuperacionDesglosada.cbperiodofinal.getSelectedItem()));
            mod.setNumeroi(String.valueOf(frmRecuperacionDesglosada.cbnumeroinicial.getSelectedItem()));
            mod.setNumerof(String.valueOf(frmRecuperacionDesglosada.cbnumerofinal.getSelectedItem()));
             modC.imprimerecuperaciondesglosadaxqna(mod);
           
        }
        
        if (e.getSource() == frmRecuperacionDesglosada.btnDesgloGlobal) {
            
            mod.setPeriodoi(String.valueOf(frmRecuperacionDesglosada.cbperiodoinicial.getSelectedItem()));
            mod.setPeriodof(String.valueOf(frmRecuperacionDesglosada.cbperiodofinal.getSelectedItem()));
            mod.setNumeroi(String.valueOf(frmRecuperacionDesglosada.cbnumeroinicial.getSelectedItem()));
            mod.setNumerof(String.valueOf(frmRecuperacionDesglosada.cbnumerofinal.getSelectedItem()));
             modC.imprimerecuperaciondesglosadaglobal(mod);
           
        }
    }
    
    
}
