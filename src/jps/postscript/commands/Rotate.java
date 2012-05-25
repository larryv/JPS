package jps.postscript.commands;


/**
 * Represents the PostScript command <code><em>angle</em> rotate</code>.
 */
public class Rotate implements PostscriptCommand {
    public final Double angle;

    public Rotate(Double angle) {
        this.angle = angle;
    }

    public String toString() {
        return this.angle + " rotate";
    }
}
