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
  private double estimateTime;
  private int priorityNumber;
  private MyDate deadline;
  private TaskList taskList;
  private Employee responsibleEmployee;

  public Requirement(int requirementId, int priorityNumber, String description,
      double estimateTime, MyDate deadline, Employee responsibleEmployee)
  {
    this.priorityNumber = priorityNumber;
    this.responsibleEmployee = responsibleEmployee;
    taskList = new TaskList();
    this.requirementId = requirementId;
    this.description = description;
    this.estimateTime = estimateTime;
    this.deadline = deadline;
    status = NOTSTARTED;
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

  public void setApproved()
  {
    status = APPROVED;
  }

  public void setRejected()
  {
    status = REJECTED;
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