/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controlador.CtrlMenuPrincipal;

/**
 *
 * @author victor
 */
public class frmMenuPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form MenuPrincipal
     */
    public frmMenuPrincipal() {
        initComponents();
        CtrlMenuPrincipal mod = new CtrlMenuPrincipal(this);
        

    }

    

    
    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        Escritorio = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        MenuBancos = new javax.swing.JMenuItem();
        MenuTasas = new javax.swing.JMenuItem();
        MenuSalario = new javax.swing.JMenuItem();
        MenuAfiliacion = new javax.swing.JMenu();
        MenuIAfiliado = new javax.swing.JMenuItem();
        MenuSindicato = new javax.swing.JMenuItem();
        MenuPrestamos = new javax.swing.JMenu();
        MenuIPrestamos = new javax.swing.JMenuItem();
        MenuPrestamosSindicatos = new javax.swing.JMenuItem();
        MenuMocPrestamos = new javax.swing.JMenu();
        MenuMovPrestamos = new javax.swing.JMenuItem();
        MenuMovAhorros = new javax.swing.JMenuItem();
        MenuImportar = new javax.swing.JMenu();
        MenuImportarI = new javax.swing.JMenuItem();
        MenuExportar = new javax.swing.JMenuItem();
        MenuComparar = new javax.swing.JMenuItem();
        MenuCalcularRetiro = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        MenuEdosCuenta = new javax.swing.JMenuItem();
        MenuEstadisticas = new javax.swing.JMenuItem();
        MenuTrimestral = new javax.swing.JMenuItem();
        MenuDesglosada = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        MenuReporteMov = new javax.swing.JMenuItem();
        MenuGlobales = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        MenuPlazoFijo = new javax.swing.JMenuItem();
        MenuCerrarSesion = new javax.swing.JMenu();
        MenuCerrar = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEMA SEFI");
        setName("framePrincipal"); // NOI18N

        Escritorio.setBackground(new java.awt.Color(204, 204, 204));
        Escritorio.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SISTEMA SEFI");

        Escritorio.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout EscritorioLayout = new javax.swing.GroupLayout(Escritorio);
        Escritorio.setLayout(EscritorioLayout);
        EscritorioLayout.setHorizontalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscritorioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(461, Short.MAX_VALUE))
        );
        EscritorioLayout.setVerticalGroup(
            EscritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscritorioLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(384, Short.MAX_VALUE))
        );

        jMenuBar2.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jMenuBar2.setForeground(new java.awt.Color(51, 102, 255));
        jMenuBar2.setAutoscrolls(true);
        jMenuBar2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuBar2.setName(""); // NOI18N

        jMenu3.setText("Captura");

        MenuBancos.setText("Bancos");
        MenuBancos.setEnabled(false);
        MenuBancos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuBancosActionPerformed(evt);
            }
        });
        jMenu3.add(MenuBancos);

        MenuTasas.setText("Tasas");
        MenuTasas.setEnabled(false);
        MenuTasas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuTasasActionPerformed(evt);
            }
        });
        jMenu3.add(MenuTasas);

        MenuSalario.setText("Salario Mínimo");
        MenuSalario.setEnabled(false);
        jMenu3.add(MenuSalario);

        jMenuBar2.add(jMenu3);

        MenuAfiliacion.setText("Afiliacion");
        MenuAfiliacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuAfiliacionMouseClicked(evt);
            }
        });
        MenuAfiliacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAfiliacionActionPerformed(evt);
            }
        });
        MenuAfiliacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuAfiliacionKeyPressed(evt);
            }
        });

        MenuIAfiliado.setText("Movimientos Afiliados");
        MenuIAfiliado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuIAfiliadoActionPerformed(evt);
            }
        });
        MenuAfiliacion.add(MenuIAfiliado);
        MenuIAfiliado.getAccessibleContext().setAccessibleParent(MenuAfiliacion);

        MenuSindicato.setText("Movimientos Sindicatos");
        MenuAfiliacion.add(MenuSindicato);

        jMenuBar2.add(MenuAfiliacion);

        MenuPrestamos.setText("Prestamos");
        MenuPrestamos.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                MenuPrestamosMenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
        });
        MenuPrestamos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                MenuPrestamosAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        MenuPrestamos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuPrestamosMouseClicked(evt);
            }
        });
        MenuPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuPrestamosActionPerformed(evt);
            }
        });
        MenuPrestamos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuPrestamosKeyPressed(evt);
            }
        });

        MenuIPrestamos.setText("Alta Prestamos");
        MenuIPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuIPrestamosActionPerformed(evt);
            }
        });
        MenuPrestamos.add(MenuIPrestamos);

        MenuPrestamosSindicatos.setText("Prestamos Sindicatos");
        MenuPrestamos.add(MenuPrestamosSindicatos);

        jMenuBar2.add(MenuPrestamos);

        MenuMocPrestamos.setText("Movimientos");
        MenuMocPrestamos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuMocPrestamosMouseClicked(evt);
            }
        });
        MenuMocPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuMocPrestamosActionPerformed(evt);
            }
        });

        MenuMovPrestamos.setText("Movimientos Préstamos");
        MenuMovPrestamos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuMovPrestamosMouseClicked(evt);
            }
        });
        MenuMovPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuMovPrestamosActionPerformed(evt);
            }
        });
        MenuMocPrestamos.add(MenuMovPrestamos);

        MenuMovAhorros.setText("Movimientos Ahorros");
        MenuMovAhorros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuMovAhorrosMouseClicked(evt);
            }
        });
        MenuMovAhorros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuMovAhorrosActionPerformed(evt);
            }
        });
        MenuMocPrestamos.add(MenuMovAhorros);

        jMenuBar2.add(MenuMocPrestamos);

        MenuImportar.setText("Utilerias");

        MenuImportarI.setText("Importar Informacion");
        MenuImportarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuImportarIActionPerformed(evt);
            }
        });
        MenuImportar.add(MenuImportarI);

        MenuExportar.setText("Exportar Informacion");
        MenuExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuExportarActionPerformed(evt);
            }
        });
        MenuImportar.add(MenuExportar);

        MenuComparar.setText("Comparar Informacion");
        MenuComparar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuCompararActionPerformed(evt);
            }
        });
        MenuImportar.add(MenuComparar);

        MenuCalcularRetiro.setText("Calcular Retiro");
        MenuCalcularRetiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuCalcularRetiroActionPerformed(evt);
            }
        });
        MenuImportar.add(MenuCalcularRetiro);

        jMenuBar2.add(MenuImportar);

        jMenu9.setText("Reportes");

        MenuEdosCuenta.setText("Reporte Estados de Cuenta");
        MenuEdosCuenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuEdosCuentaMouseClicked(evt);
            }
        });
        MenuEdosCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuEdosCuentaActionPerformed(evt);
            }
        });
        jMenu9.add(MenuEdosCuenta);

        MenuEstadisticas.setText("Estadisticas");
        MenuEstadisticas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuEstadisticasActionPerformed(evt);
            }
        });
        jMenu9.add(MenuEstadisticas);

        MenuTrimestral.setText("Reporte Trimestral Y Rendimientos");
        MenuTrimestral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuTrimestralActionPerformed(evt);
            }
        });
        jMenu9.add(MenuTrimestral);

        MenuDesglosada.setText("Recuperacion desglosada");
        MenuDesglosada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuDesglosadaActionPerformed(evt);
            }
        });
        jMenu9.add(MenuDesglosada);

        jMenuBar2.add(jMenu9);

        jMenu10.setText("+Reportes");

        MenuReporteMov.setText("Reportes  Movimientos");
        MenuReporteMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuReporteMovActionPerformed(evt);
            }
        });
        jMenu10.add(MenuReporteMov);

        MenuGlobales.setText("Reportes Globales");
        MenuGlobales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuGlobalesActionPerformed(evt);
            }
        });
        jMenu10.add(MenuGlobales);

        jMenuBar2.add(jMenu10);

        jMenu15.setText("Plazo Fijo");

        MenuPlazoFijo.setText("Inversiones");
        MenuPlazoFijo.setEnabled(false);
        MenuPlazoFijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuPlazoFijoActionPerformed(evt);
            }
        });
        jMenu15.add(MenuPlazoFijo);

        jMenuBar2.add(jMenu15);

        MenuCerrarSesion.setText("Usuario");
        MenuCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuCerrarSesionActionPerformed(evt);
            }
        });

        MenuCerrar.setText("Cerrar Sesion");
        MenuCerrarSesion.add(MenuCerrar);

        jMenuBar2.add(MenuCerrarSesion);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 923, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Escritorio, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 447, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Escritorio, javax.swing.GroupLayout.Alignment.TRAILING))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuAfiliacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuAfiliacionKeyPressed

// TODO add your handling code here:
    }//GEN-LAST:event_MenuAfiliacionKeyPressed

    private void MenuBancosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuBancosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuBancosActionPerformed

    private void MenuPrestamosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuPrestamosKeyPressed


    }//GEN-LAST:event_MenuPrestamosKeyPressed

    private void MenuPrestamosMenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_MenuPrestamosMenuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuPrestamosMenuKeyPressed

    private void MenuPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuPrestamosActionPerformed
        //getInstance();
        
    }//GEN-LAST:event_MenuPrestamosActionPerformed

    private void MenuAfiliacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAfiliacionActionPerformed
        // getInstanceAfiliado();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuAfiliacionActionPerformed

    private void MenuPrestamosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuPrestamosMouseClicked
       
    }//GEN-LAST:event_MenuPrestamosMouseClicked

    private void MenuAfiliacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuAfiliacionMouseClicked
        

    }//GEN-LAST:event_MenuAfiliacionMouseClicked

    private void MenuMocPrestamosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuMocPrestamosMouseClicked
        //Opcion menú movimientos 

        // TODO add your handling code here:
    }//GEN-LAST:event_MenuMocPrestamosMouseClicked

    private void MenuMovPrestamosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuMovPrestamosMouseClicked

// TODO add your handling code here:
    }//GEN-LAST:event_MenuMovPrestamosMouseClicked

    private void MenuMovPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuMovPrestamosActionPerformed
      
// TODO add your handling code here:
    }//GEN-LAST:event_MenuMovPrestamosActionPerformed

    private void MenuMovAhorrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuMovAhorrosMouseClicked

// TODO add your handling code here:
    }//GEN-LAST:event_MenuMovAhorrosMouseClicked

    private void MenuMovAhorrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuMovAhorrosActionPerformed
       
         // TODO add your handling code here:
    }//GEN-LAST:event_MenuMovAhorrosActionPerformed

    private void MenuEdosCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuEdosCuentaActionPerformed
       
// TODO add your handling code here:
    }//GEN-LAST:event_MenuEdosCuentaActionPerformed

    private void MenuEdosCuentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuEdosCuentaMouseClicked
       
       
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuEdosCuentaMouseClicked

    private void MenuImportarIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuImportarIActionPerformed
        

        // TODO add your handling code here:
    }//GEN-LAST:event_MenuImportarIActionPerformed

    private void MenuExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuExportarActionPerformed
      

        // TODO add your handling code here:
    }//GEN-LAST:event_MenuExportarActionPerformed

    private void MenuCompararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuCompararActionPerformed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuCompararActionPerformed

    private void MenuEstadisticasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuEstadisticasActionPerformed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuEstadisticasActionPerformed

    private void MenuReporteMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuReporteMovActionPerformed
       
    }//GEN-LAST:event_MenuReporteMovActionPerformed

    private void MenuDesglosadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuDesglosadaActionPerformed
      

// TODO add your handling code here:
    }//GEN-LAST:event_MenuDesglosadaActionPerformed

    private void MenuGlobalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuGlobalesActionPerformed
       

// TODO add your handling code here:
    }//GEN-LAST:event_MenuGlobalesActionPerformed

    private void MenuTrimestralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuTrimestralActionPerformed
       
// TODO add your handling code here:
    }//GEN-LAST:event_MenuTrimestralActionPerformed

    private void MenuTasasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuTasasActionPerformed
      
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuTasasActionPerformed

    private void MenuPlazoFijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuPlazoFijoActionPerformed
       

// TODO add your handling code here:
    }//GEN-LAST:event_MenuPlazoFijoActionPerformed

    private void MenuCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuCerrarSesionActionPerformed
         //this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuCerrarSesionActionPerformed

    private void MenuPrestamosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_MenuPrestamosAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuPrestamosAncestorAdded

    private void MenuMocPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuMocPrestamosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuMocPrestamosActionPerformed

    private void MenuIPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuIPrestamosActionPerformed
        // getInstance();
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuIPrestamosActionPerformed

    private void MenuIAfiliadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuIAfiliadoActionPerformed
       //getInstanceAfiliado();
// TODO add your handling code here:
    }//GEN-LAST:event_MenuIAfiliadoActionPerformed

    private void MenuCalcularRetiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuCalcularRetiroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuCalcularRetiroActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmMenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDesktopPane Escritorio;
    public javax.swing.JMenu MenuAfiliacion;
    public javax.swing.JMenuItem MenuBancos;
    public javax.swing.JMenuItem MenuCalcularRetiro;
    public javax.swing.JMenuItem MenuCerrar;
    public javax.swing.JMenu MenuCerrarSesion;
    public javax.swing.JMenuItem MenuComparar;
    public javax.swing.JMenuItem MenuDesglosada;
    public javax.swing.JMenuItem MenuEdosCuenta;
    public javax.swing.JMenuItem MenuEstadisticas;
    public javax.swing.JMenuItem MenuExportar;
    public javax.swing.JMenuItem MenuGlobales;
    public javax.swing.JMenuItem MenuIAfiliado;
    public javax.swing.JMenuItem MenuIPrestamos;
    public javax.swing.JMenu MenuImportar;
    public javax.swing.JMenuItem MenuImportarI;
    public javax.swing.JMenu MenuMocPrestamos;
    public javax.swing.JMenuItem MenuMovAhorros;
    public javax.swing.JMenuItem MenuMovPrestamos;
    public javax.swing.JMenuItem MenuPlazoFijo;
    public javax.swing.JMenu MenuPrestamos;
    public javax.swing.JMenuItem MenuPrestamosSindicatos;
    public javax.swing.JMenuItem MenuReporteMov;
    public javax.swing.JMenuItem MenuSalario;
    public javax.swing.JMenuItem MenuSindicato;
    public javax.swing.JMenuItem MenuTasas;
    public javax.swing.JMenuItem MenuTrimestral;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JMenu jMenu10;
    public javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu3;
    public javax.swing.JMenu jMenu9;
    public javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables
}
