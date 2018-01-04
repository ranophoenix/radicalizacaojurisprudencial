package experimento.output;

public class AmostraCSV extends CSVFile {
	public AmostraCSV(String nomeDoArquivo) {
		super(nomeDoArquivo + "-Amostra", "Media,Sigma,N,e,TamanhoDaAmostra");
	}

	public void registrarAmostra(Amostra amostra) {
		writeLine(String.valueOf(amostra.getMedia()), String.valueOf(amostra.getDesvioPadrao()),
				String.valueOf(amostra.getTamanhoDaPopulacao()), String.valueOf(amostra.getErroAmostral()),
				String.valueOf(amostra.getTamanhoDaAmostra()));
	}
}
