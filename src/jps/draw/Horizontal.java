package jps.draw;


public class Horizontal implements Shape {
    private Shape[] shapes;

    public Horizontal(Shape... shapes) {
        this.shapes = shapes;
    }

    /* return list of shapes inside */
    public Shape[] getShapes() {
        return shapes;
    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
