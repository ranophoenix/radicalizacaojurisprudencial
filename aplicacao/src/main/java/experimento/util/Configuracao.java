package experimento.util;

import java.io.IOException;
import java.util.Properties;

public class Configuracao {
	private static final Properties configuracao = new Properties();
	
	static {
		try {
			configuracao.load(Configuracao.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	private Configuracao() {
		
	}
	
	public static String getPropriedade(String propriedade) {		
		return configuracao.getProperty(propriedade);
	}
}
