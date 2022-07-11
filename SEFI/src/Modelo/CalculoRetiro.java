/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author SERCOM
 */
public class CalculoRetiro {
 String nombre;
 String tipoo;
 int numeroempleado;
 int PeriodoInicio;
 int PeriodoFinal;
 Double p1;
 Double p3;
 Double p14;
 Double p18;
 Double p19;
 Double ppg;
 Double ppe;
 Double d01;
 Double sumaissste;
 
 Double p1cp;
 Double p3cp;
 Double p14cp;
 Double p18cp;
 Double p19cp;
 Double ppgcp;
 Double ppecp;
 Double d01cp;
 Double sumaissstecp;
 
 Double p1dif;
 Double p3dif;
 Double p14dif;
 Double p18dif;
 Double p19dif;
 Double ppgdif;
 Double ppedif;
 Double d01dif;
 Double sumaissstedif;
 
 
 Double aportacionqnalalprograma;
 Double aportacionanualalprograma;
 Double periododecotizacion;
 Double aportaciontotal;
 Double fondoderetirovoluntario;

    public CalculoRetiro(String nombre, String tipoo, int numeroempleado, int PeriodoInicio, int PeriodoFinal, Double p1, Double p3, Double p14, Double p18, Double p19, Double ppg, Double ppe, Double d01, Double sumaissste, Double p1cp, Double p3cp, Double p14cp, Double p18cp, Double p19cp, Double ppgcp, Double ppecp, Double d01cp, Double sumaissstecp, Double p1dif, Double p3dif, Double p14dif, Double p18dif, Double p19dif, Double ppgdif, Double ppedif, Double d01dif, Double sumaissstedif, Double aportacionqnalalprograma, Double aportacionanualalprograma, Double periododecotizacion, Double aportaciontotal, Double fondoderetirovoluntario) {
        this.nombre = nombre;
        this.tipoo = tipoo;
        this.numeroempleado = numeroempleado;
        this.PeriodoInicio = PeriodoInicio;
        this.PeriodoFinal = PeriodoFinal;
        this.p1 = p1;
        this.p3 = p3;
        this.p14 = p14;
        this.p18 = p18;
        this.p19 = p19;
        this.ppg = ppg;
        this.ppe = ppe;
        this.d01 = d01;
        this.sumaissste = sumaissste;
        this.p1cp = p1cp;
        this.p3cp = p3cp;
        this.p14cp = p14cp;
        this.p18cp = p18cp;
        this.p19cp = p19cp;
        this.ppgcp = ppgcp;
        this.ppecp = ppecp;
        this.d01cp = d01cp;
        this.sumaissstecp = sumaissstecp;
        this.p1dif = p1dif;
        this.p3dif = p3dif;
        this.p14dif = p14dif;
        this.p18dif = p18dif;
        this.p19dif = p19dif;
        this.ppgdif = ppgdif;
        this.ppedif = ppedif;
        this.d01dif = d01dif;
        this.sumaissstedif = sumaissstedif;
        this.aportacionqnalalprograma = aportacionqnalalprograma;
        this.aportacionanualalprograma = aportacionanualalprograma;
        this.periododecotizacion = periododecotizacion;
        this.aportaciontotal = aportaciontotal;
        this.fondoderetirovoluntario = fondoderetirovoluntario;
    }

    public CalculoRetiro() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoo() {
        return tipoo;
    }

    public void setTipoo(String tipoo) {
        this.tipoo = tipoo;
    }

    public int getNumeroempleado() {
        return numeroempleado;
    }

    public void setNumeroempleado(int numeroempleado) {
        this.numeroempleado = numeroempleado;
    }

    public int getPeriodoInicio() {
        return PeriodoInicio;
    }

    public void setPeriodoInicio(int PeriodoInicio) {
        this.PeriodoInicio = PeriodoInicio;
    }

    public int getPeriodoFinal() {
        return PeriodoFinal;
    }

    public void setPeriodoFinal(int PeriodoFinal) {
        this.PeriodoFinal = PeriodoFinal;
    }

    public Double getP1() {
        return p1;
    }

    public void setP1(Double p1) {
        this.p1 = p1;
    }

    public Double getP3() {
        return p3;
    }

    public void setP3(Double p3) {
        this.p3 = p3;
    }

    public Double getP14() {
        return p14;
    }

    public void setP14(Double p14) {
        this.p14 = p14;
    }

    public Double getP18() {
        return p18;
    }

    public void setP18(Double p18) {
        this.p18 = p18;
    }

    public Double getP19() {
        return p19;
    }

    public void setP19(Double p19) {
        this.p19 = p19;
    }

    public Double getPpg() {
        return ppg;
    }

    public void setPpg(Double ppg) {
        this.ppg = ppg;
    }

    public Double getPpe() {
        return ppe;
    }

    public void setPpe(Double ppe) {
        this.ppe = ppe;
    }

    public Double getD01() {
        return d01;
    }

    public void setD01(Double d01) {
        this.d01 = d01;
    }

    public Double getSumaissste() {
        return sumaissste;
    }

    public void setSumaissste(Double sumaissste) {
        this.sumaissste = sumaissste;
    }

    public Double getP1cp() {
        return p1cp;
    }

    public void setP1cp(Double p1cp) {
        this.p1cp = p1cp;
    }

    public Double getP3cp() {
        return p3cp;
    }

    public void setP3cp(Double p3cp) {
        this.p3cp = p3cp;
    }

    public Double getP14cp() {
        return p14cp;
    }

    public void setP14cp(Double p14cp) {
        this.p14cp = p14cp;
    }

    public Double getP18cp() {
        return p18cp;
    }

    public void setP18cp(Double p18cp) {
        this.p18cp = p18cp;
    }

    public Double getP19cp() {
        return p19cp;
    }

    public void setP19cp(Double p19cp) {
        this.p19cp = p19cp;
    }

    public Double getPpgcp() {
        return ppgcp;
    }

    public void setPpgcp(Double ppgcp) {
        this.ppgcp = ppgcp;
    }

    public Double getPpecp() {
        return ppecp;
    }

    public void setPpecp(Double ppecp) {
        this.ppecp = ppecp;
    }

    public Double getD01cp() {
        return d01cp;
    }

    public void setD01cp(Double d01cp) {
        this.d01cp = d01cp;
    }

    public Double getSumaissstecp() {
        return sumaissstecp;
    }

    public void setSumaissstecp(Double sumaissstecp) {
        this.sumaissstecp = sumaissstecp;
    }

    public Double getP1dif() {
        return p1dif;
    }

    public void setP1dif(Double p1dif) {
        this.p1dif = p1dif;
    }

    public Double getP3dif() {
        return p3dif;
    }

    public void setP3dif(Double p3dif) {
        this.p3dif = p3dif;
    }

    public Double getP14dif() {
        return p14dif;
    }

    public void setP14dif(Double p14dif) {
        this.p14dif = p14dif;
    }

    public Double getP18dif() {
        return p18dif;
    }

    public void setP18dif(Double p18dif) {
        this.p18dif = p18dif;
    }

    public Double getP19dif() {
        return p19dif;
    }

    public void setP19dif(Double p19dif) {
        this.p19dif = p19dif;
    }

    public Double getPpgdif() {
        return ppgdif;
    }

    public void setPpgdif(Double ppgdif) {
        this.ppgdif = ppgdif;
    }

    public Double getPpedif() {
        return ppedif;
    }

    public void setPpedif(Double ppedif) {
        this.ppedif = ppedif;
    }

    public Double getD01dif() {
        return d01dif;
    }

    public void setD01dif(Double d01dif) {
        this.d01dif = d01dif;
    }

    public Double getSumaissstedif() {
        return sumaissstedif;
    }

    public void setSumaissstedif(Double sumaissstedif) {
        this.sumaissstedif = sumaissstedif;
    }

    public Double getAportacionqnalalprograma() {
        return aportacionqnalalprograma;
    }

    public void setAportacionqnalalprograma(Double aportacionqnalalprograma) {
        this.aportacionqnalalprograma = aportacionqnalalprograma;
    }

    public Double getAportacionanualalprograma() {
        return aportacionanualalprograma;
    }

    public void setAportacionanualalprograma(Double aportacionanualalprograma) {
        this.aportacionanualalprograma = aportacionanualalprograma;
    }

    public Double getPeriododecotizacion() {
        return periododecotizacion;
    }

    public void setPeriododecotizacion(Double periododecotizacion) {
        this.periododecotizacion = periododecotizacion;
    }

    public Double getAportaciontotal() {
        return aportaciontotal;
    }

    public void setAportaciontotal(Double aportaciontotal) {
        this.aportaciontotal = aportaciontotal;
    }

    public Double getFondoderetirovoluntario() {
        return fondoderetirovoluntario;
    }

    public void setFondoderetirovoluntario(Double fondoderetirovoluntario) {
        this.fondoderetirovoluntario = fondoderetirovoluntario;
    }

    
    

    
}
