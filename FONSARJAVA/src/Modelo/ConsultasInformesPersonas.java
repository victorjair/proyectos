/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import View.frmEstadoCuenta;
import View.frmPrestamos;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author victor
 */
public class ConsultasInformesPersonas extends Conexion {

    public void autocompletar(frmEstadoCuenta frmEstadoCuenta) {
        TextAutoCompleter ac = new TextAutoCompleter(frmEstadoCuenta.txtRfc);
        Statement st = null;
        ResultSet rs = null;
        Statement stnombre = null;
        ResultSet rsnombre = null;
        Connection confiar = getConexionFiar();
        try {

            st = (Statement) confiar.createStatement();
            rs = st.executeQuery("select rfc from afiliado");
            while (rs.next()) {
                ac.addItem(rs.getString("rfc"));
            }
        } catch (Exception de) {

            JOptionPane.showMessageDialog(null, de.getMessage());

        }

    }

    public void autocompletarnombre(frmEstadoCuenta frmEstadoCuenta) {
        TextAutoCompleter ac = new TextAutoCompleter(frmEstadoCuenta.txtNombre);
        Statement st = null;
        ResultSet rs = null;
        Statement stnombre = null;
        ResultSet rsnombre = null;
        Connection confiar = getConexionFiar();
        try {

            st = (Statement) confiar.createStatement();
            rs = st.executeQuery("select nombre from afiliado");
            while (rs.next()) {
                ac.addItem(rs.getString("nombre"));
            }
        } catch (Exception de) {

            JOptionPane.showMessageDialog(null, de.getMessage());

        }

    }

    public void imprimeedocuentainversion(InformePersona mov) {
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        JasperReport reporte = null;
        String path = "src\\reportes\\EdoDeCuentaInversion.jasper";
        Map parametro = new HashMap();
        parametro.put("Parameter1", mov.getNombre());
        //parametro.put("Parameter2", mov.getPeriodo());

        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro, conn);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(frmPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void imprimeedocuentaahorro(InformePersona mov) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        if(!mov.getNombre().isEmpty())
        { 
            String sqlnombre = "select rfc from afiliado where nombre = ?";
        try {

            ps = conn.prepareStatement(sqlnombre);
            ps.setString(1, mov.getNombre());
            rs = ps.executeQuery();
            if (rs.next()) {
                mov.setRfc(rs.getString("rfc"));
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasInformesPersonas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        JasperReport reporte = null;
        String path = "src\\reportes\\EstadoDeCuentaAhorros2.jasper";
        Map parametro = new HashMap();
        parametro.put("Parameter1", mov.getRfc());
        parametro.put("Parameter2", mov.getPeriodo());

        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro, conn);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(frmPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void imprimeedocuentaprestamo(InformePersona mov) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        
        
        if(!mov.getNombre().isEmpty())
        { 
            String sqlnombre = "select rfc from prestamos where nombre = ?";
        try {

            ps = conn.prepareStatement(sqlnombre);
            ps.setString(1, mov.getNombre());
            rs = ps.executeQuery();
            if (rs.next()) {
                mov.setRfc(rs.getString("rfc"));
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasInformesPersonas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        JasperReport reporte = null;
        String path = "src\\reportes\\EstadoDeCuentaPrestamos_saldo_mysql.jasper";
        Map parametro = new HashMap();
        parametro.put("rfcmaster", mov.getRfc());
        //parametro.put("Parameter2",mov.getPeriodo());

        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro, conn);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(frmPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void imprimehojanoadeudo(InformePersona mov) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement psnombre = null;
        ResultSet rsnombre = null;
        
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
          if(!mov.getNombre().isEmpty())
        { 
            String sqlnombre = "select rfc from prestamos where nombre = ?";
        try {

            psnombre = conn.prepareStatement(sqlnombre);
            psnombre.setString(1, mov.getNombre());
            rsnombre = psnombre.executeQuery();
            if (rsnombre.next()) {
                mov.setRfc(rsnombre.getString("rfc"));
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasInformesPersonas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        

        String sqlnoadeudo = "select rfc from prestamos where status in ('a','A') and rfc = ?";

        try {

            ps = conn.prepareStatement(sqlnoadeudo);
            ps.setString(1, mov.getRfc());
            rs = ps.executeQuery();

            if (!rs.next()) {

                JasperReport reporte = null;
                String path = "src\\reportes\\HojaNoAdeudo.jasper";
                Map parametro = new HashMap();
                parametro.put("Parameter1", mov.getRfc());
                //parametro.put("Parameter2",mov.getPeriodo());

                try {
                    reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
                    JasperPrint jprint = JasperFillManager.fillReport(path, parametro, conn);
                    JasperViewer view = new JasperViewer(jprint, false);
                    view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

                    view.setVisible(true);

                    // TODO add your handling code here:
                } catch (JRException ex) {
                    Logger.getLogger(ConsultasInformesPersonas.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {

                JOptionPane.showMessageDialog(null, "TIENE ADEUDO AL PROGRAMA NO SE PUEDE IMPRMIR LA CONSTANCIA");

            }

        } catch (Exception de) {

            JOptionPane.showMessageDialog(null, de.getMessage());

        }

    }

    public void imprimepagoanticipado(InformePersona mov) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
         if(!mov.getNombre().isEmpty())
        { 
            String sqlnombre = "select rfc from prestamos where nombre = ?";
        try {

            ps = conn.prepareStatement(sqlnombre);
            ps.setString(1, mov.getNombre());
            rs = ps.executeQuery();
            if (rs.next()) {
                mov.setRfc(rs.getString("rfc"));
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasInformesPersonas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        JasperReport reporte = null;
        String path = "src\\reportes\\PagoAnticipado.jasper";
        Map parametro = new HashMap();
        parametro.put("Parameter1", mov.getRfc());
        //parametro.put("Parameter2",mov.getPeriodo());
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro, conn);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(frmPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
