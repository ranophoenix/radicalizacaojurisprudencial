package experimento.stemmer;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class NoStemTest {

	@Test
	public void testStem() {
		Stemmer trunc = new NoStem();
		Set<String> esperado = new HashSet<>();
		esperado.add("constituições");
		esperado.add("limitações");
		esperado.add("regimento");
		esperado.add("considerando");
		
		assertEquals(esperado, trunc.stem("constituições limitações regimento considerando"));		
	}

}
