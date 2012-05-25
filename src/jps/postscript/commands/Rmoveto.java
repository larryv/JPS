package jps.postscript.commands;


/**
 * Represents the PostScript command <code><em>x y</em> rmoveto</code>.
 */
public class Rmoveto implements PostscriptCommand {
    private final Double x;
    private final Double y;

    public Rmoveto(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return this.x + " " + this.y + " rmoveto";
    }
}
