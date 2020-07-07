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

	/** 
	 * 
	 * @param args: Main (fase1 | fase2 | fase3 | todas) (asg | dsg | atr | dtr | todas)

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
			String colecao = "";

			if (args.length > 0) {
				fase = args[0];
				colecao = args[1];
			}

			ProcessaColecoes processador = new ProcessaColecoes();
			switch (colecao) {
			case "todas":
				processador.addColecao(new SegundoGrauAcordaos());
				processador.addColecao(new SegundoGrauDecisoesMonocraticas());
				processador.addColecao(new TurmaRecursalAcordaos());
				processador.addColecao(new TurmaRecursalDecisoesMonocraticas());					
			break;
			case "asg":
				processador.addColecao(new SegundoGrauAcordaos());
			break;
			case "dsg":
				processador.addColecao(new SegundoGrauDecisoesMonocraticas());
			break;
			case "atr":
				processador.addColecao(new TurmaRecursalAcordaos());
			break;
			case "dtr":
				processador.addColecao(new TurmaRecursalDecisoesMonocraticas());					
			break;
			default:
				LOG.info("É obrigatório escolher uma das coleções (asg | dsg | atr | dtr).");
			}

			switch (fase) {
			case "todas":
				processador.processarFase1();
				processador.processarFase2();
				processador.processarFase3();
				break;			
			case "fase3":
				LOG.info("Executando Fase 3...");
				processador.processarFase3();
				LOG.info("Fase 3 concluída.");				
				break;
			case "fase2":
				LOG.info("Executando Fase 2...");
				processador.processarFase2();
				LOG.info("Fase 2 concluída.");

				break;
			case "fase1":
				LOG.info("Executando Fase 1...");
				processador.processarFase1();
				LOG.info("Fase 1 concluída.");
				break;
			default:
				LOG.info("É obrigatório escolher uma das fases de processamento (fase1 | fase2 | fase3 | todas).");
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
