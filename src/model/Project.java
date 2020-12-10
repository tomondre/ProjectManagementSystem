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

  public Project(String projectName, String status,
      RequirementList requirementList, EmployeeList projectTeam)
  {
    this.projectName = projectName;
    setStatus(status);
    this.requirementList = requirementList;
    this.projectTeam = projectTeam;
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

  public void editRoleTeamMember(Employee employee, String role)
  {
    Employee temp = projectTeam.getEmployeeByID(employee.getId());
    temp.setRole(role);
  }

  public EmployeeList getAllTeamMembers()
  {
    return projectTeam;
  }

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

  public RequirementList getAllRequirements()
  {
    return requirementList;
  }

  public Project copy()
  {
    return new Project(projectName, status, requirementList.copy(), projectTeam.copy());
  }

  public String toString()
  {
    return projectName + " (" + status + ")";
  }
}
