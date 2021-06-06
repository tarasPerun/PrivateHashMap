package com.shpp.p2p.cs.tperun.assignment17.Calculator;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import com.shpp.p2p.cs.tperun.assignment17.PrivateArrayList;

import java.awt.*;

import static com.shpp.p2p.cs.tperun.assignment17.Calculator.GraphProgram.formula;

public class GraphProcessing extends GCanvas implements CalculatorConstants {

    double xInterval = (double) APPLICATION_WIDTH / X_NUMBER;
    double yInterval = (double) APPLICATION_HEIGHT / Y_NUMBER;
    double yMax;
    StringProcessing prGrStr = new StringProcessing();

    /**
     * Draws the graph
     * @param formula The formula to graph.
     * @param xLeft The left point of the graph.
     * @param xRight The right point of the graph.
     */
    public void update(String formula, String xLeft, String xRight) {

        this.removeAll();
        drawCoordinateAxes();
        drawDiagram(formula,xLeft,  xRight);
    }

    /**
     * Clears the output window and draws the coordinate axes.
     */
    public void clear() {
        this.removeAll();
    }

    /**
     * Draws the diagram with specified parameters.
     * @param formula the formula to draw.
     * @param xLeft The left point of the graph.
     * @param xRight The right point of the graph.
     */
    private void drawDiagram(String formula, String xLeft, String xRight ) {

        double deltaX = Double.parseDouble(xRight) - Double.parseDouble(xLeft);
        double[] x = new double[X_NUMBER];
        double[] y = new double[X_NUMBER];
        double[] yNorm = new double[X_NUMBER];

        /* find the extreme value of the formula */
        yMax = 0;
        for (int i = 0; i < X_NUMBER; i++) {
            x[i] = Double.parseDouble(xLeft) + i*deltaX/(X_NUMBER-1);
            y[i] = calculateY(formula,x[i]);
            if (Math.abs(y[i]) > yMax) yMax = Math.abs(y[i]);
        }

        /* normalize the values of the function to the maximum value. */
        for (int i = 0; i < X_NUMBER; i++) {
            yNorm[i] = y[i]/yMax;
        }

        for (int i = 0; i < X_NUMBER; i++) {
            drawSquare(Color.RED, xInterval/4. + xInterval*i,(1-yNorm[i])*getHeight()/2., 3);
        }
        for (int i = 0; i < X_NUMBER-1; i++) {
            drawLine(Color.RED, xInterval/4 + xInterval*i,(1-yNorm[i])*getHeight()/2.,
                    xInterval/4 + xInterval*(i+1),(1-yNorm[i+1])*getHeight()/2.);
        }
        /* draw horizontal and vertical  marks */
        for (int i = 0; i < X_NUMBER; i++) {
            drawSquare(Color.BLACK, 20 + xInterval * i, getHeight() / 2., 2);
            drawSquare(Color.BLACK, getWidth() / 2., yInterval * i - yInterval / 8, 2);

            /* The x values on the horizontal axis. */
            double lab =  Double.parseDouble( xLeft) + i*deltaX/(X_NUMBER-1);
            double lab1 = Math.round(lab*ROUND_FACTOR)/(double)ROUND_FACTOR;
            GLabel label = new GLabel("" + lab1);
            label.setFont("Default-15");
            label.setColor(Color.BLACK);

            /* Display every fourth inscription */
            if(i%4 == 0)
            add(label, xInterval / 4 + xInterval * i, getHeight() / 2. - 5);
        }

        /* draw vertical numbers */
        for (int i = 0; i < Y_NUMBER; i++) {

            double yMark = yMax - i * yMax / 5;
            double yMark1 = Math.round(yMark * ROUND_FACTOR) / (double) ROUND_FACTOR;

            GLabel label = new GLabel("" + yMark1);
            label.setFont("Default-15");
            label.setColor(Color.BLACK);
            if(i != (Y_NUMBER - 1)/2)
            add(label, getWidth()/2. + 5, ((double) getHeight()/(Y_NUMBER-1))*i);
        }
    }

    /**
     * Calculates the values of the formula depending on the value of the argument x.
     *
     * @param formula The function to calculate.
     * @param x       The argument of the function.
     * @return The value of the formula.
     */
    double calculateY(String formula, double x) {
        double y;
        String result;

        PrivateArrayList<String> parsedFormula = prGrStr.parseToFormula(formula);
        PrivateArrayList<String> insertedFormula = prGrStr.insertValueToFormula(parsedFormula,x);
        PrivateArrayList<String> organizedFormula = prGrStr.organizeFormula(insertedFormula);
        result = StringProcessing.calculate(organizedFormula);
        y = Double.parseDouble(result);
        return y;
    }


    /**
     * Draws coordinate axes.
     */
    public void drawCoordinateAxes() {

        drawLine(Color.BLACK, 0, getHeight() / 2., getWidth(), getHeight() / 2.);
        drawLine(Color.BLACK, getWidth() / 2., 0, getWidth() / 2., getHeight());

        GLabel xLabel = new GLabel("x");
        xLabel.setFont("Default-15");
        xLabel.setColor(Color.BLACK);
        add(xLabel, getWidth() - 30, getHeight() / 2. + 20);

        GLabel yLabel = new GLabel("y = " + formula);
        yLabel.setFont("Default-15");
        yLabel.setColor(Color.BLACK);
        add(yLabel, getWidth()/2. + 10,  20);
    }

    /**
     * Draws a line between two points.
     *
     * @param color  The color of line.
     * @param startX The x-coordinate of start point.
     * @param startY The y-coordinate of start point.
     * @param endX   The x-coordinate of end point.
     * @param endY   The y-coordinate of end point.
     */
    public void drawLine(Color color, double startX, double startY, double endX, double endY) {

        GLine line = new GLine(startX, startY, endX, endY);
        line.setColor(color);
        add(line);
    }

    /**
     * Draws a square with the specified parameters at the specified position.
     *
     * @param color The square color.
     * @param x     The x-coordinate
     * @param y     The y-coordinate.
     * @param size  The length of the square side in pixels.
     */
    private void drawSquare(Color color, double x, double y, double size) {

        GRect grect = new GRect(x, y, size, size);
        grect.setFilled(true);
        grect.setFillColor(color);
        add(grect);
    }
}

