package experimento.output;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import experimento.ExperimentoException;
import experimento.util.Configuracao;

public class CSVFile implements Closeable {
	private FileWriter writer;

	public CSVFile(String file) throws ExperimentoException {
		this(file, null);
	}

	public CSVFile(String file, String header) throws ExperimentoException {
		try {
			File csvDir = new File(Configuracao.getPropriedade("CSV_DIR"));
			if (!csvDir.exists()) {
				csvDir.mkdir();
			}
			writer = new FileWriter(Configuracao.getPropriedade("CSV_DIR") + "/" + file + ".csv");
			if (header != null) {
				writer.write(header + "\n");
			}
		} catch (IOException e) {
			throw new ExperimentoException("Erro ao gerar CSV.", e);
		}
	}

	public void writeLine(String... fields) {
		try {
			StringBuilder line = new StringBuilder();
			line.append(fields[0]);
			if (fields.length > 1) {
				for (int i = 1; i < fields.length; i++) {
					line.append(",").append(fields[i]);				
				}
			}
			line.append("\n");
			writer.append(line.toString());
		} catch (IOException e) {
			throw new ExperimentoException("Erro ao escrever linha.", e);
		}
	}

	@Override
	public void close() throws ExperimentoException {
		try {
			writer.close();
		} catch (IOException e) {
			throw new ExperimentoException("Erro ao fechar CSV.", e);
		}
	}

}
