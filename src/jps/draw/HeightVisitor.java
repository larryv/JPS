package jps.draw;


public final class HeightVisitor implements ShapeVisitor<Double> {
    /* To satisfy compiler */
    public Double visit(Shape s) {
        return null;
    }

    public Double visit(Circle c) {
        return 2 * c.getRadius();   /* Height is simply twice the radius */
    }

    public Double visit(Polygon p) {
        Integer n = p.getNumSides();
        Double e = p.getSideLength();

        /* Complicated math! */
        if (n % 2 == 1) {
            return e * (1 + Math.cos(Math.PI / n))
                   / (2 * Math.sin(Math.PI / n));
        }
        if (n % 4 == 0) {
            return e * (Math.cos(Math.PI / n)) / Math.sin(Math.PI / n);
        }
        return e * (Math.cos(Math.PI / n)) / Math.sin(Math.PI / n);
    }

    public Double visit(Rectangle r) {
        return r.getHeight();           /* Just get height */
    }

    public Double visit(Spacer s) {
        return s.getHeight();           /* Same as rectangle */
    }

    public Double visit(Rotated r) {
        Double height = 0.0;

        /* Get appropriate measurement depending on angle */
        switch (r.getRotAngle()) {
        case NINETY:
        case TWO_HUNDRED_SEVENTY:
            height = r.getShape().accept(new WidthVisitor());
            break;
        case ONE_HUNDRED_EIGHTY:
            height = r.getShape().accept(new HeightVisitor());
            break;
        default:
            break;
        }
        return height;
    }

    public Double visit(Scaled s) {
        /* Scale height */
        return s.getFy() * s.getShape().accept(new HeightVisitor());
    }

    public Double visit(Layered l) {
        Double height;
        Double maxHeight = 0.0;
        Shape[] shapes = l.getShapes();

        for (int i = 0; i < shapes.length; i++) {
            /* Get height of each shape in Layered */
            height = shapes[i].accept(new HeightVisitor());

            /* If height is greater than the max height, replace it */
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        return maxHeight;
    }

    public Double visit(Vertical v) {
        Double height = 0.0;
        Shape[] shapes = v.getShapes();

        for (int i = 0; i < shapes.length; i++) {
            /* Aggregate heights */
            height += shapes[i].accept(new HeightVisitor());
        }

        return height;
    }

    public Double visit(Horizontal h) {
        Double height;
        Double maxHeight = 0.0;
        Shape[] shapes = h.getShapes();

        for (int i = 0; i < shapes.length; i++) {
            /* Get greatest of heights of constituent shapes */
            height = shapes[i].accept(new HeightVisitor());
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        return maxHeight;
    }

    public Double visit(SolarSystem ss) {
        return 2 * ss.getRadius();          /* Twice the radius */
    }

    public Double visit(Logo l) {
        /* Height of logo is the height of constituent Horizontal */
        return l.getLogo().accept(new HeightVisitor());
    }
}
