package Controlador;

import Modelo.CalculoRetiro;
import java.awt.event.ActionListener;
import Modelo.ConsultasCalculo;
import Modelo.Issste;
import Modelo.SalarioMinimo;
import Modelo.TablaISR;

import View.frmCalculoRetiro;
import java.awt.event.ActionEvent;

/**
 *
 * @author SERCOM
 */
public class ctrlCalculoRetiro implements ActionListener {

    private CalculoRetiro calculoretiro;
    private ConsultasCalculo consultascalculo;
    private frmCalculoRetiro frmCalculoRetiro;
    private Issste cuotas;
    private SalarioMinimo minimo;
    private TablaISR isrtabla;

    public ctrlCalculoRetiro(CalculoRetiro calculoretiro, ConsultasCalculo consultascalculo, frmCalculoRetiro frmCalculoRetiro,
            Issste cuotas, SalarioMinimo minimo, TablaISR isrtabla) {
        this.calculoretiro = calculoretiro;
        this.consultascalculo = consultascalculo;
        this.frmCalculoRetiro = frmCalculoRetiro;
        this.minimo = minimo;
        this.isrtabla = isrtabla;
        this.cuotas = cuotas;
        this.frmCalculoRetiro.btnCalcular.addActionListener(this);
        this.frmCalculoRetiro.btnImprimir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String nombreEmpleado;
        Double CalculoPorcentaje = 0.0;
        Double PorcentajeFondo = 21.56;
        Double IsrCalculo = 0.0;
        Double factor = 1.33456733;
        Double baseparaisr;
        Double baseparaisste;
        Double p1;
        Double p3;
        Double p14;
        Double p18;
        Double p19;
        Double ppg;
        Double ppe;
        Double d01;
        Double sumaissste;
        Double sumadiferencias;
        Double aportacionanual;
        int periodoinicio = 0;
        int periodofinal = 0;
        int periododecotizacion = 19;
        int periodosalario = 2015;
        int periodotablasisr = 2015;
        Double aportaciontotal;

        Double p1cp = 0.0;
        Double p3cp = 0.0;
        Double p14cp = 0.0;
        Double p18cp = 0.0;
        Double p19cp = 0.0;
        Double ppgcp = 0.0;
        Double ppecp = 0.0;
        Double d01cp = 1364.57;
        Double sumaissstecp = 0.0;
        Double calculosumaissste = 0.0;

        Double p1dif = 0.0;
        Double p3dif = 0.0;
        Double p14dif = 0.0;
        Double p18dif = 0.0;
        Double p19dif = 0.0;
        Double ppgdif = 0.0;
        Double ppedif = 0.0;
        Double d01dif = 0.0;
        Double sumaissstedif = 0.0;

        Double SalarioIsr = 0.0;
        Double UnSalarioIssste = 0.0;
        Double DiezSalariosIssste = 0.0;

        //Cuotas ISSSTE
        Double D_03 = 0.0;
        Double D_04 = 0.0;
        Double D_21 = 0.0;
        Double D_22 = 0.0;
        Double D_23 = 0.0;
        Double D_03R = 0.0;
        Double D_04R = 0.0;
        Double D_21R = 0.0;
        Double D_22R = 0.0;
        Double D_23R = 0.0;
        Double salariom = 0.00;
        Double deudas =  0.0;
        Double totalfinal =  0.0;

        if (e.getSource() == frmCalculoRetiro.btnCalcular) {

            nombreEmpleado = frmCalculoRetiro.txtNombre.getText();
            periodosalario = Integer.parseInt(frmCalculoRetiro.txtSalarioMinimo.getText());
            periodotablasisr = Integer.parseInt(frmCalculoRetiro.txtTablaIsr.getText());
            periodoinicio = Integer.parseInt(frmCalculoRetiro.txtinicio.getText());
            if (periodoinicio < 1997)
            {
            periodoinicio = 1997;
            }
            periodofinal = Integer.parseInt(frmCalculoRetiro.txtfin.getText());
            periododecotizacion = periodofinal - periodoinicio;
            p1 = Double.parseDouble(frmCalculoRetiro.txtp1nomina.getText());
            p3 = Double.parseDouble(frmCalculoRetiro.txtp3nomina.getText());
            p14 = Double.parseDouble(frmCalculoRetiro.txtp14nomina.getText());
            p18 = Double.parseDouble(frmCalculoRetiro.txtp18nomina.getText());
            p19 = Double.parseDouble(frmCalculoRetiro.txtp19nomina.getText());
            ppg = Double.parseDouble(frmCalculoRetiro.txtppgnomina.getText());
            ppe = Double.parseDouble(frmCalculoRetiro.txtppenomina.getText());
            d01 = Double.parseDouble(frmCalculoRetiro.txtd01nomina.getText());
            sumaissste = Double.parseDouble(frmCalculoRetiro.txtissstenomina.getText());
            deudas = Double.parseDouble(frmCalculoRetiro.txtDeudas.getText());
           deudas = Double.parseDouble(String.format("%.2f", deudas));
           
            p1cp = p1 * factor;
            p1cp = Double.parseDouble(String.format("%.2f", p1cp));

            p3cp = p3;
            p19cp = p19 * factor;
            p19cp = Double.parseDouble(String.format("%.2f", p19cp));

            p18cp = p18 * factor;
             p18cp = Double.parseDouble(String.format("%.2f", p18cp));

            baseparaisr = (p1cp + p18cp + p19cp) * 2;
            baseparaisste = p1cp + p3;
            //SalarioIsr = (SalarioBase + SumaP18 + SumaP19)*2;

            minimo.setYear(periodosalario);
            if (consultascalculo.salariominimo(minimo)) {
                salariom = Double.parseDouble(minimo.getZonaa());
                UnSalarioIssste = salariom * 15;
                DiezSalariosIssste = UnSalarioIssste * 10;
            }

            if (baseparaisste > DiezSalariosIssste) {
                baseparaisste = DiezSalariosIssste;

            }
            if (baseparaisste < UnSalarioIssste) {
                baseparaisste = UnSalarioIssste;
            }

            if (consultascalculo.cuotasissste(cuotas)) {
                D_03 = Double.parseDouble(cuotas.getD_03());
                D_04 = Double.parseDouble(cuotas.getD_04());
                D_21 = Double.parseDouble(cuotas.getD_21());
                D_22 = Double.parseDouble(cuotas.getD_22());
                D_23 = Double.parseDouble(cuotas.getD_23());
                D_03R = baseparaisste * D_03;
                D_04R = baseparaisste * D_04;
                D_21R = baseparaisste * D_21;
                D_22R = baseparaisste * D_22;
                D_23R = baseparaisste * D_23;
                D_03R = Double.parseDouble(String.format("%.2f", D_03R));
                D_04R = Double.parseDouble(String.format("%.2f", D_04R));
                D_21R = Double.parseDouble(String.format("%.2f", D_21R));
                D_22R = Double.parseDouble(String.format("%.2f", D_22R));
                D_23R = Double.parseDouble(String.format("%.2f", D_23R));
                calculosumaissste = D_03R + D_04R + D_21R + D_22R + D_23R;
                calculosumaissste = Double.parseDouble(String.format("%.2f", calculosumaissste));

                
            }
            isrtabla.setPeriodotabla(periodotablasisr);
            isrtabla.setCalculoIsr(baseparaisr);
            if (consultascalculo.calculoisr(isrtabla)) {

                isrtabla.setCalculoIsr(baseparaisr);
                isrtabla.setIntermedio(isrtabla.getIntermedio());
                if (consultascalculo.calculosubsidio(isrtabla)) {
                    IsrCalculo = isrtabla.getRCalculoIsr();
                    if (IsrCalculo < 0) {

                        frmCalculoRetiro.txtd01pres.setText(String.valueOf(0));
                        IsrCalculo = IsrCalculo * (-1);
                        frmCalculoRetiro.txtp14pres.setText(String.valueOf(IsrCalculo));
                    } else {
                        frmCalculoRetiro.txtd01pres.setText(String.valueOf(IsrCalculo));
                        frmCalculoRetiro.txtp14pres.setText(String.valueOf(0));
                    }

                }

            }

            frmCalculoRetiro.txtp1pres.setText(String.valueOf(p1cp));
            frmCalculoRetiro.txtp3pres.setText(String.valueOf(p3cp));
            frmCalculoRetiro.txtp19pres.setText(String.valueOf(p19cp));
            frmCalculoRetiro.txtp18pres.setText(String.valueOf(p18cp));
            frmCalculoRetiro.txtppgpres.setText(String.valueOf(ppgcp));
            frmCalculoRetiro.txtppepres.setText(String.valueOf(ppecp));
            frmCalculoRetiro.txtissstepres.setText(String.valueOf(calculosumaissste));

            // frmCalculoRetiro.txtd01pres.setText(String.valueOf(aportaciontotal));
            // frmCalculoRetiro.txtp14pres.setText(String.valueOf(p14cp));
            p1dif = p1cp - p1;
            p3dif = p3cp - p3;
            p14dif = p14cp - p14;
            p18dif = p18cp - p18;
            p19dif = p19cp - p19;
            ppgdif = ppgcp - ppg;
            ppedif = ppecp - ppe;
            d01dif = IsrCalculo - d01;
            sumaissstedif = calculosumaissste - sumaissste;

            p1dif = Double.parseDouble(String.format("%.2f", p1dif));
            p3dif = Double.parseDouble(String.format("%.2f", p3dif));
            p14dif = Double.parseDouble(String.format("%.2f", p14dif));
            p18dif = Double.parseDouble(String.format("%.2f", p18dif));
            p19dif = Double.parseDouble(String.format("%.2f", p19dif));
            ppgdif = Double.parseDouble(String.format("%.2f", ppgdif));
            ppedif = Double.parseDouble(String.format("%.2f", ppedif));
            d01dif = Double.parseDouble(String.format("%.2f", d01dif));
            sumaissstedif = Double.parseDouble(String.format("%.2f", sumaissstedif));

            sumadiferencias = p1dif + p3dif + p14dif + p18dif + p19dif + ppgdif
                    + ppedif + d01dif + sumaissstedif;
            aportacionanual = sumadiferencias * 24;
            aportaciontotal = aportacionanual * periododecotizacion;

            sumadiferencias = Double.parseDouble(String.format("%.2f", sumadiferencias));
            aportacionanual = Double.parseDouble(String.format("%.2f", aportacionanual));
            aportaciontotal = Double.parseDouble(String.format("%.2f", aportaciontotal));
            
            
            CalculoPorcentaje = (aportaciontotal * PorcentajeFondo) / 100;
            CalculoPorcentaje = Double.parseDouble(String.format("%.2f", CalculoPorcentaje));
            totalfinal = CalculoPorcentaje - deudas;
            CalculoPorcentaje = Double.parseDouble(String.format("%.2f", CalculoPorcentaje));
           
            
            
            frmCalculoRetiro.txtp1dif.setText(String.valueOf(p1dif));
            frmCalculoRetiro.txtp3dif.setText(String.valueOf(p3dif));
            frmCalculoRetiro.txtp19dif.setText(String.valueOf(p19dif));
            frmCalculoRetiro.txtp14dif.setText(String.valueOf(p14dif));
            frmCalculoRetiro.txtp18dif.setText(String.valueOf(p18dif));
            frmCalculoRetiro.txtppgdif.setText(String.valueOf(ppgdif));
            frmCalculoRetiro.txtppedif.setText(String.valueOf(ppedif));

            frmCalculoRetiro.txtissstedif.setText(String.valueOf(sumaissstedif));

            frmCalculoRetiro.txtd01dif.setText(String.valueOf(d01dif));

            frmCalculoRetiro.txtaportacionqnal.setText(String.valueOf(sumadiferencias));
            frmCalculoRetiro.txtaportacionanual.setText(String.valueOf(aportacionanual));
            frmCalculoRetiro.txtperiodosdecotizacion.setText(String.valueOf(periododecotizacion));
            frmCalculoRetiro.txtaportaciontotal.setText(String.valueOf(aportaciontotal));
            frmCalculoRetiro.txtfondoderetiro.setText(String.valueOf(CalculoPorcentaje));
            frmCalculoRetiro.txtTotalFinal.setText(String.valueOf(totalfinal));

        }

        
        if (e.getSource() == frmCalculoRetiro.btnImprimir) {
            periodoinicio = Integer.parseInt(frmCalculoRetiro.txtinicio.getText());
            if (periodoinicio < 1997)
            {
            periodoinicio = 1997;
            }
              periodofinal = Integer.parseInt(frmCalculoRetiro.txtfin.getText());
            periododecotizacion = periodofinal - periodoinicio;
            
            calculoretiro.setPeriodoInicio(periodoinicio);
            calculoretiro.setPeriodoFinal(periodofinal);
            calculoretiro.setP1(Double.parseDouble(frmCalculoRetiro.txtp1pres.getText()));
            calculoretiro.setP3(Double.parseDouble(frmCalculoRetiro.txtp3pres.getText()));
            calculoretiro.setP14(Double.parseDouble(frmCalculoRetiro.txtp14pres.getText()));
            calculoretiro.setP18(Double.parseDouble(frmCalculoRetiro.txtp18pres.getText()));
            calculoretiro.setP19(Double.parseDouble(frmCalculoRetiro.txtp19pres.getText()));
            calculoretiro.setPpg(Double.parseDouble(frmCalculoRetiro.txtppgpres.getText()));
            calculoretiro.setPpe(Double.parseDouble(frmCalculoRetiro.txtppepres.getText()));
            calculoretiro.setD01(Double.parseDouble(frmCalculoRetiro.txtd01pres.getText()));
            calculoretiro.setSumaissste(Double.parseDouble(frmCalculoRetiro.txtissstepres.getText()));
            calculoretiro.setP1cp(Double.parseDouble(frmCalculoRetiro.txtp1nomina.getText()));
            calculoretiro.setP3cp(Double.parseDouble(frmCalculoRetiro.txtp3nomina.getText()));
            calculoretiro.setP14cp(Double.parseDouble(frmCalculoRetiro.txtp14nomina.getText()));
            calculoretiro.setP18cp(Double.parseDouble(frmCalculoRetiro.txtp18nomina.getText()));
            calculoretiro.setP19cp(Double.parseDouble(frmCalculoRetiro.txtp19nomina.getText()));
            calculoretiro.setPpgcp(Double.parseDouble(frmCalculoRetiro.txtppgnomina.getText()));
            calculoretiro.setPpecp(Double.parseDouble(frmCalculoRetiro.txtppenomina.getText()));
            calculoretiro.setD01cp(Double.parseDouble(frmCalculoRetiro.txtd01nomina.getText()));
            calculoretiro.setSumaissstecp(Double.parseDouble(frmCalculoRetiro.txtissstenomina.getText()));
            calculoretiro.setP1dif(Double.parseDouble(frmCalculoRetiro.txtp1dif.getText()));
            calculoretiro.setP3dif(Double.parseDouble(frmCalculoRetiro.txtp3dif.getText()));
            calculoretiro.setP14dif(Double.parseDouble(frmCalculoRetiro.txtp14dif.getText()));
            calculoretiro.setP18dif(Double.parseDouble(frmCalculoRetiro.txtp18dif.getText()));
            calculoretiro.setP19dif(Double.parseDouble(frmCalculoRetiro.txtp19dif.getText()));
            calculoretiro.setPpgdif(Double.parseDouble(frmCalculoRetiro.txtppgdif.getText()));
            calculoretiro.setPpedif(Double.parseDouble(frmCalculoRetiro.txtppedif.getText()));
            calculoretiro.setD01dif(Double.parseDouble(frmCalculoRetiro.txtd01dif.getText()));
            calculoretiro.setSumaissstedif(Double.parseDouble(frmCalculoRetiro.txtissstedif.getText()));
            calculoretiro.setAportacionqnalalprograma(Double.parseDouble(frmCalculoRetiro.txtaportacionqnal.getText()));
            calculoretiro.setAportacionanualalprograma(Double.parseDouble(frmCalculoRetiro.txtaportacionanual.getText()));
            calculoretiro.setPeriododecotizacion(Double.parseDouble(frmCalculoRetiro.txtperiodosdecotizacion.getText()));
            calculoretiro.setAportaciontotal(Double.parseDouble(frmCalculoRetiro.txtaportaciontotal.getText()));
            calculoretiro.setFondoderetirovoluntario(Double.parseDouble(frmCalculoRetiro.txtfondoderetiro.getText()));
            calculoretiro.setNombre(frmCalculoRetiro.txtNombre.getText());
            consultascalculo.imprimeCalculo(calculoretiro);
        
        }
    }

}
