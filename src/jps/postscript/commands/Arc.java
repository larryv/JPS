package jps.postscript.commands;

import org.apache.commons.lang3.StringUtils;


/**
 * Represents the PostScript command <code><em>x y r ang1 ang2</em>
 * arc</code>.
 */
public class Arc implements PostscriptCommand {
    private Double x;
    private Double y;
    private Double r;
    private Double ang1;
    private Double ang2;

    public Arc(Double x, Double y, Double r, Double ang1, Double ang2) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.ang1 = ang1;
        this.ang2 = ang2;
    }

    public String toString() {
        Object[] ps = {this.x, this.y, this.r, this.ang1, this.ang2, "arc"};
        return StringUtils.join(ps, ' ');
    }
}
