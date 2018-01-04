package experimento.trec;

import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;

import experimento.ExperimentoException;

public class TrecQRelSet extends TreeSet<TrecQRel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean isRelevant(TrecQResult result) {
		return this.stream().filter(rel -> rel.getQid() == result.getQid() && rel.getDocno().equals(result.getDocno())
				&& rel.getRelevance() >= 1).count() > 0;
	}

	public int totalRelevants(Integer qid) {
		return (int) this.stream().filter(rel -> rel.getQid() == qid && rel.getRelevance() >= 1).count();
	}

	@Override
	public boolean add(TrecQRel e) {
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
