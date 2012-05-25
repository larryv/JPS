package jps.draw;


public final class Scaled implements Shape {
    private Double fx;      /* Horizontal scaling factor */
    private Double fy;      /* Vertical scaling factor */
    private Shape shape;    /* Base shape */

    public Scaled(Shape shape, Double fx, Double fy) {
        this.shape = shape;
        this.fx = fx;
        this.fy = fy;
    }

    public Double getFx() {
        return this.fx;
    }

    public Double getFy() {
        return this.fy;
    }

    public Shape getShape() {
        return this.shape;
    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
