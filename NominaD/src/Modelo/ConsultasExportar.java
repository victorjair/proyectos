/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import static Modelo.ConsultaEmpleado.QUOTE;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author victor
 */
public class ConsultasExportar extends Conexion {

    public static final String SEPARATOR = ",";
    public static final String QUOTE = "\"";
    private String periodo;
    private String nombrereporte;
    private String plantel;
    private String tipoplantel;
    private String clavereporte;
    private String qnaactual;
    String fechafinalantiguedad = "";
    String fechapago = "";
    String fechainiciopago = "";
    String fechafinalpago = "";

    

    public ConsultasExportar() {
    }

    private static String[] removeTrailingQuotes(String[] fields) {

        String result[] = new String[fields.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = fields[i].replaceAll("^" + QUOTE, "").replaceAll(QUOTE + "$", "");
        }
        return result;
    }
    public void exportarinformecuotasyaportaciones(Reporte reporte) {
        Connection con = getConexion();
        String sqlprueba = "select plantel,sum(P_01) as p01,sum(P_01A) as p01A,sum(P_01B) as p01B,sum(P_01C) as p01C,"
                + "sum(P_01D) as p01D,sum(P_01E) as p01E,sum(P_03) as p03 from hojaisp where NUMEROQUINCENA = ? "
                + "and TPLANTEL = ? group by plantel asc";

        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("CuotasYAportaciones");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);
        Row rowencabezado7 = hojareporte.createRow(6);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        row.createCell(0).setCellValue("PLANTEL");
        row.createCell(1).setCellValue("P_01");
        row.createCell(2).setCellValue("P_01A");
        row.createCell(3).setCellValue("P_01B");
        row.createCell(4).setCellValue("P_01C");
        row.createCell(5).setCellValue("P_01D");
        row.createCell(6).setCellValue("P_01E");
        row.createCell(7).setCellValue("P_03");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement pscuotasyaportaciones = null;
        ResultSet rs = null;
        ResultSet rscuotasyaportaciones = null;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            pscuotasyaportaciones = con.prepareStatement(sqlprueba);
            pscuotasyaportaciones.setString(1, reporte.getPeriodo());
            pscuotasyaportaciones.setString(2, reporte.getTipoPlantelReporte());
            rscuotasyaportaciones = pscuotasyaportaciones.executeQuery();

            int numCol = rscuotasyaportaciones.getMetaData().getColumnCount();

            while (rscuotasyaportaciones.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell Celdap01 = filaDatosCuotasYAportaciones.createCell(0);
                    Celdap01.setCellStyle(datosEstilo);
                    Celdap01.setCellValue(rscuotasyaportaciones.getString("plantel"));

                    Cell Celdap01A = filaDatosCuotasYAportaciones.createCell(1);
                    Celdap01A.setCellStyle(datosEstiloMoneda);
                    Celdap01A.setCellValue(rscuotasyaportaciones.getDouble("p01"));

                    Cell Celdap01B = filaDatosCuotasYAportaciones.createCell(2);
                    Celdap01B.setCellStyle(datosEstiloMoneda);
                    Celdap01B.setCellValue(rscuotasyaportaciones.getDouble("p01A"));

                    Cell Celdap01C = filaDatosCuotasYAportaciones.createCell(3);
                    Celdap01C.setCellStyle(datosEstiloMoneda);
                    Celdap01C.setCellValue(rscuotasyaportaciones.getDouble("p01B"));

                    Cell Celdap01D = filaDatosCuotasYAportaciones.createCell(4);
                    Celdap01D.setCellStyle(datosEstiloMoneda);
                    Celdap01D.setCellValue(rscuotasyaportaciones.getDouble("p01C"));

                    Cell Celdap01E = filaDatosCuotasYAportaciones.createCell(5);
                    Celdap01E.setCellStyle(datosEstiloMoneda);
                    Celdap01E.setCellValue(rscuotasyaportaciones.getDouble("p01D"));

                    Cell Celdap01EE = filaDatosCuotasYAportaciones.createCell(6);
                    Celdap01EE.setCellStyle(datosEstiloMoneda);
                    Celdap01EE.setCellValue(rscuotasyaportaciones.getDouble("p01E"));

                    Cell Celdap03 = filaDatosCuotasYAportaciones.createCell(7);
                    Celdap03.setCellStyle(datosEstiloMoneda);
                    Celdap03.setCellValue(rscuotasyaportaciones.getDouble("p03"));

                }
                numFilaDatosCuotasYAportaciones++;

            }
            int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);
            Row sumapercepciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 3);
            Row cuotasisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 4);
            Row fovisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 5);
            Row aportacionisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 6);

            Cell Celdasumas = sumapercepciones.createCell(0);
            Celdasumas.setCellStyle(datosEstilo);
            Celdasumas.setCellValue("SUMA DE PERCEPCIONES");

            Cell Celdascuotasisste = cuotasisste.createCell(0);
            Celdascuotasisste.setCellStyle(datosEstilo);
            Celdascuotasisste.setCellValue("CUOTAS ISSSTE 8.525%");

            Cell Celdasfovisste = fovisste.createCell(0);
            Celdasfovisste.setCellStyle(datosEstilo);
            Celdasfovisste.setCellValue("APORTACION FOVISSTE 5%");

            Cell Celdasaportacionisste = aportacionisste.createCell(0);
            Celdasaportacionisste.setCellStyle(datosEstilo);
            Celdasaportacionisste.setCellValue("APORTACION ISSSTE 9.970%");

            Cell Celdasumap1 = filaDatosSuma.createCell(1);
            Celdasumap1.setCellStyle(datosEstiloMoneda);
            Celdasumap1.setCellFormula("SUM(B2:B" + numFilaDatosCuotasYAportaciones + ")");

            Cell Celdasumap2 = filaDatosSuma.createCell(2);
            Celdasumap2.setCellStyle(datosEstiloMoneda);
            Celdasumap2.setCellFormula("SUM(C2:C" + numFilaDatosCuotasYAportaciones + ")");

            Cell Celdasumap3 = filaDatosSuma.createCell(3);
            Celdasumap3.setCellStyle(datosEstiloMoneda);
            Celdasumap3.setCellFormula("SUM(D2:D" + numFilaDatosCuotasYAportaciones + ")");

            Cell Celdasumap4 = filaDatosSuma.createCell(4);
            Celdasumap4.setCellStyle(datosEstiloMoneda);
            Celdasumap4.setCellFormula("SUM(E2:E" + numFilaDatosCuotasYAportaciones + ")");

            Cell Celdasumap5 = filaDatosSuma.createCell(5);
            Celdasumap5.setCellStyle(datosEstiloMoneda);
            Celdasumap5.setCellFormula("SUM(F2:F" + numFilaDatosCuotasYAportaciones + ")");

            Cell Celdasumap6 = filaDatosSuma.createCell(6);
            Celdasumap6.setCellStyle(datosEstiloMoneda);
            Celdasumap6.setCellFormula("SUM(G2:G" + numFilaDatosCuotasYAportaciones + ")");

            Cell Celdasumap7 = filaDatosSuma.createCell(7);
            Celdasumap7.setCellStyle(datosEstiloMoneda);
            Celdasumap7.setCellFormula("SUM(H2:H" + numFilaDatosCuotasYAportaciones + ")");

            Cell Celdasumas2 = sumapercepciones.createCell(1);
            Celdasumas2.setCellStyle(datosEstiloMoneda);
            Celdasumas2.setCellFormula("SUM(B" + filasumas + ":H" + filasumas + ")");

            Cell Celdascuotasisste2 = cuotasisste.createCell(1);
            Celdascuotasisste2.setCellStyle(datosEstiloMoneda);
            Celdascuotasisste2.setCellFormula("0.08525*SUM(B" + filasumas + ":H" + filasumas + ")");

            Cell Celdasfovisste2 = fovisste.createCell(1);
            Celdasfovisste2.setCellStyle(datosEstiloMoneda);
            Celdasfovisste2.setCellFormula("0.05*SUM(B" + filasumas + ":H" + filasumas + ")");

            Cell Celdasaportacionisste2 = aportacionisste.createCell(1);
            Celdasaportacionisste2.setCellStyle(datosEstiloMoneda);
            Celdasaportacionisste2.setCellFormula("0.09970*SUM(B" + filasumas + ":H" + filasumas + ")");

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("CuotasYAportacionesSP" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformetrabajadorescotizantes(Reporte reporte) {
        Connection con = getConexion();
        String sqlprueba = "select count(TIPOPER) as cuenta,TIPOPER as tipo from hojaisp where numeroquincena = ? group "
                + "by TIPOPER";

        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("TrabajadoresCotizantez");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);
        Row rowencabezado7 = hojareporte.createRow(6);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        row.createCell(0).setCellValue("TIPO DE NOMBRAMIENTO");
        row.createCell(1).setCellValue("NUMERO DE TRABADORES");
        /*row.createCell(2).setCellValue("P_01A");
        row.createCell(3).setCellValue("P_01B");
        row.createCell(4).setCellValue("P_01C");
        row.createCell(5).setCellValue("P_01D");
        row.createCell(6).setCellValue("P_01E");
        row.createCell(7).setCellValue("P_03");*/

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell Celdap01 = filaDatosCuotasYAportaciones.createCell(0);
                    Celdap01.setCellStyle(datosEstilo);
                    Celdap01.setCellValue(rstrabajadorescotizantez.getString("tipo"));

                    Cell Celdap01A = filaDatosCuotasYAportaciones.createCell(1);
                    Celdap01A.setCellStyle(datosEstilo);
                    Celdap01A.setCellValue(rstrabajadorescotizantez.getInt("cuenta"));

                }
                numFilaDatosCuotasYAportaciones++;

            }
            int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);
            Row sumapercepciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 3);
            Row cuotasisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 4);
            Row fovisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 5);
            Row aportacionisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 6);

            Cell Celdasumas = sumapercepciones.createCell(0);
            Celdasumas.setCellStyle(datosEstilo);
            Celdasumas.setCellValue("TOTAL");

            Cell Celdasumap1 = filaDatosSuma.createCell(1);
            Celdasumap1.setCellStyle(datosEstilo);
            Celdasumap1.setCellFormula("SUM(B2:B" + numFilaDatosCuotasYAportaciones + ")");

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("TrabajadoresCotizantesSP" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformedispersiondepension(Reporte reporte) {
        Connection con = getConexion();
        String sqlprueba = "select rfc,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,D_10 from hojaisp where numeroquincena = ? and D_10 > 0";

        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("DispersionDePension");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);
        Row rowencabezado7 = hojareporte.createRow(6);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        row.createCell(0).setCellValue("RFC");
        row.createCell(1).setCellValue("NOMBRE");
        row.createCell(2).setCellValue("PLANTEL");
        row.createCell(3).setCellValue("COBRA");
        row.createCell(4).setCellValue("PERCEPCION");
        row.createCell(5).setCellValue("DEDUCCION");
        row.createCell(6).setCellValue("NETO");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell Celdap01 = filaDatosCuotasYAportaciones.createCell(0);
                    Celdap01.setCellStyle(datosEstilo);
                    Celdap01.setCellValue(rstrabajadorescotizantez.getString("rfc"));

                    Cell Celdap01A = filaDatosCuotasYAportaciones.createCell(1);
                    Celdap01A.setCellStyle(datosEstilo);
                    Celdap01A.setCellValue(rstrabajadorescotizantez.getString("nombre"));

                    Cell CeldaPlantel = filaDatosCuotasYAportaciones.createCell(2);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rstrabajadorescotizantez.getString("plantel"));

                    Cell CeldaCobra = filaDatosCuotasYAportaciones.createCell(3);
                    CeldaCobra.setCellStyle(datosEstilo);
                    CeldaCobra.setCellValue("SI");

                    Cell CeldaD_10 = filaDatosCuotasYAportaciones.createCell(4);
                    CeldaD_10.setCellStyle(datosEstilo);
                    CeldaD_10.setCellValue(rstrabajadorescotizantez.getDouble("D_10"));

                    Cell CeldaDeduccion = filaDatosCuotasYAportaciones.createCell(5);
                    CeldaDeduccion.setCellStyle(datosEstilo);
                    CeldaDeduccion.setCellValue("0");

                    Cell CeldaNeto = filaDatosCuotasYAportaciones.createCell(6);
                    CeldaNeto.setCellStyle(datosEstilo);
                    CeldaNeto.setCellValue(rstrabajadorescotizantez.getDouble("D_10"));

                }
                numFilaDatosCuotasYAportaciones++;

            }
            int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);
            Row sumapercepciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 3);
            Row cuotasisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 4);
            Row fovisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 5);
            Row aportacionisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 6);

            Cell Celdasumas = sumapercepciones.createCell(0);
            Celdasumas.setCellStyle(datosEstilo);
            Celdasumas.setCellValue("TOTAL");

            Cell Celdasconteopensiones = sumapercepciones.createCell(1);
            Celdasconteopensiones.setCellStyle(datosEstilo);
            Celdasconteopensiones.setCellValue(numFilaDatosCuotasYAportaciones - 10);

            Cell Celdasumap1 = sumapercepciones.createCell(4);
            Celdasumap1.setCellStyle(datosEstilo);
            Celdasumap1.setCellFormula("SUM(E10:E" + numFilaDatosCuotasYAportaciones + ")");

            Cell Celdasumap2 = sumapercepciones.createCell(6);
            Celdasumap2.setCellStyle(datosEstilo);
            Celdasumap2.setCellFormula("SUM(G10:G" + numFilaDatosCuotasYAportaciones + ")");

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("DispersionDePensionSP" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformedcaratuladenomina(Reporte reporte) {
        Connection con = getConexion();
        String sqlprueba = " select SUM(P_01)as p1,SUM(P_02)as p2,SUM(P_03)as p3,SUM(P_04)as p4,SUM(P_05)as p5\n"
                + "            ,SUM(P_06)as p6,SUM(P_07)as p7,SUM(P_08)as p8,SUM(P_09)as p9,SUM(P_10)as p10,SUM(P_11)as p11\n"
                + "            ,SUM(P_12)as p12,SUM(P_13)as p13,SUM(P_14)as p14,SUM(P_15)as p15,SUM(P_16)as p16,SUM(P_17)as p17,SUM(P_18)as p18\n"
                + "            ,SUM(P_19)as p19,SUM(P_20)as p20,SUM(P_21)as p21,SUM(P_22)as p22,SUM(P_23)as p23,SUM(P_24)as p24,SUM(P_25)as p25\n"
                + "            ,SUM(P_26)as p26,SUM(P_27)as p27,SUM(P_28)as p28,SUM(P_29)as p29,SUM(P_30)as p30,SUM(P_31)as p31,SUM(P_32)as p32\n"
                + "            ,SUM(P_33)as p33,SUM(P_34)as p34,SUM(P_35)as p35,SUM(P_36)as p36,SUM(P_37)as p37,SUM(P_38)as p38,SUM(P_39)as p39\n"
                + "            ,SUM(P_40)as p40,SUM(P_41)as p41,SUM(P_42)as p42,SUM(P_43)as p43,SUM(P_44)as p44,SUM(P_45)as p45,SUM(P_46)as p46\n"
                + "            ,SUM(P_47)as p47,SUM(P_48)as p48,SUM(P_49)as p49,SUM(P_50)as p50,SUM(D_01)as d1,SUM(D_02)as d2,SUM(D_03)as d3,SUM(D_04)as d4,SUM(D_05)as d5\n"
                + "            ,SUM(D_06)as d6,SUM(D_07)as d7,SUM(D_08)as d8,SUM(D_09)as d9,SUM(D_10)as d10,SUM(D_11)as d11\n"
                + "            ,SUM(D_12)as d12,SUM(D_13)as d13,SUM(D_14)as d14,SUM(D_15)as d15,SUM(D_16)as d16,SUM(D_17)as d17,SUM(D_18)as d18\n"
                + "            ,SUM(D_19)as d19,SUM(D_20)as d20,SUM(D_21)as d21,SUM(D_22)as d22,SUM(D_23)as d23,SUM(D_24)as d24,SUM(D_25)as d25\n"
                + "            ,SUM(D_26)as d26,SUM(D_27)as d27,SUM(D_28)as d28,SUM(D_29)as d29,SUM(D_30)as d30,SUM(D_31)as d31,SUM(D_32)as d32\n"
                + "            ,SUM(D_33)as d33,SUM(D_34)as d34,SUM(D_35)as d35,SUM(D_36)as d36,SUM(D_37)as d37,SUM(D_38)as d38,SUM(D_39)as d39\n"
                + "            ,SUM(D_40)as d40,SUM(D_41)as d41,SUM(D_42)as d42,SUM(D_43)as d43,SUM(D_44)as d44,SUM(D_45)as d45,SUM(D_46)as d46\n"
                + "            ,SUM(D_47)as d47,SUM(D_48)as d48,SUM(D_49)as d49,SUM(D_50)as d50,sum(P_PE) as ppe,sum(P_PG)as ppg,sum(D_CB)as dcb,\n"
                + "            SUM(P_01A)as p1a,SUM(P_01B)as p1b,SUM(P_01C)as p1c,SUM(P_01D)as p1d,SUM(P_01E)as p1e,count(if(P_01>0,1,null))as p1con,\n"
                + "            count(if(P_02>0,1,null))as p2c,count(if(P_03>0,1,null))as p3c,count(if(P_04>0,1,null))as p4c,count(if(P_05>0,1,null))as p5c,\n"
                + "            count(if(P_06>0,1,null))as p6c,count(if(P_07>0,1,null))as p7c,count(if(P_08>0,1,null))as p8c,count(if(P_09>0,1,null))as p9c,\n"
                + "            count(if(P_10>0,1,null))as p10c,count(if(P_11>0,1,null))as p11c,count(if(P_12>0,1,null))as p12c,count(if(P_13>0,1,null))as p13c,\n"
                + "            count(if(P_14>0,1,null))as p14c,count(if(P_15>0,1,null))as p15c,count(if(P_16>0,1,null))as p16c,count(if(P_17>0,1,null))as p17c,\n"
                + "            count(if(P_18>0,1,null))as p18c,count(if(P_19>0,1,null))as p19c,count(if(P_20>0,1,null))as p20c,count(if(P_21>0,1,null))as p21c,\n"
                + "            count(if(P_22>0,1,null))as p22c,count(if(P_23>0,1,null))as p23c,count(if(P_24>0,1,null))as p24c,count(if(P_25>0,1,null))as p25c,\n"
                + "            count(if(P_26>0,1,null))as p26c,count(if(P_27>0,1,null))as p27c,count(if(P_28>0,1,null))as p28c,count(if(P_29>0,1,null))as p29c,\n"
                + "            count(if(P_30>0,1,null))as p30c,count(if(P_31>0,1,null))as p31c,count(if(P_32>0,1,null))as p32c,count(if(P_33>0,1,null))as p33c,\n"
                + "            count(if(P_34>0,1,null))as p34c,count(if(P_35>0,1,null))as p35c,count(if(P_36>0,1,null))as p36c,count(if(P_37>0,1,null))as p37c,\n"
                + "            count(if(P_38>0,1,null))as p38c,count(if(P_39>0,1,null))as p39c,count(if(P_40>0,1,null))as p40c,count(if(P_41>0,1,null))as p41c,"
                + "count(if(P_42>0,1,null))as p42c,\n"
                + "            count(if(P_43>0,1,null))as p43c,count(if(P_44>0,1,null))as p44c,count(if(P_45>0,1,null))as p45c,count(if(P_46>0,1,null))as p46c,\n"
                + "            count(if(P_47>0,1,null))as p47c,count(if(P_48>0,1,null))as p48c,count(if(P_49>0,1,null))as p49c,count(if(P_50>0,1,null))as p50c,\n"
                + "            count(if(P_01A>0,1,null))as p1ac,count(if(P_01B>0,1,null))as p1bc,count(if(P_01C>0,1,null))as p1cc,count(if(P_01D>0,1,null))as p1dc,\n"
                + "            count(if(P_01E>0,1,null))as p1ec,count(if(P_PE>0,1,null))as ppec,count(if(P_PG>0,1,null))as ppgc,count(if(D_01>0,1,null))as d1c,\n"
                + "            count(if(D_02>0,1,null))as d2c,count(if(D_03>0,1,null))as d3c,count(if(D_04>0,1,null))as d4c,count(if(D_05>0,1,null))as d5c,\n"
                + "            count(if(D_06>0,1,null))as d6c,count(if(D_07>0,1,null))as d7c,count(if(D_08>0,1,null))as d8c,count(if(D_09>0,1,null))as d9c,\n"
                + "            count(if(D_10>0,1,null))as d10c,count(if(D_11>0,1,null))as d11c,count(if(D_12>0,1,null))as d12c,count(if(D_13>0,1,null))as d13c,\n"
                + "            count(if(D_14>0,1,null))as d14c,count(if(D_15>0,1,null))as d15c,count(if(D_16>0,1,null))as d16c,count(if(D_17>0,1,null))as d17c,\n"
                + "            count(if(D_18>0,1,null))as d18c,count(if(D_19>0,1,null))as d19c,count(if(D_20>0,1,null))as d20c,count(if(D_21>0,1,null))as d21c,\n"
                + "            count(if(D_22>0,1,null))as d22c,count(if(D_23>0,1,null))as d23c,count(if(D_24>0,1,null))as d24c,count(if(D_25>0,1,null))as d25c,\n"
                + "            count(if(D_26>0,1,null))as d26c,count(if(D_27>0,1,null))as d27c,count(if(D_28>0,1,null))as d28c,count(if(D_29>0,1,null))as d29c,\n"
                + "            count(if(D_30>0,1,null))as d30c,count(if(D_31>0,1,null))as d31c,count(if(D_32>0,1,null))as d32c,count(if(D_33>0,1,null))as d33c,\n"
                + "            count(if(D_34>0,1,null))as d34c,count(if(D_35>0,1,null))as d35c,count(if(D_36>0,1,null))as d36c,count(if(D_37>0,1,null))as d37c,\n"
                + "            count(if(D_38>0,1,null))as d38c,count(if(D_39>0,1,null))as d39c,count(if(D_40>0,1,null))as d40c,count(if(D_41>0,1,null))as d41c,"
                + "            count(if(D_42>0,1,null))as d42c,\n"
                + "            count(if(D_43>0,1,null))as d43c,count(if(D_44>0,1,null))as d44c,count(if(D_45>0,1,null))as d45c,count(if(D_46>0,1,null))as d46c,\n"
                + "            count(if(D_47>0,1,null))as d47c,count(if(D_48>0,1,null))as d48c,count(if(D_49>0,1,null))as d49c,count(if(D_50>0,1,null))as d50c,\n"
                + "            count(if(D_CB>0,1,null))as dcbc\n"
                + "             from hojaisp where numeroquincena = ?";

        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("CaratulaDeNomina");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);
        Row rowencabezado7 = hojareporte.createRow(6);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        row.createCell(0).setCellValue("CONCEPTO");
        row.createCell(1).setCellValue("DESCRIPCION");
        row.createCell(2).setCellValue("APLICADO");
        row.createCell(3).setCellValue("IMPORTE1");
        row.createCell(4).setCellValue("GRAVADO");
        row.createCell(5).setCellValue("EXENTO");
        row.createCell(6).setCellValue("APLICADO");
        row.createCell(7).setCellValue("CONCEPTO");
        row.createCell(8).setCellValue("DESCRIPCION");
        row.createCell(9).setCellValue("APLICADO");
        row.createCell(10).setCellValue("IMPORTE1");
        row.createCell(11).setCellValue("APLICADOS");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                //for (int a = 0; a < numCol; a++) {

                //Row rowencabezado1 = hojareporte.createRow(0);
                Cell Celdap01Cadena = filaDatosCuotasYAportaciones.createCell(0);
                Celdap01Cadena.setCellStyle(datosEstilo);
                Celdap01Cadena.setCellValue("P_01");
                Cell Celdap01Cadena2 = filaDatosCuotasYAportaciones.createCell(1);
                Celdap01Cadena2.setCellStyle(datosEstilo);
                Celdap01Cadena2.setCellValue("SUELDO");
                Cell Celdap01 = filaDatosCuotasYAportaciones.createCell(2);
                Celdap01.setCellStyle(datosEstiloMoneda);
                Celdap01.setCellValue(rstrabajadorescotizantez.getDouble("p1"));
                Cell Celdap01conteo = filaDatosCuotasYAportaciones.createCell(6);
                Celdap01conteo.setCellStyle(datosEstilo);
                Celdap01conteo.setCellValue(rstrabajadorescotizantez.getInt("p1con"));
                Cell CeldaD01Cadena = filaDatosCuotasYAportaciones.createCell(7);
                CeldaD01Cadena.setCellStyle(datosEstilo);
                CeldaD01Cadena.setCellValue("D_01");
                Cell Celdad01Cadena2 = filaDatosCuotasYAportaciones.createCell(8);
                Celdad01Cadena2.setCellStyle(datosEstilo);
                Celdad01Cadena2.setCellValue("RETENCION ISR");
                Cell Celdad01 = filaDatosCuotasYAportaciones.createCell(9);
                Celdad01.setCellStyle(datosEstiloMoneda);
                Celdad01.setCellValue(rstrabajadorescotizantez.getDouble("d1"));
                Cell Celdad01conteo = filaDatosCuotasYAportaciones.createCell(11);
                Celdad01conteo.setCellStyle(datosEstilo);
                Celdad01conteo.setCellValue(rstrabajadorescotizantez.getInt("d1c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones2 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap01aCadena = filaDatosCuotasYAportaciones2.createCell(0);
                Celdap01aCadena.setCellStyle(datosEstilo);
                Celdap01aCadena.setCellValue("P_01A");
                Cell Celdap01aCadena2 = filaDatosCuotasYAportaciones2.createCell(1);
                Celdap01aCadena2.setCellStyle(datosEstilo);
                Celdap01aCadena2.setCellValue("SUELDO SEMS");
                Cell Celdap01a = filaDatosCuotasYAportaciones2.createCell(2);
                Celdap01a.setCellStyle(datosEstiloMoneda);
                Celdap01a.setCellValue(rstrabajadorescotizantez.getDouble("p1a"));
                Cell Celdap01aconteo = filaDatosCuotasYAportaciones2.createCell(6);
                Celdap01aconteo.setCellStyle(datosEstilo);
                Celdap01aconteo.setCellValue(rstrabajadorescotizantez.getInt("p1ac"));
                Cell Celdad02Cadena = filaDatosCuotasYAportaciones2.createCell(7);
                Celdad02Cadena.setCellStyle(datosEstilo);
                Celdad02Cadena.setCellValue("D_02");
                Cell Celdad02Cadena2 = filaDatosCuotasYAportaciones2.createCell(8);
                Celdad02Cadena2.setCellStyle(datosEstilo);
                Celdad02Cadena2.setCellValue("INASISTENCIAS");
                Cell Celdad02 = filaDatosCuotasYAportaciones2.createCell(9);
                Celdad02.setCellStyle(datosEstiloMoneda);
                Celdad02.setCellValue(rstrabajadorescotizantez.getDouble("d2"));
                Cell Celdad02conteo = filaDatosCuotasYAportaciones2.createCell(11);
                Celdad02conteo.setCellStyle(datosEstilo);
                Celdad02conteo.setCellValue(rstrabajadorescotizantez.getInt("d2c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones3 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap01bCadena = filaDatosCuotasYAportaciones3.createCell(0);
                Celdap01bCadena.setCellStyle(datosEstilo);
                Celdap01bCadena.setCellValue("P_01B");
                Cell Celdap01bCadena2 = filaDatosCuotasYAportaciones3.createCell(1);
                Celdap01bCadena2.setCellStyle(datosEstilo);
                Celdap01bCadena2.setCellValue("SUELDO COBACH BASE");
                Cell Celdap01b = filaDatosCuotasYAportaciones3.createCell(2);
                Celdap01b.setCellStyle(datosEstiloMoneda);
                Celdap01b.setCellValue(rstrabajadorescotizantez.getDouble("p1b"));
                Cell Celdap01bconteo = filaDatosCuotasYAportaciones3.createCell(6);
                Celdap01bconteo.setCellStyle(datosEstilo);
                Celdap01bconteo.setCellValue(rstrabajadorescotizantez.getInt("p1bc"));
                Cell Celdad03Cadena = filaDatosCuotasYAportaciones3.createCell(7);
                Celdad03Cadena.setCellStyle(datosEstilo);
                Celdad03Cadena.setCellValue("D_03");
                Cell Celdad03Cadena2 = filaDatosCuotasYAportaciones3.createCell(8);
                Celdad03Cadena2.setCellStyle(datosEstilo);
                Celdad03Cadena2.setCellValue("SEGURO SALUD TRABAJADORES ACTIVOS ISSSTE");
                Cell Celdad03 = filaDatosCuotasYAportaciones3.createCell(9);
                Celdad03.setCellStyle(datosEstiloMoneda);
                Celdad03.setCellValue(rstrabajadorescotizantez.getDouble("d3"));
                Cell Celdad03conteo = filaDatosCuotasYAportaciones3.createCell(11);
                Celdad03conteo.setCellStyle(datosEstilo);
                Celdad03conteo.setCellValue(rstrabajadorescotizantez.getInt("d3c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones4 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap01cCadena = filaDatosCuotasYAportaciones4.createCell(0);
                Celdap01cCadena.setCellStyle(datosEstilo);
                Celdap01cCadena.setCellValue("P_01C");
                Cell Celdap01cCadena2 = filaDatosCuotasYAportaciones4.createCell(1);
                Celdap01cCadena2.setCellStyle(datosEstilo);
                Celdap01cCadena2.setCellValue("SUELDO COBACH lim");
                Cell Celdap01c = filaDatosCuotasYAportaciones4.createCell(2);
                Celdap01c.setCellStyle(datosEstiloMoneda);
                Celdap01c.setCellValue(rstrabajadorescotizantez.getDouble("p1c"));
                Cell Celdap01cconteo = filaDatosCuotasYAportaciones4.createCell(6);
                Celdap01cconteo.setCellStyle(datosEstilo);
                Celdap01cconteo.setCellValue(rstrabajadorescotizantez.getInt("p1cc"));
                Cell Celdad04Cadena = filaDatosCuotasYAportaciones4.createCell(7);
                Celdad04Cadena.setCellStyle(datosEstilo);
                Celdad04Cadena.setCellValue("D_04");
                Cell Celdad04Cadena2 = filaDatosCuotasYAportaciones4.createCell(8);
                Celdad04Cadena2.setCellStyle(datosEstilo);
                Celdad04Cadena2.setCellValue("CESANTIA EDAD AVANZAJA Y VEJEZ ISSSTE");
                Cell Celdad04 = filaDatosCuotasYAportaciones4.createCell(9);
                Celdad04.setCellStyle(datosEstiloMoneda);
                Celdad04.setCellValue(rstrabajadorescotizantez.getDouble("d4"));
                Cell Celdad04conteo = filaDatosCuotasYAportaciones4.createCell(11);
                Celdad04conteo.setCellStyle(datosEstilo);
                Celdad04conteo.setCellValue(rstrabajadorescotizantez.getInt("d4c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones5 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap01dCadena = filaDatosCuotasYAportaciones5.createCell(0);
                Celdap01dCadena.setCellStyle(datosEstilo);
                Celdap01dCadena.setCellValue("P_01D");
                Cell Celdap01dCadena2 = filaDatosCuotasYAportaciones5.createCell(1);
                Celdap01dCadena2.setCellStyle(datosEstilo);
                Celdap01dCadena2.setCellValue("SUELDO COBACH INT");
                Cell Celdap01d = filaDatosCuotasYAportaciones5.createCell(2);
                Celdap01d.setCellStyle(datosEstiloMoneda);
                Celdap01d.setCellValue(rstrabajadorescotizantez.getDouble("p1d"));
                Cell Celdap01dconteo = filaDatosCuotasYAportaciones5.createCell(6);
                Celdap01dconteo.setCellStyle(datosEstilo);
                Celdap01dconteo.setCellValue(rstrabajadorescotizantez.getInt("p1dc"));
                Cell Celdad05Cadena = filaDatosCuotasYAportaciones5.createCell(7);
                Celdad05Cadena.setCellStyle(datosEstilo);
                Celdad05Cadena.setCellValue("D_05");
                Cell Celdad05Cadena2 = filaDatosCuotasYAportaciones5.createCell(8);
                Celdad05Cadena2.setCellStyle(datosEstilo);
                Celdad05Cadena2.setCellValue("CUOTA SINDICAL SUTSOPEGEM");
                Cell Celdad05 = filaDatosCuotasYAportaciones5.createCell(9);
                Celdad05.setCellStyle(datosEstiloMoneda);
                Celdad05.setCellValue(rstrabajadorescotizantez.getDouble("d5"));
                Cell Celdad05conteo = filaDatosCuotasYAportaciones5.createCell(11);
                Celdad05conteo.setCellStyle(datosEstilo);
                Celdad05conteo.setCellValue(rstrabajadorescotizantez.getInt("d5c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones6 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap02Cadena = filaDatosCuotasYAportaciones6.createCell(0);
                Celdap02Cadena.setCellStyle(datosEstilo);
                Celdap02Cadena.setCellValue("P_02");
                Cell Celdap02Cadena2 = filaDatosCuotasYAportaciones6.createCell(1);
                Celdap02Cadena2.setCellStyle(datosEstilo);
                Celdap02Cadena2.setCellValue("RETROACTIVO");
                Cell Celdap02 = filaDatosCuotasYAportaciones6.createCell(2);
                Celdap02.setCellStyle(datosEstiloMoneda);
                Celdap02.setCellValue(rstrabajadorescotizantez.getDouble("p2"));
                Cell Celdap02conteo = filaDatosCuotasYAportaciones6.createCell(6);
                Celdap02conteo.setCellStyle(datosEstilo);
                Celdap02conteo.setCellValue(rstrabajadorescotizantez.getInt("p2c"));
                Cell Celdad06Cadena = filaDatosCuotasYAportaciones6.createCell(7);
                Celdad06Cadena.setCellStyle(datosEstilo);
                Celdad06Cadena.setCellValue("D_06");
                Cell Celdad06Cadena2 = filaDatosCuotasYAportaciones6.createCell(8);
                Celdad06Cadena2.setCellStyle(datosEstilo);
                Celdad06Cadena2.setCellValue("SEGURO DE DAÑOS  FOVISSTE");
                Cell Celdad06 = filaDatosCuotasYAportaciones6.createCell(9);
                Celdad06.setCellStyle(datosEstiloMoneda);
                Celdad06.setCellValue(rstrabajadorescotizantez.getDouble("d6"));
                Cell Celdad06conteo = filaDatosCuotasYAportaciones6.createCell(11);
                Celdad06conteo.setCellStyle(datosEstilo);
                Celdad06conteo.setCellValue(rstrabajadorescotizantez.getInt("d6c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones7 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap03Cadena = filaDatosCuotasYAportaciones7.createCell(0);
                Celdap03Cadena.setCellStyle(datosEstilo);
                Celdap03Cadena.setCellValue("P_03");
                Cell Celdap03Cadena2 = filaDatosCuotasYAportaciones7.createCell(1);
                Celdap03Cadena2.setCellStyle(datosEstilo);
                Celdap03Cadena2.setCellValue("PRIMA DE ANTIGUEDAD");
                Cell Celdap03 = filaDatosCuotasYAportaciones7.createCell(2);
                Celdap03.setCellStyle(datosEstiloMoneda);
                Celdap03.setCellValue(rstrabajadorescotizantez.getDouble("p3"));
                Cell Celdap03conteo = filaDatosCuotasYAportaciones7.createCell(6);
                Celdap03conteo.setCellStyle(datosEstilo);
                Celdap03conteo.setCellValue(rstrabajadorescotizantez.getInt("p3c"));
                Cell Celdad07Cadena = filaDatosCuotasYAportaciones7.createCell(7);
                Celdad07Cadena.setCellStyle(datosEstilo);
                Celdad07Cadena.setCellValue("D_07");
                Cell Celdad07Cadena2 = filaDatosCuotasYAportaciones7.createCell(8);
                Celdad07Cadena2.setCellStyle(datosEstilo);
                Celdad07Cadena2.setCellValue("PRESTAMOS CORTO PLAZO ISSSTE");
                Cell Celdad07 = filaDatosCuotasYAportaciones7.createCell(9);
                Celdad07.setCellStyle(datosEstiloMoneda);
                Celdad07.setCellValue(rstrabajadorescotizantez.getDouble("d7"));
                Cell Celdad07conteo = filaDatosCuotasYAportaciones7.createCell(11);
                Celdad07conteo.setCellStyle(datosEstilo);
                Celdad07conteo.setCellValue(rstrabajadorescotizantez.getInt("d7c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones8 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap04Cadena = filaDatosCuotasYAportaciones8.createCell(0);
                Celdap04Cadena.setCellStyle(datosEstilo);
                Celdap04Cadena.setCellValue("P_04");
                Cell Celdap04Cadena2 = filaDatosCuotasYAportaciones8.createCell(1);
                Celdap04Cadena2.setCellStyle(datosEstilo);
                Celdap04Cadena2.setCellValue("DESPENSA ADMINISTRATIVA");
                Cell Celdap04 = filaDatosCuotasYAportaciones8.createCell(2);
                Celdap04.setCellStyle(datosEstiloMoneda);
                Celdap04.setCellValue(rstrabajadorescotizantez.getDouble("p4"));
                Cell Celdap04conteo = filaDatosCuotasYAportaciones8.createCell(6);
                Celdap04conteo.setCellStyle(datosEstilo);
                Celdap04conteo.setCellValue(rstrabajadorescotizantez.getInt("p4c"));
                Cell Celdad08Cadena = filaDatosCuotasYAportaciones8.createCell(7);
                Celdad08Cadena.setCellStyle(datosEstilo);
                Celdad08Cadena.setCellValue("D_08");
                Cell Celdad08Cadena2 = filaDatosCuotasYAportaciones8.createCell(8);
                Celdad08Cadena2.setCellStyle(datosEstilo);
                Celdad08Cadena2.setCellValue("PRESTAMOS CAJA DE AHORRO SUSPEG");
                Cell Celdad08 = filaDatosCuotasYAportaciones8.createCell(9);
                Celdad08.setCellStyle(datosEstiloMoneda);
                Celdad08.setCellValue(rstrabajadorescotizantez.getDouble("d8"));
                Cell Celdad08conteo = filaDatosCuotasYAportaciones8.createCell(11);
                Celdad08conteo.setCellStyle(datosEstilo);
                Celdad08conteo.setCellValue(rstrabajadorescotizantez.getInt("d8c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones9 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap05Cadena = filaDatosCuotasYAportaciones9.createCell(0);
                Celdap05Cadena.setCellStyle(datosEstilo);
                Celdap05Cadena.setCellValue("P_05");
                Cell Celdap05Cadena2 = filaDatosCuotasYAportaciones9.createCell(1);
                Celdap05Cadena2.setCellStyle(datosEstilo);
                Celdap05Cadena2.setCellValue("DESPENSA DOCENTE 3-19 HORAS");
                Cell Celdap05 = filaDatosCuotasYAportaciones9.createCell(2);
                Celdap05.setCellStyle(datosEstiloMoneda);
                Celdap05.setCellValue(rstrabajadorescotizantez.getDouble("p5"));
                Cell Celdap05conteo = filaDatosCuotasYAportaciones9.createCell(6);
                Celdap05conteo.setCellStyle(datosEstilo);
                Celdap05conteo.setCellValue(rstrabajadorescotizantez.getInt("p5c"));
                Cell Celdad09Cadena = filaDatosCuotasYAportaciones9.createCell(7);
                Celdad09Cadena.setCellStyle(datosEstilo);
                Celdad09Cadena.setCellValue("D_09");
                Cell Celdad09Cadena2 = filaDatosCuotasYAportaciones9.createCell(8);
                Celdad09Cadena2.setCellStyle(datosEstilo);
                Celdad09Cadena2.setCellValue("PRESTAMOSHIPOTECARIO FOVISSTE");
                Cell Celdad09 = filaDatosCuotasYAportaciones9.createCell(9);
                Celdad09.setCellStyle(datosEstiloMoneda);
                Celdad09.setCellValue(rstrabajadorescotizantez.getDouble("d9"));
                Cell Celdad09conteo = filaDatosCuotasYAportaciones9.createCell(11);
                Celdad09conteo.setCellStyle(datosEstilo);
                Celdad09conteo.setCellValue(rstrabajadorescotizantez.getInt("d9c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones10 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap06Cadena = filaDatosCuotasYAportaciones10.createCell(0);
                Celdap06Cadena.setCellStyle(datosEstilo);
                Celdap06Cadena.setCellValue("P_06");
                Cell Celdap06Cadena2 = filaDatosCuotasYAportaciones10.createCell(1);
                Celdap06Cadena2.setCellStyle(datosEstilo);
                Celdap06Cadena2.setCellValue("MATERIAL DIDACTICO");
                Cell Celdap06 = filaDatosCuotasYAportaciones10.createCell(2);
                Celdap06.setCellStyle(datosEstiloMoneda);
                Celdap06.setCellValue(rstrabajadorescotizantez.getDouble("p6"));
                Cell Celdap06conteo = filaDatosCuotasYAportaciones10.createCell(6);
                Celdap06conteo.setCellStyle(datosEstilo);
                Celdap06conteo.setCellValue(rstrabajadorescotizantez.getInt("p6c"));
                Cell Celdad10Cadena = filaDatosCuotasYAportaciones10.createCell(7);
                Celdad10Cadena.setCellStyle(datosEstilo);
                Celdad10Cadena.setCellValue("D_10");
                Cell Celdad10Cadena2 = filaDatosCuotasYAportaciones10.createCell(8);
                Celdad10Cadena2.setCellStyle(datosEstilo);
                Celdad10Cadena2.setCellValue("PENSION ALIMENTICIA");
                Cell Celdad10 = filaDatosCuotasYAportaciones10.createCell(9);
                Celdad10.setCellStyle(datosEstiloMoneda);
                Celdad10.setCellValue(rstrabajadorescotizantez.getDouble("d10"));
                Cell Celdad10conteo = filaDatosCuotasYAportaciones10.createCell(11);
                Celdad10conteo.setCellStyle(datosEstilo);
                Celdad10conteo.setCellValue(rstrabajadorescotizantez.getInt("d10c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones11 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap07Cadena = filaDatosCuotasYAportaciones11.createCell(0);
                Celdap07Cadena.setCellStyle(datosEstilo);
                Celdap07Cadena.setCellValue("P_07");
                Cell Celdap07Cadena2 = filaDatosCuotasYAportaciones11.createCell(1);
                Celdap07Cadena2.setCellStyle(datosEstilo);
                Celdap07Cadena2.setCellValue("AGUINALDO");
                Cell Celdap07 = filaDatosCuotasYAportaciones11.createCell(2);
                Celdap07.setCellStyle(datosEstiloMoneda);
                Celdap07.setCellValue(rstrabajadorescotizantez.getDouble("p7"));
                Cell Celdap07conteo = filaDatosCuotasYAportaciones11.createCell(6);
                Celdap07conteo.setCellStyle(datosEstilo);
                Celdap07conteo.setCellValue(rstrabajadorescotizantez.getInt("p7c"));
                Cell Celdad11Cadena = filaDatosCuotasYAportaciones11.createCell(7);
                Celdad11Cadena.setCellStyle(datosEstilo);
                Celdad11Cadena.setCellValue("D_11");
                Cell Celdad11Cadena2 = filaDatosCuotasYAportaciones11.createCell(8);
                Celdad11Cadena2.setCellStyle(datosEstilo);
                Celdad11Cadena2.setCellValue("CUOTA SINDICAL SUTCOBAC");
                Cell Celdad11 = filaDatosCuotasYAportaciones11.createCell(9);
                Celdad11.setCellStyle(datosEstiloMoneda);
                Celdad11.setCellValue(rstrabajadorescotizantez.getDouble("d11"));
                Cell Celdad11conteo = filaDatosCuotasYAportaciones11.createCell(11);
                Celdad11conteo.setCellStyle(datosEstilo);
                Celdad11conteo.setCellValue(rstrabajadorescotizantez.getInt("d11c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones12 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap08Cadena = filaDatosCuotasYAportaciones12.createCell(0);
                Celdap08Cadena.setCellStyle(datosEstilo);
                Celdap08Cadena.setCellValue("P_08");
                Cell Celdap08Cadena2 = filaDatosCuotasYAportaciones12.createCell(1);
                Celdap08Cadena2.setCellStyle(datosEstilo);
                Celdap08Cadena2.setCellValue("PRIMA VACACIONAL");
                Cell Celdap08 = filaDatosCuotasYAportaciones12.createCell(2);
                Celdap08.setCellStyle(datosEstiloMoneda);
                Celdap08.setCellValue(rstrabajadorescotizantez.getDouble("p8"));
                Cell Celdap08conteo = filaDatosCuotasYAportaciones12.createCell(6);
                Celdap08conteo.setCellStyle(datosEstilo);
                Celdap08conteo.setCellValue(rstrabajadorescotizantez.getInt("p8c"));
                Cell Celdad12Cadena = filaDatosCuotasYAportaciones12.createCell(7);
                Celdad12Cadena.setCellStyle(datosEstilo);
                Celdad12Cadena.setCellValue("D_12");
                Cell Celdad12Cadena2 = filaDatosCuotasYAportaciones12.createCell(8);
                Celdad12Cadena2.setCellStyle(datosEstilo);
                Celdad12Cadena2.setCellValue("CAJA DE AHORRO SINDICAL");
                Cell Celdad12 = filaDatosCuotasYAportaciones12.createCell(9);
                Celdad12.setCellStyle(datosEstiloMoneda);
                Celdad12.setCellValue(rstrabajadorescotizantez.getDouble("d12"));
                Cell Celdad12conteo = filaDatosCuotasYAportaciones12.createCell(11);
                Celdad12conteo.setCellStyle(datosEstilo);
                Celdad12conteo.setCellValue(rstrabajadorescotizantez.getInt("d12c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones13 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap09Cadena = filaDatosCuotasYAportaciones13.createCell(0);
                Celdap09Cadena.setCellStyle(datosEstilo);
                Celdap09Cadena.setCellValue("P_09");
                Cell Celdap09Cadena2 = filaDatosCuotasYAportaciones13.createCell(1);
                Celdap09Cadena2.setCellStyle(datosEstilo);
                Celdap09Cadena2.setCellValue("DEVOLUCION DESCUENTO INDEBIDO");
                Cell Celdap09 = filaDatosCuotasYAportaciones13.createCell(2);
                Celdap09.setCellStyle(datosEstiloMoneda);
                Celdap09.setCellValue(rstrabajadorescotizantez.getDouble("p9"));
                Cell Celdap09conteo = filaDatosCuotasYAportaciones13.createCell(6);
                Celdap09conteo.setCellStyle(datosEstilo);
                Celdap09conteo.setCellValue(rstrabajadorescotizantez.getInt("p9c"));
                Cell Celdad13Cadena = filaDatosCuotasYAportaciones13.createCell(7);
                Celdad13Cadena.setCellStyle(datosEstilo);
                Celdad13Cadena.setCellValue("D_13");
                Cell Celdad13Cadena2 = filaDatosCuotasYAportaciones13.createCell(8);
                Celdad13Cadena2.setCellStyle(datosEstilo);
                Celdad13Cadena2.setCellValue("DESCUENTO PAGO INDEBIDO");
                Cell Celdad13 = filaDatosCuotasYAportaciones13.createCell(9);
                Celdad13.setCellStyle(datosEstiloMoneda);
                Celdad13.setCellValue(rstrabajadorescotizantez.getDouble("d13"));
                Cell Celdad13conteo = filaDatosCuotasYAportaciones13.createCell(11);
                Celdad13conteo.setCellStyle(datosEstilo);
                Celdad13conteo.setCellValue(rstrabajadorescotizantez.getInt("d13c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones14 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap10Cadena = filaDatosCuotasYAportaciones14.createCell(0);
                Celdap10Cadena.setCellStyle(datosEstilo);
                Celdap10Cadena.setCellValue("P_10");
                Cell Celdap10Cadena2 = filaDatosCuotasYAportaciones14.createCell(1);
                Celdap10Cadena2.setCellStyle(datosEstilo);
                Celdap10Cadena2.setCellValue("DEVOLUCION DE SUELDOS");
                Cell Celdap10 = filaDatosCuotasYAportaciones14.createCell(2);
                Celdap10.setCellStyle(datosEstiloMoneda);
                Celdap10.setCellValue(rstrabajadorescotizantez.getDouble("p10"));
                Cell Celdap10conteo = filaDatosCuotasYAportaciones14.createCell(6);
                Celdap10conteo.setCellStyle(datosEstilo);
                Celdap10conteo.setCellValue(rstrabajadorescotizantez.getInt("p10c"));
                Cell Celdad14Cadena = filaDatosCuotasYAportaciones14.createCell(7);
                Celdad14Cadena.setCellStyle(datosEstilo);
                Celdad14Cadena.setCellValue("D_14");
                Cell Celdad14Cadena2 = filaDatosCuotasYAportaciones14.createCell(8);
                Celdad14Cadena2.setCellStyle(datosEstilo);
                Celdad14Cadena2.setCellValue("PRESTAMOS CAJA DE AHORRO SUTCOBACH");
                Cell Celdad14 = filaDatosCuotasYAportaciones14.createCell(9);
                Celdad14.setCellStyle(datosEstiloMoneda);
                Celdad14.setCellValue(rstrabajadorescotizantez.getDouble("d14"));
                Cell Celdad14conteo = filaDatosCuotasYAportaciones14.createCell(11);
                Celdad14conteo.setCellStyle(datosEstilo);
                Celdad14conteo.setCellValue(rstrabajadorescotizantez.getInt("d14c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones15 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap11Cadena = filaDatosCuotasYAportaciones15.createCell(0);
                Celdap11Cadena.setCellStyle(datosEstilo);
                Celdap11Cadena.setCellValue("P_11");
                Cell Celdap11Cadena2 = filaDatosCuotasYAportaciones15.createCell(1);
                Celdap11Cadena2.setCellStyle(datosEstilo);
                Celdap11Cadena2.setCellValue("CANASTILLA DE MATERNIDAD");
                Cell Celdap11 = filaDatosCuotasYAportaciones15.createCell(2);
                Celdap11.setCellStyle(datosEstiloMoneda);
                Celdap11.setCellValue(rstrabajadorescotizantez.getDouble("p11"));
                Cell Celdap11conteo = filaDatosCuotasYAportaciones15.createCell(6);
                Celdap11conteo.setCellStyle(datosEstilo);
                Celdap11conteo.setCellValue(rstrabajadorescotizantez.getInt("p11c"));
                Cell Celdad15Cadena = filaDatosCuotasYAportaciones15.createCell(7);
                Celdad15Cadena.setCellStyle(datosEstilo);
                Celdad15Cadena.setCellValue("D_15");
                Cell Celdad15Cadena2 = filaDatosCuotasYAportaciones15.createCell(8);
                Celdad15Cadena2.setCellStyle(datosEstilo);
                Celdad15Cadena2.setCellValue("AYUDA MUTUA");
                Cell Celdad15 = filaDatosCuotasYAportaciones15.createCell(9);
                Celdad15.setCellStyle(datosEstiloMoneda);
                Celdad15.setCellValue(rstrabajadorescotizantez.getDouble("d15"));
                Cell Celdad15conteo = filaDatosCuotasYAportaciones15.createCell(11);
                Celdad15conteo.setCellStyle(datosEstilo);
                Celdad15conteo.setCellValue(rstrabajadorescotizantez.getInt("d15c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones16 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap12Cadena = filaDatosCuotasYAportaciones16.createCell(0);
                Celdap12Cadena.setCellStyle(datosEstilo);
                Celdap12Cadena.setCellValue("P_12");
                Cell Celdap12Cadena2 = filaDatosCuotasYAportaciones16.createCell(1);
                Celdap12Cadena2.setCellStyle(datosEstilo);
                Celdap12Cadena2.setCellValue("GUARDERIA ADMINISTRATIVA");
                Cell Celdap12 = filaDatosCuotasYAportaciones16.createCell(2);
                Celdap12.setCellStyle(datosEstiloMoneda);
                Celdap12.setCellValue(rstrabajadorescotizantez.getDouble("p12"));
                Cell Celdap12conteo = filaDatosCuotasYAportaciones16.createCell(6);
                Celdap12conteo.setCellStyle(datosEstilo);
                Celdap12conteo.setCellValue(rstrabajadorescotizantez.getInt("p12c"));
                Cell Celdad16Cadena = filaDatosCuotasYAportaciones16.createCell(7);
                Celdad16Cadena.setCellStyle(datosEstilo);
                Celdad16Cadena.setCellValue("D_16");
                Cell Celdad16Cadena2 = filaDatosCuotasYAportaciones16.createCell(8);
                Celdad16Cadena2.setCellStyle(datosEstilo);
                Celdad16Cadena2.setCellValue("OTRAS DEDUCCIONES");
                Cell Celdad16 = filaDatosCuotasYAportaciones16.createCell(9);
                Celdad16.setCellStyle(datosEstiloMoneda);
                Celdad16.setCellValue(rstrabajadorescotizantez.getDouble("d16"));
                Cell Celdad16conteo = filaDatosCuotasYAportaciones16.createCell(11);
                Celdad16conteo.setCellStyle(datosEstilo);
                Celdad16conteo.setCellValue(rstrabajadorescotizantez.getInt("d16c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones17 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap13Cadena = filaDatosCuotasYAportaciones17.createCell(0);
                Celdap13Cadena.setCellStyle(datosEstilo);
                Celdap13Cadena.setCellValue("P_13");
                Cell Celdap13Cadena2 = filaDatosCuotasYAportaciones17.createCell(1);
                Celdap13Cadena2.setCellStyle(datosEstilo);
                Celdap13Cadena2.setCellValue("ESTIMULO DE PUNT y ASIST.");
                Cell Celdap13 = filaDatosCuotasYAportaciones17.createCell(2);
                Celdap13.setCellStyle(datosEstiloMoneda);
                Celdap13.setCellValue(rstrabajadorescotizantez.getDouble("p13"));
                Cell Celdap13conteo = filaDatosCuotasYAportaciones17.createCell(6);
                Celdap13conteo.setCellStyle(datosEstilo);
                Celdap13conteo.setCellValue(rstrabajadorescotizantez.getInt("p13c"));
                Cell Celdad17Cadena = filaDatosCuotasYAportaciones17.createCell(7);
                Celdad17Cadena.setCellStyle(datosEstilo);
                Celdad17Cadena.setCellValue("D_17");
                Cell Celdad17Cadena2 = filaDatosCuotasYAportaciones17.createCell(8);
                Celdad17Cadena2.setCellStyle(datosEstilo);
                Celdad17Cadena2.setCellValue("ANTICIPO DE SUELDO");
                Cell Celdad17 = filaDatosCuotasYAportaciones17.createCell(9);
                Celdad17.setCellStyle(datosEstiloMoneda);
                Celdad17.setCellValue(rstrabajadorescotizantez.getDouble("d17"));
                Cell Celdad17conteo = filaDatosCuotasYAportaciones17.createCell(11);
                Celdad17conteo.setCellStyle(datosEstilo);
                Celdad17conteo.setCellValue(rstrabajadorescotizantez.getInt("d17c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones18 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap14Cadena = filaDatosCuotasYAportaciones18.createCell(0);
                Celdap14Cadena.setCellStyle(datosEstilo);
                Celdap14Cadena.setCellValue("P_14");
                Cell Celdap14Cadena2 = filaDatosCuotasYAportaciones18.createCell(1);
                Celdap14Cadena2.setCellStyle(datosEstilo);
                Celdap14Cadena2.setCellValue("SUBSIDIO AL EMPLEO");
                Cell Celdap14 = filaDatosCuotasYAportaciones18.createCell(2);
                Celdap14.setCellStyle(datosEstiloMoneda);
                Celdap14.setCellValue(rstrabajadorescotizantez.getDouble("p14"));
                Cell Celdap14conteo = filaDatosCuotasYAportaciones18.createCell(6);
                Celdap14conteo.setCellStyle(datosEstilo);
                Celdap14conteo.setCellValue(rstrabajadorescotizantez.getInt("p14c"));
                Cell Celdad18Cadena = filaDatosCuotasYAportaciones18.createCell(7);
                Celdad18Cadena.setCellStyle(datosEstilo);
                Celdad18Cadena.setCellValue("D_18");
                Cell Celdad18Cadena2 = filaDatosCuotasYAportaciones18.createCell(8);
                Celdad18Cadena2.setCellStyle(datosEstilo);
                Celdad18Cadena2.setCellValue("AYUDA MUTUA SECCION XXXI");
                Cell Celdad18 = filaDatosCuotasYAportaciones18.createCell(9);
                Celdad18.setCellStyle(datosEstiloMoneda);
                Celdad18.setCellValue(rstrabajadorescotizantez.getDouble("d18"));
                Cell Celdad18conteo = filaDatosCuotasYAportaciones18.createCell(11);
                Celdad18conteo.setCellStyle(datosEstilo);
                Celdad18conteo.setCellValue(rstrabajadorescotizantez.getInt("d18c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones19 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap15Cadena = filaDatosCuotasYAportaciones19.createCell(0);
                Celdap15Cadena.setCellStyle(datosEstilo);
                Celdap15Cadena.setCellValue("P_15");
                Cell Celdap15Cadena2 = filaDatosCuotasYAportaciones19.createCell(1);
                Celdap15Cadena2.setCellStyle(datosEstilo);
                Celdap15Cadena2.setCellValue("PAGO DE LENTES");
                Cell Celdap15 = filaDatosCuotasYAportaciones19.createCell(2);
                Celdap15.setCellStyle(datosEstiloMoneda);
                Celdap15.setCellValue(rstrabajadorescotizantez.getDouble("p15"));
                Cell Celdap15conteo = filaDatosCuotasYAportaciones19.createCell(6);
                Celdap15conteo.setCellStyle(datosEstilo);
                Celdap15conteo.setCellValue(rstrabajadorescotizantez.getInt("p15c"));
                Cell Celdad19Cadena = filaDatosCuotasYAportaciones19.createCell(7);
                Celdad19Cadena.setCellStyle(datosEstilo);
                Celdad19Cadena.setCellValue("D_19");
                Cell Celdad19Cadena2 = filaDatosCuotasYAportaciones19.createCell(8);
                Celdad19Cadena2.setCellStyle(datosEstilo);
                Celdad19Cadena2.setCellValue("CUOTA SINDICAL SITCOBACH");
                Cell Celdad19 = filaDatosCuotasYAportaciones19.createCell(9);
                Celdad19.setCellStyle(datosEstiloMoneda);
                Celdad19.setCellValue(rstrabajadorescotizantez.getDouble("d19"));
                Cell Celdad19conteo = filaDatosCuotasYAportaciones19.createCell(11);
                Celdad19conteo.setCellStyle(datosEstilo);
                Celdad19conteo.setCellValue(rstrabajadorescotizantez.getInt("d19c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones20 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap16Cadena = filaDatosCuotasYAportaciones20.createCell(0);
                Celdap16Cadena.setCellStyle(datosEstilo);
                Celdap16Cadena.setCellValue("P_16");
                Cell Celdap16Cadena2 = filaDatosCuotasYAportaciones20.createCell(1);
                Celdap16Cadena2.setCellStyle(datosEstilo);
                Celdap16Cadena2.setCellValue("ESTIMULO PRODUCTIVIDAD ADMINISTRATIVA");
                Cell Celdap16 = filaDatosCuotasYAportaciones20.createCell(2);
                Celdap16.setCellStyle(datosEstiloMoneda);
                Celdap16.setCellValue(rstrabajadorescotizantez.getDouble("p16"));
                Cell Celdap16conteo = filaDatosCuotasYAportaciones20.createCell(6);
                Celdap16conteo.setCellStyle(datosEstilo);
                Celdap16conteo.setCellValue(rstrabajadorescotizantez.getInt("p16c"));
                Cell Celdad20Cadena = filaDatosCuotasYAportaciones20.createCell(7);
                Celdad20Cadena.setCellStyle(datosEstilo);
                Celdad20Cadena.setCellValue("D_20");
                Cell Celdad20Cadena2 = filaDatosCuotasYAportaciones20.createCell(8);
                Celdad20Cadena2.setCellStyle(datosEstilo);
                Celdad20Cadena2.setCellValue("SEGUROS NEW YORK LIFE");
                Cell Celdad20 = filaDatosCuotasYAportaciones20.createCell(9);
                Celdad20.setCellStyle(datosEstiloMoneda);
                Celdad20.setCellValue(rstrabajadorescotizantez.getDouble("d20"));
                Cell Celdad20conteo = filaDatosCuotasYAportaciones20.createCell(11);
                Celdad20conteo.setCellStyle(datosEstilo);
                Celdad20conteo.setCellValue(rstrabajadorescotizantez.getInt("d20c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones21 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap17Cadena = filaDatosCuotasYAportaciones21.createCell(0);
                Celdap17Cadena.setCellStyle(datosEstilo);
                Celdap17Cadena.setCellValue("P_17");
                Cell Celdap17Cadena2 = filaDatosCuotasYAportaciones21.createCell(1);
                Celdap17Cadena2.setCellStyle(datosEstilo);
                Celdap17Cadena2.setCellValue("OTRAS PERCEPCIONES");
                Cell Celdap17 = filaDatosCuotasYAportaciones21.createCell(2);
                Celdap17.setCellStyle(datosEstiloMoneda);
                Celdap17.setCellValue(rstrabajadorescotizantez.getDouble("p17"));
                Cell Celdap17conteo = filaDatosCuotasYAportaciones21.createCell(6);
                Celdap17conteo.setCellStyle(datosEstilo);
                Celdap17conteo.setCellValue(rstrabajadorescotizantez.getInt("p17c"));
                Cell Celdad21Cadena = filaDatosCuotasYAportaciones21.createCell(7);
                Celdad21Cadena.setCellStyle(datosEstilo);
                Celdad21Cadena.setCellValue("D_21");
                Cell Celdad21Cadena2 = filaDatosCuotasYAportaciones21.createCell(8);
                Celdad21Cadena2.setCellStyle(datosEstilo);
                Celdad21Cadena2.setCellValue("SEGUROS SALUD PENSIONADO ISSSTE");
                Cell Celdad21 = filaDatosCuotasYAportaciones21.createCell(9);
                Celdad21.setCellStyle(datosEstiloMoneda);
                Celdad21.setCellValue(rstrabajadorescotizantez.getDouble("d21"));
                Cell Celdad21conteo = filaDatosCuotasYAportaciones21.createCell(11);
                Celdad21conteo.setCellStyle(datosEstilo);
                Celdad21conteo.setCellValue(rstrabajadorescotizantez.getInt("d21c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones22 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap18Cadena = filaDatosCuotasYAportaciones22.createCell(0);
                Celdap18Cadena.setCellStyle(datosEstilo);
                Celdap18Cadena.setCellValue("P_18");
                Cell Celdap18Cadena2 = filaDatosCuotasYAportaciones22.createCell(1);
                Celdap18Cadena2.setCellStyle(datosEstilo);
                Celdap18Cadena2.setCellValue("DIFERENCIA DE SUELDO GRAVABLE");
                Cell Celdap18 = filaDatosCuotasYAportaciones22.createCell(2);
                Celdap18.setCellStyle(datosEstiloMoneda);
                Celdap18.setCellValue(rstrabajadorescotizantez.getDouble("p18"));
                Cell Celdap18conteo = filaDatosCuotasYAportaciones22.createCell(6);
                Celdap18conteo.setCellStyle(datosEstilo);
                Celdap18conteo.setCellValue(rstrabajadorescotizantez.getInt("p18c"));
                Cell Celdad22Cadena = filaDatosCuotasYAportaciones22.createCell(7);
                Celdad22Cadena.setCellStyle(datosEstilo);
                Celdad22Cadena.setCellValue("D_22");
                Cell Celdad22Cadena2 = filaDatosCuotasYAportaciones22.createCell(8);
                Celdad22Cadena2.setCellStyle(datosEstilo);
                Celdad22Cadena2.setCellValue("SEGUROS INVALIDEZ Y VIDA ISSSTE");
                Cell Celdad22 = filaDatosCuotasYAportaciones22.createCell(9);
                Celdad22.setCellStyle(datosEstiloMoneda);
                Celdad22.setCellValue(rstrabajadorescotizantez.getDouble("d22"));
                Cell Celdad22conteo = filaDatosCuotasYAportaciones22.createCell(11);
                Celdad22conteo.setCellStyle(datosEstilo);
                Celdad22conteo.setCellValue(rstrabajadorescotizantez.getInt("d22c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones23 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap19Cadena = filaDatosCuotasYAportaciones23.createCell(0);
                Celdap19Cadena.setCellStyle(datosEstilo);
                Celdap19Cadena.setCellValue("P_19");
                Cell Celdap19Cadena2 = filaDatosCuotasYAportaciones23.createCell(1);
                Celdap19Cadena2.setCellStyle(datosEstilo);
                Celdap19Cadena2.setCellValue("ASIGNACION DOCENTE");
                Cell Celdap19 = filaDatosCuotasYAportaciones23.createCell(2);
                Celdap19.setCellStyle(datosEstiloMoneda);
                Celdap19.setCellValue(rstrabajadorescotizantez.getDouble("p19"));
                Cell Celdap19conteo = filaDatosCuotasYAportaciones23.createCell(6);
                Celdap19conteo.setCellStyle(datosEstilo);
                Celdap19conteo.setCellValue(rstrabajadorescotizantez.getInt("p19c"));
                Cell Celdad23Cadena = filaDatosCuotasYAportaciones23.createCell(7);
                Celdad23Cadena.setCellStyle(datosEstilo);
                Celdad23Cadena.setCellValue("D_23");
                Cell Celdad23Cadena2 = filaDatosCuotasYAportaciones23.createCell(8);
                Celdad23Cadena2.setCellStyle(datosEstilo);
                Celdad23Cadena2.setCellValue("SERV. SOC. CULT. ISSSTE ");
                Cell Celdad23 = filaDatosCuotasYAportaciones23.createCell(9);
                Celdad23.setCellStyle(datosEstiloMoneda);
                Celdad23.setCellValue(rstrabajadorescotizantez.getDouble("d23"));
                Cell Celdad23conteo = filaDatosCuotasYAportaciones23.createCell(11);
                Celdad23conteo.setCellStyle(datosEstilo);
                Celdad23conteo.setCellValue(rstrabajadorescotizantez.getInt("d23c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones24 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap20Cadena = filaDatosCuotasYAportaciones24.createCell(0);
                Celdap20Cadena.setCellStyle(datosEstilo);
                Celdap20Cadena.setCellValue("P_20");
                Cell Celdap20Cadena2 = filaDatosCuotasYAportaciones24.createCell(1);
                Celdap20Cadena2.setCellStyle(datosEstilo);
                Celdap20Cadena2.setCellValue("DIAS ECONOMICOS NO DISF.");
                Cell Celdap20 = filaDatosCuotasYAportaciones24.createCell(2);
                Celdap20.setCellStyle(datosEstiloMoneda);
                Celdap20.setCellValue(rstrabajadorescotizantez.getDouble("p20"));
                Cell Celdap20conteo = filaDatosCuotasYAportaciones24.createCell(6);
                Celdap20conteo.setCellStyle(datosEstilo);
                Celdap20conteo.setCellValue(rstrabajadorescotizantez.getInt("p20c"));
                Cell Celdad24Cadena = filaDatosCuotasYAportaciones24.createCell(7);
                Celdad24Cadena.setCellStyle(datosEstilo);
                Celdad24Cadena.setCellValue("D_24");
                Cell Celdad24Cadena2 = filaDatosCuotasYAportaciones24.createCell(8);
                Celdad24Cadena2.setCellStyle(datosEstilo);
                Celdad24Cadena2.setCellValue("AHORRO SOLIDARIO ISSSTE");
                Cell Celdad24 = filaDatosCuotasYAportaciones24.createCell(9);
                Celdad24.setCellStyle(datosEstiloMoneda);
                Celdad24.setCellValue(rstrabajadorescotizantez.getDouble("d24"));
                Cell Celdad24conteo = filaDatosCuotasYAportaciones24.createCell(11);
                Celdad24conteo.setCellStyle(datosEstilo);
                Celdad24conteo.setCellValue(rstrabajadorescotizantez.getInt("d24c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones25 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap21Cadena = filaDatosCuotasYAportaciones25.createCell(0);
                Celdap21Cadena.setCellStyle(datosEstilo);
                Celdap21Cadena.setCellValue("P_21");
                Cell Celdap21Cadena2 = filaDatosCuotasYAportaciones25.createCell(1);
                Celdap21Cadena2.setCellStyle(datosEstilo);
                Celdap21Cadena2.setCellValue("GUARDERIA DOCENTE 3-40 HRS.");
                Cell Celdap21 = filaDatosCuotasYAportaciones25.createCell(2);
                Celdap21.setCellStyle(datosEstiloMoneda);
                Celdap21.setCellValue(rstrabajadorescotizantez.getDouble("p21"));
                Cell Celdap21conteo = filaDatosCuotasYAportaciones25.createCell(6);
                Celdap21conteo.setCellStyle(datosEstilo);
                Celdap21conteo.setCellValue(rstrabajadorescotizantez.getInt("p21c"));
                Cell Celdad25Cadena = filaDatosCuotasYAportaciones25.createCell(7);
                Celdad25Cadena.setCellStyle(datosEstilo);
                Celdad25Cadena.setCellValue("D_25");
                Cell Celdad25Cadena2 = filaDatosCuotasYAportaciones25.createCell(8);
                Celdad25Cadena2.setCellStyle(datosEstilo);
                Celdad25Cadena2.setCellValue("FONDO DE INVERSION DE AHORRO PARA EL RETIRO");
                Cell Celdad25 = filaDatosCuotasYAportaciones25.createCell(9);
                Celdad25.setCellStyle(datosEstiloMoneda);
                Celdad25.setCellValue(rstrabajadorescotizantez.getDouble("d25"));
                Cell Celdad25conteo = filaDatosCuotasYAportaciones25.createCell(11);
                Celdad25conteo.setCellStyle(datosEstilo);
                Celdad25conteo.setCellValue(rstrabajadorescotizantez.getInt("d25c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones26 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap22Cadena = filaDatosCuotasYAportaciones26.createCell(0);
                Celdap22Cadena.setCellStyle(datosEstilo);
                Celdap22Cadena.setCellValue("P_22");
                Cell Celdap22Cadena2 = filaDatosCuotasYAportaciones26.createCell(1);
                Celdap22Cadena2.setCellStyle(datosEstilo);
                Celdap22Cadena2.setCellValue("DESPENSA DOCENTE 20-40 HRS.");
                Cell Celdap22 = filaDatosCuotasYAportaciones26.createCell(2);
                Celdap22.setCellStyle(datosEstiloMoneda);
                Celdap22.setCellValue(rstrabajadorescotizantez.getDouble("p22"));
                Cell Celdap22conteo = filaDatosCuotasYAportaciones26.createCell(6);
                Celdap22conteo.setCellStyle(datosEstilo);
                Celdap22conteo.setCellValue(rstrabajadorescotizantez.getInt("p22c"));
                Cell Celdad26Cadena = filaDatosCuotasYAportaciones26.createCell(7);
                Celdad26Cadena.setCellStyle(datosEstilo);
                Celdad26Cadena.setCellValue("D_26");
                Cell Celdad26Cadena2 = filaDatosCuotasYAportaciones26.createCell(8);
                Celdad26Cadena2.setCellStyle(datosEstilo);
                Celdad26Cadena2.setCellValue("PRESTAMO INVERSION AHORRO");
                Cell Celdad26 = filaDatosCuotasYAportaciones26.createCell(9);
                Celdad26.setCellStyle(datosEstiloMoneda);
                Celdad26.setCellValue(rstrabajadorescotizantez.getDouble("d26"));
                Cell Celdad26conteo = filaDatosCuotasYAportaciones26.createCell(11);
                Celdad26conteo.setCellStyle(datosEstilo);
                Celdad26conteo.setCellValue(rstrabajadorescotizantez.getInt("d26c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones27 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap23Cadena = filaDatosCuotasYAportaciones27.createCell(0);
                Celdap23Cadena.setCellStyle(datosEstilo);
                Celdap23Cadena.setCellValue("P_23");
                Cell Celdap23Cadena2 = filaDatosCuotasYAportaciones27.createCell(1);
                Celdap23Cadena2.setCellStyle(datosEstilo);
                Celdap23Cadena2.setCellValue("APOYO CAPACIDADES DIFERENTES");
                Cell Celdap23 = filaDatosCuotasYAportaciones27.createCell(2);
                Celdap23.setCellStyle(datosEstiloMoneda);
                Celdap23.setCellValue(rstrabajadorescotizantez.getDouble("p23"));
                Cell Celdap23conteo = filaDatosCuotasYAportaciones27.createCell(6);
                Celdap23conteo.setCellStyle(datosEstilo);
                Celdap23conteo.setCellValue(rstrabajadorescotizantez.getInt("p23c"));
                Cell Celdad27Cadena = filaDatosCuotasYAportaciones27.createCell(7);
                Celdad27Cadena.setCellStyle(datosEstilo);
                Celdad27Cadena.setCellValue("D_27");
                Cell Celdad27Cadena2 = filaDatosCuotasYAportaciones27.createCell(8);
                Celdad27Cadena2.setCellStyle(datosEstilo);
                Celdad27Cadena2.setCellValue("CAJA DE AHORRO SECCION COBACH");
                Cell Celdad27 = filaDatosCuotasYAportaciones27.createCell(9);
                Celdad27.setCellStyle(datosEstiloMoneda);
                Celdad27.setCellValue(rstrabajadorescotizantez.getDouble("d27"));
                Cell Celdad27conteo = filaDatosCuotasYAportaciones27.createCell(11);
                Celdad27conteo.setCellStyle(datosEstilo);
                Celdad27conteo.setCellValue(rstrabajadorescotizantez.getInt("d27c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones28 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap24Cadena = filaDatosCuotasYAportaciones28.createCell(0);
                Celdap24Cadena.setCellStyle(datosEstilo);
                Celdap24Cadena.setCellValue("P_24");
                Cell Celdap24Cadena2 = filaDatosCuotasYAportaciones28.createCell(1);
                Celdap24Cadena2.setCellStyle(datosEstilo);
                Celdap24Cadena2.setCellValue("ESTIMULO POR EFICIENCIA ACADEMICA");
                Cell Celdap24 = filaDatosCuotasYAportaciones28.createCell(2);
                Celdap24.setCellStyle(datosEstiloMoneda);
                Celdap24.setCellValue(rstrabajadorescotizantez.getDouble("p24"));
                Cell Celdap24conteo = filaDatosCuotasYAportaciones28.createCell(6);
                Celdap24conteo.setCellStyle(datosEstilo);
                Celdap24conteo.setCellValue(rstrabajadorescotizantez.getInt("p24c"));
                Cell Celdad28Cadena = filaDatosCuotasYAportaciones28.createCell(7);
                Celdad28Cadena.setCellStyle(datosEstilo);
                Celdad28Cadena.setCellValue("D_28");
                Cell Celdad28Cadena2 = filaDatosCuotasYAportaciones28.createCell(8);
                Celdad28Cadena2.setCellStyle(datosEstilo);
                Celdad28Cadena2.setCellValue("PRESTAMO CAJA AHORRO SECCION COBACH");
                Cell Celdad28 = filaDatosCuotasYAportaciones28.createCell(9);
                Celdad28.setCellStyle(datosEstiloMoneda);
                Celdad28.setCellValue(rstrabajadorescotizantez.getDouble("d28"));
                Cell Celdad28conteo = filaDatosCuotasYAportaciones28.createCell(11);
                Celdad28conteo.setCellStyle(datosEstilo);
                Celdad28conteo.setCellValue(rstrabajadorescotizantez.getInt("d28c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones29 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap25Cadena = filaDatosCuotasYAportaciones29.createCell(0);
                Celdap25Cadena.setCellStyle(datosEstilo);
                Celdap25Cadena.setCellValue("P_25");
                Cell Celdap25Cadena2 = filaDatosCuotasYAportaciones29.createCell(1);
                Celdap25Cadena2.setCellStyle(datosEstilo);
                Celdap25Cadena2.setCellValue("CANASTILLA DE MATERNIDAD DOCENTE");
                Cell Celdap25 = filaDatosCuotasYAportaciones29.createCell(2);
                Celdap25.setCellStyle(datosEstiloMoneda);
                Celdap25.setCellValue(rstrabajadorescotizantez.getDouble("p25"));
                Cell Celdap25conteo = filaDatosCuotasYAportaciones29.createCell(6);
                Celdap25conteo.setCellStyle(datosEstilo);
                Celdap25conteo.setCellValue(rstrabajadorescotizantez.getInt("p25c"));
                Cell Celdad29Cadena = filaDatosCuotasYAportaciones29.createCell(7);
                Celdad29Cadena.setCellStyle(datosEstilo);
                Celdad29Cadena.setCellValue("D_29");
                Cell Celdad29Cadena2 = filaDatosCuotasYAportaciones29.createCell(8);
                Celdad29Cadena2.setCellStyle(datosEstilo);
                Celdad29Cadena2.setCellValue("CUOTA SINDICAL LXIII");
                Cell Celdad29 = filaDatosCuotasYAportaciones29.createCell(9);
                Celdad29.setCellStyle(datosEstiloMoneda);
                Celdad29.setCellValue(rstrabajadorescotizantez.getDouble("d29"));
                Cell Celdad29conteo = filaDatosCuotasYAportaciones29.createCell(11);
                Celdad29conteo.setCellStyle(datosEstilo);
                Celdad29conteo.setCellValue(rstrabajadorescotizantez.getInt("d29c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones30 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap26Cadena = filaDatosCuotasYAportaciones30.createCell(0);
                Celdap26Cadena.setCellStyle(datosEstilo);
                Celdap26Cadena.setCellValue("P_26");
                Cell Celdap26Cadena2 = filaDatosCuotasYAportaciones30.createCell(1);
                Celdap26Cadena2.setCellStyle(datosEstilo);
                Celdap26Cadena2.setCellValue("APOYO PARA TRANSPORTE");
                Cell Celdap26 = filaDatosCuotasYAportaciones30.createCell(2);
                Celdap26.setCellStyle(datosEstiloMoneda);
                Celdap26.setCellValue(rstrabajadorescotizantez.getDouble("p26"));
                Cell Celdap26conteo = filaDatosCuotasYAportaciones30.createCell(6);
                Celdap26conteo.setCellStyle(datosEstilo);
                Celdap26conteo.setCellValue(rstrabajadorescotizantez.getInt("p26c"));
                Cell Celdad30Cadena = filaDatosCuotasYAportaciones30.createCell(7);
                Celdad30Cadena.setCellStyle(datosEstilo);
                Celdad30Cadena.setCellValue("D_30");
                Cell Celdad30Cadena2 = filaDatosCuotasYAportaciones30.createCell(8);
                Celdad30Cadena2.setCellStyle(datosEstilo);
                Celdad30Cadena2.setCellValue("CAJA DE AHORRO SECCION LXIII");
                Cell Celdad30 = filaDatosCuotasYAportaciones30.createCell(9);
                Celdad30.setCellStyle(datosEstiloMoneda);
                Celdad30.setCellValue(rstrabajadorescotizantez.getDouble("d30"));
                Cell Celdad30conteo = filaDatosCuotasYAportaciones30.createCell(11);
                Celdad30conteo.setCellStyle(datosEstilo);
                Celdad30conteo.setCellValue(rstrabajadorescotizantez.getInt("d30c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones31 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap27Cadena = filaDatosCuotasYAportaciones31.createCell(0);
                Celdap27Cadena.setCellStyle(datosEstilo);
                Celdap27Cadena.setCellValue("P_27");
                Cell Celdap27Cadena2 = filaDatosCuotasYAportaciones31.createCell(1);
                Celdap27Cadena2.setCellStyle(datosEstilo);
                Celdap27Cadena2.setCellValue("COMPENSACION EXTRAORDINARIA");
                Cell Celdap27 = filaDatosCuotasYAportaciones31.createCell(2);
                Celdap27.setCellStyle(datosEstiloMoneda);
                Celdap27.setCellValue(rstrabajadorescotizantez.getDouble("p27"));
                Cell Celdap27conteo = filaDatosCuotasYAportaciones31.createCell(6);
                Celdap27conteo.setCellStyle(datosEstilo);
                Celdap27conteo.setCellValue(rstrabajadorescotizantez.getInt("p27c"));
                Cell Celdad31Cadena = filaDatosCuotasYAportaciones31.createCell(7);
                Celdad31Cadena.setCellStyle(datosEstilo);
                Celdad31Cadena.setCellValue("D_31");
                Cell Celdad31Cadena2 = filaDatosCuotasYAportaciones31.createCell(8);
                Celdad31Cadena2.setCellStyle(datosEstilo);
                Celdad31Cadena2.setCellValue("PRESTAMO CAJA DE AHORRO SECCION LXIII");
                Cell Celdad31 = filaDatosCuotasYAportaciones31.createCell(9);
                Celdad31.setCellStyle(datosEstiloMoneda);
                Celdad31.setCellValue(rstrabajadorescotizantez.getDouble("d31"));
                Cell Celdad31conteo = filaDatosCuotasYAportaciones31.createCell(11);
                Celdad31conteo.setCellStyle(datosEstilo);
                Celdad31conteo.setCellValue(rstrabajadorescotizantez.getInt("d31c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones32 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap28Cadena = filaDatosCuotasYAportaciones32.createCell(0);
                Celdap28Cadena.setCellStyle(datosEstilo);
                Celdap28Cadena.setCellValue("P_28");
                Cell Celdap28Cadena2 = filaDatosCuotasYAportaciones32.createCell(1);
                Celdap28Cadena2.setCellStyle(datosEstilo);
                Celdap28Cadena2.setCellValue("ESTIMULO PERSONAL DIRECTIVO");
                Cell Celdap28 = filaDatosCuotasYAportaciones32.createCell(2);
                Celdap28.setCellStyle(datosEstiloMoneda);
                Celdap28.setCellValue(rstrabajadorescotizantez.getDouble("p28"));
                Cell Celdap28conteo = filaDatosCuotasYAportaciones32.createCell(6);
                Celdap28conteo.setCellStyle(datosEstilo);
                Celdap28conteo.setCellValue(rstrabajadorescotizantez.getInt("p28c"));
                Cell Celdad32Cadena = filaDatosCuotasYAportaciones32.createCell(7);
                Celdad32Cadena.setCellStyle(datosEstilo);
                Celdad32Cadena.setCellValue("D_32");
                Cell Celdad32Cadena2 = filaDatosCuotasYAportaciones32.createCell(8);
                Celdad32Cadena2.setCellStyle(datosEstilo);
                Celdad32Cadena2.setCellValue("FONDO DE INVERSION SECCION COBACH");
                Cell Celdad32 = filaDatosCuotasYAportaciones32.createCell(9);
                Celdad32.setCellStyle(datosEstiloMoneda);
                Celdad32.setCellValue(rstrabajadorescotizantez.getDouble("d32"));
                Cell Celdad32conteo = filaDatosCuotasYAportaciones32.createCell(11);
                Celdad32conteo.setCellStyle(datosEstilo);
                Celdad32conteo.setCellValue(rstrabajadorescotizantez.getInt("d32c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones33 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap29Cadena = filaDatosCuotasYAportaciones33.createCell(0);
                Celdap29Cadena.setCellStyle(datosEstilo);
                Celdap29Cadena.setCellValue("P_29");
                Cell Celdap29Cadena2 = filaDatosCuotasYAportaciones33.createCell(1);
                Celdap29Cadena2.setCellStyle(datosEstilo);
                Celdap29Cadena2.setCellValue("PRODUCTIVIDAD ADM/DOC");
                Cell Celdap29 = filaDatosCuotasYAportaciones33.createCell(2);
                Celdap29.setCellStyle(datosEstiloMoneda);
                Celdap29.setCellValue(rstrabajadorescotizantez.getDouble("p29"));
                Cell Celdap29conteo = filaDatosCuotasYAportaciones33.createCell(6);
                Celdap29conteo.setCellStyle(datosEstilo);
                Celdap29conteo.setCellValue(rstrabajadorescotizantez.getInt("p29c"));
                Cell Celdad33Cadena = filaDatosCuotasYAportaciones33.createCell(7);
                Celdad33Cadena.setCellStyle(datosEstilo);
                Celdad33Cadena.setCellValue("D_33");
                Cell Celdad33Cadena2 = filaDatosCuotasYAportaciones33.createCell(8);
                Celdad33Cadena2.setCellStyle(datosEstilo);
                Celdad33Cadena2.setCellValue("PRESTAMO INVERSION SECCION COBACH");
                Cell Celdad33 = filaDatosCuotasYAportaciones33.createCell(9);
                Celdad33.setCellStyle(datosEstiloMoneda);
                Celdad33.setCellValue(rstrabajadorescotizantez.getDouble("d33"));
                Cell Celdad33conteo = filaDatosCuotasYAportaciones33.createCell(11);
                Celdad33conteo.setCellStyle(datosEstilo);
                Celdad33conteo.setCellValue(rstrabajadorescotizantez.getInt("d33c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones34 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap30Cadena = filaDatosCuotasYAportaciones34.createCell(0);
                Celdap30Cadena.setCellStyle(datosEstilo);
                Celdap30Cadena.setCellValue("P_30");
                Cell Celdap30Cadena2 = filaDatosCuotasYAportaciones34.createCell(1);
                Celdap30Cadena2.setCellStyle(datosEstilo);
                Celdap30Cadena2.setCellValue("");
                Cell Celdap30 = filaDatosCuotasYAportaciones34.createCell(2);
                Celdap30.setCellStyle(datosEstiloMoneda);
                Celdap30.setCellValue(rstrabajadorescotizantez.getDouble("p30"));
                Cell Celdap30conteo = filaDatosCuotasYAportaciones34.createCell(6);
                Celdap30conteo.setCellStyle(datosEstilo);
                Celdap30conteo.setCellValue(rstrabajadorescotizantez.getInt("p30c"));
                Cell Celdad34Cadena = filaDatosCuotasYAportaciones34.createCell(7);
                Celdad34Cadena.setCellStyle(datosEstilo);
                Celdad34Cadena.setCellValue("D_34");
                Cell Celdad34Cadena2 = filaDatosCuotasYAportaciones34.createCell(8);
                Celdad34Cadena2.setCellStyle(datosEstilo);
                Celdad34Cadena2.setCellValue("CAJA DE AHORRO SITCOBACH");
                Cell Celdad34 = filaDatosCuotasYAportaciones34.createCell(9);
                Celdad34.setCellStyle(datosEstiloMoneda);
                Celdad34.setCellValue(rstrabajadorescotizantez.getDouble("d34"));
                Cell Celdad34conteo = filaDatosCuotasYAportaciones34.createCell(11);
                Celdad34conteo.setCellStyle(datosEstilo);
                Celdad34conteo.setCellValue(rstrabajadorescotizantez.getInt("d34c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones35 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap31Cadena = filaDatosCuotasYAportaciones35.createCell(0);
                Celdap31Cadena.setCellStyle(datosEstilo);
                Celdap31Cadena.setCellValue("P_31");
                Cell Celdap31Cadena2 = filaDatosCuotasYAportaciones35.createCell(1);
                Celdap31Cadena2.setCellStyle(datosEstilo);
                Celdap31Cadena2.setCellValue("");
                Cell Celdap31 = filaDatosCuotasYAportaciones35.createCell(2);
                Celdap31.setCellStyle(datosEstiloMoneda);
                Celdap31.setCellValue(rstrabajadorescotizantez.getDouble("p31"));
                Cell Celdap31conteo = filaDatosCuotasYAportaciones35.createCell(6);
                Celdap31conteo.setCellStyle(datosEstilo);
                Celdap31conteo.setCellValue(rstrabajadorescotizantez.getInt("p31c"));
                Cell Celdad35Cadena = filaDatosCuotasYAportaciones35.createCell(7);
                Celdad35Cadena.setCellStyle(datosEstilo);
                Celdad35Cadena.setCellValue("D_35");
                Cell Celdad35Cadena2 = filaDatosCuotasYAportaciones35.createCell(8);
                Celdad35Cadena2.setCellStyle(datosEstilo);
                Celdad35Cadena2.setCellValue("PRESTAMO CAJA DE AHORRO SITCOBACH");
                Cell Celdad35 = filaDatosCuotasYAportaciones35.createCell(9);
                Celdad35.setCellStyle(datosEstiloMoneda);
                Celdad35.setCellValue(rstrabajadorescotizantez.getDouble("d35"));
                Cell Celdad35conteo = filaDatosCuotasYAportaciones35.createCell(11);
                Celdad35conteo.setCellStyle(datosEstilo);
                Celdad35conteo.setCellValue(rstrabajadorescotizantez.getInt("d35c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones36 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap32Cadena = filaDatosCuotasYAportaciones36.createCell(0);
                Celdap32Cadena.setCellStyle(datosEstilo);
                Celdap32Cadena.setCellValue("P_32");
                Cell Celdap32Cadena2 = filaDatosCuotasYAportaciones36.createCell(1);
                Celdap32Cadena2.setCellStyle(datosEstilo);
                Celdap32Cadena2.setCellValue("DESPENSA DOCENTE EMSAD");
                Cell Celdap32 = filaDatosCuotasYAportaciones36.createCell(2);
                Celdap32.setCellStyle(datosEstiloMoneda);
                Celdap32.setCellValue(rstrabajadorescotizantez.getDouble("p32"));
                Cell Celdap32conteo = filaDatosCuotasYAportaciones36.createCell(6);
                Celdap32conteo.setCellStyle(datosEstilo);
                Celdap32conteo.setCellValue(rstrabajadorescotizantez.getInt("p32c"));
                Cell Celdad36Cadena = filaDatosCuotasYAportaciones36.createCell(7);
                Celdad36Cadena.setCellStyle(datosEstilo);
                Celdad36Cadena.setCellValue("D_36");
                Cell Celdad36Cadena2 = filaDatosCuotasYAportaciones36.createCell(8);
                Celdad36Cadena2.setCellStyle(datosEstilo);
                Celdad36Cadena2.setCellValue("AYUDA MUTUA SITCOBACH");
                Cell Celdad36 = filaDatosCuotasYAportaciones36.createCell(9);
                Celdad36.setCellStyle(datosEstiloMoneda);
                Celdad36.setCellValue(rstrabajadorescotizantez.getDouble("d36"));
                Cell Celdad36conteo = filaDatosCuotasYAportaciones36.createCell(11);
                Celdad36conteo.setCellStyle(datosEstilo);
                Celdad36conteo.setCellValue(rstrabajadorescotizantez.getInt("d36c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones37 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap33Cadena = filaDatosCuotasYAportaciones37.createCell(0);
                Celdap33Cadena.setCellStyle(datosEstilo);
                Celdap33Cadena.setCellValue("P_33");
                Cell Celdap33Cadena2 = filaDatosCuotasYAportaciones37.createCell(1);
                Celdap33Cadena2.setCellStyle(datosEstilo);
                Celdap33Cadena2.setCellValue("DIFERENCIA DE SUELDO NO GRAVABLE");
                Cell Celdap33 = filaDatosCuotasYAportaciones37.createCell(2);
                Celdap33.setCellStyle(datosEstiloMoneda);
                Celdap33.setCellValue(rstrabajadorescotizantez.getDouble("p33"));
                Cell Celdap33conteo = filaDatosCuotasYAportaciones37.createCell(6);
                Celdap33conteo.setCellStyle(datosEstilo);
                Celdap33conteo.setCellValue(rstrabajadorescotizantez.getInt("p33c"));
                Cell Celdad37Cadena = filaDatosCuotasYAportaciones37.createCell(7);
                Celdad37Cadena.setCellStyle(datosEstilo);
                Celdad37Cadena.setCellValue("D_37");
                Cell Celdad37Cadena2 = filaDatosCuotasYAportaciones37.createCell(8);
                Celdad37Cadena2.setCellStyle(datosEstilo);
                Celdad37Cadena2.setCellValue("SEGURO ARGOS");
                Cell Celdad37 = filaDatosCuotasYAportaciones37.createCell(9);
                Celdad37.setCellStyle(datosEstiloMoneda);
                Celdad37.setCellValue(rstrabajadorescotizantez.getDouble("d37"));
                Cell Celdad37conteo = filaDatosCuotasYAportaciones37.createCell(11);
                Celdad37conteo.setCellStyle(datosEstilo);
                Celdad37conteo.setCellValue(rstrabajadorescotizantez.getInt("d37c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones38 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap34Cadena = filaDatosCuotasYAportaciones38.createCell(0);
                Celdap34Cadena.setCellStyle(datosEstilo);
                Celdap34Cadena.setCellValue("P_34");
                Cell Celdap34Cadena2 = filaDatosCuotasYAportaciones38.createCell(1);
                Celdap34Cadena2.setCellStyle(datosEstilo);
                Celdap34Cadena2.setCellValue("BONO DE FIN DE AÑO");
                Cell Celdap34 = filaDatosCuotasYAportaciones38.createCell(2);
                Celdap34.setCellStyle(datosEstiloMoneda);
                Celdap34.setCellValue(rstrabajadorescotizantez.getDouble("p34"));
                Cell Celdap34conteo = filaDatosCuotasYAportaciones38.createCell(6);
                Celdap34conteo.setCellStyle(datosEstilo);
                Celdap34conteo.setCellValue(rstrabajadorescotizantez.getInt("p34c"));
                Cell Celdad38Cadena = filaDatosCuotasYAportaciones38.createCell(7);
                Celdad38Cadena.setCellStyle(datosEstilo);
                Celdad38Cadena.setCellValue("D_38");
                Cell Celdad38Cadena2 = filaDatosCuotasYAportaciones38.createCell(8);
                Celdad38Cadena2.setCellStyle(datosEstilo);
                Celdad38Cadena2.setCellValue("AYUDA MUTUA SECCION LXIII");
                Cell Celdad38 = filaDatosCuotasYAportaciones38.createCell(9);
                Celdad38.setCellStyle(datosEstiloMoneda);
                Celdad38.setCellValue(rstrabajadorescotizantez.getDouble("d38"));
                Cell Celdad38conteo = filaDatosCuotasYAportaciones38.createCell(11);
                Celdad38conteo.setCellStyle(datosEstilo);
                Celdad38conteo.setCellValue(rstrabajadorescotizantez.getInt("d38c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones39 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap35Cadena = filaDatosCuotasYAportaciones39.createCell(0);
                Celdap35Cadena.setCellStyle(datosEstilo);
                Celdap35Cadena.setCellValue("P_35");
                Cell Celdap35Cadena2 = filaDatosCuotasYAportaciones39.createCell(1);
                Celdap35Cadena2.setCellStyle(datosEstilo);
                Celdap35Cadena2.setCellValue("AYUDA A LA ECONOMIA FAMILIAR");
                Cell Celdap35 = filaDatosCuotasYAportaciones39.createCell(2);
                Celdap35.setCellStyle(datosEstiloMoneda);
                Celdap35.setCellValue(rstrabajadorescotizantez.getDouble("p35"));
                Cell Celdap35conteo = filaDatosCuotasYAportaciones39.createCell(6);
                Celdap35conteo.setCellStyle(datosEstilo);
                Celdap35conteo.setCellValue(rstrabajadorescotizantez.getInt("p35c"));
                Cell Celdad39Cadena = filaDatosCuotasYAportaciones39.createCell(7);
                Celdad39Cadena.setCellStyle(datosEstilo);
                Celdad39Cadena.setCellValue("D_39");
                Cell Celdad39Cadena2 = filaDatosCuotasYAportaciones39.createCell(8);
                Celdad39Cadena2.setCellStyle(datosEstilo);
                Celdad39Cadena2.setCellValue("SEGUROS METLIFE");
                Cell Celdad39 = filaDatosCuotasYAportaciones39.createCell(9);
                Celdad39.setCellStyle(datosEstiloMoneda);
                Celdad39.setCellValue(rstrabajadorescotizantez.getDouble("d39"));
                Cell Celdad39conteo = filaDatosCuotasYAportaciones39.createCell(11);
                Celdad39conteo.setCellStyle(datosEstilo);
                Celdad39conteo.setCellValue(rstrabajadorescotizantez.getInt("d39c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones40 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap36Cadena = filaDatosCuotasYAportaciones40.createCell(0);
                Celdap36Cadena.setCellStyle(datosEstilo);
                Celdap36Cadena.setCellValue("P_36");
                Cell Celdap36Cadena2 = filaDatosCuotasYAportaciones40.createCell(1);
                Celdap36Cadena2.setCellStyle(datosEstilo);
                Celdap36Cadena2.setCellValue("ESTIMULO ECONOMICO POR DOCTORADO");
                Cell Celdap36 = filaDatosCuotasYAportaciones40.createCell(2);
                Celdap36.setCellStyle(datosEstiloMoneda);
                Celdap36.setCellValue(rstrabajadorescotizantez.getDouble("p36"));
                Cell Celdap36conteo = filaDatosCuotasYAportaciones40.createCell(6);
                Celdap36conteo.setCellStyle(datosEstilo);
                Celdap36conteo.setCellValue(rstrabajadorescotizantez.getInt("p36c"));
                Cell Celdad40Cadena = filaDatosCuotasYAportaciones40.createCell(7);
                Celdad40Cadena.setCellStyle(datosEstilo);
                Celdad40Cadena.setCellValue("D_40");
                Cell Celdad40Cadena2 = filaDatosCuotasYAportaciones40.createCell(8);
                Celdad40Cadena2.setCellStyle(datosEstilo);
                Celdad40Cadena2.setCellValue("PRESTAMOS FONSAR");
                Cell Celdad40 = filaDatosCuotasYAportaciones40.createCell(9);
                Celdad40.setCellStyle(datosEstiloMoneda);
                Celdad40.setCellValue(rstrabajadorescotizantez.getDouble("d40"));
                Cell Celdad40conteo = filaDatosCuotasYAportaciones40.createCell(11);
                Celdad40conteo.setCellStyle(datosEstilo);
                Celdad40conteo.setCellValue(rstrabajadorescotizantez.getInt("d40c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones41 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap37Cadena = filaDatosCuotasYAportaciones41.createCell(0);
                Celdap37Cadena.setCellStyle(datosEstilo);
                Celdap37Cadena.setCellValue("P_37");
                Cell Celdap37Cadena2 = filaDatosCuotasYAportaciones41.createCell(1);
                Celdap37Cadena2.setCellStyle(datosEstilo);
                Celdap37Cadena2.setCellValue("BONO DEL SERVIDOR PUBLICO");
                Cell Celdap37 = filaDatosCuotasYAportaciones41.createCell(2);
                Celdap37.setCellStyle(datosEstiloMoneda);
                Celdap37.setCellValue(rstrabajadorescotizantez.getDouble("p37"));
                Cell Celdap37conteo = filaDatosCuotasYAportaciones41.createCell(6);
                Celdap37conteo.setCellStyle(datosEstilo);
                Celdap37conteo.setCellValue(rstrabajadorescotizantez.getInt("p37c"));
                Cell Celdad41Cadena = filaDatosCuotasYAportaciones41.createCell(7);
                Celdad41Cadena.setCellStyle(datosEstilo);
                Celdad41Cadena.setCellValue("D_41");
                Cell Celdad41Cadena2 = filaDatosCuotasYAportaciones41.createCell(8);
                Celdad41Cadena2.setCellStyle(datosEstilo);
                Celdad41Cadena2.setCellValue("FONDO SOLIDARIO DE AHORRO PARA EL RETIRO");
                Cell Celdad41 = filaDatosCuotasYAportaciones41.createCell(9);
                Celdad41.setCellStyle(datosEstiloMoneda);
                Celdad41.setCellValue(rstrabajadorescotizantez.getDouble("d41"));
                Cell Celdad41conteo = filaDatosCuotasYAportaciones41.createCell(11);
                Celdad41conteo.setCellStyle(datosEstilo);
                Celdad41conteo.setCellValue(rstrabajadorescotizantez.getInt("d41c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones42 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap38Cadena = filaDatosCuotasYAportaciones42.createCell(0);
                Celdap38Cadena.setCellStyle(datosEstilo);
                Celdap38Cadena.setCellValue("P_38");
                Cell Celdap38Cadena2 = filaDatosCuotasYAportaciones42.createCell(1);
                Celdap38Cadena2.setCellStyle(datosEstilo);
                Celdap38Cadena2.setCellValue("AJUSTE DE CALENDARIO");
                Cell Celdap38 = filaDatosCuotasYAportaciones42.createCell(2);
                Celdap38.setCellStyle(datosEstiloMoneda);
                Celdap38.setCellValue(rstrabajadorescotizantez.getDouble("p38"));
                Cell Celdap38conteo = filaDatosCuotasYAportaciones42.createCell(6);
                Celdap38conteo.setCellStyle(datosEstilo);
                Celdap38conteo.setCellValue(rstrabajadorescotizantez.getInt("p38c"));
                Cell Celdad42Cadena = filaDatosCuotasYAportaciones42.createCell(7);
                Celdad42Cadena.setCellStyle(datosEstilo);
                Celdad42Cadena.setCellValue("D_42");
                Cell Celdad42Cadena2 = filaDatosCuotasYAportaciones42.createCell(8);
                Celdad42Cadena2.setCellStyle(datosEstilo);
                Celdad42Cadena2.setCellValue("PRESTAMO RESPALDA 2M");
                Cell Celdad42 = filaDatosCuotasYAportaciones42.createCell(9);
                Celdad42.setCellStyle(datosEstiloMoneda);
                Celdad42.setCellValue(rstrabajadorescotizantez.getDouble("d42"));
                Cell Celdad42conteo = filaDatosCuotasYAportaciones42.createCell(11);
                Celdad42conteo.setCellStyle(datosEstilo);
                Celdad42conteo.setCellValue(rstrabajadorescotizantez.getInt("d42c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones43 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap39Cadena = filaDatosCuotasYAportaciones43.createCell(0);
                Celdap39Cadena.setCellStyle(datosEstilo);
                Celdap39Cadena.setCellValue("P_39");
                Cell Celdap39Cadena2 = filaDatosCuotasYAportaciones43.createCell(1);
                Celdap39Cadena2.setCellStyle(datosEstilo);
                Celdap39Cadena2.setCellValue("PRIMA DE ANTIGUEDDA AJUSTE DE CALENDARIO");
                Cell Celdap39 = filaDatosCuotasYAportaciones43.createCell(2);
                Celdap39.setCellStyle(datosEstiloMoneda);
                Celdap39.setCellValue(rstrabajadorescotizantez.getDouble("p39"));
                Cell Celdap39conteo = filaDatosCuotasYAportaciones43.createCell(6);
                Celdap39conteo.setCellStyle(datosEstilo);
                Celdap39conteo.setCellValue(rstrabajadorescotizantez.getInt("p39c"));
                Cell Celdad43Cadena = filaDatosCuotasYAportaciones43.createCell(7);
                Celdad43Cadena.setCellStyle(datosEstilo);
                Celdad43Cadena.setCellValue("D_43");
                Cell Celdad43Cadena2 = filaDatosCuotasYAportaciones43.createCell(8);
                Celdad43Cadena2.setCellStyle(datosEstilo);
                Celdad43Cadena2.setCellValue("FONDO DE INVERSION DE AHORRO PARA LA JUBILACION");
                Cell Celdad43 = filaDatosCuotasYAportaciones43.createCell(9);
                Celdad43.setCellStyle(datosEstiloMoneda);
                Celdad43.setCellValue(rstrabajadorescotizantez.getDouble("d43"));
                Cell Celdad43conteo = filaDatosCuotasYAportaciones43.createCell(11);
                Celdad43conteo.setCellStyle(datosEstilo);
                Celdad43conteo.setCellValue(rstrabajadorescotizantez.getInt("d43c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones44 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap40Cadena = filaDatosCuotasYAportaciones44.createCell(0);
                Celdap40Cadena.setCellStyle(datosEstilo);
                Celdap40Cadena.setCellValue("P_39");
                Cell Celdap40Cadena2 = filaDatosCuotasYAportaciones44.createCell(1);
                Celdap40Cadena2.setCellStyle(datosEstilo);
                Celdap40Cadena2.setCellValue("PRIMA DE ANTIGUEDAD AJUSTE DE CALENDARIO");
                Cell Celdap40 = filaDatosCuotasYAportaciones44.createCell(2);
                Celdap40.setCellStyle(datosEstiloMoneda);
                Celdap40.setCellValue(rstrabajadorescotizantez.getDouble("p40"));
                Cell Celdap40conteo = filaDatosCuotasYAportaciones44.createCell(6);
                Celdap40conteo.setCellStyle(datosEstilo);
                Celdap40conteo.setCellValue(rstrabajadorescotizantez.getInt("p40c"));
                Cell Celdad44Cadena = filaDatosCuotasYAportaciones44.createCell(7);
                Celdad44Cadena.setCellStyle(datosEstilo);
                Celdad44Cadena.setCellValue("D_44");
                Cell Celdad44Cadena2 = filaDatosCuotasYAportaciones44.createCell(8);
                Celdad44Cadena2.setCellStyle(datosEstilo);
                Celdad44Cadena2.setCellValue("PRESTAMO FONDO DE INVERSION DE AHORRO PARA LA JUBILACION");
                Cell Celdad44 = filaDatosCuotasYAportaciones44.createCell(9);
                Celdad44.setCellStyle(datosEstiloMoneda);
                Celdad44.setCellValue(rstrabajadorescotizantez.getDouble("d44"));
                Cell Celdad44conteo = filaDatosCuotasYAportaciones44.createCell(11);
                Celdad44conteo.setCellStyle(datosEstilo);
                Celdad44conteo.setCellValue(rstrabajadorescotizantez.getInt("d44c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones45 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap41Cadena = filaDatosCuotasYAportaciones45.createCell(0);
                Celdap41Cadena.setCellStyle(datosEstilo);
                Celdap41Cadena.setCellValue("P_41");
                Cell Celdap41Cadena2 = filaDatosCuotasYAportaciones45.createCell(1);
                Celdap41Cadena2.setCellStyle(datosEstilo);
                Celdap41Cadena2.setCellValue("ESTIMULO POR AÑOS DE SERVICIO");
                Cell Celdap41 = filaDatosCuotasYAportaciones45.createCell(2);
                Celdap41.setCellStyle(datosEstiloMoneda);
                Celdap41.setCellValue(rstrabajadorescotizantez.getDouble("p41"));
                Cell Celdap41conteo = filaDatosCuotasYAportaciones45.createCell(6);
                Celdap41conteo.setCellStyle(datosEstilo);
                Celdap41conteo.setCellValue(rstrabajadorescotizantez.getInt("p41c"));
                Cell Celdad45Cadena = filaDatosCuotasYAportaciones45.createCell(7);
                Celdad45Cadena.setCellStyle(datosEstilo);
                Celdad45Cadena.setCellValue("D_45");
                Cell Celdad45Cadena2 = filaDatosCuotasYAportaciones45.createCell(8);
                Celdad45Cadena2.setCellStyle(datosEstilo);
                Celdad45Cadena2.setCellValue("CAJA DE AHORRO ESCOLAR SECCION XXXI");
                Cell Celdad45 = filaDatosCuotasYAportaciones45.createCell(9);
                Celdad45.setCellStyle(datosEstiloMoneda);
                Celdad45.setCellValue(rstrabajadorescotizantez.getDouble("d45"));
                Cell Celdad45conteo = filaDatosCuotasYAportaciones45.createCell(11);
                Celdad45conteo.setCellStyle(datosEstilo);
                Celdad45conteo.setCellValue(rstrabajadorescotizantez.getInt("d45c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones46 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap42Cadena = filaDatosCuotasYAportaciones46.createCell(0);
                Celdap42Cadena.setCellStyle(datosEstilo);
                Celdap42Cadena.setCellValue("P_42");
                Cell Celdap42Cadena2 = filaDatosCuotasYAportaciones46.createCell(1);
                Celdap42Cadena2.setCellStyle(datosEstilo);
                Celdap42Cadena2.setCellValue("SUBSIDIO PARA EL EMPLEO ACREDITABLE");
                Cell Celdap42 = filaDatosCuotasYAportaciones46.createCell(2);
                Celdap42.setCellStyle(datosEstiloMoneda);
                Celdap42.setCellValue(rstrabajadorescotizantez.getDouble("p42"));
                Cell Celdap42conteo = filaDatosCuotasYAportaciones46.createCell(6);
                Celdap42conteo.setCellStyle(datosEstilo);
                Celdap42conteo.setCellValue(rstrabajadorescotizantez.getInt("p42c"));
                Cell Celdad46Cadena = filaDatosCuotasYAportaciones46.createCell(7);
                Celdad46Cadena.setCellStyle(datosEstilo);
                Celdad46Cadena.setCellValue("D_46");
                Cell Celdad46Cadena2 = filaDatosCuotasYAportaciones46.createCell(8);
                Celdad46Cadena2.setCellStyle(datosEstilo);
                Celdad46Cadena2.setCellValue("PRESTAMOS CAJA DE AHORRO ESCOLAR SECCION XXXI");
                Cell Celdad46 = filaDatosCuotasYAportaciones46.createCell(9);
                Celdad46.setCellStyle(datosEstiloMoneda);
                Celdad46.setCellValue(rstrabajadorescotizantez.getDouble("d46"));
                Cell Celdad46conteo = filaDatosCuotasYAportaciones46.createCell(11);
                Celdad46conteo.setCellStyle(datosEstilo);
                Celdad46conteo.setCellValue(rstrabajadorescotizantez.getInt("d46c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones47 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap43Cadena = filaDatosCuotasYAportaciones47.createCell(0);
                Celdap43Cadena.setCellStyle(datosEstilo);
                Celdap43Cadena.setCellValue("P_43");
                Cell Celdap43Cadena2 = filaDatosCuotasYAportaciones47.createCell(1);
                Celdap43Cadena2.setCellStyle(datosEstilo);
                Celdap43Cadena2.setCellValue("APOYO CLIDDA");
                Cell Celdap43 = filaDatosCuotasYAportaciones47.createCell(2);
                Celdap43.setCellStyle(datosEstiloMoneda);
                Celdap43.setCellValue(rstrabajadorescotizantez.getDouble("p43"));
                Cell Celdap43conteo = filaDatosCuotasYAportaciones47.createCell(6);
                Celdap43conteo.setCellStyle(datosEstilo);
                Celdap43conteo.setCellValue(rstrabajadorescotizantez.getInt("p43c"));
                Cell Celdad47Cadena = filaDatosCuotasYAportaciones47.createCell(7);
                Celdad47Cadena.setCellStyle(datosEstilo);
                Celdad47Cadena.setCellValue("D_47");
                Cell Celdad47Cadena2 = filaDatosCuotasYAportaciones47.createCell(8);
                Celdad47Cadena2.setCellStyle(datosEstilo);
                Celdad47Cadena2.setCellValue("FONDO DE AOHRRO SOLIDARIO DE INVERSION");
                Cell Celdad47 = filaDatosCuotasYAportaciones47.createCell(9);
                Celdad47.setCellStyle(datosEstiloMoneda);
                Celdad47.setCellValue(rstrabajadorescotizantez.getDouble("d47"));
                Cell Celdad47conteo = filaDatosCuotasYAportaciones47.createCell(11);
                Celdad47conteo.setCellStyle(datosEstilo);
                Celdad47conteo.setCellValue(rstrabajadorescotizantez.getInt("d47c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones48 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap44Cadena = filaDatosCuotasYAportaciones48.createCell(0);
                Celdap44Cadena.setCellStyle(datosEstilo);
                Celdap44Cadena.setCellValue("P_44");
                Cell Celdap44Cadena2 = filaDatosCuotasYAportaciones48.createCell(1);
                Celdap44Cadena2.setCellStyle(datosEstilo);
                Celdap44Cadena2.setCellValue("APOYO PARA IMPRESION DE TESIS");
                Cell Celdap44 = filaDatosCuotasYAportaciones48.createCell(2);
                Celdap44.setCellStyle(datosEstiloMoneda);
                Celdap44.setCellValue(rstrabajadorescotizantez.getDouble("p44"));
                Cell Celdap44conteo = filaDatosCuotasYAportaciones48.createCell(6);
                Celdap44conteo.setCellStyle(datosEstilo);
                Celdap44conteo.setCellValue(rstrabajadorescotizantez.getInt("p44c"));
                Cell Celdad48Cadena = filaDatosCuotasYAportaciones48.createCell(7);
                Celdad48Cadena.setCellStyle(datosEstilo);
                Celdad48Cadena.setCellValue("D_48");
                Cell Celdad48Cadena2 = filaDatosCuotasYAportaciones48.createCell(8);
                Celdad48Cadena2.setCellStyle(datosEstilo);
                Celdad48Cadena2.setCellValue("PRESTAMOS FONDO DE AHORRO SOLIDARIO DE INVERSION");
                Cell Celdad48 = filaDatosCuotasYAportaciones48.createCell(9);
                Celdad48.setCellStyle(datosEstiloMoneda);
                Celdad48.setCellValue(rstrabajadorescotizantez.getDouble("d48"));
                Cell Celdad48conteo = filaDatosCuotasYAportaciones48.createCell(11);
                Celdad48conteo.setCellStyle(datosEstilo);
                Celdad48conteo.setCellValue(rstrabajadorescotizantez.getInt("d48c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones49 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap45Cadena = filaDatosCuotasYAportaciones49.createCell(0);
                Celdap45Cadena.setCellStyle(datosEstilo);
                Celdap45Cadena.setCellValue("P_45");
                Cell Celdap45Cadena2 = filaDatosCuotasYAportaciones49.createCell(1);
                Celdap45Cadena2.setCellStyle(datosEstilo);
                Celdap45Cadena2.setCellValue("APOYO POR TITULACION LICENCIATURA");
                Cell Celdap45 = filaDatosCuotasYAportaciones49.createCell(2);
                Celdap45.setCellStyle(datosEstiloMoneda);
                Celdap45.setCellValue(rstrabajadorescotizantez.getDouble("p45"));
                Cell Celdap45conteo = filaDatosCuotasYAportaciones49.createCell(6);
                Celdap45conteo.setCellStyle(datosEstilo);
                Celdap45conteo.setCellValue(rstrabajadorescotizantez.getInt("p45c"));
                Cell Celdad49Cadena = filaDatosCuotasYAportaciones49.createCell(7);
                Celdad49Cadena.setCellStyle(datosEstilo);
                Celdad49Cadena.setCellValue("D_49");
                Cell Celdad49Cadena2 = filaDatosCuotasYAportaciones49.createCell(8);
                Celdad49Cadena2.setCellStyle(datosEstilo);
                Celdad49Cadena2.setCellValue("PRESTAMOS FONDO GARANTIA FIAR");
                Cell Celdad49 = filaDatosCuotasYAportaciones49.createCell(9);
                Celdad49.setCellStyle(datosEstiloMoneda);
                Celdad49.setCellValue(rstrabajadorescotizantez.getDouble("d49"));
                Cell Celdad49conteo = filaDatosCuotasYAportaciones49.createCell(11);
                Celdad49conteo.setCellStyle(datosEstilo);
                Celdad49conteo.setCellValue(rstrabajadorescotizantez.getInt("d49c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones50 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap46Cadena = filaDatosCuotasYAportaciones50.createCell(0);
                Celdap46Cadena.setCellStyle(datosEstilo);
                Celdap46Cadena.setCellValue("P_46");
                Cell Celdap46Cadena2 = filaDatosCuotasYAportaciones50.createCell(1);
                Celdap46Cadena2.setCellStyle(datosEstilo);
                Celdap46Cadena2.setCellValue("APOYO POR TITULACION MAESTRIA");
                Cell Celdap46 = filaDatosCuotasYAportaciones50.createCell(2);
                Celdap46.setCellStyle(datosEstiloMoneda);
                Celdap46.setCellValue(rstrabajadorescotizantez.getDouble("p46"));
                Cell Celdap46conteo = filaDatosCuotasYAportaciones50.createCell(6);
                Celdap46conteo.setCellStyle(datosEstilo);
                Celdap46conteo.setCellValue(rstrabajadorescotizantez.getInt("p46c"));
                Cell Celdad50Cadena = filaDatosCuotasYAportaciones50.createCell(7);
                Celdad50Cadena.setCellStyle(datosEstilo);
                Celdad50Cadena.setCellValue("D_49");
                Cell Celdad50Cadena2 = filaDatosCuotasYAportaciones50.createCell(8);
                Celdad50Cadena2.setCellStyle(datosEstilo);
                Celdad50Cadena2.setCellValue("PRESTAMOS FONDO GARANTIA FONSAR");
                Cell Celdad50 = filaDatosCuotasYAportaciones50.createCell(9);
                Celdad50.setCellStyle(datosEstiloMoneda);
                Celdad50.setCellValue(rstrabajadorescotizantez.getDouble("d50"));
                Cell Celdad50conteo = filaDatosCuotasYAportaciones50.createCell(11);
                Celdad50conteo.setCellStyle(datosEstilo);
                Celdad50conteo.setCellValue(rstrabajadorescotizantez.getInt("d50c"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones51 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell Celdap47Cadena = filaDatosCuotasYAportaciones51.createCell(0);
                Celdap47Cadena.setCellStyle(datosEstilo);
                Celdap47Cadena.setCellValue("P_47");
                Cell Celdap47Cadena2 = filaDatosCuotasYAportaciones51.createCell(1);
                Celdap47Cadena2.setCellStyle(datosEstilo);
                Celdap47Cadena2.setCellValue("APOYO APARATOS ORTOPEDICOS");
                Cell Celdap47 = filaDatosCuotasYAportaciones51.createCell(2);
                Celdap47.setCellStyle(datosEstiloMoneda);
                Celdap47.setCellValue(rstrabajadorescotizantez.getDouble("p47"));
                Cell Celdap47conteo = filaDatosCuotasYAportaciones51.createCell(6);
                Celdap47conteo.setCellStyle(datosEstilo);
                Celdap47conteo.setCellValue(rstrabajadorescotizantez.getInt("p47c"));
                Cell CeldadcbCadena = filaDatosCuotasYAportaciones51.createCell(7);
                CeldadcbCadena.setCellStyle(datosEstilo);
                CeldadcbCadena.setCellValue("D_CB");
                Cell CeldadcbCadena2 = filaDatosCuotasYAportaciones51.createCell(8);
                CeldadcbCadena2.setCellStyle(datosEstilo);
                CeldadcbCadena2.setCellValue("PRESTAMOS SEFI");
                Cell Celdadcb = filaDatosCuotasYAportaciones51.createCell(9);
                Celdadcb.setCellStyle(datosEstiloMoneda);
                Celdadcb.setCellValue(rstrabajadorescotizantez.getDouble("dcb"));
                Cell Celdadcbconteo = filaDatosCuotasYAportaciones51.createCell(11);
                Celdadcbconteo.setCellStyle(datosEstilo);
                Celdadcbconteo.setCellValue(rstrabajadorescotizantez.getInt("dcbc"));
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones52 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell CeldappeCadena = filaDatosCuotasYAportaciones52.createCell(0);
                CeldappeCadena.setCellStyle(datosEstilo);
                CeldappeCadena.setCellValue("P_PE");
                Cell CeldappeCadena2 = filaDatosCuotasYAportaciones52.createCell(1);
                CeldappeCadena2.setCellStyle(datosEstilo);
                CeldappeCadena2.setCellValue("PREVISION EXENTA");
                Cell Celdappe = filaDatosCuotasYAportaciones52.createCell(2);
                Celdappe.setCellStyle(datosEstiloMoneda);
                Celdappe.setCellValue(rstrabajadorescotizantez.getDouble("ppe"));
                Cell Celdappeconteo = filaDatosCuotasYAportaciones52.createCell(6);
                Celdappeconteo.setCellStyle(datosEstilo);
                Celdappeconteo.setCellValue(rstrabajadorescotizantez.getInt("ppec"));
                Cell Celdad51Cadena = filaDatosCuotasYAportaciones52.createCell(7);
                Celdad51Cadena.setCellStyle(datosEstilo);
                Celdad51Cadena.setCellValue("d51");
                Cell Celdad51Cadena2 = filaDatosCuotasYAportaciones52.createCell(8);
                Celdad51Cadena2.setCellStyle(datosEstilo);
                Celdad51Cadena2.setCellValue("");
                Cell Celdad51 = filaDatosCuotasYAportaciones52.createCell(9);
                Celdad51.setCellStyle(datosEstiloMoneda);
                Celdad51.setCellValue(0);
                Cell Celdad51conteo = filaDatosCuotasYAportaciones52.createCell(11);
                Celdad51conteo.setCellStyle(datosEstilo);
                Celdad51conteo.setCellValue(0);
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones53 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                Cell CeldappgCadena = filaDatosCuotasYAportaciones53.createCell(0);
                CeldappgCadena.setCellStyle(datosEstilo);
                CeldappgCadena.setCellValue("P_PG");
                Cell CeldappgCadena2 = filaDatosCuotasYAportaciones53.createCell(1);
                CeldappgCadena2.setCellStyle(datosEstilo);
                CeldappgCadena2.setCellValue("PREVISION GRAVADA");
                Cell Celdappg = filaDatosCuotasYAportaciones53.createCell(2);
                Celdappg.setCellStyle(datosEstiloMoneda);
                Celdappg.setCellValue(rstrabajadorescotizantez.getDouble("ppg"));
                Cell Celdappgconteo = filaDatosCuotasYAportaciones53.createCell(6);
                Celdappgconteo.setCellStyle(datosEstilo);
                Celdappgconteo.setCellValue(rstrabajadorescotizantez.getInt("ppgc"));
                Cell Celdad52Cadena = filaDatosCuotasYAportaciones53.createCell(7);
                Celdad52Cadena.setCellStyle(datosEstilo);
                Celdad52Cadena.setCellValue("d52");
                Cell Celdad52Cadena2 = filaDatosCuotasYAportaciones53.createCell(8);
                Celdad52Cadena2.setCellStyle(datosEstilo);
                Celdad52Cadena2.setCellValue("NO USADA");
                Cell Celdad52 = filaDatosCuotasYAportaciones53.createCell(9);
                Celdad52.setCellStyle(datosEstiloMoneda);
                Celdad52.setCellValue(0);
                Cell Celdad52conteo = filaDatosCuotasYAportaciones53.createCell(11);
                Celdad52conteo.setCellStyle(datosEstilo);
                Celdad52conteo.setCellValue(0);
                numFilaDatosCuotasYAportaciones++;
                Row filaDatosCuotasYAportaciones54 = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                //   }
            }
            int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);
            Row sumapercepciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 3);
            Row cuotasisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 4);
            Row fovisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 5);
            Row aportacionisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 6);

            Cell Celdasumas = sumapercepciones.createCell(0);
            Celdasumas.setCellStyle(datosEstilo);
            Celdasumas.setCellValue("TOTAL PERCEP");

            Cell Celdasumap1 = sumapercepciones.createCell(2);
            Celdasumap1.setCellStyle(datosEstiloMoneda);
            Celdasumap1.setCellFormula("SUM(C11:C63)");

            Cell CeldaTotalDeducciones = sumapercepciones.createCell(8);
            CeldaTotalDeducciones.setCellStyle(datosEstilo);
            CeldaTotalDeducciones.setCellValue("TOTAL DEDUCCIONES");

            Cell Celdasumap2 = sumapercepciones.createCell(9);
            Celdasumap1.setCellStyle(datosEstiloMoneda);
            Celdasumap2.setCellFormula("SUM(J11:J63)");

            Cell CeldaTotalNeto = sumapercepciones.createCell(10);
            CeldaTotalNeto.setCellStyle(datosEstilo);
            CeldaTotalNeto.setCellValue("NETO");

            Cell Celdanetocaratula = sumapercepciones.createCell(11);
            Celdanetocaratula.setCellStyle(datosEstiloMoneda);
            Celdanetocaratula.setCellFormula("C67-J67");

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("CaratulaDeNomina" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformelistadohorizontal(Reporte reporte) {
        Connection con = getConexion();
        String sqlprueba = "SELECT empleado,concat(AP_PAT,' ',AP_MAT,' ',nombre)AS nombre,fechaold,rfc,curp,TIPO,Horas,hrsbase,hrslimit,hrsinter,zona,plantel AS planta,plantel AS plantaDESC,\n"
                + "plantel,plantel AS plantelDESC,empleado AS contrato,empleado AS contratodesc,nopuesto AS puesto,categoria AS puestodesc,TIPOPER AS tipoplaza,\n"
                + "P_01,P_01A,P_01B,P_01C,P_01D,	P_02,	P_03,	P_04,	P_05,	P_06,	P_07,	P_08,	P_09,	P_10,	P_11,	P_12,	P_13,	P_14,	P_15,	P_16,	P_17,	P_18,	P_19,	P_20,	P_21,	P_22,	P_23,	P_24,	P_25,	\n"
                + "P_26,	P_27,	P_28,	P_29,	P_30,	P_31,	P_32,	P_33,	P_34,	P_35,	P_36,	P_37,	P_38,	P_39,	P_40,	P_41,	P_42,	P_43,	P_44,	P_45,	P_46,	P_47,	P_48,	P_49,	P_50,P_PE,P_PG,\n"
                + "(P_01+P_02+	P_03+	P_04+	P_05+	P_06+	P_07+	P_08+	P_09+	P_10+	P_11+	P_12+	P_13+	P_14+	P_15+	P_16+	P_17+	P_18+	P_19+	P_20+	P_21+	P_22+	P_23+	P_24+	P_25+	P_26+	P_27+	P_28+	\n"
                + "P_29+	P_30+	P_31+	P_32+	P_33+	P_34+	P_35+	P_36+	P_37+	P_38+	P_39+	P_40+	P_41+	P_42+	P_43+	P_44+	P_45+	P_46+	P_47+	P_48+	P_49+	P_50+P_PE+P_PG+P_01A+P_01B+P_01C+P_01D)AS percepciones,\n"
                + "D_01,	D_02,	D_03,	D_04,	D_05,	D_06,	D_07,	D_08,	D_09,	D_10,	D_11,	D_12,	D_13,	D_14,	D_15,	D_16,	D_17,	D_18,	D_19,	D_20,	D_21,	D_22,	D_23,	D_24,	D_25,	D_26,	D_27,	D_28,	D_29,	D_30,	D_31,	\n"
                + "D_32,	D_33,	D_34,	D_35,	D_36,	D_37,	D_38,	D_39,	D_40,	D_41,	D_42,	D_43,	D_44,	D_45,	D_46,	D_47,	D_48,	D_49,	D_50,D_CB,\n"
                + "(D_01+	D_02+	D_03+	D_04+	D_05+	D_06+	D_07+	D_08+	D_09+	D_10+	D_11+	D_12+	D_13+	D_14+	D_15+	D_16+	D_17+	D_18+	D_19+	D_20+	D_21+	D_22+	D_23+	D_24+	D_25+	D_26+	D_27+	D_28+	D_29+	D_30+	\n"
                + "D_31+	D_32+	D_33+	D_34+	D_35+	D_36+	D_37+	D_38+	D_39+	D_40+	D_41+	D_42+	D_43+	D_44+	D_45+	D_46+	D_47+	D_48+	D_49+	D_50+D_CB)AS deducciones,\n"
                + "(P_01+P_02+	P_03+	P_04+	P_05+	P_06+	P_07+	P_08+	P_09+	P_10+	P_11+	P_12+	P_13+	P_14+	P_15+	P_16+	P_17+	P_18+	P_19+	P_20+	P_21+	P_22+	P_23+	P_24+	P_25+	P_26+	P_27+	P_28+	\n"
                + "P_29+	P_30+	P_31+	P_32+	P_33+	P_34+	P_35+	P_36+	P_37+	P_38+	P_39+	P_40+	P_41+	P_42+	P_43+	P_44+	P_45+	P_46+	P_47+	P_48+	P_49+	P_50+P_PE+P_PG+P_01A+P_01B+P_01C+P_01D)-\n"
                + "(D_01+	D_02+	D_03+	D_04+	D_05+	D_06+	D_07+	D_08+	D_09+	D_10+	D_11+	D_12+	D_13+	D_14+	D_15+	D_16+	D_17+	D_18+	D_19+	D_20+	D_21+	D_22+	D_23+	D_24+	D_25+	D_26+	D_27+	D_28+	D_29+	D_30+	\n"
                + "D_31+	D_32+	D_33+	D_34+	D_35+	D_36+	D_37+	D_38+	D_39+	D_40+	D_41+	D_42+	D_43+	D_44+	D_45+	D_46+	D_47+	D_48+	D_49+	D_50+D_CB) AS neto\n"
                + " FROM hojaisp WHERE numeroquincena = ? ";

        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("ListadoHorizonal");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);
        Row rowencabezado7 = hojareporte.createRow(6);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        row.createCell(1).setCellValue("EMPLEADO");
        row.createCell(2).setCellValue("EMPLEADO DESC");
        row.createCell(3).setCellValue("INGRESO");
        row.createCell(4).setCellValue("RFC");
        row.createCell(5).setCellValue("CURP");
        row.createCell(6).setCellValue("CLASE");
        row.createCell(7).setCellValue("HORAS");

        row.createCell(8).setCellValue("HORASBASe");
        row.createCell(9).setCellValue("HORASLIMITADA");
        row.createCell(10).setCellValue("HORASINTERINAS");
        row.createCell(11).setCellValue("ZONA");
        row.createCell(12).setCellValue("PLANTA");
        row.createCell(13).setCellValue("PLANTA DESC");
        row.createCell(14).setCellValue("DEPARTAMENTO");
        row.createCell(15).setCellValue("DEPARTAMENTO CONTRATO");
        row.createCell(16).setCellValue("CONTRATO");
        row.createCell(17).setCellValue("CONTRATO DESC");
        row.createCell(18).setCellValue("PUESTO");
        row.createCell(19).setCellValue("PUESTO DESC");
        row.createCell(20).setCellValue("TIPO PLAZA");
        row.createCell(21).setCellValue("P_01/Sueldo");
        row.createCell(22).setCellValue("P_01A/Sueldo SEMS");
        row.createCell(23).setCellValue("P_01B/Sueldo COBACH Base");
        row.createCell(24).setCellValue("P_01C/Sueldo COBACH Lim");
        row.createCell(25).setCellValue("P_01D/Sueldo COBACH Int");
        row.createCell(26).setCellValue("P_02/Retroactivo");
        row.createCell(27).setCellValue("P_03/Prima de Antiguedad");
        row.createCell(28).setCellValue("P_04/Despensa Administrativa");
        row.createCell(29).setCellValue("P_05/Despensa Docente 3-19 hrs");
        row.createCell(30).setCellValue("P_06/Material Didactico");
        row.createCell(31).setCellValue("P_07/Aguinaldo");
        row.createCell(32).setCellValue("P_08/Prima Vacacional");
        row.createCell(33).setCellValue("P_09/Devolucion Descto.Indebido");
        row.createCell(34).setCellValue("P_10/Devolución De Sueldos");
        row.createCell(35).setCellValue("P_11/Canastilla de Maternidad");
        row.createCell(36).setCellValue("P_12/Guarderia Administrativa");
        row.createCell(37).setCellValue("P_13/Estimulo Punt.y Asistencia");
        row.createCell(38).setCellValue("P_14/Subsidio para el Empleo");
        row.createCell(39).setCellValue("P_15/Pago de Lentes");
        row.createCell(40).setCellValue("P_16/Estimulo Productividad Admva.");
        row.createCell(41).setCellValue("P_17/Otras Percepciones");
        row.createCell(42).setCellValue("P_18/Diferencia de Sueldo Gravable");
        row.createCell(43).setCellValue("P_19/Asignación Docente");
        row.createCell(44).setCellValue("P_20/Dias Económicos no Disf.");
        row.createCell(45).setCellValue("P_21/Guarderia Docente 3-40 hrs");
        row.createCell(46).setCellValue("P_22/Despensa Docente 20-40 hrs");
        row.createCell(47).setCellValue("P_23/Apoyo Capacidades Diferentes");
        row.createCell(48).setCellValue("P_24/Estimulo Por Eficiencia Academica");
        row.createCell(49).setCellValue("P_25/Canastilla de Maternidad Docente");
        row.createCell(50).setCellValue("P_26/Apoyo para Transporte");
        row.createCell(51).setCellValue("P_27/Compensación Extraordinaria");
        row.createCell(52).setCellValue("P_28/Estimulo Personal Directivo");
        row.createCell(53).setCellValue("P_29/Productividad Adm/Doc");
        row.createCell(54).setCellValue("P_30/P_30");
        row.createCell(55).setCellValue("P_31/P_31");
        row.createCell(56).setCellValue("P_32/Despensa Docente EMSAD");
        row.createCell(57).setCellValue("P_33/Diferencia de Sueldo no grav");
        row.createCell(58).setCellValue("P_34/Bono de Fin de Año");
        row.createCell(59).setCellValue("P_35/Ayuda a la Economia Familiar");
        row.createCell(60).setCellValue("P_36/Estímulo Económico por Doctorado");
        row.createCell(61).setCellValue("P_37/Bono Del Servidor Público");
        row.createCell(62).setCellValue("P_38/Ajuste De Calendario");
        row.createCell(63).setCellValue("P_39/Prima de Antiguedad Ajuste De Calendario");
        row.createCell(64).setCellValue("P_40/Bono Del Día Del Maestro");
        row.createCell(65).setCellValue("P_41/Estimulo Por años de servicio");
        row.createCell(66).setCellValue("P_42/Subsidio para el empleo Acreditable");
        row.createCell(67).setCellValue("P_43/Apoyo CLIDDA");
        row.createCell(68).setCellValue("P_44/Apoyo para impresion de tesis");
        row.createCell(69).setCellValue("P_45/Apoyo por titulacion licenciatura");
        row.createCell(70).setCellValue("P_46/Apoyo por titulacion maestria");
        row.createCell(71).setCellValue("P_47/Apoyo Aparatos ortopedicos");
        row.createCell(72).setCellValue("P_48/Bono a las Madres");
        row.createCell(73).setCellValue("P_49/49");
        row.createCell(74).setCellValue("P_50/50");
        row.createCell(75).setCellValue("P_PE/Percepción Exenta");
        row.createCell(76).setCellValue("P_PG/Percepción Gravable");
        row.createCell(77).setCellValue("percepciones");
        row.createCell(78).setCellValue("D_01/Retencion de ISR");
        row.createCell(79).setCellValue("D_02/Inasistencias");
        row.createCell(80).setCellValue("D_03/Seguro Salud Trabajadores Acti");
        row.createCell(81).setCellValue("D_04/Cesantia Edad Avanzada y Vejez");
        row.createCell(82).setCellValue("D_05/Cuota Sindical SUTSOPEGM");
        row.createCell(83).setCellValue("D_06/Seguro de Daños Fovissste");
        row.createCell(84).setCellValue("D_07/Prestamo Corto Plazo ISSSTE");
        row.createCell(85).setCellValue("D_08/Prestamo Caja De Ahorro SUSPEG");
        row.createCell(86).setCellValue("D_09/Prestamo Hipotecario FOVISSSTE");
        row.createCell(87).setCellValue("D_10/Pension Alimenticia");
        row.createCell(88).setCellValue("D_11/Cuota Sindical SUTCOBACH");
        row.createCell(89).setCellValue("D_12/Caja de Ahorro Sindical");
        row.createCell(90).setCellValue("D_13/Descuento Pago Indebido");
        row.createCell(91).setCellValue("D_14/Prestamo Caja De Ahorro SUTCOBACH");
        row.createCell(92).setCellValue("D_15/Ayuda Mutua");
        row.createCell(93).setCellValue("D_16/Otras Deducciónes");
        row.createCell(94).setCellValue("D_17/Anticipo de Sueldo");
        row.createCell(95).setCellValue("D_18/Ayuda Mutua SECCION XXXI");
        row.createCell(96).setCellValue("D_19/Cuota Sindical SITCOBACH");
        row.createCell(97).setCellValue("D_20/Seguros New York Life");
        row.createCell(98).setCellValue("D_21/Seguro Salud Pensionado ISSSTE");
        row.createCell(99).setCellValue("D_22/Seguro Inválidez y Vida ISSSTE");
        row.createCell(100).setCellValue("D_23/Serv. Soc. Cult. ISSSTE");
        row.createCell(101).setCellValue("D_24/Ahorro Solidario al ISSSTE");
        row.createCell(102).setCellValue("D_25/Fondo de Inversion Ahorro para el retiro");
        row.createCell(103).setCellValue("D_26/Prestamo Inversión Ahorro");
        row.createCell(104).setCellValue("D_27/Caja De Ahorro Sec. COBACH");
        row.createCell(105).setCellValue("D_28/Prestamo Caja De Ahorro Sec. COBACH");
        row.createCell(106).setCellValue("D_29/Cuota Sindical Seccion LXIII");
        row.createCell(107).setCellValue("D_30/Caja de Ahorro seccion LXIII");
        row.createCell(108).setCellValue("D_31/Prestamo Caja de Ahorro seccion LXIII");
        row.createCell(109).setCellValue("D_32/Fondo De Inversión Sección Cobach");
        row.createCell(110).setCellValue("D_33/Préstamo De Inversión Sección Cobach");
        row.createCell(111).setCellValue("D_34/Caja de Ahorro SITCOBACH");
        row.createCell(112).setCellValue("D_35/Préstamo Caja de Ahorro SITCOBACH");
        row.createCell(113).setCellValue("D_36/Ayuda Mutua SITCOBACH");
        row.createCell(114).setCellValue("D_37/Seguro ARGOS");
        row.createCell(115).setCellValue("D_38/Ayuda Mutua Seccion LXIII");
        row.createCell(116).setCellValue("D_39/Seguros Metlife");
        row.createCell(117).setCellValue("D_40/Prestamo Fonsar");
        row.createCell(118).setCellValue("D_41/Fondo Solidario de Ahorro para el Retiro");
        row.createCell(119).setCellValue("D_42/Prestamos Respalda 2M");
        row.createCell(120).setCellValue("D_43/Fondo de Inversion de Ahorro para la Jubilacion");
        row.createCell(121).setCellValue("D_44/Prestamos Fondo de Inversion de Ahorro para la Jubilacion");
        row.createCell(122).setCellValue("D_45/Caja de Ahorro Escolar Seccion XXXI");
        row.createCell(123).setCellValue("D_46/Prestamos Caja De Ahorro Escolar Seccion XXXI");
        row.createCell(124).setCellValue("D_47/Fondo De  AHorro Solidario De Inversion");
        row.createCell(125).setCellValue("D_48/Prestamos Fondo de Ahorro Solidario De Inversion");
        row.createCell(126).setCellValue("D_49/Prestamos Fondo De Garantia Fiar");
        row.createCell(127).setCellValue("D_50/Prestamos Fondo de Garantia Fonsar");
        row.createCell(128).setCellValue("D_CB/Prestamo Sefi");
        row.createCell(129).setCellValue("DEDUCCION");
        row.createCell(130).setCellValue("TOTAL");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;
        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 1; a <= numCol; a++) {

                    String columnValue = rstrabajadorescotizantez.getString(a);
                    Cell Celda1 = filaDatosCuotasYAportaciones.createCell(a);
                    Celda1.setCellStyle(datosEstilo);
                    Celda1.setCellValue(columnValue);

                }
                numFilaDatosCuotasYAportaciones++;

            }
            /*int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);
            Row sumapercepciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 3);
            Row cuotasisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 4);
            Row fovisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 5);
            Row aportacionisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 6);
                
                    Cell Celdasumas = sumapercepciones.createCell(0);
                    Celdasumas.setCellStyle(datosEstilo);
                    Celdasumas.setCellValue("TOTAL");
                   
                    Cell Celdasconteopensiones = sumapercepciones.createCell(1);
                    Celdasconteopensiones.setCellStyle(datosEstilo);
                    Celdasconteopensiones.setCellValue(numFilaDatosCuotasYAportaciones-10);
                    
                    
                    Cell Celdasumap1 = sumapercepciones.createCell(4);
                    Celdasumap1.setCellStyle(datosEstilo);
                    Celdasumap1.setCellFormula("SUM(E10:E"+numFilaDatosCuotasYAportaciones+")");
                    
                    Cell Celdasumap2 = sumapercepciones.createCell(6);
                    Celdasumap2.setCellStyle(datosEstilo);
                    Celdasumap2.setCellFormula("SUM(G10:G"+numFilaDatosCuotasYAportaciones+")");*/

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("ListadoHorizontal" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void exportarinformehojai(Reporte reporte) {
        Connection connota = getConexionnota();
       String sqlprueba = "SELECT * FROM hojaicp WHERE numeroquincena = ? ";

        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("HojaiCP");
        Row row = hojareporte.createRow(1);
        String columnValue;
        int valorColumna;
        float valorColumnaFloat;
        long  valorCedulaImss;

        row.createCell(1).setCellValue("RFC");
        row.createCell(2).setCellValue("EMPLEADO ");
        row.createCell(3).setCellValue("PERIODO");
        row.createCell(4).setCellValue("CLAVE MVTO");
        row.createCell(5).setCellValue("P_01");
        row.createCell(6).setCellValue("P_02");
        row.createCell(7).setCellValue("P_03");

        row.createCell(8).setCellValue("P_04");
        row.createCell(9).setCellValue("P_05");
        row.createCell(10).setCellValue("P_06");
        row.createCell(11).setCellValue("P_07");
        row.createCell(12).setCellValue("P_08");
        row.createCell(13).setCellValue("P_09");
        row.createCell(14).setCellValue("P_10");
        row.createCell(15).setCellValue("P_11");
        row.createCell(16).setCellValue("P_12");
        row.createCell(17).setCellValue("P_13");
        row.createCell(18).setCellValue("P_14");
        row.createCell(19).setCellValue("P_15");
        row.createCell(20).setCellValue("P_16");
        row.createCell(21).setCellValue("P_17");
        row.createCell(22).setCellValue("P_18");
        row.createCell(23).setCellValue("P_19");
        row.createCell(24).setCellValue("P_20");
        row.createCell(25).setCellValue("P_21");
        row.createCell(26).setCellValue("P_22");
        row.createCell(27).setCellValue("P_23");
        row.createCell(28).setCellValue("P_24");
        row.createCell(29).setCellValue("P_25");
        row.createCell(30).setCellValue("P_26");
        row.createCell(31).setCellValue("P_27");
        row.createCell(32).setCellValue("P_28");
        row.createCell(33).setCellValue("P_29");
        row.createCell(34).setCellValue("P_30");
        row.createCell(35).setCellValue("D_01");
        row.createCell(36).setCellValue("D_10");
        row.createCell(37).setCellValue("P_D1");
        row.createCell(38).setCellValue("P_C1");
        row.createCell(39).setCellValue("P_C2");
        row.createCell(40).setCellValue("P_31");
        row.createCell(41).setCellValue("P_32");
        row.createCell(42).setCellValue("P_33");
        row.createCell(43).setCellValue("D_CB");
        row.createCell(44).setCellValue("D_02");
        row.createCell(45).setCellValue("D_03");
        row.createCell(46).setCellValue("D_04");
        row.createCell(47).setCellValue("D_05");
        row.createCell(48).setCellValue("D_06");
        row.createCell(49).setCellValue("D_07");
        row.createCell(50).setCellValue("D_08");
        row.createCell(51).setCellValue("D_09");
        row.createCell(52).setCellValue("D_11");
        row.createCell(53).setCellValue("D_12");
        row.createCell(54).setCellValue("D_13");
        row.createCell(55).setCellValue("D_14");
        row.createCell(56).setCellValue("D_15");
        row.createCell(57).setCellValue("D_16");
        row.createCell(58).setCellValue("D_17");
        row.createCell(59).setCellValue("D_18");
        row.createCell(60).setCellValue("D_19");
        row.createCell(61).setCellValue("D_20");
        row.createCell(62).setCellValue("D_21");
        row.createCell(63).setCellValue("D_22");
        row.createCell(64).setCellValue("D_23");
        row.createCell(65).setCellValue("D_24");
        row.createCell(66).setCellValue("D_25");
        row.createCell(67).setCellValue("D_26");
        row.createCell(68).setCellValue("D_27");
        row.createCell(69).setCellValue("D_28");
        row.createCell(70).setCellValue("P_MC");
        row.createCell(71).setCellValue("P_PG");
        row.createCell(72).setCellValue("P_PE");
        row.createCell(73).setCellValue("P_36");
        row.createCell(74).setCellValue("D_29");
        row.createCell(75).setCellValue("P_37");
        row.createCell(76).setCellValue("D_30");
        row.createCell(77).setCellValue("D_31");
        row.createCell(78).setCellValue("P_35");
        row.createCell(79).setCellValue("P_38");
        row.createCell(80).setCellValue("P_39");
        row.createCell(81).setCellValue("D_32");
        row.createCell(82).setCellValue("D_33");
        row.createCell(83).setCellValue("D_34");
        row.createCell(84).setCellValue("D_35");
        row.createCell(85).setCellValue("P_40");
        row.createCell(86).setCellValue("D_36");
        row.createCell(87).setCellValue("D_37");
        row.createCell(88).setCellValue("P_01A");
        row.createCell(89).setCellValue("P_01B");
        row.createCell(90).setCellValue("P_01C");
        row.createCell(91).setCellValue("P_01D");
        row.createCell(92).setCellValue("P_01E");
        row.createCell(93).setCellValue("P_34");
        row.createCell(94).setCellValue("D_38");
        row.createCell(95).setCellValue("P_41");
        row.createCell(96).setCellValue("P_42");
        row.createCell(97).setCellValue("P_43");
        row.createCell(98).setCellValue("P_44");
        row.createCell(99).setCellValue("P_45");
        row.createCell(100).setCellValue("P_46");
        row.createCell(101).setCellValue("P_47");
        row.createCell(102).setCellValue("D_39");
        row.createCell(103).setCellValue("D_40");
        row.createCell(104).setCellValue("D_41");
        row.createCell(105).setCellValue("D_42");
        row.createCell(106).setCellValue("D_01A");
        row.createCell(107).setCellValue("D_43");
        row.createCell(108).setCellValue("D_44");
        row.createCell(109).setCellValue("D_45");
        row.createCell(110).setCellValue("D_46");
        row.createCell(111).setCellValue("D_47");
        row.createCell(112).setCellValue("D_48");
        row.createCell(113).setCellValue("D_49");
        row.createCell(114).setCellValue("D_50");
        row.createCell(115).setCellValue("TIPO");
        row.createCell(116).setCellValue("QUINCENA");
        row.createCell(117).setCellValue("LAPSO");
        row.createCell(118).setCellValue("PLANTEL");
        row.createCell(119).setCellValue("NOPUESTO");
        row.createCell(120).setCellValue("LEIDO");
        row.createCell(121).setCellValue("AP_PAT");
        row.createCell(122).setCellValue("AP_MAT");
        row.createCell(123).setCellValue("NOMBRE");
        row.createCell(124).setCellValue("CATEGORIA");
        row.createCell(125).setCellValue("HORAS");
        row.createCell(126).setCellValue("NIVEL");
        row.createCell(127).setCellValue("FECHAOLD");
        row.createCell(128).setCellValue("TIPOPER");
        row.createCell(129).setCellValue("ZONA");
        row.createCell(130).setCellValue("SEMESTRE");
        row.createCell(131).setCellValue("TPLANTEL");
        row.createCell(132).setCellValue("TIPOSIN");
        row.createCell(133).setCellValue("MUTUAL");
        row.createCell(134).setCellValue("FACORE");
        row.createCell(135).setCellValue("CEDULAIMSS");
        row.createCell(136).setCellValue("REGIMENPENSIONARIO");

       
        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement pshojai = null;
        ResultSet rs = null;
        ResultSet rshojai = null;
        int numFilaDatosHojai = 2;
        try {
            pshojai = connota.prepareStatement(sqlprueba);
            pshojai.setString(1, reporte.getPeriodo());
            rshojai = pshojai.executeQuery();

            int numCol = rshojai.getMetaData().getColumnCount();

            while (rshojai.next()) {

                Row filaDatosHojai = hojareporte.createRow(numFilaDatosHojai);
                for (int a = 1; a <= numCol; a++) {

                    
                    if (a == 2 || a == 3 || a == 116 || a == 119 || a == 125
                         || a == 126 || a == 129 || a == 130 || a == 132 || a == 133   ){
                     
                     valorColumna = rshojai.getInt(a);
                    Cell Celda1 = filaDatosHojai.createCell(a);
                    Celda1.setCellStyle(datosEstilo);
                    Celda1.setCellValue(valorColumna);
  
                     }else if (a >= 5 &&  a <= 114  ){
                     
                     valorColumnaFloat = rshojai.getFloat(a);
                    Cell Celda1 = filaDatosHojai.createCell(a);
                    Celda1.setCellStyle(datosEstilo);
                    Celda1.setCellValue(valorColumnaFloat);
    
                     
                     }else if (a == 134 ){
                     
                     valorColumnaFloat = rshojai.getFloat(a);
                    Cell Celda1 = filaDatosHojai.createCell(a);
                    Celda1.setCellStyle(datosEstilo);
                    Celda1.setCellValue(valorColumnaFloat);
    
                     
                     }
                     else if (a == 137 || a == 138 ){
                     
                     }else if (a == 135 ){
                    valorCedulaImss = rshojai.getLong(a);
                    Cell Celda1 = filaDatosHojai.createCell(a);
                    Celda1.setCellStyle(datosEstilo);
                    Celda1.setCellValue(valorCedulaImss);
  
                     }
                    
                     else
                     {
                    columnValue = rshojai.getString(a);
                    Cell Celda1 = filaDatosHojai.createCell(a);
                    Celda1.setCellStyle(datosEstilo);
                    Celda1.setCellValue(columnValue);
  
                     }
                   
                }
                numFilaDatosHojai++;

            }
            

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("HojaiCP" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void exportarinformeEstadoDeOrigen(Reporte reporte) {
        Connection connota = getConexionnota();
        String nombreCampo;
       
        /////<editor-fold desc=" EMSAD SIN PLAN">
        String sqlemsadsp = "SELECT SUM(P_01)   AS   P01,\n" + "SUM(P_01A)   AS   P_01A,\n"
               + "SUM(P_01B)   AS   P_01B,\n"
               + "SUM(P_01C)   AS   P_01C,\n"
               + "SUM(P_01D)   AS   P_01D,\n"
               + "SUM(P_01E)   AS   P_01E,\n"
               + "SUM(P_18)   AS   P18,\n"
               + "SUM(P_19)   AS   P19,\n"
               + "SUM(P_02)   AS   P02,\n"
               + "SUM(P_03)   AS   P03,\n"
               + "SUM(P_04)   AS   P04,\n"
               + "SUM(P_05)   AS   P05,\n"
               + "SUM(P_06)   AS   P06,\n"
               + "SUM(P_07)   AS   P07,\n"
               + "SUM(P_08)   AS   P08,\n"
               + "SUM(P_09)   AS   P09,\n"
               + "SUM(P_10)   AS   P10,\n"
               + "SUM(P_11)   AS   P11,\n"
               + "SUM(P_12)   AS   P12,\n"
               + "SUM(P_13)   AS   P13,\n"
               + "SUM(P_14)   AS   P14,\n"
               + "SUM(P_15)   AS   P15,\n"
               + "SUM(P_16)   AS   P16,\n"
               + "SUM(P_17)   AS   P17,\n"
               + "SUM(P_20)   AS   P20,\n"
               + "SUM(P_21)   AS   P21,\n"
               + "SUM(P_22)   AS   P22,\n"
               + "SUM(P_23)   AS   P23,\n"
               + "SUM(P_24)   AS   P24,\n"
               + "SUM(P_25)   AS   P25,\n"
               + "SUM(P_26)   AS   P26,\n"
               + "SUM(P_27)   AS   P27,\n"
               + "SUM(P_28)   AS   P28,\n"
               + "SUM(P_29)   AS   P29,\n"
               + "SUM(P_30)   AS   P30,\n"
               + "SUM(P_31)   AS   P31,\n"
               + "SUM(P_32)   AS   P32,\n"
               + "SUM(P_33)   AS   P33,\n"
               + "SUM(P_34)   AS   P34,\n"
               + "SUM(P_35)   AS   P35,\n"
               + "SUM(P_36)   AS   P36,\n"
               + "SUM(P_37)   AS   P37,\n"
               + "SUM(P_38)   AS   P38,\n"
               + "SUM(P_39)   AS   P39,\n"
               + "SUM(P_40)   AS   P40,\n"
               + "SUM(P_41)   AS   P41,\n"
               + "SUM(P_42)   AS   P42,\n"
               + "SUM(P_43)   AS   P43,\n"
               + "SUM(P_44)   AS   P44,\n"
               + "SUM(P_45)   AS   P45,\n"
               + "SUM(P_46)   AS   P46,\n"
               + "SUM(P_47)   AS   P47,\n"
               + "SUM(P_PE)   AS   PPE,\n"
               + "SUM(P_PG)   AS   PPG,\n"
               + "SUM(D_01)   AS   D01,\n"
               + "SUM(D_02)   AS   D02,\n"
               + "SUM(D_03)   AS   D03,\n"
               + "SUM(D_04)   AS   D04,\n"
               + "SUM(D_05)   AS   D05,\n"
               + "SUM(D_06)   AS   D06,\n"
               + "SUM(D_07)   AS   D07,\n"
               + "SUM(D_08)   AS   D08,\n"
               + "SUM(D_09)   AS   D09,\n"
               + "SUM(D_10)   AS   D10,\n"
               + "SUM(D_11)   AS   D11,\n"
               + "SUM(D_12)   AS   D12,\n"
               + "SUM(D_13)   AS   D13,\n"
               + "SUM(D_14)   AS   D14,\n"
               + "SUM(D_15)   AS   D15,\n"
               + "SUM(D_16)   AS   D16,\n"
               + "SUM(D_17)   AS   D17,\n"
               + "SUM(D_18)   AS   D18,\n"
               + "SUM(D_19)   AS   D19,\n"
               + "SUM(D_20)   AS   D20,\n"
               + "SUM(D_21)   AS   D21,\n"
               + "SUM(D_22)   AS   D22,\n"
               + "SUM(D_23)   AS   D23,\n"
               + "SUM(D_24)   AS   D24,\n"
               + "SUM(D_25)   AS   D25,\n"
               + "SUM(D_26)   AS   D26,\n"
               + "SUM(D_27)   AS   D27,\n"
               + "SUM(D_28)   AS   D28,\n"
               + "SUM(D_29)   AS   D29,\n"
               + "SUM(D_30)   AS   D30,\n"
               + "SUM(D_31)   AS   D31,\n"
               + "SUM(D_32)   AS   D32,\n"
               + "SUM(D_33)   AS   D33,\n"
               + "SUM(D_34)   AS   D34,\n"
               + "SUM(D_35)   AS   D35,\n"
               + "SUM(D_36)   AS   D36,\n"
               + "SUM(D_37)   AS   D37,\n"
               + "SUM(D_38)   AS   D38,\n"
               + "SUM(D_39)   AS   D39,\n"
               + "SUM(D_40)   AS   D40,\n"
               + "SUM(D_41)   AS   D41,\n"
               + "SUM(D_42)   AS   D42,\n"
               + "SUM(D_CB)   AS   DCB FROM hojai WHERE NUMEROQUINCENA = ? AND TPLANTEL = 'EMSAD'";
        
        /////</editor-fold>
        /////
        
        /////<editor-fold desc="consulta EMSAD CON PLAN">
        String sqlemsadcp = "SELECT SUM(P_01)   AS   P01,\n" + "SUM(P_01A)   AS   P_01A,\n"
               + "SUM(P_01B)   AS   P_01B,\n"
               + "SUM(P_01C)   AS   P_01C,\n"
               + "SUM(P_01D)   AS   P_01D,\n"
               + "SUM(P_01E)   AS   P_01E,\n"
               + "SUM(P_18)   AS   P18,\n"
               + "SUM(P_19)   AS   P19,\n"
               + "SUM(P_02)   AS   P02,\n"
               + "SUM(P_03)   AS   P03,\n"
               + "SUM(P_04)   AS   P04,\n"
               + "SUM(P_05)   AS   P05,\n"
               + "SUM(P_06)   AS   P06,\n"
               + "SUM(P_07)   AS   P07,\n"
               + "SUM(P_08)   AS   P08,\n"
               + "SUM(P_09)   AS   P09,\n"
               + "SUM(P_10)   AS   P10,\n"
               + "SUM(P_11)   AS   P11,\n"
               + "SUM(P_12)   AS   P12,\n"
               + "SUM(P_13)   AS   P13,\n"
               + "SUM(P_14)   AS   P14,\n"
               + "SUM(P_15)   AS   P15,\n"
               + "SUM(P_16)   AS   P16,\n"
               + "SUM(P_17)   AS   P17,\n"
               + "SUM(P_20)   AS   P20,\n"
               + "SUM(P_21)   AS   P21,\n"
               + "SUM(P_22)   AS   P22,\n"
               + "SUM(P_23)   AS   P23,\n"
               + "SUM(P_24)   AS   P24,\n"
               + "SUM(P_25)   AS   P25,\n"
               + "SUM(P_26)   AS   P26,\n"
               + "SUM(P_27)   AS   P27,\n"
               + "SUM(P_28)   AS   P28,\n"
               + "SUM(P_29)   AS   P29,\n"
               + "SUM(P_30)   AS   P30,\n"
               + "SUM(P_31)   AS   P31,\n"
               + "SUM(P_32)   AS   P32,\n"
               + "SUM(P_33)   AS   P33,\n"
               + "SUM(P_34)   AS   P34,\n"
               + "SUM(P_35)   AS   P35,\n"
               + "SUM(P_36)   AS   P36,\n"
               + "SUM(P_37)   AS   P37,\n"
               + "SUM(P_38)   AS   P38,\n"
               + "SUM(P_39)   AS   P39,\n"
               + "SUM(P_40)   AS   P40,\n"
               + "SUM(P_41)   AS   P41,\n"
               + "SUM(P_42)   AS   P42,\n"
               + "SUM(P_43)   AS   P43,\n"
               + "SUM(P_44)   AS   P44,\n"
               + "SUM(P_45)   AS   P45,\n"
               + "SUM(P_46)   AS   P46,\n"
               + "SUM(P_47)   AS   P47,\n"
               + "SUM(P_PE)   AS   PPE,\n"
               + "SUM(P_PG)   AS   PPG,\n"
               + "SUM(D_01)   AS   D01,\n"
               + "SUM(D_02)   AS   D02,\n"
               + "SUM(D_03)   AS   D03,\n"
               + "SUM(D_04)   AS   D04,\n"
               + "SUM(D_05)   AS   D05,\n"
               + "SUM(D_06)   AS   D06,\n"
               + "SUM(D_07)   AS   D07,\n"
               + "SUM(D_08)   AS   D08,\n"
               + "SUM(D_09)   AS   D09,\n"
               + "SUM(D_10)   AS   D10,\n"
               + "SUM(D_11)   AS   D11,\n"
               + "SUM(D_12)   AS   D12,\n"
               + "SUM(D_13)   AS   D13,\n"
               + "SUM(D_14)   AS   D14,\n"
               + "SUM(D_15)   AS   D15,\n"
               + "SUM(D_16)   AS   D16,\n"
               + "SUM(D_17)   AS   D17,\n"
               + "SUM(D_18)   AS   D18,\n"
               + "SUM(D_19)   AS   D19,\n"
               + "SUM(D_20)   AS   D20,\n"
               + "SUM(D_21)   AS   D21,\n"
               + "SUM(D_22)   AS   D22,\n"
               + "SUM(D_23)   AS   D23,\n"
               + "SUM(D_24)   AS   D24,\n"
               + "SUM(D_25)   AS   D25,\n"
               + "SUM(D_26)   AS   D26,\n"
               + "SUM(D_27)   AS   D27,\n"
               + "SUM(D_28)   AS   D28,\n"
               + "SUM(D_29)   AS   D29,\n"
               + "SUM(D_30)   AS   D30,\n"
               + "SUM(D_31)   AS   D31,\n"
               + "SUM(D_32)   AS   D32,\n"
               + "SUM(D_33)   AS   D33,\n"
               + "SUM(D_34)   AS   D34,\n"
               + "SUM(D_35)   AS   D35,\n"
               + "SUM(D_36)   AS   D36,\n"
               + "SUM(D_37)   AS   D37,\n"
               + "SUM(D_38)   AS   D38,\n"
               + "SUM(D_39)   AS   D39,\n"
               + "SUM(D_40)   AS   D40,\n"
               + "SUM(D_41)   AS   D41,\n"
               + "SUM(D_42)   AS   D42,\n"
               + "SUM(D_CB)   AS   DCB FROM hojaicp WHERE NUMEROQUINCENA = ? AND TPLANTEL = 'EMSAD'";
        
        /////</editor-fold>
        /////
        /////<editor-fold desc="consulta COOPERACION SIN PLAN">
        String sqlcooperacionsp = "SELECT SUM(P_01)   AS   P01,\n" + "SUM(P_01A)   AS   P_01A,\n"
               + "SUM(P_01B)   AS   P_01B,\n"
               + "SUM(P_01C)   AS   P_01C,\n"
               + "SUM(P_01D)   AS   P_01D,\n"
               + "SUM(P_01E)   AS   P_01E,\n"
               + "SUM(P_18)   AS   P18,\n"
               + "SUM(P_19)   AS   P19,\n"
               + "SUM(P_02)   AS   P02,\n"
               + "SUM(P_03)   AS   P03,\n"
               + "SUM(P_04)   AS   P04,\n"
               + "SUM(P_05)   AS   P05,\n"
               + "SUM(P_06)   AS   P06,\n"
               + "SUM(P_07)   AS   P07,\n"
               + "SUM(P_08)   AS   P08,\n"
               + "SUM(P_09)   AS   P09,\n"
               + "SUM(P_10)   AS   P10,\n"
               + "SUM(P_11)   AS   P11,\n"
               + "SUM(P_12)   AS   P12,\n"
               + "SUM(P_13)   AS   P13,\n"
               + "SUM(P_14)   AS   P14,\n"
               + "SUM(P_15)   AS   P15,\n"
               + "SUM(P_16)   AS   P16,\n"
               + "SUM(P_17)   AS   P17,\n"
               + "SUM(P_20)   AS   P20,\n"
               + "SUM(P_21)   AS   P21,\n"
               + "SUM(P_22)   AS   P22,\n"
               + "SUM(P_23)   AS   P23,\n"
               + "SUM(P_24)   AS   P24,\n"
               + "SUM(P_25)   AS   P25,\n"
               + "SUM(P_26)   AS   P26,\n"
               + "SUM(P_27)   AS   P27,\n"
               + "SUM(P_28)   AS   P28,\n"
               + "SUM(P_29)   AS   P29,\n"
               + "SUM(P_30)   AS   P30,\n"
               + "SUM(P_31)   AS   P31,\n"
               + "SUM(P_32)   AS   P32,\n"
               + "SUM(P_33)   AS   P33,\n"
               + "SUM(P_34)   AS   P34,\n"
               + "SUM(P_35)   AS   P35,\n"
               + "SUM(P_36)   AS   P36,\n"
               + "SUM(P_37)   AS   P37,\n"
               + "SUM(P_38)   AS   P38,\n"
               + "SUM(P_39)   AS   P39,\n"
               + "SUM(P_40)   AS   P40,\n"
               + "SUM(P_41)   AS   P41,\n"
               + "SUM(P_42)   AS   P42,\n"
               + "SUM(P_43)   AS   P43,\n"
               + "SUM(P_44)   AS   P44,\n"
               + "SUM(P_45)   AS   P45,\n"
               + "SUM(P_46)   AS   P46,\n"
               + "SUM(P_47)   AS   P47,\n"
               + "SUM(P_PE)   AS   PPE,\n"
               + "SUM(P_PG)   AS   PPG,\n"
               + "SUM(D_01)   AS   D01,\n"
               + "SUM(D_02)   AS   D02,\n"
               + "SUM(D_03)   AS   D03,\n"
               + "SUM(D_04)   AS   D04,\n"
               + "SUM(D_05)   AS   D05,\n"
               + "SUM(D_06)   AS   D06,\n"
               + "SUM(D_07)   AS   D07,\n"
               + "SUM(D_08)   AS   D08,\n"
               + "SUM(D_09)   AS   D09,\n"
               + "SUM(D_10)   AS   D10,\n"
               + "SUM(D_11)   AS   D11,\n"
               + "SUM(D_12)   AS   D12,\n"
               + "SUM(D_13)   AS   D13,\n"
               + "SUM(D_14)   AS   D14,\n"
               + "SUM(D_15)   AS   D15,\n"
               + "SUM(D_16)   AS   D16,\n"
               + "SUM(D_17)   AS   D17,\n"
               + "SUM(D_18)   AS   D18,\n"
               + "SUM(D_19)   AS   D19,\n"
               + "SUM(D_20)   AS   D20,\n"
               + "SUM(D_21)   AS   D21,\n"
               + "SUM(D_22)   AS   D22,\n"
               + "SUM(D_23)   AS   D23,\n"
               + "SUM(D_24)   AS   D24,\n"
               + "SUM(D_25)   AS   D25,\n"
               + "SUM(D_26)   AS   D26,\n"
               + "SUM(D_27)   AS   D27,\n"
               + "SUM(D_28)   AS   D28,\n"
               + "SUM(D_29)   AS   D29,\n"
               + "SUM(D_30)   AS   D30,\n"
               + "SUM(D_31)   AS   D31,\n"
               + "SUM(D_32)   AS   D32,\n"
               + "SUM(D_33)   AS   D33,\n"
               + "SUM(D_34)   AS   D34,\n"
               + "SUM(D_35)   AS   D35,\n"
               + "SUM(D_36)   AS   D36,\n"
               + "SUM(D_37)   AS   D37,\n"
               + "SUM(D_38)   AS   D38,\n"
               + "SUM(D_39)   AS   D39,\n"
               + "SUM(D_40)   AS   D40,\n"
               + "SUM(D_41)   AS   D41,\n"
               + "SUM(D_42)   AS   D42,\n"
               + "SUM(D_CB)   AS   DCB FROM hojai WHERE NUMEROQUINCENA = ? AND TPLANTEL = 'COOPERACION'";
        
        /////</editor-fold>
        
        /////<editor-fold desc="consulta COOPERACION CON PLAN">
        String sqlcooperacioncp = "SELECT SUM(P_01)   AS   P01,\n" + "SUM(P_01A)   AS   P_01A,\n"
               + "SUM(P_01B)   AS   P_01B,\n"
               + "SUM(P_01C)   AS   P_01C,\n"
               + "SUM(P_01D)   AS   P_01D,\n"
               + "SUM(P_01E)   AS   P_01E,\n"
               + "SUM(P_18)   AS   P18,\n"
               + "SUM(P_19)   AS   P19,\n"
               + "SUM(P_02)   AS   P02,\n"
               + "SUM(P_03)   AS   P03,\n"
               + "SUM(P_04)   AS   P04,\n"
               + "SUM(P_05)   AS   P05,\n"
               + "SUM(P_06)   AS   P06,\n"
               + "SUM(P_07)   AS   P07,\n"
               + "SUM(P_08)   AS   P08,\n"
               + "SUM(P_09)   AS   P09,\n"
               + "SUM(P_10)   AS   P10,\n"
               + "SUM(P_11)   AS   P11,\n"
               + "SUM(P_12)   AS   P12,\n"
               + "SUM(P_13)   AS   P13,\n"
               + "SUM(P_14)   AS   P14,\n"
               + "SUM(P_15)   AS   P15,\n"
               + "SUM(P_16)   AS   P16,\n"
               + "SUM(P_17)   AS   P17,\n"
               + "SUM(P_20)   AS   P20,\n"
               + "SUM(P_21)   AS   P21,\n"
               + "SUM(P_22)   AS   P22,\n"
               + "SUM(P_23)   AS   P23,\n"
               + "SUM(P_24)   AS   P24,\n"
               + "SUM(P_25)   AS   P25,\n"
               + "SUM(P_26)   AS   P26,\n"
               + "SUM(P_27)   AS   P27,\n"
               + "SUM(P_28)   AS   P28,\n"
               + "SUM(P_29)   AS   P29,\n"
               + "SUM(P_30)   AS   P30,\n"
               + "SUM(P_31)   AS   P31,\n"
               + "SUM(P_32)   AS   P32,\n"
               + "SUM(P_33)   AS   P33,\n"
               + "SUM(P_34)   AS   P34,\n"
               + "SUM(P_35)   AS   P35,\n"
               + "SUM(P_36)   AS   P36,\n"
               + "SUM(P_37)   AS   P37,\n"
               + "SUM(P_38)   AS   P38,\n"
               + "SUM(P_39)   AS   P39,\n"
               + "SUM(P_40)   AS   P40,\n"
               + "SUM(P_41)   AS   P41,\n"
               + "SUM(P_42)   AS   P42,\n"
               + "SUM(P_43)   AS   P43,\n"
               + "SUM(P_44)   AS   P44,\n"
               + "SUM(P_45)   AS   P45,\n"
               + "SUM(P_46)   AS   P46,\n"
               + "SUM(P_47)   AS   P47,\n"
               + "SUM(P_PE)   AS   PPE,\n"
               + "SUM(P_PG)   AS   PPG,\n"
               + "SUM(D_01)   AS   D01,\n"
               + "SUM(D_02)   AS   D02,\n"
               + "SUM(D_03)   AS   D03,\n"
               + "SUM(D_04)   AS   D04,\n"
               + "SUM(D_05)   AS   D05,\n"
               + "SUM(D_06)   AS   D06,\n"
               + "SUM(D_07)   AS   D07,\n"
               + "SUM(D_08)   AS   D08,\n"
               + "SUM(D_09)   AS   D09,\n"
               + "SUM(D_10)   AS   D10,\n"
               + "SUM(D_11)   AS   D11,\n"
               + "SUM(D_12)   AS   D12,\n"
               + "SUM(D_13)   AS   D13,\n"
               + "SUM(D_14)   AS   D14,\n"
               + "SUM(D_15)   AS   D15,\n"
               + "SUM(D_16)   AS   D16,\n"
               + "SUM(D_17)   AS   D17,\n"
               + "SUM(D_18)   AS   D18,\n"
               + "SUM(D_19)   AS   D19,\n"
               + "SUM(D_20)   AS   D20,\n"
               + "SUM(D_21)   AS   D21,\n"
               + "SUM(D_22)   AS   D22,\n"
               + "SUM(D_23)   AS   D23,\n"
               + "SUM(D_24)   AS   D24,\n"
               + "SUM(D_25)   AS   D25,\n"
               + "SUM(D_26)   AS   D26,\n"
               + "SUM(D_27)   AS   D27,\n"
               + "SUM(D_28)   AS   D28,\n"
               + "SUM(D_29)   AS   D29,\n"
               + "SUM(D_30)   AS   D30,\n"
               + "SUM(D_31)   AS   D31,\n"
               + "SUM(D_32)   AS   D32,\n"
               + "SUM(D_33)   AS   D33,\n"
               + "SUM(D_34)   AS   D34,\n"
               + "SUM(D_35)   AS   D35,\n"
               + "SUM(D_36)   AS   D36,\n"
               + "SUM(D_37)   AS   D37,\n"
               + "SUM(D_38)   AS   D38,\n"
               + "SUM(D_39)   AS   D39,\n"
               + "SUM(D_40)   AS   D40,\n"
               + "SUM(D_41)   AS   D41,\n"
               + "SUM(D_42)   AS   D42,\n"
               + "SUM(D_CB)   AS   DCB FROM hojaicp WHERE NUMEROQUINCENA = ? AND TPLANTEL = 'COOPERACION'";
        
        /////</editor-fold>
        
        /////<editor-fold desc="consulta OFICIALES SIN PLAN">
        String sqloficialessp = "SELECT SUM(P_01)   AS   P01,\n" + "SUM(P_01A)   AS   P_01A,\n"
               + "SUM(P_01B)   AS   P_01B,\n"
               + "SUM(P_01C)   AS   P_01C,\n"
               + "SUM(P_01D)   AS   P_01D,\n"
               + "SUM(P_01E)   AS   P_01E,\n"
               + "SUM(P_18)   AS   P18,\n"
               + "SUM(P_19)   AS   P19,\n"
               + "SUM(P_02)   AS   P02,\n"
               + "SUM(P_03)   AS   P03,\n"
               + "SUM(P_04)   AS   P04,\n"
               + "SUM(P_05)   AS   P05,\n"
               + "SUM(P_06)   AS   P06,\n"
               + "SUM(P_07)   AS   P07,\n"
               + "SUM(P_08)   AS   P08,\n"
               + "SUM(P_09)   AS   P09,\n"
               + "SUM(P_10)   AS   P10,\n"
               + "SUM(P_11)   AS   P11,\n"
               + "SUM(P_12)   AS   P12,\n"
               + "SUM(P_13)   AS   P13,\n"
               + "SUM(P_14)   AS   P14,\n"
               + "SUM(P_15)   AS   P15,\n"
               + "SUM(P_16)   AS   P16,\n"
               + "SUM(P_17)   AS   P17,\n"
               + "SUM(P_20)   AS   P20,\n"
               + "SUM(P_21)   AS   P21,\n"
               + "SUM(P_22)   AS   P22,\n"
               + "SUM(P_23)   AS   P23,\n"
               + "SUM(P_24)   AS   P24,\n"
               + "SUM(P_25)   AS   P25,\n"
               + "SUM(P_26)   AS   P26,\n"
               + "SUM(P_27)   AS   P27,\n"
               + "SUM(P_28)   AS   P28,\n"
               + "SUM(P_29)   AS   P29,\n"
               + "SUM(P_30)   AS   P30,\n"
               + "SUM(P_31)   AS   P31,\n"
               + "SUM(P_32)   AS   P32,\n"
               + "SUM(P_33)   AS   P33,\n"
               + "SUM(P_34)   AS   P34,\n"
               + "SUM(P_35)   AS   P35,\n"
               + "SUM(P_36)   AS   P36,\n"
               + "SUM(P_37)   AS   P37,\n"
               + "SUM(P_38)   AS   P38,\n"
               + "SUM(P_39)   AS   P39,\n"
               + "SUM(P_40)   AS   P40,\n"
               + "SUM(P_41)   AS   P41,\n"
               + "SUM(P_42)   AS   P42,\n"
               + "SUM(P_43)   AS   P43,\n"
               + "SUM(P_44)   AS   P44,\n"
               + "SUM(P_45)   AS   P45,\n"
               + "SUM(P_46)   AS   P46,\n"
               + "SUM(P_47)   AS   P47,\n"
               + "SUM(P_PE)   AS   PPE,\n"
               + "SUM(P_PG)   AS   PPG,\n"
               + "SUM(D_01)   AS   D01,\n"
               + "SUM(D_02)   AS   D02,\n"
               + "SUM(D_03)   AS   D03,\n"
               + "SUM(D_04)   AS   D04,\n"
               + "SUM(D_05)   AS   D05,\n"
               + "SUM(D_06)   AS   D06,\n"
               + "SUM(D_07)   AS   D07,\n"
               + "SUM(D_08)   AS   D08,\n"
               + "SUM(D_09)   AS   D09,\n"
               + "SUM(D_10)   AS   D10,\n"
               + "SUM(D_11)   AS   D11,\n"
               + "SUM(D_12)   AS   D12,\n"
               + "SUM(D_13)   AS   D13,\n"
               + "SUM(D_14)   AS   D14,\n"
               + "SUM(D_15)   AS   D15,\n"
               + "SUM(D_16)   AS   D16,\n"
               + "SUM(D_17)   AS   D17,\n"
               + "SUM(D_18)   AS   D18,\n"
               + "SUM(D_19)   AS   D19,\n"
               + "SUM(D_20)   AS   D20,\n"
               + "SUM(D_21)   AS   D21,\n"
               + "SUM(D_22)   AS   D22,\n"
               + "SUM(D_23)   AS   D23,\n"
               + "SUM(D_24)   AS   D24,\n"
               + "SUM(D_25)   AS   D25,\n"
               + "SUM(D_26)   AS   D26,\n"
               + "SUM(D_27)   AS   D27,\n"
               + "SUM(D_28)   AS   D28,\n"
               + "SUM(D_29)   AS   D29,\n"
               + "SUM(D_30)   AS   D30,\n"
               + "SUM(D_31)   AS   D31,\n"
               + "SUM(D_32)   AS   D32,\n"
               + "SUM(D_33)   AS   D33,\n"
               + "SUM(D_34)   AS   D34,\n"
               + "SUM(D_35)   AS   D35,\n"
               + "SUM(D_36)   AS   D36,\n"
               + "SUM(D_37)   AS   D37,\n"
               + "SUM(D_38)   AS   D38,\n"
               + "SUM(D_39)   AS   D39,\n"
               + "SUM(D_40)   AS   D40,\n"
               + "SUM(D_41)   AS   D41,\n"
               + "SUM(D_42)   AS   D42,\n"
               + "SUM(D_CB)   AS   DCB FROM hojai WHERE NUMEROQUINCENA = ? AND TPLANTEL = 'OFICIALES'";
        
        /////</editor-fold>
        /////
        /////<editor-fold desc="consulta OFICIALES CON PLAN">
        String sqloficialescp = "SELECT SUM(P_01)   AS   P01,\n" + "SUM(P_01A)   AS   P_01A,\n"
               + "SUM(P_01B)   AS   P_01B,\n"
               + "SUM(P_01C)   AS   P_01C,\n"
               + "SUM(P_01D)   AS   P_01D,\n"
               + "SUM(P_01E)   AS   P_01E,\n"
               + "SUM(P_18)   AS   P18,\n"
               + "SUM(P_19)   AS   P19,\n"
               + "SUM(P_02)   AS   P02,\n"
               + "SUM(P_03)   AS   P03,\n"
               + "SUM(P_04)   AS   P04,\n"
               + "SUM(P_05)   AS   P05,\n"
               + "SUM(P_06)   AS   P06,\n"
               + "SUM(P_07)   AS   P07,\n"
               + "SUM(P_08)   AS   P08,\n"
               + "SUM(P_09)   AS   P09,\n"
               + "SUM(P_10)   AS   P10,\n"
               + "SUM(P_11)   AS   P11,\n"
               + "SUM(P_12)   AS   P12,\n"
               + "SUM(P_13)   AS   P13,\n"
               + "SUM(P_14)   AS   P14,\n"
               + "SUM(P_15)   AS   P15,\n"
               + "SUM(P_16)   AS   P16,\n"
               + "SUM(P_17)   AS   P17,\n"
               + "SUM(P_20)   AS   P20,\n"
               + "SUM(P_21)   AS   P21,\n"
               + "SUM(P_22)   AS   P22,\n"
               + "SUM(P_23)   AS   P23,\n"
               + "SUM(P_24)   AS   P24,\n"
               + "SUM(P_25)   AS   P25,\n"
               + "SUM(P_26)   AS   P26,\n"
               + "SUM(P_27)   AS   P27,\n"
               + "SUM(P_28)   AS   P28,\n"
               + "SUM(P_29)   AS   P29,\n"
               + "SUM(P_30)   AS   P30,\n"
               + "SUM(P_31)   AS   P31,\n"
               + "SUM(P_32)   AS   P32,\n"
               + "SUM(P_33)   AS   P33,\n"
               + "SUM(P_34)   AS   P34,\n"
               + "SUM(P_35)   AS   P35,\n"
               + "SUM(P_36)   AS   P36,\n"
               + "SUM(P_37)   AS   P37,\n"
               + "SUM(P_38)   AS   P38,\n"
               + "SUM(P_39)   AS   P39,\n"
               + "SUM(P_40)   AS   P40,\n"
               + "SUM(P_41)   AS   P41,\n"
               + "SUM(P_42)   AS   P42,\n"
               + "SUM(P_43)   AS   P43,\n"
               + "SUM(P_44)   AS   P44,\n"
               + "SUM(P_45)   AS   P45,\n"
               + "SUM(P_46)   AS   P46,\n"
               + "SUM(P_47)   AS   P47,\n"
               + "SUM(P_PE)   AS   PPE,\n"
               + "SUM(P_PG)   AS   PPG,\n"
               + "SUM(D_01)   AS   D01,\n"
               + "SUM(D_02)   AS   D02,\n"
               + "SUM(D_03)   AS   D03,\n"
               + "SUM(D_04)   AS   D04,\n"
               + "SUM(D_05)   AS   D05,\n"
               + "SUM(D_06)   AS   D06,\n"
               + "SUM(D_07)   AS   D07,\n"
               + "SUM(D_08)   AS   D08,\n"
               + "SUM(D_09)   AS   D09,\n"
               + "SUM(D_10)   AS   D10,\n"
               + "SUM(D_11)   AS   D11,\n"
               + "SUM(D_12)   AS   D12,\n"
               + "SUM(D_13)   AS   D13,\n"
               + "SUM(D_14)   AS   D14,\n"
               + "SUM(D_15)   AS   D15,\n"
               + "SUM(D_16)   AS   D16,\n"
               + "SUM(D_17)   AS   D17,\n"
               + "SUM(D_18)   AS   D18,\n"
               + "SUM(D_19)   AS   D19,\n"
               + "SUM(D_20)   AS   D20,\n"
               + "SUM(D_21)   AS   D21,\n"
               + "SUM(D_22)   AS   D22,\n"
               + "SUM(D_23)   AS   D23,\n"
               + "SUM(D_24)   AS   D24,\n"
               + "SUM(D_25)   AS   D25,\n"
               + "SUM(D_26)   AS   D26,\n"
               + "SUM(D_27)   AS   D27,\n"
               + "SUM(D_28)   AS   D28,\n"
               + "SUM(D_29)   AS   D29,\n"
               + "SUM(D_30)   AS   D30,\n"
               + "SUM(D_31)   AS   D31,\n"
               + "SUM(D_32)   AS   D32,\n"
               + "SUM(D_33)   AS   D33,\n"
               + "SUM(D_34)   AS   D34,\n"
               + "SUM(D_35)   AS   D35,\n"
               + "SUM(D_36)   AS   D36,\n"
               + "SUM(D_37)   AS   D37,\n"
               + "SUM(D_38)   AS   D38,\n"
               + "SUM(D_39)   AS   D39,\n"
               + "SUM(D_40)   AS   D40,\n"
               + "SUM(D_41)   AS   D41,\n"
               + "SUM(D_42)   AS   D42,\n"
               + "SUM(D_CB)   AS   DCB FROM hojaicp WHERE NUMEROQUINCENA = ? AND TPLANTEL = 'OFICIALES'";
        
        /////</editor-fold>
        ///
        /////<editor-fold desc="consulta TOTALES SIN PLAN">
        String sqltotalessp = "SELECT SUM(P_01)   AS   P01,\n" + "SUM(P_01A)   AS   P_01A,\n"
               + "SUM(P_01B)   AS   P_01B,\n"
               + "SUM(P_01C)   AS   P_01C,\n"
               + "SUM(P_01D)   AS   P_01D,\n"
               + "SUM(P_01E)   AS   P_01E,\n"
               + "SUM(P_18)   AS   P18,\n"
               + "SUM(P_19)   AS   P19,\n"
               + "SUM(P_02)   AS   P02,\n"
               + "SUM(P_03)   AS   P03,\n"
               + "SUM(P_04)   AS   P04,\n"
               + "SUM(P_05)   AS   P05,\n"
               + "SUM(P_06)   AS   P06,\n"
               + "SUM(P_07)   AS   P07,\n"
               + "SUM(P_08)   AS   P08,\n"
               + "SUM(P_09)   AS   P09,\n"
               + "SUM(P_10)   AS   P10,\n"
               + "SUM(P_11)   AS   P11,\n"
               + "SUM(P_12)   AS   P12,\n"
               + "SUM(P_13)   AS   P13,\n"
               + "SUM(P_14)   AS   P14,\n"
               + "SUM(P_15)   AS   P15,\n"
               + "SUM(P_16)   AS   P16,\n"
               + "SUM(P_17)   AS   P17,\n"
               + "SUM(P_20)   AS   P20,\n"
               + "SUM(P_21)   AS   P21,\n"
               + "SUM(P_22)   AS   P22,\n"
               + "SUM(P_23)   AS   P23,\n"
               + "SUM(P_24)   AS   P24,\n"
               + "SUM(P_25)   AS   P25,\n"
               + "SUM(P_26)   AS   P26,\n"
               + "SUM(P_27)   AS   P27,\n"
               + "SUM(P_28)   AS   P28,\n"
               + "SUM(P_29)   AS   P29,\n"
               + "SUM(P_30)   AS   P30,\n"
               + "SUM(P_31)   AS   P31,\n"
               + "SUM(P_32)   AS   P32,\n"
               + "SUM(P_33)   AS   P33,\n"
               + "SUM(P_34)   AS   P34,\n"
               + "SUM(P_35)   AS   P35,\n"
               + "SUM(P_36)   AS   P36,\n"
               + "SUM(P_37)   AS   P37,\n"
               + "SUM(P_38)   AS   P38,\n"
               + "SUM(P_39)   AS   P39,\n"
               + "SUM(P_40)   AS   P40,\n"
               + "SUM(P_41)   AS   P41,\n"
               + "SUM(P_42)   AS   P42,\n"
               + "SUM(P_43)   AS   P43,\n"
               + "SUM(P_44)   AS   P44,\n"
               + "SUM(P_45)   AS   P45,\n"
               + "SUM(P_46)   AS   P46,\n"
               + "SUM(P_47)   AS   P47,\n"
               + "SUM(P_PE)   AS   PPE,\n"
               + "SUM(P_PG)   AS   PPG,\n"
               + "SUM(D_01)   AS   D01,\n"
               + "SUM(D_02)   AS   D02,\n"
               + "SUM(D_03)   AS   D03,\n"
               + "SUM(D_04)   AS   D04,\n"
               + "SUM(D_05)   AS   D05,\n"
               + "SUM(D_06)   AS   D06,\n"
               + "SUM(D_07)   AS   D07,\n"
               + "SUM(D_08)   AS   D08,\n"
               + "SUM(D_09)   AS   D09,\n"
               + "SUM(D_10)   AS   D10,\n"
               + "SUM(D_11)   AS   D11,\n"
               + "SUM(D_12)   AS   D12,\n"
               + "SUM(D_13)   AS   D13,\n"
               + "SUM(D_14)   AS   D14,\n"
               + "SUM(D_15)   AS   D15,\n"
               + "SUM(D_16)   AS   D16,\n"
               + "SUM(D_17)   AS   D17,\n"
               + "SUM(D_18)   AS   D18,\n"
               + "SUM(D_19)   AS   D19,\n"
               + "SUM(D_20)   AS   D20,\n"
               + "SUM(D_21)   AS   D21,\n"
               + "SUM(D_22)   AS   D22,\n"
               + "SUM(D_23)   AS   D23,\n"
               + "SUM(D_24)   AS   D24,\n"
               + "SUM(D_25)   AS   D25,\n"
               + "SUM(D_26)   AS   D26,\n"
               + "SUM(D_27)   AS   D27,\n"
               + "SUM(D_28)   AS   D28,\n"
               + "SUM(D_29)   AS   D29,\n"
               + "SUM(D_30)   AS   D30,\n"
               + "SUM(D_31)   AS   D31,\n"
               + "SUM(D_32)   AS   D32,\n"
               + "SUM(D_33)   AS   D33,\n"
               + "SUM(D_34)   AS   D34,\n"
               + "SUM(D_35)   AS   D35,\n"
               + "SUM(D_36)   AS   D36,\n"
               + "SUM(D_37)   AS   D37,\n"
               + "SUM(D_38)   AS   D38,\n"
               + "SUM(D_39)   AS   D39,\n"
               + "SUM(D_40)   AS   D40,\n"
               + "SUM(D_41)   AS   D41,\n"
               + "SUM(D_42)   AS   D42,\n"
               + "SUM(D_CB)   AS   DCB FROM hojai WHERE NUMEROQUINCENA = ?";
        
        /////</editor-fold>
        ///
        /////<editor-fold desc="consulta TOTALES CON PLAN">
        String sqltotalescp = "SELECT SUM(P_01)   AS   P01,\n" + "SUM(P_01A)   AS   P_01A,\n"
               + "SUM(P_01B)   AS   P_01B,\n"
               + "SUM(P_01C)   AS   P_01C,\n"
               + "SUM(P_01D)   AS   P_01D,\n"
               + "SUM(P_01E)   AS   P_01E,\n"
               + "SUM(P_18)   AS   P18,\n"
               + "SUM(P_19)   AS   P19,\n"
               + "SUM(P_02)   AS   P02,\n"
               + "SUM(P_03)   AS   P03,\n"
               + "SUM(P_04)   AS   P04,\n"
               + "SUM(P_05)   AS   P05,\n"
               + "SUM(P_06)   AS   P06,\n"
               + "SUM(P_07)   AS   P07,\n"
               + "SUM(P_08)   AS   P08,\n"
               + "SUM(P_09)   AS   P09,\n"
               + "SUM(P_10)   AS   P10,\n"
               + "SUM(P_11)   AS   P11,\n"
               + "SUM(P_12)   AS   P12,\n"
               + "SUM(P_13)   AS   P13,\n"
               + "SUM(P_14)   AS   P14,\n"
               + "SUM(P_15)   AS   P15,\n"
               + "SUM(P_16)   AS   P16,\n"
               + "SUM(P_17)   AS   P17,\n"
               + "SUM(P_20)   AS   P20,\n"
               + "SUM(P_21)   AS   P21,\n"
               + "SUM(P_22)   AS   P22,\n"
               + "SUM(P_23)   AS   P23,\n"
               + "SUM(P_24)   AS   P24,\n"
               + "SUM(P_25)   AS   P25,\n"
               + "SUM(P_26)   AS   P26,\n"
               + "SUM(P_27)   AS   P27,\n"
               + "SUM(P_28)   AS   P28,\n"
               + "SUM(P_29)   AS   P29,\n"
               + "SUM(P_30)   AS   P30,\n"
               + "SUM(P_31)   AS   P31,\n"
               + "SUM(P_32)   AS   P32,\n"
               + "SUM(P_33)   AS   P33,\n"
               + "SUM(P_34)   AS   P34,\n"
               + "SUM(P_35)   AS   P35,\n"
               + "SUM(P_36)   AS   P36,\n"
               + "SUM(P_37)   AS   P37,\n"
               + "SUM(P_38)   AS   P38,\n"
               + "SUM(P_39)   AS   P39,\n"
               + "SUM(P_40)   AS   P40,\n"
               + "SUM(P_41)   AS   P41,\n"
               + "SUM(P_42)   AS   P42,\n"
               + "SUM(P_43)   AS   P43,\n"
               + "SUM(P_44)   AS   P44,\n"
               + "SUM(P_45)   AS   P45,\n"
               + "SUM(P_46)   AS   P46,\n"
               + "SUM(P_47)   AS   P47,\n"
               + "SUM(P_PE)   AS   PPE,\n"
               + "SUM(P_PG)   AS   PPG,\n"
               + "SUM(D_01)   AS   D01,\n"
               + "SUM(D_02)   AS   D02,\n"
               + "SUM(D_03)   AS   D03,\n"
               + "SUM(D_04)   AS   D04,\n"
               + "SUM(D_05)   AS   D05,\n"
               + "SUM(D_06)   AS   D06,\n"
               + "SUM(D_07)   AS   D07,\n"
               + "SUM(D_08)   AS   D08,\n"
               + "SUM(D_09)   AS   D09,\n"
               + "SUM(D_10)   AS   D10,\n"
               + "SUM(D_11)   AS   D11,\n"
               + "SUM(D_12)   AS   D12,\n"
               + "SUM(D_13)   AS   D13,\n"
               + "SUM(D_14)   AS   D14,\n"
               + "SUM(D_15)   AS   D15,\n"
               + "SUM(D_16)   AS   D16,\n"
               + "SUM(D_17)   AS   D17,\n"
               + "SUM(D_18)   AS   D18,\n"
               + "SUM(D_19)   AS   D19,\n"
               + "SUM(D_20)   AS   D20,\n"
               + "SUM(D_21)   AS   D21,\n"
               + "SUM(D_22)   AS   D22,\n"
               + "SUM(D_23)   AS   D23,\n"
               + "SUM(D_24)   AS   D24,\n"
               + "SUM(D_25)   AS   D25,\n"
               + "SUM(D_26)   AS   D26,\n"
               + "SUM(D_27)   AS   D27,\n"
               + "SUM(D_28)   AS   D28,\n"
               + "SUM(D_29)   AS   D29,\n"
               + "SUM(D_30)   AS   D30,\n"
               + "SUM(D_31)   AS   D31,\n"
               + "SUM(D_32)   AS   D32,\n"
               + "SUM(D_33)   AS   D33,\n"
               + "SUM(D_34)   AS   D34,\n"
               + "SUM(D_35)   AS   D35,\n"
               + "SUM(D_36)   AS   D36,\n"
               + "SUM(D_37)   AS   D37,\n"
               + "SUM(D_38)   AS   D38,\n"
               + "SUM(D_39)   AS   D39,\n"
               + "SUM(D_40)   AS   D40,\n"
               + "SUM(D_41)   AS   D41,\n"
               + "SUM(D_42)   AS   D42,\n"
               + "SUM(D_CB)   AS   DCB FROM hojaicp WHERE NUMEROQUINCENA = ?";
        
        /////</editor-fold>
        ///
        int numerocolumna = 1;
        int tamanocolumna = 28;
        int numerocolumna2 = 1;
        int tamanocolumna2 = 51;
        
        
        Workbook book = new XSSFWorkbook();
        Sheet hojareporteemsad1 = book.createSheet("EMSAD1");
       // hojareporteemsad1.setColumnWidth(numerocolumna, tamanocolumna);
        hojareporteemsad1.autoSizeColumn(numerocolumna);
        Sheet hojareporteemsad2 = book.createSheet("EMSAD2");
        //hojareporteemsad2.setColumnWidth(numerocolumna2, tamanocolumna2);
        hojareporteemsad2.autoSizeColumn(numerocolumna);
        Sheet hojareportecooperacion1 = book.createSheet("COOPERACION1");
        Sheet hojareportecooperacion2 = book.createSheet("COOPERACION2");
      
        Sheet hojareporteoficiales1 = book.createSheet("OFICIALES1");
        Sheet hojareporteoficiales2 = book.createSheet("OFICIALES2");
      
        Sheet hojareportetotales1 = book.createSheet("TOTALES1");
        Sheet hojareportetotales2 = book.createSheet("TOTALES2");
      
        
        
        Row row = hojareporteemsad1.createRow(1);
        Row rowemsad2 = hojareporteemsad2.createRow(1);
        
        
        
        
        row.createCell(0).setCellValue("CONCEPTOS GRAVABLES");
        row.createCell(1).setCellValue("CLAVE");
        row.createCell(2).setCellValue("PRESUPUESTADO");
        row.createCell(4).setCellValue("EJERCICIO");
       
        rowemsad2.createCell(1).setCellValue("PRESUPUESTADO");
        rowemsad2.createCell(2).setCellValue("EJERCIDO");
        rowemsad2.createCell(3).setCellValue("SUPERAVIT/DEFICIT");
       
        
        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psemsadsp = null;
        PreparedStatement psemsadcp = null;
        PreparedStatement pscooperacionsp = null;
        PreparedStatement pscooperacioncp = null;
        PreparedStatement psoficialessp = null;
        PreparedStatement psoficialescp = null;
        PreparedStatement pstotalessp = null;
        PreparedStatement pstotalescp = null;
        
        
        
        ResultSet rs = null;
        ResultSet rsemsadsp = null;
        ResultSet rsemsadcp = null;
        ResultSet rscooperacionsp = null;
        ResultSet rscooperacioncp = null;
        ResultSet rsoficialessp = null;
        ResultSet rsoficialescp = null;
        ResultSet rstotalessp = null;
        ResultSet rstotalescp = null;
        
        int numFilaDatosemsadsp = 3;
        int numFilaDatosemsadcp = 3;
        int numFilaDatoscooperacionsp = 3;
        int numFilaDatoscooperacioncp = 3;
        int numFilaDatosoficialessp = 3;
        int numFilaDatosoficialescp = 3;
        int numFilaDatostotalessp = 3;
        int numFilaDatostotalescp = 3;
        
        //<editor-fold desc="genera DATOS EMSAD">
        /////////////////INICIA INSERCION DATOS SIN PLAN
        try {
            psemsadsp = connota.prepareStatement(sqlemsadsp);
            psemsadsp.setString(1, reporte.getPeriodo());
            rsemsadsp = psemsadsp.executeQuery();
            
            
            psemsadcp = connota.prepareStatement(sqlemsadcp);
            psemsadcp.setString(1, reporte.getPeriodo());
            rsemsadcp = psemsadcp.executeQuery();

            int numColemsadcp = rsemsadcp.getMetaData().getColumnCount();

            int numCol = rsemsadsp.getMetaData().getColumnCount();

            while (rsemsadsp.next() && rsemsadcp.next()) {

                for (int a = 1; a <= numCol; a++) {
                 
                  Row filaDatosemsadsp = hojareporteemsad1.createRow(numFilaDatosemsadsp);
               
        
                  
                  
                  if(a== 9)
                  {
                      
                    Cell CeldaConceptos = filaDatosemsadsp.createCell(0);
                    CeldaConceptos.setCellStyle(datosEstilo);
                    CeldaConceptos.setCellValue("SUMA");
                    
                    
                    Cell Celdasumap1 = filaDatosemsadsp.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C4:C11)");
                     
                    Cell Celdasumaemsad1 = filaDatosemsadsp.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E4:E11)"); 
                    
                    numFilaDatosemsadsp++;
                    filaDatosemsadsp = hojareporteemsad1.createRow(numFilaDatosemsadsp);
                  
                    Cell CeldaConceptos2 = filaDatosemsadsp.createCell(0);
                    CeldaConceptos2.setCellStyle(datosEstilo);
                    CeldaConceptos2.setCellValue("CONCEPTOS NO GRAVABLES");
                   
                    
                    Cell CeldaNombreCampo = filaDatosemsadsp.createCell(1);
                      CeldaNombreCampo.setCellStyle(datosEstilo);
                      CeldaNombreCampo.setCellValue(rsemsadsp.getMetaData().getColumnName(a));

                      Cell CeldaValorCampo = filaDatosemsadsp.createCell(2);
                      CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo.setCellValue(rsemsadsp.getDouble(a));

                      Cell CeldaValorCampo2 = filaDatosemsadsp.createCell(4);
                      CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo2.setCellValue(rsemsadcp.getDouble(a));
                    
                  } else if(a== 55)
                  {
                      
                    Cell CeldaConceptos = filaDatosemsadsp.createCell(0);
                    CeldaConceptos.setCellStyle(datosEstilo);
                    CeldaConceptos.setCellValue("SUMA");
                      
                    Cell Celdasumap1 = filaDatosemsadsp.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C13:C58)");
                     
                    Cell Celdasumaemsad1 = filaDatosemsadsp.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E13:E58)"); 
                    
                    numFilaDatosemsadsp++;
                    filaDatosemsadsp = hojareporteemsad1.createRow(numFilaDatosemsadsp);
                  
                    Cell CeldaConceptos2 = filaDatosemsadsp.createCell(0);
                    CeldaConceptos2.setCellStyle(datosEstilo);
                    CeldaConceptos2.setCellValue("TOTAL PERCEPCIONES");
                    
                    Cell Celdasumap11 = filaDatosemsadsp.createCell(2);
                    Celdasumap11.setCellStyle(datosEstiloMoneda);
                    Celdasumap11.setCellFormula("SUM(C12+C59)");
                     
                    Cell Celdasumaemsad11 = filaDatosemsadsp.createCell(4);
                    Celdasumaemsad11.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad11.setCellFormula("SUM(E12+E59)"); 
                    
                    numFilaDatosemsadsp++;
                    filaDatosemsadsp = hojareporteemsad1.createRow(numFilaDatosemsadsp);
                  
                      Cell CeldaConceptos3 = filaDatosemsadsp.createCell(0);
                    CeldaConceptos3.setCellStyle(datosEstilo);
                    CeldaConceptos3.setCellValue("DEDUCCIONES");
                    
                    
                     Cell CeldaNombreCampo = filaDatosemsadsp.createCell(1);
                      CeldaNombreCampo.setCellStyle(datosEstilo);
                      CeldaNombreCampo.setCellValue(rsemsadsp.getMetaData().getColumnName(a));

                      Cell CeldaValorCampo = filaDatosemsadsp.createCell(2);
                      CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo.setCellValue(rsemsadsp.getDouble(a));

                      Cell CeldaValorCampo2 = filaDatosemsadsp.createCell(4);
                      CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo2.setCellValue(rsemsadcp.getDouble(a));
                    
                  } 
                  
                else{    
                      
               
                
                
                
                Cell CeldaNombreCampo = filaDatosemsadsp.createCell(1);
                CeldaNombreCampo.setCellStyle(datosEstilo);
                CeldaNombreCampo.setCellValue(rsemsadsp.getMetaData().getColumnName(a));
                
                
                
                Cell CeldaValorCampo = filaDatosemsadsp.createCell(2);
                CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                CeldaValorCampo.setCellValue(rsemsadsp.getDouble(a));
                  
                Cell CeldaValorCampo2 = filaDatosemsadsp.createCell(4);
                CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                CeldaValorCampo2.setCellValue(rsemsadcp.getDouble(a));
                 
                  
                  }
                
                    numFilaDatosemsadsp++;
                }
                 numFilaDatosemsadsp++;
                 Row filaDatosemsadspfinal = hojareporteemsad1.createRow(numFilaDatosemsadsp);
                 
                   Cell CeldaConceptos3 = filaDatosemsadspfinal.createCell(0);
                    CeldaConceptos3.setCellStyle(datosEstilo);
                    CeldaConceptos3.setCellValue("TOTAL DEDUCCIONES");
                   
                    Cell Celdasumap1 = filaDatosemsadspfinal.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C61:C103)");
                     
                    Cell Celdasumaemsad1 = filaDatosemsadspfinal.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E61:E103)"); 
                    
                    numFilaDatosemsadsp++;
                    filaDatosemsadspfinal = hojareporteemsad1.createRow(numFilaDatosemsadsp);
                  
                       Cell CeldaConceptos4 = filaDatosemsadspfinal.createCell(0);
                    CeldaConceptos4.setCellStyle(datosEstilo);
                    CeldaConceptos4.setCellValue("NETO A RECIBIR");
                
                    Cell Celdasumap11 = filaDatosemsadspfinal.createCell(2);
                    Celdasumap11.setCellStyle(datosEstiloMoneda);
                    Celdasumap11.setCellFormula("(C60-C105)");
                     
                    Cell Celdasumaemsad11 = filaDatosemsadspfinal.createCell(4);
                    Celdasumaemsad11.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad11.setCellFormula("E60-E105"); 
                

            }
            
            //////////////INCIA HOJA2 EMSAD
            
                  Row filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
               
                    Cell Celdadato1 = filaDatosemsad2.createCell(0);
                    Celdadato1.setCellStyle(datosEstilo);
                    Celdadato1.setCellValue("CONCEPTOS GRAVADOS ISSSTE");
                  
                    Cell Celdasumapresupuestado1 = filaDatosemsad2.createCell(1);
                    Celdasumapresupuestado1.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado1.setCellFormula("EMSAD1!C12");
                    
                    Cell Celdaejercido1 = filaDatosemsad2.createCell(2);
                    Celdaejercido1.setCellStyle(datosEstiloMoneda);
                    Celdaejercido1.setCellFormula("EMSAD1!E12");
                    
                    Cell CeldaDiferencia1= filaDatosemsad2.createCell(3);
                    CeldaDiferencia1.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia1.setCellFormula("B4-C4");
                    
                     numFilaDatosemsadcp++;
                     filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                     
                     Cell Celdadato2 = filaDatosemsad2.createCell(0);
                    Celdadato2.setCellStyle(datosEstilo);
                    Celdadato2.setCellValue("D-03 SEG. DE SAUL Y ACTIVOS ISSSTE 7.375%");
                  
                    Cell Celdasumapresupuestado2 = filaDatosemsad2.createCell(1);
                    Celdasumapresupuestado2.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado2.setCellFormula("(B4*7.375)/100");
                    
                    Cell Celdaejercido2 = filaDatosemsad2.createCell(2);
                    Celdaejercido2.setCellStyle(datosEstiloMoneda);
                    Celdaejercido2.setCellFormula("(C4*7.375)/100");
                    
                    Cell CeldaDiferencia2= filaDatosemsad2.createCell(3);
                    CeldaDiferencia2.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia2.setCellFormula("B5-C5");
                    
                     numFilaDatosemsadcp++;
                      filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                    
                     Cell Celdadato3 = filaDatosemsad2.createCell(0);
                    Celdadato3.setCellStyle(datosEstilo);
                    Celdadato3.setCellValue("D-21 SEG. DE SALUD PENSIONADOS ISSSTE 0.72%");
                  
                    Cell Celdasumapresupuestado3 = filaDatosemsad2.createCell(1);
                    Celdasumapresupuestado3.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado3.setCellFormula("(B4*0.72)/100");
                    
                    Cell Celdaejercido3 = filaDatosemsad2.createCell(2);
                    Celdaejercido3.setCellStyle(datosEstiloMoneda);
                    Celdaejercido3.setCellFormula("(C4*0.72)/100");
                    
                    Cell CeldaDiferencia3= filaDatosemsad2.createCell(3);
                    CeldaDiferencia3.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia3.setCellFormula("B6-C6");
                    
                    
                    numFilaDatosemsadcp++;
                     filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                     
                     Cell Celdadato4 = filaDatosemsad2.createCell(0);
                    Celdadato4.setCellStyle(datosEstilo);
                    Celdadato4.setCellValue("RIESGO DE TRABAJO 0.75%");
                  
                    Cell Celdasumapresupuestado4 = filaDatosemsad2.createCell(1);
                    Celdasumapresupuestado4.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado4.setCellFormula("(B4*0.75)/100");
                    
                    Cell Celdaejercido4 = filaDatosemsad2.createCell(2);
                    Celdaejercido4.setCellStyle(datosEstiloMoneda);
                    Celdaejercido4.setCellFormula("(C4*0.75)/100");
                    
                    Cell CeldaDiferencia4 = filaDatosemsad2.createCell(3);
                    CeldaDiferencia4.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia4.setCellFormula("B7-C7");
                    
                    numFilaDatosemsadcp++;
                     filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                     
                     Cell Celdadato5 = filaDatosemsad2.createCell(0);
                    Celdadato5.setCellStyle(datosEstilo);
                    Celdadato5.setCellValue("D-04 SEG. DE RET. CESANTIA EN EDAD AVANZADA 5.175%");
                  
                    Cell Celdasumapresupuestado5 = filaDatosemsad2.createCell(1);
                    Celdasumapresupuestado5.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado5.setCellFormula("(B4*5.175)/100");
                    
                    Cell Celdaejercido5 = filaDatosemsad2.createCell(2);
                    Celdaejercido5.setCellStyle(datosEstiloMoneda);
                    Celdaejercido5.setCellFormula("(C4*5.175)/100");
                    
                    Cell CeldaDiferencia5 = filaDatosemsad2.createCell(3);
                    CeldaDiferencia5.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia5.setCellFormula("B8-C8");
                    
                    numFilaDatosemsadcp++;
                     filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                     
                     Cell Celdadato6 = filaDatosemsad2.createCell(0);
                    Celdadato6.setCellStyle(datosEstilo);
                    Celdadato6.setCellValue("D-22 SEG. DE INVALIDEZ Y VIDA 0.625%");
                  
                    Cell Celdasumapresupuestado6 = filaDatosemsad2.createCell(1);
                    Celdasumapresupuestado6.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado6.setCellFormula("(B4*0.625)/100");
                    
                    Cell Celdaejercido6 = filaDatosemsad2.createCell(2);
                    Celdaejercido6.setCellStyle(datosEstiloMoneda);
                    Celdaejercido6.setCellFormula("(C4*0.625)/100");
                    
                    Cell CeldaDiferencia6 = filaDatosemsad2.createCell(3);
                    CeldaDiferencia6.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia6.setCellFormula("B9-C9");
                    
                     numFilaDatosemsadcp++;
                      filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                    
                     Cell Celdadato7 = filaDatosemsad2.createCell(0);
                    Celdadato7.setCellStyle(datosEstilo);
                    Celdadato7.setCellValue("D-23 SERV. SOC. Y CULTURALES ISSSTE 0.5%");
                  
                    Cell Celdasumapresupuestado7 = filaDatosemsad2.createCell(1);
                    Celdasumapresupuestado7.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado7.setCellFormula("(B4*0.5)/100");
                    
                    Cell Celdaejercido7 = filaDatosemsad2.createCell(2);
                    Celdaejercido7.setCellStyle(datosEstiloMoneda);
                    Celdaejercido7.setCellFormula("(C4*0.5)/100");
                    
                    Cell CeldaDiferencia7 = filaDatosemsad2.createCell(3);
                    CeldaDiferencia7.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia7.setCellFormula("B10-C10");
                    
                    
                    numFilaDatosemsadcp++;
                     filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                     
                     Cell Celdadato8 = filaDatosemsad2.createCell(0);
                    Celdadato8.setCellStyle(datosEstilo);
                    Celdadato8.setCellValue("FOVISSTE 5%");
                  
                    Cell Celdasumapresupuestado8 = filaDatosemsad2.createCell(1);
                    Celdasumapresupuestado8.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado8.setCellFormula("(B4*5)/100");
                    
                    Cell Celdaejercido8 = filaDatosemsad2.createCell(2);
                    Celdaejercido8.setCellStyle(datosEstiloMoneda);
                    Celdaejercido8.setCellFormula("(C4*5)/100");
                    
                    Cell CeldaDiferencia8 = filaDatosemsad2.createCell(3);
                    CeldaDiferencia8.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia8.setCellFormula("B11-C11");
                    
                    
                    
                    numFilaDatosemsadcp++;
                     filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                     
                     Cell Celdadato10 = filaDatosemsad2.createCell(0);
                    Celdadato10.setCellStyle(datosEstilo);
                    Celdadato10.setCellValue("APORTACION AL PROGRAMA");
                  
                    
                    
                    Cell Celdaejercido10 = filaDatosemsad2.createCell(2);
                    Celdaejercido10.setCellStyle(datosEstiloMoneda);
                    Celdaejercido10.setCellFormula("D12");
                    
                    Cell CeldaDiferencia10 = filaDatosemsad2.createCell(3);
                    CeldaDiferencia10.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia10.setCellFormula("SUM(D4:D11)");
                    
                     numFilaDatosemsadcp++;
                     filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                     
                     Cell Celdadato11 = filaDatosemsad2.createCell(0);
                    Celdadato11.setCellStyle(datosEstilo);
                    Celdadato11.setCellValue("TOTALES");
                  
                    Cell Celdaejercido11 = filaDatosemsad2.createCell(1);
                    Celdaejercido11.setCellStyle(datosEstiloMoneda);
                    Celdaejercido11.setCellFormula("SUM(B4:B12)");
                    
                    Cell CeldaDiferencia11 = filaDatosemsad2.createCell(2);
                    CeldaDiferencia11.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia11.setCellFormula("SUM(C4:C12)");
                    
                     numFilaDatosemsadcp++;
                     filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                     
                     Cell Celdadato12 = filaDatosemsad2.createCell(0);
                    Celdadato12.setCellStyle(datosEstilo);
                    Celdadato12.setCellValue("APORTACION A PREVISION SOCIAL");
                  
                    Cell Celdaejercido12 = filaDatosemsad2.createCell(1);
                    Celdaejercido12.setCellStyle(datosEstiloMoneda);
                    Celdaejercido12.setCellFormula("D12");
                   
                     numFilaDatosemsadcp++;
                     filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                     
                     Cell Celdadato13 = filaDatosemsad2.createCell(0);
                    Celdadato13.setCellStyle(datosEstilo);
                    Celdadato13.setCellValue("PPE Y PPG");
                  
                    Cell Celdaejercido13 = filaDatosemsad2.createCell(1);
                    Celdaejercido13.setCellStyle(datosEstiloMoneda);
                    Celdaejercido13.setCellFormula("EMSAD1!E57+EMSAD1!E58");
                    
                    numFilaDatosemsadcp++;
                     filaDatosemsad2 = hojareporteemsad2.createRow(numFilaDatosemsadcp);
                     
                     Cell Celdadato14 = filaDatosemsad2.createCell(0);
                    Celdadato14.setCellStyle(datosEstilo);
                    Celdadato14.setCellValue("PREVISION SOCIAL CONTINGENTE");
                  
                    Cell Celdaejercido14 = filaDatosemsad2.createCell(1);
                    Celdaejercido14.setCellStyle(datosEstiloMoneda);
                    Celdaejercido14.setCellFormula("B14-B15");
            
            ///////////////////////////////TERMINA EMSAD 2
            
            
           
            

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       ///////////////TERMINA EMSAD
        //</editor-fold> 
        ///
        
        //<editor-fold desc="genera DATOS COOPERACION">
        ///////////////////INICIA COOPERACION//////////////////////
         /////////////////INICIA INSERCION DATOS SIN PLAN
        try {
            pscooperacionsp = connota.prepareStatement(sqlcooperacionsp);
            pscooperacionsp.setString(1, reporte.getPeriodo());
            rscooperacionsp = pscooperacionsp.executeQuery();
            
            
            pscooperacioncp = connota.prepareStatement(sqlcooperacioncp);
            pscooperacioncp.setString(1, reporte.getPeriodo());
            rscooperacioncp = pscooperacioncp.executeQuery();

            int numColcooperacioncp = rsemsadcp.getMetaData().getColumnCount();

            int numColcooperacionsp = pscooperacionsp.getMetaData().getColumnCount();

            while (rscooperacionsp.next() && rscooperacioncp.next()) {

                for (int a = 1; a <= numColcooperacionsp; a++) {
                 
                  Row filaDatoscooperacionsp = hojareportecooperacion1.createRow(numFilaDatoscooperacionsp);
               
        
                  
                  
                  if(a== 9)
                  {
                      
                    Cell CeldaConceptos = filaDatoscooperacionsp.createCell(0);
                    CeldaConceptos.setCellStyle(datosEstilo);
                    CeldaConceptos.setCellValue("SUMA");
                    
                    
                    Cell Celdasumap1 = filaDatoscooperacionsp.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C4:C11)");
                     
                    Cell Celdasumaemsad1 = filaDatoscooperacionsp.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E4:E11)"); 
                    
                    numFilaDatoscooperacionsp++;
                    filaDatoscooperacionsp = hojareportecooperacion1.createRow(numFilaDatoscooperacionsp);
                  
                    Cell CeldaConceptos2 = filaDatoscooperacionsp.createCell(0);
                    CeldaConceptos2.setCellStyle(datosEstilo);
                    CeldaConceptos2.setCellValue("CONCEPTOS NO GRAVABLES");
                   
                    
                    Cell CeldaNombreCampo = filaDatoscooperacionsp.createCell(1);
                      CeldaNombreCampo.setCellStyle(datosEstilo);
                      CeldaNombreCampo.setCellValue(rscooperacionsp.getMetaData().getColumnName(a));

                      Cell CeldaValorCampo = filaDatoscooperacionsp.createCell(2);
                      CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo.setCellValue(rscooperacionsp.getDouble(a));

                      Cell CeldaValorCampo2 = filaDatoscooperacionsp.createCell(4);
                      CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo2.setCellValue(rscooperacioncp.getDouble(a));
                    
                  } else if(a== 55)
                  {
                      
                    Cell CeldaConceptos = filaDatoscooperacionsp.createCell(0);
                    CeldaConceptos.setCellStyle(datosEstilo);
                    CeldaConceptos.setCellValue("SUMA");
                      
                    Cell Celdasumap1 = filaDatoscooperacionsp.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C13:C58)");
                     
                    Cell Celdasumaemsad1 = filaDatoscooperacionsp.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E13:E58)"); 
                    
                    numFilaDatoscooperacionsp++;
                    filaDatoscooperacionsp = hojareportecooperacion1.createRow(numFilaDatoscooperacionsp);
                  
                    Cell CeldaConceptos2 = filaDatoscooperacionsp.createCell(0);
                    CeldaConceptos2.setCellStyle(datosEstilo);
                    CeldaConceptos2.setCellValue("TOTAL PERCEPCIONES");
                    
                    Cell Celdasumap11 = filaDatoscooperacionsp.createCell(2);
                    Celdasumap11.setCellStyle(datosEstiloMoneda);
                    Celdasumap11.setCellFormula("SUM(C12+C59)");
                     
                    Cell Celdasumaemsad11 = filaDatoscooperacionsp.createCell(4);
                    Celdasumaemsad11.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad11.setCellFormula("SUM(E12+E59)"); 
                    
                    numFilaDatoscooperacionsp++;
                    filaDatoscooperacionsp = hojareportecooperacion1.createRow(numFilaDatoscooperacionsp);
                  
                      Cell CeldaConceptos3 = filaDatoscooperacionsp.createCell(0);
                    CeldaConceptos3.setCellStyle(datosEstilo);
                    CeldaConceptos3.setCellValue("DEDUCCIONES");
                    
                    
                     Cell CeldaNombreCampo = filaDatoscooperacionsp.createCell(1);
                      CeldaNombreCampo.setCellStyle(datosEstilo);
                      CeldaNombreCampo.setCellValue(rscooperacionsp.getMetaData().getColumnName(a));

                      Cell CeldaValorCampo = filaDatoscooperacionsp.createCell(2);
                      CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo.setCellValue(rscooperacionsp.getDouble(a));

                      Cell CeldaValorCampo2 = filaDatoscooperacionsp.createCell(4);
                      CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo2.setCellValue(rscooperacioncp.getDouble(a));
                    
                  } 
                  
                else{    
                      
               
                
                
                
                Cell CeldaNombreCampo = filaDatoscooperacionsp.createCell(1);
                CeldaNombreCampo.setCellStyle(datosEstilo);
                CeldaNombreCampo.setCellValue(rscooperacionsp.getMetaData().getColumnName(a));
                
                
                
                Cell CeldaValorCampo = filaDatoscooperacionsp.createCell(2);
                CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                CeldaValorCampo.setCellValue(rscooperacionsp.getDouble(a));
                  
                Cell CeldaValorCampo2 = filaDatoscooperacionsp.createCell(4);
                CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                CeldaValorCampo2.setCellValue(rscooperacioncp.getDouble(a));
                 
                  
                  }
                
                    numFilaDatoscooperacionsp++;
                }
                 numFilaDatoscooperacionsp++;
                 Row filaDatoscooperacionspfinal = hojareportecooperacion1.createRow(numFilaDatoscooperacionsp);
                 
                   Cell CeldaConceptos3 = filaDatoscooperacionspfinal.createCell(0);
                    CeldaConceptos3.setCellStyle(datosEstilo);
                    CeldaConceptos3.setCellValue("TOTAL DEDUCCIONES");
                   
                    Cell Celdasumap1 = filaDatoscooperacionspfinal.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C61:C103)");
                     
                    Cell Celdasumaemsad1 = filaDatoscooperacionspfinal.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E61:E103)"); 
                    
                    numFilaDatoscooperacionsp++;
                    filaDatoscooperacionspfinal = hojareportecooperacion1.createRow(numFilaDatoscooperacionsp);
                  
                       Cell CeldaConceptos4 = filaDatoscooperacionspfinal.createCell(0);
                    CeldaConceptos4.setCellStyle(datosEstilo);
                    CeldaConceptos4.setCellValue("NETO A RECIBIR");
                
                    Cell Celdasumap11 = filaDatoscooperacionspfinal.createCell(2);
                    Celdasumap11.setCellStyle(datosEstiloMoneda);
                    Celdasumap11.setCellFormula("(C60-C105)");
                     
                    Cell Celdasumaemsad11 = filaDatoscooperacionspfinal.createCell(4);
                    Celdasumaemsad11.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad11.setCellFormula("E60-E105"); 
                

            }
            
            //////////////INCIA HOJA2 COOPERACION
            
                  Row filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
               
                    Cell Celdadato1cooperacion = filaDatoscooperacion2.createCell(0);
                    Celdadato1cooperacion.setCellStyle(datosEstilo);
                    Celdadato1cooperacion.setCellValue("CONCEPTOS GRAVADOS ISSSTE");
                  
                    Cell Celdasumapresupuestado1cooperacion = filaDatoscooperacion2.createCell(1);
                    Celdasumapresupuestado1cooperacion.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado1cooperacion.setCellFormula("COOPERACION1!C12");
                    
                    Cell Celdaejercido1cooperacion = filaDatoscooperacion2.createCell(2);
                    Celdaejercido1cooperacion.setCellStyle(datosEstiloMoneda);
                    Celdaejercido1cooperacion.setCellFormula("COOPERACION1!E12");
                    
                    Cell CeldaDiferencia1cooperacion= filaDatoscooperacion2.createCell(3);
                    CeldaDiferencia1cooperacion.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia1cooperacion.setCellFormula("B4-C4");
                    
                     numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                     
                     Cell Celdadato2 = filaDatoscooperacion2.createCell(0);
                    Celdadato2.setCellStyle(datosEstilo);
                    Celdadato2.setCellValue("D-03 SEG. DE SAUL Y ACTIVOS ISSSTE 7.375%");
                  
                    Cell Celdasumapresupuestado2 = filaDatoscooperacion2.createCell(1);
                    Celdasumapresupuestado2.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado2.setCellFormula("(B4*7.375)/100");
                    
                    Cell Celdaejercido2 = filaDatoscooperacion2.createCell(2);
                    Celdaejercido2.setCellStyle(datosEstiloMoneda);
                    Celdaejercido2.setCellFormula("(C4*7.375)/100");
                    
                    Cell CeldaDiferencia2= filaDatoscooperacion2.createCell(3);
                    CeldaDiferencia2.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia2.setCellFormula("B5-C5");
                    
                      numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                   
                     Cell Celdadato3 = filaDatoscooperacion2.createCell(0);
                    Celdadato3.setCellStyle(datosEstilo);
                    Celdadato3.setCellValue("D-21 SEG. DE SALUD PENSIONADOS ISSSTE 0.72%");
                  
                    Cell Celdasumapresupuestado3 = filaDatoscooperacion2.createCell(1);
                    Celdasumapresupuestado3.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado3.setCellFormula("(B4*0.72)/100");
                    
                    Cell Celdaejercido3 = filaDatoscooperacion2.createCell(2);
                    Celdaejercido3.setCellStyle(datosEstiloMoneda);
                    Celdaejercido3.setCellFormula("(C4*0.72)/100");
                    
                    Cell CeldaDiferencia3= filaDatoscooperacion2.createCell(3);
                    CeldaDiferencia3.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia3.setCellFormula("B6-C6");
                    
                    
                      numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                    
                     Cell Celdadato4 = filaDatoscooperacion2.createCell(0);
                    Celdadato4.setCellStyle(datosEstilo);
                    Celdadato4.setCellValue("RIESGO DE TRABAJO 0.75%");
                  
                    Cell Celdasumapresupuestado4 = filaDatoscooperacion2.createCell(1);
                    Celdasumapresupuestado4.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado4.setCellFormula("(B4*0.75)/100");
                    
                    Cell Celdaejercido4 = filaDatoscooperacion2.createCell(2);
                    Celdaejercido4.setCellStyle(datosEstiloMoneda);
                    Celdaejercido4.setCellFormula("(C4*0.75)/100");
                    
                    Cell CeldaDiferencia4 = filaDatoscooperacion2.createCell(3);
                    CeldaDiferencia4.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia4.setCellFormula("B7-C7");
                    
                        numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                    
                     Cell Celdadato5 = filaDatoscooperacion2.createCell(0);
                    Celdadato5.setCellStyle(datosEstilo);
                    Celdadato5.setCellValue("D-04 SEG. DE RET. CESANTIA EN EDAD AVANZADA 5.175%");
                  
                    Cell Celdasumapresupuestado5 = filaDatoscooperacion2.createCell(1);
                    Celdasumapresupuestado5.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado5.setCellFormula("(B4*5.175)/100");
                    
                    Cell Celdaejercido5 = filaDatoscooperacion2.createCell(2);
                    Celdaejercido5.setCellStyle(datosEstiloMoneda);
                    Celdaejercido5.setCellFormula("(C4*5.175)/100");
                    
                    Cell CeldaDiferencia5 = filaDatoscooperacion2.createCell(3);
                    CeldaDiferencia5.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia5.setCellFormula("B8-C8");
                    
                         numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                     
                     Cell Celdadato6 = filaDatoscooperacion2.createCell(0);
                    Celdadato6.setCellStyle(datosEstilo);
                    Celdadato6.setCellValue("D-22 SEG. DE INVALIDEZ Y VIDA 0.625%");
                  
                    Cell Celdasumapresupuestado6 = filaDatoscooperacion2.createCell(1);
                    Celdasumapresupuestado6.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado6.setCellFormula("(B4*0.625)/100");
                    
                    Cell Celdaejercido6 = filaDatoscooperacion2.createCell(2);
                    Celdaejercido6.setCellStyle(datosEstiloMoneda);
                    Celdaejercido6.setCellFormula("(C4*0.625)/100");
                    
                    Cell CeldaDiferencia6 = filaDatoscooperacion2.createCell(3);
                    CeldaDiferencia6.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia6.setCellFormula("B9-C9");
                    
                          numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                   
                     Cell Celdadato7 = filaDatoscooperacion2.createCell(0);
                    Celdadato7.setCellStyle(datosEstilo);
                    Celdadato7.setCellValue("D-23 SERV. SOC. Y CULTURALES ISSSTE 0.5%");
                  
                    Cell Celdasumapresupuestado7 = filaDatoscooperacion2.createCell(1);
                    Celdasumapresupuestado7.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado7.setCellFormula("(B4*0.5)/100");
                    
                    Cell Celdaejercido7 = filaDatoscooperacion2.createCell(2);
                    Celdaejercido7.setCellStyle(datosEstiloMoneda);
                    Celdaejercido7.setCellFormula("(C4*0.5)/100");
                    
                    Cell CeldaDiferencia7 = filaDatoscooperacion2.createCell(3);
                    CeldaDiferencia7.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia7.setCellFormula("B10-C10");
                    
                    
                           numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                    
                     Cell Celdadato8 = filaDatoscooperacion2.createCell(0);
                    Celdadato8.setCellStyle(datosEstilo);
                    Celdadato8.setCellValue("FOVISSTE 5%");
                  
                    Cell Celdasumapresupuestado8 = filaDatoscooperacion2.createCell(1);
                    Celdasumapresupuestado8.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado8.setCellFormula("(B4*5)/100");
                    
                    Cell Celdaejercido8 = filaDatoscooperacion2.createCell(2);
                    Celdaejercido8.setCellStyle(datosEstiloMoneda);
                    Celdaejercido8.setCellFormula("(C4*5)/100");
                    
                    Cell CeldaDiferencia8 = filaDatoscooperacion2.createCell(3);
                    CeldaDiferencia8.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia8.setCellFormula("B11-C11");
                    
                    
                    
                           numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                   
                     Cell Celdadato10 = filaDatoscooperacion2.createCell(0);
                    Celdadato10.setCellStyle(datosEstilo);
                    Celdadato10.setCellValue("APORTACION AL PROGRAMA");
                  
                    
                    
                    Cell Celdaejercido10 = filaDatoscooperacion2.createCell(2);
                    Celdaejercido10.setCellStyle(datosEstiloMoneda);
                    Celdaejercido10.setCellFormula("D12");
                    
                    Cell CeldaDiferencia10 = filaDatoscooperacion2.createCell(3);
                    CeldaDiferencia10.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia10.setCellFormula("SUM(D4:D11)");
                    
                             numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                    
                     Cell Celdadato11 = filaDatoscooperacion2.createCell(0);
                    Celdadato11.setCellStyle(datosEstilo);
                    Celdadato11.setCellValue("TOTALES");
                  
                    Cell Celdaejercido11 = filaDatoscooperacion2.createCell(1);
                    Celdaejercido11.setCellStyle(datosEstiloMoneda);
                    Celdaejercido11.setCellFormula("SUM(B4:B12)");
                    
                    Cell CeldaDiferencia11 = filaDatoscooperacion2.createCell(2);
                    CeldaDiferencia11.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia11.setCellFormula("SUM(C4:C12)");
                    
                           numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                      
                     Cell Celdadato12 = filaDatoscooperacion2.createCell(0);
                    Celdadato12.setCellStyle(datosEstilo);
                    Celdadato12.setCellValue("APORTACION A PREVISION SOCIAL");
                  
                    Cell Celdaejercido12 = filaDatoscooperacion2.createCell(1);
                    Celdaejercido12.setCellStyle(datosEstiloMoneda);
                    Celdaejercido12.setCellFormula("D12");
                   
                           numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                     
                     Cell Celdadato13 = filaDatoscooperacion2.createCell(0);
                    Celdadato13.setCellStyle(datosEstilo);
                    Celdadato13.setCellValue("PPE Y PPG");
                  
                    Cell Celdaejercido13 = filaDatoscooperacion2.createCell(1);
                    Celdaejercido13.setCellStyle(datosEstiloMoneda);
                    Celdaejercido13.setCellFormula("COOPERACION1!E57+COOPERACION1!E58");
                    
                            numFilaDatoscooperacioncp++;
                     filaDatoscooperacion2 = hojareportecooperacion2.createRow(numFilaDatoscooperacioncp);
                    
                     Cell Celdadato14 = filaDatoscooperacion2.createCell(0);
                    Celdadato14.setCellStyle(datosEstilo);
                    Celdadato14.setCellValue("PREVISION SOCIAL CONTINGENTE");
                  
                    Cell Celdaejercido14 = filaDatoscooperacion2.createCell(1);
                    Celdaejercido14.setCellStyle(datosEstiloMoneda);
                    Celdaejercido14.setCellFormula("B14-B15");
            
            ///////////////////////////////TERMINA COOPERACION 2
        }catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
                //</editor-fold>
        ////////////////////
        
        //<editor-fold desc="genera DATOS OFICIALES">
         ///////////////////INICIA OFICLALES//////////////////////
         /////////////////INICIA INSERCION DATOS SIN PLAN
        try {
            psoficialessp = connota.prepareStatement(sqloficialessp);
            psoficialessp.setString(1, reporte.getPeriodo());
            rsoficialessp = psoficialessp.executeQuery();
            
            
            psoficialescp = connota.prepareStatement(sqloficialescp);
            psoficialescp.setString(1, reporte.getPeriodo());
            rsoficialescp = psoficialescp.executeQuery();

            int numColoficialescp = rsoficialescp.getMetaData().getColumnCount();

            int numColoficialessp = psoficialessp.getMetaData().getColumnCount();

            while (rsoficialessp.next() && rsoficialescp.next()) {

                for (int a = 1; a <= numColoficialessp; a++) {
                 
                  Row filaDatosoficialessp = hojareporteoficiales1.createRow(numFilaDatosoficialessp);
               
        
                  
                  
                  if(a== 9)
                  {
                      
                    Cell CeldaConceptos = filaDatosoficialessp.createCell(0);
                    CeldaConceptos.setCellStyle(datosEstilo);
                    CeldaConceptos.setCellValue("SUMA");
                    
                    
                    Cell Celdasumap1 = filaDatosoficialessp.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C4:C11)");
                     
                    Cell Celdasumaemsad1 = filaDatosoficialessp.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E4:E11)"); 
                    
                    numFilaDatosoficialessp++;
                    filaDatosoficialessp = hojareporteoficiales1.createRow(numFilaDatosoficialessp);
                  
                    Cell CeldaConceptos2 = filaDatosoficialessp.createCell(0);
                    CeldaConceptos2.setCellStyle(datosEstilo);
                    CeldaConceptos2.setCellValue("CONCEPTOS NO GRAVABLES");
                   
                    
                    Cell CeldaNombreCampo = filaDatosoficialessp.createCell(1);
                      CeldaNombreCampo.setCellStyle(datosEstilo);
                      CeldaNombreCampo.setCellValue(rsoficialessp.getMetaData().getColumnName(a));

                      Cell CeldaValorCampo = filaDatosoficialessp.createCell(2);
                      CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo.setCellValue(rsoficialessp.getDouble(a));

                      Cell CeldaValorCampo2 = filaDatosoficialessp.createCell(4);
                      CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo2.setCellValue(rsoficialescp.getDouble(a));
                    
                  } else if(a== 55)
                  {
                      
                    Cell CeldaConceptos = filaDatosoficialessp.createCell(0);
                    CeldaConceptos.setCellStyle(datosEstilo);
                    CeldaConceptos.setCellValue("SUMA");
                      
                    Cell Celdasumap1 = filaDatosoficialessp.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C13:C58)");
                     
                    Cell Celdasumaemsad1 = filaDatosoficialessp.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E13:E58)"); 
                    
                     numFilaDatosoficialessp++;
                    filaDatosoficialessp = hojareporteoficiales1.createRow(numFilaDatosoficialessp);
                  
                    Cell CeldaConceptos2 = filaDatosoficialessp.createCell(0);
                    CeldaConceptos2.setCellStyle(datosEstilo);
                    CeldaConceptos2.setCellValue("TOTAL PERCEPCIONES");
                    
                    Cell Celdasumap11 = filaDatosoficialessp.createCell(2);
                    Celdasumap11.setCellStyle(datosEstiloMoneda);
                    Celdasumap11.setCellFormula("SUM(C12+C59)");
                     
                    Cell Celdasumaemsad11 = filaDatosoficialessp.createCell(4);
                    Celdasumaemsad11.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad11.setCellFormula("SUM(E12+E59)"); 
                    
                     numFilaDatosoficialessp++;
                    filaDatosoficialessp = hojareporteoficiales1.createRow(numFilaDatosoficialessp);
                  
                      Cell CeldaConceptos3 = filaDatosoficialessp.createCell(0);
                    CeldaConceptos3.setCellStyle(datosEstilo);
                    CeldaConceptos3.setCellValue("DEDUCCIONES");
                    
                    
                     Cell CeldaNombreCampo = filaDatosoficialessp.createCell(1);
                      CeldaNombreCampo.setCellStyle(datosEstilo);
                      CeldaNombreCampo.setCellValue(rsoficialessp.getMetaData().getColumnName(a));

                      Cell CeldaValorCampo = filaDatosoficialessp.createCell(2);
                      CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo.setCellValue(rsoficialessp.getDouble(a));

                      Cell CeldaValorCampo2 = filaDatosoficialessp.createCell(4);
                      CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo2.setCellValue(rsoficialescp.getDouble(a));
                    
                  } 
                  
                else{    
                      
               
                
                
                
                Cell CeldaNombreCampo = filaDatosoficialessp.createCell(1);
                CeldaNombreCampo.setCellStyle(datosEstilo);
                CeldaNombreCampo.setCellValue(rsoficialessp.getMetaData().getColumnName(a));
                
                
                
                Cell CeldaValorCampo = filaDatosoficialessp.createCell(2);
                CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                CeldaValorCampo.setCellValue(rsoficialessp.getDouble(a));
                  
                Cell CeldaValorCampo2 = filaDatosoficialessp.createCell(4);
                CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                CeldaValorCampo2.setCellValue(rsoficialescp.getDouble(a));
                 
                  
                  }
                
                  numFilaDatosoficialessp++;
                    }
                 numFilaDatosoficialessp++;
                 Row filaDatosoficialesspfinal = hojareporteoficiales1.createRow(numFilaDatosoficialessp);
                 
                   Cell CeldaConceptos3 = filaDatosoficialesspfinal.createCell(0);
                    CeldaConceptos3.setCellStyle(datosEstilo);
                    CeldaConceptos3.setCellValue("TOTAL DEDUCCIONES");
                   
                    Cell Celdasumap1 = filaDatosoficialesspfinal.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C61:C103)");
                     
                    Cell Celdasumaemsad1 = filaDatosoficialesspfinal.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E61:E103)"); 
                    
                    numFilaDatosoficialessp++;
                    filaDatosoficialesspfinal = hojareporteoficiales1.createRow(numFilaDatosoficialessp);
                  
                       Cell CeldaConceptos4 = filaDatosoficialesspfinal.createCell(0);
                    CeldaConceptos4.setCellStyle(datosEstilo);
                    CeldaConceptos4.setCellValue("NETO A RECIBIR");
                
                    Cell Celdasumap11 = filaDatosoficialesspfinal.createCell(2);
                    Celdasumap11.setCellStyle(datosEstiloMoneda);
                    Celdasumap11.setCellFormula("(C60-C105)");
                     
                    Cell Celdasumaemsad11 = filaDatosoficialesspfinal.createCell(4);
                    Celdasumaemsad11.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad11.setCellFormula("E60-E105"); 
                

            }
            
            //////////////INCIA HOJA2 COOPERACION
            
                  Row filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
               
                    Cell Celdadato1cooperacion = filaDatosoficiales2.createCell(0);
                    Celdadato1cooperacion.setCellStyle(datosEstilo);
                    Celdadato1cooperacion.setCellValue("CONCEPTOS GRAVADOS ISSSTE");
                  
                    Cell Celdasumapresupuestado1cooperacion = filaDatosoficiales2.createCell(1);
                    Celdasumapresupuestado1cooperacion.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado1cooperacion.setCellFormula("OFICIALES1!C12");
                    
                    Cell Celdaejercido1cooperacion = filaDatosoficiales2.createCell(2);
                    Celdaejercido1cooperacion.setCellStyle(datosEstiloMoneda);
                    Celdaejercido1cooperacion.setCellFormula("OFICIALES1!E12");
                    
                    Cell CeldaDiferencia1cooperacion= filaDatosoficiales2.createCell(3);
                    CeldaDiferencia1cooperacion.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia1cooperacion.setCellFormula("B4-C4");
                    
                     numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                     
                     Cell Celdadato2 = filaDatosoficiales2.createCell(0);
                    Celdadato2.setCellStyle(datosEstilo);
                    Celdadato2.setCellValue("D-03 SEG. DE SAUL Y ACTIVOS ISSSTE 7.375%");
                  
                    Cell Celdasumapresupuestado2 = filaDatosoficiales2.createCell(1);
                    Celdasumapresupuestado2.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado2.setCellFormula("(B4*7.375)/100");
                    
                    Cell Celdaejercido2 = filaDatosoficiales2.createCell(2);
                    Celdaejercido2.setCellStyle(datosEstiloMoneda);
                    Celdaejercido2.setCellFormula("(C4*7.375)/100");
                    
                    Cell CeldaDiferencia2= filaDatosoficiales2.createCell(3);
                    CeldaDiferencia2.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia2.setCellFormula("B5-C5");
                    
                      numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                  
                     Cell Celdadato3 = filaDatosoficiales2.createCell(0);
                    Celdadato3.setCellStyle(datosEstilo);
                    Celdadato3.setCellValue("D-21 SEG. DE SALUD PENSIONADOS ISSSTE 0.72%");
                  
                    Cell Celdasumapresupuestado3 = filaDatosoficiales2.createCell(1);
                    Celdasumapresupuestado3.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado3.setCellFormula("(B4*0.72)/100");
                    
                    Cell Celdaejercido3 = filaDatosoficiales2.createCell(2);
                    Celdaejercido3.setCellStyle(datosEstiloMoneda);
                    Celdaejercido3.setCellFormula("(C4*0.72)/100");
                    
                    Cell CeldaDiferencia3= filaDatosoficiales2.createCell(3);
                    CeldaDiferencia3.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia3.setCellFormula("B6-C6");
                    
                    
                      numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                  
                      
                     Cell Celdadato4 = filaDatosoficiales2.createCell(0);
                    Celdadato4.setCellStyle(datosEstilo);
                    Celdadato4.setCellValue("RIESGO DE TRABAJO 0.75%");
                  
                    Cell Celdasumapresupuestado4 = filaDatosoficiales2.createCell(1);
                    Celdasumapresupuestado4.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado4.setCellFormula("(B4*0.75)/100");
                    
                    Cell Celdaejercido4 = filaDatosoficiales2.createCell(2);
                    Celdaejercido4.setCellStyle(datosEstiloMoneda);
                    Celdaejercido4.setCellFormula("(C4*0.75)/100");
                    
                    Cell CeldaDiferencia4 = filaDatosoficiales2.createCell(3);
                    CeldaDiferencia4.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia4.setCellFormula("B7-C7");
                    
                       numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                   
                     Cell Celdadato5 = filaDatosoficiales2.createCell(0);
                    Celdadato5.setCellStyle(datosEstilo);
                    Celdadato5.setCellValue("D-04 SEG. DE RET. CESANTIA EN EDAD AVANZADA 5.175%");
                  
                    Cell Celdasumapresupuestado5 = filaDatosoficiales2.createCell(1);
                    Celdasumapresupuestado5.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado5.setCellFormula("(B4*5.175)/100");
                    
                    Cell Celdaejercido5 = filaDatosoficiales2.createCell(2);
                    Celdaejercido5.setCellStyle(datosEstiloMoneda);
                    Celdaejercido5.setCellFormula("(C4*5.175)/100");
                    
                    Cell CeldaDiferencia5 = filaDatosoficiales2.createCell(3);
                    CeldaDiferencia5.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia5.setCellFormula("B8-C8");
                    
                        numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                    
                     Cell Celdadato6 = filaDatosoficiales2.createCell(0);
                    Celdadato6.setCellStyle(datosEstilo);
                    Celdadato6.setCellValue("D-22 SEG. DE INVALIDEZ Y VIDA 0.625%");
                  
                    Cell Celdasumapresupuestado6 = filaDatosoficiales2.createCell(1);
                    Celdasumapresupuestado6.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado6.setCellFormula("(B4*0.625)/100");
                    
                    Cell Celdaejercido6 = filaDatosoficiales2.createCell(2);
                    Celdaejercido6.setCellStyle(datosEstiloMoneda);
                    Celdaejercido6.setCellFormula("(C4*0.625)/100");
                    
                    Cell CeldaDiferencia6 = filaDatosoficiales2.createCell(3);
                    CeldaDiferencia6.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia6.setCellFormula("B9-C9");
                    
                         numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                 
                     Cell Celdadato7 = filaDatosoficiales2.createCell(0);
                    Celdadato7.setCellStyle(datosEstilo);
                    Celdadato7.setCellValue("D-23 SERV. SOC. Y CULTURALES ISSSTE 0.5%");
                  
                    Cell Celdasumapresupuestado7 = filaDatosoficiales2.createCell(1);
                    Celdasumapresupuestado7.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado7.setCellFormula("(B4*0.5)/100");
                    
                    Cell Celdaejercido7 = filaDatosoficiales2.createCell(2);
                    Celdaejercido7.setCellStyle(datosEstiloMoneda);
                    Celdaejercido7.setCellFormula("(C4*0.5)/100");
                    
                    Cell CeldaDiferencia7 = filaDatosoficiales2.createCell(3);
                    CeldaDiferencia7.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia7.setCellFormula("B10-C10");
                    
                    
                         numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                   
                     Cell Celdadato8 = filaDatosoficiales2.createCell(0);
                    Celdadato8.setCellStyle(datosEstilo);
                    Celdadato8.setCellValue("FOVISSTE 5%");
                  
                    Cell Celdasumapresupuestado8 = filaDatosoficiales2.createCell(1);
                    Celdasumapresupuestado8.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado8.setCellFormula("(B4*5)/100");
                    
                    Cell Celdaejercido8 = filaDatosoficiales2.createCell(2);
                    Celdaejercido8.setCellStyle(datosEstiloMoneda);
                    Celdaejercido8.setCellFormula("(C4*5)/100");
                    
                    Cell CeldaDiferencia8 = filaDatosoficiales2.createCell(3);
                    CeldaDiferencia8.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia8.setCellFormula("B11-C11");
                    
                    
                    
                          numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                  
                     Cell Celdadato10 = filaDatosoficiales2.createCell(0);
                    Celdadato10.setCellStyle(datosEstilo);
                    Celdadato10.setCellValue("APORTACION AL PROGRAMA");
                  
                    
                    
                    Cell Celdaejercido10 = filaDatosoficiales2.createCell(2);
                    Celdaejercido10.setCellStyle(datosEstiloMoneda);
                    Celdaejercido10.setCellFormula("D12");
                    
                    Cell CeldaDiferencia10 = filaDatosoficiales2.createCell(3);
                    CeldaDiferencia10.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia10.setCellFormula("SUM(D4:D11)");
                    
                            numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                   
                     Cell Celdadato11 = filaDatosoficiales2.createCell(0);
                    Celdadato11.setCellStyle(datosEstilo);
                    Celdadato11.setCellValue("TOTALES");
                  
                    Cell Celdaejercido11 = filaDatosoficiales2.createCell(1);
                    Celdaejercido11.setCellStyle(datosEstiloMoneda);
                    Celdaejercido11.setCellFormula("SUM(B4:B12)");
                    
                    Cell CeldaDiferencia11 = filaDatosoficiales2.createCell(2);
                    CeldaDiferencia11.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia11.setCellFormula("SUM(C4:C12)");
                    
                             numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                     
                     Cell Celdadato12 = filaDatosoficiales2.createCell(0);
                    Celdadato12.setCellStyle(datosEstilo);
                    Celdadato12.setCellValue("APORTACION A PREVISION SOCIAL");
                  
                    Cell Celdaejercido12 = filaDatosoficiales2.createCell(1);
                    Celdaejercido12.setCellStyle(datosEstiloMoneda);
                    Celdaejercido12.setCellFormula("D12");
                   
                           numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                      
                     Cell Celdadato13 = filaDatosoficiales2.createCell(0);
                    Celdadato13.setCellStyle(datosEstilo);
                    Celdadato13.setCellValue("PPE Y PPG");
                  
                    Cell Celdaejercido13 = filaDatosoficiales2.createCell(1);
                    Celdaejercido13.setCellStyle(datosEstiloMoneda);
                    Celdaejercido13.setCellFormula("OFICIALES1!E57+OFICIALES1!E58");
                    
                            numFilaDatosoficialescp++;
                     filaDatosoficiales2 = hojareporteoficiales2.createRow(numFilaDatosoficialescp);
                    
                     Cell Celdadato14 = filaDatosoficiales2.createCell(0);
                    Celdadato14.setCellStyle(datosEstilo);
                    Celdadato14.setCellValue("PREVISION SOCIAL CONTINGENTE");
                  
                    Cell Celdaejercido14 = filaDatosoficiales2.createCell(1);
                    Celdaejercido14.setCellStyle(datosEstiloMoneda);
                    Celdaejercido14.setCellFormula("B14-B15");
            
            ///////////////////////////////TERMINA OFICIALES 
        }catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        ///////////TERMINA OFICIALES
                //</editor-fold >
        /////////////////////////////////
        
        
         //<editor-fold desc="genera DATOS TOTALES">
         ///////////////////INICIA TOTALES//////////////////////
         /////////////////INICIA INSERCION DATOS SIN PLAN
        try {
            pstotalessp = connota.prepareStatement(sqltotalessp);
            pstotalessp.setString(1, reporte.getPeriodo());
            rstotalessp = pstotalessp.executeQuery();
            
            
            pstotalescp = connota.prepareStatement(sqltotalescp);
            pstotalescp.setString(1, reporte.getPeriodo());
            rstotalescp = pstotalescp.executeQuery();

            int numColtotalescp = rstotalescp.getMetaData().getColumnCount();

            int numColtotalessp = pstotalessp.getMetaData().getColumnCount();

            while (rstotalessp.next() && rstotalescp.next()) {

                for (int a = 1; a <= numColtotalessp; a++) {
                 
                  Row filaDatostotalessp = hojareportetotales1.createRow(numFilaDatostotalessp);
               
        
                  
                  
                  if(a== 9)
                  {
                      
                    Cell CeldaConceptos = filaDatostotalessp.createCell(0);
                    CeldaConceptos.setCellStyle(datosEstilo);
                    CeldaConceptos.setCellValue("SUMA");
                    
                    
                    Cell Celdasumap1 = filaDatostotalessp.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C4:C11)");
                     
                    Cell Celdasumaemsad1 = filaDatostotalessp.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E4:E11)"); 
                    
                    numFilaDatostotalessp++;
                    filaDatostotalessp = hojareportetotales1.createRow(numFilaDatostotalessp);
                  
                    Cell CeldaConceptos2 = filaDatostotalessp.createCell(0);
                    CeldaConceptos2.setCellStyle(datosEstilo);
                    CeldaConceptos2.setCellValue("CONCEPTOS NO GRAVABLES");
                   
                    
                    Cell CeldaNombreCampo = filaDatostotalessp.createCell(1);
                      CeldaNombreCampo.setCellStyle(datosEstilo);
                      CeldaNombreCampo.setCellValue(rstotalessp.getMetaData().getColumnName(a));

                      Cell CeldaValorCampo = filaDatostotalessp.createCell(2);
                      CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo.setCellValue(rstotalessp.getDouble(a));

                      Cell CeldaValorCampo2 = filaDatostotalessp.createCell(4);
                      CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo2.setCellValue(rstotalescp.getDouble(a));
                    
                  } else if(a== 55)
                  {
                      
                    Cell CeldaConceptos = filaDatostotalessp.createCell(0);
                    CeldaConceptos.setCellStyle(datosEstilo);
                    CeldaConceptos.setCellValue("SUMA");
                      
                    Cell Celdasumap1 = filaDatostotalessp.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C13:C58)");
                     
                    Cell Celdasumaemsad1 = filaDatostotalessp.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E13:E58)"); 
                    
                     numFilaDatostotalessp++;
                    filaDatostotalessp = hojareportetotales1.createRow(numFilaDatostotalessp);
                  
                    Cell CeldaConceptos2 = filaDatostotalessp.createCell(0);
                    CeldaConceptos2.setCellStyle(datosEstilo);
                    CeldaConceptos2.setCellValue("TOTAL PERCEPCIONES");
                    
                    Cell Celdasumap11 = filaDatostotalessp.createCell(2);
                    Celdasumap11.setCellStyle(datosEstiloMoneda);
                    Celdasumap11.setCellFormula("SUM(C12+C59)");
                     
                    Cell Celdasumaemsad11 = filaDatostotalessp.createCell(4);
                    Celdasumaemsad11.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad11.setCellFormula("SUM(E12+E59)"); 
                    
                     numFilaDatostotalessp++;
                    filaDatostotalessp = hojareportetotales1.createRow(numFilaDatostotalessp);
                  
                      Cell CeldaConceptos3 = filaDatostotalessp.createCell(0);
                    CeldaConceptos3.setCellStyle(datosEstilo);
                    CeldaConceptos3.setCellValue("DEDUCCIONES");
                    
                    
                     Cell CeldaNombreCampo = filaDatostotalessp.createCell(1);
                      CeldaNombreCampo.setCellStyle(datosEstilo);
                      CeldaNombreCampo.setCellValue(rstotalessp.getMetaData().getColumnName(a));

                      Cell CeldaValorCampo = filaDatostotalessp.createCell(2);
                      CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo.setCellValue(rstotalessp.getDouble(a));

                      Cell CeldaValorCampo2 = filaDatostotalessp.createCell(4);
                      CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                      CeldaValorCampo2.setCellValue(rstotalescp.getDouble(a));
                    
                  } 
                  
                else{    
                      
               
                
                
                
                Cell CeldaNombreCampo = filaDatostotalessp.createCell(1);
                CeldaNombreCampo.setCellStyle(datosEstilo);
                CeldaNombreCampo.setCellValue(rstotalessp.getMetaData().getColumnName(a));
                
                
                
                Cell CeldaValorCampo = filaDatostotalessp.createCell(2);
                CeldaValorCampo.setCellStyle(datosEstiloMoneda);
                CeldaValorCampo.setCellValue(rstotalessp.getDouble(a));
                  
                Cell CeldaValorCampo2 = filaDatostotalessp.createCell(4);
                CeldaValorCampo2.setCellStyle(datosEstiloMoneda);
                CeldaValorCampo2.setCellValue(rstotalescp.getDouble(a));
                 
                  
                  }
                
                  numFilaDatostotalessp++;
                    }
                 numFilaDatostotalessp++;
                 Row filaDatostotalesspfinal = hojareportetotales1.createRow(numFilaDatostotalessp);
                 
                   Cell CeldaConceptos3 = filaDatostotalesspfinal.createCell(0);
                    CeldaConceptos3.setCellStyle(datosEstilo);
                    CeldaConceptos3.setCellValue("TOTAL DEDUCCIONES");
                   
                    Cell Celdasumap1 = filaDatostotalesspfinal.createCell(2);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(C61:C103)");
                     
                    Cell Celdasumaemsad1 = filaDatostotalesspfinal.createCell(4);
                    Celdasumaemsad1.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad1.setCellFormula("SUM(E61:E103)"); 
                    
                   numFilaDatostotalessp++;
                   filaDatostotalesspfinal = hojareportetotales1.createRow(numFilaDatostotalessp);
                 
                       Cell CeldaConceptos4 = filaDatostotalesspfinal.createCell(0);
                    CeldaConceptos4.setCellStyle(datosEstilo);
                    CeldaConceptos4.setCellValue("NETO A RECIBIR");
                
                    Cell Celdasumap11 = filaDatostotalesspfinal.createCell(2);
                    Celdasumap11.setCellStyle(datosEstiloMoneda);
                    Celdasumap11.setCellFormula("(C60-C105)");
                     
                    Cell Celdasumaemsad11 = filaDatostotalesspfinal.createCell(4);
                    Celdasumaemsad11.setCellStyle(datosEstiloMoneda);
                    Celdasumaemsad11.setCellFormula("E60-E105"); 
                

            }
            
            //////////////INCIA HOJA2 TOTALES
            
                  Row filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
               
                    Cell Celdadato1cooperacion = filaDatostotales2.createCell(0);
                    Celdadato1cooperacion.setCellStyle(datosEstilo);
                    Celdadato1cooperacion.setCellValue("CONCEPTOS GRAVADOS ISSSTE");
                  
                    Cell Celdasumapresupuestado1cooperacion = filaDatostotales2.createCell(1);
                    Celdasumapresupuestado1cooperacion.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado1cooperacion.setCellFormula("TOTALES1!C12");
                    
                    Cell Celdaejercido1cooperacion = filaDatostotales2.createCell(2);
                    Celdaejercido1cooperacion.setCellStyle(datosEstiloMoneda);
                    Celdaejercido1cooperacion.setCellFormula("TOTALES1!E12");
                    
                    Cell CeldaDiferencia1cooperacion= filaDatostotales2.createCell(3);
                    CeldaDiferencia1cooperacion.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia1cooperacion.setCellFormula("B4-C4");
                    
                     numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                     
                     Cell Celdadato2 = filaDatostotales2.createCell(0);
                    Celdadato2.setCellStyle(datosEstilo);
                    Celdadato2.setCellValue("D-03 SEG. DE SAUL Y ACTIVOS ISSSTE 7.375%");
                  
                    Cell Celdasumapresupuestado2 = filaDatostotales2.createCell(1);
                    Celdasumapresupuestado2.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado2.setCellFormula("(B4*7.375)/100");
                    
                    Cell Celdaejercido2 = filaDatostotales2.createCell(2);
                    Celdaejercido2.setCellStyle(datosEstiloMoneda);
                    Celdaejercido2.setCellFormula("(C4*7.375)/100");
                    
                    Cell CeldaDiferencia2= filaDatostotales2.createCell(3);
                    CeldaDiferencia2.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia2.setCellFormula("B5-C5");
                    
                      numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                  
                     Cell Celdadato3 = filaDatostotales2.createCell(0);
                    Celdadato3.setCellStyle(datosEstilo);
                    Celdadato3.setCellValue("D-21 SEG. DE SALUD PENSIONADOS ISSSTE 0.72%");
                  
                    Cell Celdasumapresupuestado3 = filaDatostotales2.createCell(1);
                    Celdasumapresupuestado3.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado3.setCellFormula("(B4*0.72)/100");
                    
                    Cell Celdaejercido3 = filaDatostotales2.createCell(2);
                    Celdaejercido3.setCellStyle(datosEstiloMoneda);
                    Celdaejercido3.setCellFormula("(C4*0.72)/100");
                    
                    Cell CeldaDiferencia3= filaDatostotales2.createCell(3);
                    CeldaDiferencia3.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia3.setCellFormula("B6-C6");
                    
                     numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                     
                     Cell Celdadato4 = filaDatostotales2.createCell(0);
                    Celdadato4.setCellStyle(datosEstilo);
                    Celdadato4.setCellValue("RIESGO DE TRABAJO 0.75%");
                  
                    Cell Celdasumapresupuestado4 = filaDatostotales2.createCell(1);
                    Celdasumapresupuestado4.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado4.setCellFormula("(B4*0.75)/100");
                    
                    Cell Celdaejercido4 = filaDatostotales2.createCell(2);
                    Celdaejercido4.setCellStyle(datosEstiloMoneda);
                    Celdaejercido4.setCellFormula("(C4*0.75)/100");
                    
                    Cell CeldaDiferencia4 = filaDatostotales2.createCell(3);
                    CeldaDiferencia4.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia4.setCellFormula("B7-C7");
                    
                      numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                     
                 
                     Cell Celdadato5 = filaDatostotales2.createCell(0);
                    Celdadato5.setCellStyle(datosEstilo);
                    Celdadato5.setCellValue("D-04 SEG. DE RET. CESANTIA EN EDAD AVANZADA 5.175%");
                  
                    Cell Celdasumapresupuestado5 = filaDatostotales2.createCell(1);
                    Celdasumapresupuestado5.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado5.setCellFormula("(B4*5.175)/100");
                    
                    Cell Celdaejercido5 = filaDatostotales2.createCell(2);
                    Celdaejercido5.setCellStyle(datosEstiloMoneda);
                    Celdaejercido5.setCellFormula("(C4*5.175)/100");
                    
                    Cell CeldaDiferencia5 = filaDatostotales2.createCell(3);
                    CeldaDiferencia5.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia5.setCellFormula("B8-C8");
                    
                       numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                  
                     Cell Celdadato6 = filaDatostotales2.createCell(0);
                    Celdadato6.setCellStyle(datosEstilo);
                    Celdadato6.setCellValue("D-22 SEG. DE INVALIDEZ Y VIDA 0.625%");
                  
                    Cell Celdasumapresupuestado6 = filaDatostotales2.createCell(1);
                    Celdasumapresupuestado6.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado6.setCellFormula("(B4*0.625)/100");
                    
                    Cell Celdaejercido6 = filaDatostotales2.createCell(2);
                    Celdaejercido6.setCellStyle(datosEstiloMoneda);
                    Celdaejercido6.setCellFormula("(C4*0.625)/100");
                    
                    Cell CeldaDiferencia6 = filaDatostotales2.createCell(3);
                    CeldaDiferencia6.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia6.setCellFormula("B9-C9");
                    
                       numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                  
                     Cell Celdadato7 = filaDatostotales2.createCell(0);
                    Celdadato7.setCellStyle(datosEstilo);
                    Celdadato7.setCellValue("D-23 SERV. SOC. Y CULTURALES ISSSTE 0.5%");
                  
                    Cell Celdasumapresupuestado7 = filaDatostotales2.createCell(1);
                    Celdasumapresupuestado7.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado7.setCellFormula("(B4*0.5)/100");
                    
                    Cell Celdaejercido7 = filaDatostotales2.createCell(2);
                    Celdaejercido7.setCellStyle(datosEstiloMoneda);
                    Celdaejercido7.setCellFormula("(C4*0.5)/100");
                    
                    Cell CeldaDiferencia7 = filaDatostotales2.createCell(3);
                    CeldaDiferencia7.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia7.setCellFormula("B10-C10");
                    
                    
                         numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                  
                     Cell Celdadato8 = filaDatostotales2.createCell(0);
                    Celdadato8.setCellStyle(datosEstilo);
                    Celdadato8.setCellValue("FOVISSTE 5%");
                  
                    Cell Celdasumapresupuestado8 = filaDatostotales2.createCell(1);
                    Celdasumapresupuestado8.setCellStyle(datosEstiloMoneda);
                    Celdasumapresupuestado8.setCellFormula("(B4*5)/100");
                    
                    Cell Celdaejercido8 = filaDatostotales2.createCell(2);
                    Celdaejercido8.setCellStyle(datosEstiloMoneda);
                    Celdaejercido8.setCellFormula("(C4*5)/100");
                    
                    Cell CeldaDiferencia8 = filaDatostotales2.createCell(3);
                    CeldaDiferencia8.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia8.setCellFormula("B11-C11");
                    
                    
                    
                         numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                  
                     Cell Celdadato10 = filaDatostotales2.createCell(0);
                    Celdadato10.setCellStyle(datosEstilo);
                    Celdadato10.setCellValue("APORTACION AL PROGRAMA");
                  
                    
                    
                    Cell Celdaejercido10 = filaDatostotales2.createCell(2);
                    Celdaejercido10.setCellStyle(datosEstiloMoneda);
                    Celdaejercido10.setCellFormula("D12");
                    
                    Cell CeldaDiferencia10 = filaDatostotales2.createCell(3);
                    CeldaDiferencia10.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia10.setCellFormula("SUM(D4:D11)");
                    
                          numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                  
                     Cell Celdadato11 = filaDatostotales2.createCell(0);
                    Celdadato11.setCellStyle(datosEstilo);
                    Celdadato11.setCellValue("TOTALES");
                  
                    Cell Celdaejercido11 = filaDatostotales2.createCell(1);
                    Celdaejercido11.setCellStyle(datosEstiloMoneda);
                    Celdaejercido11.setCellFormula("SUM(B4:B12)");
                    
                    Cell CeldaDiferencia11 = filaDatostotales2.createCell(2);
                    CeldaDiferencia11.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia11.setCellFormula("SUM(C4:C12)");
                    
                            numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                   
                     Cell Celdadato12 = filaDatostotales2.createCell(0);
                    Celdadato12.setCellStyle(datosEstilo);
                    Celdadato12.setCellValue("APORTACION A PREVISION SOCIAL");
                  
                    Cell Celdaejercido12 = filaDatostotales2.createCell(1);
                    Celdaejercido12.setCellStyle(datosEstiloMoneda);
                    Celdaejercido12.setCellFormula("D12");
                   
                            numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                     
                     Cell Celdadato13 = filaDatostotales2.createCell(0);
                    Celdadato13.setCellStyle(datosEstilo);
                    Celdadato13.setCellValue("PPE Y PPG");
                  
                    Cell Celdaejercido13 = filaDatostotales2.createCell(1);
                    Celdaejercido13.setCellStyle(datosEstiloMoneda);
                    Celdaejercido13.setCellFormula("TOTALES1!E57+TOTALES1!E58");
                    
                             numFilaDatostotalescp++;
                     filaDatostotales2 = hojareportetotales2.createRow(numFilaDatostotalescp);
                   
                     Cell Celdadato14 = filaDatostotales2.createCell(0);
                    Celdadato14.setCellStyle(datosEstilo);
                    Celdadato14.setCellValue("PREVISION SOCIAL CONTINGENTE");
                  
                    Cell Celdaejercido14 = filaDatostotales2.createCell(1);
                    Celdaejercido14.setCellStyle(datosEstiloMoneda);
                    Celdaejercido14.setCellFormula("B14-B15");
            
            ///////////////////////////////TERMINA TOTALES 
        }catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        ///////////TERMINA TOTALES
         //</editor-fold >
       
        
        /////////////EXPORTAR ARCHIVO EXCEL
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("EstadoDeOrigen" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformerelaciondeconceptos(Reporte reporte) {
        Connection con = getConexion();
        String claveparainforme = reporte.getClaveReporte();

        String sqlprueba = "select rfc,empleado,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,TIPO,"
                + "categoria," + claveparainforme + " as cantidad from hojaisp where numeroquincena = ? and " + claveparainforme + " > 0";

        //String sqlprueba = "select rfc,empleado,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,TIPO,"
        //      + "categoria," "P_01 as cantidad from hojaisp where numeroquincena = ? and " +claveparainforme+ " > 0";
        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("RelacionDeConceptos");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);
        Row rowencabezado7 = hojareporte.createRow(6);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        row.createCell(0).setCellValue("EMPLEADO");
        row.createCell(1).setCellValue("RFC");
        row.createCell(2).setCellValue("NOMBRE");
        row.createCell(3).setCellValue("PLANTEL");
        row.createCell(4).setCellValue("CLAVE");
        row.createCell(5).setCellValue("CANTIDAD");
        row.createCell(6).setCellValue("TIPO");
        row.createCell(7).setCellValue("CATEGORIA");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            //psinformestrabajadorescotizantes.setString(1, reporte.getClaveReporte());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaEmpleado = filaDatosCuotasYAportaciones.createCell(0);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rstrabajadorescotizantez.getString("empleado"));

                    Cell CeldaRfc = filaDatosCuotasYAportaciones.createCell(1);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rstrabajadorescotizantez.getString("rfc"));

                    Cell CeldaNombre = filaDatosCuotasYAportaciones.createCell(2);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rstrabajadorescotizantez.getString("nombre"));

                    Cell CeldaPlantel = filaDatosCuotasYAportaciones.createCell(3);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rstrabajadorescotizantez.getString("plantel"));

                    Cell CeldaClave = filaDatosCuotasYAportaciones.createCell(4);
                    CeldaClave.setCellStyle(datosEstiloMoneda);
                    CeldaClave.setCellValue(claveparainforme);

                    Cell CeldaCantidad = filaDatosCuotasYAportaciones.createCell(5);
                    CeldaCantidad.setCellStyle(datosEstiloMoneda);
                    CeldaCantidad.setCellValue(rstrabajadorescotizantez.getDouble("cantidad"));

                    Cell CeldaTIPO = filaDatosCuotasYAportaciones.createCell(6);
                    CeldaTIPO.setCellStyle(datosEstilo);
                    CeldaTIPO.setCellValue(rstrabajadorescotizantez.getString("TIPO"));

                    Cell CeldaCategoria = filaDatosCuotasYAportaciones.createCell(7);
                    CeldaCategoria.setCellStyle(datosEstilo);
                    CeldaCategoria.setCellValue(rstrabajadorescotizantez.getString("categoria"));

                }
                numFilaDatosCuotasYAportaciones++;

            }
            int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);
            Row sumapercepciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 3);
            Row cuotasisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 4);
            Row fovisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 5);
            Row aportacionisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 6);

            Cell Celdasumas = sumapercepciones.createCell(0);
            Celdasumas.setCellStyle(datosEstilo);
            Celdasumas.setCellValue("TOTAL");

            Cell Celdasumap1 = sumapercepciones.createCell(5);
            Celdasumap1.setCellStyle(datosEstiloMoneda);
            Celdasumap1.setCellFormula("SUM(F10:F" + numFilaDatosCuotasYAportaciones + ")");

            /* Cell Celdasumas = sumapercepciones.createCell(0);
                    Celdasumas.setCellStyle(datosEstilo);
                    Celdasumas.setCellValue("TOTAL");
                   
                    Cell Celdasconteopensiones = sumapercepciones.createCell(1);
                    Celdasconteopensiones.setCellStyle(datosEstilo);
                    Celdasconteopensiones.setCellValue(numFilaDatosCuotasYAportaciones-10);
                    
                    
                    Cell Celdasumap1 = sumapercepciones.createCell(4);
                    Celdasumap1.setCellStyle(datosEstilo);
                    Celdasumap1.setCellFormula("SUM(E10:E"+numFilaDatosCuotasYAportaciones+")");
                    
                    Cell Celdasumap2 = sumapercepciones.createCell(6);
                    Celdasumap2.setCellStyle(datosEstilo);
                    Celdasumap2.setCellFormula("SUM(G10:G"+numFilaDatosCuotasYAportaciones+")");*/
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("RelacionDeConceptos" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformerelaciondeconceptosresumen(Reporte reporte) {
        Connection con = getConexion();
        String claveparainforme = reporte.getClaveReporte();

        String sqlprueba = "select (select letrero from plantel where numero = plantel) as nombreplantel,plantel,"
                + "categoria,sum(" + claveparainforme + ") as cantidad from hojaisp where numeroquincena = ? and " + claveparainforme + " > 0 group by plantel";

        //String sqlprueba = "select rfc,empleado,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,TIPO,"
        //      + "categoria," "P_01 as cantidad from hojaisp where numeroquincena = ? and " +claveparainforme+ " > 0";
        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("RelacionDeConceptosResumen");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);
        Row rowencabezado7 = hojareporte.createRow(6);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        row.createCell(0).setCellValue("PLANTEL");
        row.createCell(1).setCellValue("NOMBREPLANTEL");
        row.createCell(2).setCellValue("CLAVE");
        row.createCell(3).setCellValue("CANTIDAD");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            //psinformestrabajadorescotizantes.setString(1, reporte.getClaveReporte());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaEmpleado = filaDatosCuotasYAportaciones.createCell(0);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rstrabajadorescotizantez.getString("plantel"));

                    Cell CeldaRfc = filaDatosCuotasYAportaciones.createCell(1);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rstrabajadorescotizantez.getString("nombreplantel"));

                    Cell CeldaPlantel = filaDatosCuotasYAportaciones.createCell(2);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(claveparainforme);

                    Cell CeldaCantidad = filaDatosCuotasYAportaciones.createCell(3);
                    CeldaCantidad.setCellStyle(datosEstiloMoneda);
                    CeldaCantidad.setCellValue(rstrabajadorescotizantez.getDouble("cantidad"));

                }
                numFilaDatosCuotasYAportaciones++;

            }
            int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);
            Row sumapercepciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 3);
            Row cuotasisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 4);
            Row fovisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 5);
            Row aportacionisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 6);

            Cell Celdasumas = sumapercepciones.createCell(0);
            Celdasumas.setCellStyle(datosEstilo);
            Celdasumas.setCellValue("TOTAL");

            Cell Celdasumap1 = sumapercepciones.createCell(3);
            Celdasumap1.setCellStyle(datosEstiloMoneda);
            Celdasumap1.setCellFormula("SUM(D10:D" + numFilaDatosCuotasYAportaciones + ")");

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("RelacionDeConceptosResumen" + qnaactual + claveparainforme + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformedispersion1(Reporte reporte) {
        Connection con = getConexion();
        String claveparainforme = reporte.getClaveReporte();

        String sqlprueba = "SELECT  RFC,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,PLANTEL,CASE TIPO WHEN 'A' THEN categoria ELSE '' END AS puestoadministrativo,CASE TIPO WHEN 'D' THEN categoria ELSE '' END AS puestodocente,'S' as cobra,(P_01+\n"
                + "P_02+\n"
                + "P_03+\n"
                + "P_04+\n"
                + "P_05+\n"
                + "P_06+\n"
                + "P_07+\n"
                + "P_08+\n"
                + "P_09+\n"
                + "P_10+\n"
                + "P_11+\n"
                + "P_12+\n"
                + "P_13+\n"
                + "P_14+\n"
                + "P_15+\n"
                + "P_16+\n"
                + "P_17+\n"
                + "P_18+\n"
                + "P_19+\n"
                + "P_20+\n"
                + "P_21+\n"
                + "P_22+\n"
                + "P_23+\n"
                + "P_24+\n"
                + "P_25+\n"
                + "P_26+\n"
                + "P_27+\n"
                + "P_28+\n"
                + "P_29+\n"
                + "P_30+\n"
                + "P_31+\n"
                + "P_32+\n"
                + "P_33+\n"
                + "P_34+\n"
                + "P_35+\n"
                + "P_36+\n"
                + "P_37+\n"
                + "P_38+\n"
                + "P_39+\n"
                + "P_40+\n"
                + "P_41+\n"
                + "P_42+\n"
                + "P_43+\n"
                + "P_44+\n"
                + "P_45+\n"
                + "P_46+\n"
                + "P_47+\n"
                + "P_48+\n"
                + "P_49+\n"
                + "P_50+\n"
                + "P_01A+P_01B+P_01C+P_01D+P_01E+P_PE+P_PG\n"
                + ")AS percepciones,(D_01+\n"
                + "D_02+\n"
                + "D_03+\n"
                + "D_04+\n"
                + "D_05+\n"
                + "D_06+\n"
                + "D_07+\n"
                + "D_08+\n"
                + "D_09+\n"
                + "D_10+\n"
                + "D_11+\n"
                + "D_12+\n"
                + "D_13+\n"
                + "D_14+\n"
                + "D_15+\n"
                + "D_16+\n"
                + "D_17+\n"
                + "D_18+\n"
                + "D_19+\n"
                + "D_20+\n"
                + "D_21+\n"
                + "D_22+\n"
                + "D_23+\n"
                + "D_24+\n"
                + "D_25+\n"
                + "D_26+\n"
                + "D_27+\n"
                + "D_28+\n"
                + "D_29+\n"
                + "D_30+\n"
                + "D_31+\n"
                + "D_32+\n"
                + "D_33+\n"
                + "D_34+\n"
                + "D_35+\n"
                + "D_36+\n"
                + "D_37+\n"
                + "D_38+\n"
                + "D_39+\n"
                + "D_40+\n"
                + "D_41+\n"
                + "D_42+\n"
                + "D_43+\n"
                + "D_44+\n"
                + "D_45+\n"
                + "D_46+\n"
                + "D_47+\n"
                + "D_48+\n"
                + "D_49+\n"
                + "D_50+D_CB\n"
                + ")AS deducciones,(P_01+\n"
                + "P_02+\n"
                + "P_03+\n"
                + "P_04+\n"
                + "P_05+\n"
                + "P_06+\n"
                + "P_07+\n"
                + "P_08+\n"
                + "P_09+\n"
                + "P_10+\n"
                + "P_11+\n"
                + "P_12+\n"
                + "P_13+\n"
                + "P_14+\n"
                + "P_15+\n"
                + "P_16+\n"
                + "P_17+\n"
                + "P_18+\n"
                + "P_19+\n"
                + "P_20+\n"
                + "P_21+\n"
                + "P_22+\n"
                + "P_23+\n"
                + "P_24+\n"
                + "P_25+\n"
                + "P_26+\n"
                + "P_27+\n"
                + "P_28+\n"
                + "P_29+\n"
                + "P_30+\n"
                + "P_31+\n"
                + "P_32+\n"
                + "P_33+\n"
                + "P_34+\n"
                + "P_35+\n"
                + "P_36+\n"
                + "P_37+\n"
                + "P_38+\n"
                + "P_39+\n"
                + "P_40+\n"
                + "P_41+\n"
                + "P_42+\n"
                + "P_43+\n"
                + "P_44+\n"
                + "P_45+\n"
                + "P_46+\n"
                + "P_47+\n"
                + "P_48+\n"
                + "P_49+\n"
                + "P_50+\n"
                + "P_01A+P_01B+P_01C+P_01D+P_01E+P_PE+P_PG\n"
                + ")- (D_01+\n"
                + "D_02+\n"
                + "D_03+\n"
                + "D_04+\n"
                + "D_05+\n"
                + "D_06+\n"
                + "D_07+\n"
                + "D_08+\n"
                + "D_09+\n"
                + "D_10+\n"
                + "D_11+\n"
                + "D_12+\n"
                + "D_13+\n"
                + "D_14+\n"
                + "D_15+\n"
                + "D_16+\n"
                + "D_17+\n"
                + "D_18+\n"
                + "D_19+\n"
                + "D_20+\n"
                + "D_21+\n"
                + "D_22+\n"
                + "D_23+\n"
                + "D_24+\n"
                + "D_25+\n"
                + "D_26+\n"
                + "D_27+\n"
                + "D_28+\n"
                + "D_29+\n"
                + "D_30+\n"
                + "D_31+\n"
                + "D_32+\n"
                + "D_33+\n"
                + "D_34+\n"
                + "D_35+\n"
                + "D_36+\n"
                + "D_37+\n"
                + "D_38+\n"
                + "D_39+\n"
                + "D_40+\n"
                + "D_41+\n"
                + "D_42+\n"
                + "D_43+\n"
                + "D_44+\n"
                + "D_45+\n"
                + "D_46+\n"
                + "D_47+\n"
                + "D_48+\n"
                + "D_49+\n"
                + "D_50+D_CB\n"
                + ")AS neto FROM hojaisp WHERE numeroquincena = ?";

        //String sqlprueba = "select rfc,empleado,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,TIPO,"
        //      + "categoria," "P_01 as cantidad from hojaisp where numeroquincena = ? and " +claveparainforme+ " > 0";
        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("RelacionDeConceptosResumen");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);
        Row rowencabezado7 = hojareporte.createRow(6);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        row.createCell(0).setCellValue("RFC");
        row.createCell(1).setCellValue("NOMBRE");
        row.createCell(2).setCellValue("CLAVE");
        row.createCell(3).setCellValue("PLANTEL");
        row.createCell(4).setCellValue("PUESTO/ADMINISTRATIVO");
        row.createCell(5).setCellValue("PUESTO/DOCENTE");
        row.createCell(6).setCellValue("PERCEPCION");
        row.createCell(7).setCellValue("DEDUCCION");
        row.createCell(8).setCellValue("NETO");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            //psinformestrabajadorescotizantes.setString(1, reporte.getClaveReporte());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaEmpleado = filaDatosCuotasYAportaciones.createCell(0);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rstrabajadorescotizantez.getString("RFC"));
                    Cell CeldaRfc = filaDatosCuotasYAportaciones.createCell(1);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rstrabajadorescotizantez.getString("nombre"));
                    Cell CeldaCantidad = filaDatosCuotasYAportaciones.createCell(2);
                    CeldaCantidad.setCellStyle(datosEstiloMoneda);
                    CeldaCantidad.setCellValue(rstrabajadorescotizantez.getString("plantel"));
                    Cell CeldaPuestoA = filaDatosCuotasYAportaciones.createCell(3);
                    CeldaPuestoA.setCellStyle(datosEstiloMoneda);
                    CeldaPuestoA.setCellValue(rstrabajadorescotizantez.getString("puestoadministrativo"));

                    Cell CeldaPuestoD = filaDatosCuotasYAportaciones.createCell(4);
                    CeldaPuestoD.setCellStyle(datosEstiloMoneda);
                    CeldaPuestoD.setCellValue(rstrabajadorescotizantez.getString("puestodocente"));

                    Cell CeldaCobra = filaDatosCuotasYAportaciones.createCell(5);
                    CeldaCobra.setCellStyle(datosEstiloMoneda);
                    CeldaCobra.setCellValue(rstrabajadorescotizantez.getString("cobra"));

                    Cell CeldaPercep = filaDatosCuotasYAportaciones.createCell(6);
                    CeldaPercep.setCellStyle(datosEstiloMoneda);
                    CeldaPercep.setCellValue(rstrabajadorescotizantez.getDouble("percepciones"));
                    Cell CeldaDeduc = filaDatosCuotasYAportaciones.createCell(7);
                    CeldaDeduc.setCellStyle(datosEstiloMoneda);
                    CeldaDeduc.setCellValue(rstrabajadorescotizantez.getDouble("deducciones"));
                    Cell CeldaNeto = filaDatosCuotasYAportaciones.createCell(8);
                    CeldaNeto.setCellStyle(datosEstiloMoneda);
                    CeldaNeto.setCellValue(rstrabajadorescotizantez.getDouble("neto"));

                }
                numFilaDatosCuotasYAportaciones++;

            }
            int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);
            Row sumapercepciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 3);
            Row cuotasisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 4);
            Row fovisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 5);
            Row aportacionisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 6);

            Cell Celdasumas = sumapercepciones.createCell(0);
            Celdasumas.setCellStyle(datosEstilo);
            Celdasumas.setCellValue("TOTAL");

            Cell Celdasumap1 = sumapercepciones.createCell(3);
            Celdasumap1.setCellStyle(datosEstiloMoneda);
            Celdasumap1.setCellFormula("SUM(D10:D" + numFilaDatosCuotasYAportaciones + ")");

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("Dispersion1" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformedispersion2(Reporte reporte) {
        Connection con = getConexion();
        String claveparainforme = reporte.getClaveReporte();

        String sqlprueba = "SELECT  RFC,PLANTEL,TIPO,NOPUESTO,sum(P_01)as  P_01,sum(P_01A)as  P_01A,sum(P_01B)as  P_01B,sum(P_01C)as  P_01C,sum(P_01D)as  P_01D,\n"
                + "               sum(P_02)as  P_02,\n"
                + "               sum(P_03)as  P_03,\n"
                + "               sum(P_04)as  P_04,\n"
                + "               sum(P_05)as  P_05,\n"
                + "               sum(P_06)as  P_06,\n"
                + "               sum(P_07)as  P_07,\n"
                + "               sum(P_08)as  P_08,\n"
                + "               sum(P_09)as  P_09,\n"
                + "               sum(P_10)as  P_10,\n"
                + "               sum(P_11)as  P_11,\n"
                + "               sum(P_12)as  P_12,\n"
                + "               sum(P_13)as  P_13,\n"
                + "               sum(P_14)as  P_14,\n"
                + "               sum(P_15)as  P_15,\n"
                + "               sum(P_16)as  P_16,\n"
                + "               sum(P_17)as  P_17,\n"
                + "               sum(P_18)as  P_18,\n"
                + "               sum(P_19)as  P_19,\n"
                + "               sum(P_20)as  P_20,\n"
                + "               sum(P_21)as  P_21,\n"
                + "               sum(P_22)as  P_22,\n"
                + "               sum(P_23)as  P_23,\n"
                + "               sum(P_24)as  P_24,\n"
                + "               sum(P_25)as  P_25,\n"
                + "               sum(P_26)as  P_26,\n"
                + "               sum(P_27)as  P_27,\n"
                + "               sum(P_28)as  P_28,\n"
                + "               sum(P_29)as  P_29,\n"
                + "               sum(P_30)as  P_30,\n"
                + "               sum(P_31)as  P_31,\n"
                + "               sum(P_32)as  P_32,\n"
                + "               sum(P_33)as  P_33,\n"
                + "               sum(P_34)as  P_34,\n"
                + "               sum(P_35)as  P_35,\n"
                + "               sum(P_36)as  P_36,\n"
                + "               sum(P_37)as  P_37,\n"
                + "               sum(P_38)as  P_38,\n"
                + "               sum(P_39)as  P_39,\n"
                + "               sum(P_40)as  P_40,\n"
                + "               sum(P_41)as  P_41,\n"
                + "               sum(P_42)as  P_42,\n"
                + "               sum(P_43)as  P_43,\n"
                + "               sum(P_44)as  P_44,\n"
                + "               sum(P_45)as  P_45,\n"
                + "               sum(P_46)as  P_46,\n"
                + "               sum(P_47)as  P_47,\n"
                + "               sum(P_48)as  P_48,\n"
                + "               sum(P_49)as  P_49,\n"
                + "               sum(P_50)as  P_50,sum(P_PE)as  P_PE,sum(P_PG)as  P_PG,sum(D_01)as  D_01,\n"
                + "               sum(D_02)as  D_02,\n"
                + "               sum(D_03)as  D_03,\n"
                + "               sum(D_04)as  D_04,\n"
                + "               sum(D_05)as  D_05,\n"
                + "               sum(D_06)as  D_06,\n"
                + "               sum(D_07)as  D_07,\n"
                + "               sum(D_08)as  D_08,\n"
                + "               sum(D_09)as  D_09,\n"
                + "               sum(D_10)as  D_10,\n"
                + "               sum(D_11)as  D_11,\n"
                + "               sum(D_12)as  D_12,\n"
                + "               sum(D_13)as  D_13,\n"
                + "               sum(D_14)as  D_14,\n"
                + "               sum(D_15)as  D_15,\n"
                + "               sum(D_16)as  D_16,\n"
                + "               sum(D_17)as  D_17,\n"
                + "               sum(D_18)as  D_18,\n"
                + "               sum(D_19)as  D_19,\n"
                + "               sum(D_20)as  D_20,\n"
                + "               sum(D_21)as  D_21,\n"
                + "               sum(D_22)as  D_22,\n"
                + "               sum(D_23)as  D_23,\n"
                + "               sum(D_24)as  D_24,\n"
                + "               sum(D_25)as  D_25,\n"
                + "               sum(D_26)as  D_26,\n"
                + "               sum(D_27)as  D_27,\n"
                + "               sum(D_28)as  D_28,\n"
                + "               sum(D_29)as  D_29,\n"
                + "               sum(D_30)as  D_30,\n"
                + "               sum(D_31)as  D_31,\n"
                + "               sum(D_32)as  D_32,\n"
                + "               sum(D_33)as  D_33,\n"
                + "               sum(D_34)as  D_34,\n"
                + "               sum(D_35)as  D_35,\n"
                + "               sum(D_36)as  D_36,\n"
                + "               sum(D_37)as  D_37,\n"
                + "               sum(D_38)as  D_38,\n"
                + "               sum(D_39)as  D_39,\n"
                + "               sum(D_40)as  D_40,\n"
                + "               sum(D_41)as  D_41,\n"
                + "               sum(D_42)as  D_42,\n"
                + "               sum(D_43)as  D_43,\n"
                + "               sum(D_44)as  D_44,\n"
                + "               sum(D_45)as  D_45,\n"
                + "               sum(D_46)as  D_46,\n"
                + "               sum(D_47)as  D_47,\n"
                + "               sum(D_48)as  D_48,\n"
                + "               sum(D_49)as  D_49,\n"
                + "               sum(D_50)as  D_50,sum(D_CB) as   D_CB FROM hojaisp WHERE numeroquincena = ? GROUP BY empleado ORDER BY rfc asc,nopuesto asc";

        //String sqlprueba = "select rfc,empleado,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,TIPO,"
        //      + "categoria," "P_01 as cantidad from hojaisp where numeroquincena = ? and " +claveparainforme+ " > 0";
        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("Dispersion2");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);
        Row rowencabezado7 = hojareporte.createRow(6);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        row.createCell(0).setCellValue("RFC");
        row.createCell(1).setCellValue("PLANTEL");
        row.createCell(2).setCellValue("TIPO");
        row.createCell(3).setCellValue("NOPUESTO");
        row.createCell(4).setCellValue("CLAVE");
        row.createCell(5).setCellValue("CANTIDAD");
        row.createCell(6).setCellValue("OBSERVACION");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;
        Double concepto = 0.00;
        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            //psinformestrabajadorescotizantes.setString(1, reporte.getClaveReporte());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 1; a <= numCol; a++) {

                    if (a > 4) {

                        concepto = rstrabajadorescotizantez.getDouble(a);
                        if (concepto > 0) {

                            Cell CeldaRFC = filaDatosCuotasYAportaciones.createCell(0);
                            CeldaRFC.setCellStyle(datosEstilo);
                            CeldaRFC.setCellValue(rstrabajadorescotizantez.getString(1));

                            Cell CeldaPlantel = filaDatosCuotasYAportaciones.createCell(1);
                            CeldaPlantel.setCellStyle(datosEstilo);
                            CeldaPlantel.setCellValue(rstrabajadorescotizantez.getString(2));

                            Cell CeldaTipo = filaDatosCuotasYAportaciones.createCell(2);
                            CeldaTipo.setCellStyle(datosEstilo);
                            CeldaTipo.setCellValue(rstrabajadorescotizantez.getString(3));

                            Cell CeldaNoPuesto = filaDatosCuotasYAportaciones.createCell(3);
                            CeldaNoPuesto.setCellStyle(datosEstilo);
                            CeldaNoPuesto.setCellValue(rstrabajadorescotizantez.getString(4));

                            Cell CeldaNombreCampo = filaDatosCuotasYAportaciones.createCell(4);
                            CeldaNombreCampo.setCellStyle(datosEstilo);
                            CeldaNombreCampo.setCellValue(rstrabajadorescotizantez.getMetaData().getColumnName(a));
                            Double columnValue = rstrabajadorescotizantez.getDouble(a);
                            Cell Celda12DD = filaDatosCuotasYAportaciones.createCell(5);
                            Celda12DD.setCellStyle(datosEstiloMoneda);
                            Celda12DD.setCellValue(columnValue);
                            Cell CeldaOBS = filaDatosCuotasYAportaciones.createCell(6);
                            CeldaOBS.setCellStyle(datosEstilo);
                            CeldaOBS.setCellValue("NO");

                            numFilaDatosCuotasYAportaciones++;
                            filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                        } else {

                        }

                    }
                }
                numFilaDatosCuotasYAportaciones++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("Dispersion2" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformeparatimbrado(Reporte reporte) {
        Connection con = getConexion();
        Connection connota = getConexionnota();

        String numeroperiodo = reporte.getNumeroPeriodo();
        String yearperiodo = reporte.getYearPeriodo();
        int folioinicialCFDI = reporte.getFolioinicialCFDI();
        String tipoNomina = reporte.getTipoNomina();
        String tipoJornada = reporte.getTipoJornada();
        String tipoContrato = reporte.getTipocontrato();
        String tipoRegimen = reporte.getTipoRegimen();
        String riesgoPuesto = reporte.getRiesgoDePuesto();
        String periodicidadPago = reporte.getPeriodicidadDePago();
        String CodigoPostal = reporte.getCodigoPostal();
        String Serie = reporte.getSerie();
        String RegistroPatronal = reporte.getRegistroPatronal();
        String OrigenRecursos = reporte.getOrigenRecursos();

        ////<editor-fold defaultstate="collapsed" desc="consultasSQL">    
        String sqldatosperiodo = "SELECT fechapago,fechainicialpago,fechafinalpago FROM periodos WHERE numeroquincena = ?";

        String sqldatosdelrecibo = "SELECT empleado,NOW(),1200 AS folio,'AH' AS serie,\n"
                + "(P_01+P_01A+P_01B+P_01C+P_01D+P_01E+P_02+	P_03+	P_04+	P_05+	P_06+	P_07+	P_08+	P_09+	P_10+	P_11+	P_12+	P_13+	P_14+	P_15+	P_16+	P_17+	P_18+	P_19+	P_20+	P_21+	P_22+	P_23+	P_24+	P_25+	P_26+	P_27+	P_28+	P_29+	P_30+	P_31+	P_32+	P_33+	P_34+	P_35+	P_36+	P_37+	P_38+	P_39+	P_40+	P_41+	P_42+	P_43+	P_44+	P_45+	P_46+	P_47+P_PG+P_PE)AS subtotal,\n"
                + "(D_01+	D_02+	D_03+	D_04+	D_05+	D_06+	D_07+	D_08+	D_09+	D_10+	D_11+	D_12+	D_13+	D_14+	D_15+	D_16+	D_17+	D_18+	D_19+	D_20+	D_21+	D_22+	D_23+	D_24+	D_25+	D_26+	D_27+	D_28+	D_29+	D_30+	D_31+	D_32+	D_33+	D_34+	D_35+	D_36+	D_37+	D_38+	D_39+	D_40+	D_41+	D_42+	D_43+	D_44+	D_45+	D_46+	D_47+	D_48+	D_49+	D_50+D_CB)AS descuento,\n"
                + "(P_01+P_01A+P_01B+P_01C+P_01D+P_01E+P_02+	P_03+	P_04+	P_05+	P_06+	P_07+	P_08+	P_09+	P_10+	P_11+	P_12+	P_13+	P_14+	P_15+	P_16+	P_17+	P_18+	P_19+	P_20+	P_21+	P_22+	P_23+	P_24+	P_25+	P_26+	P_27+	P_28+	P_29+	P_30+	P_31+	P_32+	P_33+	P_34+	P_35+	P_36+	P_37+	P_38+	P_39+	P_40+	P_41+	P_42+	P_43+	P_44+	P_45+	P_46+	P_47+P_PG+P_PE)-\n"
                + "(D_01+	D_02+	D_03+	D_04+	D_05+	D_06+	D_07+	D_08+	D_09+	D_10+	D_11+	D_12+	D_13+	D_14+	D_15+	D_16+	D_17+	D_18+	D_19+	D_20+	D_21+	D_22+	D_23+	D_24+	D_25+	D_26+	D_27+	D_28+	D_29+	D_30+	D_31+	D_32+	D_33+	D_34+	D_35+	D_36+	D_37+	D_38+	D_39+	D_40+	D_41+	D_42+	D_43+	D_44+	D_45+	D_46+	D_47+	D_48+	D_49+	D_50+D_CB) AS netoD,\n"
                + "'39000' AS lugarexpedicion\n"
                + "FROM hojaicp WHERE numeroquincena = ? group by empleado";
        String sqlreceptor = "SELECT empleado,AP_PAT,AP_MAT,NOMBRE,(SELECT rfc FROM datoscurp WHERE datoscurp.empleado = hojaicp.empleado ) AS RFC FROM hojaicp WHERE numeroquincena = ? group by empleado ";
        String sqlnominaemisor = "SELECT empleado,'0'AS curp,(SELECT RegistroPatronal FROM datosemisor)AS RegistroPatronal,'0' AS "
                + "patronorigen,(SELECT EntidadOrigenRecurso FROM datosemisor)AS EntidadOrigenRecurso,(P_01+P_01A+P_01B+P_01C+P_01D+P_03) AS recursopropio "
                + "FROM hojaicp WHERE numeroquincena = ?";

        String sqlconceptos = "SELECT empleado,(P_01+P_01A+P_01B+P_01C+P_01D+P_01E+P_02+	P_03+	P_04+	P_05+	P_06+	P_07+	P_08+	P_09+	P_10+	P_11+	P_12+	P_13+	P_14+	P_15+	P_16+	P_17+	P_18+	P_19+	P_20+	P_21+	P_22+	P_23+	P_24+	P_25+	P_26+	P_27+	P_28+	P_29+	P_30+	P_31+	P_32+	P_33+	P_34+	P_35+	P_36+	P_37+	P_38+	P_39+	P_40+	P_41+	P_42+	P_43+	P_44+	P_45+	P_46+	P_47+P_PG+P_PE) AS valorunitario, \n"
                + "(P_01+P_01A+P_01B+P_01C+P_01D+P_01E+P_02+	P_03+	P_04+	P_05+	P_06+	P_07+	P_08+	P_09+	P_10+	P_11+	P_12+	P_13+	P_14+	P_15+	P_16+	P_17+	P_18+	P_19+	P_20+	P_21+	P_22+	P_23+	P_24+	P_25+	P_26+	P_27+	P_28+	P_29+	P_30+	P_31+	P_32+	P_33+	P_34+	P_35+	P_36+	P_37+	P_38+	P_39+	P_40+	P_41+	P_42+	P_43+	P_44+	P_45+	P_46+	P_47+P_PG+P_PE) AS descuento\n"
                + "FROM hojaicp WHERE numeroquincena = ? group by empleado";
        String sqlnomina = "SELECT empleado,'O' AS tiponomina,'15/01/2020' AS fechapago,'01/01/2020' AS fechainicialpago,'15/01/2020' AS fechafinal,'15'as diaspagados,\n"
                + "(P_01+P_01A+P_01B+P_01C+P_01D+P_01E+P_02+	P_03+	P_04+	P_05+	P_06+	P_07+	P_08+	P_09+	P_10+	P_11+	P_12+	P_13+	P_14+	P_15+	P_16+	P_17+	P_18+	P_19+	P_20+	P_21+	P_22+	P_23+	P_24+	P_25+	P_26+	P_27+	P_28+	P_29+	P_30+	P_31+	P_32+	P_33+	P_34+	P_35+	P_36+	P_37+	P_38+	P_39+	P_40+	P_41+	P_42+	P_43+	P_44+	P_45+	P_46+	P_47+P_PG+P_PE)AS percepciones,\n"
                + "(D_01+	D_02+	D_03+	D_04+	D_05+	D_06+	D_07+	D_08+	D_09+	D_10+	D_11+	D_12+	D_13+	D_14+	D_15+	D_16+	D_17+	D_18+	D_19+	D_20+	D_21+	D_22+	D_23+	D_24+	D_25+	D_26+	D_27+	D_28+	D_29+	D_30+	D_31+	D_32+	D_33+	D_34+	D_35+	D_36+	D_37+	D_38+	D_39+	D_40+	D_41+	D_42+	D_43+	D_44+	D_45+	D_46+	D_47+	D_48+	D_49+	D_50+D_CB)\n"
                + "AS deducciones\n"
                + " FROM hojaicp WHERE numeroquincena = ? group by empleado";
        String sqlpercepciones = "SELECT empleado,\n"
                + "(P_01+P_01A+P_01B+P_01C+P_01D+P_01E+P_02+	P_03+	P_04+	P_05+	P_06+	P_07+	P_08+	P_09+	P_10+	P_11+	P_12+	P_13+	P_14+	P_15+	P_16+	P_17+	P_18+	P_19+	P_20+	P_21+	P_22+	P_23+	P_24+	P_25+	P_26+	P_27+	P_28+	P_29+	P_30+	P_31+	P_32+	P_33+	P_34+	P_35+	P_36+	P_37+	P_38+	P_39+	P_40+	P_41+	P_42+	P_43+	P_44+	P_45+	P_46+	P_47+P_PG+P_PE)AS totalsueldos,\n"
                + "'00' AS totalseparacionindeminzacion,'00' AS totaljubilacionpensionretiro,SUM(P_01+P_01A+P_01B+P_01C+P_01D+P_01E) AS totalgravado,\n"
                + "(P_02+P_03+	P_04+	P_05+	P_06+	P_07+	P_08+	P_09+	P_10+	P_11+	P_12+	P_13+	P_14+	P_15+	P_16+	P_17+	P_18+	P_19+	P_20+	P_21+	P_22+	P_23+	P_24+	P_25+	P_26+	P_27+	P_28+	P_29+	P_30+	P_31+	P_32+	P_33+	P_34+	P_35+	P_36+	P_37+	P_38+	P_39+	P_40+	P_41+	P_42+	P_43+	P_44+	P_45+	P_46+	P_47+P_PG+P_PE)AS totalexento\n"
                + "FROM hojaicp WHERE numeroquincena = ? GROUP BY empleado";
        String sqlpercepcion = "";
        String sqldeducciones = "SELECT empleado,\n"
                + "(D_02+	D_03+	D_04+	D_05+	D_06+	D_07+	D_08+	D_09+	D_10+	D_11+	D_12+	D_13+	D_14+	D_15+	D_16+	D_17+	D_18+	D_19+	D_20+	D_21+	D_22+	D_23+	D_24+	D_25+	D_26+	D_27+	D_28+	D_29+	D_30+	D_31+	D_32+	D_33+	D_34+	D_35+	D_36+	D_37+	D_38+	D_39+	D_40+	D_41+	D_42+	D_43+	D_44+	D_45+	D_46+	D_47+	D_48+	D_49+	D_50+D_CB)AS totalotrasdeducciones,\n"
                + "D_01 AS totalimpuestosretenidos\n"
                + " FROM hojaicp WHERE numeroquincena = ?";

        String sqldeduccion = "";
        String sqlotrospagos = "";
        String sqlsubsidio = "SELECT empleado,P_14 FROM hojaicp WHERE numeroquincena = ? AND p_14 > 0";
        String sqlconcepto = "select descripcion,claveSat from conceptos where Concepto in (?)";

        ////</editor-fold > 
        ///
        ////<editor-fold defaultstate="collapsed" desc="consultaprueba"> 
        String sqlprueba = "SELECT  empleado,'038' as tipopercepcion,'clave'as clave,'concepto' as concepto,sum(P_01)as  P_01,sum(P_01A)as  P_01A,sum(P_01B)as  P_01B,sum(P_01C)as  P_01C,sum(P_01D)as  P_01D,\n"
                + "               sum(P_02)as  P_02,\n"
                + "               sum(P_03)as  P_03,\n"
                + "               sum(P_04)as  P_04,\n"
                + "               sum(P_05)as  P_05,\n"
                + "               sum(P_06)as  P_06,\n"
                + "               sum(P_07)as  P_07,\n"
                + "               sum(P_08)as  P_08,\n"
                + "               sum(P_09)as  P_09,\n"
                + "               sum(P_10)as  P_10,\n"
                + "               sum(P_11)as  P_11,\n"
                + "               sum(P_12)as  P_12,\n"
                + "               sum(P_13)as  P_13,\n"
                + "               sum(P_14)as  P_14,\n"
                + "               sum(P_15)as  P_15,\n"
                + "               sum(P_16)as  P_16,\n"
                + "               sum(P_17)as  P_17,\n"
                + "               sum(P_18)as  P_18,\n"
                + "               sum(P_19)as  P_19,\n"
                + "               sum(P_20)as  P_20,\n"
                + "               sum(P_21)as  P_21,\n"
                + "               sum(P_22)as  P_22,\n"
                + "               sum(P_23)as  P_23,\n"
                + "               sum(P_24)as  P_24,\n"
                + "               sum(P_25)as  P_25,\n"
                + "               sum(P_26)as  P_26,\n"
                + "               sum(P_27)as  P_27,\n"
                + "               sum(P_28)as  P_28,\n"
                + "               sum(P_29)as  P_29,\n"
                + "               sum(P_30)as  P_30,\n"
                + "               sum(P_31)as  P_31,\n"
                + "               sum(P_32)as  P_32,\n"
                + "               sum(P_33)as  P_33,\n"
                + "               sum(P_34)as  P_34,\n"
                + "               sum(P_35)as  P_35,\n"
                + "               sum(P_36)as  P_36,\n"
                + "               sum(P_37)as  P_37,\n"
                + "               sum(P_38)as  P_38,\n"
                + "               sum(P_39)as  P_39,\n"
                + "               sum(P_40)as  P_40,\n"
                + "               sum(P_41)as  P_41,\n"
                + "               sum(P_42)as  P_42,\n"
                + "               sum(P_43)as  P_43,\n"
                + "               sum(P_44)as  P_44,\n"
                + "               sum(P_45)as  P_45,\n"
                + "               sum(P_46)as  P_46,\n"
                + "               sum(P_47)as  P_47,\n"
                + "                sum(P_PE)as  P_PE,sum(P_PG)as  P_PG FROM hojaicp WHERE numeroquincena = ? GROUP BY empleado ORDER BY rfc asc,nopuesto asc";
        ////</editor-fold > 
        ///
        ////<editor-fold defaultstate="collapsed" desc="consultapruebadeduccion"> 
        String sqlpruebadeduccion = "SELECT  empleado,'038' as tipopercepcion,'clave'as clave,'concepto' as concepto,sum(D_01)as  D_01,\n"
                + "               sum(D_02)as  D_02,\n"
                + "               sum(D_03)as  D_03,\n"
                + "               sum(D_04)as  D_04,\n"
                + "               sum(D_05)as  D_05,\n"
                + "               sum(D_06)as  D_06,\n"
                + "               sum(D_07)as  D_07,\n"
                + "               sum(D_08)as  D_08,\n"
                + "               sum(D_09)as  D_09,\n"
                + "               sum(D_10)as  D_10,\n"
                + "               sum(D_11)as  D_11,\n"
                + "               sum(D_12)as  D_12,\n"
                + "               sum(D_13)as  D_13,\n"
                + "               sum(D_14)as  D_14,\n"
                + "               sum(D_15)as  D_15,\n"
                + "               sum(D_16)as  D_16,\n"
                + "               sum(D_17)as  D_17,\n"
                + "               sum(D_18)as  D_18,\n"
                + "               sum(D_19)as  D_19,\n"
                + "               sum(D_20)as  D_20,\n"
                + "               sum(D_21)as  D_21,\n"
                + "               sum(D_22)as  D_22,\n"
                + "               sum(D_23)as  D_23,\n"
                + "               sum(D_24)as  D_24,\n"
                + "               sum(D_25)as  D_25,\n"
                + "               sum(D_26)as  D_26,\n"
                + "               sum(D_27)as  D_27,\n"
                + "               sum(D_28)as  D_28,\n"
                + "               sum(D_29)as  D_29,\n"
                + "               sum(D_30)as  D_30,\n"
                + "               sum(D_31)as  D_31,\n"
                + "               sum(D_32)as  D_32,\n"
                + "               sum(D_33)as  D_33,\n"
                + "               sum(D_34)as  D_34,\n"
                + "               sum(D_35)as  D_35,\n"
                + "               sum(D_36)as  D_36,\n"
                + "               sum(D_37)as  D_37,\n"
                + "               sum(D_38)as  D_38,\n"
                + "               sum(D_39)as  D_39,\n"
                + "               sum(D_40)as  D_40,\n"
                + "               sum(D_41)as  D_41,\n"
                + "               sum(D_42)as  D_42,\n"
                + "               sum(D_43)as  D_43,\n"
                + "               sum(D_44)as  D_44,\n"
                + "               sum(D_45)as  D_45,\n"
                + "               sum(D_46)as  D_46,\n"
                + "               sum(D_47)as  D_47,sum(D_48)as  D_48,sum(D_49)as  D_49,sum(D_50)as  D_50,\n"
                + "                sum(D_CB)as  D_CB  FROM hojaicp WHERE numeroquincena = ? GROUP BY empleado ORDER BY rfc asc,nopuesto asc";
        ////</editor-fold > 
        /////

        ////<editor-fold defaultstate="collapsed" desc="crearWoorbook"> 
        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("percepcion");
        Sheet datosdelrecibo = book.createSheet("DatosDelRecibo");
        Sheet receptor = book.createSheet("Receptor");
        Sheet conceptos = book.createSheet("conceptos");
        Sheet nomina = book.createSheet("nomina");
        Sheet nominaemisor = book.createSheet("nominaemisor");
        Sheet nominareceptor = book.createSheet("nominareceptor");
        Sheet percepciones = book.createSheet("percepciones");
        Sheet percepcion = book.createSheet("percepcionvacia");
        Sheet horasextra = book.createSheet("horasextra");
        Sheet jubiladopensionretiro = book.createSheet("jubiladopensionretiro");
        Sheet separacionindemnizacion = book.createSheet("separacionindemnizacion");
        Sheet deducciones = book.createSheet("deducciones");
        Sheet deduccion = book.createSheet("deduccion");
        Sheet otrospagos = book.createSheet("otrospagos");
        Sheet subsidio = book.createSheet("subsidio");
        Sheet compensacionsaldoafavor = book.createSheet("compensacionsaldoafavor");
        Sheet incapacidades = book.createSheet("incapacidades");

        Row row = hojareporte.createRow(9);
        Row rowdatosdelrecibo = datosdelrecibo.createRow(9);
        Row rowreceptor = receptor.createRow(9);
        Row rowconceptos = conceptos.createRow(9);
        Row rownomina = nomina.createRow(9);
        Row rownominaemisor = nominaemisor.createRow(9);
        Row rownominareceptor = nominareceptor.createRow(9);
        Row rowpercepciones = percepciones.createRow(9);
        Row rowpercepcion = percepcion.createRow(9);
        Row rowdeducciones = deducciones.createRow(9);
        Row rowdeduccion = deduccion.createRow(9);
        Row rowotrospagos = otrospagos.createRow(9);
        Row rowsubsidio = subsidio.createRow(9);

        rowdatosdelrecibo.createCell(0).setCellValue("Id Rec");
        rowdatosdelrecibo.createCell(1).setCellValue("Fecha");
        rowdatosdelrecibo.createCell(2).setCellValue("Serie");
        rowdatosdelrecibo.createCell(3).setCellValue("Folio");
        rowdatosdelrecibo.createCell(4).setCellValue("Subtotal");
        rowdatosdelrecibo.createCell(5).setCellValue("Descuento");
        rowdatosdelrecibo.createCell(6).setCellValue("Total");
        rowdatosdelrecibo.createCell(7).setCellValue("LugarExpedicion");
        rowdatosdelrecibo.createCell(8).setCellValue("Confirmacion");
        rowdatosdelrecibo.createCell(9).setCellValue("CorreoElectronico");

        rowreceptor.createCell(0).setCellValue("Id Rec");
        rowreceptor.createCell(1).setCellValue("paterno");
        rowreceptor.createCell(2).setCellValue("materno");
        rowreceptor.createCell(3).setCellValue("nombre");
        rowreceptor.createCell(4).setCellValue("rfc");

        rowconceptos.createCell(0).setCellValue("Id Rec");
        rowconceptos.createCell(1).setCellValue("ValorUnitario*");
        rowconceptos.createCell(2).setCellValue("Descuento");

        rownomina.createCell(0).setCellValue("Id Rec");
        rownomina.createCell(1).setCellValue("C_tipoNomina");
        rownomina.createCell(2).setCellValue("FechaPago");
        rownomina.createCell(3).setCellValue("FechaInicialPago");
        rownomina.createCell(4).setCellValue("FechaFinalPago");
        rownomina.createCell(5).setCellValue("NumdiasPagados");
        rownomina.createCell(6).setCellValue("TotalPercepciones");
        rownomina.createCell(7).setCellValue("TotalDeducciones");
        rownomina.createCell(8).setCellValue("TotalOtrosPagos");

        rownominaemisor.createCell(0).setCellValue("Id Rec");
        rownominaemisor.createCell(1).setCellValue("Curp");
        rownominaemisor.createCell(2).setCellValue("RegistroPatronal");
        rownominaemisor.createCell(3).setCellValue("RFcPatronOrigen");
        rownominaemisor.createCell(4).setCellValue("EntidadSNCFOrigenRecurso");
        rownominaemisor.createCell(5).setCellValue("EntidadSNFCMontoRecursoPropio");

        rownominareceptor.createCell(0).setCellValue("Id Rec");
        rownominareceptor.createCell(1).setCellValue("Curp");
        rownominareceptor.createCell(2).setCellValue("NumSeguridadSocial");
        rownominareceptor.createCell(3).setCellValue("FechaInicioRelacionLaboral");
        rownominareceptor.createCell(4).setCellValue("Antiguedad");
        rownominareceptor.createCell(5).setCellValue("C_tipoContrato");
        rownominareceptor.createCell(6).setCellValue("Sindicalizado");
        rownominareceptor.createCell(7).setCellValue("c_tipoJornada");
        rownominareceptor.createCell(8).setCellValue("C_tipoRegimen");
        rownominareceptor.createCell(9).setCellValue("NumeroEmpleado");
        rownominareceptor.createCell(10).setCellValue("Departamento");
        rownominareceptor.createCell(11).setCellValue("Puesto");
        rownominareceptor.createCell(12).setCellValue("c_RiesgpPuesto");
        rownominareceptor.createCell(13).setCellValue("C_PeriodicidadPago");
        rownominareceptor.createCell(14).setCellValue("c_Banco");
        rownominareceptor.createCell(15).setCellValue("CuentaBancaria");
        rownominareceptor.createCell(16).setCellValue("SalarioBaseCotApor");
        rownominareceptor.createCell(17).setCellValue("SalarioDiarioIntegrado");
        rownominareceptor.createCell(18).setCellValue("c_ClaveEntFed.");

        rowpercepciones.createCell(0).setCellValue("Id Rec");
        rowpercepciones.createCell(1).setCellValue("TotalSueldos");
        rowpercepciones.createCell(2).setCellValue("TotalSeparacionIndemizacion");
        rowpercepciones.createCell(3).setCellValue("TotalJubilacionPensionRetiro");
        rowpercepciones.createCell(4).setCellValue("TotalGravado");
        rowpercepciones.createCell(5).setCellValue("TotalExcento");

        rowpercepcion.createCell(0).setCellValue("Id Rec");
        rowpercepcion.createCell(1).setCellValue("c_TipoPercepcion");
        rowpercepcion.createCell(2).setCellValue("Clave");
        rowpercepcion.createCell(3).setCellValue("Concepto");
        rowpercepcion.createCell(4).setCellValue("ImporteGravado");
        rowpercepcion.createCell(5).setCellValue("ImporteExento");
        rowpercepcion.createCell(6).setCellValue("AccionesOTitulosValorMercado");
        rowpercepcion.createCell(7).setCellValue("AccionesOTitulosPrecioAOtorgarse");

        rowdeducciones.createCell(0).setCellValue("Id Rec");
        rowdeducciones.createCell(1).setCellValue("TotalOtrasDeducciones");
        rowdeducciones.createCell(2).setCellValue("TotalImpuestosRetenidos");

        rowdeduccion.createCell(0).setCellValue("Id Rec");
        rowdeduccion.createCell(1).setCellValue("c_TipoDeduccion*");
        rowdeduccion.createCell(2).setCellValue("Clave");
        rowdeduccion.createCell(3).setCellValue("Concepto");
        rowdeduccion.createCell(4).setCellValue("Importe");
        rowdeduccion.createCell(5).setCellValue("TotalImpuestosRetenidos");

        rowotrospagos.createCell(0).setCellValue("Id Rec");
        rowotrospagos.createCell(1).setCellValue("c_TipoOtroPago*");
        rowotrospagos.createCell(2).setCellValue("Clave");
        rowotrospagos.createCell(3).setCellValue("Concepto");
        rowotrospagos.createCell(4).setCellValue("Importe");

        rowsubsidio.createCell(0).setCellValue("Id Rec");
        rowsubsidio.createCell(1).setCellValue("SubsidioCausado*");

        row.createCell(0).setCellValue("Id Rec");
        row.createCell(1).setCellValue("C_TipoPercepcion");
        row.createCell(2).setCellValue("Clave");
        row.createCell(3).setCellValue("Concepto");
        row.createCell(4).setCellValue("Importe Gravado");
        row.createCell(5).setCellValue("ImporteExento");
        row.createCell(6).setCellValue("OBSERVACION");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);
        ////</editor-fold> 
        /////////////////

        /////////////Consulta a la base de datos
        ////<editor-fold defaultstate="collapsed" desc="Variables ps y Rs"> 
        PreparedStatement psdatosdelrecibo = null;
        PreparedStatement psreceptor = null;
        PreparedStatement psconceptos = null;
        PreparedStatement psnomina = null;
        PreparedStatement psnominaemisor = null;
        PreparedStatement psnominareceptor = null;
        PreparedStatement pspercepciones = null;
        PreparedStatement pspercepcion = null;
        PreparedStatement psdeducciones = null;
        PreparedStatement psdeduccion = null;
        PreparedStatement psotrospagos = null;
        PreparedStatement pssubsidio = null;
        PreparedStatement psconcepto = null;
        PreparedStatement psperiodo = null;
        ResultSet rsdatosdelrecibo = null;
        ResultSet rsreceptor = null;
        ResultSet rsconceptos = null;
        ResultSet rsnomina = null;
        ResultSet rsnominaemisor = null;
        ResultSet rsnominareceptor = null;
        ResultSet rspercepciones = null;
        ResultSet rspercepcion = null;
        ResultSet rsdeducciones = null;
        ResultSet rsdeduccion = null;
        ResultSet rsotrospagos = null;
        ResultSet rssubsidio = null;
        ResultSet rsconcepto = null;
        ResultSet rsperiodo = null;
        int numFilaDatosdatosdelrecibo = 10;
        int numFilaDatosreceptor = 10;
        int numFilaDatosconceptos = 10;
        int numFilaDatosnomina = 10;
        int numFilaDatosnominaemisor = 10;
        int numFilaDatosnominareceptor = 10;
        int numFilaDatospercepciones = 10;
        int numFilaDatospercepcion = 10;
        int numFilaDatosdeducciones = 10;
        int numFilaDatosdeduccion = 10;
        int numFilaDatosotrospagos = 10;
        int numFilaDatossubsidio = 10;
        String descripcionConcepto = "";
        String claveSat = "";
        ////</editor-fold> 
        ////

        //////////////////////////////////////////////////////////////
        ////<editor-fold defaultstate="collapsed" desc="datosdelrecibo"> 
        try {
            psdatosdelrecibo = connota.prepareStatement(sqldatosdelrecibo);
            psdatosdelrecibo.setString(1, reporte.getPeriodo());
            rsdatosdelrecibo = psdatosdelrecibo.executeQuery();
            int numCol = rsdatosdelrecibo.getMetaData().getColumnCount();

            while (rsdatosdelrecibo.next()) {

                Row filaDatosdatosdelrecibo = datosdelrecibo.createRow(numFilaDatosdatosdelrecibo);
                for (int a = 1; a <= numCol; a++) {

                    Cell CeldaCampo = filaDatosdatosdelrecibo.createCell(a - 1);
                    CeldaCampo.setCellStyle(datosEstilo);
                    if (a == 3) {
                        CeldaCampo.setCellValue(Serie);
                    } else if (a == 4) {
                        CeldaCampo.setCellValue(folioinicialCFDI);
                    } else if (a == 8) {
                        CeldaCampo.setCellValue(CodigoPostal);
                    } else {
                        CeldaCampo.setCellValue(rsdatosdelrecibo.getString(a));
                    }
                }
                numFilaDatosdatosdelrecibo++;
                folioinicialCFDI++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        ////</editor-fold > 
        //////
        ////<editor-fold defaultstate="collapsed" desc="receptor"> 

        try {
            psreceptor = connota.prepareStatement(sqlreceptor);
            psreceptor.setString(1, reporte.getPeriodo());
            rsreceptor = psreceptor.executeQuery();
            int numCol = rsreceptor.getMetaData().getColumnCount();

            while (rsreceptor.next()) {

                Row filaDatosreceptor = receptor.createRow(numFilaDatosreceptor);
                for (int a = 1; a <= numCol; a++) {

                    Cell CeldaCampo = filaDatosreceptor.createCell(a - 1);
                    CeldaCampo.setCellStyle(datosEstilo);
                    CeldaCampo.setCellValue(rsreceptor.getString(a));
                }
                numFilaDatosreceptor++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////</editor-fold > 
        ////////////
        ////<editor-fold defaultstate="collapsed" desc="conceptos"> 
        try {
            psconceptos = connota.prepareStatement(sqlconceptos);
            psconceptos.setString(1, reporte.getPeriodo());
            rsconceptos = psconceptos.executeQuery();
            int numCol = rsconceptos.getMetaData().getColumnCount();

            while (rsconceptos.next()) {

                Row filaDatosconceptos = conceptos.createRow(numFilaDatosconceptos);
                for (int a = 1; a <= numCol; a++) {

                    Cell CeldaCampo = filaDatosconceptos.createCell(a - 1);
                    CeldaCampo.setCellStyle(datosEstilo);
                    CeldaCampo.setCellValue(rsconceptos.getString(a));
                }
                numFilaDatosconceptos++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        ////</editor-fold > 
        ///

        ////<editor-fold defaultstate="collapsed" desc="nomina"> 
        try {

            /////////////////////NOMINA PERIODO
            try {
                psperiodo = connota.prepareStatement(sqldatosperiodo);
                psperiodo.setString(1, reporte.getNumeroPeriodo());
                rsperiodo = psperiodo.executeQuery();
                int numCol = rsperiodo.getMetaData().getColumnCount();

                while (rsperiodo.next()) {
                    fechapago = rsperiodo.getString("fechapago") + reporte.getYearPeriodo();
                    fechainiciopago = rsperiodo.getString("fechainicialpago") + reporte.getYearPeriodo();
                    fechafinalpago = rsperiodo.getString("fechafinalpago") + reporte.getYearPeriodo();
                    fechafinalantiguedad = reporte.getYearPeriodo() + "/" + (rsperiodo.getString("fechafinalpago").substring(3, 5)) + "/" + (rsperiodo.getString("fechafinalpago").substring(0, 2));

                }

            } catch (SQLException ex) {
                Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
            }
            /////////////TERMINA NOMINA PERIODO

            psnomina = connota.prepareStatement(sqlnomina);
            psnomina.setString(1, reporte.getPeriodo());
            rsnomina = psnomina.executeQuery();
            int numCol = rsnomina.getMetaData().getColumnCount();

            while (rsnomina.next()) {

                Row filaDatosnomina = nomina.createRow(numFilaDatosnomina);
                for (int a = 1; a <= numCol; a++) {

                    Cell CeldaCampo = filaDatosnomina.createCell(a - 1);
                    CeldaCampo.setCellStyle(datosEstilo);
                    if (a == 2) {
                        CeldaCampo.setCellValue(tipoNomina);
                    } else if (a == 3) {
                        CeldaCampo.setCellValue(fechapago);
                    } else if (a == 4) {
                        CeldaCampo.setCellValue(fechainiciopago);
                    } else if (a == 5) {
                        CeldaCampo.setCellValue(fechafinalpago);
                    } else {
                        CeldaCampo.setCellValue(rsnomina.getString(a));
                    }
                }
                numFilaDatosnomina++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        ////</editor-fold > 
        ///

        ////<editor-fold defaultstate="collapsed" desc="nominareceptor"> 
        try {

            //String fechafinalprueba = "2020/01/15";
            String sqlnominareceptor = "SELECT empleado,(SELECT curp FROM datoscurp WHERE datoscurp.empleado = hojaicp.empleado) AS curp,cedulaimss,fechaold ,CONCAT('P',TIMESTAMPDIFF(WEEK,STR_TO_DATE(fechaold,'%Y/%m/%d'),? ),'W') as antiguedad ,\n"
                    + "'03' AS tipocontrato,case tiposin when 0 then 'NO' ELSE 'SI' END AS sindicalizado,'01' AS tipojornada,'02' AS tiporegimen,empleado,\n"
                    + "plantel,categoria,'1' AS riegopuesto,'99' AS periodicidadpago,'002' AS banco,'00' AS cuentabancaria,(SUM(P_01+P_01A+P_01B+P_01C+P_01D+P_01E+P_03)/15)*1.0452 AS salariobase,(SUM(P_01+P_01A+P_01B+P_01C+P_01D+P_01E+P_03)/15)*1.0452 AS salariodiariointegrado,\n"
                    + "'GRO' AS cveentidad\n"
                    + "FROM hojaicp WHERE numeroquincena = ? group by empleado";

            psnominareceptor = connota.prepareStatement(sqlnominareceptor);
            psnominareceptor.setString(1, fechafinalantiguedad);
            psnominareceptor.setString(2, reporte.getPeriodo());
            rsnominareceptor = psnominareceptor.executeQuery();
            int numCol = rsnominareceptor.getMetaData().getColumnCount();

            while (rsnominareceptor.next()) {

                Row filaDatosnominareceptor = nominareceptor.createRow(numFilaDatosnominareceptor);
                for (int a = 1; a <= numCol; a++) {

                    Cell CeldaCampo = filaDatosnominareceptor.createCell(a - 1);
                    CeldaCampo.setCellStyle(datosEstilo);
                    if (a == 6) {
                        CeldaCampo.setCellValue(tipoContrato);
                    } else if (a == 8) {
                        CeldaCampo.setCellValue(tipoJornada);

                    } else if (a == 9) {
                        CeldaCampo.setCellValue(tipoRegimen);

                    } else if (a == 13) {
                        CeldaCampo.setCellValue(riesgoPuesto);

                    } else if (a == 14) {
                        CeldaCampo.setCellValue(periodicidadPago);

                    } else {
                        CeldaCampo.setCellValue(rsnominareceptor.getString(a));
                    }
                }
                numFilaDatosnominareceptor++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////</editor-fold> 
        ///
        ////<editor-fold defaultstate="collapsed" desc="percepciones"> 
        try {
            pspercepciones = connota.prepareStatement(sqlpercepciones);
            pspercepciones.setString(1, reporte.getPeriodo());
            rspercepciones = pspercepciones.executeQuery();
            int numCol = rspercepciones.getMetaData().getColumnCount();

            while (rspercepciones.next()) {

                Row filaDatospercepciones = percepciones.createRow(numFilaDatospercepciones);
                for (int a = 1; a <= numCol; a++) {

                    Cell CeldaCampo = filaDatospercepciones.createCell(a - 1);
                    CeldaCampo.setCellStyle(datosEstilo);
                    CeldaCampo.setCellValue(rspercepciones.getString(a));
                }
                numFilaDatospercepciones++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////</editor-fold > 
        ///
        ////<editor-fold defaultstate="collapsed" desc="deducciones"> 
        try {
            psdeducciones = connota.prepareStatement(sqldeducciones);
            psdeducciones.setString(1, reporte.getPeriodo());
            rsdeducciones = psdeducciones.executeQuery();
            int numCol = rsdeducciones.getMetaData().getColumnCount();

            while (rsdeducciones.next()) {

                Row filaDatosdeducciones = deducciones.createRow(numFilaDatosdeducciones);
                for (int a = 1; a <= numCol; a++) {

                    Cell CeldaCampo = filaDatosdeducciones.createCell(a - 1);
                    CeldaCampo.setCellStyle(datosEstilo);
                    CeldaCampo.setCellValue(rsdeducciones.getString(a));
                }
                numFilaDatosdeducciones++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        ////</editor-fold > 
        ////

        ////<editor-fold defaultstate="collapsed" desc="percepcion"> 
        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;
        Double concepto = 0.00;
        String nombrecampo;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = connota.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 1; a <= numCol; a++) {

                    if (a > 4) {

                        concepto = rstrabajadorescotizantez.getDouble(a);
                        if (concepto > 0) {

                            //Celda empleado
                            Cell CeldaRFC = filaDatosCuotasYAportaciones.createCell(0);
                            CeldaRFC.setCellStyle(datosEstilo);
                            CeldaRFC.setCellValue(rstrabajadorescotizantez.getString(1));

                            nombrecampo = rstrabajadorescotizantez.getMetaData().getColumnName(a);
                            try {
                                psconcepto = connota.prepareStatement(sqlconcepto);
                                psconcepto.setString(1, nombrecampo);
                                rsconcepto = psconcepto.executeQuery();
                                //int numCol = rsconcepto.getMetaData().getColumnCount();
                                while (rsconcepto.next()) {

                                    descripcionConcepto = rsconcepto.getString(1);
                                    claveSat = rsconcepto.getString(2);
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            Cell CeldaTipoPercepcion = filaDatosCuotasYAportaciones.createCell(1);
                            CeldaTipoPercepcion.setCellStyle(datosEstilo);
                            CeldaTipoPercepcion.setCellValue(claveSat);

                            Cell CeldaNombreCampo = filaDatosCuotasYAportaciones.createCell(2);
                            CeldaNombreCampo.setCellStyle(datosEstilo);
                            CeldaNombreCampo.setCellValue(rstrabajadorescotizantez.getMetaData().getColumnName(a));

                            Cell CeldaNombreConcepto = filaDatosCuotasYAportaciones.createCell(3);
                            CeldaNombreConcepto.setCellStyle(datosEstilo);
                            CeldaNombreConcepto.setCellValue(descripcionConcepto);

                            if (nombrecampo.equals("P_01") || nombrecampo.equals("P_01A") || nombrecampo.equals("P_01B")
                                    || nombrecampo.equals("P_01C") || nombrecampo.equals("P_01D")) {
                                Double columnValue = rstrabajadorescotizantez.getDouble(a);
                                Cell CeldaGravado = filaDatosCuotasYAportaciones.createCell(4);
                                CeldaGravado.setCellStyle(datosEstiloMoneda);
                                CeldaGravado.setCellValue(columnValue);
                                Cell CeldaExento = filaDatosCuotasYAportaciones.createCell(5);
                                CeldaExento.setCellStyle(datosEstiloMoneda);
                                CeldaExento.setCellValue(0);
                            } else {
                                Double columnValue = rstrabajadorescotizantez.getDouble(a);
                                Cell CeldaGravado = filaDatosCuotasYAportaciones.createCell(4);
                                CeldaGravado.setCellStyle(datosEstiloMoneda);
                                CeldaGravado.setCellValue(0);
                                Cell CeldaExento = filaDatosCuotasYAportaciones.createCell(5);
                                CeldaExento.setCellStyle(datosEstiloMoneda);
                                CeldaExento.setCellValue(columnValue);

                            }
                            /*Double columnValue = rstrabajadorescotizantez.getDouble(a);
                            Cell Celda12DD = filaDatosCuotasYAportaciones.createCell(4);
                            Celda12DD.setCellStyle(datosEstiloMoneda);
                            Celda12DD.setCellValue(columnValue);
                           
                            Cell CeldaImporteExento = filaDatosCuotasYAportaciones.createCell(5);
                            CeldaImporteExento.setCellStyle(datosEstiloMoneda);
                            CeldaImporteExento.setCellValue(columnValue);*/

 /* Cell CeldaOBS = filaDatosCuotasYAportaciones.createCell(7);
                            CeldaOBS.setCellStyle(datosEstilo);
                            CeldaOBS.setCellValue("NO");*/
                            numFilaDatosCuotasYAportaciones++;
                            filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                        } else {

                        }

                    }
                }
                numFilaDatosCuotasYAportaciones++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////</editor-fold > 
        ///
        ////<editor-fold defaultstate="collapsed" desc="deduccion"> 
        try {
            psdeduccion = connota.prepareStatement(sqlpruebadeduccion);
            psdeduccion.setString(1, reporte.getPeriodo());
            rsdeduccion = psdeduccion.executeQuery();

            int numCol = rsdeduccion.getMetaData().getColumnCount();

            while (rsdeduccion.next()) {

                Row filaDatosCuotasYAportacionesDeduccion = deduccion.createRow(numFilaDatosdeduccion);
                for (int a = 1; a <= numCol; a++) {

                    if (a > 4) {

                        concepto = rsdeduccion.getDouble(a);
                        if (concepto > 0) {

                            //Celda empleado
                            Cell CeldaRFC = filaDatosCuotasYAportacionesDeduccion.createCell(0);
                            CeldaRFC.setCellStyle(datosEstilo);
                            CeldaRFC.setCellValue(rsdeduccion.getString(1));

                            nombrecampo = rsdeduccion.getMetaData().getColumnName(a);
                            try {
                                psconcepto = connota.prepareStatement(sqlconcepto);
                                psconcepto.setString(1, nombrecampo);
                                rsconcepto = psconcepto.executeQuery();
                                //int numCol = rsconcepto.getMetaData().getColumnCount();
                                while (rsconcepto.next()) {

                                    descripcionConcepto = rsconcepto.getString(1);
                                    claveSat = rsconcepto.getString(2);
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            Cell CeldaTipoPercepcion = filaDatosCuotasYAportacionesDeduccion.createCell(1);
                            CeldaTipoPercepcion.setCellStyle(datosEstilo);
                            CeldaTipoPercepcion.setCellValue(claveSat);

                            Cell CeldaNombreCampo = filaDatosCuotasYAportacionesDeduccion.createCell(2);
                            CeldaNombreCampo.setCellStyle(datosEstilo);
                            CeldaNombreCampo.setCellValue(rsdeduccion.getMetaData().getColumnName(a));

                            Cell CeldaNombreConcepto = filaDatosCuotasYAportacionesDeduccion.createCell(3);
                            CeldaNombreConcepto.setCellStyle(datosEstilo);
                            CeldaNombreConcepto.setCellValue(descripcionConcepto);

                            Double columnValue = rsdeduccion.getDouble(a);
                            Cell Celda12DD = filaDatosCuotasYAportacionesDeduccion.createCell(4);
                            Celda12DD.setCellStyle(datosEstiloMoneda);
                            Celda12DD.setCellValue(columnValue);

                            Cell CeldaImporteExento = filaDatosCuotasYAportacionesDeduccion.createCell(5);
                            CeldaImporteExento.setCellStyle(datosEstiloMoneda);
                            CeldaImporteExento.setCellValue(columnValue);

                            numFilaDatosdeduccion++;
                            filaDatosCuotasYAportacionesDeduccion = deduccion.createRow(numFilaDatosdeduccion);

                        } else {

                        }

                    }
                }
                numFilaDatosdeduccion++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////</editor-fold> 
        //////
        ////<editor-fold defaultstate="collapsed" desc="subsidio"> 
        ////////////////////SUBSIDIO/////////////////////////////////
        try {
            pssubsidio = connota.prepareStatement(sqlsubsidio);
            pssubsidio.setString(1, reporte.getPeriodo());
            rssubsidio = pssubsidio.executeQuery();

            int numCol = rssubsidio.getMetaData().getColumnCount();

            while (rssubsidio.next()) {

                Row filaDatosCuotasYAportacionesSubsidio = subsidio.createRow(numFilaDatossubsidio);
                for (int a = 1; a <= numCol; a++) {

                    Cell CeldaIdRecSubsidio = filaDatosCuotasYAportacionesSubsidio.createCell(0);
                    CeldaIdRecSubsidio.setCellStyle(datosEstilo);
                    CeldaIdRecSubsidio.setCellValue(rssubsidio.getInt(1));

                    Cell CeldaMontoSubsidio = filaDatosCuotasYAportacionesSubsidio.createCell(1);
                    CeldaMontoSubsidio.setCellStyle(datosEstilo);
                    CeldaMontoSubsidio.setCellValue(rssubsidio.getDouble(2));

                }
                numFilaDatossubsidio++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////</editor-fold> 
        ////
        ////<editor-fold defaultstate="collapsed" desc="nominaemisor"> 
        //////////////////////////////////////////////////////////////
        /////////////////////NOMINA EMISOR
        try {
            psnominaemisor = connota.prepareStatement(sqlnominaemisor);
            psnominaemisor.setString(1, reporte.getPeriodo());
            rsnominaemisor = psnominaemisor.executeQuery();

            int numCol = rsnominaemisor.getMetaData().getColumnCount();

            while (rsnominaemisor.next()) {

                Row filaDatosCuotasYAportacionesNominaEmisor = nominaemisor.createRow(numFilaDatosnominaemisor);
                for (int a = 1; a <= numCol; a++) {

                    Cell CeldaIdRecNominaEmisor = filaDatosCuotasYAportacionesNominaEmisor.createCell(0);
                    CeldaIdRecNominaEmisor.setCellStyle(datosEstilo);
                    CeldaIdRecNominaEmisor.setCellValue(rsnominaemisor.getInt(1));

                    Cell CeldaCurp = filaDatosCuotasYAportacionesNominaEmisor.createCell(1);
                    CeldaCurp.setCellStyle(datosEstilo);
                    CeldaCurp.setCellValue(rsnominaemisor.getString(2));

                    Cell CeldaPatronal = filaDatosCuotasYAportacionesNominaEmisor.createCell(2);
                    CeldaPatronal.setCellStyle(datosEstilo);
                    CeldaPatronal.setCellValue(RegistroPatronal);
                    //CeldaPatronal.setCellValue(rsnominaemisor.getString(3));

                    Cell CeldaRfcOrigen = filaDatosCuotasYAportacionesNominaEmisor.createCell(3);
                    CeldaRfcOrigen.setCellStyle(datosEstilo);
                    CeldaRfcOrigen.setCellValue(rsnominaemisor.getString(4));

                    Cell CeldaOrigenRecurso = filaDatosCuotasYAportacionesNominaEmisor.createCell(4);
                    CeldaOrigenRecurso.setCellStyle(datosEstilo);
                    CeldaOrigenRecurso.setCellValue(OrigenRecursos);
                    //CeldaOrigenRecurso.setCellValue(rsnominaemisor.getString(5));

                    Cell CeldaMontoRecurso = filaDatosCuotasYAportacionesNominaEmisor.createCell(5);
                    CeldaMontoRecurso.setCellStyle(datosEstilo);
                    CeldaMontoRecurso.setCellValue(rsnominaemisor.getString(6));

                }
                numFilaDatosnominaemisor++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        ////</editor-fold> 
        ///    

        ////<editor-fold defaultstate="collapsed" desc="guardaArchivo"> 
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("Timbrado" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////</editor-fold > 
        /////
    }

    public void exportarinformesrelacioncreditoshipotecarios(Reporte reporte) {
        Connection con = getConexion();
        String claveparainforme = reporte.getClaveReporte();

        String sqlprueba = "select rfc,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,D_09,D_06,(D_09+D_06)as total FROM hojaisp WHERE numeroquincena = ? and (D_06 > 0"
                + " OR D_09 > 0)";

        //String sqlprueba = "select rfc,empleado,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,TIPO,"
        //      + "categoria," "P_01 as cantidad from hojaisp where numeroquincena = ? and " +claveparainforme+ " > 0";
        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("CreditosHipotecarios");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);
        Row rowencabezado7 = hojareporte.createRow(6);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        //row.createCell(0).setCellValue("No.");
        row.createCell(0).setCellValue("RFC");
        row.createCell(1).setCellValue("NOMBRE");
        row.createCell(2).setCellValue("PLANTEL");
        row.createCell(3).setCellValue("D_09");
        row.createCell(4).setCellValue("D_06");
        row.createCell(5).setCellValue("TOTAL");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            //psinformestrabajadorescotizantes.setString(1, reporte.getClaveReporte());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaEmpleado = filaDatosCuotasYAportaciones.createCell(0);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rstrabajadorescotizantez.getString("RFC"));
                    Cell CeldaRfc = filaDatosCuotasYAportaciones.createCell(1);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rstrabajadorescotizantez.getString("nombre"));
                    Cell CeldaCantidad = filaDatosCuotasYAportaciones.createCell(2);
                    CeldaCantidad.setCellStyle(datosEstiloMoneda);
                    CeldaCantidad.setCellValue(rstrabajadorescotizantez.getString("plantel"));
                    Cell CeldaPuestoA = filaDatosCuotasYAportaciones.createCell(3);
                    CeldaPuestoA.setCellStyle(datosEstiloMoneda);
                    CeldaPuestoA.setCellValue(rstrabajadorescotizantez.getDouble("D_09"));

                    Cell CeldaPuestoD = filaDatosCuotasYAportaciones.createCell(4);
                    CeldaPuestoD.setCellStyle(datosEstiloMoneda);
                    CeldaPuestoD.setCellValue(rstrabajadorescotizantez.getDouble("D_06"));

                    Cell CeldaCobra = filaDatosCuotasYAportaciones.createCell(5);
                    CeldaCobra.setCellStyle(datosEstiloMoneda);
                    CeldaCobra.setCellValue(rstrabajadorescotizantez.getDouble("total"));

                }
                numFilaDatosCuotasYAportaciones++;

            }
            int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);
            Row sumapercepciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 3);
            Row cuotasisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 4);
            Row fovisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 5);
            Row aportacionisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 6);

            Cell Celdasumas = sumapercepciones.createCell(0);
            Celdasumas.setCellStyle(datosEstilo);
            Celdasumas.setCellValue("TOTAL");

            Cell Celdasumap1 = sumapercepciones.createCell(3);
            Celdasumap1.setCellStyle(datosEstiloMoneda);
            Celdasumap1.setCellFormula("SUM(D10:D" + numFilaDatosCuotasYAportaciones + ")");
            Cell Celdasumap2 = sumapercepciones.createCell(4);
            Celdasumap2.setCellStyle(datosEstiloMoneda);
            Celdasumap2.setCellFormula("SUM(E10:E" + numFilaDatosCuotasYAportaciones + ")");
            Cell Celdasumap3 = sumapercepciones.createCell(5);
            Celdasumap3.setCellStyle(datosEstiloMoneda);
            Celdasumap3.setCellFormula("SUM(F10:F" + numFilaDatosCuotasYAportaciones + ")");

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("CreditosHipotecarios" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformesestadiscticaspornombramiento(Reporte reporte) {
        Connection con = getConexion();
        String claveparainforme = reporte.getClaveReporte();

        String sqlprueba = "SELECT \n"
                + "  (SELECT count(TIPOPER) from hojaisp WHERE TIPOPER IN ('L')  AND TIPO = 'A' AND   TPLANTEL = ? and numeroquincena = ?)AS countlimita,\n"
                + "  (SELECT count(TIPOPER) from hojaisp WHERE TIPOPER IN ('L')  AND TIPO = 'D' AND   TPLANTEL = ? AND numeroquincena = ?)AS countlimitd,\n"
                + "  (SELECT count(TIPOPER) from hojaisp WHERE TIPOPER IN ('B')  AND TIPO = 'A' AND   TPLANTEL = ? and numeroquincena = ?)AS countbasea,\n"
                + "  (SELECT count(TIPOPER) from hojaisp WHERE TIPOPER IN ('B')  AND TIPO = 'D' AND   TPLANTEL = ? AND numeroquincena = ?)AS countbased,\n"
                + "  (SELECT count(TIPOPER) from hojaisp WHERE TIPOPER IN ('C')  AND TIPO = 'A' AND   TPLANTEL = ? and numeroquincena = ?)AS countconfa,\n"
                + "  (SELECT count(TIPOPER) from hojaisp WHERE TIPOPER IN ('C')  AND TIPO = 'D' AND   TPLANTEL = ? AND numeroquincena = ?)AS countconfd,\n"
                + "  (SELECT SUM(P_01+P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27\n"
                + "  +P_28+P_29+P_30+P_31+P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50+P_PE+P_PG) from hojaisp WHERE TIPOPER IN ('L')  AND TIPO = 'A' AND   TPLANTEL = ? and numeroquincena = ?)AS sumlimita,\n"
                + "  (SELECT SUM(P_01+P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27\n"
                + "  +P_28+P_29+P_30+P_31+P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50+P_PE+P_PG)from hojaisp WHERE TIPOPER IN ('L')  AND TIPO = 'D' AND   TPLANTEL = ? AND numeroquincena = ?)AS sumlimitd,\n"
                + "  (SELECT SUM(P_01+P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27\n"
                + "  +P_28+P_29+P_30+P_31+P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50+P_PE+P_PG) from hojaisp WHERE TIPOPER IN ('B')  AND TIPO = 'A' AND   TPLANTEL = ? and numeroquincena = ?)AS sumbasea,\n"
                + "  (SELECT SUM(P_01+P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27\n"
                + "  +P_28+P_29+P_30+P_31+P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50+P_PE+P_PG)from hojaisp WHERE TIPOPER IN ('B')  AND TIPO = 'D' AND   TPLANTEL = ? AND numeroquincena = ?)AS sumbased,\n"
                + "  \n"
                + "  (SELECT SUM(P_01+P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27\n"
                + "  +P_28+P_29+P_30+P_31+P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50+P_PE+P_PG) from hojaisp WHERE TIPOPER IN ('C')  AND TIPO = 'A' AND   TPLANTEL = ? and numeroquincena = ?)AS sumconfa,\n"
                + "  (SELECT SUM(P_01+P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27\n"
                + "  +P_28+P_29+P_30+P_31+P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50+P_PE+P_PG)from hojaisp WHERE TIPOPER IN ('C')  AND TIPO = 'D' AND   TPLANTEL = ? AND numeroquincena = ?)AS sumconfd,\n"
                + "  (SELECT count(TIPOPER) from hojaisp WHERE  TIPO = 'A' AND   TPLANTEL = ? and numeroquincena = ?)AS countlimittotala,\n"
                + "  (SELECT count(TIPOPER) from hojaisp WHERE  TIPO = 'D' AND   TPLANTEL = ? and numeroquincena = ?)AS countlimittotald\n"
                + "  FROM hojaisp WHERE numeroquincena = 201720 LIMIT 1";

        //String sqlprueba = "select rfc,empleado,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,TIPO,"
        //      + "categoria," "P_01 as cantidad from hojaisp where numeroquincena = ? and " +claveparainforme+ " > 0";
        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("EstadisticasXNombramiento");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);

        Row rowencabezado10 = hojareporte.createRow(10);
        Row rowencabezado11 = hojareporte.createRow(11);
        Row rowencabezado12 = hojareporte.createRow(12);
        Row rowencabezado13 = hojareporte.createRow(13);

        Row rowencabezado17 = hojareporte.createRow(17);
        Row rowencabezado18 = hojareporte.createRow(18);
        Row rowencabezado19 = hojareporte.createRow(19);

        Row rowencabezado21 = hojareporte.createRow(21);
        Row rowencabezado22 = hojareporte.createRow(22);
        Row rowencabezado23 = hojareporte.createRow(23);

        Row rowencabezado25 = hojareporte.createRow(25);
        Row rowencabezado26 = hojareporte.createRow(26);
        Row rowencabezado27 = hojareporte.createRow(27);

        Row rowencabezado29 = hojareporte.createRow(29);

        Row rowencabezado32 = hojareporte.createRow(32);
        Row rowencabezado33 = hojareporte.createRow(33);
        Row rowencabezado34 = hojareporte.createRow(34);
        Row rowencabezado35 = hojareporte.createRow(35);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        rowencabezado10.createCell(0).setCellValue("RESUMEN GENERAL");
        rowencabezado11.createCell(1).setCellValue("ADMINISTRATIVO");
        rowencabezado12.createCell(1).setCellValue("DOCENTE");
        rowencabezado13.createCell(1).setCellValue("TOTAL");

        rowencabezado17.createCell(0).setCellValue("PERSONAL ADMINISTRATIVO BASE");
        rowencabezado18.createCell(0).setCellValue("PERSONAL DOCENTE BASE");
        rowencabezado19.createCell(0).setCellValue("SUBTOTAL");

        rowencabezado21.createCell(0).setCellValue("PERSONAL ADMINISTRATIVO CONF");
        rowencabezado22.createCell(0).setCellValue("PERSONAL DOCENTE CONF");
        rowencabezado23.createCell(0).setCellValue("SUBTOTAL");

        rowencabezado25.createCell(0).setCellValue("PERSONAL ADMINISTRATIVO LIMIT");
        rowencabezado26.createCell(0).setCellValue("PERSONAL DOCENTE LIMIT");
        rowencabezado27.createCell(0).setCellValue("SUBTOTAL");

        rowencabezado29.createCell(0).setCellValue("EL COSTO TOTAL DE LA NOMINA ES:");

        rowencabezado32.createCell(0).setCellValue("MOVIMIENTOS");
        rowencabezado33.createCell(0).setCellValue("JEFE DEL DEPTO DE RECURSOS HUMANOS");
        rowencabezado34.createCell(0).setCellValue("__________________________________");
        rowencabezado35.createCell(0).setCellValue("MARTHA ELENA MORALES HERNANDEZ");

        row.createCell(0).setCellValue("No");
        row.createCell(1).setCellValue("TIPO");
        row.createCell(2).setCellValue("CANT/BASE");
        row.createCell(3).setCellValue("BASE");
        row.createCell(4).setCellValue("CONT/CONF");
        row.createCell(5).setCellValue("CONF");
        row.createCell(6).setCellValue("CANT/LIMIT");
        row.createCell(7).setCellValue("LIM");
        row.createCell(8).setCellValue("SUBTOTAL");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(2, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(3, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(4, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(5, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(6, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(7, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(8, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(9, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(10, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(11, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(12, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(13, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(14, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(15, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(16, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(17, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(18, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(19, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(20, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(21, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(22, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(23, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(24, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(25, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(26, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(27, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(28, reporte.getPeriodo());

//psinformestrabajadorescotizantes.setString(1, reporte.getClaveReporte());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();

            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell Celda11AA = rowencabezado11.createCell(0);
                    Celda11AA.setCellStyle(datosEstilo);
                    Celda11AA.setCellValue(rstrabajadorescotizantez.getInt("countlimittotala"));

                    Cell Celda12DD = rowencabezado12.createCell(0);
                    Celda12DD.setCellStyle(datosEstilo);
                    Celda12DD.setCellValue(rstrabajadorescotizantez.getInt("countlimittotald"));

                    Cell Celda11A = rowencabezado11.createCell(2);
                    Celda11A.setCellStyle(datosEstilo);
                    Celda11A.setCellValue(rstrabajadorescotizantez.getInt("countbasea"));

                    Cell Celda12D = rowencabezado12.createCell(2);
                    Celda12D.setCellStyle(datosEstilo);
                    Celda12D.setCellValue(rstrabajadorescotizantez.getInt("countbased"));

                    Cell Celda13 = rowencabezado11.createCell(3);
                    Celda13.setCellStyle(datosEstiloMoneda);
                    Celda13.setCellValue(rstrabajadorescotizantez.getDouble("sumbasea"));

                    Cell Celda14 = rowencabezado12.createCell(3);
                    Celda14.setCellStyle(datosEstiloMoneda);
                    Celda14.setCellValue(rstrabajadorescotizantez.getDouble("sumbased"));

                    Cell Celda15 = rowencabezado11.createCell(4);
                    Celda15.setCellStyle(datosEstilo);
                    Celda15.setCellValue(rstrabajadorescotizantez.getInt("countconfa"));

                    Cell Celda16 = rowencabezado12.createCell(4);
                    Celda16.setCellStyle(datosEstilo);
                    Celda16.setCellValue(rstrabajadorescotizantez.getInt("countconfd"));

                    Cell Celda16a = rowencabezado11.createCell(5);
                    Celda16a.setCellStyle(datosEstiloMoneda);
                    Celda16a.setCellValue(rstrabajadorescotizantez.getDouble("sumconfa"));

                    Cell Celda17 = rowencabezado12.createCell(5);
                    Celda17.setCellStyle(datosEstiloMoneda);
                    Celda17.setCellValue(rstrabajadorescotizantez.getDouble("sumconfd"));

                    Cell Celda18 = rowencabezado11.createCell(6);
                    Celda18.setCellStyle(datosEstilo);
                    Celda18.setCellValue(rstrabajadorescotizantez.getInt("countlimita"));

                    Cell Celda19 = rowencabezado12.createCell(6);
                    Celda19.setCellStyle(datosEstilo);
                    Celda19.setCellValue(rstrabajadorescotizantez.getInt("countlimitd"));

                    Cell Celda20 = rowencabezado11.createCell(7);
                    Celda20.setCellStyle(datosEstiloMoneda);
                    Celda20.setCellValue(rstrabajadorescotizantez.getDouble("sumlimita"));

                    Cell Celda21 = rowencabezado12.createCell(7);
                    Celda21.setCellStyle(datosEstiloMoneda);
                    Celda21.setCellValue(rstrabajadorescotizantez.getDouble("sumlimitd"));

                    /////////////////////////////////
                    Cell Celda22 = rowencabezado17.createCell(4);
                    Celda22.setCellStyle(datosEstilo);
                    Celda22.setCellValue(rstrabajadorescotizantez.getInt("countbasea"));
                    Cell Celda22sum = rowencabezado17.createCell(5);
                    Celda22sum.setCellStyle(datosEstiloMoneda);
                    Celda22sum.setCellValue(rstrabajadorescotizantez.getDouble("sumbasea"));

                    Cell Celda23 = rowencabezado18.createCell(4);
                    Celda23.setCellStyle(datosEstilo);
                    Celda23.setCellValue(rstrabajadorescotizantez.getInt("countbased"));
                    Cell Celda23sum = rowencabezado18.createCell(5);
                    Celda23sum.setCellStyle(datosEstiloMoneda);
                    Celda23sum.setCellValue(rstrabajadorescotizantez.getDouble("sumbased"));

                    Cell Celda24 = rowencabezado21.createCell(4);
                    Celda24.setCellStyle(datosEstilo);
                    Celda24.setCellValue(rstrabajadorescotizantez.getInt("countconfa"));
                    Cell Celda24sum = rowencabezado21.createCell(5);
                    Celda24sum.setCellStyle(datosEstiloMoneda);
                    Celda24sum.setCellValue(rstrabajadorescotizantez.getDouble("sumconfa"));

                    Cell Celda25 = rowencabezado22.createCell(4);
                    Celda25.setCellStyle(datosEstilo);
                    Celda25.setCellValue(rstrabajadorescotizantez.getInt("countconfd"));

                    Cell Celda25sum = rowencabezado22.createCell(5);
                    Celda25sum.setCellStyle(datosEstiloMoneda);
                    Celda25sum.setCellValue(rstrabajadorescotizantez.getDouble("sumconfd"));

                    Cell Celda26 = rowencabezado25.createCell(4);
                    Celda26.setCellStyle(datosEstilo);
                    Celda26.setCellValue(rstrabajadorescotizantez.getInt("countlimita"));

                    Cell Celda26sum = rowencabezado25.createCell(5);
                    Celda26sum.setCellStyle(datosEstiloMoneda);
                    Celda26sum.setCellValue(rstrabajadorescotizantez.getDouble("sumlimita"));

                    Cell Celda27 = rowencabezado26.createCell(4);
                    Celda27.setCellStyle(datosEstilo);
                    Celda27.setCellValue(rstrabajadorescotizantez.getInt("countlimitd"));

                    Cell Celda27sum = rowencabezado26.createCell(5);
                    Celda27sum.setCellStyle(datosEstiloMoneda);
                    Celda27sum.setCellValue(rstrabajadorescotizantez.getDouble("sumlimitd"));

                }
                numFilaDatosCuotasYAportaciones++;

            }
            Cell Celda12t = rowencabezado13.createCell(2);
            Celda12t.setCellStyle(datosEstilo);
            Celda12t.setCellFormula("SUM(C12+C13)");
            Cell Celda13t = rowencabezado13.createCell(3);
            Celda13t.setCellStyle(datosEstiloMoneda);
            Celda13t.setCellFormula("SUM(D12+D13)");
            Cell Celda14t = rowencabezado13.createCell(4);
            Celda14t.setCellStyle(datosEstilo);
            Celda14t.setCellFormula("SUM(E12+E13)");
            Cell Celda15t = rowencabezado13.createCell(5);
            Celda15t.setCellStyle(datosEstiloMoneda);
            Celda15t.setCellFormula("SUM(F12+F13)");
            Cell Celda16t = rowencabezado13.createCell(6);
            Celda16t.setCellStyle(datosEstilo);
            Celda16t.setCellFormula("SUM(G12+G13)");
            Cell Celda17t = rowencabezado13.createCell(7);
            Celda17t.setCellStyle(datosEstiloMoneda);
            Celda17t.setCellFormula("SUM(H12+H13)");

            Cell Celda19t = rowencabezado11.createCell(8);
            Celda19t.setCellStyle(datosEstiloMoneda);
            Celda19t.setCellFormula("SUM(D12+F12+H12)");

            Cell Celda20t = rowencabezado12.createCell(8);
            Celda20t.setCellStyle(datosEstiloMoneda);
            Celda20t.setCellFormula("SUM(D13+F13+H13)");
            Cell Celda18t = rowencabezado13.createCell(8);
            Celda18t.setCellStyle(datosEstiloMoneda);
            Celda18t.setCellFormula("SUM(I12+I13)");

            Cell Celda21t = rowencabezado19.createCell(4);
            Celda21t.setCellStyle(datosEstiloMoneda);
            Celda21t.setCellFormula("SUM(E18+E19)");

            Cell Celda22t = rowencabezado23.createCell(4);
            Celda22t.setCellStyle(datosEstiloMoneda);
            Celda22t.setCellFormula("SUM(E22+E23)");
            Cell Celda23t = rowencabezado27.createCell(4);
            Celda23t.setCellStyle(datosEstiloMoneda);
            Celda23t.setCellFormula("SUM(E26+E27)");

            Cell Celda24t = rowencabezado19.createCell(5);
            Celda24t.setCellStyle(datosEstiloMoneda);
            Celda24t.setCellFormula("SUM(F18+F19)");

            Cell Celda25t = rowencabezado23.createCell(5);
            Celda25t.setCellStyle(datosEstiloMoneda);
            Celda25t.setCellFormula("SUM(F22+F23)");

            Cell Celda26t = rowencabezado27.createCell(5);
            Celda26t.setCellStyle(datosEstiloMoneda);
            Celda26t.setCellFormula("SUM(F26+F27)");

            Cell Celda27t = rowencabezado29.createCell(5);
            Celda27t.setCellStyle(datosEstiloMoneda);
            Celda27t.setCellFormula("SUM(I12+I13)");

            Cell Celda28t = rowencabezado13.createCell(0);
            Celda28t.setCellStyle(datosEstilo);
            Celda28t.setCellFormula("SUM(A12+A13)");

            /* int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);
            Row sumapercepciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 3);
            Row cuotasisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 4);
            Row fovisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 5);
            Row aportacionisste = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 6);*/
 /* Cell Celdasumas = sumapercepciones.createCell(0);
                    Celdasumas.setCellStyle(datosEstilo);
                    Celdasumas.setCellValue("TOTAL");
               
                  Cell Celdasumap1 = sumapercepciones.createCell(3);
                    Celdasumap1.setCellStyle(datosEstiloMoneda);
                    Celdasumap1.setCellFormula("SUM(D10:D" + numFilaDatosCuotasYAportaciones + ")");
                    Cell Celdasumap2 = sumapercepciones.createCell(4);
                    Celdasumap2.setCellStyle(datosEstiloMoneda);
                    Celdasumap2.setCellFormula("SUM(E10:E" + numFilaDatosCuotasYAportaciones + ")");
                    Cell Celdasumap3 = sumapercepciones.createCell(5);
                    Celdasumap3.setCellStyle(datosEstiloMoneda);
                    Celdasumap3.setCellFormula("SUM(F10:F" + numFilaDatosCuotasYAportaciones + ")");*/
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("EstadisticasXNombramiento" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformesestadiscticasporcategoria(Reporte reporte) {
        Connection con = getConexion();
        String claveparainforme = reporte.getClaveReporte();

        String sqlprueba = "select count(*) as suma,CATEGORIA,sum(TIPOPER = 'B') as sumab,"
                + "sum(TIPOPER = 'C')as sumac,sum(TIPOPER = 'L')as sumal,sum(CASE TIPOPER WHEN  'B'"
                + " THEN (P_01+  P_01A+  P_01B+  P_01C+  P_01D+  P_01E+  P_02+   P_03+   P_04+   "
                + "P_05+   P_06+   P_07+   P_08+ P_09+ P_10+P_11+P_12+P_13+    P_14+   P_15+   P_16+   "
                + "P_17+   P_18+   P_19+   P_20+   P_21+   P_22+   P_23+   P_24+   P_25+   P_26+   P_27+   "
                + "P_28+   P_29+   P_30+   P_31+   P_32+   P_33+   P_34+   P_35+   P_36+   P_37+   P_38+  "
                + " P_39+   P_40+   P_41+   P_42+   P_43+ P_44+P_45+P_46+P_47+P_48+P_49+ P_50+P_PE+P_PG)ELSE 0 END )"
                + "as sumabase,sum(CASE TIPOPER WHEN  'C' THEN (P_01+  P_01A+  P_01B+  P_01C+  P_01D+ "
                + " P_01E+  P_02+   P_03+   P_04+   P_05+   P_06+   P_07+   P_08+ P_09+ P_10+P_11+P_12+P_13+"
                + "    P_14+   P_15+   P_16+   P_17+   P_18+   P_19+   P_20+   P_21+   P_22+   P_23+   "
                + "P_24+   P_25+   P_26+   P_27+   P_28+   P_29+   P_30+   P_31+   P_32+   P_33+   P_34+  "
                + " P_35+   P_36+   P_37+   P_38+   P_39+   P_40+   P_41+   P_42+   P_43+ P_44+P_45+P_46+"
                + "P_47+P_48+P_49+ P_50+P_PE+P_PG)ELSE 0 END )as sumaconfianza,sum(CASE TIPOPER WHEN  'L' THEN "
                + "(P_01+  P_01A+  P_01B+  P_01C+  P_01D+  P_01E+  P_02+   P_03+   P_04+   P_05+   "
                + "P_06+   P_07+   P_08+ P_09+ P_10+P_11+P_12+P_13+    P_14+   P_15+   P_16+   P_17+  "
                + " P_18+   P_19+   P_20+   P_21+   P_22+   P_23+   P_24+   P_25+   P_26+   P_27+   "
                + "P_28+   P_29+   P_30+   P_31+   P_32+   P_33+   P_34+   P_35+   P_36+   P_37+   "
                + "P_38+   P_39+   P_40+   P_41+   P_42+   P_43+ P_44+P_45+P_46+P_47+P_48+P_49+ P_50+P_PE+P_PG)"
                + "ELSE 0 END )as sumalimitado from hojaisp where numeroquincena = ? and TPLANTEL = ? and TIPO = ? group by categoria";

        //String sqlprueba = "select rfc,empleado,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,TIPO,"
        //      + "categoria," "P_01 as cantidad from hojaisp where numeroquincena = ? and " +claveparainforme+ " > 0";
        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("EstadisticasXCategoria");
        Row row = hojareporte.createRow(9);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado2 = hojareporte.createRow(1);
        Row rowencabezado3 = hojareporte.createRow(2);
        Row rowencabezado4 = hojareporte.createRow(3);
        Row rowencabezado5 = hojareporte.createRow(4);
        Row rowencabezado6 = hojareporte.createRow(5);

        rowencabezado1.createCell(0).setCellValue("Colegio de Bachilleres del estado de guerrero");
        rowencabezado2.createCell(0).setCellValue("Planteles: " + reporte.getTipoPlantelReporte());
        rowencabezado3.createCell(0).setCellValue("Cve Mvto: Nomina de Pago");
        rowencabezado4.createCell(0).setCellValue("Nomina:Quincenal");
        rowencabezado5.createCell(0).setCellValue("Periodo del 16/10/2017 al 31/10/2017");
        rowencabezado6.createCell(0).setCellValue("Ordenado Por Plantel");

        row.createCell(0).setCellValue("CANT/HSM");
        row.createCell(1).setCellValue("TIPO");
        row.createCell(2).setCellValue("CANT/BASE");
        row.createCell(3).setCellValue("BASE");
        row.createCell(4).setCellValue("CONT/CONF");
        row.createCell(5).setCellValue("CONF");
        row.createCell(6).setCellValue("CANT/LIMIT");
        row.createCell(7).setCellValue("LIM");
        row.createCell(8).setCellValue("SUBTOTAL");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;

        int numFilaDatosCuotasYAportaciones = 10;
        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(2, reporte.getTipoPlantelReporte());
            psinformestrabajadorescotizantes.setString(3, reporte.getTipoPlaza());

//psinformestrabajadorescotizantes.setString(1, reporte.getClaveReporte());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();
            Double sumatotal = 0.00;
            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();

            while (rstrabajadorescotizantez.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell Celda11AA = filaDatosCuotasYAportaciones.createCell(0);
                    Celda11AA.setCellStyle(datosEstilo);
                    Celda11AA.setCellValue(rstrabajadorescotizantez.getInt("suma"));

                    Cell Celda12DD = filaDatosCuotasYAportaciones.createCell(1);
                    Celda12DD.setCellStyle(datosEstilo);
                    Celda12DD.setCellValue(rstrabajadorescotizantez.getString("CATEGORIA"));

                    Cell Celda11A = filaDatosCuotasYAportaciones.createCell(2);
                    Celda11A.setCellStyle(datosEstilo);
                    Celda11A.setCellValue(rstrabajadorescotizantez.getInt("sumab"));

                    Cell Celda12D = filaDatosCuotasYAportaciones.createCell(3);
                    Celda12D.setCellStyle(datosEstiloMoneda);
                    Celda12D.setCellValue(rstrabajadorescotizantez.getDouble("sumabase"));

                    Cell Celdasumac = filaDatosCuotasYAportaciones.createCell(4);
                    Celdasumac.setCellStyle(datosEstilo);
                    Celdasumac.setCellValue(rstrabajadorescotizantez.getInt("sumac"));

                    Cell Celdasumaconf = filaDatosCuotasYAportaciones.createCell(5);
                    Celdasumaconf.setCellStyle(datosEstiloMoneda);
                    Celdasumaconf.setCellValue(rstrabajadorescotizantez.getDouble("sumaconfianza"));

                    Cell Celdasumal = filaDatosCuotasYAportaciones.createCell(6);
                    Celdasumal.setCellStyle(datosEstilo);
                    Celdasumal.setCellValue(rstrabajadorescotizantez.getInt("sumal"));

                    Cell Celdasumalimitado = filaDatosCuotasYAportaciones.createCell(7);
                    Celdasumalimitado.setCellStyle(datosEstiloMoneda);
                    Celdasumalimitado.setCellValue(rstrabajadorescotizantez.getDouble("sumalimitado"));

                    sumatotal = rstrabajadorescotizantez.getDouble("sumalimitado") + rstrabajadorescotizantez.getDouble("sumaconfianza")
                            + rstrabajadorescotizantez.getDouble("sumabase");
                    Cell Celdasumatotal = filaDatosCuotasYAportaciones.createCell(8);
                    Celdasumatotal.setCellStyle(datosEstiloMoneda);
                    Celdasumatotal.setCellValue(sumatotal);

                }
                numFilaDatosCuotasYAportaciones++;

            }

            int filasumas = numFilaDatosCuotasYAportaciones + 2;
            Row filaDatosSuma = hojareporte.createRow(numFilaDatosCuotasYAportaciones + 1);

            /* Cell Celdasumas = sumapercepciones.createCell(0);
                    Celdasumas.setCellStyle(datosEstilo);
                    Celdasumas.setCellValue("TOTAL");*/
            Cell Celdasumap1 = filaDatosSuma.createCell(2);
            Celdasumap1.setCellStyle(datosEstiloMoneda);
            Celdasumap1.setCellFormula("SUM(C10:C" + numFilaDatosCuotasYAportaciones + ")");
            Cell Celdasumap2 = filaDatosSuma.createCell(3);
            Celdasumap2.setCellStyle(datosEstiloMoneda);
            Celdasumap2.setCellFormula("SUM(D10:D" + numFilaDatosCuotasYAportaciones + ")");
            Cell Celdasumap3 = filaDatosSuma.createCell(4);
            Celdasumap3.setCellStyle(datosEstiloMoneda);
            Celdasumap3.setCellFormula("SUM(E10:E" + numFilaDatosCuotasYAportaciones + ")");
            Cell Celdasumap4 = filaDatosSuma.createCell(5);
            Celdasumap4.setCellStyle(datosEstiloMoneda);
            Celdasumap4.setCellFormula("SUM(F10:F" + numFilaDatosCuotasYAportaciones + ")");
            Cell Celdasumap5 = filaDatosSuma.createCell(6);
            Celdasumap5.setCellStyle(datosEstiloMoneda);
            Celdasumap5.setCellFormula("SUM(G10:G" + numFilaDatosCuotasYAportaciones + ")");
            Cell Celdasumap6 = filaDatosSuma.createCell(7);
            Celdasumap6.setCellStyle(datosEstiloMoneda);
            Celdasumap6.setCellFormula("SUM(H10:H" + numFilaDatosCuotasYAportaciones + ")");
            Cell Celdasumap7 = filaDatosSuma.createCell(8);
            Celdasumap7.setCellStyle(datosEstiloMoneda);
            Celdasumap7.setCellFormula("SUM(I10:I" + numFilaDatosCuotasYAportaciones + ")");
            Cell Celdasumap8 = filaDatosSuma.createCell(0);
            Celdasumap8.setCellStyle(datosEstiloMoneda);
            Celdasumap8.setCellFormula("SUM(A10:A" + numFilaDatosCuotasYAportaciones + ")");

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("EstadisticasXCategoria" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformeslistadodenomina(Reporte reporte) {
        Connection con = getConexion();
        String claveparainforme = reporte.getClaveReporte();
        String plantelreporte;
        String sqlprueba = "SELECT TIPO, RFC, CONCAT(AP_PAT, ' ', AP_MAT, ' ', NOMBRE) as NOMBRE, CATEGORIA, (15 - FALTAS) AS TOTDIAS, FALTAS, 'S'\n"
                + "AS cobra, FECHAOLD, hrsbase, hrslimit, hrsinter, (P_01 + P_01A + P_01B + P_01C + P_01D +\n"
                + "    P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15 + P_16 + P_17 + P_18 + P_19 + P_20 + P_21 + P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 + P_31 +\n"
                + "    P_32 + P_33 + P_34 + P_35 + P_36 + P_37 + P_38 + P_39 + P_40 + P_41 + P_42 + P_43 + P_44 + P_45 + P_46 + P_47 + P_48 + P_49 + P_50 + P_PE + P_PG) AS percepciones, (D_01 + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 +\n"
                + "    D_09 + D_10 + D_11 + D_12 + D_13 + D_14 + D_15 + D_16 + D_17 + D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 + D_30 + D_31 + D_32 + D_33 + D_34 + D_35 + D_36 + D_37 + D_38 +\n"
                + "    D_39 + D_40 + D_41 + D_42 + D_43 + D_44 + D_45 + D_46 + D_47 + D_48 + D_49 + D_50 + D_CB) AS deducciones, (P_01 + P_01A + P_01B + P_01C + P_01D +\n"
                + "    P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15 + P_16 + P_17 + P_18 + P_19 + P_20 + P_21 + P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 + P_31 +\n"
                + "    P_32 + P_33 + P_34 + P_35 + P_36 + P_37 + P_38 + P_39 + P_40 + P_41 + P_42 + P_43 + P_44 + P_45 + P_46 + P_47 + P_48 + P_49 + P_50 + P_PE + P_PG) - (D_01 + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 +\n"
                + "    D_09 + D_10 + D_11 + D_12 + D_13 + D_14 + D_15 + D_16 + D_17 + D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 + D_30 + D_31 + D_32 + D_33 + D_34 + D_35 + D_36 + D_37 + D_38 +\n"
                + "    D_39 + D_40 + D_41 + D_42 + D_43 + D_44 + D_45 + D_46 + D_47 + D_48 + D_49 + D_50 + D_CB) AS neto,\n"
                + "P_01, P_01A, P_01B, P_01C, P_01D,\n"
                + "P_02, P_03, P_04, P_05, P_06, P_07, P_08, P_09, P_10, P_11, P_12, P_13, P_14, P_15, P_16, P_17, P_18, P_19, P_20, P_21, P_22, P_23, P_24, P_25, P_26, P_27, P_28, P_29, P_30,\n"
                + "P_31, P_32, P_33, P_34, P_35, P_36, P_37, P_38, P_39, P_40, P_41, P_42, P_43, P_44, P_45, P_46, P_47, P_48, P_49, P_50, P_PE, P_PG, D_01, D_02, D_03, D_04, D_05, D_06, D_07, D_08,\n"
                + "D_09, D_10, D_11, D_12, D_13, D_14, D_15, D_16, D_17, D_18, D_19, D_20, D_21, D_22, D_23, D_24, D_25, D_26, D_27, D_28, D_29, D_30, D_31, D_32,\n"
                + "D_33, D_34, D_35, D_36, D_37, D_38, D_39, D_40, D_41, D_42, D_43, D_44, D_45, D_46, D_47, D_48, D_49, D_50, D_CB\n"
                + "from hojai where numeroquincena = ? and plantel = ?\n"
                + "group by empleado order by TIPO ASC, P_01 desc, cvecategoria desc ";
        String sqlpruebaplantel = "SELECT\n"
                + "sum(P_01)as P_01,sum(P_01A)as P_01A,sum(P_01B)as P_01B,sum(P_01C)as P_01C,sum(P_01D)as P_01D,sum(P_02)as P_02,	sum(P_03)as P_03,	sum(P_04)as P_04,	sum(P_05)as P_05,	sum(P_06)as P_06,	sum(P_07)as P_07,	sum(P_08)as P_08,	sum(P_09)as P_09,	sum(P_10)as P_10,	 +\n"
                + "sum(P_11)as P_11,	sum(P_12)as P_12,	sum(P_13)as P_13,	sum(P_14)as P_14,	sum(P_15)as P_15,	sum(P_16)as P_16,	sum(P_17)as P_17,	sum(P_18)as P_18,	sum(P_19)as P_19,	sum(P_20)as P_20,	 +\n"
                + "sum(P_21)as P_21,	sum(P_22)as P_22,	sum(P_23)as P_23,	sum(P_24)as P_24,	sum(P_25)as P_25,	sum(P_26)as P_26,	sum(P_27)as P_27,	sum(P_28)as P_28,	sum(P_29)as P_29,	sum(P_30)as P_30,	 +\n"
                + "sum(P_31)as P_31,	sum(P_32)as P_32,	sum(P_33)as P_33,	sum(P_34)as P_34,	sum(P_35)as P_35,	sum(P_36)as P_36,	sum(P_37)as P_37,	sum(P_38)as P_38,	sum(P_39)as P_39,	sum(P_40)as P_40,	 +\n"
                + "sum(P_41)as P_41,	sum(P_42)as P_42,	sum(P_43)as P_43,	sum(P_44)as P_44,	sum(P_45)as P_45,	sum(P_46)as P_46,	sum(P_47)as P_47,	sum(P_48)as P_48,	sum(P_49)as P_49,	sum(P_50)as P_50, +\n"
                + "sum(P_PE)as P_PE,sum(P_PG)as P_PG, +\n"
                + "sum(D_01)as D_01,	sum(D_02)as D_02,	sum(D_03)as D_03,	sum(D_04)as D_04,	sum(D_05)as D_05,	sum(D_06)as D_06,	sum(D_07)as D_07,	sum(D_08)as D_08,	sum(D_09)as D_09,	sum(D_10)as D_10,	 +\n"
                + "sum(D_11)as D_11,	sum(D_12)as D_12,	sum(D_13)as D_13,	sum(D_14)as D_14,	sum(D_15)as D_15,	sum(D_16)as D_16,	sum(D_17)as D_17,	sum(D_18)as D_18,	sum(D_19)as D_19,	sum(D_20)as D_20,	 +\n"
                + "sum(D_21)as D_21,	sum(D_22)as D_22,	sum(D_23)as D_23,	sum(D_24)as D_24,	sum(D_25)as D_25,	sum(D_26)as D_26,	sum(D_27)as D_27,	sum(D_28)as D_28,	sum(D_29)as D_29,	sum(D_30)as D_30,	 +\n"
                + "sum(D_31)as D_31,	sum(D_32)as D_32,	sum(D_33)as D_33,	sum(D_34)as D_34,	sum(D_35)as D_35,	sum(D_36)as D_36,	sum(D_37)as D_37,	sum(D_38)as D_38,	sum(D_39)as D_39,	sum(D_40)as D_40,	 +\n"
                + "sum(D_41)as D_41,	sum(D_42)as D_42,	sum(D_43)as D_43,	sum(D_44)as D_44,	sum(D_45)as D_45,	sum(D_46)as D_46,	sum(D_47)as D_47,	sum(D_48)as D_48,	sum(D_49)as D_49,	sum(D_50)as D_50, +\n"
                + "sum(D_CB) as  D_CB, +\n"
                + "SUM((P_01+P_01A+P_01B+P_01C+P_01D+ +\n"
                + "P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27+P_28+P_29+P_30+P_31+ +\n"
                + "P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50))as  percepciones,SUM((D_01+D_02+D_03+D_04+D_05+D_06+D_07+D_08+ +\n"
                + "D_09+D_10+D_11+D_12+D_13+D_14+D_15+D_16+D_17+D_18+D_19+D_20+D_21+D_22+D_23+D_24+D_25+D_26+D_27+D_28+D_29+D_30+D_31+D_32+D_33+D_34+D_35+D_36+D_37+D_38+ +\n"
                + "D_39+D_40+D_41+D_42+D_43+D_44+D_45+D_46+D_47+D_48+D_49+D_50))as  deducciones,SUM((P_01+ +\n"
                + "P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27+P_28+P_29+P_30+P_31+ +\n"
                + "P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50))- SUM((D_01+D_02+D_03+D_04+D_05+D_06+D_07+D_08+ +\n"
                + "D_09+D_10+D_11+D_12+D_13+D_14+D_15+D_16+D_17+D_18+D_19+D_20+D_21+D_22+D_23+D_24+D_25+D_26+D_27+D_28+D_29+D_30+D_31+D_32+D_33+D_34+D_35+D_36+D_37+D_38+ +\n"
                + "D_39+D_40+D_41+D_42+D_43+D_44+D_45+D_46+D_47+D_48+D_49+D_50))as  neto \n"
                + " from hojaisp where  numeroquincena = ? and plantel =  ?  group by plantel";

        //String sqlprueba = "select rfc,empleado,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,TIPO,"
        //      + "categoria," "P_01 as cantidad from hojaisp where numeroquincena = ? and " +claveparainforme+ " > 0";
        Workbook book = new XSSFWorkbook();

        XSSFFont fuente = (XSSFFont) book.createFont();
        fuente.setBold(true);
        fuente.setFontName("Arial");
        fuente.setFontHeightInPoints((short) 9);

        Sheet hojareporte = book.createSheet("ListadoDeNomina");
        hojareporte.setDisplayGridlines(false);
        hojareporte.getPrintSetup().setLandscape(true);
        hojareporte.getPrintSetup().setPaperSize(HSSFPrintSetup.LEGAL_PAPERSIZE);
        Row row = hojareporte.createRow(5);
        Row rowencabezado2 = hojareporte.createRow(6);
        hojareporte.addMergedRegion(new CellRangeAddress(5, 5, 2, 5));
        hojareporte.addMergedRegion(new CellRangeAddress(5, 5, 6, 9));
        hojareporte.isDisplayGridlines();

        row.createCell(0).setCellValue("No1");
        row.createCell(1).setCellValue("RFC");
        row.createCell(2).setCellValue("NOMBRE");
        row.createCell(3).setCellValue("PUESTO");
        row.createCell(4).setCellValue("DL");
        row.createCell(5).setCellValue("NF");
        row.createCell(6).setCellValue("COB");
        row.createCell(7).setCellValue("FECHA ING");
        row.createCell(8).setCellValue("FIRMA");

        rowencabezado2.createCell(0).setCellValue("CLAVE");
        rowencabezado2.createCell(1).setCellValue("CANTIDAD");
        rowencabezado2.createCell(2).setCellValue("CLAVE");
        rowencabezado2.createCell(3).setCellValue("CANTIDAD");
        rowencabezado2.createCell(4).setCellValue("CLAVE");
        rowencabezado2.createCell(5).setCellValue("CANTIDAD");
        rowencabezado2.createCell(6).setCellValue("CLAVE");
        rowencabezado2.createCell(7).setCellValue("CANTIDAD");
        rowencabezado2.createCell(8).setCellValue("CLAVE");
        rowencabezado2.createCell(9).setCellValue("CANTIDAD");
        rowencabezado2.createCell(10).setCellValue("CLAVE");
        rowencabezado2.createCell(11).setCellValue("CANTIDAD");
        rowencabezado2.createCell(12).setCellValue("PERCEPCION");
        rowencabezado2.createCell(13).setCellValue("DEDUCCION");
        rowencabezado2.createCell(14).setCellValue("NETO");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.NONE);
        datosEstilo.setBorderLeft(BorderStyle.NONE);
        datosEstilo.setBorderRight(BorderStyle.NONE);
        datosEstilo.setBorderBottom(BorderStyle.NONE);
        qnaactual = reporte.getPeriodo();
        plantelreporte = reporte.getPlantelReporte();

        CellStyle datosEstiloNegrita = book.createCellStyle();
        datosEstiloNegrita.setBorderBottom(BorderStyle.NONE);
        datosEstiloNegrita.setBorderLeft(BorderStyle.NONE);
        datosEstiloNegrita.setBorderRight(BorderStyle.NONE);
        datosEstiloNegrita.setBorderBottom(BorderStyle.NONE);
        datosEstiloNegrita.setFont(fuente);

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.NONE);
        datosEstiloMoneda.setBorderLeft(BorderStyle.NONE);
        datosEstiloMoneda.setBorderRight(BorderStyle.NONE);
        datosEstiloMoneda.setBorderBottom(BorderStyle.NONE);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psglobal = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;
        ResultSet rsglobal = null;
        int numFilaDatosCuotasYAportaciones = 10;

        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(2, reporte.getPlantelReporte());
            // psinformestrabajadorescotizantes.setString(3, reporte.getTipoPlaza());

            //psinformestrabajadorescotizantes.setString(1, reporte.getClaveReporte());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();
            Double sumatotal = 0.00;
            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();
            //int b = 1;
            Double concepto = 0.00;
            int c = 1;
            int consecutivo = 1;
            //int bandera = 1; 
            //numCol = 5;
            while (rstrabajadorescotizantez.next()) {

                int b = 1;
                c = 1;
                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                Cell CeldaConsecutivo = filaDatosCuotasYAportaciones.createCell(0);
                CeldaConsecutivo.setCellStyle(datosEstiloNegrita);
                CeldaConsecutivo.setCellValue(consecutivo);
                for (int a = 1; a < numCol; a++) {

                    if (a > 14) {

                        if (c == 13) {
                            c = 1;
                            numFilaDatosCuotasYAportaciones++;
                            filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                        }

                        concepto = rstrabajadorescotizantez.getDouble(a);
                        if (concepto > 0) {

                            Cell CeldaNombreCampo = filaDatosCuotasYAportaciones.createCell(c);
                            CeldaNombreCampo.setCellStyle(datosEstilo);
                            CeldaNombreCampo.setCellValue(rstrabajadorescotizantez.getMetaData().getColumnName(a));

                            Double columnValue = rstrabajadorescotizantez.getDouble(a);
                            Cell Celda12DD = filaDatosCuotasYAportaciones.createCell(c + 1);
                            Celda12DD.setCellStyle(datosEstiloMoneda);
                            Celda12DD.setCellValue(columnValue);
                            c = c + 2;

                        } else {

                        }

                    } else {

                        String columnValue = rstrabajadorescotizantez.getString(a);
                        Cell Celda12DD = filaDatosCuotasYAportaciones.createCell(b);
                        Celda12DD.setCellStyle(datosEstiloNegrita);
                        Celda12DD.setCellValue(columnValue);
                        b++;
                        if (b == 15) {
                            numFilaDatosCuotasYAportaciones++;
                            filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                        }
                    }

                }
                numFilaDatosCuotasYAportaciones++;
                consecutivo++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            psglobal = con.prepareStatement(sqlpruebaplantel);
            psglobal.setString(1, reporte.getPeriodo());
            psglobal.setString(2, reporte.getPlantelReporte());
            rsglobal = psglobal.executeQuery();
            int numColGlobal = rsglobal.getMetaData().getColumnCount();
            Row filaDatosGlobales = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
            int cGlobal = 1;
            Double conceptoGlobal = 0.00;
            while (rsglobal.next()) {

                for (int band = 1; band <= numColGlobal; band++) {

                    conceptoGlobal = rsglobal.getDouble(band);
                    if (conceptoGlobal > 0) {

                        Cell CeldaNombreCampoGlobal = filaDatosGlobales.createCell(3);
                        CeldaNombreCampoGlobal.setCellStyle(datosEstilo);
                        CeldaNombreCampoGlobal.setCellValue(rsglobal.getMetaData().getColumnName(band));
                        Double columnValue = rsglobal.getDouble(band);
                        Cell CeldaGlobal = filaDatosGlobales.createCell(4);
                        CeldaGlobal.setCellStyle(datosEstiloMoneda);
                        CeldaGlobal.setCellValue(columnValue);
                        numFilaDatosCuotasYAportaciones++;
                        filaDatosGlobales = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                    } else {

                    }

                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("ListadoDeNomina" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportarinformescostosportipoplantel(Reporte reporte) {
        Connection con = getConexion();
        String claveparainforme = reporte.getClaveReporte();
        String plantelreporte;
        String sqlprueba = "SELECT PLANTEL,TIPO,sum(P_01+P_01A+P_01B+P_01C+P_01D+\n"
                + "                P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27+P_28+P_29+P_30+P_31+\n"
                + "                P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50+P_PE+P_PG)AS percepciones,sum(D_01+D_02+D_03+D_04+D_05+D_06+D_07+D_08+\n"
                + "                D_09+D_10+D_11+D_12+D_13+D_14+D_15+D_16+D_17+D_18+D_19+D_20+D_21+D_22+D_23+D_24+D_25+D_26+D_27+D_28+D_29+D_30+D_31+D_32+D_33+D_34+D_35+D_36+D_37+D_38+\n"
                + "                D_39+D_40+D_41+D_42+D_43+D_44+D_45+D_46+D_47+D_48+D_49+D_50+D_CB)AS deducciones,sum(P_01+P_01A+P_01B+P_01C+P_01D+\n"
                + "                P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27+P_28+P_29+P_30+P_31+\n"
                + "                P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50+P_PE+P_PG)- sum(D_01+D_02+D_03+D_04+D_05+D_06+D_07+D_08+\n"
                + "                D_09+D_10+D_11+D_12+D_13+D_14+D_15+D_16+D_17+D_18+D_19+D_20+D_21+D_22+D_23+D_24+D_25+D_26+D_27+D_28+D_29+D_30+D_31+D_32+D_33+D_34+D_35+D_36+D_37+D_38+\n"
                + "                D_39+D_40+D_41+D_42+D_43+D_44+D_45+D_46+D_47+D_48+D_49+D_50+D_CB)aS neto,sum(P_01)as P_01,sum(P_02)as P_02,sum(P_03)as P_03,	sum(P_04)as P_04,	sum(P_05)as P_05,	\n"
                + "					 sum(P_06)as P_06,sum(P_07)as P_07,sum(P_08)as P_08,sum(P_09)as P_09,sum(P_10)as P_10,sum(P_11)as P_11,sum(P_12)as P_12,sum(P_13)as P_13,sum(P_14)as P_14,	\n"
                + "					 sum(P_15)as P_15,sum(P_16)as P_16,sum(P_17)as P_17,sum(P_18)as P_18,sum(P_19)as P_19,sum(P_20)as P_20,sum(P_21)as P_21,sum(P_22)as P_22,sum(P_23)as P_23,sum(P_24)as P_24,	\n"
                + "					 sum(P_25)as P_25,sum(P_26)as P_26,sum(P_27)as P_27,sum(P_28)as P_28,sum(P_29)as P_29,sum(P_30)as P_30,sum(P_31)as P_31,sum(P_32)as P_32,sum(P_33)as P_33,sum(P_34)as P_34,	\n"
                + "					 sum(P_35)as P_35,sum(P_36)as P_36,sum(P_37)as P_37,sum(P_38)as P_38,sum(P_39)as P_39,sum(P_40)as P_40,sum(P_41)as P_41,sum(P_42)as P_42,sum(P_43)as P_43,sum(P_44)as P_44,	\n"
                + "					 sum(P_45)as P_45,sum(P_46)as P_46,sum(P_47)as P_47,sum(P_48)as P_48,sum(P_49)as P_49,sum(P_50)as P_50,sum(D_01)as D_01,sum(D_02)as D_02,sum(D_03)as D_03,sum(D_04)as D_04,sum(D_05)as D_05,	\n"
                + "					 sum(D_06)as D_06,sum(D_07)as D_07,sum(D_08)as D_08,sum(D_09)as D_09,sum(D_10)as D_10,sum(D_11)as D_11,sum(D_12)as D_12,sum(D_13)as D_13,sum(D_14)as D_14,sum(D_15)as D_15,sum(D_16)as D_16,	\n"
                + "					 sum(D_17)as D_17,sum(D_18)as D_18,sum(D_19)as D_19,sum(D_20)as D_20,sum(D_21)as D_21,sum(D_22)as D_22,sum(D_23)as D_23,sum(D_24)as D_24,sum(D_25)as D_25,sum(D_26)as D_26,sum(D_27)as D_27,	\n"
                + "					 sum(D_28)as D_28,sum(D_29)as D_29,sum(D_30)as D_30,sum(D_31)as D_31,sum(D_32)as D_32,sum(D_33)as D_33,sum(D_34)as D_34,sum(D_35)as D_35,sum(D_36)as D_36,sum(D_37)as D_37,sum(D_38)as D_38,	\n"
                + "					 sum(D_39)as D_39,sum(D_40)as D_40,sum(D_41)as D_41,sum(D_42)as D_42,sum(D_43)as D_43,sum(D_44)as D_44,sum(D_45)as D_45,sum(D_46)as D_46,sum(D_47)as D_47,sum(D_48)as D_48,sum(D_49)as D_49,	\n"
                + "					 sum(D_50)as D_50,sum(D_CB)as D_CB\n"
                + "                from hojai where  numeroquincena = ? and TPLANTEL = ? GROUP BY plantel,TIPO ";
        String sqlpruebaplantel = "SELECT          'GLOBAL' as PLANTEL,TIPO,sum(P_01+P_01A+P_01B+P_01C+P_01D+\n"
                + "                P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27+P_28+P_29+P_30+P_31+\n"
                + "                P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50+P_PE+P_PG)AS percepciones,sum(D_01+D_02+D_03+D_04+D_05+D_06+D_07+D_08+\n"
                + "                D_09+D_10+D_11+D_12+D_13+D_14+D_15+D_16+D_17+D_18+D_19+D_20+D_21+D_22+D_23+D_24+D_25+D_26+D_27+D_28+D_29+D_30+D_31+D_32+D_33+D_34+D_35+D_36+D_37+D_38+\n"
                + "                D_39+D_40+D_41+D_42+D_43+D_44+D_45+D_46+D_47+D_48+D_49+D_50+D_CB)AS deducciones,sum(P_01+P_01A+P_01B+P_01C+P_01D+\n"
                + "                P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27+P_28+P_29+P_30+P_31+\n"
                + "                P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_47+P_48+P_49+P_50+P_PE+P_PG)- sum(D_01+D_02+D_03+D_04+D_05+D_06+D_07+D_08+\n"
                + "                D_09+D_10+D_11+D_12+D_13+D_14+D_15+D_16+D_17+D_18+D_19+D_20+D_21+D_22+D_23+D_24+D_25+D_26+D_27+D_28+D_29+D_30+D_31+D_32+D_33+D_34+D_35+D_36+D_37+D_38+\n"
                + "                D_39+D_40+D_41+D_42+D_43+D_44+D_45+D_46+D_47+D_48+D_49+D_50+D_CB)aS neto,sum(P_01)as P_01,sum(P_02)as P_02,sum(P_03)as P_03,	sum(P_04)as P_04,	sum(P_05)as P_05,	\n"
                + "					 sum(P_06)as P_06,sum(P_07)as P_07,sum(P_08)as P_08,sum(P_09)as P_09,sum(P_10)as P_10,sum(P_11)as P_11,sum(P_12)as P_12,sum(P_13)as P_13,sum(P_14)as P_14,	\n"
                + "					 sum(P_15)as P_15,sum(P_16)as P_16,sum(P_17)as P_17,sum(P_18)as P_18,sum(P_19)as P_19,sum(P_20)as P_20,sum(P_21)as P_21,sum(P_22)as P_22,sum(P_23)as P_23,sum(P_24)as P_24,	\n"
                + "					 sum(P_25)as P_25,sum(P_26)as P_26,sum(P_27)as P_27,sum(P_28)as P_28,sum(P_29)as P_29,sum(P_30)as P_30,sum(P_31)as P_31,sum(P_32)as P_32,sum(P_33)as P_33,sum(P_34)as P_34,	\n"
                + "					 sum(P_35)as P_35,sum(P_36)as P_36,sum(P_37)as P_37,sum(P_38)as P_38,sum(P_39)as P_39,sum(P_40)as P_40,sum(P_41)as P_41,sum(P_42)as P_42,sum(P_43)as P_43,sum(P_44)as P_44,	\n"
                + "					 sum(P_45)as P_45,sum(P_46)as P_46,sum(P_47)as P_47,sum(P_48)as P_48,sum(P_49)as P_49,sum(P_50)as P_50,sum(D_01)as D_01,sum(D_02)as D_02,sum(D_03)as D_03,sum(D_04)as D_04,sum(D_05)as D_05,	\n"
                + "					 sum(D_06)as D_06,sum(D_07)as D_07,sum(D_08)as D_08,sum(D_09)as D_09,sum(D_10)as D_10,sum(D_11)as D_11,sum(D_12)as D_12,sum(D_13)as D_13,sum(D_14)as D_14,sum(D_15)as D_15,sum(D_16)as D_16,	\n"
                + "					 sum(D_17)as D_17,sum(D_18)as D_18,sum(D_19)as D_19,sum(D_20)as D_20,sum(D_21)as D_21,sum(D_22)as D_22,sum(D_23)as D_23,sum(D_24)as D_24,sum(D_25)as D_25,sum(D_26)as D_26,sum(D_27)as D_27,	\n"
                + "					 sum(D_28)as D_28,sum(D_29)as D_29,sum(D_30)as D_30,sum(D_31)as D_31,sum(D_32)as D_32,sum(D_33)as D_33,sum(D_34)as D_34,sum(D_35)as D_35,sum(D_36)as D_36,sum(D_37)as D_37,sum(D_38)as D_38,	\n"
                + "					 sum(D_39)as D_39,sum(D_40)as D_40,sum(D_41)as D_41,sum(D_42)as D_42,sum(D_43)as D_43,sum(D_44)as D_44,sum(D_45)as D_45,sum(D_46)as D_46,sum(D_47)as D_47,sum(D_48)as D_48,sum(D_49)as D_49,	\n"
                + "					 sum(D_50)as D_50,sum(D_CB)as D_CB\n"
                + "                from hojai where  numeroquincena = ? and TPLANTEL = ? GROUP BY TIPO";

        //String sqlprueba = "select rfc,empleado,CONCAT(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,plantel,TIPO,"
        //      + "categoria," "P_01 as cantidad from hojaisp where numeroquincena = ? and " +claveparainforme+ " > 0";
        Workbook book = new XSSFWorkbook();

        XSSFFont fuente = (XSSFFont) book.createFont();
        fuente.setBold(true);
        fuente.setFontName("Arial");
        fuente.setFontHeightInPoints((short) 9);

        Sheet hojareporte = book.createSheet("CostosDeNominaPorPlantel");
        hojareporte.setDisplayGridlines(false);
        hojareporte.getPrintSetup().setLandscape(true);
        hojareporte.getPrintSetup().setPaperSize(HSSFPrintSetup.LEGAL_PAPERSIZE);
        Row row = hojareporte.createRow(5);
        Row rowencabezado2 = hojareporte.createRow(6);
        hojareporte.addMergedRegion(new CellRangeAddress(5, 5, 2, 5));
        hojareporte.addMergedRegion(new CellRangeAddress(5, 5, 6, 9));
        hojareporte.isDisplayGridlines();

        /*row.createCell(0).setCellValue("No1");
        row.createCell(1).setCellValue("RFC");
        row.createCell(2).setCellValue("NOMBRE");
        row.createCell(3).setCellValue("PUESTO");
        row.createCell(4).setCellValue("DL");
        row.createCell(5).setCellValue("NF");
        row.createCell(6).setCellValue("COB");
        row.createCell(7).setCellValue("FECHA ING");
        row.createCell(8).setCellValue("FIRMA");*/
        rowencabezado2.createCell(0).setCellValue("CLAVE");
        rowencabezado2.createCell(1).setCellValue("CANTIDAD");
        rowencabezado2.createCell(2).setCellValue("CLAVE");
        rowencabezado2.createCell(3).setCellValue("CANTIDAD");
        rowencabezado2.createCell(4).setCellValue("CLAVE");
        rowencabezado2.createCell(5).setCellValue("CANTIDAD");
        rowencabezado2.createCell(6).setCellValue("CLAVE");
        rowencabezado2.createCell(7).setCellValue("CANTIDAD");
        rowencabezado2.createCell(8).setCellValue("CLAVE");
        rowencabezado2.createCell(9).setCellValue("CANTIDAD");
        rowencabezado2.createCell(10).setCellValue("CLAVE");
        rowencabezado2.createCell(11).setCellValue("CANTIDAD");
        rowencabezado2.createCell(12).setCellValue("PERCEPCION");
        rowencabezado2.createCell(13).setCellValue("DEDUCCION");
        rowencabezado2.createCell(14).setCellValue("NETO");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.NONE);
        datosEstilo.setBorderLeft(BorderStyle.NONE);
        datosEstilo.setBorderRight(BorderStyle.NONE);
        datosEstilo.setBorderBottom(BorderStyle.NONE);
        qnaactual = reporte.getPeriodo();
        plantelreporte = reporte.getPlantelReporte();

        CellStyle datosEstiloNegrita = book.createCellStyle();
        datosEstiloNegrita.setBorderBottom(BorderStyle.NONE);
        datosEstiloNegrita.setBorderLeft(BorderStyle.NONE);
        datosEstiloNegrita.setBorderRight(BorderStyle.NONE);
        datosEstiloNegrita.setBorderBottom(BorderStyle.NONE);
        datosEstiloNegrita.setFont(fuente);

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.NONE);
        datosEstiloMoneda.setBorderLeft(BorderStyle.NONE);
        datosEstiloMoneda.setBorderRight(BorderStyle.NONE);
        datosEstiloMoneda.setBorderBottom(BorderStyle.NONE);
        datosEstiloMoneda.setDataFormat((short) 7);

        CellStyle datosEstiloMonedaNegrita = book.createCellStyle();
        datosEstiloMonedaNegrita.setBorderBottom(BorderStyle.NONE);
        datosEstiloMonedaNegrita.setBorderLeft(BorderStyle.NONE);
        datosEstiloMonedaNegrita.setBorderRight(BorderStyle.NONE);
        datosEstiloMonedaNegrita.setBorderBottom(BorderStyle.NONE);
        datosEstiloMonedaNegrita.setDataFormat((short) 7);
        datosEstiloMonedaNegrita.setFont(fuente);

        PreparedStatement ps = null;
        PreparedStatement psglobal = null;
        PreparedStatement psinformestrabajadorescotizantes = null;
        ResultSet rs = null;
        ResultSet rstrabajadorescotizantez = null;
        ResultSet rsglobal = null;
        int numFilaDatosCuotasYAportaciones = 10;

        try {
            psinformestrabajadorescotizantes = con.prepareStatement(sqlprueba);
            psinformestrabajadorescotizantes.setString(1, reporte.getPeriodo());
            psinformestrabajadorescotizantes.setString(2, reporte.getTipoPlantelReporte());
            // psinformestrabajadorescotizantes.setString(3, reporte.getTipoPlaza());

            //psinformestrabajadorescotizantes.setString(1, reporte.getClaveReporte());
            rstrabajadorescotizantez = psinformestrabajadorescotizantes.executeQuery();
            Double sumatotal = 0.00;
            int numCol = rstrabajadorescotizantez.getMetaData().getColumnCount();
            //int b = 1;
            Double concepto = 0.00;
            int c = 1;
            int consecutivo = 1;
            //int bandera = 1; 
            //numCol = 5;
            while (rstrabajadorescotizantez.next()) {

                int b = 1;
                c = 1;
                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                Cell CeldaConsecutivo = filaDatosCuotasYAportaciones.createCell(0);
                CeldaConsecutivo.setCellStyle(datosEstiloNegrita);
                CeldaConsecutivo.setCellValue(consecutivo);
                for (int a = 1; a <= numCol; a++) {

                    if (a > 5) {

                        if (c == 13) {
                            c = 1;
                            numFilaDatosCuotasYAportaciones++;
                            filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                        }

                        concepto = rstrabajadorescotizantez.getDouble(a);
                        if (concepto > 0) {

                            Cell CeldaNombreCampo = filaDatosCuotasYAportaciones.createCell(c);
                            CeldaNombreCampo.setCellStyle(datosEstilo);
                            CeldaNombreCampo.setCellValue(rstrabajadorescotizantez.getMetaData().getColumnName(a));

                            Double columnValue = rstrabajadorescotizantez.getDouble(a);
                            Cell Celda12DD = filaDatosCuotasYAportaciones.createCell(c + 1);
                            Celda12DD.setCellStyle(datosEstiloMoneda);
                            Celda12DD.setCellValue(columnValue);
                            c = c + 2;

                        } else {

                        }

                    } else {

                        Cell CeldaNombreCampo = filaDatosCuotasYAportaciones.createCell(b);
                        CeldaNombreCampo.setCellStyle(datosEstilo);
                        CeldaNombreCampo.setCellValue(rstrabajadorescotizantez.getMetaData().getColumnName(a));

                        //String columnValue = rstrabajadorescotizantez.getString(a);
                        if (b == 5 || b == 7 || b == 9) {
                            Double columnValue = rstrabajadorescotizantez.getDouble(a);
                            Cell Celda12DD = filaDatosCuotasYAportaciones.createCell(b + 1);
                            Celda12DD.setCellStyle(datosEstiloMonedaNegrita);
                            Celda12DD.setCellValue(columnValue);

                        } else {
                            String columnValue = rstrabajadorescotizantez.getString(a);
                            Cell Celda12DD = filaDatosCuotasYAportaciones.createCell(b + 1);
                            Celda12DD.setCellStyle(datosEstiloNegrita);
                            Celda12DD.setCellValue(columnValue);
                        }

                        b = b + 2;
                        if (b == 11) {
                            numFilaDatosCuotasYAportaciones++;
                            filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                        }
                    }

                }
                numFilaDatosCuotasYAportaciones++;
                consecutivo++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            psglobal = con.prepareStatement(sqlpruebaplantel);
            psglobal.setString(1, reporte.getPeriodo());
            psglobal.setString(2, reporte.getTipoPlantelReporte());
            rsglobal = psglobal.executeQuery();
            int numColGlobal = rsglobal.getMetaData().getColumnCount();

            Row filaDatosGlobales = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
            int cGlobal = 1;
            Double conceptoGlobal = 0.00;
            //int c = 1; 
            while (rsglobal.next()) {
                int bGlobal = 1;
                cGlobal = 1;

                for (int aGlobal = 1; aGlobal <= numColGlobal; aGlobal++) {

                    if (aGlobal > 5) {

                        if (cGlobal == 13) {
                            cGlobal = 1;
                            numFilaDatosCuotasYAportaciones++;
                            filaDatosGlobales = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                        }

                        conceptoGlobal = rsglobal.getDouble(aGlobal);
                        if (conceptoGlobal > 0) {

                            Cell CeldaNombreCampoGlobal = filaDatosGlobales.createCell(cGlobal);
                            CeldaNombreCampoGlobal.setCellStyle(datosEstilo);
                            CeldaNombreCampoGlobal.setCellValue(rsglobal.getMetaData().getColumnName(aGlobal));

                            Double columnValue = rsglobal.getDouble(aGlobal);
                            Cell Celda12DD = filaDatosGlobales.createCell(cGlobal + 1);
                            Celda12DD.setCellStyle(datosEstiloMoneda);
                            Celda12DD.setCellValue(columnValue);

                            cGlobal = cGlobal + 2;

                        } else {

                        }

                    } else {

                        Cell CeldaNombreCampoGlobal = filaDatosGlobales.createCell(bGlobal);
                        CeldaNombreCampoGlobal.setCellStyle(datosEstilo);
                        CeldaNombreCampoGlobal.setCellValue(rsglobal.getMetaData().getColumnName(aGlobal));

                        if (bGlobal == 5 || bGlobal == 7 || bGlobal == 9) {
                            Double columnValue = rsglobal.getDouble(aGlobal);
                            Cell Celda12DD = filaDatosGlobales.createCell(bGlobal + 1);
                            Celda12DD.setCellStyle(datosEstiloMonedaNegrita);
                            Celda12DD.setCellValue(columnValue);

                        } else {
                            String columnValue = rsglobal.getString(aGlobal);
                            Cell Celda12DD = filaDatosGlobales.createCell(bGlobal + 1);
                            Celda12DD.setCellStyle(datosEstiloNegrita);
                            Celda12DD.setCellValue(columnValue);

                        }

                        bGlobal = bGlobal + 2;
                        if (bGlobal == 11) {
                            numFilaDatosCuotasYAportaciones++;
                            filaDatosGlobales = hojareporte.createRow(numFilaDatosCuotasYAportaciones);

                        }
                    }

                }
                numFilaDatosCuotasYAportaciones++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("CostoDeNominaPorPlantel" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void procesarnuevanomina(Reporte reporte) {
        Connection con = getConexion();
        String sqlprueba = "select RFC,\n"
                + "empleado,\n"
                + "periodo,\n"
                + "clavemvto,\n"
                + "P_01,\n"
                + "P_02,\n"
                + "P_03,\n"
                + "P_04,\n"
                + "P_05,\n"
                + "P_06,\n"
                + "P_07,\n"
                + "P_08,\n"
                + "P_09,\n"
                + "P_10,\n"
                + "P_11,\n"
                + "P_12,\n"
                + "P_13,\n"
                + "P_14,\n"
                + "P_15,\n"
                + "P_16,\n"
                + "P_17,\n"
                + "P_18,\n"
                + "P_19,\n"
                + "P_20,\n"
                + "P_21,\n"
                + "P_22,\n"
                + "P_23,\n"
                + "P_24,\n"
                + "P_25,\n"
                + "P_26,\n"
                + "P_27,\n"
                + "P_28,\n"
                + "P_29,\n"
                + "P_30,\n"
                + "D_01,\n"
                + "D_10,\n"
                + "P_DI,\n"
                + "P_C1,\n"
                + "P_C2,\n"
                + "P_31,\n"
                + "P_32,\n"
                + "P_33,\n"
                + "D_CB,\n"
                + "D_02,\n"
                + "D_03,\n"
                + "D_04,\n"
                + "D_05,\n"
                + "D_06,\n"
                + "D_07,\n"
                + "D_08,\n"
                + "D_09,\n"
                + "D_11,\n"
                + "D_12,\n"
                + "D_13,\n"
                + "D_14,\n"
                + "D_15,\n"
                + "D_16,\n"
                + "D_17,\n"
                + "D_18,\n"
                + "D_19,\n"
                + "D_20,\n"
                + "D_21,\n"
                + "D_22,\n"
                + "D_23,\n"
                + "D_24,\n"
                + "D_25,\n"
                + "D_26,\n"
                + "D_27,\n"
                + "D_28,\n"
                + "P_MC,\n"
                + "P_PG,\n"
                + "P_PE,\n"
                + "P_36,\n"
                + "D_29,\n"
                + "P_37,\n"
                + "D_30,\n"
                + "D_31,\n"
                + "P_35,\n"
                + "P_38,\n"
                + "P_39,\n"
                + "D_32,\n"
                + "D_33,\n"
                + "D_34,\n"
                + "D_35,\n"
                + "P_40,\n"
                + "D_36,\n"
                + "D_37,\n"
                + "P_01A,\n"
                + "P_01B,\n"
                + "P_01C,\n"
                + "P_01D,\n"
                + "P_01E,\n"
                + "P_34,\n"
                + "D_38,\n"
                + "P_41,\n"
                + "P_42,\n"
                + "P_43,\n"
                + "P_44,\n"
                + "P_45,\n"
                + "P_46,\n"
                + "P_47,\n"
                + "D_39,\n"
                + "D_40,\n"
                + "D_41,\n"
                + "D_42,\n"
                + "D_01A,\n"
                + "D_43,\n"
                + "D_44,\n"
                + "D_45,\n"
                + "D_46,\n"
                + "TIPO,\n"
                + "QUINCENA,\n"
                + "LAPSO,\n"
                + "PLANTEL,\n"
                + "NOPUESTO,\n"
                + "LEIDO,\n"
                + "AP_PAT,\n"
                + "AP_MAT,\n"
                + "NOMBRE,\n"
                + "CATEGORIA,\n"
                + "HORAS,\n"
                + "NIVEL,\n"
                + "FECHAOLD,\n"
                + "TIPOPER,\n"
                + "ZONA,\n"
                + "SEMESTRE,\n"
                + "TPLANTEL,\n"
                + "TIPOSIN,\n"
                + "MUTUAL,\n"
                + "FACORE,\n"
                + "CEDULAIMSS,\n"
                + "REGIMENPENSIONARIO,\n"
                + "faltas,\n"
                + "curp,\n"
                + "totdias,\n"
                + "hrsbase,\n"
                + "hrslimit,\n"
                + "hrsinter,\n"
                + "cvecategoria,\n"
                + "statusissste,\n"
                + "usuario,\n"
                + "fechamodificacionissste,\n"
                + "saldoissste,\n"
                + "porcentajehipotecario,\n"
                + "porcentajeseccion,\n"
                + "porcentajeahorroissste,\n"
                + "observacionfaltas,\n"
                + "numerodepago,\n"
                + "totaldepagos,\n"
                + "folioissste,\n"
                + "numerocontrato,\n"
                + "tipocontratos,\n"
                + "nombreplantel,\n"
                + "numeroplantel,\n"
                + "P_48,\n"
                + "P_49,\n"
                + "P_50,\n"
                + "D_47,\n"
                + "D_48,\n"
                + "D_49,\n"
                + "D_50,\n"
                + "observacion,\n"
                + "fechabajaissste,\n"
                + "cveayudamutua,\n"
                + "cvecuotasindical,\n"
                + "caja_sutcobach,\n"
                + "caja_seccion,\n"
                + "caja_sitcobach,\n"
                + "cajaseccion63,\n"
                + "status,\n"
                + "motivobaja FROM hojaisp WHERE numeroquincena = ? ";
        String insertarnuevanomina = "INSERT INTO hojaisp (RFC,\n"
                + "empleado,\n"
                + "periodo,\n"
                + "clavemvto,\n"
                + "P_01,\n"
                + "P_02,\n"
                + "P_03,\n"
                + "P_04,\n"
                + "P_05,\n"
                + "P_06,\n"
                + "P_07,\n"
                + "P_08,\n"
                + "P_09,\n"
                + "P_10,\n"
                + "P_11,\n"
                + "P_12,\n"
                + "P_13,\n"
                + "P_14,\n"
                + "P_15,\n"
                + "P_16,\n"
                + "P_17,\n"
                + "P_18,\n"
                + "P_19,\n"
                + "P_20,\n"
                + "P_21,\n"
                + "P_22,\n"
                + "P_23,\n"
                + "P_24,\n"
                + "P_25,\n"
                + "P_26,\n"
                + "P_27,\n"
                + "P_28,\n"
                + "P_29,\n"
                + "P_30,\n"
                + "D_01,\n"
                + "D_10,\n"
                + "P_DI,\n"
                + "P_C1,\n"
                + "P_C2,\n"
                + "P_31,\n"
                + "P_32,\n"
                + "P_33,\n"
                + "D_CB,\n"
                + "D_02,\n"
                + "D_03,\n"
                + "D_04,\n"
                + "D_05,\n"
                + "D_06,\n"
                + "D_07,\n"
                + "D_08,\n"
                + "D_09,\n"
                + "D_11,\n"
                + "D_12,\n"
                + "D_13,\n"
                + "D_14,\n"
                + "D_15,\n"
                + "D_16,\n"
                + "D_17,\n"
                + "D_18,\n"
                + "D_19,\n"
                + "D_20,\n"
                + "D_21,\n"
                + "D_22,\n"
                + "D_23,\n"
                + "D_24,\n"
                + "D_25,\n"
                + "D_26,\n"
                + "D_27,\n"
                + "D_28,\n"
                + "P_MC,\n"
                + "P_PG,\n"
                + "P_PE,\n"
                + "P_36,\n"
                + "D_29,\n"
                + "P_37,\n"
                + "D_30,\n"
                + "D_31,\n"
                + "P_35,\n"
                + "P_38,\n"
                + "P_39,\n"
                + "D_32,\n"
                + "D_33,\n"
                + "D_34,\n"
                + "D_35,\n"
                + "P_40,\n"
                + "D_36,\n"
                + "D_37,\n"
                + "P_01A,\n"
                + "P_01B,\n"
                + "P_01C,\n"
                + "P_01D,\n"
                + "P_01E,\n"
                + "P_34,\n"
                + "D_38,\n"
                + "P_41,\n"
                + "P_42,\n"
                + "P_43,\n"
                + "P_44,\n"
                + "P_45,\n"
                + "P_46,\n"
                + "P_47,\n"
                + "D_39,\n"
                + "D_40,\n"
                + "D_41,\n"
                + "D_42,\n"
                + "D_01A,\n"
                + "D_43,\n"
                + "D_44,\n"
                + "D_45,\n"
                + "D_46,\n"
                + "TIPO,\n"
                + "QUINCENA,\n"
                + "LAPSO,\n"
                + "PLANTEL,\n"
                + "NOPUESTO,\n"
                + "LEIDO,\n"
                + "AP_PAT,\n"
                + "AP_MAT,\n"
                + "NOMBRE,\n"
                + "CATEGORIA,\n"
                + "HORAS,\n"
                + "NIVEL,\n"
                + "FECHAOLD,\n"
                + "TIPOPER,\n"
                + "ZONA,\n"
                + "SEMESTRE,\n"
                + "TPLANTEL,\n"
                + "TIPOSIN,\n"
                + "MUTUAL,\n"
                + "FACORE,\n"
                + "CEDULAIMSS,\n"
                + "REGIMENPENSIONARIO,\n"
                + "faltas,\n"
                + "curp,\n"
                + "totdias,\n"
                + "hrsbase,\n"
                + "hrslimit,\n"
                + "hrsinter,\n"
                + "cvecategoria,\n"
                + "statusissste,\n"
                + "usuario,\n"
                + "fechamodificacionissste,\n"
                + "saldoissste,\n"
                + "porcentajehipotecario,\n"
                + "porcentajeseccion,\n"
                + "porcentajeahorroissste,\n"
                + "observacionfaltas,\n"
                + "numerodepago,\n"
                + "totaldepagos,\n"
                + "folioissste,\n"
                + "numerocontrato,\n"
                + "tipocontratos,\n"
                + "nombreplantel,\n"
                + "numeroplantel,\n"
                + "P_48,\n"
                + "P_49,\n"
                + "P_50,\n"
                + "D_47,\n"
                + "D_48,\n"
                + "D_49,\n"
                + "D_50,\n"
                + "observacion,\n"
                + "fechabajaissste,\n"
                + "cveayudamutua,\n"
                + "cvecuotasindical,\n"
                + "caja_sutcobach,\n"
                + "caja_seccion,\n"
                + "caja_sitcobach,\n"
                + "cajaseccion63,\n"
                + "status,\n"
                + "motivobaja,numeroquincena)VALUES (?,	?,	?,	?,	?,	?,	?,	?,	?,"
                + "	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,"
                + "	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement psinsertar = null;
        ResultSet rsinsertar = null;
        int numFilaDatosCuotasYAportaciones = 10;
        int yearanterior;
        int periodointermedio;
        String periodoactual = reporte.getPeriodo() + reporte.getNumeroquincena();
        String peridoanteriorcadena;
        int periodoanterior = Integer.parseInt(periodoactual) - 1;

        //String numerodeperiodo;
        if (reporte.getNumeroquincena().equals("01")) {

            periodointermedio = Integer.parseInt(reporte.getPeriodo()) - 1;
            peridoanteriorcadena = periodointermedio + "24";
            periodoanterior = Integer.parseInt(peridoanteriorcadena);
        }
        //int numeroanterior;
        //int periodoanterior;
        //int periodo1ant;
        //int periodo2act;

        /*periodo = Integer.parseInt(reporte.getPeriodo());
        numerodeperiodo = Integer.parseInt(reporte.getNumeroquincena());
        numeroanterior = numerodeperiodo - 1;*/
 /*if(numerodeperiodo == 1){
        numeroanterior = 24;
        periodoanterior = periodo - 1;
        periodo1ant = periodoanterior + numeroanterior;
        
        }
        else{
        periodo1ant = periodo + numeroanterior;
        }*/
        try {
            ps = con.prepareStatement(sqlprueba);
            ps.setInt(1, periodoanterior);
            rs = ps.executeQuery();
            int numCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {

                try {
                    /////////////////////////////////////////////////////
                    psinsertar = con.prepareStatement(insertarnuevanomina);
                    psinsertar.setString(1, rs.getString(1));
                    psinsertar.setString(2, rs.getString(2));
                    psinsertar.setString(3, rs.getString(3));
                    psinsertar.setString(4, rs.getString(4));
                    psinsertar.setDouble(5, rs.getDouble(5));
                    psinsertar.setDouble(6, rs.getDouble(6));
                    psinsertar.setDouble(7, rs.getDouble(7));
                    psinsertar.setDouble(8, rs.getDouble(8));
                    psinsertar.setDouble(9, rs.getDouble(9));
                    psinsertar.setDouble(10, rs.getDouble(10));
                    psinsertar.setDouble(11, rs.getDouble(11));
                    psinsertar.setDouble(12, rs.getDouble(12));
                    psinsertar.setDouble(13, rs.getDouble(13));
                    psinsertar.setDouble(14, rs.getDouble(14));
                    psinsertar.setDouble(15, rs.getDouble(15));
                    psinsertar.setDouble(16, rs.getDouble(16));
                    psinsertar.setDouble(17, rs.getDouble(17));
                    psinsertar.setDouble(18, rs.getDouble(18));
                    psinsertar.setDouble(19, rs.getDouble(19));
                    psinsertar.setDouble(20, rs.getDouble(20));
                    psinsertar.setDouble(21, rs.getDouble(21));
                    psinsertar.setDouble(22, rs.getDouble(22));
                    psinsertar.setDouble(23, rs.getDouble(23));
                    psinsertar.setDouble(24, rs.getDouble(24));
                    psinsertar.setDouble(25, rs.getDouble(25));
                    psinsertar.setDouble(26, rs.getDouble(26));
                    psinsertar.setDouble(27, rs.getDouble(27));
                    psinsertar.setDouble(28, rs.getDouble(28));
                    psinsertar.setDouble(29, rs.getDouble(29));
                    psinsertar.setDouble(30, rs.getDouble(30));
                    psinsertar.setDouble(31, rs.getDouble(31));
                    psinsertar.setDouble(32, rs.getDouble(32));
                    psinsertar.setDouble(33, rs.getDouble(33));
                    psinsertar.setDouble(34, rs.getDouble(34));
                    psinsertar.setDouble(35, rs.getDouble(35));
                    psinsertar.setDouble(36, rs.getDouble(36));
                    psinsertar.setDouble(37, rs.getDouble(37));
                    psinsertar.setDouble(38, rs.getDouble(38));
                    psinsertar.setDouble(39, rs.getDouble(39));
                    psinsertar.setDouble(40, rs.getDouble(40));
                    psinsertar.setDouble(41, rs.getDouble(41));
                    psinsertar.setDouble(42, rs.getDouble(42));
                    psinsertar.setDouble(43, rs.getDouble(43));
                    psinsertar.setDouble(44, rs.getDouble(44));
                    psinsertar.setDouble(45, rs.getDouble(45));
                    psinsertar.setDouble(46, rs.getDouble(46));
                    psinsertar.setDouble(47, rs.getDouble(47));
                    psinsertar.setDouble(48, rs.getDouble(48));
                    psinsertar.setDouble(49, rs.getDouble(49));
                    psinsertar.setDouble(50, rs.getDouble(50));
                    psinsertar.setDouble(51, rs.getDouble(51));
                    psinsertar.setDouble(52, rs.getDouble(52));
                    psinsertar.setDouble(53, rs.getDouble(53));
                    psinsertar.setDouble(54, rs.getDouble(54));
                    psinsertar.setDouble(55, rs.getDouble(55));
                    psinsertar.setDouble(56, rs.getDouble(56));
                    psinsertar.setDouble(57, rs.getDouble(57));
                    psinsertar.setDouble(58, rs.getDouble(58));
                    psinsertar.setDouble(59, rs.getDouble(59));
                    psinsertar.setDouble(60, rs.getDouble(60));
                    psinsertar.setDouble(61, rs.getDouble(61));
                    psinsertar.setDouble(62, rs.getDouble(62));
                    psinsertar.setDouble(63, rs.getDouble(63));
                    psinsertar.setDouble(64, rs.getDouble(64));
                    psinsertar.setDouble(65, rs.getDouble(65));
                    psinsertar.setDouble(66, rs.getDouble(66));
                    psinsertar.setDouble(67, rs.getDouble(67));
                    psinsertar.setDouble(68, rs.getDouble(68));
                    psinsertar.setDouble(69, rs.getDouble(69));
                    psinsertar.setDouble(70, rs.getDouble(70));
                    psinsertar.setDouble(71, rs.getDouble(71));
                    psinsertar.setDouble(72, rs.getDouble(72));
                    psinsertar.setDouble(73, rs.getDouble(73));
                    psinsertar.setDouble(74, rs.getDouble(74));
                    psinsertar.setDouble(75, rs.getDouble(75));
                    psinsertar.setDouble(76, rs.getDouble(76));
                    psinsertar.setDouble(77, rs.getDouble(77));
                    psinsertar.setDouble(78, rs.getDouble(78));
                    psinsertar.setDouble(79, rs.getDouble(79));
                    psinsertar.setDouble(80, rs.getDouble(80));
                    psinsertar.setDouble(81, rs.getDouble(81));
                    psinsertar.setDouble(82, rs.getDouble(82));
                    psinsertar.setDouble(83, rs.getDouble(83));
                    psinsertar.setDouble(84, rs.getDouble(84));
                    psinsertar.setDouble(85, rs.getDouble(85));
                    psinsertar.setDouble(86, rs.getDouble(86));
                    psinsertar.setDouble(87, rs.getDouble(87));
                    psinsertar.setDouble(88, rs.getDouble(88));
                    psinsertar.setDouble(89, rs.getDouble(89));
                    psinsertar.setDouble(90, rs.getDouble(90));
                    psinsertar.setDouble(91, rs.getDouble(91));
                    psinsertar.setDouble(92, rs.getDouble(92));
                    psinsertar.setDouble(93, rs.getDouble(93));
                    psinsertar.setDouble(94, rs.getDouble(94));
                    psinsertar.setDouble(95, rs.getDouble(95));
                    psinsertar.setDouble(96, rs.getDouble(96));
                    psinsertar.setDouble(97, rs.getDouble(97));
                    psinsertar.setDouble(98, rs.getDouble(98));
                    psinsertar.setDouble(99, rs.getDouble(99));
                    psinsertar.setDouble(100, rs.getDouble(100));
                    psinsertar.setDouble(101, rs.getDouble(101));
                    psinsertar.setDouble(102, rs.getDouble(102));
                    psinsertar.setDouble(103, rs.getDouble(103));
                    psinsertar.setDouble(104, rs.getDouble(104));
                    psinsertar.setDouble(105, rs.getDouble(105));
                    psinsertar.setDouble(106, rs.getDouble(106));
                    psinsertar.setDouble(107, rs.getDouble(107));
                    psinsertar.setDouble(108, rs.getDouble(108));
                    psinsertar.setDouble(109, rs.getDouble(109));
                    psinsertar.setDouble(110, rs.getDouble(110));
                    psinsertar.setString(111, rs.getString(111));
                    psinsertar.setString(112, rs.getString(112));
                    psinsertar.setString(113, rs.getString(113));
                    psinsertar.setString(114, rs.getString(114));
                    psinsertar.setString(115, rs.getString(115));
                    psinsertar.setString(116, rs.getString(116));
                    psinsertar.setString(117, rs.getString(117));
                    psinsertar.setString(118, rs.getString(118));
                    psinsertar.setString(119, rs.getString(119));
                    psinsertar.setString(120, rs.getString(120));
                    psinsertar.setString(121, rs.getString(121));
                    psinsertar.setString(122, rs.getString(122));
                    psinsertar.setString(123, rs.getString(123));
                    psinsertar.setString(124, rs.getString(124));
                    psinsertar.setString(125, rs.getString(125));
                    psinsertar.setString(126, rs.getString(126));
                    psinsertar.setString(127, rs.getString(127));
                    psinsertar.setString(128, rs.getString(128));
                    psinsertar.setString(129, rs.getString(129));
                    psinsertar.setString(130, rs.getString(130));
                    psinsertar.setString(131, rs.getString(131));
                    psinsertar.setString(132, rs.getString(132));
                    psinsertar.setString(133, rs.getString(133));
                    psinsertar.setString(134, rs.getString(134));
                    psinsertar.setString(135, rs.getString(135));
                    psinsertar.setString(136, rs.getString(136));
                    psinsertar.setString(137, rs.getString(137));
                    psinsertar.setString(138, rs.getString(138));
                    psinsertar.setString(139, rs.getString(139));
                    psinsertar.setString(140, rs.getString(140));
                    psinsertar.setString(141, rs.getString(141));
                    psinsertar.setString(142, rs.getString(142));
                    psinsertar.setString(143, rs.getString(143));
                    psinsertar.setDouble(144, rs.getDouble(144));
                    psinsertar.setDouble(145, rs.getDouble(145));
                    psinsertar.setDouble(146, rs.getDouble(146));
                    psinsertar.setString(147, rs.getString(147));
                    psinsertar.setString(148, rs.getString(148));
                    psinsertar.setString(149, rs.getString(149));
                    psinsertar.setString(150, rs.getString(150));
                    psinsertar.setString(151, rs.getString(151));
                    psinsertar.setString(152, rs.getString(152));
                    psinsertar.setString(153, rs.getString(153));
                    psinsertar.setString(154, rs.getString(154));
                    psinsertar.setDouble(155, rs.getDouble(155));
                    psinsertar.setDouble(156, rs.getDouble(156));
                    psinsertar.setDouble(157, rs.getDouble(157));
                    psinsertar.setDouble(158, rs.getDouble(158));
                    psinsertar.setDouble(159, rs.getDouble(159));
                    psinsertar.setDouble(160, rs.getDouble(160));
                    psinsertar.setDouble(161, rs.getDouble(161));
                    psinsertar.setString(162, rs.getString(162));
                    psinsertar.setString(163, rs.getString(163));
                    psinsertar.setString(164, rs.getString(164));
                    psinsertar.setString(165, rs.getString(165));
                    psinsertar.setDouble(166, rs.getDouble(166));
                    psinsertar.setDouble(167, rs.getDouble(167));
                    psinsertar.setDouble(168, rs.getDouble(168));
                    psinsertar.setDouble(169, rs.getDouble(169));
                    psinsertar.setString(170, rs.getString(170));
                    psinsertar.setString(171, rs.getString(171));
                    psinsertar.setString(172, periodoactual);
                    ///////////////////////////////////////////////////
                    psinsertar.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                }
                numFilaDatosCuotasYAportaciones++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void importararchivolayouthojai(Reporte reporte) throws FileNotFoundException, IOException {
 
        int bandera = 1;
        Connection con = getConexion();
        Connection connota = getConexionnota();
        String sqlprueba = "select * FROM  hojai  WHERE numeroquincena = ? limit 1";
     
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        BufferedReader br = null;
        String claveparaimportar = reporte.getPeriodo();
        try {
            psVerifica = connota.prepareStatement(sqlprueba);
            psVerifica.setString(1, reporte.getPeriodo());
            rsverifica = psVerifica.executeQuery();
            
            if (!rsverifica.isBeforeFirst()) 
            {
                

        try {

            br = new BufferedReader(new FileReader(reporte.getRuta()));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                int campoRegimenPensionario =  fields.length ;
                
                
                //  System.out.println(Arrays.toString(fields));
               
                fields = removeTrailingQuotes(fields);
                //System.out.println(Arrays.toString(fields));
                ///////////EMPIEZA LA INSERCION A LA BASE DE DATOS
                PreparedStatement psinsertar = null;
                //Connection con = getConexion();
                 //<editor-fold desc="consulta insertar">
                          String insertarnuevanomina = "INSERT INTO hojai (RFC,\n"
                + "empleado,\n"
                + "periodo,\n"
                + "clavemvto,\n"
                + "P_01,\n"
                + "P_02,\n"
                + "P_03,\n"
                + "P_04,\n"
                + "P_05,\n"
                + "P_06,\n"
                + "P_07,\n"
                + "P_08,\n"
                + "P_09,\n"
                + "P_10,\n"
                + "P_11,\n"
                + "P_12,\n"
                + "P_13,\n"
                + "P_14,\n"
                + "P_15,\n"
                + "P_16,\n"
                + "P_17,\n"
                + "P_18,\n"
                + "P_19,\n"
                + "P_20,\n"
                + "P_21,\n"
                + "P_22,\n"
                + "P_23,\n"
                + "P_24,\n"
                + "P_25,\n"
                + "P_26,\n"
                + "P_27,\n"
                + "P_28,\n"
                + "P_29,\n"
                + "P_30,\n"
                + "D_01,\n"
                + "D_10,\n"
                + "P_DI,\n"
                + "P_C1,\n"
                + "P_C2,\n"
                + "P_31,\n"
                + "P_32,\n"
                + "P_33,\n"
                + "D_CB,\n"
                + "D_02,\n"
                + "D_03,\n"
                + "D_04,\n"
                + "D_05,\n"
                + "D_06,\n"
                + "D_07,\n"
                + "D_08,\n"
                + "D_09,\n"
                + "D_11,\n"
                + "D_12,\n"
                + "D_13,\n"
                + "D_14,\n"
                + "D_15,\n"
                + "D_16,\n"
                + "D_17,\n"
                + "D_18,\n"
                + "D_19,\n"
                + "D_20,\n"
                + "D_21,\n"
                + "D_22,\n"
                + "D_23,\n"
                + "D_24,\n"
                + "D_25,\n"
                + "D_26,\n"
                + "D_27,\n"
                + "D_28,\n"
                + "P_MC,\n"
                + "P_PG,\n"
                + "P_PE,\n"
                + "P_36,\n"
                + "D_29,\n"
                + "P_37,\n"
                + "D_30,\n"
                + "D_31,\n"
                + "P_35,\n"
                + "P_38,\n"
                + "P_39,\n"
                + "D_32,\n"
                + "D_33,\n"
                + "D_34,\n"
                + "D_35,\n"
                + "P_40,\n"
                + "D_36,\n"
                + "D_37,\n"
                + "P_01A,\n"
                + "P_01B,\n"
                + "P_01C,\n"
                + "P_01D,\n"
                + "P_01E,\n"
                + "P_34,\n"
                + "D_38,\n"
                + "P_41,\n"
                + "P_42,\n"
                + "P_43,\n"
                + "P_44,\n"
                + "P_45,\n"
                + "P_46,\n"
                + "P_47,\n"
                + "D_39,\n"
                + "D_40,\n"
                + "D_41,\n"
                + "D_42,\n"
                + "D_01A,\n"
                + "D_43,\n"
                + "D_44,\n"
                + "D_45,\n"
                + "D_46,\n"
                + "D_47,\n"
                + "D_48,\n"
                + "D_49,\n"
                + "D_50,\n"
                + "TIPO,\n"
                + "QUINCENA,\n"
                + "LAPSO,\n"
                + "PLANTEL,\n"
                + "NOPUESTO,\n"
                + "LEIDO,\n"
                + "AP_PAT,\n"
                + "AP_MAT,\n"
                + "NOMBRE,\n"
                + "CATEGORIA,\n"
                + "HORAS,\n"
                + "NIVEL,\n"
                + "FECHAOLD,\n"
                + "TIPOPER,\n"
                + "ZONA,\n"
                + "SEMESTRE,\n"
                + "TPLANTEL,\n"
                + "TIPOSIN,\n"
                + "MUTUAL,\n"
                + "FACORE,\n"
                + "CEDULAIMSS,\n"
                + "REGIMENPENSIONARIO,\n"
                + "numeroquincena)VALUES ("
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,"
                + "	?,	?,	 ?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,?,?,?)";
 
                ///son 136 signos de interrogacion
                
                     //</editor-fold >
                     ///
            
                
                try {

                   // psinsertar = connota.prepareStatement(insertarnuevanomina);
                        //<editor-fold desc="insertardatos">
            
                    psinsertar = connota.prepareStatement(insertarnuevanomina);
                    psinsertar.setString(1, fields[0]);
                    psinsertar.setString(2, fields[1]);
                    psinsertar.setString(3, fields[2]);
                    psinsertar.setString(4, fields[3]);
                    psinsertar.setDouble(5,Double.parseDouble(fields[4]));
                    psinsertar.setDouble(6,Double.parseDouble( fields[5]));
                    psinsertar.setDouble(7,Double.parseDouble( fields[6]));
                    psinsertar.setDouble(8,Double.parseDouble( fields[7]));
                    psinsertar.setDouble(9,Double.parseDouble( fields[8]));
                    psinsertar.setDouble(10,Double.parseDouble( fields[9]));
                    psinsertar.setDouble(11,Double.parseDouble( fields[10]));
                    psinsertar.setDouble(12,Double.parseDouble( fields[11]));
                    psinsertar.setDouble(13,Double.parseDouble( fields[12]));
                    psinsertar.setDouble(14,Double.parseDouble( fields[13]));
                    psinsertar.setDouble(15,Double.parseDouble( fields[14]));
                    psinsertar.setDouble(16,Double.parseDouble( fields[15]));
                    psinsertar.setDouble(17,Double.parseDouble( fields[16]));
                    psinsertar.setDouble(18,Double.parseDouble( fields[17]));
                    psinsertar.setDouble(19,Double.parseDouble( fields[18]));
                    psinsertar.setDouble(20,Double.parseDouble( fields[19]));
                    psinsertar.setDouble(21,Double.parseDouble( fields[20]));
                    psinsertar.setDouble(22,Double.parseDouble( fields[21]));
                    psinsertar.setDouble(23,Double.parseDouble( fields[22]));
                    psinsertar.setDouble(24,Double.parseDouble( fields[23]));
                    psinsertar.setDouble(25,Double.parseDouble( fields[24]));
                    psinsertar.setDouble(26,Double.parseDouble( fields[25]));
                    psinsertar.setDouble(27,Double.parseDouble( fields[26]));
                    psinsertar.setDouble(28,Double.parseDouble( fields[27]));
                    psinsertar.setDouble(29,Double.parseDouble( fields[28]));
                    psinsertar.setDouble(30,Double.parseDouble( fields[29]));
                    psinsertar.setDouble(31,Double.parseDouble( fields[30]));
                    psinsertar.setDouble(32,Double.parseDouble( fields[31]));
                    psinsertar.setDouble(33,Double.parseDouble( fields[32]));
                    psinsertar.setDouble(34,Double.parseDouble( fields[33]));
                    psinsertar.setDouble(35,Double.parseDouble( fields[34]));
                    psinsertar.setDouble(36,Double.parseDouble( fields[35]));
                    psinsertar.setDouble(37,Double.parseDouble( fields[36]));
                    psinsertar.setDouble(38,Double.parseDouble( fields[37]));
                    psinsertar.setDouble(39,Double.parseDouble( fields[38]));
                    psinsertar.setDouble(40,Double.parseDouble( fields[39]));
                    psinsertar.setDouble(41, Double.parseDouble(fields[40]));
                    psinsertar.setDouble(42, Double.parseDouble(fields[41]));
                    psinsertar.setDouble(43,Double.parseDouble( fields[42]));
                    psinsertar.setDouble(44,Double.parseDouble( fields[43]));
                    psinsertar.setDouble(45,Double.parseDouble( fields[44]));
                    psinsertar.setDouble(46,Double.parseDouble( fields[45]));
                    psinsertar.setDouble(47,Double.parseDouble( fields[46]));
                    psinsertar.setDouble(48,Double.parseDouble( fields[47]));
                    psinsertar.setDouble(49,Double.parseDouble( fields[48]));
                    psinsertar.setDouble(50,Double.parseDouble( fields[49]));
                    psinsertar.setDouble(51,Double.parseDouble( fields[50]));
                    psinsertar.setDouble(52,Double.parseDouble( fields[51]));
                    psinsertar.setDouble(53,Double.parseDouble( fields[52]));
                    psinsertar.setDouble(54,Double.parseDouble( fields[53]));
                    psinsertar.setDouble(55,Double.parseDouble( fields[54]));
                    psinsertar.setDouble(56,Double.parseDouble( fields[55]));
                    psinsertar.setDouble(57,Double.parseDouble( fields[56]));
                    psinsertar.setDouble(58,Double.parseDouble( fields[57]));
                    psinsertar.setDouble(59, Double.parseDouble(fields[58]));
                    psinsertar.setDouble(60,Double.parseDouble( fields[59]));
                    psinsertar.setDouble(61,Double.parseDouble( fields[60]));
                    psinsertar.setDouble(62,Double.parseDouble( fields[61]));
                    psinsertar.setDouble(63,Double.parseDouble( fields[62]));
                    psinsertar.setDouble(64,Double.parseDouble( fields[63]));
                    psinsertar.setDouble(65,Double.parseDouble( fields[64]));
                    psinsertar.setDouble(66,Double.parseDouble( fields[65]));
                    psinsertar.setDouble(67,Double.parseDouble( fields[66]));
                    psinsertar.setDouble(68,Double.parseDouble( fields[67]));
                    psinsertar.setDouble(69,Double.parseDouble( fields[68]));
                    psinsertar.setDouble(70,Double.parseDouble( fields[69]));
                    psinsertar.setDouble(71,Double.parseDouble( fields[70]));
                    psinsertar.setDouble(72,Double.parseDouble( fields[71]));
                    psinsertar.setDouble(73,Double.parseDouble( fields[72]));
                    psinsertar.setDouble(74,Double.parseDouble( fields[73]));
                    psinsertar.setDouble(75,Double.parseDouble( fields[74]));
                    psinsertar.setDouble(76, Double.parseDouble(fields[75]));
                    psinsertar.setDouble(77,Double.parseDouble( fields[76]));
                    psinsertar.setDouble(78,Double.parseDouble( fields[77]));
                    psinsertar.setDouble(79,Double.parseDouble( fields[78]));
                    psinsertar.setDouble(80,Double.parseDouble( fields[79]));
                    psinsertar.setDouble(81,Double.parseDouble( fields[80]));
                    psinsertar.setDouble(82,Double.parseDouble( fields[81]));
                    psinsertar.setDouble(83,Double.parseDouble( fields[82]));
                    psinsertar.setDouble(84,Double.parseDouble( fields[83]));
                    psinsertar.setDouble(85,Double.parseDouble( fields[84]));
                    psinsertar.setDouble(86,Double.parseDouble( fields[85]));
                    psinsertar.setDouble(87,Double.parseDouble( fields[86]));
                    psinsertar.setDouble(88,Double.parseDouble( fields[87]));
                    psinsertar.setDouble(89,Double.parseDouble( fields[88]));
                    psinsertar.setDouble(90,Double.parseDouble( fields[89]));
                    psinsertar.setDouble(91,Double.parseDouble( fields[90]));
                    psinsertar.setDouble(92,Double.parseDouble( fields[91]));
                    psinsertar.setDouble(93,Double.parseDouble( fields[92]));
                    psinsertar.setDouble(94,Double.parseDouble( fields[93]));
                    psinsertar.setDouble(95,Double.parseDouble( fields[94]));
                    psinsertar.setDouble(96,Double.parseDouble( fields[95]));
                    psinsertar.setDouble(97,Double.parseDouble( fields[96]));
                    psinsertar.setDouble(98,Double.parseDouble( fields[97]));
                    psinsertar.setDouble(99, Double.parseDouble(fields[98]));
                    psinsertar.setDouble(100,Double.parseDouble( fields[99]));
                    psinsertar.setDouble(101,Double.parseDouble( fields[100]));
                    psinsertar.setDouble(102,Double.parseDouble( fields[101]));
                    psinsertar.setDouble(103,Double.parseDouble( fields[102]));
                    psinsertar.setDouble(104,Double.parseDouble( fields[103]));
                    psinsertar.setDouble(105, Double.parseDouble(fields[104]));
                    psinsertar.setDouble(106, Double.parseDouble(fields[105]));
                    psinsertar.setDouble(107,Double.parseDouble( fields[106]));
                    psinsertar.setDouble(108,Double.parseDouble( fields[107]));
                    psinsertar.setDouble(109,Double.parseDouble( fields[108]));
                    psinsertar.setDouble(110,Double.parseDouble( fields[109]));
                    psinsertar.setDouble(111,Double.parseDouble( fields[110]));
                    psinsertar.setDouble(112,Double.parseDouble( fields[111]));
                    psinsertar.setDouble(113,Double.parseDouble(fields[112]));
                    psinsertar.setDouble(114,Double.parseDouble(fields[113]));
                    psinsertar.setString(115,fields[114]);
                    psinsertar.setString(116, fields[115]);
                    psinsertar.setString(117, fields[116]);
                    psinsertar.setString(118, fields[117]);
                    psinsertar.setString(119, fields[118]);
                    psinsertar.setString(120, fields[119]);
                    psinsertar.setString(121, fields[120]);
                    psinsertar.setString(122, fields[121]);
                    psinsertar.setString(123, fields[122]);
                    psinsertar.setString(124, fields[123]);
                    psinsertar.setString(125, fields[124]);
                    psinsertar.setString(126, fields[125]);
                    psinsertar.setString(127, fields[126]);
                    psinsertar.setString(128, fields[127]);
                    psinsertar.setString(129, fields[128]);
                    psinsertar.setString(130, fields[129]);
                    psinsertar.setString(131, fields[130]);
                    psinsertar.setString(132, fields[131]);
                    psinsertar.setString(133, fields[132]);
                    psinsertar.setDouble(134,Double.parseDouble(fields[133]));
                    psinsertar.setString(135, fields[134]);
                    if(campoRegimenPensionario == 135)
                        {
                        psinsertar.setString(136, "0");
                        } 
                    
                    else{
                      psinsertar.setString(136, fields[135]);
                    
                    }
                    psinsertar.setString(137, reporte.getPeriodo());
                    //</editor-fold >
                    /////
                    
                    psinsertar.execute();
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
                JOptionPane.showMessageDialog(null, "La qna ya esta dada de alta");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        

    }
    
    public void procesarnuevanominanota(Reporte reporte) {
        Connection con = getConexion();
        Connection connota = getConexionnota();
        String sqlprueba = "select RFC,\n"
                + "empleado,\n"
                + "periodo,\n"
                + "clavemvto,\n"
                + "P_01,\n"
                + "P_02,\n"
                + "P_03,\n"
                + "P_04,\n"
                + "P_05,\n"
                + "P_06,\n"
                + "P_07,\n"
                + "P_08,\n"
                + "P_09,\n"
                + "P_10,\n"
                + "P_11,\n"
                + "P_12,\n"
                + "P_13,\n"
                + "P_14,\n"
                + "P_15,\n"
                + "P_16,\n"
                + "P_17,\n"
                + "P_18,\n"
                + "P_19,\n"
                + "P_20,\n"
                + "P_21,\n"
                + "P_22,\n"
                + "P_23,\n"
                + "P_24,\n"
                + "P_25,\n"
                + "P_26,\n"
                + "P_27,\n"
                + "P_28,\n"
                + "P_29,\n"
                + "P_30,\n"
                + "D_01,\n"
                + "D_10,\n"
                + "P_DI,\n"
                + "P_C1,\n"
                + "P_C2,\n"
                + "P_31,\n"
                + "P_32,\n"
                + "P_33,\n"
                + "D_CB,\n"
                + "D_02,\n"
                + "D_03,\n"
                + "D_04,\n"
                + "D_05,\n"
                + "D_06,\n"
                + "D_07,\n"
                + "D_08,\n"
                + "D_09,\n"
                + "D_11,\n"
                + "D_12,\n"
                + "D_13,\n"
                + "D_14,\n"
                + "D_15,\n"
                + "D_16,\n"
                + "D_17,\n"
                + "D_18,\n"
                + "D_19,\n"
                + "D_20,\n"
                + "D_21,\n"
                + "D_22,\n"
                + "D_23,\n"
                + "D_24,\n"
                + "D_25,\n"
                + "D_26,\n"
                + "D_27,\n"
                + "D_28,\n"
                + "P_MC,\n"
                + "P_PG,\n"
                + "P_PE,\n"
                + "P_36,\n"
                + "D_29,\n"
                + "P_37,\n"
                + "D_30,\n"
                + "D_31,\n"
                + "P_35,\n"
                + "P_38,\n"
                + "P_39,\n"
                + "D_32,\n"
                + "D_33,\n"
                + "D_34,\n"
                + "D_35,\n"
                + "P_40,\n"
                + "D_36,\n"
                + "D_37,\n"
                + "P_01A,\n"
                + "P_01B,\n"
                + "P_01C,\n"
                + "P_01D,\n"
                + "P_01E,\n"
                + "P_34,\n"
                + "D_38,\n"
                + "P_41,\n"
                + "P_42,\n"
                + "P_43,\n"
                + "P_44,\n"
                + "P_45,\n"
                + "P_46,\n"
                + "P_47,\n"
                + "D_39,\n"
                + "D_40,\n"
                + "D_41,\n"
                + "D_42,\n"
                + "D_01A,\n"
                + "D_43,\n"
                + "D_44,\n"
                + "D_45,\n"
                + "D_46,\n"
                + "D_47,\n"
                + "D_48,\n"
                + "D_49,\n"
                + "D_50,\n"
                + "TIPO,\n"
                + "QUINCENA,\n"
                + "LAPSO,\n"
                + "PLANTEL,\n"
                + "NOPUESTO,\n"
                + "LEIDO,\n"
                + "AP_PAT,\n"
                + "AP_MAT,\n"
                + "NOMBRE,\n"
                + "CATEGORIA,\n"
                + "HORAS,\n"
                + "NIVEL,\n"
                + "FECHAOLD,\n"
                + "TIPOPER,\n"
                + "ZONA,\n"
                + "SEMESTRE,\n"
                + "TPLANTEL,\n"
                + "TIPOSIN,\n"
                + "MUTUAL,\n"
                + "FACORE,\n"
                + "CEDULAIMSS,\n"
                + "REGIMENPENSIONARIO"
                + " FROM hojai WHERE numeroquincena = ? ";
        String insertarnuevanomina = "INSERT INTO hojaicp (RFC,\n"
                + "empleado,\n"
                + "periodo,\n"
                + "clavemvto,\n"
                + "P_01,\n"
                + "P_02,\n"
                + "P_03,\n"
                + "P_04,\n"
                + "P_05,\n"
                + "P_06,\n"
                + "P_07,\n"
                + "P_08,\n"
                + "P_09,\n"
                + "P_10,\n"
                + "P_11,\n"
                + "P_12,\n"
                + "P_13,\n"
                + "P_14,\n"
                + "P_15,\n"
                + "P_16,\n"
                + "P_17,\n"
                + "P_18,\n"
                + "P_19,\n"
                + "P_20,\n"
                + "P_21,\n"
                + "P_22,\n"
                + "P_23,\n"
                + "P_24,\n"
                + "P_25,\n"
                + "P_26,\n"
                + "P_27,\n"
                + "P_28,\n"
                + "P_29,\n"
                + "P_30,\n"
                + "D_01,\n"
                + "D_10,\n"
                + "P_DI,\n"
                + "P_C1,\n"
                + "P_C2,\n"
                + "P_31,\n"
                + "P_32,\n"
                + "P_33,\n"
                + "D_CB,\n"
                + "D_02,\n"
                + "D_03,\n"
                + "D_04,\n"
                + "D_05,\n"
                + "D_06,\n"
                + "D_07,\n"
                + "D_08,\n"
                + "D_09,\n"
                + "D_11,\n"
                + "D_12,\n"
                + "D_13,\n"
                + "D_14,\n"
                + "D_15,\n"
                + "D_16,\n"
                + "D_17,\n"
                + "D_18,\n"
                + "D_19,\n"
                + "D_20,\n"
                + "D_21,\n"
                + "D_22,\n"
                + "D_23,\n"
                + "D_24,\n"
                + "D_25,\n"
                + "D_26,\n"
                + "D_27,\n"
                + "D_28,\n"
                + "P_MC,\n"
                + "P_PG,\n"
                + "P_PE,\n"
                + "P_36,\n"
                + "D_29,\n"
                + "P_37,\n"
                + "D_30,\n"
                + "D_31,\n"
                + "P_35,\n"
                + "P_38,\n"
                + "P_39,\n"
                + "D_32,\n"
                + "D_33,\n"
                + "D_34,\n"
                + "D_35,\n"
                + "P_40,\n"
                + "D_36,\n"
                + "D_37,\n"
                + "P_01A,\n"
                + "P_01B,\n"
                + "P_01C,\n"
                + "P_01D,\n"
                + "P_01E,\n"
                + "P_34,\n"
                + "D_38,\n"
                + "P_41,\n"
                + "P_42,\n"
                + "P_43,\n"
                + "P_44,\n"
                + "P_45,\n"
                + "P_46,\n"
                + "P_47,\n"
                + "D_39,\n"
                + "D_40,\n"
                + "D_41,\n"
                + "D_42,\n"
                + "D_01A,\n"
                + "D_43,\n"
                + "D_44,\n"
                + "D_45,\n"
                + "D_46,\n"
                + "D_47,\n"
                + "D_48,\n"
                + "D_49,\n"
                + "D_50,\n"
                + "TIPO,\n"
                + "QUINCENA,\n"
                + "LAPSO,\n"
                + "PLANTEL,\n"
                + "NOPUESTO,\n"
                + "LEIDO,\n"
                + "AP_PAT,\n"
                + "AP_MAT,\n"
                + "NOMBRE,\n"
                + "CATEGORIA,\n"
                + "HORAS,\n"
                + "NIVEL,\n"
                + "FECHAOLD,\n"
                + "TIPOPER,\n"
                + "ZONA,\n"
                + "SEMESTRE,\n"
                + "TPLANTEL,\n"
                + "TIPOSIN,\n"
                + "MUTUAL,\n"
                + "FACORE,\n"
                + "CEDULAIMSS,\n"
                + "REGIMENPENSIONARIO,\n"
                + "numeroquincena)VALUES ("
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,"
                + "	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement psinsertar = null;
        ResultSet rsinsertar = null;
        int numFilaDatosCuotasYAportaciones = 10;
        int yearanterior;
        int periodointermedio;
        String periodoactual = reporte.getPeriodo() + reporte.getNumeroquincena();
        String peridoanteriorcadena;
        int periodoanterior = Integer.parseInt(periodoactual) - 1;

        //String numerodeperiodo;
        if (reporte.getNumeroquincena().equals("01")) {

            periodointermedio = Integer.parseInt(reporte.getPeriodo()) - 1;
            peridoanteriorcadena = periodointermedio + "24";
            periodoanterior = Integer.parseInt(peridoanteriorcadena);
        }
        //int numeroanterior;
        //int periodoanterior;
        //int periodo1ant;
        //int periodo2act;

        /*periodo = Integer.parseInt(reporte.getPeriodo());
        numerodeperiodo = Integer.parseInt(reporte.getNumeroquincena());
        numeroanterior = numerodeperiodo - 1;*/
 /*if(numerodeperiodo == 1){
        numeroanterior = 24;
        periodoanterior = periodo - 1;
        periodo1ant = periodoanterior + numeroanterior;
        
        }
        else{
        periodo1ant = periodo + numeroanterior;
        }*/
        try {
            ps = connota.prepareStatement(sqlprueba);
            ps.setString(1, periodoactual);
            rs = ps.executeQuery();
            int numCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {

                try {
                    /////////////////////////////////////////////////////
                    psinsertar = connota.prepareStatement(insertarnuevanomina);
                    psinsertar.setString(1, rs.getString(1));
                    psinsertar.setString(2, rs.getString(2));
                    psinsertar.setString(3, rs.getString(3));
                    psinsertar.setString(4, rs.getString(4));
                    psinsertar.setDouble(5, rs.getDouble(5));
                    psinsertar.setDouble(6, rs.getDouble(6));
                    psinsertar.setDouble(7, rs.getDouble(7));
                    psinsertar.setDouble(8, rs.getDouble(8));
                    psinsertar.setDouble(9, rs.getDouble(9));
                    psinsertar.setDouble(10, rs.getDouble(10));
                    psinsertar.setDouble(11, rs.getDouble(11));
                    psinsertar.setDouble(12, rs.getDouble(12));
                    psinsertar.setDouble(13, rs.getDouble(13));
                    psinsertar.setDouble(14, rs.getDouble(14));
                    psinsertar.setDouble(15, rs.getDouble(15));
                    psinsertar.setDouble(16, rs.getDouble(16));
                    psinsertar.setDouble(17, rs.getDouble(17));
                    psinsertar.setDouble(18, rs.getDouble(18));
                    psinsertar.setDouble(19, rs.getDouble(19));
                    psinsertar.setDouble(20, rs.getDouble(20));
                    psinsertar.setDouble(21, rs.getDouble(21));
                    psinsertar.setDouble(22, rs.getDouble(22));
                    psinsertar.setDouble(23, rs.getDouble(23));
                    psinsertar.setDouble(24, rs.getDouble(24));
                    psinsertar.setDouble(25, rs.getDouble(25));
                    psinsertar.setDouble(26, rs.getDouble(26));
                    psinsertar.setDouble(27, rs.getDouble(27));
                    psinsertar.setDouble(28, rs.getDouble(28));
                    psinsertar.setDouble(29, rs.getDouble(29));
                    psinsertar.setDouble(30, rs.getDouble(30));
                    psinsertar.setDouble(31, rs.getDouble(31));
                    psinsertar.setDouble(32, rs.getDouble(32));
                    psinsertar.setDouble(33, rs.getDouble(33));
                    psinsertar.setDouble(34, rs.getDouble(34));
                    psinsertar.setDouble(35, rs.getDouble(35));
                    psinsertar.setDouble(36, rs.getDouble(36));
                    psinsertar.setDouble(37, rs.getDouble(37));
                    psinsertar.setDouble(38, rs.getDouble(38));
                    psinsertar.setDouble(39, rs.getDouble(39));
                    psinsertar.setDouble(40, rs.getDouble(40));
                    psinsertar.setDouble(41, rs.getDouble(41));
                    psinsertar.setDouble(42, rs.getDouble(42));
                    psinsertar.setDouble(43, rs.getDouble(43));
                    psinsertar.setDouble(44, rs.getDouble(44));
                    psinsertar.setDouble(45, rs.getDouble(45));
                    psinsertar.setDouble(46, rs.getDouble(46));
                    psinsertar.setDouble(47, rs.getDouble(47));
                    psinsertar.setDouble(48, rs.getDouble(48));
                    psinsertar.setDouble(49, rs.getDouble(49));
                    psinsertar.setDouble(50, rs.getDouble(50));
                    psinsertar.setDouble(51, rs.getDouble(51));
                    psinsertar.setDouble(52, rs.getDouble(52));
                    psinsertar.setDouble(53, rs.getDouble(53));
                    psinsertar.setDouble(54, rs.getDouble(54));
                    psinsertar.setDouble(55, rs.getDouble(55));
                    psinsertar.setDouble(56, rs.getDouble(56));
                    psinsertar.setDouble(57, rs.getDouble(57));
                    psinsertar.setDouble(58, rs.getDouble(58));
                    psinsertar.setDouble(59, rs.getDouble(59));
                    psinsertar.setDouble(60, rs.getDouble(60));
                    psinsertar.setDouble(61, rs.getDouble(61));
                    psinsertar.setDouble(62, rs.getDouble(62));
                    psinsertar.setDouble(63, rs.getDouble(63));
                    psinsertar.setDouble(64, rs.getDouble(64));
                    psinsertar.setDouble(65, rs.getDouble(65));
                    psinsertar.setDouble(66, rs.getDouble(66));
                    psinsertar.setDouble(67, rs.getDouble(67));
                    psinsertar.setDouble(68, rs.getDouble(68));
                    psinsertar.setDouble(69, rs.getDouble(69));
                    psinsertar.setDouble(70, rs.getDouble(70));
                    psinsertar.setDouble(71, rs.getDouble(71));
                    psinsertar.setDouble(72, rs.getDouble(72));
                    psinsertar.setDouble(73, rs.getDouble(73));
                    psinsertar.setDouble(74, rs.getDouble(74));
                    psinsertar.setDouble(75, rs.getDouble(75));
                    psinsertar.setDouble(76, rs.getDouble(76));
                    psinsertar.setDouble(77, rs.getDouble(77));
                    psinsertar.setDouble(78, rs.getDouble(78));
                    psinsertar.setDouble(79, rs.getDouble(79));
                    psinsertar.setDouble(80, rs.getDouble(80));
                    psinsertar.setDouble(81, rs.getDouble(81));
                    psinsertar.setDouble(82, rs.getDouble(82));
                    psinsertar.setDouble(83, rs.getDouble(83));
                    psinsertar.setDouble(84, rs.getDouble(84));
                    psinsertar.setDouble(85, rs.getDouble(85));
                    psinsertar.setDouble(86, rs.getDouble(86));
                    psinsertar.setDouble(87, rs.getDouble(87));
                    psinsertar.setDouble(88, rs.getDouble(88));
                    psinsertar.setDouble(89, rs.getDouble(89));
                    psinsertar.setDouble(90, rs.getDouble(90));
                    psinsertar.setDouble(91, rs.getDouble(91));
                    psinsertar.setDouble(92, rs.getDouble(92));
                    psinsertar.setDouble(93, rs.getDouble(93));
                    psinsertar.setDouble(94, rs.getDouble(94));
                    psinsertar.setDouble(95, rs.getDouble(95));
                    psinsertar.setDouble(96, rs.getDouble(96));
                    psinsertar.setDouble(97, rs.getDouble(97));
                    psinsertar.setDouble(98, rs.getDouble(98));
                    psinsertar.setDouble(99, rs.getDouble(99));
                    psinsertar.setDouble(100, rs.getDouble(100));
                    psinsertar.setDouble(101, rs.getDouble(101));
                    psinsertar.setDouble(102, rs.getDouble(102));
                    psinsertar.setDouble(103, rs.getDouble(103));
                    psinsertar.setDouble(104, rs.getDouble(104));
                    psinsertar.setDouble(105, rs.getDouble(105));
                    psinsertar.setDouble(106, rs.getDouble(106));
                    psinsertar.setDouble(107, rs.getDouble(107));
                    psinsertar.setDouble(108, rs.getDouble(108));
                    psinsertar.setDouble(109, rs.getDouble(109));
                    psinsertar.setDouble(110, rs.getDouble(110));
                    psinsertar.setDouble(111, rs.getDouble(111));
                    psinsertar.setDouble(112, rs.getDouble(112));
                    psinsertar.setDouble(113, rs.getDouble(113));
                    psinsertar.setDouble(114, rs.getDouble(114));
                    psinsertar.setString(115, rs.getString(115));
                    psinsertar.setString(116, rs.getString(116));
                    psinsertar.setString(117, rs.getString(117));
                    psinsertar.setString(118, rs.getString(118));
                    psinsertar.setString(119, rs.getString(119));
                    psinsertar.setString(120, rs.getString(120));
                    psinsertar.setString(121, rs.getString(121));
                    psinsertar.setString(122, rs.getString(122));
                    psinsertar.setString(123, rs.getString(123));
                    psinsertar.setString(124, rs.getString(124));
                    psinsertar.setString(125, rs.getString(125));
                    psinsertar.setString(126, rs.getString(126));
                    psinsertar.setString(127, rs.getString(127));
                    psinsertar.setString(128, rs.getString(128));
                    psinsertar.setString(129, rs.getString(129));
                    psinsertar.setString(130, rs.getString(130));
                    psinsertar.setString(131, rs.getString(131));
                    psinsertar.setString(132, rs.getString(132));
                    psinsertar.setString(133, rs.getString(133));
                    psinsertar.setString(134, rs.getString(134));
                    psinsertar.setString(135, rs.getString(135));
                    psinsertar.setString(136, rs.getString(136));
                    psinsertar.setString(137, periodoactual);
                    ///////////////////////////////////////////////////
                    psinsertar.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                }
                numFilaDatosCuotasYAportaciones++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public boolean comprobarqnanota (Reporte reporte){
        Connection connota = getConexionnota();
        String sqlprueba = "select * FROM  hojaicp  WHERE  numeroquincena = ?";
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        String periodoactual = reporte.getPeriodo() + reporte.getNumeroquincena();
        try {
            psVerifica = connota.prepareStatement(sqlprueba);
            //psVerifica.setInt(1, persona.getEmpleado());
            psVerifica.setString(1, periodoactual);
           rsverifica = psVerifica.executeQuery();
            if (!rsverifica.next()) {

                return true;
            } else {
                
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
     
    }
    
    public void procesarnuevanominanotaRemoto(Reporte reporte) {
        Connection con = getConexion();
        Connection connota = getConexionnota();
        Connection connotaremoto = getConexionnotaRemoto();
        String sqlprueba = "select RFC,\n"
                + "empleado,\n"
                + "periodo,\n"
                + "clavemvto,\n"
                + "P_01,\n"
                + "P_02,\n"
                + "P_03,\n"
                + "P_04,\n"
                + "P_05,\n"
                + "P_06,\n"
                + "P_07,\n"
                + "P_08,\n"
                + "P_09,\n"
                + "P_10,\n"
                + "P_11,\n"
                + "P_12,\n"
                + "P_13,\n"
                + "P_14,\n"
                + "P_15,\n"
                + "P_16,\n"
                + "P_17,\n"
                + "P_18,\n"
                + "P_19,\n"
                + "P_20,\n"
                + "P_21,\n"
                + "P_22,\n"
                + "P_23,\n"
                + "P_24,\n"
                + "P_25,\n"
                + "P_26,\n"
                + "P_27,\n"
                + "P_28,\n"
                + "P_29,\n"
                + "P_30,\n"
                + "D_01,\n"
                + "D_10,\n"
                + "P_DI,\n"
                + "P_C1,\n"
                + "P_C2,\n"
                + "P_31,\n"
                + "P_32,\n"
                + "P_33,\n"
                + "D_CB,\n"
                + "D_02,\n"
                + "D_03,\n"
                + "D_04,\n"
                + "D_05,\n"
                + "D_06,\n"
                + "D_07,\n"
                + "D_08,\n"
                + "D_09,\n"
                + "D_11,\n"
                + "D_12,\n"
                + "D_13,\n"
                + "D_14,\n"
                + "D_15,\n"
                + "D_16,\n"
                + "D_17,\n"
                + "D_18,\n"
                + "D_19,\n"
                + "D_20,\n"
                + "D_21,\n"
                + "D_22,\n"
                + "D_23,\n"
                + "D_24,\n"
                + "D_25,\n"
                + "D_26,\n"
                + "D_27,\n"
                + "D_28,\n"
                + "P_MC,\n"
                + "P_PG,\n"
                + "P_PE,\n"
                + "P_36,\n"
                + "D_29,\n"
                + "P_37,\n"
                + "D_30,\n"
                + "D_31,\n"
                + "P_35,\n"
                + "P_38,\n"
                + "P_39,\n"
                + "D_32,\n"
                + "D_33,\n"
                + "D_34,\n"
                + "D_35,\n"
                + "P_40,\n"
                + "D_36,\n"
                + "D_37,\n"
                + "P_01A,\n"
                + "P_01B,\n"
                + "P_01C,\n"
                + "P_01D,\n"
                + "P_01E,\n"
                + "P_34,\n"
                + "D_38,\n"
                + "P_41,\n"
                + "P_42,\n"
                + "P_43,\n"
                + "P_44,\n"
                + "P_45,\n"
                + "P_46,\n"
                + "P_47,\n"
                + "D_39,\n"
                + "D_40,\n"
                + "D_41,\n"
                + "D_42,\n"
                + "D_01A,\n"
                + "D_43,\n"
                + "D_44,\n"
                + "D_45,\n"
                + "D_46,\n"
                + "D_47,\n"
                + "D_48,\n"
                + "D_49,\n"
                + "D_50,\n"
                + "TIPO,\n"
                + "QUINCENA,\n"
                + "LAPSO,\n"
                + "PLANTEL,\n"
                + "NOPUESTO,\n"
                + "LEIDO,\n"
                + "AP_PAT,\n"
                + "AP_MAT,\n"
                + "NOMBRE,\n"
                + "CATEGORIA,\n"
                + "HORAS,\n"
                + "NIVEL,\n"
                + "FECHAOLD,\n"
                + "TIPOPER,\n"
                + "ZONA,\n"
                + "SEMESTRE,\n"
                + "TPLANTEL,\n"
                + "TIPOSIN,\n"
                + "MUTUAL,\n"
                + "FACORE,\n"
                + "CEDULAIMSS,\n"
                + "REGIMENPENSIONARIO"
                + " FROM hojai WHERE numeroquincena = ? ";
            String sqlinsertarnuevanominaremoto = "INSERT INTO hojai (RFC,\n"
                + "empleado,\n"
                + "periodo,\n"
                + "clavemvto,\n"
                + "P_01,\n"
                + "P_02,\n"
                + "P_03,\n"
                + "P_04,\n"
                + "P_05,\n"
                + "P_06,\n"
                + "P_07,\n"
                + "P_08,\n"
                + "P_09,\n"
                + "P_10,\n"
                + "P_11,\n"
                + "P_12,\n"
                + "P_13,\n"
                + "P_14,\n"
                + "P_15,\n"
                + "P_16,\n"
                + "P_17,\n"
                + "P_18,\n"
                + "P_19,\n"
                + "P_20,\n"
                + "P_21,\n"
                + "P_22,\n"
                + "P_23,\n"
                + "P_24,\n"
                + "P_25,\n"
                + "P_26,\n"
                + "P_27,\n"
                + "P_28,\n"
                + "P_29,\n"
                + "P_30,\n"
                + "D_01,\n"
                + "D_10,\n"
                + "P_DI,\n"
                + "P_C1,\n"
                + "P_C2,\n"
                + "P_31,\n"
                + "P_32,\n"
                + "P_33,\n"
                + "D_CB,\n"
                + "D_02,\n"
                + "D_03,\n"
                + "D_04,\n"
                + "D_05,\n"
                + "D_06,\n"
                + "D_07,\n"
                + "D_08,\n"
                + "D_09,\n"
                + "D_11,\n"
                + "D_12,\n"
                + "D_13,\n"
                + "D_14,\n"
                + "D_15,\n"
                + "D_16,\n"
                + "D_17,\n"
                + "D_18,\n"
                + "D_19,\n"
                + "D_20,\n"
                + "D_21,\n"
                + "D_22,\n"
                + "D_23,\n"
                + "D_24,\n"
                + "D_25,\n"
                + "D_26,\n"
                + "D_27,\n"
                + "D_28,\n"
                + "P_MC,\n"
                + "P_PG,\n"
                + "P_PE,\n"
                + "P_36,\n"
                + "D_29,\n"
                + "P_37,\n"
                + "D_30,\n"
                + "D_31,\n"
                + "P_35,\n"
                + "P_38,\n"
                + "P_39,\n"
                + "D_32,\n"
                + "D_33,\n"
                + "D_34,\n"
                + "D_35,\n"
                + "P_40,\n"
                + "D_36,\n"
                + "D_37,\n"
                + "P_01A,\n"
                + "P_01B,\n"
                + "P_01C,\n"
                + "P_01D,\n"
                + "P_01E,\n"
                + "P_34,\n"
                + "D_38,\n"
                + "P_41,\n"
                + "P_42,\n"
                + "P_43,\n"
                + "P_44,\n"
                + "P_45,\n"
                + "P_46,\n"
                + "P_47,\n"
                + "D_39,\n"
                + "D_40,\n"
                + "D_41,\n"
                + "D_42,\n"
                + "D_01A,\n"
                + "D_43,\n"
                + "D_44,\n"
                + "D_45,\n"
                + "D_46,\n"
                + "D_47,\n"
                + "D_48,\n"
                + "D_49,\n"
                + "D_50,\n"
                + "TIPO,\n"
                + "QUINCENA,\n"
                + "LAPSO,\n"
                + "PLANTEL,\n"
                + "NOPUESTO,\n"
                + "LEIDO,\n"
                + "AP_PAT,\n"
                + "AP_MAT,\n"
                + "NOMBRE,\n"
                + "CATEGORIA,\n"
                + "HORAS,\n"
                + "NIVEL,\n"
                + "FECHAOLD,\n"
                + "TIPOPER,\n"
                + "ZONA,\n"
                + "SEMESTRE,\n"
                + "TPLANTEL,\n"
                + "TIPOSIN,\n"
                + "MUTUAL,\n"
                + "FACORE,\n"
                + "CEDULAIMSS,\n"
                + "REGIMENPENSIONARIO,\n"
                + "numeroquincena)VALUES ("
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,"
                + "	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	"
                + "?,	?,	?,	?,	?,	?,	?,	?,?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement psinsertar = null;
        ResultSet rsinsertar = null;
        int numFilaDatosCuotasYAportaciones = 10;
        int yearanterior;
        int periodointermedio;
        
        
        try {
            ps = connota.prepareStatement(sqlprueba);
            ps.setString(1, reporte.getPeriodo());
            rs = ps.executeQuery();
            int numCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {

                try {
                    /////////////////////////////////////////////////////
                    psinsertar = connotaremoto.prepareStatement(sqlinsertarnuevanominaremoto);
                    psinsertar.setString(1, rs.getString(1));
                    psinsertar.setString(2, rs.getString(2));
                    psinsertar.setString(3, rs.getString(3));
                    psinsertar.setString(4, rs.getString(4));
                    psinsertar.setDouble(5, rs.getDouble(5));
                    psinsertar.setDouble(6, rs.getDouble(6));
                    psinsertar.setDouble(7, rs.getDouble(7));
                    psinsertar.setDouble(8, rs.getDouble(8));
                    psinsertar.setDouble(9, rs.getDouble(9));
                    psinsertar.setDouble(10, rs.getDouble(10));
                    psinsertar.setDouble(11, rs.getDouble(11));
                    psinsertar.setDouble(12, rs.getDouble(12));
                    psinsertar.setDouble(13, rs.getDouble(13));
                    psinsertar.setDouble(14, rs.getDouble(14));
                    psinsertar.setDouble(15, rs.getDouble(15));
                    psinsertar.setDouble(16, rs.getDouble(16));
                    psinsertar.setDouble(17, rs.getDouble(17));
                    psinsertar.setDouble(18, rs.getDouble(18));
                    psinsertar.setDouble(19, rs.getDouble(19));
                    psinsertar.setDouble(20, rs.getDouble(20));
                    psinsertar.setDouble(21, rs.getDouble(21));
                    psinsertar.setDouble(22, rs.getDouble(22));
                    psinsertar.setDouble(23, rs.getDouble(23));
                    psinsertar.setDouble(24, rs.getDouble(24));
                    psinsertar.setDouble(25, rs.getDouble(25));
                    psinsertar.setDouble(26, rs.getDouble(26));
                    psinsertar.setDouble(27, rs.getDouble(27));
                    psinsertar.setDouble(28, rs.getDouble(28));
                    psinsertar.setDouble(29, rs.getDouble(29));
                    psinsertar.setDouble(30, rs.getDouble(30));
                    psinsertar.setDouble(31, rs.getDouble(31));
                    psinsertar.setDouble(32, rs.getDouble(32));
                    psinsertar.setDouble(33, rs.getDouble(33));
                    psinsertar.setDouble(34, rs.getDouble(34));
                    psinsertar.setDouble(35, rs.getDouble(35));
                    psinsertar.setDouble(36, rs.getDouble(36));
                    psinsertar.setDouble(37, rs.getDouble(37));
                    psinsertar.setDouble(38, rs.getDouble(38));
                    psinsertar.setDouble(39, rs.getDouble(39));
                    psinsertar.setDouble(40, rs.getDouble(40));
                    psinsertar.setDouble(41, rs.getDouble(41));
                    psinsertar.setDouble(42, rs.getDouble(42));
                    psinsertar.setDouble(43, rs.getDouble(43));
                    psinsertar.setDouble(44, rs.getDouble(44));
                    psinsertar.setDouble(45, rs.getDouble(45));
                    psinsertar.setDouble(46, rs.getDouble(46));
                    psinsertar.setDouble(47, rs.getDouble(47));
                    psinsertar.setDouble(48, rs.getDouble(48));
                    psinsertar.setDouble(49, rs.getDouble(49));
                    psinsertar.setDouble(50, rs.getDouble(50));
                    psinsertar.setDouble(51, rs.getDouble(51));
                    psinsertar.setDouble(52, rs.getDouble(52));
                    psinsertar.setDouble(53, rs.getDouble(53));
                    psinsertar.setDouble(54, rs.getDouble(54));
                    psinsertar.setDouble(55, rs.getDouble(55));
                    psinsertar.setDouble(56, rs.getDouble(56));
                    psinsertar.setDouble(57, rs.getDouble(57));
                    psinsertar.setDouble(58, rs.getDouble(58));
                    psinsertar.setDouble(59, rs.getDouble(59));
                    psinsertar.setDouble(60, rs.getDouble(60));
                    psinsertar.setDouble(61, rs.getDouble(61));
                    psinsertar.setDouble(62, rs.getDouble(62));
                    psinsertar.setDouble(63, rs.getDouble(63));
                    psinsertar.setDouble(64, rs.getDouble(64));
                    psinsertar.setDouble(65, rs.getDouble(65));
                    psinsertar.setDouble(66, rs.getDouble(66));
                    psinsertar.setDouble(67, rs.getDouble(67));
                    psinsertar.setDouble(68, rs.getDouble(68));
                    psinsertar.setDouble(69, rs.getDouble(69));
                    psinsertar.setDouble(70, rs.getDouble(70));
                    psinsertar.setDouble(71, rs.getDouble(71));
                    psinsertar.setDouble(72, rs.getDouble(72));
                    psinsertar.setDouble(73, rs.getDouble(73));
                    psinsertar.setDouble(74, rs.getDouble(74));
                    psinsertar.setDouble(75, rs.getDouble(75));
                    psinsertar.setDouble(76, rs.getDouble(76));
                    psinsertar.setDouble(77, rs.getDouble(77));
                    psinsertar.setDouble(78, rs.getDouble(78));
                    psinsertar.setDouble(79, rs.getDouble(79));
                    psinsertar.setDouble(80, rs.getDouble(80));
                    psinsertar.setDouble(81, rs.getDouble(81));
                    psinsertar.setDouble(82, rs.getDouble(82));
                    psinsertar.setDouble(83, rs.getDouble(83));
                    psinsertar.setDouble(84, rs.getDouble(84));
                    psinsertar.setDouble(85, rs.getDouble(85));
                    psinsertar.setDouble(86, rs.getDouble(86));
                    psinsertar.setDouble(87, rs.getDouble(87));
                    psinsertar.setDouble(88, rs.getDouble(88));
                    psinsertar.setDouble(89, rs.getDouble(89));
                    psinsertar.setDouble(90, rs.getDouble(90));
                    psinsertar.setDouble(91, rs.getDouble(91));
                    psinsertar.setDouble(92, rs.getDouble(92));
                    psinsertar.setDouble(93, rs.getDouble(93));
                    psinsertar.setDouble(94, rs.getDouble(94));
                    psinsertar.setDouble(95, rs.getDouble(95));
                    psinsertar.setDouble(96, rs.getDouble(96));
                    psinsertar.setDouble(97, rs.getDouble(97));
                    psinsertar.setDouble(98, rs.getDouble(98));
                    psinsertar.setDouble(99, rs.getDouble(99));
                    psinsertar.setDouble(100, rs.getDouble(100));
                    psinsertar.setDouble(101, rs.getDouble(101));
                    psinsertar.setDouble(102, rs.getDouble(102));
                    psinsertar.setDouble(103, rs.getDouble(103));
                    psinsertar.setDouble(104, rs.getDouble(104));
                    psinsertar.setDouble(105, rs.getDouble(105));
                    psinsertar.setDouble(106, rs.getDouble(106));
                    psinsertar.setDouble(107, rs.getDouble(107));
                    psinsertar.setDouble(108, rs.getDouble(108));
                    psinsertar.setDouble(109, rs.getDouble(109));
                    psinsertar.setDouble(110, rs.getDouble(110));
                    psinsertar.setDouble(111, rs.getDouble(111));
                    psinsertar.setDouble(112, rs.getDouble(112));
                    psinsertar.setDouble(113, rs.getDouble(113));
                    psinsertar.setDouble(114, rs.getDouble(114));
                    psinsertar.setString(115, rs.getString(115));
                    psinsertar.setString(116, rs.getString(116));
                    psinsertar.setString(117, rs.getString(117));
                    psinsertar.setString(118, rs.getString(118));
                    psinsertar.setString(119, rs.getString(119));
                    psinsertar.setString(120, rs.getString(120));
                    psinsertar.setString(121, rs.getString(121));
                    psinsertar.setString(122, rs.getString(122));
                    psinsertar.setString(123, rs.getString(123));
                    psinsertar.setString(124, rs.getString(124));
                    psinsertar.setString(125, rs.getString(125));
                    psinsertar.setString(126, rs.getString(126));
                    psinsertar.setString(127, rs.getString(127));
                    psinsertar.setString(128, rs.getString(128));
                    psinsertar.setString(129, rs.getString(129));
                    psinsertar.setString(130, rs.getString(130));
                    psinsertar.setString(131, rs.getString(131));
                    psinsertar.setString(132, rs.getString(132));
                    psinsertar.setString(133, rs.getString(133));
                    psinsertar.setString(134, rs.getString(134));
                    psinsertar.setString(135, rs.getString(135));
                    psinsertar.setString(136, rs.getString(136));
                    psinsertar.setString(137, reporte.getPeriodo());
                    ///////////////////////////////////////////////////
                    psinsertar.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                }
                numFilaDatosCuotasYAportaciones++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public void procesarQnaFonsar(Reporte reporte) {
        Connection con = getConexion();
        Connection connota = getConexionnota();
        Connection connotafonsar = getConexionfonsar();
         Connection connotafonsarremoto = getConexionnotaRemotoFonsar();
        
        String sqlprueba = "select rfc,concat(AP_PAT,' ',AP_MAT,' ',NOMBRE)as nombre,plantel,facore,round(((P_01+P_01A+P_01B+P_01C+P_01D+P_01E)*1.5)/100,2) as importe,'CIP' as literal,empleado from hojaicp where \n"
                + "	numeroquincena = ? and D_25 > 0";
        
         String sqlinsertarfonsar = "INSERT INTO qnaahorrorecuperada(rfc,nombre,plantel,facore,importe,numeroquincena,"
                        + "literal,numeroempleado)"
                        + "Values(?,?,?,?,"
                        + "?,?,?,?)";
        
            
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement psinsertar = null;
        PreparedStatement psinsertarremoto = null;
        ResultSet rsinsertar = null;
        int numFilaDatosCuotasYAportaciones = 10;
        int yearanterior;
        int periodointermedio;
        
        
        try {
            ps = connota.prepareStatement(sqlprueba);
            ps.setString(1, reporte.getPeriodo());
            rs = ps.executeQuery();
            int numCol = rs.getMetaData().getColumnCount();
            while (rs.next()) {

                try {
                    /////////////////////////////////////////////////////
                    psinsertar = connotafonsar.prepareStatement(sqlinsertarfonsar);
                    psinsertar.setString(1, rs.getString(1));
                    psinsertar.setString(2, rs.getString(2));
                    psinsertar.setString(3, rs.getString(3));
                    psinsertar.setString(4, rs.getString(4));
                    psinsertar.setDouble(5, rs.getDouble(5));
                    psinsertar.setString(6, reporte.getPeriodo());
                    psinsertar.setString(7, rs.getString(6));
                    psinsertar.setInt(8, rs.getInt(7));
                    psinsertar.execute();
                    
                    psinsertarremoto = connotafonsarremoto.prepareStatement(sqlinsertarfonsar);
                    psinsertarremoto.setString(1, rs.getString(1));
                    psinsertarremoto.setString(2, rs.getString(2));
                    psinsertarremoto.setString(3, rs.getString(3));
                    psinsertarremoto.setString(4, rs.getString(4));
                    psinsertarremoto.setDouble(5, rs.getDouble(5));
                    psinsertarremoto.setString(6, reporte.getPeriodo());
                    psinsertarremoto.setString(7, rs.getString(6));
                    psinsertarremoto.setInt(8, rs.getInt(7));
                    psinsertarremoto.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                }
                numFilaDatosCuotasYAportaciones++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void procesarayudamutua(Reporte reporte) {
        Connection con = getConexion();
        String sqlactualizaaceros = "UPDATE hojaisp set D_15 = 0 , D_18 = 0, D_36 = 0, D_38 = 0  WHERE numeroquincena = ? ";
        String sqldatosayudamutua = "select rfc,concat(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,empleado,round((P_01+P_01A+P_01B+P_01C+P_01D+P_01E),2) as sueldobase,\n"
                + "(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20\n"
                + "+ P_21 + P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 \n"
                + "+ P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + P_45 + P_46 + P_47)as percepciones,\n"
                + "(D_01 + D_10 + D_CB + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 + D_09 + D_11 +D_12 + D_13 + D_14 + D_16 + D_17  + D_19 + \n"
                + "D_20 + D_21 + D_22 + D_23  + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 + D_30 + D_31 + D_32 + D_33 + D_34 + D_35  + D_37  + D_39 + \n"
                + "D_40 + D_41 + D_42 + D_01A + D_43 + D_44 + D_45 + D_46+D_47+D_48+D_49+D_50)as deducciones,round((P_01+P_01A+P_01B+P_01C+P_01D+P_01E),2) as sueldobase,round((P_01+P_01A+P_01B+P_01C+P_01D+P_01E)*1/100,2) as monto,\n"
                + "(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20\n"
                + "+ P_21 + P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 \n"
                + "+ P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + P_45 + P_46 + P_47)-(D_01 + D_10 + D_CB + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 + D_09 + D_11 +D_12 + D_13 + D_14 + D_16 + D_17  + D_19 + \n"
                + "D_20 + D_21 + D_22 + D_23  + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 + D_30 + D_31 + D_32 + D_33 + D_34 + D_35  + D_37  + D_39 + \n"
                + "D_40 + D_41 + D_42 + D_01A + D_43 + D_44 + D_45 + D_46+D_48+D_49+D_50) as neto,cveayudamutua,plantel from hojaisp where numeroquincena = ? and cveayudamutua in('D_15','D_18','D_36','D_38')  order by empleado";
        //String sqlactualizadatosayuda = "UPDATE hojaisp SET  ? = ? where numeroquincena = ? and empleado = ? ";

        PreparedStatement psactualizaaceros = null;
        ResultSet rsactualizaaceros = null;
        PreparedStatement psdatosayuda = null;
        ResultSet rsdatosayuda = null;
        PreparedStatement psactualizadatos = null;
        ResultSet rsactualizadatos = null;
        Double monto = 0.00;
        Double neto = 0.00;
        String nombreClave;
        int numFilaDatosCuotasYAportaciones = 10;

        try {
            psactualizaaceros = con.prepareStatement(sqlactualizaaceros);
            psactualizaaceros.setString(1, reporte.getPeriodo());
            psactualizaaceros.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            psdatosayuda = con.prepareStatement(sqldatosayudamutua);
            psdatosayuda.setString(1, reporte.getPeriodo());
            rsdatosayuda = psdatosayuda.executeQuery();
            while (rsdatosayuda.next()) {

                monto = rsdatosayuda.getDouble(8);
                neto = rsdatosayuda.getDouble(9);
                nombreClave = rsdatosayuda.getString(10);

                if (neto > monto) {

                    neto = neto - monto;
                } else {

                    monto = neto;
                    neto = neto - monto;
                }

                if (monto > 0) {
                    try {

                        String sqlactualizadatosayuda = "UPDATE hojaisp SET " + nombreClave + " = ? where numeroquincena = ? and empleado = ? ";

                        psactualizadatos = con.prepareStatement(sqlactualizadatosayuda);
                        psactualizadatos.setDouble(1, monto);
                        psactualizadatos.setString(2, reporte.getPeriodo());
                        psactualizadatos.setString(3, rsdatosayuda.getString(3));
                        psactualizadatos.execute();

                    } catch (SQLException ex) {
                        Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void procesarcuotasindical(Reporte reporte) {
        Connection con = getConexion();
        String sqlactualizaaceros = "UPDATE hojaisp set D_05 = 0 , D_11 = 0, D_19 = 0, D_29 = 0  WHERE numeroquincena = ? ";
        String sqldatosayudamutua = "select rfc,concat(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,empleado,round((P_01+P_01A+P_01B+P_01C+P_01D+P_01E),2) as sueldobase,\n"
                + "(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20\n"
                + "+ P_21 + P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 \n"
                + "+ P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + P_45 + P_46 + P_47)as percepciones,\n"
                + "(D_01 + D_10 + D_CB + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 + D_09 + D_11 +D_12 + D_13 + D_14 + D_16 + D_17  + D_19 + \n"
                + "D_20 + D_21 + D_22 + D_23  + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 + D_30 + D_31 + D_32 + D_33 + D_34 + D_35  + D_37  + D_39 + \n"
                + "D_40 + D_41 + D_42 + D_01A + D_43 + D_44 + D_45 + D_46+D_47+D_48+D_49+D_50)as deducciones,round((P_01+P_01A+P_01B+P_01C+P_01D+P_01E),2) as sueldobase,round((P_01+P_01A+P_01B+P_01C+P_01D+P_01E)*1/100,2) as monto,\n"
                + "(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20\n"
                + "+ P_21 + P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 \n"
                + "+ P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + P_45 + P_46 + P_47)-(D_01 + D_10 + D_CB + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 + D_09 + D_11 +D_12 + D_13 + D_14 + D_16 + D_17  + D_19 + \n"
                + "D_20 + D_21 + D_22 + D_23  + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 + D_30 + D_31 + D_32 + D_33 + D_34 + D_35  + D_37  + D_39 + \n"
                + "D_40 + D_41 + D_42 + D_01A + D_43 + D_44 + D_45 + D_46+D_48+D_49+D_50) as neto,cvecuotasindical,plantel from hojaisp where numeroquincena = ? and cvecuotasindical in('D_05','D_11','D_19','D_29')  order by empleado";
        //String sqlactualizadatosayuda = "UPDATE hojaisp SET  ? = ? where numeroquincena = ? and empleado = ? ";

        PreparedStatement psactualizaaceros = null;
        ResultSet rsactualizaaceros = null;
        PreparedStatement psdatosayuda = null;
        ResultSet rsdatosayuda = null;
        PreparedStatement psactualizadatos = null;
        ResultSet rsactualizadatos = null;
        Double monto = 0.00;
        Double neto = 0.00;
        String nombreClave;
        int numFilaDatosCuotasYAportaciones = 10;

        try {
            psactualizaaceros = con.prepareStatement(sqlactualizaaceros);
            psactualizaaceros.setString(1, reporte.getPeriodo());
            psactualizaaceros.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            psdatosayuda = con.prepareStatement(sqldatosayudamutua);
            psdatosayuda.setString(1, reporte.getPeriodo());
            rsdatosayuda = psdatosayuda.executeQuery();
            while (rsdatosayuda.next()) {

                monto = rsdatosayuda.getDouble(8);
                neto = rsdatosayuda.getDouble(9);
                nombreClave = rsdatosayuda.getString(10);

                if (neto > monto) {

                    neto = neto - monto;
                } else {

                    monto = neto;
                    neto = neto - monto;
                }

                if (monto > 0) {
                    try {

                        String sqlactualizadatosayuda = "UPDATE hojaisp SET " + nombreClave + " = ? where numeroquincena = ? and empleado = ? ";

                        psactualizadatos = con.prepareStatement(sqlactualizadatosayuda);
                        psactualizadatos.setDouble(1, monto);
                        psactualizadatos.setString(2, reporte.getPeriodo());
                        psactualizadatos.setString(3, rsdatosayuda.getString(3));
                        psactualizadatos.execute();

                    } catch (SQLException ex) {
                        Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void procesarantiguedad(Reporte reporte) {
        Connection con = getConexion();
        String sqlactualizaaceros = "UPDATE hojaisp set P_03 = 0  WHERE numeroquincena = ? ";
        String sqldatosayudamutua = "select P_03,round(((P_01+P_01A+P_01B+P_01C+P_01D+P_01E) * (select porcentaje from antiguedad where antiguedad = TIMESTAMPDIFF(YEAR,STR_TO_DATE(fechaold,'%d/%m/%Y'), CURDATE()))/100),2) as antiguedadmonto,\n"
                + "                (select porcentaje from antiguedad where antiguedad = TIMESTAMPDIFF(YEAR,STR_TO_DATE(fechaold,'%d/%m/%Y'), CURDATE()))as porcentaje,\n"
                + "                rfc,concat(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,(P_01+P_01A+P_01B+P_01C+P_01D+P_01E)AS sueldo,empleado,\n"
                + "                	TIMESTAMPDIFF(YEAR,STR_TO_DATE(fechaold,'%d/%m/%Y'), CURDATE()) as antiguedad from hojaisp where numeroquincena = ?    order by rfc\n"
                + "                ";

        PreparedStatement psactualizaaceros = null;
        ResultSet rsactualizaaceros = null;
        PreparedStatement psdatosayuda = null;
        ResultSet rsdatosayuda = null;
        PreparedStatement psactualizadatos = null;
        ResultSet rsactualizadatos = null;
        Double monto = 0.00;
        Double neto = 0.00;
        String nombreClave;
        Double montoantiguedadempleado = 0.00;
        int numFilaDatosCuotasYAportaciones = 10;

        try {
            psactualizaaceros = con.prepareStatement(sqlactualizaaceros);
            psactualizaaceros.setString(1, reporte.getPeriodo());
            psactualizaaceros.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            psdatosayuda = con.prepareStatement(sqldatosayudamutua);
            psdatosayuda.setString(1, reporte.getPeriodo());
            rsdatosayuda = psdatosayuda.executeQuery();
            while (rsdatosayuda.next()) {
                montoantiguedadempleado = rsdatosayuda.getDouble(2);
                try {

                    String sqlactualizadatosayuda = "UPDATE hojaisp SET P_03 = ? WHERE empleado = ? and "
                            + "numeroquincena = ? ";

                    psactualizadatos = con.prepareStatement(sqlactualizadatosayuda);
                    psactualizadatos.setDouble(1, montoantiguedadempleado);
                    psactualizadatos.setString(2, rsdatosayuda.getString(7));
                    psactualizadatos.setString(3, reporte.getPeriodo());
                    psactualizadatos.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void procesarpensiones(Reporte reporte) {
        Connection con = getConexion();
        String sqlactualizaaceros = "UPDATE hojaisp set D_10 = 0 WHERE numeroquincena = ?";
        String sqldatosayudamutua = "select rfc,concat(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,empleado,round((P_01+P_01A+P_01B+P_01C+P_01D+P_01E),2) as sueldobase,\n"
                + "	(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20\n"
                + "	+ P_21 + P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 \n"
                + "	+ P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + P_45 + P_46 + P_47)as percepciones,\n"
                + "	(D_01  + D_02 + D_03 + D_04 + D_05 + D_06 +D_07 + D_08 + D_09 + D_11 + D_12 + D_13 + D_14 + D_15+ D_16 + D_17 + D_18 + D_19 + \n"
                + "	D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26  + D_27 + D_28 + D_29 + D_30 + D_31 + D_32 + D_33 + D_34  + D_35 + D_36 + D_37 + D_38 + D_39 + \n"
                + "    D_41 + D_42 + D_01A + D_43 + D_44 + D_45 + D_46+D_47+D_48+D_49+D_50)as deducciones,plantel from hojaisp where numeroquincena = ? and empleado = 917  order by empleado";

        PreparedStatement psactualizaaceros = null;
        ResultSet rsactualizaaceros = null;
        PreparedStatement psdatosayuda = null;
        ResultSet rsdatosayuda = null;
        PreparedStatement psactualizadatos = null;
        ResultSet rsactualizadatos = null;
        Double monto = 0.00;
        Double montopension = 0.00;
        Double percepciones = 0.00;
        Double deducciones = 0.00;
        Double neto = 0.00;
        String nombreClave;
        Double montoantiguedadempleado = 0.00;
        int numFilaDatosCuotasYAportaciones = 10;

        try {
            psactualizaaceros = con.prepareStatement(sqlactualizaaceros);
            psactualizaaceros.setString(1, reporte.getPeriodo());
            psactualizaaceros.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            psdatosayuda = con.prepareStatement(sqldatosayudamutua);
            psdatosayuda.setString(1, reporte.getPeriodo());
            rsdatosayuda = psdatosayuda.executeQuery();
            while (rsdatosayuda.next()) {
                percepciones = rsdatosayuda.getDouble(5);
                deducciones = rsdatosayuda.getDouble(6);
                neto = percepciones - deducciones;
                try {

                    String sqlactualizadatosayuda = "select * from pension where status in ('A','a') and "
                            + "empleado = ? order by secuencia asc ";

                    psactualizadatos = con.prepareStatement(sqlactualizadatosayuda);
                    psactualizadatos.setInt(1, rsdatosayuda.getInt(3));
                    rsactualizadatos = psactualizadatos.executeQuery();
                    while (rsactualizadatos.next()) {
                        montopension = rsactualizadatos.getDouble(9);

                        if (neto > montopension) {
                            neto = neto - montopension;
                        } else {
                            montopension = neto;
                            neto = neto - montopension;
                        }

                        if (montopension > 0) {
                            try {
                                PreparedStatement psinsertarpension = null;
                                ResultSet rsinsertarpension = null;
                                String sqlinsertarpension = "INSERT INTO pensionessaplicadas(oficio,empleado,"
                                        + "descuentoaplicado,plantel,clave,numeroquincena,netorestante,secuencia,rfc,"
                                        + "beneficiario)\n"
                                        + "		                     VALUES (?,?,?,?,?,"
                                        + "?,?,?,?,?)";
                                psinsertarpension = con.prepareStatement(sqlinsertarpension);
                                psinsertarpension.setString(1, rsactualizadatos.getString(8));
                                psinsertarpension.setInt(2, rsactualizadatos.getInt(4));
                                psinsertarpension.setDouble(3, montopension);
                                psinsertarpension.setInt(4, rsactualizadatos.getInt(7));
                                psinsertarpension.setString(5, rsactualizadatos.getString(11));
                                psinsertarpension.setString(6, reporte.getPeriodo());
                                psinsertarpension.setDouble(7, neto);
                                psinsertarpension.setInt(8, rsactualizadatos.getInt(2));
                                psinsertarpension.setString(9, rsactualizadatos.getString(12));
                                psinsertarpension.setString(10, rsactualizadatos.getString(6));
                                psinsertarpension.execute();

                            } catch (SQLException ex) {
                                Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                }

                //////////////
                PreparedStatement pspensionglobal = null;
                ResultSet rspensionglobal = null;
                String sqlpensionglobal = "select sum(descuentoaplicado)as totaldescuento,clave from "
                        + "pensionessaplicadas where numeroquincena = ? and empleado = "
                        + "? group by clave";
                pspensionglobal = con.prepareStatement(sqlpensionglobal);
                pspensionglobal.setString(1, reporte.getPeriodo());
                pspensionglobal.setInt(2, rsdatosayuda.getInt(3));
                rspensionglobal = pspensionglobal.executeQuery();
                while (rspensionglobal.next()) {
                    PreparedStatement pspensionglobalupdate = null;
                    ResultSet rspensionglobalupdate = null;
                    String sqlpensionglobalupdate = "UPDATE hojaisp set D_10 = ? where numeroquincena = ? and empleado = ?";
                    pspensionglobalupdate = con.prepareStatement(sqlpensionglobalupdate);
                    pspensionglobalupdate.setDouble(1, rspensionglobal.getDouble(1));
                    pspensionglobalupdate.setString(2, reporte.getPeriodo());
                    pspensionglobalupdate.setInt(3, rsdatosayuda.getInt(3));
                    pspensionglobalupdate.execute();

                }

                ////////////
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void procesarahorros(Reporte reporte) {
        Connection con = getConexion();
        String sqlactualizaaceros = "UPDATE hojaisp set D_12 = 0,D_24 = 0,D_25 = 0,D_27 = 0,D_30 = 0, "
                + "D_32 = 0,D_34 = 0, WHERE numeroquincena = ?";
        String sqldatosayudamutua = "select rfc,concat(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,empleado,round((P_01+P_01A+P_01B+P_01C+P_01D+P_01E),2) as sueldobase,\n"
                + "    (P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20\n"
                + "    + P_21 + P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 \n"
                + "    + P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + P_45 + P_46 + P_47)as percepciones,\n"
                + "    (D_01 + D_10 + D_CB + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 + D_09 + D_11 +D_12 + D_13 + D_14 + D_16 + D_17  + D_19 + \n"
                + "    D_20 + D_21 + D_22 + D_23  + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 + D_30 + D_31 + D_32 + D_33 + D_34 + D_35  + D_37  + D_39 + \n"
                + "    D_40 + D_41 + D_42 + D_01A + D_43 + D_44 + D_45 + D_46)as deducciones,round((P_01+P_01A+P_01B+P_01C+P_01D+P_01E)*1/100,2) as monto,\n"
                + "    (P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20\n"
                + "    + P_21 + P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 \n"
                + "    + P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + P_45 + P_46 + P_47)-(D_01 + D_10 + D_CB + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 + D_09 + D_11 +D_12 + D_13 + D_14 + D_16 + D_17  + D_19 + \n"
                + "    D_20 + D_21 + D_22 + D_23  + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 + D_30 + D_31 + D_32 + D_33 + D_34 + D_35  + D_37  + D_39 + \n"
                + "    D_40 + D_41 + D_42 + D_01A + D_43 + D_44 + D_45 + D_46) as neto,caja_sutcobach,porcentajeahorroissste,facore,caja_seccion,cajaseccion63,porcentajeseccion,caja_sitcobach,plantel from hojaisp where numeroquincena = ?   order by empleado ";

        PreparedStatement psactualizaaceros = null;
        ResultSet rsactualizaaceros = null;
        PreparedStatement psdatosayuda = null;
        ResultSet rsdatosayuda = null;
        PreparedStatement psactualizadatos = null;
        ResultSet rsactualizadatos = null;
        Double monto = 0.00;
        Double montopension = 0.00;
        Double percepciones = 0.00;
        Double deducciones = 0.00;
        Double neto = 0.00;
        String nombreClave;
        Double montoantiguedadempleado = 0.00;
        int numFilaDatosCuotasYAportaciones = 10;

        try {
            psactualizaaceros = con.prepareStatement(sqlactualizaaceros);
            psactualizaaceros.setString(1, reporte.getPeriodo());
            psactualizaaceros.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            psdatosayuda = con.prepareStatement(sqldatosayudamutua);
            psdatosayuda.setString(1, reporte.getPeriodo());
            rsdatosayuda = psdatosayuda.executeQuery();
            while (rsdatosayuda.next()) {
                percepciones = rsdatosayuda.getDouble(5);
                deducciones = rsdatosayuda.getDouble(6);
                neto = percepciones - deducciones;
                try {

                    String sqlactualizadatosayuda = "select * from pension where status in ('A','a') and "
                            + "empleado = ? order by secuencia asc ";

                    psactualizadatos = con.prepareStatement(sqlactualizadatosayuda);
                    psactualizadatos.setInt(1, rsdatosayuda.getInt(3));
                    rsactualizadatos = psactualizadatos.executeQuery();
                    while (rsactualizadatos.next()) {
                        montopension = rsactualizadatos.getDouble(9);

                        if (neto > montopension) {
                            neto = neto - montopension;
                        } else {
                            montopension = neto;
                            neto = neto - montopension;
                        }

                        if (montopension > 0) {
                            try {
                                PreparedStatement psinsertarpension = null;
                                ResultSet rsinsertarpension = null;
                                String sqlinsertarpension = "INSERT INTO pensionessaplicadas(oficio,empleado,"
                                        + "descuentoaplicado,plantel,clave,numeroquincena,netorestante,secuencia,rfc,"
                                        + "beneficiario)\n"
                                        + "		                     VALUES (?,?,?,?,?,"
                                        + "?,?,?,?,?)";
                                psinsertarpension = con.prepareStatement(sqlinsertarpension);
                                psinsertarpension.setString(1, rsactualizadatos.getString(8));
                                psinsertarpension.setInt(2, rsactualizadatos.getInt(4));
                                psinsertarpension.setDouble(3, montopension);
                                psinsertarpension.setInt(4, rsactualizadatos.getInt(7));
                                psinsertarpension.setString(5, rsactualizadatos.getString(11));
                                psinsertarpension.setString(6, reporte.getPeriodo());
                                psinsertarpension.setDouble(7, neto);
                                psinsertarpension.setInt(8, rsactualizadatos.getInt(2));
                                psinsertarpension.setString(9, rsactualizadatos.getString(12));
                                psinsertarpension.setString(10, rsactualizadatos.getString(6));
                                psinsertarpension.execute();

                            } catch (SQLException ex) {
                                Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                }

                //////////////
                PreparedStatement pspensionglobal = null;
                ResultSet rspensionglobal = null;
                String sqlpensionglobal = "select sum(descuentoaplicado)as totaldescuento,clave from "
                        + "pensionessaplicadas where numeroquincena = ? and empleado = "
                        + "? group by clave";
                pspensionglobal = con.prepareStatement(sqlpensionglobal);
                pspensionglobal.setString(1, reporte.getPeriodo());
                pspensionglobal.setInt(2, rsdatosayuda.getInt(3));
                rspensionglobal = pspensionglobal.executeQuery();
                while (rspensionglobal.next()) {
                    PreparedStatement pspensionglobalupdate = null;
                    ResultSet rspensionglobalupdate = null;
                    String sqlpensionglobalupdate = "UPDATE hojaisp set D_10 = ? where numeroquincena = ? and empleado = ?";
                    pspensionglobalupdate = con.prepareStatement(sqlpensionglobalupdate);
                    pspensionglobalupdate.setDouble(1, rspensionglobal.getDouble(1));
                    pspensionglobalupdate.setString(2, reporte.getPeriodo());
                    pspensionglobalupdate.setInt(3, rsdatosayuda.getInt(3));
                    pspensionglobalupdate.execute();

                }

                ////////////
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList procesarIsrGlobal(Reporte reporte, Empleado empleado) {

        Connection con = getConexion();
        ArrayList<Empleado> ListaRfcEmpleado = new ArrayList<Empleado>();

        String sqlactualizaaceros = "UPDATE hojaisp set P_14 = 0 ,D_01 = 0,D_03 = 0,D_04 = 0,D_21 = 0,D_22 = 0 ,D_23 = 0 "
                + "WHERE numeroquincena = ? ";

        String sqldatos = "SELECT rfc,COUNT(rfc)AS plazas, sum(P_01+P_01A+P_01B+P_01C+P_01D+P_01E) as salario,sum(P_03)AS antiguedad,sum(P_18) AS p18,sum(P_19)AS p19,empleado,\n"
                + "               sum(P_01+P_01A+P_01B+P_01C+P_01D+P_03) as baseissste from hojaisp\n"
                + "                where numeroquincena = ? group BY rfc ORDER BY rfc asc  ";

        PreparedStatement psactualizaaceros = null;
        ResultSet rsactualizaaceros = null;
        PreparedStatement psdatos = null;
        ResultSet rsdatos = null;

        try {
            psactualizaaceros = con.prepareStatement(sqlactualizaaceros);
            psactualizaaceros.setString(1, reporte.getPeriodo());
            psactualizaaceros.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            psdatos = con.prepareStatement(sqldatos);
            psdatos.setString(1, reporte.getPeriodo());
            rsdatos = psdatos.executeQuery();
            while (rsdatos.next()) {

                Empleado lista = new Empleado(rsdatos.getString("rfc"), rsdatos.getInt("empleado"),
                        rsdatos.getDouble("antiguedad"), rsdatos.getDouble("p18"), rsdatos.getDouble("p19"),
                        rsdatos.getDouble("salario"), rsdatos.getDouble("baseissste"), rsdatos.getInt("plazas"));

                ListaRfcEmpleado.add(lista);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ListaRfcEmpleado;
    }

    public ArrayList procesarDatosNotaTecnicaGlobales(Reporte reporte, Empleado empleado) {

        Connection con = getConexion();
        Connection connota = getConexionnota();
        int numeroquincenaaplicar;
        ArrayList<Empleado> ListaRfcEmpleado = new ArrayList<Empleado>();

        String sqlactualizaaceros = "UPDATE hojaicp set P_14 = 0 ,D_01 = 0,D_03 = 0,D_04 = 0,D_21 = 0,D_22 = 0 ,D_23 = 0,"
                + "P_PE = 0, P_PG = 0 WHERE numeroquincena = ? AND empleado NOT IN(select Numero_Empleado FROM jubilados)  ";

        String sqldatos = "SELECT * , (P_01 + P_01A + P_01B + P_01C + P_01D + P_01E) as salariobase, (P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 +\n"
                + "        P_11 + P_12 + P_13 + P_14 + P_15 + P_16 + P_17 + P_18 + P_19 + P_20 + P_21 +\n"
                + "        P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 + P_DI + P_C1 +\n"
                + "        P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 + P_35 + P_38 +\n"
                + "        P_39 + P_40 + P_01A + P_01B + P_01C + P_01D + P_01E + P_34 + P_41 + P_42 + P_43 +\n"
                + "        P_44 + P_45 + P_46 + P_47) as percepciones, (P_02 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 +\n"
                + "        P_11 + P_12 + P_13 + P_15 + P_16 + P_17 + P_20 + P_21 +\n"
                + "        P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 + P_DI + P_C1 +\n"
                + "        P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 + P_35 + P_38 +\n"
                + "        P_39 + P_40 + P_34 + P_41 + P_42 + P_43 +\n"
                + "        P_44 + P_45 + P_46 + P_47) as percepcionessincalculo, (D_01 + D_10 + D_CB + D_02 + D_03 + D_04 +\n"
                + "        D_05 + D_06 + D_07 + D_08 + D_09 + D_11 + D_12 + D_13 + D_14 + D_15 + D_16 + D_17 +\n"
                + "        D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 +\n"
                + "        D_30 + D_31 + D_32 + D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 +\n"
                + "        D_42 + D_01A + D_43 + D_44 + D_45 + D_46 + D_47 + D_48 + D_49 + D_50) as deducciones, (D_10 + D_CB + D_02 +\n"
                + "        +D_05 + D_06 + D_07 + D_08 + D_09 + D_11 + D_12 + D_13 + D_14 + D_15 + D_16 + D_17 +\n"
                + "        D_18 + D_19 + D_20 + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 +\n"
                + "        D_30 + D_31 + D_32 + D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 +\n"
                + "        D_42 + D_01A + D_43 + D_44 + D_45 + D_46 + D_47 + D_48 + D_49 + D_50) as deduccionessincalculo,\n"
                + "    (P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07 + P_08 + P_09 + P_10 +\n"
                + "        P_11 + P_12 + P_13 + P_14 + P_15 + P_16 + P_17 + P_18 + P_19 + P_20 + P_21 +\n"
                + "        P_22 + P_23 + P_24 + P_25 + P_26 + P_27 + P_28 + P_29 + P_30 + P_DI + P_C1 +\n"
                + "        P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + P_37 + P_35 + P_38 +\n"
                + "        P_39 + P_40 + P_01A + P_01B + P_01C + P_01D + P_01E + P_34 + P_41 + P_42 + P_43 +\n"
                + "        P_44 + P_45 + P_46 + P_47) - (D_01 + D_10 + D_CB + D_02 + D_03 + D_04 +\n"
                + "        D_05 + D_06 + D_07 + D_08 + D_09 + D_11 + D_12 + D_13 + D_14 + D_15 + D_16 + D_17 +\n"
                + "        D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + D_27 + D_28 + D_29 +\n"
                + "        D_30 + D_31 + D_32 + D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 +\n"
                + "        D_42 + D_01A + D_43 + D_44 + D_45 + D_46 + D_47 + D_48 + D_49 + D_50) as neto, (P_01 + P_01A + P_01B + P_01C + P_01D + P_01E + P_18 + P_19 +\n"
                + "        P_14) - (D_01 + D_03 + D_04 + D_21 + D_22 + D_23) as neto2 from hojai where numeroquincena = ? AND empleado NOT IN(select Numero_Empleado FROM jubilados) order by rfc ";

        PreparedStatement psactualizaaceros = null;
        ResultSet rsactualizaaceros = null;
        PreparedStatement psdatos = null;
        ResultSet rsdatos = null;

        try {
            psactualizaaceros = connota.prepareStatement(sqlactualizaaceros);
            psactualizaaceros.setString(1, reporte.getPeriodo());
            psactualizaaceros.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            psdatos = connota.prepareStatement(sqldatos);
            psdatos.setString(1, reporte.getPeriodo());
            rsdatos = psdatos.executeQuery();
            while (rsdatos.next()) {

                Empleado lista = new Empleado(rsdatos.getString("rfc"), rsdatos.getInt("empleado"),
                        rsdatos.getFloat("P_01"), rsdatos.getFloat("P_01A"), rsdatos.getFloat("P_01B"), rsdatos.getFloat("P_01C"), rsdatos.getFloat("P_01D"),
                        rsdatos.getFloat("P_01E"), rsdatos.getFloat("P_03"), rsdatos.getFloat("P_18"), rsdatos.getFloat("P_19"),
                        rsdatos.getDouble("salariobase"), rsdatos.getDouble("percepciones"), rsdatos.getDouble("deducciones"), rsdatos.getDouble("neto"),
                         rsdatos.getDouble("percepcionessincalculo"), rsdatos.getDouble("deduccionessincalculo"), rsdatos.getDouble("P_14"), rsdatos.getDouble("D_01"));

                ListaRfcEmpleado.add(lista);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ListaRfcEmpleado;
    }

    public ArrayList procesarDatosNotaTecnica(Reporte reporte, Empleado empleado) {

        Connection con = getConexion();
        Connection connota = getConexionnota();
        int numeroquincenaaplicar;
        ArrayList<Empleado> ListaRfcEmpleado = new ArrayList<Empleado>();

        /*String sqlactualizaaceros = "UPDATE hojaicp set P_14 = 0 ,D_01 = 0,D_03 = 0,D_04 = 0,D_21 = 0,D_22 = 0 ,D_23 = 0,"
                + "P_PE = 0, P_PG = 0 WHERE numeroquincena = ? ";*/

        String sqldatos = "SELECT rfc,COUNT(rfc)AS plazas, sum(P_01+P_01A+P_01B+P_01C+P_01D+P_01E) as salario,sum(P_03)AS antiguedad,sum(P_18) AS p18,sum(P_19)AS p19,empleado,\n"
                + "                             sum(P_01+P_01A+P_01B+P_01C+P_01D+P_03) as baseissste,SUM(P_01)AS P1,SUM(P_01A)AS P1A,SUM(P_01B)AS P1B,SUM(P_01C)AS P1C,SUM(P_01D) AS P1D,SUM(P_01E)AS P1E from hojai\n"
                + "                              where numeroquincena = ? AND RFC = ? ORDER BY empleado asc";

        PreparedStatement psactualizaaceros = null;
        ResultSet rsactualizaaceros = null;
        PreparedStatement psdatos = null;
        ResultSet rsdatos = null;

        /*try {
            psactualizaaceros = connota.prepareStatement(sqlactualizaaceros);
            psactualizaaceros.setString(1, reporte.getPeriodo());
            psactualizaaceros.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        try {

            psdatos = connota.prepareStatement(sqldatos);
            psdatos.setString(1, reporte.getPeriodo());
            psdatos.setString(2, empleado.getRfc());
            rsdatos = psdatos.executeQuery();
            while (rsdatos.next()) {

                Empleado lista = new Empleado(rsdatos.getString("rfc"), rsdatos.getInt("plazas"),
                        rsdatos.getDouble("salario"), rsdatos.getDouble("antiguedad"), rsdatos.getDouble("p18"),
                        rsdatos.getDouble("p19"), rsdatos.getInt("empleado"), rsdatos.getDouble("baseissste"), rsdatos.getDouble("p1"), rsdatos.getDouble("p1a"),
                        rsdatos.getDouble("p1b"), rsdatos.getDouble("p1c"), rsdatos.getDouble("p1d"), rsdatos.getDouble("p1e"));

                ListaRfcEmpleado.add(lista);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ListaRfcEmpleado;
    }
    
    public void exportarnetos(Reporte reporte) {
       Connection connota = getConexionnota();
       float netosp;
       float percepcionessp;
       float deduccionessp;
       
       float netocp;
       float percepcionescp;
       float deduccionescp;
       
         String sqlnetos = "select rfc,concat(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,empleado,(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07"
                + " + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20 + P_21 + P_22 + P_23 + P_24 +"
                + " P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + "
                + "P_37 + P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + "
                + "P_45 + P_46 + P_47)as percepciones,(D_01 + D_10 + D_CB + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 + D_09 +"
                + " D_11 + D_12 + D_13 + D_14 + D_15+ D_16 + D_17 + D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + "
                + "D_27 + D_28 + D_29 + D_30 + D_31 + D_32+ D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 + D_42 +"
                + " D_01A + D_43 + D_44 + D_45 + D_46+D_47+D_48+D_49+D_50)as deducciones,(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07"
                + " + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20 + P_21 + P_22 + P_23 + P_24 +"
                + " P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + "
                + "P_37 + P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + "
                + "P_45 + P_46 + P_47)- (D_01 + D_10 + D_CB + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 + D_09 +"
                + " D_11 + D_12 + D_13 + D_14 + D_15+ D_16 + D_17 + D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + "
                + "D_27 + D_28 + D_29 + D_30 + D_31 + D_32+ D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 + D_42 +"
                + " D_01A + D_43 + D_44 + D_45 + D_46+D_47+D_48+D_49+D_50) as neto,"
                 + "plantel,d_25,d_26 from hojai where numeroquincena = ?  order by empleado ";
                
         
         String sqlnetosconplan = "select rfc,concat(AP_PAT,' ',AP_MAT,' ',NOMBRE) as nombre,empleado,(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07"
                + " + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20 + P_21 + P_22 + P_23 + P_24 +"
                + " P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + "
                + "P_37 + P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + "
                + "P_45 + P_46 + P_47)as percepciones,(D_01 + D_10 + D_CB + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 + D_09 +"
                + " D_11 + D_12 + D_13 + D_14 + D_15+ D_16 + D_17 + D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + "
                + "D_27 + D_28 + D_29 + D_30 + D_31 + D_32+ D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 + D_42 +"
                + " D_01A + D_43 + D_44 + D_45 + D_46+D_47+D_48+D_49+D_50)as deducciones,(P_01 + P_02 + P_03 + P_04 + P_05 + P_06 + P_07"
                + " + P_08 + P_09 + P_10 + P_11 + P_12 + P_13 + P_14 + P_15+ P_16 + P_17 + P_18 + P_19 + P_20 + P_21 + P_22 + P_23 + P_24 +"
                + " P_25 + P_26 + P_27 + P_28 + P_29 + P_30 +P_DI + P_C1+ P_C2 + P_31 + P_32 + P_33 + P_MC + P_PG + P_PE + P_36 + "
                + "P_37 + P_35 + P_38 + P_39 + P_40 + P_01A + P_01B + P_01C +P_01D + P_01E + P_34 + P_41 + P_42 + P_43 + P_44 + "
                + "P_45 + P_46 + P_47)- (D_01 + D_10 + D_CB + D_02 + D_03 + D_04 + D_05 + D_06 + D_07 + D_08 + D_09 +"
                + " D_11 + D_12 + D_13 + D_14 + D_15+ D_16 + D_17 + D_18 + D_19 + D_20 + D_21 + D_22 + D_23 + D_24 + D_25 + D_26 + "
                + "D_27 + D_28 + D_29 + D_30 + D_31 + D_32+ D_33 + D_34 + D_35 + D_36 + D_37 + D_38 + D_39 + D_40 + D_41 + D_42 +"
                + " D_01A + D_43 + D_44 + D_45 + D_46+D_47+D_48+D_49+D_50) as neto,"
                 + "plantel,d_25,d_26 from hojaicp where numeroquincena = ?  order by empleado";

        Workbook book = new XSSFWorkbook();
        Sheet hojareporte = book.createSheet("NETOSSINPLAN");
        Sheet hojareportecp = book.createSheet("NETOSCONPLAN");
        Sheet hojareportecompara = book.createSheet("COMPARA");
       
        Row row = hojareporte.createRow(1);
        Row rowcp = hojareportecp.createRow(1);
        Row rowencabezado1 = hojareporte.createRow(0);
        Row rowencabezado1cp = hojareportecp.createRow(0);
        
        
        row.createCell(0).setCellValue("RFC");
        row.createCell(1).setCellValue("NOMBRE");
        row.createCell(2).setCellValue("EMPLEADO");
        row.createCell(3).setCellValue("PERCEPCIONES");
        row.createCell(4).setCellValue("DEDUCCIONES");
        row.createCell(5).setCellValue("NETO");
        row.createCell(6).setCellValue("PLANTEL");
        row.createCell(7).setCellValue("D25");
        row.createCell(8).setCellValue("D26");
        
        rowcp.createCell(0).setCellValue("RFC");
        rowcp.createCell(1).setCellValue("NOMBRE");
        rowcp.createCell(2).setCellValue("EMPLEADO");
        rowcp.createCell(3).setCellValue("PERCEPCIONES");
        rowcp.createCell(4).setCellValue("DEDUCCIONES");
        rowcp.createCell(5).setCellValue("NETO");
        rowcp.createCell(6).setCellValue("PLANTEL");
        rowcp.createCell(7).setCellValue("D25");
        rowcp.createCell(8).setCellValue("D26");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = reporte.getPeriodo();

        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short) 7);

        PreparedStatement ps = null;
        PreparedStatement psnetos = null;
        PreparedStatement psnetoscp = null;
        ResultSet rs = null;
        ResultSet rsnetos = null;
        ResultSet rsnetoscp = null;


        int numFilaDatosCuotasYAportaciones = 2;
        int numFilaDatosCuotasYAportacionescp = 2;
        int cantFilas = 0;
        try {
            psnetos = connota.prepareStatement(sqlnetos);
            psnetos.setString(1, reporte.getPeriodo());
            rsnetos = psnetos.executeQuery();

            int numCol = rsnetos.getMetaData().getColumnCount();

            if(rsnetos.last())
            {
            cantFilas = rsnetos.getRow();
            rsnetos.beforeFirst();
            
            }    
            while (rsnetos.next()) {

                Row filaDatosCuotasYAportaciones = hojareporte.createRow(numFilaDatosCuotasYAportaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaRfc = filaDatosCuotasYAportaciones.createCell(0);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rsnetos.getString("rfc"));

                    Cell CeldaNombre = filaDatosCuotasYAportaciones.createCell(1);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rsnetos.getString("nombre"));
                    
                    Cell CeldaEmpleado = filaDatosCuotasYAportaciones.createCell(2);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rsnetos.getInt("empleado"));
                    
                     Cell CeldaPercepciones = filaDatosCuotasYAportaciones.createCell(3);
                    CeldaPercepciones.setCellStyle(datosEstiloMoneda);
                    CeldaPercepciones.setCellValue(rsnetos.getDouble("percepciones"));
                    
                    Cell CeldaDeducciones = filaDatosCuotasYAportaciones.createCell(4);
                    CeldaDeducciones.setCellStyle(datosEstiloMoneda);
                    CeldaDeducciones.setCellValue(rsnetos.getDouble("deducciones"));
                    
                    Cell CeldaNeto = filaDatosCuotasYAportaciones.createCell(5);
                    CeldaNeto.setCellStyle(datosEstiloMoneda);
                    CeldaNeto.setCellValue(rsnetos.getDouble("neto"));
                    
                    Cell CeldaPlantel = filaDatosCuotasYAportaciones.createCell(6);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rsnetos.getString("plantel"));
                    
                    Cell CeldaD25 = filaDatosCuotasYAportaciones.createCell(7);
                    CeldaD25.setCellStyle(datosEstiloMoneda);
                    CeldaD25.setCellValue(rsnetos.getDouble("d_25"));
                    
                    Cell CeldaD26 = filaDatosCuotasYAportaciones.createCell(8);
                    CeldaD26.setCellStyle(datosEstiloMoneda);
                    CeldaD26.setCellValue(rsnetos.getDouble("d_26"));
                    

                }
                numFilaDatosCuotasYAportaciones++;

            }
           
            

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        try {
            psnetoscp = connota.prepareStatement(sqlnetosconplan);
            psnetoscp.setString(1, reporte.getPeriodo());
            rsnetoscp = psnetoscp.executeQuery();

            int numCol = rsnetoscp.getMetaData().getColumnCount();

            while (rsnetoscp.next()) {

                Row filaDatosCuotasYAportacionescp = hojareportecp.createRow(numFilaDatosCuotasYAportacionescp);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaRfc = filaDatosCuotasYAportacionescp.createCell(0);
                    CeldaRfc.setCellStyle(datosEstilo);
                    CeldaRfc.setCellValue(rsnetoscp.getString("rfc"));

                    Cell CeldaNombre = filaDatosCuotasYAportacionescp.createCell(1);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rsnetoscp.getString("nombre"));
                    
                    Cell CeldaEmpleado = filaDatosCuotasYAportacionescp.createCell(2);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rsnetoscp.getInt("empleado"));
                    
                     Cell CeldaPercepciones = filaDatosCuotasYAportacionescp.createCell(3);
                    CeldaPercepciones.setCellStyle(datosEstiloMoneda);
                    CeldaPercepciones.setCellValue(rsnetoscp.getDouble("percepciones"));
                    
                    Cell CeldaDeducciones = filaDatosCuotasYAportacionescp.createCell(4);
                    CeldaDeducciones.setCellStyle(datosEstiloMoneda);
                    CeldaDeducciones.setCellValue(rsnetoscp.getDouble("deducciones"));
                    
                    Cell CeldaNeto = filaDatosCuotasYAportacionescp.createCell(5);
                    CeldaNeto.setCellStyle(datosEstiloMoneda);
                    CeldaNeto.setCellValue(rsnetoscp.getDouble("neto"));
                    
                    Cell CeldaPlantel = filaDatosCuotasYAportacionescp.createCell(6);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rsnetoscp.getString("plantel"));
                    
                    Cell CeldaD25 = filaDatosCuotasYAportacionescp.createCell(7);
                    CeldaD25.setCellStyle(datosEstiloMoneda);
                    CeldaD25.setCellValue(rsnetoscp.getDouble("d_25"));
                    
                    Cell CeldaD26 = filaDatosCuotasYAportacionescp.createCell(8);
                    CeldaD26.setCellStyle(datosEstiloMoneda);
                    CeldaD26.setCellValue(rsnetoscp.getDouble("d_26"));
                    

                }
                numFilaDatosCuotasYAportacionescp++;

            }
           
            

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("NETOS" + qnaactual + ".xlsx").getCanonicalPath());
            fileChooser.setSelectedFile(f);
            //int seleccion = fileChooser.showSaveDialog(fileChooser);
            if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();

                if (file == null) {
                    return;
                }

                //file = new File(file.getParentFile(),"ReporteTrimestral" + periodo + numeroreporte + ".xlsx");
                FileOutputStream fileout = new FileOutputStream(file);
                book.write(fileout);
                fileout.close();
            }

            /*FileOutputStream fileout = new FileOutputStream("ComparativoAhorro" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    

}
