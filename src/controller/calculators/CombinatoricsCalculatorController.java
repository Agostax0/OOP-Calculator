package controller.calculators;

import model.calculators.CombinatoricsCalculatorModel;
import utils.AbstractCalculator;
import view.calculators.CombinatoricsCalculatorPanel;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CombinatoricsCalculatorController extends AbstractCalculator {
    @Override
    public void setCalculator() {
       this.model = new CombinatoricsCalculatorModel();
       this.panel = new CombinatoricsCalculatorPanel();
    }
}
