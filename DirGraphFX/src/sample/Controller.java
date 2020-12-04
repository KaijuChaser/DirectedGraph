package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {

   @FXML
   AnchorPane graph;

   @FXML
   private TextField first;

   @FXML
   private TextField second;

   @FXML
   private Label amount = new Label();

   @FXML
   private Label pathNumber = new Label();

   @FXML
   private Label pathLabel = new Label();

   @FXML
   private Label length = new Label();

   Vertex selected = null;

   ArrayList<Path> paths = new ArrayList<>();
   int index = 0;

   DirGraph dg = new DirGraph();

   GrNode center;

   @FXML
   void start() {
      index = 0;
      if (center!=null)center.getVertex().setStyle("-fx-background-color: white;");
      try {
         paths = dg.findPaths(Integer.parseInt(first.getText()),Integer.parseInt(second.getText()));
         Collections.sort(paths);
         center = dg.findCentral();
         setPath(paths.get(index));
         amount.setText("Кол-во путей:"+ paths.size());
      } catch (Exception ignored) {}
   }

   void setPath(Path p) {
      for (GrNode n : dg.getNodes()) {
         n.getVertex().setStyle("-fx-background-color: white;");
      }
      pathNumber.setText("Путь "+ (index + 1)+':');
      pathLabel.setText(p.toString());
      length.setText("Длина:" + p.getDist());

      p.getSrc().getVertex().setStyle("-fx-background-color: lime;");
      for (Link l:p.getLinks()) {
         l.getDest().getVertex().setStyle("-fx-background-color: lime;");
      }
      center.getVertex().setStyle("-fx-background-color: red;");
   }

   @FXML
   void next() {
      index++;
      if (index>paths.size()-1) index -=paths.size();
      setPath(paths.get(index));
   }

   public void onGraphPressed(MouseEvent mouseEvent) {
      graph.getChildren().add(createVertex(mouseEvent));
   }

   private Vertex createVertex(MouseEvent mouseEvent) {
      Vertex vertex = new Vertex(mouseEvent.getX(), mouseEvent.getY());

      vertex.setOnMousePressed(e -> onVertexPressed(e, vertex));
      vertex.setOnMouseDragged(e -> onVertexDragged(e, vertex));
      vertex.setOnDragDetected(e -> onVertexDragDetected(e, vertex));

      dg.add(vertex);
      return vertex;
   }

   private void onVertexPressed(MouseEvent e, Vertex vertex) {
      if (e.isSecondaryButtonDown())
         selectVertex(vertex);
   }

   private void selectVertex(Vertex vertex) {
      if (vertex.isSelected()) {
         selected = null;
         vertex.setStyle("-fx-background-color: white;");
         vertex.setSelected(false);
      } else {
         if (selected != null) {
            createArrow(selected, vertex);
            selected.setStyle("-fx-background-color: white;");
            selected.setSelected(false);
            selected = null;
         } else {
            selected = vertex;
            vertex.setStyle("-fx-background-color: lightgreen;");
            vertex.setSelected(true);
         }
      }
   }

   private void onVertexDragDetected(MouseEvent e, Vertex vertex) {
      if (!e.isPrimaryButtonDown())
         vertex.toFront();
   }

   private void onVertexDragged(MouseEvent e, Button vertex) {
      vertex.setLayoutX(vertex.getLayoutX() + e.getX() + vertex.getTranslateX());
      vertex.setLayoutY(vertex.getLayoutY() + e.getY() + vertex.getTranslateY());
   }

   private Arrow createArrow(Vertex start, Vertex end) {
      Arrow arrow = new Arrow(start.getLayoutX(), start.getLayoutY(), end.getLayoutX(), end.getLayoutY(),   dg.addLink(start.id,end.id,0));
      arrow.x1Property().bind(start.layoutXProperty());
      arrow.y1Property().bind(start.layoutYProperty());
      arrow.x2Property().bind(end.layoutXProperty());
      arrow.y2Property().bind(end.layoutYProperty());

      arrow.toBack();
      graph.getChildren().add(arrow);
      return arrow;
   }


}
