package org.hupo.psi.calimocho.xgmml;

/**
 * This object contains the id of a node and its positions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/08/12</pre>
 */

public class XgmmlNode {

    private String id;
    private String key;
    private double x;
    private double y;

    private int rowIndex;
    private int colIndex;
    private int cols;
    private int distance = 80;

    public XgmmlNode(String key, String id, int rowindex, int colindex, int cols, int distance){
        this.key = key;
        this.id = id;
        this.cols = cols;
        this.distance = distance;

        this.colIndex = colindex;
        this.colIndex++;
        this.rowIndex =rowindex;

        if (colIndex >= this.cols) {
            this.rowIndex++;
            this.colIndex = 0;
        }

        x = this.distance * this.colIndex;
        y = this.distance * this.rowIndex;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getCols() {
        return cols;
    }

    public String getKey() {
        return key;
    }
}
