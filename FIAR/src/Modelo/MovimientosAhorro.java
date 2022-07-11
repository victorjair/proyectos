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
public class MovimientosAhorro {
    
    private String Rfc;
    private String Id;
    private String Nombre;
    private String Plantel;
    private Double Abono;
    private Double RetirosAnteriores;
    private Double TotalAhorrado;
    private Double TotalEstado;
    private Double TotalSaldo;
    
    private Double Ahorros;
    private Double Retiros;
    private Double Saldo;
    private Double Total;
    private String Numeroquincena;
    private String Literal;
    private int Cheque;
    private int Transferencia;
    private String TipoRetiro;
    private String FechaAbono;
    private String UsuarioMovimiento;

    public String getUsuarioMovimiento() {
        return UsuarioMovimiento;
    }

    public void setUsuarioMovimiento(String UsuarioMovimiento) {
        this.UsuarioMovimiento = UsuarioMovimiento;
    }

    public Double getRetirosAnteriores() {
        return RetirosAnteriores;
    }

    public void setRetirosAnteriores(Double RetirosAnteriores) {
        this.RetirosAnteriores = RetirosAnteriores;
    }

    public Double getTotalAhorrado() {
        return TotalAhorrado;
    }

    public void setTotalAhorrado(Double TotalAhorrado) {
        this.TotalAhorrado = TotalAhorrado;
    }

    public Double getTotalEstado() {
        return TotalEstado;
    }

    public void setTotalEstado(Double TotalEstado) {
        this.TotalEstado = TotalEstado;
    }

    public Double getTotalSaldo() {
        return TotalSaldo;
    }

    public void setTotalSaldo(Double TotalSaldo) {
        this.TotalSaldo = TotalSaldo;
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

    public Double getAbono() {
        return Abono;
    }

    public void setAbono(Double Abono) {
        this.Abono = Abono;
    }

    public String getNumeroquincena() {
        return Numeroquincena;
    }

    public void setNumeroquincena(String Numeroquincena) {
        this.Numeroquincena = Numeroquincena;
    }

    public String getLiteral() {
        return Literal;
    }

    public void setLiteral(String Literal) {
        this.Literal = Literal;
    }

    public int getCheque() {
        return Cheque;
    }

    public void setCheque(int Cheque) {
        this.Cheque = Cheque;
    }

    public int getTransferencia() {
        return Transferencia;
    }

    public void setTransferencia(int Transferencia) {
        this.Transferencia = Transferencia;
    }

    public String getTipoRetiro() {
        return TipoRetiro;
    }

    public void setTipoRetiro(String TipoRetiro) {
        this.TipoRetiro = TipoRetiro;
    }

    public String getFechaAbono() {
        return FechaAbono;
    }

    public void setFechaAbono(String FechaAbono) {
        this.FechaAbono = FechaAbono;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public Double getAhorros() {
        return Ahorros;
    }

    public void setAhorros(Double Ahorros) {
        this.Ahorros = Ahorros;
    }

    public Double getRetiros() {
        return Retiros;
    }

    public void setRetiros(Double Retiros) {
        this.Retiros = Retiros;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double Saldo) {
        this.Saldo = Saldo;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double Total) {
        this.Total = Total;
    }

    public MovimientosAhorro(String Rfc, String Id, String Nombre, String Plantel, Double Abono, Double Ahorros, Double Retiros, Double Saldo, Double Total, String Numeroquincena, String Literal, int Cheque, int Transferencia, String TipoRetiro, String FechaAbono, String UsuarioMovimiento) {
        this.Rfc = Rfc;
        this.Id = Id;
        this.Nombre = Nombre;
        this.Plantel = Plantel;
        this.Abono = Abono;
        this.Ahorros = Ahorros;
        this.Retiros = Retiros;
        this.Saldo = Saldo;
        this.Total = Total;
        this.Numeroquincena = Numeroquincena;
        this.Literal = Literal;
        this.Cheque = Cheque;
        this.Transferencia = Transferencia;
        this.TipoRetiro = TipoRetiro;
        this.FechaAbono = FechaAbono;
        this.UsuarioMovimiento = UsuarioMovimiento;
    }

    

    

    

    public MovimientosAhorro() {
    }
    
    

    

    
    
    
    
}
