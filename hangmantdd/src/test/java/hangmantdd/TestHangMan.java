package hangmantdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestHangMan {
	static Random random;
	static Hangman hang;
	static int strLength=0;
	
@BeforeAll
public static void setup()
{
random=new Random();
hang=new Hangman();
}

@BeforeEach
void setupBefore()
{
	strLength=random.nextInt(6)+5;
	hang.setScore(0);
}
	
@Test
void test_AlphabetsInaWord()
{
	String word="pizza";
	char a='z';
	int count=hang.getWordCount(a,word);
	assertEquals(2,count);
	
}

@Test
void test_UniqueWordsAreHandled()
{
	Set<String>uniqueWord=new HashSet();
	Random random=new Random();
	int round=0;
	Hangman hang=new Hangman();
	String word=hang.fetchWods(strLength);
	assertTrue(uniqueWord.add(word));
	
}
@Test
void test_ClueBeforeStart()
{
	Hangman hang=new Hangman();
	String clue=hang.fetchClue("pizza");
	assertEquals("-----", clue);
}

@Test
void test_ClueAfterFirstGuess()
{
	String clue=hang.fetchClue("pizza");
	String guess=hang.processGuess("pizza",clue,'z');
	assertEquals("--zz-", guess);
}

@Test
void test_AfterWrongGuess()
{
	String clue=hang.fetchClue("pizza");
	String guess=hang.processGuess("pizza", clue, 'd');
	assertEquals("-----", clue);
}

@Test 
void test_InvalidCharecterWhileGuessing()
{
	assertThrows(IllegalArgumentException.class, ()->
		hang.processGuess("pizza", "-----", '1'));
}


@Test 
void test_InvalidCharecterWhileGuessingWithMessage()
{
	Exception e=assertThrows(IllegalArgumentException.class, ()->
		hang.processGuess("pizza", "-----", '1'));
	assertEquals("Invalid character Exceptions", e.getMessage());
}

@Test
void test_InitialAttemp()
{
	hang.loadWords();
	assertEquals(10,hang.getRemainingAttempt());
	
}

@Test
void test_TrialsRemaining()
{
	hang.loadWords();
	hang.processGuess("pizza", "-----", 'k');
	assertEquals(9, hang.getRemainingAttempt());
}
@Test
void test_InitiaScore()
{
	hang.fetchWods(strLength);
	assertEquals(0.0,hang.getScore());
	
}
@Test
void test_ScoreAfterCorrectGusess1()
{
	hang.processGuess("lazza", "-----", 'z');
	hang.processGuess("lazza", "---z-", 'z');
	assertEquals(3.0, hang.getScore());
}

@Test
void test_writetoDatabase()
{
	HangmanDBService db=mock(HangmanDBService.class);
	Hangman han=new Hangman(db);
	when(db.writeScore("lazza",10.0)).thenReturn(true);
	assertTrue(han.saveWord("lazza",10.0));
}
}
