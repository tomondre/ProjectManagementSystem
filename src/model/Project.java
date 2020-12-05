package model;

import java.io.Serializable;

public class Project implements Serializable
{
  private String projectName;
  private EmployeeList projectTeam;
  private RequirementList requirementList;
  public static final String DONE = "Done";
  public static final String ARCHIVED = "Archived";
  public static final String IN_PROCESS = "In process";

  //TODO add status of the projects - this we will be displaying in the website

  public Project(String projectName)
  {
    projectTeam = new EmployeeList();
    this.projectName = projectName;
    requirementList = new RequirementList();
  }

  public Project(String projectName,
      RequirementList requirementList)             //this one if for copy method. Also if there will be responsible person we have to add him into conctructor
  {
    this.projectName = projectName;
    this.requirementList = requirementList;
  }

  public String getName()
  {
    return projectName;
  }

  public void setProjectName(String name)
  {
    projectName = name;
  }

  public RequirementList getAllRequirements()
  {
    return requirementList;
  }

  public RequirementList getNotApprovedRequirements()
  {
    return requirementList.getAllNotApprovedRequirements();
  }

  public void addTeamMember(Employee employee, String role)
  {
    employee.setRole(role);
    projectTeam.addEmployee(employee);
  }

  //delete projectTeam after making a copy to archive so they wont have roles and can be assigned to new projects
  public void deleteTeamRoles()
  {
    projectTeam.deleteRoles();
  }

  public void addTeamMemberToTask(int requirementID, int taskID, int employeeID)
  {
    if (getTaskById(requirementID, taskID) != null
        && getTeamMember(employeeID) != null)
    {
      getTaskById(requirementID, taskID)
          .addTeamMember(getTeamMember(employeeID));
      //      getTeamMember(employeeID).addTask(getTaskById(requirementID, taskID));
    }
  }

  public Employee getTeamMember(int employeeID)
  {
    if (projectTeam != null)
    {
      if (projectTeam.getEmployeeByID(employeeID) != null)
      {
        return projectTeam.getEmployeeByID(employeeID);
      }
    }
    return null;
  }

  public Task getTaskById(int requirementID, int taskID)
  {
    return requirementList.getTaskById(requirementID, taskID);
  }

  public Requirement getRequirementByID(int requirementID)
  {
    return requirementList.getRequirementByID(requirementID);
  }

  public void addRequirement(Requirement requirement)
  {
    requirementList.addRequirement(requirement);
  }

  public void removeRequirement(int requirementID)
  {
    requirementList.removeRequirement(requirementID);
  }

  public EmployeeList getProjectTeam()
  {
    return projectTeam;
  }

  public void sortRequirements()
  {
    requirementList.sortRequirementsByPriorities();
  }

  //change this so it will match constructor
  public Project copy()
  {
    return new Project(projectName, requirementList.copy());
  }

  public String toString()
  {
    return projectName;
  }

  public RequirementList getRequirementList()
  {
    return requirementList;
  }
}
