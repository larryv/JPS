package jps.draw;

import java.util.TreeSet;


public final class WidthVisitor implements ShapeVisitor<Double> {
    /* Satisfy compiler */
    public Double visit(Shape s) {
        return null;
    }

    public Double visit(Circle c) {
        return 2 * c.getRadius();       /* Twice the radius */
    }

    public Double visit(Polygon p) {
        Integer n = p.getNumSides();
        Double e = p.getSideLength();

        /* Fancy math */
        if (n % 2 == 1) {
            return (e * Math.sin(Math.PI * (n-1) / (2*n)))
                   / Math.sin(Math.PI / n);
        }
        if (n % 4 == 0) {
            return (e * Math.cos(Math.PI / n)) / Math.sin(Math.PI / n);
        }
        return e / Math.sin(Math.PI / n);
    }

    public Double visit(Rectangle r) {
        return r.getWidth();            /* Just get width */
    }

    public Double visit(Spacer s) {
        return s.getWidth();            /* Same as rectangle */
    }

    public Double visit(Rotated r) {
        Double width = 0.0;

        /* Get width from different measurement, depending on angle */
        switch (r.getRotAngle()) {
        case NINETY:
        case TWO_HUNDRED_SEVENTY:
            width = r.getShape().accept(new HeightVisitor());
            break;
        case ONE_HUNDRED_EIGHTY:
            width = r.getShape().accept(new WidthVisitor());
            break;
        default:
            break;
        }
        return width;
    }

    public Double visit(Scaled s) {
        /* Scale it! */
        return s.getFx() * s.getShape().accept(new WidthVisitor());
    }

    public Double visit(Layered l) {
        Double width;
        Double maxWidth = 0.0;
        Shape[] shapes = l.getShapes();

        /* Get widest shape */
        for (Shape shape: shapes) {
            width = shape.accept(new WidthVisitor());
            if (width > maxWidth) {
                maxWidth = width;
            }
        }

        return maxWidth;
    }

    public Double visit(Vertical v) {
        Double width = 0.0;
        Double maxWidth = 0.0;
        Shape[] shapes = v.getShapes();

        /* Get widest shape */
        for (Shape shape: shapes) {
            width = shape.accept(new WidthVisitor());
            if (width > maxWidth) {
                maxWidth = width;
            }
        }

        return maxWidth;
    }

    public Double visit(Horizontal h) {
        Double width = 0.0;
        Shape[] shapes = h.getShapes();

        /* Add all widths */
        for (Shape shape: shapes) {
            width += shape.accept(new WidthVisitor());
        }

        return width;
    }

    public Double visit(SolarSystem ss) {
        return 2 * ss.getRadius();      /* Same as circle */
    }

    public Double visit(Logo l) {
        /* Get width of constituent Horizontal */
        return l.getLogo().accept(new WidthVisitor());
    }
}
