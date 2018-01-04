package experimento.stemmer;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class UniNETest {

	@Test
	public void testStem() {
		final Stemmer trunc = new UniNE();
		Set<String> esperado = new HashSet<>();
		esperado.add("constituica");
		esperado.add("limitaca");
		esperado.add("regiment");
		esperado.add("considerand");
		
		assertEquals(esperado, trunc.stem("constituições limitações regimento considerando"));				
	}

}
