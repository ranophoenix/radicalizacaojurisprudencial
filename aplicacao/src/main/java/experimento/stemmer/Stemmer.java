package experimento.stemmer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import experimento.util.Util;

public abstract class Stemmer {
	private Map<String, Integer> termosUnicos = new HashMap<String, Integer>();
	private long processados = 0;
	
	public Set<String> stem(String texto) {		
		String[] tokens = texto.split(" ");
		String[] tokensAposStem = this.processar(tokens);
		Set<String> termosUnicosDoDocumento = Util.termosUnicos(tokensAposStem);		

		for (String token : tokensAposStem){
 			if (termosUnicos.containsKey(token)){ 				
 				termosUnicos.put(token, termosUnicos.get(token) + 1);
 			} else {
 				termosUnicos.put(token, 1);
 			}
 		}
 		processados++;
 		
 		return termosUnicosDoDocumento;
	}
	
	public abstract String[] processar(String[] tokens);
	
	public int quantidadeDeTermosUnicosNaColecao() {
		return termosUnicos.size();
	}
	
	public Map<String, Integer> getTermosUnicosNaColecao() {
		return termosUnicos;
	}	
	
	public long getProcessados() {
		return processados;
	}
	
	public void reset() {
		processados = 0;
		termosUnicos = new HashMap<String, Integer>();
	}

}
