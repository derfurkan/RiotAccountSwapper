package de.furkan.componentutil.components;

import java.awt.*;
import javax.swing.*;

/** Created by Furkan.#4554 Date: 05.08.2021 */
public class TextboxComponent extends de.furkan.componentutil.Component {

  private final JTextField textField = new JTextField();
  private final Dimension windowDimension;
  private final JFrame panel;
  private boolean visible;
  private String preText, fontName;
  private int x, y, height, width, fontStyle, fontSize;
  private Color textColor, boxColor;

  /**
   * @param preText The Text that will be displayed as Default text in the Box.
   * @param posX The X (Left/Right) position of the Box on the JPanel/JFrame.
   * @param posY The Y (Up/Down) position of the Box on the JPanel/JFrame.
   * @param height The Height of the Box.
   * @param width The Width of the Box.
   * @param fontName The Name of the Font that will be used for the preText parameter. (Just use
   *     Java.awt Font class and choose one Font or load your own)
   * @param fontSize The Size of the Text (Font) that will be displayed in the middle of the Box.
   * @param fontStyle The Style of the Text (Font) that will be displayed in the middle of the Box.
   *     (PLAIN/BOLD/ITALIC etc.) (Also use Java.awt font class)
   * @param textColor The Color of the Text (Font) that will be displayed in the middle of the Box.
   *     (Just use Java.awt Color class)
   * @param boxColor The Color of the Box. (Just use Java.awt Color class)
   * @param windowDimension The dimension (HEIGHT and WIDTH) of the Window for calculating the
   *     Center.
   * @param panel The Panel where the Box will be displayed on.
   */
  public TextboxComponent(
      String preText,
      int posX,
      int posY,
      int height,
      int width,
      String fontName,
      int fontSize,
      int fontStyle,
      Color textColor,
      Color boxColor,
      Dimension windowDimension,
      JFrame panel) {
    this.preText = preText;
    this.x = posX;
    this.y = posY;
    this.height = height;
    this.width = width;
    this.fontName = fontName;
    this.fontSize = fontSize;
    this.fontStyle = fontStyle;
    this.textColor = textColor;
    this.boxColor = boxColor;
    this.visible = true;
    this.windowDimension = windowDimension;
    this.panel = panel;
  }

  public JTextField getTextField() {
    return textField;
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

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public String getPreText() {
    return preText;
  }

  public void setPreText(String preText) {
    this.preText = preText;
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

  public int getFontStyle() {
    return fontStyle;
  }

  public void setFontStyle(int fontStyle) {
    this.fontStyle = fontStyle;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public int getFontSize() {
    return fontSize;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public Color getTextColor() {
    return textColor;
  }

  public void setTextColor(Color textColor) {
    this.textColor = textColor;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public Color getBoxColor() {
    return boxColor;
  }

  public void setBoxColor(Color boxColor) {
    this.boxColor = boxColor;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public void create(boolean centerX, boolean centerY) {
    this.textField.setVisible(this.visible);
    this.textField.setFont(new Font(this.fontName, this.fontStyle, this.fontSize));
    this.textField.setBackground(this.boxColor);
    this.textField.setForeground(this.textColor);
    this.textField.setText(this.preText);
    this.setY(centerY);
    this.setX(centerX);
    this.textField.setBounds(
        centerX ? (this.windowDimension.width / 2) - (this.width / 2) + x : x,
        centerY ? (this.windowDimension.height / 2) + y : y,
        this.width,
        this.height);

    this.panel.add(getComponent());
    this.panel.repaint();
  }

  public String getTypedText() {
    return this.textField.getText();
  }

  public JTextField getComponent() {
    return this.textField;
  }
}
