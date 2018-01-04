package experimento.trec;

public class TrecQuery {
	private int qid;
	private String query;

	public TrecQuery(int qid, String texto) {
		super();
		this.qid = qid;
		this.query = texto;
	}

	public int getQid() {
		return qid;
	}

	public String getQuery() {
		return query;
	}

	@Override
	public String toString() {
		return "TrecQuery [qid=" + qid + ", query=" + query + "]";
	}

	
	
}
