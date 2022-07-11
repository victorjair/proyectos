package Modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author victor
 */
public class Movimientos {

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
    
    private String Rfc;
    private String Id;
    private String Nombre;
    private String Plantel;
    private String Puesto;
    private int Folio;
    private Double Abono;
    private Double Capital;
    private Double Interes;
    private Double Saldo;
    private int NumeroQuincena;
    private String Movimiento;
    private String UsuarioMovimiento;
    private String  FechaAbono ;

    public Double getCapital() {
        return Capital;
    }

    public void setCapital(Double Capital) {
        this.Capital = Capital;
    }

    public Double getInteres() {
        return Interes;
    }

    public void setInteres(Double Interes) {
        this.Interes = Interes;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double Saldo) {
        this.Saldo = Saldo;
    }
    

    public String getRfc() {
        return Rfc;
    }

    public void setRfc(String Rfc) {
        this.Rfc = Rfc;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getPlantel() {
        return Plantel;
    }

    public void setPlantel(String Plantel) {
        this.Plantel = Plantel;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String Puesto) {
        this.Puesto = Puesto;
    }

    public int getFolio() {
        return Folio;
    }

    public void setFolio(int Folio) {
        this.Folio = Folio;
    }

    public Double getAbono() {
        return Abono;
    }

    public void setAbono(Double Abono) {
        this.Abono = Abono;
    }

    public int getNumeroQuincena() {
        return NumeroQuincena;
    }

    public void setNumeroQuincena(int NumeroQuincena) {
        this.NumeroQuincena = NumeroQuincena;
    }

    public String getMovimiento() {
        return Movimiento;
    }

    public void setMovimiento(String Movimiento) {
        this.Movimiento = Movimiento;
    }

    public String getUsuarioMovimiento() {
        return UsuarioMovimiento;
    }

    public void setUsuarioMovimiento(String UsuarioMovimiento) {
        this.UsuarioMovimiento = UsuarioMovimiento;
    }

    public String getFechaAbono() {
        return FechaAbono;
    }

    public void setFechaAbono(String FechaAbono) {
        this.FechaAbono = FechaAbono;
    }

    public Movimientos(String Rfc, String Id, String Nombre, String Plantel, String Puesto, int Folio, Double Abono, Double Capital, Double Interes, Double Saldo, int NumeroQuincena, String Movimiento, String UsuarioMovimiento, String FechaAbono) {
        this.Rfc = Rfc;
        this.Id = Id;
        this.Nombre = Nombre;
        this.Plantel = Plantel;
        this.Puesto = Puesto;
        this.Folio = Folio;
        this.Abono = Abono;
        this.Capital = Capital;
        this.Interes = Interes;
        this.Saldo = Saldo;
        this.NumeroQuincena = NumeroQuincena;
        this.Movimiento = Movimiento;
        this.UsuarioMovimiento = UsuarioMovimiento;
        this.FechaAbono = FechaAbono;
    }

    

   

    

    
    public Movimientos() {
    }

    @Override
    public String toString() {
        return "Movimientos{" + '}';
    }
    
    
    
    
    
}
