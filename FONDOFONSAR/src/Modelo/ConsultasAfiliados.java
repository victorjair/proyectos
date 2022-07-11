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

        String sql = "UPDATE afiliado set nombre=?,direccion=?,plantel=? "
                + ",puesto=?,porcentajeahorro=?,telefono=?,celular=?,correo=?,qnadescuento=?,qnaafiliacion=?"
                + ",numeroempleado=?,fechaactualizacion=?,porcentajecp=?"
                + "where rfc = ?";

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
            ps.setString(9, persona.getQnaAfiliacion());
            ps.setString(10, persona.getQnaDescuento());
            ps.setString(11, persona.getNumeroEmpleado());
            ps.setString(12, persona.getFechaAfiliacion());
            BigDecimal bd = new BigDecimal((persona.getPorcentajeAhorro() / persona.factor));
            bd = bd.setScale(6, BigDecimal.ROUND_HALF_EVEN);
            ps.setDouble(13, bd.doubleValue());
            ps.setString(14, persona.getRfc());
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

        //String sql = "DELETE from afiliado WHERE rfc =?";
        String sql = "UPDATE afiliado SET status = ?,causabaja = ?,fechabaja = ?, usuariobaja = ? WHERE rfc = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "B");
            ps.setString(2, persona.getMotivoBaja());
            ps.setString(3, persona.getFechaAfiliacion());
            ps.setString(4, Usuario.usuario);
            ps.setString(5, persona.getRfc());
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

    public boolean buscar(Afiliado persona) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT * from afiliado where nombre = ? ";

        try {

            ps = con.prepareStatement(sql);
            //ps.setString(1, persona.getRfc());
            ps.setString(1, persona.getNombre());
           
            rs = ps.executeQuery();

            if (rs.next()) {

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

        String sql = "SELECT * from beneficiario where rfcafiliado = ? ";

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
