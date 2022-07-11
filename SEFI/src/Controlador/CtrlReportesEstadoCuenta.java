/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasInformesPersonas;
import Modelo.ConsultasMovimientosAhorro;
import Modelo.InformePersona;
import Modelo.MovimientosAhorro;
import View.frmEstadoCuenta;
import View.frmMovAhorros;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class CtrlReportesEstadoCuenta implements ActionListener {

    private InformePersona mod;
    private ConsultasInformesPersonas modC;
    private frmEstadoCuenta frmEstadoCuenta;

    public CtrlReportesEstadoCuenta(InformePersona mod, ConsultasInformesPersonas modC, frmEstadoCuenta frmEstadoCuenta) {

        this.mod = mod;
        this.modC = modC;
        this.frmEstadoCuenta = frmEstadoCuenta;

        this.frmEstadoCuenta.btnEdoCuentaAhorro.addActionListener(this);
        this.frmEstadoCuenta.btnActualizarSaldo.addActionListener(this);
        this.frmEstadoCuenta.btnEdoCuentaDesglo.addActionListener(this);
        this.frmEstadoCuenta.btnEdoCuentaPrestamos.addActionListener(this);
        this.frmEstadoCuenta.btnHojaNoAdeudo.addActionListener(this);
        this.frmEstadoCuenta.btnPagoAnticipado.addActionListener(this);
        this.frmEstadoCuenta.btnEdoCuentaFias.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmEstadoCuenta.btnEdoCuentaFias) {

            if (frmEstadoCuenta.txtNombre.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Por favor Introduce el NOMBRE");

            } else {

                mod.setNombre(frmEstadoCuenta.txtNombre.getText());

                modC.imprimeedocuentainversion(mod);
                //JOptionPane.showMessageDialog(null, "RFC No Registrado");

                //modC.imprimereporteahorro(mod);
            }
        }

        if (e.getSource() == frmEstadoCuenta.btnEdoCuentaAhorro) {

            if (frmEstadoCuenta.txtRfc.getText().isEmpty() && frmEstadoCuenta.txtNombre.getText().isEmpty() ) {

                JOptionPane.showMessageDialog(null, "Por favor Introduce el RFC o el Nombre");

            } else {
                mod.setRfc(frmEstadoCuenta.txtRfc.getText());
                mod.setPeriodo(frmEstadoCuenta.txtperiodo.getText());
                mod.setNombre(frmEstadoCuenta.txtNombre.getText());
                // JOptionPane.showMessageDialog(null, "Boton Apretado");
                modC.imprimeedocuentaahorro(mod);
                //JOptionPane.showMessageDialog(null, "RFC No Registrado");

                //modC.imprimereporteahorro(mod);
            }
        }

        if (e.getSource() == frmEstadoCuenta.btnEdoCuentaPrestamos) {

            if (frmEstadoCuenta.txtRfc.getText().isEmpty() && frmEstadoCuenta.txtNombre.getText().isEmpty() ) {

                JOptionPane.showMessageDialog(null, "Por favor Introduce el RFC o el nombre");

            } else {
                mod.setRfc(frmEstadoCuenta.txtRfc.getText());
                 mod.setNombre(frmEstadoCuenta.txtNombre.getText());
                //mod.setRfc(frmEstadoCuenta.txtRfc.getText());
                modC.imprimeedocuentaprestamo(mod);
            }
        }

        if (e.getSource() == frmEstadoCuenta.btnHojaNoAdeudo) {

            if (frmEstadoCuenta.txtRfc.getText().isEmpty() && frmEstadoCuenta.txtNombre.getText().isEmpty() ) {

                JOptionPane.showMessageDialog(null, "Por favor Introduce el RFC o Nombre");

            } else {
                mod.setRfc(frmEstadoCuenta.txtRfc.getText());
                mod.setNombre(frmEstadoCuenta.txtNombre.getText());
                modC.imprimehojanoadeudo(mod);
            }
        }

        if (e.getSource() == frmEstadoCuenta.btnPagoAnticipado) {

            if (frmEstadoCuenta.txtRfc.getText().isEmpty() && frmEstadoCuenta.txtNombre.getText().isEmpty() ) {

                JOptionPane.showMessageDialog(null, "Por favor Introduce el RFC o Nombre");

            } else {
                mod.setRfc(frmEstadoCuenta.txtRfc.getText());
                mod.setNombre(frmEstadoCuenta.txtNombre.getText());
                modC.imprimepagoanticipado(mod);
            }
        }

    }

}
