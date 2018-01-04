package experimento.colecao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teste extends Colecao {
	private List<ColecaoSolr> colecoes = new ArrayList<ColecaoSolr>();
	
	{
		colecoes.add(new ColecaoSolr("testes"));
	}

	@Override
	public List<String> getCamposTextuais() {		
		return Arrays.asList("txtEmenta", "txtRelatorio", "txtAcordao", "txtVoto");
	}

	@Override
	public String getAlias() {
		return "testes";
	}

	@Override
	public String getCampoChave() {	
		return "Chave";
	}

	@Override
	public List<ColecaoSolr> getColecoesSolr() {		
		return colecoes;
	}

}
