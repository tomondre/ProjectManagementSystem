package model;

import java.io.Serializable;

public class ProjectManagmentSystem implements Serializable
{
  private EmployeeList employees;
  private ProjectList projectList;
  private ProjectList archivedProjects;

  //TODO check the roles when assigning employees to project team
  //TODO we dont have to copy every single object in project, we just have to copy the employeelist which has references to employees;
  //We have decided to use following path to get specific projectList/requirements/tasks: String projectName / int requirementID / int taskID
  public ProjectManagmentSystem()
  {
    employees = new EmployeeList();
    projectList = new ProjectList();
    archivedProjects = new ProjectList();
  }

  //********************************projectList************************************
  public void addProject(Project project)
  {
    projectList.addProject(project);
  }

  public Project getProjectByName(String projectName)
  {
    return projectList.getProjectByName(projectName);
  }

  public ProjectList getProjectList()
  {
    return projectList;
  }

  //******************************Requirement***********************************
  public void addRequirement(String projectName, Requirement requirement)
  {
    if (getProjectByName(projectName) != null)
    {
      getProjectByName(projectName).addRequirement(requirement);
    }
  }

  public Requirement getRequirementByID(String projectName, String  requirementID)
  {
    return getProjectByName(projectName).getRequirementByID(requirementID);
  }

  //TODO check if its the same as requirement constructor
  public void setRequirement(String projectName, String requirementId,
      int priorityNumber, String description, double estimateTime,
      MyDate deadline, String status)

  {
    getRequirementByID(projectName, requirementId)
        .set(priorityNumber, description, estimateTime, deadline, status);
  }
  //TODO requirements status will be changed differently
  /*public void setRequirementApproved(String projectName, String requirementID)
  {
    getRequirementByID(projectName, requirementID).setApproved();
  }

  public void setRequirementRejected(String projectName, String requirementID)
  {
    getRequirementByID(projectName, requirementID).setRejected();
  }*/

  public void removeRequirement(String projectName, String requirementID)
  {
    if (getProjectByName(projectName) != null)
    {
      getProjectByName(projectName).removeRequirement(requirementID);
    }
  }

  //**********************************Tasks*************************************
  public void addTask(String projectName, String requirementID, Task task)
  {
    if (getRequirementByID(projectName, requirementID) != null)
    {
      getRequirementByID(projectName, requirementID).addTask(task);
    }
  }

  public Task getTaskByID(String projectName, String requirementID, int taskID)
  {
    if (getRequirementByID(projectName, requirementID) != null)
    {
      return getRequirementByID(projectName, requirementID).getTaskById(taskID);
    }
    return null;
  }

  //TODO change based on constructor of the Task
  public void setTask(String projectName, String requirementID, int taskID,
      String description, double estimatedTime, MyDate deadline,
      double timeUsed, boolean isDone)
  {
    if (getTaskByID(projectName, requirementID, taskID) != null)
    {
      getTaskByID(projectName, requirementID, taskID)
          .set(description, estimatedTime, deadline, timeUsed, isDone);
    }
  }

  public void removeTask(String projectName, String requirementID, int taskID)
  {
    if (getRequirementByID(projectName, requirementID) != null)
    {
      getRequirementByID(projectName, requirementID).removeTask(taskID);
    }
  }

  public void addUsedHourToTask(String projectName, String requirementID,
      int taskID, double hours)
  {
    if (getTaskByID(projectName, requirementID, taskID) != null)
    {
      getTaskByID(projectName, requirementID, taskID).addTimeUsed(hours);
    }
  }

  public void setTaskDone(String projectName, String requirementID, int taskID)
  {
    if (getTaskByID(projectName, requirementID, taskID) != null)
    {
      getTaskByID(projectName, requirementID, taskID).taskIsDone();
    }
    if (getRequirementByID(projectName, requirementID) != null)
    {
      getRequirementByID(projectName, requirementID).checkStatus();
    }
  }

  //**********************************Employee**********************************
  public EmployeeList getEmployees()
  {
    return employees;
  }

  public Employee getEmployee(int employeeID)
  {
    return employees.getEmployeeByID(employeeID);
  }

  public void addEmployee(Employee employee) throws IllegalArgumentException
  {
    employees.addEmployee(employee);
  }

  public void addEmployeeToAProject(String projectName, int employeeID,
      String role)
  {
    if (getProjectByName(projectName) != null
        && getEmployee(employeeID) != null)
    {
      getProjectByName(projectName)
          .addTeamMember(getEmployee(employeeID), role);
    }
  }

  public Employee getEmployeeAssignedToProject(String projectName,
      int employeeID)
  {
    if (getProjectByName(projectName) != null)
    {
      return getProjectByName(projectName).getTeamMember(employeeID);
    }
    return null;
  }

  public void setEmployee(int employeeID, String firstName, String lastName)
  {
    if (getEmployee(employeeID) != null)
    {
      getEmployee(employeeID).set(firstName, lastName);
    }
  }

  public void addTeamMemberToTask(String projectName, String requirementID,
      int taskID, int employeeID)
  {
    if (getProjectByName(projectName) != null)
    {

      getProjectByName(projectName)
          .addTeamMemberToTask(requirementID, taskID, employeeID);
    }
  }

  public EmployeeList getNotAssignedEmployees()
  {
    return employees.getNotAssignedEmployees();
  }

  public TaskList getEmployeeTaskList(int employeeID)
  {
    if (getEmployee(employeeID) != null)
    {
      return getEmployee(employeeID).getWorkingOnTasks();
    }
    return null;
  }

  //*****************************Archive****************************************
  public void moveToArchive(String projectName)
  {
    if (getProjectByName(projectName) != null)
    {
      Project temp = getProjectByName(projectName);
      archivedProjects.addProject(temp.copy());
      temp.deleteTeamRoles();
      projectList.remove(temp);
    }
  }

  public ProjectList getArchivedProjects()
  {
    return archivedProjects;
  }

  //********************************LISTS***************************************
  public TaskList getTaskList(String projectName, String requirementID)
  {
    if (getRequirementByID(projectName, requirementID) != null)
    {
      return getRequirementByID(projectName, requirementID).getTaskList();
    }
    return null;
  }

  public RequirementList getRequirementList(String projectName)
  {
    if (getProjectByName(projectName) != null)
    {
      return getProjectByName(projectName).getRequirementList();
    }
    return null;
  }

  public String toString()
  {
    return projectList.toString();
  }
}