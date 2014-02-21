//John LeClaire, Plagiarism Checker
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class PlagiarismCheckerDriver {
  
  public static void main(String[] args) throws FileNotFoundException
  {
    
    /*
     * Files to check
     * ./sm_doc_set 2/catchmeifyoucan.txt
     * 
     */
    
    File sampDocFolder = new File("./sm_doc_set 2/"); //makes file of folder with sample docs
    
    ArrayList<ArrayList<String>> sampWordlist = new ArrayList<ArrayList<String>>(); //array of string arrays to hold wordlists of sample docs  
    ArrayList<ArrayList<Integer>> sampHashlist = new ArrayList<ArrayList<Integer>>();
    ArrayList<PlagiarismChecker> sampClasses = new ArrayList<PlagiarismChecker>(); //array of checker classes to have a different class for each sample doc    
    
    for(File sampDoc : sampDocFolder.listFiles()){ //for every samp doc, instantiate a checker and add it to array of checkers
      sampClasses.add(new PlagiarismChecker(sampDoc.getPath()));
    }
    
    for(PlagiarismChecker sampClass : sampClasses){ //for each one of those checkers, add the sample docs string array to wordlist
      sampWordlist.add(sampClass.fileToArraylist());
      sampHashlist.add(sampClass.listToHash(sampClass.fileToArraylist())); //turn string array to hashlist
    }
    
    Scanner kb = new Scanner(System.in);
    System.out.println("Enter file path of the document you wish to check.");
    String userFilePath = kb.nextLine(); //filepath of user doc
    
    PlagiarismChecker userClass = new PlagiarismChecker(userFilePath); //checker for the user doc
    ArrayList<String> userWordlist = new ArrayList<String>(); //wordlist, hashlist for user doc
    ArrayList<Integer> userHashlist = new ArrayList<Integer>();
    
    userWordlist.addAll(userClass.fileToArraylist());
    userHashlist.addAll(userClass.listToHash(userClass.fileToArraylist()));
    
    for(ArrayList<Integer> sampHash : sampHashlist){ //retain all hashes same between user doc and sample doc
      sampHash.retainAll(userHashlist);     
    }
    
    System.out.println("Six word phrases in common with: ");
    
    for(int i = 0; i < sampDocFolder.listFiles().length; i++){     //prints name and length of all file hashlists  
      System.out.println(sampDocFolder.listFiles()[i] + "             " + sampHashlist.get(i).size());  
    }
  }  
}