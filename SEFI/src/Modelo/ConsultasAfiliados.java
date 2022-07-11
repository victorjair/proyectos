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
    
      public boolean registrarbeneficiarioSegundoTermino(Beneficiario benef){
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO beneficiariosegundotermino(rfcafiliado,nombre,noafiliado,parentesco,"
                + "fechanac) VALUES (?,?,?,?,?)";
        
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1,benef.getRfcAfiliado());
            ps.setString(2,benef.getNombre());
            //ps.setDouble(3, benef.getPorcentaje());
            ps.setInt(3,benef.getPrioridadBeneficiario());
            ps.setString(4, benef.getParentesco());
            ps.setString(5, benef.getFechanacimiento());
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
        
        Connection conremoto = getConexionnotaRemoto();
        PreparedStatement psremoto = null;
        
      
        String sql = "INSERT INTO afiliado(rfc,nombre,direccion,plantel,puesto,"
                + "telefono,celular,correo,status,"
                + "sueldobase,qnaafiliacion,numeroempleado,pass,fechaafiliacion,estadocivil,sexo,sindicato,fechaingresocobach) Values(?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, persona.getRfc());
            ps.setString(2, persona.getNombre());
            ps.setString(3, persona.getDireccion());
            ps.setString(4, persona.getPlantel());
            ps.setString(5, persona.getPuesto());
            ps.setString(6, persona.getTelefono());
            ps.setString(7, persona.getCelular());
            ps.setString(8, persona.getCorreo());
            ps.setString(9, "A");
            ps.setFloat(10, persona.getSueldoBase());
            ps.setString(11, persona.getQnaAfiliacion());
            ps.setString(12, persona.getNumeroEmpleado());
            ps.setString(13, persona.getRfc());
            ps.setString(14, persona.getFechaAfiliacion());
            ps.setString(15, persona.getEstadocivil());
            ps.setString(16, persona.getSexo());
            ps.setString(17, persona.getSindicato());
            ps.setString(18, persona.getFechaIngreso());
            
            

            ps.execute();
            
            
            psremoto = conremoto.prepareStatement(sql);
            psremoto.setString(1, persona.getRfc());
            psremoto.setString(2, persona.getNombre());
            psremoto.setString(3, persona.getDireccion());
            psremoto.setString(4, persona.getPlantel());
            psremoto.setString(5, persona.getPuesto());
            psremoto.setString(6, persona.getTelefono());
            psremoto.setString(7, persona.getCelular());
            psremoto.setString(8, persona.getCorreo());
            psremoto.setString(9, "A");
            psremoto.setFloat(10, persona.getSueldoBase());
            psremoto.setString(11, persona.getQnaAfiliacion());
            psremoto.setString(12, persona.getNumeroEmpleado());
            psremoto.setString(13, persona.getRfc());
            psremoto.setString(14, persona.getFechaAfiliacion());
            psremoto.setString(15, persona.getEstadocivil());
            psremoto.setString(16, persona.getSexo());
            psremoto.setString(17, persona.getSindicato());
            psremoto.setString(18, persona.getFechaIngreso());
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
    
     public boolean registrarbajaprograma(Afiliado persona) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        
        Connection connota = getConexionnota();
        PreparedStatement psremoto = null;
        
      
        String sql = "INSERT INTO jubilados(Nombre,Fecha_Entrega,QnaBaja,Numero_Empleado) Values"
                + "(?,?,?,?)";
                

        try {

            ps = connota.prepareStatement(sql);
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getFechabaja());
            ps.setString(3, persona.getQnabaja());
            ps.setString(4, persona.getNumeroEmpleado());
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
    
    
    public boolean modificarbeneficiario(Beneficiario benef) {
     PreparedStatement ps = null;
     Connection con = getConexion();
        
         
        String sql = "UPDATE beneficiario set nombre=?,parentesco=? "
                + ",fechanac=?,porcentaje=?"
                + "where Id = ? ";
        
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, benef.getNombre());
            ps.setString(2, benef.getParentesco());
            ps.setString(3, benef.getFechanacimiento());
            ps.setString(4, benef.getPorcentajecadena());
           
            ps.setString(5, benef.getId());
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
    
    
     public boolean modificarbeneficiariosegundotermino(Beneficiario benef) {
     PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE beneficiariosegundotermino set nombre=?,parentesco=? "
                + ",fechanac=?"
                + "where Id = ? ";
        
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, benef.getNombre());
            ps.setString(2, benef.getParentesco());
            ps.setString(3, benef.getFechanacimiento());
            
            ps.setString(4, benef.getId());
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
        
          Connection conremoto = getConexionnotaRemoto();
        PreparedStatement psremoto = null;
    

        String sql = "UPDATE afiliado set nombre=?,direccion=?,plantel=? "
                + ",puesto=?,telefono=?,celular=?,correo=?,qnaafiliacion=?"
                + ",numeroempleado=?,fechaactualizacion=?,estadocivil=?,sexo=?,sindicato=?,fechaingresocobach=?"
                + "where rfc = ?";

        try {

            ps = con.prepareStatement(sql);
            // ps.setString(1, persona.getRfc());
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getDireccion());
            ps.setString(3, persona.getPlantel());
            ps.setString(4, persona.getPuesto());
            //ps.setFloat(5, persona.getPorcentajeAhorro());
            ps.setString(5, persona.getTelefono());
            ps.setString(6, persona.getCelular());
            ps.setString(7, persona.getCorreo());
            ps.setString(8, persona.getQnaAfiliacion());
            //ps.setString(10, persona.getQnaDescuento());
            ps.setString(9, persona.getNumeroEmpleado());
            ps.setString(10, persona.getFechaAfiliacion());
            ps.setString(11, persona.getEstadocivil());
            ps.setString(12, persona.getSexo());
            ps.setString(13, persona.getSindicato());
            ps.setString(14, persona.getFechaIngreso());
            
            BigDecimal bd = new BigDecimal((persona.getPorcentajeAhorro() / persona.factor));
            bd = bd.setScale(6, BigDecimal.ROUND_HALF_EVEN);
            //ps.setDouble(13, bd.doubleValue());
            ps.setString(15, persona.getRfc());
            ps.execute();
            
            
            psremoto = conremoto.prepareStatement(sql);
            psremoto.setString(1, persona.getNombre());
            psremoto.setString(2, persona.getDireccion());
            psremoto.setString(3, persona.getPlantel());
            psremoto.setString(4, persona.getPuesto());
            psremoto.setString(5, persona.getTelefono());
            psremoto.setString(6, persona.getCelular());
            psremoto.setString(7, persona.getCorreo());
            psremoto.setString(8, persona.getQnaAfiliacion());
            psremoto.setString(9, persona.getNumeroEmpleado());
            psremoto.setString(10, persona.getFechaAfiliacion());
            psremoto.setString(11, persona.getEstadocivil());
            psremoto.setString(12, persona.getSexo());
            psremoto.setString(13, persona.getSindicato());
            psremoto.setString(14, persona.getFechaIngreso());
            BigDecimal bdremoto = new BigDecimal((persona.getPorcentajeAhorro() / persona.factor));
            bdremoto = bdremoto.setScale(6, BigDecimal.ROUND_HALF_EVEN);
            psremoto.setString(15, persona.getRfc());
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
    
    
    public boolean modificarbajaprograma(Afiliado persona) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        Connection connota = getConexionnota();
    
        PreparedStatement psremoto = null;
    

        String sql = "UPDATE jubilados set Nombre=?,Fecha_Entrega=?,QnaBaja=? where Numero_Empleado = ?";

        try {

            ps = connota.prepareStatement(sql);
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getFechabaja());
            ps.setString(3, persona.getQnabaja());
            ps.setString(4, persona.getNumeroEmpleado());
          
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
    
     public boolean eliminarbeneficiarioSegundoTermino(Beneficiario benef){
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "Delete from beneficiariosegundotermino where Id = ?";
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
        
            Connection conremoto = getConexionnotaRemoto();
        PreparedStatement psremoto = null;
    

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
            
            psremoto = conremoto.prepareStatement(sql);
            psremoto.setString(1, "B");
            psremoto.setString(2, persona.getMotivoBaja());
            psremoto.setString(3, persona.getFechaAfiliacion());
            psremoto.setString(4, Usuario.usuario);
            psremoto.setString(5, persona.getRfc());
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
public boolean eliminarbajaprograma(Afiliado persona) {

        PreparedStatement ps = null;
        Connection con = getConexion();
        Connection connota = getConexionnota();
        
        PreparedStatement psremoto = null;
    

        //String sql = "DELETE from afiliado WHERE rfc =?";
        String sql = "DELETE from jubilados where Numero_Empleado = ?";

        try {
            ps = connota.prepareStatement(sql);
            ps.setString(1,persona.getNumeroEmpleado());
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
                //persona.setPorcentajeAhorro(Float.parseFloat(rs.getString("porcentajeahorro")));
                persona.setQnaAfiliacion(rs.getString("qnaafiliacion"));
                //persona.setQnaDescuento(rs.getString("qnadescuento"));
                persona.setRfc(rs.getString("rfc"));
                persona.setSueldoBase(Float.parseFloat((rs.getString("sueldobase"))));
                persona.setTelefono(rs.getString("telefono"));
                persona.setEstadocivil(rs.getString("estadocivil"));
                persona.setSexo(rs.getString("sexo"));
                persona.setSindicato(rs.getString("sindicato"));
                persona.setFechaIngreso(rs.getString("fechaingresocobach"));

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
    
    
    public boolean buscarbajaprograma(Afiliado persona) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();
       Connection connota = getConexionnota();
       
        String sql = "SELECT * from jubilados where Numero_Empleado = ? ";

        try {

            ps = connota.prepareStatement(sql);
            ps.setString(1, persona.getNumeroEmpleado());
            rs = ps.executeQuery();

            if (rs.next()) {

                persona.setNombre(rs.getString("Nombre"));
                persona.setQnabaja(rs.getString("QnaBaja"));
                persona.setFechabaja(rs.getString("Fecha_Entrega"));
              
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
    
    public ArrayList buscarbeneficiarioSegundoTermino(Afiliado persona) {

        ArrayList<Beneficiario> Listabeneficiario = new ArrayList<Beneficiario>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

       String sql = "SELECT nombre,Id,CASE WHEN fechanac = '0000-00-00' then '2009-01-01' ELSE fechanac END as fechanac ,rfcafiliado,ifnull(porcentaje,0)as porcentaje,noafiliado,parentesco from beneficiariosegundotermino where rfcafiliado = ? ";

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
