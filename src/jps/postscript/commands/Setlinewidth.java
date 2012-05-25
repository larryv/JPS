package jps.postscript.commands;


/**
 * Represents the PostScript command <code><em>n</em> setlinewidth</code>.
 */
public class Setlinewidth implements PostscriptCommand {
    private Double n;

    public Setlinewidth(Double n) {
        this.n = n;
    }

    public String toString() {
        return this.n + " setlinewidth";
    }
}
