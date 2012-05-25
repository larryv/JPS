package jps.draw;


public final class Circle implements Shape {
    private Double radius;

    public Circle(Double radius) {
        this.radius = radius;
    }

    public Double getRadius() {
        return radius;
    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
