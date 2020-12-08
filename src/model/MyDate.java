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

  public boolean equals(Object obj)
  {
    if (!(obj instanceof MyDate))
      return false;
    MyDate other = (MyDate)obj;
    return this.day == other.day
        && this.month == other.month
        && this.year == other.year;
  }
  public String toString()
  {
    return day + "/" + month + "/" + year;
  }
}
