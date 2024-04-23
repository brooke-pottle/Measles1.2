/**
 * This program allows the user to generate codon bias and genetic analysis reports.
 * 
 * COSC 1437
 * 
 * @author Brooke Pottle
 */
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Tester {

	public static void main(String[] args) throws IOException {
		
		String acidTableFile = "aminoAcidTable.csv";
		String rf1File = "measlesSequenceRF1.csv";
		String rf2File = "measlesSequenceRF2.csv";
		String rf3File = "measlesSequenceRF3.csv";
		String delimiter = ",";
		String[] rfCodons = {};
		
		ArrayList<AminoAcid> aminoAcidList = new ArrayList<>();
		
		Scanner inFile = new Scanner(new File(acidTableFile));
		Scanner kbd = new Scanner(System.in);
		
		//Skip first line of table header
		inFile.nextLine();
		
		/**
		 * 1.A This block reads and stores the data from "aminoAcidTable.csv" 
		 * and creates an AminoAcid object each line and stores in Array List until done
		 */
		while(inFile.hasNextLine()) {
			
			//Read line and store as a string 
			String line = inFile.nextLine();
			//Split string on "," and initialize a String array with the values
			String[] aminoData = line.split(delimiter);
			//Declare and initialize variables for name and abbreviations 
			String name = aminoData[0];
			String threeLetter = aminoData[1];
			String oneLetter = aminoData[2];
			//Declare an ArrayList and loop to add all codons  
			ArrayList<String> codons = new ArrayList<>();
			for(int i = 3; i < aminoData.length; i++) {
					codons.add(aminoData[i]);
			}
			
			//Create an AminoAcid instance based off of iteration's data
			AminoAcid aminoAcid = new AminoAcid(name, threeLetter, oneLetter, codons);
			//Add instance to array list of amino acids
			aminoAcidList.add(aminoAcid);
			
			}
		
		//1.B Open reading frame and store codons in an array
		int userNum;
		do {
			System.out.println("Please enter a number (1-3) for the reading frame would you like to analyze: ");
			userNum = kbd.nextInt();
			if(userNum < 1 || userNum > 3)
				System.out.println("Invalid value");
		}
		while(userNum < 1 || userNum > 3);
		
		//Calls the storeRF method with the specific RF file
		switch(userNum) {
		case 1:
			rfCodons = storeRF(rf1File);
			break;
		case 2:
			rfCodons = storeRF(rf2File);
			break;
		case 3:
			rfCodons = storeRF(rf3File);
			break;
		}
		//Eat whitespace
		kbd.nextLine();
		
		//2.A This asks the user for the amino acid they are looking for
		String userInput;
		do {
			System.out.println("Enter \"D\" to display a specific amino acid codon bias analysis or\n"
							 + "Enter \"P\" to print a full amino acid analysis report to file");
			userInput = kbd.nextLine().toUpperCase();
			
			if(!userInput.equals("D") && !userInput.equals("P")) 
				System.out.println("Invalid letter entered. Please try again.");

				
		}
		while(!userInput.equals("D") && !userInput.equals("P"));
		
		//Simple menu for user to choose between display and print to file
		switch(userInput) {
			
		case "D":
			//Repeat while user enters "Y" 
			do {
				System.out.println("Please enter the one-letter abbreviation \nfor the amino acid you are looking for: ");
				userInput = kbd.nextLine();
				displayAminoAcid(userInput.toUpperCase(), aminoAcidList, rfCodons);
				//Ask user if they want to display another amino acid and check for invalid values
				do {
					System.out.println("\nWould you like to display another amino acid? Enter Y/N: ");
					userInput = kbd.nextLine();
					if(!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("n"))
						System.out.println("Invalid letter entered. Please try again.");
				}
				while(!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("n"));
			}
			while(userInput.equalsIgnoreCase("y"));
			//Fall through to printing full report after user presses enter
			System.out.println("Press enter to print a complete codon bias report:");
			userInput = kbd.nextLine();
			
		case "P": 
			codonBiasRFReport(aminoAcidList, rfCodons, userNum);
			
			kbd.close();
		}
		
		
		
	
//2.C (this is unfinished)
//		int codonCount = 0;
//		int startCodonNum = 0;
//		int stopCodonNum = 0;
//		boolean hasFoundATG = false;
//		String aminoLetterSeq;
//		
//		for(int i = 0; i < rfCodons.length; i++) {
//			codonCount++;
//			if(!hasFoundATG) {
//				if(rfCodons[i].equals("ATG")) {
//					hasFoundATG = true;
//					startCodonNum = codonCount;
//				
//				}
//			}
//			else if (rfCodons[i].equals("TAA") || rfCodons[i].equals("TAG") || rfCodons[i].equals("TGA")) {
//				stopCodonNum = codonCount;
//				if(stopCodonNum - startCodonNum >= 50) {
//				break;
//				}
//				
//			}
//		}

		
		
	
	
		
		
		
		
		



	}
	
	/**
	 * 2.B This takes the RF1 genetic sequence and amino acid list and writes a report of amino acid codon bias
	 * 
	 * @param rf1 This is the genetic sequence
	 * @param list This is the list of amino acids
	 * @throws IOException 
	 * @author Brooke Pottle
	 */
	public static void codonBiasRFReport(ArrayList<AminoAcid> list, String[] sequence, int rfNum) throws IOException {
		
		String outFileName = ""; 
		int[] codonCount;
		
		switch(rfNum) {
		case 1: 
			outFileName = "codonBiasRF1.txt";
			break;
		case 2:
			outFileName = "codonBiasRF2.txt";
			break;
		case 3:
			outFileName = "codonBiasRF3.txt";
			break;
		}
		
		
		PrintWriter writer = new PrintWriter(new FileWriter(outFileName));
		
		writer.println("~~~~~~~~~~~~~~~~Complete Codon Bias Analysis~~~~~~~~~~~~~~~~");
		
		for(AminoAcid acids : list) {
			double total = 0;
			writer.println("------------------------------------------------------------");
			writer.println(acids.toString() + "\n");
			codonCount = calcCodonRF(acids, sequence);
			for(int i = 0; i < codonCount.length; i++) {
				total += codonCount[i];
			}
			for(int j = 0; j < codonCount.length; j++) {
				writer.printf("%s:", acids.getCodon(j));
				writer.printf("%4d", codonCount[j]);
				writer.printf("%8.2f%%\n", codonCount[j] / total * 100);
			}
			System.out.println();
		}
		writer.close();
	}
	
	/**
	 * 
	 * @param letter
	 * @param list
	 * @param sequence
	 * @author Brooke Pottle
	 */
	public static void displayAminoAcid(String letter, ArrayList <AminoAcid> list, String[] sequence) {
		
		AminoAcid myAcid = new AminoAcid();
		
		for(AminoAcid acids : list) {
			if(letter.equals(acids.getOneLetter())) {
				System.out.println(acids.toString());
				myAcid = acids;
			}
		}
		int[] codonCount = calcCodonRF(myAcid, sequence);
		
		double total = 0;
		
		for(int k = 0; k < codonCount.length; k++) {
				total += codonCount[k];
			}

		for(int i = 0; i < codonCount.length; i++) {
				System.out.print(myAcid.getCodon(i) + ": ");
				System.out.print(codonCount[i] + " ");
				System.out.printf("%8.2f%%\n", codonCount[i] / total * 100);
			}
	}
	
	/**
	 * 1.B
	 * @return 
	 * @throws IOException
	 * @author Brooke Pottle
	 */
	public static String[] storeRF(String rfFile) throws IOException {
		
		String[] codons = {};
		
		File inFile = new File(rfFile);
		Scanner scanFile = new Scanner(inFile);
		
		while(scanFile.hasNext()) {
			String line = scanFile.nextLine();
			codons = line.split(",");
		}
		scanFile.close();
		return codons;
	}
	
	/** 
	 * 3.A
	 * @param aminoLetter
	 * @param list
	 * @param sequence
	 * @author Brooke Pottle
	 */
	public static int[] calcCodonRF(AminoAcid acid, String[] sequence ) { 
	
		int[] count = new int[acid.getListSize()];
				
				for(int i = 0; i < sequence.length; i++) {
					for(int j = 0; j < acid.getListSize(); j++) {
						if(sequence[i].equals(acid.getCodon(j))) {
							count[j]++;
						}
					}
				}
			return count;
		}

}