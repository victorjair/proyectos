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
public class Empleado {
    private String Rfc;
private int Empleado;
private int periodo;
private String Cvemvto;
private String Tipo;
private String Quincena;
private String Lapso;
private String Plantel;
private String NumeroPlantel;
private String NombrePlantel;
private String CveCategoria;
private String Usuario;
private int plazas;
private String NoPuesto;
private String Leido;
private String AP_PAT;
private String AP_MAT;
private String Nombre;
private String Categoria;
private String Horas;
private String Nivel;
private String FechaOld;
private String TipoPer;
private String Zona;
private String Curp;
private String Semestre;
private String TPlantel;
private String TipoSin;
private String Mutual;
private String Facore;
private String CedulaImss;
private String RegimenPensionario;
private int Id;
private int Numeroquincena;
private int AyudaMutua;
private float porcentajeseccion31;
private float porcentajeHipotecarioIssste;

private float porcentajeseccion63;
private float porcentajesitcobach;
private float porcentajesutcobach;
private float porcentajeissste;
private int HorasSems;
private int HorasBase;
private int HorasLimitadas;
private int HorasInterinas;
private int NumeroInasistencias;
private String FechaInasistencias;
private String FechaModificacionissste;
private String Saldoissste;
private int TotalDias;
private String status;
private String statusissste;
private String MotivoDeBaja;
private String FechaDeBaja;
private String FechaDeReactivacion;
private String FechaDeBajaIssste;
private String NumeroDePago;
private String TotalDePago;
private String Folioissste;
private String NumeroDeContrato;
private String TipoContrato;
private String Observacion;
private String CveAyudaMutua;
private String CveCuotaSindical;
private String CajaSutcobach;
private String CajaSeccion;
private String CajaSitcobach;
private String CajaSeccion63;
private String Ruta;
private String ClaveImportar;
private String NumeroQuincenaImportar;
private Float montocuotasindical;
private Double antiguedadsuma;
private Double salariosuma;
private Double sumap1;
private Double sumap1a;
private Double sumap1b;
private Double sumap1c;
private Double sumap1d;
private Double sumap1e;
private Double sumap18;
private Double sumap19;
private Double D_01masdeunaplaza;
private Double P_14masdeunaplaza;
private Double D_03masdeunaplaza;
private Double D_04masdeunaplaza;
private Double D_21masdeunaplaza;
private Double D_22masdeunaplaza;
private Double D_23masdeunaplaza;

private Double PercepcionesEmpleado;
private Double DeduccionesEmpleado;
private Double PercepcionesSinCalculoEmpleado;
private Double DeduccionesSinCalculoEmpleado;
private Double NetoEmpleado;
private Double SalarioBase;
private Double baseissste;
private Double sumaSalarioBase;
private Double P14sinplanNeto;
private Double D01sinplanNeto;


private Double P01Update;
private Double P01AUpdate;
private Double P01BUpdate;
private Double P01CUpdate;
private Double P01DUpdate;
private Double P01EUpdate;
private Double P018Update;
private Double P019Update;
private Double PPEUpdate;
private Double PPGUpdate;
private Double P14Update;
private Double D01Update;



              

private int   numerodeplazas;
private int   numerodeempleadoganamas;
private float P_01;private float P_02;private float P_03;private float P_05;private float P_06;private float P_07;private float P_08;
private float P_09;private float P_10;private float P_11;private float P_12;private float P_13;private float P_14;private float P_15;
private float P_16;private float P_17;private float P_18;private float P_19;private float P_20;private float P_21;private float P_22;
private float P_23;private float P_24;private float P_25;private float P_26;private float P_27;private float P_28;private float P_29;
private float P_30;private float P_31;private float P_32;private float P_33;private float P_34;private float P_35;private float P_36;
private float P_37;private float P_38;private float P_39;private float P_40;private float P_41;private float P_42;private float P_43;
private float P_44;private float P_45;private float P_46;private float P_47;private float P_48;private float P_49;private float P_50;
private float P_51;private float P_52;private float P_53;private float P_54;private float P_55;private float P_56;private float P_57;
private float P_58 ;private float P_59;private float P_60;private float P_01A;private float P_01B;private float P_01C;private float P_01D;
private float P_01E;private float P_04;private float P_DI;private float P_C1;private float P_C2;

private float D_01;private float D_02;private float D_03;private float D_05;private float D_06;private float D_07;private float D_08;
private float D_09;private float D_10;private float D_11;private float D_12;private float D_13;private float D_14;private float D_15;
private float D_16;private float D_17;private float D_18;private float D_19;private float D_20;private float D_21;private float D_22;
private float D_23;private float D_24;private float D_25;private float D_26;private float D_27;private float D_28;private float D_29;
private float D_30;private float D_31;private float D_32;private float D_33;private float D_34;private float D_35;private float D_36;
private float D_37;private float D_38;private float D_39;private float D_40;private float D_41;private float D_42;private float D_43;
private float D_44;private float D_45;private float D_46;private float D_47;private float D_48;private float D_49;private float D_50;
private float D_51;private float D_52;private float D_53;private float D_54;private float D_55;private float D_56;private float D_57;
private float D_CB;private float P_MC;private float P_PG;private float P_PE;private float D_58;private float D_59;private float D_60;
private float D_04;private float D_01A;
    public Empleado() {
    }

    public Empleado(String Rfc, int Empleado, Double antiguedadsuma, Double sumap18, Double sumap19, Double SalarioBase, Double baseissste, int numerodeplazas,Double NetoEmpleado) {
        this.Rfc = Rfc;
        this.Empleado = Empleado;
        this.antiguedadsuma = antiguedadsuma;
        this.sumap18 = sumap18;
        this.sumap19 = sumap19;
        this.SalarioBase = SalarioBase;
        this.baseissste = baseissste;
        this.numerodeplazas = numerodeplazas;
        this.NetoEmpleado = NetoEmpleado;
    }

    public Empleado(String Rfc, int Empleado,Double sumaSalarioBase,   Double antiguedadsuma, Double sumap18, Double sumap19 ,Double baseissste,int numerodeplazas) {
        this.Rfc = Rfc;
        this.Empleado = Empleado;
        this.sumaSalarioBase = sumaSalarioBase;
        this.antiguedadsuma = antiguedadsuma;
        this.sumap18 = sumap18;
        this.sumap19 = sumap19;
        this.SalarioBase = SalarioBase;
        this.baseissste = baseissste;
        this.numerodeplazas = numerodeplazas;

    }
    
    
    public Empleado(String Rfc,int numerodeplazas,Double sumaSalarioBase,   Double antiguedadsuma, Double sumap18, Double sumap19,int Empleado, 
            Double baseissste, Double sumap1,Double sumap1a,Double sumap1b,Double sumap1c,Double sumap1d,Double sumap1e) {
        this.Rfc = Rfc;
        this.Empleado = Empleado;
        this.sumaSalarioBase = sumaSalarioBase;
        this.antiguedadsuma = antiguedadsuma;
        this.sumap18 = sumap18;
        this.sumap19 = sumap19;
        this.baseissste = baseissste;
        this.numerodeplazas = numerodeplazas;
        this.sumap1 = sumap1;
        this.sumap1a = sumap1a;
        this.sumap1b = sumap1b;
        this.sumap1c = sumap1c;
        this.sumap1d = sumap1d;
        this.sumap1e = sumap1e;
   
    }

    public Empleado(String Rfc, int Empleado, float P_01, float P_01A, float P_01B, float P_01C, float P_01D, float P_01E, float P_03,
            float P_18, float P_19, Double SalarioBase, Double PercepcionesEmpleado, Double DeduccionesEmpleado, Double NetoEmpleado, Double PercepcionesSinCalculoEmpleado,
             Double DeduccionesSinCalculoEmpleado,Double P14sinplanNeto,Double D01sinplanNeto ) {
        this.Rfc = Rfc;
        this.Empleado = Empleado;
        this.PercepcionesEmpleado = PercepcionesEmpleado;
        this.DeduccionesEmpleado = DeduccionesEmpleado;
        this.PercepcionesSinCalculoEmpleado = PercepcionesSinCalculoEmpleado;
        this.DeduccionesSinCalculoEmpleado = DeduccionesSinCalculoEmpleado;

        this.NetoEmpleado = NetoEmpleado;
        this.SalarioBase = SalarioBase;
        this.P_01 = P_01;
        this.P_03 = P_03;
        this.P_18 = P_18;
        this.P_19 = P_19;
        this.P_01A = P_01A;
        this.P_01B = P_01B;
        this.P_01C = P_01C;
        this.P_01D = P_01D;
        this.P_01E = P_01E;
        this.P14sinplanNeto = P14sinplanNeto;
        this.D01sinplanNeto = D01sinplanNeto;
    }
    public Empleado(String Rfc, int Empleado, int periodo, String Cvemvto, String Tipo, String Quincena, String Lapso, String Plantel, String NoPuesto, String Leido, String AP_PAT, String AP_MAT, String Nombre, String Categoria, String Horas, String Nivel, String FechaOld, String TipoPer, String Zona, String Semestre, String TPlantel, String TipoSin, String Mutual, String Facore, String CedulaImss, String RegimenPensionario, int Id, int Numeroquincena, int AyudaMutua, float porcentajeseccion31, float porcentajeseccion63, float porcentajesitcobach, float porcentajesutcobach, float porcentajeissste, int HorasSems, int HorasBase, int HorasLimitadas, int HorasInterinas, float P_01, float P_02, float P_03, float P_05, float P_06, float P_07, float P_08, float P_09, float P_10, float P_11, float P_12, float P_13, float P_14, float P_15, float P_16, float P_17, float P_18, float P_19, float P_20, float P_21, float P_22, float P_23, float P_24, float P_25, float P_26, float P_27, float P_28, float P_29, float P_30, float P_31, float P_32, float P_33, float P_34, float P_35, float P_36, float P_37, float P_38, float P_39, float P_40, float P_41, float P_42, float P_43, float P_44, float P_45, float P_46, float P_47, float P_48, float P_49, float P_50, float P_51, float P_52, float P_53, float P_54, float P_55, float P_56, float P_57, float P_58, float P_59, float P_60, float P_01A, float P_01B, float P_01C, float P_01D, float P_01E, float D_01, float D_02, float D_03, float D_05, float D_06, float D_07, float D_08, float D_09, float D_10, float D_11, float D_12, float D_13, float D_14, float D_15, float D_16, float D_17, float D_18, float D_19, float D_20, float D_21, float D_22, float D_23, float D_24, float D_25, float D_26, float D_27, float D_28, float D_29, float D_30, float D_31, float D_32, float D_33, float D_34, float D_35, float D_36, float D_37, float D_38, float D_39, float D_40, float D_41, float D_42, float D_43, float D_44, float D_45, float D_46, float D_47, float D_48, float D_49, float D_50, float D_51, float D_52, float D_53, float D_54, float D_55, float D_56, float D_57, float D_CB, float P_MC, float P_PG, float P_PE) {
        this.Rfc = Rfc;
        this.Empleado = Empleado;
        this.periodo = periodo;
        this.Cvemvto = Cvemvto;
        this.Tipo = Tipo;
        this.Quincena = Quincena;
        this.Lapso = Lapso;
        this.Plantel = Plantel;
        this.NoPuesto = NoPuesto;
        this.Leido = Leido;
        this.AP_PAT = AP_PAT;
        this.AP_MAT = AP_MAT;
        this.Nombre = Nombre;
        this.Categoria = Categoria;
        this.Horas = Horas;
        this.Nivel = Nivel;
        this.FechaOld = FechaOld;
        this.TipoPer = TipoPer;
        this.Zona = Zona;
        this.Semestre = Semestre;
        this.TPlantel = TPlantel;
        this.TipoSin = TipoSin;
        this.Mutual = Mutual;
        this.Facore = Facore;
        this.CedulaImss = CedulaImss;
        this.RegimenPensionario = RegimenPensionario;
        this.Id = Id;
        this.Numeroquincena = Numeroquincena;
        this.AyudaMutua = AyudaMutua;
        this.porcentajeseccion31 = porcentajeseccion31;
        this.porcentajeseccion63 = porcentajeseccion63;
        this.porcentajesitcobach = porcentajesitcobach;
        this.porcentajesutcobach = porcentajesutcobach;
        this.porcentajeissste = porcentajeissste;
        this.HorasSems = HorasSems;
        this.HorasBase = HorasBase;
        this.HorasLimitadas = HorasLimitadas;
        this.HorasInterinas = HorasInterinas;
        this.P_01 = P_01;
        this.P_02 = P_02;
        this.P_03 = P_03;
        this.P_05 = P_05;
        this.P_06 = P_06;
        this.P_07 = P_07;
        this.P_08 = P_08;
        this.P_09 = P_09;
        this.P_10 = P_10;
        this.P_11 = P_11;
        this.P_12 = P_12;
        this.P_13 = P_13;
        this.P_14 = P_14;
        this.P_15 = P_15;
        this.P_16 = P_16;
        this.P_17 = P_17;
        this.P_18 = P_18;
        this.P_19 = P_19;
        this.P_20 = P_20;
        this.P_21 = P_21;
        this.P_22 = P_22;
        this.P_23 = P_23;
        this.P_24 = P_24;
        this.P_25 = P_25;
        this.P_26 = P_26;
        this.P_27 = P_27;
        this.P_28 = P_28;
        this.P_29 = P_29;
        this.P_30 = P_30;
        this.P_31 = P_31;
        this.P_32 = P_32;
        this.P_33 = P_33;
        this.P_34 = P_34;
        this.P_35 = P_35;
        this.P_36 = P_36;
        this.P_37 = P_37;
        this.P_38 = P_38;
        this.P_39 = P_39;
        this.P_40 = P_40;
        this.P_41 = P_41;
        this.P_42 = P_42;
        this.P_43 = P_43;
        this.P_44 = P_44;
        this.P_45 = P_45;
        this.P_46 = P_46;
        this.P_47 = P_47;
        this.P_48 = P_48;
        this.P_49 = P_49;
        this.P_50 = P_50;
        this.P_51 = P_51;
        this.P_52 = P_52;
        this.P_53 = P_53;
        this.P_54 = P_54;
        this.P_55 = P_55;
        this.P_56 = P_56;
        this.P_57 = P_57;
        this.P_58 = P_58;
        this.P_59 = P_59;
        this.P_60 = P_60;
        this.P_01A = P_01A;
        this.P_01B = P_01B;
        this.P_01C = P_01C;
        this.P_01D = P_01D;
        this.P_01E = P_01E;
        this.D_01 = D_01;
        this.D_02 = D_02;
        this.D_03 = D_03;
        this.D_05 = D_05;
        this.D_06 = D_06;
        this.D_07 = D_07;
        this.D_08 = D_08;
        this.D_09 = D_09;
        this.D_10 = D_10;
        this.D_11 = D_11;
        this.D_12 = D_12;
        this.D_13 = D_13;
        this.D_14 = D_14;
        this.D_15 = D_15;
        this.D_16 = D_16;
        this.D_17 = D_17;
        this.D_18 = D_18;
        this.D_19 = D_19;
        this.D_20 = D_20;
        this.D_21 = D_21;
        this.D_22 = D_22;
        this.D_23 = D_23;
        this.D_24 = D_24;
        this.D_25 = D_25;
        this.D_26 = D_26;
        this.D_27 = D_27;
        this.D_28 = D_28;
        this.D_29 = D_29;
        this.D_30 = D_30;
        this.D_31 = D_31;
        this.D_32 = D_32;
        this.D_33 = D_33;
        this.D_34 = D_34;
        this.D_35 = D_35;
        this.D_36 = D_36;
        this.D_37 = D_37;
        this.D_38 = D_38;
        this.D_39 = D_39;
        this.D_40 = D_40;
        this.D_41 = D_41;
        this.D_42 = D_42;
        this.D_43 = D_43;
        this.D_44 = D_44;
        this.D_45 = D_45;
        this.D_46 = D_46;
        this.D_47 = D_47;
        this.D_48 = D_48;
        this.D_49 = D_49;
        this.D_50 = D_50;
        this.D_51 = D_51;
        this.D_52 = D_52;
        this.D_53 = D_53;
        this.D_54 = D_54;
        this.D_55 = D_55;
        this.D_56 = D_56;
        this.D_57 = D_57;
        this.D_CB = D_CB;
        this.P_MC = P_MC;
        this.P_PG = P_PG;
        this.P_PE = P_PE;
    }

   
    public String getRfc() {
        return Rfc;
    }

    public void setRfc(String Rfc) {
        this.Rfc = Rfc;
    }

    public int getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(int Empleado) {
        this.Empleado = Empleado;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getCvemvto() {
        return Cvemvto;
    }

    public void setCvemvto(String Cvemvto) {
        this.Cvemvto = Cvemvto;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getQuincena() {
        return Quincena;
    }

    public void setQuincena(String Quincena) {
        this.Quincena = Quincena;
    }

    public String getLapso() {
        return Lapso;
    }

    public void setLapso(String Lapso) {
        this.Lapso = Lapso;
    }

    public String getPlantel() {
        return Plantel;
    }

    public void setPlantel(String Plantel) {
        this.Plantel = Plantel;
    }

    public String getNoPuesto() {
        return NoPuesto;
    }

    public void setNoPuesto(String NoPuesto) {
        this.NoPuesto = NoPuesto;
    }

    public String getLeido() {
        return Leido;
    }

    public void setLeido(String Leido) {
        this.Leido = Leido;
    }

    public String getAP_PAT() {
        return AP_PAT;
    }

    public void setAP_PAT(String AP_PAT) {
        this.AP_PAT = AP_PAT;
    }

    public String getAP_MAT() {
        return AP_MAT;
    }

    public void setAP_MAT(String AP_MAT) {
        this.AP_MAT = AP_MAT;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public String getHoras() {
        return Horas;
    }

    public void setHoras(String Horas) {
        this.Horas = Horas;
    }

    public String getNivel() {
        return Nivel;
    }

    public void setNivel(String Nivel) {
        this.Nivel = Nivel;
    }

    public String getFechaOld() {
        return FechaOld;
    }

    public void setFechaOld(String FechaOld) {
        this.FechaOld = FechaOld;
    }

    public String getTipoPer() {
        return TipoPer;
    }

    public void setTipoPer(String TipoPer) {
        this.TipoPer = TipoPer;
    }

    public String getZona() {
        return Zona;
    }

    public void setZona(String Zona) {
        this.Zona = Zona;
    }

    public String getSemestre() {
        return Semestre;
    }

    public void setSemestre(String Semestre) {
        this.Semestre = Semestre;
    }

    public String getTPlantel() {
        return TPlantel;
    }

    public void setTPlantel(String TPlantel) {
        this.TPlantel = TPlantel;
    }

    public String getTipoSin() {
        return TipoSin;
    }

    public void setTipoSin(String TipoSin) {
        this.TipoSin = TipoSin;
    }

    public String getMutual() {
        return Mutual;
    }

    public void setMutual(String Mutual) {
        this.Mutual = Mutual;
    }

    public String getFacore() {
        return Facore;
    }

    public void setFacore(String Facore) {
        this.Facore = Facore;
    }

    public String getCedulaImss() {
        return CedulaImss;
    }

    public void setCedulaImss(String CedulaImss) {
        this.CedulaImss = CedulaImss;
    }

    public String getRegimenPensionario() {
        return RegimenPensionario;
    }

    public void setRegimenPensionario(String RegimenPensionario) {
        this.RegimenPensionario = RegimenPensionario;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getNumeroquincena() {
        return Numeroquincena;
    }

    public void setNumeroquincena(int Numeroquincena) {
        this.Numeroquincena = Numeroquincena;
    }

    public float getP_01() {
        return P_01;
    }

    public void setP_01(float P_01) {
        this.P_01 = P_01;
    }

    public float getP_02() {
        return P_02;
    }

    public void setP_02(float P_02) {
        this.P_02 = P_02;
    }

    public float getP_03() {
        return P_03;
    }

    public void setP_03(float P_03) {
        this.P_03 = P_03;
    }

    public float getP_05() {
        return P_05;
    }

    public void setP_05(float P_05) {
        this.P_05 = P_05;
    }

    public float getP_06() {
        return P_06;
    }

    public void setP_06(float P_06) {
        this.P_06 = P_06;
    }

    public float getP_07() {
        return P_07;
    }

    public void setP_07(float P_07) {
        this.P_07 = P_07;
    }

    public float getP_08() {
        return P_08;
    }

    public void setP_08(float P_08) {
        this.P_08 = P_08;
    }

    public float getP_09() {
        return P_09;
    }

    public void setP_09(float P_09) {
        this.P_09 = P_09;
    }

    public float getP_10() {
        return P_10;
    }

    public void setP_10(float P_10) {
        this.P_10 = P_10;
    }

    public float getP_11() {
        return P_11;
    }

    public void setP_11(float P_11) {
        this.P_11 = P_11;
    }

    public float getP_12() {
        return P_12;
    }

    public void setP_12(float P_12) {
        this.P_12 = P_12;
    }

    public float getP_13() {
        return P_13;
    }

    public void setP_13(float P_13) {
        this.P_13 = P_13;
    }

    public float getP_14() {
        return P_14;
    }

    public void setP_14(float P_14) {
        this.P_14 = P_14;
    }

    public float getP_15() {
        return P_15;
    }

    public void setP_15(float P_15) {
        this.P_15 = P_15;
    }

    public float getP_16() {
        return P_16;
    }

    public void setP_16(float P_16) {
        this.P_16 = P_16;
    }

    public float getP_17() {
        return P_17;
    }

    public void setP_17(float P_17) {
        this.P_17 = P_17;
    }

    public float getP_18() {
        return P_18;
    }

    public void setP_18(float P_18) {
        this.P_18 = P_18;
    }

    public float getP_19() {
        return P_19;
    }

    public void setP_19(float P_19) {
        this.P_19 = P_19;
    }

    public float getP_20() {
        return P_20;
    }

    public void setP_20(float P_20) {
        this.P_20 = P_20;
    }

    public float getP_21() {
        return P_21;
    }

    public void setP_21(float P_21) {
        this.P_21 = P_21;
    }

    public float getP_22() {
        return P_22;
    }

    public void setP_22(float P_22) {
        this.P_22 = P_22;
    }

    public float getP_23() {
        return P_23;
    }

    public void setP_23(float P_23) {
        this.P_23 = P_23;
    }

    public float getP_24() {
        return P_24;
    }

    public void setP_24(float P_24) {
        this.P_24 = P_24;
    }

    public float getP_25() {
        return P_25;
    }

    public void setP_25(float P_25) {
        this.P_25 = P_25;
    }

    public float getP_26() {
        return P_26;
    }

    public void setP_26(float P_26) {
        this.P_26 = P_26;
    }

    public float getP_27() {
        return P_27;
    }

    public void setP_27(float P_27) {
        this.P_27 = P_27;
    }

    public float getP_28() {
        return P_28;
    }

    public void setP_28(float P_28) {
        this.P_28 = P_28;
    }

    public float getP_29() {
        return P_29;
    }

    public void setP_29(float P_29) {
        this.P_29 = P_29;
    }

    public float getP_30() {
        return P_30;
    }

    public void setP_30(float P_30) {
        this.P_30 = P_30;
    }

    public float getP_31() {
        return P_31;
    }

    public void setP_31(float P_31) {
        this.P_31 = P_31;
    }

    public float getP_32() {
        return P_32;
    }

    public void setP_32(float P_32) {
        this.P_32 = P_32;
    }

    public float getP_33() {
        return P_33;
    }

    public void setP_33(float P_33) {
        this.P_33 = P_33;
    }

    public float getP_34() {
        return P_34;
    }

    public void setP_34(float P_34) {
        this.P_34 = P_34;
    }

    public float getP_35() {
        return P_35;
    }

    public void setP_35(float P_35) {
        this.P_35 = P_35;
    }

    public float getP_36() {
        return P_36;
    }

    public void setP_36(float P_36) {
        this.P_36 = P_36;
    }

    public float getP_37() {
        return P_37;
    }

    public void setP_37(float P_37) {
        this.P_37 = P_37;
    }

    public float getP_38() {
        return P_38;
    }

    public void setP_38(float P_38) {
        this.P_38 = P_38;
    }

    public float getP_39() {
        return P_39;
    }

    public void setP_39(float P_39) {
        this.P_39 = P_39;
    }

    public float getP_40() {
        return P_40;
    }

    public void setP_40(float P_40) {
        this.P_40 = P_40;
    }

    public float getP_41() {
        return P_41;
    }

    public void setP_41(float P_41) {
        this.P_41 = P_41;
    }

    public float getP_42() {
        return P_42;
    }

    public void setP_42(float P_42) {
        this.P_42 = P_42;
    }

    public float getP_43() {
        return P_43;
    }

    public void setP_43(float P_43) {
        this.P_43 = P_43;
    }

    public float getP_44() {
        return P_44;
    }

    public void setP_44(float P_44) {
        this.P_44 = P_44;
    }

    public float getP_45() {
        return P_45;
    }

    public void setP_45(float P_45) {
        this.P_45 = P_45;
    }

    public float getP_46() {
        return P_46;
    }

    public void setP_46(float P_46) {
        this.P_46 = P_46;
    }

    public float getP_47() {
        return P_47;
    }

    public void setP_47(float P_47) {
        this.P_47 = P_47;
    }

    public float getP_48() {
        return P_48;
    }

    public void setP_48(float P_48) {
        this.P_48 = P_48;
    }

    public float getP_49() {
        return P_49;
    }

    public void setP_49(float P_49) {
        this.P_49 = P_49;
    }

    public float getP_50() {
        return P_50;
    }

    public void setP_50(float P_50) {
        this.P_50 = P_50;
    }

    public float getP_51() {
        return P_51;
    }

    public void setP_51(float P_51) {
        this.P_51 = P_51;
    }

    public float getP_52() {
        return P_52;
    }

    public void setP_52(float P_52) {
        this.P_52 = P_52;
    }

    public float getP_53() {
        return P_53;
    }

    public void setP_53(float P_53) {
        this.P_53 = P_53;
    }

    public float getP_54() {
        return P_54;
    }

    public void setP_54(float P_54) {
        this.P_54 = P_54;
    }

    public float getP_55() {
        return P_55;
    }

    public void setP_55(float P_55) {
        this.P_55 = P_55;
    }

    public float getP_56() {
        return P_56;
    }

    public void setP_56(float P_56) {
        this.P_56 = P_56;
    }

    public float getP_57() {
        return P_57;
    }

    public void setP_57(float P_57) {
        this.P_57 = P_57;
    }

    public float getP_58() {
        return P_58;
    }

    public void setP_58(float P_58) {
        this.P_58 = P_58;
    }

    public float getP_59() {
        return P_59;
    }

    public void setP_59(float P_59) {
        this.P_59 = P_59;
    }

    public float getP_60() {
        return P_60;
    }

    public void setP_60(float P_60) {
        this.P_60 = P_60;
    }

    public float getP_01A() {
        return P_01A;
    }

    public void setP_01A(float P_01A) {
        this.P_01A = P_01A;
    }

    public float getP_01B() {
        return P_01B;
    }

    public void setP_01B(float P_01B) {
        this.P_01B = P_01B;
    }

    public float getP_01C() {
        return P_01C;
    }

    public void setP_01C(float P_01C) {
        this.P_01C = P_01C;
    }

    public float getP_01D() {
        return P_01D;
    }

    public void setP_01D(float P_01D) {
        this.P_01D = P_01D;
    }

    public float getP_01E() {
        return P_01E;
    }

    public void setP_01E(float P_01E) {
        this.P_01E = P_01E;
    }

    public float getD_01() {
        return D_01;
    }

    public void setD_01(float D_01) {
        this.D_01 = D_01;
    }

    public float getD_02() {
        return D_02;
    }

    public void setD_02(float D_02) {
        this.D_02 = D_02;
    }

    public float getD_03() {
        return D_03;
    }

    public void setD_03(float D_03) {
        this.D_03 = D_03;
    }

    public float getD_05() {
        return D_05;
    }

    public void setD_05(float D_05) {
        this.D_05 = D_05;
    }

    public float getD_06() {
        return D_06;
    }

    public void setD_06(float D_06) {
        this.D_06 = D_06;
    }

    public float getD_07() {
        return D_07;
    }

    public void setD_07(float D_07) {
        this.D_07 = D_07;
    }

    public float getD_08() {
        return D_08;
    }

    public void setD_08(float D_08) {
        this.D_08 = D_08;
    }

    public float getD_09() {
        return D_09;
    }

    public void setD_09(float D_09) {
        this.D_09 = D_09;
    }

    public float getD_10() {
        return D_10;
    }

    public void setD_10(float D_10) {
        this.D_10 = D_10;
    }

    public float getD_11() {
        return D_11;
    }

    public void setD_11(float D_11) {
        this.D_11 = D_11;
    }

    public float getD_12() {
        return D_12;
    }

    public void setD_12(float D_12) {
        this.D_12 = D_12;
    }

    public float getD_13() {
        return D_13;
    }

    public void setD_13(float D_13) {
        this.D_13 = D_13;
    }

    public float getD_14() {
        return D_14;
    }

    public void setD_14(float D_14) {
        this.D_14 = D_14;
    }

    public float getD_15() {
        return D_15;
    }

    public void setD_15(float D_15) {
        this.D_15 = D_15;
    }

    public float getD_16() {
        return D_16;
    }

    public void setD_16(float D_16) {
        this.D_16 = D_16;
    }

    public float getD_17() {
        return D_17;
    }

    public void setD_17(float D_17) {
        this.D_17 = D_17;
    }

    public float getD_18() {
        return D_18;
    }

    public void setD_18(float D_18) {
        this.D_18 = D_18;
    }

    public float getD_19() {
        return D_19;
    }

    public void setD_19(float D_19) {
        this.D_19 = D_19;
    }

    public float getD_20() {
        return D_20;
    }

    public void setD_20(float D_20) {
        this.D_20 = D_20;
    }

    public float getD_21() {
        return D_21;
    }

    public void setD_21(float D_21) {
        this.D_21 = D_21;
    }

    public float getD_22() {
        return D_22;
    }

    public void setD_22(float D_22) {
        this.D_22 = D_22;
    }

    public float getD_23() {
        return D_23;
    }

    public void setD_23(float D_23) {
        this.D_23 = D_23;
    }

    public float getD_24() {
        return D_24;
    }

    public void setD_24(float D_24) {
        this.D_24 = D_24;
    }

    public float getD_25() {
        return D_25;
    }

    public void setD_25(float D_25) {
        this.D_25 = D_25;
    }

    public float getD_26() {
        return D_26;
    }

    public void setD_26(float D_26) {
        this.D_26 = D_26;
    }

    public float getD_27() {
        return D_27;
    }

    public void setD_27(float D_27) {
        this.D_27 = D_27;
    }

    public float getD_28() {
        return D_28;
    }

    public void setD_28(float D_28) {
        this.D_28 = D_28;
    }

    public float getD_29() {
        return D_29;
    }

    public void setD_29(float D_29) {
        this.D_29 = D_29;
    }

    public float getD_30() {
        return D_30;
    }

    public void setD_30(float D_30) {
        this.D_30 = D_30;
    }

    public float getD_31() {
        return D_31;
    }

    public void setD_31(float D_31) {
        this.D_31 = D_31;
    }

    public float getD_32() {
        return D_32;
    }

    public void setD_32(float D_32) {
        this.D_32 = D_32;
    }

    public float getD_33() {
        return D_33;
    }

    public void setD_33(float D_33) {
        this.D_33 = D_33;
    }

    public float getD_34() {
        return D_34;
    }

    public void setD_34(float D_34) {
        this.D_34 = D_34;
    }

    public float getD_35() {
        return D_35;
    }

    public void setD_35(float D_35) {
        this.D_35 = D_35;
    }

    public float getD_36() {
        return D_36;
    }

    public void setD_36(float D_36) {
        this.D_36 = D_36;
    }

    public float getD_37() {
        return D_37;
    }

    public void setD_37(float D_37) {
        this.D_37 = D_37;
    }

    public float getD_38() {
        return D_38;
    }

    public void setD_38(float D_38) {
        this.D_38 = D_38;
    }

    public float getD_39() {
        return D_39;
    }

    public void setD_39(float D_39) {
        this.D_39 = D_39;
    }

    public float getD_40() {
        return D_40;
    }

    public void setD_40(float D_40) {
        this.D_40 = D_40;
    }

    public float getD_41() {
        return D_41;
    }

    public void setD_41(float D_41) {
        this.D_41 = D_41;
    }

    public float getD_42() {
        return D_42;
    }

    public void setD_42(float D_42) {
        this.D_42 = D_42;
    }

    public float getD_43() {
        return D_43;
    }

    public void setD_43(float D_43) {
        this.D_43 = D_43;
    }

    public float getD_44() {
        return D_44;
    }

    public void setD_44(float D_44) {
        this.D_44 = D_44;
    }

    public float getD_45() {
        return D_45;
    }

    public void setD_45(float D_45) {
        this.D_45 = D_45;
    }

    public float getD_46() {
        return D_46;
    }

    public void setD_46(float D_46) {
        this.D_46 = D_46;
    }

    public float getD_47() {
        return D_47;
    }

    public void setD_47(float D_47) {
        this.D_47 = D_47;
    }

    public float getD_48() {
        return D_48;
    }

    public void setD_48(float D_48) {
        this.D_48 = D_48;
    }

    public float getD_49() {
        return D_49;
    }

    public void setD_49(float D_49) {
        this.D_49 = D_49;
    }

    public float getD_50() {
        return D_50;
    }

    public void setD_50(float D_50) {
        this.D_50 = D_50;
    }

    public float getD_51() {
        return D_51;
    }

    public void setD_51(float D_51) {
        this.D_51 = D_51;
    }

    public float getD_52() {
        return D_52;
    }

    public void setD_52(float D_52) {
        this.D_52 = D_52;
    }

    public float getD_53() {
        return D_53;
    }

    public void setD_53(float D_53) {
        this.D_53 = D_53;
    }

    public float getD_54() {
        return D_54;
    }

    public void setD_54(float D_54) {
        this.D_54 = D_54;
    }

    public float getD_55() {
        return D_55;
    }

    public void setD_55(float D_55) {
        this.D_55 = D_55;
    }

    public float getD_56() {
        return D_56;
    }

    public void setD_56(float D_56) {
        this.D_56 = D_56;
    }

    public float getD_57() {
        return D_57;
    }

    public void setD_57(float D_57) {
        this.D_57 = D_57;
    }

    public float getD_CB() {
        return D_CB;
    }

    public void setD_CB(float D_CB) {
        this.D_CB = D_CB;
    }

    public float getP_MC() {
        return P_MC;
    }

    public void setP_MC(float P_MC) {
        this.P_MC = P_MC;
    }

    public float getP_PG() {
        return P_PG;
    }

    public void setP_PG(float P_PG) {
        this.P_PG = P_PG;
    }

    public float getP_PE() {
        return P_PE;
    }

    public void setP_PE(float P_PE) {
        this.P_PE = P_PE;
    }

    public int getAyudaMutua() {
        return AyudaMutua;
    }

    public void setAyudaMutua(int AyudaMutua) {
        this.AyudaMutua = AyudaMutua;
    }

    public float getPorcentajeseccion31() {
        return porcentajeseccion31;
    }

    public void setPorcentajeseccion31(float porcentajeseccion31) {
        this.porcentajeseccion31 = porcentajeseccion31;
    }

    public float getPorcentajeseccion63() {
        return porcentajeseccion63;
    }

    public void setPorcentajeseccion63(float porcentajeseccion63) {
        this.porcentajeseccion63 = porcentajeseccion63;
    }

    public float getPorcentajesitcobach() {
        return porcentajesitcobach;
    }

    public void setPorcentajesitcobach(float porcentajesitcobach) {
        this.porcentajesitcobach = porcentajesitcobach;
    }

    public float getPorcentajesutcobach() {
        return porcentajesutcobach;
    }

    public void setPorcentajesutcobach(float porcentajesutcobach) {
        this.porcentajesutcobach = porcentajesutcobach;
    }

    public float getPorcentajeissste() {
        return porcentajeissste;
    }

    public void setPorcentajeissste(float porcentajeissste) {
        this.porcentajeissste = porcentajeissste;
    }

    public int getHorasSems() {
        return HorasSems;
    }

    public void setHorasSems(int HorasSems) {
        this.HorasSems = HorasSems;
    }

    public int getHorasBase() {
        return HorasBase;
    }

    public void setHorasBase(int HorasBase) {
        this.HorasBase = HorasBase;
    }

    public int getHorasLimitadas() {
        return HorasLimitadas;
    }

    public void setHorasLimitadas(int HorasLimitadas) {
        this.HorasLimitadas = HorasLimitadas;
    }

    public int getHorasInterinas() {
        return HorasInterinas;
    }

    public void setHorasInterinas(int HorasInterinas) {
        this.HorasInterinas = HorasInterinas;
    }

    public String getCurp() {
        return Curp;
    }

    public void setCurp(String Curp) {
        this.Curp = Curp;
    }

    public float getPorcentajeHipotecarioIssste() {
        return porcentajeHipotecarioIssste;
    }

    public void setPorcentajeHipotecarioIssste(float porcentajeHipotecarioIssste) {
        this.porcentajeHipotecarioIssste = porcentajeHipotecarioIssste;
    }

    public int getNumeroInasistencias() {
        return NumeroInasistencias;
    }

    public void setNumeroInasistencias(int NumeroInasistencias) {
        this.NumeroInasistencias = NumeroInasistencias;
    }

    public String getFechaInasistencias() {
        return FechaInasistencias;
    }

    public void setFechaInasistencias(String FechaInasistencias) {
        this.FechaInasistencias = FechaInasistencias;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivoDeBaja() {
        return MotivoDeBaja;
    }

    public void setMotivoDeBaja(String MotivoDeBaja) {
        this.MotivoDeBaja = MotivoDeBaja;
    }

    public String getFechaDeBaja() {
        return FechaDeBaja;
    }

    public void setFechaDeBaja(String FechaDeBaja) {
        this.FechaDeBaja = FechaDeBaja;
    }

    public String getFechaDeReactivacion() {
        return FechaDeReactivacion;
    }

    public void setFechaDeReactivacion(String FechaDeReactivacion) {
        this.FechaDeReactivacion = FechaDeReactivacion;
    }

    public String getNumeroPlantel() {
        return NumeroPlantel;
    }

    public void setNumeroPlantel(String NumeroPlantel) {
        this.NumeroPlantel = NumeroPlantel;
    }

    public String getNombrePlantel() {
        return NombrePlantel;
    }

    public void setNombrePlantel(String NombrePlantel) {
        this.NombrePlantel = NombrePlantel;
    }

    public String getCveCategoria() {
        return CveCategoria;
    }

    public void setCveCategoria(String CveCategoria) {
        this.CveCategoria = CveCategoria;
    }

    public float getD_58() {
        return D_58;
    }

    public void setD_58(float D_58) {
        this.D_58 = D_58;
    }

    public float getD_59() {
        return D_59;
    }

    public void setD_59(float D_59) {
        this.D_59 = D_59;
    }

    public float getD_60() {
        return D_60;
    }

    public void setD_60(float D_60) {
        this.D_60 = D_60;
    }

    public float getP_04() {
        return P_04;
    }

    public void setP_04(float P_04) {
        this.P_04 = P_04;
    }

    public float getD_04() {
        return D_04;
    }

    public void setD_04(float D_04) {
        this.D_04 = D_04;
    }

    public float getP_DI() {
        return P_DI;
    }

    public void setP_DI(float P_DI) {
        this.P_DI = P_DI;
    }

    public float getP_C1() {
        return P_C1;
    }

    public void setP_C1(float P_C1) {
        this.P_C1 = P_C1;
    }

    public float getP_C2() {
        return P_C2;
    }

    public void setP_C2(float P_C2) {
        this.P_C2 = P_C2;
    }

    public float getD_01A() {
        return D_01A;
    }

    public void setD_01A(float D_01A) {
        this.D_01A = D_01A;
    }

    public String getFechaModificacionissste() {
        return FechaModificacionissste;
    }

    public void setFechaModificacionissste(String FechaModificacionissste) {
        this.FechaModificacionissste = FechaModificacionissste;
    }

    public String getSaldoissste() {
        return Saldoissste;
    }

    public void setSaldoissste(String Saldoissste) {
        this.Saldoissste = Saldoissste;
    }

    public String getNumeroDePago() {
        return NumeroDePago;
    }

    public void setNumeroDePago(String NumeroDePago) {
        this.NumeroDePago = NumeroDePago;
    }

    public String getTotalDePago() {
        return TotalDePago;
    }

    public void setTotalDePago(String TotalDePago) {
        this.TotalDePago = TotalDePago;
    }

    public String getFolioissste() {
        return Folioissste;
    }

    public void setFolioissste(String Folioissste) {
        this.Folioissste = Folioissste;
    }

    public String getNumeroDeContrato() {
        return NumeroDeContrato;
    }

    public void setNumeroDeContrato(String NumeroDeContrato) {
        this.NumeroDeContrato = NumeroDeContrato;
    }

    public String getTipoContrato() {
        return TipoContrato;
    }

    public void setTipoContrato(String TipoContrato) {
        this.TipoContrato = TipoContrato;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

    public String getFechaDeBajaIssste() {
        return FechaDeBajaIssste;
    }

    public void setFechaDeBajaIssste(String FechaDeBajaIssste) {
        this.FechaDeBajaIssste = FechaDeBajaIssste;
    }

    public String getCveAyudaMutua() {
        return CveAyudaMutua;
    }

    public void setCveAyudaMutua(String CveAyudaMutua) {
        this.CveAyudaMutua = CveAyudaMutua;
    }

    public String getCveCuotaSindical() {
        return CveCuotaSindical;
    }

    public void setCveCuotaSindical(String CveCuotaSindical) {
        this.CveCuotaSindical = CveCuotaSindical;
    }

    public String getCajaSutcobach() {
        return CajaSutcobach;
    }

    public void setCajaSutcobach(String CajaSutcobach) {
        this.CajaSutcobach = CajaSutcobach;
    }

    public String getCajaSeccion() {
        return CajaSeccion;
    }

    public void setCajaSeccion(String CajaSeccion) {
        this.CajaSeccion = CajaSeccion;
    }

    public String getCajaSitcobach() {
        return CajaSitcobach;
    }

    public void setCajaSitcobach(String CajaSitcobach) {
        this.CajaSitcobach = CajaSitcobach;
    }

    public String getCajaSeccion63() {
        return CajaSeccion63;
    }

    public void setCajaSeccion63(String CajaSeccion63) {
        this.CajaSeccion63 = CajaSeccion63;
    }

    public int getTotalDias() {
        return TotalDias;
    }

    public void setTotalDias(int TotalDias) {
        this.TotalDias = TotalDias;
    }

    public String getStatusissste() {
        return statusissste;
    }

    public void setStatusissste(String statusissste) {
        this.statusissste = statusissste;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public Float getMontocuotasindical() {
        return montocuotasindical;
    }

    public void setMontocuotasindical(Float montocuotasindical) {
        this.montocuotasindical = montocuotasindical;
    }

    public int getNumerodeplazas() {
        return numerodeplazas;
    }

    public void setNumerodeplazas(int numerodeplazas) {
        this.numerodeplazas = numerodeplazas;
    }

    public Double getAntiguedadsuma() {
        return antiguedadsuma;
    }

    public void setAntiguedadsuma(Double antiguedadsuma) {
        this.antiguedadsuma = antiguedadsuma;
    }

    public Double getSalariosuma() {
        return salariosuma;
    }

    public void setSalariosuma(Double salariosuma) {
        this.salariosuma = salariosuma;
    }

    public Double getSumap18() {
        return sumap18;
    }

    public void setSumap18(Double sumap18) {
        this.sumap18 = sumap18;
    }

    public Double getSumap19() {
        return sumap19;
    }

    public void setSumap19(Double sumap19) {
        this.sumap19 = sumap19;
    }

    public int getNumerodeempleadoganamas() {
        return numerodeempleadoganamas;
    }

    public void setNumerodeempleadoganamas(int numerodeempleadoganamas) {
        this.numerodeempleadoganamas = numerodeempleadoganamas;
    }

    public Double getD_01masdeunaplaza() {
        return D_01masdeunaplaza;
    }

    public void setD_01masdeunaplaza(Double D_01masdeunaplaza) {
        this.D_01masdeunaplaza = D_01masdeunaplaza;
    }

    public Double getD_03masdeunaplaza() {
        return D_03masdeunaplaza;
    }

    public void setD_03masdeunaplaza(Double D_03masdeunaplaza) {
        this.D_03masdeunaplaza = D_03masdeunaplaza;
    }

    public Double getD_04masdeunaplaza() {
        return D_04masdeunaplaza;
    }

    public void setD_04masdeunaplaza(Double D_04masdeunaplaza) {
        this.D_04masdeunaplaza = D_04masdeunaplaza;
    }

    public Double getD_21masdeunaplaza() {
        return D_21masdeunaplaza;
    }

    public void setD_21masdeunaplaza(Double D_21masdeunaplaza) {
        this.D_21masdeunaplaza = D_21masdeunaplaza;
    }

    public Double getD_22masdeunaplaza() {
        return D_22masdeunaplaza;
    }

    public void setD_22masdeunaplaza(Double D_22masdeunaplaza) {
        this.D_22masdeunaplaza = D_22masdeunaplaza;
    }

    public Double getD_23masdeunaplaza() {
        return D_23masdeunaplaza;
    }

    public void setD_23masdeunaplaza(Double D_23masdeunaplaza) {
        this.D_23masdeunaplaza = D_23masdeunaplaza;
    }

    public Double getP_14masdeunaplaza() {
        return P_14masdeunaplaza;
    }

    public void setP_14masdeunaplaza(Double P_14masdeunaplaza) {
        this.P_14masdeunaplaza = P_14masdeunaplaza;
    }

    public Double getPercepcionesEmpleado() {
        return PercepcionesEmpleado;
    }

    public void setPercepcionesEmpleado(Double PercepcionesEmpleado) {
        this.PercepcionesEmpleado = PercepcionesEmpleado;
    }

    public Double getDeduccionesEmpleado() {
        return DeduccionesEmpleado;
    }

    public void setDeduccionesEmpleado(Double DeduccionesEmpleado) {
        this.DeduccionesEmpleado = DeduccionesEmpleado;
    }

    public Double getNetoEmpleado() {
        return NetoEmpleado;
    }

    public void setNetoEmpleado(Double NetoEmpleado) {
        this.NetoEmpleado = NetoEmpleado;
    }

    public Double getSalarioBase() {
        return SalarioBase;
    }

    public void setSalarioBase(Double SalarioBase) {
        this.SalarioBase = SalarioBase;
    }

    public String getRuta() {
        return Ruta;
    }

    public void setRuta(String Ruta) {
        this.Ruta = Ruta;
    }

    public String getClaveImportar() {
        return ClaveImportar;
    }

    public void setClaveImportar(String ClaveImportar) {
        this.ClaveImportar = ClaveImportar;
    }

    public String getNumeroQuincenaImportar() {
        return NumeroQuincenaImportar;
    }

    public void setNumeroQuincenaImportar(String NumeroQuincenaImportar) {
        this.NumeroQuincenaImportar = NumeroQuincenaImportar;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    public Double getBaseissste() {
        return baseissste;
    }

    public void setBaseissste(Double baseissste) {
        this.baseissste = baseissste;
    }

    public Double getSumaSalarioBase() {
        return sumaSalarioBase;
    }

    public void setSumaSalarioBase(Double sumaSalarioBase) {
        this.sumaSalarioBase = sumaSalarioBase;
    }

    public Double getSumap1() {
        return sumap1;
    }

    public void setSumap1(Double sumap1) {
        this.sumap1 = sumap1;
    }

    public Double getSumap1a() {
        return sumap1a;
    }

    public void setSumap1a(Double sumap1a) {
        this.sumap1a = sumap1a;
    }

    public Double getSumap1b() {
        return sumap1b;
    }

    public void setSumap1b(Double sumap1b) {
        this.sumap1b = sumap1b;
    }

    public Double getSumap1c() {
        return sumap1c;
    }

    public void setSumap1c(Double sumap1c) {
        this.sumap1c = sumap1c;
    }

    public Double getSumap1d() {
        return sumap1d;
    }

    public void setSumap1d(Double sumap1d) {
        this.sumap1d = sumap1d;
    }

    public Double getSumap1e() {
        return sumap1e;
    }

    public void setSumap1e(Double sumap1e) {
        this.sumap1e = sumap1e;
    }

    public Double getPercepcionesSinCalculoEmpleado() {
        return PercepcionesSinCalculoEmpleado;
    }

    public void setPercepcionesSinCalculoEmpleado(Double PercepcionesSinCalculoEmpleado) {
        this.PercepcionesSinCalculoEmpleado = PercepcionesSinCalculoEmpleado;
    }

    public Double getDeduccionesSinCalculoEmpleado() {
        return DeduccionesSinCalculoEmpleado;
    }

    public void setDeduccionesSinCalculoEmpleado(Double DeduccionesSinCalculoEmpleado) {
        this.DeduccionesSinCalculoEmpleado = DeduccionesSinCalculoEmpleado;
    }

    public Double getP14sinplanNeto() {
        return P14sinplanNeto;
    }

    public void setP14sinplanNeto(Double P14sinplanNeto) {
        this.P14sinplanNeto = P14sinplanNeto;
    }

    public Double getD01sinplanNeto() {
        return D01sinplanNeto;
    }

    public void setD01sinplanNeto(Double D01sinplanNeto) {
        this.D01sinplanNeto = D01sinplanNeto;
    }

    public Double getP01Update() {
        return P01Update;
    }

    public void setP01Update(Double P01Update) {
        this.P01Update = P01Update;
    }

    public Double getP01AUpdate() {
        return P01AUpdate;
    }

    public void setP01AUpdate(Double P01AUpdate) {
        this.P01AUpdate = P01AUpdate;
    }

    public Double getP01BUpdate() {
        return P01BUpdate;
    }

    public void setP01BUpdate(Double P01BUpdate) {
        this.P01BUpdate = P01BUpdate;
    }

    public Double getP01CUpdate() {
        return P01CUpdate;
    }

    public void setP01CUpdate(Double P01CUpdate) {
        this.P01CUpdate = P01CUpdate;
    }

    public Double getP01DUpdate() {
        return P01DUpdate;
    }

    public void setP01DUpdate(Double P01DUpdate) {
        this.P01DUpdate = P01DUpdate;
    }

    public Double getP01EUpdate() {
        return P01EUpdate;
    }

    public void setP01EUpdate(Double P01EUpdate) {
        this.P01EUpdate = P01EUpdate;
    }

    public Double getP018Update() {
        return P018Update;
    }

    public void setP018Update(Double P018Update) {
        this.P018Update = P018Update;
    }

    public Double getP019Update() {
        return P019Update;
    }

    public void setP019Update(Double P019Update) {
        this.P019Update = P019Update;
    }

    public Double getPPEUpdate() {
        return PPEUpdate;
    }

    public void setPPEUpdate(Double PPEUpdate) {
        this.PPEUpdate = PPEUpdate;
    }

    public Double getPPGUpdate() {
        return PPGUpdate;
    }

    public void setPPGUpdate(Double PPGUpdate) {
        this.PPGUpdate = PPGUpdate;
    }

    public Double getP14Update() {
        return P14Update;
    }

    public void setP14Update(Double P14Update) {
        this.P14Update = P14Update;
    }

    public Double getD01Update() {
        return D01Update;
    }

    public void setD01Update(Double D01Update) {
        this.D01Update = D01Update;
    }
    
    
    
}
