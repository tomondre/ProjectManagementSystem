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

  public RequirementList getAllNotApprovedRequirements()
  {
    ArrayList<Requirement> temp = new ArrayList<>();
    for (Requirement r : requirements)
    {
      if (!r.toBeApproved() && !r.getStatus().equals(Requirement.APPROVED))
      {
        temp.add(r);
      }
    }
    return sortRequirementsByPriorities(temp);
  }

  public RequirementList getAllToBeApprovedRequirements()
  {
    ArrayList<Requirement> temp = new ArrayList<>();
    for (Requirement r : requirements)
    {
      if (r.toBeApproved())
      {
        temp.add(r);
      }
    }
    return sortRequirementsByPriorities(temp);
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
  }

  //TODO fix the sorting
  public RequirementList sortRequirementsByPriorities(
      ArrayList<Requirement> toSort)
  {
    toSort.sort((x, y) -> Integer.compare(x.getPriority(), y.getPriority()));
    /*toSort.sort(
        (x, y) -> String.CASE_INSENSITIVE_ORDER.compare(x.getID(), y.getID()));*/
    RequirementList sorted = new RequirementList();
    for (Requirement req : toSort)
    {
      sorted.addRequirement(req);
    }
    return sorted;
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

  public Task getTaskById(String requirementID, int taskID)
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


