package com.ddk.karthi.apps;

public class AddTwoFractions {
    public AddTwoFractions(Integer[] fraction1, Integer[] fraction2) {
        this.fraction1 = fraction1;
        this.fraction2 = fraction2;
        add();
    }

    @Override
    public String toString() {
        return (this.sum[0] % this.sum[1] == 0) ?
                Integer.toString(this.sum[0]/this.sum[1]) :
                this.sum[0] + "/" + this.sum[1];
    }

    int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a%b);
    }

    void add() {
        this.sum = new Integer[2];
        if ( this.fraction1[1] == this.fraction2[1] ) {
            this.sum[0] = this.fraction1[0] + this.fraction2[0];
            this.sum[1] = this.fraction2[0];
        } else {
            this.sum[1] = this.fraction1[1] * this.fraction2[1];
            this.sum[0] = this.fraction1[0] * (this.sum[1] / this.fraction1[1]) +
                    this.fraction2[0] * (this.sum[1] / this.fraction2[1]);
        }
        int g = gcd(this.sum[0], this.sum[1]);
        if ( (this.sum[0] % g == 0) && (this.sum[1] % g == 0) ) {
            this.sum[0] /= g;
            this.sum[1] /= g;
        }
    }


    Integer[] fraction1;
    Integer[] fraction2;
    Integer[] sum;
}
