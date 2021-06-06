package com.shpp.p2p.cs.tperun.assignment17.Huffman;

import com.shpp.p2p.cs.tperun.assignment17.PrivateLinkedList;

import java.util.LinkedList;

/* The class that defines a HuffmanTree, its creation, some methods
 * for working with binary trees.
 */
public class EncodingTreeNode implements Comparable<EncodingTreeNode> {

    /* The instance variables. */
    Byte value;
    int size;
    EncodingTreeNode leftChild;
    EncodingTreeNode rightChild;


    /* Constructors */
    public EncodingTreeNode(Byte value, int size,
                            EncodingTreeNode leftChild, EncodingTreeNode rightChild) {

        this.size = size;
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public EncodingTreeNode(Byte value, int size) {

        this.size = size;
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
    }

    public EncodingTreeNode(Byte value) {

        this.size = 0;
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
    }

    public void setLeftChild(EncodingTreeNode node) {
        leftChild = node;
    }

    public void setRightChild(EncodingTreeNode node) {
        rightChild = node;
    }

    public void setValue(Byte bytes) {
        value = bytes;
    }

    public EncodingTreeNode getLeftChild() {
        return leftChild;
    }

    public EncodingTreeNode getRightChild() {
        return rightChild;
    }

    public int getSize() {
        return size;
    }

    public Byte getValue() {
        return value;
    }

    /* We will compare trees by the value of the variable size */
    @Override
    public int compareTo(EncodingTreeNode node) {

        if (node.size == size) {
            return 0;
        } else if (node.size < size) {
            return 1;
        }
        return -1;
    }


    /**
     * Returns the path to unique byte in the tree. This path is the code
     * bit sequence for the unique byte.
     *
     * @param bytes  The unique byte.
     * @param bitSet The string of 0 and 1 that represents the path to a unique byte.
     * @return The path to unique byte in the tree
     */
    public String getUniqueBitCodesFromTree(Byte bytes, String bitSet) {

        String pathToLeaf;
        if (value == bytes) {
            return bitSet;

        } else {
            if (leftChild != null) {

                pathToLeaf = leftChild.getUniqueBitCodesFromTree(bytes, bitSet.concat("0"));
                if (pathToLeaf != null) {
                    return pathToLeaf;
                }
            }

            if (rightChild != null) {
                pathToLeaf = rightChild.getUniqueBitCodesFromTree(bytes, bitSet.concat("1"));
                if (pathToLeaf != null) {
                    return pathToLeaf;
                }
            }
        }
        return null;
    }

    /**
     * Packs a tree shape into a binary string.
     *
     * @return The binary string in which the tree shape is encrypted for placing
     * it in the archive file.
     */
    public String packTreeShape(StringBuilder binaryShape) {

        if (leftChild != null) {
            if (leftChild.value == null) {
                leftChild.packTreeShape(binaryShape.append("1"));
            } else {
                leftChild.packTreeShape(binaryShape.append("0"));
            }
        }
        if (rightChild != null) {
            if (rightChild.value == null) {
                rightChild.packTreeShape(binaryShape.append("1"));
            } else {
                rightChild.packTreeShape(binaryShape.append("0"));
            }
        }
        return "1".concat(binaryShape.toString());
    }

    /**
     * Packs the leaves of a tree into a sequence containing
     * the characters from the leaves in the order in which they
     * appear when traversing the tree in order.
     *
     * @param result The sequence of unique bytes.
     * @return The packed leaves in order.
     */
    public PrivateLinkedList<Byte> packTreeLeaves(PrivateLinkedList<Byte> result) {

        if (leftChild != null) {
            leftChild.packTreeLeaves(result);

            if (leftChild.value != null) {
                result.add(leftChild.value);
            }
        }
        if (rightChild != null) {
            rightChild.packTreeLeaves(result);

            if (rightChild.value != null) {
                result.add(rightChild.value);
            }
        }
        return result;
    }
}
