package com.company;

public class BuildMinHeap {                                               // Class for building heap
    private static final int FRONTELEMENT = 0;
    BuildingRecords[] buildingArray;                                        // Initialize buildingRecordsArray
    private int heapSize;

    public BuildMinHeap() {                                                  //Constructor
        this.heapSize = -1;
        this.buildingArray = new BuildingRecords[2000];
    }

    private int leftNode(int index) {
        return (index * 2) + 1;
    }                   //Method to check LeftNode

    private int rightNode(int index) {
        return (index * 2) + 2;
    }               // Method to check RightNode

    private int parentNode(int index) {
        return (index - 1) / 2;
    }               //Method to check Parent

    private void swap(int index1, int index2) {                                // Method to swap two index
        BuildingRecords temporary = buildingArray[index1];
        buildingArray[index1] = buildingArray[index2];
        buildingArray[index2] = temporary;
    }

    private boolean isLeafNode(int index) {                                     // Method to check LeafNode
        if (index >= buildingArray.length / 2 && index <= buildingArray.length)
            return true;
        else
            return false;
    }

    public void Heapify(int index) {                         // not handled the case where executed times are equal
        if (!isLeafNode(index)) {
            if ((buildingArray[leftNode(index)] != null &&
                    buildComparator(buildingArray[leftNode(index)], index))
                    || (buildingArray[rightNode(index)] != null &&
                    buildComparator(buildingArray[rightNode(index)], index))
            ) {
                if (buildingArray[leftNode(index)] == null) {
                    swap(index, rightNode(index));
                    Heapify(rightNode(index));
                } else if (buildingArray[rightNode(index)] == null) {
                    swap(index, leftNode(index));
                    Heapify(leftNode(index));
                }
                else {
                    if (buildComparator(buildingArray[leftNode(index)], rightNode(index))) { // Call to comparator function
                        swap(index, leftNode(index));
                        Heapify(leftNode(index));
                    }
                    else {
                        swap(index, rightNode(index));
                        Heapify(rightNode(index));
                    }
                }
            }
        }
    }

    public boolean buildComparator(BuildingRecords buildingRecordObject, int index) {  // Method to create a comparator to compare objects
        if (buildingArray[index].getExecutedTime() > buildingRecordObject.getExecutedTime())
            return true;
        if (buildingArray[index].getExecutedTime() < buildingRecordObject.getExecutedTime())
            return false;
        if (buildingArray[index].getBuildingNumber() < buildingRecordObject.getBuildingNumber())
            return false;
        return true;
    }

    public void insert(BuildingRecords record) {           // Method to pass BuildingRecord as an object into heap

        buildingArray[++heapSize] = record;
        int current = heapSize;

        while (current > 0 && buildComparator(buildingArray[current], parentNode(current))) {
            swap(current, parentNode(current));
            current = parentNode(current);
        }
    }

    public void minHeap() {
        for (int i = (buildingArray.length - 1) / 2; i >= 1; i--) {
            Heapify(i);
        }
    }

    public BuildingRecords removeRoot() {                                                  // Method to extract min
        //
        BuildingRecords buildingRecordObject = null;
        if (buildingArray[0] != null) {
            buildingRecordObject = buildingArray[FRONTELEMENT];
            buildingArray[FRONTELEMENT] = buildingArray[heapSize];
            buildingArray[heapSize--] = null;
            Heapify(FRONTELEMENT);
        }

        return buildingRecordObject;
    }

}
