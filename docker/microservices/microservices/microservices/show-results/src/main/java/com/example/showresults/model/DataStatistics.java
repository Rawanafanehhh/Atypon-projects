package com.example.showresults.model;

public class DataStatistics {
    private double min;
    private double max;
    private double avg;

    public DataStatistics() {
    }

    public DataStatistics(double min, double max, double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }
    @Override
    public String toString(){
        return"Minimum data: "+min +"\ndata: "+max +"\nAverage: "+avg;
    }
}