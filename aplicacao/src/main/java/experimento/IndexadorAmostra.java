package experimento;

import java.util.Map;
import java.util.Set;

import experimento.colecao.Colecao;
import experimento.colecao.Documento;
import experimento.colecao.LeitorXMLFeeder;
import experimento.output.Sumario;
import experimento.output.SumarioCSV;
import experimento.output.Termo;
import experimento.output.TermosCSV;
import experimento.output.Tud;
import experimento.output.TudCSV;
import experimento.stemmer.Stemmer;
import experimento.util.ExtratorDeAmostraTermosUnicos;

public class IndexadorAmostra implements Indexador {
	private Colecao colecao;
	private TudCSV tudCsv;
	private LeitorXMLFeeder leitor;

	private Set<String> chavesAmostra;

	public IndexadorAmostra(Colecao colecao) {
		this.colecao = colecao;
		leitor = new LeitorXMLFeeder(colecao);
	}

	@Override
	public void executar() throws ExperimentoException {
		ExtratorDeAmostraTermosUnicos amostra = new ExtratorDeAmostraTermosUnicos(colecao);
		chavesAmostra = amostra.extrair();		

		// Faz o processamento levando em conta somente as chaves da amostra
		tudCsv = new TudCSV(colecao.getClass().getSimpleName());
		leitor.processar(this::processarStemmers);
		tudCsv.close();
		gerarSumario();
		gerarTermos();
	}

	private void processarStemmers(Documento documento) {
		if (chavesAmostra.contains(documento.getChave())) {
			for (Stemmer stemmer : colecao.getStemmers()) {
				Set<String> termosUnicosDocumento = stemmer.stem(documento.getTextoNormalizado());
				int qtdTermosUnicos = termosUnicosDocumento.size();
				Tud tud = Tud.obterInstancia();
				tud.setChave(documento.getChave());
				tud.setQuantidadeDeTermosUnicos(qtdTermosUnicos);
				tud.setAlgoritmo(stemmer.getClass().getSimpleName());
				tudCsv.registrarTud(tud);
			}
			chavesAmostra.remove(documento.getChave());
		}

	}

	private void gerarTermos() {
		try (TermosCSV termosCsv = new TermosCSV(colecao.getClass().getSimpleName())) {
			for (Stemmer stemmer : colecao.getStemmers()) {
				Map<String, Integer> termos = stemmer.getTermosUnicosNaColecao();
				Set<String> chaves = termos.keySet();
				String nomeStemmer = stemmer.getClass().getSimpleName();
				Termo termo = Termo.obterInstancia();
				for (String chave : chaves) {
					termo.setTermo(chave);
					termo.setOcorrencias(termos.get(chave));
					termo.setAlgoritmo(nomeStemmer);
					termosCsv.registrarTermo(termo);
				}
			}
		}
	}

	private void gerarSumario() {
		try (SumarioCSV sumarioCsv = new SumarioCSV(colecao.getClass().getSimpleName())) {
			for (Stemmer stemmer : colecao.getStemmers()) {
				long documentosProcessados = stemmer.getProcessados();
				int termosUnicosNaColecao = stemmer.quantidadeDeTermosUnicosNaColecao();
				String nomeStemmer = stemmer.getClass().getSimpleName();
				Sumario sumario = Sumario.obterInstancia();
				sumario.setDocumentosProcessados(documentosProcessados);
				sumario.setTermosUnicosNaColecao(termosUnicosNaColecao);
				sumario.setAlgoritmo(nomeStemmer);
				sumarioCsv.registrarSumario(sumario);
				;
			}
		}
	}

}
