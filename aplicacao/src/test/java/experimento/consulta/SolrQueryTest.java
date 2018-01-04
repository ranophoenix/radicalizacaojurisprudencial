package experimento.consulta;

import java.util.HashSet;
import java.util.Set;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import experimento.colecao.ColecaoSolr;
import experimento.colecao.LeitorXMLFeeder;
import experimento.colecao.SegundoGrauAcordaos;

public class SolrQueryTest {
	private SolrClient getSolrClient() {
		return new ColecaoSolr("dtr_nostem").getSolrClient();
	}
	
	@Test
	public void testaRegistrosRetornados() throws Exception {
		SolrClient client = getSolrClient();
		SolrQuery query = (new SolrQuery("sergipe ementa")).addField("score").addField("rank").setRows(30);
		query.set("q.op", "AND");		
		QueryResponse rsp = client.query(query);
		
		 Assert.assertEquals(1222, rsp.getResults().getNumFound());
		 
		 SolrDocumentList lista = rsp.getResults();
		 
		 Assert.assertEquals(30, lista.size());		 
		
	}	
	
	
	@Ignore
	@Test
	public void testarDuplicados() throws Exception {
		LeitorXMLFeeder leitor = new LeitorXMLFeeder(new SegundoGrauAcordaos());
		Set<String> chaves = new HashSet<String>();
		
		leitor.processar(documento -> {
			chaves.add(documento.getChave());		
		});
		
		Assert.assertNotEquals(181994, chaves.size());
		 		
	}	
	
}
