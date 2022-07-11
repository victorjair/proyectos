/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasReporteTrimestral;
import Modelo.ReporteTrimestral;
import View.frmReporteTrimestral;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author victor
 */
public class CtrlReporteTrimestral implements ActionListener {

    private frmReporteTrimestral frmReporteTrimestral;
    private ConsultasReporteTrimestral modC;
    private ReporteTrimestral mod;

    public CtrlReporteTrimestral(ReporteTrimestral mod, ConsultasReporteTrimestral modC, frmReporteTrimestral frmReporteTrimestral) {
        this.frmReporteTrimestral = frmReporteTrimestral;
        this.modC = modC;
        this.mod = mod;
        this.frmReporteTrimestral.btnReporteTrimestral.addActionListener(this);
        this.frmReporteTrimestral.btnCalcularRendimientos.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmReporteTrimestral.btnReporteTrimestral) {

            mod.setPeriodoi(String.valueOf(frmReporteTrimestral.cbperiodoinicial.getSelectedItem()));
            mod.setNumeroi(String.valueOf(frmReporteTrimestral.cbnumeroinicial.getSelectedItem()));
            modC.imprimereportetrimestral(mod);

        }
        
        if (e.getSource() == frmReporteTrimestral.btnCalcularRendimientos) {

            mod.setPeriodoi(String.valueOf(frmReporteTrimestral.cbperiodoinicial.getSelectedItem()));
            mod.setNumeroi(String.valueOf(frmReporteTrimestral.cbnumeroinicial.getSelectedItem()));
            modC.calcularrendimientos(mod);

        }

    }

}
