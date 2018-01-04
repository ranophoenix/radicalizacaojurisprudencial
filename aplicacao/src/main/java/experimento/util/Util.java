package experimento.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import experimento.colecao.ColecaoSolr;
import experimento.trec.TrecQResult;
import experimento.trec.TrecQResultSet;
import experimento.trec.TrecQuery;

public class Util {

	private static final String PONTUACAO = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`\u00B4{|}~\u00B0\u00A7\u00AA\u00BF\u0093\u0094\u0096";
	private static final Logger LOG = Logger.getLogger(Util.class.getName());

	/**
	 * Converte o texto para caixa-baixa e remove os caracteres	 
	 * 
	 * @param texto
	 * @return
	 */
	public static String normalizar(String texto) {
		String aux = texto.toLowerCase();
		for (int i = 0; i < PONTUACAO.length(); i++) {
			String simbolo = String.valueOf(PONTUACAO.charAt(i));
			aux = aux.replaceAll(Pattern.quote(simbolo), " ");
		}
		aux = aux.replaceAll("\\s{2,}", " ");
		aux = aux.trim();

		return aux;
	}

	/**
	 * Converte um array de tokens num conjunto de tokens (remove a duplicidade)
	 * 
	 * @param tokens
	 * @return
	 */
	public static Set<String> termosUnicos(String[] tokens) {
		Set<String> conjunto = new HashSet<String>();
		for (String token : tokens) {
			if (token != null && !"".equals(token)) {
				conjunto.add(token);
			}
		}

		return conjunto;
	}

	/**
	 * Calcula o tamanho da amostra para população finita e z = 1.96 (95% de
	 * nível de confiança.
	 * 
	 * @param sigma
	 *            desvio padrão
	 * @param N
	 *            tamanho da população
	 * @param e
	 *            erro amostral
	 * @return tamanho da amostra
	 */
	public static int tamanhoDaAmostra(double sigma, long N, double e) {
		double numerador = Math.pow(1.96, 2) * Math.pow(sigma, 2) * N;
		double denominador = Math.pow(e, 2) * (N - 1) + Math.pow(1.96, 2) * Math.pow(sigma, 2);
		return (int) Math.ceil(numerador / denominador);
	}

	public static void processarIndiceSolr(ColecaoSolr colecao, Consumer<String> callback) {
		String caminho = Configuracao.getPropriedade("solr.core.location") + "/" + colecao.getNome() + "/data/index";
		FSDirectory directory;
		try {
			directory = FSDirectory.open(Paths.get(caminho));
			IndexReader indexReader = DirectoryReader.open(directory);
			TermsEnum termEnum = MultiFields.getTerms(indexReader, "texto").iterator();
			BytesRef bytesRef;

			while ((bytesRef = termEnum.next()) != null) {
				callback.accept(bytesRef.utf8ToString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Set<String> termosIndiceSolr(ColecaoSolr colecao) {
		Set<String> termos = new HashSet<String>();
		processarIndiceSolr(colecao, termos::add);

		return termos;
	}

	public static long contarTermosIndiceSolr(ColecaoSolr colecao) {
		final AtomicLong i = new AtomicLong(0);
		processarIndiceSolr(colecao, s -> i.incrementAndGet());

		return i.get();
	}

	public static double truncarDouble(double numero, int escala) {
		return BigDecimal.valueOf(numero).setScale(escala, RoundingMode.HALF_UP).doubleValue();
	}

	public static void salvarResultados(String nomeArquivo, Map<String, TrecQResultSet> resultados) {
		resultados.forEach((colecao, res) -> {
			res.export(nomeArquivo + "_" + colecao);
		});
	}

	public static TrecQResultSet popularTrecQResultSet(ColecaoSolr colecao, TrecQuery query)
			throws SolrServerException, IOException {
		SolrClient client = colecao.getSolrClient();

		SolrQuery solrQuery = (new SolrQuery(query.getQuery())).setRows(Integer.MAX_VALUE);
		solrQuery.set("q.op", "AND");
		solrQuery.set("fl", "id,score");

		QueryResponse rsp = client.query(solrQuery);

		TrecQResultSet resultados = new TrecQResultSet();

		int i = 1;

		LOG.info("Encontrados: " + rsp.getResults().getNumFound());
		
		for (SolrDocument solrDocument : rsp.getResults()) {
			TrecQResult resultado = new TrecQResult(query.getQid(), "Q0", solrDocument.get("id").toString(), i,
					Double.parseDouble(solrDocument.get("score").toString()), colecao.getNome());
			resultados.add(resultado);
			i++;
		}

		return resultados;
	}

}
