package com.ddk.karthi.apps;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "CreditCard")
public class CreditCard {
    @XmlElement(name = "validateCC")
    public void validateCC() {
        this.isValidCreditCard = validate();
    }

    @XmlElement(name = "isValidCreditCard")
    public Boolean isValidCreditCard() {
        return this.isValidCreditCard;
    }

    private boolean validate() {
        if ( ! (this.creditCardStr.length() > 14 && this.creditCardStr.length() < 17) )
            return false;

        int firstDigit = this.creditCardStr.charAt(0) - '0';
        int secondDigit = this.creditCardStr.charAt(1) - '0';
        if ( ! (firstDigit == 4
                || firstDigit == 5
                || firstDigit == 6
                || (firstDigit == 3 && secondDigit == 7)) )
            return false;

        List<Integer> creditCard = new ArrayList(creditCardStr.length());
        for ( int i = 0 ; i < creditCardStr.length() ; i++ ) {
            creditCard.add(creditCardStr.charAt(i) - '0');
        }

        /*
        **
        ** Double every second digit from right to left. If doubling of a digit results in a
        ** two-digit number, add up the two digits to get a single-digit number
        ** (like for 12:1+2, 18=1+8).
        **
        ** Now add all single-digit numbers.
        **
         */
        int sumOfDoubleEvenPlaces = 0;
        for ( int i = creditCard.size()-2 ; i >= 0 ; i -=2 ) {
            int val = creditCard.get(i) * 2;
            if ( val > 9 )
                val = val / 10 + val % 10;
            sumOfDoubleEvenPlaces += val;
        }

        /*
        ** Add all digits in the odd places from right to left in the card number
         */
        int sumOfOddPlaces = 0;
        for ( int i = creditCard.size()-1 ; i >= 0 ; i-=2 )
            sumOfOddPlaces += creditCard.get(i);

        /*
        ** If the sum of sumOfDoubleEvenPlaces and sumOfOddPlaces is divisible by 10,
        ** the card number is valid
         */
        if ( (sumOfDoubleEvenPlaces + sumOfOddPlaces) % 10 == 0 )
            return true;

        return false;
    }

    @XmlElement(name = "isValidCreditCard")
    Boolean isValidCreditCard;
    @XmlElement(name = "creditCardStr")
    String creditCardStr;
}
