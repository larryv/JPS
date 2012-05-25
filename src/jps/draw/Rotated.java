package jps.draw;


public final class Rotated implements Shape {
    private RotationAngle angle;    /* Store rotation angle */
    private Shape shape;            /* Store shape */

    /* Enum to restrict possible angles */
    public enum RotationAngle {
        NINETY, ONE_HUNDRED_EIGHTY, TWO_HUNDRED_SEVENTY;
    }

    public Rotated(Shape shape, RotationAngle angle) {
        this.shape = shape;
        this.angle = angle;
    }
    public RotationAngle getRotAngle() {
        return angle;
    }

    public Shape getShape() {
        return shape;
    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
