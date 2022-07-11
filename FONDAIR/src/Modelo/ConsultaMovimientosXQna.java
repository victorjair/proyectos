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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
public class ConsultaMovimientosXQna extends Conexion {

     private String qnainicial;
     private String qnafinal;
  

    public void imprimeInformeMovimientos(MovimientosXqna mov) {
        
        
        qnainicial = mov.getPeriodoi()+ mov.getNumeroi();
        qnafinal = mov.getPeriodof()+   mov.getNumerof();
       
        Connection con = getConexion();
        Workbook book = new XSSFWorkbook();
        Sheet Rendimientos = book.createSheet("RENDIMIENTOS");
        Row row = Rendimientos.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("RFC");
        row.createCell(2).setCellValue("NOMBRE");
        row.createCell(3).setCellValue("PLANTEL");
        row.createCell(4).setCellValue("FACORE");
        row.createCell(5).setCellValue("IMPORTE");
        row.createCell(6).setCellValue("NUMEROQNA");
        row.createCell(7).setCellValue("LITERAL");
        row.createCell(8).setCellValue("CHEQUE");
        row.createCell(9).setCellValue("TRANSFERENCIA");
        row.createCell(10).setCellValue("TIPORETIRO");
        row.createCell(11).setCellValue("NUMEROEMPLEADO");
        row.createCell(12).setCellValue("FECHAMOD");
        row.createCell(13).setCellValue("USUARIO");
        
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
          
        int numFilaDatosRendimientos = 1;
        
         String sqlprueba = "select * FROM  qnaahorrorecuperada  WHERE numeroquincena >= ? AND numeroquincena <= ?";
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
       
        
        

    }
}
