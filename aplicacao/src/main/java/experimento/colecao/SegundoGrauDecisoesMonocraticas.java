package experimento.colecao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SegundoGrauDecisoesMonocraticas extends Colecao {
	private List<ColecaoSolr> colecoesSolr = new ArrayList<>();
	
	{
		colecoesSolr.add(new ColecaoSolr("dsg_nostem"));
		colecoesSolr.add(new ColecaoSolr("dsg_porter"));
		colecoesSolr.add(new ColecaoSolr("dsg_rslp"));
		colecoesSolr.add(new ColecaoSolr("dsg_rslps"));
		colecoesSolr.add(new ColecaoSolr("dsg_unine"));
	}
	

	@Override
	public List<String> getCamposTextuais() {
		return Arrays.asList("textoConclusao");
	}

	@Override
	public String getAlias() {
		return "dsg";
	}

	@Override
	public String toString() {
		return "Decisções Monocráticas do Segundo Grau";
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
