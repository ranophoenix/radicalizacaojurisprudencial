package experimento.colecao;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import experimento.util.Configuracao;

public class ColecaoSolr {
	private String nome;
	private SolrClient solrClient;

	public ColecaoSolr(String nome) {
		this.nome = nome;
		solrClient = new HttpSolrClient.Builder(Configuracao.getPropriedade("SOLR_URL") + nome).build();
	}

	public String getNome() {
		return nome;
	}

	public SolrClient getSolrClient() {
		return solrClient;
	}

}
