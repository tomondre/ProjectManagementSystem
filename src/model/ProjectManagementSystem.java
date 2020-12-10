package model;

import java.io.Serializable;

public class ProjectManagementSystem implements Serializable
{
  private EmployeeList employees;
  private ProjectList ongoingProjectList;
  private ProjectList archivedProjects;

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
    if (getAllProjectsOngoing().getProjectByName(projectName) != null)
    {
      return getAllProjectsOngoing().getProjectByName(projectName).getAllRequirements();
    }
    else
    {
      return getAllArchivedProjects().getProjectByName(projectName).getAllRequirements();
    }
  }

  public Requirement getRequirementByID(String projectName,
      String requirementID)
  {
    if (getAllProjectsOngoing().getProjectByName(projectName) != null)
    {
      return getAllProjectsOngoing().getProjectByName(projectName).getRequirementByID(requirementID);
    }
    else
    {
      return getAllArchivedProjects().getProjectByName(projectName).getRequirementByID(requirementID);
    }
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

  public void employeesDeleteRoles(EmployeeList employees)
  {
    for (int i = 0; i < this.employees.size(); i++)
    {
      for (int j = 0; j < employees.size(); j++)
      {
        if (this.employees.get(i).getId() == employees.get(j).getId())
        {
          this.employees.get(i).setRole("");
        }
      }
    }
  }

  //*****************************Archive****************************************
  public void moveToArchive(String projectName)
  {
    if (getAllProjectsOngoing().getProjectByName(projectName) != null)
    {
      Project temp = getAllProjectsOngoing().getProjectByName(projectName);
      archivedProjects.addProject(temp.copy());
      EmployeeList empl = temp.getAllTeamMembers();
      employeesDeleteRoles(empl);
      ongoingProjectList.remove(temp);
    }
  }

  public String toString()
  {
    return ongoingProjectList.toString();
  }
}