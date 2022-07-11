/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Afiliado;
import Modelo.Beneficiario;
import Modelo.ConsultasAfiliados;
import View.frmAfiliado;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author victor
 */
public class CtrlAfiliado implements ActionListener {

    private Afiliado mod;
    private Beneficiario modB;
    private ConsultasAfiliados modC;
    private frmAfiliado frmPersona;

    public CtrlAfiliado(Afiliado mod, ConsultasAfiliados modc, frmAfiliado frmPersona) {

        this.frmPersona = frmPersona;
        this.mod = mod;
        this.modC = modc;
        this.frmPersona.BtnAlta.addActionListener(this);
        this.frmPersona.btnBuscar.addActionListener(this);
        this.frmPersona.btnBuscarNomina.addActionListener(this);
       
        this.frmPersona.btnEliminar.addActionListener(this);
        this.frmPersona.btnModificar.addActionListener(this);
        this.frmPersona.btnLimpiar.addActionListener(this);
        this.frmPersona.btnAltaBeneficiario.addActionListener(this);
        this.frmPersona.btnEliminaBeneficiario.addActionListener(this);

    }

    public void iniciar() {

     //   frmPersona.setTitle("Afiliados Etiqueta");

    }

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int numerobeneficiario;
        Double filatabla = 0.0;
        Double total = 0.0;
        Double totalmasactual = 0.0;
        int filaId = 0;

        if (e.getSource() == frmPersona.btnEliminaBeneficiario) {
            
            
            Beneficiario modB = new Beneficiario();
            DefaultTableModel modeloeliminar = (DefaultTableModel) frmPersona.jTBeneficiarios.getModel();
            modB.setId(String.valueOf(modeloeliminar.getValueAt(frmPersona.jTBeneficiarios.getSelectedRow(), 4)));
            if (modC.eliminarbeneficiario(modB)) {

                JOptionPane.showMessageDialog(null, "Beneficiario Eliminados");
                //DefaultTableModel modelocolocar = (DefaultTableModel) frmPersona.jTBeneficiarios.getModel();
                modeloeliminar.setRowCount(0);
                ArrayList Beneficiarios = new ArrayList();
                Beneficiarios = modC.buscarbeneficiario(mod);

                Object[] filaeliminar = new Object[modeloeliminar.getColumnCount()];

                Iterator it = Beneficiarios.iterator();
                while (it.hasNext()) {

                    Object objeto = it.next();
                    Beneficiario benef = (Beneficiario) objeto;
                    //System.out.println(benef.getNombre());
                    filaeliminar[0] = benef.getNombre();
                    filaeliminar[1] = benef.getPorcentaje();
                    filaeliminar[2] = benef.getParentesco();
                    filaeliminar[3] = benef.getFechanacimiento();
                    filaeliminar[4] = benef.getId();
                    filaeliminar[5] = benef.getPrioridadBeneficiario();
                    modeloeliminar.addRow(filaeliminar);
                }

            } else {

                JOptionPane.showMessageDialog(null, "Beneficiario No eliminado Intente Más tarde");

            }
        }

        if (e.getSource() == frmPersona.btnAltaBeneficiario) {
            Date dateverificar;
            dateverificar = frmPersona.fecha1.getDate();
            
               if (frmPersona.txtNombre1.getText().isEmpty()
                    || frmPersona.txtPor1.getText().isEmpty()
                    || dateverificar == null
            
                                       ) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
            
            
            Beneficiario modB = new Beneficiario();
            modB.setNombre(frmPersona.txtNombre1.getText());
            modB.setParentesco(String.valueOf(frmPersona.cbParen1.getSelectedItem()));
            modB.setRfcAfiliado(frmPersona.txtRfc.getText());
            String formato = frmPersona.fecha1.getDateFormatString();
            Date date = frmPersona.fecha1.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            modB.setFechanacimiento(String.valueOf(sdf.format(date)));
            modB.setPorcentaje(Double.parseDouble(frmPersona.txtPor1.getText()));
            modB.setPrioridadBeneficiario(frmPersona.jTBeneficiarios.getRowCount() + 1);
            if (frmPersona.jTBeneficiarios.getRowCount() > 0) {

                for (int i = 0; i < frmPersona.jTBeneficiarios.getRowCount(); i++) {

                    filatabla = Double.parseDouble(frmPersona.jTBeneficiarios.getValueAt(i, 1).toString());
                    total += filatabla;

                }

                totalmasactual = total + Double.parseDouble(frmPersona.txtPor1.getText());

            }

            //modB.setFechanacimiento(String.valueOf(frmPersona.fecha1.getDate()));
            if (frmPersona.txtRfc.getText().isEmpty() || frmPersona.txtNombre1.getText().isEmpty()
                    || frmPersona.txtPor1.getText().isEmpty()) {
            } else {

                if (totalmasactual > 100) {
                    JOptionPane.showMessageDialog(null, "Porcentaje es mayor 0 menor a 100 % ajuste los datos");

                } else {
                    if (modC.registrarbeneficiario(modB)) {

                        DefaultTableModel modeloinsertar = (DefaultTableModel) frmPersona.jTBeneficiarios.getModel();
                        modeloinsertar.setRowCount(0);
                        ArrayList Beneficiarios = new ArrayList();
                        Beneficiarios = modC.buscarbeneficiario(mod);

                        Object[] filainsertar = new Object[modeloinsertar.getColumnCount()];

                        Iterator it = Beneficiarios.iterator();
                        while (it.hasNext()) {

                            Object objeto = it.next();
                            Beneficiario benef = (Beneficiario) objeto;
                            //System.out.println(benef.getNombre());
                            filainsertar[0] = benef.getNombre();
                            filainsertar[1] = benef.getPorcentaje();
                            filainsertar[2] = benef.getParentesco();
                            filainsertar[3] = benef.getFechanacimiento();
                            filainsertar[4] = benef.getId();
                            filainsertar[5] = benef.getPrioridadBeneficiario();
                            modeloinsertar.addRow(filainsertar);
                        }

                        JOptionPane.showMessageDialog(null, "Beneficiario Guardado");
                        limpiarbeneficiario();

                    } else {
                        JOptionPane.showMessageDialog(null, "Beneficiario No Guardado Intente Mas Tarde");
                        limpiarbeneficiario();

                    }

                }
            }
             
             }
        }

        if (e.getSource() == frmPersona.BtnAlta) {

            if (frmPersona.txtCategoria.getText().isEmpty()
                    || frmPersona.txtCorreo.getText().isEmpty()
                    || frmPersona.txtCelular.getText().isEmpty()
                    || frmPersona.txtDireccion.getText().isEmpty()
                    || frmPersona.txtEmpleado.getText().isEmpty()
                    || frmPersona.txtNombre.getText().isEmpty()
                    || frmPersona.txtPlantel.getText().isEmpty()
                    || frmPersona.txtPorcentaje.getText().isEmpty()
                    || frmPersona.txtQnaAfiliacion.getText().isEmpty()
                    || frmPersona.txtQnaDescuento.getText().isEmpty()
                    || frmPersona.txtRfc.getText().isEmpty()
                    || frmPersona.txtSueldo.getText().isEmpty()
                    || frmPersona.txtTelefono.getText().isEmpty()
                    || frmPersona.txtFechaAfiliacion.getText().isEmpty()
                    || frmPersona.txtCorreo.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {
                mod.setPuesto(frmPersona.txtCategoria.getText());
                mod.setCelular(frmPersona.txtCelular.getText());
                mod.setCorreo(frmPersona.txtCorreo.getText());
                mod.setDireccion(frmPersona.txtDireccion.getText());
                mod.setNumeroEmpleado(frmPersona.txtEmpleado.getText());
                mod.setNombre(frmPersona.txtNombre.getText());
                mod.setPlantel(frmPersona.txtPlantel.getText());
                mod.setPorcentajeAhorro(Integer.parseInt(frmPersona.txtPorcentaje.getText()));
                mod.setQnaAfiliacion(frmPersona.txtQnaAfiliacion.getText());
                mod.setQnaDescuento(frmPersona.txtQnaDescuento.getText());
                mod.setRfc(frmPersona.txtRfc.getText());
                mod.setSueldoBase(Float.parseFloat(frmPersona.txtSueldo.getText()));
                mod.setTelefono(frmPersona.txtTelefono.getText());
                mod.setFechaAfiliacion(frmPersona.txtFechaAfiliacion.getText());
                if (modC.registrar(mod)) {

                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    limpiar();

                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Guardado Intente Mas Tarde");
                    limpiar();

                }

            }

        }

        if (e.getSource() == frmPersona.btnModificar) {
            if (frmPersona.txtCategoria.getText().isEmpty()
                    || frmPersona.txtCorreo.getText().isEmpty()
                    || frmPersona.txtCelular.getText().isEmpty()
                    || frmPersona.txtDireccion.getText().isEmpty()
                    || frmPersona.txtEmpleado.getText().isEmpty()
                    || frmPersona.txtNombre.getText().isEmpty()
                    || frmPersona.txtPlantel.getText().isEmpty()
                    || frmPersona.txtPorcentaje.getText().isEmpty()
                    || frmPersona.txtQnaAfiliacion.getText().isEmpty()
                    || frmPersona.txtQnaDescuento.getText().isEmpty()
                    || frmPersona.txtRfc.getText().isEmpty()
                    || frmPersona.txtSueldo.getText().isEmpty()
                    || frmPersona.txtTelefono.getText().isEmpty()
                    || frmPersona.txtFechaAfiliacion.getText().isEmpty()
                    || frmPersona.txtCorreo.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Faltan Campos Ogligatorios por llenar");

            } else {

                mod.setPuesto(frmPersona.txtCategoria.getText());
                mod.setCelular(frmPersona.txtCelular.getText());
                mod.setCorreo(frmPersona.txtCorreo.getText());
                mod.setDireccion(frmPersona.txtDireccion.getText());
                mod.setNumeroEmpleado(frmPersona.txtEmpleado.getText());
                mod.setNombre(frmPersona.txtNombre.getText());
                mod.setPlantel(frmPersona.txtPlantel.getText());
                mod.setPorcentajeAhorro(Float.parseFloat(frmPersona.txtPorcentaje.getText()));
                mod.setQnaAfiliacion(frmPersona.txtQnaAfiliacion.getText());
                mod.setQnaDescuento(frmPersona.txtQnaDescuento.getText());
                mod.setRfc(frmPersona.txtRfc.getText());
                mod.setSueldoBase(Float.parseFloat(frmPersona.txtSueldo.getText()));
                mod.setTelefono(frmPersona.txtTelefono.getText());
                mod.setFechaAfiliacion(frmPersona.txtFechaAfiliacion.getText());
                if (modC.modificar(mod)) {
                    JOptionPane.showMessageDialog(null, "Registro Modificado");
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Registro No Modificado Intente Mas Tarde");
                    limpiar();
                }

        }

        }

        if (e.getSource() == frmPersona.btnEliminar) {
            
            if (frmPersona.txtRfc.getText().isEmpty())
            {   

                JOptionPane.showMessageDialog(null, "Faltan Campo RFC");

            } else {
                
            Object [] baja = {"Fallecimiento","Retiro Voluntario","Enfermedad","Jubilacion","Baja Temporal"};
            
            Object opcion = JOptionPane.showInputDialog(null,"Selecciona el motivo de baja","Elegir"
             ,JOptionPane.QUESTION_MESSAGE,null,baja,baja[0]
            );
                    
                    
                    
                    
            mod.setMotivoBaja(String.valueOf(opcion));
            mod.setRfc(frmPersona.txtRfc.getText());
            mod.setFechaAfiliacion(frmPersona.txtFechaAfiliacion.getText());

            if (modC.eliminar(mod)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Registro No Eliminado Intente Mas Tarde");
                limpiar();
            }
        }

        }
        
         if (e.getSource() == frmPersona.btnBuscarNomina) {
          
            if (frmPersona.txtNombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Introduce el NOMBRE");

            } else {
                 // mod.setRfc(frmPersona.txtRfc.getText());
                  mod.setNombre(frmPersona.txtNombre.getText());

                if (modC.buscarnomina(mod)) {
                    frmPersona.txtCategoria.setText(mod.getPuesto());
                   // frmPersona.txtCelular.setText(mod.getCelular());
                   // frmPersona.txtCorreo.setText(mod.getCorreo());
                   // frmPersona.txtDireccion.setText(mod.getDireccion());
                    frmPersona.txtEmpleado.setText(mod.getNumeroEmpleado());
                    //frmPersona.txtNombre.setText(mod.getNombre());
                    frmPersona.txtPlantel.setText(mod.getPlantel());
                  //  frmPersona.txtPorcentaje.setText(String.valueOf(mod.getPorcentajeAhorro()));
                    frmPersona.txtQnaAfiliacion.setText(mod.getQnaAfiliacion());
                    frmPersona.txtQnaDescuento.setText(mod.getQnaDescuento());
                    frmPersona.txtRfc.setText(mod.getRfc());
                    frmPersona.txtSueldo.setText(String.valueOf(mod.getSueldoBase()));
                  //  frmPersona.txtTelefono.setText(mod.getTelefono());

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro el afiliado");
                    limpiar();
                }
                
                  if (modC.completarquincenaactual(mod)) {
                    frmPersona.txtQnaAfiliacion.setText(mod.getQnaAfiliacion());
                     
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro el afiliado");
                
                }
                if (modC.completarquincena(mod)) {
                    frmPersona.txtQnaDescuento.setText(mod.getQnaDescuento());
                     
                }else{
                    JOptionPane.showMessageDialog(null, "No se encontro el afiliado");
                
                }
                
                DefaultTableModel modelo = (DefaultTableModel) frmPersona.jTBeneficiarios.getModel();
                modelo.setRowCount(0);
                ArrayList Beneficiarios = new ArrayList();
                Beneficiarios = modC.buscarbeneficiario(mod);

                Object[] fila = new Object[modelo.getColumnCount()];

                Iterator it = Beneficiarios.iterator();
                while (it.hasNext()) {

                    Object objeto = it.next();
                    Beneficiario benef = (Beneficiario) objeto;
                    //System.out.println(benef.getNombre());
                    fila[0] = benef.getNombre();
                    fila[1] = benef.getPorcentaje();
                    fila[2] = benef.getParentesco();
                    fila[3] = benef.getFechanacimiento();
                    fila[4] = benef.getId();
                    fila[5] = benef.getPrioridadBeneficiario();
                    modelo.addRow(fila);
                }
                /*for(int i=0;i<Beneficiarios.size();i++)
            {
            fila[0] = Beneficiarios.;
            fila[1] = "por";
            fila[2] = "par";       
            fila[3] = "fecha";
            fila[4] = "id";      
            fila[5] = "numero";      
            modelo.addRow(fila);
            } */

            }

        }

        if (e.getSource() == frmPersona.btnBuscar) {
          
            if (frmPersona.txtNombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Introduce el NOMBRE");

            } else {
                 // mod.setRfc(frmPersona.txtRfc.getText());
                  mod.setNombre(frmPersona.txtNombre.getText());

                if (modC.buscar(mod)) {
                    frmPersona.txtCategoria.setText(mod.getPuesto());
                    frmPersona.txtCelular.setText(mod.getCelular());
                    frmPersona.txtCorreo.setText(mod.getCorreo());
                    frmPersona.txtDireccion.setText(mod.getDireccion());
                    frmPersona.txtEmpleado.setText(mod.getNumeroEmpleado());
                    //frmPersona.txtNombre.setText(mod.getNombre());
                    frmPersona.txtPlantel.setText(mod.getPlantel());
                    frmPersona.txtPorcentaje.setText(String.valueOf(mod.getPorcentajeAhorro()));
                    frmPersona.txtQnaAfiliacion.setText(mod.getQnaAfiliacion());
                    frmPersona.txtQnaDescuento.setText(mod.getQnaDescuento());
                    frmPersona.txtRfc.setText(mod.getRfc());
                    frmPersona.txtSueldo.setText(String.valueOf(mod.getSueldoBase()));
                    frmPersona.txtTelefono.setText(mod.getTelefono());

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro el afiliado");
                    limpiar();
                }
                DefaultTableModel modelo = (DefaultTableModel) frmPersona.jTBeneficiarios.getModel();
                modelo.setRowCount(0);
                ArrayList Beneficiarios = new ArrayList();
                Beneficiarios = modC.buscarbeneficiario(mod);

                Object[] fila = new Object[modelo.getColumnCount()];

                Iterator it = Beneficiarios.iterator();
                while (it.hasNext()) {

                    Object objeto = it.next();
                    Beneficiario benef = (Beneficiario) objeto;
                    //System.out.println(benef.getNombre());
                    fila[0] = benef.getNombre();
                    fila[1] = benef.getPorcentaje();
                    fila[2] = benef.getParentesco();
                    fila[3] = benef.getFechanacimiento();
                    fila[4] = benef.getId();
                    fila[5] = benef.getPrioridadBeneficiario();
                    modelo.addRow(fila);
                }
                /*for(int i=0;i<Beneficiarios.size();i++)
            {
            fila[0] = Beneficiarios.;
            fila[1] = "por";
            fila[2] = "par";       
            fila[3] = "fecha";
            fila[4] = "id";      
            fila[5] = "numero";      
            modelo.addRow(fila);
            } */

            }

        }

        if (e.getSource() == frmPersona.btnLimpiar) {

            limpiar();
        }

    }

    public void limpiar() {
        frmPersona.txtCategoria.setText(null);
        frmPersona.txtCelular.setText(null);
        frmPersona.txtCorreo.setText(null);
        frmPersona.txtDireccion.setText(null);
        frmPersona.txtEmpleado.setText(null);
        frmPersona.txtNombre.setText(null);
        frmPersona.txtPlantel.setText(null);
        frmPersona.txtPorcentaje.setText(null);
        frmPersona.txtQnaAfiliacion.setText(null);
        frmPersona.txtQnaDescuento.setText(null);
        frmPersona.txtRfc.setText(null);
        frmPersona.txtSueldo.setText(null);
        frmPersona.txtTelefono.setText(null);

    }

    public void limpiarbeneficiario() {
        frmPersona.txtNombre1.setText(null);
        frmPersona.txtPor1.setText(null);
        frmPersona.fecha1.setCalendar(null);

    }

}
