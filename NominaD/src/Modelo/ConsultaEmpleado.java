/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class ConsultaEmpleado extends Conexion {
    public static final String SEPARATOR = ",";
    public static final String QUOTE = "\"";
    
    
    
      public boolean isrcampos(TablaISR isr){
          PreparedStatement ps = null;
            ResultSet rs = null;
            Connection con = getConexion();
          String sql = "SELECT * from tablaisr where periodo = ? ";
          
             try {

            ps = con.prepareStatement(sql);
            ps.setInt(1,2018);
            rs = ps.executeQuery();
            if (rs.next()) {
                isr.setLimiteInferior(rs.getFloat("LimiteInferior"));
                isr.setLimiteSuperior(rs.getFloat("LimiteSuperior"));
                isr.setCuotaFija(rs.getFloat("CuotaFija"));
                isr.setPorcentaje(rs.getFloat("Porcentaje"));
                isr.setAplicaDias(rs.getFloat("AplicaDias"));
          
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
    
     
     public boolean cuotasissste(Issste cuota){
          PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "SELECT * from issste where periodo = ? ";
         try {

            ps = con.prepareStatement(sql);
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
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }
        
     }
    
     
     public boolean calcularcuotasindical(Empleado persona){
         PreparedStatement ps = null;
         ResultSet rs = null;
         Connection con = getConexion();
         String sql = "select cvecuotasindical,round(((P_01+P_01A+P_01B+P_01C+P_01D+P_01E)*1)/100,2) as cuotas from hojaisp where numeroquincena = ? and empleado = ?";
          try {

             ps = con.prepareStatement(sql);
             ps.setInt(1, persona.getNumeroquincena());
             ps.setInt(2, persona.getEmpleado());
             rs = ps.executeQuery();
             if (rs.next()) {
                 persona.setMontocuotasindical(rs.getFloat("cuotas"));
                 persona.setCveCuotaSindical(rs.getString("cvecuotasindical"));
               
                  return true;

             } else {
                 return false;

             }

         } catch (SQLException de) {

             JOptionPane.showMessageDialog(null, de.getMessage());
             return false;
         } finally {
             try {
                 con.close();
             } catch (SQLException e) {

                 System.err.println(e);

             }

         }
     
     }
     
     public boolean calcularantiguedad(Empleado persona){
         PreparedStatement ps = null;
         ResultSet rs = null;
         Connection con = getConexion();
         String sql = "select P_03,round(((P_01+P_01A+P_01B+P_01C+P_01D+P_01E) * (select porcentaje from antiguedad where antiguedad = TIMESTAMPDIFF(YEAR,STR_TO_DATE(fechaold,'%d/%m/%Y'), CURDATE()))/100),2) as antiguedadmonto,"
                 + "(select porcentaje from antiguedad where antiguedad = TIMESTAMPDIFF(YEAR,STR_TO_DATE(fechaold,'%d/%m/%Y'), CURDATE()))as porcentaje,"
                 + "rfc,concat(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,(P_01+P_01A+P_01B+P_01C+P_01D+P_01E)AS sueldo,empleado,\n"
                 + "	TIMESTAMPDIFF(YEAR,STR_TO_DATE(fechaold,'%d/%m/%Y'), CURDATE()) as antiguedad from hojaisp where numeroquincena = ? and empleado = ?   order by rfc\n"
                 + "";
         try {

             ps = con.prepareStatement(sql);
             ps.setInt(1, persona.getNumeroquincena());
             ps.setInt(2, persona.getEmpleado());
             rs = ps.executeQuery();
             if (rs.next()) {
                 persona.setP_03(rs.getFloat("antiguedadmonto"));

                 return true;

             } else {
                 return false;

             }

         } catch (SQLException de) {

             JOptionPane.showMessageDialog(null, de.getMessage());
             return false;
         } finally {
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
        Connection con = getConexion();
        String sql = "SELECT round((? - subsidio)/2,2) as calculosubsidio " +
        "FROM tablasubsidio where ?  between LimiteInferior and LimiteSuperior";
          try {

            ps = con.prepareStatement(sql);
            ps.setFloat(1,calculo.getIntermedio());
            ps.setDouble(2,calculo.getCalculoIsr());
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
     
     public boolean calculoisr(TablaISR calculo){
        
         Double intermedio = 0.0;
         PreparedStatement ps = null;
         ResultSet rs = null;
         Connection con = getConexion();
        String sql = "SELECT round((? - LimiteInferior)*((PorCientoParaAplicarse)/100),2)+ cuotafija as calculo " +
        "FROM isr_copy2 where ?  between Limiteinferior and Limitesuperior";
        
        //String sql = "SELECT round((? - LimiteInferior)*((Porcentaje)/100),2)+ cuotafija as calculo " +
        //"FROM tablaisr where ?  between Limiteinferior and Limitesuperior";
         try {

            ps = con.prepareStatement(sql);
            ps.setDouble(1,calculo.getCalculoIsr());
            ps.setDouble(2,calculo.getCalculoIsr());
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
     
    public boolean salariominimo(SalarioMinimo salario){
        
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "select * from salariominimo where ano = ? ";
        try {

            ps = con.prepareStatement(sql);
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
                con.close();
            } catch (SQLException e) {

                System.err.println(e);

            }

        }
    
    }
    
    public boolean calcularneto(Empleado persona){
      PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "select (P_01+P_01A+P_01B+P_01C+P_01D+P_01E)as salariobase,(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 "
                + "+ P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20 + P_21 + "
                + "P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ "
                + "P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 + P_35 + P_38 + "
                + "P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + "
                + "P_44 + P_45 + P_46 + P_47) as percepciones,(D_01 + D_10 + D_CB + D_02 + D_03 + D_04 "
                + "+ D_05 + D_06 + D_07 + D_08 + D_09 + D_11 + D_12 + D_13 + D_14 + D_15+ D_16 + D_17 "
                + "+ D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + D_27 + D_28 + D_29"
                + " + D_30 + D_31 + D_32+ D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 "
                + "+ D_42 + D_01A+ D_43+ D_44+ D_45+ D_46+ D_47+ D_48+ D_49+ D_50)as deducciones,"
                + "(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 "
                + "+ P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20 + P_21 + "
                + "P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ "
                + "P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 + P_35 + P_38 + "
                + "P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + "
                + "P_44 + P_45 + P_46 + P_47)- (D_01 + D_10 + D_CB + D_02 + D_03 + D_04 "
                + "+ D_05 + D_06 + D_07 + D_08 + D_09 + D_11 + D_12 + D_13 + D_14 + D_15+ D_16 + D_17 "
                + "+ D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + D_27 + D_28 + D_29"
                + " + D_30 + D_31 + D_32+ D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 "
                + "+ D_42 + D_01A+ D_43+ D_44+ D_45+ D_46+ D_47+ D_48+ D_49+ D_50) as neto from hojaisp where empleado = ? and numeroquincena = ? ";
        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1,persona.getEmpleado());
            ps.setInt(2,persona.getNumeroquincena());
            
            rs = ps.executeQuery();
            if (rs.next()) {
                
                 
               
                persona.setPercepcionesEmpleado(rs.getDouble("percepciones"));
                persona.setDeduccionesEmpleado(rs.getDouble("deducciones"));
                persona.setNetoEmpleado(rs.getDouble("neto"));
                persona.setSalarioBase(rs.getDouble("salariobase"));
         
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
    
    public boolean calcularnetonota(Empleado persona){
      PreparedStatement ps = null;
      ResultSet rs = null;
      Connection con = getConexion();
      Connection connota = getConexionnota();
        
        String sql = "select (P_01+P_01A+P_01B+P_01C+P_01D+P_01E)as salariobase,(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 "
                + "+ P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20 + P_21 + "
                + "P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ "
                + "P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 + P_35 + P_38 + "
                + "P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + "
                + "P_44 + P_45 + P_46 + P_47) as percepciones,(D_01 + D_10 + D_CB + D_02 + D_03 + D_04 "
                + "+ D_05 + D_06 + D_07 + D_08 + D_09 + D_11 + D_12 + D_13 + D_14 + D_15+ D_16 + D_17 "
                + "+ D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + D_27 + D_28 + D_29"
                + " + D_30 + D_31 + D_32+ D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 "
                + "+ D_42 + D_01A+ D_43+ D_44+ D_45+ D_46+ D_47+ D_48+ D_49+ D_50)as deducciones,"
                + "(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 "
                + "+ P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20 + P_21 + "
                + "P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ "
                + "P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 + P_35 + P_38 + "
                + "P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + "
                + "P_44 + P_45 + P_46 + P_47)- (D_01 + D_10 + D_CB + D_02 + D_03 + D_04 "
                + "+ D_05 + D_06 + D_07 + D_08 + D_09 + D_11 + D_12 + D_13 + D_14 + D_15+ D_16 + D_17 "
                + "+ D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + D_27 + D_28 + D_29"
                + " + D_30 + D_31 + D_32+ D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 "
                + "+ D_42 + D_01A+ D_43+ D_44+ D_45+ D_46+ D_47+ D_48+ D_49+ D_50) as neto from hojaicp where empleado = ? and numeroquincena = ? ";
        try {

            ps = connota.prepareStatement(sql);
            ps.setInt(1,persona.getEmpleado());
            ps.setInt(2,persona.getNumeroquincena());
                
            rs = ps.executeQuery();
            if (rs.next()) {
                
                 
               
                persona.setPercepcionesEmpleado(rs.getDouble("percepciones"));
                persona.setDeduccionesEmpleado(rs.getDouble("deducciones"));
                persona.setNetoEmpleado(rs.getDouble("neto"));
                persona.setSalarioBase(rs.getDouble("salariobase"));
         
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
    public boolean bajaempleado(Empleado persona){
    PreparedStatement ps = null;
        Connection con = getConexion();

        //String sql = "DELETE from afiliado WHERE rfc =?";
        String sql = "UPDATE hojaisp SET status = ? , observacion = ? WHERE empleado = ? and numeroquincena = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,"B");
            ps.setString(2,persona.getMotivoDeBaja());
            ps.setInt(3, persona.getEmpleado());
            ps.setInt(4, persona.getNumeroquincena());
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
    public boolean completarcamposcategoria(Empleado persona) {
    PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "select * from categoriascaptura where categorias = ?";
        try {
             ps = con.prepareStatement(sql);
             ps.setString (1, persona.getCategoria());

            rs = ps.executeQuery();
            if (rs.next()) {
                //credito.setFolio(Integer.parseInt(rs.getString("folio")));
                persona.setCveCategoria(rs.getString("cvecategoria"));
                persona.setNivel(rs.getString("cvecategoria"));
                persona.setHoras(rs.getString("horas"));
                return true;

            } else {
                return false;

            }

        }  catch (SQLException e) {

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
    
    public boolean completarcampos(Empleado persona) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "select letrero,numeroplantel,tipo,CASE zona WHEN 'A' THEN 1 WHEN 'B' THEN 2 WHEN 'C' THEN 3 END as zona  from plantelcaptura where numero = ? ";
        try {
             ps = con.prepareStatement(sql);
             ps.setString (1, persona.getPlantel());

            rs = ps.executeQuery();
            if (rs.next()) {
                //credito.setFolio(Integer.parseInt(rs.getString("folio")));
                persona.setNombrePlantel(rs.getString("letrero"));
                persona.setNumeroPlantel(rs.getString("numeroplantel"));
                persona.setTPlantel(rs.getString("tipo"));
                persona.setZona(rs.getString("zona"));
 
                return true;

            } else {
                return false;

            }

        }
        catch (SQLException e) {

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
    
    public boolean comprobarempleado (Empleado persona){
        Connection con = getConexion();
        String sqlprueba = "select * FROM  hojaisp  WHERE empleado = ? and numeroquincena = ?";
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        try {
            psVerifica = con.prepareStatement(sqlprueba);
            psVerifica.setInt(1, persona.getEmpleado());
            psVerifica.setInt(2, persona.getNumeroquincena());

            rsverifica = psVerifica.executeQuery();
            if (!rsverifica.next()) {

                return true;
            } else {
                JOptionPane.showMessageDialog(null, "El numero de empleado Ya Existe");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
     
    }
    
    public boolean comprobarqnanota (Empleado persona){
        Connection connota = getConexionnota();
       String sqlprueba = "select * FROM  hojaicp  WHERE  numeroquincena = ?";
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        try {
            psVerifica = connota.prepareStatement(sqlprueba);
            //psVerifica.setInt(1, persona.getEmpleado());
            psVerifica.setInt(1, persona.getNumeroquincena());
           rsverifica = psVerifica.executeQuery();
            if (!rsverifica.next()) {

                return true;
            } else {
                JOptionPane.showMessageDialog(null, "La Qna Ya Existe");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
     
    }
    public boolean registrar(Empleado persona) {
       
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO hojaisp(RFC,empleado,periodo,clavemvto,P_01,P_02,"
                + "P_03,P_04,P_05,P_06,P_07,P_08,P_09,P_10,P_11,P_12,P_13,P_14,P_15,P_16,P_17,P_18,P_19,"
                + "P_20,P_21,P_22,P_23,P_24,P_25,P_26,P_27,P_28,P_29,P_30,D_01,D_10,P_DI,P_C1,P_C2,P_31,P_32,"
                + "P_33,D_CB,D_02,D_03,D_04,D_05,D_06,D_07,D_08,D_09,D_11,D_12,D_13,D_14,D_15,D_16,D_17,D_18,"
                + "D_19,D_20,D_21,D_22,D_23,D_24,D_25,D_26,D_27,D_28,P_MC,P_PG,P_PE,P_36,D_29,P_37,D_30,D_31,P_35,"
                + "P_38,P_39,D_32,D_33,D_34,D_35,P_40,D_36,D_37,P_01A,P_01B,P_01C,P_01D,P_01E,P_34,D_38,P_41,P_42,"
                + "P_43,P_44,P_45,P_46,P_47,D_39,D_40,D_41,D_42,D_01A,D_43,D_44,D_45,D_46,TIPO,QUINCENA,LAPSO,"
                + "PLANTEL,NOPUESTO,LEIDO,AP_PAT,AP_MAT,NOMBRE,CATEGORIA,HORAS,NIVEL,FECHAOLD,TIPOPER,ZONA,SEMESTRE,"
                + "TPLANTEL,TIPOSIN,MUTUAL,FACORE,CEDULAIMSS,REGIMENPENSIONARIO,numeroquincena,faltas,curp,totdias,"
                + "hrsbase,hrslimit,hrsinter,cvecategoria,statusissste,usuario,fechamodificacionissste,saldoissste,"
                + "porcentajehipotecario,porcentajeseccion,porcentajeahorroissste,observacionfaltas,numerodepago,totaldepagos,"
                + "folioissste,numerocontrato,tipocontratos,nombreplantel,numeroplantel,P_48,P_49,P_50,D_47,D_48,D_49,D_50,"
                + "observacion,fechabajaissste,cveayudamutua,cvecuotasindical,caja_sutcobach,caja_seccion,caja_sitcobach,cajaseccion63"
                + ") "
                + ""
                + ""
                + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                + "       ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                + "       ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                + "       ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                + "       ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                + "       ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        
       
        
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, persona.getRfc());
            ps.setInt(2, persona.getEmpleado());
            ps.setInt(3, persona.getPeriodo());
            ps.setString(4, persona.getCvemvto());
            
            ps.setFloat(5, persona.getP_01());
            ps.setFloat(6, persona.getP_02());
            ps.setFloat(7, persona.getP_03());
            ps.setFloat(8, persona.getP_04());
            ps.setFloat(9, persona.getP_05());
            ps.setFloat(10, persona.getP_06());
            ps.setFloat(11, persona.getP_07());
            ps.setFloat(12, persona.getP_08());
            ps.setFloat(13, persona.getP_09());
            ps.setFloat(14, persona.getP_10());
            ps.setFloat(15, persona.getP_11());
            ps.setFloat(16, persona.getP_12());
            ps.setFloat(17, persona.getP_13());
            ps.setFloat(18, persona.getP_14());
            ps.setFloat(19, persona.getP_15());
            ps.setFloat(20, persona.getP_16());
            ps.setFloat(21, persona.getP_17());
            ps.setFloat(22, persona.getP_18());
            ps.setFloat(23, persona.getP_19());
            ps.setFloat(24, persona.getP_20());
            ps.setFloat(25, persona.getP_21());
            ps.setFloat(26, persona.getP_22());
            ps.setFloat(27, persona.getP_23());
            ps.setFloat(28, persona.getP_24());
            ps.setFloat(29, persona.getP_25());
            ps.setFloat(30, persona.getP_26());
            ps.setFloat(31, persona.getP_27());
            ps.setFloat(32, persona.getP_28());
            ps.setFloat(33, persona.getP_29());
            ps.setFloat(34, persona.getP_30());
            
            ps.setFloat(35, persona.getD_01());
            ps.setFloat(36, persona.getD_10());
            
            
            ps.setFloat(37, persona.getP_DI());
            ps.setFloat(38, persona.getP_C1());
            ps.setFloat(39, persona.getP_C2());
            ps.setFloat(40, persona.getP_31());
            ps.setFloat(41, persona.getP_32());
            ps.setFloat(42, persona.getP_33());
            ps.setFloat(43, persona.getD_CB());
            ps.setFloat(44, persona.getD_02());
            
            ps.setFloat(45, persona.getD_03());
            ps.setFloat(46, persona.getD_04());
            ps.setFloat(47, persona.getD_05());
            ps.setFloat(48, persona.getD_06());
            ps.setFloat(49, persona.getD_07());
            ps.setFloat(50, persona.getD_08());
            ps.setFloat(51, persona.getD_09());
            ps.setFloat(52, persona.getD_11());
            ps.setFloat(53, persona.getD_12());
            ps.setFloat(54, persona.getD_13());
            ps.setFloat(55, persona.getD_14());
            ps.setFloat(56, persona.getD_15());
            ps.setFloat(57, persona.getD_16());
            ps.setFloat(58, persona.getD_17());
            ps.setFloat(59, persona.getD_18());
            ps.setFloat(60, persona.getD_19());
            ps.setFloat(61, persona.getD_20());
            ps.setFloat(62, persona.getD_21());
            ps.setFloat(63, persona.getD_22());
            ps.setFloat(64, persona.getD_23());
            ps.setFloat(65, persona.getD_24());
            ps.setFloat(66, persona.getD_25());
            ps.setFloat(67, persona.getD_26());
            ps.setFloat(68, persona.getD_27());
            ps.setFloat(69, persona.getD_28());
            
            
            ps.setFloat(70, persona.getP_MC());
            ps.setFloat(71, persona.getP_PG());
            ps.setFloat(72, persona.getP_PE());
            
            
            ps.setFloat(73, persona.getP_36());
            ps.setFloat(74, persona.getD_29());
            ps.setFloat(75, persona.getP_37());
            ps.setFloat(76, persona.getD_30());
            ps.setFloat(77, persona.getD_31());
            
            
          ps.setFloat(78, persona.getP_35());
          ps.setFloat(79, persona.getP_38());
          ps.setFloat(80, persona.getP_39());
          ps.setFloat(81, persona.getD_32());
          ps.setFloat(82, persona.getD_33());
          ps.setFloat(83, persona.getD_34());
          ps.setFloat(84, persona.getD_35());
          ps.setFloat(85, persona.getP_40());
          ps.setFloat(86, persona.getD_36());
          
          ps.setFloat(87, persona.getD_37());
          ps.setFloat(88, persona.getP_01A());
          ps.setFloat(89, persona.getP_01B());
          ps.setFloat(90, persona.getP_01C());
          ps.setFloat(91, persona.getP_01D());
          ps.setFloat(92, persona.getP_01E());
          ps.setFloat(93, persona.getP_34());
          ps.setFloat(94, persona.getD_38());
          ps.setFloat(95, persona.getP_41());
           ps.setFloat(96, persona.getP_42());
           ps.setFloat(97, persona.getP_43());
           ps.setFloat(98, persona.getP_44());
           ps.setFloat(99, persona.getP_45());
           ps.setFloat(100, persona.getP_46());
           ps.setFloat(101, persona.getP_47());
           ps.setFloat(102, persona.getD_39());
           
           ps.setFloat(103, persona.getD_40());
           ps.setFloat(104, persona.getD_41());
           ps.setFloat(105, persona.getD_42());
           ps.setFloat(106, persona.getD_01A());
           ps.setFloat(107, persona.getD_43());
           ps.setFloat(108, persona.getD_44());
           ps.setFloat(109, persona.getD_45());
           ps.setFloat(110, persona.getD_46());
           
           
           ps.setString(111, persona.getTipo());
           ps.setString(112, persona.getQuincena());
           ps.setString(113, persona.getLapso());
           
           ps.setString(114, persona.getPlantel());
           ps.setString(115, persona.getNoPuesto());
           ps.setString(116, "OK");
           ps.setString(117, persona.getAP_PAT());
           ps.setString(118, persona.getAP_MAT());
           ps.setString(119, persona.getNombre());
           ps.setString(120, persona.getCategoria());
           ps.setString(121, persona.getHoras());
           ps.setString(122, persona.getNivel());
           ps.setString(123, persona.getFechaOld());
           ps.setString(124, persona.getTipoPer());
           ps.setString(125, persona.getZona());
           
            
            ps.setString(126, persona.getSemestre());
            ps.setString(127, persona.getTPlantel());
            ps.setString(128, persona.getTipoSin());
            ps.setString(129, persona.getMutual());
            ps.setString(130, persona.getFacore());
            ps.setString(131, persona.getCedulaImss());
            ps.setString(132, persona.getRegimenPensionario());
            ps.setInt(133, persona.getNumeroquincena());
            ps.setInt(134, persona.getNumeroInasistencias());
            
              ps.setString(135, persona.getCurp());
           ps.setString(136, "15");
           ps.setInt(137, persona.getHorasBase());
           ps.setInt(138, persona.getHorasLimitadas());
           ps.setInt(139, persona.getHorasInterinas());
           ps.setString(140, persona.getCveCategoria());
           ps.setString(141, persona.getStatus());
           ps.setString(142, "admin");
           ps.setString(143, persona.getFechaModificacionissste());
           ps.setString(144, persona.getSaldoissste());
           ps.setFloat(145, persona.getPorcentajeHipotecarioIssste());
           ps.setFloat(146, persona.getPorcentajeseccion31());
           ps.setFloat(147, persona.getPorcentajeissste());
           ps.setString(148, persona.getFechaInasistencias());
                   
                   
           ps.setString(149, persona.getNumeroDePago());
           ps.setString(150, persona.getTotalDePago());
            ps.setString(151, persona.getFolioissste());
          
           ps.setString(152, persona.getNumeroDeContrato());
           ps.setString(153, persona.getTipoContrato());
           ps.setString(154, persona.getNombrePlantel());
           ps.setString(155, persona.getNumeroPlantel());
           
            ps.setFloat(156, persona.getP_48());
           ps.setFloat(157, persona.getP_49());
           ps.setFloat(158, persona.getP_50());
           ps.setFloat(159, persona.getD_47());
           ps.setFloat(160, persona.getD_48());
           ps.setFloat(161, persona.getD_49());
          ps.setFloat(162, persona.getD_50());
         
           
           ps.setString(163, persona.getObservacion());
          ps.setString(164, persona.getFechaDeBajaIssste());
          ps.setString(165, persona.getCveAyudaMutua());
          ps.setString(166, persona.getCveCuotaSindical());
          ps.setString(167, persona.getCajaSutcobach());
          ps.setString(168, persona.getCajaSeccion());
          ps.setString(169, persona.getCajaSitcobach());
          ps.setString(170, persona.getCajaSeccion63());
         
             
            /*
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
            ps.setDouble(17, bd.doubleValue());*/

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
    
    public boolean modificar(Empleado persona) {
        
        PreparedStatement ps = null;
        Connection con = getConexion();

         
        
        String sql = "UPDATE hojaisp set RFC=?,periodo=?,clavemvto=?,P_01=?,P_02=?,"
                + "P_03=?,P_04=?,P_05=?,P_06=?,P_07=?,P_08=?,P_09=?,P_10=?,P_11=?,P_12=?,P_13=?,P_14=?,P_15=?,P_16=?,P_17=?,P_18=?,P_19=?,"
                + "P_20=?,P_21=?,P_22=?,P_23=?,P_24=?,P_25=?,P_26=?,P_27=?,P_28=?,P_29=?,P_30=?,D_01=?,D_10=?,P_DI=?,P_C1=?,P_C2=?,P_31=?,P_32=?,"
                + "P_33=?,D_CB=?,D_02=?,D_03=?,D_04=?,D_05=?,D_06=?,D_07=?,D_08=?,D_09=?,D_11=?,D_12=?,D_13=?,D_14=?,D_15=?,D_16=?,D_17=?,D_18=?,"
                + "D_19=?,D_20=?,D_21=?,D_22=?,D_23=?,D_24=?,D_25=?,D_26=?,D_27=?,D_28=?,P_MC=?,P_PG=?,P_PE=?,P_36=?,D_29=?,P_37=?,D_30=?,D_31=?,P_35=?,"
                + "P_38=?,P_39=?,D_32=?,D_33=?,D_34=?,D_35=?,P_40=?,D_36=?,D_37=?,P_01A=?,P_01B=?,P_01C=?,P_01D=?,P_01E=?,P_34=?,D_38=?,P_41=?,P_42=?,"
                + "P_43=?,P_44=?,P_45=?,P_46=?,P_47=?,D_39=?,D_40=?,D_41=?,D_42=?,D_01A=?,D_43=?,D_44=?,D_45=?,D_46=?,TIPO=?,QUINCENA=?,LAPSO=?,"
                + "PLANTEL=?,NOPUESTO=?,LEIDO=?,AP_PAT=?,AP_MAT=?,NOMBRE=?,CATEGORIA=?,HORAS=?,NIVEL=?,FECHAOLD=?,TIPOPER=?,ZONA=?,SEMESTRE=?,"
                + "TPLANTEL=?,TIPOSIN=?,MUTUAL=?,FACORE=?,CEDULAIMSS=?,REGIMENPENSIONARIO=?,faltas=?,curp=?,totdias=?,"
                + "hrsbase=?,hrslimit=?,hrsinter=?,cvecategoria=?,statusissste=?,usuario=?,fechamodificacionissste=?,saldoissste=?,"
                + "porcentajehipotecario=?,porcentajeseccion=?,porcentajeahorroissste=?,observacionfaltas=?,numerodepago=?,totaldepagos=?,"
                + "folioissste=?,numerocontrato=?,tipocontratos=?,nombreplantel=?,numeroplantel=?,P_48=?,P_49=?,P_50=?,D_47=?,D_48=?,D_49=?,D_50=?,"
                + "observacion=?,fechabajaissste=?,cveayudamutua=?,cvecuotasindical=?,caja_sutcobach=?,caja_seccion=?,caja_sitcobach=?,cajaseccion63=?"
                + "where empleado = ? and numeroquincena = ?   ";

      
               try {

                     ps = con.prepareStatement(sql);
          
                   ps.setString(1, persona.getRfc());
                   //ps.setInt(2, persona.getPeriodo());
                   //ps.setString(3, persona.getCvemvto());
                   ps.setInt(2, 0);
                   ps.setString(3,"0");

                   ps.setFloat(4, persona.getP_01());
                   ps.setFloat(5, persona.getP_02());
                   ps.setFloat(6, persona.getP_03());
                   ps.setFloat(7, persona.getP_04());
                   ps.setFloat(8, persona.getP_05());
                   ps.setFloat(9, persona.getP_06());
                   ps.setFloat(10, persona.getP_07());
                   ps.setFloat(11, persona.getP_08());
                   ps.setFloat(12, persona.getP_09());
                   ps.setFloat(13, persona.getP_10());
                   ps.setFloat(14, persona.getP_11());
                   ps.setFloat(15, persona.getP_12());
                   ps.setFloat(16, persona.getP_13());
                   ps.setFloat(17, persona.getP_14());
                   ps.setFloat(18, persona.getP_15());
                   ps.setFloat(19, persona.getP_16());
                   ps.setFloat(20, persona.getP_17());
                   ps.setFloat(21, persona.getP_18());
                   ps.setFloat(22, persona.getP_19());
                   ps.setFloat(23, persona.getP_20());
                   ps.setFloat(24, persona.getP_21());
                   ps.setFloat(25, persona.getP_22());
                   ps.setFloat(26, persona.getP_23());
                   ps.setFloat(27, persona.getP_24());
                   ps.setFloat(28, persona.getP_25());
                   ps.setFloat(29, persona.getP_26());
                   ps.setFloat(30, persona.getP_27());
                   ps.setFloat(31, persona.getP_28());
                   ps.setFloat(32, persona.getP_29());
                   ps.setFloat(33, persona.getP_30());

                   ps.setFloat(34, persona.getD_01());
                   ps.setFloat(35, persona.getD_10());

                   //ps.setFloat(36, persona.getP_DI());
                   //ps.setFloat(37, persona.getP_C1());
                   //ps.setFloat(38, persona.getP_C2());
                   ps.setFloat(36, 0);
                   ps.setFloat(37, 0);
                   ps.setFloat(38, 0);
                   ps.setFloat(39, persona.getP_31());
                   ps.setFloat(40, persona.getP_32());
                   ps.setFloat(41, persona.getP_33());
                   ps.setFloat(42, persona.getD_CB());
                   ps.setFloat(43, persona.getD_02());

                   ps.setFloat(44, persona.getD_03());
                   ps.setFloat(45, persona.getD_04());
                   ps.setFloat(46, persona.getD_05());
                   ps.setFloat(47, persona.getD_06());
                   ps.setFloat(48, persona.getD_07());
                   ps.setFloat(49, persona.getD_08());
                   ps.setFloat(50, persona.getD_09());
                   ps.setFloat(51, persona.getD_11());
                   ps.setFloat(52, persona.getD_12());
                   ps.setFloat(53, persona.getD_13());
                   ps.setFloat(54, persona.getD_14());
                   ps.setFloat(55, persona.getD_15());
                   ps.setFloat(56, persona.getD_16());
                   ps.setFloat(57, persona.getD_17());
                   ps.setFloat(58, persona.getD_18());
                   ps.setFloat(59, persona.getD_19());
                   ps.setFloat(60, persona.getD_20());
                   ps.setFloat(61, persona.getD_21());
                   ps.setFloat(62, persona.getD_22());
                   ps.setFloat(63, persona.getD_23());
                   ps.setFloat(64, persona.getD_24());
                   ps.setFloat(65, persona.getD_25());
                   ps.setFloat(66, persona.getD_26());
                   ps.setFloat(67, persona.getD_27());
                   ps.setFloat(68, persona.getD_28());

                   //ps.setFloat(69, persona.getP_MC());
                   //ps.setFloat(70, persona.getP_PG());
                   //ps.setFloat(71, persona.getP_PE());

                   ps.setFloat(69, 0);
                   ps.setFloat(70, 0);
                   ps.setFloat(71, 0);

                   ps.setFloat(72, persona.getP_36());
                   ps.setFloat(73, persona.getD_29());
                   ps.setFloat(74, persona.getP_37());
                   ps.setFloat(75, persona.getD_30());
                   ps.setFloat(76, persona.getD_31());

                   ps.setFloat(77, persona.getP_35());
                   ps.setFloat(78, persona.getP_38());
                   ps.setFloat(79, persona.getP_39());
                   ps.setFloat(80, persona.getD_32());
                   ps.setFloat(81, persona.getD_33());
                   ps.setFloat(82, persona.getD_34());
                   ps.setFloat(83, persona.getD_35());
                   ps.setFloat(84, persona.getP_40());
                   ps.setFloat(85, persona.getD_36());

                   ps.setFloat(86, persona.getD_37());
                   ps.setFloat(87, persona.getP_01A());
                   ps.setFloat(88, persona.getP_01B());
                   ps.setFloat(89, persona.getP_01C());
                   ps.setFloat(90, persona.getP_01D());
                   ps.setFloat(91, persona.getP_01E());
                   ps.setFloat(92, persona.getP_34());
                   ps.setFloat(93, persona.getD_38());
                   ps.setFloat(94, persona.getP_41());
                   ps.setFloat(95, persona.getP_42());
                   ps.setFloat(96, persona.getP_43());
                   ps.setFloat(97, persona.getP_44());
                   ps.setFloat(98, persona.getP_45());
                   ps.setFloat(99, persona.getP_46());
                   ps.setFloat(100, persona.getP_47());
                   ps.setFloat(101, persona.getD_39());

                   ps.setFloat(102, persona.getD_40());
                   ps.setFloat(103, persona.getD_41());
                   ps.setFloat(104, persona.getD_42());
                   //ps.setFloat(105, persona.getD_01A());
                   ps.setFloat(105, 0);
                   
                
                  
                  ps.setFloat(106, persona.getD_43());
                   ps.setFloat(107, persona.getD_44());
                   ps.setFloat(108, persona.getD_45());
                   ps.setFloat(109, persona.getD_46());

                   ps.setString(110, persona.getTipo());
                   ps.setString(111, persona.getQuincena());
                   ps.setString(112, persona.getLapso());

                   ps.setString(113, persona.getPlantel());
                   ps.setString(114, persona.getNoPuesto());
                   ps.setString(115, persona.getLeido());
                   ps.setString(116, persona.getAP_PAT());
                   ps.setString(117, persona.getAP_MAT());
                   ps.setString(118, persona.getNombre());
                   ps.setString(119, persona.getCategoria());
                   ps.setString(120, persona.getHoras());
                   ps.setString(121, persona.getNivel());
                   ps.setString(122, persona.getFechaOld());
                   ps.setString(123, persona.getTipoPer());
                   ps.setString(124, persona.getZona());
                   
                   ps.setString(125, persona.getSemestre());
                   ps.setString(126, persona.getTPlantel());
                   ps.setString(127, persona.getTipoSin());
                   ps.setString(128, persona.getMutual());
                   ps.setString(129, persona.getFacore());
                   ps.setString(130, persona.getCedulaImss());
                   ps.setString(131, persona.getRegimenPensionario());
                   ps.setInt(132, persona.getNumeroInasistencias());

                   ps.setString(133, persona.getCurp());
                   ps.setString(134, "15");
                   ps.setInt(135, persona.getHorasBase());
                   ps.setInt(136, persona.getHorasLimitadas());
                   ps.setInt(137, persona.getHorasInterinas());
                   ps.setString(138, persona.getCveCategoria());
                   ps.setString(139, persona.getStatus());
                   ps.setString(140, "admin");
                   ps.setString(141, persona.getFechaModificacionissste());
                   ps.setString(142, persona.getSaldoissste());
                   ps.setFloat(143, persona.getPorcentajeHipotecarioIssste());
                   ps.setFloat(144, persona.getPorcentajeseccion31());
                   ps.setFloat(145, persona.getPorcentajeissste());
                   ps.setString(146, persona.getFechaInasistencias());
                   
                   
                   ps.setString(147, persona.getNumeroDePago());
                   ps.setString(148, persona.getTotalDePago());
                   ps.setString(149, persona.getFolioissste());

                   ps.setString(150, persona.getNumeroDeContrato());
                   ps.setString(151, persona.getTipoContrato());
                   ps.setString(152, persona.getNombrePlantel());
                   ps.setString(153, persona.getNumeroPlantel());

                   ps.setFloat(154, persona.getP_48());
                   ps.setFloat(155, persona.getP_49());
                   ps.setFloat(156, persona.getP_50());
                   ps.setFloat(157, persona.getD_47());
                   ps.setFloat(158, persona.getD_48());
                   ps.setFloat(159, persona.getD_49());
                   ps.setFloat(160, persona.getD_50());
                 

                   /*ps.setString(161, persona.getObservacion());
                   ps.setString(162, persona.getFechaDeBajaIssste());
                   ps.setString(163, persona.getCveAyudaMutua());
                   ps.setString(164, persona.getCveCuotaSindical());
                   ps.setString(165, persona.getCajaSutcobach());
                   ps.setString(166, persona.getCajaSeccion());
                   ps.setString(167, persona.getCajaSitcobach());
                   ps.setString(168, persona.getCajaSeccion63());*/
                 
                   ps.setString(161, "0");
                   ps.setString(162, "0");
                   ps.setString(163, "0");
                   ps.setString(164, "0");
                   ps.setString(165, "0");
                   ps.setString(166, "0");
                   ps.setString(167, "0");
                   ps.setString(168, "0");
                   
                    ps.setInt(169, persona.getEmpleado());
                    ps.setInt(170, persona.getNumeroquincena());
               
                   
                    /*ps.setInt(169, 6500);
                    ps.setInt(170, 202150);*/
               
                  
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
    
    public boolean buscar(Empleado persona) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT * from hojaisp where empleado = ? and numeroquincena = ? ";

        try {

            ps = con.prepareStatement(sql);
            //ps.setString(1, persona.getRfc());
            ps.setInt(1, persona.getEmpleado());
            ps.setInt(2, persona.getNumeroquincena());
           
            rs = ps.executeQuery();

            if (rs.next()) {

                
                /*persona.setNombre(rs.getString("nombre"));
                persona.setPuesto(rs.getString("puesto"));
                persona.setCelular(rs.getString("celular"));*/
                persona.setEmpleado(rs.getInt("empleado"));
                persona.setRfc(rs.getString("RFC"));
                persona.setPeriodo(rs.getInt("periodo"));
                persona.setCvemvto(rs.getString("clavemvto"));
                persona.setP_01(rs.getFloat("P_01"));
                persona.setP_02(rs.getFloat("P_02"));
                persona.setP_03(rs.getFloat("P_03"));
                persona.setP_04(rs.getFloat("P_04"));
                persona.setP_05(rs.getFloat("P_05"));
                persona.setP_06(rs.getFloat("P_06"));
                persona.setP_07(rs.getFloat("P_07"));
                persona.setP_08(rs.getFloat("P_08"));
                persona.setP_09(rs.getFloat("P_09"));
                persona.setP_10(rs.getFloat("P_10"));
                persona.setP_11(rs.getFloat("P_11"));
                persona.setP_12(rs.getFloat("P_12"));
                persona.setP_13(rs.getFloat("P_13"));
                persona.setP_14(rs.getFloat("P_14"));
                persona.setP_15(rs.getFloat("P_15"));
                persona.setP_16(rs.getFloat("P_16"));
                persona.setP_17(rs.getFloat("P_17"));
                persona.setP_18(rs.getFloat("P_18"));
                persona.setP_19(rs.getFloat("P_19"));
                persona.setP_20(rs.getFloat("P_20"));
                persona.setP_21(rs.getFloat("P_21"));
                persona.setP_22(rs.getFloat("P_22"));
                persona.setP_23(rs.getFloat("P_23"));
                persona.setP_24(rs.getFloat("P_24"));
                persona.setP_25(rs.getFloat("P_25"));
                persona.setP_26(rs.getFloat("P_26"));
                persona.setP_27(rs.getFloat("P_27"));
                persona.setP_28(rs.getFloat("P_28"));
                persona.setP_29(rs.getFloat("P_29"));
                persona.setP_30(rs.getFloat("P_30"));
                persona.setD_01(rs.getFloat("D_01"));
                persona.setD_10(rs.getFloat("D_10"));
                persona.setP_DI(rs.getFloat("P_DI"));
                persona.setP_C1(rs.getFloat("P_C1"));
                persona.setP_C2(rs.getFloat("P_C2"));
                persona.setP_31(rs.getFloat("P_31"));
                persona.setP_32(rs.getFloat("P_32"));
                persona.setP_33(rs.getFloat("P_33"));
                persona.setD_CB(rs.getFloat("D_CB"));
                
                persona.setD_02(rs.getFloat("D_02"));
                persona.setD_03(rs.getFloat("D_03"));
                persona.setD_04(rs.getFloat("D_04"));
                persona.setD_05(rs.getFloat("D_05"));
                persona.setD_06(rs.getFloat("D_06"));
                persona.setD_07(rs.getFloat("D_07"));
                persona.setD_08(rs.getFloat("D_08"));
                persona.setD_09(rs.getFloat("D_09"));
                persona.setD_11(rs.getFloat("D_11"));
                
                persona.setD_12(rs.getFloat("D_12"));
                persona.setD_13(rs.getFloat("D_13"));
                persona.setD_14(rs.getFloat("D_14"));
                persona.setD_15(rs.getFloat("D_15"));
                persona.setD_16(rs.getFloat("D_16"));
                persona.setD_17(rs.getFloat("D_17"));
                persona.setD_18(rs.getFloat("D_18"));
                persona.setD_19(rs.getFloat("D_19"));
                persona.setD_20(rs.getFloat("D_20"));
                persona.setD_21(rs.getFloat("D_21"));
                persona.setD_22(rs.getFloat("D_22"));
                persona.setD_23(rs.getFloat("D_23"));
               
                persona.setD_24(rs.getFloat("D_24"));
                persona.setD_25(rs.getFloat("D_25"));
                persona.setD_26(rs.getFloat("D_26"));
                persona.setD_27(rs.getFloat("D_27"));
                persona.setD_28(rs.getFloat("D_28"));
              
                persona.setP_MC(rs.getFloat("P_MC"));
               persona.setP_PG(rs.getFloat("P_PG"));
               persona.setP_PE(rs.getFloat("P_PE"));
               
               persona.setP_36(rs.getFloat("P_36"));
               persona.setD_29(rs.getFloat("D_29"));
               persona.setP_37(rs.getFloat("P_37"));
               persona.setD_30(rs.getFloat("D_30"));
               persona.setD_31(rs.getFloat("D_31"));
               persona.setP_35(rs.getFloat("P_38"));
               persona.setP_39(rs.getFloat("P_39"));
               
               persona.setD_32(rs.getFloat("D_32"));
               persona.setD_33(rs.getFloat("D_33"));
               persona.setD_34(rs.getFloat("D_34"));
               persona.setD_35(rs.getFloat("D_35"));
               persona.setP_40(rs.getFloat("P_40"));
               persona.setD_36(rs.getFloat("D_36"));
               persona.setD_37(rs.getFloat("D_37"));
               persona.setP_01A(rs.getFloat("P_01A"));
               persona.setP_01B(rs.getFloat("P_01B"));
               persona.setP_01C(rs.getFloat("P_01C"));
               persona.setP_01D(rs.getFloat("P_01D"));
               persona.setP_01E(rs.getFloat("P_01E"));
               
               persona.setP_34(rs.getFloat("P_34"));
               persona.setD_38(rs.getFloat("D_38"));
               persona.setP_41(rs.getFloat("P_41"));
               persona.setP_42(rs.getFloat("P_42"));
               persona.setP_43(rs.getFloat("P_43"));
               persona.setP_44(rs.getFloat("P_44"));
               persona.setP_45(rs.getFloat("P_45"));
               persona.setP_46(rs.getFloat("P_46"));
               persona.setP_47(rs.getFloat("P_47"));
               persona.setD_39(rs.getFloat("D_39"));
               persona.setD_40(rs.getFloat("D_40"));
               persona.setD_41(rs.getFloat("D_41"));
               persona.setD_42(rs.getFloat("D_42"));
               
               persona.setD_01A(rs.getFloat("D_01A"));
               persona.setD_43(rs.getFloat("D_43"));
               persona.setD_44(rs.getFloat("D_44"));
               persona.setD_45(rs.getFloat("D_45"));
               persona.setD_46(rs.getFloat("D_46"));
               
               
                persona.setTipo(rs.getString("TIPO"));
                persona.setQuincena(rs.getString("QUINCENA"));
                persona.setLapso(rs.getString("LAPSO"));
                persona.setTipo(rs.getString("TIPO"));
                persona.setPlantel(rs.getString("PLANTEL"));
                persona.setNoPuesto(rs.getString("NOPUESTO"));
                persona.setLeido(rs.getString("LEIDO"));
                persona.setAP_PAT(rs.getString("AP_PAT"));
                persona.setAP_MAT(rs.getString("AP_MAT"));
                persona.setNombre(rs.getString("NOMBRE"));
                persona.setCategoria(rs.getString("CATEGORIA"));
                persona.setHoras(rs.getString("HORAS"));
                persona.setNivel(rs.getString("NIVEL"));
                persona.setFechaOld(rs.getString("FECHAOLD"));
                persona.setTipoPer(rs.getString("TIPOPER"));
                persona.setZona(rs.getString("ZONA"));
                persona.setSemestre(rs.getString("SEMESTRE"));
                persona.setTPlantel(rs.getString("TPLANTEL"));
                persona.setTipoSin(rs.getString("TIPOSIN"));
                persona.setMutual(rs.getString("MUTUAL"));
                persona.setFacore(rs.getString("FACORE"));
                persona.setCedulaImss(rs.getString("CEDULAIMSS"));
                persona.setRegimenPensionario(rs.getString("REGIMENPENSIONARIO"));
                persona.setCedulaImss(rs.getString("CEDULAIMSS"));
                persona.setNumeroquincena(rs.getInt("numeroquincena"));
                persona.setNumeroInasistencias(rs.getInt("faltas"));
                persona.setCurp(rs.getString("curp"));
                persona.setTotalDias(rs.getInt("totdias"));
                persona.setHorasBase(rs.getInt("hrsbase"));
                persona.setHorasLimitadas(rs.getInt("hrslimit"));

               persona.setCveCategoria(rs.getString("cvecategoria"));
               persona.setStatus(rs.getString("status"));
               persona.setUsuario(rs.getString("usuario"));
                persona.setFechaModificacionissste(rs.getString("fechamodificacionissste"));
                persona.setSaldoissste(rs.getString("saldoissste"));
                persona.setPorcentajeHipotecarioIssste(rs.getFloat("porcentajehipotecario"));
                persona.setPorcentajeseccion31(rs.getFloat("porcentajeseccion"));
                persona.setPorcentajeissste(rs.getFloat("porcentajeahorroissste"));
                persona.setFechaInasistencias(rs.getString("observacionfaltas"));
                persona.setNumeroDePago(rs.getString("numerodepago"));
                persona.setTotalDePago(rs.getString("totaldepagos"));
                persona.setFolioissste(rs.getString("folioissste"));
                persona.setNumeroDeContrato(rs.getString("numerocontrato"));
                persona.setTipoContrato(rs.getString("tipocontratos"));
                persona.setNombrePlantel(rs.getString("nombreplantel"));
                persona.setNumeroPlantel(rs.getString("numeroplantel"));
                
                 persona.setP_48(rs.getFloat("P_48"));
                persona.setP_49(rs.getFloat("P_49"));
                persona.setP_50(rs.getFloat("P_50"));
                persona.setD_47(rs.getFloat("D_47"));
                persona.setD_48(rs.getFloat("D_48"));
                persona.setD_49(rs.getFloat("D_49"));
                persona.setD_50(rs.getFloat("D_50"));
                
                 persona.setObservacion(rs.getString("observacion"));
                 persona.setFechaDeBajaIssste(rs.getString("fechabajaissste"));
                 persona.setCveAyudaMutua(rs.getString("cveayudamutua"));
                 persona.setCveCuotaSindical(rs.getString("cvecuotasindical"));
                 persona.setCajaSutcobach(rs.getString("caja_sutcobach"));
                 persona.setCajaSeccion(rs.getString("caja_seccion"));
                 persona.setCajaSitcobach(rs.getString("caja_sitcobach"));
                 persona.setCajaSeccion63(rs.getString("cajaseccion63"));
               
                 

                
               

                  
               
               
               
               

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
    
    public boolean dobleplaza(Empleado persona){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "select count(rfc) as totalplazas from hojaisp where rfc = ? and numeroquincena = ?";
          try {

            ps = con.prepareStatement(sql);
            ps.setString(1, persona.getRfc());
            ps.setInt(2, persona.getNumeroquincena());
            rs = ps.executeQuery();
            if (rs.next()) {

                persona.setNumerodeplazas(rs.getInt("totalplazas"));
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
    
    public boolean empleadoganamas(Empleado persona){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "select (P_01+P_01A+P_01B+P_01C+P_01D+P_01E) as suma,empleado from hojaisp"
                + "	        where numeroquincena = ? and rfc in (?) order by suma desc limit 1";
        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, persona.getNumeroquincena());
            ps.setString(2, persona.getRfc());
            rs = ps.executeQuery();
            if (rs.next()) {

              persona.setNumerodeempleadoganamas(rs.getInt("empleado"));
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
    
    public boolean empleadoganamasnota(Empleado persona){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        Connection connota = getConexionnota();
       
        //String sql = "select (P_01+P_01A+P_01B+P_01C+P_01D+P_01E) as suma,empleado from hojaisp"
         //       + "	        where numeroquincena = ? and rfc in (?) order by suma desc limit 1";
        String sql = "select empleado from hojai where (D_01 > 0 or P_14 > 0) and numeroquincena = ? "
                + "and rfc = ? order by empleado limit 1  ";

         try {

            ps = connota.prepareStatement(sql);
            ps.setInt(1, persona.getNumeroquincena());
            ps.setString(2, persona.getRfc());
            rs = ps.executeQuery();
            if (rs.next()) {

              persona.setNumerodeempleadoganamas(rs.getInt("empleado"));
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
    public boolean actualizarempleadoganamas(Empleado persona) {
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "update hojaisp set D_01=?,D_03=?,D_04=?,D_21=?,D_22=?,D_23=?,P_14=?"
                + " where numeroquincena = ? and empleado = ? ";

         
              try {
                     ps = con.prepareStatement(sql);
                  ps.setDouble(1, persona.getD_01masdeunaplaza());
                  ps.setDouble(2, persona.getD_03masdeunaplaza());
                  ps.setDouble(3, persona.getD_04masdeunaplaza());
                  ps.setDouble(4, persona.getD_21masdeunaplaza());
                  ps.setDouble(5, persona.getD_22masdeunaplaza());
                  ps.setDouble(6, persona.getD_23masdeunaplaza());
                  ps.setDouble(7, persona.getP_14masdeunaplaza());
                  ps.setInt(8, persona.getNumeroquincena());
                  ps.setInt(9, persona.getNumerodeempleadoganamas());
                   
               
                  
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
    
    public boolean actualizarempleadoganamasnota(Empleado persona) {
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        Connection connota = getConexionnota();
        String sql = "update hojaicp set P_01=?,P_01A=?,P_01B=?,P_01C=?,P_01D=?,P_01E=?,P_18=?,P_19=?,P_PG=?,P_PE=?, "
                + "D_01=?,D_03=?,D_04=?,D_21=?,D_22=?,D_23=?,P_14=? where numeroquincena = ? and empleado = ? ";
               

         
              try {
                     ps = connota.prepareStatement(sql);
                  
                  ps.setDouble(1, persona.getP01Update());
                  ps.setDouble(2, persona.getP01AUpdate());
                  ps.setDouble(3, persona.getP01BUpdate());
                  ps.setDouble(4, persona.getP01CUpdate());
                  ps.setDouble(5, persona.getP01DUpdate());
                  ps.setDouble(6, persona.getP01EUpdate());
                  ps.setDouble(7, persona.getP018Update());
                  ps.setDouble(8, persona.getP019Update());
                  ps.setDouble(9, persona.getPPGUpdate());
                  ps.setDouble(10, persona.getPPEUpdate());
                  
                  ps.setDouble(11, persona.getD01Update());
                  ps.setDouble(12, persona.getD_03masdeunaplaza());
                  ps.setDouble(13, persona.getD_04masdeunaplaza());
                  ps.setDouble(14, persona.getD_21masdeunaplaza());
                  ps.setDouble(15, persona.getD_22masdeunaplaza());
                  ps.setDouble(16, persona.getD_23masdeunaplaza());
                  ps.setDouble(17, persona.getP14Update());
                  ps.setInt(18, persona.getNumeroquincena());
                  ps.setInt(19, persona.getEmpleado());
                   
               
                  
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
    
    public boolean actualizarprevision(Empleado persona) {
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        Connection connota = getConexionnota();
        String sql = "update hojaicp set P_PE = ? where numeroquincena = ? and empleado = ? ";
               

         
              try {
                     ps = connota.prepareStatement(sql);
                  
                 
                  ps.setDouble(1, persona.getPPEUpdate());
                  ps.setInt(2, persona.getNumeroquincena());
                  ps.setInt(3, persona.getEmpleado());
                   
               
                  
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
    
    public boolean actualizarotrosempleados(Empleado persona) {
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "update hojaisp set D_01=?,D_03=?,D_04=?,D_21=?,D_22=?,D_23=?,P_14=?"
                + " where numeroquincena = ? and rfc = ? and empleado <> ? ";

         
              try {
                  ps = con.prepareStatement(sql);
                  ps.setDouble(1,0);
                  ps.setDouble(2,0);
                  ps.setDouble(3,0);
                  ps.setDouble(4,0);
                  ps.setDouble(5,0);
                  ps.setDouble(6,0);
                  ps.setDouble(7,0);
                  ps.setInt(8, persona.getNumeroquincena());
                  ps.setString(9, persona.getRfc());
                  ps.setInt(10, persona.getNumerodeempleadoganamas());
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
     
     public boolean sumadobleplaza(Empleado persona){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "select sum(P_01+P_01B+P_01C+P_01D+P_01E) as salariosuma,"
                + "sum(P_03)as sumaantiguedad,sum(P_18) as sumap18,sum(P_19) as sumap19 from hojaisp where rfc = ? and numeroquincena = ?";
          try {

            ps = con.prepareStatement(sql);
            ps.setString(1, persona.getRfc());
            ps.setInt(2, persona.getNumeroquincena());
            rs = ps.executeQuery();
            if (rs.next()) {

                persona.setSalariosuma(rs.getDouble("salariosuma"));
                persona.setAntiguedadsuma(rs.getDouble("sumaantiguedad"));
                persona.setSumap18(rs.getDouble("sumap18"));
                persona.setSumap19(rs.getDouble("sumap19"));
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
    public boolean secuenciaalta(Pension pension){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String sql = "select count(*)+1 as secuencia from pension where empleado = ? and status in ('a','A')";
          try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, pension.getEmpleado());
            rs = ps.executeQuery();
            if (rs.next()) {

                pension.setSecuencia(rs.getInt("secuencia"));
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
    public boolean guardarpension(Pension pension) {
       
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO pension(empleado,porcentaje,beneficiario,plantel,oficio,montodescuento"
                + ",status,clave,rfc,secuencia,numeroquincena) Values(?,?,?,?,?,?,?,?,?,?,?)";
  try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, pension.getEmpleado());
            ps.setFloat(2, pension.getPorcentaje());
            ps.setString(3, pension.getBeneficiario());
            ps.setString(4, pension.getPlantel());
            ps.setString(5, pension.getOficio());
            ps.setFloat(6, pension.getMontodescuento());
            ps.setString(7, "A");
            ps.setString(8, "D_10");
            ps.setString(9, pension.getRfc());
            ps.setInt(10, pension.getSecuencia());
            ps.setString(11, pension.getNumeroQuincena());

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
    
    public boolean modificarpension(Pension pension) {
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE pension set porcentaje=?,beneficiario = ?,plantel = ?,oficio = ?,montodescuento = ?, rfc = ?,"
              + "numeroquincena = ? where Id = ? ";
        
         
              try {
                     ps = con.prepareStatement(sql);
                    
               
                      ps.setFloat(1, pension.getPorcentaje());
                     ps.setString(2, pension.getBeneficiario());
                     ps.setString(3, pension.getPlantel());
                     ps.setString(4, pension.getOficio());
                     ps.setFloat(5, pension.getMontodescuento());
                     ps.setString(6, pension.getRfc());
                     ps.setString(7, pension.getNumeroQuincena());
                     ps.setString(8, pension.getIdPension());
                   
               
                  
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
    
    public boolean buscarpension(Pension pension) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT * from pension where Id = ?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, pension.getIdPension());
            rs = ps.executeQuery();
            if (rs.next()) {

                pension.setIdPension(rs.getString("Id"));
                pension.setSecuencia(rs.getInt("secuencia"));
                pension.setEmpleado(rs.getInt("empleado"));
                pension.setPorcentaje(rs.getFloat("porcentaje"));
                pension.setBeneficiario(rs.getString("beneficiario"));
                pension.setPlantel(rs.getString("plantel"));
                pension.setOficio(rs.getString("oficio"));
                pension.setMontodescuento(rs.getFloat("montodescuento"));
                pension.setStatus(rs.getString("status"));
                pension.setRfc(rs.getString("rfc"));
                pension.setNumeroQuincena(rs.getString("numeroquincena"));

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
 
    public boolean bajapension(Pension pension){
    PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE pension SET status = ? Where Id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,"B");
            ps.setString(2, pension.getIdPension());
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
    
    
 public boolean guardarprestamo(Prestamos prestamo) {
       
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "INSERT INTO prestamos(folio,empleado,monto,interes,descuento,total"
                + ",plantel,status,saldo,clave,numeroquincenainicio,numeroquincena,prioridad,plazo) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, prestamo.getFolio());
            ps.setFloat(2, prestamo.getEmpleado());
            ps.setFloat(3, prestamo.getMonto());
            ps.setFloat(4, prestamo.getInteres());
            ps.setFloat(5, prestamo.getDescuento());
            ps.setFloat(6, prestamo.getTotal());
            ps.setString(7,prestamo.getPlantel());
            ps.setString(8, "A");
            ps.setFloat(9, prestamo.getSaldo());
            ps.setString(10, prestamo.getClaveDescuento());
            ps.setString(11, prestamo.getNumeroquincenaInicio());
            ps.setString(12, prestamo.getNumeroquincenaPrestamo());
            ps.setString(13,"1");
            ps.setInt(14,prestamo.getPlazo());
           
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
 
 public boolean modificarprestamo(Prestamos prestamo) {
        
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE prestamos set folio=?,monto = ?,interes = ?,descuento = ?,total = ?, plantel = ?,"
              + "plazo = ?,saldo=?,clave=?,numeroquincenainicio=?,numeroquincena=?,prioridad=? where Id = ? ";
        
         
              try {
                     ps = con.prepareStatement(sql);
                    
               
                      ps.setInt(1, prestamo.getFolio());
                     ps.setFloat(2, prestamo.getMonto());
                     ps.setFloat(3, prestamo.getInteres());
                     ps.setFloat(4, prestamo.getDescuento());
                     ps.setFloat(5, prestamo.getTotal());
                     ps.setString(6, prestamo.getPlantel());
                     ps.setInt(7, prestamo.getPlazo());
                     ps.setFloat(8, prestamo.getSaldo());
                     ps.setString(9, prestamo.getClaveDescuento());
                     ps.setString(10, prestamo.getNumeroquincenaInicio());
                     ps.setString(11, prestamo.getNumeroquincenaPrestamo());
                     ps.setInt(12, prestamo.getPrioridad());
                     ps.setString(13, prestamo.getIdPrestamo());
                   
               
                  
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
 
 public boolean buscarprestamo(Prestamos prestamo) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT * from prestamos where Id = ?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, prestamo.getIdPrestamo());
            rs = ps.executeQuery();
            if (rs.next()) {

                prestamo.setFolio(rs.getInt("folio"));
                prestamo.setMonto(rs.getFloat("monto"));
                prestamo.setInteres(rs.getFloat("interes"));
                prestamo.setDescuento(rs.getFloat("descuento"));
                prestamo.setTotal(rs.getFloat("total"));
                prestamo.setPlantel(rs.getString("plantel"));
                prestamo.setPlazo(rs.getInt("plazo"));
                prestamo.setStatus(rs.getString("status"));
                prestamo.setSaldo(rs.getFloat("saldo"));
                prestamo.setClaveDescuento(rs.getString("clave"));
                prestamo.setNumeroquincenaInicio(rs.getString("numeroquincenainicio"));
                prestamo.setNumeroquincenaPrestamo(rs.getString("numeroquincena"));
                prestamo.setPrioridad(rs.getInt("prioridad"));

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
 
 public boolean bajaprestamo(Prestamos prestamo){
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE prestamos SET status = ? Where Id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,"B");
            ps.setString(2, prestamo.getIdPrestamo());
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
    public ArrayList buscarbeneficiariopension(Pension pension) {

        ArrayList<Pension> Listabeneficiario = new ArrayList<Pension>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT beneficiario,porcentaje,oficio,montodescuento,status,secuencia,Id from pension where empleado = ? and status in ('a','A') ";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1,pension.getEmpleado());
            rs = ps.executeQuery();
            
            while (rs.next()) {

                Pension lista = new Pension(rs.getString("beneficiario"),rs.getFloat("porcentaje"), rs.getString("oficio"),
                        rs.getFloat("montodescuento"), rs.getString("status"), rs.getInt("secuencia"), rs.getString("Id"));

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
    
    public ArrayList buscarbeneficiarioprestamos(Prestamos prestamo) {

        ArrayList<Prestamos> Listabeneficiario = new ArrayList<Prestamos>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        Connection con = getConexion();

        String sql = "SELECT clave,folio,monto,interes,plazo,total,descuento,"
                + "saldo,numeroquincenainicio,status,prioridad,Id from prestamos where empleado = ?  ";

        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1,prestamo.getEmpleado());
            rs = ps.executeQuery();
            
            while (rs.next()) {

                Prestamos lista = new Prestamos(rs.getString("clave"),rs.getInt("folio"), rs.getFloat("monto"),
                        rs.getFloat("interes"), rs.getInt("plazo"), rs.getFloat("total"), rs.getFloat("descuento"),
                 rs.getFloat("saldo"), rs.getString("numeroquincenainicio"), rs.getString("status"), rs.getInt("prioridad"),
                 rs.getString("Id"));

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
    
    private static String[] removeTrailingQuotes(String[] fields) {

        String result[] = new String[fields.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = fields[i].replaceAll("^" + QUOTE, "").replaceAll(QUOTE + "$", "");
        }
        return result;
    }
    
public void importararchivolayout(Empleado persona) throws FileNotFoundException, IOException {
 

        Connection con = getConexion();
        String sqlprueba = "select * FROM  hojaisp  WHERE numeroquincena = ? and empleado = 100000";
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        BufferedReader br = null;
        String claveparaimportar = persona.getClaveImportar();
        try {
            psVerifica = con.prepareStatement(sqlprueba);
            psVerifica.setString(1, persona.getNumeroQuincenaImportar());
            rsverifica = psVerifica.executeQuery();
            if (!rsverifica.next()) {
                

        try {

            br = new BufferedReader(new FileReader(persona.getRuta()));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                //  System.out.println(Arrays.toString(fields));

                fields = removeTrailingQuotes(fields);
                //System.out.println(Arrays.toString(fields));
                ///////////EMPIEZA LA INSERCION A LA BASE DE DATOS
                PreparedStatement ps = null;
                //Connection con = getConexion();
                String sql = "UPDATE hojaisp set " + claveparaimportar  + " =? where empleado = ? and numeroquincena = ?";
                
                
                try {

                    ps = con.prepareStatement(sql);
                    ps.setString(1, fields[0]);
                    ps.setString(2, fields[1]);
                    ps.setString(3, fields[2]);
                    
                    ps.execute();
                    line = br.readLine();

                    //return true;
                } catch (SQLException e) {
                    System.err.println(e);
                    //return false;

                } 
            }

        } catch (Exception e) {

        }
        finally {
                    try {
                        con.close();

                    } catch (SQLException e) {

                        System.err.println(e);

                    }

                }
                

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo dar de alta");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        

    }
    
public void importararchivolayoutprestamos(Empleado persona) throws FileNotFoundException, IOException {
 

        Connection con = getConexion();
        String sqlprueba = "select * FROM  hojaisp  WHERE numeroquincena = ? and empleado = 100000";
     
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        BufferedReader br = null;
        String claveparaimportar = persona.getClaveImportar();
        try {
            psVerifica = con.prepareStatement(sqlprueba);
            psVerifica.setString(1, persona.getNumeroQuincenaImportar());
            rsverifica = psVerifica.executeQuery();
            if (!rsverifica.next()) {
                

        try {

            br = new BufferedReader(new FileReader(persona.getRuta()));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                //  System.out.println(Arrays.toString(fields));

                fields = removeTrailingQuotes(fields);
                //System.out.println(Arrays.toString(fields));
                ///////////EMPIEZA LA INSERCION A LA BASE DE DATOS
                PreparedStatement ps = null;
                //Connection con = getConexion();
                
                          String sql = "INSERT INTO prestamos(folio,empleado,monto,interes,descuento,"
                            + "total"
                + ",plantel,status,saldo,clave,numeroquincenainicio,numeroquincena,prioridad,plazo) Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
 
                
                
                try {

                    ps = con.prepareStatement(sql);
                    ps.setString(1, fields[0]);
                    ps.setString(2, fields[1]);
                    ps.setString(3, fields[2]);
                    ps.setString(4, fields[3]);
                    ps.setString(5, fields[4]);
                    ps.setString(6, fields[5]);
                    ps.setString(7, fields[6]);
                    ps.setString(8, fields[7]);
                    ps.setString(9, fields[8]);
                    ps.setString(10, fields[9]);
                    ps.setString(11, fields[10]);
                    ps.setString(12, fields[11]);
                    ps.setString(13, fields[12]);
                    ps.setString(14, fields[13]);
                    
                    ps.execute();
                    line = br.readLine();

                    //return true;
                } catch (SQLException e) {
                    System.err.println(e);
                    //return false;

                } 
            }

        } catch (Exception e) {

        }
        finally {
                    try {
                        con.close();

                    } catch (SQLException e) {

                        System.err.println(e);

                    }

                }
                

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo dar de alta");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        

    }



}
