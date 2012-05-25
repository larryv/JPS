package jps;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.apache.commons.lang3.StringUtils;

import jps.draw.*;
import jps.postscript.commands.*;


public final class JPS {

    public static final String NEWLINE = System.getProperty("line.separator");

    private JPS() {
        // This class should not be instantiated
    }

    /**
     * Writes PostScript to a file.
     * @param ps a string of valid PostScript.
     * @param filename the name of the destination file.
     */
    public static void toPSFile(String ps, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(ps);
            bw.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Converts a JPS shape to PostScript.
     * @param x the <em>x</em>-coordinate of the starting point.
     * @param y the <em>y</em>-coordinate of the starting point.
     * @param s a shape that will be translated to PostScript.
     * @param draw specifies whether or not the shape is filled.
     * @return the PostScript representation of the shape, drawn in
     *         black.
     */
    public static String toPSString(Double x, Double y, Shape s,
                                    PSVisitor.Draw draw) {
        Object[] ps = {new Newpath(),
                       new Moveto(x, y),
                       s.accept(new PSVisitor(draw)),
                       new Showpage()};
        return StringUtils.join(ps, NEWLINE);
    }

    /**
     * Converts a JPS shape to PostScript.
     * @param x the <em>x</em>-coordinate of the starting point.
     * @param y the <em>y</em>-coordinate of the starting point.
     * @param s a shape that will be translated to PostScript.
     * @param rgb the shape's color as an array of RGB values between
     *            0 and 255, inclusive.
     * @return the PostScript representation of the shape, drawn without
     *         a fill.
     */
    public static String toPSString(Double x, Double y, Shape s,
                                    Double[] rgb) {
        Object[] ps = {new Newpath(),
                       new Moveto(x, y),
                       s.accept(new PSVisitor(rgb)),
                       new Showpage()};
        return StringUtils.join(ps, NEWLINE);
    }

    /**
     * Converts a JPS shape to PostScript.
     * @param x the <em>x</em>-coordinate of the starting point.
     * @param y the <em>y</em>-coordinate of the starting point.
     * @param s a shape that will be translated to PostScript.
     * @param draw specifies whether or not the shape is filled.
     * @param rgb the shape's color as an array of RGB values between
     *            0 and 255, inclusive.
     * @return the PostScript representation of the shape.
     */
    public static String toPSString(Double x, Double y, Shape s,
                                    PSVisitor.Draw draw, Double[] rgb) {
        Object[] ps = {new Newpath(),
                       new Moveto(x, y),
                       s.accept(new PSVisitor(draw, rgb)),
                       new Showpage()};
        return StringUtils.join(ps, NEWLINE);
    }

    /**
     * Converts a JPS shape to PostScript.
     * @param x the <em>x</em>-coordinate of the starting point.
     * @param y the <em>y</em>-coordinate of the starting point.
     * @param s a shape that will be translated to PostScript.
     * @return the PostScript representation of the shape, drawn in
     *         black without fill.
     */
    public static String toPSString(Double x, Double y, Shape s) {
        Object[] ps = {new Newpath(),
                       new Moveto(x, y),
                       s.accept(new PSVisitor()),
                       new Showpage()};
        return StringUtils.join(ps, NEWLINE);
    }
}
