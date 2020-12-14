package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class of containing list of Employee objects
 */
public class EmployeeList implements Serializable
{
  private ArrayList<Employee> employees;

  /**
   * No-argument constructor initializing the EmployeeList
   */
  public EmployeeList()
  {
    employees = new ArrayList<Employee>();
  }

  /**
   * Constructor used in copy method
   * @param employees the list of Employee objects
   */
  public EmployeeList(ArrayList<Employee> employees)
  {
    this.employees = employees;
  }

  /**
   * Adds an Employee object to the list if there is no other existing Employee with the same ID
   * @param employee
   */
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

  /**
   * Edits a fields inside Employee object
   * @param employee the Employee object to be edited
   * @param oldEmployee the Employee with the new field informations
   */
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

  /**
   * Gets an Employee object with the given ID
   * @param employeeID the ID of the Employee object to be returned
   * @return the Employee object with the given ID
   */
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

  /**
   * Gets a EmployeeList object with not assigned Employee objects to Project
   * @return the list of not assigned  Employee objects
   */
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

  /**
   * Deletes roles of the Employee objects inside the listi
   * @param employees
   */
  public void employeesDeleteRoles(EmployeeList employees)
  {
    for (int i = 0; i < this.employees.size(); i++)
    {
      for (int j = 0; j < employees.size(); j++)
      {
        if (this.employees.get(i).getId() == employees.get(j).getId())
        {
          this.employees.get(i).setRole("");
        }
      }
    }
  }

  public void deleteRoles()
  {
    for (Employee e : employees)
    {
      e.setRole("");
      e.resetTasks();
    }
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

  /**
   * Get a String representation of EmployeeList
   * @return a String containing information about all Task objects in the list - each Employee object followed by a new line character
   */
  public String toString()
  {
    String str = "";
    for (Employee employee : employees)
    {
      str += employee + "\n";
    }
    return str;
  }
}
