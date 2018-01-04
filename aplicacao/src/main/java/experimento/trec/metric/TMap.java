package experimento.trec.metric;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.AtomicDouble;

import experimento.trec.TrecQRelSet;
import experimento.trec.TrecQResult;
import experimento.trec.TrecQResultSet;

public class TMap extends TrecMetric {
	
	public TMap(TrecQRelSet rels, TrecQResultSet results) {

		if (rels.isEmpty() || results.isEmpty())
			throw new IllegalArgumentException("Trec files shoud not be empty.");

		Map<Integer, List<TrecQResult>> groupedResults = results.stream()
				.collect(Collectors.groupingBy(TrecQResult::getQid));

		groupedResults.forEach((qid, qidResults) -> {						
			
			final AtomicInteger relevants = new AtomicInteger(0);
			final AtomicInteger position = new AtomicInteger(0);
			final AtomicDouble sumAps = new AtomicDouble(0.0);
			
			qidResults.forEach(qidResult -> {
				position.incrementAndGet();
				boolean isRelevant = rels.isRelevant(qidResult);
				if (isRelevant) {
					relevants.incrementAndGet();
					sumAps.addAndGet(relevants.get() / (double) position.get());
				}
			});
			
			int totalRelevants = rels.totalRelevants(qid);
			double value = 0.0;
			
			if (totalRelevants > 0 ){ 
				value = sumAps.get() /totalRelevants;
			}
			
			putMetric(qid, value);
		});

	}

}
