/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import View.frmMovAhorros;
import View.frmPrestamos;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JREmptyDataSource;
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




public class ConsultasMovimientosAhorro extends Conexion {
    
    public void autocompletar(frmMovAhorros frmMovAhorros){
        TextAutoCompleter ac = new TextAutoCompleter(frmMovAhorros.txtRfc);
        TextAutoCompleter nom = new TextAutoCompleter(frmMovAhorros.txtNombre);
       
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
    
    public boolean completarcampos(MovimientosAhorro mov){
    
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * from afiliado where nombre = ? ";

        try {

            ps = con.prepareStatement(sql);
            //ps.setString(1, credito.getRfc());
            ps.setString (1, mov.getNombre());

            rs = ps.executeQuery();

            if (rs.next()) {
                
                //credito.setNombre(rs.getString("nombre"));
                
                mov.setRfc(rs.getString("rfc"));
                mov.setPlantel(rs.getString("plantel"));
                
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
    
    

    public boolean registrarmovimientoahorro(MovimientosAhorro mov) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        PreparedStatement psremoto = null;
        Connection conremoto = getConexionnotaRemoto();
        String sql = "INSERT INTO qnaahorrorecuperada(rfc,nombre,plantel,importe,numeroquincena,literal,cheque,transferencia,"
                + "tiporetiro,usuariomovimiento,totalahorrado,retirosanteriores,totalestado,totalsaldo)"
                + "values"
                + "(?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?)";
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, mov.getRfc());
            ps.setString(2, mov.getNombre());
            ps.setString(3, mov.getPlantel());
            ps.setDouble(4, mov.getAbono());
            ps.setString(5, mov.getNumeroquincena());
            ps.setString(6, mov.getLiteral());
            ps.setInt(7, mov.getCheque());
            ps.setInt(8, mov.getTransferencia());
            ps.setString(9, mov.getTipoRetiro());
            ps.setString(10, Usuario.usuario);
            ps.setDouble(11, mov.getTotalAhorrado());
            ps.setDouble(12, mov.getRetirosAnteriores());
            ps.setDouble(13, mov.getTotalEstado());
            ps.setDouble(14, mov.getTotalSaldo());
          
            ps.execute();
            
            psremoto = conremoto.prepareStatement(sql);
            psremoto.setString(1, mov.getRfc());
            psremoto.setString(2, mov.getNombre());
            psremoto.setString(3, mov.getPlantel());
            psremoto.setDouble(4, mov.getAbono());
            psremoto.setString(5, mov.getNumeroquincena());
            psremoto.setString(6, mov.getLiteral());
            psremoto.setInt(7, mov.getCheque());
            psremoto.setInt(8, mov.getTransferencia());
            psremoto.setString(9, mov.getTipoRetiro());
            psremoto.setString(10, Usuario.usuario);
            psremoto.setDouble(11, mov.getTotalAhorrado());
            psremoto.setDouble(12, mov.getRetirosAnteriores());
            psremoto.setDouble(13, mov.getTotalEstado());
            psremoto.setDouble(14, mov.getTotalSaldo());
          
            psremoto.execute();
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

    public boolean eliminarmovimientoahorro(MovimientosAhorro mov) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        
           PreparedStatement psremoto = null;
        Connection conremoto = getConexionnotaRemoto();
     

        String sql = "DELETE from qnaahorrorecuperada WHERE Id = ? ";
        //String sql = "UPDATE prestamos SET status = ?,fechacancelacion = ?,usuariocancelacion = ? WHERE folio = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mov.getId());
            ps.execute();
            
            psremoto = conremoto.prepareStatement(sql);
            psremoto.setString(1, mov.getId());
            psremoto.execute();
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

    public boolean buscarmovimientoahorro(MovimientosAhorro mov) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT rfc,nombre,plantel,importe,tiporetiro,IFNULL(cheque,0)as cheque,ifnull(transferencia,0)as transferencia,numeroquincena,"
                + " fechamodificacion,ifnull(totalahorrado,0)as totalahorrado,ifnull(retirosanteriores,0) as retirosanteriores,ifnull(totalestado,0)as totalestado,ifnull(totalsaldo,0)as totalsaldo from qnaahorrorecuperada where Id = ? and literal = 'DIV' ";
                

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, mov.getId());
            //ps.setString(2, mov.getNumeroquincena());
            //ps.setDouble(3, mov.getAbono());

            rs = ps.executeQuery();

            if (rs.next()) {
                mov.setRfc(rs.getString("rfc"));
                mov.setNombre(rs.getString("nombre"));
                mov.setPlantel(rs.getString("plantel"));
                mov.setAbono(Double.parseDouble(rs.getString("importe")));
                mov.setTipoRetiro(rs.getString("tiporetiro"));
                mov.setCheque(Integer.parseInt(rs.getString("cheque")));
                mov.setTransferencia(Integer.parseInt(rs.getString("transferencia")));
                mov.setNumeroquincena(rs.getString("numeroquincena"));
                mov.setFechaAbono(rs.getString("fechamodificacion"));
                mov.setTotalAhorrado(Double.parseDouble(rs.getString("totalahorrado")));
                mov.setRetirosAnteriores(Double.parseDouble(rs.getString("retirosanteriores")));
                mov.setTotalEstado(Double.parseDouble(rs.getString("totalestado")));
                mov.setTotalSaldo(Double.parseDouble(rs.getString("totalsaldo")));
               
                //mov.setId(rs.getString("Id"));

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
    
    public boolean buscarmovimientoahorronuevo(MovimientosAhorro mov){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT rfc,nombre,plantel ,(select sum(importe) from qnaahorrorecuperada where literal not in ('DIV')"
                + " and rfc = ?)as ahorro,(select  CASE WHEN sum(importe)IS NULL THEN 0 ELSE sum(importe) END from qnaahorrorecuperada where literal  in ('DIV') and "
                + "rfc = ?)as retiros,(select sum(importe) from qnaahorrorecuperada where literal not in ('DIV')"
                + " and rfc = ?)-(select CASE WHEN sum(importe)IS NULL THEN 0 ELSE sum(importe) END from qnaahorrorecuperada where literal  in ('DIV') and "
                + "rfc = ?)as saldo from afiliado where rfc = ? ";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, mov.getRfc());
            ps.setString(2, mov.getRfc());
            ps.setString(3, mov.getRfc());
            ps.setString(4, mov.getRfc());
            ps.setString(5, mov.getRfc());
           
            //ps.setString(2, mov.getNumeroquincena());
            //ps.setDouble(3, mov.getAbono());

            rs = ps.executeQuery();

            if (rs.next()) {
                
                mov.setRfc(rs.getString("rfc"));
                mov.setNombre(rs.getString("nombre"));
                mov.setPlantel(rs.getString("plantel"));
                mov.setAhorros(Double.parseDouble(rs.getString("ahorro")));
                
                mov.setRetiros(Double.parseDouble(rs.getString("retiros")));
                /*if(mov.getRetiros().equals(""))
                {
                mov.equals(0);
                } */  
                mov.setSaldo(Double.parseDouble(rs.getString("saldo")));
               
                

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

    public boolean modificarmovimientoahorro(MovimientosAhorro mov) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        PreparedStatement psremoto = null;
        Connection conremoto = getConexionnotaRemoto();
     
        String sql = "UPDATE qnaahorrorecuperada set nombre=?,rfc=?,plantel=? "
                + ",importe=?,numeroquincena=?,cheque=?,transferencia=?,tiporetiro=?"
                + "where Id = ?";

        try {

            ps = con.prepareStatement(sql);
            // ps.setString(1, persona.getRfc());
            ps.setString(1, mov.getNombre());
            ps.setString(2, mov.getRfc());
            ps.setString(3, mov.getPlantel());
            ps.setDouble(4, mov.getAbono());
            ps.setInt(5, Integer.parseInt(mov.getNumeroquincena()));
            ps.setInt(6, mov.getCheque());
            ps.setInt(7, mov.getTransferencia());
            ps.setString(8, mov.getTipoRetiro());
            ps.setString(9, mov.getId());
            ps.execute();
            
            psremoto = conremoto.prepareStatement(sql);
            // ps.setString(1, persona.getRfc());
            psremoto.setString(1, mov.getNombre());
            psremoto.setString(2, mov.getRfc());
            psremoto.setString(3, mov.getPlantel());
            psremoto.setDouble(4, mov.getAbono());
            psremoto.setInt(5, Integer.parseInt(mov.getNumeroquincena()));
            psremoto.setInt(6, mov.getCheque());
            psremoto.setInt(7, mov.getTransferencia());
            psremoto.setString(8, mov.getTipoRetiro());
            psremoto.setString(9, mov.getId());
            psremoto.execute();
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
    
    public void imprimemandatoahorro(MovimientosAhorro mov) {
        
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        
        JasperReport reporte ;
        String path = "src\\reportes\\ReciboAhorro.jasper";
        HashMap  parametro = new HashMap();
        parametro.put("Parameter1",String.valueOf(mov.getAhorros()));
        parametro.put("Parameter2",String.valueOf(mov.getRetiros()));
        parametro.put("Parameter3",String.valueOf(mov.getAbono()));
        parametro.put("Parameter4",String.valueOf(mov.getSaldo()));
        parametro.put("Parameter5",mov.getNombre());
        parametro.put("Parameter6",mov.getTipoRetiro());
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path,parametro,new JREmptyDataSource());
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
            

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(frmPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
