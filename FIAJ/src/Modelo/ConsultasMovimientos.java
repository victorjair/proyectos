/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


import View.frmPrestamos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ConsultasMovimientos extends Conexion {
    
    
    public boolean completarcampos(Movimientos mov){
    
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * from prestamos where folio = ? ";

        try {

            ps = con.prepareStatement(sql);
            //ps.setString(1, credito.getRfc());
            ps.setInt(1, mov.getFolio());

            rs = ps.executeQuery();

            if (rs.next()) {
                
                //credito.setNombre(rs.getString("nombre"));
                
                mov.setRfc(rs.getString("rfc"));
                mov.setPlantel(rs.getString("plantel"));
                mov.setNombre(rs.getString("nombre"));
                
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
    
    public boolean buscaId(Movimientos mov){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT max(Id) as Id from qnarecuperacionrecuperada where folio = ?";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, mov.getFolio());
            rs = ps.executeQuery();
            if (rs.next()) {
                mov.setId(rs.getString("Id"));
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
    

    public boolean registrarmovimiento(Movimientos mov) {
        PreparedStatement ps = null;
        PreparedStatement psactualiza = null;
        PreparedStatement psactualizasaldo = null;
        PreparedStatement psactualizastatus = null;
        Connection con = getConexion();

        String sql = "INSERT INTO qnarecuperacionrecuperada(rfc,nombre,plantel,folio,abono,numeroquincena,movimiento,fechaabono,usuariomovimiento,"
                + " capital,interes)values"
                + "(?,?,?,?,"
                + "?,?,?,?,?,?,?)";
        
        String sqlactualiza = "update prestamos set abonoparcial = (select ifnull(sum(abono),0) from "
                + "qnarecuperacionrecuperada where "
                + "	folio = ? and movimiento not in ('I')),sumaindebido = (select ifnull(sum(abono),0) from "
                + "qnarecuperacionrecuperada where "
                + "	folio = ? and movimiento  in ('I')) where folio = ?";

        String sqlactualizasaldo = "update prestamos set saldo =  IFNull(total,0) + "
                + "IFNull(sumaindebido,0) - IFNull(abonoparcial,0) where folio = ?";

        /*String sqlactualizastatus = "update prestamos set status = 'A'  where saldo > '0'  "
                + "and status not in ('c','C') and folio = ?";*/
        String sqlactualizastatus = "UPDATE prestamos SET STATUS = CASE WHEN saldo > 0 THEN 'A' "
                + "ELSE 'S' END  WHERE folio = ? AND STATUS NOT IN ('c','C')";
                     
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, mov.getRfc());
            ps.setString(2, mov.getNombre());
            ps.setString(3, mov.getPlantel());
            ps.setInt(4, mov.getFolio());
            ps.setDouble(5, mov.getAbono());
            ps.setInt(6, mov.getNumeroQuincena());
            ps.setString(7, mov.getMovimiento());
            //ps.setDouble(8, mov.getSaldo());
            ps.setString(8, mov.getFechaAbono());
            ps.setString(9, Usuario.usuario);
            ps.setDouble(10, mov.getCapital());
            ps.setDouble(11, mov.getInteres());
            ps.execute();
            
            
            psactualiza = con.prepareStatement(sqlactualiza);
            psactualiza.setInt(1, mov.getFolio());
            psactualiza.setInt(2, mov.getFolio());
            psactualiza.setInt(3, mov.getFolio());
            psactualiza.execute();

            psactualizasaldo = con.prepareStatement(sqlactualizasaldo);
            psactualizasaldo.setInt(1, mov.getFolio());
            psactualizasaldo.execute();

            psactualizastatus = con.prepareStatement(sqlactualizastatus);
            psactualizastatus.setInt(1, mov.getFolio());
            psactualizastatus.execute();
            
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

    public boolean eliminarmovimiento(Movimientos mov) {

        PreparedStatement ps = null;
        PreparedStatement psactualiza = null;
        PreparedStatement psactualizasaldo = null;
        PreparedStatement psactualizastatus = null;
        Connection con = getConexion();

        String sql = "DELETE from qnarecuperacionrecuperada WHERE Id = ? ";
        //String sql = "UPDATE prestamos SET status = ?,fechacancelacion = ?,usuariocancelacion = ? WHERE folio = ?";

        String sqlactualiza = "update prestamos set abonoparcial = (select ifnull(sum(abono),0) from "
                + "qnarecuperacionrecuperada where "
                + "	folio = ? and movimiento not in ('I')),sumaindebido = (select ifnull(sum(abono),0) from "
                + "qnarecuperacionrecuperada where "
                + "	folio = ? and movimiento  in ('I')) where folio = ?";

        String sqlactualizasaldo = "update prestamos set saldo =  IFNull(total,0) + "
                + "IFNull(sumaindebido,0) - IFNull(abonoparcial,0) where folio = ?";

        /*String sqlactualizastatus = "update prestamos set status = 'A'  where saldo > '0'  "
                + "and status not in ('c','C') and folio = ?";*/
        String sqlactualizastatus = "UPDATE prestamos SET STATUS = CASE WHEN saldo > 0 THEN 'A' "
                + "ELSE 'S' END  WHERE folio = ? AND STATUS NOT IN ('c','C')";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mov.getId());
            //ps.setInt(1,mov.getFolio());
           // ps.setString(2, mov.getFechaAbono());
           // ps.setString(3, mov.getMovimiento());
            ps.execute();
            
            psactualiza = con.prepareStatement(sqlactualiza);
            psactualiza.setInt(1, mov.getFolio());
            psactualiza.setInt(2, mov.getFolio());
            psactualiza.setInt(3, mov.getFolio());
            psactualiza.execute();

            psactualizasaldo = con.prepareStatement(sqlactualizasaldo);
            psactualizasaldo.setInt(1, mov.getFolio());
            psactualizasaldo.execute();

            psactualizastatus = con.prepareStatement(sqlactualizastatus);
            psactualizastatus.setInt(1, mov.getFolio());
            psactualizastatus.execute();
            
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

    public boolean buscarmovimiento(Movimientos mov) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * from qnarecuperacionrecuperada where Id = ? ";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, mov.getId());
            //ps.setString(2, mov.getMovimiento());
            //ps.setInt(3, mov.getFolio());

            rs = ps.executeQuery();

            if (rs.next()) {
                mov.setRfc(rs.getString("rfc"));
                mov.setNombre(rs.getString("nombre"));
                mov.setPlantel(rs.getString("plantel"));
                mov.setFolio(Integer.parseInt(rs.getString("folio")));
                mov.setAbono(Double.parseDouble(rs.getString("abono")));
                mov.setNumeroQuincena(Integer.parseInt(rs.getString("numeroquincena")));
                mov.setMovimiento(rs.getString("movimiento"));
                mov.setFechaAbono(rs.getString("fechaabono"));
               // mov.setId(rs.getString("Id"));

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
    
    public boolean modificarmovimiento(Movimientos mov){
     PreparedStatement ps = null;
     PreparedStatement psactualiza = null;
     PreparedStatement psactualizasaldo = null;
     PreparedStatement psactualizastatus = null;
        Connection con = getConexion();

        String sql = "UPDATE qnarecuperacionrecuperada set nombre=?,rfc=?,plantel=? "
                + ",folio=?,abono=?,numeroquincena=?,movimiento=?,fechaabono=?"
                + "where Id = ?";

        String sqlactualiza = "update prestamos set abonoparcial = (select ifnull(sum(abono),0) from "
                + "qnarecuperacionrecuperada where "
                + "	folio = ? and movimiento not in ('I')),sumaindebido = (select ifnull(sum(abono),0) from "
                + "qnarecuperacionrecuperada where "
                + "	folio = ? and movimiento  in ('I')) where folio = ?";

        String sqlactualizasaldo = "update prestamos set saldo =  IFNull(total,0) + "
                + "IFNull(sumaindebido,0) - IFNull(abonoparcial,0) where folio = ?";

        /*String sqlactualizastatus = "update prestamos set status = 'A'  where saldo > '0'  "
                + "and status not in ('c','C') and folio = ?";*/
        String sqlactualizastatus = "UPDATE prestamos SET STATUS = CASE WHEN saldo > 0 THEN 'A' "
                + "ELSE 'S' END  WHERE folio = ? AND STATUS NOT IN ('c','C')";
        try {

            ps = con.prepareStatement(sql);
            // ps.setString(1, persona.getRfc());
             ps.setString(1, mov.getNombre());
             ps.setString(2, mov.getRfc());
             ps.setString(3, mov.getPlantel());
             ps.setInt(4, mov.getFolio());
             ps.setDouble(5, mov.getAbono());
             ps.setInt(6, mov.getNumeroQuincena());
             ps.setString(7, mov.getMovimiento());
             ps.setString(8,mov.getFechaAbono());
             ps.setString(9, mov.getId());
             ps.execute();
             
             psactualiza = con.prepareStatement(sqlactualiza);
            psactualiza.setInt(1, mov.getFolio());
            psactualiza.setInt(2, mov.getFolio());
            psactualiza.setInt(3, mov.getFolio());
            psactualiza.execute();

            psactualizasaldo = con.prepareStatement(sqlactualizasaldo);
            psactualizasaldo.setInt(1, mov.getFolio());
            psactualizasaldo.execute();

            psactualizastatus = con.prepareStatement(sqlactualizastatus);
            psactualizastatus.setInt(1, mov.getFolio());
            psactualizastatus.execute();
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
    
    
     public void imprimeReciboIndebido(Movimientos mov) {
        
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        JasperReport reporte = null;
        String path = "src\\reportes\\ReporteDescuentoIndebido.jasper";
        Map parametro = new HashMap();
        parametro.put("Id",mov.getId());
        
        try {
            reporte = (JasperReport
                    ) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro ,conn);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
            

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(frmPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /* Connection con = getConexion();
        JasperReport archivo = JasperCompileManager.compileReport("coffe.jrxml");
        //Map<String,Object> map = new HashMap<String, Object>();
       
        //JRDataSource data = new JREmptyDataSource();
        JasperPrint prin = JasperFillManager.fillReport(archivo,null,con);
        JasperExportManager.exportReportToPdfFile(prin,"reporte.pdf");*/
        
    
    }

}
