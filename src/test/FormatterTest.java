package test;

import static org.junit.Assert.assertEquals;

import utils.NumberFormatter;

/**
 * 
 *
 */
public class FormatterTest {

    /**
     * test javadoc.
     */
    @org.junit.Test
    public void initialize() {

    }
    /**
     * 
     */
    @org.junit.Test
    public void testNumberFormat() {
        assertEquals("2.34E-7", NumberFormatter.trimZeros("2.3400E-07"));

        assertEquals("0",NumberFormatter.format(0, 10, 10, 10));
        assertEquals("3.142", NumberFormatter.format(Math.PI, 7, 3, 3));             //numero con molti decimali
        assertEquals("2.34E-7", NumberFormatter.format(234* 0.000000001, 7, 3, 3)); //numero molto piccolo
        assertEquals("0.00000001", NumberFormatter.format(0.00000001, 7, 8, 8));      //numero molto piccolo
        assertEquals("0.3", NumberFormatter.format(0.1 + 0.2, 7, 8, 8));   //errore di calcolo di java ad un decimale molto piccolo
        assertEquals("1.23E18", NumberFormatter.format(1234567891000000000.0, 7, 2, 2));  //numero molto grande
        assertEquals("1.23E18", NumberFormatter.format(1230000000000000000.0, 7, 5, 5));  //numero molto grande
        assertEquals("1.2301E18", NumberFormatter.format(1230100000000000000.0, 7, 5, 5));//numero molto grande
        assertEquals("4.56E120", NumberFormatter.format(4562315E114, 10, 2, 2));
        assertEquals("4.56E-120", NumberFormatter.format(4562315E-126, 10, 2, 2)); //numero troppo piccolo diventa 0
        assertEquals("6.7530792054E-8", NumberFormatter.format(0.0000000675307920539, 7, 10, 4));

    }
}