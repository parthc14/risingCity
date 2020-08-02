package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class risingCity {

    public static void main(String[] args) throws FileNotFoundException {
        int lineNumber = 0;
        BuildMinHeap buildHeap = new BuildMinHeap();
        BuildingRecords printBuildingHeap = null, output = null;
        ArrayList<String> lineInput = new ArrayList<>();
        ArrayList<int[]> buildingValues = new ArrayList<>();

        RedBlackTree redBlackTree = new RedBlackTree();


        ArrayList<BuildingRecords> emptyNode = new ArrayList<>();
        ArrayList<BuildingRecords> rbtOutput = new ArrayList<>();



        String tempString;
        int time, bNum, totalTime, bNum1, bNum2;
        int executedTime = 0 , globalTime = 0, tempBuildTimer = 0;
        int[] keyValues, nextvalues;



//        File file = new File("/Users/sanketnayak/Desktop/Sample_input1.txt");
//        File file = new File("/Users/sanketnayak/Desktop/Sample_input2.txt");
//        File file = new File("/Users/sanketnayak/Desktop/input_1.txt");
        File file = new File("C:\\Users\\Parth\\Desktop\\ProjectFinalAds\\input.txt");

        Scanner lineInputsOfLine = new Scanner(file);

        //Read lineInputsOfLine commands into a List
        while(lineInputsOfLine.hasNextLine()){
            lineInput.add(lineInputsOfLine.nextLine());
        }

        for (String lineValue : lineInput) {
            keyValues = new int[4];
            tempString = lineValue;
            if (tempString.contains("Insert")) {
                keyValues[0] = Integer.parseInt(tempString.substring(0, tempString.indexOf(":")));
                keyValues[1] = Integer.parseInt(tempString.substring(tempString.indexOf("(") + 1, tempString.indexOf(",")));
                keyValues[2] = Integer.parseInt(tempString.substring(tempString.indexOf(",") + 1, tempString.indexOf(")")));
                keyValues[3] = 1;
                buildingValues.add(keyValues);

            }
            else if (tempString.contains("PrintBuilding") && tempString.contains(",")) {
                keyValues[0] = Integer.parseInt(tempString.substring(0, tempString.indexOf(":")));
                keyValues[1] = Integer.parseInt(tempString.substring(tempString.indexOf("(") + 1, tempString.indexOf(",")));
                keyValues[2] = Integer.parseInt(tempString.substring(tempString.indexOf(",") + 1, tempString.indexOf(")")));
                keyValues[3] = 0;
                buildingValues.add(keyValues);
            }
            else {
                keyValues[0] = Integer.parseInt(tempString.substring(0, tempString.indexOf(":")));
                keyValues[1] = Integer.parseInt(tempString.substring(tempString.indexOf("(") + 1, tempString.indexOf(")")));
                keyValues[2] = -1;
                keyValues[3] = 0;
                buildingValues.add(keyValues);
            }
        }

        while( lineInput.size() > lineNumber || buildHeap.buildingArray[0] != null || tempBuildTimer > 0){

            if(buildingValues.size() > 0 && buildingValues.get(0)[3] == 1 && globalTime == buildingValues.get(0)[0]){
                keyValues = buildingValues.remove(0);
                BuildingRecords buildingRecord = new BuildingRecords(keyValues[1], keyValues[2]);
                buildHeap.insert(buildingRecord);
                redBlackTree.add(buildingRecord);
                lineNumber++;
            }
            else if(buildingValues.size() > 0 && buildingValues.get(0)[3] == 0 && globalTime == buildingValues.get(0)[0]) {
                keyValues = buildingValues.remove(0);
                if (keyValues[2] < 0) {
                    output = RedBlackTree.findNode(RedBlackTree.root, keyValues[1]);
                    if (output == null) {
                        System.out.println("0,0,0");
                    } else {
                        System.out.println(output);
                    }
                } else {
                    emptyNode = new ArrayList<>();
                    rbtOutput = RedBlackTree.searchRangeFunction(emptyNode, RedBlackTree.root, keyValues[1], keyValues[2]);
                    for (int i = rbtOutput.size() - 1; i >= 0; i--) {
                        if (i > 1)
                            System.out.print(rbtOutput.get(i) + ",");
                        else
                            System.out.print(rbtOutput.get(i));
                    }
                    System.out.println();

                }
                lineNumber++;
            }

            if( tempBuildTimer != 0) {
                if (tempBuildTimer == 5) {

                    if (printBuildingHeap.getExecutedTime() + 1 == printBuildingHeap.getTotalTime()) {
                        System.out.println("(" + printBuildingHeap.getBuildingNumber() + "," + globalTime + ")");
                        redBlackTree.removeElement(printBuildingHeap);
                    } else {
                        printBuildingHeap.setExecutedTime(printBuildingHeap.getExecutedTime() + 1);

                        buildHeap.insert(printBuildingHeap);

                    }
                    tempBuildTimer = 0;
                } else if (printBuildingHeap.getExecutedTime() + 1 == printBuildingHeap.getTotalTime()) {
                    System.out.println("(" + printBuildingHeap.getBuildingNumber() + "," + globalTime + ")");

                    redBlackTree.removeElement(printBuildingHeap);
                    tempBuildTimer = 0;
                } else {
                    printBuildingHeap.setExecutedTime(printBuildingHeap.getExecutedTime() + 1);

                    tempBuildTimer++;
                }
            }
            if(tempBuildTimer == 0 && buildHeap.buildingArray[0] != null) {
                printBuildingHeap = buildHeap.removeRoot();
                tempBuildTimer++;
            }

            globalTime++;
        }

        //System.out.println(Arrays.toString(buildHeap.Building_array));

    }
}
