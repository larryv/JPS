package jps.draw;


public class Rectangle implements Shape {
    private Double width;
    private Double height;

    public Rectangle(Double width, Double height) {
        this.width = width;
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
