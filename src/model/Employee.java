package model;

import java.io.Serializable;

/**
 * A class of Employee object
 */
public class Employee implements Serializable
{
  public static final String DEVELOPER = "Developer";
  public static final String SCRUM_MASTER = "Scrum master";
  public static final String PRODUCT_OWNER = "Product owner";
  public static final String PROJECT_CREATOR = "Project creator";
  private int employeeID;
  private String firstName;
  private String lastName;
  private String role;
  private TaskList tasks;

  /**
   * A constructor for creating Employee
   * @param employeeID the unique ID of the Employee object
   * @param firstName the first name of the Employee object
   * @param lastName the last name of the Employee object
   */
  public Employee(int employeeID, String firstName, String lastName)
  {
    this.employeeID = employeeID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = "";
    tasks = new TaskList();
  }

  /**
   * A constructor called when copying an Employee object
   * @param employeeID the unique ID of the Employee object
   * @param firstName the first name of the Employee object
   * @param lastName the last name of the Employee object
   * @param role the role of the Employee object
   * @param tasks tasks the Employee is assigned to
   */
  public Employee(int employeeID, String firstName, String lastName,
      String role, TaskList tasks)
  {
    this.employeeID = employeeID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.tasks = tasks;
  }

  /**
   * Sets an ID of the Employee object
   * @param employeeID the unique
   */
  public void setEmployeeID(int employeeID)
  {
    this.employeeID = employeeID;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public void setRole(String role)
  {
    this.role = role;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public int getId()
  {
    return employeeID;
  }

  public String getRole()
  {
    return role;
  }

  public void addTask(Task task)
  {
    tasks.addTask(task);
  }

  public void removeTask(Task task)
  {
    tasks.removeTask(task.getTaskID());
  }

  public void resetTasks()
  {
    tasks.clear();
  }

  public TaskList getWorkingOnTasks()
  {
    return tasks;
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

  public String toString()
  {
    String toAppend = role.equals("") ? "": "(" + role + ")";
    return firstName + " " + lastName + " " + toAppend;
  }
}
