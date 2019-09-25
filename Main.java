/*
 * Name: Justin Senia
 * E-Number: E00851822
 * Date: 2/9/2017
 * Class: COSC 314
 * Project: #1, Part 1
 */

import java.util.*;
import java.io.*;

public class Main {
    
    //Declaring random variable outside of main so it can be used by all methods
    public static Random randGen;
    
    //main method for Project 1 
    public static void main(String[] args) throws IOException{
        
        //declaring variable "n" to be used as a universal "size" variable
        int n;
        
        //FOR COSC 314 PROJECT #1, PART 1:
        //This program takes in 3 seperate external files, reads in the first number
        //to use as matrix size dimensions, then reads in the remaining data,
        //finds the transitive closure on it via warshalls algorithm.
        //Then writes the resulting binary matrix from the transitive closure to a file.
        
        //creating file locations to be used to locate external files and also
        //creating a location for the output file to be saved to.
        File inFileOne = new File("file1.txt"); 
        File inFileTwo = new File("file2.txt"); 
        File inFileThree = new File("file3.txt"); 
        File outFile = new File("OutputFile.txt");
        
        //creating a printwriter for the output file
        //(passed: outFile= out file location to be saved)
        PrintWriter pWriter = new PrintWriter(outFile);
        
        //scanner created for file external reading
        Scanner fIn = new Scanner(inFileOne); 
        
		//Label at top of output
		pWriter.println("COSC 314 Project #1, Part 1:");
		pWriter.println("");
		
        //read size of matrix from file
        n = fIn.nextInt(); 
        //create & populate matrix with external file data
        //(passed: n= size, fIn= scanner linked to external file)
        int[][] fMatrixOne = popFileMatrix(n, fIn);
        //performing transitive closure with warshall's algorithm and placing data in orig loc.
        //(passed: fMatrixOne= file populated matrix, n= size)
        fMatrixOne = transClosureMatrix(fMatrixOne, n);
        //writing descriptor of data to file
        pWriter.println("Transitive Closure from file1: ");
        //writing copy of matrix to file
        //(passed: fMatrixOne= trans closure matrix, n= size, pWriter= printwriter)
        printMatrix(fMatrixOne, n, pWriter);
        //closing scanner to ready for next iteration
        fIn.close();
        
        //scanner re-initialized with new value for file external reading
        fIn = new Scanner(inFileTwo);
        //read size of matrix from file
        n = fIn.nextInt();
        //create & populate matrix with external file data
        //(passed: n= size, fIn= scanner linked to external file)
        int[][] fMatrixTwo = popFileMatrix(n, fIn);
        //performing transitive closure with warshall's algorithm and placing data in orig loc.
        //(passed: fMatrixOne= file populated matrix, n= size)
        fMatrixTwo = transClosureMatrix(fMatrixTwo, n);
        //writing descriptor of data to file
        pWriter.println("Transitive Closure from file2: ");
        //writing copy of matrix to file
        //(passed: fMatrixOne= trans closure matrix, n= size, pWriter= printwriter)
        printMatrix(fMatrixTwo, n, pWriter);
        //closing scanner to ready for next iteration
        fIn.close();
        
        //scanner re-initialized with new value for file external reading
        fIn = new Scanner(inFileThree);
        //read size of matrix from file
        n = fIn.nextInt();
        //create & populate matrix with external file data
        //(passed: n= size, fIn= scanner linked to external file)
        int[][] fMatrixThree = popFileMatrix(n, fIn);
        //performing transitive closure with warshall's algorithm and placing data in orig loc.
        //(passed: fMatrixOne= file populated matrix, n= size)
        fMatrixThree = transClosureMatrix(fMatrixThree, n);
        //writing descriptor of data to file
        pWriter.println("Transitive Closure from file3: ");
        //writing copy of matrix to file
        //(passed: fMatrixOne= trans closure matrix, n= size, pWriter= printwriter)
        printMatrix(fMatrixThree, n, pWriter);
		
		//display prompt to let user know program is terminating
		System.out.println("Algorithm finished, Ending Program...");
		
        //closing scanner
        fIn.close();
        
        //closing printwriter for data protection
        pWriter.close();
        
        
        /*
        
        //Inneficiency testing for COSC 314 Project #1, part 1:
        //sets matrix size parameters at an increasing size n, randomly populates the
        //matrix with a 50% chance of each field being a "1", then proceeds to run
        //transitive closure on the matrix via the warshalls algorithm, keeps track of
        //the time it takes to complete the task and then outputs the results to the
        //output window.
        n = 100;
        int[][] rndMatrixOne = randPopMatrix(n, 13, .5);
        rndMatrixOne = timedTransCloseMatrix(rndMatrixOne, n, 13);
        
        n = 200;
        int[][] rndMatrixTwo = randPopMatrix(n, 13, .5);
        rndMatrixTwo = timedTransCloseMatrix(rndMatrixTwo, n, 13);
                
        n = 500;
        int[][] rndMatrixThree = randPopMatrix(n, 13, .5);
        rndMatrixThree = timedTransCloseMatrix(rndMatrixThree, n, 13);
                
        n = 1000;
        int[][] rndMatrixFour = randPopMatrix(n, 13, .5);
        rndMatrixFour = timedTransCloseMatrix(rndMatrixFour, n, 13);
        */
        
        //acts as a spacer for more legible output window data readouts
        System.out.println("");
        
    }
    
    
    //Transitive closure algorithm
    //Uses warshalls algorithm to calculate transitive closure and reurns a
    //matrix containing the results
    //(passed: R= matrix, nElements= length of one side of matrix)
    public static int[][] transClosureMatrix(int[][] R, int nElements){
        
        //creating matrix pointer, assigning passed matrix to new pointer 
        int[][] A = R;
        //creating & initializing a matrix of equal size to matrix A,
        //(passed: nElements= length of one side of matrix)
        int[][] B = popMatrix(nElements);
        
        //3 nested for loops that use warshall's algorithm to bitwise Or and And
        //the proper elements of a single matrix in order to find the transitive closure
        //over multiple iterations
        for (int k = 0; k < nElements; k++){
            for (int i = 0; i < nElements; i++)
                for (int j = 0; j < nElements; j++)
                    B[i][j] = bitwiseOr(A[i][j], bitwiseAnd(A[i][k], A[k][j]));
            
            //copying the values of matrix B to matrix  A so loop can reset and 
            //go onto next iteration of calculations (if it hasn't reached the last iteration yet
            for (int i = 0; i < nElements; i++)
                for (int j = 0; j < nElements; j++)
                    A[i][j] = B[i][j];
        }
        
        //return matrix A with completed transitive closure
        return A;
    }
    
    
    //bitwise AND
    //takes two values, if they are both 1, returns a 1, otherwise returns 0
    //(passed: d= 1st value to be compared, m= 2nd value to be compared)
    public static int bitwiseAnd(int d, int m){
        
        if (d == 1 && m == 1){
            return 1;
        } 
        else 
            return 0;
    }
    
    
    //bitwise OR
    //takes two values, if they are both 0, returns a 0, otherwise returns 1
    //(passed: v= 1st value to be compared, b= 2nd value to be compared)
    public static int bitwiseOr(int v, int b){
        
        if (v == 0 && b == 0){
            return 0;
        } 
        else 
            return 1;
    }
    
    
    //creates a matrix populated by all zero's based on input parameter value, returns created matrix
    //(passed: nPop= size of on side of desired matrix to be created)
    public static int[][] popMatrix(int nPop){
        
        //create new matrix based on input param size
        int[][] zeroMatrix = new int[nPop][nPop];
        
        //populate new matrix with all zero's
        for (int t = 0; t < nPop; t++){
            for (int y = 0; y < nPop; y++){
                zeroMatrix[t][y] = 0;
            }
        }
        
        //returns new matrix filled with all zero's
        return zeroMatrix;
    }
    
    
    //Makes a new matrix based off of properly formatted external file input, returns matrix
    //(passed: nPopFile= matrix dimension size based on external file
    //(passed(cont): keyInF = Scanner with file location attached to it)
    public static int[][] popFileMatrix(int nPopFile, Scanner keyInF){
        
        //create new matrix based on read size in external file
        int[][] fileMatrix = new int[nPopFile][nPopFile];
        
        //copies all external file values to comparable size matrix
        for (int d = 0; d < nPopFile; d++){
            for (int f = 0; f < nPopFile; f++){
                fileMatrix[d][f] = keyInF.nextInt();
            }
        }
        
        //returns file populated matrix
        return fileMatrix;
    }
    
    
    //Creates a matrix based off of supplied matrix dimension size,
    //seed value and the user supplied probability of getting a connection
    //returns the random matrix
    //(passed: n= matrix size dimension, seedVal= seedvalue, pcnt= probability)
    public static int[][] randPopMatrix(int n, int seedVal, double pcnt){
        
        //create new random  generator based on suplied seed value
        randGen = new Random(seedVal);
        
        //creating matrix based on supplied size value
        int[][] randMatrix = new int[n][n];
        
        //populates matrix with approprate values at appropriate places based on probability parameter
        for (int t = 0; t < n; t++){
            for (int y = 0; y < n; y++){
                if (randGen.nextDouble() < 1 - pcnt)
                    randMatrix[t][y] = 0;
                else
                    randMatrix[t][y] = 1; 
            }
        }
        
        //return the populated matrix
        return randMatrix;
    }
    
    
    //prints the supplied matrix to an external file
    //(passed: toBePrinted= matrix to be printed, nPrint= length of matrix side,
    //(passed(cont): pW= printwriter with preconfigured file location attached.
    public static void printMatrix(int[][] toBePrinted, int nPrnt, PrintWriter pW){
        
        //iterates through matrix, and prints matrix in proper form
        for (int d = 0; d < nPrnt; d++){
            for (int f = 0; f < nPrnt; f++){
                pW.print(toBePrinted[d][f]);
            }
            pW.println("");
        }
        pW.println("\n");
    }
    
    
    //Executes a Transitive closure method using warshalls algorithm and keeps track
    //of the time it takes for the method to execute
    //passed: R= supplied matrix for use, nVal= length of matrix side, seedVal= seed value for rnd #)
    public static int[][] timedTransCloseMatrix(int[][] R, int nVal, int seedVal){
        
        //makes note of initial time before warshall's begins
        long startTime = System.currentTimeMillis();
		
        //executes Warshall's algorithm on supplied data
        //(passed: R= matrix that needs trans closure, nVal= length of matrix side)
        R = transClosureMatrix(R, nVal);
		
        //makes note of end time after warshall's has completed
        long endTime = System.currentTimeMillis();
        
		
        //prints out properly formatted data to describe timing results of warshalls algorithm
        System.out.println("N Value: " + nVal + " Seed Value: " + 
        seedVal + " Time taken to calculate Transitive closure: " +
        (endTime - startTime) + " Milliseconds");
        
		
        //return Transitive closure matrix "R"
        return R;
    }
}
