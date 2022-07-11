/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class ConsultaEmpleado extends Conexion {
    
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
            psVerifica.setInt(1, persona.getNumeroquincena());

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
    
}
