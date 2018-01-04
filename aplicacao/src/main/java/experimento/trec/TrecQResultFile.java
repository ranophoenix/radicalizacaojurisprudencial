package experimento.trec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import experimento.ExperimentoException;

public class TrecQResultFile {
	private final TrecQResultSet qResults = new TrecQResultSet();

	public TrecQResultSet getLines() {
		return qResults;
	}

	public TrecQResultFile(String filename) throws ExperimentoException {
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {

			stream.forEach(this::parser);

		} catch (IOException e) {
			throw new ExperimentoException(e);
		}

	}
	
	private void parser(String line) {
		if (! "".equals(line.trim())) {
			String[] fields = line.split("\\s+");
			int qid = Integer.parseInt(fields[0]);
			String q0 = fields[1];
			String docno = fields[2];
			int rank = Integer.parseInt(fields[3]);
			double score = Double.parseDouble(fields[4]);
			String tag = fields[5];
			TrecQResult qRes = new TrecQResult(qid, q0, docno, rank, score, tag);
			qResults.add(qRes);
		}
	}
}
