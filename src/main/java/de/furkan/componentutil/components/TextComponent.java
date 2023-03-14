package de.furkan.componentutil.components;

import java.awt.*;
import javax.swing.*;

/** Created by Furkan.#4554 Date: 29.07.2021 */
public class TextComponent extends de.furkan.componentutil.Component {

  private final Dimension windowDimension;
  private final JLabel label = new JLabel();
  private final JPanel panel;
  int width = 0;
  private String text, fontName;
  private int x, y, fontSize, fontStyle;
  private boolean visible;
  private Color color;

  /**
   * @param text The Text that will be displayed on the JPanel/JFrame.
   * @param posX The X (Left/Right) position of the Text on the JPanel/JFrame.
   * @param posY The Y (Up/Down) position of the Text on the JPanel/JFrame.
   * @param fontName The Name of the Font that will be used for the text parameter. (Just use
   *     Java.awt Font class and choose one Font or load your own)
   * @param fontSize The Size of the Text (Font) that will be displayed.
   * @param fontStyle The Style of the Text (Font) that will be displayed. (PLAIN/BOLD/ITALIC etc.)
   *     (Also use Java.awt font class)
   * @param color The Color of the Text (Font) that will be displayed. (Just use Java.awt Color
   *     class)
   * @param windowDimension The dimension (HEIGHT and WIDTH) of the Window for calculating the
   *     Center.
   * @param panel The Panel where the Text will be displayed on.
   */
  public TextComponent(
      String text,
      int posX,
      int posY,
      Font font,
      Color color,
      Dimension windowDimension,
      JPanel panel) {
    this.visible = true;
    this.text = text;
    this.x = posX;
    this.y = posY;
    this.fontName = font.getFontName();
    this.fontSize = font.getSize();
    this.fontStyle = font.getStyle();
    this.color = color;
    this.windowDimension = windowDimension;
    this.panel = panel;
  }

  public TextComponent(
      String text,
      int posX,
      int posY,
      String fontName,
      int fontSize,
      int fontStyle,
      Color color,
      Dimension windowDimension,
      JPanel panel) {
    this.visible = true;
    this.text = text;
    this.x = posX;
    this.y = posY;
    this.fontName = fontName;
    this.fontSize = fontSize;
    this.fontStyle = fontStyle;
    this.color = color;
    this.windowDimension = windowDimension;
    this.panel = panel;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public String getFontName() {
    return fontName;
  }

  public void setFontName(String fontName) {
    this.fontName = fontName;
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

  public int getFontSize() {
    return fontSize;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public int getFontStyle() {
    return fontStyle;
  }

  public void setFontStyle(int fontStyle) {
    this.fontStyle = fontStyle;
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

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public void create(boolean centerX, boolean centerY) {
    this.label.setVisible(visible);
    this.label.setFont(new Font(this.fontName, this.fontStyle, this.fontSize));
    this.label.setText(this.text);
    this.label.setForeground(this.color);
    this.setY(centerY);
    this.setX(centerX);
    this.label.setBounds(
        centerX
            ? ((this.windowDimension.width - 15 - this.label.getPreferredSize().width) / 2) + this.x
            : this.x,
        centerY
            ? ((this.windowDimension.height - this.label.getPreferredSize().height) / 2) + y
            : this.y,
        width == 0 ? this.label.getPreferredSize().width : width,
        this.label.getPreferredSize().height);
    this.panel.add(getComponent());
    this.panel.repaint();
  }

  public JLabel getComponent() {
    return this.label;
  }
}
