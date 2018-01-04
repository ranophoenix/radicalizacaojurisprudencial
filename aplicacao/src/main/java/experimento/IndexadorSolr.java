package experimento;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import experimento.colecao.Colecao;
import experimento.colecao.ColecaoSolr;
import experimento.colecao.Documento;
import experimento.colecao.LeitorXMLFeeder;

public class IndexadorSolr implements Indexador {
	private Colecao colecao;
	private LeitorXMLFeeder leitor;

	public IndexadorSolr(Colecao colecao) {
		this.colecao = colecao;
		this.leitor = new LeitorXMLFeeder(colecao);
	}

	@Override
	public void executar() {
		excluirIndices();
		leitor.processar(this::indexarDocumento);
		efetuarCommitNosIndices();
	}

	private void excluirIndices() throws ExperimentoException {
		List<ColecaoSolr> colecoes = colecao.getColecoesSolr();
		if (colecoes != null) {
			colecoes.forEach(colecao -> {
				try {
					SolrClient solrClient = colecao.getSolrClient();
					solrClient.deleteByQuery("*:*");
				} catch (SolrServerException | IOException e) {
					throw new ExperimentoException("Erro ao excluir índices.", e);
				}
			});
		}
	}

	private void efetuarCommitNosIndices() throws ExperimentoException {
		List<ColecaoSolr> colecoes = colecao.getColecoesSolr();
		if (colecoes != null) {
			colecoes.forEach(colecao -> {
				try {
					SolrClient solrClient = colecao.getSolrClient();
					solrClient.commit(true, true);
					solrClient.optimize(true, true);
				} catch (SolrServerException | IOException e) {
					throw new ExperimentoException("Erro ao efetuar commit nos índices.", e);
				}
			});
		}
	}

	protected void indexarDocumento(Documento doc) throws ExperimentoException {
		List<ColecaoSolr> colecoes = colecao.getColecoesSolr();
		if (colecoes != null) {
			for (ColecaoSolr colecao : colecoes) {
				SolrInputDocument documento = new SolrInputDocument();
				documento.addField("id", doc.getChave());
				documento.addField("texto", doc.getTexto());

				try {
					SolrClient solrClient = colecao.getSolrClient();
					solrClient.add(documento);
				} catch (IOException | SolrServerException e) {
					throw new ExperimentoException("Erro ao indexar documento.", e);
				}
			}
		}
	}

}
