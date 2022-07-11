/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author victor
 */
public class ConsultasImportarAhorro extends Conexion {

    public static final String SEPARATOR = ",";
    public static final String QUOTE = "\"";

    private static String[] removeTrailingQuotes(String[] fields) {

        String result[] = new String[fields.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = fields[i].replaceAll("^" + QUOTE, "").replaceAll(QUOTE + "$", "");
        }
        return result;
    }

    public void importararchivoquincenal(ImportarAhorro mov) throws FileNotFoundException, IOException {
 

        Connection con = getConexion();
        String sqlprueba = "select * FROM  qnaahorrorecuperada  WHERE numeroquincena = ? and literal in ('CII','CIP') limit 1";
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        BufferedReader br = null;
        try {
            psVerifica = con.prepareStatement(sqlprueba);
            psVerifica.setString(1, mov.getNumeroQuincena());
            rsverifica = psVerifica.executeQuery();
            if (!rsverifica.next()) {
                

        try {

            br = new BufferedReader(new FileReader(mov.getRuta()));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                //  System.out.println(Arrays.toString(fields));

                fields = removeTrailingQuotes(fields);
                //System.out.println(Arrays.toString(fields));
                ///////////EMPIEZA LA INSERCION A LA BASE DE DATOS
                PreparedStatement ps = null;
                //Connection con = getConexion();
                String sql = "INSERT INTO qnaahorrorecuperada(rfc,nombre,plantel,facore,importe,numeroquincena,"
                        + "literal,numeroempleado)"
                        + "Values(?,?,?,?,"
                        + "?,?,?,?)";
                
                
                
                try {

                    ps = con.prepareStatement(sql);
                    ps.setString(1, fields[1].substring(0, 4) + "-" + fields[1].substring(4, 10));
                    ps.setString(2, fields[2]);
                    ps.setString(3, fields[3].replace(" ", ""));
                    ps.setString(4, fields[4]);
                    ps.setString(5, fields[5]);
                    ps.setString(6, mov.getNumeroQuincena());
                    ps.setString(7, "CIP");
                    ps.setString(8, fields[0].replace(" ", ""));
                    

                    ps.execute();
                    line = br.readLine();

                    //return true;
                } catch (SQLException e) {
                    System.err.println(e);
                    //return false;

                } /*finally {
                    try {
                        con.close();

                    } catch (SQLException e) {

                        System.err.println(e);

                    }

                }*/
            }

        } catch (Exception e) {

        }
        finally {
                    try {
                        con.close();

                    } catch (SQLException e) {

                        System.err.println(e);

                    }

                }
                

            } else {
                JOptionPane.showMessageDialog(null, "LA QUINCENA YA ESTA DADA DE ALTA,NO LA PUEDE REPETIR");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        

    }

    public void importararchivoquincenalprestamos(ImportarAhorro mov) throws FileNotFoundException, IOException {

        Connection con = getConexion();
        String sqlprueba = "select * FROM  qnarecuperacionrecuperada  WHERE numeroquincena = ? and movimiento in ('D') limit 1";
        PreparedStatement psVerifica = null;
        ResultSet rsverifica = null;
        BufferedReader br = null;

        try {
            psVerifica = con.prepareStatement(sqlprueba);
            psVerifica.setString(1, mov.getNumeroQuincena());
            rsverifica = psVerifica.executeQuery();
            if (!rsverifica.next()) {
                try {

                    br = new BufferedReader(new FileReader(mov.getRuta()));
                    String line = br.readLine();
                    while (null != line) {
                        String[] fields = line.split(SEPARATOR);
                        //  System.out.println(Arrays.toString(fields));

                        fields = removeTrailingQuotes(fields);
                        //System.out.println(Arrays.toString(fields));
                        ///////////EMPIEZA LA INSERCION A LA BASE DE DATOS
                        PreparedStatement ps = null;
                        PreparedStatement psactualiza = null;
                       PreparedStatement psactualizasaldo = null;
                       PreparedStatement psactualizastatus = null;
                       
                        //Connection con = getConexion();
                        String sql = "INSERT INTO qnarecuperacionrecuperada(numeroempleado,rfc,nombre,plantel,abono,folio,"
                                + "numeroquincena,movimiento)"
                                + "Values(?,?,?,?,"
                                + "?,?,?,?)";
                        
                        String sqlactualiza = "update prestamos set abonoparcial = (select ifnull(sum(abono),0) from "
                        + "qnarecuperacionrecuperada where "
                        + "	folio = ? and movimiento not in ('I')),sumaindebido = (select ifnull(sum(abono),0) from "
                        + "qnarecuperacionrecuperada where "
                        + "	folio = ? and movimiento  in ('I')) where folio = ?";
                        
                        String sqlactualizasaldo = "update prestamos set saldo =  IFNull(total,0) + "
                                + "IFNull(sumaindebido,0) - IFNull(abonoparcial,0) where folio = ?";
                        
                        /*String sqlactualizastatus = "update prestamos set status = 'A'  where saldo > '0'  "
                                + "and status not in ('c','C') and folio = ?"; */
                        String sqlactualizastatus = "UPDATE prestamos SET STATUS = CASE WHEN saldo > 0 THEN 'A' "
                                + "ELSE 'S' END  WHERE folio = ? AND STATUS NOT IN ('c','C')";
                        
                        try {

                            ps = con.prepareStatement(sql);
                            ps.setString(1, fields[0].replace(" ", ""));
                            ps.setString(2, fields[1].substring(0, 4) + "-" + fields[1].substring(4, 10));
                            ps.setString(3, fields[2]);
                            ps.setString(4, fields[3].replace(" ", ""));
                            ps.setString(5, fields[4]);
                            ps.setString(6, fields[5]);
                            ps.setString(7, mov.getNumeroQuincena());
                            ps.setString(8, "D");
                            ps.execute();
                            
                            psactualiza = con.prepareStatement(sqlactualiza);
                            psactualiza.setString(1, fields[5]);
                            psactualiza.setString(2, fields[5]);
                            psactualiza.setString(3, fields[5]);
                            psactualiza.execute();
                            
                            psactualizasaldo = con.prepareStatement(sqlactualizasaldo);
                            psactualizasaldo.setString(1, fields[5]);
                            psactualizasaldo.execute();
                            
                            psactualizastatus = con.prepareStatement(sqlactualizastatus);
                            psactualizastatus.setString(1, fields[5]);
                            psactualizastatus.execute();
                           
                             line = br.readLine();
                           
                           
                            //return true;
                        } catch (SQLException e) {
                            System.err.println(e);
                            //return false;

                        } 
                    }

                } catch (Exception e) {

                } /*finally {
                    if (null != br) {
                        br.close();
                    }

                }*/
                
                finally {
                            try {
                                con.close();

                            } catch (SQLException e) {

                                System.err.println(e);

                            }

                        }

            } else {
                JOptionPane.showMessageDialog(null, "LA QUINCENA YA ESTA DADA DE ALTA,NO LA PUEDE REPETIR");

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasExportar.class.getName()).log(Level.SEVERE, null, ex);
        }


       

    }

}