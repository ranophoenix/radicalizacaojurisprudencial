package experimento.colecao;

import experimento.util.Util;

public class Documento {
	private String chave;
	private String texto;
	private String textoNormalizado;

	public Documento(String chave, String texto) {
		this.chave = chave;
		this.texto = texto;
	}

	public String getChave() {
		return chave;
	}

	public String getTexto() {
		return texto;
	}
	
	public String getTextoNormalizado() {
		if (textoNormalizado == null) {
			 textoNormalizado = Util.normalizar(texto);
		} 
		return textoNormalizado;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chave == null) ? 0 : chave.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Documento other = (Documento) obj;
		if (chave == null) {
			if (other.chave != null)
				return false;
		} else if (!chave.equals(other.chave))
			return false;
		return true;
	}

}
