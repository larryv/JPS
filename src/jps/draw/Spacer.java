package jps.draw;


public final class Spacer extends Rectangle {

    public Spacer(Double width, Double height) {
        super(width, height);
    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
