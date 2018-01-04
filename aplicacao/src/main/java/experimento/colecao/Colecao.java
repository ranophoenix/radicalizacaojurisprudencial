package experimento.colecao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import experimento.stemmer.NoStem;
import experimento.stemmer.Porter;
import experimento.stemmer.RSLP;
import experimento.stemmer.RSLPS;
import experimento.stemmer.Stemmer;
import experimento.stemmer.UniNE;

public abstract class Colecao {

	public abstract List<String> getCamposTextuais();

	public abstract String getAlias();

	public abstract String getCampoChave();

	protected final Logger log = Logger.getLogger(this.getClass().getName());

	protected final Set<String> termosUnicos = new HashSet<String>();

	private Stemmer[] stemmers = new Stemmer[] { new NoStem(), new Porter(), new RSLP(), new RSLPS(), new UniNE(),
			/*
			 * new Trunc3(), new Trunc4(), new Trunc5(), new Trunc6(), new
			 * Trunc7(), new Trunc8(),
			 */
	};
	
	public Stemmer[] getStemmers() {
		return stemmers;
	}
	
	public abstract List<ColecaoSolr> getColecoesSolr();	
}
