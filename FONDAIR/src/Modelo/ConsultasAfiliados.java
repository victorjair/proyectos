/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import View.frmAfiliado;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class ConsultasAfiliados extends Conexion {
    
    public boolean completarquincena(Afiliado persona){
     
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT CASE WHEN  substr(MAX(numeroquincena),5,2) = 24 then "
                + "CONCAT(  substr(max(numeroquincena),1,4)+ 1   , '01')  else MAX(numeroquincena) + 1 "
                + "end   as maximaquincena  from qnarecuperaciongenerada  ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                //credito.setFolio(Integer.parseInt(rs.getString("folio")));
               persona.setQnaDescuento(rs.getString("maximaquincena"));

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
        
    
    
     public boolean completarquincenaactual(Afiliado persona){
     
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
               persona.setQnaAfiliacion(rs.getString("qnaactual"));

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
    
    
    public void autocompletar(frmAfiliado persona){
        TextAutoCompleter ac = new TextAutoCompleter(persona.txtRfc);
        TextAutoCompleter nom = new TextAutoCompleter(persona.txtNombre);
       
        Statement  st = null;
        ResultSet rs = null;
        Statement  stnombre = null;
        ResultSet rsnombre = null;
        Connection con = getConexion();
       
        try{
            
            st = (Statement)con.createStatement();
            rs= st.executeQuery("select  rfc from afiliado");
            while (rs.next()){
            ac.addItem(rs.getString("rfc"));
            }
        }
        catch(Exception de){
        
          JOptionPane.showMessageDialog(null, de.getMessage());
        
        }
        
        try{
            
            st = (Statement)con.createStatement();
            rs= st.executeQuery("select concat(nombre,' ',Id) as nombre  from afiliado");
            while (rs.next()){
            nom.addItem(rs.getString("nombre"));
            }
        }
        catch(Exception de){
        
          JOptionPane.showMessageDialog(null, de.getMessage());
        
        }
               
    
    }
    
    public void autocompletarnomina(frmAfiliado persona){
        TextAutoCompleter ac = new TextAutoCompleter(persona.txtRfc);
        TextAutoCompleter nom = new TextAutoCompleter(persona.txtNombre);
       
        Statement  st = null;
        ResultSet rs = null;
        Statement  stnombre = null;
        ResultSet rsnombre = null;
        Connection con = getConexion();
       
        try{
            
            st = (Statement)con.createStatement();
            rs= st.executeQuery("select nombre from hojai order by numeroquincena desc limit 1");
            while (rs.next()){
            ac.addItem(rs.getString("nombre"));
            }
        }
        catch(Exception de){
        
          JOptionPane.showMessageDialog(null, de.getMessage());
        
        }
        
        
               
    
    }
    
    

    public boolean registrarbeneficiario(Beneficiario benef){
        PreparedStatement ps = null;
        Connection con = getConexion();
        
      String sql = "INSERT INTO beneficiario(rfcafiliado,nombre,porcentaje,noafiliado,parentesco,"
                + "fechanac) VALUES (?,?,?,?,?,?)";
        
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1,benef.getRfcAfiliado());
            ps.setString(2,benef.getNombre());
            ps.setDouble(3, benef.getPorcentaje());
            ps.setInt(4,benef.getPrioridadBeneficiario());
            ps.setString(5, benef.getParentesco());
            ps.setString(6, benef.getFechanacimiento());
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
    
    public boolean registrar(Afiliado persona) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        
             PreparedStatement psremoto = null;
       Connection conremoto = getConexionnotaRemoto();
    
        String sql = "INSERT INTO afiliado(rfc,nombre,direccion,plantel,puesto,porcentajeahorro,"
                + "telefono,celular,correo,status,"
                + "sueldobase,qnaafiliacion,qnadescuento,numeroempleado,pass,fechaafiliacion,porcentajecp) Values(?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, persona.getRfc());
            ps.setString(2, persona.getNombre());
            ps.setString(3, persona.getDireccion());
            ps.setString(4, persona.getPlantel());
            ps.setString(5, persona.getPuesto());
            ps.setFloat(6, persona.getPorcentajeAhorro());
            ps.setString(7, persona.getTelefono());
            ps.setString(8, persona.getCelular());
            ps.setString(9, persona.getCorreo());
            ps.setString(10, "A");
            ps.setFloat(11, persona.getSueldoBase());
            ps.setString(12, persona.getQnaAfiliacion());
            ps.setString(13, persona.getQnaDescuento());
            ps.setString(14, persona.getNumeroEmpleado());
            ps.setString(15, persona.getRfc());
            ps.setString(16, persona.getFechaAfiliacion());
            BigDecimal bd = new BigDecimal((persona.getPorcentajeAhorro() / persona.factor));
            bd = bd.setScale(6, BigDecimal.ROUND_HALF_EVEN);
            ps.setDouble(17, bd.doubleValue());

            ps.execute();
            
            /*psremoto = conremoto.prepareStatement(sql);
            psremoto.setString(1, persona.getRfc());
            psremoto.setString(2, persona.getNombre());
            psremoto.setString(3, persona.getDireccion());
            psremoto.setString(4, persona.getPlantel());
            psremoto.setString(5, persona.getPuesto());
            psremoto.setFloat(6, persona.getPorcentajeAhorro());
            psremoto.setString(7, persona.getTelefono());
            psremoto.setString(8, persona.getCelular());
            psremoto.setString(9, persona.getCorreo());
            psremoto.setString(10, "A");
            psremoto.setFloat(11, persona.getSueldoBase());
            psremoto.setString(12, persona.getQnaAfiliacion());
            psremoto.setString(13, persona.getQnaDescuento());
            psremoto.setString(14, persona.getNumeroEmpleado());
            psremoto.setString(15, persona.getRfc());
            psremoto.setString(16, persona.getFechaAfiliacion());
            BigDecimal bdremoto = new BigDecimal((persona.getPorcentajeAhorro() / persona.factor));
            bdremoto = bdremoto.setScale(6, BigDecimal.ROUND_HALF_EVEN);
            psremoto.setDouble(17, bdremoto.doubleValue());
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
    
    
    public boolean reactivarafiliado(Afiliado persona) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String usuarioActual;
        
        String sql = "UPDATE afiliado set status=?,qnadescuento=? where Id = ?";
        try {

            ps = con.prepareStatement(sql);
            // ps.setString(1, persona.getRfc());
            ps.setString(1,"A");
            ps.setString(2, persona.getQnaDescuento());
            ps.setString(3, persona.getId());
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

    public boolean modificar(Afiliado persona) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String usuarioActual;
        PreparedStatement psremoto = null;
       Connection conremoto = getConexionnotaRemoto();
    
        
        String sql = "UPDATE afiliado set nombre=?,direccion=?,plantel=? "
                + ",puesto=?,porcentajeahorro=?,telefono=?,celular=?,correo=?,qnadescuento=?,qnaafiliacion=?"
                + ",numeroempleado=?,fechaactualizacion=?,porcentajecp=?,qnabaja=?,status=?,usuariomodificacion=?"
                + "where Id = ?";

        try {

            ps = con.prepareStatement(sql);
            // ps.setString(1, persona.getRfc());
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getDireccion());
            ps.setString(3, persona.getPlantel());
            ps.setString(4, persona.getPuesto());
            ps.setFloat(5, persona.getPorcentajeAhorro());
            ps.setString(6, persona.getTelefono());
            ps.setString(7, persona.getCelular());
            ps.setString(8, persona.getCorreo());
            ps.setString(9, persona.getQnaDescuento());
            ps.setString(10, persona.getQnaAfiliacion());
            ps.setString(11, persona.getNumeroEmpleado());
            ps.setString(12, persona.getFechaAfiliacion());
            BigDecimal bd = new BigDecimal((persona.getPorcentajeAhorro() / persona.factor));
            bd = bd.setScale(6, BigDecimal.ROUND_HALF_EVEN);
            ps.setDouble(13, bd.doubleValue());
            ps.setString(14, persona.getQnaBaja());
            ps.setString(15, persona.getStatus());
            usuarioActual = Usuario.usuario;
            ps.setString(16,usuarioActual);
            //ps.setString(16, persona.g);
            ps.setString(17, persona.getId());
            ps.execute();
            
            /*
            psremoto = conremoto.prepareStatement(sql);
            psremoto.setString(1, persona.getNombre());
            psremoto.setString(2, persona.getDireccion());
            psremoto.setString(3, persona.getPlantel());
            psremoto.setString(4, persona.getPuesto());
            psremoto.setFloat(5, persona.getPorcentajeAhorro());
            psremoto.setString(6, persona.getTelefono());
            psremoto.setString(7, persona.getCelular());
            psremoto.setString(8, persona.getCorreo());
            psremoto.setString(9, persona.getQnaDescuento());
            psremoto.setString(10, persona.getQnaAfiliacion());
            psremoto.setString(11, persona.getNumeroEmpleado());
            psremoto.setString(12, persona.getFechaAfiliacion());
            BigDecimal bdremoto = new BigDecimal((persona.getPorcentajeAhorro() / persona.factor));
            bdremoto = bdremoto.setScale(6, BigDecimal.ROUND_HALF_EVEN);
            psremoto.setDouble(13, bdremoto.doubleValue());
            psremoto.setString(14, persona.getQnaBaja());
            psremoto.setString(15, persona.getStatus());
            
            usuarioActual = Usuario.usuario;
            psremoto.setString(16,usuarioActual);
            psremoto.setString(17, persona.getId());
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
    
    public boolean eliminarbeneficiario(Beneficiario benef){
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "Delete from beneficiario where Id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,benef.getId());
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

    public boolean eliminar(Afiliado persona) {

        PreparedStatement ps = null;
        Connection con = getConexion();

       PreparedStatement psremoto = null;
       Connection conremoto = getConexionnotaRemoto();
    
        //String sql = "DELETE from afiliado WHERE rfc =?";
        String sql = "UPDATE afiliado SET status = ?,causabaja = ?,fechabaja = ?, usuariobaja = ?,qnabaja= ? WHERE Id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "B");
            ps.setString(2, persona.getMotivoBaja());
            ps.setString(3, persona.getFechaAfiliacion());
            ps.setString(4, Usuario.usuario);
            ps.setString(5, persona.getQnaBaja());
            ps.setString(6, persona.getId());
            ps.execute();
            
           /* psremoto = conremoto.prepareStatement(sql);
            psremoto.setString(1, "B");
            psremoto.setString(2, persona.getMotivoBaja());
            psremoto.setString(3, persona.getFechaAfiliacion());
            psremoto.setString(4, Usuario.usuario);
            psremoto.setString(5, persona.getQnaBaja());
            psremoto.setString(6, persona.getId());
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

    public boolean buscar(Afiliado persona) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT * from afiliado where Id = ? ";

        try {

            ps = con.prepareStatement(sql);
            //ps.setString(1, persona.getRfc());
            ps.setString(1, persona.getId());
           
            rs = ps.executeQuery();

            if (rs.next()) {

                persona.setNombre(rs.getString("nombre"));
                persona.setPuesto(rs.getString("puesto"));
                persona.setCelular(rs.getString("celular"));
                persona.setCorreo(rs.getString("correo"));
                persona.setDireccion(rs.getString("direccion"));
                persona.setNumeroEmpleado(rs.getString("numeroempleado"));
                persona.setNombre(rs.getString("nombre"));
                persona.setPlantel(rs.getString("plantel"));
                persona.setPorcentajeAhorro(Float.parseFloat(rs.getString("porcentajeahorro")));
                persona.setQnaAfiliacion(rs.getString("qnaafiliacion"));
                persona.setQnaDescuento(rs.getString("qnadescuento"));
                persona.setRfc(rs.getString("rfc"));
                persona.setSueldoBase(Float.parseFloat((rs.getString("sueldobase"))));
                persona.setTelefono(rs.getString("telefono"));
                persona.setStatus(rs.getString("status"));
                persona.setQnaBaja(rs.getString("qnabaja"));
                persona.setMotivoBaja(rs.getString("causabaja"));
                persona.setPorcentajeAhorroCp(Float.parseFloat(rs.getString("porcentajecp")));

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
    
    public boolean buscarnomina(Afiliado persona) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection connota = getConexionnota();

        String sql = "SELECT RFC,CATEGORIA,empleado,PLANTEL,P_01+P_01A+P_01B+P_01C+P_01D+P_01E as sueldo from hojaicp where CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) = ? order by numeroquincena desc limit 1  ";

        try {

            ps = connota.prepareStatement(sql);
            //ps.setString(1, persona.getRfc());
            ps.setString(1, persona.getNombre());
           
            rs = ps.executeQuery();

            if (rs.next()) {

                persona.setPuesto(rs.getString("CATEGORIA"));
               // persona.setCelular(rs.getString("celular"));
               // persona.setCorreo(rs.getString("correo"));
               // persona.setDireccion(rs.getString("direccion"));
                persona.setNumeroEmpleado(rs.getString("empleado"));
               // persona.setNombre(rs.getString("AP_PAT") +rs.getString("NOMBRE"));
                persona.setPlantel(rs.getString("PLANTEL"));
               // persona.setPorcentajeAhorro(Float.parseFloat(rs.getString("porcentajeahorro")));
               // persona.setQnaAfiliacion(rs.getString("qnaafiliacion"));
               // persona.setQnaDescuento(rs.getString("qnadescuento"));
                persona.setRfc(rs.getString("RFC"));
                persona.setSueldoBase(Float.parseFloat((rs.getString("sueldo"))));
               // persona.setTelefono(rs.getString("telefono"));

                return true;

            } else {
                return false;

            }

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

    public ArrayList buscarbeneficiario(Afiliado persona) {

        ArrayList<Beneficiario> Listabeneficiario = new ArrayList<Beneficiario>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT nombre,Id,CASE WHEN fechanac = '0000-00-00' then '2009-01-01' ELSE fechanac END as fechanac ,rfcafiliado,porcentaje,noafiliado,parentesco from beneficiario where rfcafiliado = ? ";

        try {

            ps = con.prepareStatement(sql);

            ps.setString(1, persona.getRfc());
            rs = ps.executeQuery();
            
            while (rs.next()) {

                Beneficiario lista = new Beneficiario(rs.getString("nombre"), Double.parseDouble(rs.getString("porcentaje")), rs.getString("parentesco"),
                        rs.getString("Id"), rs.getString("fechanac"), rs.getString("rfcafiliado"), Integer.parseInt(rs.getString("noafiliado")));

                Listabeneficiario.add(lista);

            }

        } catch (SQLException e) {

            System.err.println(e);
            //return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }

        return Listabeneficiario;
    }

}
