package experimento.stemmer;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class RSLPTest {

	@Test
	public void testStem() {
		final Stemmer trunc = new RSLP();
		Set<String> esperado = new HashSet<>();
		esperado.add("constitu");
		esperado.add("limit");
		esperado.add("reg");
		esperado.add("consider");
		esperado.add("respons");
		esperado.add("inconstitucionalic");
		
		assertEquals(esperado, trunc.stem("constituições limitações regimento considerando responsabilidade inconstitucionalicimamente"));					
	}

}
