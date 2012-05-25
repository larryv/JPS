package jps.postscript.commands;


/**
 * Represents the PostScript command <code><em>x y</em> scale</code>.
 */
public class Scale implements PostscriptCommand {
    private Double x;
    private Double y;

    public Scale(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return this.x + " " + this.y + " scale";
    }
}
