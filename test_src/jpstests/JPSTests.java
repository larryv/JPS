package jpstests;

import org.apache.commons.lang3.StringUtils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import jps.JPS;
import jps.draw.*;

public final class JPSTests {

    // Test circle, fill drawing
    @Test
    public void testCircle() {
        Circle c = new Circle(34.9);
        String ps = JPS.toPSString(400.0, 400.0, c, PSVisitor.Draw.FILL);

        String[] expected = {"newpath",
                             "400.0 400.0 moveto",
                             "gsave",
                             "currentpoint translate",
                             "34.9 0.0 moveto",
                             "0.0 0.0 0.0 setrgbcolor",
                             "0.0 0.0 34.9 0.0 360.0 arc",
                             "fill",
                             "grestore",
                             "showpage"};

        assertEquals(ps, StringUtils.join(expected, JPS.NEWLINE));
    }

    // Test 9-sided polygon with Papayawhip fill color
    @Test
    public void testPolygon() {
        Polygon p = new Polygon(9, 30.0);
        Double[] color = {255.0, 239.0, 213.0};
        String ps = JPS.toPSString(400.0, 400.0, p, PSVisitor.Draw.FILL,
                                   color);

        String[] expected = {"newpath",
                             "400.0 400.0 moveto",
                             "gsave",
                             "currentpoint translate",
                             "-15.0 -42.53461364713282 rmoveto",
                             "1.0 0.9372549019607843 0.8352941176470589 setrgbcolor",
                             "0 1 9 {",
                             "30.0 0.0 rlineto",
                             "currentpoint translate",
                             "40.0 rotate",
                             "} for",
                             "fill",
                             "grestore",
                             "showpage"};

        assertEquals(ps, StringUtils.join(expected, JPS.NEWLINE));
    }

    // Test 30 by 50 rectangle
    @Test
    public void testRectangle() {
        Rectangle r = new Rectangle(30.0, 50.0);
        String ps = JPS.toPSString(400.0, 400.0, r);

        String[] expected = {"newpath",
                             "400.0 400.0 moveto",
                             "gsave",
                             "currentpoint translate",
                             "-15.0 -25.0 rmoveto",
                             "0.0 0.0 0.0 setrgbcolor",
                             "30.0 0.0 rlineto",
                             "0.0 50.0 rlineto",
                             "-30.0 0.0 rlineto",
                             "closepath",
                             "stroke",
                             "grestore",
                             "showpage"};

        assertEquals(ps, StringUtils.join(expected, JPS.NEWLINE));
    }

    // Test square of side 31.1
    @Test
    public void testSq() {
        Square s = new Square(31.1);
        String ps = JPS.toPSString(400.0, 400.0, s);

        String[] expected = {"newpath",
                             "400.0 400.0 moveto",
                             "gsave",
                             "currentpoint translate",
                             "-15.55 -15.550000000000002 rmoveto",
                             "0.0 0.0 0.0 setrgbcolor",
                             "0 1 4 {",
                             "31.1 0.0 rlineto",
                             "currentpoint translate",
                             "90.0 rotate",
                             "} for",
                             "stroke",
                             "grestore",
                             "showpage"};

        assertEquals(ps, StringUtils.join(expected, JPS.NEWLINE));
    }

    // Test rotated of spacer by 270 degrees
    @Test
    public void testRotSpacer() {
        Rotated.RotationAngle angle = Rotated.RotationAngle.TWO_HUNDRED_SEVENTY;
        Rotated rot = new Rotated(new Spacer(26.0, 44.0), angle);
        String ps = JPS.toPSString(400.0, 400.0, rot);

        String[] expected = {"newpath",
                             "400.0 400.0 moveto",
                             "gsave",
                             "currentpoint translate",
                             "270.0 rotate",
                             "gsave",
                             "currentpoint translate",
                             "-13.0 -22.0 rmoveto",
                             "0.0 0.0 0.0 setrgbcolor",
                             "26.0 0.0 rlineto",
                             "0.0 44.0 rlineto",
                             "-26.0 0.0 rlineto",
                             "closepath",
                             "grestore",
                             "grestore",
                             "showpage"};

        assertEquals(ps, StringUtils.join(expected, JPS.NEWLINE));
    }

    // Test a circle scaled horizontally by 3.0 and vertically by 0.5
    @Test
    public void testScaledCir() {
        Scaled s = new Scaled(new Circle(34.9), 3.0, 0.5);
        String ps = JPS.toPSString(400.0, 400.0, s);

        String[] expected = {"newpath",
                             "400.0 400.0 moveto",
                             "gsave",
                             "3.0 0.5 scale",
                             "gsave",
                             "currentpoint translate",
                             "34.9 0.0 moveto",
                             "0.0 0.0 0.0 setrgbcolor",
                             "0.0 0.0 34.9 0.0 360.0 arc",
                             "stroke",
                             "grestore",
                             "grestore",
                             "showpage"};

        assertEquals(ps, StringUtils.join(expected, JPS.NEWLINE));
    }

    // Test the layering of a circle and a triangle
    @Test
    public void testLayeredCirTri() {
        Layered lay = new Layered(new Circle(34.9), new Triangle(30.0));
        String ps = JPS.toPSString(350.0, 299.1, lay);

        String[] expected = {"newpath",
                             "350.0 299.1 moveto",
                             "gsave",
                             "currentpoint translate",
                             "34.9 0.0 moveto",
                             "0.0 0.0 0.0 setrgbcolor",
                             "0.0 0.0 34.9 0.0 360.0 arc",
                             "stroke",
                             "grestore",
                             "gsave",
                             "currentpoint translate",
                             "-15.0 -12.99038105676658 rmoveto",
                             "0.0 0.0 0.0 setrgbcolor",
                             "0 1 3 {",
                             "30.0 0.0 rlineto",
                             "currentpoint translate",
                             "120.0 rotate",
                             "} for",
                             "stroke",
                             "grestore",
                             "showpage"};

        assertEquals(ps, StringUtils.join(expected, JPS.NEWLINE));
    }

    // Test vertical structure of circle below square below triangle
    // with lines of Papayawhip color
    @Test
    public void testVertical(){
        Vertical v = new Vertical(new Circle(34.9), new Square(31.1),
                                  new Triangle(30.0));
        Double[] color = {255.0, 239.0, 213.0};
        String ps = JPS.toPSString(400.0, 400.0, v, color);

        String[] expected = {"newpath",
                             "400.0 400.0 moveto",
                             "gsave",
                             "currentpoint translate",
                             "0.0 -63.44038105676658 rmoveto",

                             "0.0 34.9 rmoveto",
                             "gsave",
                             "currentpoint translate",
                             "34.9 0.0 moveto",
                             "1.0 0.9372549019607843 0.8352941176470589 setrgbcolor",
                             "0.0 0.0 34.9 0.0 360.0 arc",
                             "stroke",
                             "grestore",
                             "0.0 34.9 rmoveto",

                             "0.0 15.550000000000002 rmoveto",
                             "gsave",
                             "currentpoint translate",
                             "-15.55 -15.550000000000002 rmoveto",
                             "1.0 0.9372549019607843 0.8352941176470589 setrgbcolor",
                             "0 1 4 {",
                             "31.1 0.0 rlineto",
                             "currentpoint translate",
                             "90.0 rotate",
                             "} for",
                             "stroke",
                             "grestore",
                             "0.0 15.550000000000002 rmoveto",

                             "0.0 12.99038105676658 rmoveto",
                             "gsave",
                             "currentpoint translate",
                             "-15.0 -12.99038105676658 rmoveto",
                             "1.0 0.9372549019607843 0.8352941176470589 setrgbcolor",
                             "0 1 3 {",
                             "30.0 0.0 rlineto",
                             "currentpoint translate",
                             "120.0 rotate",
                             "} for",
                             "stroke",
                             "grestore",
                             "0.0 12.99038105676658 rmoveto",

                             "grestore",
                             "showpage"};

        assertEquals(ps, StringUtils.join(expected, JPS.NEWLINE));
    }

    // Test horizontal with rotated spacer and scaled circle to the right
    @Test
    public void testHorizontal() {
        Rotated.RotationAngle angle = Rotated.RotationAngle.TWO_HUNDRED_SEVENTY;
        Rotated r = new Rotated(new Spacer(26.0, 44.0), angle);
        Scaled s = new Scaled(new Circle(34.9), 3.0, 0.5);
        Horizontal h = new Horizontal(r, s);
        String ps = JPS.toPSString(400.0, 400.0, h);

        String[] expected = {"newpath",
                             "400.0 400.0 moveto",
                             "gsave",
                             "currentpoint translate",
                             "-126.69999999999999 0.0 rmoveto",

                             "22.0 0.0 rmoveto",
                             "gsave",
                             "currentpoint translate",
                             "270.0 rotate",
                             "gsave",
                             "currentpoint translate",
                             "-13.0 -22.0 rmoveto",
                             "0.0 0.0 0.0 setrgbcolor",
                             "26.0 0.0 rlineto",
                             "0.0 44.0 rlineto",
                             "-26.0 0.0 rlineto",
                             "closepath",
                             "grestore",
                             "grestore",
                             "22.0 0.0 rmoveto",

                             "104.69999999999999 0.0 rmoveto",
                             "gsave",
                             "3.0 0.5 scale",
                             "gsave",
                             "currentpoint translate",
                             "34.9 0.0 moveto",
                             "0.0 0.0 0.0 setrgbcolor",
                             "0.0 0.0 34.9 0.0 360.0 arc",
                             "stroke",
                             "grestore",
                             "grestore",
                             "104.69999999999999 0.0 rmoveto",

                             "grestore",
                             "showpage"};

        assertEquals(ps, StringUtils.join(expected, JPS.NEWLINE));
    }
}
