package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polyline;

public class Arrow extends Group {

   private TextField length = new TextField("null");

   private Link link;

   private Polyline head  = new Polyline();
   private Polyline mainLine = new Polyline();
   private SimpleDoubleProperty x1 = new SimpleDoubleProperty();
   private SimpleDoubleProperty y1 = new SimpleDoubleProperty();
   private SimpleDoubleProperty x2 = new SimpleDoubleProperty();
   private SimpleDoubleProperty y2 = new SimpleDoubleProperty();

   public Arrow (double x1, double y1, double x2, double y2, Link link) {
      this.link = link;
      this.x1.set(x1);
      this.y1.set(y1);
      this.x2.set(x2);
      this.y2.set(y2);

      length.setBorder(null);
      length.setBackground(null);
      length.setPrefWidth(50);
      length.layoutXProperty().bind(this.x1.add(this.x2).divide(2));
      length.layoutYProperty().bind(this.y1.add(this.y2).divide(2));
      length.setOnKeyTyped(e -> onFieldUpdated());

      getChildren().addAll(mainLine, length,head);

      for (SimpleDoubleProperty s : new SimpleDoubleProperty[]{this.x1, this.y1, this.x2, this.y2}) {
         s.addListener( (l,o,n) -> update());
      }

      update();
   }

   private void onFieldUpdated() {
      try {
         link.setDist(Integer.parseInt(length.getText()));
      } catch (Exception ignored) {}
   }

   private double[] scale (double x1, double y1, double x2, double y2) {
      double ang = Math.atan2(y2-y1, x2-x1);
      return new double[]{
              x1+ Math.cos(ang)*20,
              y1+ Math.sin(ang)*20
      };
   }

   private void update() {
      double[] start = scale(x1.get(),y1.get(),x2.get(),y2.get());
      double[] end = scale(x2.get(),y2.get(),x1.get(),y1.get());
      mainLine.getPoints().setAll(start[0],start[1],end[0],end[1]);

      double ang = Math.atan2(end[1]-start[1],end[0]-start[0]);

      double x = end[0] - Math.cos(ang+Math.toRadians(20))*10;
      double y = end[1] - Math.sin(ang+Math.toRadians(20))*10;
      head.getPoints().setAll(x,y,end[0],end[1]);
      x = end[0] - Math.cos(ang-Math.toRadians(20))*10;
      y = end[1] - Math.sin(ang-Math.toRadians(20))*10;
      head.getPoints().addAll(x,y);
   }

   public double getX1() {
      return x1.get();
   }
   public SimpleDoubleProperty x1Property() {
      return x1;
   }
   public void setX1(double x1) {
      this.x1.set(x1);
   }
   public double getY1() {
      return y1.get();
   }
   public SimpleDoubleProperty y1Property() {
      return y1;
   }
   public void setY1(double y1) {
      this.y1.set(y1);
   }
   public double getX2() {
      return x2.get();
   }
   public SimpleDoubleProperty x2Property() {
      return x2;
   }
   public void setX2(double x2) {
      this.x2.set(x2);
   }
   public double getY2() {
      return y2.get();
   }
   public SimpleDoubleProperty y2Property() {
      return y2;
   }
   public void setY2(double y2) {
      this.y2.set(y2);
   }

   public int getLength() {
      return Integer.parseInt(length.getText());
   }
}
