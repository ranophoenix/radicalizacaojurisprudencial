package experimento.output;

public class Tud {
	private String chave;
	private int quantidadeDeTermosUnicos;
	private String algoritmo;
	
	private static Tud tud;
	
	private Tud() {
		
	}

	public static Tud obterInstancia() {
		if (tud == null) {
			tud = new Tud();
		}
		return tud;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public int getQuantidadeDeTermosUnicos() {
		return quantidadeDeTermosUnicos;
	}

	public void setQuantidadeDeTermosUnicos(int quantidadeDeTermosUnicos) {
		this.quantidadeDeTermosUnicos = quantidadeDeTermosUnicos;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}


}
