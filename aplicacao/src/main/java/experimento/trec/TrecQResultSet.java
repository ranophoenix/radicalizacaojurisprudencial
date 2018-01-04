package experimento.trec;

import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;

import experimento.ExperimentoException;

public class TrecQResultSet extends TreeSet<TrecQResult> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean add(TrecQResult e) {
		boolean added = super.add(e);
		if (!added) {
			throw new InvalidTrecRecordException("There are results duplicated in this collection.");
		}
		return added;
	}

	public void export(String filename) throws ExperimentoException {
		try (FileWriter writer = new FileWriter(filename)) {
			this.forEach(r -> {
				try {
					writer.append(r.toString() + "\n");
				} catch (IOException e) {
					throw new ExperimentoException(e);
				}
			});
		} catch (IOException e) {
			throw new ExperimentoException(e);
		}
	}
}
