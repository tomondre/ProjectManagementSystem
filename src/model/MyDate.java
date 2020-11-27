package model;

import java.io.Serializable;

public class MyDate implements Serializable
{
  private int day, month, year;

  public MyDate(int day, int month, int year)
  {
    this.day = day;
    this.month = month;
    this.year = year;
  }

  public String toString()
  {
    return day + "." + month + "." + year;
  }
}
