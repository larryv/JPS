package jps.draw;


public final class SolarSystem implements Shape {
    private Double radius;

    /* Circles representing the orbits */
    private Circle neptuneOrbit;
    private Scaled uranusOrbit, saturnOrbit, jupiterOrbit, marsOrbit,
            earthOrbit, venusOrbit, mercuryOrbit;
    private Layered orbits;

    /* Circles representing the planets */
    private static Circle sun, mercury, venus, earth, mars, jupiter,
            saturn, uranus, neptune;

    public SolarSystem(Double radius) {
        this.radius = radius;
        Circle c = new Circle(radius);
        this.neptuneOrbit = c;
        this.uranusOrbit = new Scaled(c, 0.75, 0.75);
        this.saturnOrbit = new Scaled(c, 0.55, 0.55);
        this.jupiterOrbit = new Scaled(c, 0.4, 0.4);
        this.marsOrbit = new Scaled(c, 0.25, 0.25);
        this.earthOrbit = new Scaled(c, 0.2, 0.2);
        this.venusOrbit = new Scaled(c, 0.15, 0.15);
        this.mercuryOrbit = new Scaled(c, 0.1, 0.1);

        /* Create Layered object containing all orbits */
        this.orbits = new Layered(this.neptuneOrbit,
                                  this.uranusOrbit,
                                  this.saturnOrbit,
                                  this.jupiterOrbit,
                                  this.marsOrbit,
                                  this.earthOrbit,
                                  this.venusOrbit,
                                  this.mercuryOrbit);

        /* Create planet objects */
        this.sun = new Circle(this.radius/15.0);
        this.mercury = new Circle(this.radius/150.0);
        this.venus = new Circle(this.radius/100.0);
        this.earth = new Circle(this.radius/100.0);
        this.mars = new Circle(this.radius/125.0);
        this.jupiter = new Circle(this.radius/40.0);
        this.saturn = new Circle(this.radius/50.0);
        this.uranus = new Circle(this.radius/60.0);
        this.neptune = new Circle(this.radius/60.0);
    }

    public Double getRadius() {
        return radius;
    }

    public Layered getOrbits() {
        return orbits;
    }

    /* Enum comtaining color information, reference to appropriate
     * Circle object, and scaling for placement on orbits */
    public enum Planet {
        /* PLANET (r, g, b, Circle, scaling factor) */
        SUN (255.0, 215.0, 0.0, sun, 0.0),
        MERCURY (102.0, 102.0, 102.0, mercury, 0.1),
        VENUS (255.0, 165.0, 0.0, venus, 0.15),
        EARTH (0.0, 0.0, 205.0, earth, 0.2),
        MARS (255.0, 69.0, 0.0, mars, 0.25),
        JUPITER (238.0, 118.0, 33.0, jupiter, 0.4),
        SATURN (255.0, 236.0, 139.0, saturn, 0.55),
        URANUS (135.0, 206.0, 255.0, uranus, 0.75),
        NEPTUNE (24.0, 116.0, 205.0, neptune, 1.0);

        private final Double red, green, blue, scale;
        private Circle circle;
        Planet(Double red, Double green, Double blue,
                        Circle circle, Double scale) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.circle = circle;
            this.scale = scale;
        }

        public Double[] getColors() {
            Double[] colors = {this.red, this.green, this.blue};
            return colors;
        }

        public Circle getCircle() {
            return circle;
        }

        public Double getScale() {
            return scale;
        }

    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
