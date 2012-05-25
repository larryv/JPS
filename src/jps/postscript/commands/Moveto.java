package jps.postscript.commands;


/**
 * Represents the PostScript command <code><em>x y</em> moveto</code>.
 */
public class Moveto implements PostscriptCommand {
    private final Double x;
    private final Double y;

    public Moveto(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return this.x + " " + this.y + " moveto";
    }

}
