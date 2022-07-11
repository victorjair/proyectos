/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

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
public class ConsultasPrestamos extends Conexion {
    
     public boolean completarquincena(Prestamo credito){
     
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

         String sql = "SELECT CASE WHEN  substr(MAX(numeroquincena),5,2) = 24 then "
                + "CONCAT(  substr(max(numeroquincena),1,4)+ 1   ,   \"01\")  else MAX(numeroquincena) + 1 "
                + "end   as maximaquincena  from qnarecuperaciongenerada  ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                //credito.setFolio(Integer.parseInt(rs.getString("folio")));
                credito.setQnaDescuento(rs.getString("maximaquincena"));

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
        
             
       public boolean completarquincenaactual(Prestamo credito){
     
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "select concat(year(now()),numeroquincena) as qnaactual from dias "
                + "where day(now()) = dia and month(now()) = mes ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                //credito.setFolio(Integer.parseInt(rs.getString("folio")));
               credito.setQna(rs.getString("qnaactual"));

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
        
    
    
    public boolean completarcampos(Prestamo credito){
    
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * from afiliado where nombre = ? ";

        try {

            ps = con.prepareStatement(sql);
            //ps.setString(1, credito.getRfc());
            ps.setString (1, credito.getNombre());

            rs = ps.executeQuery();

            if (rs.next()) {
                
                //credito.setNombre(rs.getString("nombre"));
                
                credito.setRfc(rs.getString("rfc"));
               // credito.setPuesto(rs.getString("puesto"));
                credito.setDomicilio(rs.getString("direccion"));
              //  credito.setNumeroEmpleado(rs.getString("numeroempleado"));
             //   credito.setPlantel(rs.getString("plantel"));
             //   credito.setSueldo(Float.parseFloat((rs.getString("sueldobase"))));
                
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
    
    public boolean completarcamposnomina(Prestamo credito){
    
        int i = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connota = getConexionnota();
        
               

        String sql = "SELECT plantel,CATEGORIA,empleado,(ifnull(P_01,0) + ifnull(P_02,0) + ifnull(P_03,0) + ifnull(P_04,0) + ifnull(P_05,0) + "
                + "ifnull(P_06,0) + ifnull(P_07,0) + ifnull(P_08,0) + ifnull(P_09,0) + ifnull(P_10,0) + ifnull(P_11,0) + ifnull(P_12,0) + ifnull(P_13,0) + ifnull(P_14,0) + ifnull(P_15,0) + "
                + "ifnull(P_16,0) + ifnull(P_17,0) + ifnull(P_18,0) + ifnull(P_19,0) + ifnull(P_20,0) + ifnull(P_21,0) + ifnull(P_22,0) + ifnull(P_23,0) + ifnull(P_24,0) + ifnull(P_25,0) + "
                + "ifnull(P_26,0) + ifnull(P_27,0) + ifnull(P_28,0) + ifnull(P_29,0) + ifnull(P_30,0) +ifnull(P_DI,0) + ifnull(P_C1,0)+ ifnull(P_C2,0) + ifnull(P_31,0) + ifnull(P_32,0) + "
                + "ifnull(P_33,0) + ifnull(P_MC,0) + ifnull(P_PG,0) + ifnull(P_PE,0) + ifnull(P_36,0) + ifnull(P_37,0) + ifnull(P_35,0) + ifnull(P_38,0) + ifnull(P_39,0) + ifnull(P_40,0) + "
                + "ifnull(P_01A,0) + ifnull(P_01B,0) + ifnull(P_01C,0) +ifnull(P_01D,0) + ifnull(P_01E,0) + ifnull(P_34,0) + ifnull(P_41,0) + ifnull(P_42,0) + ifnull(P_43,0) + "
                + "ifnull(P_44,0) + ifnull(P_45,0) + ifnull(P_46,0) + ifnull(P_47,0))-"
                
                + "(ifnull(D_01,0) + ifnull(D_10,0) + ifnull(D_CB,0) + ifnull(D_02,0) + ifnull(D_03,0) + ifnull(D_04,0) + ifnull(D_05,0) + ifnull(D_06,0) + ifnull(D_07,0) + "
                + "ifnull(D_08,0) + ifnull(D_09,0) + ifnull(D_11,0) + ifnull(D_12,0) + ifnull(D_13,0) + ifnull(D_14,0) + ifnull(D_15,0)+ ifnull(D_16,0) + ifnull(D_17,0) + ifnull(D_18,0) + "
                + "ifnull(D_19,0) + ifnull(D_20,0) + ifnull(D_21,0) + ifnull(D_22,0) + ifnull(D_23,0) + ifnull(D_24,0) + ifnull(D_25,0) + ifnull(D_26,0) + ifnull(D_27,0) + ifnull(D_28,0) + "
                + "ifnull(D_29,0) + ifnull(D_30,0) + ifnull(D_31,0) + ifnull(D_32,0)+ ifnull(D_33,0) + ifnull(D_34,0) + ifnull(D_35,0) + ifnull(D_36,0) + ifnull(D_37,0) + ifnull(D_38,0) + "
                + "ifnull(D_39,0) + ifnull(D_40,0) + ifnull(D_41,0) + ifnull(D_42,0) + ifnull(D_01A,0) + ifnull(D_43,0) + ifnull(D_44,0) + ifnull(D_45,0) + ifnull(D_46,0) + ifnull(D_47,0) + "
                + "ifnull(D_48,0) + ifnull(D_49,0) + ifnull(D_50,0))as neto " 
                + "from hojai where concat(ap_pat,' ',ap_mat,' ',nombre)  = ? AND numeroquincena = "
                + "(SELECT MAX(numeroquincena) FROM hojai WHERE concat(ap_pat,' ',ap_mat,' ',nombre)  = ?)" ;

        try {

            ps = connota.prepareStatement(sql);
            ps.setString (1, credito.getNombre());
            ps.setString (2, credito.getNombre());
            rs = ps.executeQuery();
            while (rs.next()) {
               /* System.out.println(rs.getString("categoria"));
                System.out.println(rs.getString("empleado"));
                System.out.println(rs.getString("plantel"));
                 System.out.println(i);*/
                
                if(i==0)
                {    
                credito.setPuesto(rs.getString("CATEGORIA"));
                credito.setNumeroEmpleado(rs.getString("empleado"));
                credito.setPlantel(rs.getString("plantel"));
                credito.setSueldo(Float.parseFloat((rs.getString("neto"))));
                }
                if(i==1)
                {    
                credito.setPuesto2(rs.getString("CATEGORIA"));
                credito.setNumeroEmpleado2(rs.getString("empleado"));
                credito.setPlantel2(rs.getString("plantel"));
                credito.setSueldo2(Float.parseFloat((rs.getString("neto"))));
                }
                if(i==2)
                {    
                credito.setPuesto3(rs.getString("CATEGORIA"));
                credito.setNumeroEmpleado3(rs.getString("empleado"));
                credito.setPlantel3(rs.getString("plantel"));
                credito.setSueldo3(Float.parseFloat((rs.getString("neto"))));
                }
                
                
                i++;
             
            }
            
             return true;
        
            

        } catch (SQLException e) {

            System.err.println(e);
            return false;
        } finally {
            try {
                connota.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }
            
              
    
    }
    
    public void autocompletar(frmPrestamos credito){
        TextAutoCompleter ac = new TextAutoCompleter(credito.txtRfc);
        TextAutoCompleter nom = new TextAutoCompleter(credito.txtNombre);
       
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
    

    public boolean registrarprestamo(Prestamo credito) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        
         PreparedStatement psremoto = null;
         Connection conremoto = getConexionnotaRemoto();
        String usuarioActual;
    
         String sql = "INSERT INTO prestamos(rfc,nombre,plantel,folio,puesto,numcheque,plazo,monto,interes,descuento,"
                + "status,fechacreacion,interesanual,rfcaval,nombreaval,total,fondo_garantia,sueldo,domicilio,qna,"
                + "qnadescuento,numeroempleado,clabe,transferencia,observaciones,usuarioalta,saldo,abonoparcial,sumaindebido) Values"
                + "(?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, credito.getRfc());
            ps.setString(2, credito.getNombre());
            ps.setString(3, credito.getPlantel());
            ps.setInt(4, credito.getFolio());
            ps.setString(5, credito.getPuesto());
            ps.setInt(6, credito.getNumCheque());

            ps.setInt(7, credito.getPlazo());
            ps.setFloat(8, credito.getMonto());
            ps.setFloat(9, credito.getInteres());
            ps.setFloat(10, credito.getDescuento());
            ps.setString(11, "A");
            ps.setString(12, credito.getFechaAlta());
            ps.setFloat(13, credito.getInteresAnual());

            ps.setString(14, credito.getRfcAval());
            ps.setString(15, credito.getNombreAval());
            ps.setFloat(16, credito.getTotal());
            ps.setFloat(17, credito.getFondo_Garantia());
            ps.setFloat(18, credito.getSueldo());
            ps.setString(19, credito.getDomicilio());
            ps.setString(20, credito.getQna());
            ps.setString(21, credito.getQnaDescuento());
            ps.setString(22, credito.getNumeroEmpleado());
            //ps.setString(6, credito.getRegion());
            ps.setInt(23, credito.getClabe());
            ps.setInt(24, credito.getTransferencia());
            ps.setString(25, credito.getObservaciones());
            usuarioActual = Usuario.usuario;
            ps.setString(26,usuarioActual);
            ps.setFloat(27, credito.getTotal());
            ps.setFloat(28,0);
            ps.setFloat(29,0);
            
                    ps.execute();
                    
                    
            /*  psremoto = conremoto.prepareStatement(sql);
            psremoto.setString(1, credito.getRfc());
            psremoto.setString(2, credito.getNombre());
            psremoto.setString(3, credito.getPlantel());
            psremoto.setInt(4, credito.getFolio());
            psremoto.setString(5, credito.getPuesto());
            psremoto.setInt(6, credito.getNumCheque());

            psremoto.setInt(7, credito.getPlazo());
            psremoto.setFloat(8, credito.getMonto());
            psremoto.setFloat(9, credito.getInteres());
            psremoto.setFloat(10, credito.getDescuento());
            psremoto.setString(11, "A");
            psremoto.setString(12, credito.getFechaAlta());
            psremoto.setFloat(13, credito.getInteresAnual());

            psremoto.setString(14, credito.getRfcAval());
            psremoto.setString(15, credito.getNombreAval());
            psremoto.setFloat(16, credito.getTotal());
            psremoto.setFloat(17, credito.getFondo_Garantia());
            psremoto.setFloat(18, credito.getSueldo());
            psremoto.setString(19, credito.getDomicilio());
            psremoto.setString(20, credito.getQna());
            psremoto.setString(21, credito.getQnaDescuento());
            psremoto.setString(22, credito.getNumeroEmpleado());
            //ps.setString(6, credito.getRegion());
            psremoto.setInt(23, credito.getClabe());
            psremoto.setInt(24, credito.getTransferencia());
            psremoto.setString(25, credito.getObservaciones());
            usuarioActual = Usuario.usuario;
            psremoto.setString(26,usuarioActual);
            psremoto.setFloat(27, credito.getTotal());
            psremoto.setFloat(28,0);
            psremoto.setFloat(29,0);
            psremoto.execute();*/
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

    public boolean cancelarprestamo(Prestamo credito) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        
        PreparedStatement psremoto = null;
         Connection conremoto = getConexionnotaRemoto();
     

        //String sql = "DELETE from afiliado WHERE rfc =?";
        String sql = "UPDATE prestamos SET status = ?,fechacancelacion = ?,usuariocancelacion = ? WHERE folio = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "C");
            ps.setString(2, credito.getFechaAlta());
            ps.setString(3,Usuario.usuario);
            ps.setInt(4, credito.getFolio());
            ps.execute();
            
            /*psremoto = conremoto.prepareStatement(sql);
            psremoto.setString(1, "C");
            psremoto.setString(2, credito.getFechaAlta());
            psremoto.setString(3,Usuario.usuario);
            psremoto.setInt(4, credito.getFolio());
            psremoto.execute();*/
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

    public boolean buscafoliomayor(Prestamo credito) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT CASE WHEN max(folio) is null then 0 else max(folio) end +1  as folio  from prestamos  ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                credito.setFolio(Integer.parseInt(rs.getString("folio")));

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

    public boolean buscarprestamo(Prestamo credito) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT puesto,rfc,nombre,plantel,numcheque,plazo,monto,interes,descuento,status,"
                + "fechacreacion,CASE WHEN interesanual IS NULL THEN 0 ELSE interesanual END as interesanual,rfcaval,nombreaval,total,"
                + "CASE WHEN fondo_garantia IS NULL THEN 0 ELSE fondo_garantia END as fondo_garantia ,CASE WHEN sueldo IS NULL THEN 0 ELSE sueldo  END as sueldo,domicilio,"
                + "qna,qnadescuento,numeroempleado,CASE WHEN clabe IS NULL THEN 0 ELSE clabe END as clabe,"
                + "CASE WHEN transferencia IS NULL THEN 0 ELSE transferencia END as transferencia,observaciones,"
                + "usuarioalta from prestamos where folio = ? ";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1,credito.getFolio());

            rs = ps.executeQuery();

            if (rs.next()) {
                credito.setRfc(rs.getString("rfc"));
                credito.setNombre(rs.getString("nombre"));
                credito.setPlantel(rs.getString("plantel"));
                //credito.setFolio(Integer.parseInt(rs.getString("folio")));
                credito.setPuesto(rs.getString("puesto"));
                credito.setNumCheque(Integer.parseInt(rs.getString("numcheque")));
                credito.setPlazo(Integer.parseInt(rs.getString("plazo")));
                credito.setMonto(Float.parseFloat(rs.getString("monto")));
                credito.setInteres(Float.parseFloat(rs.getString("interes")));
                credito.setDescuento(Float.parseFloat(rs.getString("descuento")));
                credito.setStatus(rs.getString("status"));
                credito.setFechaAlta(rs.getString("fechacreacion"));
                credito.setInteresAnual(Integer.parseInt(rs.getString("interesanual")));
                credito.setRfcAval(rs.getString("rfcaval"));
                credito.setNombreAval(rs.getString("nombreaval"));
                credito.setTotal(Float.parseFloat(rs.getString("total")));
                credito.setFondo_Garantia(Float.parseFloat(rs.getString("fondo_garantia")));
                credito.setSueldo(Float.parseFloat(rs.getString("sueldo")));
                credito.setDomicilio(rs.getString("domicilio"));
                credito.setQna(rs.getString("qna"));
                credito.setQnaDescuento(rs.getString("qnadescuento"));
                credito.setNumeroEmpleado(rs.getString("numeroempleado"));
                credito.setClabe(Integer.parseInt(rs.getString("clabe")));
                credito.setTransferencia(Integer.parseInt(rs.getString("transferencia")));
                credito.setObservaciones(rs.getString("observaciones"));
                credito.setUsuarioAlta(rs.getString("usuarioalta"));

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
    
    public void imprimemandato(Prestamo credito) {
        
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        JasperReport reporte = null;
        String path = "src\\reportes\\MandatoDescto.jasper";
        Map parametro = new HashMap();
        parametro.put("Parameter1",credito.getFolio());
        
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