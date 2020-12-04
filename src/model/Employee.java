package model;

import java.io.Serializable;

public class Employee implements Serializable
{
  private static final String TEAM_MEMBER = "Team member";
  private static final String SCRUM_MASTER = "Scrum master";
  private static final String PRODUCT_OWNER = "Product owner";
  private static final String PROJECT_CREATOR = "Project creator";
  private int employeeID;
  private String firstName;
  private String lastName;
  private String role;
  private TaskList tasks;

  //TODO when deleting project we have to also delete project in employee,
  public Employee(int employeeID, String firstName, String lastName)
  {
    this.employeeID = employeeID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = "";
    tasks = new TaskList();
  }

  public Employee(int employeeID, String firstName, String lastName,
      String role, TaskList tasks)
  {
    this.employeeID = employeeID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.tasks = tasks;
  }

  public void set(String firstName, String lastName)
  {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public void addTask(Task task)
  {
    tasks.addTask(task);
  }

  public void resetTasks()
  {
    tasks.clear();
  }

  public TaskList getWorkingOnTasks()
  {
    return tasks;
  }

  public String getRole()
  {
    return role;
  }

  public void setRole(String role)
  {
    if (role != "")
    {
      if (role.charAt(0) == 'T' || role.charAt(0) == 't')
      {
        this.role = TEAM_MEMBER;
      }
      else if (role.charAt(0) == 'S' || role.charAt(0) == 's')
      {
        this.role = SCRUM_MASTER;
      }

      else if ((role.charAt(0) == 'P' || role.charAt(0) == 'p')
          && role.charAt(3) == 'd')
      {
        this.role = PRODUCT_OWNER;
      }
      else if ((role.charAt(0) == 'P' || role.charAt(0) == 'p')
          && role.charAt(3) == 'j')
      {
        this.role = PROJECT_CREATOR;
      }
    }
  }

  public String toString()
  {
    String temp = "";
    return "No: " + employeeID + ", " + firstName + " " + lastName + ", " + role
        + tasks;
  }

  public void setTasks(TaskList tasks)
  {
    this.tasks = tasks;
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Employee))
    {
      return false;
    }
    Employee other = (Employee) obj;
    return employeeID == other.employeeID && firstName.equals(other.firstName)
        && lastName.equals(other.lastName);
  }

  public Employee copy()
  {
    return new Employee(employeeID, firstName, lastName, role, tasks.copy());
  }

  public int getId()
  {
    return employeeID;
  }
}
