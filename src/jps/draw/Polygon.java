package jps.draw;


public class Polygon implements Shape {
    private Double sideLength;
    private Integer numSides;

    public Polygon(Integer numSides, Double sideLength) {
        this.numSides = numSides;
        this.sideLength = sideLength;
    }

    public Integer getNumSides() {
        return numSides;
    }

    public Double getSideLength() {
        return sideLength;
    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
