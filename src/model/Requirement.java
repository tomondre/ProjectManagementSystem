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
  private int requirementId;
  private String description;
  private String status;
  private double estimateTime;
  private int priorityNumber;
  private MyDate deadline;
  private TaskList taskList;
  private Employee responsibleEmployee;
  private String requirementType;

  public Requirement(int requirementId, int priorityNumber, String description,
      double estimateTime, String status, String requirementType, MyDate deadline, Employee responsibleEmployee)
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
    this.deadline = deadline;
  }

  //Constructor for copy method
  public Requirement(int requirementId, int priorityNumber, String description,
      double estimateTime, MyDate deadline, Employee responsibleEmployee,
      TaskList taskList, String status)
  {
    this.priorityNumber = priorityNumber;
    this.taskList = taskList;
    this.requirementId = requirementId;
    this.description = description;
    this.estimateTime = estimateTime;
    this.deadline = deadline;
    this.status = status;
    this.responsibleEmployee = responsibleEmployee;
  }

  //******************************SETTERS*****************************************
  public void set(int priorityNumber, String description, double estimateTime,
      MyDate deadline, String status)
  {
    this.priorityNumber = priorityNumber;
    this.description = description;
    this.estimateTime = estimateTime;
    this.deadline = deadline;
    setStatus(status);
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

  public String getStatus()
  {
    return status;
  }

  public double getLeftEstimate()
  {
    return estimateTime - taskList.totalTimeUsed();
  }

  public boolean isApproved()
  {
    return status.equals(APPROVED);
  }

  public double totalTimeUsed()
  {
    return taskList.totalTimeUsed();
  }

  public int getPriority()
  {
    return priorityNumber;
  }

  public int getID()
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
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Requirement))
    {
      return false;
    }
    Requirement other = (Requirement) obj;
    return false;                             //edit this based on constructor
  }

  public String toString()
  {
    checkStatus();
    return "ID: " + requirementId + ", priorityNumber: " + priorityNumber
        + ", description: " + description + ", status: " + status
        + ", estimate time: " + estimateTime + "timeUsed: " + taskList
        .totalTimeUsed() + ", deadline: " + deadline + "responsibleEmployee:"
        + responsibleEmployee +", tasks: " +  taskList;
  }

  //  public String toString()
  //  {
  //    return "ID: " + requirementId + " status: " + status + taskList;
  //  }

  public Requirement copy()
  {
    return new Requirement(requirementId, priorityNumber, description,
        estimateTime, deadline, responsibleEmployee, taskList.copy(), status);
  }
}