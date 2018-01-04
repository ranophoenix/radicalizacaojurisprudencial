package experimento.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testNormalizarRemoverTodasAsPontuacoes() {
		String texto = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`\u00B4{|}~\u00B0\u00A7\u00AA\u00BF\u0093\u0094\u0096";		
		
		assertEquals("", Util.normalizar(texto));
		
		texto = "?gb?h";
		assertEquals("gb h", Util.normalizar(texto));
	}

	@Test
	public void testTermosUnicos() {
		String[] tokens = "Este não é um texto longo, não é?".split(" ");
		Set<String> esperado = new HashSet<String>(Arrays.asList("Este","não", "é", "um", "texto", "longo,", "é?"));
		
		assertEquals(esperado, Util.termosUnicos(tokens));
	}
	
	@Test
	public void testTamanhoDaAmostra() {
		assertEquals(63, Util.tamanhoDaAmostra(4.1681, 2637, 1.025));
	}
	
	@Test
	public void testTamanhoDaAmostra2() {
		double sigma = 6940.012146091022;
		long N = 37142;
		double e = 0.05*sigma;
		assertEquals(1476, Util.tamanhoDaAmostra(sigma, N, e));
	}
	

}
