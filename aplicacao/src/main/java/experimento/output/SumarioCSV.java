package experimento.output;

public class SumarioCSV extends CSVFile {
	public SumarioCSV(String nomeDoArquivo) {
		super(nomeDoArquivo + "-Sumario", "Processados, TUC, Stemmer");
	}
	
	public void registrarSumario(Sumario sumario) {
		writeLine(String.valueOf(sumario.getDocumentosProcessados()), String.valueOf(sumario.getTermosUnicosNaColecao()), sumario.getAlgoritmo());
	}
}
