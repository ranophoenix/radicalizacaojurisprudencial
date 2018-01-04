package experimento.trec;

public class TrecQRel implements Comparable<TrecQRel> {
	private int qid;
	private String q0;
	private String docno;
	private int relevance;

	public TrecQRel(int qid, String q0, String docno, int relevance) {
		super();
		this.qid = qid;
		this.q0 = q0;
		this.docno = docno;
		this.relevance = relevance;
	}

	public int getQid() {
		return qid;
	}

	public String getQ0() {
		return q0;
	}

	public String getDocno() {
		return docno;
	}

	public int getRelevance() {
		return relevance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docno == null) ? 0 : docno.hashCode());
		result = prime * result + qid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrecQRel other = (TrecQRel) obj;
		if (docno == null) {
			if (other.docno != null)
				return false;
		} else if (!docno.equals(other.docno))
			return false;
		if (qid != other.qid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return qid + " " + q0 + " " + docno + " " + relevance;
	}

	@Override
	public int compareTo(TrecQRel o) {
		if (qid == o.qid) return docno.compareTo(o.docno);
		return Integer.compare(qid, o.qid);
	}

}
