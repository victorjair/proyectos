/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasExportar;
import Modelo.ExportarExcel;
import View.frmExportar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victor
 */
public class CtrlExportar implements ActionListener {

    private ExportarExcel mod;
    private ConsultasExportar modC;
    private frmExportar frmExportar;

    public CtrlExportar(ExportarExcel mod , ConsultasExportar modC , frmExportar frmExportar) {

        this.mod = mod;
        this.modC = modC;
        this.frmExportar = frmExportar;

        this.frmExportar.btnExportar.addActionListener(this);

    }

    public CtrlExportar() {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        mod.setNumeroQuincena(String.valueOf(frmExportar.cbperiodo.getSelectedItem()) + String.valueOf(frmExportar.cbnumero.getSelectedItem()));
        mod.setNumero(String.valueOf(frmExportar.cbnumero.getSelectedItem()));
        mod.setPeriodo(String.valueOf(frmExportar.cbperiodo.getSelectedItem()));
       
        if (e.getSource() == frmExportar.btnExportar) {

            if (frmExportar.cbtipo.getSelectedItem() == "Ahorro") {
                try {
                    modC.exportarAhorroExcel(mod);
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlExportar.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                modC.exportarRecuperacionExcel(mod);

            }

        }

    }
}
