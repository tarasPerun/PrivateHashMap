package com.shpp.p2p.cs.tperun.assignment17.Huffman;

import com.shpp.p2p.cs.tperun.assignment17.PrivateArrayList;
import com.shpp.p2p.cs.tperun.assignment17.PrivateHashMap;
import com.shpp.p2p.cs.tperun.assignment17.PrivateLinkedList;
import com.shpp.p2p.cs.tperun.assignment17.PrivatePriorityQueue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* This class contains some methods i.e.  reading byte arrays from a file,
 * saving a byte array to a file, counting the frequency of unique bytes,
 * building a Huffman tree,creating coded and decoded tables, archiving,
 * unzipping, etc.
 */
public class Processor implements Constants {

    public int inputFileSize;
    public int outputFileSize;

    TypeProcessor typeProc = new TypeProcessor();

    /**
     * Reads the file into byte array.
     *
     * @param fileName THe input file.
     * @return The byte array.
     */
    public byte[] readFileToByteArray(String fileName) throws IOException {

        FileInputStream inputStream = new FileInputStream(fileName);
        byte[] buffer = new byte[inputStream.available()];
        inputFileSize = inputStream.available();
        if(inputFileSize == 0) {
            System.out.println("The number of unique characters must be greater than two");
            System.exit(1);
        }
        inputStream.read(buffer, 0, inputStream.available());
        inputStream.close();

        return buffer;
    }

    /**
     * Counts the frequency of unique bytes in the file.
     *
     * @param array The input byte array.
     * @return The table that contains a set of unique bytes and how often
     * they appear in a byte array.
     */
    public PrivateHashMap<Byte, Integer> countUniqueByteFrequency(byte[] array) {

        PrivateHashMap<Byte, Integer> uniqueByteFrequencies = new PrivateHashMap<>();
        for (byte key : array) {
            if (!uniqueByteFrequencies.containsKey(key)) {
                uniqueByteFrequencies.put(key, 1);
            } else {
                uniqueByteFrequencies.put(key, uniqueByteFrequencies.get(key) + 1);
            }
        }
        return uniqueByteFrequencies;
    }

    /**
     * Builds the Huffman tree.
     *
     * @param uniqueByteFrequencies The table of frequencies of unique bytes.
     * @return The Huffman tree.
     */
    public EncodingTreeNode buildHuffmanTree(PrivateHashMap<Byte, Integer> uniqueByteFrequencies) {

        /* Create the list of encoding tree nodes */
        PrivatePriorityQueue<EncodingTreeNode> encodingTreeNodes = new PrivatePriorityQueue<>();

        for (Byte bytes : uniqueByteFrequencies.keySet()) {
            encodingTreeNodes.enqueue(new EncodingTreeNode(bytes, uniqueByteFrequencies.get(bytes)));
        }

        while (encodingTreeNodes.size() > 1) {

            /* Find the two node with the minimum frequency */
            EncodingTreeNode left = encodingTreeNodes.dequeueMin();
            EncodingTreeNode right = encodingTreeNodes.dequeueMin();

            /* Create a parent node and put it in the list */
            EncodingTreeNode father = new EncodingTreeNode(null,
                    left.size + right.size, left, right);
            encodingTreeNodes.enqueue(father);
        }
        return encodingTreeNodes.dequeueMin();
    }

    /**
     * Generates a table of original bytes with their codes.
     *
     * @param tree        The encoding tree.
     * @param frequencies The table of frequencies of unique bytes.
     * @return The table of original bytes with their codes.
     */
    public PrivateHashMap<Byte, String> createUniqueBytesTable(EncodingTreeNode tree,
                                                        PrivateHashMap<Byte, Integer> frequencies) {
        PrivateHashMap<Byte, String> uniqueBytesTable = new PrivateHashMap<>();

        for (Byte bytes : frequencies.keySet()) {
            uniqueBytesTable.put(bytes, tree.getUniqueBitCodesFromTree(bytes, ""));
        }
        return uniqueBytesTable;
    }

    /**
     * Archives the input file into the output file using the Huffman algorithm.
     *
     * @param fileInput  The input file.
     * @param fileOutput The output file.
     */
    public void archive(String fileInput, String fileOutput) throws IOException {

        /* Read the input file into a byte array. */
        byte[] byteArray = readFileToByteArray(fileInput);

        /* Create a table of frequencies of unique bytes. */
        PrivateHashMap<Byte, Integer> uniqueByteFrequencies = countUniqueByteFrequency(byteArray);

        /* Build the encoding Huffman tree. */
        EncodingTreeNode huffmanTree = buildHuffmanTree(uniqueByteFrequencies);

        /* Create the encoding table with unique bytes and their codes. */
        PrivateHashMap<Byte, String> uniqueBytesTable = createUniqueBytesTable(huffmanTree,
                uniqueByteFrequencies);

        /* Building a binary data string using a uniqueBytesTable. */
        StringBuilder dataString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            dataString.append(uniqueBytesTable.get(byteArray[i]));
        }

        /* The length of the data tape in bits. */
        int dataStringLength = dataString.length();

        /*
         If the length of the bit sequence is not a multiple of 8, add the required
         number of zeros.
        */
        if (dataString.length() % BYTE != 0) {
            for (int i = 0; i < dataString.length() % BYTE; i++) {
                dataString.append("0");
            }
        }

        /* Cut the data string into an array of strings by 8 symbols. * / */
        PrivateArrayList<String> list = new PrivateArrayList<>();

        int numberDataByte = dataString.length() / BYTE;
        for (int i = 0; i < numberDataByte; i++) {
            list.add(dataString.substring(i * BYTE, (i + 1) * BYTE));
        }

        /* Create output coded data byte array. */
        byte[] outputByteArray = new byte[numberDataByte];

        /*
         First, put in the output array the service table, which contains
         the information necessary for decoding to the archive file.
        */
        PrivateArrayList<Byte> result = getServiceBytes(fileInput);

        /* Add the length of the data string into archive */
        byte[] array = typeProc.intToByteArray(dataStringLength);

        for (int i = 0; i < array.length; i++) {
            result.add(array[i]);
        }

        for (int i = 0; i < numberDataByte; i++) {
            outputByteArray[i] = (byte) Integer.parseInt(list.get(i), 2);
            result.add(outputByteArray[i]);
        }

        byte[] codedArray = new byte[result.size()];

        for (int i = 0; i < result.size(); i++) {
            codedArray[i] = result.get(i);
        }

        /* The output file size. This value is necessary to assess the efficiency of compression */
        outputFileSize = codedArray.length;

        /* Save the coded byte array into a file */
        saveByteArrayToFile(codedArray, fileOutput);
    }


    /**
     * Unarchives the input file into the output file.
     *
     * @param fileInput  The input file.
     * @param fileOutput The output file.
     */
    public void unarchive(String fileInput, String fileOutput) throws IOException {

        /* Read the input file into byte array. */
        byte[] inputByteArray = readFileToByteArray(fileInput);

        /* Read the size of the Huffman tree from the first two bytes. */
        short treeSize = (short) typeProc.getShortFromTwoBytes(inputByteArray);

        /* the number of bytes that contain the shape of the tree */
        int bytesNumber = (int) Math.ceil(treeSize / 8.);

        /* Read the coded shape of a tree */
        String treeShape = "";
        for (int i = SHORT_SIZE_IN_BYTES; i < SHORT_SIZE_IN_BYTES + bytesNumber; i++) {
            treeShape = treeShape.concat(typeProc.byteToBinaryString(inputByteArray[i], 8));
        }
        treeShape = treeShape.substring(0, treeSize);

        /* Count the number of unique bytes, which is equal to the number of zeros in the tree shape.
         At the same time we form a boolean array, which corresponds to the binary shape of the tree.
         */
        int numberUniqueBytes = 0;
        PrivateLinkedList<Boolean> booleansTreeShape = new PrivateLinkedList<>();
        for (int i = 0; i < treeShape.length(); i++) {

            if (treeShape.charAt(i) == '0') {
                numberUniqueBytes++;
                booleansTreeShape.add(false);
            } else booleansTreeShape.add(true);
        }

        /* Read the following numberUniqueBytes bytes and queue them. */
        PrivateLinkedList<Byte> uniqueBytes = new PrivateLinkedList<>();
        for (int i = SHORT_SIZE_IN_BYTES + bytesNumber; i < SHORT_SIZE_IN_BYTES + bytesNumber + numberUniqueBytes; i++) {
            uniqueBytes.addLast(inputByteArray[i]);
        }

        /* Restore the shape of the Huffman tree. */
        EncodingTreeNode reconstructedTree = reconstructTree(booleansTreeShape, uniqueBytes);
        for (int i = SHORT_SIZE_IN_BYTES + bytesNumber; i < SHORT_SIZE_IN_BYTES + bytesNumber + numberUniqueBytes; i++) {
            uniqueBytes.addLast(inputByteArray[i]);
        }

        /* Read the length of bit string */
        byte tempArray[] = new byte[INT_SIZE_IN_BYTES];
        for (int i = 0; i < INT_SIZE_IN_BYTES; i++) {
            tempArray[i] = inputByteArray[i + SHORT_SIZE_IN_BYTES + bytesNumber + numberUniqueBytes];
        }
        int bitLength = typeProc.getIntFromFourBytes(tempArray);

        /* Create a table for quick decoding of the archive. */
        PrivateHashMap<Byte, String> table = createDecodingTable(reconstructedTree, uniqueBytes);

        /*
         Convert the data array into binary string. Take bytes from 2 + bytesNumber + numberUniqueBytes
         to the end, convert each into an 8-bit sequence  0 and 1 and connect in string.
        */
        StringBuilder resultString = new StringBuilder();
        for (int i = 2 + bytesNumber + numberUniqueBytes + INT_SIZE_IN_BYTES; i < inputByteArray.length; i++) {
            resultString.append(typeProc.byteToBinaryString(inputByteArray[i], BYTE));
        }

        StringBuilder currentString = new StringBuilder();
        PrivateArrayList<Byte> decoded = new PrivateArrayList<>();
        PrivateHashMap<String, Byte> inverseDecodedTable = new PrivateHashMap<>();

        for (Byte key : table.keySet()) {
            inverseDecodedTable.put(table.get(key), key);
        }

        for (int i = 0; i < bitLength; i++) {

            currentString.append(resultString.charAt(i));

            if (inverseDecodedTable.containsKey(currentString.toString())) {
                decoded.add(inverseDecodedTable.get(currentString.toString()));
                currentString = new StringBuilder();
            }
        }

        byte[] outputArray = new byte[decoded.size()];
        for (int i = 0; i < outputArray.length; i++) {
            outputArray[i] = decoded.get(i);
        }

        /* The output file size. This value is necessary to assess the efficiency
        of compression/decompression */
        outputFileSize = outputArray.length;

        saveByteArrayToFile(outputArray, fileOutput);
    }

    /**
     * Writes the service bytes of the archive to the file (the number of original,
     * bytes, the size of the uncompressed file, the table of the original bytes)
     *
     * @param inputFile The input file.
     */
    public PrivateArrayList<Byte> getServiceBytes(String inputFile) throws IOException {

        byte[] byteArray = readFileToByteArray(inputFile);
        PrivateHashMap<Byte, Integer> uniqueByteFrequencies = countUniqueByteFrequency(byteArray);
        if (uniqueByteFrequencies.size() < SHORT_SIZE_IN_BYTES) {
            System.out.println("The number of unique characters must be greater than two");
            System.exit(1);
        }

        EncodingTreeNode huffmanTree = buildHuffmanTree(uniqueByteFrequencies);
        String treeShape = huffmanTree.packTreeShape(new StringBuilder());

        PrivateLinkedList<Byte> treeLeaves = huffmanTree.packTreeLeaves(new PrivateLinkedList<>());

        PrivateArrayList<Byte> resultArray = new PrivateArrayList<>();

        byte[] arrShort = typeProc.shortToByteArray((short) treeShape.length());

        /* Add two bytes with tree size in bits*/
        for (int i = 0; i < SHORT_SIZE_IN_BYTES; i++) {
            resultArray.add(arrShort[i]);
        }

        /* Calculate number of bytes containing the tree shape */
        int number = (int) Math.ceil(treeShape.length() / 8.);

        if (treeShape.length() % BYTE != 0) {
            for (int i = 0; i < treeShape.length() % BYTE; i++) {
                treeShape = treeShape.concat("0");
            }
        }

        /* Cut the treeShape string into an array of strings by 8 symbols */
        PrivateArrayList<String> list = new PrivateArrayList<>();

        int numberTreeShapeByte = treeShape.length() / BYTE;
        for (int i = 0; i < numberTreeShapeByte; i++) {
            list.add(treeShape.substring(i * BYTE, (i + 1) * BYTE));
        }

        /* Add numberDataByte bytes with tree shape to result array */
        for (int i = 0; i < numberTreeShapeByte; i++) {
            resultArray.add(typeProc.bitsToByte(list.get(i)));
        }

        /* Add the array of unique bytes */
        for (Object b : treeLeaves) {
            resultArray.add((Byte) b);
        }
        return resultArray;
    }

    /**
     * Reconstructs the encoding tree from his shape and leaves.
     *
     * @param treeShape  The tree shape in form of bits sequence.
     * @param treeLeaves The encoding tree.
     */
    public EncodingTreeNode reconstructTree(PrivateLinkedList<Boolean> treeShape,
                                            PrivateLinkedList<Byte> treeLeaves) {

        EncodingTreeNode node = new EncodingTreeNode(null);

        if (treeShape.removeFirst()) {
            node.setLeftChild(reconstructTree(treeShape, treeLeaves));
            node.setRightChild(reconstructTree(treeShape, treeLeaves));
        } else {
            node.setValue(treeLeaves.removeFirst());
        }
        return node;
    }

    /**
     * Creates the decoding table from the encoding tree and the list of
     * unique bytes.
     *
     * @param tree The encoding tree.
     * @param list The list of the unique bytes.
     * @return The decoding table.
     */
    PrivateHashMap<Byte, String> createDecodingTable(EncodingTreeNode tree, PrivateLinkedList<Byte> list) {

        PrivateHashMap<Byte, String> decodingTable = new PrivateHashMap<>();

        for (Byte bytes : list) {
            decodingTable.put(bytes, tree.getUniqueBitCodesFromTree(bytes, ""));
        }
        return decodingTable;
    }

    public int getInputFileSize() {
        return inputFileSize;
    }

    public int getOutputFileSize() {
        return outputFileSize;
    }

    /**
     * Saves an array of bytes into a file
     *
     * @param array    The input byte array.
     * @param fileName The name of the file to which the array is saved.
     */
    public int saveByteArrayToFile(byte[] array, String fileName) {

        try (FileOutputStream outputStream = new FileOutputStream(fileName, false)) {
            outputStream.write(array, 0, array.length);

        } catch (IOException ex) {

            System.out.println("The file hasn't been written");
            return -1;
        }
        return 0;
    }
}
