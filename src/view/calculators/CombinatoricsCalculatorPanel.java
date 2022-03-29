package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import utils.AbstractCalculator;
import view.components.CCDisplay;
import view.components.CCNumPad;
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CombinatoricsCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static String opFormat = "";
    private static String opString = "";
    /**
     * 
     * @param controller
     */
    public CombinatoricsCalculatorPanel(final AbstractCalculator controller) {
        final var display = new CCDisplay();
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        controller.setDisplay(display);
        final ActionListener btnAl = e -> {
            final var btn = (JButton) e.getSource();
            controller.getManager().read(btn.getText());
            String text;
            if (!opFormat.isBlank()) {
                if (!opString.isBlank() && controller.isBinaryOperator(opString)) {
                    text = opFormat + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b).split(opString)[1];
                } else {
                    text = opFormat + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b);
                }
            } else {
                text = controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b);
            }
            display.updateText(text);
        };
        final ActionListener calculateAl = e -> {
            final String adder = controller.isBinaryOperator(opString) ? controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b).split(opString)[1] : "";
            display.updateUpperText(opFormat + adder + ") =");
            controller.getManager().calculate();
            display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
            opFormat = "";
            opString = "";
        };
        final ActionListener backspaceAl = e -> {
            if (controller.getManager().getCurrentState().get(controller.getManager().getCurrentState().size() - 1).length() > 1) {
                opFormat = "";
                opString = "";
            }
            controller.getManager().deleteLast();
            String text;
            if (!opFormat.isBlank()) {
                if (!opString.isBlank() && controller.isBinaryOperator(opString)) {
                    text = opFormat + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b).split(opString)[1];
                } else {
                    text = opFormat + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b);
                }
            } else {
                text = controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b);
            }
            display.updateText(text);
        };
        final var numpad = new CCNumPad(btnAl, calculateAl, backspaceAl);
        numpad.getButtons().get("(").setEnabled(false);
        numpad.getButtons().get(")").setEnabled(false);
        numpad.getButtons().get(".").setEnabled(false);
        this.add(numpad, BorderLayout.WEST);
        this.add(new OperationsPanel(controller, display), BorderLayout.CENTER);
        this.add(new ExplainationPanel(), BorderLayout.EAST);
    }
    static class OperationsPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private final AbstractCalculator controller;
        private final CCDisplay display;

        OperationsPanel(final AbstractCalculator controller, final CCDisplay display) {
            this.display = display;
            this.controller = controller;
            this.setLayout(new GridLayout(8, 1));
            this.createButton("Sequences", "sequencesNumber");
            this.createButton("Factorial", "factorial");
            this.createButton("Binomial Coefficient", "binomialCoefficient");
            this.createButton("Scombussolamento", "scombussolamento");
            this.createButton("Partitions", "bellNumber");
            this.createButton("Partitions(binary)", "stirlingNumber");
            this.createButton("Fibonacci", "fibonacci");
            this.createButton("Fibonacci(binary)", "binaryFibonacci");
        }
        private void createButton(final String btnName, final String opName) {
            final var btn = new JButton(btnName);
            btn.addActionListener(e -> {
                final String closer = controller.isBinaryOperator(opName) ? ", " : "";
                opFormat = btnName + "(" + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b) + closer;
                display.updateText(opFormat);
                controller.getManager().read(opName);
                opString = opName;
            });
            this.add(btn);
        }
    }
    static class ExplainationPanel extends JPanel {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        ExplainationPanel() {
            this.setLayout(new GridLayout(8, 1));
            final var btn1 = createButton("sequencesNumber");
            btn1.setToolTipText("NUMERO DI SEQUENZE");
            this.add(btn1);
            final var btn2 = createButton("factorial");
            btn2.setToolTipText("FATTORIALE DISCENDENTE");
            this.add(btn2);
            final var btn3 = createButton("binomialCoefficient");
            btn3.setToolTipText("COEFFICIENTE BINOMIALE");
            this.add(btn3);
            final var btn4 = createButton("scombussolamento");
            btn4.setToolTipText("SCOMBUSSOLAMENTO");
            this.add(btn4);
            final var btn5 = createButton("bellNumber");
            btn5.setToolTipText("NUMERO DI BELL");
            this.add(btn5);
            final var btn6 = createButton("stirlingNumber");
            btn6.setToolTipText("NUMERO DI STIRLING");
            this.add(btn6);
            final var btn7 = createButton("fibonacci");
            btn7.setToolTipText("NUMERO DI FIBONACCI");
            this.add(btn7);
            final var btn8 = createButton("binaryFibonacci");
            btn8.setToolTipText("SEQUENZE DI FIBONACCI");
            this.add(btn8);
        }
        private JButton createButton(final String opName) {
            final var btn = new JButton("?");
            btn.addActionListener(e -> {
                this.add(new JTextArea(opName));
            });
            return btn;
        }
    }
}
