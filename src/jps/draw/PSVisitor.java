package jps.draw;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.StringUtils;

import static jps.JPS.NEWLINE;
import jps.postscript.commands.*;


public final class PSVisitor implements ShapeVisitor<String> {
    private Draw drawStyle = Draw.STROKE;
    private Double[] rgb = {0.0, 0.0, 0.0};

    /* Enum for drawing style */
    public enum Draw {
        STROKE, FILL;
    }

    /* Allow specification of stroke/fill and colors */
    public PSVisitor(Draw drawStyle, Double[] rgb) {
        this.drawStyle = drawStyle;
        this.rgb = new Double[] {rgb[0] / 255.0,
                                 rgb[1] / 255.0,
                                 rgb[2] / 255.0};
    }

    /* Allow specification of colors, assume stroke */
    public PSVisitor(Double[] rgb) {
        this.rgb = new Double[] {rgb[0] / 255.0,
                                 rgb[1] / 255.0,
                                 rgb[2] / 255.0};
    }

    /* Allow specification of stroke/fill, assume black */
    public PSVisitor(Draw drawStyle) {
        this.drawStyle = drawStyle;
    }

    /* Assume black strokes */
    public PSVisitor() {
    }

    /* Draw 360-degree arc */
    public String visit(Circle c) {
        Double radius = c.getRadius();
        Object[] ps = {new Gsave(),
                       new Currentpoint() + " translate",
                       new Moveto(radius, 0.0),
                       lineColor(),
                       new Arc(0.0, 0.0, radius, 0.0, 360.0),
                       drawStyle(),
                       new Grestore()};
        return StringUtils.join(ps, NEWLINE);
    }

    /* Draw each side, rotate appropriately, and repeat */
    public String visit(Polygon p) {
        Double height = p.accept(new HeightVisitor());
        Double sideLength = p.getSideLength();
        Integer numSides = p.getNumSides();

        /*
         * Draw over first stroke to smooth over last vertex
         */
        Object[] ps = {new Gsave(),
                       new Currentpoint() + " translate",
                       new Rmoveto(-sideLength/2.0, -height/2.0),
                       lineColor(),
                       "0 1 " + numSides + " {",
                       new Rlineto(sideLength, 0.0),
                       new Currentpoint() + " translate",
                       new Rotate(360.0 / numSides),
                       "} for",
                       drawStyle(),
                       new Grestore()};
        return StringUtils.join(ps, NEWLINE);
    }

    /* Draw across, up, back, and down */
    public String visit(Rectangle r) {
        Double height = r.accept(new HeightVisitor());
        Double width = r.accept(new WidthVisitor());

        Object[] ps = {new Gsave(),
                       new Currentpoint() + " translate",
                       new Rmoveto(-width / 2.0, -height / 2.0),
                       lineColor(),
                       new Rlineto(width, 0.0),
                       new Rlineto(0.0, height),
                       new Rlineto(-width, 0.0),
                       new Closepath(),
                       drawStyle(),
                       new Grestore()};
        return StringUtils.join(ps, NEWLINE);
    }

    /* Same as Rectangle, except without the final stroke/fill */
    public String visit(Spacer s) {
        Double height = s.accept(new HeightVisitor());
        Double width = s.accept(new WidthVisitor());

        Object[] ps = {new Gsave(),
                       new Currentpoint() + " translate",
                       new Rmoveto(-width / 2.0, -height / 2.0),
                       // new Setlinewidth(13.0),
                       lineColor(),
                       new Rlineto(width, 0.0),
                       new Rlineto(0.0, height),
                       new Rlineto(-width, 0.0),
                       new Closepath(),
                       // new Stroke(),
                       new Grestore()};
        return StringUtils.join(ps, NEWLINE);
    }

    /* Rotate coordinate system and draw */
    public String visit(Rotated r) {
        Double angle = 0.0;
        switch(r.getRotAngle()) {
        case NINETY:
            angle = 90.0;
            break;
        case ONE_HUNDRED_EIGHTY:
            angle = 180.0;
            break;
        case TWO_HUNDRED_SEVENTY:
            angle = 270.0;
            break;
        default:
            break;
        }

        Object[] ps = {new Gsave(),
                       new Currentpoint() + " translate",
                       new Rotate(angle),
                       r.getShape().accept(new PSVisitor(drawStyle, rgb)),
                       new Grestore()};
        return StringUtils.join(ps, NEWLINE);
    }

    /* Scale coordinate system and draw */
    public String visit(Scaled s) {
        Double fx = s.getFx();
        Double fy = s.getFy();

        Object[] ps = {new Gsave(),
                       new Scale(fx, fy),
                       s.getShape().accept(new PSVisitor(drawStyle, rgb)),
                       new Grestore()};
        return StringUtils.join(ps, NEWLINE);
    }

    /* Draw each constituent shape on same center point */
    public String visit(Layered l) {
        Shape[] shapes = l.getShapes();
        Queue<Object> ps = new LinkedList<Object>();

        /* Adjust rgb values due to initial division in constructor */
        rgb = new Double[] {rgb[0] * 255.0, rgb[1] * 255.0, rgb[2] * 255.0};

        for (Shape shape: shapes) {
            ps.add(shape.accept(new PSVisitor(drawStyle, rgb)));
        }

        return StringUtils.join(ps, NEWLINE);
    }

    /* Draw each shape, moving by increment of half-heights */
    public String visit(Vertical v) {
        Shape[] shapes = v.getShapes();
        Double height = v.accept(new HeightVisitor());
        Queue<Object> ps = new LinkedList<Object>();

        rgb = new Double[] {rgb[0] * 255.0, rgb[1] * 255.0, rgb[2] * 255.0};

        ps.add(new Gsave());
        ps.add(new Currentpoint() + " translate");
        ps.add(new Rmoveto(0.0, -height / 2.0));
        for (Shape shape: shapes) {
            Double halfHeight = shape.accept(new HeightVisitor()) / 2.0;
            ps.add(new Rmoveto(0.0, halfHeight));
            ps.add(shape.accept(new PSVisitor(drawStyle, rgb)));
            ps.add(new Rmoveto(0.0, halfHeight));
        }
        ps.add(new Grestore());

        return StringUtils.join(ps, NEWLINE);
    }

    /* Draw each shape, moving by increments of half-widths */
    public String visit(Horizontal h) {
        Shape[] shapes = h.getShapes();
        Double width = h.accept(new WidthVisitor());
        Queue<Object> ps = new LinkedList<Object>();

        rgb = new Double[] {rgb[0] * 255.0, rgb[1] * 255.0, rgb[2] * 255.0};

        ps.add(new Gsave());
        ps.add(new Currentpoint() + " translate");
        ps.add(new Rmoveto(-width / 2.0, 0.0));
        for (Shape shape: shapes) {
            Double halfWidth = shape.accept(new WidthVisitor()) / 2.0;
            ps.add(new Rmoveto(halfWidth, 0.0));
            ps.add(shape.accept(new PSVisitor(drawStyle, rgb)));
            ps.add(new Rmoveto(halfWidth, 0.0));
        }
        ps.add(new Grestore());

        return StringUtils.join(ps, NEWLINE);
    }

    /* Draw model of solar system */
    public String visit(SolarSystem ss) {
        Double radius = ss.getRadius();
        Layered orbits = ss.getOrbits();
        Double[] moonColors = {127.0, 127.0, 127.0};
        Queue<Object> ps = new LinkedList<Object>();

        ps.add(new Gsave());
        ps.add(new Currentpoint() + " translate");

        /* Draw orbits */
        ps.add(orbits.accept(new PSVisitor()));

        /*
         * Draw planets.
         * For each planet, rotate system randomly and
         * draw at orbital distance.
         */
        for (SolarSystem.Planet p : SolarSystem.Planet.values()) {
            ps.add(new Gsave());
            ps.add(new Rotate(Math.random() * 360.0));
            ps.add(new Rmoveto(radius * p.getScale(), 0.0));
            ps.add(p.getCircle().accept(new PSVisitor(Draw.FILL, p.getColors())));

            /* If Saturn, draw rings */
            if (p == SolarSystem.Planet.SATURN) {
                ps.add((new Circle(radius / 20.0)).accept(new PSVisitor()));
                ps.add((new Circle(radius / 25.0)).accept(new PSVisitor()));
                ps.add((new Circle(radius / 30.0)).accept(new PSVisitor()));
            }
            /* If Earth, draw Moon and its orbit */
            if (p == SolarSystem.Planet.EARTH) {
                ps.add((new Circle(radius/50.0)).accept(new PSVisitor()));
                ps.add(new Gsave());
                ps.add(new Rotate(Math.random() * 360.0));
                ps.add(new Rmoveto(radius/50.0, 0.0));
                ps.add((new Circle(radius / 175.0)).accept(new PSVisitor(Draw.FILL, moonColors)));
                ps.add(new Grestore());
            }
            ps.add(new Grestore());
        }
        ps.add(new Grestore());

        return StringUtils.join(ps, NEWLINE);
    }

    /* Draw MIT logo */
    public String visit(Logo l) {
        Double[] mitRed = {153.0, 51.0, 51.0};
        Double[] mitGray = {102.0, 102.0, 102.0};
        Queue<Object> ps = new LinkedList<Object>();

        /* Draw red portions of logo */
        ps.add(l.getLogo().accept(new PSVisitor(Draw.FILL, mitRed)));

        /* Draw single gray portion */

        Double basicWidth = l.getBasicWidth();

        Spacer m = new Spacer(5.0 * basicWidth, 5.0 * basicWidth);
        Rectangle iBottom = new Rectangle(basicWidth, basicWidth * 10.0/3.0);
        Vertical i = new Vertical(iBottom,
                                  new Spacer(basicWidth, basicWidth * 5.0/3.0));
        Spacer t = new Spacer(basicWidth * 11.0/3.0, basicWidth * 5.0);

        Horizontal mit = new Horizontal(m, i, t);
        ps.add(mit.accept(new PSVisitor(Draw.FILL, mitGray)));

        return StringUtils.join(ps, NEWLINE);
    }

    /* Satisfy the compiler */
    public String visit(Shape s) {
        System.err.println("You suck.");
        return "";
    }

    /* Return appropriate PostScript for stroke/fill */
    private String drawStyle() {
        String style = " ";
        switch (drawStyle) {
        case STROKE:
            style = "stroke";
            break;
        case FILL:
            style = "fill";
            break;
        }
        return style;
    }

    /* Return setrgbcolor command */
    private String lineColor() {
        return (rgb[0] + " " + rgb[1] + " " + rgb[2] + " setrgbcolor");
    }
}
