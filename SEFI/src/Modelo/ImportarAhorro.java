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
public class ImportarAhorro {
    
    private String Nombre;
    private String Rfc;
    private String Plantel;
    private Double Facore;
    private Double Importe;
    private String NumeroQuincena;
    private String Literal;
    private int NumeroEmpleado;
    private String UsuarioMovimiento;
    private int Folio;
    private String ruta;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getRfc() {
        return Rfc;
    }

    public void setRfc(String Rfc) {
        this.Rfc = Rfc;
    }

    public String getPlantel() {
        return Plantel;
    }

    public void setPlantel(String Plantel) {
        this.Plantel = Plantel;
    }

    public Double getFacore() {
        return Facore;
    }

    public void setFacore(Double Facore) {
        this.Facore = Facore;
    }

    public Double getImporte() {
        return Importe;
    }

    public void setImporte(Double Importe) {
        this.Importe = Importe;
    }

    public String getNumeroQuincena() {
        return NumeroQuincena;
    }

    public void setNumeroQuincena(String NumeroQuincena) {
        this.NumeroQuincena = NumeroQuincena;
    }

    public String getLiteral() {
        return Literal;
    }

    public void setLiteral(String Literal) {
        this.Literal = Literal;
    }

    public int getNumeroEmpleado() {
        return NumeroEmpleado;
    }

    public void setNumeroEmpleado(int NumeroEmpleado) {
        this.NumeroEmpleado = NumeroEmpleado;
    }

    public String getUsuarioMovimiento() {
        return UsuarioMovimiento;
    }

    public void setUsuarioMovimiento(String UsuarioMovimiento) {
        this.UsuarioMovimiento = UsuarioMovimiento;
    }

    public int getFolio() {
        return Folio;
    }

    public void setFolio(int Folio) {
        this.Folio = Folio;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public ImportarAhorro() {
    }

    public ImportarAhorro(String Nombre) {
        this.Nombre = Nombre;
    }
    
    
    
}
