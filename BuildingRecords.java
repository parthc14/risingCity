package com.company;

public class BuildingRecords {          // Class to maintain building records
    private int buildingNumber;
    private int executedTime;
    private int totalTime;
    //private BuildHeap bHeap;


    public BuildingRecords(int buildingNumber, int totalTime) {  // Constructor Declaration for BuildingRecords class
        this.buildingNumber = buildingNumber;
        this.executedTime = 0;
        this.totalTime = totalTime;
        //this.bHeap = bHeap;
        //bHeap.insert(this);
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }          // Getters for buildingNumber

    public int getExecutedTime() {
        return executedTime;
    }              // Getter for executedTime

    public int getTotalTime() {
        return totalTime;
    }                   // getter for TotalTime


    public void setExecutedTime(int executedTime) {         // Setter for setting ExecutedTme
        this.executedTime = executedTime;
    }

    @Override
    public String toString() {                          //Method that converts output to String format required
        return "(" + buildingNumber + "," + (executedTime) + "," + totalTime + ")";
    }
}
