package jps.postscript.commands;


/**
 * Represents the PostScript command <code>translate</code>.
 */
public class Translate implements PostscriptCommand {
    private Double x;
    private Double y;

    public Translate(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return this.x + " " + this.y + " translate";
    }
}
