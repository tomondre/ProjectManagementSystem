package model;

import java.io.Serializable;

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

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public TaskList getTasks()
  {
    return tasks;
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

  public String getRole()
  {
    return role;
  }

  public void setRole(String role)
  {
    this.role = role;
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

  public String toString()
  {
    String toAppend = role.equals("") ? "": "(" + role + ")";
    return firstName + " " + lastName + " " + toAppend;
  }
}
