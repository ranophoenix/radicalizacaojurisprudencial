package experimento.stemmer;

import org.apache.lucene.analysis.pt.PortugueseLightStemmer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttributeImpl;

public class UniNE extends Stemmer {
	private PortugueseLightStemmer stemmer = new PortugueseLightStemmer();
	@Override
	public String[] processar(String[] tokens) {
		CharTermAttributeImpl t = new CharTermAttributeImpl();
		String[] resultado = new String[tokens.length];

		for (int i = 0; i < tokens.length; i++) {
			char[] token = tokens[i].toCharArray();
			t.copyBuffer(token, 0, token.length);
			if (t.buffer().length <= t.length()) {
				t.resizeBuffer(t.length() + 1);
			}	
			int novoTamanho = stemmer.stem(t.buffer(), t.length());
			t.setLength(novoTamanho);
			resultado[i] = t.toString();
			t.clear();
		}

		return resultado;
	}

}
