package GUI;

import FileAdapter.SystemAdapter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;

public class Controller
{

  @FXML private MenuItem exitMenuItem;

  @FXML private RadioMenuItem ongoingProjects;

  @FXML private ToggleGroup projects;

  @FXML private RadioMenuItem archivedProjects;

  @FXML private MenuItem aboutMenuItem;

  @FXML private Tab projectsTab;

  @FXML private ListView<String> projectsListView;

  @FXML private TextField projectNameTextField;

  @FXML private ComboBox<String> projectStatusComboBox;

  @FXML private Button addProjectButton;

  @FXML private Button editProjectButton;

  @FXML private ComboBox<String> availableEmployeeComboBox;

  @FXML private Button assignEmployeeButton;

  @FXML private Button removeEmployeeButton;

  @FXML private Button saveProjectButton;

  @FXML private ListView<String> teamMembersListView;

  @FXML private Tab requirementsTab;

  @FXML private ListView<?> requirementsListView;

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

  @FXML private ListView<?> tasksListView;

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

  @FXML private ListView<String> employeesListView;

  @FXML private TextField employeeIDTextField;

  @FXML private TextField employeeFirstName;

  @FXML private TextField employeeLastName;

  @FXML private ComboBox<String> employeeRoleComboBox;

  @FXML private TextArea employeeTaskTextArea;

  @FXML private Button addEmployeeButton;

  @FXML private Button editEmployeeButton;

  @FXML private Button saveEmployeeButton;
  //TODO stuped solution
  private EmployeeList available;
  private Project toEdit;
  private SystemAdapter adapter;
  private ProjectManagmentSystem colourIT;

  public void initialize()
  {
    adapter = new SystemAdapter("colourIT.bin");
    updateProjects();
    updateEmployees();
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
      if (projectsListView.getSelectionModel().getSelectedIndex() < 0)
      {
        alertPopUp("Choose project to edit.");
      }
      else
      {
        int index = projectsListView.getSelectionModel().getSelectedIndex();
        //TODO there are 2 projects referring to the same object. Change it and try if it works.
        Project test = adapter.getSystem().getProjectList().get(index);
        toEdit = adapter.getSystem().getProjectByName(test.getName());
        projectNameTextField.setText(toEdit.getName());

        projectFieldsAreEditable(true);
        teamMembersFieldsAreEditable(true);

        availableEmployeeComboBox.getSelectionModel().select(0);
        employeeRoleComboBox.getSelectionModel().select(0);

        availableEmployeeComboBox.getItems().clear();
        employeeRoleComboBox.getItems().clear();

        available = adapter.getSystem().getEmployees();
        availableEmployeeComboBox.getItems()
            .addAll(available.toString().split("\n"));

        employeeRoleComboBox.getItems().add(Employee.DEVELOPER);
        employeeRoleComboBox.getItems().add(Employee.SCRUM_MASTER);
        employeeRoleComboBox.getItems().add(Employee.PRODUCT_OWNER);
        employeeRoleComboBox.getItems().add(Employee.PROJECT_CREATOR);
      }
      updateTeamMembers();
    }
    //TODO Not sure how to change it. To assign an employee and show it on the team members listview.
    else if (e.getSource() == assignEmployeeButton)
    {
        int employeeIndex = availableEmployeeComboBox.getSelectionModel()
            .getSelectedIndex();
        Employee chosenEmployee = available.get(employeeIndex);

        String role = employeeRoleComboBox.getSelectionModel().getSelectedItem();

        toEdit.addTeamMember(chosenEmployee, role);
        updateTeamMembers();

      /*if (projectsListView.getSelectionModel().getSelectedIndex() == -1)
      {
        alertPopUp("Select project.");
        return;
      }
      int index = projectsListView.getSelectionModel().getSelectedIndex();
      Project toAssignTeamMember = adapter.getSystem().getProjectList()
          .get(index);


      availableEmployeeComboBox.getItems().clear();
      employeeRoleComboBox.getItems().clear();
      EmployeeList available = adapter.getSystem().getEmployees();
      availableEmployeeComboBox.getItems()
          .addAll(available.toString().split("\n"));
      employeeRoleComboBox.getItems().add(Employee.DEVELOPER);
      employeeRoleComboBox.getItems().add(Employee.SCRUM_MASTER);
      employeeRoleComboBox.getItems().add(Employee.PRODUCT_OWNER);
      employeeRoleComboBox.getItems().add(Employee.PROJECT_CREATOR);

      int employeeIndex = availableEmployeeComboBox.getSelectionModel()
          .getSelectedIndex();
      Employee chosenEmployee = available.get(employeeIndex);

      String role = employeeRoleComboBox.getSelectionModel().getSelectedItem();

      toAssignTeamMember.addTeamMember(chosenEmployee, role);*/

    }
    else if (e.getSource() == removeEmployeeButton)
    {
      availableEmployeeComboBox.setDisable(true);
      employeeRoleComboBox.setDisable(true);
    }
    else if (e.getSource() == saveProjectButton)
    {
      if (projectNameTextField.getText().isEmpty())
      {
        alertPopUp("Fill in all the fields.");
        return;
      }
      int index = projectsListView.getSelectionModel().getSelectedIndex();
      String newName = projectNameTextField.getText();
      String status = projectStatusComboBox.getSelectionModel()
          .getSelectedItem();
      if (index < 0)
      {
        adapter.addProject(newName, status);
      }
      else
      {
        try
        {
          String oldName = adapter.getSystem().getProjectList().get(index)
              .getName();
          adapter.editProject(newName, oldName, status);
        }
        catch (IllegalArgumentException er)
        {
          alertPopUp(er.getMessage());
        }
      }
      updateProjects();
      projectNameTextField.setEditable(false);
      projectStatusComboBox.setDisable(true);
      availableEmployeeComboBox.setDisable(true);
      employeeRoleComboBox.setDisable(true);
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
      taskIDTextField.setEditable(false);
      taskStatusComboBox.setDisable(true);
      taskDescriptionTextArea.setEditable(false);
      taskEstimateTextField.setEditable(false);
      taskTimeUsedTextField.setEditable(false);
      taskDeadline.setEditable(false);
      taskTeamMembersComboBox.setDisable(true);
    }

    else if (e.getSource() == addEmployeeButton)
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
    else if (e.getSource() == editEmployeeButton)
    {
      if (employeesListView.getSelectionModel().getSelectedIndex() < 0)
      {
        alertPopUp("Choose employee to edit.");
      }
      else
      {
        int index = employeesListView.getSelectionModel().getSelectedIndex();
        Employee toEdit = adapter.getSystem().getEmployees().get(index);
        //TODO change employee ID from int to string
        employeeIDTextField.setText(String.valueOf(toEdit.getId()));
        employeeFirstName.setText(toEdit.getFirstName());
        employeeLastName.setText(toEdit.getLastName());

        employeeFieldsAreEditable(true);
      }
    }
    else if (e.getSource() == saveEmployeeButton)
    {
      //TODO change so it corresponds to te employee. Now is copied from the Projects
      if (employeeIDTextField.getText().isEmpty() || employeeFirstName.getText()
          .isEmpty() || employeeLastName.getText().isEmpty())
      {
        alertPopUp("Fill in all the fields.");
        return;
      }
      int index = employeesListView.getSelectionModel().getSelectedIndex();
      int employeeID = Integer.parseInt(employeeIDTextField.getText());
      String firstName = employeeFirstName.getText();
      String lastName = employeeLastName.getText();

      if (index < 0)
      {
        Employee toAdd = new Employee(employeeID, firstName, lastName);
        try
        {
          adapter.addEmployee(toAdd);
        }
        catch (IllegalArgumentException empE)
        {
          alertPopUp(empE.getMessage());
        }
      }
      else
      {
        try
        {
          Employee oldEmployee = adapter.getSystem().getEmployees().get(index);
          Employee toEdit = new Employee(employeeID, firstName, lastName);

          adapter.editEmployee(toEdit, oldEmployee);
        }
        catch (IllegalArgumentException error)
        {
          alertPopUp(error.getMessage());
        }
      }
      updateEmployees();
      employeeFieldsAreEditable(false);
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
  }

  public void tabChange(Event event)
  {
    if (projectsTab.isSelected())
    {
      updateProjects();
    }
    else if (requirementsTab.isSelected())
    {
      //TODO
    }
    else if (tasksTab.isSelected())
    {
      //TODO
    }
    else if (employeesTab.isSelected())
    {
      //TODO
      updateEmployees();
    }
  }

  public void updateProjects()
  {
    if (adapter != null)
    {
      projectsListView.getItems().clear();
      projectStatusComboBox.getItems().clear();
      ProjectManagmentSystem temp = adapter.getSystem();
      projectsListView.getItems().addAll(temp.toString().split("\n"));
      projectStatusComboBox.getItems().add(Project.IN_PROCESS);
      projectStatusComboBox.getItems().add(Project.DONE);
      projectStatusComboBox.getItems().add(Project.ARCHIVED);
    }
  }

  public void updateTeamMembers()
  {
    if (projectsListView.getSelectionModel().getSelectedIndex() != -1)
    {
      teamMembersListView.getItems().clear();
      int index = projectsListView.getSelectionModel().getSelectedIndex();
      Project getEmployeesFromProject = adapter.getSystem().getProjectList().get(index);
      EmployeeList teamMembers = adapter.getSystem().getProjectList().getTeamMembers(getEmployeesFromProject);
      if (teamMembers != null)
      {
        teamMembersListView.getItems().addAll(teamMembers.toString().split("\n"));
      }
    }
  }

  public void updateEmployees()
  {
    employeesListView.getItems().clear();
    EmployeeList employees = adapter.getSystem().getEmployees();
    employeesListView.getItems().addAll(employees.toString().split("\n"));
  }

  public void projectFieldsAreEditable(boolean areEditable)
  {
    projectNameTextField.setEditable(areEditable);
    projectStatusComboBox.setDisable(!areEditable);
  }

  public void employeeFieldsAreEditable(boolean areEditable)
  {
    employeeIDTextField.setEditable(areEditable);
    employeeFirstName.setEditable(areEditable);
    employeeLastName.setEditable(areEditable);
    employeeRoleComboBox.setDisable(!areEditable);
  }

  public void teamMembersFieldsAreEditable(boolean areEditable)
  {
    availableEmployeeComboBox.setDisable(!areEditable);
    availableEmployeeComboBox.getSelectionModel().select(0);
    employeeRoleComboBox.setDisable(!areEditable);

  }

  public void alertPopUp(String e)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, e, ButtonType.OK);
    alert.setTitle("Error");
    alert.setHeaderText(null);

    alert.showAndWait();
  }
}
