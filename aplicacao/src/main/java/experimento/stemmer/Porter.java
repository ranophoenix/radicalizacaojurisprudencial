package experimento.stemmer;

import org.tartarus.snowball.ext.PortugueseStemmer;

public class Porter extends Stemmer {
	private PortugueseStemmer stemmer = new PortugueseStemmer();
	@Override
	public String[] processar(String[] tokens) {
		String[] resultado = new String[tokens.length];
		
		for (int i = 0; i < tokens.length; i++){
			String token = tokens[i];
			stemmer.setCurrent(token);
			stemmer.stem();
			resultado[i] = stemmer.getCurrent();
		}
			
		return resultado;
	}

}
