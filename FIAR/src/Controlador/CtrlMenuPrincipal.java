/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Afiliado;
import Modelo.CompararExcel;
import Modelo.ConsultaMovimientosXQna;
import Modelo.ConsultaPlazoFijo;
import Modelo.ConsultasAfiliados;
import Modelo.ConsultasCompararExcel;
import Modelo.ConsultasEstadisticas;
import Modelo.ConsultasExportar;
import Modelo.ConsultasGlobales;
import Modelo.ConsultasImportarAhorro;
import Modelo.ConsultasInformesPersonas;
import Modelo.ConsultasMovimientos;
import Modelo.ConsultasMovimientosAhorro;
import Modelo.ConsultasPrestamos;
import Modelo.ConsultasRecuperacionDesglosada;
import Modelo.ConsultasReporteTrimestral;
import Modelo.ConsultasReportesMovimientos;
import Modelo.ConsultasTasas;
import Modelo.ConsultasUsuario;
import Modelo.Estadisticas;
import Modelo.ExportarExcel;
import Modelo.Globales;
import Modelo.ImportarAhorro;
import Modelo.InformePersona;
import Modelo.Movimientos;
import Modelo.MovimientosAhorro;
import Modelo.MovimientosXqna;
import Modelo.PlazoFijo;
import Modelo.Prestamo;
import Modelo.RecuperacionDesglosada;
import Modelo.ReporteMovimientos;
import Modelo.ReporteTrimestral;
import Modelo.Tasas;
import Modelo.Usuario;
import View.frmAfiliado;
import View.frmComparativos;
import View.frmEstadisticas;
import View.frmEstadoCuenta;
import View.frmExportar;
import View.frmExportarMovimientos;
import View.frmImportarAhorros;
import View.frmLogin;
import View.frmMenuPrincipal;
import View.frmMovAhorros;
import View.frmMovimientos;
import View.frmMovimientosXQna;
import View.frmPlazoFijo;
import View.frmPrestamos;
import View.frmRecuperacionDesglosada;
import View.frmReporteTrimestral;
import View.frmReportesGlobales;
import View.frmTasas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class CtrlMenuPrincipal implements ActionListener {

    private frmMenuPrincipal MenuPrincipal;
    private frmAfiliado myInstanceAfiliado = null;
    private frmPrestamos frmPrestamos = null;
    private frmMovimientos frmMovimientos = null;
    private frmMovAhorros frmMovAhorros = null;
    private frmImportarAhorros frmImportarAhorros = null;
    private frmExportar frmExportar = null;
    private frmComparativos frmComparativos = null;
    private frmEstadoCuenta frmEstadoCuenta = null;
    private frmEstadisticas frmEstadisticas = null;
    private frmReporteTrimestral frmReporteTrimestral = null;
    private frmRecuperacionDesglosada frmRecuperacionDesglosada = null;
    private frmExportarMovimientos frmExportarMovimientos = null;
    private frmReportesGlobales frmReportesGlobales = null;
    private frmPlazoFijo frmPlazoFijo = null;
    private frmTasas frmTasas = null;
    private frmMovimientosXQna frmMovimientosXQna = null;

    public CtrlMenuPrincipal(frmMenuPrincipal MenuPrincipal) {
        this.MenuPrincipal = MenuPrincipal;
        //MenuPrincipal.jMenuBar2.add
        MenuPrincipal.MenuAfiliacion.addActionListener(this);
        MenuPrincipal.MenuIAfiliado.addActionListener(this);
        MenuPrincipal.MenuIPrestamos.addActionListener(this);
        MenuPrincipal.MenuMovPrestamos.addActionListener(this);
        MenuPrincipal.MenuMovAhorros.addActionListener(this);
        MenuPrincipal.MenuCerrar.addActionListener(this);

        MenuPrincipal.MenuImportarI.addActionListener(this);
        MenuPrincipal.MenuExportar.addActionListener(this);
        MenuPrincipal.MenuComparar.addActionListener(this);
        MenuPrincipal.MenuEdosCuenta.addActionListener(this);
        MenuPrincipal.MenuEstadisticas.addActionListener(this);
        MenuPrincipal.MenuTrimestral.addActionListener(this);
        MenuPrincipal.MenuDesglosada.addActionListener(this);
        MenuPrincipal.MenuReporteMov.addActionListener(this);
        MenuPrincipal.MenuGlobales.addActionListener(this);
        MenuPrincipal.MenuPlazoFijo.addActionListener(this);
        MenuPrincipal.MenuTasas.addActionListener(this);
        MenuPrincipal.MenuMovimientosxQna.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == MenuPrincipal.MenuCerrar) {
            MenuPrincipal.dispose();
            Usuario usuarioPersona = new Usuario();
            ConsultasUsuario modC = new ConsultasUsuario();
            frmLogin frmLogin = new frmLogin();
            CtrlUsuario ctrl = new CtrlUsuario(usuarioPersona, modC, frmLogin);
            frmLogin.setVisible(true);
        }

        if (e.getSource() == MenuPrincipal.MenuIAfiliado) {
            //JOptionPane.showMessageDialog(null, "seleccionaste MenuAfiliacion");
            if (myInstanceAfiliado == null || myInstanceAfiliado.isClosed()) {
                myInstanceAfiliado = new frmAfiliado();
                Afiliado mod = new Afiliado();
                ConsultasAfiliados modC = new ConsultasAfiliados();
                CtrlAfiliado ctrl = new CtrlAfiliado(mod, modC, myInstanceAfiliado);
                modC.autocompletar(myInstanceAfiliado);
                MenuPrincipal.Escritorio.add(myInstanceAfiliado);
                myInstanceAfiliado.setVisible(true);
            } else {

                myInstanceAfiliado.setVisible(true);
            }

        }

        if (e.getSource() == MenuPrincipal.MenuIPrestamos) {
            if (frmPrestamos == null || frmPrestamos.isClosed()) {
                frmPrestamos = new frmPrestamos();
                Prestamo mod = new Prestamo();
                ConsultasPrestamos modC = new ConsultasPrestamos();
                CtrlPrestamo ctrl = new CtrlPrestamo(mod, modC, frmPrestamos);
                modC.autocompletar(frmPrestamos);
                MenuPrincipal.Escritorio.add(frmPrestamos);
                frmPrestamos.setVisible(true);

            } else {
                frmPrestamos.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuMovPrestamos) {
            if (frmMovimientos == null || frmMovimientos.isClosed()) {
                frmMovimientos = new frmMovimientos();
                Movimientos mod = new Movimientos();
                ConsultasMovimientos modC = new ConsultasMovimientos();
                CtrlMovimientos ctrl = new CtrlMovimientos(mod, modC, frmMovimientos);
                MenuPrincipal.Escritorio.add(frmMovimientos);
                frmMovimientos.setVisible(true);

            } else {
                frmMovimientos.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuMovAhorros) {
            if (frmMovAhorros == null || frmMovAhorros.isClosed()) {
                frmMovAhorros = new frmMovAhorros();
                MovimientosAhorro mod = new MovimientosAhorro();
                ConsultasMovimientosAhorro modC = new ConsultasMovimientosAhorro();
                CtrlMovimientosAhorro ctrl = new CtrlMovimientosAhorro(mod, modC, frmMovAhorros);
                modC.autocompletar(frmMovAhorros);
                MenuPrincipal.Escritorio.add(frmMovAhorros);
                frmMovAhorros.setVisible(true);

            } else {
                frmMovAhorros.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuImportarI) {
            if (frmImportarAhorros == null || frmImportarAhorros.isClosed()) {
                frmImportarAhorros = new frmImportarAhorros();
                ImportarAhorro mod = new ImportarAhorro();
                ConsultasImportarAhorro modC = new ConsultasImportarAhorro();
                CtrlImportarAhorro ctrl = new CtrlImportarAhorro(mod, modC, frmImportarAhorros);
                MenuPrincipal.Escritorio.add(frmImportarAhorros);
                frmImportarAhorros.setVisible(true);

            } else {
                frmImportarAhorros.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuExportar) {
            if (frmExportar == null || frmExportar.isClosed()) {
                frmExportar = new frmExportar();
                ExportarExcel mod = new ExportarExcel();
                ConsultasExportar modC = new ConsultasExportar();
                CtrlExportar ctrl = new CtrlExportar(mod, modC, frmExportar);
                MenuPrincipal.Escritorio.add(frmExportar);
                frmExportar.setVisible(true);

            } else {
                frmExportar.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuComparar) {
            if (frmComparativos == null || frmComparativos.isClosed()) {
                frmComparativos = new frmComparativos();
                CompararExcel mod = new CompararExcel();
                ConsultasCompararExcel modC = new ConsultasCompararExcel();
                CtrlCompararExcel ctrl = new CtrlCompararExcel(mod, modC, frmComparativos);
                MenuPrincipal.Escritorio.add(frmComparativos);
                frmComparativos.setVisible(true);

            } else {
                frmComparativos.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuEdosCuenta) {
            if (frmEstadoCuenta == null || frmEstadoCuenta.isClosed()) {
                frmEstadoCuenta = new frmEstadoCuenta();
                InformePersona mod = new InformePersona();
                ConsultasInformesPersonas modC = new ConsultasInformesPersonas();
                CtrlReportesEstadoCuenta ctrl = new CtrlReportesEstadoCuenta(mod, modC, frmEstadoCuenta);
                modC.autocompletar(frmEstadoCuenta);
                modC.autocompletarnombre(frmEstadoCuenta);
                modC.autocompletarnombrefias(frmEstadoCuenta);
                MenuPrincipal.Escritorio.add(frmEstadoCuenta);
                frmEstadoCuenta.setVisible(true);

            } else {
                frmEstadoCuenta.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuEstadisticas) {
            if (frmEstadisticas == null || frmEstadisticas.isClosed()) {
                frmEstadisticas = new frmEstadisticas();
                Estadisticas mod = new Estadisticas();
                ConsultasEstadisticas modC = new ConsultasEstadisticas();
                CtrlEstadisticas ctrl = new CtrlEstadisticas(mod, modC, frmEstadisticas);
                MenuPrincipal.Escritorio.add(frmEstadisticas);
                frmEstadisticas.setVisible(true);

            } else {
                frmEstadisticas.setVisible(true);

            }

        }
        
        if (e.getSource() == MenuPrincipal.MenuMovimientosxQna) {
            if (frmMovimientosXQna == null || frmMovimientosXQna.isClosed()) {
                frmMovimientosXQna = new frmMovimientosXQna();
                MovimientosXqna mod = new MovimientosXqna();
                ConsultaMovimientosXQna modC = new ConsultaMovimientosXQna();
                CtrlMovimientosXQna ctrl = new CtrlMovimientosXQna(mod, modC, frmMovimientosXQna);
                MenuPrincipal.Escritorio.add(frmMovimientosXQna);
                frmMovimientosXQna.setVisible(true);

            } else {
                frmMovimientosXQna.setVisible(true);

            }

        }
        

        if (e.getSource() == MenuPrincipal.MenuTrimestral) {
            if (frmReporteTrimestral == null || frmReporteTrimestral.isClosed()) {
                frmReporteTrimestral = new frmReporteTrimestral();
                ReporteTrimestral mod = new ReporteTrimestral();
                ConsultasReporteTrimestral modC = new ConsultasReporteTrimestral();
                CtrlReporteTrimestral ctrl = new CtrlReporteTrimestral(mod, modC, frmReporteTrimestral);
                MenuPrincipal.Escritorio.add(frmReporteTrimestral);
                frmReporteTrimestral.setVisible(true);

            } else {
                frmReporteTrimestral.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuDesglosada) {
            if (frmRecuperacionDesglosada == null || frmRecuperacionDesglosada.isClosed()) {
                frmRecuperacionDesglosada = new frmRecuperacionDesglosada();
                RecuperacionDesglosada mod = new RecuperacionDesglosada();
                ConsultasRecuperacionDesglosada modC = new ConsultasRecuperacionDesglosada();
                CtrlRecuperacionDesglosada ctrl = new CtrlRecuperacionDesglosada(mod, modC, frmRecuperacionDesglosada);
                MenuPrincipal.Escritorio.add(frmRecuperacionDesglosada);
                frmRecuperacionDesglosada.setVisible(true);

            } else {
                frmRecuperacionDesglosada.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuReporteMov) {
            if (frmExportarMovimientos == null || frmExportarMovimientos.isClosed()) {
                frmExportarMovimientos = new frmExportarMovimientos();
                ReporteMovimientos mod = new ReporteMovimientos();
                ConsultasReportesMovimientos modC = new ConsultasReportesMovimientos();
                CtrlReportesMovimientos ctrl = new CtrlReportesMovimientos(mod, modC, frmExportarMovimientos);
                MenuPrincipal.Escritorio.add(frmExportarMovimientos);
                frmExportarMovimientos.setVisible(true);

            } else {
                frmExportarMovimientos.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuGlobales) {
            if (frmReportesGlobales == null || frmReportesGlobales.isClosed()) {
                frmReportesGlobales = new frmReportesGlobales();
                Globales mod = new Globales();
                ConsultasGlobales modC = new ConsultasGlobales();
                CtrlGlobales ctrl = new CtrlGlobales(mod, modC, frmReportesGlobales);
                MenuPrincipal.Escritorio.add(frmReportesGlobales);
                frmReportesGlobales.setVisible(true);

            } else {
                frmReportesGlobales.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuPlazoFijo) {
            if (frmPlazoFijo == null || frmPlazoFijo.isClosed()) {
                frmPlazoFijo = new frmPlazoFijo();
                PlazoFijo mod = new PlazoFijo();
                ConsultaPlazoFijo modC = new ConsultaPlazoFijo();
                CtrlPlazoFijo ctrl = new CtrlPlazoFijo(mod, modC, frmPlazoFijo);
                modC.autocompletar(frmPlazoFijo);
                MenuPrincipal.Escritorio.add(frmPlazoFijo);
                frmPlazoFijo.setVisible(true);

            } else {
                frmPlazoFijo.setVisible(true);

            }

        }

        if (e.getSource() == MenuPrincipal.MenuTasas) {
            if (frmTasas == null || frmTasas.isClosed()) {
                frmTasas = new frmTasas();
                Tasas mod = new Tasas();
                ConsultasTasas modC = new ConsultasTasas();
                CtrlTasas ctrl = new CtrlTasas(mod, modC, frmTasas);
                MenuPrincipal.Escritorio.add(frmTasas);
                frmTasas.setVisible(true);

            } else {
                frmTasas.setVisible(true);

            }

        }

    }

}
