package GUI;
//TODO test the archived files.
import FileAdapter.SystemAdapter;
import javafx.collections.ObservableList;
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

  @FXML private ListView<Project> projectsListView;

  @FXML private TextField projectNameTextField;

  @FXML private ComboBox<String> projectStatusComboBox;

  @FXML private Button addProjectButton;

  @FXML private Button editProjectButton;

  @FXML private Button archiveProjectButton;

  @FXML private ComboBox<Employee> availableEmployeeComboBox;

  @FXML private Button assignEmployeeButton;

  @FXML private Button editRoleButton;

  @FXML private Button saveProjectButton;

  @FXML private ListView<Employee> teamMembersListView;

  @FXML private Tab requirementsTab;

  @FXML private ListView<Requirement> requirementsListView;

  @FXML private ListView<Requirement> requirementsForTestingListView;

  @FXML private ComboBox<Project> projectSelectedComboBox;

  @FXML private TextField requirementIDTextField;

  @FXML private ComboBox<String> requirementStatusComboBox;

  @FXML private ComboBox<String> requirementTypeComboBox;

  @FXML private TextField requirementDescriptionTextField;

  @FXML private TextField estimateHoursTextField;

  @FXML private TextField priorityNumberTextField;

  @FXML private TextField deadlineTextField;

  @FXML private TextField timeUsedTextField;

  @FXML private ComboBox<Employee> responsibleTeamMemberComboBox;

  @FXML private Button addRequirementButton;

  @FXML private Button editRequirementButton;

  @FXML private Button removeRequirementButton;

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

  @FXML private Button removeTaskButton;

  @FXML private Button saveTaskButton;

  @FXML private Tab employeesTab;

  @FXML private ListView<Employee> employeesListView;

  @FXML private TextField employeeIDTextField;

  @FXML private TextField employeeFirstName;

  @FXML private TextField employeeLastName;

  @FXML private ComboBox<String> employeeRoleComboBox;

  @FXML private ListView<Task> employeeTaskListView;

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
    menuItemsRadioButtons();
    updateProjects();
    updateEmployees();
  }

  public void handleActions(ActionEvent e)
  {
    if (e.getSource() == addProjectButton)
    {
      Command = "addProject";
      projectNameTextField.setEditable(true);
      projectStatusComboBox.setDisable(false);
      getProjectFieldsCleared();

    }
    else if (e.getSource() == editProjectButton)
    {
      Command = "editProject";
      if (projectsListView.getSelectionModel().getSelectedIndex() < 0)
      {
        alertPopUp("Choose project to edit.");
      }
      else
      {
        projectFieldsAreEditable(true);
      }
    }
    else if (e.getSource() == archiveProjectButton)
    {
      if (projectsListView.getSelectionModel().getSelectedIndex() == -1)
      {
        alertPopUp("Select project to archive.");
      }
      else
      {
        Command = "archiveProject";
      }
    }
    else if (e.getSource() == assignEmployeeButton)
    {
      Command = "assignEmployee";
      teamMembersFieldsAreEditable(true);

      getTeamMembersFieldsCleared();

      EmployeeList availableEmployees = adapter.getSystem().getAllEmployees()
          .getNotAssignedEmployees();
      for (int i = 0; i < availableEmployees.size(); i++)
      {
        availableEmployeeComboBox.getItems().addAll(availableEmployees.get(i));
      }
    }
    else if (e.getSource() == editRoleButton)
    {
      if (teamMembersListView.getSelectionModel().getSelectedIndex() == -1)
      {
        alertPopUp("Select team member to edit.");
      }
      else
      {
        Command = "editRole";
        availableEmployeeComboBox.setDisable(true);
        employeeRoleComboBox.setDisable(false);

        employeeRoleComboBox.getItems().clear();
        employeeRoleComboBox.getItems()
            .addAll(Employee.DEVELOPER, Employee.SCRUM_MASTER, Employee.PRODUCT_OWNER, Employee.PROJECT_CREATOR);
      }
    }
    else if (e.getSource() == saveProjectButton)
    {
      if (projectNameTextField.getText().isEmpty())
      {
        alertPopUp("Fill in all the fields.");
        return;
      }
      Project selectedProject;
      String newName = projectNameTextField.getText();
      String status = projectStatusComboBox.getSelectionModel()
          .getSelectedItem();
      switch (Command)
      {
        case "addProject":
          try
          {
            adapter.addProject(newName, status);
          }
          catch (IllegalArgumentException event)
          {
            alertPopUp(event.getMessage());
          }
          break;
        case "assignEmployee":
          Employee chosenEmployee = availableEmployeeComboBox
              .getSelectionModel().getSelectedItem();

          String role = employeeRoleComboBox.getSelectionModel()
              .getSelectedItem();

          selectedProject = projectsListView.getSelectionModel()
              .getSelectedItem();
          adapter
              .addEmployeeToProject(selectedProject.getName(), chosenEmployee,
                  role);
          break;
        case "editRole":
          selectedProject = projectsListView.getSelectionModel()
              .getSelectedItem();
          Employee employee = teamMembersListView.getSelectionModel()
              .getSelectedItem();
          role = employeeRoleComboBox.getSelectionModel().getSelectedItem();

          adapter.changeRoleEmployee(selectedProject.getName(), employee, role);
          break;
        case "editProject":
          try
          {
            selectedProject = projectsListView.getSelectionModel()
                .getSelectedItem();

            adapter.editProject(newName, selectedProject.getName(), status);
          }
          catch (IllegalArgumentException er)
          {
            alertPopUp(er.getMessage());
          }
          break;
        case "archiveProject":
          selectedProject = projectsListView.getSelectionModel()
              .getSelectedItem();

          adapter.editProject(selectedProject.getName(), selectedProject.getName(), Project.ARCHIVED);
          adapter.moveProjectToArchive(selectedProject.getName());
          break;
      }
      updateProjects();
      updateTeamMembers();

      projectFieldsAreEditable(false);
      teamMembersFieldsAreEditable(false);
      getProjectFieldsCleared();
      getTeamMembersFieldsCleared();
      Command = "";
    }
    else if (e.getSource() == addRequirementButton)
    {
      if (projectSelectedComboBox.getSelectionModel().getSelectedIndex() == -1)
      {
        alertPopUp("Select project.");
      }
      else
      {
        Command = "addRequirement";
        requirementsFieldsAreEditable(true);
        getRequirementFieldsCleared();

        String projectName = projectSelectedComboBox.getSelectionModel()
            .getSelectedItem().getName();

        EmployeeList teamMembers = adapter.getSystem().getAllProjectsOngoing()
            .getProjectByName(projectName).getAllTeamMembers();

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
        Command = "editRequirement";
        requirementsFieldsAreEditable(true);
        requirementIDTextField.setEditable(false);
        fillRequirementsFields();
      }
    }
    else if (e.getSource() == removeRequirementButton)
    {
      if (requirementsListView.getSelectionModel().getSelectedIndex() == -1)
      {
        alertPopUp("Choose requirement to remove.");
      }
      else
      {
        Command = "removeRequirement";
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

      Requirement selectedRequirement = requirementsListView.getSelectionModel()
          .getSelectedItem();

      switch (Command)
      {
        case "addRequirement":
          adapter.addRequirement(selectedProject.getName(), requirementID,
              priorityNumber, requirementDescription, timeEstimate, status,
              requirementType, deadline, responsibleEmployee);
          break;
        case "editRequirement":
          adapter.setRequirement(selectedProject.getName(), requirementID,
              priorityNumber, requirementDescription, timeEstimate, status,
              requirementType, deadline, responsibleEmployee);
          break;
        case "removeRequirement":
          adapter.removeRequirement(selectedProject.getName(),
              selectedRequirement.getID());
          break;
      }
      requirementsFieldsAreEditable(false);
      getRequirementFieldsCleared();
      updateRequirements();
      Command = "";
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
        Command = "addTask";
        getTaskFieldsCleared();
        taskFieldsAreEditable(true);
        //TODO refactor a bit
        EmployeeList employeeList = projectSelectedOnTasksComboBox
            .getSelectionModel().getSelectedItem().getAllTeamMembers();
        for (int i = 0; i < employeeList.size(); i++)
        {
          taskResponsibleEmployeeComboBox.getItems().add(employeeList.get(i));
          taskTeamMembersListView.getItems().add(employeeList.get(i));
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
        Command = "editTask";
        taskFieldsAreEditable(true);
        taskIDTextField.setEditable(false);
      }
    }
    else if (e.getSource() == removeTaskButton)
    {
      if (tasksListView.getSelectionModel().getSelectedIndex() == -1)
      {
        alertPopUp("Choose task to remove.");
      }
      else
      {
        Command = "removeTask";
      }
    }
    else if (e.getSource() == saveTaskButton)
    {
      if (taskFieldsValidation() && !Command.equals("removeTask"))
      {
        alertPopUp("Fill in all the fields.");
        getTaskFieldsCleared();
      }
      else
      {
        String selectedProjectName = projectSelectedOnTasksComboBox
            .getSelectionModel().getSelectedItem().getName();
        String selectedRequirementID = requirementSelectedComboBox
            .getSelectionModel().getSelectedItem().getID();

        int taskID = Integer.parseInt(taskIDTextField.getText());
        boolean status =
            taskStatusComboBox.getSelectionModel().getSelectedIndex() != 0;
        String description = taskDescriptionTextArea.getText();
        double timeEstimate = Double
            .parseDouble(taskEstimateTextField.getText());
        double timeUsed = Double.parseDouble(taskTimeUsedTextField.getText());
        String[] time = taskDeadline.getText().split("[./\\-]");
        MyDate deadline = new MyDate(Integer.parseInt(time[0]),
            Integer.parseInt(time[1]), Integer.parseInt(time[2]));
        Employee responsibleEmployee = taskResponsibleEmployeeComboBox
            .getSelectionModel().getSelectedItem();
        ObservableList<Employee> employeesChosen = taskTeamMembersListView
            .getSelectionModel().getSelectedItems();
        EmployeeList employeesToAssign = new EmployeeList();

        Task toAdd = new Task(taskID, description, status, timeUsed,
            timeEstimate, deadline, employeesToAssign, responsibleEmployee);
        for (Employee empl : employeesChosen)
        {
          employeesToAssign.addEmployee(empl);
        }
        switch (Command)
        {
          case "addTask":
            adapter.addTask(selectedProjectName, selectedRequirementID, toAdd);
            for (int i = 0; i < employeesToAssign.size(); i++)
            {
              //TODO refactor
              adapter.addTaskToEmployee(employeesToAssign.get(i),
                  new Task(taskID, description, status, timeUsed, timeEstimate,
                      deadline, employeesToAssign, responsibleEmployee));
            }
            break;
          case "editTask":
            Task selectedTask = tasksListView.getSelectionModel()
                .getSelectedItem();
            adapter.setTask(selectedProjectName, selectedRequirementID,
                selectedTask.getTaskID(), description, status, timeUsed,
                timeEstimate, deadline, employeesToAssign, responsibleEmployee);
            break;
          case "removeTask":
            selectedTask = tasksListView.getSelectionModel().getSelectedItem();
            EmployeeList employeesAssigned = adapter.getSystem()
                .getAllTasks(selectedProjectName, selectedRequirementID)
                .getTaskById(selectedTask.getTaskID()).getAssignedToTask();
            for (int i = 0; i < employeesAssigned.size(); i++)
            {
              adapter.removeTaskFromEmployee(employeesAssigned.get(i),
                  selectedTask);
            }
            adapter.removeTask(selectedProjectName, selectedRequirementID,
                selectedTask.getTaskID());
            break;
        }
        int projectIndex = projectSelectedOnTasksComboBox.getSelectionModel()
            .getSelectedIndex();
        int requirementIndex = requirementSelectedComboBox.getSelectionModel()
            .getSelectedIndex();
        taskFieldsAreEditable(false);
        getTaskFieldsCleared();
        tasksListView.getItems().clear();
        updateProjectsToSelect(projectSelectedOnTasksComboBox);
        projectSelectedOnTasksComboBox.getSelectionModel().select(projectIndex);
        updateRequirementToSelect();
        requirementSelectedComboBox.getSelectionModel()
            .select(requirementIndex);
        updateTasks();
      }
      Command = "";
    }
    else if (e.getSource() == addEmployeeButton)
    {
      Command = "addEmployee";
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
        Command = "editEmployee";
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

      switch (Command)
      {
        case "addEmployee":
          Employee toAdd = new Employee(employeeID, firstName, lastName);
          try
          {
            adapter.addEmployee(toAdd);
          }
          catch (IllegalArgumentException empE)
          {
            alertPopUp(empE.getMessage());
          }
          break;
        case "editEmployee":
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
          break;
      }
      updateEmployees();
      employeeFieldsAreEditable(false);
      getEmployeeFieldsCleared();
      Command = "";
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
    else if (e.getSource() == aboutMenuItem)
    {
      alertPopUp(
          "Project management system, " + "designed for the company ColourIT. "
              + "It helps them keep track of their projects.\n\nVersion: 1.0"
              + "\nRealise date: 18/12/2020"
              + "\nReporting problems to: Group 8 - IT-1X-A20"
              + "\n\nAll rights reserved to ColourIT.");
    }
    else if (e.getSource() == projectSelectedComboBox)
    {
      requirementsListView.getItems().clear();
      requirementsForTestingListView.getItems().clear();
      if (projectSelectedComboBox.getSelectionModel().getSelectedIndex() != -1)
      {
        String projectName = projectSelectedComboBox.getSelectionModel()
            .getSelectedItem().getName();
        adapter.checkRequirementsStatus(projectName);
        requirementsListView.getItems().addAll(
            adapter.getSystem().getAllRequirements(projectName)
                .getAllNotApprovedRequirements());
        requirementsForTestingListView.getItems().addAll(
            adapter.getSystem().getAllRequirements(projectName)
                .getAllToBeApprovedRequirements());
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

  public void menuItemsRadioButtons()
  {
    if (ongoingProjects.isSelected())
    {
      projectNameTextField.setPromptText("Ongoing projects");
      setButtonsDisabled(false);
      updateProjects();
    }
    else if (archivedProjects.isSelected())
    {
      projectNameTextField.setPromptText("Archived projects");
      setButtonsDisabled(true);
      updateProjects();
    }
  }

  public void tabChange(Event event)//TODO Ask Allan about this event property
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

      ProjectList temp = new ProjectList();

      if (ongoingProjects.isSelected())
      {
        temp = adapter.getSystem().getAllProjectsOngoing();
      }
      else if (archivedProjects.isSelected())
      {
        temp = adapter.getSystem().getAllArchivedProjects();
      }
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

      EmployeeList teamMembers = adapter.getSystem().getAllProjectsOngoing()
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
      requirementsForTestingListView.getItems().clear();
      String projectSelectedName = projectSelectedComboBox.getSelectionModel()
          .getSelectedItem().getName();
      adapter.checkRequirementsStatus(projectSelectedName);

      requirementsListView.getItems().addAll(
          adapter.getSystem().getAllRequirements(projectSelectedName)
              .getAllNotApprovedRequirements());
      requirementsForTestingListView.getItems().addAll(
          adapter.getSystem().getAllRequirements(projectSelectedName)
              .getAllToBeApprovedRequirements());
    }
  }

  public void updateProjectsToSelect(ComboBox<Project> comboBox)
  {
    comboBox.getItems().clear();
    ProjectList projectList = adapter.getSystem().getAllProjectsOngoing();
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
          .getAllRequirements(selectedProject.getName());
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
      Requirement selectedRequirement = requirementSelectedComboBox
          .getSelectionModel().getSelectedItem();
      TaskList taskList = selectedRequirement.getTaskList();
      EmployeeList employees = projectSelectedOnTasksComboBox
          .getSelectionModel().getSelectedItem().getAllTeamMembers();
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

    EmployeeList employees = adapter.getSystem().getAllEmployees();

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
    taskStatusComboBox.getItems().clear();
    taskStatusComboBox.getItems().addAll("Ongoing", "Done");
    taskDescriptionTextArea.setEditable(areEditable);
    taskEstimateTextField.setEditable(areEditable);
    taskTimeUsedTextField.setEditable(areEditable);
    taskDeadline.setEditable(areEditable);
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
    availableEmployeeComboBox.getItems().clear();
    employeeRoleComboBox.getItems().clear();

    employeeRoleComboBox.getItems()
        .addAll(Employee.DEVELOPER, Employee.SCRUM_MASTER,
            Employee.PRODUCT_OWNER, Employee.PROJECT_CREATOR);

    employeeRoleComboBox.getSelectionModel().select(0);
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
      timeUsedTextField
          .setText(String.valueOf(selectedRequirement.getTotalTimeUsed()));
      responsibleTeamMemberComboBox.getItems().clear();
      EmployeeList employeeList = projectSelectedComboBox.getSelectionModel()
          .getSelectedItem().getAllTeamMembers();
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
    taskTeamMembersListView.getItems().clear();
    Task selectedTask = tasksListView.getSelectionModel().getSelectedItem();
    if (selectedTask != null)
    {
      taskIDTextField.setText(String.valueOf(selectedTask.getID()));
      taskStatusComboBox.getSelectionModel()
          .select(selectedTask.isDone() ? 1 : 0);
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
        taskTeamMembersListView.getItems().add(teamMembers.get(i));
      }
    }
  }

  public void fillEmployeeTab()
  {
    Employee toEdit = employeesListView.getSelectionModel().getSelectedItem();
    //TODO maybe change employee ID to String if we have time.
    employeeIDTextField.setText(String.valueOf(toEdit.getId()));
    employeeFirstName.setText(toEdit.getFirstName());
    employeeLastName.setText(toEdit.getLastName());
    employeeTaskListView.getItems().clear();
    //TODO do we need to go through the adapter?
    TaskList employeeTasks = adapter.getSystem().getAllEmployees()
        .getEmployeeByID(toEdit.getId()).getWorkingOnTasks();
    for (int i = 0; i < employeeTasks.size(); i++)
    {
      employeeTaskListView.getItems().add(employeeTasks.get(i));
    }
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

  public void setButtonsDisabled(boolean areDisabled)
  {
    addProjectButton.setDisable(areDisabled);
    editProjectButton.setDisable(areDisabled);
    archiveProjectButton.setDisable(areDisabled);
    assignEmployeeButton.setDisable(areDisabled);
    editRoleButton.setDisable(areDisabled);
    saveProjectButton.setDisable(areDisabled);
    addRequirementButton.setDisable(areDisabled);
    editRequirementButton.setDisable(areDisabled);
    removeRequirementButton.setDisable(areDisabled);
    saveRequirementButton.setDisable(areDisabled);
    addTaskButton.setDisable(areDisabled);
    editTaskButton.setDisable(areDisabled);
    removeTaskButton.setDisable(areDisabled);
    saveTaskButton.setDisable(areDisabled);
    addEmployeeButton.setDisable(areDisabled);
    editEmployeeButton.setDisable(areDisabled);
    saveEmployeeButton.setDisable(areDisabled);
  }

  public void alertPopUp(String e)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, e, ButtonType.OK);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setResizable(true);

    alert.showAndWait();
  }
}
