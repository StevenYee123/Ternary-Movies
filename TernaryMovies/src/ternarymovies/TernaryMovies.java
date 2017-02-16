/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ternarymovies;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import java.util.Scanner;
/*
 *
 * @author steven
 */

public class TernaryMovies {

    //Creates static(Universal) root to be used
    static Node root;

    /**
     * NOTE!! The changes made to the sheet will NOT work if the XML document is
     * currently open while running the program
     */
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //Calls the years from the XML table to populate the tree
        enterValues();
        //Create a link to the xml file
        File file = new File("MoviesList.xls");
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        Cell cell;
        Sheet sheet = wb.getSheetAt(0);
        //"Try" and "catch" used to catch any invalid entries for year
        try {
            //Prompt the user to search for a movie year
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter a year to search for: ");
            int searchYear = scan.nextInt();
            //Once entered, the system checks every year in the column for a match
            if (searchNodes(root, searchYear) == true) {
                for (int i = 1; i < 21; i++) {
                    cell = sheet.getRow(i).getCell(2);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (searchYear == Integer.parseInt(cell.getRichStringCellValue().toString())) {
                        cell = sheet.getRow(i).getCell(0);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        System.out.println("Movie Title: " + cell.getRichStringCellValue().toString());
                        cell = sheet.getRow(i).getCell(1);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        System.out.println("Director: " + cell.getRichStringCellValue().toString());
                        cell = sheet.getRow(i).getCell(2);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        System.out.println("Year Filmed: " + cell.getRichStringCellValue().toString() + "\n");
                    }
                }
            } else {
                System.out.println("Not Found");
            }
        } catch (Exception e) {
            System.err.print("Invalid entry" + "\n");
        }
    }

    private static void enterValues() throws IOException {
        //Reads the file name
        File file = new File("MoviesList.xls");

        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        Sheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        Cell cell;
        //Number of rows
        int rows;
        rows = sheet.getPhysicalNumberOfRows();
        //Sets the number of columns
        int cols = 0;
        //Use a for loop to insert each year
        for (int i = 1; i < 21; i++) {
            cell = sheet.getRow(i).getCell(2);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            insert(Integer.parseInt(cell.getRichStringCellValue().toString()));
        }

    }

    //Use this method to insert values into the tree
    public static void insert(int newValue) {
        // create new root if tree is empty
        if (root == null) {
            root = new Node(newValue);
        } else {
            insert(root, new Node(newValue));
        }
    }

    public static void insert(Node root, Node newNode) {
        if (newNode == null) {
            return;
        }
        //Increment tree every time a new child is added
        incrementSubtree(root);

        // determine which node the new node should be a child of, insert it in that node recursively
        if (newNode.value < root.value) {
            if (root.leftChild == null) {
                root.leftChild = newNode;
            } else {
                insert(root.leftChild, newNode);
            }
        } else if (newNode.value > root.value) {
            if (root.rightChild == null) {
                root.rightChild = newNode;
            } else {
                insert(root.rightChild, newNode);
            }
        } else {
            if (root.midChild == null) {
                root.midChild = newNode;
            } else {
                insert(root.midChild, newNode);
            }
        }
    }

    //Method used to increment tree

    private static void incrementSubtree(Node n) {
        n.subtree = n.subtree + 1;
    }

    //Method used to search for the value in the tree

    private static boolean searchNodes(Node root, int value) {
        if (root == null) {
            return false;
        }
        if (value < root.value) {
            return searchNodes(root.leftChild, value);
        } else if (value > root.value) {
            return searchNodes(root.rightChild, value);
        } else {
            if (root.end && value == root.value) {
                return true;
            } else if (value == root.value - 1) {
                return false;
            } else {
                return searchNodes(root.midChild, value);
            }
        }
    }
}
