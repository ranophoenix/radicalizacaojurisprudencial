package experimento.colecao;

import org.junit.Test;

import experimento.util.Util;

import static org.junit.Assert.*;

public class ContagemDeTermosIndexados {

	@Test
	public void contarAsg() {
		ColecaoSolr colecao = new ColecaoSolr("asg_nostem");		
		assertEquals(408336, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("asg_porter");
		assertEquals(316008, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("asg_rslp");
		assertEquals(295822, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("asg_rslps");
		assertEquals(384393, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("asg_unine");
		assertEquals(350679, Util.contarTermosIndiceSolr(colecao));
	}
	
	@Test
	public void contarDsg() {
		ColecaoSolr colecao = new ColecaoSolr("dsg_nostem");		
		assertEquals(145270, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("dsg_porter");
		assertEquals(110378, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("dsg_rslp");
		assertEquals(104082, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("dsg_rslps");
		assertEquals(135851, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("dsg_unine");
		assertEquals(124661, Util.contarTermosIndiceSolr(colecao));
	}
	
	@Test
	public void contarAtr() {
		ColecaoSolr colecao = new ColecaoSolr("atr_nostem");		
		assertEquals(188266, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("atr_porter");
		assertEquals(151139, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("atr_rslp");
		assertEquals(144213, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("atr_rslps");
		assertEquals(178675, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("atr_unine");
		assertEquals(165836, Util.contarTermosIndiceSolr(colecao));
	}
	
	@Test
	public void contarDtr() {
		ColecaoSolr colecao = new ColecaoSolr("dtr_nostem");		
		assertEquals(54862, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("dtr_porter");
		assertEquals(39640, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("dtr_rslp");
		assertEquals(36833, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("dtr_rslps");
		assertEquals(50944, Util.contarTermosIndiceSolr(colecao));
		
		colecao = new ColecaoSolr("dtr_unine");
		assertEquals(45897, Util.contarTermosIndiceSolr(colecao));
	}
	
}

