package jps.draw;


public final class Vertical implements Shape {
    private Shape[] shapes;

    public Vertical(Shape... shapes) {
        this.shapes = shapes;
    }

    public Shape[] getShapes() {
        return shapes;
    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
