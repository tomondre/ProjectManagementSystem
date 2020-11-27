//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class SystemAdapterGUI extends Application
//{
//  private SystemAdapter adapter;
//  private VBox mainPane;
//  private HBox leftPane;
//  private HBox rightPane;
//
//  private TextArea allProjects;
//  private TextField addName;
//  private Button save;
//  private MyActionListener listener;
//
//  private Button addRequirement;
//  private TextField id;
//  private TextField description;
//
//  public void start(Stage window)
//  {
//    adapter = new SystemAdapter("v1.bin");
//    listener = new MyActionListener();
//    mainPane = new VBox();
//    allProjects = new TextArea();
//    addName = new TextField();
//    save = new Button("Save");
//    save.setOnAction(listener);
// leftPane = new HBox();
//    leftPane.getChildren().addAll(addName, save, allProjects);
//rightPane = new HBox();
//addRequirement = new Button("id");
//mainPane = new VBox();
//    mainPane.getChildren().addAll(leftPane, rightPane);
//
//    Scene scene = new Scene(mainPane);
//    window.setScene(scene);
//    window.show();
//  }
//
//  private class MyActionListener implements EventHandler<ActionEvent>
//  {
//    public void handle(ActionEvent e)
//    {
//      if (e.getSource() == save)
//      {
//        String temp = addName.getText();
//        adapter.addProject(temp);
//        ProjectList n = adapter.getAllProjects();
//        allProjects.setText(n.toString());
//      }
//    }
//  }
//}
