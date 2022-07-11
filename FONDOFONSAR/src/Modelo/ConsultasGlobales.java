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
         PreparedStatement pstemporal = null;
       
        ResultSet rs = null;
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
        
        String sqldeudores = "select b.folio,b.plantel,(SELECT ifnull(SUM(abono),0) FROM qnarecuperacionrecuperada a WHERE a.folio = b.folio AND a.numeroquincena >= ? AND a.numeroquincena <= ? AND movimiento NOT IN ('I')) AS abono,(SELECT ifnull(SUM(abono),0) FROM qnarecuperacionrecuperada a WHERE a.folio = b.folio AND a.numeroquincena >= ? AND a.numeroquincena <= ? AND movimiento  IN ('I')) AS indebido,\n"
                + "                b.folio,b.rfc,b.total,b.interes,b.nombre,b.monto,b.status from prestamos b  where b.qna >= ? and b.qna <= ?   group by cast(b.folio as signed) order by cast(b.folio as signed)  ";

        
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
                    abono = rs.getDouble("abono");
                    total = rs.getDouble("total");
                    if(statuscompara.equals("C"))
                    {
                    total = 0.0;
                    } 
                    
                    
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
                    if(saldo > 0)
                    {
                    status = "A";
                    }
                    else{
                    
                    status = "S";
                    
                    }
                
                  saldo = Double.parseDouble(String.format("%.2f", saldo));
          
                  if(saldo > 0)
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
                    interesindebido = (indebido * interestotal)/total;
                    capitalindebido = indebido - interesindebido;
                    interesindebido = Double.parseDouble(String.format("%.2f", interesindebido));
                    capitalindebido = Double.parseDouble(String.format("%.2f", capitalindebido));
                    
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
                
                
                

        } catch (SQLException ex) {
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
