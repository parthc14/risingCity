package com.company;

import java.util.ArrayList;

public class RedBlackTree {
    static class Node {
        BuildingRecords buildingRecordObject; // data value
        Node leftChild; // pointer to left child
        Node rightChild; // pointer to right child.
        Node parentNode; // pointer to parent node.
        String nodeColour; // color of the node.

        public Node(BuildingRecords data, String nodeColour) {
            this.buildingRecordObject = data;
            this.leftChild = null;
            this.rightChild = null;
            this.parentNode = null;
            this.nodeColour = nodeColour;
        }
    }

    static Node root; // root of the tree.

    // function to create a new Node.
    public Node createNewNode(BuildingRecords buildingRecordObject) {
        Node node = new Node(buildingRecordObject, "R");
        node.leftChild = new Node(null, "B");
        node.rightChild = new Node(null, "B");
        return node;
    }

    // function to add elements in the tree.
    public boolean add(BuildingRecords buildingRecordObject) {
        Node node = createNewNode(buildingRecordObject);
        if (root == null) {
            root = node;
            root.nodeColour = "B";
            return true;
        }

        Node temporaryNode = root;
        while (temporaryNode != null) {
            if (temporaryNode.buildingRecordObject.getBuildingNumber() == buildingRecordObject.getBuildingNumber()) {
                return false;
            }
            if (temporaryNode.buildingRecordObject.getBuildingNumber() > buildingRecordObject.getBuildingNumber()) {
                if (temporaryNode.leftChild.buildingRecordObject == null) {
                    temporaryNode.leftChild = node;
                    node.parentNode = temporaryNode;
                    balance(node); // balance the tree.
                    return true;
                }
                temporaryNode = temporaryNode.leftChild;
                continue;
            }
            if (temporaryNode.buildingRecordObject.getBuildingNumber() < buildingRecordObject.getBuildingNumber()) {
                if (temporaryNode.rightChild.buildingRecordObject == null) {
                    temporaryNode.rightChild = node;
                    node.parentNode = temporaryNode;
                    balance(node); // balance the tree
                    return true;
                }
                temporaryNode = temporaryNode.rightChild;
            }
        }
        return true;
    }

    // function to remove elements from the tree.
    public void removeElement(BuildingRecords data) {
        if (root == null) {
            return;
        }

        // search for the given element in the tree.
        Node temporaryElement = root;
        while (temporaryElement.buildingRecordObject != null) {
            if (temporaryElement.buildingRecordObject == data) {
                break;
            }
            if (temporaryElement.buildingRecordObject.getBuildingNumber() >= data.getBuildingNumber()) {
                temporaryElement = temporaryElement.leftChild;
                continue;
            }
            if (temporaryElement.buildingRecordObject.getBuildingNumber() < data.getBuildingNumber()) {
                temporaryElement = temporaryElement.rightChild;
                continue;
            }
        }

        // if not found. then return.
        if (temporaryElement.buildingRecordObject == null) {
            return;
        }

        // find the next greater number than the given data
        Node next = findNext(temporaryElement);

        // swap the data values of given node and next greater number
        BuildingRecords temp = temporaryElement.buildingRecordObject;
        temporaryElement.buildingRecordObject = next.buildingRecordObject;
        next.buildingRecordObject = temp;

        // remove the next node
        Node parent = next.parentNode;
        if (parent == null) {
            if (next.leftChild.buildingRecordObject == null) {
                root = null;
            } else {
                root = next.leftChild;
                next.parentNode = null;
                root.nodeColour = "B";
            }
            return;
        }

        if (parent.rightChild == next) {
            parent.rightChild = next.leftChild;
        } else {
            parent.leftChild = next.leftChild;
        }
        next.leftChild.parentNode = parent;
        String color = next.leftChild.nodeColour + next.nodeColour;
        balance(next.leftChild, color); // balance the tree.
    }

    // function to balance the tree after DELETION
    private static void balance(Node node, String color) {
        // if node is Red-Black.
        if (node.parentNode == null || color.equals("BR") || color.equals("RB")) {
            node.nodeColour = "B";
            return;
        }

        Node parent = node.parentNode;

        // get siblingNode of the given node.
        Node siblingNode = null;
        if (parent.leftChild == node) {
            siblingNode = parent.rightChild;
        } else {
            siblingNode = parent.leftChild;
        }

        Node leftSibling = siblingNode.leftChild; // siblingNode's left node.
        Node rightSibling = siblingNode.rightChild; // siblings right node.

        if (leftSibling == null && rightSibling == null) {
            return;
        }

        // siblingNode leftSibling and rightSibling all are balck.
        if (siblingNode.nodeColour == "B" && leftSibling.nodeColour == "B" && rightSibling.nodeColour == "B") {
            siblingNode.nodeColour = "R";
            node.nodeColour = "B";
            String c = parent.nodeColour + "B";
            balance(parent, c);
            return;
        }

        // siblingNode is red.
        if (siblingNode.nodeColour == "R") {
            if (parent.rightChild == siblingNode) {
                leftRotation(siblingNode);
            } else {
                rightRotation(siblingNode);
            }
            balance(node, color);
            return;
        }

        // siblingNode is red but one of its children are red.
        if (leftSibling == null) {
            return;
        }
        if (parent.leftChild == siblingNode) {
            if (leftSibling.nodeColour == "R") {
                rightRotation(siblingNode);
                leftSibling.nodeColour = "B";
            } else {
                leftRotation(rightSibling);
                rightRotation(rightSibling);
                rightSibling.leftChild.nodeColour = "B";
            }
            return;
        } else {
            if (rightSibling.nodeColour == "R") {
                leftRotation(siblingNode);
                rightSibling.nodeColour = "B";
            } else {
                rightRotation(leftSibling);
                leftRotation(leftSibling);
                leftSibling.rightChild.nodeColour = "B";
            }
            return;
        }
    }

    // function to find the next greater element than the given node.
    private static Node findNext(Node node) {
        Node nextGreaterNode = node.rightChild;
        if (nextGreaterNode.buildingRecordObject == null) {
            return node;
        }
        while (nextGreaterNode.leftChild.buildingRecordObject != null) {
            nextGreaterNode = nextGreaterNode.leftChild;
        }
        return nextGreaterNode;
    }

    // function to balance the tree IN CASE OF INSERTION
    public static void balance(Node node) {
//		System.out.println("Balancing Node : " + node.data);

        // if given node is root node.
        if (node.parentNode == null) {
            root = node;
            root.nodeColour = "B";
            return;
        }

        // if node's parent color is black.
        if (node.parentNode.nodeColour == "B") {
            return;
        }

        // get the node's parent's sibling node.
        Node sibling = null;
        if (node.parentNode.parentNode.leftChild == node.parentNode) {
            sibling = node.parentNode.parentNode.rightChild;
        } else {
            sibling = node.parentNode.parentNode.leftChild;
        }

        // if sibling color is red.
        if (sibling.nodeColour == "R") {
            node.parentNode.nodeColour = "B";
            sibling.nodeColour = "B";
            node.parentNode.parentNode.nodeColour = "R";
            balance(node.parentNode.parentNode);
            return;
        }

        // if sibling color is black.
        else {
            if (node.parentNode.leftChild == node && node.parentNode.parentNode.leftChild == node.parentNode) {
                rightRotation(node.parentNode);
                balance(node.parentNode);
                return;
            }
            if (node.parentNode.rightChild == node && node.parentNode.parentNode.rightChild == node.parentNode) {
                leftRotation(node.parentNode);
                balance(node.parentNode);
                return;
            }
            if (node.parentNode.rightChild == node && node.parentNode.parentNode.leftChild == node.parentNode) {
                leftRotation(node);
                rightRotation(node);
                balance(node);
                return;
            }
            if (node.parentNode.leftChild == node && node.parentNode.parentNode.rightChild == node.parentNode) {
                rightRotation(node);
                leftRotation(node);
                balance(node);
                return;
            }
        }
    }

    // function to perform Left Rotation.
    private static void leftRotation(Node node) {       // Function to perform leftRotation
        Node parent = node.parentNode;
        Node left = node.leftChild;
        node.leftChild = parent;
        parent.rightChild = left;
        if (left != null) {
            left.parentNode = parent;
        }
        String parentColour = parent.nodeColour;
        parent.nodeColour = node.nodeColour;
        node.nodeColour = parentColour;
        Node grandParent = parent.parentNode;
        parent.parentNode = node;
        node.parentNode = grandParent;

        if (grandParent == null) {
            root = node;
            return;
        } else {
            if (grandParent.leftChild == parent) {
                grandParent.leftChild = node;
            } else {
                grandParent.rightChild = node;
            }
        }
    }

    // function to perform Right Rotation.
    private static void rightRotation(Node node) {            // Function to perform rightRotation
        Node parent = node.parentNode;
        Node right = node.rightChild;
        node.rightChild = parent;
        parent.leftChild = right;
        if (right != null) {
            right.parentNode = parent;
        }
        String parentColour = parent.nodeColour;
        parent.nodeColour = node.nodeColour;
        node.nodeColour = parentColour;
        Node grandParent = parent.parentNode;
        parent.parentNode = node;
        node.parentNode = grandParent;

        if (grandParent == null) {
            root = node;
            return;
        } else {
            if (grandParent.leftChild == parent) {
                grandParent.leftChild = node;
            } else {
                grandParent.rightChild = node;
            }
        }
    }

    public static BuildingRecords findNode(Node node, int buildingNumber) {  // finds a node with the buildingNumber
        if (node == null || node.buildingRecordObject == null) {
            return null;
        }
        if (node.buildingRecordObject.getBuildingNumber() == buildingNumber) {
            return node.buildingRecordObject;
        } else if (node.buildingRecordObject.getBuildingNumber() < buildingNumber) {
            return findNode(node.rightChild, buildingNumber);
        } else if (node.buildingRecordObject.getBuildingNumber() > buildingNumber) {
            return findNode(node.leftChild, buildingNumber);
        }
        return null;
    }
// recursive function to compute BuildingRecords, node, startBuilding, endBuilding
    public static ArrayList<BuildingRecords> searchRangeFunction(ArrayList<BuildingRecords> list, Node node, int startBuilding, int endBuilding) {
        if (node == null || node.buildingRecordObject == null) {
            return list;
        }
        if (inRangeFunction(node.buildingRecordObject.getBuildingNumber(), startBuilding, endBuilding)) {
            list.add(node.buildingRecordObject);
        }
        if (node.buildingRecordObject.getBuildingNumber() > startBuilding) {
            searchRangeFunction(list, node.leftChild, startBuilding, endBuilding);
        }
        if (node.buildingRecordObject.getBuildingNumber() < endBuilding) {
            searchRangeFunction(list, node.rightChild, startBuilding, endBuilding);
        }
        return list;
    }

    private static boolean inRangeFunction(int buildingNum, int startBuildNum, int endBuildNum) { // boolean to compute whether in range or not
        if (buildingNum >= startBuildNum && buildingNum <= endBuildNum)
            return true;
        return false;
    }
}
