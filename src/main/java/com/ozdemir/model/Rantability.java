package com.ozdemir.model;

public class Rantability {
    private int projectId;
    private double projectPrice;
    private double totalEmployeeCost;
    private double rantability;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public double getProjectPrice() {
        return projectPrice;
    }

    public void setProjectPrice(double projectPrice) {
        this.projectPrice = projectPrice;
    }

    public double getTotalEmployeeCost() {
        return totalEmployeeCost;
    }

    public void setTotalEmployeeCost(double totalEmployeeCost) {
        this.totalEmployeeCost = totalEmployeeCost;
    }

    public double getRantability() {
        return rantability;
    }

    public void setRantability(double rantability) {
        this.rantability = rantability;
    }

    @Override
    public String toString() {
        return "Rantability{" +
                "projectId=" + projectId +
                ", projectPrice=" + projectPrice +
                ", totalEmployeeCost=" + totalEmployeeCost +
                ", rantability=" + rantability +
                '}';
    }
}
