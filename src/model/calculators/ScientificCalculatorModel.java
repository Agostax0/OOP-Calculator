package model.calculators;

import java.util.Map;
import java.util.stream.LongStream;

import utils.CCBinaryOperator;
import utils.CCUnaryOperator;
/**
 * 
 * MISSING JAVADOC.
 *
 */

public class ScientificCalculatorModel extends StandardCalculatorModel {
    /**
     * ScientificCalculatorModel contains all the operations of StandardCalculatorModel
     * and new operations unique to a scientific calculator.
     *
     */
    public ScientificCalculatorModel() {
        super();
        this.unaryOpMap.putAll(Map.of("ln", new CCUnaryOperator((n) -> this.ln(n), 0, null),
                                      "abs", new CCUnaryOperator((n) -> this.abs(n), 0, null),
                                      "factorial", new CCUnaryOperator((n) -> (double)this.factorial(n), 0, null)                                      
                             ));
        this.binaryOpMap.putAll(Map.of("log", new CCBinaryOperator((n, b) -> this.log(n, b), 0, null),
                                       "nthRoot", new CCBinaryOperator((n, i) -> this.nthRoot(n, i), 0, null),
                                       "potenza", new CCBinaryOperator((n, e) -> this.potenza(n, e), 0, null)                                  
                             ));
    }
    /**
     * 
     * @param n is the argument of the logarithm 
     * @return the natural logarithm of the value n
     */
    
    private double ln(final double n) {
        return Math.log(n);
    }
    /**
     * 
     * @param n is the value we want to get the modulus of
     * @return the modulus of the value n
     */
    
    private double abs(final double n) {
        return Math.abs(n);
    }
    /**
     * 
     * @param n is the number we want to get the factorial of
     * @return the factorial of the value n
     */
    
    private long factorial(final double n) {
        return LongStream.rangeClosed(1, Math.round(n)).reduce(1, (long n1, long n2) -> n1*n2);             
    }
    /**
     * 
     * @param n is the argument of the logarithm
     * @param b is the base of the logarithm
     * @return the logarithm (with base b) of the argument
     */
    
    private double log(final double n, final double b) {
        return this.ln(n) / this.ln(b);
    }
    
    /**
     * 
     * @param n is the value we want to get the root of
     * @param i is the index of the root
     * @return the 'i'th root of the first argument 
     */
    
    private double nthRoot(final double n, final double i) {
        return Math.pow(n, 1 / i);
    }
    /**
     * 
     * @param n is the value we want to raise
     * @param e is the exponent
     * @return the value of the first argument raised to the power of the second argument
     */
    
    private double potenza(final double n, final double e) {
        return Math.pow(n, e);
    }
    
}
