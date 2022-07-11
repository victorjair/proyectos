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
import javax.swing.JOptionPane;
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
public class ConsultasReporteTrimestral extends Conexion {

    private String periodo;
    private String numeroreporte;
    private String tasames;
    private Double aportacion;
    private Double recuperacion;
    private Double fias;
    private Double total = 0.0;
    private Double totalporcentaje = 0.0;
    private Double totalprimermes = 0.0;
    private Double totalsegundomes = 0.0;
    private Double totaltercermes = 0.0;
    private String qnahonorarios;
    private String qnaprestamos;
    private String primermes;
    private String segundomes;
    private String tercermes;
    private String mesprestamos;

    private String qnainicial;
    private String qnafinal;
    private String qna1;
    private String qna2;
    private String qna3;
    private String qna4;
    private String qna5;
    private Double qnaAnterior;
    private Double RendimientoComparar;
    private Double tasa;
    
    
    public void imprimereportetrimestral(ReporteTrimestral mov) {

        periodo = mov.getPeriodoi();
        numeroreporte = mov.getNumeroi();
        Connection con = getConexion();

        Workbook book = new XSSFWorkbook();
        Sheet honorarios = book.createSheet("HONORARIOS");

        Row rowHonorarios = honorarios.createRow(0);
        Row rowPrimerMes = honorarios.createRow(3);
        Row rowSegundoMes = honorarios.createRow(4);
        Row rowTercerMes = honorarios.createRow(5);
        Row rowTotal = honorarios.createRow(7);
        Row rowPorcentaje = honorarios.createRow(9);
        Row rowHonorariosMes = honorarios.createRow(11);

        if (Integer.parseInt(numeroreporte) == 1) {
            primermes = "ENERO";
            segundomes = "FEBRERO";
            tercermes = "MARZO";
        } else if (Integer.parseInt(numeroreporte) == 2) {
            primermes = "ABRIL";
            segundomes = "MAYO";
            tercermes = "JUNIO";
        } else if (Integer.parseInt(numeroreporte) == 3) {
            primermes = "JULIO";
            segundomes = "AGOSTO";
            tercermes = "SEPTIEMBRE";
        } else if (Integer.parseInt(numeroreporte) == 4) {
            primermes = "OCTUBRE";
            segundomes = "NOVIEMBRE";
            tercermes = "DICIEMBRE";
        }
        rowHonorarios.createCell(3).setCellValue("CALCULO DE HONORARIOS");
        rowPrimerMes.createCell(0).setCellValue("PRESTAMOS DEL MES DE " + primermes);
        rowPrimerMes.createCell(1).setCellValue("+");
        rowSegundoMes.createCell(0).setCellValue("PRESTAMOS DEL MES DE " + segundomes);
        rowSegundoMes.createCell(1).setCellValue("+");
        rowTercerMes.createCell(0).setCellValue("PRESTAMOS DEL MES DE " + tercermes);
        rowTercerMes.createCell(1).setCellValue("+");
        rowTotal.createCell(0).setCellValue("TOTAL");
        rowPorcentaje.createCell(0).setCellValue("PORCENTAJE");
        rowPorcentaje.createCell(1).setCellValue("X");
        rowPorcentaje.createCell(2).setCellValue("1%");

        rowHonorariosMes.createCell(0).setCellValue("HONORARIOS");

        Sheet estadisticas = book.createSheet("ESTADISTICAS");
        Row row = estadisticas.createRow(0);
        row.createCell(0).setCellValue("QUINCENAS");
        row.createCell(1).setCellValue("AFILIADOS");
        row.createCell(2).setCellValue("INCREMENTO");
        row.createCell(3).setCellValue("APORTACIONES");
        row.createCell(4).setCellValue("RECUPERACIONES");
        row.createCell(5).setCellValue("FIAS");
        row.createCell(6).setCellValue("TOTAL DEPOSITOS");
        row.createCell(7).setCellValue("PRESTAMOS");

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
                  
        
        //styleCurrencyFormat.setDataFormat((short)8);
        Sheet enero = book.createSheet("ENERO");
        Row rowenero = enero.createRow(0);
        rowenero.createCell(0).setCellValue("FECHA");
        rowenero.createCell(1).setCellValue("PLANTEL");
        rowenero.createCell(2).setCellValue("NOMBRE");
        rowenero.createCell(3).setCellValue("RFC");
        rowenero.createCell(4).setCellValue("MONTO");
        rowenero.createCell(5).setCellValue("PLAZO");
        rowenero.createCell(6).setCellValue("DESCUENTO");
        rowenero.createCell(7).setCellValue("INTERES");
        rowenero.createCell(8).setCellValue("TOTAL");
        rowenero.createCell(9).setCellValue("FOLIO");
        rowenero.createCell(10).setCellValue("CHEQUE");
        rowenero.createCell(11).setCellValue("STATUS");
        rowenero.createCell(12).setCellValue("FONDO");
        Sheet febrero = book.createSheet("FEBRERO");
        Row rowfebrero = febrero.createRow(0);
        rowfebrero.createCell(0).setCellValue("FECHA");
        rowfebrero.createCell(1).setCellValue("PLANTEL");
        rowfebrero.createCell(2).setCellValue("NOMBRE");
        rowfebrero.createCell(3).setCellValue("RFC");
        rowfebrero.createCell(4).setCellValue("MONTO");
        rowfebrero.createCell(5).setCellValue("PLAZO");
        rowfebrero.createCell(6).setCellValue("DESCUENTO");
        rowfebrero.createCell(7).setCellValue("INTERES");
        rowfebrero.createCell(8).setCellValue("TOTAL");
        rowfebrero.createCell(9).setCellValue("FOLIO");
        rowfebrero.createCell(10).setCellValue("CHEQUE");
        rowfebrero.createCell(11).setCellValue("STATUS");
        rowfebrero.createCell(12).setCellValue("FONDO");
        Sheet marzo = book.createSheet("MARZO");
        Row rowmarzo = marzo.createRow(0);
        rowmarzo.createCell(0).setCellValue("FECHA");
        rowmarzo.createCell(1).setCellValue("PLANTEL");
        rowmarzo.createCell(2).setCellValue("NOMBRE");
        rowmarzo.createCell(3).setCellValue("RFC");
        rowmarzo.createCell(4).setCellValue("MONTO");
        rowmarzo.createCell(5).setCellValue("PLAZO");
        rowmarzo.createCell(6).setCellValue("DESCUENTO");
        rowmarzo.createCell(7).setCellValue("INTERES");
        rowmarzo.createCell(8).setCellValue("TOTAL");
        rowmarzo.createCell(9).setCellValue("FOLIO");
        rowmarzo.createCell(10).setCellValue("CHEQUE");
        rowmarzo.createCell(11).setCellValue("STATUS");
        rowmarzo.createCell(12).setCellValue("FONDO");
        Sheet abril = book.createSheet("ABRIL");
        Sheet mayo = book.createSheet("MAYO");
        Sheet junio = book.createSheet("JUNIO");
        Sheet julio = book.createSheet("JULIO");
        Sheet agosto = book.createSheet("AGOSTO");
        Sheet septiembre = book.createSheet("SEPTIEMBRE");
        Sheet octubre = book.createSheet("OCTUBRE");
        Sheet noviembre = book.createSheet("NOVIEMBRE");
        Sheet diciembre = book.createSheet("DICIEMBRE");

        if (Integer.parseInt(numeroreporte) > 1) {
            //Sheet abril = book.createSheet("ABRIL");
            Row rowabril = abril.createRow(0);
            rowabril.createCell(0).setCellValue("FECHA");
            rowabril.createCell(1).setCellValue("PLANTEL");
            rowabril.createCell(2).setCellValue("NOMBRE");
            rowabril.createCell(3).setCellValue("RFC");
            rowabril.createCell(4).setCellValue("MONTO");
            rowabril.createCell(5).setCellValue("PLAZO");
            rowabril.createCell(6).setCellValue("DESCUENTO");
            rowabril.createCell(7).setCellValue("INTERES");
            rowabril.createCell(8).setCellValue("TOTAL");
            rowabril.createCell(9).setCellValue("FOLIO");
            rowabril.createCell(10).setCellValue("CHEQUE");
            rowabril.createCell(11).setCellValue("STATUS");
            rowabril.createCell(12).setCellValue("FONDO");

            Row rowmayo = mayo.createRow(0);
            rowmayo.createCell(0).setCellValue("FECHA");
            rowmayo.createCell(1).setCellValue("PLANTEL");
            rowmayo.createCell(2).setCellValue("NOMBRE");
            rowmayo.createCell(3).setCellValue("RFC");
            rowmayo.createCell(4).setCellValue("MONTO");
            rowmayo.createCell(5).setCellValue("PLAZO");
            rowmayo.createCell(6).setCellValue("DESCUENTO");
            rowmayo.createCell(7).setCellValue("INTERES");
            rowmayo.createCell(8).setCellValue("TOTAL");
            rowmayo.createCell(9).setCellValue("FOLIO");
            rowmayo.createCell(10).setCellValue("CHEQUE");
            rowmayo.createCell(11).setCellValue("STATUS");
            rowmayo.createCell(12).setCellValue("FONDO");
            Row rowjunio = junio.createRow(0);
            rowjunio.createCell(0).setCellValue("FECHA");
            rowjunio.createCell(1).setCellValue("PLANTEL");
            rowjunio.createCell(2).setCellValue("NOMBRE");
            rowjunio.createCell(3).setCellValue("RFC");
            rowjunio.createCell(4).setCellValue("MONTO");
            rowjunio.createCell(5).setCellValue("PLAZO");
            rowjunio.createCell(6).setCellValue("DESCUENTO");
            rowjunio.createCell(7).setCellValue("INTERES");
            rowjunio.createCell(8).setCellValue("TOTAL");
            rowjunio.createCell(9).setCellValue("FOLIO");
            rowjunio.createCell(10).setCellValue("CHEQUE");
            rowjunio.createCell(11).setCellValue("STATUS");
            rowjunio.createCell(12).setCellValue("FONDO");
        }
        if (Integer.parseInt(numeroreporte) > 2) {
            Row rowjulio = julio.createRow(0);
            rowjulio.createCell(0).setCellValue("FECHA");
            rowjulio.createCell(1).setCellValue("PLANTEL");
            rowjulio.createCell(2).setCellValue("NOMBRE");
            rowjulio.createCell(3).setCellValue("RFC");
            rowjulio.createCell(4).setCellValue("MONTO");
            rowjulio.createCell(5).setCellValue("PLAZO");
            rowjulio.createCell(6).setCellValue("DESCUENTO");
            rowjulio.createCell(7).setCellValue("INTERES");
            rowjulio.createCell(8).setCellValue("TOTAL");
            rowjulio.createCell(9).setCellValue("FOLIO");
            rowjulio.createCell(10).setCellValue("CHEQUE");
            rowjulio.createCell(11).setCellValue("STATUS");
            rowjulio.createCell(12).setCellValue("FONDO");
            Row rowagosto = agosto.createRow(0);
            rowagosto.createCell(0).setCellValue("FECHA");
            rowagosto.createCell(1).setCellValue("PLANTEL");
            rowagosto.createCell(2).setCellValue("NOMBRE");
            rowagosto.createCell(3).setCellValue("RFC");
            rowagosto.createCell(4).setCellValue("MONTO");
            rowagosto.createCell(5).setCellValue("PLAZO");
            rowagosto.createCell(6).setCellValue("DESCUENTO");
            rowagosto.createCell(7).setCellValue("INTERES");
            rowagosto.createCell(8).setCellValue("TOTAL");
            rowagosto.createCell(9).setCellValue("FOLIO");
            rowagosto.createCell(10).setCellValue("CHEQUE");
            rowagosto.createCell(11).setCellValue("STATUS");
            rowagosto.createCell(12).setCellValue("FONDO");
            Row rowseptiembre = septiembre.createRow(0);
            rowseptiembre.createCell(0).setCellValue("FECHA");
            rowseptiembre.createCell(1).setCellValue("PLANTEL");
            rowseptiembre.createCell(2).setCellValue("NOMBRE");
            rowseptiembre.createCell(3).setCellValue("RFC");
            rowseptiembre.createCell(4).setCellValue("MONTO");
            rowseptiembre.createCell(5).setCellValue("PLAZO");
            rowseptiembre.createCell(6).setCellValue("DESCUENTO");
            rowseptiembre.createCell(7).setCellValue("INTERES");
            rowseptiembre.createCell(8).setCellValue("TOTAL");
            rowseptiembre.createCell(9).setCellValue("FOLIO");
            rowseptiembre.createCell(10).setCellValue("CHEQUE");
            rowseptiembre.createCell(11).setCellValue("STATUS");
            rowseptiembre.createCell(12).setCellValue("FONDO");
        }
        if (Integer.parseInt(numeroreporte) > 3) {
            Row rowoctubre = octubre.createRow(0);
            rowoctubre.createCell(0).setCellValue("FECHA");
            rowoctubre.createCell(1).setCellValue("PLANTEL");
            rowoctubre.createCell(2).setCellValue("NOMBRE");
            rowoctubre.createCell(3).setCellValue("RFC");
            rowoctubre.createCell(4).setCellValue("MONTO");
            rowoctubre.createCell(5).setCellValue("PLAZO");
            rowoctubre.createCell(6).setCellValue("DESCUENTO");
            rowoctubre.createCell(7).setCellValue("INTERES");
            rowoctubre.createCell(8).setCellValue("TOTAL");
            rowoctubre.createCell(9).setCellValue("FOLIO");
            rowoctubre.createCell(10).setCellValue("CHEQUE");
            rowoctubre.createCell(11).setCellValue("STATUS");
            rowoctubre.createCell(12).setCellValue("FONDO");
            Row rownoviembre = noviembre.createRow(0);
            rownoviembre.createCell(0).setCellValue("FECHA");
            rownoviembre.createCell(1).setCellValue("PLANTEL");
            rownoviembre.createCell(2).setCellValue("NOMBRE");
            rownoviembre.createCell(3).setCellValue("RFC");
            rownoviembre.createCell(4).setCellValue("MONTO");
            rownoviembre.createCell(5).setCellValue("PLAZO");
            rownoviembre.createCell(6).setCellValue("DESCUENTO");
            rownoviembre.createCell(7).setCellValue("INTERES");
            rownoviembre.createCell(8).setCellValue("TOTAL");
            rownoviembre.createCell(9).setCellValue("FOLIO");
            rownoviembre.createCell(10).setCellValue("CHEQUE");
            rownoviembre.createCell(11).setCellValue("STATUS");
            rownoviembre.createCell(12).setCellValue("FONDO");
            Row rowdiciembre = diciembre.createRow(0);
            rowdiciembre.createCell(0).setCellValue("FECHA");
            rowdiciembre.createCell(1).setCellValue("PLANTEL");
            rowdiciembre.createCell(2).setCellValue("NOMBRE");
            rowdiciembre.createCell(3).setCellValue("RFC");
            rowdiciembre.createCell(4).setCellValue("MONTO");
            rowdiciembre.createCell(5).setCellValue("PLAZO");
            rowdiciembre.createCell(6).setCellValue("DESCUENTO");
            rowdiciembre.createCell(7).setCellValue("INTERES");
            rowdiciembre.createCell(8).setCellValue("TOTAL");
            rowdiciembre.createCell(9).setCellValue("FOLIO");
            rowdiciembre.createCell(10).setCellValue("CHEQUE");
            rowdiciembre.createCell(11).setCellValue("STATUS");
            rowdiciembre.createCell(12).setCellValue("FONDO");
        }

        PreparedStatement ps = null;

        ResultSet rs = null;
        int numFilaDatosEstadisticas = 1;
        int numFilaDatosHonorarios = 0;
        int numFilaDatosEnero = 1;
        int numFilaDatosFebrero = 1;
        int numFilaDatosMarzo = 1;
        int numFilaDatosAbril = 1;
        int numFilaDatosMayo = 1;
        int numFilaDatosJunio = 1;
        int numFilaDatosJulio = 1;
        int numFilaDatosAgosto = 1;
        int numFilaDatosSeptiembre = 1;
        int numFilaDatosOctubre = 1;
        int numFilaDatosNoviembre = 1;
        int numFilaDatosDiciembre = 1;

        String sqlEstadisticas = " SELECT\n"
                + "                (SELECT SUM(abono) FROM qnarecuperacionrecuperada WHERE numeroquincena = A.numeroquincena   AND MOVIMIENTO IN ('D') ) AS RECUPERACION,\n"
                + "                (SELECT SUM(capital) FROM qnarecuperacionrecuperada WHERE numeroquincena = A.numeroquincena   AND MOVIMIENTO IN ('D') ) AS CAPITAL,\n"
                + "                (SELECT SUM(interes) FROM qnarecuperacionrecuperada WHERE numeroquincena = A.numeroquincena   AND MOVIMIENTO IN ('D') ) AS INTERES,\n"
                + "                (SELECT COUNT(rfc) FROM afiliado WHERE QNAAFILIACION <= A.numeroquincena    )AS AFILIADOS,\n"
                + "                (SELECT COUNT(rfc) FROM afiliado WHERE QNAAFILIACION = A.numeroquincena    )AS INCREMENTO,\n"
                + "                (SELECT COUNT(rfc) FROM afiliado WHERE QNABAJA = A.numeroquincena    )AS BAJAS,\n"
                + "                (SELECT SUM(capital) from fias where numeroquincena = A.numeroquincena and status not in ('C','c'))as FIAS,\n"
                + "                (SELECT SUM(monto) FROM prestamos WHERE QNA = A.numeroquincena AND STATUS NOT IN ('C','c')    )AS PRESTAMOS,\n"
                + "                SUM(importe)AS APORTACION,A.numeroquincena  FROM qnaahorrorecuperada A  WHERE A.numeroquincena  >= ? and A.numeroquincena <=  ?  AND LITERAL IN ('CIP','CII') GROUP BY A.numeroquincena";

        
        
        String sqlPrestamos = "SELECT * from prestamos where qna >= ? and qna <= ? and status not in"
                + " ('c','C') ";
        String sqlPrestamosHonorarios = "SELECT CASE WHEN sum(monto)IS NULL THEN 0 ELSE sum(monto) END as monto , qna from prestamos where qna >= ? and qna <= ? and status not in"
                + " ('c','C') group by qna ";

        try {
            ps = con.prepareStatement(sqlEstadisticas);
            if (Integer.parseInt(numeroreporte) == 1) {
                ps.setString(1, periodo + "01");
                ps.setString(2, periodo + "06");

            } else if (Integer.parseInt(numeroreporte) == 2) {
                ps.setString(1, periodo + "01");
                ps.setString(2, periodo + "12");

            } else if (Integer.parseInt(numeroreporte) == 3) {
                ps.setString(1, periodo + "01");
                ps.setString(2, periodo + "18");
            } else if (Integer.parseInt(numeroreporte) == 4) {
                ps.setString(1, periodo + "01");
                ps.setString(2, periodo + "24");
            } else {
            }

            rs = ps.executeQuery();
            int numCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {

                Row filaDatosEstadisticas = estadisticas.createRow(numFilaDatosEstadisticas);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNumeroquincena = filaDatosEstadisticas.createCell(0);
                    CeldaNumeroquincena.setCellStyle(datosEstilo);
                    CeldaNumeroquincena.setCellValue(rs.getInt("NUMEROQUINCENA"));

                    Cell CeldaAfiliados = filaDatosEstadisticas.createCell(1);
                    CeldaAfiliados.setCellStyle(datosEstilo);
                    CeldaAfiliados.setCellValue(rs.getInt("AFILIADOS"));

                    Cell CeldaIncremento = filaDatosEstadisticas.createCell(2);
                    CeldaIncremento.setCellStyle(datosEstilo);
                    CeldaIncremento.setCellValue(rs.getInt("INCREMENTO"));

                    Cell CeldaAportaciones = filaDatosEstadisticas.createCell(3);
                    //CeldaAportaciones.setCellStyle(datosEstilo);
                    CeldaAportaciones.setCellValue(rs.getDouble("APORTACION"));
                     CeldaAportaciones.setCellStyle(datosEstiloMoneda);
                    //CeldaAportaciones.set

                    Cell CeldaRecuperaciones = filaDatosEstadisticas.createCell(4);
                    CeldaRecuperaciones.setCellStyle(datosEstiloMoneda);
                    CeldaRecuperaciones.setCellValue(rs.getDouble("RECUPERACION"));

                    Cell CeldaFias = filaDatosEstadisticas.createCell(5);
                    CeldaFias.setCellStyle(datosEstiloMoneda);
                    CeldaFias.setCellValue(rs.getDouble("FIAS"));
                    aportacion = rs.getDouble("APORTACION");
                    recuperacion = rs.getDouble("RECUPERACION");
                    fias = rs.getDouble("FIAS");
                    total = aportacion + recuperacion + fias;

                    Cell CeldaTotalDepositos = filaDatosEstadisticas.createCell(6);
                    CeldaTotalDepositos.setCellStyle(datosEstiloMoneda);
                    CeldaTotalDepositos.setCellValue(total);

                    Cell CeldaPrestamos = filaDatosEstadisticas.createCell(7);
                    CeldaPrestamos.setCellStyle(datosEstiloMoneda);
                    CeldaPrestamos.setCellValue(rs.getDouble("PRESTAMOS"));

                }
                numFilaDatosEstadisticas++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasReporteTrimestral.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ps = con.prepareStatement(sqlPrestamos);
            if (Integer.parseInt(numeroreporte) == 1) {
                ps.setString(1, periodo + "01");
                ps.setString(2, periodo + "06");

            } else if (Integer.parseInt(numeroreporte) == 2) {
                ps.setString(1, periodo + "01");
                ps.setString(2, periodo + "12");

            } else if (Integer.parseInt(numeroreporte) == 3) {
                ps.setString(1, periodo + "01");
                ps.setString(2, periodo + "18");
            } else if (Integer.parseInt(numeroreporte) == 4) {
                ps.setString(1, periodo + "01");
                ps.setString(2, periodo + "24");
            } else {
            }

            rs = ps.executeQuery();
            int numCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                qnaprestamos = rs.getString("qna").substring(4, 6);
                Row filaDatosPrestamos = null;
                if (qnaprestamos.equals("01") || qnaprestamos.equals("02")) {
                    filaDatosPrestamos = enero.createRow(numFilaDatosEnero);
                }
                if (qnaprestamos.equals("03") || qnaprestamos.equals("04")) {
                    filaDatosPrestamos = febrero.createRow(numFilaDatosFebrero);
                }
                if (qnaprestamos.equals("05") || qnaprestamos.equals("06")) {
                    filaDatosPrestamos = marzo.createRow(numFilaDatosMarzo);
                }
                if (qnaprestamos.equals("07") || qnaprestamos.equals("08")) {
                    filaDatosPrestamos = abril.createRow(numFilaDatosAbril);
                }
                if (qnaprestamos.equals("09") || qnaprestamos.equals("10")) {
                    filaDatosPrestamos = mayo.createRow(numFilaDatosMayo);
                }
                if (qnaprestamos.equals("11") || qnaprestamos.equals("12")) {
                    filaDatosPrestamos = junio.createRow(numFilaDatosJunio);
                }
                if (qnaprestamos.equals("13") || qnaprestamos.equals("14")) {
                    filaDatosPrestamos = julio.createRow(numFilaDatosJulio);
                }
                if (qnaprestamos.equals("15") || qnaprestamos.equals("16")) {
                    filaDatosPrestamos = agosto.createRow(numFilaDatosAgosto);
                }
                if (qnaprestamos.equals("17") || qnaprestamos.equals("18")) {
                    filaDatosPrestamos = septiembre.createRow(numFilaDatosSeptiembre);
                }
                if (qnaprestamos.equals("19") || qnaprestamos.equals("20")) {
                    filaDatosPrestamos = octubre.createRow(numFilaDatosOctubre);
                }
                if (qnaprestamos.equals("21") || qnaprestamos.equals("22")) {
                    filaDatosPrestamos = noviembre.createRow(numFilaDatosNoviembre);
                }
                if (qnaprestamos.equals("23") || qnaprestamos.equals("24")) {
                    filaDatosPrestamos = diciembre.createRow(numFilaDatosDiciembre);
                }

                //Row filaDatosPrestamos = enero.createRow(numFilaDatosEnero);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaFechaCreacion = filaDatosPrestamos.createCell(0);
                    CeldaFechaCreacion.setCellStyle(datosEstilo);
                    CeldaFechaCreacion.setCellValue(rs.getString("fechacreacion"));

                    Cell Celdaplantel = filaDatosPrestamos.createCell(1);
                    Celdaplantel.setCellStyle(datosEstilo);
                    Celdaplantel.setCellValue(rs.getString("plantel"));

                    Cell Celdanombre = filaDatosPrestamos.createCell(2);
                    Celdanombre.setCellStyle(datosEstilo);
                    Celdanombre.setCellValue(rs.getString("nombre"));

                    Cell Celdarfc = filaDatosPrestamos.createCell(3);
                    Celdarfc.setCellStyle(datosEstilo);
                    Celdarfc.setCellValue(rs.getString("rfc"));

                    Cell Celdamonto = filaDatosPrestamos.createCell(4);
                    Celdamonto.setCellStyle(datosEstiloMoneda);
                    Celdamonto.setCellValue(rs.getDouble("monto"));

                    Cell Celdaplazo = filaDatosPrestamos.createCell(5);
                    Celdaplazo.setCellStyle(datosEstilo);
                    Celdaplazo.setCellValue(rs.getInt("plazo"));

                    Cell Celdadescuento = filaDatosPrestamos.createCell(6);
                    Celdadescuento.setCellStyle(datosEstiloMoneda);
                    Celdadescuento.setCellValue(rs.getDouble("descuento"));

                    Cell Celdainteres = filaDatosPrestamos.createCell(7);
                    Celdainteres.setCellStyle(datosEstiloMoneda);
                    Celdainteres.setCellValue(rs.getDouble("interes"));

                    Cell Celdatotal = filaDatosPrestamos.createCell(8);
                    Celdatotal.setCellStyle(datosEstiloMoneda);
                    Celdatotal.setCellValue(rs.getDouble("total"));

                    Cell Celdafolio = filaDatosPrestamos.createCell(9);
                    Celdafolio.setCellStyle(datosEstilo);
                    Celdafolio.setCellValue(rs.getInt("folio"));

                    Cell Celdanumcheque = filaDatosPrestamos.createCell(10);
                    Celdanumcheque.setCellStyle(datosEstilo);
                    Celdanumcheque.setCellValue(rs.getInt("numcheque"));

                    Cell Celdastatus = filaDatosPrestamos.createCell(11);
                    Celdastatus.setCellStyle(datosEstilo);
                    Celdastatus.setCellValue(rs.getString("status"));

                    Cell Celdafondo_garantia = filaDatosPrestamos.createCell(12);
                    Celdafondo_garantia.setCellStyle(datosEstiloMoneda);
                    Celdafondo_garantia.setCellValue(rs.getDouble("fondo_garantia"));

                    Cell Celdaqna = filaDatosPrestamos.createCell(13);
                    Celdaqna.setCellStyle(datosEstilo);
                    Celdaqna.setCellValue(rs.getDouble("qna"));

                }
                if (qnaprestamos.equals("01") || qnaprestamos.equals("02")) {
                    numFilaDatosEnero++;
                }
                if (qnaprestamos.equals("03") || qnaprestamos.equals("04")) {
                    numFilaDatosFebrero++;
                }
                if (qnaprestamos.equals("05") || qnaprestamos.equals("06")) {
                    numFilaDatosMarzo++;
                }
                if (qnaprestamos.equals("07") || qnaprestamos.equals("08")) {
                    numFilaDatosAbril++;
                }
                if (qnaprestamos.equals("09") || qnaprestamos.equals("10")) {
                    numFilaDatosMayo++;
                }
                if (qnaprestamos.equals("11") || qnaprestamos.equals("12")) {
                    numFilaDatosJunio++;
                }
                if (qnaprestamos.equals("13") || qnaprestamos.equals("14")) {
                    numFilaDatosJulio++;
                }
                if (qnaprestamos.equals("15") || qnaprestamos.equals("16")) {
                    numFilaDatosAgosto++;
                }
                if (qnaprestamos.equals("17") || qnaprestamos.equals("18")) {
                    numFilaDatosSeptiembre++;
                }
                if (qnaprestamos.equals("19") || qnaprestamos.equals("20")) {
                    numFilaDatosOctubre++;
                }
                if (qnaprestamos.equals("21") || qnaprestamos.equals("22")) {
                    numFilaDatosNoviembre++;
                }
                if (qnaprestamos.equals("23") || qnaprestamos.equals("24")) {
                    numFilaDatosDiciembre++;
                }

            }

            Row filaDatosDeudores = enero.createRow(numFilaDatosEnero + 1);
            Cell CeldaSumaMonto = filaDatosDeudores.createCell(4);
            CeldaSumaMonto.setCellStyle(datosEstiloMoneda);
            CeldaSumaMonto.setCellFormula("SUM(E2:E" + numFilaDatosEnero + ")");
            Cell CeldaSumaDescuento = filaDatosDeudores.createCell(6);
            CeldaSumaDescuento.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuento.setCellFormula("SUM(G2:G" + numFilaDatosEnero + ")");
            Cell CeldaSumaInteres = filaDatosDeudores.createCell(7);
            CeldaSumaInteres.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteres.setCellFormula("SUM(H2:H" + numFilaDatosEnero + ")");
            Cell CeldaSumaTotal = filaDatosDeudores.createCell(8);
            CeldaSumaTotal.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotal.setCellFormula("SUM(I2:I" + numFilaDatosEnero + ")");
            Cell CeldaSumaFondo = filaDatosDeudores.createCell(12);
            CeldaSumaFondo.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondo.setCellFormula("SUM(M2:M" + numFilaDatosEnero + ")");

            Row filaDatosFebrero = febrero.createRow(numFilaDatosFebrero + 1);
            Cell CeldaSumaMontoFebrero = filaDatosFebrero.createCell(4);
            CeldaSumaMontoFebrero.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontoFebrero.setCellFormula("SUM(E2:E" + numFilaDatosFebrero + ")");
            Cell CeldaSumaDescuentoF = filaDatosFebrero.createCell(6);
            CeldaSumaDescuentoF.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentoF.setCellFormula("SUM(G2:G" + numFilaDatosFebrero + ")");
            Cell CeldaSumaInteresF = filaDatosFebrero.createCell(7);
            CeldaSumaInteresF.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresF.setCellFormula("SUM(H2:H" + numFilaDatosFebrero + ")");
            Cell CeldaSumaTotalF = filaDatosFebrero.createCell(8);
            CeldaSumaTotalF.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalF.setCellFormula("SUM(I2:I" + numFilaDatosFebrero + ")");
            Cell CeldaSumaFondoF = filaDatosFebrero.createCell(12);
            CeldaSumaFondoF.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondoF.setCellFormula("SUM(M2:M" + numFilaDatosFebrero + ")");

            Row filaDatosDeudoresmarzo = marzo.createRow(numFilaDatosMarzo + 1);
            Cell CeldaSumaMontomarzo = filaDatosDeudoresmarzo.createCell(4);
            CeldaSumaMontomarzo.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontomarzo.setCellFormula("SUM(E2:E" + numFilaDatosMarzo + ")");
            Cell CeldaSumaDescuentomarzo = filaDatosDeudoresmarzo.createCell(6);
            CeldaSumaDescuentomarzo.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentomarzo.setCellFormula("SUM(G2:G" + numFilaDatosMarzo + ")");
            Cell CeldaSumaInteresmarzo = filaDatosDeudoresmarzo.createCell(7);
            CeldaSumaInteresmarzo.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresmarzo.setCellFormula("SUM(H2:H" + numFilaDatosMarzo + ")");
            Cell CeldaSumaTotalmarzo = filaDatosDeudoresmarzo.createCell(8);
            CeldaSumaTotalmarzo.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalmarzo.setCellFormula("SUM(I2:I" + numFilaDatosMarzo + ")");
            Cell CeldaSumaFondomarzo = filaDatosDeudoresmarzo.createCell(12);
            CeldaSumaFondomarzo.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondomarzo.setCellFormula("SUM(M2:M" + numFilaDatosMarzo + ")");

            Row filaDatosDeudoresabril = abril.createRow(numFilaDatosAbril + 1);
            Cell CeldaSumaMontoabril = filaDatosDeudoresabril.createCell(4);
            CeldaSumaMontoabril.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontoabril.setCellFormula("SUM(E2:E" + numFilaDatosAbril + ")");
            Cell CeldaSumaDescuentoabril = filaDatosDeudoresabril.createCell(6);
            CeldaSumaDescuentoabril.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentoabril.setCellFormula("SUM(G2:G" + numFilaDatosAbril + ")");
            Cell CeldaSumaInteresabril = filaDatosDeudoresabril.createCell(7);
            CeldaSumaInteresabril.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresabril.setCellFormula("SUM(H2:H" + numFilaDatosAbril + ")");
            Cell CeldaSumaTotalabril = filaDatosDeudoresabril.createCell(8);
            CeldaSumaTotalabril.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalabril.setCellFormula("SUM(I2:I" + numFilaDatosAbril + ")");
            Cell CeldaSumaFondoabril = filaDatosDeudoresabril.createCell(12);
            CeldaSumaFondoabril.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondoabril.setCellFormula("SUM(M2:M" + numFilaDatosAbril + ")");

            Row filaDatosDeudoresMayo = mayo.createRow(numFilaDatosMayo + 1);
            Cell CeldaSumaMontoMayo = filaDatosDeudoresMayo.createCell(4);
            CeldaSumaMontoMayo.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontoMayo.setCellFormula("SUM(E2:E" + numFilaDatosMayo + ")");
            Cell CeldaSumaDescuentoMayo = filaDatosDeudoresMayo.createCell(6);
            CeldaSumaDescuentoMayo.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentoMayo.setCellFormula("SUM(G2:G" + numFilaDatosMayo + ")");
            Cell CeldaSumaInteresMayo = filaDatosDeudoresMayo.createCell(7);
            CeldaSumaInteresMayo.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresMayo.setCellFormula("SUM(H2:H" + numFilaDatosMayo + ")");
            Cell CeldaSumaTotalMayo = filaDatosDeudoresMayo.createCell(8);
            CeldaSumaTotalMayo.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalMayo.setCellFormula("SUM(I2:I" + numFilaDatosMayo + ")");
            Cell CeldaSumaFondoMayo = filaDatosDeudoresMayo.createCell(12);
            CeldaSumaFondoMayo.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondoMayo.setCellFormula("SUM(M2:M" + numFilaDatosMayo + ")");

            Row filaDatosDeudoresJunio = junio.createRow(numFilaDatosJunio + 1);
            Cell CeldaSumaMontoJunio = filaDatosDeudoresJunio.createCell(4);
            CeldaSumaMontoJunio.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontoJunio.setCellFormula("SUM(E2:E" + numFilaDatosJunio + ")");
            Cell CeldaSumaDescuentoJunio = filaDatosDeudoresJunio.createCell(6);
            CeldaSumaDescuentoJunio.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentoJunio.setCellFormula("SUM(G2:G" + numFilaDatosJunio + ")");
            Cell CeldaSumaInteresJunio = filaDatosDeudoresJunio.createCell(7);
            CeldaSumaInteresJunio.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresJunio.setCellFormula("SUM(H2:H" + numFilaDatosJunio + ")");
            Cell CeldaSumaTotalJunio = filaDatosDeudoresJunio.createCell(8);
            CeldaSumaTotalJunio.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalJunio.setCellFormula("SUM(I2:I" + numFilaDatosJunio + ")");
            Cell CeldaSumaFondoJunio = filaDatosDeudoresJunio.createCell(12);
            CeldaSumaFondoJunio.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondoJunio.setCellFormula("SUM(M2:M" + numFilaDatosJunio + ")");

            Row filaDatosDeudoresJulio = julio.createRow(numFilaDatosJulio + 1);
            Cell CeldaSumaMontoJulio = filaDatosDeudoresJulio.createCell(4);
            CeldaSumaMontoJulio.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontoJulio.setCellFormula("SUM(E2:E" + numFilaDatosJulio + ")");
            Cell CeldaSumaDescuentoJulio = filaDatosDeudoresJulio.createCell(6);
            CeldaSumaDescuentoJulio.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentoJulio.setCellFormula("SUM(G2:G" + numFilaDatosJulio + ")");
            Cell CeldaSumaInteresJulio = filaDatosDeudoresJulio.createCell(7);
            CeldaSumaInteresJulio.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresJulio.setCellFormula("SUM(H2:H" + numFilaDatosJulio + ")");
            Cell CeldaSumaTotalJulio = filaDatosDeudoresJulio.createCell(8);
            CeldaSumaTotalJulio.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalJulio.setCellFormula("SUM(I2:I" + numFilaDatosJulio + ")");
            Cell CeldaSumaFondoJulio = filaDatosDeudoresJulio.createCell(12);
            CeldaSumaFondoJulio.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondoJulio.setCellFormula("SUM(M2:M" + numFilaDatosJulio + ")");

            Row filaDatosDeudoresAgosto = agosto.createRow(numFilaDatosAgosto + 1);
            Cell CeldaSumaMontoAgosto = filaDatosDeudoresAgosto.createCell(4);
            CeldaSumaMontoAgosto.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontoAgosto.setCellFormula("SUM(E2:E" + numFilaDatosAgosto + ")");
            Cell CeldaSumaDescuentoAgosto = filaDatosDeudoresAgosto.createCell(6);
            CeldaSumaDescuentoAgosto.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentoAgosto.setCellFormula("SUM(G2:G" + numFilaDatosAgosto + ")");
            Cell CeldaSumaInteresAgosto = filaDatosDeudoresAgosto.createCell(7);
            CeldaSumaInteresAgosto.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresAgosto.setCellFormula("SUM(H2:H" + numFilaDatosAgosto + ")");
            Cell CeldaSumaTotalAgosto = filaDatosDeudoresAgosto.createCell(8);
            CeldaSumaTotalAgosto.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalAgosto.setCellFormula("SUM(I2:I" + numFilaDatosAgosto + ")");
            Cell CeldaSumaFondoAgosto = filaDatosDeudoresAgosto.createCell(12);
            CeldaSumaFondoAgosto.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondoAgosto.setCellFormula("SUM(M2:M" + numFilaDatosAgosto + ")");

            Row filaDatosDeudoresSeptiembre = septiembre.createRow(numFilaDatosSeptiembre + 1);
            Cell CeldaSumaMontoSeptiembre = filaDatosDeudoresSeptiembre.createCell(4);
            CeldaSumaMontoSeptiembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontoSeptiembre.setCellFormula("SUM(E2:E" + numFilaDatosSeptiembre + ")");
            Cell CeldaSumaDescuentoSeptiembre = filaDatosDeudoresSeptiembre.createCell(6);
            CeldaSumaDescuentoSeptiembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentoSeptiembre.setCellFormula("SUM(G2:G" + numFilaDatosSeptiembre + ")");
            Cell CeldaSumaInteresSeptiembre = filaDatosDeudoresSeptiembre.createCell(7);
            CeldaSumaInteresSeptiembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresSeptiembre.setCellFormula("SUM(H2:H" + numFilaDatosSeptiembre + ")");
            Cell CeldaSumaTotalSeptiembre = filaDatosDeudoresSeptiembre.createCell(8);
            CeldaSumaTotalSeptiembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalSeptiembre.setCellFormula("SUM(I2:I" + numFilaDatosSeptiembre + ")");
            Cell CeldaSumaFondoSeptiembre = filaDatosDeudoresSeptiembre.createCell(12);
            CeldaSumaFondoSeptiembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondoSeptiembre.setCellFormula("SUM(M2:M" + numFilaDatosSeptiembre + ")");

            Row filaDatosDeudoresOctubre = octubre.createRow(numFilaDatosOctubre + 1);
            Cell CeldaSumaMontoOctubre = filaDatosDeudoresOctubre.createCell(4);
            CeldaSumaMontoOctubre.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontoOctubre.setCellFormula("SUM(E2:E" + numFilaDatosOctubre + ")");
            Cell CeldaSumaDescuentoOctubre = filaDatosDeudoresOctubre.createCell(6);
            CeldaSumaDescuentoOctubre.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentoOctubre.setCellFormula("SUM(G2:G" + numFilaDatosOctubre + ")");
            Cell CeldaSumaInteresOctubre = filaDatosDeudoresOctubre.createCell(7);
            CeldaSumaInteresOctubre.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresOctubre.setCellFormula("SUM(H2:H" + numFilaDatosOctubre + ")");
            Cell CeldaSumaTotalOctubre = filaDatosDeudoresOctubre.createCell(8);
            CeldaSumaTotalOctubre.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalOctubre.setCellFormula("SUM(I2:I" + numFilaDatosOctubre + ")");
            Cell CeldaSumaFondoOctubre = filaDatosDeudoresOctubre.createCell(12);
            CeldaSumaFondoOctubre.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondoOctubre.setCellFormula("SUM(M2:M" + numFilaDatosOctubre + ")");

            Row filaDatosDeudoresNoviembre = noviembre.createRow(numFilaDatosNoviembre + 1);
            Cell CeldaSumaMontoNoviembre = filaDatosDeudoresNoviembre.createCell(4);
            CeldaSumaMontoNoviembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontoNoviembre.setCellFormula("SUM(E2:E" + numFilaDatosNoviembre + ")");
            Cell CeldaSumaDescuentoNoviembre = filaDatosDeudoresNoviembre.createCell(6);
            CeldaSumaDescuentoNoviembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentoNoviembre.setCellFormula("SUM(G2:G" + numFilaDatosNoviembre + ")");
            Cell CeldaSumaInteresNoviembre = filaDatosDeudoresNoviembre.createCell(7);
            CeldaSumaInteresNoviembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresNoviembre.setCellFormula("SUM(H2:H" + numFilaDatosNoviembre + ")");
            Cell CeldaSumaTotalNoviembre = filaDatosDeudoresNoviembre.createCell(8);
            CeldaSumaTotalNoviembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalNoviembre.setCellFormula("SUM(I2:I" + numFilaDatosNoviembre + ")");
            Cell CeldaSumaFondoNoviembre = filaDatosDeudoresNoviembre.createCell(12);
            CeldaSumaFondoNoviembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondoNoviembre.setCellFormula("SUM(M2:M" + numFilaDatosNoviembre + ")");

            Row filaDatosDeudoresDiciembre = diciembre.createRow(numFilaDatosDiciembre + 1);
            Cell CeldaSumaMontoDiciembre = filaDatosDeudoresDiciembre.createCell(4);
            CeldaSumaMontoDiciembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaMontoDiciembre.setCellFormula("SUM(E2:E" + numFilaDatosDiciembre + ")");
            Cell CeldaSumaDescuentoDiciembre = filaDatosDeudoresDiciembre.createCell(6);
            CeldaSumaDescuentoDiciembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaDescuentoDiciembre.setCellFormula("SUM(G2:G" + numFilaDatosDiciembre + ")");
            Cell CeldaSumaInteresDiciembre = filaDatosDeudoresDiciembre.createCell(7);
            CeldaSumaInteresDiciembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaInteresDiciembre.setCellFormula("SUM(H2:H" + numFilaDatosDiciembre + ")");
            Cell CeldaSumaTotalDiciembre = filaDatosDeudoresDiciembre.createCell(8);
            CeldaSumaTotalDiciembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaTotalDiciembre.setCellFormula("SUM(I2:I" + numFilaDatosDiciembre + ")");
            Cell CeldaSumaFondoDiciembre = filaDatosDeudoresDiciembre.createCell(12);
            CeldaSumaFondoDiciembre.setCellStyle(datosEstiloMoneda);
            CeldaSumaFondoDiciembre.setCellFormula("SUM(M2:M" + numFilaDatosDiciembre + ")");

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasReporteTrimestral.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ps = con.prepareStatement(sqlPrestamosHonorarios);
            if (Integer.parseInt(numeroreporte) == 1) {
                ps.setString(1, periodo + "01");
                ps.setString(2, periodo + "06");

            } else if (Integer.parseInt(numeroreporte) == 2) {
                ps.setString(1, periodo + "07");
                ps.setString(2, periodo + "12");

            } else if (Integer.parseInt(numeroreporte) == 3) {
                ps.setString(1, periodo + "13");
                ps.setString(2, periodo + "18");
            } else if (Integer.parseInt(numeroreporte) == 4) {
                ps.setString(1, periodo + "19");
                ps.setString(2, periodo + "24");
            } else {
            }

            rs = ps.executeQuery();
            int numCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {

                qnahonorarios = rs.getString("qna").substring(4, 6);
                if (qnahonorarios.equals("01") || qnahonorarios.equals("02")
                        || qnahonorarios.equals("07") || qnahonorarios.equals("08")
                        || qnahonorarios.equals("13") || qnahonorarios.equals("14")
                        || qnahonorarios.equals("19") || qnahonorarios.equals("20")) {
                    totalprimermes = totalprimermes + rs.getDouble("monto");
                }
                if (qnahonorarios.equals("03") || qnahonorarios.equals("04")
                        || qnahonorarios.equals("09") || qnahonorarios.equals("10")
                        || qnahonorarios.equals("15") || qnahonorarios.equals("16")
                        || qnahonorarios.equals("21") || qnahonorarios.equals("22")) {
                    totalsegundomes = totalsegundomes + rs.getDouble("monto");
                }
                if (qnahonorarios.equals("05") || qnahonorarios.equals("06")
                        || qnahonorarios.equals("11") || qnahonorarios.equals("12")
                        || qnahonorarios.equals("17") || qnahonorarios.equals("18")
                        || qnahonorarios.equals("23") || qnahonorarios.equals("24")) {
                    totaltercermes = totaltercermes + rs.getDouble("monto");
                }

                //for (int a = 0; a < numCol; a++) {
                //Cell CeldaNumeroquincena = filaDatosHonorarios.createCell(0);
                //CeldaNumeroquincena.setCellStyle(datosEstilo);
                //CeldaNumeroquincena.setCellValue(rs.getString("monto"));
                numFilaDatosHonorarios++;

            }
            total = totalprimermes + totalsegundomes + totaltercermes;
            totalporcentaje = ((totalprimermes + totalsegundomes + totaltercermes) * 1) / 100;
            rowPrimerMes.createCell(2).setCellValue(totalprimermes);
            rowSegundoMes.createCell(2).setCellValue(totalsegundomes);
            rowTercerMes.createCell(2).setCellValue(totaltercermes);
            rowTotal.createCell(2).setCellValue(total);
            rowHonorariosMes.createCell(2).setCellValue(totalporcentaje);

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasReporteTrimestral.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

              JFileChooser fileChooser = new JFileChooser();
            
            
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel","xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("ReporteTrimestral" + periodo + numeroreporte + ".xlsx").getCanonicalPath());
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

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //JOptionPane.showMessageDialog(null, "REPORTE TRIMESTRAL");
    }

    public void calcularrendimientos(ReporteTrimestral mov){
        Connection con = getConexion();
        Workbook book = new XSSFWorkbook();
        Sheet Rendimientos = book.createSheet("RENDIMIENTOS");
        
        Row row = Rendimientos.createRow(0);
        row.createCell(0).setCellValue("RFC");
        row.createCell(1).setCellValue("NOMBRE");
        row.createCell(2).setCellValue("PLANTEL");
        row.createCell(3).setCellValue("IMPORTE");
        row.createCell(4).setCellValue("NUMEROQUINCENA");
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
       
        
        
        String sqlprueba = "select * FROM  qnaahorrorecuperada  WHERE numeroquincena = ? and literal in ('ITA','ICI') limit 1";
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        periodo = mov.getPeriodoi();
        numeroreporte = mov.getNumeroi();
        if (numeroreporte == "01") {
            numeroreporte = "06";
            tasames = "01";
            qnainicial = periodo + "00";
            qnafinal = periodo + "00";
            
            qna1 = periodo + "01";
            qna2 = periodo + "02";
            qna3 = periodo + "03";
            qna4 = periodo + "04";
            qna5 = periodo + "05";
            
            
        } else if (numeroreporte == "02") {
            numeroreporte = "12";
            tasames = "04";
            qnainicial = periodo + "01";
            qnafinal = periodo + "06";
            
            qna1 = periodo + "07";
            qna2 = periodo + "08";
            qna3 = periodo + "09";
            qna4 = periodo + "10";
            qna5 = periodo + "11";
            
        } else if (numeroreporte == "03") {
            numeroreporte = "18";
            tasames = "07";
            qnainicial = periodo + "01";
            qnafinal = periodo + "12";
            
            qna1 = periodo + "13";
            qna2 = periodo + "14";
            qna3 = periodo + "15";
            qna4 = periodo + "16";
            qna5 = periodo + "17";
            
        } else if (numeroreporte == "04") {
            numeroreporte = "24";
            tasames = "09";
            qnainicial = periodo + "01";
            qnafinal = periodo + "18";
            
            qna1 = periodo + "19";
            qna2 = periodo + "20";
            qna3 = periodo + "21";
            qna4 = periodo + "22";
            qna5 = periodo + "23";
        }
        String sqlgenera = "select * from qnaahorrorecuperada where numeroquincena = ? and literal in ('ITA','ICI')";
        String tasa = "select ((promedio*5)/24)/100 as promedio from tasa where ano = ? and mes = ?";
        String sqlRendimientos = "select \n"
                + "(select CASE WHEN sum(importe)*6*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) IS NULL THEN 0 ELSE sum(importe)*6*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) END from qnaahorrorecuperada where numeroquincena >= ? and numeroquincena <= ? and literal in ('CII','CIP') and rfc = a.rfc)+\n"
                + "(select CASE WHEN sum(importe)*5*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) IS NULL THEN 0 ELSE sum(importe)*5*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) END from qnaahorrorecuperada where numeroquincena = ? and rfc = a.rfc)+\n"
                + "(select CASE WHEN sum(importe)*4*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) IS NULL THEN 0 ELSE sum(importe)*4*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) END from qnaahorrorecuperada where numeroquincena = ? and rfc = a.rfc)+\n"
                + "(select CASE WHEN sum(importe)*3*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) IS NULL THEN 0 ELSE sum(importe)*3*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) END  from qnaahorrorecuperada where numeroquincena = ? and rfc = a.rfc)+\n"
                + "(select CASE WHEN sum(importe)*2*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) IS NULL THEN 0 ELSE sum(importe)*2*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) END  from qnaahorrorecuperada where numeroquincena = ? and rfc = a.rfc)+\n"
                + "(select CASE WHEN sum(importe)*1*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) IS NULL THEN 0 ELSE sum(importe)*1*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?) END  from qnaahorrorecuperada where numeroquincena = ? and rfc = a.rfc)+\n"
                + "round((SUM(IF(a.literal in ('CIP','CII','ICI','ITA','REG')  and SUBSTR(numeroquincena,1,4) < ?\n"
                + "				,a.importe, 0))- SUM(IF(a.literal in ('DIV')  and SUBSTR(numeroquincena,1,4) < ?, a.importe, 0)))*6*(select ((promedio*5)/24)/100 as promedio from tasa  where mes = ? and ano = ?),2)\n"
                + "				as abono,\n"
                + "nombre,rfc,literal,plantel from qnaahorrorecuperada a    group by rfc";
        
         String sqlinserta = "insert into "
                + "qnaahorrorecuperada(rfc,nombre,plantel,importe,numeroquincena,literal)"
                + "values(?,?,?,?,?,?)";
                
        try {
            psVerifica = con.prepareStatement(sqlprueba);
            psVerifica.setString(1, periodo + numeroreporte);
            rsverifica = psVerifica.executeQuery();
            if (!rsverifica.next()) {
            ////////////////    
            
            try {
                    PreparedStatement psRendimientos = null;
                    ResultSet rsRendimientos = null;
                    psRendimientos = con.prepareStatement(sqlRendimientos);
                    psRendimientos.setString(1,tasames);
                    psRendimientos.setString(2,periodo);
                    psRendimientos.setString(3,tasames);
                    psRendimientos.setString(4,periodo);
                     psRendimientos.setString(5,qnainicial);
                    psRendimientos.setString(6,qnafinal);
                    psRendimientos.setString(7,tasames);
                    psRendimientos.setString(8,periodo);
                    psRendimientos.setString(9,tasames);
                    psRendimientos.setString(10,periodo);
                    psRendimientos.setString(11,qna1);
                    psRendimientos.setString(12,tasames);
                    psRendimientos.setString(13,periodo);
                    psRendimientos.setString(14,tasames);
                    psRendimientos.setString(15,periodo);
                    psRendimientos.setString(16,qna2);
                    psRendimientos.setString(17,tasames);
                    psRendimientos.setString(18,periodo);
                    psRendimientos.setString(19,tasames);
                    psRendimientos.setString(20,periodo);
                    psRendimientos.setString(21,qna3);
                    psRendimientos.setString(22,tasames);
                    psRendimientos.setString(23,periodo);
                    psRendimientos.setString(24,tasames);
                    psRendimientos.setString(25,periodo);
                    psRendimientos.setString(26,qna4);
                    psRendimientos.setString(22,tasames);
                    psRendimientos.setString(23,periodo);
                    psRendimientos.setString(24,tasames);
                    psRendimientos.setString(25,periodo);
                    psRendimientos.setString(26,qna4);
                    psRendimientos.setString(27,tasames);
                    psRendimientos.setString(28,periodo);
                    psRendimientos.setString(29,tasames);
                    psRendimientos.setString(30,periodo);
                    psRendimientos.setString(31,qna5);
                    psRendimientos.setString(32,periodo);
                    psRendimientos.setString(33,periodo);
                    psRendimientos.setString(34,tasames);
                    psRendimientos.setString(35,periodo);;
                    
                    rsRendimientos = psRendimientos.executeQuery();
                    while (rsRendimientos.next()) {

                        PreparedStatement psInserta = null;
                        ResultSet rsInserta = null;
                        psInserta = con.prepareStatement(sqlinserta);
                        //psInserta.setString(1, mov.getNumeroQuincena());

                        psInserta.setString(1,rsRendimientos.getString("rfc"));
                        psInserta.setString(2,rsRendimientos.getString("nombre"));
                        psInserta.setString(3,rsRendimientos.getString("plantel"));
                        psInserta.setDouble(4,rsRendimientos.getDouble("abono"));
                        psInserta.setString(5,periodo+numeroreporte);
                        psInserta.setString(6,"ITA");
                        RendimientoComparar = rsRendimientos.getDouble("abono");
                        if(RendimientoComparar > 0)
                        {    
                        psInserta.execute();
                        }
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                }
                
              PreparedStatement psGenera = null;
                    ResultSet rsGenera = null;
                    try{
                    psGenera = con.prepareStatement(sqlgenera);
                    psGenera.setString(1,periodo + numeroreporte);
                    rsGenera = psGenera.executeQuery();
                    int numCol = rsGenera.getMetaData().getColumnCount();
                    
                    while (rsGenera.next()) {

                Row filaDatosIndebidos = Rendimientos.createRow(numFilaDatosRendimientos);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosIndebidos.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rsGenera.getString("rfc"));

                    Cell CeldaPlantel = filaDatosIndebidos.createCell(1);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rsGenera.getString("nombre"));

                    Cell CeldaImporte = filaDatosIndebidos.createCell(2);
                    CeldaImporte.setCellStyle(datosEstilo);
                    CeldaImporte.setCellValue(rsGenera.getString("plantel"));

                    Cell CeldaFacore = filaDatosIndebidos.createCell(3);
                    CeldaFacore.setCellStyle(datosEstiloMoneda);
                    CeldaFacore.setCellValue(rsGenera.getDouble("importe"));

                    Cell CeldaNumeroEmpleado = filaDatosIndebidos.createCell(4);
                    CeldaNumeroEmpleado.setCellStyle(datosEstilo);
                    CeldaNumeroEmpleado.setCellValue(rsGenera.getInt("numeroquincena"));

                    
                }
                numFilaDatosRendimientos++;

            }
                    

                    } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            ///////////////////////////// 
            } 
            else {
             
                
                    PreparedStatement psGenera = null;
                    ResultSet rsGenera = null;
                    try{
                    psGenera = con.prepareStatement(sqlgenera);
                    psGenera.setString(1,periodo + numeroreporte);
                    rsGenera = psGenera.executeQuery();
                    int numCol = rsGenera.getMetaData().getColumnCount();
                    
                    while (rsGenera.next()) {

                Row filaDatosIndebidos = Rendimientos.createRow(numFilaDatosRendimientos);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosIndebidos.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rsGenera.getString("rfc"));

                    Cell CeldaPlantel = filaDatosIndebidos.createCell(1);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rsGenera.getString("nombre"));

                    Cell CeldaImporte = filaDatosIndebidos.createCell(2);
                    CeldaImporte.setCellStyle(datosEstilo);
                    CeldaImporte.setCellValue(rsGenera.getString("plantel"));

                    Cell CeldaFacore = filaDatosIndebidos.createCell(3);
                    CeldaFacore.setCellStyle(datosEstiloMoneda);
                    CeldaFacore.setCellValue(rsGenera.getDouble("importe"));

                    Cell CeldaNumeroEmpleado = filaDatosIndebidos.createCell(4);
                    CeldaNumeroEmpleado.setCellStyle(datosEstilo);
                    CeldaNumeroEmpleado.setCellValue(rsGenera.getInt("numeroquincena"));

                    
                }
                numFilaDatosRendimientos++;

            }
                    

                    } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
                    
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {

             JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel","xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("Rendimientos" + periodo+numeroreporte + ".xlsx").getCanonicalPath());
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
           /* FileOutputStream fileout = new FileOutputStream("Rendimientos" + periodo+numeroreporte + ".xlsx");
            book.write(fileout);
            fileout.close();*/

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
     
    
}
