/**
 * 
 */
package edu.cmu.cs211.seamcarving.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Class that allows an image to be displayed in the GUI
 * 
 * @author Mark Desnoyer
 *
 */
public class ImageComponent extends Component {
    private static final long serialVersionUID = 1L;
    private Image image_;

    /**
     * Create an image component to display the image
     * 
     * @param image Image to display
     * @param width The display width
     * @param height The display height
     */
    public ImageComponent(Image image, int width, int height) {
        image_ = image;
        setSize(width, height);
    }
    
    public ImageComponent(Image image) {
        setImage(image);
    }
    
    public ImageComponent() {
        this(null, 0, 0);
    }

    public void setImage(Image image) {
        image_ = image;
        if (image != null)
            super.setSize(image.getWidth(null), image.getHeight(null));
        if (isVisible())
            repaint();
    }

    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (isVisible())
            repaint();
    }

    public Image getImage() { return image_; }

    public void paint(Graphics g) {
        if (image_ != null) {
            g.drawImage(image_, 0, 0, null);
        }
    }

}
