/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 *
 * @author victor
 */
public class frmEstadisticas extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmEstadisticas
     */
    public frmEstadisticas() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cbperiodoinicial = new javax.swing.JComboBox<>();
        cbnumeroinicial = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbperiodofinal = new javax.swing.JComboBox<>();
        cbnumerofinal = new javax.swing.JComboBox<>();
        bntEnviar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Estadisticas");

        jLabel1.setText("Qna Inicial");

        cbperiodoinicial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025" }));
        cbperiodoinicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbperiodoinicialActionPerformed(evt);
            }
        });

        cbnumeroinicial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));
        cbnumeroinicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbnumeroinicialActionPerformed(evt);
            }
        });

        jLabel2.setText("Qna Final");

        cbperiodofinal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025" }));
        cbperiodofinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbperiodofinalActionPerformed(evt);
            }
        });

        cbnumerofinal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24" }));
        cbnumerofinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbnumerofinalActionPerformed(evt);
            }
        });

        bntEnviar.setText("Enviar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbperiodoinicial, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbnumeroinicial, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbperiodofinal, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbnumerofinal, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(bntEnviar))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbperiodoinicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbnumeroinicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbperiodofinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbnumerofinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(bntEnviar)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbnumeroinicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbnumeroinicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbnumeroinicialActionPerformed

    private void cbperiodoinicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbperiodoinicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbperiodoinicialActionPerformed

    private void cbperiodofinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbperiodofinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbperiodofinalActionPerformed

    private void cbnumerofinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbnumerofinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbnumerofinalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bntEnviar;
    public javax.swing.JComboBox<String> cbnumerofinal;
    public javax.swing.JComboBox<String> cbnumeroinicial;
    public javax.swing.JComboBox<String> cbperiodofinal;
    public javax.swing.JComboBox<String> cbperiodoinicial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
