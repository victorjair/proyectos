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
public class Beneficiario {
    
    private String nombre;
    private Double porcentaje;
    private String parentesco;
    private String Id;
    private String fechanacimiento;
    private String rfcAfiliado;
    private int prioridadBeneficiario;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getRfcAfiliado() {
        return rfcAfiliado;
    }

    public void setRfcAfiliado(String rfcAfiliado) {
        this.rfcAfiliado = rfcAfiliado;
    }

    public int getPrioridadBeneficiario() {
        return prioridadBeneficiario;
    }

    public void setPrioridadBeneficiario(int prioridadBeneficiario) {
        this.prioridadBeneficiario = prioridadBeneficiario;
    }

    public Beneficiario() {
    }

    public Beneficiario(String nombre, Double porcentaje, String parentesco, String Id, String fechanacimiento, String rfcAfiliado, int prioridadBeneficiario) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.parentesco = parentesco;
        this.Id = Id;
        this.fechanacimiento = fechanacimiento;
        this.rfcAfiliado = rfcAfiliado;
        this.prioridadBeneficiario = prioridadBeneficiario;
    }
    
    
    
    
    
    
}
