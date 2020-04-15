package com.ddk.karthi.apps;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddTwoFractions")
public class AddTwoFractions {
    @XmlElement(name = "getSum")
    public String getSum() {
        return (this.sum[0] % this.sum[1] == 0) ?
                Integer.toString(this.sum[0]/this.sum[1]) :
                this.sum[0] + "/" + this.sum[1];
    }

    @XmlElement(name = "getFraction1")
    public String getFraction1() {
        return this.f1;
    }

    @XmlElement(name = "getFraction2")
    public String getFraction2() {
        return this.f2;
    }

    public void add() throws Exception {
        fraction1 = parseFractionString(getFraction1());
        fraction2 = parseFractionString(getFraction2());

        this.sum = new Integer[2];
        if ( this.fraction1[1] == this.fraction2[1] ) {
            this.sum[0] = this.fraction1[0] + this.fraction2[0];
            this.sum[1] = this.fraction2[1];
        } else {
            this.sum[1] = this.fraction1[1] * this.fraction2[1];
            this.sum[0] = this.fraction1[0] * (this.sum[1] / this.fraction1[1]) +
                    this.fraction2[0] * (this.sum[1] / this.fraction2[1]);
        }
        int g = gcd(this.sum[0], this.sum[1]);
        //if ( (this.sum[0] % g == 0) && (this.sum[1] % g == 0) ) {
        this.sum[0] /= g;
        this.sum[1] /= g;
        //}
    }

    private int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a%b);
    }

    private Integer[] parseFractionString(String f) throws Exception {
        String[] fSplit = f.split("/");
        if ( fSplit.length != 2 )
            throw new Exception(f + " is not a valid fraction");

        Integer[] fr = new Integer[2];
        fr[0] = Integer.parseInt(fSplit[0]);
        fr[1] = Integer.parseInt(fSplit[1]);
        return fr;
    }

    Integer[] fraction1;
    Integer[] fraction2;
    Integer[] sum;
    @XmlElement(name = "f1")
    protected String f1;
    @XmlElement(name = "f2")
    protected String f2;
}
