package experimento.stemmer;

public class NoStem extends Stemmer {

	@Override
	public String[] processar(String[] tokens) {		
		return tokens;
	}

}
