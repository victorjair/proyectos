/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiar;

import Controlador.CtrlUsuario;
import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import View.frmLogin;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author victor
 */
public class FIAR {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //frm menu = new frmMenuPrincipal();
        //menu.setVisible(true);
        //frmEstadoCuenta menu = new frmEstadoCuenta();
        //menu.setVisible(true);
        try{
        for(javax.swing.UIManager.LookAndFeelInfo info: javax.swing.UIManager.getInstalledLookAndFeels())
        {
        if("Nimbus".equals(info.getName())){
        
        javax.swing.UIManager.setLookAndFeel(info.getClassName());
        }
        
        }
            
       } catch (ClassNotFoundException ex) {
            Logger.getLogger(FIAR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FIAR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FIAR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FIAR.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
         Usuario usuarioPersona = new Usuario();
         //Usuario mod = new Usuario();
         ConsultasUsuario modC = new ConsultasUsuario();
         frmLogin frmLogin = new frmLogin();
         CtrlUsuario ctrl = new CtrlUsuario(usuarioPersona, modC, frmLogin);
          
         // frmLogin login = new frmLogin();
          frmLogin.setVisible(true);
        //frmMenuPrincipal menu = new frmMenuPrincipal();
        //menu.setVisible(true);
        // TODO code application logic here
    }
    
}
