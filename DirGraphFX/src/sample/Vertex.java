package sample;

import javafx.scene.control.Button;

public class Vertex extends Button {

   private static int count = 0;
   public int id;
   private boolean isSelected;

   public Vertex(double x, double y) {
      setLayoutX(x);
      setLayoutY(y);

      translateXProperty().bind(widthProperty().divide(-2));
      translateYProperty().bind(heightProperty().divide(-2));

      id = count++;
      setText(id+"");
      getStyleClass().add("visNode");
   }

   public boolean isSelected() {
      return isSelected;
   }

   public void setSelected(boolean selected) {
      isSelected = selected;
   }

}
