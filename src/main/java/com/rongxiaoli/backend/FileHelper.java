package com.rongxiaoli.backend;
//Todo: Unfinished module. Finish it.

import java.io.*;

public class FileHelper {
    private boolean isFileIOReady = false;
    private File operatingFile;
    private String fileAbsolutePath;
    private FileInputStream fileInputStream;
    private FileReader reader;
    private boolean isFileCreated;

    public FileHelper() {
    }

    /**
     * FileHelper init func.
     *
     * @param fileAbsolutePath
     * @throws IOException This IOException is from operatingFile.createNewFile().
     */
    public FileHelper(String fileAbsolutePath) throws IOException {
        this.fileAbsolutePath = fileAbsolutePath;
        operatingFile = new File(fileAbsolutePath);
        operatingFile.getParentFile().mkdirs();
        try {
            reader = new FileReader(operatingFile);
        } catch (FileNotFoundException e) {
            operatingFile.createNewFile();
        }
        isFileIOReady = true;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
        File testFile = new File(fileAbsolutePath); //Todo: There's a problem: What if someone input a file name which the whole path does not exist？
        isFileCreated = testFile.getParentFile().exists();
    }

    /**
     * Is the file created.
     *
     * @return True if created.
     * @throws IOException Throws when an I/O exception occurs.
     */
    public boolean isFileCreated() {
        return isFileCreated;
    }

    public String readLine() throws NullPointerException, FileNotFoundException, IOException {
        if (!isFileIOReady) {
            operatingFile = new File(fileAbsolutePath);
            reader = new FileReader(operatingFile);
        }
        BufferedReader br = new BufferedReader(reader);
        return br.readLine();
    }
}
