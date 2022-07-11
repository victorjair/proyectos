/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nominab;
import Controlador.CtrlEmpleado;
import Modelo.ConsultaEmpleado;
import Modelo.Empleado;
import Vista.MenuNota;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
/**
 *
 * @author victor
 */
public class NominaB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
          try{
        for(javax.swing.UIManager.LookAndFeelInfo info: javax.swing.UIManager.getInstalledLookAndFeels())
        {
        if("Nimbus".equals(info.getName())){
        
        javax.swing.UIManager.setLookAndFeel(info.getClassName());
        }
        
        }
            
       } catch (ClassNotFoundException ex) {
            Logger.getLogger(NominaB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(NominaB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(NominaB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(NominaB.class.getName()).log(Level.SEVERE, null, ex);
        }
        Empleado empleado = new Empleado();
        ConsultaEmpleado modC = new ConsultaEmpleado();
        MenuNota menunota = new MenuNota();
        CtrlEmpleado ctrl = new CtrlEmpleado(empleado, modC, menunota);
        menunota.setVisible(true);
    }
    
}
