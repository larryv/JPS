package jps.draw;


public interface Shape {
    /* Specify visitor acceptor */
    public <T> T accept(ShapeVisitor<T> visitor);
}
