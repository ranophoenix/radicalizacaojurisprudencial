package experimento.output;

public class TudCSV extends CSVFile {
	public TudCSV(String nomeDoArquivo) {
		super(nomeDoArquivo, "Chave,TUD,Stemmer");
	}

	public void registrarTud(Tud tud) {
		writeLine(tud.getChave(), String.valueOf(tud.getQuantidadeDeTermosUnicos()), tud.getAlgoritmo());
	}
}
