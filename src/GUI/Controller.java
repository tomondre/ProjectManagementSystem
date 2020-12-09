package GUI;

import FileAdapter.SystemAdapter;
import javafx.collections.ObservableList;
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

  @FXML private ListView<Project> projectsListView;

  @FXML private TextField projectNameTextField;

  @FXML private ComboBox<String> projectStatusComboBox;

  @FXML private Button addProjectButton;

  @FXML private Button editProjectButton;

  @FXML private ComboBox<Employee> availableEmployeeComboBox;

  @FXML private Button assignEmployeeButton;

  @FXML private Button removeEmployeeButton;

  @FXML private Button saveProjectButton;

  @FXML private ListView<Employee> teamMembersListView;

  @FXML private Tab requirementsTab;

  @FXML private ListView<Requirement> requirementsListView;

  @FXML private ComboBox<Project> projectSelectedComboBox;

  @FXML private TextField requirementIDTextField;

  @FXML private ComboBox<String> requirementStatusComboBox;

  @FXML private ComboBox<String> requirementTypeComboBox;

  @FXML private TextField requirementDescriptionTextField;

  @FXML private TextField estimateHoursTextField;

  @FXML private TextField priorityNumberTextField;

  @FXML private TextField deadlineTextField;

  @FXML private ComboBox<Employee> responsibleTeamMemberComboBox;

  @FXML private Button addRequirementButton;

  @FXML private Button editRequirementButton;

  @FXML private Button saveRequirementButton;

  @FXML private Tab tasksTab;

  @FXML private ListView<Task> tasksListView;

  @FXML private ComboBox<Project> projectSelectedOnTasksComboBox;

  @FXML private ComboBox<Requirement> requirementSelectedComboBox;

  @FXML private TextField taskIDTextField;

  @FXML private ComboBox<String> taskStatusComboBox;

  @FXML private TextField taskDescriptionTextArea;

  @FXML private TextField taskEstimateTextField;

  @FXML private TextField taskTimeUsedTextField;

  @FXML private TextField taskDeadline;

  @FXML private ComboBox<Employee> taskResponsibleEmployeeComboBox;

  @FXML private ListView<Employee> taskTeamMembersListView;

  @FXML private Button addTaskButton;

  @FXML private Button editTaskButton;

  @FXML private Button saveTaskButton;

  @FXML private Tab employeesTab;

  @FXML private ListView<Employee> employeesListView;

  @FXML private TextField employeeIDTextField;

  @FXML private TextField employeeFirstName;

  @FXML private TextField employeeLastName;

  @FXML private ComboBox<String> employeeRoleComboBox;

  @FXML private TextArea employeeTaskTextArea;

  @FXML private Button addEmployeeButton;

  @FXML private Button editEmployeeButton;

  @FXML private Button saveEmployeeButton;
  private SystemAdapter adapter;
  private String Command = "";

  public void initialize()
  {
    adapter = new SystemAdapter("colourIT.bin");
    taskTeamMembersListView.getSelectionModel()
        .setSelectionMode(SelectionMode.MULTIPLE);
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

    else if (e.getSource() == assignEmployeeButton)
    {
      Command = "assign";
      teamMembersFieldsAreEditable(true);

      getTeamMembersFieldsCleared();

      EmployeeList availableEmployees = adapter.getSystem()
          .getNotAssignedEmployees();
      for (int i = 0; i < availableEmployees.size(); i++)
      {
        availableEmployeeComboBox.getItems().addAll(availableEmployees.get(i));
      }
    }
    //TODO think if we really need to remove employee from a project. If we have time. Khaled
    else if (e.getSource() == removeEmployeeButton)
    {
      Command = "remove";
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
      switch (Command)
      {
        case "add":
          try
          {
            adapter.addProject(newName, status);
          }
          catch (IllegalArgumentException event)
          {
            alertPopUp(event.getMessage());
          }
          break;
        case "assign":
          Employee chosenEmployee = availableEmployeeComboBox
              .getSelectionModel().getSelectedItem();

          String role = employeeRoleComboBox.getSelectionModel()
              .getSelectedItem();

          Project assignEmployee = projectsListView.getSelectionModel()
              .getSelectedItem();
          //TODO make a method in the systemAdapter for assigning employee to a project
          ProjectManagementSystem system = adapter.getSystem();
          system.addEmployeeToAProject(assignEmployee.getName(),
              chosenEmployee.getId(), role);
          adapter.save(system);
          break;
        case "remove":
          //TODO if we have time we can look at this as well.
          break;
        default:
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
          break;
      }
      updateProjects();
      updateTeamMembers();

      Command = "";

      projectFieldsAreEditable(false);
      teamMembersFieldsAreEditable(false);
      getProjectFieldsCleared();
      getTeamMembersFieldsCleared();
    }
    else if (e.getSource() == addRequirementButton)
    {
      if (projectSelectedComboBox.getSelectionModel().getSelectedIndex() == -1)
      {
        alertPopUp("Select project.");
      }
      else
      {
        requirementsFieldsAreEditable(true);
        getRequirementFieldsCleared();

        String projectName = projectSelectedComboBox.getSelectionModel()
            .getSelectedItem().getName();

        EmployeeList teamMembers = adapter.getSystem()
            .getAllEmployeesAssignedToProject(projectName);

        for (int i = 0; i < teamMembers.size(); i++)
        {
          responsibleTeamMemberComboBox.getItems().add(teamMembers.get(i));
        }

      }
    }
    else if (e.getSource() == editRequirementButton)
    {
      int index = requirementsListView.getSelectionModel().getSelectedIndex();
      if (index == -1)
      {
        alertPopUp("Choose requirement to edit.");
      }
      else
      {
        requirementsFieldsAreEditable(true);
        requirementIDTextField.setEditable(false);
        fillRequirementsFields();
      }
    }
    else if (e.getSource() == saveRequirementButton)
    {
      if (requirementsFieldsValidator())
      {
        alertPopUp("Fill in all the fields.");
        return;
      }

      Project selectedProject = projectSelectedComboBox.getSelectionModel()
          .getSelectedItem();

      String requirementID = requirementIDTextField.getText();
      String status = requirementStatusComboBox.getSelectionModel()
          .getSelectedItem();
      String requirementType = requirementTypeComboBox.getSelectionModel()
          .getSelectedItem();
      String requirementDescription = requirementDescriptionTextField.getText();
      double timeEstimate = Double
          .parseDouble(estimateHoursTextField.getText());
      int priorityNumber = Integer.parseInt(priorityNumberTextField.getText());
      String[] date = deadlineTextField.getText().split("[./\\-]");
      MyDate deadline = new MyDate(Integer.parseInt(date[0]),
          Integer.parseInt(date[1]), Integer.parseInt(date[2]));

      Employee responsibleEmployee = responsibleTeamMemberComboBox
          .getSelectionModel().getSelectedItem();

      if (requirementsListView.getSelectionModel().getSelectedIndex() < 0)
      {
        adapter.addRequirement(selectedProject.getName(), requirementID,
            priorityNumber, requirementDescription, timeEstimate, status,
            requirementType, deadline, responsibleEmployee);
      }
      else
      {
        Requirement toEdit = requirementsListView.getSelectionModel()
            .getSelectedItem();
        adapter.setRequirement(selectedProject.getName(), requirementID,
            priorityNumber, requirementDescription, timeEstimate, status,
            requirementType, deadline, responsibleEmployee);

      }
      requirementsFieldsAreEditable(false);
      getRequirementFieldsCleared();
      updateRequirements();
    }
    else if (e.getSource() == addTaskButton)
    {
      if (projectSelectedOnTasksComboBox.getSelectionModel().getSelectedIndex()
          == -1
          || requirementSelectedComboBox.getSelectionModel().getSelectedIndex()
          == -1)
      {
        alertPopUp("Select project and requirement.");
      }
      else
      {
        getTaskFieldsCleared();
        taskFieldsAreEditable(true);
        //TODO refactor a bit
        EmployeeList employeeList = projectSelectedOnTasksComboBox
            .getSelectionModel().getSelectedItem().getAllTeamMembers();
        for (int i = 0; i < employeeList.size(); i++)
        {
          taskResponsibleEmployeeComboBox.getItems().add(employeeList.get(i));
          teamMembersListView.getItems().add(employeeList.get(i));
        }
      }
    }
    else if (e.getSource() == editTaskButton)
    {
      if (tasksListView.getSelectionModel().getSelectedIndex() == -1)
      {
        alertPopUp("Select task to edit.");
      }
      else
      {
        taskFieldsAreEditable(true);
        taskIDTextField.setEditable(false);
      }
    }
    else if (e.getSource() == saveTaskButton)
    {
      if (taskFieldsValidation())
      {
        alertPopUp("Fill in all the fields.");
      }

      String projectSelectedName = projectSelectedOnTasksComboBox
          .getSelectionModel().getSelectedItem().getName();
      String selectedRequirementName = requirementSelectedComboBox
          .getSelectionModel().getSelectedItem().getID();

      int taskID = Integer.parseInt(taskIDTextField.getText());
      //TODO check if correct status is parsed.
      boolean status =
          taskStatusComboBox.getSelectionModel().getSelectedIndex() != 0;
      String description = taskDescriptionTextArea.getText();
      double timeEstimate = Double.parseDouble(taskEstimateTextField.getText());
      double timeUsed = Double.parseDouble(taskTimeUsedTextField.getText());
      String[] time = taskDeadline.getText().split("[./\\-]");
      MyDate deadline = new MyDate(Integer.parseInt(time[0]),
          Integer.parseInt(time[1]), Integer.parseInt(time[2]));
      Employee responsibleEmployee = taskResponsibleEmployeeComboBox
          .getSelectionModel().getSelectedItem();
      //TODO Check the cast
      ObservableList<Employee> employeesChosen = taskTeamMembersListView
          .getSelectionModel().getSelectedItems();
      EmployeeList employeesToAssign = new EmployeeList();
      for (Employee empl : employeesChosen)
      {
        employeesToAssign.addEmployee(empl);
      }
      if (tasksListView.getSelectionModel().getSelectedIndex() < 0)
      {
        adapter.addTask(projectSelectedName, selectedRequirementName, taskID,
            description, status, timeUsed, timeEstimate, deadline,
            employeesToAssign, responsibleEmployee);
      }
      else
      {
        int oldTaskID = tasksListView.getSelectionModel().getSelectedItem()
            .getTaskID();
        adapter.setTask(projectSelectedName, selectedRequirementName, oldTaskID,
            description, status, timeUsed, timeEstimate, deadline,
            employeesToAssign, responsibleEmployee);
      }
      taskFieldsAreEditable(false);
      getTaskFieldsCleared();
    }
    else if (e.getSource() == addEmployeeButton)
    {
      getEmployeeFieldsCleared();
      employeeFieldsAreEditable(true);
    }
    else if (e.getSource() == editEmployeeButton)
    {
      if (employeesListView.getSelectionModel().getSelectedIndex() < 0)
      {
        alertPopUp("Choose employee to edit.");
      }
      else
      {
        employeeFieldsAreEditable(true);
      }
    }
    else if (e.getSource() == saveEmployeeButton)
    {
      if (employeeFieldsValidator())
      {
        alertPopUp("Fill in all the fields.");
        return;
      }

      int employeeID = Integer.parseInt(employeeIDTextField.getText());
      String firstName = employeeFirstName.getText();
      String lastName = employeeLastName.getText();

      if (employeesListView.getSelectionModel().getSelectedIndex() < 0)
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
          Employee oldEmployee = employeesListView.getSelectionModel()
              .getSelectedItem();
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
      getEmployeeFieldsCleared();
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
      requirementsListView.getItems().clear();
      if (projectSelectedComboBox.getSelectionModel().getSelectedIndex() != -1)
      {
        Project project = projectSelectedComboBox.getSelectionModel().getSelectedItem();
        RequirementList requirements = adapter.getSystem().getRequirementList(project.getName());

        for (int i = 0; i < requirements.size(); i++)
        {
          requirementsListView.getItems().addAll(requirements.get(i));
        }
      }
    }
    else if (e.getSource() == projectSelectedOnTasksComboBox)
    {
      updateRequirementToSelect();
    }
    else if (e.getSource() == requirementSelectedComboBox)
    {
      updateTasks();
    }
  }

  public void tabChange(Event event)
  {
    if (adapter != null)
    {
      getProjectFieldsCleared();
      getRequirementFieldsCleared();
      getTaskFieldsCleared();
      getEmployeeFieldsCleared();
      getTeamMembersFieldsCleared();

      if (projectsTab.isSelected())
      {
        updateProjects();
      }
      else if (requirementsTab.isSelected())
      {
        updateProjectsToSelect(projectSelectedComboBox);
      }
      else if (tasksTab.isSelected())
      {
        updateProjectsToSelect(projectSelectedOnTasksComboBox);
        updateRequirementToSelect();
      }
      else if (employeesTab.isSelected())
      {
        updateEmployees();
      }
    }
  }

  public void updateProjects()
  {
    if (adapter != null)
    {
      projectsListView.getItems().clear();
      projectStatusComboBox.getItems().clear();

      ProjectList temp = adapter.getSystem().getAllProjectsOngoing();

      for (int i = 0; i < temp.size(); i++)
      {
        projectsListView.getItems().add(temp.get(i));
      }

      projectStatusComboBox.getItems()
          .addAll(Project.IN_PROCESS, Project.DONE, Project.ARCHIVED);
    }
  }

  public void updateTeamMembers()
  {
    if (projectsListView.getSelectionModel().getSelectedIndex() != -1)
    {
      teamMembersListView.getItems().clear();
      Project selectedProject = projectsListView.getSelectionModel()
          .getSelectedItem();

      EmployeeList teamMembers = adapter.getSystem().getProjectList()
          .getProjectByName(selectedProject.getName()).getAllTeamMembers();

      if (teamMembers != null)
      {
        for (int i = 0; i < teamMembers.size(); i++)
        {
          teamMembersListView.getItems().add(teamMembers.get(i));
        }
      }
    }
  }

  public void updateRequirements()
  {
    if (adapter != null
        && projectSelectedComboBox.getSelectionModel().getSelectedIndex() != -1)
    {
      requirementsListView.getItems().clear();
      String projectSelectedName = projectSelectedComboBox.getSelectionModel()
          .getSelectedItem().getName();
      RequirementList requirementList = adapter.getSystem()
          .getRequirementList(projectSelectedName);

      for (int i = 0; i < requirementList.size(); i++)
      {
        requirementsListView.getItems().add(requirementList.get(i));
      }
    }
  }

  public void updateProjectsToSelect(ComboBox<Project> comboBox)
  {
    comboBox.getItems().clear();
    ProjectList projectList = adapter.getSystem().getProjectList();
    for (int i = 0; i < projectList.size(); i++)
    {
      comboBox.getItems().add(projectList.get(i));
    }
  }

  public void updateRequirementToSelect()
  {
    requirementSelectedComboBox.getItems().clear();
    if (projectSelectedOnTasksComboBox.getSelectionModel().getSelectedIndex()
        != -1)
    {

      Project selectedProject = projectSelectedOnTasksComboBox
          .getSelectionModel().getSelectedItem();
      RequirementList requirementList = adapter.getSystem()
          .getRequirementList(selectedProject.getName());
      for (int i = 0; i < requirementList.size(); i++)
      {
        requirementSelectedComboBox.getItems().add(requirementList.get(i));
      }
    }
  }

  public void updateTasks()
  {
    if (requirementSelectedComboBox.getSelectionModel().getSelectedIndex() != -1
        && projectSelectedOnTasksComboBox.getSelectionModel().getSelectedIndex()
        != -1)
    {
      tasksListView.getItems().clear();
      taskTeamMembersListView.getItems().clear();
      Requirement selectedRequirement = requirementSelectedComboBox.getSelectionModel().getSelectedItem();
      TaskList taskList = selectedRequirement.getTaskList();
      EmployeeList employees = projectSelectedOnTasksComboBox.getSelectionModel().getSelectedItem().getAllTeamMembers();
      if (taskList.size() > 0)
      {
        for (int i = 0; i < taskList.size(); i++)
        {
          tasksListView.getItems().add(taskList.get(i));
        }
      }
      if (employees.size() > 0)
      {
        for (int i = 0; i < employees.size(); i++)
        {
          taskTeamMembersListView.getItems().add(employees.get(i));
        }
      }
    }
  }

  public void updateEmployees()
  {
    employeesListView.getItems().clear();

    EmployeeList employees = adapter.getSystem().getEmployees();

    for (int i = 0; i < employees.size(); i++)
    {
      employeesListView.getItems().addAll(employees.get(i));
    }
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
    taskStatusComboBox.getItems().addAll("Ongoing", "Done");
    taskDescriptionTextArea.setEditable(areEditable);
    taskEstimateTextField.setEditable(areEditable);
    taskTimeUsedTextField.setEditable(areEditable);
    taskDeadline.setEditable(areEditable);
    taskTeamMembersListView.setDisable(!areEditable);
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
    responsibleTeamMemberComboBox.getItems().clear();
    responsibleTeamMemberComboBox.getSelectionModel().select(0);
  }

  public void getTaskFieldsCleared()
  {
    tasksListView.getItems().clear();
    taskIDTextField.clear();
    taskStatusComboBox.getSelectionModel().select(0);
    taskDescriptionTextArea.clear();
    taskEstimateTextField.clear();
    taskTimeUsedTextField.clear();
    taskDeadline.clear();
    taskTeamMembersListView.getItems().clear();
    taskResponsibleEmployeeComboBox.getItems().clear();
  }

  public void getEmployeeFieldsCleared()
  {
    employeeIDTextField.clear();
    employeeFirstName.clear();
    employeeLastName.clear();
    employeeRoleComboBox.getSelectionModel().select(0);
  }

  public boolean taskFieldsValidation()
  {
    return taskIDTextField.getText().isEmpty() || taskDescriptionTextArea
        .getText().isEmpty() || taskEstimateTextField.getText().isEmpty()
        || taskDeadline.getText().isEmpty() ||
        taskTeamMembersListView.getSelectionModel().getSelectedIndices().size()
            == 0;
  }

  public boolean employeeFieldsValidator()
  {
    return employeeIDTextField.getText().isEmpty() || employeeFirstName
        .getText().isEmpty() || employeeLastName.getText().isEmpty();
  }

  public void fillFieldsInProjectTab()
  {
    Project selectedProject = projectsListView.getSelectionModel()
        .getSelectedItem();
    if (selectedProject != null)
    {
      String status = selectedProject.getStatus();
      projectNameTextField.setText(selectedProject.getName());
      projectStatusComboBox.getSelectionModel().select(status);

      updateTeamMembers();
    }
  }

  public void fillRequirementsFields()
  {
    Requirement selectedRequirement = requirementsListView.getSelectionModel()
        .getSelectedItem();
    if (selectedRequirement != null)
    {
      requirementIDTextField.setText(selectedRequirement.getID());
      requirementStatusComboBox.getSelectionModel()
          .select(selectedRequirement.getStatus());
      requirementTypeComboBox.getSelectionModel()
          .select(selectedRequirement.getRequirementType());
      requirementDescriptionTextField
          .setText(selectedRequirement.getDescription());
      estimateHoursTextField
          .setText(String.valueOf(selectedRequirement.getEstimateTime()));
      priorityNumberTextField
          .setText(String.valueOf(selectedRequirement.getPriority()));
      deadlineTextField.setText(selectedRequirement.getDeadline().toString());
      responsibleTeamMemberComboBox.getItems().clear();
      EmployeeList employeeList = projectSelectedComboBox.getSelectionModel()
          .getSelectedItem().getProjectTeam();
      for (int i = 0; i < employeeList.size(); i++)
      {
        responsibleTeamMemberComboBox.getItems().add(employeeList.get(i));
      }
      responsibleTeamMemberComboBox.getSelectionModel()
          .select(selectedRequirement.getResponsibleEmployee());
    }
  }

  public void fillTaskFields()
  {
    Task selectedTask = tasksListView.getSelectionModel().getSelectedItem();
    if (selectedTask != null)
    {
      taskIDTextField.setText(String.valueOf(selectedTask.getID()));
      taskStatusComboBox.getSelectionModel()
          .select(selectedTask.isDone() ? 0 : 1);
      taskDescriptionTextArea.setText(selectedTask.getDescription());
      taskEstimateTextField
          .setText(String.valueOf(selectedTask.getEstimateTime()));
      taskTimeUsedTextField.setText(String.valueOf(selectedTask.getTimeUsed()));
      taskDeadline.setText(selectedTask.getDeadline().toString());
      taskResponsibleEmployeeComboBox.getItems()
          .add(selectedTask.getResponsibleEmployee());
      taskResponsibleEmployeeComboBox.getSelectionModel().select(0);
      EmployeeList teamMembers = selectedTask.getAssignedToTask();
      for (int i = 0; i < teamMembers.size(); i++)
      {
        teamMembersListView.getItems().add(teamMembers.get(i));
      }
    }
  }

  public void fillFieldsEmployeeTab()
  {
    Employee toEdit = employeesListView.getSelectionModel().getSelectedItem();
    //TODO maybe change employee ID to String if we have time.
    employeeIDTextField.setText(String.valueOf(toEdit.getId()));
    employeeFirstName.setText(toEdit.getFirstName());
    employeeLastName.setText(toEdit.getLastName());
  }

  public boolean requirementsFieldsValidator()
  {
    return projectSelectedComboBox.getSelectionModel().getSelectedIndex() == -1
        || requirementIDTextField.getText().isEmpty()
        || requirementDescriptionTextField.getText().isEmpty()
        || estimateHoursTextField.getText().isEmpty() || priorityNumberTextField
        .getText().isEmpty() || deadlineTextField.getText().isEmpty()
        || responsibleTeamMemberComboBox.getSelectionModel().getSelectedIndex()
        == -1;
  }

  public void alertPopUp(String e)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, e, ButtonType.OK);
    alert.setTitle("Error");
    alert.setHeaderText(null);

    alert.showAndWait();
  }
}
