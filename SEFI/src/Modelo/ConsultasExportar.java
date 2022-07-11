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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author victor
 */
public class ConsultasExportar extends Conexion {

    private int qnaanterior;
    private String qnaactual;
    private int periodoanterior;

    public void exportarAhorroExcel(ExportarExcel mov) throws SQLException, SQLException {
        Connection con = getConexion();
        String sqlprueba = "select * FROM  qnaahorrogenerada  WHERE numeroquincena = ? ";
        String sqlgenera = "select round((porcentajecp*sueldobase)/100,2) as monto,rfc,nombre,plantel,porcentajecp,statusout as "
                + "		status,puesto,sueldobase,qnaafiliacion,qnamodificacion,qnadescuento,numeroempleado from afiliado  where "
                + "		qnaafiliacion <= ? AND STATUS IN('A','a') and nombre not in ("
                + "		'FLORES MARTINEZ GABRIEL',"
                + "		'LORENZO RAMIREZ MARIO',"
                + "		'SOTELO ALONSO DULCE MARGARITA',"
                + "		'SALMERON GONZALEZ VICTOR YAIR',"
                + "		'ROBLES CASTRO ERIKA',"
                + "		'gutierrez fragoso jose eduardo',"
                + "		'escobar rodriguez horacio',"
                + "		'VILLAREAL BACA JUAN CARLOS'"
                + "		) order by rfc ";
        String sqlinserta = "insert into "
                + "	qnaahorrogenerada(rfc,nombre,plantel,facore,status,numeroquincena,monto,puesto,sueldobase,qnaafiliacion,"
                + "	qnamodificacion,qnadescuento,numeroempleado) "
                + "	values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        try {
            psVerifica = con.prepareStatement(sqlprueba);
            psVerifica.setString(1, mov.getNumeroQuincena());
            rsverifica = psVerifica.executeQuery();
            if (!rsverifica.next()) {
                try {
                    PreparedStatement psGenera = null;
                    ResultSet rsGenera = null;
                    psGenera = con.prepareStatement(sqlgenera);
                    psGenera.setString(1, mov.getNumeroQuincena());
                    rsGenera = psGenera.executeQuery();
                    while (rsGenera.next()) {

                        PreparedStatement psInserta = null;
                        ResultSet rsInserta = null;
                        psInserta = con.prepareStatement(sqlinserta);
                        //psInserta.setString(1, mov.getNumeroQuincena());

                        psInserta.setString(1, rsGenera.getString("rfc"));
                        psInserta.setString(2, rsGenera.getString("nombre"));
                        psInserta.setString(3, rsGenera.getString("plantel"));
                        psInserta.setString(4, rsGenera.getString("porcentajecp"));
                        psInserta.setString(5, rsGenera.getString("status"));
                        psInserta.setString(6, mov.getNumeroQuincena());
                        psInserta.setString(7,rsGenera.getString("monto"));
                        psInserta.setString(8, rsGenera.getString("puesto"));
                        psInserta.setString(9, rsGenera.getString("sueldobase"));
                        psInserta.setString(10,rsGenera.getString("qnaafiliacion"));
                        
                         psInserta.setString(11,rsGenera.getString("qnamodificacion"));
                         psInserta.setString(12,rsGenera.getString("qnadescuento"));
                         psInserta.setString(13,rsGenera.getString("numeroempleado"));
                       
                        psInserta.execute();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        ////////////////////////////////////////////
        ////////////////////////////////////////////

        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("NUEVOS");
        Sheet bajas = book.createSheet("BAJAS");

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("RFC");
        row.createCell(1).setCellValue("NOMBRE");
        row.createCell(2).setCellValue("PLANTEL");
        row.createCell(3).setCellValue("FACORE");
        row.createCell(4).setCellValue("STATUS");
        row.createCell(5).setCellValue("NUMERO EMPLEADO");

        Row rowbajas = bajas.createRow(0);
        rowbajas.createCell(0).setCellValue("RFC");
        rowbajas.createCell(1).setCellValue("NOMBRE");
        rowbajas.createCell(2).setCellValue("PLANTEL");
        rowbajas.createCell(3).setCellValue("FACORE");
        rowbajas.createCell(4).setCellValue("STATUS");
        rowbajas.createCell(5).setCellValue("NUMERO EMPLEADO");

        CellStyle datosEstilo = book.createCellStyle();
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        datosEstilo.setBorderLeft(BorderStyle.THIN);
        datosEstilo.setBorderRight(BorderStyle.THIN);
        datosEstilo.setBorderBottom(BorderStyle.THIN);
        qnaactual = mov.getNumeroQuincena();
        
        CellStyle datosEstiloMoneda = book.createCellStyle();
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setBorderLeft(BorderStyle.THIN);
        datosEstiloMoneda.setBorderRight(BorderStyle.THIN);
        datosEstiloMoneda.setBorderBottom(BorderStyle.THIN);
        datosEstiloMoneda.setDataFormat((short)7);

        /////////////Consulta a la base de datos
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement psbajas = null;
        ResultSet rsbajas = null;
        //Connection con = getConexion();
        int numFilaDatos = 1;
        int numFilaDatosBajas = 1;

        String sql = "select numeroempleado ,rfc,nombre,"
                + "plantel,facore,CASE WHEN qnamodificacion = '' THEN 'NUEVO' ELSE "
                + "'MODIFICACION' END  as status from qnaahorrogenerada WHERE (numeroquincena = ? "
                + "and qnaafiliacion < ?) and  qnadescuento = ? order by rfc ";

        String sqlbajas = "select numeroempleado,rfc,nombre,plantel,porcentajecp,'BAJA' as status from afiliado "
                + "where qnabaja = ? order by  rfc ";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mov.getNumeroQuincena());
            ps.setString(2, mov.getNumeroQuincena());
            ps.setString(3, mov.getNumeroQuincena());
            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatos = sheet.createRow(numFilaDatos);
                for (int a = 0; a < numCol; a++) {

                    Cell Celdarfc = filaDatos.createCell(0);
                    Celdarfc.setCellStyle(datosEstilo);
                    Celdarfc.setCellValue(rs.getString("rfc"));

                    Cell CeldaNombre = filaDatos.createCell(1);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("nombre"));

                    Cell CeldaPlantel = filaDatos.createCell(2);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rs.getString("plantel"));

                    Cell CeldaFacore = filaDatos.createCell(3);
                    CeldaFacore.setCellStyle(datosEstilo);
                    CeldaFacore.setCellValue(rs.getDouble("facore"));

                    Cell CeldaStatus = filaDatos.createCell(4);
                    CeldaStatus.setCellStyle(datosEstilo);
                    CeldaStatus.setCellValue(rs.getString("status"));

                    Cell CeldaDatos = filaDatos.createCell(5);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString("numeroempleado"));

                }
                numFilaDatos++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            psbajas = con.prepareStatement(sqlbajas);
            psbajas.setString(1, mov.getNumeroQuincena());
            rsbajas = psbajas.executeQuery();

            int numColBajas = rsbajas.getMetaData().getColumnCount();

            while (rsbajas.next()) {

                Row FilaDatosBajas = bajas.createRow(numFilaDatosBajas);
                for (int a = 0; a < numColBajas; a++) {

                    Cell Celdarfcbaja = FilaDatosBajas.createCell(0);
                    Celdarfcbaja.setCellStyle(datosEstilo);
                    Celdarfcbaja.setCellValue(rsbajas.getString("rfc"));

                    Cell CeldaNombrebaja = FilaDatosBajas.createCell(1);
                    CeldaNombrebaja.setCellStyle(datosEstilo);
                    CeldaNombrebaja.setCellValue(rsbajas.getString("nombre"));

                    Cell CeldaPlantelbaja = FilaDatosBajas.createCell(2);
                    CeldaPlantelbaja.setCellStyle(datosEstilo);
                    CeldaPlantelbaja.setCellValue(rsbajas.getString("plantel"));

                    Cell CeldaFacorebaja = FilaDatosBajas.createCell(3);
                    CeldaFacorebaja.setCellStyle(datosEstilo);
                    CeldaFacorebaja.setCellValue(rsbajas.getDouble("porcentajecp"));

                    Cell CeldaStatusbaja = FilaDatosBajas.createCell(4);
                    CeldaStatusbaja.setCellStyle(datosEstilo);
                    CeldaStatusbaja.setCellValue(rsbajas.getString("status"));

                    Cell CeldaDatosbaja = FilaDatosBajas.createCell(5);
                    CeldaDatosbaja.setCellStyle(datosEstilo);
                    CeldaDatosbaja.setCellValue(rsbajas.getString("numeroempleado"));

                }
                numFilaDatosBajas++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("QnaDescuentoAhorroD50" + qnaactual + ".xlsx").getCanonicalPath());
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
            
            /*FileOutputStream fileout = new FileOutputStream("QnaDescuentoAhorroD25"+qnaactual+".xlsx");
            book.write(fileout);
            fileout.close();*/

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportarExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExportarExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Workbook book = new XSSFWorkbook();
        //Sheet sheet = book.createSheet("Hola Java");
        //  JOptionPane.showMessageDialog(null, "Ahorro");  
        // return true;
    }

    public void exportarRecuperacionExcel(ExportarExcel mov) {

        Connection con = getConexion();

        String sqlprueba = "select * FROM  qnarecuperaciongenerada  WHERE numeroquincena = ? ";
        String sqlgenera = "select rfc ,nombre,plantel,descuento,folio"
                + "	,saldo,qnadescuento,numeroempleado from prestamos WHERE qna <= ? and  status  in ('A','a') "
                + "	and nombre not in "
                + "	("
                + "	'FLORES MARTINEZ GABRIEL',"
                + "	'LORENZO RAMIREZ MARIO',"
                + "	'SOTELO ALONSO DULCE MARGARITA',"
                + "	'SALMERON GONZALEZ VICTOR YAIR',"
                + "	'ROBLES CASTRO ERIKA',"
                + "	'gutierrez fragoso jose eduardo',"
                + "	'escobar rodriguez horacio',"
                + "	'VILLAREAL BACA JUAN CARLOS'"
                + "	)order by rfc ";
        String sqlinserta = "insert into"
                + "	qnarecuperaciongenerada(rfc,nombre,plantel,abono,folio,saldo,qnadescuento,numeroempleado,numeroquincena,movimiento)"
                + "	values(?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        try {
            psVerifica = con.prepareStatement(sqlprueba);
            psVerifica.setString(1, mov.getNumeroQuincena());
            rsverifica = psVerifica.executeQuery();
            if (!rsverifica.next()) {
                try {
                    PreparedStatement psGenera = null;
                    ResultSet rsGenera = null;
                    psGenera = con.prepareStatement(sqlgenera);
                    psGenera.setString(1, mov.getNumeroQuincena());
                    rsGenera = psGenera.executeQuery();
                    while (rsGenera.next()) {

                        PreparedStatement psInserta = null;
                        ResultSet rsInserta = null;
                        psInserta = con.prepareStatement(sqlinserta);
                        //psInserta.setString(1, mov.getNumeroQuincena());

                        psInserta.setString(1, rsGenera.getString("rfc"));
                        psInserta.setString(2, rsGenera.getString("nombre"));
                        psInserta.setString(3, rsGenera.getString("plantel"));
                        psInserta.setString(4, rsGenera.getString("descuento"));
                        psInserta.setString(5, rsGenera.getString("folio"));
                        psInserta.setString(6, rsGenera.getString("saldo"));
                        psInserta.setString(7, rsGenera.getString("qnadescuento"));
                        psInserta.setString(8, rsGenera.getString("numeroempleado"));
                        psInserta.setString(9, mov.getNumeroQuincena());
                        psInserta.setString(10, "D");
                        psInserta.execute();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        Workbook book = new XSSFWorkbook();
        Sheet nuevos = book.createSheet("NUEVOS");
        Sheet bajas = book.createSheet("BAJAS");
        Sheet modificaciones = book.createSheet("MODIFICACIONES");
        periodoanterior = Integer.parseInt(mov.getPeriodo()) - 1;
        qnaanterior = Integer.parseInt(mov.getNumeroQuincena()) - 1;
        qnaactual = mov.getNumeroQuincena();

        if (Integer.parseInt(mov.getNumero()) == 1) {

            qnaanterior = Integer.parseInt(String.valueOf(periodoanterior) + "24");

        }

        Row row = nuevos.createRow(0);
        row.createCell(0).setCellValue("RFC");
        row.createCell(1).setCellValue("NOMBRE");
        row.createCell(2).setCellValue("PLANTEL");
        row.createCell(3).setCellValue("DESCUENTO");
        row.createCell(4).setCellValue("FOLIO");
        row.createCell(5).setCellValue("STATUS");
        row.createCell(6).setCellValue("APLICAR");
        row.createCell(7).setCellValue("TOTAL");
        row.createCell(8).setCellValue("EMPLEADO");
        row.createCell(9).setCellValue("SALDO");
        row.createCell(10).setCellValue("PLAZO");
        Row rowbajas = bajas.createRow(0);
        rowbajas.createCell(0).setCellValue("RFC");
        rowbajas.createCell(1).setCellValue("NOMBRE");
        rowbajas.createCell(2).setCellValue("PLANTEL");
        rowbajas.createCell(3).setCellValue("DESCUENTO");
        rowbajas.createCell(4).setCellValue("FOLIO");
        rowbajas.createCell(5).setCellValue("STATUS");
        rowbajas.createCell(6).setCellValue("APLICAR");
        rowbajas.createCell(7).setCellValue("TOTAL");
        rowbajas.createCell(8).setCellValue("EMPLEADO");
        rowbajas.createCell(9).setCellValue("SALDO");
        rowbajas.createCell(10).setCellValue("PLAZO");
        Row rowmodificaciones = modificaciones.createRow(0);
        rowmodificaciones.createCell(0).setCellValue("RFC");
        rowmodificaciones.createCell(1).setCellValue("NOMBRE");
        rowmodificaciones.createCell(2).setCellValue("PLANTEL");
        rowmodificaciones.createCell(3).setCellValue("DESCUENTO");
        rowmodificaciones.createCell(4).setCellValue("FOLIO");
        rowmodificaciones.createCell(5).setCellValue("STATUS");
        rowmodificaciones.createCell(6).setCellValue("APLICAR");
        rowmodificaciones.createCell(7).setCellValue("TOTAL");
        rowmodificaciones.createCell(8).setCellValue("EMPLEADO");
        rowmodificaciones.createCell(9).setCellValue("SALDO");
        rowmodificaciones.createCell(10).setCellValue("PLAZO");

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
        ResultSet rs = null;
        PreparedStatement psbajas = null;
        ResultSet rsbajas = null;
        PreparedStatement psmodificaciones = null;
        ResultSet rsmodificaciones = null;
        //Connection con = getConexion();
        int numFilaDatos = 1;
        int numFilaDatosBajas = 1;
        int numFilaDatosModificaciones = 1;

        String sql = "select a.rfc,a.nombre,CASE WHEN substr(a.plantel,3,1) = ','  "
                + "THEN substr(a.plantel,1,2) ELSE substr(a.plantel,1,3) END as plantel,a.abono as descuento,a.folio, 'A' as status,"
                + "a.abono as aplicar,b.total as total,b.numeroempleado "
                + "as empleado,b.total as saldo,b.plazo from qnarecuperaciongenerada  a INNER JOIN "
                + "prestamos b ON a.folio = b.folio WHERE (a.numeroquincena = ? and a.qnadescuento  = ? "
                + " and b.status in ('A','a')) order by b.folio";

        
        /*String sql = "select a.rfc,a.nombre,CASE WHEN substr(a.plantel,3,1) = ','  "
                + "THEN substr(a.plantel,1,2) ELSE substr(a.plantel,1,3) END as plantel,CASE WHEN (a.abono<=b.saldo) "
                + "THEN(a.abono) ELSE (b.saldo) END as descuento,a.folio, 'A' as status,CASE WHEN "
                + "(a.abono<=b.saldo) THEN(a.abono) ELSE (b.saldo) END as aplicar,b.total as total,b.numeroempleado "
                + "as empleado,b.saldo,b.plazo from qnarecuperaciongenerada  a INNER JOIN "
                + "prestamos b ON a.folio = b.folio WHERE (a.numeroquincena = ? and a.qnadescuento  = ? "
                + " and b.status in ('A','a')) order by b.folio";*/

        String sqlbajas = "select a.rfc ,a.nombre  ,CASE WHEN substr(a.plantel,3,1) = ','  THEN"
                + " substr(a.plantel,1,2) ELSE substr(a.plantel,1,3) END as plantel,CASE WHEN (a.abono<=b.saldo) "
                + "THEN(a.abono) ELSE (b.saldo) END as descuento,a.folio , 'A' as status,CASE WHEN "
                + "(a.abono<=b.saldo) THEN(a.abono) ELSE (b.saldo) END as aplicar,b.total as total,b.numeroempleado "
                + "as empleado,b.saldo as saldo,b.plazo as plazo from qnarecuperaciongenerada  a INNER JOIN "
                + "prestamos b ON a.folio = b.folio WHERE (a.numeroquincena = ? and  "
                + "not exists(select c.folio from qnarecuperaciongenerada c where a.folio = c.folio "
                + "and c.numeroquincena = ? ))";

        String sqlmodificaciones = "select a.rfc ,a.nombre  ,CASE WHEN substr(a.plantel,3,1) = ',' "
                + " THEN substr(a.plantel,1,2) ELSE substr(a.plantel,1,3) END as plantel,CASE WHEN (a.abono<=b.saldo) "
                + "THEN(a.abono) ELSE (b.saldo) END as descuento,a.folio , 'A' as STATUS,CASE WHEN "
                + "(a.abono<=b.saldo) THEN(a.abono) ELSE (b.saldo) END as aplicar,b.total as total,b.numeroempleado as"
                + " EMPLEADO,b.saldo as saldo,b.plazo as plazo from qnarecuperaciongenerada  a INNER JOIN "
                + "prestamos b ON a.folio = b.folio WHERE (a.numeroquincena = ? and  a.abono <> "
                + "(select c.abono from qnarecuperaciongenerada c where c.numeroquincena = ? and "
                + "a.folio = c.folio) and b.status in ('A','a')) order by b.folio";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mov.getNumeroQuincena());
            ps.setString(2, mov.getNumeroQuincena());
            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatos = nuevos.createRow(numFilaDatos);
                for (int a = 0; a < numCol; a++) {

                    Cell Celdarfc = filaDatos.createCell(0);
                    Celdarfc.setCellStyle(datosEstilo);
                    Celdarfc.setCellValue(rs.getString("rfc"));

                    Cell CeldaNombre = filaDatos.createCell(1);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("nombre"));

                    Cell CeldaPlantel = filaDatos.createCell(2);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rs.getString("plantel"));

                    Cell CeldaFacore = filaDatos.createCell(3);
                    CeldaFacore.setCellStyle(datosEstiloMoneda);
                    CeldaFacore.setCellValue(rs.getDouble("descuento"));

                    Cell CeldaStatus = filaDatos.createCell(4);
                    CeldaStatus.setCellStyle(datosEstilo);
                    CeldaStatus.setCellValue(rs.getInt("folio"));

                    Cell CeldaDatos = filaDatos.createCell(5);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString("status"));

                    Cell CeldaAplicar = filaDatos.createCell(6);
                    CeldaAplicar.setCellStyle(datosEstiloMoneda);
                    CeldaAplicar.setCellValue(rs.getDouble("aplicar"));

                    Cell CeldaTotal = filaDatos.createCell(7);
                    CeldaTotal.setCellStyle(datosEstiloMoneda);
                    CeldaTotal.setCellValue(rs.getDouble("total"));

                    Cell CeldaEmpleado = filaDatos.createCell(8);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rs.getString("empleado"));

                    Cell CeldaSaldo = filaDatos.createCell(9);
                    CeldaSaldo.setCellStyle(datosEstiloMoneda);
                    CeldaSaldo.setCellValue(rs.getDouble("saldo"));

                    Cell CeldaPlazo = filaDatos.createCell(10);
                    CeldaPlazo.setCellStyle(datosEstilo);
                    CeldaPlazo.setCellValue(rs.getInt("plazo"));

                }
                numFilaDatos++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            psbajas = con.prepareStatement(sqlbajas);
            psbajas.setInt(1, qnaanterior);
            psbajas.setString(2, mov.getNumeroQuincena());
            rsbajas = psbajas.executeQuery();

            int numCol = rsbajas.getMetaData().getColumnCount();

            while (rsbajas.next()) {

                Row filaDatosBajas = bajas.createRow(numFilaDatosBajas);
                for (int a = 0; a < numCol; a++) {

                    Cell Celdarfc = filaDatosBajas.createCell(0);
                    Celdarfc.setCellStyle(datosEstilo);
                    Celdarfc.setCellValue(rsbajas.getString("rfc"));

                    Cell CeldaNombre = filaDatosBajas.createCell(1);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rsbajas.getString("nombre"));

                    Cell CeldaPlantel = filaDatosBajas.createCell(2);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rsbajas.getString("plantel"));

                    Cell CeldaFacore = filaDatosBajas.createCell(3);
                    CeldaFacore.setCellStyle(datosEstiloMoneda);
                    CeldaFacore.setCellValue(rsbajas.getDouble("descuento"));

                    Cell CeldaStatus = filaDatosBajas.createCell(4);
                    CeldaStatus.setCellStyle(datosEstilo);
                    CeldaStatus.setCellValue(rsbajas.getInt("folio"));

                    Cell CeldaDatos = filaDatosBajas.createCell(5);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rsbajas.getString("status"));

                    Cell CeldaAplicar = filaDatosBajas.createCell(6);
                    CeldaAplicar.setCellStyle(datosEstiloMoneda);
                    CeldaAplicar.setCellValue(rsbajas.getDouble("aplicar"));

                    Cell CeldaTotal = filaDatosBajas.createCell(7);
                    CeldaTotal.setCellStyle(datosEstiloMoneda);
                    CeldaTotal.setCellValue(rsbajas.getDouble("total"));

                    Cell CeldaEmpleado = filaDatosBajas.createCell(8);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rsbajas.getString("empleado"));

                    Cell CeldaSaldo = filaDatosBajas.createCell(9);
                    CeldaSaldo.setCellStyle(datosEstiloMoneda);
                    CeldaSaldo.setCellValue(rsbajas.getDouble("saldo"));

                    Cell CeldaPlazo = filaDatosBajas.createCell(10);
                    CeldaPlazo.setCellStyle(datosEstilo);
                    CeldaPlazo.setCellValue(rsbajas.getInt("plazo"));

                }
                numFilaDatosBajas++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            psmodificaciones = con.prepareStatement(sqlmodificaciones);
            psmodificaciones.setString(1, mov.getNumeroQuincena());
            psmodificaciones.setInt(2, qnaanterior);
            rsmodificaciones = psmodificaciones.executeQuery();

            int numCol = rsmodificaciones.getMetaData().getColumnCount();

            while (rsmodificaciones.next()) {

                Row filaDatosModificaciones = modificaciones.createRow(numFilaDatosModificaciones);
                for (int a = 0; a < numCol; a++) {

                    Cell Celdarfc = filaDatosModificaciones.createCell(0);
                    Celdarfc.setCellStyle(datosEstilo);
                    Celdarfc.setCellValue(rsmodificaciones.getString("rfc"));

                    Cell CeldaNombre = filaDatosModificaciones.createCell(1);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rsmodificaciones.getString("nombre"));

                    Cell CeldaPlantel = filaDatosModificaciones.createCell(2);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rsmodificaciones.getString("plantel"));

                    Cell CeldaFacore = filaDatosModificaciones.createCell(3);
                    CeldaFacore.setCellStyle(datosEstiloMoneda);
                    CeldaFacore.setCellValue(rsmodificaciones.getDouble("descuento"));

                    Cell CeldaStatus = filaDatosModificaciones.createCell(4);
                    CeldaStatus.setCellStyle(datosEstilo);
                    CeldaStatus.setCellValue(rsmodificaciones.getInt("folio"));

                    Cell CeldaDatos = filaDatosModificaciones.createCell(5);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rsmodificaciones.getString("status"));

                    Cell CeldaAplicar = filaDatosModificaciones.createCell(6);
                    CeldaAplicar.setCellStyle(datosEstiloMoneda);
                    CeldaAplicar.setCellValue(rsmodificaciones.getDouble("aplicar"));

                    Cell CeldaTotal = filaDatosModificaciones.createCell(7);
                    CeldaTotal.setCellStyle(datosEstiloMoneda);
                    CeldaTotal.setCellValue(rsmodificaciones.getDouble("total"));

                    Cell CeldaEmpleado = filaDatosModificaciones.createCell(8);
                    CeldaEmpleado.setCellStyle(datosEstilo);
                    CeldaEmpleado.setCellValue(rsmodificaciones.getString("empleado"));

                    Cell CeldaSaldo = filaDatosModificaciones.createCell(9);
                    CeldaSaldo.setCellStyle(datosEstiloMoneda);
                    CeldaSaldo.setCellValue(rsmodificaciones.getDouble("saldo"));

                    Cell CeldaPlazo = filaDatosModificaciones.createCell(10);
                    CeldaPlazo.setCellStyle(datosEstilo);
                    CeldaPlazo.setCellValue(rsmodificaciones.getInt("plazo"));

                }
                numFilaDatosModificaciones++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("QnaDescuentoprestamoDCB" + qnaactual + ".xlsx").getCanonicalPath());
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
            
            /*FileOutputStream fileout = new FileOutputStream("QnaDescuentoprestamod26" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportarExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExportarExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return true;
    }

}
