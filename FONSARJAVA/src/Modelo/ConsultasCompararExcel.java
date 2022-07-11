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
public class ConsultasCompararExcel extends Conexion {

    private int qnaanterior;
    private String qnaactual;
    private int periodoanterior;
    private Double enviado;
    private Double aplicado;
    private Double diferencia;

    public void compararAhorroExcel(CompararExcel mov) {
        Connection con = getConexion();
        Connection connota = getConexionnota();

        Workbook book = new XSSFWorkbook();
        Sheet hojaindebidos = book.createSheet("INDEBIDOS");
        Sheet hojanoaplicados = book.createSheet("NO APLICADOS");
        Sheet hojadisminuyeron = book.createSheet("DISMINUYERON");
        Sheet hojaaumentaron = book.createSheet("AUMENTARON");
        
        
       
        
        Row row = hojaindebidos.createRow(0);
        row.createCell(0).setCellValue("NOMBRE");
        row.createCell(1).setCellValue("PLANTEL");
        row.createCell(2).setCellValue("MONTO");
        row.createCell(3).setCellValue("FACORE");
        row.createCell(4).setCellValue("EMPLEADO");

        Row rownoaplicados = hojanoaplicados.createRow(0);
        rownoaplicados.createCell(0).setCellValue("NOMBRE");
        rownoaplicados.createCell(1).setCellValue("PLANTEL");
        rownoaplicados.createCell(2).setCellValue("MONTO");
        rownoaplicados.createCell(3).setCellValue("FACORE");
        rownoaplicados.createCell(4).setCellValue("EMPLEADO");

        Row rowdisminuyeron = hojadisminuyeron.createRow(0);
        rowdisminuyeron.createCell(0).setCellValue("NOMBRE");
        rowdisminuyeron.createCell(1).setCellValue("ENVIADO");
        rowdisminuyeron.createCell(2).setCellValue("APLICADO");
        rowdisminuyeron.createCell(3).setCellValue("DIFERENCIA");
        rowdisminuyeron.createCell(4).setCellValue("EMPLEADO");

        Row rowaumentaron = hojaaumentaron.createRow(0);
        rowaumentaron.createCell(0).setCellValue("NOMBRE");
        rowaumentaron.createCell(1).setCellValue("ENVIADO");
        rowaumentaron.createCell(2).setCellValue("APLICADO");
        rowaumentaron.createCell(3).setCellValue("DIFERENCIA");
        rowaumentaron.createCell(4).setCellValue("EMPLEADO");

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
        datosEstiloMoneda.setDataFormat((short) 7);

        /////////////Consulta a la base de datos
        PreparedStatement ps = null;
        PreparedStatement psindebidos = null;
        PreparedStatement psnomina = null;
        PreparedStatement psjubilados = null;
        PreparedStatement pstemporalgenerada = null;
        PreparedStatement pstemporalrecuperada = null;
        PreparedStatement pstemporalgenerada2 = null;

        ResultSet rs = null;
        ResultSet rsindebidos = null;
        ResultSet rsnomina = null;
        ResultSet rsjubilados = null;

        /*PreparedStatement psnoaplicados = null;
        ResultSet rsnoaplicados = null;
        PreparedStatement psaumentaron = null;
        ResultSet rsnoaplicados = null;
        PreparedStatement psnoaplicados = null;
        ResultSet rsnoaplicados = null;*/
        //Connection con = getConexion();
        int numFilaDatosIndebidos = 1;
        int numFilaDatosNoAplicados = 1;
        int numFilaDatosDisminuyeron = 1;
        int numFilaDatosAumentaron = 1;
        //int numFilaDatosBajas = 1;

        String sqlgeneradatemporal = "create temporary table tmp_generada"
                + "(select rfc,nombre,plantel,numeroquincena,facore,monto,numeroempleado "
                + "from qnaahorrogenerada where numeroquincena = ? )";

        String sqlgeneradatemporal2 = "create temporary table tmp_generada2"
                + "(select rfc,nombre,plantel,numeroquincena,facore,monto,numeroempleado "
                + "from qnaahorrogenerada where numeroquincena = ?)";

        String sqlrecuperadatemporal = "create temporary table tmp_recuperada"
                + "(select rfc,nombre,plantel,numeroquincena,facore,importe,numeroempleado "
                + "from qnaahorrorecuperada where numeroquincena = ?  and literal in ('CIP','CII'))";

        try {
            pstemporalgenerada = con.prepareStatement(sqlgeneradatemporal);
            pstemporalgenerada.setString(1, mov.getNumeroQuincena());
            pstemporalgenerada.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pstemporalgenerada2 = con.prepareStatement(sqlgeneradatemporal2);
            pstemporalgenerada2.setString(1, mov.getNumeroQuincena());
            pstemporalgenerada2.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pstemporalrecuperada = con.prepareStatement(sqlrecuperadatemporal);
            pstemporalrecuperada.setString(1, mov.getNumeroQuincena());
            pstemporalrecuperada.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlindebidos = "select nombre,plantel,importe,facore,CASE WHEN numeroempleado IS NULL THEN 0 ELSE numeroempleado END as numeroempleado from tmp_recuperada where rfc not in (select rfc from tmp_generada)";

        String sqlnoaplicados = "select * from tmp_generada where rfc not in (select rfc from tmp_recuperada)";

        String sqldisminuyeron = "select numeroempleado,nombre,rfc,facore as aplicado,(select facore from tmp_generada2 where rfc = \n"
                + "                tmp_recuperada.rfc and numeroempleado = tmp_recuperada.numeroempleado  )as enviado from tmp_recuperada  \n"
                + "                where \n"
                + "                rfc  in (select rfc from tmp_generada where  round(tmp_generada.facore,2) >  round(tmp_recuperada.facore,2)\n"
                + "                and tmp_generada.numeroempleado = tmp_recuperada.numeroempleado and tmp_generada.rfc = tmp_recuperada.rfc and tmp_generada.plantel = \n"
                + "                tmp_recuperada.plantel)";

        String sqlaumentaron = "select numeroempleado,nombre,rfc,facore as aplicado,\n"
                + "(select sum(facore) from tmp_generada2 where rfc = tmp_recuperada.rfc and numeroempleado = \n"
                + "tmp_recuperada.numeroempleado and\n"
                + "plantel = tmp_recuperada.plantel limit 1 )as enviado from tmp_recuperada \n"
                + "where rfc  in (select rfc from tmp_generada where \n"
                + "round(tmp_generada.facore,2) <  round(tmp_recuperada.facore,2)	\n"
                + "and tmp_generada.numeroempleado = tmp_recuperada.numeroempleado and \n"
                + "tmp_generada.rfc = tmp_recuperada.rfc and tmp_generada.plantel = tmp_recuperada.plantel)";

        String sqlnomina = "select (P_01+P_01A+P_01B+P_01C+P_01D+P_01E+P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+\n"
                + "		P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27+P_28+P_29+P_30+P_31+P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_PE+P_PG)\n"
                + "		- (D_01+D_02+D_03+D_04+D_05+D_06+D_07+D_08+D_09+D_10+D_11+D_12+D_13+D_14+D_15+D_16+D_17+D_18+D_19+D_20+D_21+D_22+D_23+D_24+D_25+D_26+D_27+D_28+D_29+D_30+D_31+D_32+D_33+D_34+\n"
                + "		D_35+D_36+D_37+D_38+D_39+D_40+D_41+D_42+D_43+D_44+D_45+D_46+D_CB)as neto from hojai where empleado = ? \n"
                + "		and numeroquincena = ?  group by empleado";

        String sqlestatusjubilados = "select estatus from jubiladosfiar where nombre = ?";
        
        
        try {
            psindebidos = con.prepareStatement(sqlindebidos);
            //ps.setString(1, mov.getNumeroQuincena());
            //ps.setString(2, mov.getNumeroQuincena());
            //ps.setString(3, mov.getNumeroQuincena());
            // ps.setString(4, mov.getNumeroQuincena());

            rsindebidos = psindebidos.executeQuery();

            int numCol = rsindebidos.getMetaData().getColumnCount();

            while (rsindebidos.next()) {

                Row filaDatosIndebidos = hojaindebidos.createRow(numFilaDatosIndebidos);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosIndebidos.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rsindebidos.getString("nombre"));

                    Cell CeldaPlantel = filaDatosIndebidos.createCell(1);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rsindebidos.getString("plantel"));

                    Cell CeldaImporte = filaDatosIndebidos.createCell(2);
                    CeldaImporte.setCellStyle(datosEstiloMoneda);
                    CeldaImporte.setCellValue(rsindebidos.getDouble("importe"));

                    Cell CeldaFacore = filaDatosIndebidos.createCell(3);
                    CeldaFacore.setCellStyle(datosEstilo);
                    CeldaFacore.setCellValue(rsindebidos.getDouble("facore"));

                    Cell CeldaNumeroEmpleado = filaDatosIndebidos.createCell(4);
                    CeldaNumeroEmpleado.setCellStyle(datosEstilo);
                    CeldaNumeroEmpleado.setCellValue(rsindebidos.getString("numeroempleado"));

                }
                numFilaDatosIndebidos++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////////////NO APLICADOS
        try {
            ps = con.prepareStatement(sqlnoaplicados);
            //ps.setString(1, mov.getNumeroQuincena());

            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatosNoaplicados = hojanoaplicados.createRow(numFilaDatosNoAplicados);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosNoaplicados.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("nombre"));

                    Cell CeldaPlantel = filaDatosNoaplicados.createCell(1);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rs.getString("plantel"));

                    Cell CeldaImporte = filaDatosNoaplicados.createCell(2);
                    CeldaImporte.setCellStyle(datosEstiloMoneda);
                    CeldaImporte.setCellValue(rs.getDouble("monto"));

                    Cell CeldaFacore = filaDatosNoaplicados.createCell(3);
                    CeldaFacore.setCellStyle(datosEstilo);
                    CeldaFacore.setCellValue(rs.getDouble("facore"));

                    Cell CeldaDatos = filaDatosNoaplicados.createCell(4);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString("numeroempleado"));

                 
                }
                
                 // try {
                        psnomina = connota.prepareStatement(sqlnomina);
                        String numeroempleadonomina = rs.getString("numeroempleado");
                        psnomina.setString(1,numeroempleadonomina );
                        psnomina.setString(2, mov.getNumeroQuincena());
                        rsnomina = psnomina.executeQuery();
                        
                        if(rsnomina.next()) {
                        Cell CeldaNeto = filaDatosNoaplicados.createCell(5);
                        CeldaNeto.setCellStyle(datosEstilo);
                        CeldaNeto.setCellValue(rsnomina.getString("neto"));
                        
                        //Cell CeldaSioNo = filaDatosNoaplicados.createCell(6);
                        //CeldaSioNo.setCellStyle(datosEstilo);
                        //CeldaSioNo.setCellValue("SI");
                        }else{
                        Cell CeldaNeto = filaDatosNoaplicados.createCell(5);
                        CeldaNeto.setCellStyle(datosEstilo);
                        CeldaNeto.setCellValue("No Nomina");
                        }
                        
                        
                        psjubilados = connota.prepareStatement(sqlestatusjubilados);
                        String nombrejubilados = rs.getString("nombre");
                        psjubilados.setString(1,nombrejubilados );
                        rsjubilados = psjubilados.executeQuery();
                        
                        if (rsjubilados.next()) {
                            Cell CeldaNeto = filaDatosNoaplicados.createCell(6);
                            CeldaNeto.setCellStyle(datosEstilo);
                            CeldaNeto.setCellValue(rsjubilados.getString("estatus"));
                        } else {
                            Cell CeldaNeto = filaDatosNoaplicados.createCell(6);
                            CeldaNeto.setCellStyle(datosEstilo);
                            CeldaNeto.setCellValue("No Esta En Lista");
                        }
                    //} catch (SQLException ex) {
                    //    Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
                   // }
                
                numFilaDatosNoAplicados++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////////////////////////////DISMINUYERON DESCUENTO
        try {
            ps = con.prepareStatement(sqldisminuyeron);
            //ps.setString(1, mov.getNumeroQuincena());
            //ps.setString(2, mov.getNumeroQuincena());

            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatosDisminuyeron = hojadisminuyeron.createRow(numFilaDatosDisminuyeron);

                enviado = rs.getDouble("enviado");
                aplicado = rs.getDouble("aplicado");
                diferencia = enviado - aplicado;

                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosDisminuyeron.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("nombre"));

                    Cell CeldaAhorroFIAR = filaDatosDisminuyeron.createCell(1);
                    CeldaAhorroFIAR.setCellStyle(datosEstilo);
                    CeldaAhorroFIAR.setCellValue(enviado);

                    Cell CeldaAhorroOUTSOURCING = filaDatosDisminuyeron.createCell(2);
                    CeldaAhorroOUTSOURCING.setCellStyle(datosEstilo);
                    CeldaAhorroOUTSOURCING.setCellValue(aplicado);

                    Cell CeldaDIFERENCIA = filaDatosDisminuyeron.createCell(3);
                    CeldaDIFERENCIA.setCellStyle(datosEstilo);
                    CeldaDIFERENCIA.setCellValue(diferencia);

                    Cell Celdanumeroempleado = filaDatosDisminuyeron.createCell(4);
                    Celdanumeroempleado.setCellStyle(datosEstilo);
                    Celdanumeroempleado.setCellValue(rs.getString("numeroempleado"));

                }
                numFilaDatosDisminuyeron++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        ///////////////TERMINA DISMINUCION DESCUENTO
        ///////////////AUMENTARON DESCUENTO
        try {
            ps = con.prepareStatement(sqlaumentaron);
            //ps.setString(1, mov.getNumeroQuincena());
            //ps.setString(2, mov.getNumeroQuincena());

            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatosAumentaron = hojaaumentaron.createRow(numFilaDatosAumentaron);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosAumentaron.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("nombre"));

                    enviado = rs.getDouble("enviado");
                    aplicado = rs.getDouble("aplicado");
                    diferencia = aplicado - enviado;

                    Cell CeldaAhorroFIAR = filaDatosAumentaron.createCell(1);
                    CeldaAhorroFIAR.setCellStyle(datosEstilo);
                    CeldaAhorroFIAR.setCellValue(enviado);

                    Cell CeldaAhorroOUTSOURCING = filaDatosAumentaron.createCell(2);
                    CeldaAhorroOUTSOURCING.setCellStyle(datosEstilo);
                    CeldaAhorroOUTSOURCING.setCellValue(aplicado);

                    Cell CeldaDIFERENCIA = filaDatosAumentaron.createCell(3);
                    CeldaDIFERENCIA.setCellStyle(datosEstilo);
                    CeldaDIFERENCIA.setCellValue(diferencia);

                    Cell Celdanumeroempleado = filaDatosAumentaron.createCell(4);
                    Celdanumeroempleado.setCellStyle(datosEstilo);
                    Celdanumeroempleado.setCellValue(rs.getString("numeroempleado"));

                }
                numFilaDatosAumentaron++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("FonsarComparativoAhorro" + qnaactual + ".xlsx").getCanonicalPath());
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
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void compararRecuperacionExcel(CompararExcel mov) {
        Connection con = getConexion();
        Connection connota = getConexionnota();

        Workbook book = new XSSFWorkbook();
        Sheet hojaindebidos = book.createSheet("INDEBIDOS");
        Sheet hojanoaplicados = book.createSheet("NO APLICADOS");
        Sheet hojadisminuyeron = book.createSheet("DISMINUYERON");
        Sheet hojaaumentaron = book.createSheet("AUMENTARON");
        Sheet hojanombrediferente = book.createSheet("NOMBREDIFERENTE");
        Sheet hojarfcdiferente = book.createSheet("RFCDIFERENTE");
        Sheet hojaduplicados = book.createSheet("DUPLICADOS");

        Row rownombrediferente = hojanombrediferente.createRow(0);
        rownombrediferente.createCell(0).setCellValue("RFCAPLICADO");
        rownombrediferente.createCell(1).setCellValue("NOMBRE APLICADO");
        rownombrediferente.createCell(2).setCellValue("FOLIOENVIADO");
        rownombrediferente.createCell(3).setCellValue("RFCENVIADO");
        rownombrediferente.createCell(4).setCellValue("NOMBREENVIADO");
        rownombrediferente.createCell(4).setCellValue("FOLIOENVIADO");
        
        Row rowrfcdiferente = hojarfcdiferente.createRow(0);
        rowrfcdiferente.createCell(0).setCellValue("RFCAPLICADO");
        rowrfcdiferente.createCell(1).setCellValue("NOMBRE APLICADO");
        rowrfcdiferente.createCell(2).setCellValue("FOLIOENVIADO");
        rowrfcdiferente.createCell(3).setCellValue("RFCENVIADO");
        rowrfcdiferente.createCell(4).setCellValue("NOMBREENVIADO");
        rowrfcdiferente.createCell(4).setCellValue("FOLIOENVIADO");

        
        Row rowduplicados = hojaduplicados.createRow(0);
        rowduplicados.createCell(0).setCellValue("NOMBRE");
        rowduplicados.createCell(1).setCellValue("PLANTEL");
        rowduplicados.createCell(2).setCellValue("FOLIO");
        rowduplicados.createCell(3).setCellValue("DESCUENTO");
        rowduplicados.createCell(4).setCellValue("EMPLEADO");
        rowduplicados.createCell(5).setCellValue("VECES");
       
        Row row = hojaindebidos.createRow(0);
        row.createCell(0).setCellValue("NOMBRE");
        row.createCell(1).setCellValue("PLANTEL");
        row.createCell(2).setCellValue("FOLIO");
        row.createCell(3).setCellValue("DESCUENTO");
        row.createCell(4).setCellValue("EMPLEADO");

        Row rownoaplicados = hojanoaplicados.createRow(0);
        rownoaplicados.createCell(0).setCellValue("NOMBRE");
        rownoaplicados.createCell(1).setCellValue("PLANTEL");
        rownoaplicados.createCell(2).setCellValue("FOLIO");
        rownoaplicados.createCell(3).setCellValue("DESCUENTO");
        rownoaplicados.createCell(4).setCellValue("EMPLEADO");

        Row rowdisminuyeron = hojadisminuyeron.createRow(0);
        rowdisminuyeron.createCell(0).setCellValue("NOMBRE");
        rowdisminuyeron.createCell(1).setCellValue("FOLIO");
        rowdisminuyeron.createCell(2).setCellValue("ENVIADO");
        rowdisminuyeron.createCell(3).setCellValue("APLICADO");
        rowdisminuyeron.createCell(4).setCellValue("DIFERENCIA");
        rowdisminuyeron.createCell(5).setCellValue("EMPLEADO");

        Row rowaumentaron = hojaaumentaron.createRow(0);
        rowaumentaron.createCell(0).setCellValue("NOMBRE");
        rowaumentaron.createCell(1).setCellValue("FOLIO");
        rowaumentaron.createCell(2).setCellValue("ENVIADO");
        rowaumentaron.createCell(3).setCellValue("APLICADO");
        rowaumentaron.createCell(4).setCellValue("DIFERENCIA");
        rowaumentaron.createCell(5).setCellValue("EMPLEADO");

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
        datosEstiloMoneda.setDataFormat((short) 7);

        /////////////Consulta a la base de datos
        PreparedStatement psnomina = null;
        PreparedStatement psjubilados = null;
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsnomina = null;
        ResultSet rsjubilados = null;


        int numFilaDatosIndebidos = 1;
        int numFilaDatosNoAplicados = 1;
        int numFilaDatosDisminuyeron = 1;
        int numFilaDatosAumentaron = 1;
        int numFilaDatosNombreDiferente = 1;
        int numFilaDatosRfcDiferente = 1;
        int numFilaDatosDuplicados = 1;

        String sqlindebidos = "select * from qnarecuperacionrecuperada where "
                + "numeroquincena = ? and folio not in (select folio from "
                + "qnarecuperaciongenerada where numeroquincena = ?) and movimiento in ('D') ";

        String sqlnoaplicados = "select * from qnarecuperaciongenerada where numeroquincena = "
                + "? and folio not in (select folio from qnarecuperacionrecuperada where "
                + "numeroquincena = ?)  ";

        String sqldisminuyeron = "select a.numeroempleado,a.rfc,a.folio,a.numeroquincena,a.abono as enviado,a.nombre ,"
                + "(select abono from qnarecuperacionrecuperada where folio = a.folio and numeroquincena = ? "
                + "and movimiento in ('D') group by folio )as aplicado  from qnarecuperaciongenerada a "
                + "where a.numeroquincena = ? and a.folio  in (select b.folio from qnarecuperacionrecuperada "
                + "b  where b.numeroquincena = ?  and b.abono < a.abono and b.movimiento in ('D') )   ";

       /* String sqlaumentaron = "select a.numeroempleado,a.rfc,a.folio,a.numeroquincena,a.abono as enviado,a.nombre "
                + ",(select abono from qnarecuperacionrecuperada where folio = a.folio and numeroquincena = ?)"
                + "as aplicado  from qnarecuperaciongenerada a where a.numeroquincena = ? and a.folio"
                + "  in (select b.folio from qnarecuperacionrecuperada b  where b.numeroquincena = ?  "
                + "and b.abono > a.abono and b.movimiento in ('D') )  ";*/
        String sqlaumentaron = "select a.numeroempleado,a.rfc,a.folio,a.numeroquincena,a.abono as enviado,a.nombre ,"
                + "(select abono from qnarecuperacionrecuperada where folio = a.folio and numeroquincena = ? "
                + "and movimiento in ('D') group by folio )as aplicado  from qnarecuperaciongenerada a "
                + "where a.numeroquincena = ? and a.folio  in (select b.folio from qnarecuperacionrecuperada "
                + "b  where b.numeroquincena = ?  and b.abono > a.abono and b.movimiento in ('D') )   ";
        
         String sqlnomina = "select (P_01+P_01A+P_01B+P_01C+P_01D+P_01E+P_02+P_03+P_04+P_05+P_06+P_07+P_08+P_09+P_10+P_11+P_12+P_13+P_14+P_15+P_16+P_17+P_18+P_19+\n"
                + "		P_20+P_21+P_22+P_23+P_24+P_25+P_26+P_27+P_28+P_29+P_30+P_31+P_32+P_33+P_34+P_35+P_36+P_37+P_38+P_39+P_40+P_41+P_42+P_43+P_44+P_45+P_46+P_PE+P_PG)\n"
                + "		- (D_01+D_02+D_03+D_04+D_05+D_06+D_07+D_08+D_09+D_10+D_11+D_12+D_13+D_14+D_15+D_16+D_17+D_18+D_19+D_20+D_21+D_22+D_23+D_24+D_25+D_26+D_27+D_28+D_29+D_30+D_31+D_32+D_33+D_34+\n"
                + "		D_35+D_36+D_37+D_38+D_39+D_40+D_41+D_42+D_43+D_44+D_45+D_46+D_CB)as neto from hojai where empleado = ? \n"
                + "		and numeroquincena = ?  group by empleado";

        String sqlestatusjubilados = "select estatus from jubiladosfiar where nombre = ?";
        
        String sqlnombrediferente = "select t1.rfc as rfcRH,t1.nombre as nombreRH,t1.folio as FolioRH,"
                + "t2.rfc,t2.nombre,t2.folio from qnarecuperacionrecuperada t1,prestamos t2 where "
                + "t1.numeroquincena = ? and t1.folio = t2.folio and t1.nombre <> t2.nombre";
        String sqlrfcdiferente = "select t1.rfc as rfcRH,t1.nombre as nombreRH,t1.folio as FolioRH,t2.rfc,t2.nombre,t2.folio from "
                + "qnarecuperacionrecuperada t1,prestamos t2 where t1.numeroquincena = ? and "
                + "t1.folio = t2.folio and t1.rfc <> t2.rfc";
        
         String sqlduplicados = "select nombre,plantel,abono,numeroempleado,folio,count(*)as veces from qnarecuperacionrecuperada where numeroquincena"
                 + "= ? and movimiento in ('D') group by folio having count(*) > 1";
      
     

        try {
            ps = con.prepareStatement(sqlindebidos);
            ps.setString(1, mov.getNumeroQuincena());
            ps.setString(2, mov.getNumeroQuincena());
            //ps.setString(3, mov.getNumeroQuincena());
            // ps.setString(4, mov.getNumeroQuincena());

            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatosIndebidos = hojaindebidos.createRow(numFilaDatosIndebidos);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosIndebidos.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("nombre"));

                    Cell CeldaPlantel = filaDatosIndebidos.createCell(1);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rs.getString("plantel"));

                    Cell CeldaImporte = filaDatosIndebidos.createCell(2);
                    CeldaImporte.setCellStyle(datosEstilo);
                    CeldaImporte.setCellValue(rs.getInt("folio"));

                    Cell CeldaFacore = filaDatosIndebidos.createCell(3);
                    CeldaFacore.setCellStyle(datosEstiloMoneda);
                    CeldaFacore.setCellValue(rs.getDouble("abono"));

                    Cell CeldaDatos = filaDatosIndebidos.createCell(4);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString("numeroempleado"));

                }
                
                
                numFilaDatosIndebidos++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////////////NO APLICADOS
        try {
            ps = con.prepareStatement(sqlnoaplicados);
            ps.setString(1, mov.getNumeroQuincena());
            ps.setString(2, mov.getNumeroQuincena());

            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatosNoaplicados = hojanoaplicados.createRow(numFilaDatosNoAplicados);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosNoaplicados.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("nombre"));

                    Cell CeldaPlantel = filaDatosNoaplicados.createCell(1);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rs.getString("plantel"));

                    Cell CeldaImporte = filaDatosNoaplicados.createCell(2);
                    CeldaImporte.setCellStyle(datosEstilo);
                    CeldaImporte.setCellValue(rs.getInt("folio"));

                    Cell CeldaFacore = filaDatosNoaplicados.createCell(3);
                    CeldaFacore.setCellStyle(datosEstiloMoneda);
                    CeldaFacore.setCellValue(rs.getDouble("abono"));

                    Cell CeldaDatos = filaDatosNoaplicados.createCell(4);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString("numeroempleado"));

                }
                
                        psnomina = connota.prepareStatement(sqlnomina);
                        String numeroempleadonomina = rs.getString("numeroempleado");
                        psnomina.setString(1,numeroempleadonomina );
                        psnomina.setString(2, mov.getNumeroQuincena());
                        rsnomina = psnomina.executeQuery();
                        
                        if(rsnomina.next()) {
                        Cell CeldaNeto = filaDatosNoaplicados.createCell(5);
                        CeldaNeto.setCellStyle(datosEstilo);
                        CeldaNeto.setCellValue(rsnomina.getString("neto"));
                        
                        //Cell CeldaSioNo = filaDatosNoaplicados.createCell(6);
                        //CeldaSioNo.setCellStyle(datosEstilo);
                        //CeldaSioNo.setCellValue("SI");
                        }else{
                        Cell CeldaNeto = filaDatosNoaplicados.createCell(5);
                        CeldaNeto.setCellStyle(datosEstilo);
                        CeldaNeto.setCellValue("No Nomina");
                        }
                        psjubilados = connota.prepareStatement(sqlestatusjubilados);
                        String nombrejubilados = rs.getString("nombre");
                        psjubilados.setString(1,nombrejubilados );
                        rsjubilados = psjubilados.executeQuery();
                        
                        if (rsjubilados.next()) {
                            Cell CeldaNeto = filaDatosNoaplicados.createCell(6);
                            CeldaNeto.setCellStyle(datosEstilo);
                            CeldaNeto.setCellValue(rsjubilados.getString("estatus"));
                        } else {
                            Cell CeldaNeto = filaDatosNoaplicados.createCell(6);
                            CeldaNeto.setCellStyle(datosEstilo);
                            CeldaNeto.setCellValue("No Esta En Lista");
                        }
                numFilaDatosNoAplicados++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        ////////////////////////////DISMINUYERON DESCUENTO
        try {
            ps = con.prepareStatement(sqldisminuyeron);
            ps.setString(1, mov.getNumeroQuincena());
            ps.setString(2, mov.getNumeroQuincena());
            ps.setString(3, mov.getNumeroQuincena());

            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatosDisminuyeron = hojadisminuyeron.createRow(numFilaDatosDisminuyeron);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosDisminuyeron.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("nombre"));

                    Cell CeldaAhorroFIAR = filaDatosDisminuyeron.createCell(1);
                    CeldaAhorroFIAR.setCellStyle(datosEstilo);
                    CeldaAhorroFIAR.setCellValue(rs.getInt("folio"));

                    enviado = rs.getDouble("enviado");
                    aplicado = rs.getDouble("aplicado");
                    diferencia = enviado - aplicado;
                    Cell CeldaAhorroOUTSOURCING = filaDatosDisminuyeron.createCell(2);
                    CeldaAhorroOUTSOURCING.setCellStyle(datosEstiloMoneda);
                    CeldaAhorroOUTSOURCING.setCellValue(enviado);

                    Cell CeldaDIFERENCIA = filaDatosDisminuyeron.createCell(3);
                    CeldaDIFERENCIA.setCellStyle(datosEstiloMoneda);
                    CeldaDIFERENCIA.setCellValue(aplicado);

                    Cell CeldaDiferencia = filaDatosDisminuyeron.createCell(4);
                    CeldaDiferencia.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia.setCellValue(diferencia);

                    Cell Celdanumeroempleado = filaDatosDisminuyeron.createCell(5);
                    Celdanumeroempleado.setCellStyle(datosEstilo);
                    Celdanumeroempleado.setCellValue(rs.getString("numeroempleado"));

                }
                numFilaDatosDisminuyeron++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        ///////////////TERMINA DISMINUCION DESCUENTO
        ///////////////AUMENTARON DESCUENTO
        try {
            ps = con.prepareStatement(sqlaumentaron);
            ps.setString(1, mov.getNumeroQuincena());
            ps.setString(2, mov.getNumeroQuincena());
            ps.setString(3, mov.getNumeroQuincena());

            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatosAumentaron = hojaaumentaron.createRow(numFilaDatosAumentaron);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosAumentaron.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("nombre"));

                    Cell CeldaAhorroFIAR = filaDatosAumentaron.createCell(1);
                    CeldaAhorroFIAR.setCellStyle(datosEstilo);
                    CeldaAhorroFIAR.setCellValue(rs.getInt("folio"));

                    enviado = rs.getDouble("enviado");
                    aplicado = rs.getDouble("aplicado");
                    diferencia = aplicado - enviado;
                    Cell CeldaAhorroOUTSOURCING = filaDatosAumentaron.createCell(2);
                    CeldaAhorroOUTSOURCING.setCellStyle(datosEstiloMoneda);
                    CeldaAhorroOUTSOURCING.setCellValue(enviado);

                    Cell CeldaDIFERENCIA = filaDatosAumentaron.createCell(3);
                    CeldaDIFERENCIA.setCellStyle(datosEstiloMoneda);
                    CeldaDIFERENCIA.setCellValue(aplicado);

                    Cell CeldaDiferencia = filaDatosAumentaron.createCell(4);
                    CeldaDiferencia.setCellStyle(datosEstiloMoneda);
                    CeldaDiferencia.setCellValue(diferencia);

                    Cell Celdanumeroempleado = filaDatosAumentaron.createCell(5);
                    Celdanumeroempleado.setCellStyle(datosEstilo);
                    Celdanumeroempleado.setCellValue(rs.getString("numeroempleado"));

                }
                numFilaDatosAumentaron++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        /////////////FIN AUMENTARON DESCUENTO
        
         ////////////////////////////NOMBRE NO CORRESPONDE
        try {
            ps = con.prepareStatement(sqlnombrediferente);
            ps.setString(1, mov.getNumeroQuincena());
          
            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatosNombreDiferente;
                filaDatosNombreDiferente = hojanombrediferente.createRow(numFilaDatosNombreDiferente);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosNombreDiferente.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("rfcRH"));

                    Cell CeldaAhorroFIAR = filaDatosNombreDiferente.createCell(1);
                    CeldaAhorroFIAR.setCellStyle(datosEstilo);
                    CeldaAhorroFIAR.setCellValue(rs.getString("nombreRH"));

                    //enviado = rs.getDouble("enviado");
                    //aplicado = rs.getDouble("aplicado");
                    //diferencia = enviado - aplicado;
                    Cell CeldaAhorroOUTSOURCING = filaDatosNombreDiferente.createCell(2);
                    CeldaAhorroOUTSOURCING.setCellStyle(datosEstilo);
                    CeldaAhorroOUTSOURCING.setCellValue(rs.getString("folioRH"));

                    Cell CeldaDIFERENCIA = filaDatosNombreDiferente.createCell(3);
                    CeldaDIFERENCIA.setCellStyle(datosEstilo);
                    CeldaDIFERENCIA.setCellValue(rs.getString("rfc"));

                    Cell CeldaDiferencia = filaDatosNombreDiferente.createCell(4);
                    CeldaDiferencia.setCellStyle(datosEstilo);
                    CeldaDiferencia.setCellValue(rs.getString("nombre"));

                    Cell Celdanumeroempleado = filaDatosNombreDiferente.createCell(5);
                    Celdanumeroempleado.setCellStyle(datosEstilo);
                    Celdanumeroempleado.setCellValue(rs.getString("folio"));

                }
                numFilaDatosNombreDiferente++;
                

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        ///////////////TERMINA NOMBRE NO CORRESPONDE
         ////////////////////////////RFC NO CORRESPONDE
        try {
            ps = con.prepareStatement(sqlrfcdiferente);
            ps.setString(1, mov.getNumeroQuincena());
          
            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatosRfcDiferente;
                filaDatosRfcDiferente = hojarfcdiferente.createRow(numFilaDatosRfcDiferente);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosRfcDiferente.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("rfcRH"));

                    Cell CeldaAhorroFIAR = filaDatosRfcDiferente.createCell(1);
                    CeldaAhorroFIAR.setCellStyle(datosEstilo);
                    CeldaAhorroFIAR.setCellValue(rs.getString("nombreRH"));

                    //enviado = rs.getDouble("enviado");
                    //aplicado = rs.getDouble("aplicado");
                    //diferencia = enviado - aplicado;
                    Cell CeldaAhorroOUTSOURCING = filaDatosRfcDiferente.createCell(2);
                    CeldaAhorroOUTSOURCING.setCellStyle(datosEstilo);
                    CeldaAhorroOUTSOURCING.setCellValue(rs.getString("folioRH"));

                    Cell CeldaDIFERENCIA = filaDatosRfcDiferente.createCell(3);
                    CeldaDIFERENCIA.setCellStyle(datosEstilo);
                    CeldaDIFERENCIA.setCellValue(rs.getString("rfc"));

                    Cell CeldaDiferencia = filaDatosRfcDiferente.createCell(4);
                    CeldaDiferencia.setCellStyle(datosEstilo);
                    CeldaDiferencia.setCellValue(rs.getString("nombre"));

                    Cell Celdanumeroempleado = filaDatosRfcDiferente.createCell(5);
                    Celdanumeroempleado.setCellStyle(datosEstilo);
                    Celdanumeroempleado.setCellValue(rs.getString("folio"));

                }
                numFilaDatosRfcDiferente++;
                

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

        ///////////////TERMINA RFC NO CORRESPONDE
        /////////////////////DUPLICADOS
        try {
            ps = con.prepareStatement(sqlduplicados);
            ps.setString(1, mov.getNumeroQuincena());
            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount();

            while (rs.next()) {

                Row filaDatosDuplicados = hojaduplicados.createRow(numFilaDatosDuplicados);
                for (int a = 0; a < numCol; a++) {

                    Cell CeldaNombre = filaDatosDuplicados.createCell(0);
                    CeldaNombre.setCellStyle(datosEstilo);
                    CeldaNombre.setCellValue(rs.getString("nombre"));

                    Cell CeldaPlantel = filaDatosDuplicados.createCell(1);
                    CeldaPlantel.setCellStyle(datosEstilo);
                    CeldaPlantel.setCellValue(rs.getString("plantel"));

                    Cell CeldaImporte = filaDatosDuplicados.createCell(2);
                    CeldaImporte.setCellStyle(datosEstilo);
                    CeldaImporte.setCellValue(rs.getInt("folio"));

                    Cell CeldaFacore = filaDatosDuplicados.createCell(3);
                    CeldaFacore.setCellStyle(datosEstiloMoneda);
                    CeldaFacore.setCellValue(rs.getDouble("abono"));

                    Cell CeldaDatos = filaDatosDuplicados.createCell(4);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString("numeroempleado"));
                    
                    Cell CeldaVeces= filaDatosDuplicados.createCell(5);
                    CeldaVeces.setCellStyle(datosEstilo);
                    CeldaVeces.setCellValue(rs.getString("veces"));

                }
                
                     
                numFilaDatosDuplicados++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasCompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /////////////TERMINA DUPLICADOS
        
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guarda Archivo"); //name for chooser
            FileFilter filter = new FileNameExtensionFilter("Excel", "xlsx"); //filter to show only that
            fileChooser.setAcceptAllFileFilterUsed(true); //to show or not all other files
            fileChooser.addChoosableFileFilter(filter);
            File f = new File(new File("FonsarComparativoPrestamos" + qnaactual + ".xlsx").getCanonicalPath());
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

            /*FileOutputStream fileout = new FileOutputStream("ComparativoPrestamos" + qnaactual + ".xlsx");
            book.write(fileout);
            fileout.close();*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CompararExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
