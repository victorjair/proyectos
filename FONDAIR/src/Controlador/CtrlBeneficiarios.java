/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultasBeneficiario;
import Modelo.Beneficiario;
import View.frmBeneficiario;
import View.frmMenuPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author victor
 */
public class CtrlBeneficiarios implements ActionListener {
    
    private Beneficiario mod;
    private ConsultasBeneficiario modC;
    private frmBeneficiario frmBeneficiario;
    
    public CtrlBeneficiarios(Beneficiario mod, ConsultasBeneficiario modC, frmBeneficiario frmBeneficiario) {
        this.mod = mod;
        this.modC = modC;
        this.frmBeneficiario = frmBeneficiario;
        this.frmBeneficiario.btnAlta.addActionListener(this);
        this.frmBeneficiario.btnEliminar.addActionListener(this);
        this.frmBeneficiario.btnModificar.addActionListener(this);
    
    
    }
    
     public void actionPerformed(ActionEvent e) {
          
        if (e.getSource() == frmBeneficiario.btnAlta) {
            
        }
        
       
        if (e.getSource() == frmBeneficiario.btnEliminar) {
            
        }
        
        if (e.getSource() == frmBeneficiario.btnModificar) {
            
        }
          
     
     
     
     
     }
    
    
    
}
