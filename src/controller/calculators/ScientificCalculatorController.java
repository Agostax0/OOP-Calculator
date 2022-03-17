package controller.calculators;

import model.calculators.ScientificCalculatorModel;
import utils.AbstractCalculator;
import view.calculators.ScientificCalculatorPanel;

/**
 * 
 * MISSING JAVADOC
 *
 */
public class ScientificCalculatorController extends AbstractCalculator {
    @Override
    public void setCalculator() {
        this.model = new ScientificCalculatorModel();
        this.panel = new ScientificCalculatorPanel();
    }

}
