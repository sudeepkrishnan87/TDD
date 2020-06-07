package hangmantdd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hangman {
Set<String>uniquewordSet=new HashSet();
private  static final int MAX_ATTEMPT=10;
private int remainingAttempt;
private double score;
public int getRemainingAttempt()
{
	return remainingAttempt;
}
List<String>words=new ArrayList();
	public int getWordCount(char a, String word) {
		// TODO Auto-generated method stub
		int count=0;
		for(char c:word.toCharArray())
		{
			if(c==a)
			{
				 count++;
			}
			
		}
		return count;
	}

	public String fetchWods(int strLength) {
		// TODO Auto-generated method stub
		String result=null;
		loadWords();
		words.forEach((words)->{
			if(words.length()==strLength)
			{
				uniquewordSet.add(words);
			}
		});
		return result;
	}
public void loadWords()
{
	remainingAttempt=MAX_ATTEMPT;
	score=0.0;
	String result=null;
	try(BufferedReader br=new BufferedReader(new FileReader("WordSource.txt")))
	{
		while((result=br.readLine())!=null)
			words.add(result);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public String fetchClue(String word) {
	// TODO Auto-generated method stub
	
	StringBuilder clue=new StringBuilder();
	for(int i=0;i<word.length();i++)
		clue.append("-");
	return clue.toString();
}

public String processGuess(String word, String clue, char c) {
	// TODO Auto-generated method stub
	
	if(c>='A'&& c<='Z')c+=32;
	if(c<'a'|| c>'z') 
		throw new IllegalArgumentException("Invalid character Exceptions");
	StringBuilder guess=new StringBuilder();
	int index=word.indexOf(c);
	for(int i=0;i<word.length();i++) {
		if(c==word.charAt(i)&&c!=clue.charAt(i))
		{
			guess.append(c);
			score+=remainingAttempt/clue.length();
		}
		else
		guess.append(clue.charAt(i));
		
		
	}
	--remainingAttempt;
	return guess.toString();
}

public void setScore(double score) {
	this.score = score;
}

public double getScore() {
	// TODO Auto-generated method stub
	return score;
}
}
