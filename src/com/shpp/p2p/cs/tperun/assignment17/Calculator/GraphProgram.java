package com.shpp.p2p.cs.tperun.assignment17.Calculator;

import javax.swing.*;

import java.awt.event.ActionEvent;

import com.shpp.cs.a.simple.SimpleProgram;

/* The program displays a graph of the function of one variable y = f(x).
 * The formula of the function and the range of its display are entered
 * by the user.
 * Keep in mind that not all functions have a domain on the entire numerical
 * axis. For example, the square root function is defined only for
 * positive values of x.
 */

public class GraphProgram extends SimpleProgram implements CalculatorConstants {

    public static String formula;
    public static String leftX;
    public static String rightX;
    private final GraphProcessing graph = new GraphProcessing();
    private final JTextField nameField1 = new JTextField(NUM_COLUMNS);
    private final JTextField nameField2 = new JTextField(NUM_COLUMNS/4);
    private final JTextField nameField3= new JTextField(NUM_COLUMNS/4);

    /**
     * This method has the responsibility for reading the formula and range
     * of x value and initializing the interactors at the top of the window.
     */
    public void init() {

        add(new JLabel("Formula: y ="), NORTH);
        add(nameField1, NORTH);
        add(new JLabel("X from:"), NORTH);
        add(nameField2, NORTH);
        add(new JLabel("X to:"), NORTH);
        add(nameField3, NORTH);
        add(new JButton("Graph"), NORTH);
        add(new JButton("Clear"), NORTH);
        nameField1.addActionListener(this);
        nameField1.setActionCommand("Graph");
        addActionListeners();
        add(graph);
    }

    /**
     * This method is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent event) {

       formula = nameField1.getText();
       leftX = nameField2.getText();
       rightX = nameField3.getText();

        if (event.getActionCommand().equals("Graph")) {

            graph.update(formula, leftX, rightX);

        } else if (event.getActionCommand().equals("Clear")) {

             graph.clear();
             nameField1.setText("");
             nameField2.setText("");
             nameField3.setText("");
        }
    }
}
