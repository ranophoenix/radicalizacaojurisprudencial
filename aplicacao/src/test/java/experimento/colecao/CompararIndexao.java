package experimento.colecao;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import experimento.IndexadorSolr;
import experimento.util.Util;

public class CompararIndexao {

	@Test
	public void compararQuantidadeDeTermos() throws Exception {
		Teste teste = new Teste();
		IndexadorSolr iSolr = new IndexadorSolr(teste);
		iSolr.executar();

		LeitorXMLFeeder leitor = new LeitorXMLFeeder(teste);
		final Set<String> termosUnicos = new HashSet<>();
		leitor.processar(documento -> {
			String[] tokens = documento.getTextoNormalizado().split(" ");
			termosUnicos.addAll(Util.termosUnicos(tokens));
		});		

		Set<String> termosNoIndice = Util.termosIndiceSolr(teste.getColecoesSolr().get(0));

		assertEquals(termosUnicos, termosNoIndice);		
	}
	
	@Test
	public void compararRemocaoDeSimbolos() throws Exception {
		Teste teste = new Teste();
		SolrClient client = teste.getColecoesSolr().get(0).getSolrClient();
		client.deleteByQuery("*:*");
		client.commit(true, true);
		Documento documento = new Documento("teste", "falar-lhe !\"#$%&'()*+,-./:;<=>?@[\\]^_`\u00B4{|}~\u00B0\u00A7\u00AA\u00BF\u0093\u0094\u0096 teste");
		
		SolrInputDocument documentoSolr = new SolrInputDocument();
		documentoSolr.addField("id", documento.getChave());
		documentoSolr.addField("texto",documento.getTextoNormalizado());
		
		client.add(documentoSolr);
		client.commit(true, true);
		
		Set<String> termosNoIndice = Util.termosIndiceSolr(teste.getColecoesSolr().get(0));
			
		assertEquals(3, termosNoIndice.size());
		
	
	}
	
	

}
