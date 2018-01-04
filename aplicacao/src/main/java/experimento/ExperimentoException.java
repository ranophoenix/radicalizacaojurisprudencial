package experimento;

public class ExperimentoException extends RuntimeException {
	public ExperimentoException() {
		super();
	}
	
	public ExperimentoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	public ExperimentoException(Throwable causa) {
		super(causa);
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
