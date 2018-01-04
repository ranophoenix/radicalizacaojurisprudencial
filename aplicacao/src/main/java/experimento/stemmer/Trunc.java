package experimento.stemmer;

public abstract class Trunc extends Stemmer {
	private int n;
	public Trunc(int n) {
		this.n = n;
	}
	
	@Override
	public String[] processar(String[] tokens) {
		String[] tokensRadicalizados = new String[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			if (token.length() > n) {
				tokensRadicalizados[i] = token.substring(0, n);
			} else {
				tokensRadicalizados[i] = token;
			}
		}
		return tokensRadicalizados;
	}
	
}
