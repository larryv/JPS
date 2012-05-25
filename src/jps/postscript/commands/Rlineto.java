package jps.postscript.commands;


/**
 * Represents the PostScript command <code><em>x y</em> rlineto</code>.
 */
public class Rlineto implements PostscriptCommand {
    private final Double x;
    private final Double y;

    public Rlineto(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return this.x + " " + this.y + " rlineto";
    }
}
