/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultaMovimientosXQna;
import Modelo.MovimientosXqna;
import View.frmMovimientosXQna;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author victor
 */
public class CtrlMovimientosXQna implements ActionListener {
    
    private frmMovimientosXQna frmMovimientosXQna;
    private ConsultaMovimientosXQna modC;
    private MovimientosXqna mod;
    public CtrlMovimientosXQna(MovimientosXqna mod,ConsultaMovimientosXQna modC,frmMovimientosXQna frmMovimientosXQna) {
        this.frmMovimientosXQna = frmMovimientosXQna;
        this.modC = modC;
        this.mod = mod;
        this.frmMovimientosXQna.bntEnviar.addActionListener(this);

    }
    
     @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmMovimientosXQna.bntEnviar) {
            
            mod.setPeriodoi(String.valueOf(frmMovimientosXQna.cbperiodoinicial.getSelectedItem()));
            mod.setPeriodof(String.valueOf(frmMovimientosXQna.cbperiodofinal.getSelectedItem()));
            mod.setNumeroi(String.valueOf(frmMovimientosXQna.cbnumeroinicial.getSelectedItem()));
            mod.setNumerof(String.valueOf(frmMovimientosXQna.cbnumerofinal.getSelectedItem()));
            modC.imprimeInformeMovimientos(mod);
        }
    }
    
}
