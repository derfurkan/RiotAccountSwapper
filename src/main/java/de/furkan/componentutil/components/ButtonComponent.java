package de.furkan.componentutil.components;

import de.furkan.componentutil.ButtonClick;
import java.awt.*;
import javax.swing.*;

/** Created by Furkan.#4554 Date: 30.07.2021 */
public class ButtonComponent extends de.furkan.componentutil.Component {

  private final JButton jButton = new JButton();
  private final Dimension windowDimension;
  private final JPanel panel;
  private boolean visible;
  private String buttonText, fontName;
  private int x, y, height, width, fontStyle, fontSize;
  private Color textColor, buttonColor;

  /**
   * @param buttonText The Text that will be displayed in the middle of the Button.
   * @param posX The X (Left/Right) position of the Button on the JPanel/JFrame.
   * @param posY The Y (Up/Down) position of the Button on the JPanel/JFrame.
   * @param height The Height of the Button.
   * @param width The Width of the Button.
   * @param fontName The Name of the Font that will be used for the buttonText parameter. (Just use
   *     Java.awt Font class and choose one Font or load your own)
   * @param fontSize The Size of the Text (Font) that will be displayed in the middle of the Button.
   * @param fontStyle The Style of the Text (Font) that will be displayed in the middle of the
   *     Button. (PLAIN/BOLD/ITALIC etc.) (Also use Java.awt font class)
   * @param textColor The Color of the Text (Font) that will be displayed in the middle of the
   *     Button. (Just use Java.awt Color class)
   * @param buttonColor The Color of the Button. (Just use Java.awt Color class)
   * @param windowDimension The dimension (HEIGHT and WIDTH) of the Window for calculating the
   *     Center.
   * @param panel The Panel where the Button will be displayed on.
   */
  public ButtonComponent(
      String buttonText,
      int posX,
      int posY,
      int height,
      int width,
      String fontName,
      int fontSize,
      int fontStyle,
      Color textColor,
      Color buttonColor,
      Dimension windowDimension,
      JPanel panel) {
    this.buttonText = buttonText;
    this.x = posX;
    this.y = posY;
    this.height = height;
    this.width = width;
    this.fontName = fontName;
    this.fontSize = fontSize;
    this.fontStyle = fontStyle;
    this.textColor = textColor;
    this.buttonColor = buttonColor;
    this.visible = true;
    this.windowDimension = windowDimension;
    this.panel = panel;
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

  public String getButtonText() {
    return buttonText;
  }

  public void setButtonText(String buttonText) {
    this.buttonText = buttonText;
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

  public Color getButtonColor() {
    return buttonColor;
  }

  public void setButtonColor(Color buttonColor) {
    this.buttonColor = buttonColor;
    this.create(this.isCenterX(), this.isCenterY());
  }

  public void create(boolean centerX, boolean centerY) {
    this.jButton.setVisible(this.visible);
    this.jButton.setBackground(this.buttonColor);
    this.setY(centerY);
    this.setX(centerX);
    this.jButton.setFont(new Font(this.fontName, this.fontStyle, this.fontSize));
    this.jButton.setForeground(this.textColor);
    this.jButton.setText(this.buttonText);
    this.jButton.setBounds(
        centerX ? (this.windowDimension.width / 2) - (this.width / 2) + x : x,
        centerY ? (this.windowDimension.height / 2) + y : y,
        this.width,
        this.height);

    this.panel.add(getComponent());
    this.panel.repaint();
  }

  public void onClick(ButtonClick buttonClick) {
    if (this.jButton.isEnabled()) this.jButton.addActionListener(e -> buttonClick.onClick());
  }

  public JButton getComponent() {
    return this.jButton;
  }
}
