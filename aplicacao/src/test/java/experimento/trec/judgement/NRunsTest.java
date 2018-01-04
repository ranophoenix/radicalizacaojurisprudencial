package experimento.trec.judgement;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import experimento.trec.TrecQRel;
import experimento.trec.TrecQRelSet;
import experimento.trec.TrecQResult;
import experimento.trec.TrecQResultSet;

public class NRunsTest {
	/**
	 * Popula o resultado de queries cada qual com n documentos no resultado.
	 * 
	 * @param sistema
	 *            no do sistema
	 * @param queries
	 *            quantidade queries
	 * @param inicio
	 *            identificador inicial do documento
	 * @param fim
	 *            idenficador final do documento
	 * @return
	 */
	private TrecQResultSet popularResultados(String sistema, int queries, int inicio, int fim) {
		TrecQResultSet resultado = new TrecQResultSet();
		for (int qid = 1; qid <= queries; qid++) {
			for (int i = inicio; i <= fim; i++) {
				resultado.add(new TrecQResult(qid, "Q0", "D" + i, i, 51 - i, sistema));
			}
		}

		return resultado;
	}

	/**
	 * Simula 3 sistemas retornando exatamente os mesmos documentos. Dessa
	 * forma, são 150 documentos. O NRuns pega os 30 primeiros documentos de
	 * cada sistema para compor o pool e conta o número de repetições. Nesse
	 * caso, todos os documentos são repetidos 3 vezes. Após isso, ele pega os
	 * 30% iniciais (nesse caso, 9 documentos) e marca-os como relevantes.
	 */
	@Test
	public void testGetRelevants1() {
		Map<String, TrecQResultSet> resultados = new LinkedHashMap<>();
		int queries = 2;

		resultados.put("sistema1", popularResultados("sistema1", queries, 1, 50));
		resultados.put("sistema2", popularResultados("sistema2", queries, 1, 50));
		resultados.put("sistema3", popularResultados("sistema3", queries, 1, 50));

		NRuns judgement = new NRuns(resultados);

		TrecQRelSet esperado = new TrecQRelSet();
		for (int qid = 1; qid <= queries; qid++) {
			for (int i = 1; i <= 9; i++) {
				esperado.add(new TrecQRel(qid, "Q0", "D" + i, 1));
			}
		}

		TrecQRelSet relevantes = judgement.getRelevants();

		assertEquals(esperado, relevantes);

	}

	/**
	 * Simula 3 sistemas retornando a mesma quantidade de documentos distintos.
	 * Dessa forma, são 150 documentos. O NRuns pega os 30 primeiros documentos
	 * de cada sistema para compor o pool e conta o número de repetições. Nesse
	 * caso, não há repetições. Após isso, ele pega os 30% iniciais (nesse caso,
	 * 27 documentos) e marca-os como relevantes. Como o sistema1 foi o primeiro
	 * a incluir os documentos, os 30% da lista serão composto unicamente por
	 * documentos dele.
	 */
	@Test
	public void testGetRelevants2() {
		Map<String, TrecQResultSet> resultados = new LinkedHashMap<>();

		resultados.put("sistema1", popularResultados("sistema1", 1, 1, 50));
		resultados.put("sistema2", popularResultados("sistema2", 1, 51, 100));
		resultados.put("sistema3", popularResultados("sistema3", 1, 101, 150));

		NRuns judgement = new NRuns(resultados);

		TrecQRelSet esperado = new TrecQRelSet();
		for (int qid = 1; qid <= 1; qid++) {
			for (int i = 1; i <= 27; i++) {
				esperado.add(new TrecQRel(qid, "Q0", "D" + i, 1));
			}
		}

		TrecQRelSet relevantes = judgement.getRelevants();

		assertEquals(esperado, relevantes);

	}

	/**
	 * Simula 3 sistemas retornando um único documento em comum entre 2
	 * sistemas. Dessa forma, são 150 documentos. O NRuns pega os 30 primeiros
	 * documentos de cada sistema para compor o pool e conta o número de
	 * repetições. Após isso, ele pega os 30% iniciais (nesse caso, 27
	 * documentos) e marca-os como relevantes.
	 */
	@Test
	public void testGetRelevants3() {
		Map<String, TrecQResultSet> resultados = new LinkedHashMap<>();

		resultados.put("sistema1", popularResultados("sistema1", 1, 1, 50));
		resultados.put("sistema2", popularResultados("sistema2", 1, 51, 80));
		resultados.put("sistema3", popularResultados("sistema3", 1, 80, 150));

		NRuns judgement = new NRuns(resultados);

		TrecQRelSet esperado = new TrecQRelSet();

		for (int i = 1; i <= 26; i++) {
			esperado.add(new TrecQRel(1, "Q0", "D" + i, 1));
		}

		esperado.add(new TrecQRel(1, "Q0", "D80", 1)); // Documento comum entre
														// o sistema 2 e o 3.

		TrecQRelSet relevantes = judgement.getRelevants();

		assertEquals(esperado, relevantes);

	}

	/**
	 * Simula 2 sistemas retornando documentos idênticos. Dessa forma, são 150
	 * documentos. O NRuns pega os 30 primeiros documentos de cada sistema para
	 * compor o pool e conta o número de repetições. Nesse caso, há 60
	 * documentos (30 únicos e 30 repetidos). Após isso, ele ordena pelo número
	 * de repetições e pega os 30% iniciais (aqui, 18 documentos) e marca-os
	 * como relevantes. Com isso, nenhum documento do primeiro sistema é para
	 * ser retornado.
	 */
	@Test
	public void testGetRelevants4() {
		Map<String, TrecQResultSet> resultados = new LinkedHashMap<>();

		resultados.put("sistema1", popularResultados("sistema1", 1, 1, 50));
		resultados.put("sistema2", popularResultados("sistema2", 1, 51, 80));
		resultados.put("sistema3", popularResultados("sistema3", 1, 51, 80));

		NRuns judgement = new NRuns(resultados);

		TrecQRelSet esperado = new TrecQRelSet();

		for (int i = 51; i <= 68; i++) {
			esperado.add(new TrecQRel(1, "Q0", "D" + i, 1));
		}

		TrecQRelSet relevantes = judgement.getRelevants();

		assertEquals(esperado, relevantes);

	}

}
