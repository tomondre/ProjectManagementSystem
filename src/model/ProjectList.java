package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProjectList implements Serializable
{
  private ArrayList<Project> projects;

  public ProjectList()
  {
    projects = new ArrayList<Project>();
  }

  public void addProject(Project project)
  {
    for (Project p : projects)
    {
      if (p.getName().equals(project.getName()))
      {
        throw new IllegalArgumentException("Project already exists");
      }
    }
    projects.add(project);
  }

  public Project getProjectByName(String projectName)
  {
    for (Project p : projects)
    {
      if (p.getName().equals(projectName))
      {
        return p;
      }
    }
    return null;
  }

  public void editProject(String newName, String oldName, String status)
  {
    Project temp = getProjectByName(oldName);

    if (newName.equals(oldName))
    {
      temp.setStatus(status);
    }
    else if (getProjectByName(newName) == null)
    {
      temp.setStatus(status);
      temp.setProjectName(newName);
    }
    else
    {
      throw new IllegalArgumentException("Project already exists");
    }
  }

  public int size()
  {
    return projects.size();
  }

  public void remove(Project project)
  {
    projects.remove(project);
  }

  public Project get(int index)
  {
    return projects.get(index);
  }

  public void exportingXML(){
    for (Project project : projects){
      project.deleteTeamRoles();
    }
  }

  public String toString()
  {
    String temp = "";
    for (Project p : projects)
    {
      temp += p + "\n";
    }
    return temp;
  }
}
