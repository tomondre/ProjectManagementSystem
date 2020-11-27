package model;

import java.io.Serializable;
import java.util.ArrayList;

public class RequirementList implements Serializable
{
  private ArrayList<Requirement> requirements;

  public RequirementList()
  {
    requirements = new ArrayList<Requirement>();
  }

  public RequirementList getAllNotApprovedRequirements()
  {
    RequirementList temp = new RequirementList();
    for (Requirement r : requirements)
    {
      if (!r.isApproved())
      {
        temp.addRequirement(r);
      }
    }
    return temp;
  }

  public void addRequirement(Requirement r)
  {
    for (Requirement req : requirements)                  //checking if the requirement is already in the list
    {
      if (req.getID() == r.getID())
      {
        return;
      }
    }
    requirements.add(r);
  }

  public void sortRequirementsByPriorities()
  {

    Requirement temp;
    boolean working = true;

    while (working)
    {
      working = false;
      for (int i = 0; i < requirements.size() - 1; i++)
      {
        if (requirements.get(i).getID() > requirements.get(i
            + 1)                             //change this to sort by priorities not id!
            .getID() && i < requirements.size() - 1)
        {
          temp = requirements.get(i);
          requirements.set(i, requirements.get(i + 1));
          requirements.set(i + 1, temp);
          working = true;
        }
      }
    }
  }

  public Requirement getRequirementByID(int requirementID)
  {
    for (Requirement requirement : requirements)
    {
      if (requirement.getID() == requirementID)
      {
        return requirement;
      }
    }
    return null;
  }

  public void removeRequirement(int requirementID)
  {
    requirements.remove(getRequirementByID(requirementID));
  }

  public String toString()
  {
    String temp = "\n";
    if (requirements.size() != 0)
    {
      for (Requirement r : requirements)
      {
        temp += "    Requirement: " + r + "\n";
      }
    }
    return temp;
  }

  public Task getTaskById(int requirementID, int taskID)
  {
    if (getRequirementByID(requirementID) != null)
    {
      return getRequirementByID(requirementID).getTaskById(taskID);

    }
    return null;
  }

  public RequirementList copy()
  {

    RequirementList temp = new RequirementList();
    for (Requirement r : requirements)
    {
      temp.addRequirement(r.copy());
    }
    return temp;
  }
}


