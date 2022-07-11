/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nominac;

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
public class NominaC {
     // TODO code application logic here
          
   
   
    public static void main(String[] args) {
        // TODO code application logic here
         Empleado empleado = new Empleado();
         ConsultaEmpleado modC = new ConsultaEmpleado();
         MenuNota menunota = new MenuNota();
         CtrlEmpleado ctrl = new CtrlEmpleado(empleado, modC, menunota);
         menunota.setVisible(true);

    }
    
}
