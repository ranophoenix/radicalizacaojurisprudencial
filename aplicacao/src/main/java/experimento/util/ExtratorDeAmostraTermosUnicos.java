package experimento.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import experimento.colecao.Colecao;
import experimento.colecao.Documento;
import experimento.colecao.LeitorXMLFeeder;
import experimento.output.Amostra;
import experimento.output.AmostraCSV;
import experimento.stemmer.NoStem;
import experimento.stemmer.Stemmer;

public class ExtratorDeAmostraTermosUnicos {
	private List<String> chaves = new ArrayList<String>();
	private Set<String> chavesAmostra = new HashSet<String>();
	private LeitorXMLFeeder leitor;
	private final Logger log = Logger.getLogger(this.getClass().getName());
	private SummaryStatistics stats = new SummaryStatistics();
	private Colecao colecao;

	
	public ExtratorDeAmostraTermosUnicos(Colecao colecao) {
		this.colecao = colecao;
		this.leitor = new LeitorXMLFeeder(colecao);
	}
	
	public Set<String> extrair() {
		leitor.processar(this::selecionarAmostra);

		double erroAmostral = 0.05 * stats.getStandardDeviation();
		int tamanhoAmostra = Util.tamanhoDaAmostra(stats.getStandardDeviation(), stats.getN(), erroAmostral);
		log.info("Tamanho da amostra: " + tamanhoAmostra);
		try (AmostraCSV csv = new AmostraCSV("TermosUnicos_" + colecao.getClass().getSimpleName())) {
			Amostra amostra = Amostra.obterInstancia();
			amostra.setMedia(stats.getMean());
			amostra.setDesvioPadrao(stats.getStandardDeviation());
			amostra.setTamanhoDaPopulacao(stats.getN());
			amostra.setErroAmostral(erroAmostral);
			amostra.setTamanhoDaAmostra(tamanhoAmostra);
			csv.registrarAmostra(amostra);
		}
		Random random = new Random();
		while (chavesAmostra.size() < tamanhoAmostra) {
			int idxAleatorio = random.nextInt(chaves.size());
			String selecionada = chaves.get(idxAleatorio);
			if (chavesAmostra.contains(selecionada)) {
				log.log(Level.WARNING, "Coleção possui chave duplicada: " + selecionada);
			} else {
				chavesAmostra.add(selecionada);
			}
			chaves.remove(selecionada);
		}
		chaves = null; // Uma vez selecionada a amostra, as chaves não são
						// mais necessárias.		
		
		return chavesAmostra;
	}
	
	private void selecionarAmostra(Documento documento) {
		chaves.add(documento.getChave());
		Stemmer stemmer = new NoStem();
		Set<String> termosUnicosDocumento = stemmer.stem(documento.getTextoNormalizado());
		int qtdTermosUnicos = termosUnicosDocumento.size();		
		stats.addValue(qtdTermosUnicos);
	}
	
}
