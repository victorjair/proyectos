/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.mysql.jdbc.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author victor
 */
public class ConsultasRecuperacionDesglosada extends Conexion{
    private String qnainicial;
    private String qnafinal;
    
    public void imprimerecuperaciondesglosadaxqna (RecuperacionDesglosada mov){
        qnainicial = mov.getPeriodoi()+ mov.getNumeroi();
        qnafinal = mov.getPeriodof()+   mov.getNumerof();
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        JasperReport reporte = null;
        String path = "src\\reportes\\RecuperacionDesglosadaXQna.jasper";
        Map parametro = new HashMap();
        parametro.put("Parameter1",qnainicial);
        parametro.put("Parameter2",qnafinal);
        // parametro.put("Parameter3",mov.getMovimiento());
       
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro ,conn);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
            

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(RecuperacionDesglosada.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    public void imprimerecuperaciondesglosadaglobal (RecuperacionDesglosada mov){
    qnainicial = mov.getPeriodoi()+ mov.getNumeroi();
        qnafinal = mov.getPeriodof()+   mov.getNumerof();
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        JasperReport reporte = null;
        String path = "src\\reportes\\RecuperacionDesglosadaGlobal.jasper";
        Map parametro = new HashMap();
        parametro.put("Parameter1",qnainicial);
        parametro.put("Parameter2",qnafinal);
        // parametro.put("Parameter3",mov.getMovimiento());
       
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro ,conn);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
            

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(RecuperacionDesglosada.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    public void imprimeprestamosxmes (RecuperacionDesglosada mov){
      qnainicial = mov.getPeriodoi()+ mov.getNumeroi();
        qnafinal = mov.getPeriodof()+   mov.getNumerof();
        Conexion con = new Conexion();
        Connection conn = con.getConexion();
        JasperReport reporte = null;
        String path = "src\\reportes\\PrestamosXMes.jasper";
        Map parametro = new HashMap();
        parametro.put("Parameter1",qnainicial);
        parametro.put("Parameter2",qnafinal);
        // parametro.put("Parameter3",mov.getMovimiento());
       
        try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(path, parametro ,conn);
            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);
            

            // TODO add your handling code here:
        } catch (JRException ex) {
            Logger.getLogger(RecuperacionDesglosada.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
