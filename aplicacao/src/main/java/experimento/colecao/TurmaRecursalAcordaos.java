package experimento.colecao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TurmaRecursalAcordaos extends Colecao {
	private List<ColecaoSolr> colecoesSolr = new ArrayList<>();
	
	{
		colecoesSolr.add(new ColecaoSolr("atr_nostem"));
		colecoesSolr.add(new ColecaoSolr("atr_porter"));
		colecoesSolr.add(new ColecaoSolr("atr_rslp"));
		colecoesSolr.add(new ColecaoSolr("atr_rslps"));
		colecoesSolr.add(new ColecaoSolr("atr_unine"));
	}
	
	@Override
	public List<String> getCamposTextuais() {
		return Arrays.asList("Ementa", "textoConclusao");
	}

	@Override
	public String getAlias() {
		return "atr";
	}

	@Override
	public String toString() {
		return "Acórdãos da Turma Recursal";
	}

	@Override
	public String getCampoChave() {
		return "Chave";
	}

	@Override
	public List<ColecaoSolr> getColecoesSolr() {
		return colecoesSolr;
	}

}
