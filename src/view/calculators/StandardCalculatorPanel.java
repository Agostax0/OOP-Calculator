package view.calculators;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.components.CCDisplay;
import view.components.CCNumPad;
import javax.swing.*;

import controller.calculators.CalculatorController;
import model.calculators.StandardCalculatorModelFactory;
//TODO MISSING JAVADOC.
/**
 * 
 * MISSING JAVADOC.
 *
 */

public class StandardCalculatorPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -3801351406960094788L;
    private final CCDisplay display = new CCDisplay();
    private final CalculatorController controller;
    //TODO MISSING JAVADOC.
    /**
     * MISSING JAVADOC.
     * @param controller 
     * 
     */
    public StandardCalculatorPanel(final CalculatorController controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);

        this.setNumbers();
        this.setOperators();
    }
    private void setNumbers() {
        final ActionListener btnAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().read(((JButton) e.getSource()).getText());
                display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
            }
        };
        final ActionListener calcAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().calculate();
                display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
            }
        };
        final ActionListener backspaceAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().deleteLast();
                display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));

            }
        };
        final JPanel numbers = new CCNumPad(btnAl, calcAl, backspaceAl);

        this.add(numbers, BorderLayout.CENTER);
    }
    private void setOperators() {
        final  ActionListener binaryAl = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().read(((JButton) e.getSource()).getText());
                display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
            }
        };
        final  ActionListener unaryAl = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                display.updateText(((JButton) e.getSource()).getText() + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
                controller.getManager().read(((JButton) e.getSource()).getText());
            }
        };
        final JPanel operator = new JPanel();
        operator.setLayout(new GridLayout(4, 2));
        for (final var entry : StandardCalculatorModelFactory.create().getUnaryOpMap().entrySet()) {
            final var btn = new JButton(entry.getKey());
            btn.addActionListener(unaryAl);
            operator.add(btn);
        }
        for (final var entry : StandardCalculatorModelFactory.create().getBinaryOpMap().entrySet()) {
            final var btn = new JButton(entry.getKey());
            btn.addActionListener(binaryAl);
            operator.add(btn);
        }
        this.add(operator, BorderLayout.EAST);
    }
}
