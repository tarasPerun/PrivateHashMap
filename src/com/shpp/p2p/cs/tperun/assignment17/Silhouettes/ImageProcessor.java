package com.shpp.p2p.cs.tperun.assignment17.Silhouettes;


import com.shpp.p2p.cs.tperun.assignment17.PrivateArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;

/* This class contains some methods for working with images -
 * output on display, read the graphic file from disk, save
 * the graphic file, converting a color image to black and white,
 * inverting it, increasing its contrast, converting an image to a
 * two-dimensional array of intensities, converting an image to a
 * two-dimensional boolean array,finding a background color in an image
 * etc.
 */
public class ImageProcessor implements GraphConstants {

    /**
     * Reads an image from a file and displays it in a window.
     *
     * @param pathname The ile location on disk.
     */
    public void displayFromFile(String pathname) {

        /* Set the window title */
        JFrame window = new JFrame("Silhouettes");

        /* Set the initial window size*/
        window.setSize(600, 400);

        /* Set the window to close when x is pressed. */
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage image;
        try {
            image = ImageIO.read(new File(pathname));
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics gr) {

                    /* Set the exact size of the window depending on the file size. */
                    window.setSize(image.getWidth() + HORIZONTAL_OFFSET,
                            image.getHeight() + VERTICAL_OFFSET);
                    gr.drawImage(image, 0, 0, null);
                }
            };
            window.add(panel);
            window.setVisible(true);
        } catch (IOException ex) {
            System.out.println("File not found");
            System.exit(1);
        }
    }

    /**
     * Displays an BufferedImage image in a window.
     *
     * @param image The image to display.
     */
    public void displayFromImage(BufferedImage image) {

        /* Set the window title */
        JFrame window = new JFrame("Silhouettes");

        /* Set the initial window size*/
        window.setSize(600, 400);

        /* Set the window to close when x is pressed. */
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                /* Set the exact size of the window depending on the file size. */
                window.setSize(image.getWidth() + HORIZONTAL_OFFSET,
                        image.getHeight() + VERTICAL_OFFSET);
                g.drawImage(image, 0, 0, null);
            }
        };

        window.add(pane);
        window.setVisible(true);
    }


    /**
     * Reads a graphic file from disk to a class object BufferedImage.
     *
     * @param pathName THe path to the graphic file being read,
     *                 a variable class String.
     * @return The class object BufferedImage.This object has an upper
     * left corner coordinate of (0,0).
     */
    public BufferedImage readGraphicFile(String pathName) {

        BufferedImage result;
        try {
            File file = new File(pathName);
            result = ImageIO.read(file);
            return result;
        } catch (IOException exception) {
            System.out.println("File not found");
            System.exit(1);
        }
        return null;
    }

    /**
     * Writes a class object BufferedImage containing an image to a file with
     * the specified location and format.
     *
     * @param source     The class object BufferedImage.
     * @param pathName   The location where the file should be written and his name.
     * @param formatName The file format (.jpg, .jpeg, .png etc).
     */
    public void writeToFile(BufferedImage source, String pathName, String formatName) {

        try {
            File output = new File(pathName);
            ImageIO.write(source, formatName, output);
        } catch (IOException exception) {
            System.out.println("The file can't be written");
        }
    }

    /**
     * Inverts the image.
     *
     * @param source The image to invert.
     * @return The inverted image.
     */
    public BufferedImage inverseImage(BufferedImage source) {

        Color ibwColor;
        int[][] luminance = imageToLuminance(source);

        /* Create a new empty image of the same size and type. */
        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());

        for (int row = 0; row < source.getWidth(); row++) {
            for (int col = 0; col < source.getHeight(); col++) {
                int grey = MAX_LUMINANCE - luminance[row][col];
                ibwColor = new Color(grey, grey, grey);
                result.setRGB(row, col, ibwColor.getRGB());
            }
        }
        return result;
    }


    /**
     * Converts the RGB image to black and white image.
     *
     * @param source The image to converting.
     */
    public BufferedImage blackAndWhiteImage(BufferedImage source) {

        Color bwColor;
        int[][] luminance = imageToLuminance(source);

        try {
            /* Create a new empty image of the same size and type. */
            BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());

            for (int row = 0; row < source.getWidth(); row++) {
                for (int col = 0; col < source.getHeight(); col++) {

                    int grey = luminance[row][col];
                    bwColor = new Color(grey, grey, grey);

                    result.setRGB(row, col, bwColor.getRGB());
                }
            }
            if (findBackgroundColor(source))
                return result;
            else return inverseImage(result);

        } catch (IllegalArgumentException ex) {
            System.out.println("Unknown image type");
            System.exit(1);
        }
        return null;
    }

    /**
     * Converts the image to int array [][]
     *
     * @param image The input image
     * @return The int array [][]
     */
    public int[][] imageToLuminance(BufferedImage image) {

        int[][] luminance = new int[image.getWidth()][image.getHeight()];
        for (int row = 0; row < image.getWidth(); row++) {
            for (int col = 0; col < image.getHeight(); col++) {

                /* Get the color of the current pixel and three channels of this color */
                Color color = new Color(image.getRGB(row, col));

                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();

                /*
                 Calculate a gray color from three components according to the well-known formula
                 https://www.w3.org/TR/AERT/#color-contrast
                 */
                luminance[row][col] = (int) (red * 0.299 + green * 0.587 + blue * 0.114);
            }
        }
        return luminance;
    }

    /**
     * Looks in the image for the background color by perimeter pixel color analysis.
     *
     * @param source The initial image.
     * @return True or false depending on whether the background is light or dark.
     */
    public boolean findBackgroundColor(BufferedImage source) {

        int[][] luminance = imageToLuminance(source);
        int backgroundCount = 0;

        /* An image perimeter pixel array */
        PrivateArrayList<Integer> perimeterLum = new PrivateArrayList<>();

        for (int i = 0; i < luminance.length; i++) {

            perimeterLum.add(luminance[i][0]);
            perimeterLum.add(luminance[i][luminance[0].length - 1]);
        }

        for (int i = 0; i < luminance[0].length; i++) {
            perimeterLum.add(luminance[0][i]);
            perimeterLum.add(luminance[luminance.length - 1][i]);
        }

        for (int i : perimeterLum) {
            if (i > MAX_LUMINANCE / 2) backgroundCount++;
        }

        if (backgroundCount > perimeterLum.size() / 2) {

            return true;
        }
        return false;
    }


    /**
     * Creates a two-dimensional boolean array from a black and white image.
     *
     * @param image The input image.
     * @return The output boolean array.
     */
    public boolean[][] intToBoolean(BufferedImage image) {

        /* Form a two-dimensional array of pixels of the input image */
        int[][] intPixels = new int[image.getWidth()][image.getHeight()];

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                intPixels[i][j] = image.getRGB(i, j);
            }
        }

        /* Form a two-dimensional boolean array of the same size as the original */
        boolean[][] booleanPixels = new boolean[intPixels.length][intPixels[0].length];
        int red;

        /*
         Analyze the red component of each pixel of the array and fill the boolean array
         with true and false by the even or odd value
        */
        for (int row = 0; row < intPixels.length; row++) {
            for (int col = 0; col < intPixels[row].length; col++) {

                Color color = new Color(image.getRGB(row, col));
                red = color.getRed();

                /* The condition that the point is not on the perimeter of the image */
                boolean condition = row != 0 && col != 0 && row != intPixels.length - 1 &&
                        col != intPixels[0].length - 1;

                if ((red >= 0 && red <= MAX_LUMINANCE / 2) && condition) {

                    booleanPixels[row][col] = true;
                }
            }
        }
        return booleanPixels;
    }

    /**
     * Increases the image contrast.
     *
     * @param source The initial image.
     * @return The output image.
     */
    public BufferedImage equalizedImage(BufferedImage source) {

        /* Create a new empty image of the same size and type. */
        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        int[][] luminance = imageToLuminance(source);

        for (int row = 0; row < luminance.length; row++) {
            for (int col = 0; col < luminance[row].length; col++) {

                if (luminance[row][col] >= 0 && luminance[row][col] < THRESHOLD_LUMINANCE) {
                    luminance[row][col] = MIN_LUMINANCE;
                } else luminance[row][col] = MAX_LUMINANCE;

                Color color = new Color(luminance[row][col], luminance[row][col], luminance[row][col]);
                result.setRGB(row, col, color.getRGB());
            }
        }
        return result;
    }

    /**
     * Trims the edges of the image to separate slightly stuck together silhouettes.
     *
     * @param source The input image.
     * @return The output image.
     */
    public BufferedImage etchEdge(BufferedImage source) {

        BufferedImage result;
        if (findBackgroundColor(source)) {
            result = equalizedImage(source);
        } else result = inverseImage(equalizedImage(source));

        BufferedImage result1 = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());

        int[][] lum = imageToLuminance(result);

        /* The number of trimming cycles depending on the maximum size of the silhouette. */
        int cycles = (int) (Silhouettes.maxElement * STICKING_FACTOR);

        /* If the size of the silhouette is small, the number of trimming cycles should be increased */
        if (cycles < MIN_CYCLE) {
            cycles = (int) (TRIM_FACTOR * MIN_CYCLE);
        }

        /* Etching the image horizontally */
        for (int i = 0; i < cycles; i++) {
            for (int row = 0; row < lum.length; row++) {
                for (int col = 0; col < lum[row].length; col++) {

                    if (row == 0 || col == 0 || row == lum.length - 1 || col == lum[0].length - 1) {
                        lum[row][col] = MAX_LUMINANCE;
                    } else {
                        if (lum[row - 1][col] == MAX_LUMINANCE && lum[row][col] == MIN_LUMINANCE) {
                            lum[row][col] = MAX_LUMINANCE;
                            row++;
                        }
                        if (lum[row - 1][col] == MIN_LUMINANCE && lum[row][col] == MAX_LUMINANCE) {
                            lum[row][col - 1] = MAX_LUMINANCE;
                            row++;
                        }
                    }
                    Color color = new Color(lum[row][col], lum[row][col], lum[row][col]);
                    result1.setRGB(row, col, color.getRGB());
                }
            }
        }

        /* Etching the image vertically */
        for (int i = 0; i < cycles; i++) {
            for (int row = 0; row < lum.length; row++) {
                for (int col = 0; col < lum[row].length; col++) {

                    if (row == 0 || col == 0 || row == lum.length - 1 || col == lum[0].length - 1) {
                        lum[row][col] = MAX_LUMINANCE;

                    } else {
                        if (lum[row][col - 1] == MAX_LUMINANCE && lum[row][col] == MIN_LUMINANCE) {
                            lum[row][col] = MAX_LUMINANCE;
                            col++;
                        }
                        if (lum[row][col - 1] == MIN_LUMINANCE && lum[row][col] == MAX_LUMINANCE) {
                            lum[row][col - 1] = MAX_LUMINANCE;
                            col++;
                        }
                    }
                    Color color = new Color(lum[row][col], lum[row][col], lum[row][col]);
                    result1.setRGB(row, col, color.getRGB());
                }
            }
        }
        return result1;
    }

    /**
     * Gets a boolean array from a graphic file with an alpha channel.
     *
     * @param image The input image with alpha channel.
     * @return The two dimensional boolean array.
     */
    public boolean[][] getBooleanArrayAlpha(BufferedImage image) {

        /* Display the image type. */
        System.out.println("\nImage type - " + image.getType() + "(TYPE_4BYTE_ABGR)");

        int[][] alphaPixel = new int[image.getWidth()][image.getHeight()];
        boolean[][] array = new boolean[image.getWidth()][image.getHeight()];

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                Color color = new Color(image.getRGB(i, j), true);

                if (color.getRed() != 0) {

                /*
                 Calculate a gray color from three components according to the well-known formula
                 https://www.w3.org/TR/AERT/#color-contrast
                 */
                    alphaPixel[i][j] = 255 - (int) (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114);

                    int temp = alphaPixel[i][j];
                    if (temp < (MAX_LUMINANCE - THRESHOLD_LUMINANCE)) temp = MIN_LUMINANCE;
                    else temp = MAX_LUMINANCE;
                    alphaPixel[i][j] = temp;
                } else alphaPixel[i][j] = color.getAlpha();
            }
        }

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                int alpha = alphaPixel[i][j];

                /* The condition that the point is not on the perimeter of the image */
                boolean condition = i != 0 && j != 0 && i != alphaPixel.length - 1 &&
                        j != alphaPixel[0].length - 1;
                if ((alpha >= MAX_LUMINANCE / 2 && alpha <= MAX_LUMINANCE) && condition) {
                    array[i][j] = true;
                }
            }
        }
        return array;
    }
}






