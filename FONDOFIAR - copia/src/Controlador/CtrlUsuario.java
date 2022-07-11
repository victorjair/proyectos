/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasUsuario;
import Modelo.Usuario;
import View.frmLogin;
import View.frmMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class CtrlUsuario implements ActionListener,KeyListener {
    public String UsuarioActual;
    Usuario mod;
    ConsultasUsuario modC;
    frmLogin frmLogin;

    public CtrlUsuario(Usuario mod, ConsultasUsuario modC, frmLogin frmLogin) {
        this.mod = mod;
        this.modC = modC;
        this.frmLogin = frmLogin;
        this.frmLogin.btnAcceder.addActionListener(this);
        this.frmLogin.btnAcceder.addKeyListener(this);
        //this.frmLogin.addKeyListener(this);
        //this.frmLogin.txtPassword.addKeyListener(this);
        //this.frmLogin.btnAcceder.addKeyListener(this);
       
    }

    /**
     *
     * @param e
     */
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
          
        
        
        if (e.getSource() == frmLogin.btnAcceder) {
            mod.setUsuario(frmLogin.txtUsuario.getText());
            mod.setPassword(frmLogin.txtPassword.getText());
            // JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorrectos");
              
            if (modC.verificausuario(mod)) {
               //JOptionPane.showMessageDialog(null, "Usuario Correcto Pasele");
                frmMenuPrincipal menu = new frmMenuPrincipal();
                menu.setVisible(true);
                frmLogin.dispose();
           
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorrectos");
                

            }
            
        }

    }
    
  @Override
    public void keyTyped(KeyEvent ke) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
          mod.setUsuario(frmLogin.txtUsuario.getText());
            mod.setPassword(frmLogin.txtPassword.getText());
            // JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorrectos");
              
            if (modC.verificausuario(mod)) {
               //JOptionPane.showMessageDialog(null, "Usuario Correcto Pasele");
                frmMenuPrincipal menu = new frmMenuPrincipal();
                menu.setVisible(true);
                frmLogin.dispose();
           
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorrectos");
                

            }
      //JOptionPane.showMessageDialog(null, "Presionaste Enter");
              
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    

}
