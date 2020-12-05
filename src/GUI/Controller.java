package GUI;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Project;
import model.ProjectManagmentSystem;

public class Controller
{

  @FXML private MenuItem exitMenuItem;

  @FXML private RadioMenuItem ongoingProjects;

  @FXML private ToggleGroup projects;

  @FXML private RadioMenuItem archivedProjects;

  @FXML private MenuItem aboutMenuItem;

  @FXML private Tab projectsTab;

  @FXML private TextArea projectsTextArea;

  @FXML private TextField projectNameTextField;

  @FXML private ComboBox<Project> projectStatusComboBox;

  @FXML private Button addProjectButton;

  @FXML private Button editProjectButton;

  @FXML private ComboBox<?> availableEmployeeComboBox;

  @FXML private Button assignEmployeeButton;

  @FXML private Button removeEmployeeButton;

  @FXML private Button saveProjectButton;

  @FXML private TextArea teamMembersTextArea;

  @FXML private Tab requirementsTab;

  @FXML private TextArea requirementsTextArea;

  @FXML private ComboBox<?> projectSelectedComboBox;

  @FXML private TextField requirementIDTextField;

  @FXML private ComboBox<?> requirementStatusComboBox;

  @FXML private ComboBox<?> requirementTypeComboBox;

  @FXML private TextField requirementDescriptionTextField;

  @FXML private TextField estimateHoursTextField;

  @FXML private TextField priorityNumberTextField;

  @FXML private TextField deadlineTextField;

  @FXML private ComboBox<?> responsibleTeamMemberComboBox;

  @FXML private Button addRequirementButton;

  @FXML private Button editRequirementButton;

  @FXML private Button saveRequirementButton;

  @FXML private Tab tasksTab;

  @FXML private TextArea tasksTextArea;

  @FXML private ComboBox<?> projectSelectedOnTasksComboBox;

  @FXML private ComboBox<?> requirementSelectedComboBox;

  @FXML private TextField taskIDTextField;

  @FXML private ComboBox<?> taskStatusComboBox;

  @FXML private TextField taskDescriptionTextArea;

  @FXML private TextField taskEstimateTextField;

  @FXML private TextField taskTimeUsedTextField;

  @FXML private TextField taskDeadline;

  @FXML private ComboBox<?> taskTeamMembersComboBox;

  @FXML private Button addTaskButton;

  @FXML private Button editTaskButton;

  @FXML private Button saveTaskButton;

  @FXML private Tab employeesTab;

  @FXML private TextArea employeeTextArea;

  @FXML private TextField employeeIDTextField;

  @FXML private TextField employeeFirstName;

  @FXML private TextField employeeLastName;

  @FXML private ComboBox<?> employeeRoleComboBox;

  @FXML private TextArea employeeTaskTextArea;

  @FXML private Button addEmployeeButton;

  @FXML private Button editEmployeeButton;

  @FXML private Button saveEmployeeButton;

  public void initialize()
  {

  }

  public void handleActions(ActionEvent e)
  {
    if (e.getSource() == addProjectButton)
    {
      projectNameTextField.setEditable(true);
      projectStatusComboBox.setDisable(false);
      projectNameTextField.clear();
      projectStatusComboBox.getSelectionModel().select(0);
    }
    else if (e.getSource() == editProjectButton)
    {
      projectNameTextField.setEditable(true);
      projectStatusComboBox.setDisable(false);
    }
    else if (e.getSource() == assignEmployeeButton)
    {
      availableEmployeeComboBox.setDisable(false);
      availableEmployeeComboBox.getSelectionModel().select(0);
    }
    else if (e.getSource() == removeEmployeeButton)
    {
      availableEmployeeComboBox.setDisable(true);
    }
    else if (e.getSource() == saveProjectButton)
    {
      projectNameTextField.setEditable(false);
      projectStatusComboBox.setDisable(true);
      availableEmployeeComboBox.setDisable(true);
    }

    else if (e.getSource() == addRequirementButton
        || e.getSource() == editRequirementButton)
    {
      requirementIDTextField.setEditable(true);
      requirementStatusComboBox.setDisable(false);
      requirementTypeComboBox.setDisable(false);
      requirementDescriptionTextField.setEditable(true);
      estimateHoursTextField.setEditable(true);
      priorityNumberTextField.setEditable(true);
      deadlineTextField.setEditable(true);
      responsibleTeamMemberComboBox.setDisable(false);
      if (e.getSource() == addRequirementButton)
      {
        requirementIDTextField.clear();
        requirementStatusComboBox.getSelectionModel().select(0);
        requirementTypeComboBox.getSelectionModel().select(0);
        requirementDescriptionTextField.clear();
        estimateHoursTextField.clear();
        priorityNumberTextField.clear();
        deadlineTextField.clear();
        responsibleTeamMemberComboBox.getSelectionModel().select(0);
      }
    }
    else if (e.getSource() == saveRequirementButton)
    {
      requirementIDTextField.setEditable(false);
      requirementStatusComboBox.setDisable(true);
      requirementTypeComboBox.setDisable(true);
      requirementDescriptionTextField.setEditable(false);
      estimateHoursTextField.setEditable(false);
      priorityNumberTextField.setEditable(false);
      deadlineTextField.setEditable(false);
      responsibleTeamMemberComboBox.setDisable(true);
    }
    else if (e.getSource() == addTaskButton || e.getSource() == editTaskButton)
    {
      taskIDTextField.setEditable(true);
      taskStatusComboBox.setDisable(false);
      taskDescriptionTextArea.setEditable(true);
      taskEstimateTextField.setEditable(true);
      taskTimeUsedTextField.setEditable(true);
      taskDeadline.setEditable(true);
      taskTeamMembersComboBox.setDisable(false);
      if (e.getSource() == addTaskButton)
      {
        taskIDTextField.clear();
        taskStatusComboBox.getSelectionModel().select(0);
        taskDescriptionTextArea.clear();
        taskEstimateTextField.clear();
        taskTimeUsedTextField.clear();
        taskDeadline.clear();
        taskTeamMembersComboBox.getSelectionModel().select(0);
      }
    }
    else if (e.getSource() == saveTaskButton)
    {
      taskIDTextField.setEditable(!true);
      taskStatusComboBox.setDisable(!false);
      taskDescriptionTextArea.setEditable(!true);
      taskEstimateTextField.setEditable(!true);
      taskTimeUsedTextField.setEditable(!true);
      taskDeadline.setEditable(!true);
      taskTeamMembersComboBox.setDisable(!false);
    }

    else if (e.getSource() == addEmployeeButton
        || e.getSource() == editEmployeeButton)
    {
      employeeIDTextField.setEditable(true);
      employeeFirstName.setEditable(true);
      employeeLastName.setEditable(true);
      employeeRoleComboBox.setDisable(false);
      if (e.getSource() == addEmployeeButton)
      {
        employeeIDTextField.clear();
        employeeFirstName.clear();
        employeeLastName.clear();
        employeeRoleComboBox.getSelectionModel().select(0);
      }
    }
    else if (e.getSource() == saveEmployeeButton)
    {
      employeeIDTextField.setEditable(!true);
      employeeFirstName.setEditable(!true);
      employeeLastName.setEditable(!true);
      employeeRoleComboBox.setDisable(!false);
    }
    else if (e.getSource() == exitMenuItem)
    {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
          "Do you really want to exit the program?", ButtonType.YES,
          ButtonType.NO);
      alert.setTitle("Exit");
      alert.setHeaderText(null);

      alert.showAndWait();

      if (alert.getResult() == ButtonType.YES)
      {
        System.exit(0);
      }
    }
    else if (e.getSource() == ongoingProjects)
    {
      projectNameTextField.setText("current projects");
    }
    else if (e.getSource() == archivedProjects)
    {
      projectNameTextField.setText("archived projects");
    }
    else if (e.getSource() == aboutMenuItem)
    {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText(null);
      alert.setTitle("About");
      alert.setContentText("Choose appropriate text");
      alert.showAndWait();
    }
    else if (e.getSource() == projectsTab)
    {
      ProjectManagmentSystem test =  new ProjectManagmentSystem();
      test.getProjectByName(projectsTextArea.getSelectedText());
    }
    else if (e.getSource() == requirementsTab)
    {

    }
    else if (e.getSource() == tasksTab)
    {

    }
    else if (e.getSource() == employeesTab)
    {

    }
  }


}
