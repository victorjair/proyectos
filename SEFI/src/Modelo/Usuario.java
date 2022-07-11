/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author victor
 */
public class Usuario {
    
    static String usuario;
    private String password;
    static  String fechaUltimoRegistro;
    static String tipoUsuario;
    static String privilegios;

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        Usuario.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getFechaUltimoRegistro() {
        return fechaUltimoRegistro;
    }

    public static void setFechaUltimoRegistro(String fechaUltimoRegistro) {
        Usuario.fechaUltimoRegistro = fechaUltimoRegistro;
    }

    public static String getTipoUsuario() {
        return tipoUsuario;
    }

    public static void setTipoUsuario(String tipoUsuario) {
        Usuario.tipoUsuario = tipoUsuario;
    }

    public static String getPrivilegios() {
        return privilegios;
    }

    public static void setPrivilegios(String privilegios) {
        Usuario.privilegios = privilegios;
    }

    public Usuario() {
    }

   
    
}
