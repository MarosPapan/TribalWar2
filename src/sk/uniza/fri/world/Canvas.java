package sk.uniza.fri.world;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
//import java.util.TreeMap;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * 15. 4. 2022 - 11:53
 *
 */

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example.
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 *
 * @version: 1.6.1 (shapes)
 */
public class Canvas {
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas() {
        if (Canvas.canvasSingleton == null) {
            Canvas.canvasSingleton = new Canvas("World of Tribals", 1200, 600, Color.lightGray);
        }

        Canvas.canvasSingleton.setVisible(true);
        return Canvas.canvasSingleton;
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color background;
    private Image canvasImage;
    private Timer timer;
    private List<Object> objects;
    private HashMap<Object, IDraw> shapes;

    /**
     * Create a Canvas.
     * @param title  title to appear in Canvas Frame
     * @param width  the desired width for the canvas
     * @param height  the desired height for the canvas
     * @param bgClour  the desired background colour of the canvas
     */
    public Canvas(String title, int width, int height, Color bgClour) {
        this.frame = new JFrame();
        this.canvas = new CanvasPane();
        this.frame.setContentPane(this.canvas);

        this.frame.setTitle(title);

        //this.frame.setPreferredSize(new Dimension(width, height));
        this.canvas.setPreferredSize(new Dimension(width, height));

        this.timer = new javax.swing.Timer(25, null);
        this.timer.start();
        this.background = bgClour;
        this.frame.pack();
        this.objects = new ArrayList<Object>();
        this.shapes = new HashMap<Object, IDraw>();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false)
     */
    public void setVisible(boolean visible) {
        if (this.graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour

            //Dimension size = this.frame.getSize();
            Dimension size = this.canvas.getSize();

            //this.canvasImage = this.frame.createImage(size.width, size.height);
            this.canvasImage = this.canvas.createImage(size.width, size.height);

            this.graphic = (Graphics2D)this.canvasImage.getGraphics();
            this.graphic.setColor(this.background);
            this.graphic.fillRect(0, 0, size.width, size.height);
            this.graphic.setColor(Color.black);
        }
        this.frame.setVisible(visible);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public CanvasPane getPane() {
        return this.canvas;
    }

    public Color getColor() {
        return this.background;
    }

    public void drawString(String text, int x, int y, Color farba) {
        Color povodnaFarba = this.graphic.getColor();
        this.graphic.setColor(farba);
        this.graphic.drawString(text, x, y);
        this.graphic.setColor(povodnaFarba);
        this.canvas.repaint();
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
    // Note: this is a slightly backwards way of maintaining the shape
    // objects. It is carefully designed to keep the visible shape interfaces
    // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, String color, Shape shape) {
        this.objects.remove(referenceObject);   // just in case it was already there
        this.objects.add(referenceObject);      // add at the end
        this.shapes.put(referenceObject, new ShapeDescription(shape, color));
        this.redraw();
    }

    /**
     * Draw a given image onto the canvas.
     * @param  referenceObject  an object to define identity for this image
     * @param  image            the image object to be drawn on the canvas
     */
    // Note: this is a slightly backwards way of maintaining the shape
    // objects. It is carefully designed to keep the visible shape interfaces
    // in this project clean and simple for educational purposes.
    //public void draw(Object referenceObject, BufferedImage image, AffineTransform transform) {
    public void draw(Object referenceObject, BufferedImage image, int posX, int posY) {
        this.objects.remove(referenceObject);   // just in case it was already there
        this.objects.add(referenceObject);      // add at the end
        this.shapes.put(referenceObject, new PictureDescription(image, posX, posY));
        this.redraw();
    }

    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased
     */
    public void erase(Object referenceObject) {
        this.objects.remove(referenceObject);   // just in case it was already there
        this.shapes.remove(referenceObject);
        this.redraw();
    }

    /**
     * Set the foreground colour of the Canvas.
     * @param  newColour   the new colour for the foreground of the Canvas
     */
    public void setForegroundColor(String newColour) {
        if (newColour.equals("red")) {
            this.graphic.setColor(Color.red);
        } else if (newColour.equals("black")) {
            this.graphic.setColor(Color.black);
        } else if (newColour.equals("blue")) {
            this.graphic.setColor(Color.blue);
        } else if (newColour.equals("orange")) {
            this.graphic.setColor(Color.orange);
        } else if (newColour.equals("yellow")) {
            this.graphic.setColor(Color.yellow);
        } else if (newColour.equals("green")) {
            this.graphic.setColor(Color.green);
        } else if (newColour.equals("magenta")) {
            this.graphic.setColor(Color.magenta);
        } else if (newColour.equals("white")) {
            this.graphic.setColor(Color.white);
        } else {
            this.graphic.setColor(Color.black);
        }
    }

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            System.out.println("Cakanie sa nepodarilo");
        }
    }

    /**
     * * Redraw all shapes currently on the Canvas.
     */
    private void redraw() {
        this.erase();
        for (Object shape : this.objects ) {
            this.shapes.get(shape).draw(this.graphic);
        }
        this.canvas.repaint();
        //this.frame.repaint();
    }

    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase() {
        Color original = this.graphic.getColor();
        this.graphic.setColor(this.background);
        Dimension size = this.canvas.getSize();
        //Dimension size = this.frame.getSize();
        this.graphic.fill(new Rectangle(0, 0, size.width, size.height));
        this.graphic.setColor(original);
    }

    public void addKeyListener(KeyListener listener) {
        this.frame.addKeyListener(listener);
    } //****

    public void addMouseListener(MouseListener listener) {
        this.canvas.addMouseListener(listener);
        //this.frame.addMouseListener(listener);


    }

    public void addTimerListener(ActionListener listener) {
        this.timer.addActionListener(listener);
    }


    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel {
        public void paint(Graphics graphic) {
            graphic.drawImage(Canvas.this.canvasImage, 0, 0, null);
        }
    }

    /***********************************************************************
     * Inner interface IDraw - defines functions that need to be supported by
     * shapes descriptors
     */
    private interface IDraw {
        void draw(Graphics2D graphic);
    }

    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription implements IDraw {
        private Shape shape;
        private String color;

        @SuppressWarnings("checkstyle:RedundantModifier")
        ShapeDescription(Shape shape, String color) {
            this.shape = shape;
            this.color = color;
        }

        public void draw(Graphics2D graphic) {
            Canvas.this.setForegroundColor(this.color);
            graphic.fill(this.shape);
        }
    }

    private class PictureDescription implements IDraw {
        private BufferedImage picture;
        private int posX;
        private int posY;
        private AffineTransform transformation;

        @SuppressWarnings("checkstyle:RedundantModifier")
        //PictureDescription(BufferedImage picture, AffineTransform transformation) {
        PictureDescription(BufferedImage picture, int posX, int posY) {
            this.picture = picture;
            //this.transformation = transformation;
            this.posX = posX;
            this.posY = posY;
        }

        public void draw(Graphics2D graphics) {
            //graphic.drawImage(this.picture, this.transformation, null);
            graphic.drawImage(this.picture, this.posX, this.posY, null);
        }
    }

}
