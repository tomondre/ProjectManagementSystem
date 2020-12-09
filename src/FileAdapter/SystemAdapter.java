package FileAdapter;

import com.google.gson.Gson;
import model.*;
import org.json.JSONObject;
import org.json.XML;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SystemAdapter
{
  private MyFileIO mfio;
  private String fileName;
  private MyTextFileIO mtxtfio;

  public SystemAdapter(String fileName)
  {
    mfio = new MyFileIO();
    this.fileName = fileName;
    this.mtxtfio = new MyTextFileIO();
  }

  //*******************************************Projects********************************
  public void addProject(String name, String status)
      throws IllegalArgumentException
  {
    ProjectManagementSystem system = getSystem();
    Project toAdd = new Project(name, status);
    system.getProjectList().addProject(toAdd);
    save(system);
  }

  public void editProject(String newName, String oldName, String status)
      throws IllegalArgumentException
  {
    ProjectManagementSystem system = getSystem();
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
  public void addRequirement(String projectName, String requirementId,
      int priorityNumber, String description, double estimateTime,
      String status, String requirementType, MyDate deadline,
      Employee responsibleEmployee)
  {
    ProjectManagementSystem system = getSystem();
    system.addRequirement(projectName,
        new Requirement(requirementId, priorityNumber, description,
            estimateTime, status, requirementType, deadline,
            responsibleEmployee));
    save(system);
  }

  public void setRequirement(String projectName, String requirementId,
      int priorityNumber, String description, double estimateTime,
      String status, String requirementType, MyDate deadline,
      Employee responsibleEmployee)
  {
    ProjectManagementSystem system = getSystem();
    system
        .setRequirement(projectName, requirementId, priorityNumber, description,
            estimateTime, status, requirementType, deadline,
            responsibleEmployee);
    save(system);
  }

  //TODO remove requirement
  public void removeRequirement(String projectName, String requirementID)
  {
    ProjectManagementSystem system = getSystem();
    system.removeRequirement(projectName, requirementID);
    save(system);
  }

  //******************************************Tasks******************************************
  public void addTask(String projectName, String requirementID, int taskID,
      String description, boolean status, double timeUsed, double estimatedTime,
      MyDate deadline, EmployeeList employees, Employee responsibleEmployee)
  {
    ProjectManagementSystem system = getSystem();
    system.addTask(projectName, requirementID,
        new Task(taskID, description, status, timeUsed, estimatedTime, deadline,
            employees, responsibleEmployee));
    save(system);
  }


  public void setTask(String projectName, String requirementID, int taskID,
      String description, boolean status, double timeUsed, double estimatedTime,
      MyDate deadline, EmployeeList employees, Employee responsibleEmployee)
  {
    ProjectManagementSystem system = getSystem();
    system
        .setTask(projectName, requirementID, taskID, description, status, timeUsed, estimatedTime, deadline,
            employees, responsibleEmployee);
    save(system);
  }

  public void removeTask(String projectName, String requirementID, int taskID)
  {
    ProjectManagementSystem system = getSystem();
    system.removeTask(projectName, requirementID, taskID);
    save(system);
  }

  public void addUsedHoursToTask(String projectName, String requirementID,
      int taskID, double hours)
  {
    ProjectManagementSystem system = getSystem();
    system.addUsedHourToTask(projectName, requirementID, taskID, hours);
    save(system);
  }

  //**************************************File*****************************************
  public ProjectManagementSystem getSystem()
  {
    ProjectManagementSystem system = new ProjectManagementSystem();
    try
    {
      system = (ProjectManagementSystem) mfio.readObjectFromFile(fileName);
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
    ProjectManagementSystem system = getSystem();
    try
    {
      system.getEmployees().addEmployee(employee);
    }
    finally
    {
      save(system);
    }
  }

  public void editEmployee(Employee employee, Employee oldEmployee)
      throws IllegalArgumentException
  {
    ProjectManagementSystem system = getSystem();
    try
    {
      system.getEmployees().editEmployee(employee, oldEmployee);
    }
    finally
    {
      save(system);
    }
  }

  public void save(ProjectManagementSystem system)
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

  public void exportXML()
  {
    Gson json = new Gson();
    try
    {
      mtxtfio.writeToFile("export.xml", XML.toString(
          new JSONObject(json.toJson(getSystem().getAllProjectsOngoing()))));
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
  }

  //Testing Json parser. Output is file .json which we can actually use in javascript. Ecerything works so far
  public static void main(String[] args)
  {
    SystemAdapter systemAdapter = new SystemAdapter("colourIT.bin");
    JSONObject temp = new JSONObject(
        systemAdapter.getSystem().getAllProjectsOngoing());
    String t = temp.toString();
    System.out.println(t);
    System.out.println(systemAdapter.getSystem().getAllProjectsOngoing());
    System.out.println(temp.toString());
    System.out.println(XML.toString(temp));
    systemAdapter.exportXML();
  }
}
