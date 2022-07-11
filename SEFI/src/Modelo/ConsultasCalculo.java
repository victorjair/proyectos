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
 * @author SERCOM
 */
public class ConsultasCalculo extends Conexion {
    
    
     public boolean cuotasissste(Issste cuota){
          PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connota = getConexionnota();
        String sql = "SELECT * from issste where periodo = ? ";
         try {

            ps = connota.prepareStatement(sql);
            ps.setInt(1,2018);
            rs = ps.executeQuery();
            if (rs.next()) {
                cuota.setD_03(rs.getString("d03"));
                cuota.setD_04(rs.getString("d04"));
                cuota.setD_21(rs.getString("d21"));
                cuota.setD_22(rs.getString("d22"));
                cuota.setD_23(rs.getString("d23"));
                cuota.setFactor(rs.getString("factor"));
                cuota.setFactor2(rs.getString("factor2"));
          
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
     
     
     public boolean salariominimo(SalarioMinimo salario){
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connota = getConexionnota();
        String sql = "select * from salariominimo where ano = ? ";
        try {

            ps = connota.prepareStatement(sql);
            ps.setInt(1,salario.getYear());
            rs = ps.executeQuery();
            if (rs.next()) {
                
                 
               
                salario.setYear(rs.getInt("ano"));
                salario.setZonaa(rs.getString("zonaa"));
                salario.setZonab(rs.getString("zonab"));
                salario.setZonac(rs.getString("zonac"));
          
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
    
     
      public boolean calculoisr(TablaISR calculo){
        
         Double intermedio = 0.0;
         PreparedStatement ps = null;
         ResultSet rs = null;
         Connection con = getConexionnota();
        String sql = "SELECT round((? - LimiteInferior)*((PorCientoParaAplicarse)/100),2)+ cuotafija as calculo " +
        "FROM isrcalculoretiro where ?  between Limiteinferior and Limitesuperior and periodo = ?";
        
        //String sql = "SELECT round((? - LimiteInferior)*((Porcentaje)/100),2)+ cuotafija as calculo " +
        //"FROM tablaisr where ?  between Limiteinferior and Limitesuperior";
         try {

            ps = con.prepareStatement(sql);
            ps.setDouble(1,calculo.getCalculoIsr());
            ps.setDouble(2,calculo.getCalculoIsr());
            ps.setInt(3,calculo.getPeriodotabla());
            rs = ps.executeQuery();
            if (rs.next()) {
            calculo.setIntermedio(rs.getFloat("calculo"));
                
            return true;

            } else {
                return false;

            }

        } catch(Exception de){
        
          JOptionPane.showMessageDialog(null, de.getMessage());
          return false;
        }
       
        
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                 
                System.err.println(e);

            }

        }
      
     }
      
      
      public boolean calculosubsidio(TablaISR calculo){
         PreparedStatement ps = null;
         ResultSet rs = null;
        Connection con = getConexionnota();
        String sql = "SELECT round((? - subsidio)/2,2) as calculosubsidio " +
        "FROM tablasubsidiocalculo where ?  between LimiteInferior and LimiteSuperior and periodo = ?";
          try {

            ps = con.prepareStatement(sql);
            ps.setFloat(1,calculo.getIntermedio());
            ps.setDouble(2,calculo.getCalculoIsr());
            ps.setInt(3,calculo.getPeriodotabla());
            //ps.setFloat(2,calculo.getIntermedio());
            rs = ps.executeQuery();
            if (rs.next()) {
            calculo.setRCalculoIsr(rs.getDouble("calculosubsidio"));
            
            return true;

            } else {
                return false;

            }

        }
        catch(SQLException de){
        
          JOptionPane.showMessageDialog(null, de.getMessage());
          return false;
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                 
                System.err.println(e);

            }

        }
     }
      
      
        
    public void imprimeCalculo(CalculoRetiro calculoretiro) {
        
        //Conexion con = new Conexion();
        //Connection conn = con.getConexion();
        JasperReport reporte = null;
        String path = "src\\reportes\\CalculoRetiro.jasper";
        Map parametro = new HashMap();
        
        parametro.put("parameter1",calculoretiro.getP1());
        parametro.put("parameter2",calculoretiro.getP3());
        parametro.put("parameter3",calculoretiro.getP14());
        parametro.put("parameter4",calculoretiro.getP18());
        parametro.put("parameter5",calculoretiro.getP19());
        parametro.put("parameter6",calculoretiro.getPpg());
        parametro.put("parameter7",calculoretiro.getPpe());
        parametro.put("parameter8",calculoretiro.getD01());
        parametro.put("parameter9",calculoretiro.getSumaissste());
       
        parametro.put("parameter10",calculoretiro.getP1cp());
        parametro.put("parameter11",calculoretiro.getP3cp());
        parametro.put("parameter12",calculoretiro.getP14cp());
        parametro.put("parameter13",calculoretiro.getP18cp());
        parametro.put("parameter14",calculoretiro.getP19cp());
        parametro.put("parameter15",calculoretiro.getPpgcp());
        parametro.put("parameter16",calculoretiro.getPpecp());
        parametro.put("parameter17",calculoretiro.getD01cp());
        parametro.put("parameter18",calculoretiro.getSumaissstecp());
        
        parametro.put("parameter19",calculoretiro.getP1dif());
        parametro.put("parameter20",calculoretiro.getP3dif());
        parametro.put("parameter21",calculoretiro.getP14dif());
        parametro.put("parameter22",calculoretiro.getP18dif());
        parametro.put("parameter23",calculoretiro.getP19dif());
        parametro.put("parameter24",calculoretiro.getPpgdif());
        parametro.put("parameter25",calculoretiro.getPpedif());
        parametro.put("parameter26",calculoretiro.getD01dif());
        parametro.put("parameter27",calculoretiro.getSumaissstedif());
       
        
        parametro.put("parameter28",calculoretiro.getAportacionqnalalprograma());
        parametro.put("parameter29",calculoretiro.getAportacionanualalprograma());
        parametro.put("parameter30",calculoretiro.getPeriododecotizacion());
        parametro.put("parameter31",calculoretiro.getAportaciontotal());
        parametro.put("parameter32",calculoretiro.getFondoderetirovoluntario());
       
         parametro.put("parameter33",calculoretiro.getPeriodoInicio());
        parametro.put("parameter34",calculoretiro.getPeriodoFinal());
        parametro.put("parameter35",calculoretiro.getNombre());
       
        
        try {
            
            
                    reporte = (JasperReport
                    ) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro ,new JREmptyDataSource());
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
            

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(frmPrestamos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    
    }
}
