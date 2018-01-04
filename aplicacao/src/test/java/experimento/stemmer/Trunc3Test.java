package experimento.stemmer;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class Trunc3Test {

	/**
	 * Teste básico mostrando o funcionamento do truncamento.
	 */
	@Test
	public void testStem() {
		final Stemmer trunc = new Trunc3();
		Set<String> esperado = new HashSet<>();
		esperado.add("abc");
		esperado.add("ab");
		
		assertEquals(esperado, trunc.stem("abcd ab"));
	}

}
