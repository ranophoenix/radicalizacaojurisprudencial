package experimento.trec.metric;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import experimento.trec.TrecQRelSet;
import experimento.trec.TrecQResult;
import experimento.trec.TrecQResultSet;

public class TPrecR extends TrecMetric {

	public TPrecR(TrecQRelSet rels, TrecQResultSet results) {

		if (rels.isEmpty() || results.isEmpty())
			throw new IllegalArgumentException("Trec files shoud not be empty.");

		Map<Integer, List<TrecQResult>> groupedResults = results.stream()
				.collect(Collectors.groupingBy(TrecQResult::getQid));

		groupedResults.forEach((qid, qidResults) -> {
						
			int totalRelevants = rels.totalRelevants(qid);
			int index = Math.min(totalRelevants, qidResults.size());

			List<TrecQResult> topList = qidResults.subList(0, index);			
			
			final AtomicInteger relevants = new AtomicInteger(0);
			
			topList.forEach(qidResult -> {
				boolean isRelevant = rels.isRelevant(qidResult);
				if (isRelevant) {
					relevants.incrementAndGet();
				}
			});
			double value = relevants.get() /(double) totalRelevants;
			putMetric(qid, value);
		});

	}

}
