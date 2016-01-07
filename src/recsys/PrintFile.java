package recsys;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PrintFile {

    File readFile, writeFile;

    public File getReadFile() {
        return readFile;
    }

    public void setReadFile(File readFile) {
        this.readFile = readFile;
    }

    public File getWriteFile() {
        return writeFile;
    }

    public void setWriteFile(File writeFile) {
        this.writeFile = writeFile;
    }

    BufferedWriter bw;
    FileWriter fw;

    public PrintFile(File readFile, File writeFile) {
        this.readFile = readFile;
        this.writeFile = writeFile;
        FileWriter fw;
        try {
            fw = new FileWriter(writeFile.getAbsoluteFile());
            bw = new BufferedWriter(fw);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void printRows(int numberOfRows) {
        Scanner sc;
        int i = 0;
        try {
            sc = new Scanner(readFile);
            for (; i < numberOfRows; i++) {
                System.out.println(sc.next());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("problem in read file");
            e.printStackTrace();
        }
    }

    public void writeFile(String content) {
        try {
            bw.write(content);
            bw.newLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("problem in write file");
            e.printStackTrace();
        }
    }

    public void closeFile() {
        try {
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("problem in closing the file");
            e.printStackTrace();
        }
    }
}
