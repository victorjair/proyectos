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
public class Issste {
    
    String d_03;
    String d_04;
    String d_21;
    String d_22;
    String d_23;
    String periodo;
    String factor;
    String factor2;

    public Issste(String d_03, String d_04, String d_21, String d_22, String d_23, String periodo, String factor, String factor2) {
        this.d_03 = d_03;
        this.d_04 = d_04;
        this.d_21 = d_21;
        this.d_22 = d_22;
        this.d_23 = d_23;
        this.periodo = periodo;
        this.factor = factor;
        this.factor2 = factor2;
    }

    public Issste() {
    }
    
    
    public String getD_03() {
        return d_03;
    }

    public void setD_03(String d_03) {
        this.d_03 = d_03;
    }

    public String getD_04() {
        return d_04;
    }

    public void setD_04(String d_04) {
        this.d_04 = d_04;
    }

    public String getD_21() {
        return d_21;
    }

    public void setD_21(String d_21) {
        this.d_21 = d_21;
    }

    public String getD_22() {
        return d_22;
    }

    public void setD_22(String d_22) {
        this.d_22 = d_22;
    }

    public String getD_23() {
        return d_23;
    }

    public void setD_23(String d_23) {
        this.d_23 = d_23;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getFactor2() {
        return factor2;
    }

    public void setFactor2(String factor2) {
        this.factor2 = factor2;
    }
    
    
    
}
