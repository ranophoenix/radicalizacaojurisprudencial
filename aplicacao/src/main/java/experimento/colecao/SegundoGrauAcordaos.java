package experimento.colecao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SegundoGrauAcordaos extends Colecao {
	private List<ColecaoSolr> colecoesSolr = new ArrayList<>();
	
	{
		colecoesSolr.add(new ColecaoSolr("asg_nostem"));
		colecoesSolr.add(new ColecaoSolr("asg_porter"));
		colecoesSolr.add(new ColecaoSolr("asg_rslp"));
		colecoesSolr.add(new ColecaoSolr("asg_rslps"));
		colecoesSolr.add(new ColecaoSolr("asg_unine"));
	}

	@Override
	public List<String> getCamposTextuais() {
		return Arrays.asList("txtEmenta", "txtRelatorio", "txtAcordao", "txtVoto");
	}

	@Override
	public String getAlias() {
		return "asg";
	}

	@Override
	public String toString() {
		return "Acórdãos do Segundo Grau";
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
