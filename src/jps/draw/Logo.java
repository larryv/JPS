package jps.draw;


public final class Logo implements Shape {
    private Horizontal mit;     /* Wraps a Horizontal */
    private Double basicWidth;  /* The basic width used in the logo */

    /* Let user specify height of logo */
    public Logo(Double height) {
        basicWidth = height / 5.0;                  /* Set basic width */
        Double spaceWidth = basicWidth * 2.0 / 3.0; /* Set width of spaces */
        Double medHeight = height * 2.0 / 3.0;      /* Set medium height */

        /* Create "M" */
        Rectangle m1 = new Rectangle(basicWidth, height);
        Spacer m2 = new Spacer(spaceWidth, height);
        Vertical m3 = new Vertical(new Spacer(basicWidth, height - medHeight),
                                   new Rectangle(basicWidth, medHeight));
        Spacer m4 = new Spacer(spaceWidth, height);
        Rectangle m5 = new Rectangle(basicWidth, height);
        Horizontal m = new Horizontal(m1, m2, m3, m4, m5);

        /* First interletter space */
        Spacer sp1 = new Spacer(spaceWidth, height);

        /*
         * Create "I" (without gray bottom stroke;
         * will be handled in PSVisitor)
         */
        Vertical i = new Vertical(new Spacer(basicWidth, height * 4.0/5.0),
                                  new Square(basicWidth));

        /* Second interletter space */
        Spacer sp2 = new Spacer(spaceWidth, height);

        /* Create "T" */
        Horizontal t1 = new Horizontal(new Rectangle(basicWidth, medHeight),
                                       new Spacer(2 * basicWidth, medHeight));
        Spacer t2 = new Spacer(3 * basicWidth, height * 2.0/15.0);
        Rectangle t3 = new Rectangle(3 * basicWidth, basicWidth);
        Vertical t = new Vertical(t1, t2, t3);

        /* Concatenate parts */
        mit = new Horizontal(m, sp1, i, sp2, t);
    }

    /* Return constituent Horizontal */
    public Horizontal getLogo() {
        return mit;
    }

    /* Return the basic width */
    public Double getBasicWidth() {
        return basicWidth;
    }

    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
