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
        System.out.println("Project already exist");
        return;
      }
    }
    projects.add(project);
  }

  //TODO return deafult project?
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

  public String toString()
  {
    String temp ="";
    for (Project p : projects)
    {
      temp += p + "\n";
    }
    return temp;
  }

}
