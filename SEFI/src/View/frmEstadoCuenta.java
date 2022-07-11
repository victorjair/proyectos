/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author victor
 */
public class frmEstadoCuenta extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmEstadoCuenta
     */
    public frmEstadoCuenta() {
        initComponents();
        java.util.Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy");
        txtperiodo.setText(formato.format(fecha));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Rfc = new javax.swing.JLabel();
        txtRfc = new javax.swing.JTextField();
        btnEdoCuentaAhorro = new javax.swing.JButton();
        btnEdoCuentaPrestamos = new javax.swing.JButton();
        btnEdoCuentaDesglo = new javax.swing.JButton();
        btnHojaNoAdeudo = new javax.swing.JButton();
        btnPagoAnticipado = new javax.swing.JButton();
        btnActualizarSaldo = new javax.swing.JButton();
        txtperiodo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnEdoCuentaFias = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Reportes Persona");

        Rfc.setText("RFC");

        btnEdoCuentaAhorro.setText("Edo. Cuenta Ahorros");

        btnEdoCuentaPrestamos.setText("Edo. Cuenta Prestamos");
        btnEdoCuentaPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdoCuentaPrestamosActionPerformed(evt);
            }
        });

        btnEdoCuentaDesglo.setText("Edo. Cuenta Ahorros Desglosado");

        btnHojaNoAdeudo.setText("HojaNoAdeudo");
        btnHojaNoAdeudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHojaNoAdeudoActionPerformed(evt);
            }
        });

        btnPagoAnticipado.setText("Pago Anticipado");

        btnActualizarSaldo.setText("Actualizar Saldo");

        txtperiodo.setEditable(false);

        jLabel1.setText("Año");

        btnEdoCuentaFias.setText("Edo De Cuenta Fias");

        jLabel2.setText("NOMBRE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnPagoAnticipado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEdoCuentaDesglo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEdoCuentaAhorro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEdoCuentaPrestamos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHojaNoAdeudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnActualizarSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Rfc, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtRfc, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtperiodo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(85, 85, 85))
                    .addComponent(btnEdoCuentaFias, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Rfc)
                    .addComponent(txtRfc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtperiodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdoCuentaAhorro)
                    .addComponent(btnEdoCuentaPrestamos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdoCuentaDesglo)
                    .addComponent(btnHojaNoAdeudo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPagoAnticipado)
                    .addComponent(btnActualizarSaldo))
                .addGap(18, 18, 18)
                .addComponent(btnEdoCuentaFias)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHojaNoAdeudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHojaNoAdeudoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHojaNoAdeudoActionPerformed

    private void btnEdoCuentaPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdoCuentaPrestamosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEdoCuentaPrestamosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Rfc;
    public javax.swing.JButton btnActualizarSaldo;
    public javax.swing.JButton btnEdoCuentaAhorro;
    public javax.swing.JButton btnEdoCuentaDesglo;
    public javax.swing.JButton btnEdoCuentaFias;
    public javax.swing.JButton btnEdoCuentaPrestamos;
    public javax.swing.JButton btnHojaNoAdeudo;
    public javax.swing.JButton btnPagoAnticipado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JTextField txtNombre;
    public javax.swing.JTextField txtRfc;
    public javax.swing.JTextField txtperiodo;
    // End of variables declaration//GEN-END:variables
}