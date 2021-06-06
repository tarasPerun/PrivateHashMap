package com.shpp.p2p.cs.tperun.assignment17.Silhouettes;

public interface GraphConstants {

    /*
     A factor that determines the relative size of the garbage
     that is ignored relative to the maximum silhouette size
    */
    double GARBAGE_FACTOR = 0.12;

    /*
    The maximum and minimum values of the intensities
    of the image array.
   */
    int MAX_LUMINANCE = 255;
    int MIN_LUMINANCE = 0;

    /* The threshold value of the intensity */
    int THRESHOLD_LUMINANCE = 190;

    /*
     Corrections in pixels for the correct display
     of the image in the output window
    */
    int HORIZONTAL_OFFSET = 16;
    int VERTICAL_OFFSET = 38;

    /* The component of the path to the file with the image. */
    String PATH_TO_FILE = "assets/";

    /*
     Coefficient that determines the degree of etching of the
     contours of stuck together silhouettes, depending on the size
     of these silhouettes.
    */
    double STICKING_FACTOR = 0.00015;

    /*
     Parameters that determine the minimum number of undercutting cycles
     and the increase factor
    */
    double MIN_CYCLE = 5;
    double TRIM_FACTOR = 2.2;

}
