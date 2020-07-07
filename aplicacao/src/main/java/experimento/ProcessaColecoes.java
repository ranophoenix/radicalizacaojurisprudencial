package experimento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.apache.solr.client.solrj.SolrServerException;

import experimento.colecao.Colecao;
import experimento.colecao.ColecaoSolr;
import experimento.trec.TQueryFile;
import experimento.trec.TrecQRelSet;
import experimento.trec.TrecQResultSet;
import experimento.trec.TrecQuery;
import experimento.trec.judgement.NRuns;
import experimento.trec.metric.TMap;
import experimento.trec.metric.TPrec10;
import experimento.trec.metric.TPrecR;
import experimento.trec.metric.TrecMetric;
import experimento.util.ExtratorDeAmostra;
import experimento.util.ExtratorDeAmostraTermosUnicos;
import experimento.util.Util;
import experimento.util.Configuracao;

public class ProcessaColecoes {
	private List<Colecao> colecoes = new ArrayList<Colecao>();
	private final Logger log = Logger.getLogger(getClass().getName());

	public void processar(Consumer<Colecao> callback) {
		for (Colecao colecao : colecoes) {
			log.info("Processando coleção: " + colecao);
			callback.accept(colecao);
		}
	}

	public void processarFase1() {
		processar(colecao -> {
			IndexadorAmostra indexador = new IndexadorAmostra(colecao);
			indexador.executar();
		});
	}

	public void processarFase2() {
		processar(colecao -> {
			IndexadorSolr indexador = new IndexadorSolr(colecao);
			indexador.executar();
		});
	}

	public void processarFase3() {
		processar(colecao -> {
			Map<String, TrecQResultSet> resultados = new LinkedHashMap<>();
			TQueryFile consultas = new TQueryFile(Configuracao.getPropriedade("QUERIES_DIR") + "/" + colecao.getAlias() + ".xml");
			for (ColecaoSolr colSolr : colecao.getColecoesSolr()) {
				TrecQResultSet resultadoConsulta = new TrecQResultSet();
				for (TrecQuery consulta : consultas.getLines()) {
					try {
						log.info("Query ID (" + colSolr.getNome() + "): " + consulta.getQid());
						resultadoConsulta.addAll(Util.popularTrecQResultSet(colSolr, consulta));						
					} catch (SolrServerException | IOException e) {						
						throw new ExperimentoException(e);
					}
				}	
				resultados.put(colSolr.getNome(), resultadoConsulta);
			}
			
			NRuns juiz = new NRuns(Collections.unmodifiableMap(resultados));
			
			TrecQRelSet relevantes = juiz.getRelevants();
			
			Util.salvarResultados(Configuracao.getPropriedade("TREC_DIR") + "/resultados", resultados);
			relevantes.export(Configuracao.getPropriedade("TREC_DIR") + "/qrels_" + colecao.getAlias());
			
			for (ColecaoSolr colSolr : colecao.getColecoesSolr()) {
				TrecMetric metrica = new TMap(relevantes, resultados.get(colSolr.getNome()));
				metrica.export(Configuracao.getPropriedade("CSV_DIR") + "/" + colSolr.getNome() + "_map.csv");
				metrica = new TPrecR(relevantes, resultados.get(colSolr.getNome()));
				metrica.export(Configuracao.getPropriedade("CSV_DIR") + "/" + colSolr.getNome() + "_precR.csv");
				metrica = new TPrec10(relevantes, resultados.get(colSolr.getNome()));
				metrica.export(Configuracao.getPropriedade("CSV_DIR") + "/" + colSolr.getNome() + "_p10.csv");
			}
						
		});
	}

	public void addColecao(Colecao colecao) {
		this.colecoes.add(colecao);
	}

	public void removeColecao(Colecao colecao) {
		this.colecoes.remove(colecao);
	}

	public void processarAmostraTermosUnicos() {
		processar(colecao -> {
			ExtratorDeAmostraTermosUnicos amostra = new ExtratorDeAmostraTermosUnicos(colecao);
			amostra.extrair();
		});
	}

	public void processarAmostraCaracteres() {
		processar(colecao -> {
			ExtratorDeAmostra amostra = new ExtratorDeAmostra(colecao);
			amostra.extrair();
		});
	}

}
