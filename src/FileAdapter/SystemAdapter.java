package FileAdapter;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SystemAdapter
{
  private MyFileIO mfio;
  private String fileName;

  //TODO we will also have to  make methods for exporting Lists

  public SystemAdapter(String fileName)
  {
    mfio = new MyFileIO();
    this.fileName = fileName;

  }

  //*******************************************Projects********************************
  public void addProject(String name, String status)
  {
    ProjectManagmentSystem system = getSystem();
    Project toAdd = new Project(name, status);
    system.getProjectList().addProject(toAdd);
    save(system);
  }

  public void editProject(String newName, String oldName, String status) throws IllegalArgumentException
  {
    ProjectManagmentSystem system = getSystem();
    try
    {
      system.getProjectList().editProject(newName, oldName, status);
    }
    finally
    {
      save(system);
    }
  }

  //************************************Requirements*********************************
  public void addRequirement(String projectName, int requirementId,
      int priorityNumber, String description, double estimateTime, int day,
      int month, int year, int employeeID)
  {
    ProjectManagmentSystem system = getSystem();
    system.addRequirement(projectName,
        new Requirement(requirementId, priorityNumber, description,
            estimateTime, new MyDate(day, month, year),
            system.getEmployee(employeeID)));
    save(system);
  }

  //TODO change based on constructor
  public void setRequirement(String projectName, int requirementId,
      int priorityNumber, String description, double estimateTime, int day,
      int month, int year, String status)
  {
    ProjectManagmentSystem system = getSystem();
    system
        .setRequirement(projectName, requirementId, priorityNumber, description,
            estimateTime, new MyDate(day, month, year), status);
    save(system);
  }

  public void setRequirementApproved(String projectName, int requirementID)
  {
    ProjectManagmentSystem system = getSystem();
    system.setRequirementApproved(projectName, requirementID);
    save(system);
  }

  public void setRequirementRejected(String projectName, int requirementID)
  {
    ProjectManagmentSystem system = getSystem();
    system.setRequirementRejected(projectName, requirementID);
    save(system);
  }

  public void removeRequirement(String projectName, int requirementID)
  {
    ProjectManagmentSystem system = getSystem();
    system.removeRequirement(projectName, requirementID);
    save(system);
  }

  //******************************************Tasks******************************************
  public void addTask(String projectName, int requirementID, int taskID,
      String description, double estimatedTime, int day, int month, int year,
      int responsibleEmployeeID)
  {
    ProjectManagmentSystem system = getSystem();
    system.addTask(projectName, requirementID,
        new Task(taskID, description, estimatedTime,
            new MyDate(day, month, year),
            system.getEmployee(responsibleEmployeeID)));
    save(system);
  }

  //TODO change based on constructor
  public void setTask(String projectName, int requirementID, int taskID,
      String description, double estimatedTime, double timeUsed, boolean isDone,
      int day, int month, int year)
  {
    ProjectManagmentSystem system = getSystem();
    system
        .setTask(projectName, requirementID, taskID, description, estimatedTime,
            new MyDate(day, month, year), timeUsed, isDone);
    save(system);
  }

  public void removeTask(String projectName, int requirementID, int taskID)
  {
    ProjectManagmentSystem system = getSystem();
    system.removeTask(projectName, requirementID, taskID);
    save(system);
  }

  public void addUsedHoursToTask(String projectName, int requirementID,
      int taskID, double hours)
  {
    ProjectManagmentSystem system = getSystem();
    system.addUsedHourToTask(projectName, requirementID, taskID, hours);
    save(system);
  }

  //**************************************File*****************************************
  public ProjectManagmentSystem getSystem()
  {
    ProjectManagmentSystem system = new ProjectManagmentSystem();
    try
    {
      system = (ProjectManagmentSystem) mfio.readObjectFromFile(fileName);
    }

    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
    catch (IOException e)
    {
      System.out.println("IO Error reading file");
    }
    catch (ClassNotFoundException e)
    {
      System.out.println("Class Not Found");
    }
    return system;
  }

  //**************************************Employee*****************************************
  public void addEmployee(Employee employee)
  {
    ProjectManagmentSystem system = getSystem();
    try
    {
      system.getEmployees().addEmployee(employee);
    }
    finally
    {
      save(system);
    }
  }

  public void editEmployee(Employee employee, Employee oldEmployee) throws IllegalArgumentException
  {
    ProjectManagmentSystem system = getSystem();
    try
    {
      system.getEmployees().editEmployee(employee, oldEmployee);
    }
    finally
    {
      save(system);
    }
  }

  public void save(ProjectManagmentSystem system)
  {
    try
    {
      mfio.writeToFile(fileName, system);
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
    catch (IOException e)
    {
      System.out.println("IO Error writing to file");
    }
  }
}
