package experimento.stemmer;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class RSLPSTest {

	@Test
	public void testStem() {
		final Stemmer trunc = new RSLPS();
		Set<String> esperado = new HashSet<>();
		esperado.add("constituição");
		esperado.add("limitação");
		esperado.add("regimento");
		esperado.add("considerando");
		
		assertEquals(esperado, trunc.stem("constituições limitações regimento considerando"));		
	}

}
