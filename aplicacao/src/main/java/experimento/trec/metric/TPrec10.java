package experimento.trec.metric;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import experimento.trec.TrecQRelSet;
import experimento.trec.TrecQResult;
import experimento.trec.TrecQResultSet;

public class TPrec10 extends TrecMetric {
	
	public TPrec10(TrecQRelSet rels, TrecQResultSet results) {
		
		if (rels.isEmpty() || results.isEmpty())
			throw new IllegalArgumentException("Trec files shoud not be empty.");

		Map<Integer, List<TrecQResult>> groupedResults = results.stream()
				.collect(Collectors.groupingBy(TrecQResult::getQid));

		groupedResults.forEach((qid, qidResults) -> {
						
			int index = 10;
			if (qidResults.size() < index) {
				index = qidResults.size();
			}
			List<TrecQResult> topList = qidResults.subList(0, index);			
			
			final AtomicInteger relevants = new AtomicInteger(0);
			
			topList.forEach(qidResult -> {
				boolean isRelevant = rels.isRelevant(qidResult);
				if (isRelevant) {
					relevants.incrementAndGet();
				}
			});
			
			double value = relevants.get() / 10.0;
			putMetric(qid, value);
		});

	}

}
