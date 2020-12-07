package model;

import java.io.Serializable;
import java.util.ArrayList;

public class EmployeeList implements Serializable
{
  private ArrayList<Employee> employees;

  public EmployeeList()
  {
    employees = new ArrayList<Employee>();
  }

  public EmployeeList(ArrayList<Employee> employees)
  {
    this.employees = employees;
  }

  //TODO compare employeeID in list if it already exist
  public void addEmployee(Employee employee)
  {
    for (Employee e : employees)
    {
      if (e.getId() == employee.getId())
      {
        throw new IllegalArgumentException("Employee already exists.");
      }
    }
    employees.add(employee);
  }

  public void editEmployee(Employee employee, Employee oldEmployee)
  {
    Employee temp = getEmployeeByID(oldEmployee.getId());
    int newID = employee.getId();
    for (Employee empl : employees)
    {
      if (newID == empl.getId() && !empl.equals(temp))
      {
        throw new IllegalArgumentException("Employee ID number already exists");
      }
    }
     temp.setEmployeeID(newID);
     temp.setFirstName(employee.getFirstName());
     temp.setLastName(employee.getLastName());

  }

  public Employee getEmployeeByID(int employeeID)
  {
    for (Employee e : employees)
    {
      if (e.getId() == employeeID)
      {
        return e;
      }
    }
    return null;
  }

  public EmployeeList getNotAssignedEmployees()
  {
    EmployeeList temp = new EmployeeList();
    for (Employee e : employees)
    {
      if (e.getRole().equals(""))
      {
        temp.addEmployee(e);
      }
    }
    return temp;
  }

  public void deleteRoles()
  {
    for (Employee e : employees)
    {
      e.setRole("");
      e.resetTasks();
    }
  }

  public Employee getResponsibleEmployee()
  {
    return employees.get(0);
  }

  public Employee get(int index)
  {
    return employees.get(index);
  }

  public int size()
  {
    return employees.size();
  }

  public EmployeeList copy()
  {
    ArrayList<Employee> temp = new ArrayList<Employee>();
    for (Employee e : employees)
    {
      temp.add(e.copy());
    }
    return new EmployeeList(temp);
  }

  public String toString()
  {
    String str = "";
    for (Employee employee : employees)
    {
      str += employee + "\n";
    }
    return str;
  }

  public String toStringProjectTeam()
  {
    String str = "";
    for (Employee employee : employees)
    {
      str += employee.toStringTeamMember() + "\n";
    }
    return str;
  }
}
