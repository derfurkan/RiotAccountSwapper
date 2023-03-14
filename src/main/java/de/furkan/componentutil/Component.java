package de.furkan.componentutil;

/** Created by Furkan.#4554 Date: 30.07.2021 */
public class Component {

  private boolean centerX, centerY;

  public boolean getCenterY() {
    return centerY;
  }

  public boolean getCenterX() {
    return centerX;
  }

  public void setX(boolean centerX) {
    this.centerX = centerX;
  }

  public void setY(boolean centerY) {
    this.centerY = centerY;
  }

  public void create(boolean centerX, boolean centerY) {
    this.centerX = centerX;
    this.centerY = centerY;
  }
}
