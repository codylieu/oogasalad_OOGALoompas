package main.java.engine.util.leapmotion.gamecontroller;

import com.leapmotion.leap.Vector;


/**
 * A 3-dimension moving average filter backed by arrays of size N,
 * used for efficient calculation of simple moving averages (SMA) for the past N updates.
 * 
 * In LeapGameController, xyz coordinate averages used to smooth mouse pointer movement.
 * The parameter N can be adjusted for fine tuning the sensitivity of the mouse pointer
 * 
 */
public class MovingAverageFilter {

    private static final int DIMENSIONS = 3;
    private static final int DIMENSION_X = 0;
    private static final int DIMENSION_Y = 1;
    private static final int DIMENSION_Z = 2;

    /**
     * Arrays holding N values at any given time for calculating an average.
     */
    int[] xValues, yValues, zValues;
    int[][] xyzArrays;
    int[] arraySums;

    int currentArrayIndex = 0;

    /**
     * Create a new moving average filter that calculates the average of values in the past N
     * updates.
     * 
     * @param n how many past results to be stored/calculated in a simple moving average
     */
    public MovingAverageFilter (int n) {
        xValues = new int[n];
        yValues = new int[n];
        zValues = new int[n];

        xyzArrays = new int[DIMENSIONS][n];
        xyzArrays[DIMENSION_X] = xValues;
        xyzArrays[DIMENSION_Y] = yValues;
        xyzArrays[DIMENSION_Z] = zValues;
        arraySums = new int[xyzArrays.length];
    }

    /**
     * Update this filter with a new 3D vector of values, modifying the moving average.
     * 
     * @param xyzCoordinate 3D vector of values to update the filter with
     */
    public void updateFilter (Vector xyzCoordinate) {
        for (int i = 0; i < xyzArrays.length; i++) {
            arraySums[i] -= xyzArrays[i][currentArrayIndex];
            xyzArrays[i][currentArrayIndex] = (int) xyzCoordinate.get(i);
            arraySums[i] += xyzArrays[i][currentArrayIndex];
        }
        if (++currentArrayIndex >= xValues.length) {
            currentArrayIndex = 0;
        }
    }

    /**
     * Get this filter's simple moving average of the past N updates.
     * 
     * @return 3D (e.g. xyz) vector of averages
     */
    public Vector getAverages () {
        return new Vector(arraySums[DIMENSION_X] / xValues.length,
                          arraySums[DIMENSION_Y] / yValues.length,
                          arraySums[DIMENSION_Z] / zValues.length);
    }
}
