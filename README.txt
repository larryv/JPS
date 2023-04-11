====================================
JPS: A JAVA-TO-POSTSCRIPT TRANSLATOR
====================================

This is from my sophomore year of college, so you probably shouldn’t use
it for anything important.  Or for anything at all.


What?
=====

JPS is a simple drawing library that provides a high-level method of
describing simple shapes (and combinations thereof) using Java
objects, as well as an API for outputting these shapes as PostScript.


How?
====

Building
--------

The buildfile requires Apache Ant 1.6 [3] or later, plus the JUnit Ant
task if you want to run the unit tests.  (Some package managers provide
optional Ant tasks separately from core Ant.)  The library itself
requires Java 5 [4] or later.

  - To build: `ant build`
  - To package: `ant jar` (This is the default.)
  - To test: `ant test` or `ant test-jar` or `ant test-all`
  - To document: `ant doc`
  - To clean: `ant clean` or `ant clean-jar` or `ant clean-doc` or
    `ant clean-all`

The library has been successfully built and tested in the following
environments.  Your mileage may vary, caveat emptor, etc., but all you
really need is some form of javac and java (and javadoc, if
you’re into that sort of debauchery).

  - Mac OS X 10.7, using Java SE 6
  - Ubuntu Server 12.04 LTS, using OpenJDK 6
  - Red Hat Enterprise Linux Server 6.2, using OpenJDK 6
  - SUSE Linux Enterprise Server 11, using Java SE 6 (IBM J9 VM)
  - Too lazy to spin up any more VMs.  You get the idea.


Using
-----

For the sake of argument, let’s assume that you actually manage to set
your classpath correctly.  To generate shapes in PostScript, build
objects using the jps.draw classes and pass them to the static methods
inside jps.JPS.

    import jps.JPS;
    import jps.draw.Circle;

    String ps = JPS.toPSString(400.0, 400.0, new Circle(50.0));

Currently, only the jps.JPS methods have Javadoc.  For a short
description of the available shape classes, see assignment.html in the
project’s root directory.  For further usage examples, poke around the
test suite in test_src/jpstests/JPSTests.java.


Who?  Why?
==========

Nina Guo [5] and I wrote this in 2008 as an assignment for 6.005 [6], an
undergraduate software construction class at MIT [7].  The code is
largely unchanged from then; I’ve made some cosmetic changes, revised
the paltry Javadoc (which is still paltry), and shuffled the package
layout.  For better or worse, it retains the original architecture.
Yay, visitor pattern [8]!

The aptly-named assignment.html contains the original description of
the assignment, including the problem statement, pedagogical goals, and
specification.


References
==========

 3. http://ant.apache.org/
 4. http://www.java.com/
 5. http://www.linkedin.com/in/ninaguo
 6. http://stellar.mit.edu/S/course/6/sp08/6.005/
 7. http://web.mit.edu/
 8. http://en.wikipedia.org/wiki/Visitor_pattern
