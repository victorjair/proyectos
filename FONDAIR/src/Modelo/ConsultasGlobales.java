/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author victor
 */
public class ConsultasGlobales extends Conexion {
    
    private Double saldopatrimonio = 0.0;
    private Double ahorradopatrimonio = 0.0;
    private Double retirospatrimonio = 0.0;
    private Double interespatrimonio = 0.0;
    
    private String qnainicial;
    private String qnafinal;
    private Double saldo = 0.0;
    private Double abono = 0.0;
    private Double indebido = 0.0;
    private Double total = 0.0;
    private Double interestotal = 0.0;
    private Double monto = 0.0;
    private Double interesindebido = 0.0;
    private Double capitalindebido = 0.0;
    
   
    private Double interesrecuperado = 0.0;
    private Double interesxrecuperar = 0.0;
    private Double capitalrecuperado = 0.0;
    private Double capitalxrecuperar = 0.0;
    private Double indebidointeres = 0.0;
    private Double indebidocapital = 0.0;
    private String status;
    private String statuscompara;
    private String qnacancelacion;
   
     
    public void imprimeinformacionxqna(Globales mov){
         qnainicial = mov.getPeriodoi()+ mov.getNumeroi();
         qnafinal = mov.getPeriodof()+   mov.getNumerof();
         Connection con = getConexion();
         Workbook book = new XSSFWorkbook();
        Sheet ahorros = book.createSheet("Ahorros");
        Sheet recuperacion = book.createSheet("Recuperacion");
        Sheet prestamos = book.createSheet("Prestamos");
     
        Row row = ahorros.createRow(0);
        row.createCell(0).setCellValue("RFC");
        row.createCell(1).setCellValue("NOMBRE");
        row.createCell(2).setCellValue("PLANTEL");
        row.createCell(3).setCellValue("FACORE");
        row.createCell(4).setCellValue("IMPORTE");
        row.createCell(5).setCellValue("NUMEROQUINCENA");
        row.createCell(6).setCellValue("LITERAL");
        row.createCell(7).setCellValue("NUMEROEMPLEADO");
       
         Row rowrecuperacion = recuperacion.createRow(0);
        rowrecuperacion.createCell(0).setCellValue("RFC");
        rowrecuperacion.createCell(1).setCellValue("NOMBRE");
        rowrecuperacion.createCell(2).setCellValue("PLANTEL");
        rowrecuperacion.createCell(3).setCellValue("FOLIO");
        rowrecuperacion.createCell(4).setCellValue("ABONO");
        rowrecuperacion.createCell(5).setCellValue("NUMEROQUINCENA");
        rowrecuperacion.createCell(6).setCellValue("MOVIMIENTO");
        rowrecuperacion.createCell(7).setCellValue("SALDO");
        rowrecuperacion.createCell(8).setCellValue("INTERES");
        rowrecuperacion.createCell(9).setCellValue("CAPITAL");
        rowrecuperacion.createCell(10).setCellValue("NUMEROEMPLEADO");
         
        Row rowprestamos = prestamos.createRow(0);
        rowprestamos.createCell(0).setCellValue("SALDO");
        rowprestamos.createCell(1).setCellValue("ABONOPARCIAL");
        rowprestamos.createCell(2).setCellValue("RFC");
        rowprestamos.createCell(3).setCellValue("NOMBRE");
        rowprestamos.createCell(4).setCellValue("PLANTEL");
        rowprestamos.createCell(5).setCellValue("NUMEROEMPLOEADO");
        rowprestamos.createCell(6).setCellValue("FOLIO");
        rowprestamos.createCell(7).setCellValue("PUESTO");
        rowprestamos.createCell(8).setCellValue("PLAZO");
        rowprestamos.createCell(9).setCellValue("MONTO");
        rowprestamos.createCell(10).setCellValue("INTERES"); 
        rowprestamos.createCell(11).setCellValue("DESCUENTO");
        rowprestamos.createCell(12).setCellValue("STATUS");
        rowprestamos.createCell(13).setCellValue("QNA");
        rowprestamos.createCell(14).setCellValue("QNADESCUENTO");
        rowprestamos.createCell(15).setCellValue("FECHACREACION"); 
        rowprestamos.createCell(16).setCellValue("TOTAL");
        rowprestamos.createCell(17).setCellValue("FONDO_GARANTIA");
        rowprestamos.createCell(18).setCellValue("USUARIOALTA");
       
        
        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        
        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short)7);
       
        PreparedStatement psahorros = null;
        PreparedStatement psrecuperacion = null;
        PreparedStatement psprestamos = null;
        ResultSet rsahorros = null;
        ResultSet rsrecuperacion = null;
        ResultSet rsprestamos = null;
        int numFilaDatosAhorros = 1;
        int numFilaDatosRecuperacion = 1;
        int numFilaDatosPrestamos = 1;
        
        String sqlahorros = "SELECT rfc,nombre,plantel,facore,importe,numeroquincena,literal,ifnull(numeroempleado,0)AS numeroempleado FROM qnaahorrorecuperada "
                + "WHERE numeroquincena >= ? AND numeroquincena <= ? ORDER BY id asc";
        
        String sqlrecuperacion = "SELECT rfc,nombre,plantel,folio,abono,numeroquincena,movimiento,saldo,interes,capital,ifnull(numeroempleado,0)AS numeroempleado "
                + "FROM qnarecuperacionrecuperada WHERE numeroquincena >= ? AND numeroquincena <= ?";
        
        String sqlprestamos = "SELECT saldo,abonoparcial,rfc,nombre,plantel,numeroempleado,folio,puesto,plazo,monto,interes,descuento,"
                + "STATUS,qna,qnadescuento,fechacreacion,total,fondo_garantia,usuarioalta FROM prestamos WHERE qna >= "
                + "? AND qna <= ?";
        
        
        try {
            psahorros = con.prepareStatement(sqlahorros);
            psahorros.setString(1, qnainicial);
            psahorros.setString(2, qnafinal);

            rsahorros = psahorros.executeQuery();

            int numCol = rsahorros.getMetaData().getColumnCount();
            while (rsahorros.next()) {

                Row filaDatosAhorros = ahorros.createRow(numFilaDatosAhorros);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaRfc = filaDatosAhorros.createCell(0);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rsahorros.getString("rfc"));

                    Cell CeldaNombre = filaDatosAhorros.createCell(1);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rsahorros.getString("nombre"));

                    Cell CeldaPlantel = filaDatosAhorros.createCell(2);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rsahorros.getString("plantel"));

                    Cell CeldaFacore = filaDatosAhorros.createCell(3);
                    CeldaFacore.setCellStyle(datosEstilo);
                    CeldaFacore.setCellValue(rsahorros.getFloat("facore"));

                    Cell CeldaImporte = filaDatosAhorros.createCell(4);
                    CeldaImporte.setCellStyle(datosEstiloMoneda);
                    CeldaImporte.setCellValue(rsahorros.getFloat("importe"));

                    Cell CeldaNumeroQuincena = filaDatosAhorros.createCell(5);
                    CeldaNumeroQuincena.setCellStyle(datosEstilo);
                    CeldaNumeroQuincena.setCellValue(rsahorros.getInt("numeroquincena"));

                    Cell CeldaLiteral = filaDatosAhorros.createCell(6);
                    CeldaLiteral.setCellStyle(datosEstilo);
                    CeldaLiteral.setCellValue(rsahorros.getString("literal"));

                    Cell CeldaEmpleado = filaDatosAhorros.createCell(7);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rsahorros.getString("numeroempleado"));
                }
                numFilaDatosAhorros++;
            }
                     
                                                      

            
            
                
                
                
                

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            psrecuperacion = con.prepareStatement(sqlrecuperacion);
            psrecuperacion.setString(1, qnainicial);
            psrecuperacion.setString(2, qnafinal);

            rsrecuperacion = psrecuperacion.executeQuery();

            int numCol = rsrecuperacion.getMetaData().getColumnCount();
            while (rsrecuperacion.next()) {

                Row filaDatosRecuperacion = recuperacion.createRow(numFilaDatosRecuperacion);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaRfc = filaDatosRecuperacion.createCell(0);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rsrecuperacion.getString("rfc"));

                    Cell CeldaNombre = filaDatosRecuperacion.createCell(1);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rsrecuperacion.getString("nombre"));

                    Cell CeldaPlantel = filaDatosRecuperacion.createCell(2);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rsrecuperacion.getString("plantel"));

                    Cell CeldaFolio = filaDatosRecuperacion.createCell(3);
                    CeldaFolio.setCellStyle(datosEstilo);
                    CeldaFolio.setCellValue(rsrecuperacion.getInt("folio"));

                    Cell CeldaAbono = filaDatosRecuperacion.createCell(4);
                    CeldaAbono.setCellStyle(datosEstiloMoneda);
                    CeldaAbono.setCellValue(rsrecuperacion.getFloat("abono"));

                    Cell CeldaNumeroQuincena = filaDatosRecuperacion.createCell(5);
                    CeldaNumeroQuincena.setCellStyle(datosEstilo);
                    CeldaNumeroQuincena.setCellValue(rsrecuperacion.getInt("numeroquincena"));

                    Cell CeldaLiteral = filaDatosRecuperacion.createCell(6);
                    CeldaLiteral.setCellStyle(datosEstilo);
                    CeldaLiteral.setCellValue(rsrecuperacion.getString("movimiento"));

                    Cell CeldaSaldo = filaDatosRecuperacion.createCell(7);
                    CeldaSaldo.setCellStyle(datosEstiloMoneda);
                    CeldaSaldo.setCellValue(rsrecuperacion.getFloat("saldo"));
                    
                    Cell CeldaInteres = filaDatosRecuperacion.createCell(8);
                    CeldaInteres.setCellStyle(datosEstiloMoneda);
                    CeldaInteres.setCellValue(rsrecuperacion.getFloat("interes"));
                    
                    Cell CeldaCapital = filaDatosRecuperacion.createCell(9);
                    CeldaCapital.setCellStyle(datosEstiloMoneda);
                    CeldaCapital.setCellValue(rsrecuperacion.getFloat("capital"));
                    
                    Cell CeldaEmpleado = filaDatosRecuperacion.createCell(10);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rsrecuperacion.getString("numeroempleado"));

                }
                numFilaDatosRecuperacion++;
            }
                     
                                                      

            
            
                
                
                
                

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         try {
            psprestamos = con.prepareStatement(sqlprestamos);
            psprestamos.setString(1, qnainicial);
            psprestamos.setString(2, qnafinal);

            rsprestamos = psprestamos.executeQuery();

            int numCol = rsprestamos.getMetaData().getColumnCount();
            while (rsprestamos.next()) {

                Row filaDatosPrestamos = prestamos.createRow(numFilaDatosPrestamos);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaSaldo = filaDatosPrestamos.createCell(0);
                    CeldaSaldo.setCellStyle(datosEstiloMoneda);
                    CeldaSaldo.setCellValue(rsprestamos.getFloat("saldo"));

                    Cell CeldaAbonoParcial = filaDatosPrestamos.createCell(1);
                    CeldaAbonoParcial.setCellStyle(datosEstilo);
                    CeldaAbonoParcial.setCellValue(rsprestamos.getFloat("abonoparcial"));

                    Cell CeldaRfc = filaDatosPrestamos.createCell(2);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rsprestamos.getString("rfc"));

                    Cell CeldaNombre = filaDatosPrestamos.createCell(3);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rsprestamos.getString("nombre"));

                    Cell CeldaPlantel = filaDatosPrestamos.createCell(4);
                    CeldaPlantel.setCellStyle(datosEstiloMoneda);
                    CeldaPlantel.setCellValue(rsprestamos.getString("plantel"));

                    Cell CeldaNumeroEmpleado = filaDatosPrestamos.createCell(5);
                    CeldaNumeroEmpleado.setCellStyle(datosEstilo);
                    CeldaNumeroEmpleado.setCellValue(rsprestamos.getString("numeroempleado"));

                    Cell CeldaFolio= filaDatosPrestamos.createCell(6);
                    CeldaFolio.setCellStyle(datosEstilo);
                    CeldaFolio.setCellValue(rsprestamos.getInt("folio"));

                    Cell CeldaPuesto= filaDatosPrestamos.createCell(7);
                    CeldaPuesto.setCellStyle(datosEstiloMoneda);
                    CeldaPuesto.setCellValue(rsprestamos.getString("puesto"));
                    
                    Cell CeldaPlazo = filaDatosPrestamos.createCell(8);
                    CeldaPlazo.setCellStyle(datosEstiloMoneda);
                    CeldaPlazo.setCellValue(rsprestamos.getFloat("plazo"));
                    
                    Cell CeldaMonto = filaDatosPrestamos.createCell(9);
                    CeldaMonto.setCellStyle(datosEstiloMoneda);
                    CeldaMonto.setCellValue(rsprestamos.getFloat("monto"));
                    
                    Cell CeldaInteres = filaDatosPrestamos.createCell(10);
                    CeldaInteres.setCellStyle(datosEstiloMoneda);
                    CeldaInteres.setCellValue(rsprestamos.getFloat("interes"));
                    
                    Cell CeldaDescuento = filaDatosPrestamos.createCell(11);
                    CeldaDescuento.setCellStyle(datosEstiloMoneda);
                    CeldaDescuento.setCellValue(rsprestamos.getFloat("descuento"));
                    
                    Cell CeldaStatus = filaDatosPrestamos.createCell(12);
                    CeldaStatus.setCellStyle(datosEstilo);
                    CeldaStatus.setCellValue(rsprestamos.getString("status"));
                    
                    Cell CeldaQna = filaDatosPrestamos.createCell(13);
                    CeldaQna.setCellStyle(datosEstilo);
                    CeldaQna.setCellValue(rsprestamos.getString("qna"));
                    
                    Cell CeldaQnaDescuento = filaDatosPrestamos.createCell(14);
                    CeldaQnaDescuento.setCellStyle(datosEstilo);
                    CeldaQnaDescuento.setCellValue(rsprestamos.getString("qnadescuento"));
                    
                    Cell CeldaFechaCreacion = filaDatosPrestamos.createCell(15);
                    CeldaFechaCreacion.setCellStyle(datosEstilo);
                    CeldaFechaCreacion.setCellValue(rsprestamos.getString("fechacreacion"));
                    
                    Cell CeldaTotal = filaDatosPrestamos.createCell(16);
                    CeldaTotal.setCellStyle(datosEstiloMoneda);
                    CeldaTotal.setCellValue(rsprestamos.getFloat("total"));
                 
                    Cell CeldaFondo = filaDatosPrestamos.createCell(17);
                    CeldaFondo.setCellStyle(datosEstiloMoneda);
                    CeldaFondo.setCellValue(rsprestamos.getFloat("fondo_garantia"));
                    
                    Cell CeldaUsuarioAlta = filaDatosPrestamos.createCell(18);
                    CeldaUsuarioAlta.setCellStyle(datosEstilo);
                    CeldaUsuarioAlta.setCellValue(rsprestamos.getString("usuarioalta"));
                 
                 

                }
                numFilaDatosPrestamos++;
            }
                     
                                                      

            
            
                
                
                
                

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel","xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("InformacionXQna" + qnafinal  + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if(fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION)
            {
            
            File file = fileChooser.getSelectedFile();
            
            if(file == null)
            {
            return;
            } 
            
           //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
           FileOutputStream fileout = new FileOutputStream(file);
           book.write(fileout);
           fileout.close();
           }
            /*FileOutputStream fileout = new FileOutputStream("Patrimonio"+qnafinal+".xlsx");
            book.write(fileout);
            fileout.close();*/

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasGlobales.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public void imprimepatrimonio(Globales mov) {
        
        qnainicial = mov.getPeriodoi()+ mov.getNumeroi();
        qnafinal = mov.getPeriodof()+   mov.getNumerof();
        Connection con = getConexion();
        Workbook book = new XSSFWorkbook();
        Sheet patrimonio = book.createSheet("PATRIMONIO");
        Row row = patrimonio.createRow(0);
        row.createCell(0).setCellValue("RFC");
        row.createCell(1).setCellValue("PLANTEL");
        row.createCell(2).setCellValue("AHORRADO");
        row.createCell(3).setCellValue("RETIROS");
        row.createCell(4).setCellValue("INTERESGANADO");
        row.createCell(5).setCellValue("SALDO");
        row.createCell(6).setCellValue("NOMBRE");
        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        
        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short)7);
        /////////////Consulta a la base de datos
        PreparedStatement ps = null;
        PreparedStatement pstemporal = null;
        ResultSet rs = null;
        
        int numFilaDatos = 1;
        
        String sqlpatrimoniotemporal = "create temporary table tmp_patrimonio"
                + "(select sum(importe)as importe,rfc,literal,max(nombre)as nombre,plantel from qnaahorrorecuperada where numeroquincena >= ? and numeroquincena <= ?"
                + "group by rfc,literal)";
                
         try {
              pstemporal = con.prepareStatement(sqlpatrimoniotemporal);
              pstemporal.setString(1,qnainicial);
              pstemporal.setString(2,qnafinal);
              pstemporal.execute();
         }catch (SQLException ex) {
            Logger.getLogger(ConsultasGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        String sqlpatrimonio = "select sum(IF(literal in ('CII','CIP'),importe,0))as ahorrado,sum(IF(literal in ('ICI','ITA','REG'),importe,0))as interes,sum(IF(literal in ('DIV'),importe,0))as retiros,rfc,max(nombre) as nombre,plantel from "
                + "tmp_patrimonio group by rfc";

        
        
        
        try {
            ps = con.prepareStatement(sqlpatrimonio);
            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {

                Row filaDatosPatrimonio= patrimonio.createRow(numFilaDatos);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaRfc = filaDatosPatrimonio.createCell(0);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rs.getString("rfc"));

                    Cell CeldaPlantel = filaDatosPatrimonio.createCell(1);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rs.getString("plantel"));
                    
                     ahorradopatrimonio = rs.getDouble("ahorrado");
                    retirospatrimonio = rs.getDouble("retiros");
                    interespatrimonio = rs.getDouble("interes");
                    
                    ahorradopatrimonio = Double.parseDouble(String.format("%.2f", ahorradopatrimonio));
                    retirospatrimonio = Double.parseDouble(String.format("%.2f", retirospatrimonio));
                    interespatrimonio = Double.parseDouble(String.format("%.2f", interespatrimonio));
          

                    Cell CeldaAhorrado = filaDatosPatrimonio.createCell(2);
                    CeldaAhorrado.setCellStyle(datosEstiloMoneda);
                    CeldaAhorrado.setCellValue(ahorradopatrimonio);

                    Cell CeldaRetiros = filaDatosPatrimonio.createCell(3);
                    CeldaRetiros.setCellStyle(datosEstiloMoneda);
                    CeldaRetiros.setCellValue(retirospatrimonio);

                    Cell CeldaInteres = filaDatosPatrimonio.createCell(4);
                    CeldaInteres.setCellStyle(datosEstiloMoneda);
                    CeldaInteres.setCellValue(interespatrimonio);
                    
                    saldopatrimonio = (ahorradopatrimonio + interespatrimonio) - retirospatrimonio;
                    saldopatrimonio = Double.parseDouble(String.format("%.2f", saldopatrimonio));
          
                    Cell CeldaSaldo = filaDatosPatrimonio.createCell(5);
                    CeldaSaldo.setCellStyle(datosEstiloMoneda);
                    CeldaSaldo.setCellValue(saldopatrimonio );
                    
                    Cell CeldaNombreP = filaDatosPatrimonio.createCell(6);
                    CeldaNombreP.setCellStyle(datosEstilo);
                    CeldaNombreP.setCellValue(rs.getString("nombre"));
                    
                    

                }
                numFilaDatos++;

            }
            
               Row filaDatosAhorro= patrimonio.createRow(numFilaDatos+1);
                
                Cell CeldaSumaAhorrado = filaDatosAhorro.createCell(2);
                CeldaSumaAhorrado.setCellStyle(datosEstiloMoneda);
                CeldaSumaAhorrado.setCellFormula("SUM(C2:C"+numFilaDatos+")");
                
                Cell CeldaSumaRetiros = filaDatosAhorro.createCell(3);
                CeldaSumaRetiros.setCellStyle(datosEstiloMoneda);
                CeldaSumaRetiros.setCellFormula("SUM(D2:D"+numFilaDatos+")");
                
                Cell CeldaSumaInteres = filaDatosAhorro.createCell(4);
                CeldaSumaInteres.setCellStyle(datosEstiloMoneda);
                CeldaSumaInteres.setCellFormula("SUM(E2:E"+numFilaDatos+")");
                
                
                Cell CeldaSumaSaldo = filaDatosAhorro.createCell(5);
                CeldaSumaSaldo.setCellStyle(datosEstiloMoneda);
                CeldaSumaSaldo.setCellFormula("SUM(F2:F"+numFilaDatos+")");
                
                
                

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel","xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("Patrimonio" + qnafinal  + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if(fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION)
            {
            
            File file = fileChooser.getSelectedFile();
            
            if(file == null)
            {
            return;
            } 
            
           //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
           FileOutputStream fileout = new FileOutputStream(file);
           book.write(fileout);
           fileout.close();
           }
            /*FileOutputStream fileout = new FileOutputStream("Patrimonio"+qnafinal+".xlsx");
            book.write(fileout);
            fileout.close();*/

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }

    

    public void imprimedeudoresxprestamos(Globales mov) {
        qnainicial = mov.getPeriodoi()+ mov.getNumeroi();
        qnafinal = mov.getPeriodof()+   mov.getNumerof();
        Connection con = getConexion();
        Workbook book = new XSSFWorkbook();
        Sheet deudores = book.createSheet("DEUDORESXPRESTAMOS");
        Row row = deudores.createRow(0);
        row.createCell(0).setCellValue("FOLIO");
        row.createCell(1).setCellValue("RFC");
        row.createCell(2).setCellValue("PLANTEL");
        row.createCell(3).setCellValue("TOTAL");
        row.createCell(4).setCellValue("ABONO");
        row.createCell(5).setCellValue("INDEBIDO");
        row.createCell(6).setCellValue("SALDO");
        row.createCell(7).setCellValue("INTERES TOTAL");
        row.createCell(8).setCellValue("INTERES RECUPERADO");
        row.createCell(9).setCellValue("INTERESXRECUPERAR");
        row.createCell(10).setCellValue("CAPITAL RECUPERADO");
        row.createCell(11).setCellValue("CAPITAL X RECUPERAR");
        row.createCell(12).setCellValue("STATUS");
        row.createCell(13).setCellValue("INDEBIDOINTERES");
        row.createCell(14).setCellValue("INDEBIDOCAPITAL");
        //row.createCell(15).setCellValue("INDEBIDOCAPITAL");
        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        
        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short)7);
        
         PreparedStatement ps = null;
         PreparedStatement psfaltantes = null;
         PreparedStatement pstemporal = null;
       
        ResultSet rs = null;
        ResultSet rsfaltantes = null;
        int numFilaDatos = 1;

        String sqlpatrimoniotemporal = "create temporary table tmp_deudores"
                + "(select rfc,nombre,plantel,folio,abono,movimiento,numeroquincena  from qnarecuperacionrecuperada where numeroquincena >= ? and numeroquincena <= ?)";
                
         try {
              pstemporal = con.prepareStatement(sqlpatrimoniotemporal);
              pstemporal.setString(1,qnainicial);
              pstemporal.setString(2,qnafinal);
              pstemporal.execute();
         }catch (SQLException ex) {
            Logger.getLogger(ConsultasGlobales.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sqldeudores = "select ifnull(b.qnacancelacion,0)as qnacancelacion,b.status,a.folio,b.plantel,"
                + "SUM(IF(a.movimiento in ('D','V','X','R','FG','PAF') and numeroquincena >= ? and numeroquincena <= ?, a.abono, 0)) as abono,SUM(IF(a.movimiento = 'I' and numeroquincena >= ? and numeroquincena <= ? , a.abono, 0)) as indebido,b.folio,b.rfc,b.total,b.interes,b.nombre,b.monto from prestamos b inner join qnarecuperacionrecuperada a on a.folio = b.folio where b.qna >= ? and b.qna <= ?  and b.rfc not in (\"EDRH-710615-2CO\",\n"
                + "	\"FMG270278ZGE\",\n"
                + "	\"GUFE491013\",\n"
                + "	\"LRM580704LLO\",\n"
                + "	\"RCE801704SB3\",\n"
                + "	\"SAL800727VY\",\n"
                + "	\"SOAD920324M06\",\n"
                + "	\"VBJC780523L03\",\n"
                + "	\"edrh-710615-2co\",\n"
                + "	\"fmg270278zge\",\n"
                + "	\"gufe491013\",\n"
                + "	\"lrm580704llo\",\n"
                + "	\"rce801704sb3\",\n"
                + "	\"sal800727vy\",\n"
                + "	\"soad920324m06\",\n"
                + "	\"vbjc780523l03\") group by cast(b.folio as signed) order by cast(b.folio as signed)  ";

         //String sqldeudores = "select b.folio,b.plantel,(SELECT ifnull(SUM(abono),0) FROM qnarecuperacionrecuperada a WHERE a.folio = b.folio AND a.numeroquincena >= ? AND a.numeroquincena <= ? AND movimiento NOT IN ('I')) AS abono,(SELECT ifnull(SUM(abono),0) FROM qnarecuperacionrecuperada a WHERE a.folio = b.folio AND a.numeroquincena >= ? AND a.numeroquincena <= ? AND movimiento  IN ('I')) AS indebido,\n"
           //     + "                b.folio,b.rfc,b.total,b.interes,b.nombre,b.monto,b.status from prestamos b  where b.qna >= ? and b.qna <= ?   group by cast(b.folio as signed) order by cast(b.folio as signed)  ";
       
         
          // String sqldeudores = "select b.qnacancelacion,b.`status`,b.plantel,SUM(IF(a.movimiento in ('D','V','X','R','FG','PAF')  and a.numeroquincena <= '$quincena', a.abono, 0)) as abono,SUM(IF(a.movimiento = 'I' and a.numeroquincena <= '$quincena', a.abono, 0)) as indebido,SUM(IF(a.movimiento in ('D','V','X','R','FG','PAF') and a.numeroquincena <= '$quincena', a.interes, 0)) as interespagado,SUM(IF(a.movimiento in ('D','V','X','R','FG','PAF') and a.numeroquincena <= '$quincena', a.capital, 0)) as capitalpagado,SUM(IF(a.movimiento in ('I') and a.numeroquincena <= '$quincena', a.interes, 0)) as interesindebido,SUM(IF(a.movimiento in ('I') and a.numeroquincena <= '$quincena', a.capital, 0)) as capitalindebido,b.folio,b.rfc,b.total,b.interes,b.nombre from prestamos b inner join qnarecuperacionrecuperada a on a.folio = b.folio where b.qna <= '$quincena' or a.numeroquincena <= '$quincena' group by cast(b.folio as signed) order by cast(b.folio as signed) ";
           
        String sqldeudoresfaltantes = "select ifnull(qnacancelacion,0)as qnacancelacion,status,folio,plantel,rfc,total,interes,nombre,monto,'0' AS abono,'0' AS indebido from prestamos t1 where not exists(select folio from qnarecuperacionrecuperada t2 where t2.folio = t1.folio) AND t1.qna >= ? AND t1.qna <= ? and t1.rfc NOT IN (\"EDRH-710615-2CO\",\n" +
"	\"FMG270278ZGE\",\n" +
"	\"GUFE491013\",\n" +
"	\"LRM580704LLO\",\n" +
"	\"RCE801704SB3\",\n" +
"	\"SAL800727VY\",\n" +
"	\"SOAD920324M06\",\n" +
"	\"VBJC780523L03\",\n" +
"	\"edrh-710615-2co\",\n" +
"	\"fmg270278zge\",\n" +
"	\"gufe491013\",\n" +
"	\"lrm580704llo\",\n" +
"	\"rce801704sb3\",\n" +
"	\"sal800727vy\",\n" +
"	\"soad920324m06\",\n" +
"	\"vbjc780523l03\") group by cast(folio as signed) order by cast(folio as signed)  ";

         
        /*String sqldeudores = "select  folio,rfc,plantel,total,interes,status,monto,\n"
                + "(select sum(abono) from qnarecuperacionrecuperada where numeroquincena >= ? and numeroquincena <= ? and folio = prestamos.folio and movimiento not in ('I'))as abono,\n"
                + "(select sum(abono) from qnarecuperacionrecuperada where numeroquincena >= ? and numeroquincena <= ? and folio = prestamos.folio and movimiento  in ('I'))as indebido\n"
                + "\n"
                + "  from prestamos where status not in ('c','C') and qna >= ? and qna <= ?  ";*/
       
        
         try {
            ps = con.prepareStatement(sqldeudores);
            ps.setString(1,qnainicial);
            ps.setString(2,qnafinal);
            ps.setString(3,qnainicial);
            ps.setString(4,qnafinal);
            ps.setString(5,qnainicial);
            ps.setString(6,qnafinal);
            
             rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {

                    statuscompara = rs.getString("status");
                    qnacancelacion = rs.getString("qnacancelacion");
                   
                    abono = rs.getDouble("abono");
                    total = rs.getDouble("total");
                     
                    abono = Double.parseDouble(String.format("%.2f", abono));
                    total = Double.parseDouble(String.format("%.2f", total));
                    indebido = rs.getDouble("indebido");
                    indebido = Double.parseDouble(String.format("%.2f", indebido));
                    interestotal = rs.getDouble("interes");
                    monto = rs.getDouble("monto");
                    saldo =  (total + indebido)-abono;
                    interesrecuperado = (abono * interestotal)/total;
                    interesxrecuperar = interestotal - interesrecuperado;
                    capitalrecuperado = abono  - interesrecuperado;
                    capitalxrecuperar = monto - capitalrecuperado;
                    interesindebido = (indebido * interestotal)/total;
                    capitalindebido = indebido - interesindebido;
                    interesindebido = Double.parseDouble(String.format("%.2f", interesindebido));
                    capitalindebido = Double.parseDouble(String.format("%.2f", capitalindebido));
                 
                                                            
                    if(saldo > 0)
                    {
                    status = "A";
                    }
                    else{
                    
                    status = "S";
                    
                    }
                    
                    if(statuscompara.equals("C"))
                    {
                    
                    if(Integer.parseInt(qnafinal) < Integer.parseInt(qnacancelacion))
                    {
                      
                      if(saldo > 0)
                    {
                    status = "A";
                    }
                    else{
                    
                    status = "S";
                    
                    }   
                     
                    }else{
                    status = "C";
                    monto = 0.0;
                    total = 0.0;
                    interestotal = 0.0;
                    saldo =  (total + indebido)-abono;
                    interesxrecuperar = interestotal - interesrecuperado;
                    capitalxrecuperar = monto - capitalrecuperado;
                    
                    }
                    
                    }
                
                  saldo = Double.parseDouble(String.format("%.2f", saldo));
          
                  if(saldo != 0)
                  {
                  Row filaDatosDeudores= deudores.createRow(numFilaDatos);
                  for (int a = 0; a < numCol; a++) {
                    
                    
                    
                   
                    Cell CeldaFolio = filaDatosDeudores.createCell(0);
                    CeldaFolio.setCellStyle(datosEstilo);
                    CeldaFolio.setCellValue(rs.getInt("folio"));

                    Cell CeldaRfc = filaDatosDeudores.createCell(1);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rs.getString("rfc"));

                    Cell CeldaPlantel = filaDatosDeudores.createCell(2);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rs.getString("plantel"));
                    
                    Cell CeldaTotal = filaDatosDeudores.createCell(3);
                    CeldaTotal.setCellStyle(datosEstiloMoneda);
                    CeldaTotal.setCellValue(total);

                    Cell CeldaAbono = filaDatosDeudores.createCell(4);
                    CeldaAbono.setCellStyle(datosEstiloMoneda);
                    CeldaAbono.setCellValue(abono);
                   
                    Cell CeldaIndebido = filaDatosDeudores.createCell(5);
                    CeldaIndebido.setCellStyle(datosEstiloMoneda);
                    CeldaIndebido.setCellValue(indebido);
                   
                    Cell CeldaSaldo = filaDatosDeudores.createCell(6);
                    CeldaSaldo.setCellStyle(datosEstiloMoneda);
                    CeldaSaldo.setCellValue(saldo);
                   
                    interestotal = Double.parseDouble(String.format("%.2f", interestotal));
                    Cell CeldaInteresTotal = filaDatosDeudores.createCell(7);
                    CeldaInteresTotal.setCellStyle(datosEstiloMoneda);
                    CeldaInteresTotal.setCellValue(interestotal);
                  
                    interesrecuperado = Double.parseDouble(String.format("%.2f", interesrecuperado));
                    Cell CeldaInteresRecuperado = filaDatosDeudores.createCell(8);
                    CeldaInteresRecuperado.setCellStyle(datosEstiloMoneda);
                    CeldaInteresRecuperado.setCellValue(interesrecuperado);
          
                    interesxrecuperar = Double.parseDouble(String.format("%.2f", interesxrecuperar));
                    Cell CeldaInteresXRecuperar = filaDatosDeudores.createCell(9);
                    CeldaInteresXRecuperar.setCellStyle(datosEstiloMoneda);
                    CeldaInteresXRecuperar.setCellValue(interesxrecuperar);
                    
                    capitalrecuperado = Double.parseDouble(String.format("%.2f", capitalrecuperado));
                    Cell CeldaCapitalRecuperado = filaDatosDeudores.createCell(10);
                    CeldaCapitalRecuperado.setCellStyle(datosEstiloMoneda);
                    CeldaCapitalRecuperado.setCellValue(capitalrecuperado);
                    
                    
                    capitalxrecuperar = Double.parseDouble(String.format("%.2f", capitalxrecuperar));
                     Cell CeldaCapitalXRecuperar = filaDatosDeudores.createCell(11);
                     CeldaCapitalXRecuperar.setCellStyle(datosEstiloMoneda);
                     CeldaCapitalXRecuperar.setCellValue(capitalxrecuperar);
                    
                     Cell CeldaStatus = filaDatosDeudores.createCell(12);
                     CeldaStatus.setCellStyle(datosEstilo);
                     CeldaStatus.setCellValue(status);
                      
                    Cell CeldaIndebidoInteres = filaDatosDeudores.createCell(13);
                    CeldaIndebidoInteres.setCellStyle(datosEstiloMoneda);
                    CeldaIndebidoInteres.setCellValue(interesindebido);
                    
                     Cell CeldaIndebidoCapital = filaDatosDeudores.createCell(14);
                    CeldaIndebidoCapital.setCellStyle(datosEstiloMoneda);
                    CeldaIndebidoCapital.setCellValue(capitalindebido);
                    }
                    

                
                numFilaDatos++;
                
                }
                     
                                                      

            }
            
                
                
                
                

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
         
          try{
            psfaltantes = con.prepareStatement(sqldeudoresfaltantes);
            psfaltantes.setString(1,qnainicial);
            psfaltantes.setString(2,qnafinal);
            rsfaltantes = psfaltantes.executeQuery();
            int numColfaltantes = rsfaltantes.getMetaData().getColumnCount();
            while (rsfaltantes.next()) {
                
                   statuscompara = rsfaltantes.getString("status");
                   qnacancelacion = rsfaltantes.getString("qnacancelacion");
                  
                    abono = rsfaltantes.getDouble("abono");
                    total = rsfaltantes.getDouble("total");
                    abono = Double.parseDouble(String.format("%.2f", abono));
                    total = Double.parseDouble(String.format("%.2f", total));
                    indebido = rsfaltantes.getDouble("indebido");
                    indebido = Double.parseDouble(String.format("%.2f", indebido));
                    interestotal = rsfaltantes.getDouble("interes");
                    monto = rsfaltantes.getDouble("monto");
                     saldo =  (total + indebido)-abono;
                    interesrecuperado = (abono * interestotal)/total;
                    interesxrecuperar = interestotal - interesrecuperado;
                    capitalrecuperado = abono  - interesrecuperado;
                    capitalxrecuperar = monto - capitalrecuperado;
                    interesindebido = (indebido * interestotal)/total;
                    capitalindebido = indebido - interesindebido;
                    interesindebido = Double.parseDouble(String.format("%.2f", interesindebido));
                    capitalindebido = Double.parseDouble(String.format("%.2f", capitalindebido));
                 
                    if(saldo > 0)
                    {
                    status = "A";
                    }
                    else{
                    
                    status = "S";
                    
                    }
                    
                     if(statuscompara.equals("C"))
                    {
                    
                    if(Integer.parseInt(qnafinal) < Integer.parseInt(qnacancelacion))
                    {
                      if(saldo > 0)
                    {
                    status = "A";
                    }
                    else{
                    
                    status = "S";
                    
                    }  
                     
                    }else{
                    status = "C";
                    monto = 0.0;
                    total = 0.0;
                    interestotal = 0.0;
                    saldo =  (total + indebido)-abono;
                    interesxrecuperar = interestotal - interesrecuperado;
                    capitalxrecuperar = monto - capitalrecuperado;
                    
                    }
                    
                    }
                    
                    
                
                  saldo = Double.parseDouble(String.format("%.2f", saldo));
          
                  if(saldo > 0)
                  {
                  Row filaDatosDeudores= deudores.createRow(numFilaDatos);
                  for (int a = 0; a < numColfaltantes; a++) {
                    
                    
                    
                   
                    Cell CeldaFolio = filaDatosDeudores.createCell(0);
                    CeldaFolio.setCellStyle(datosEstilo);
                    CeldaFolio.setCellValue(rsfaltantes.getInt("folio"));

                    Cell CeldaRfc = filaDatosDeudores.createCell(1);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rsfaltantes.getString("rfc"));

                    Cell CeldaPlantel = filaDatosDeudores.createCell(2);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rsfaltantes.getString("plantel"));
                    
                    Cell CeldaTotal = filaDatosDeudores.createCell(3);
                    CeldaTotal.setCellStyle(datosEstiloMoneda);
                    CeldaTotal.setCellValue(total);

                    Cell CeldaAbono = filaDatosDeudores.createCell(4);
                    CeldaAbono.setCellStyle(datosEstiloMoneda);
                    CeldaAbono.setCellValue(abono);
                   
                    Cell CeldaIndebido = filaDatosDeudores.createCell(5);
                    CeldaIndebido.setCellStyle(datosEstiloMoneda);
                    CeldaIndebido.setCellValue(indebido);
                   
                    Cell CeldaSaldo = filaDatosDeudores.createCell(6);
                    CeldaSaldo.setCellStyle(datosEstiloMoneda);
                    CeldaSaldo.setCellValue(saldo);
                   
                    interestotal = Double.parseDouble(String.format("%.2f", interestotal));
                    Cell CeldaInteresTotal = filaDatosDeudores.createCell(7);
                    CeldaInteresTotal.setCellStyle(datosEstiloMoneda);
                    CeldaInteresTotal.setCellValue(interestotal);
                  
                    interesrecuperado = Double.parseDouble(String.format("%.2f", interesrecuperado));
                    Cell CeldaInteresRecuperado = filaDatosDeudores.createCell(8);
                    CeldaInteresRecuperado.setCellStyle(datosEstiloMoneda);
                    CeldaInteresRecuperado.setCellValue(interesrecuperado);
          
                    interesxrecuperar = Double.parseDouble(String.format("%.2f", interesxrecuperar));
                    Cell CeldaInteresXRecuperar = filaDatosDeudores.createCell(9);
                    CeldaInteresXRecuperar.setCellStyle(datosEstiloMoneda);
                    CeldaInteresXRecuperar.setCellValue(interesxrecuperar);
                    
                    capitalrecuperado = Double.parseDouble(String.format("%.2f", capitalrecuperado));
                    Cell CeldaCapitalRecuperado = filaDatosDeudores.createCell(10);
                    CeldaCapitalRecuperado.setCellStyle(datosEstiloMoneda);
                    CeldaCapitalRecuperado.setCellValue(capitalrecuperado);
                    
                    
                    capitalxrecuperar = Double.parseDouble(String.format("%.2f", capitalxrecuperar));
                     Cell CeldaCapitalXRecuperar = filaDatosDeudores.createCell(11);
                     CeldaCapitalXRecuperar.setCellStyle(datosEstiloMoneda);
                     CeldaCapitalXRecuperar.setCellValue(capitalxrecuperar);
                    
                     Cell CeldaStatus = filaDatosDeudores.createCell(12);
                     CeldaStatus.setCellStyle(datosEstilo);
                     CeldaStatus.setCellValue(status);
                    
                    Cell CeldaIndebidoInteres = filaDatosDeudores.createCell(13);
                    CeldaIndebidoInteres.setCellStyle(datosEstiloMoneda);
                    CeldaIndebidoInteres.setCellValue(interesindebido);
                    
                     Cell CeldaIndebidoCapital = filaDatosDeudores.createCell(14);
                    CeldaIndebidoCapital.setCellStyle(datosEstiloMoneda);
                    CeldaIndebidoCapital.setCellValue(capitalindebido);
                    }
                    

                
                numFilaDatos++;
                
                }
                
            
            }
            
          Row filaDatosDeudores= deudores.createRow(numFilaDatos+1);
                Cell CeldaSumaTotal = filaDatosDeudores.createCell(3);
                CeldaSumaTotal.setCellStyle(datosEstiloMoneda);
                CeldaSumaTotal.setCellFormula("SUM(D2:D"+numFilaDatos+")");
                
                Cell CeldaSumaAbono = filaDatosDeudores.createCell(4);
                CeldaSumaAbono.setCellStyle(datosEstiloMoneda);
                CeldaSumaAbono.setCellFormula("SUM(E2:E"+numFilaDatos+")");
                
                Cell CeldaSumaIndebido = filaDatosDeudores.createCell(5);
                CeldaSumaIndebido.setCellStyle(datosEstiloMoneda);
                CeldaSumaIndebido.setCellFormula("SUM(F2:F"+numFilaDatos+")");
                
                Cell CeldaSumaSaldo = filaDatosDeudores.createCell(6);
                CeldaSumaSaldo.setCellStyle(datosEstiloMoneda);
                CeldaSumaSaldo.setCellFormula("SUM(G2:G"+numFilaDatos+")");
                
                Cell CeldaSumaInteresT = filaDatosDeudores.createCell(7);
                CeldaSumaInteresT.setCellStyle(datosEstiloMoneda);
                CeldaSumaInteresT.setCellFormula("SUM(H2:H"+numFilaDatos+")");
                
                Cell CeldaSumaInteresR = filaDatosDeudores.createCell(8);
                CeldaSumaInteresR.setCellStyle(datosEstiloMoneda);
                CeldaSumaInteresR.setCellFormula("SUM(I2:I"+numFilaDatos+")");
                
                Cell CeldaSumaInteresXR = filaDatosDeudores.createCell(9);
                CeldaSumaInteresXR.setCellStyle(datosEstiloMoneda);
                CeldaSumaInteresXR.setCellFormula("SUM(J2:J"+numFilaDatos+")");
                
                Cell CeldaSumaCapitalR = filaDatosDeudores.createCell(10);
                CeldaSumaCapitalR.setCellStyle(datosEstiloMoneda);
                CeldaSumaCapitalR.setCellFormula("SUM(K2:K"+numFilaDatos+")");
                
                Cell CeldaSumaCapitalXR = filaDatosDeudores.createCell(11);
                CeldaSumaCapitalXR.setCellStyle(datosEstiloMoneda);
                CeldaSumaCapitalXR.setCellFormula("SUM(L2:L"+numFilaDatos+")");
                
                Cell CeldaSumaIndebidoInteres = filaDatosDeudores.createCell(13);
                CeldaSumaIndebidoInteres.setCellStyle(datosEstiloMoneda);
                CeldaSumaIndebidoInteres.setCellFormula("SUM(N2:N"+numFilaDatos+")");
                
                Cell CeldaSumaIndebidoCapital = filaDatosDeudores.createCell(14);
                CeldaSumaIndebidoCapital.setCellStyle(datosEstiloMoneda);
                CeldaSumaIndebidoCapital.setCellFormula("SUM(O2:O"+numFilaDatos+")");  
        }catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {

             JFileChooser fileChooser = new JFileChooser();
            
            
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel","xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("DeudoresXPrestamos" + qnafinal  + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            
            
            
            
            if(fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION)
            {
            
            File file = fileChooser.getSelectedFile();
            
            if(file == null)
            {
            return;
            } 
            
           //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
           FileOutputStream fileout = new FileOutputStream(file);
           book.write(fileout);
           fileout.close();
           }
            /*FileOutputStream fileout = new FileOutputStream("DeudoresXPrestamos"+qnafinal+".xlsx");
            book.write(fileout);
            fileout.close();*/


        } catch (FileNotFoundException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        /////////////Consulta a la base de datos
        //JOptionPane.showMessageDialog(null, "DEUDORES");

    }

}
