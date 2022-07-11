/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import View.frmPlazoFijo;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author victor
 */
public class ConsultaPlazoFijo extends Conexion {
    
    public void imprimerecibo(PlazoFijo inv) {
        
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        JasperReport reporte = null;
        String path = "src\\reportes\\ReciboFias.jasper";
        Map parametro = new HashMap();
        parametro.put("folio",inv.getFolio());
        /*if(inv.getFolioReinversion() == null){
            parametro.put("Parameter1",inv.getFolio());
       
            }
            else{
            parametro.put("Parameter1",inv.getFolioReinversion());
       
            
            }*/
        
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro ,conn);
            //byte [] pdfBytes = JasperExportManager.exportReportToPdf(jprint);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
            

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(frmPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    
    }
    
     public void imprimereciboretiro(PlazoFijo inv) {
        
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        JasperReport reporte = null;
        String path = "src\\reportes\\ReciboFiasRetiro.jasper";
        Map parametro = new HashMap();
        parametro.put("folio",inv.getFolio());
        /*if(inv.getFolioReinversion() == null){
            parametro.put("Parameter1",inv.getFolio());
       
            }
            else{
            parametro.put("Parameter1",inv.getFolioReinversion());
       
            
            }*/
        
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro ,conn);
            //byte [] pdfBytes = JasperExportManager.exportReportToPdf(jprint);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
            

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(frmPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    
    }
    public void imprimemandato(PlazoFijo inv) {
        
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        JasperReport reporte = null;
        String path = "src\\reportes\\ConstanciaFias2.jasper";
        Map parametro = new HashMap();
        
        if(inv.getFolioReinversion() == null){
            parametro.put("Parameter1",inv.getFolio());
       
            }
            else{
            parametro.put("Parameter1",inv.getFolioReinversion());
       
            
            }
        
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro ,conn);
            //byte [] pdfBytes = JasperExportManager.exportReportToPdf(jprint);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
            

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(frmPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    
    }
    
    
    public boolean completarcampos(PlazoFijo inv){
    
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * from afiliado where nombre = ? ";

        try {

            ps = con.prepareStatement(sql);
            //ps.setString(1, credito.getRfc());
            ps.setString (1, inv.getNombre());

            rs = ps.executeQuery();

            if (rs.next()) {
                
                //credito.setNombre(rs.getString("nombre"));
                
                inv.setRfc(rs.getString("rfc"));
                inv.setDireccion(rs.getString("direccion"));
                
                
                return true;

            } else {
                return false;

            }

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }
            
              
    
    }
    
    
    public void autocompletar(frmPlazoFijo inv){
        TextAutoCompleter ac = new TextAutoCompleter(inv.txtrfc);
        TextAutoCompleter nom = new TextAutoCompleter(inv.txtnombre);
       
        Statement  st = null;
        ResultSet rs = null;
        Statement  stnombre = null;
        ResultSet rsnombre = null;
        Connection con = getConexion();
        
        try{
            
            st = (Statement)con.createStatement();
            rs= st.executeQuery("select rfc from afiliado");
            while (rs.next()){
            ac.addItem(rs.getString("rfc"));
            }
        }
        catch(Exception de){
        
          JOptionPane.showMessageDialog(null, de.getMessage());
        
        }
        
        try{
            
            st = (Statement)con.createStatement();
            rs= st.executeQuery("select nombre from afiliado");
            while (rs.next()){
            nom.addItem(rs.getString("nombre"));
            }
        }
        catch(Exception de){
        
          JOptionPane.showMessageDialog(null, de.getMessage());
        
        }
               
    
    }

    public boolean probarcampos(PlazoFijo inv){
    
    
     JOptionPane.showMessageDialog(null, "Accedio a la funcion");
     return true;

    }
    
    public boolean registrar(PlazoFijo inv) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String usuarioActual;

        String sql = "INSERT INTO fias(folio,rfc,nombre,capital,plazo,fechadeposito,fechavencimiento,interes,total,status,numeroquincena,"
                + "fechacreacion,tasainteres,provision,saldo,usuarioalta,estado,montoabono,montoretiro) Values"
                + "(?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            if(inv.getFolioReinversion() == null){
            ps.setString(1, inv.getFolio());
            }
            else{
            ps.setString(1, inv.getFolioReinversion());
            
            }
            //ps.setString(1, inv.getFolio());
            ps.setString(2, inv.getRfc());
            ps.setString(3, inv.getNombre());
            ps.setDouble(4, inv.getCapital());
            ps.setInt(5, inv.getPlazo());
            ps.setString(6, inv.getFechaInicio());
            ps.setString(7, inv.getFechaVencimiento());
            ps.setDouble(8, inv.getInteres());
            ps.setDouble(9, inv.getTotal());
            ps.setString(10, "A");
            ps.setString(11, inv.getQuincena());
            ps.setString(12, inv.getFechaInversion());
            ps.setDouble(13, inv.getTasaTotal());
            ps.setDouble(14, inv.getProvision());
            ps.setDouble(15, inv.getCapital());
            usuarioActual = Usuario.usuario;
            ps.setString(16, usuarioActual);
            ps.setString(17, inv.getTipoInversionista());
            if(inv.getMontoAbono() == null){
            ps.setDouble(18,0);
            }
            else{
            ps.setDouble(18, inv.getMontoAbono());
            }
            if(inv.getMontoRetiro() == null){
            ps.setDouble(19,0);
            }else{
            ps.setDouble(19, inv.getMontoRetiro());
            }
           
            ps.execute();
            return true;

        } catch (SQLException e) {
            System.err.println(e);
            return false;

        } finally {
            try {
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

   public boolean pagarplazofijo(PlazoFijo inv){
   
        PreparedStatement ps = null;
        Connection con = getConexion();
        //String sql = "DELETE from afiliado WHERE rfc =?";
        String sql = "UPDATE fias SET status = ?, referencia = ?, sancion = ?, qnapago =?, fechapago = ?,"
                + "interes = ?, total = ? , usuariopago = ?  WHERE folio = ? ";
        
         try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "P");
            ps.setString(2, inv.getReferencia());
            ps.setString(3, inv.getSancion());
            ps.setString(4, inv.getQuincenaPago());
            ps.setString(5, inv.getFechaPago());
            ps.setDouble(6, inv.getInteresGanadoPagar());
            ps.setDouble(7, inv.getTotalPagadoPagar());
            ps.setString(8, Usuario.usuario);
            ps.setString(9, inv.getFolio());
            ps.execute();
            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }

   }
    
    public boolean eliminar(PlazoFijo inv) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        //String sql = "DELETE from afiliado WHERE rfc =?";
        String sql = "UPDATE fias SET status = ?, fechacancelacion = ?,usuariocancelacion = ? WHERE folio = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "C");
            ps.setString(2, inv.getFechaInversion());
            ps.setString(3, Usuario.usuario);
            ps.setString(4, inv.getFolio());
            ps.execute();
            return true;

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

    public boolean modificar(PlazoFijo inv) {
        return true;
    }

    public boolean buscatasainteres(PlazoFijo inv){
     PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT promedio from tasa where ano = ? and mes = ? ";

        try {
          ps = con.prepareStatement(sql);
            ps.setString(1, inv.getFechaInicio().substring(0,4));
            ps.setString(2, inv.getFechaInicio().substring(5,7));

            rs = ps.executeQuery();

            if (rs.next()) {
                inv.setTasaInteres(rs.getDouble("promedio"));
                
                return true;

            } else {
                return false;

            }

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }    
        
    
    
    }
    
    public boolean buscar(PlazoFijo inv) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT folio,rfc,nombre,CASE WHEN qnapago IS NULL THEN 0 ELSE qnapago END as qnapago,CASE WHEN capital IS NULL THEN 0 ELSE capital END as capital,plazo,fechadeposito,fechavencimiento,CASE WHEN interes IS NULL THEN 0 ELSE interes END as interes,CASE WHEN total IS NULL THEN 0 ELSE total END as total,status,"
                + "numeroquincena,estado,fechacreacion,tasainteres,provision,round(interes / ((plazo/2)*30),2) as provisiondiaria,CASE WHEN referencia IS NULL THEN 0 ELSE referencia END as referencia,CASE WHEN fechapago IS NULL THEN 0 ELSE fechapago END as fechapago,CASE WHEN sancion IS NULL THEN 0 ELSE sancion END as sancion"
                + " from fias where folio = ? ";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, inv.getFolio());

            rs = ps.executeQuery();

            if (rs.next()) {
                inv.setRfc(rs.getString("rfc"));
                inv.setNombre(rs.getString("nombre"));
                inv.setCapital(rs.getDouble("capital"));
                inv.setPlazo(rs.getInt("plazo"));
                inv.setFechaInicio(rs.getString("fechadeposito"));
                inv.setFechaVencimiento(rs.getString("fechavencimiento"));
                inv.setInteres(rs.getDouble("interes"));
                inv.setTotal(rs.getDouble("total"));
                inv.setStatus(rs.getString("status"));
                inv.setTipoInversionista(rs.getString("estado"));
                inv.setQuincena(rs.getString("numeroquincena"));
                inv.setFechaInversion(rs.getString("fechacreacion"));
                inv.setTasaInteres(rs.getDouble("tasainteres"));
                inv.setProvision(rs.getDouble("provision"));
                inv.setProvisionDiaria(rs.getDouble("provisiondiaria"));
                inv.setReferencia(rs.getString("referencia"));
                inv.setFechaPago(rs.getString("fechapago"));
                inv.setSancion(rs.getString("sancion"));
                inv.setQuincenaPago(rs.getString("qnapago"));
               
                return true;

            } else {
                return false;

            }

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }
    }

    public void pagar(PlazoFijo inv) {

    }

    public boolean buscafoliomayor(PlazoFijo inv) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT max(cast(folio as signed))+1  as folio from fias  ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                inv.setFolio(rs.getString("folio"));

                return true;

            } else {
                return false;

            }

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }

    }

}
