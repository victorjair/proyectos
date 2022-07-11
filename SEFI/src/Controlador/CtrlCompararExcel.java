/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CompararExcel;
import Modelo.ConsultasCompararExcel;
import View.frmComparativos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victor
 */
public class CtrlCompararExcel implements ActionListener {
    
    private CompararExcel mod;
    private ConsultasCompararExcel modC;
    private frmComparativos frmComparativos;
    
    public CtrlCompararExcel(CompararExcel mod , ConsultasCompararExcel modC , frmComparativos frmComparativos) {

        this.mod = mod;
        this.modC = modC;
        this.frmComparativos = frmComparativos;

        this.frmComparativos.btnComparar.addActionListener(this);

    }
    
     @Override
    public void actionPerformed(ActionEvent e) {

        mod.setNumeroQuincena(String.valueOf(frmComparativos.cbperiodo.getSelectedItem()) + String.valueOf(frmComparativos.cbnumero.getSelectedItem()));
        mod.setNumero(String.valueOf(frmComparativos.cbnumero.getSelectedItem()));
        mod.setPeriodo(String.valueOf(frmComparativos.cbperiodo.getSelectedItem()));
       
        if (e.getSource() == frmComparativos.btnComparar) {

            if (frmComparativos.cbtipo.getSelectedItem() == "Ahorro") {
                modC.compararAhorroExcel(mod);
            } else {
                modC.compararRecuperacionExcel(mod);

            }

        }

    }
    
    
}
