package experimento.output;

public class Termo {
	private String termo;
	private int ocorrencias;
	private String algoritmo;

	private static Termo termoInstancia;
	
	private Termo() {
		
	}

	public static Termo obterInstancia() {
		if (termoInstancia == null) {
			termoInstancia = new Termo();
		}
		return termoInstancia;
	}

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public int getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(int ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

}
