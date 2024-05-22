package com.example.iss.domain;

public class Seat extends Entity<Integer>{
    float price;
    int column;
    int row;
    Availability availability;

    @Override
    public String toString() {
        return "Seat{" +
                "price=" + price +
                ", column=" + column +
                ", row=" + row +
                ", availability=" + availability +
                ", id=" + id +
                '}';
    }

    public Seat() {
    }

    public Seat(float price, int column, int row, Availability availability) {
        this.price = price;
        this.column = column;
        this.row = row;
        this.availability = availability;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

}
