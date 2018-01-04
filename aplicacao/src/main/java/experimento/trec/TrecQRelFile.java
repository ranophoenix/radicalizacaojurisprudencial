package experimento.trec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import experimento.ExperimentoException;

public class TrecQRelFile {
	private final TrecQRelSet qRels = new TrecQRelSet();

	public TrecQRelSet getLines() {
		return qRels;
	}

	public TrecQRelFile(String filename) throws ExperimentoException {
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
			int relevance = Integer.parseInt(fields[3]);
			TrecQRel qRel = new TrecQRel(qid, q0, docno, relevance);
			qRels.add(qRel);
		}
	}

}
