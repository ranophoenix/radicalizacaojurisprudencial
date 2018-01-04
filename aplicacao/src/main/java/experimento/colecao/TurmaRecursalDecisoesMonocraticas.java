package experimento.colecao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TurmaRecursalDecisoesMonocraticas extends Colecao {
	private List<ColecaoSolr> colecoesSolr = new ArrayList<>();
	
	{
		colecoesSolr.add(new ColecaoSolr("dtr_nostem"));
		colecoesSolr.add(new ColecaoSolr("dtr_porter"));
		colecoesSolr.add(new ColecaoSolr("dtr_rslp"));
		colecoesSolr.add(new ColecaoSolr("dtr_rslps"));
		colecoesSolr.add(new ColecaoSolr("dtr_unine"));
	}

	@Override
	public List<String> getCamposTextuais() {
		return Arrays.asList("textoConclusao");
	}

	@Override
	public String getAlias() {
		return "dtr";
	}

	@Override
	public String toString() {
		return "Decisões Monocráticas da Turma Recursal";
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
