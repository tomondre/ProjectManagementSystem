package model;

import java.io.Serializable;

public class Project implements Serializable
{
  private String projectName;
  private EmployeeList projectTeam;
  private RequirementList requirementList;
  private String status;
  public static final String DONE = "Done";
  public static final String ARCHIVED = "Archived";
  public static final String IN_PROCESS = "In process";

  public Project(String projectName, String status)
  {
    projectTeam = new EmployeeList();
    this.projectName = projectName;
    requirementList = new RequirementList();
    setStatus(status);
  }

  public Project(String projectName,
      RequirementList requirementList)             //TODO this one is for copy method. Also if there will be responsible person we have to add him into conctructor
  {
    this.projectName = projectName;
    this.requirementList = requirementList;
  }

  public void setProjectName(String name)
  {
    projectName = name;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public String getName()
  {
    return projectName;
  }

  public String getStatus()
  {
    return status;
  }

  public void addTeamMember(Employee employee, String role)
  {
    employee.setRole(role);
    projectTeam.addEmployee(employee);
  }

  public EmployeeList getAllTeamMembers()
  {
    return projectTeam;
  }

  //TODO delete projectTeam after making a copy to archive so they wont have roles and can be assigned to new projects
  public void deleteTeamRoles()
  {
    projectTeam.deleteRoles();
  }

  public void addRequirement(Requirement requirement)
  {
    requirementList.addRequirement(requirement);
  }

  public Requirement getRequirementByID(String requirementID)
  {
    return requirementList.getRequirementByID(requirementID);
  }

  public RequirementList getRequirementList()
  {
    return requirementList;
  }

  public void removeRequirement(String requirementID)
  {
    requirementList.removeRequirement(requirementID);
  }

  //TODO change this so it will match constructor
  public Project copy()
  {
    return new Project(projectName, requirementList.copy());
  }

  public String toString()
  {
    return projectName + "(" + status + ")";
  }
}
