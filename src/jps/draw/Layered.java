package jps.draw;


public final class Layered implements Shape {
    private Shape[] shapes;

    public Layered(Shape... shapes) {
        this.shapes = shapes;
    }

    public Shape[] getShapes() {
        return shapes;
    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
