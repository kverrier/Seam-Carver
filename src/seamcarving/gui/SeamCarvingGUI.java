/**
 * 
 */
package seamcarving.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import seamcarving.SeamCarvingFilter;


/**
 * @author Mark Desnoyer
 *
 */
public class SeamCarvingGUI extends JFrame implements ActionListener, HierarchyBoundsListener {

  private static final long serialVersionUID = -3209927797812220194L;
  private JTextField filenameField_;
  private JButton openButton_;
  private JButton browseButton_;
  private ImageComponent imageFrame_;
  private JPanel filenamePanel_;
  
  private BufferedImage image_;
  
  private static final int DEFAULT_MIN_WIDTH = 600;
  private static final int DEFAULT_MIN_HEIGHT = 250;
  private static final int EXTRA_WIDTH = 8;
  private static final int EXTRA_HEIGHT = 31;
  
  public static void main(String[] args) {
    JFrame gui = new SeamCarvingGUI();
    
    gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
    gui.setTitle("Seam Carving Demonstration");
    gui.setSize(DEFAULT_MIN_WIDTH, DEFAULT_MIN_HEIGHT);
    gui.setVisible(true);
  }
  
  /**
   * Default constructor
   */
  public SeamCarvingGUI() {
    Container mainPane;

    //Set up the main container
    mainPane= getContentPane();
    mainPane.setLayout(new BorderLayout());

    // Add the filename lookup
    JPanel filenamePanel = new JPanel(new FlowLayout());
    mainPane.add(filenamePanel, BorderLayout.NORTH);
    filenamePanel.add(new JLabel("Filename:"));
    filenamePanel.add(filenameField_ = new JTextField(20));
    filenamePanel.add(openButton_ = new JButton("Open"));
    openButton_.addActionListener(this);
    filenamePanel.add(browseButton_ = new JButton("Browse"));
    browseButton_.addActionListener(this);
    filenamePanel_ = filenamePanel;
    mainPane.addHierarchyBoundsListener(this);
  }

  public void actionPerformed(ActionEvent event) {
      if (event.getSource() == browseButton_) {
          browseForFile();
          return;
      }
      if (event.getSource() == openButton_) {
          openSelectedFile();
          return;
      }
  }
  
  public void ancestorResized(HierarchyEvent event) {
      if (image_ != null)
          carveSeamsToFit();
  }
  
  public void ancestorMoved(HierarchyEvent event) { }
    
  private void browseForFile() {
      // Pressed the browse button so query for a filename
      JFileChooser fc = new JFileChooser();
      
      int returnVal = fc.showOpenDialog(this);
      
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fc.getSelectedFile();
        filenameField_.setText(file.getAbsolutePath());
        openSelectedFile();
      }
  }
  
  private void openSelectedFile() {
      // Open button so try to open the file
      try {
        image_ = ImageIO.read(new File(filenameField_.getText()));
        if (image_ == null)
            return;
        
        // Display the original image in the frame
        // Add the image frames
        Container mainPane = getContentPane();
        mainPane.remove(filenamePanel_);

        imageFrame_ = new ImageComponent(image_);
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));
        imagePanel.add(imageFrame_);
        mainPane.add(imagePanel, BorderLayout.CENTER);
        setSize(image_.getWidth()+EXTRA_WIDTH, image_.getHeight()+EXTRA_HEIGHT);
        paintAll(getGraphics());
      
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Could not open image: "
            + e.getMessage());
      }
      
  }
  
  private void carveSeamsToFit() {
      // window was resized, so attempt to resize image to fit
      int imageWidth = image_.getWidth();
      int windowWidth = getWidth() - EXTRA_WIDTH;
      // Figure out how many columns to remove
      Integer nCols = imageWidth - windowWidth;
     
      SeamCarvingFilter filter = new SeamCarvingFilter();
      
      BufferedImage tempImage = image_;
      if (nCols > 0) {
        // Create a dialog so that the user knows the program is working
        JDialog dialog = new JDialog(this, "Processing");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        Container pane = dialog.getContentPane();
        JLabel progressLabel = new JLabel(String.format("Removing seam 0 of %1$d.", nCols), JLabel.CENTER);
        pane.add(progressLabel);
        dialog.setSize(200, 100);
        dialog.setVisible(true);
        
        // Remove columns        
        for (int i = 0; i < nCols; ++i) {
          progressLabel.setText(String.format("Removing seam %1$d of %2$d.", i, nCols));
          dialog.update(dialog.getGraphics());
          tempImage = filter.removeColumn(tempImage);
        }
        image_=tempImage;
        imageFrame_.setImage(image_);
        
        dialog.setVisible(false);
        return;
      }
  }
  
}
