//import javafx.application.Application;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.TabPane.TabClosingPolicy;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class SystemAdapter_V2 extends Application
//{
//  private SystemAdapter adapter;
//
//  private VBox mainPane;
//
//  private TabPane tabPane;
//  private Tab allStudentsTab;
//  private Tab changeCountryTab;
//
//  private TextArea allStudentsArea;
//  private ScrollPane allStudentsScrollPane;
//
//  private Button getButton;
//  private Button updateButton;
//
//  private VBox allStudentsPane;
//
//  private VBox changeCountryPane;
//  private HBox changeCountryTopPane;
//  private FlowPane comboPane;
//
//  private Label firstNameLabel;
//  private Label lastNameLabel;
//  private Label countryLabel;
//
//  private GridPane changeCountryInputPane;
//
//  private ComboBox<Project> studentBox;
//  private TextField firstNameField;
//  private TextField lastNameField;
//  private TextField countryField;
//
//  private FlowPane imagePane;
//  private Image logo;
//  private ImageView logoView;
//
//  private MenuBar menuBar;
//
//  private Menu fileMenu;
//  private Menu editMenu;
//  private Menu aboutMenu;
//
//  private MenuItem exitMenuItem;
//  private MenuItem aboutMenuItem;
//
//  private CheckMenuItem editAreaMenuItem;
//  private CheckMenuItem editFieldsMenuItem;
//
//  private MyActionListener listener;
//  private MyTabListener tabListener;
//
//  public void start(Stage window)
//  {
//    window.setTitle("Student File Adapter GUI");
//
//    adapter = new SystemAdapter("v1.bin");
//
//    listener = new MyActionListener();
//    tabListener = new MyTabListener();
//
//    tabPane = new TabPane();
//    tabPane.getSelectionModel().selectedItemProperty().addListener(tabListener);
//
//    allStudentsTab = new Tab("All Students");
//    changeCountryTab = new Tab("Change Country");
//
//    allStudentsArea = new TextArea();
//    allStudentsArea.setPrefRowCount(16);
//    allStudentsArea.setPrefColumnCount(50);
//    allStudentsArea.setEditable(false);
//
//    allStudentsScrollPane = new ScrollPane(allStudentsArea);
//    allStudentsScrollPane.setFitToWidth(true);
//
//    getButton = new Button("Get Students");
//    getButton.setOnAction(listener);
//    updateButton = new Button("Update");
//    updateButton.setOnAction(listener);
//
//    allStudentsPane = new VBox(10);
//    allStudentsPane.setAlignment(Pos.CENTER);
//    allStudentsPane.getChildren().add(allStudentsScrollPane);
//    allStudentsPane.getChildren().add(getButton);
//
//    changeCountryPane = new VBox(20);
//    changeCountryPane.setPadding(new Insets(10));
//
//    changeCountryTopPane = new HBox(20);
//
//    studentBox = new ComboBox<Project>();
//    studentBox.setOnAction(listener);
//
//    comboPane = new FlowPane();
//    comboPane.setAlignment(Pos.BASELINE_RIGHT);
//    comboPane.setPrefWidth(200);
//    comboPane.getChildren().add(studentBox);
//
//    firstNameLabel = new Label("id:");
//    lastNameLabel = new Label("description:");
//    countryLabel = new Label("estimate time:");
//
//    firstNameField = new TextField();
//    lastNameField = new TextField();
//
//    countryField = new TextField();
//
//    changeCountryInputPane = new GridPane();
//    changeCountryInputPane.setHgap(5);
//    changeCountryInputPane.setVgap(5);
//    changeCountryInputPane.addRow(0, firstNameLabel, firstNameField);
//    changeCountryInputPane.addRow(1, lastNameLabel, lastNameField);
//    changeCountryInputPane.addRow(2, countryLabel, countryField);
//
//    changeCountryTopPane.getChildren().add(changeCountryInputPane);
//    changeCountryTopPane.getChildren().add(comboPane);
//
//    logo = new Image("file:img/vialogoah.gif");
//    logoView = new ImageView(logo);
//    imagePane = new FlowPane();
//    imagePane.setPrefHeight(200);
//    imagePane.setAlignment(Pos.BOTTOM_CENTER);
//    imagePane.getChildren().add(logoView);
//
//    changeCountryPane.getChildren().add(changeCountryTopPane);
//    changeCountryPane.getChildren().add(updateButton);
//    changeCountryPane.getChildren().add(imagePane);
//
//    allStudentsTab.setContent(allStudentsPane);
//    changeCountryTab.setContent(changeCountryPane);
//
//    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
//    tabPane.getTabs().add(allStudentsTab);
//    tabPane.getTabs().add(changeCountryTab);
//
//    exitMenuItem = new MenuItem("Exit");
//    exitMenuItem.setOnAction(listener);
//
//    aboutMenuItem = new MenuItem("About");
//    aboutMenuItem.setOnAction(listener);
//
//    editAreaMenuItem = new CheckMenuItem("Edit student area");
//    editAreaMenuItem.setOnAction(listener);
//
//    editFieldsMenuItem = new CheckMenuItem("Edit name fields");
//    editFieldsMenuItem.setOnAction(listener);
//
//    fileMenu = new Menu("File");
//    editMenu = new Menu("Edit");
//    aboutMenu = new Menu("About");
//
//    fileMenu.getItems().add(exitMenuItem);
//
//    editMenu.getItems().add(editAreaMenuItem);
//    editMenu.getItems().add(editFieldsMenuItem);
//
//    aboutMenu.getItems().add(aboutMenuItem);
//
//    menuBar = new MenuBar();
//
//    menuBar.getMenus().add(fileMenu);
//    menuBar.getMenus().add(editMenu);
//    menuBar.getMenus().add(aboutMenu);
//
//    mainPane = new VBox();
//    mainPane.getChildren().add(menuBar);
//    mainPane.getChildren().add(tabPane);
//
//    Scene scene = new Scene(mainPane, 520, 390);
//
//    window.setScene(scene);
//    window.setResizable(false);
//    window.show();
//  }
//
//  private void updateStudentBox()
//  {
//    int currentIndex = studentBox.getSelectionModel().getSelectedIndex();
//
//    studentBox.getItems().clear();
//
//    ProjectList students = adapter.getAllProjects();
//    for (int i = 0; i < students.size(); i++)
//    {
//      studentBox.getItems().add(students.get(i));
//    }
//
//    if (currentIndex == -1 && studentBox.getItems().size() > 0)
//    {
//      studentBox.getSelectionModel().select(0);
//    }
//    else
//    {
//      studentBox.getSelectionModel().select(currentIndex);
//    }
//  }
//
//  private void updateStudentArea()
//  {
//    ProjectList students = adapter.getAllProjects();
//    allStudentsArea.setText(students.toString());
//  }
//
//  private class MyActionListener implements EventHandler<ActionEvent>
//  {
//
//    public void handle(ActionEvent e)
//    {
//      Project temp = null;
//      if (e.getSource() == getButton)
//      {
//        updateStudentArea();
//      }
//      else if (e.getSource() == updateButton)
//      {
//        String firstName = firstNameField.getText();
//        String lastName = lastNameField.getText();
//        String country = countryField.getText();
//
//        adapter.addRequirement(temp,
//            new Requirement(Integer.parseInt(firstName), lastName,
//                Double.parseDouble(country)));
//        updateStudentBox();
//        countryField.setText("");
//      }
//      else if (e.getSource() == studentBox)
//      {
//        temp = studentBox.getSelectionModel().getSelectedItem();
//
//      }
//
//      else if (e.getSource() == exitMenuItem)
//      {
//        Alert alert = new Alert(AlertType.CONFIRMATION,
//            "Do you really want to exit the program?", ButtonType.YES,
//            ButtonType.NO);
//        alert.setTitle("Exit");
//        alert.setHeaderText(null);
//
//        alert.showAndWait();
//
//        if (alert.getResult() == ButtonType.YES)
//        {
//          System.exit(0);
//        }
//      }
//
//      else if (e.getSource() == editAreaMenuItem)
//      {
//        if (editAreaMenuItem.isSelected())
//        {
//          allStudentsArea.setEditable(true);
//        }
//        else
//        {
//          allStudentsArea.setEditable(false);
//        }
//      }
//
//      else if (e.getSource() == editFieldsMenuItem)
//      {
//        if (editFieldsMenuItem.isSelected())
//        {
//          firstNameField.setEditable(true);
//          lastNameField.setEditable(true);
//        }
//        else
//        {
//          firstNameField.setEditable(false);
//          lastNameField.setEditable(false);
//        }
//      }
//
//      else if (e.getSource() == aboutMenuItem)
//      {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setHeaderText(null);
//        alert.setTitle("About");
//        alert.setContentText(
//            "This is just a little program that demonstrates some of the GUI features in Java");
//        alert.showAndWait();
//      }
//    }
//  }
//
//  private class MyTabListener implements ChangeListener<Tab>
//  {
//    public void changed(ObservableValue<? extends Tab> tab, Tab oldTab,
//        Tab newTab)
//    {
//      if (newTab == allStudentsTab)
//      {
//        updateStudentArea();
//      }
//      else if (newTab == changeCountryTab)
//      {
//        updateStudentBox();
//      }
//    }
//  }
//}
