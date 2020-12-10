package model;

import java.io.Serializable;

public class Requirement implements Serializable
{
  public static final String APPROVED = "Approved";                           //we have to decide which system of comparing Strings (roles and statuses) we want to use
  public static final String REJECTED = "Rejected";
  public static final String STARTED = "Started";
  public static final String NOT_STARTED = "Not started";
  public static final String ENDED = "Ended";
  public static final String FUNCTIONAL = "Functional";
  public static final String NON_FUNCTIONAL = "Non functional";
  private String requirementId;
  private String description;
  private String status;
  private double estimateTime;
  private int priorityNumber;
  private MyDate deadline;
  private TaskList taskList;
  private Employee responsibleEmployee;
  private String requirementType;

  public Requirement(String requirementId, int priorityNumber,
      String description, double estimateTime, String status,
      String requirementType, MyDate deadline, Employee responsibleEmployee)
  {
    this.priorityNumber = priorityNumber;
    this.responsibleEmployee = responsibleEmployee;
    taskList = new TaskList();
    this.requirementId = requirementId;
    this.description = description;
    this.estimateTime = estimateTime;
    this.deadline = deadline;
    setRequirementType(requirementType);
    setStatus(status);
  }

  //Constructor for copy method
  public Requirement(String requirementId, int priorityNumber,
      String description, double estimateTime, String status,
      String requirementType, MyDate deadline, Employee responsibleEmployee,
      TaskList tasks)
  {
    this.priorityNumber = priorityNumber;
    taskList = tasks;
    this.requirementId = requirementId;
    this.description = description;
    this.estimateTime = estimateTime;
    this.deadline = deadline;
    this.status = status;
    this.responsibleEmployee = responsibleEmployee;
    this.requirementType = requirementType;
  }

  //******************************SETTERS*****************************************
  public void set(int priorityNumber, String description, double estimateTime,
      MyDate deadline, String status, String requirementType,
      Employee responsibleEmployee)
  {
    this.priorityNumber = priorityNumber;
    this.description = description;
    this.estimateTime = estimateTime;
    this.deadline = deadline;
    setStatus(status);
    setRequirementType(requirementType);
    this.responsibleEmployee = responsibleEmployee;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public void setRequirementType(String requirementType)
  {
    this.requirementType = requirementType;
  }

  public boolean isDone()
  {
    return taskList.isDone();
  }

  //***********************************GETTERS**********************************
  public String getDescription()
  {
    return description;
  }

  public double getEstimateTime()
  {
    return estimateTime;
  }

  public MyDate getDeadline()
  {
    return deadline;
  }

  public String getStatus()
  {
    return status;
  }

  public String getRequirementType()
  {
    return requirementType;
  }

  public Employee getResponsibleEmployee()
  {
    return responsibleEmployee;
  }

  public boolean toBeApproved()
  {
    return status.equals(ENDED);
  }

  public double getTotalTimeUsed()
  {
    return taskList.totalTimeUsed();
  }

  public int getPriority()
  {
    return priorityNumber;
  }

  public String getID()
  {
    return requirementId;
  }

  //********************************taskList************************************
  public void addTask(Task task)
  {
    taskList.addTask(task);
  }

  public void removeTask(int taskID)
  {

    taskList.removeTask(taskID);
  }

  public Task getTaskById(int taskID)
  {
    return taskList.getTaskById(taskID);
  }

  public TaskList getTaskList()
  {
    return taskList;
  }

  //****************************************************************************

  public void checkStatus()
  {
    if (isDone() && !(status.equals(APPROVED) || status.equals(REJECTED)))
    {
      status = ENDED;
    }
    else if (isDone() && status.equals(REJECTED))
    {
      status = STARTED;
    }
    else if (isDone() && status.equals(APPROVED))
    {
      status = APPROVED;
    }
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Requirement))
    {
      return false;
    }
    Requirement other = (Requirement) obj;
    return this.requirementId.equals(other.requirementId) && this.description
        .equals(other.description) && this.status.equals(other.status)
        && this.estimateTime == other.estimateTime
        && this.priorityNumber == other.priorityNumber && this.deadline
        .equals(other.deadline) && this.responsibleEmployee
        .equals(other.responsibleEmployee) && this.requirementType
        .equals(other.requirementType);
  }

  public String toString()
  {
    //checkStatus();
    return "ID: " + requirementId + " -  Pr: " + priorityNumber;
  }

  public Requirement copy()
  {
    return new Requirement(requirementId, priorityNumber, description,
        estimateTime, status, requirementType, deadline, responsibleEmployee.copy(),
        taskList.copy());
  }
}