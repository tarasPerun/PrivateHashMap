package com.shpp.p2p.cs.tperun.assignment17.Huffman;

import java.io.*;

/* This program encodes and decodes data using a variable-length Huffman algorithm.
 * The input file can contain any data, not necessarily text.
 * The encryption algorithm uses the original Huffman tree, which is based on a
 * table of frequencies of unique bytes and is used to generate bit sequences (codes),
 * which do not intersect with each other and allow you to uniquely identify the symbol.
 * We encode the file so that at the beginning it will store the size of the encoding
 * tree, the compressed shape of the tree, the array of original bytes, and then the
 * actual bits of the compressed data.
 * Decoding is in reverse order.
 */
public class Huffman implements Constants {

    private static final Processor proc = new Processor();
    private static String inputFileName;
    private static String outputFileName;

    public static void main(String[] args) throws IOException {

        /* fixing the start time of the program */
        long startTime = System.currentTimeMillis();

        fileNameLogic(args);

        /* fixing the end time of the program */
        long endTime = System.currentTimeMillis();

        try {
            displayMessage(endTime - startTime);
        } catch (IOException exception) {
            System.out.println("Input file not found!");
        }
    }


    /**
     * Displays the results of the program.
     *
     * @param time Program run time.
     */
    private static void displayMessage(long time) throws IOException {

        System.out.println("\nInput file: " + inputFileName);
        System.out.println("Output file: " + outputFileName);
        System.out.println("Compression / unpacking time = " + time + " millisecond");
        System.out.println("Input file size = " + proc.getInputFileSize() + " bytes");
        System.out.println("Output file size = " + proc.getOutputFileSize() + " bytes");
        System.out.println("\nCompression / unpacking efficiency  "
                + ((double) proc.getInputFileSize() / proc.getOutputFileSize()) * 100 + " %");
    }

    /**
     * Implements the logic of the program depending on the type of command line.
     *
     * @param line The command line.
     */
    static void fileNameLogic(String[] line) throws IOException {

        /* If the command line is empty */
        if (line.length == 0) {
            inputFileName = PATH_TO_FILE.concat("test.txt");
            outputFileName = PATH_TO_FILE.concat("test.txt.par");
            try {
                proc.archive(inputFileName, outputFileName);
            } catch (IOException exception) {
                System.out.println("Invalid input. Try again");
            }
        }

        /* If there is only one parameter on the command line. */
        if (line.length == 1) {
            inputFileName = PATH_TO_FILE.concat(line[0]);

            if ((line[0]).endsWith(".par")) {
                outputFileName = PATH_TO_FILE.concat((line[0].
                        substring(0, line[0].length() - 4)).concat(".uar"));
                proc.unarchive(inputFileName, outputFileName);
            } else {
                outputFileName = PATH_TO_FILE.concat(line[0].concat(".par"));
                try {
                    proc.archive(inputFileName, outputFileName);
                } catch (IOException exception) {
                    System.out.println("Invalid input. Try again");
                }
            }
        }

        /* If the command line has input and output file names. */
        if (line.length == 2) {

            inputFileName = PATH_TO_FILE.concat(line[0]);
            outputFileName = PATH_TO_FILE.concat(line[1]);
            if ((line[0]).endsWith(".par")) {
                proc.unarchive(inputFileName, outputFileName);
            } else {
                try {
                    proc.archive(inputFileName, outputFileName);
                } catch (IOException exception) {
                    System.out.println("Invalid input. Try again");
                }
            }
        }

        /* If the command line has a flag and the names of the input and output files */
        if (line.length == 3) {

            inputFileName = PATH_TO_FILE.concat(line[1]);
            outputFileName = PATH_TO_FILE.concat(line[2]);

            if (line[0].equals("-u")) {
                proc.unarchive(inputFileName, outputFileName);
            } else if (line[0].equals("-a")) {
                try {
                    proc.archive(inputFileName, outputFileName);
                } catch (IOException exception) {
                    System.out.println("Invalid input. Try again");
                }
            } else {
                System.out.println("\nInvalid flag entered.Try again");
            }
        }
    }
}






