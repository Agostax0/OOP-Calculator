package view.calculators;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import utils.CCColors;
import utils.InputFormatter;
import view.components.CCDisplay;
import view.components.CCNumPad;
import view.components.ConversionPanel;
import view.components.HexadecimalLettersPanel;
/**
 * This is ProgrammerCalculatorPanel which holds the following operators:
 * (Bitwise)
 * -Not, Nor, Nand, Or, Xor, And, RoR, RoL, ShiftL, ShiftR.
 * (Conversions)
 * -Hexadecimal, Octal, Binary.
*/
public class ProgrammerCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -8342823219976507443L;
    private final CCDisplay display = new CCDisplay();
    private HexadecimalLettersPanel hexaLetters;
    private ConversionPanel convPanel;
    private final CCNumPad numpad;
    private transient ActionListener opAl;
    private final transient InputFormatter formatter;
    private final List<String> topOperators = List.of("roR", "roL", "shiftR", "shiftL", "nand");
    private final List<String> middleOperators = List.of("and", "or", "xor");
    private final List<String> rightOperators = List.of("not", "nor", "+", "-", "×", "÷");
    {
        final ActionListener btnAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                formatter.read(text);
                updateDisplays();
            }
        };
        final ActionListener calcAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    formatter.calculate();
                    formatter.updateHistory();
                } catch (final Exception exception) {
                    display.updateText("Syntax error");
                    formatter.deleteLast();
                }
                updateDisplays();
            }
        };
        final ActionListener backspaceAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                formatter.deleteLast();
                updateDisplays();
            }
        };
        this.numpad = new CCNumPad(btnAl, calcAl, backspaceAl);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.numpad.setPreferredSize(new Dimension((int) screenSize.getWidth() / 6, (int) screenSize.getHeight() / 4));
        this.numpad.getButtons().entrySet().forEach((entry) -> {
            if (".".equals(entry.getKey())) {
                entry.getValue().setEnabled(false);
            }
        });

        this.opAl = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                formatter.read(text);
                updateDisplays();
            }
        };
    }
    /**
     * This is ProgrammerCalculatorPanel which holds the following operators:
     * (Bitwise)
     * -Not, Nor, Nand, Or, Xor, And, RoR, RoL, ShiftL, ShiftR.
     * (Conversions)
     * -Hexadecimal, Octal, Binary.
    */
    public ProgrammerCalculatorPanel() {
        this.formatter = new InputFormatter();
        this.setPanels();
    }
    private void setPanels() {
        this.setLayout(new BorderLayout());
        this.add(this.display, BorderLayout.NORTH);
        this.setConversionPanel();
        this.setNumpad();
    }
    private void setConversionPanel() {
        final ActionListener conv = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JButton btn = (JButton) e.getSource();
                switch (btn.getText()) {
                case "HEX":
                    formatter.reset(16);
                    hexaLetters.enableAll();
                    enableButtons(10);
                    formatter.reset(16);
                    break;
                case "DEC":
                    formatter.reset(10);
                    hexaLetters.disableAll();
                    enableButtons(10);
                    formatter.reset(10);
                    break;
                case "OCT":
                    formatter.reset(8);
                    hexaLetters.disableAll();
                    enableButtons(8);
                    formatter.reset(8);
                    break;
                case "BIN":
                    formatter.reset(2);
                    hexaLetters.disableAll();
                    enableButtons(2);
                    formatter.reset(2);
                    break;
                default:
                    break;
                }
                display.updateText("0");
                convPanel.updateConvDisplays(0);
            }
        };
        this.convPanel = new ConversionPanel(conv);
        this.convPanel.setPreferredSize(new Dimension(100, 150));
        this.add(this.convPanel, BorderLayout.CENTER);
    }
    private void enableButtons(final int i) {
        final var numbers = this.getNumbers();
        numbers.entrySet().stream().filter((entry) -> Integer.parseInt(entry.getKey()) < i)
                .forEach((entry) -> entry.getValue().setEnabled(true));
        numbers.entrySet().stream().filter((entry) -> Integer.parseInt(entry.getKey()) >= i)
                .forEach((entry) -> entry.getValue().setEnabled(false));
    }
    private Map<String, JButton> getNumbers() {
        final Map<String, JButton> map = new HashMap<>();
        this.numpad.getButtons().entrySet().stream().filter((entry) -> {
            try {
                Integer.parseInt(entry.getKey());
                return true;
            } catch (final NumberFormatException e) {
                return false;
            }
        }).forEach((entry) -> map.put(entry.getKey(), entry.getValue()));
        return map;
    }
    private void setNumpad() {
        final JPanel numpad = new JPanel();
        numpad.setLayout(new GridLayout(1, 3));
        final ActionListener letterActionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                formatter.read(text);
                updateDisplays();
            }
        };
        this.hexaLetters = new HexadecimalLettersPanel(letterActionListener);
        formatter.reset(10);
        hexaLetters.disableAll();
        numpad.add(this.hexaLetters);
        numpad.add(this.getMiddleNumpad());
        numpad.add(this.getRightNumpad());
        final JPanel numpadAndOperators = new JPanel();
        numpadAndOperators.setLayout(new BorderLayout());
        numpadAndOperators.add(numpad, BorderLayout.CENTER);
        final JPanel oper = new JPanel();
        oper.setLayout(new GridLayout(1, 5));
        this.topOperators.forEach((str) -> {
            final JButton btn = new JButton(str);
            btn.addActionListener(opAl);
            btn.setBackground(CCColors.OPERATION_BUTTON);
            oper.add(btn);
        });
        final JPanel mid = new JPanel();
        mid.setLayout(new BorderLayout());
        mid.add(oper, BorderLayout.NORTH);
        mid.add(numpadAndOperators, BorderLayout.CENTER);
        this.add(mid, BorderLayout.SOUTH);
    }
    private JPanel getRightNumpad() {
        final int rows = 6;
        final int cols = 1;
        final JPanel operators = new JPanel();
        operators.setLayout(new GridLayout(rows, cols));
        this.rightOperators.forEach((op) -> {
            final JButton btn = new JButton(op);
            btn.addActionListener(opAl);
            btn.setBackground(CCColors.OPERATION_BUTTON);
            operators.add(btn);
        });
        return operators;
    }
    private JPanel getMiddleNumpad() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(this.numpad, BorderLayout.CENTER);
        final JPanel topMiddleNumpad = new JPanel();
        topMiddleNumpad.setLayout(new GridLayout(1, 3));
        this.middleOperators.forEach((str) -> {
            final JButton btn = new JButton(str);
            btn.addActionListener(opAl);
            btn.setBackground(CCColors.OPERATION_BUTTON);
            topMiddleNumpad.add(btn);
        });
        panel.add(topMiddleNumpad, BorderLayout.NORTH);
        return panel;
    }
    private void updateDisplays() {
        display.updateText(formatter.getOutput());
        convPanel.updateConvDisplays(formatter.getLastValue());
    }
}
