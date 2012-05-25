package jps.draw;


public interface ShapeVisitor<T> {
    /* visit methods for each of the JPS shapes */
    T visit(Circle c);
    T visit(Polygon p);
    T visit(Rectangle r);
    T visit(Spacer s);
    T visit(Rotated r);
    T visit(Scaled s);
    T visit(Layered l);
    T visit(Vertical v);
    T visit(Horizontal h);

    /* visit methods for extensions */
    T visit(SolarSystem ss);
    T visit(Logo l);
}
