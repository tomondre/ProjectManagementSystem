package model;

import java.io.Serializable;

public class Requirement implements Serializable
{
  private final String APPROVED = "Approved";                           //we have to decide which system of comparing Strings (roles and statuses) we want to use
  private final String REJECTED = "Rejected";
  private final String STARTED = "Started";
  private final String NOTSTARTED = "Not started";
  private final String ENDED = "Ended";
  private int requirementId;
  private String description;
  private String status;
  private boolean functional;
  private double estimateTime;
  private int priorityNumber;
  private MyDate deadline;
  private TaskList taskList;
  private Employee responsibleEmployee;

  //TODO add MyDate deadline, boolean  and also responsible team member
  public Requirement(int requirementId, String description, double estimateTime,
      Employee responsibleEmployee)
  {
    this.responsibleEmployee = responsibleEmployee;
    taskList = new TaskList();
    this.requirementId = requirementId;
    this.description = description;
    this.estimateTime = estimateTime;
    this.deadline = deadline;
    this.functional = functional;
    status = STARTED;
    this.deadline = deadline;
  }

  public Requirement(int requirementId, int priorityNumber, String description,
      double estimateTime, TaskList taskList, MyDate deadline,
      boolean functional, String status)
  {
    this.priorityNumber = priorityNumber;
    this.taskList = taskList;
    this.requirementId = requirementId;
    this.description = description;
    this.estimateTime = estimateTime;
    this.deadline = deadline;
    this.functional = functional;
    this.status = status;

  }

  //******************************SETTERS*****************************************
  public void set(String description,
      double estimateTime)              //change to match construcor, without unique requirementID

  {
    this.description = description;
    this.estimateTime = estimateTime;
  }

  public void setApproved()
  {
    status = APPROVED;
  }

  public void setRejected()
  {
    status = REJECTED;
  }

  public int getID()
  {
    return requirementId;
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

  public void setStatus(String status)
  {
    if (status.charAt(0) == 'A' || status.charAt(0) == 'a')
    {
      this.status = APPROVED;
    }
    else if (status.charAt(0) == 'N' || status.charAt(0) == 'n')
    {
      this.status = NOTSTARTED;
    }
    else if (status.toLowerCase().charAt(0) == 's')
    {
      this.status = STARTED;
    }
    else if (status.charAt(0) == 'E' || status.charAt(0) == 'e')
    {
      this.status = ENDED;
    }
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

  public void setPriority(int priority)
  {
    priorityNumber = priority;
  }

  //********************************taskList*****************************************
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
    return "ID: " + requirementId + " status: " + status + taskList;
  }

  public Requirement copy()
  {
    return new Requirement(requirementId, priorityNumber, description,
        estimateTime, taskList.copy(), deadline, functional, status);
  }

}