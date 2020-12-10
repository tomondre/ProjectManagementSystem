package model;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.Serializable;
import java.util.ArrayList;

public class RequirementList implements Serializable
{
  private ArrayList<Requirement> requirements;

  public RequirementList()
  {
    requirements = new ArrayList<Requirement>();
  }

  public void addRequirement(Requirement r)
  {
    for (Requirement req : requirements)                  //checking if the requirement is already in the list
    {
      if (req.getID().equals(r.getID()))
      {
        return;
      }
    }
    requirements.add(r);
    sortRequirementsByPriorities();
  }

  public ArrayList<Requirement> getAllNotApprovedRequirements()
  {
    ArrayList<Requirement> temp = new ArrayList<>();
    for (Requirement r : requirements)
    {
      if (!r.toBeApproved() && !r.getStatus().equals(Requirement.APPROVED))
      {
        temp.add(r);
      }
    }
    return temp;
  }

  public ArrayList<Requirement> getAllToBeApprovedRequirements()
  {
    ArrayList<Requirement> temp = new ArrayList<>();
    for (Requirement r : requirements)
    {
      if (r.toBeApproved())
      {
        temp.add(r);
      }
    }
    return temp;
  }

  public void sortRequirementsByPriorities()
  {
    requirements.sort((x, y) -> Integer.compare(x.getPriority(), y.getPriority()));
  }

  public Requirement getRequirementByID(String requirementID)
  {
    for (Requirement requirement : requirements)
    {
      if (requirement.getID().equals(requirementID))
      {
        return requirement;
      }
    }
    return null;
  }

  public Requirement get(int index)
  {
    return requirements.get(index);
  }

  public void removeRequirement(String requirementID)
  {
    requirements.remove(getRequirementByID(requirementID));
  }

  public int size()
  {
    return requirements.size();
  }


  public void checkStatus()
  {
    for (Requirement req : requirements)
    {
      req.checkStatus();
    }
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


