package jps.postscript.commands;


/**
 * Represents the PostScript command <code><em>x y</em> lineto</code>.
 */
public class Lineto implements PostscriptCommand {
    private final Double x;
    private final Double y;

    public Lineto(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return this.x + " " + this.y + " lineto";
    }
}
