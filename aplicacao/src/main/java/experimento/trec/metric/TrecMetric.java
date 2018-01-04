package experimento.trec.metric;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import experimento.ExperimentoException;

public abstract class TrecMetric {
	private Map<Integer, Double> metricValue = new HashMap<>();

	public double get(int i) {
		return metricValue.get(i);
	}

	public double getAll() {
		return metricValue.values().stream().collect(Collectors.averagingDouble(d -> d));

	}

	public Set<Integer> getQids() {
		return metricValue.keySet();
	}

	public void putMetric(Integer queryId, Double value) {
		metricValue.put(queryId, value);
	}
	
	public void export(String filename) throws ExperimentoException {
		try (FileWriter writer = new FileWriter(filename)) {
			metricValue.forEach((qid, value) -> {
				try {
					writer.append(qid + "," + value + "\n");
				} catch (IOException e) {
					throw new ExperimentoException(e);
				}
			});
		} catch (IOException e) {			
			throw new ExperimentoException(e);
		}
	}	
}
