package FileAdapter;

import model.MyDate;
import model.ProjectManagmentSystem;
import model.Requirement;
import model.Task;

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
  public void addProject(String name)
  {
    ProjectManagmentSystem system = getSystem();
    system.addProject(name);
    save(system);
  }

  //************************************Requirements*********************************
  public void addRequirement(String projectName, Requirement requirement)
  {
    ProjectManagmentSystem system = getSystem();
    system.addRequirement(projectName, requirement);
    save(system);
  }

  //TODO change based on constructor
  public void setRequirement(String projectName, int requirementID,
      String description, double estimatedTime)
  {
    ProjectManagmentSystem system = getSystem();
    system
        .setRequirement(projectName, requirementID, description, estimatedTime);
    save(system);
  }

  //******************************************Tasks******************************************
  public void addTask(String projectName, int requirementID, Task task)
  {
    ProjectManagmentSystem system = getSystem();
    system.addTask(projectName, requirementID, task);
    save(system);
  }

  //TODO change based on constructor
  public void setTask(String projectName, int requirementID, int taskID,
      String description, String status, double estimatedTime, int day,
      int month, int year)
  {
    ProjectManagmentSystem system = getSystem();
    system.setTask(projectName, requirementID, taskID, description, status,
        estimatedTime, new MyDate(day, month, year));
    save(system);
  }

  //**************************************System*****************************************
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
