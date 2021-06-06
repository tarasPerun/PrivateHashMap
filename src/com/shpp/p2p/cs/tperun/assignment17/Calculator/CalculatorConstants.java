package com.shpp.p2p.cs.tperun.assignment17.Calculator;

public interface CalculatorConstants {

    /* Width of the JTextField */
    int NUM_COLUMNS = 15;

    /* The width of the application window. */
    int APPLICATION_WIDTH = 800;

    /* The height of the application window. */
    int APPLICATION_HEIGHT = 800;

    /* The number of points on the graph along the horizontal axis. */
    int X_NUMBER = 41;

    /* The number of points on the graph along the vertical axis. */
    int Y_NUMBER = 11;

    /* The factor that determines how many decimal places in a number will be displayed */
    int ROUND_FACTOR = 100;

    /* the mathematical operations */
    String ADD = "+";
    String SUB = "-";
    String MULT = "*";
    String DIV = "/";
    String POW = "^";
    String[] MATH_OPERATIONS = {ADD, SUB, MULT, DIV, POW};

    /* the functions */
    String SIN = "sin";
    String COS = "cos";
    String TAN = "tan";
    String ATAN = "atan";
    String LOG10 = "log10";
    String LOG2 = "log2";
    String SQRT = "sqrt";
    String LOG = "log";
    String[] FUNCTIONS = {SIN, COS, TAN, ATAN, LOG10, LOG2, SQRT, LOG};
}
