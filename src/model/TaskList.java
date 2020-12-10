package model;

import java.io.Serializable;
import java.util.ArrayList;

public class TaskList implements Serializable
{
  private ArrayList<Task> tasks;

  public TaskList()
  {
    tasks = new ArrayList<Task>();
  }

  public TaskList(ArrayList<Task> tasks)
  {
    this.tasks = tasks;
  }

  public double totalTimeUsed()
  {
    double temp = 0;
    for (Task task : tasks)
    {
      temp += task.getTimeUsed();
    }
    return temp;
  }

  public boolean isDone()
  {
    if (tasks.size() == 0)
    {
      return false;
    }
    for (Task task : tasks)
    {
      if (!task.isDone())
      {
        return false;
      }
    }
    return true;
  }

  public double totalEstimate()
  {
    double temp = 0;
    for (Task task : tasks)
    {
      temp += task.getEstimateTime();
    }
    return temp;
  }

  public void addTask(Task task)
  {
    for (Task tsk : tasks)
    {
      if (tsk.getID() == task.getID())
      {
        return;
      }
    }
    tasks.add(task);
  }

  public Task getTaskById(int taskID)
  {
    for (Task task : tasks)
    {
      if (task.getID() == taskID)
      {
        return task;
      }
    }
    return null;
  }

  public void removeTask(int taskID)
  {
    tasks.remove(getTaskById(taskID));
  }

  public TaskList copy()
  {
    ArrayList<Task> temp = new ArrayList<Task>();
    for (Task task : tasks)
    {
      temp.add(task.copy());
    }
    return new TaskList(temp);
  }

  public int size()
  {
    return tasks.size();
  }

  public Task get(int index)
  {
    return tasks.get(index);
  }

  //Method we are using when clearing employees working on tasks because we also have tasklist in employee
  public void clear()
  {
    tasks.clear();
  }

  public String toString()
  {
    String temp = "";
    for (Task task : tasks)
    {
      temp += "Task: " + task + "\n";
    }
    return temp;
  }
}
