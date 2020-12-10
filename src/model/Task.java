package model;

import java.io.Serializable;
import java.util.Objects;

public class Task implements Serializable
{
  private int taskID;
  private String description;
  private boolean isDone;
  private double estimatedTime;
  private double timeUsed;
  private MyDate deadline;
  private Employee responsibleEmployee;
  private EmployeeList assignedToTask;

  public Task(int taskID, String description, boolean status, double timeUsed, double estimatedTime,
      MyDate deadline, EmployeeList employees, Employee responsibleEmployee)
  {
    this.taskID = taskID;
    this.description = description;
    isDone = status;
    this.timeUsed = timeUsed;
    this.deadline = deadline;
    this.estimatedTime = estimatedTime;
    addTeamMembers(employees);
    this.responsibleEmployee = responsibleEmployee;
  }

  //TODO change as constructor constructor for copy method. And remove it.
  public Task(int taskID, String description, double estimatedTime,
      MyDate deadline, double timeUsed, EmployeeList assignedToTask,
      boolean status)
  {
    this.taskID = taskID;
    this.description = description;
    this.timeUsed = timeUsed;
    this.deadline = deadline;
    this.estimatedTime = estimatedTime;
    isDone = status;
    this.assignedToTask = assignedToTask;
  }

  public void set(String description, boolean status, double timeUsed, double estimatedTime, MyDate deadline,
     EmployeeList employees, Employee responsibleEmployee)
  {
    this.description = description;
    isDone = status;
    this.timeUsed += timeUsed;
    this.deadline = deadline;
    this.estimatedTime = estimatedTime;
    this.responsibleEmployee = responsibleEmployee;
    addTeamMembers(employees);
  }

  public int getTaskID()
  {
    return taskID;
  }

  public double getTimeUsed()
  {
    return timeUsed;
  }

  public double getEstimateTime()
  {
    return estimatedTime;
  }

  public String getDescription()
  {
    return description;
  }

  public MyDate getDeadline()
  {
    return deadline;
  }

  public boolean isDone()
  {
    return isDone;
  }

  public void addTeamMembers(EmployeeList employees)
  {
    this.assignedToTask = new EmployeeList();
    this.assignedToTask = employees;
  }

  public void setResponsibleEmployee(Employee responsibleEmployee)
  {
    this.responsibleEmployee = responsibleEmployee;
  }

  public Employee getResponsibleEmployee()
  {
    return responsibleEmployee;
  }

  public EmployeeList getAssignedToTask()
  {
    return assignedToTask;
  }

  public int getID()
  {
    return taskID;
  }

  public String toString()
  {

    return "ID: " + taskID;

  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Task))
      return false;

    Task other = (Task) obj;
    return taskID == other.taskID && isDone == other.isDone
        && Double.compare(other.estimatedTime, estimatedTime) == 0
        && Double.compare(other.timeUsed, timeUsed) == 0
        && description.equals(other.description)
        && deadline.equals(other.deadline)
        && responsibleEmployee.equals(other.responsibleEmployee)
        && assignedToTask.equals(other.assignedToTask);
  }


  public Task copy()
  {
    return new Task(taskID, description, estimatedTime, deadline, timeUsed,
        assignedToTask.copy(), isDone);
  }
}