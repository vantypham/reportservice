package com.example.dto;


public class ReportDataObject {
    private String id;
    private double value;
    private long timestamp;

    public ReportDataObject(String id, double value, long timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ReportDataObject{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}
