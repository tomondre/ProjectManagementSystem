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
    system.getAllProjectsOngoing().addProject(toAdd);
    save(system);
  }

  public void editProject(String newName, String oldName, String status)
      throws IllegalArgumentException
  {
    ProjectManagementSystem system = getSystem();
    try
    {
      system.getAllProjectsOngoing().editProject(newName, oldName, status);
    }
    finally
    {
      save(system);
    }
  }

  public void moveProjectToArchive(String projectName)
  {
    ProjectManagementSystem system = getSystem();
    system.moveToArchive(projectName);
    save(system);
  }
  //************************************Requirements*********************************
  public void addRequirement(String projectName, String requirementId,
      int priorityNumber, String description, double estimateTime,
      String status, String requirementType, MyDate deadline,
      Employee responsibleEmployee)
  {
    ProjectManagementSystem system = getSystem();
    system.getAllProjectsOngoing().getProjectByName(projectName).addRequirement(
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
    system.getRequirementByID(projectName, requirementId)
        .set(priorityNumber, description, estimateTime, deadline, status,
            requirementType, responsibleEmployee);
    save(system);
  }

  public void removeRequirement(String projectName, String requirementID)
  {
    ProjectManagementSystem system = getSystem();
    system.getAllProjectsOngoing().getProjectByName(projectName)
        .getAllRequirements().removeRequirement(requirementID);
    save(system);
  }

  public void checkRequirementsStatus(String projectName)
  {
    ProjectManagementSystem system = getSystem();
    system.getAllRequirements(projectName).checkStatus();
    save(system);
  }

  //******************************************Tasks******************************************
  public void addTask(String projectName, String requirementID, Task task)
  {
    ProjectManagementSystem system = getSystem();
    system.getRequirementByID(projectName, requirementID).addTask(task);
    save(system);
  }

  public void setTask(String projectName, String requirementID, int taskID,
      String description, boolean status, double timeUsed, double estimatedTime,
      MyDate deadline, EmployeeList employees, Employee responsibleEmployee)
  {
    ProjectManagementSystem system = getSystem();
    system.getAllTasks(projectName, requirementID).getTaskById(taskID)
        .set(description, status, timeUsed, estimatedTime, deadline, employees,
            responsibleEmployee);
    save(system);
  }

  public void removeTask(String projectName, String requirementID, int taskID)
  {
    ProjectManagementSystem system = getSystem();
    system.getRequirementByID(projectName, requirementID).removeTask(taskID);
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
      system.getAllEmployees().addEmployee(employee);
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
      system.getAllEmployees().editEmployee(employee, oldEmployee);
    }
    finally
    {
      save(system);
    }
  }

  public void addEmployeeToProject(String projectName, Employee employee,
      String role)
  {
    ProjectManagementSystem system = getSystem();
    system.getAllProjectsOngoing().getProjectByName(projectName)
        .addTeamMember(employee, role);
    system.getAllEmployees().getEmployeeByID(employee.getId()).setRole(role);
    save(system);
  }

  public void addTaskToEmployee(Employee employee, Task task)
  {
    ProjectManagementSystem system = getSystem();
    system.getAllEmployees().getEmployeeByID(employee.getId()).addTask(task);
    save(system);
  }

  public void removeTaskFromEmployee(Employee employee, Task task)
  {
    ProjectManagementSystem system = getSystem();
    system.getAllEmployees().getEmployeeByID(employee.getId()).removeTask(task);
    save(system);
  }

  public void changeRoleEmployee(String projectName, Employee employee, String role)
  {
    ProjectManagementSystem system = getSystem();
    system.getAllProjectsOngoing().getProjectByName(projectName).editRoleTeamMember(employee, role);
    save(system);
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
    ProjectList tempProjects = getSystem().getAllProjectsOngoing();
    tempProjects.exportingXML();
    String temp = XML.toString(
        new JSONObject(json.toJson(tempProjects)));
    temp= "<ongoingProjects>" + temp;
    temp+="</ongoingProjects>";
    try
    {
      mtxtfio.writeToFile("export.xml", temp);
    }
    catch (FileNotFoundException e)
    {
      System.out.println("File not found");
    }
  }
}
