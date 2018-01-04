package experimento.output;

public class TermosCSV extends CSVFile {
	public TermosCSV(String nomeDoArquivo) {
		super(nomeDoArquivo + "-Termos", "Termo,Quantidade,Stemmer");
	}
	
	public void registrarTermo(Termo termo) {
		writeLine(termo.getTermo(), String.valueOf(termo.getOcorrencias()), termo.getAlgoritmo());
	}
}
