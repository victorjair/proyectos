/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasEstadisticas;
import Modelo.Estadisticas;
import View.frmEstadisticas;
import View.frmMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author victor
 */
public class CtrlEstadisticas implements ActionListener {

    private frmEstadisticas frmEstadisticas;
    private ConsultasEstadisticas modC;
    private Estadisticas mod;
    public CtrlEstadisticas(Estadisticas mod,ConsultasEstadisticas modC,frmEstadisticas frmEstadisticas) {
        this.frmEstadisticas = frmEstadisticas;
        this.modC = modC;
        this.mod = mod;
        this.frmEstadisticas.bntEnviar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmEstadisticas.bntEnviar) {
            
            mod.setPeriodoi(String.valueOf(frmEstadisticas.cbperiodoinicial.getSelectedItem()));
            mod.setPeriodof(String.valueOf(frmEstadisticas.cbperiodofinal.getSelectedItem()));
            mod.setNumeroi(String.valueOf(frmEstadisticas.cbnumeroinicial.getSelectedItem()));
            mod.setNumerof(String.valueOf(frmEstadisticas.cbnumerofinal.getSelectedItem()));
            modC.imprimeestadisticas(mod);
        }
    }

}

