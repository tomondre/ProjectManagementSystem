package model;

import java.io.Serializable;

public class Task implements Serializable
{
  private int taskID;
  private String description;
  private boolean isDone;
  private double estimatedTime;
  private double timeUsed;
  private MyDate deadline;
  private EmployeeList assignedToTask;

  //TODO responsible team member will be the first one in the arraylist of assignedToTask - index 0
  public Task(int taskID, String description, double estimatedTime,
      MyDate deadline, Employee responsibleEmployee)
  {
    this.taskID = taskID;
    this.description = description;
    this.timeUsed = 0;
    this.deadline = deadline;
    this.estimatedTime = estimatedTime;
    isDone = false;
    assignedToTask = new EmployeeList();
    assignedToTask.addEmployee(responsibleEmployee);
  }

  public Task(int taskID, String description, double estimatedTime,
      MyDate deadline, double timeUsed, EmployeeList assignedToTask,
      boolean isDone)
  {
    this.taskID = taskID;
    this.description = description;
    this.timeUsed = timeUsed;
    this.deadline = deadline;
    this.estimatedTime = estimatedTime;
    this.isDone = isDone;
    this.assignedToTask = assignedToTask;
  }

  //TODO add also responsible employee
  //TODO change this based on constructor
  public void set(String description, String status, double estimatedTime,
      MyDate deadline)
  {
    this.description = description;
    this.timeUsed = timeUsed;
    this.deadline = deadline;
    this.estimatedTime = estimatedTime;
    isDone = false;
  }

  public int getTaskID()
  {
    return taskID;
  }

  public void addTimeUsed(double timeUsed)
  {
    this.timeUsed += timeUsed;
  }

  public double getTimeUsed()
  {
    return timeUsed;
  }

  public double getEstimateTime()
  {
    return estimatedTime;
  }

  public void taskIsDone()
  {
    isDone = true;
  }

  public boolean isDone()
  {
    return isDone;
  }

  public void addTeamMember(Employee employee)
  {
    assignedToTask.addEmployee(employee);
  }

  public int getID()
  {
    return taskID;
  }

  public String toString()
  {
    return "ID: " + taskID + ", description: " + description + ", deadline: "
        + deadline + ", timeUsed: " + timeUsed + ", reponsible:"
        + assignedToTask.getResponsibleEmployee();
  }

  //TODO change when we will know whole contructor
  public boolean equals(Object obj)
  {
    if (!(obj instanceof Task))
    {
      return false;
    }
    Task other = (Task) obj;
    return false;
  }

  //  public void copyEmployeeList()
  //  {
  //    EmployeeList temp = assignedToTask.copy();
  //    assignedToTask = null;
  //    assignedToTask = temp;
  //  }

  public Task copy()
  {
    return new Task(taskID, description, estimatedTime, deadline, timeUsed,
        assignedToTask.copy(), isDone);
  }
}