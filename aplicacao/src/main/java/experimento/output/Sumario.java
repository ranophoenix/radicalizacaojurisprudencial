package experimento.output;

public class Sumario {
	private long documentosProcessados;
	private int termosUnicosNaColecao;
	private String algoritmo;

	private static Sumario sumario;

	private Sumario() {
	}

	public static Sumario obterInstancia() {
		if (sumario == null) {
			sumario = new Sumario();
		}
		return sumario;
	}

	public long getDocumentosProcessados() {
		return documentosProcessados;
	}

	public void setDocumentosProcessados(long documentosProcessados) {
		this.documentosProcessados = documentosProcessados;
	}

	public int getTermosUnicosNaColecao() {
		return termosUnicosNaColecao;
	}

	public void setTermosUnicosNaColecao(int termosUnicosNaColecao) {
		this.termosUnicosNaColecao = termosUnicosNaColecao;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

}
