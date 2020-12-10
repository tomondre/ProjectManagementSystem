package model;

import java.io.Serializable;

public class ProjectManagementSystem implements Serializable
{
  private EmployeeList employees;
  private ProjectList ongoingProjectList;
  private ProjectList archivedProjects;

  //TODO we dont have to copy every single object in project, we just have to copy the employee list which has references to employees;
  //We have decided to use following path to get specific projectList/requirements/tasks: String projectName / int requirementID / int taskID
  public ProjectManagementSystem()
  {
    employees = new EmployeeList();
    ongoingProjectList = new ProjectList();
    archivedProjects = new ProjectList();
  }

  //********************************projectList************************************
  public ProjectList getAllProjectsOngoing()
  {
    return ongoingProjectList;
  }

  public ProjectList getAllArchivedProjects()
  {
    return archivedProjects;
  }

  //******************************Requirement***********************************
  public RequirementList getAllRequirements(String projectName)
  {
    return getAllProjectsOngoing().getProjectByName(projectName)
        .getRequirementList();

  }

  public Requirement getRequirementByID(String projectName,
      String requirementID)
  {
    return getAllProjectsOngoing().getProjectByName(projectName)
        .getRequirementByID(requirementID);
  }

  //**********************************Tasks*************************************
  public TaskList getAllTasks(String projectName, String requirementId)
  {
    return getAllRequirements(projectName).getRequirementByID(requirementId).getTaskList();
  }

  //**********************************Employee**********************************
  public EmployeeList getAllEmployees()
  {
    return employees;
  }

  //*****************************Archive****************************************
  public void moveToArchive(String projectName)
  {
    if (getAllProjectsOngoing().getProjectByName(projectName) != null)
    {
      Project temp = getAllProjectsOngoing().getProjectByName(projectName);
      archivedProjects.addProject(temp.copy());
      temp.deleteTeamRoles();
      ongoingProjectList.remove(temp);
    }
  }

  public String toString()
  {
    return ongoingProjectList.toString();
  }
}