package experimento.output;

public class Amostra {
	private double media;
	private double desvioPadrao;
	private long tamanhoDaPopulacao;
	private double erroAmostral;
	private int tamanhoDaAmostra;

	private static Amostra amostra;

	private Amostra() {

	}

	public static Amostra obterInstancia() {
		if (amostra == null) {
			amostra = new Amostra();
		}
		return amostra;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public double getDesvioPadrao() {
		return desvioPadrao;
	}

	public void setDesvioPadrao(double sigma) {
		this.desvioPadrao = sigma;
	}

	public long getTamanhoDaPopulacao() {
		return tamanhoDaPopulacao;
	}

	public void setTamanhoDaPopulacao(long tamanhoDaPopulacao) {
		this.tamanhoDaPopulacao = tamanhoDaPopulacao;
	}

	public double getErroAmostral() {
		return erroAmostral;
	}

	public void setErroAmostral(double erroAmostral) {
		this.erroAmostral = erroAmostral;
	}

	public int getTamanhoDaAmostra() {
		return tamanhoDaAmostra;
	}

	public void setTamanhoDaAmostra(int tamanhoDaAmostra) {
		this.tamanhoDaAmostra = tamanhoDaAmostra;
	}

}
