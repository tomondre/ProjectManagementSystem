package GUI;

import FileAdapter.SystemAdapter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;

import java.util.ArrayList;

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

  @FXML private ListView<String> requirementsListView;

  @FXML private ComboBox<String> projectSelectedComboBox;

  @FXML private TextField requirementIDTextField;

  @FXML private ComboBox<String> requirementStatusComboBox;

  @FXML private ComboBox<String> requirementTypeComboBox;

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
  //TODO stupid solution
  private EmployeeList available;
  private Project toEdit;
  private SystemAdapter adapter;
  private ProjectManagmentSystem colourIT;
  private String Command = "";

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
      Command = "add";
      projectNameTextField.setEditable(true);
      projectStatusComboBox.setDisable(false);
      getProjectFieldsCleared();

    }

    else if (e.getSource() == editProjectButton)
    {
      Command = "edit";
      if (projectsListView.getSelectionModel().getSelectedIndex() < 0)
      {
        alertPopUp("Choose project to edit.");
      }
      else
      {
        projectFieldsAreEditable(true);
      }
    }
    //TODO Not sure how to change it. To assign an employee and show it on the team members listview.
    else if (e.getSource() == assignEmployeeButton)
    {
      Command = "assign";
      teamMembersFieldsAreEditable(true);

      getTeamMembersFieldsCleared();

      available = adapter.getSystem().getEmployees();
      availableEmployeeComboBox.getItems()
          .addAll(available.toString().split("\n"));

    }
    else if (e.getSource() == removeEmployeeButton)
    {
      Command = "remove";
      availableEmployeeComboBox.setDisable(true);
      employeeRoleComboBox.setDisable(true);
    }
    else if (e.getSource() == saveProjectButton)
    {
      //TODO Command needs all the ifs to be done with else if.
      if (projectNameTextField.getText().isEmpty())
      {
        alertPopUp("Fill in all the fields.");
        return;
      }
      int index = projectsListView.getSelectionModel().getSelectedIndex();
      String newName = projectNameTextField.getText();
      String status = projectStatusComboBox.getSelectionModel()
          .getSelectedItem();
      if (Command.equals("add"))
      {
        adapter.addProject(newName, status);
      }
      //TODO not sure. As probably have to change the whole save button for the projects
      else if (Command.equals("assign"))
      {
        int employeeIndex = availableEmployeeComboBox.getSelectionModel()
            .getSelectedIndex();
        Employee chosenEmployee = available.get(employeeIndex);

        String role = employeeRoleComboBox.getSelectionModel()
            .getSelectedItem();


       Project assignEmployee = adapter.getSystem().getProjectList().get(index);
       // assignEmployee.addTeamMember(chosenEmployee, role);
        ProjectManagmentSystem system = adapter.getSystem();
        system.addEmployeeToAProject(assignEmployee.getName(), chosenEmployee.getId(), role);
        adapter.save(system);
        Command = "";
      }
      else if (Command.equals("remove"))
      {
        //TODO if we have time we can look at this as well.
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
      updateTeamMembers();

      projectFieldsAreEditable(false);
      teamMembersFieldsAreEditable(false);
      getProjectFieldsCleared();
      getTeamMembersFieldsCleared();
    }

    else if (e.getSource() == addRequirementButton)
    {
      requirementsFieldsAreEditable(true);
      getRequirementFieldsCleared();
    }

    else if (e.getSource() == editRequirementButton)
    {
      requirementsFieldsAreEditable(true);

      int index = requirementsListView.getSelectionModel().getSelectedIndex();
      if (index == -1)
      {
        alertPopUp("Choose requirement to edit.");
        return;
      }
      int projectIndex = projectSelectedComboBox.getSelectionModel()
          .getSelectedIndex();
      Project project = adapter.getSystem().getProjectList().get(projectIndex);
      Requirement requirementToEdit = adapter.getSystem()
          .getRequirementList(project.getName()).get(index);

      requirementIDTextField.setText(requirementToEdit.getID());
      requirementDescriptionTextField
          .setText(requirementToEdit.getDescription());
      estimateHoursTextField
          .setText(String.valueOf(requirementToEdit.getEstimateTime()));
      priorityNumberTextField
          .setText(String.valueOf(requirementToEdit.getPriority()));
      deadlineTextField.setText(requirementToEdit.getDeadline().toString());

      //TODO responsible team member.
      getRequirementFieldsCleared();
    }
    else if (e.getSource() == saveRequirementButton)
    {
      //TODO proper validator for text
      if (requirementIDTextField.getText().isEmpty())
      {
        alertPopUp("Fill in all the fields.");
        return;
      }
      int index = requirementsListView.getSelectionModel().getSelectedIndex();
      int projectIndex = projectSelectedComboBox.getSelectionModel()
          .getSelectedIndex();
      Project selectedProject = adapter.getSystem().getProjectList()
          .get(projectIndex);
      String requirementID = requirementIDTextField.getText();
      String status = requirementStatusComboBox.getSelectionModel()
          .getSelectedItem();
      String requirementType = requirementTypeComboBox.getSelectionModel()
          .getSelectedItem();
      String requirementDescription = requirementDescriptionTextField.getText();
      double timeEstimate = Double
          .parseDouble(estimateHoursTextField.getText());
      int priorityNumber = Integer.parseInt(priorityNumberTextField.getText());
      String[] date = deadlineTextField.getText().split("[0-9]+");
      MyDate deadline = new MyDate(Integer.parseInt(date[0]),
          Integer.parseInt(date[1]), Integer.parseInt(date[2]));
      int responsibleEmployeeIndex = responsibleTeamMemberComboBox
          .getSelectionModel().getSelectedIndex();
      //TODO to be changed to take from the employees assigned to the project
      Employee responsibleEmployee = adapter.getSystem().getEmployees()
          .get(responsibleEmployeeIndex);
      if (index < 0)
      {
        adapter.addRequirement(selectedProject.getName(), requirementID,
            priorityNumber, requirementDescription, timeEstimate, status,
            requirementType, deadline, responsibleEmployee);
      }
      else
      {
        try
        {
          //TODO finish the edit requirement.
          //adapter
        }
        catch (IllegalArgumentException er)
        {
          alertPopUp(er.getMessage());
        }
      }
      requirementsFieldsAreEditable(false);
      getRequirementFieldsCleared();
    }
    else if (e.getSource() == addTaskButton)
    {
      taskFieldsAreEditable(true);
      getTaskFieldsCleared();
    }
    else if (e.getSource() == editTaskButton)
    {
      taskFieldsAreEditable(true);
    }
    else if (e.getSource() == saveTaskButton)
    {
      taskFieldsAreEditable(false);
      getTaskFieldsCleared();
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
      alertPopUp("Choose appropriate text");
    }
    else if (e.getSource() == projectSelectedComboBox)
    {
      int index = projectSelectedComboBox.getSelectionModel()
          .getSelectedIndex();
      Project project = adapter.getSystem().getProjectList().get(index);
      RequirementList requirements = adapter.getSystem()
          .getRequirementList(project.getName());

      requirementsListView.getItems()
          .addAll(requirements.toString().split("\n"));
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
      ProjectList projectList = adapter.getSystem().getProjectList();
      projectSelectedComboBox.getItems()
          .addAll(projectList.toString().split("\n"));
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
      ProjectList temp = adapter.getSystem().getProjectList();
      projectsListView.getItems().addAll(temp.toString().split("\n"));
      projectStatusComboBox.getItems()
          .addAll(Project.IN_PROCESS, Project.DONE, Project.ARCHIVED);
    }
  }

  public void updateTeamMembers()
  {
    if (projectsListView.getSelectionModel().getSelectedIndex() != -1)
    {
      teamMembersListView.getItems().clear();
      int index = projectsListView.getSelectionModel().getSelectedIndex();
      Project getEmployeesFromProject = adapter.getSystem().getProjectList()
          .get(index);
      EmployeeList teamMembers = adapter.getSystem().getProjectList().getProjectByName(getEmployeesFromProject.getName()).getAllTeamMembers();
      if (teamMembers != null)
      {
        teamMembersListView.getItems()
            .addAll(teamMembers.toStringProjectTeam().split("\n"));
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

  public void requirementsFieldsAreEditable(boolean areEditable)
  {
    requirementIDTextField.setEditable(areEditable);
    requirementStatusComboBox.setDisable(!areEditable);
    requirementTypeComboBox.setDisable(!areEditable);
    requirementDescriptionTextField.setEditable(areEditable);
    estimateHoursTextField.setEditable(areEditable);
    priorityNumberTextField.setEditable(areEditable);
    deadlineTextField.setEditable(areEditable);
    responsibleTeamMemberComboBox.setDisable(!areEditable);
  }

  public void employeeFieldsAreEditable(boolean areEditable)
  {
    employeeIDTextField.setEditable(areEditable);
    employeeFirstName.setEditable(areEditable);
    employeeLastName.setEditable(areEditable);
    employeeRoleComboBox.setDisable(!areEditable);
  }

  public void taskFieldsAreEditable(boolean areEditable)
  {
    taskIDTextField.setEditable(areEditable);
    taskStatusComboBox.setDisable(!areEditable);
    taskDescriptionTextArea.setEditable(areEditable);
    taskEstimateTextField.setEditable(areEditable);
    taskTimeUsedTextField.setEditable(areEditable);
    taskDeadline.setEditable(areEditable);
    taskTeamMembersComboBox.setDisable(!areEditable);
  }

  public void teamMembersFieldsAreEditable(boolean areEditable)
  {
    availableEmployeeComboBox.setDisable(!areEditable);
    employeeRoleComboBox.setDisable(!areEditable);

  }

  public void getProjectFieldsCleared()
  {
    projectNameTextField.clear();
    projectStatusComboBox.getSelectionModel().select(0);
  }

  public void getTeamMembersFieldsCleared()
  {
    availableEmployeeComboBox.getSelectionModel().select(0);
    employeeRoleComboBox.getSelectionModel().select(0);

    availableEmployeeComboBox.getItems().clear();
    employeeRoleComboBox.getItems().clear();

    employeeRoleComboBox.getItems()
        .addAll(Employee.DEVELOPER, Employee.SCRUM_MASTER,
            Employee.PRODUCT_OWNER, Employee.PROJECT_CREATOR);

  }

  public void getRequirementFieldsCleared()
  {
    requirementIDTextField.clear();
    requirementStatusComboBox.getItems().clear();
    requirementStatusComboBox.getItems()
        .addAll(Requirement.NOT_STARTED, Requirement.STARTED, Requirement.ENDED,
            Requirement.APPROVED, Requirement.REJECTED);
    requirementStatusComboBox.getSelectionModel().select(0);
    requirementTypeComboBox.getItems().clear();
    requirementTypeComboBox.getItems()
        .addAll(Requirement.FUNCTIONAL, Requirement.NON_FUNCTIONAL);
    requirementTypeComboBox.getSelectionModel().select(0);
    requirementDescriptionTextField.clear();
    estimateHoursTextField.clear();
    priorityNumberTextField.clear();
    deadlineTextField.clear();
    //TODO responsible team member. First have to figure out how to assign the team members to the projects.
    responsibleTeamMemberComboBox.getItems().clear();
    responsibleTeamMemberComboBox.getSelectionModel().select(0);
  }

  public void getTaskFieldsCleared()
  {
    taskIDTextField.clear();
    taskStatusComboBox.getSelectionModel().select(0);
    taskDescriptionTextArea.clear();
    taskEstimateTextField.clear();
    taskTimeUsedTextField.clear();
    taskDeadline.clear();
    taskTeamMembersComboBox.getSelectionModel().select(0);
  }


  public void fillFieldsInProjectTab()
  {
    int index = projectsListView.getSelectionModel().getSelectedIndex();
    toEdit = adapter.getSystem().getProjectList().get(index);
    String status = toEdit.getStatus();
    projectNameTextField.setText(toEdit.getName());
    projectStatusComboBox.getSelectionModel().select(status);
    updateTeamMembers();
  }

  public void alertPopUp(String e)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, e, ButtonType.OK);
    alert.setTitle("Error");
    alert.setHeaderText(null);

    alert.showAndWait();
  }
}
