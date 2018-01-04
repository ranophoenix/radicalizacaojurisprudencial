package experimento.trec.judgement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import experimento.trec.TrecQRel;
import experimento.trec.TrecQRelSet;
import experimento.trec.TrecQResult;
import experimento.trec.TrecQResultSet;

public class NRuns {
	private static final int DEPTH = 30;
	private static final double CUTOFF_RELEVANTS = 0.3;

	private TrecQRelSet relevants = new TrecQRelSet();

	public NRuns(Map<String, TrecQResultSet> resultsByCollection) {
		final Map<Integer, Map<TrecQResult, Integer>> docsByQuery = new LinkedHashMap<>();

		resultsByCollection.forEach((collection, results) -> {

			Map<Integer, List<TrecQResult>> resultByQuery = results.stream()
					.collect(Collectors.groupingBy(TrecQResult::getQid));

			resultByQuery.forEach((qid, rbq) -> {
				int index = Math.min(DEPTH, rbq.size());
				Stream<TrecQResult> ordered = rbq.stream().limit(index);
				ordered.forEach(result -> {
					if (!docsByQuery.containsKey(qid)) {
						docsByQuery.put(qid, new LinkedHashMap<>());
					}
					Map<TrecQResult, Integer> docs = docsByQuery.get(qid);
					if (!docs.containsKey(result)) {
						docs.put(result, 1);
					} else {
						docs.put(result, docs.get(result) + 1);
					}
				});
			});

		});
		
		docsByQuery.forEach((qid, docs) -> {
			docs.entrySet().stream().sorted(Map.Entry.<TrecQResult, Integer>comparingByValue().reversed())
					.limit((int) Math.ceil(docs.size() * CUTOFF_RELEVANTS)).forEach(doc -> {
						TrecQResult result = doc.getKey();
						TrecQRel qrel = new TrecQRel(result.getQid(), result.getQ0(), result.getDocno(), 1);
						relevants.add(qrel);
					});

		});
	}

	public TrecQRelSet getRelevants() {
		return relevants;
	}

}
