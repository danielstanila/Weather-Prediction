package model;

public class Date implements Comparable<Date> {

    private Integer day;
    private Integer month;
    private Integer year;

    public Date(Integer day, Integer month, Integer year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    /*@Override
    public int compareTo(Date o) {
        if (this.year == o.year) {
            if (this.month == o.month) {
                if (this.day == o.day) {
                    return 0;
                } else {
                    return this.day.compareTo(o.day);
                }
            } else {
                return this.month.compareTo(o.month);
            }
        } else {
            return this.year.compareTo(o.year);
        }
    }*/

    @Override
    public int compareTo(Date o) {
        int result = this.year.compareTo(o.year);
        if (result == 0) {
            result = this.month.compareTo(o.month);
            if (result == 0) {
                result = this.day.compareTo(o.day);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Date date = (Date) o;

        if (day != null ? !day.equals(date.day) : date.day != null) return false;
        if (month != null ? !month.equals(date.month) : date.month != null) return false;
        return year != null ? year.equals(date.year) : date.year == null;

    }

    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Date{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
