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
public class CompararExcel {
    private String numero;
    private String periodo;
    private String numeroQuincena;

    public String getNumeroQuincena() {
        return numeroQuincena;
    }

    public void setNumeroQuincena(String numeroQuincena) {
        this.numeroQuincena = numeroQuincena;
    }
    
    
    

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }


    public CompararExcel() {
    }

    public CompararExcel(String numero, String periodo, String numeroQuincena) {
        this.numero = numero;
        this.periodo = periodo;
        this.numeroQuincena = numeroQuincena;
    }
    
    
}
