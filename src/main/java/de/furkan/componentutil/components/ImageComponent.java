package de.furkan.componentutil.components;

import de.furkan.componentutil.Component;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

/** Created by Furkan.#4554 Date: 13.10.2021 */
public class ImageComponent extends Component {

  private final Dimension windowDimension;
  private final JPanel panel;
  private JLabel label;
  private URL image;
  private int x, y, width, height;
  private boolean visible;

  /**
   * @param posX The X (Left/Right) position of the Image on the JPanel/JFrame.
   * @param posY The Y (Up/Down) position of the Image on the JPanel/JFrame.
   * @param width The width position of the Image on the JPanel/JFrame.
   * @param height The height position of the Image on the JPanel/JFrame.
   * @param windowDimension The dimension (HEIGHT and WIDTH) of the Window for calculating the
   *     Center.
   * @param panel The Panel where the Image will be displayed on.
   * @param image The Image that will be displayed.
   */
  public ImageComponent(
      URL image,
      int posX,
      int posY,
      int width,
      int height,
      int imageScaling,
      Dimension windowDimension,
      JPanel panel) {
    this.visible = true;
    this.x = posX;
    this.y = posY;
    this.width = width;
    this.height = height;
    this.image = image;
    this.windowDimension = windowDimension;
    try {
      this.label =
          new JLabel(
              new ImageIcon(
                  ImageIO.read(image).getScaledInstance(this.width, this.height, imageScaling)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.panel = panel;
  }

  public URL getImage() {
    return image;
  }

  public void setImage(URL image) {
    this.image = image;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public boolean isCenterX() {
    return this.getCenterX();
  }

  public void setCenterX(boolean centerX) {
    this.setX(centerX);
    this.create(this.isCenterX(), this.isCenterY());
  }

  public boolean isCenterY() {
    return this.getCenterY();
  }

  public void setCenterY(boolean centerY) {
    this.setY(centerY);
    this.create(this.isCenterX(), this.isCenterY());
  }

  public void create(boolean centerX, boolean centerY, boolean autoWidth, boolean autoHeight) {
    this.label.setVisible(visible);
    this.setY(centerY);
    this.setX(centerX);
    this.label.setBounds(this.x, this.y, this.width, this.height);
    this.panel.add(getComponent());
    this.panel.repaint();
  }

  public JLabel getComponent() {
    return this.label;
  }
}
