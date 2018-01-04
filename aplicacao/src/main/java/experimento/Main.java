package experimento;

import java.util.logging.Level;
import java.util.logging.Logger;

import experimento.colecao.Colecao;
import experimento.colecao.SegundoGrauAcordaos;
import experimento.colecao.SegundoGrauDecisoesMonocraticas;
import experimento.colecao.TurmaRecursalAcordaos;
import experimento.colecao.TurmaRecursalDecisoesMonocraticas;

public class Main {
	private static final Logger LOG = Logger.getLogger(Main.class.getName());
	private ProcessaColecoes processador = new ProcessaColecoes();
	private final Colecao[] colecoes = new Colecao[] { new SegundoGrauAcordaos(), new SegundoGrauDecisoesMonocraticas(),
			new TurmaRecursalAcordaos(), new TurmaRecursalDecisoesMonocraticas() };

	public Main() {
		processador = new ProcessaColecoes();
		for (Colecao colecao : colecoes) {
			processador.addColecao(colecao);
		}
	}

	public void executarFase1() throws ExperimentoException {
		LOG.info("Executando Fase 1...");
		processador.processarFase1();
		LOG.info("Fase 1 concluída.");
	}

	public void executarFase2() throws ExperimentoException {
		LOG.info("Executando Fase 2...");
		processador.processarFase2();
		LOG.info("Fase 2 concluída.");
	}

	public void executarFase3() throws ExperimentoException {
		LOG.info("Executando Fase 3...");
		processador.processarFase3();
		LOG.info("Fase 3 concluída.");
	}
	
	public void executarTodasAsFases() throws ExperimentoException {
		executarFase1();
		executarFase2();
		executarFase3();
	}

	/**
	 * 
	 * @param args:
	 *            fase1: experimento de radicalização sobre uma amostra da
	 *            coleção para saber a eficácia dos algoritmos;
	 * 
	 *            fase2: indexar todos os documentos;
	 *  			  
	 *            fase3: efetuar consultas contra a base indexada com todos os
	 *            documentos;
	 *            
	 *            todas: executa todas as fases.
	 */
	public static void main(String[] args) {
		try {
			String fase = "";
			if (args.length > 0) {
				fase = args[0];
			}
			Main main = new Main();
			switch (fase) {
			case "todas":
				main.executarTodasAsFases();
				break;			
			case "fase3":
				main.executarFase3();
				break;
			case "fase2":
				main.executarFase2();
				break;
			case "fase1":
				main.executarFase1();
				break;
			default:
				LOG.info("É obrigatório escolher uma das fases de processamento (fase1 | fase2 | fase3 | todas).");
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
