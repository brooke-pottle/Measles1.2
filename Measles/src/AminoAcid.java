/**
 * This class stores allows the user to and retrieve Amino Acid data.
 * 
 * @author Brooke Pottle
 */
import java.util.ArrayList;

public class AminoAcid {
	
	private String fullName;
	private String aminoOneLetter;
	private String aminoThreeLetter;
	private ArrayList<String> aminoCodons;
	
	public AminoAcid(String name, String letter3, String letter1, ArrayList<String> codons) {
		fullName = name;
		aminoThreeLetter = letter3;
		aminoOneLetter = letter1;
		aminoCodons = codons;
	}
	
	public AminoAcid() {
		fullName = "";
		aminoOneLetter = "";
		aminoThreeLetter = "";
		
	}
	
	public void setName(String name) {
		fullName = name;
	}
	
	public void setOneLetter(String letter1) {
		aminoOneLetter = letter1;
	}
	
	public void setThreeLetter(String letter3) {
		aminoThreeLetter = letter3;
	}
	
	public String getName() {
		return fullName;
	}
	
	public String getOneLetter() {
		return aminoOneLetter;
	}
	
	public String getThreeLetter() {
		return aminoThreeLetter;
	}
	
	public String getCodon(int num) {
		return aminoCodons.get(num);
		
	}
	
	public ArrayList<String> getCodons() {
		return aminoCodons;
	}
	
	public int getListSize() {
		return aminoCodons.size();
	}
	
	public String toString() {
		return "The codons for " + fullName + "(" + 
				aminoOneLetter + ") are:" + aminoCodons;
	}
	
}
