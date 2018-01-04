package experimento.stemmer;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class PorterTest {

	@Test
	public void testStem() {
		final Stemmer trunc = new Porter();
		Set<String> esperado = new HashSet<>();
		esperado.add("constituiçõ");
		esperado.add("limit");
		esperado.add("regiment");
		esperado.add("consider");
		
		assertEquals(esperado, trunc.stem("constituições limitações regimento considerando"));		
	}

}
