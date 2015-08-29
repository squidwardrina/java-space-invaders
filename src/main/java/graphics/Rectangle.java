package graphics;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Represents a rectangle by one point, height and width.
 */
public class Rectangle implements Shape {
    private Point upperLeft;
    private double width;
    private double height;
    private Color fillColor;

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft upper left vertex
     * @param width     width of the rectangle
     * @param height    height of the rectangle
     * @param fillColor color of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height,
                     Color fillColor) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
    }

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param xPos      upper left vertex
     * @param yPos      upper left vertex
     * @param width     width of the rectangle
     * @param height    height of the rectangle
     * @param fillColor color of the rectangle
     */
    public Rectangle(int xPos, int yPos, double width, double height,
                     Color fillColor) {
        this.upperLeft = new Point(xPos, yPos);
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
    }

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param xPos   upper left vertex
     * @param yPos   upper left vertex
     * @param width  width of the rectangle
     * @param height height of the rectangle
     */
    public Rectangle(int xPos, int yPos, double width, double height) {
        this.upperLeft = new Point(xPos, yPos);
        this.width = width;
        this.height = height;
        this.fillColor = Color.BLACK;
    }

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft upper left vertex
     * @param width     width of the rectangle
     * @param height    height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.fillColor = Color.BLACK;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param newWidth new width
     */
    public void setWidth(double newWidth) {
        this.width = newWidth;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the left upper vertex.
     *
     * @return left upper vertex
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Checks whether the point is one of the lower corners of this rect.
     *
     * @param point the point to check
     * @return true if it's a lower corner, false otherwise
     */
    public boolean isLowerCorner(Point point) {
        // Get lower points
        Point lowerLeft
                = new Point(upperLeft.getX(), upperLeft.getY() + height);
        Point lowerRight
                = new Point(upperLeft.getX() + width, lowerLeft.getY());

        // Return the result
        return (point.equals(lowerLeft) || point.equals(lowerRight));
    }

    /**
     * Checks whether the point is one of the upper corners of this rect.
     *
     * @param point the point to check
     * @return true if it's an upper corner, false otherwise
     */
    public boolean isUpperCorner(Point point) {
        // Get the second upper corner
        Point upperRight
                = new Point(upperLeft.getX() + width, upperLeft.getY());

        // Return the result
        return (point.equals(upperLeft) || point.equals(upperRight));
    }

    /**
     * Draws the rectangle.
     *
     * @param d the surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(fillColor);
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(),
                (int) width, (int) height);
    }

    /**
     * Returns the y coord of the bottom of the rectangle.
     *
     * @return the y coord of the bottom of the rectangle.
     */
    public int getBottom() {
        return (int) Math.round(upperLeft.getY() + height);
    }

    /**
     * Returns the y coord of the top of the rectangle.
     *
     * @return the y coord of the bottom of the rectangle.
     */
    public int getTop() {
        return (int) Math.round(upperLeft.getY());
    }

    /**
     * Returns the x coordinate of the right side of the rectangle.
     *
     * @return the x coordinate of the right side of the rectangle.
     */
    public int getRight() {
        return (int) Math.round(upperLeft.getX() + width);
    }

    /**
     * Returns the x coordinate of the left side of the rectangle.
     *
     * @return the x coordinate of the left side of the rectangle.
     */
    public int getLeft() {
        return (int) Math.round(upperLeft.getX());
    }
}
