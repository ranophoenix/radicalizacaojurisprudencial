package experimento.trec;

public class TrecQResult implements Comparable<TrecQResult> {
	private int qid;
	private String q0;
	private String docno;
	private int rank;
	private double score;
	private String tag;

	public TrecQResult(int qid, String q0, String docno, int rank, double score, String tag) {
		super();
		this.qid = qid;
		this.q0 = q0;
		this.docno = docno;
		this.rank = rank;
		this.score = score;
		this.tag = tag;
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

	public int getRank() {
		return rank;
	}

	public double getScore() {
		return score;
	}

	public String getTag() {
		return tag;
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
		TrecQResult other = (TrecQResult) obj;
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
		return qid + " " + q0 + " " + docno + " " + rank + " " + score + " " + tag;
	}

	@Override
	public int compareTo(TrecQResult o) {
		int cmp = Integer.compare(qid, o.qid);
		if (cmp == 0) {
			cmp = Double.compare(o.score, score);
			if (cmp == 0) {
				cmp = o.docno.compareTo(docno);
			}
		}
		return cmp;
	}

}
