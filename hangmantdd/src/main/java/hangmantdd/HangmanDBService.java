package hangmantdd;

public interface HangmanDBService {

	boolean writeScore(String word,double score);
	WordAndScore readWordAndScore(String word);
}
