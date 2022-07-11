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
public class Afiliado {
    
    
private String Nombre;
private String Id;
private String rfc;
private String Direccion;
private String NumeroEmpleado;
private String Plantel;
private float PorcentajeAhorro;
private String Telefono;
private String Celular;
private String Correo;
private float  SueldoBase;
private String Puesto;
private String QnaDescuento;
private String QnaAfiliacion;
private String FechaAfiliacion;
private String motivoBaja;
private String Status;
private String QnaBaja;

    
private float  PorcentajeAhorroCp;
public final Double factor = 1.33456733;
    public Afiliado() {
    }

    public Afiliado(String Nombre, String Id, String rfc, String Direccion, String NumeroEmpleado, String Plantel, float PorcentajeAhorro, String Telefono, String Celular, String Correo, float SueldoBase, String Puesto, String QnaDescuento, String QnaAfiliacion, String FechaAfiliacion, String motivoBaja, float PorcentajeAhorroCp) {
        this.Nombre = Nombre;
        this.Id = Id;
        this.rfc = rfc;
        this.Direccion = Direccion;
        this.NumeroEmpleado = NumeroEmpleado;
        this.Plantel = Plantel;
        this.PorcentajeAhorro = PorcentajeAhorro;
        this.Telefono = Telefono;
        this.Celular = Celular;
        this.Correo = Correo;
        this.SueldoBase = SueldoBase;
        this.Puesto = Puesto;
        this.QnaDescuento = QnaDescuento;
        this.QnaAfiliacion = QnaAfiliacion;
        this.FechaAfiliacion = FechaAfiliacion;
        this.motivoBaja = motivoBaja;
        this.PorcentajeAhorroCp = PorcentajeAhorroCp;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getQnaBaja() {
        return QnaBaja;
    }

    public void setQnaBaja(String QnaBaja) {
        this.QnaBaja = QnaBaja;
    }

    

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    

    

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getNumeroEmpleado() {
        return NumeroEmpleado;
    }

    public void setNumeroEmpleado(String NumeroEmpleado) {
        this.NumeroEmpleado = NumeroEmpleado;
    }

    public String getPlantel() {
        return Plantel;
    }

    public void setPlantel(String Plantel) {
        this.Plantel = Plantel;
    }

    public float getPorcentajeAhorro() {
        return PorcentajeAhorro;
    }

    public void setPorcentajeAhorro(float PorcentajeAhorro) {
        this.PorcentajeAhorro = PorcentajeAhorro;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public float getSueldoBase() {
        return SueldoBase;
    }

    public void setSueldoBase(float SueldoBase) {
        this.SueldoBase = SueldoBase;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String Puesto) {
        this.Puesto = Puesto;
    }

    public String getQnaDescuento() {
        return QnaDescuento;
    }

    public void setQnaDescuento(String QnaDescuento) {
        this.QnaDescuento = QnaDescuento;
    }

    public String getQnaAfiliacion() {
        return QnaAfiliacion;
    }

    public void setQnaAfiliacion(String QnaAfiliacion) {
        this.QnaAfiliacion = QnaAfiliacion;
    }

    public String getFechaAfiliacion() {
        return FechaAfiliacion;
    }

    public void setFechaAfiliacion(String FechaAfiliacion) {
        this.FechaAfiliacion = FechaAfiliacion;
    }

    public float getPorcentajeAhorroCp() {
        return PorcentajeAhorroCp;
    }

    public void setPorcentajeAhorroCp(float PorcentajeAhorroCp) {
        this.PorcentajeAhorroCp = PorcentajeAhorroCp;
    }
    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }
    
    
    
  

    



    
}
