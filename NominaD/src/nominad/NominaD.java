/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nominad;

import Controlador.CtrlEmpleado;
import Modelo.ConsultaEmpleado;
import Modelo.ConsultaPension;
import Modelo.ConsultasExportar;
import Modelo.Empleado;
import Modelo.Issste;
import Modelo.Pension;
import Modelo.Prestamos;
import Modelo.Reporte;
import Modelo.SalarioMinimo;
import Modelo.TablaISR;
import Vista.MenuNotaANt;

/**
 *
 * @author victor
 */
public class NominaD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         Empleado empleado = new Empleado();
         SalarioMinimo minimo = new SalarioMinimo();
         ConsultaEmpleado modC = new ConsultaEmpleado();
         MenuNotaANt menunota = new MenuNotaANt();
         TablaISR isrtabla = new TablaISR();
         ConsultasExportar consultaReporte = new ConsultasExportar();

         Issste cuotas  = new Issste();
         ConsultaPension consultapension = new ConsultaPension();
         Pension pension = new Pension();
         Prestamos prestamo = new Prestamos();
         Reporte reporte = new Reporte();
        
         CtrlEmpleado ctrl = new CtrlEmpleado(empleado, modC, menunota,minimo,isrtabla,cuotas,
                 pension,consultapension,prestamo,reporte,consultaReporte);
         menunota.setVisible(true);
    }
    
}
