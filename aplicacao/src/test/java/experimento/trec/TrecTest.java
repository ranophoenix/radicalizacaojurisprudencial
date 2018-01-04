package experimento.trec;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import experimento.colecao.ColecaoSolr;
import experimento.trec.judgement.NRuns;
import experimento.trec.metric.TMap;
import experimento.trec.metric.TPrec10;
import experimento.trec.metric.TPrecR;
import experimento.trec.metric.TrecMetric;
import experimento.util.Util;

public class TrecTest {
	@Test
	public void testarCargaDeQrel() {
		TrecQRelFile tqrel = new TrecQRelFile("trec/qrels.test");

		assertEquals(3681, tqrel.getLines().size());
	}

	@Test
	public void testarCargaDeQresult() {
		TrecQResultFile tqres = new TrecQResultFile("trec/results.test");

		assertEquals(1500, tqres.getLines().size());
	}

	@Test
	public void testarPrec10() {
		TrecQRelFile tqrel = new TrecQRelFile("trec/qrels.test");
		TrecQResultFile tqres = new TrecQResultFile("trec/results.test");

		TrecMetric p10 = new TPrec10(tqrel.getLines(), tqres.getLines());

		assertEquals(0.3000, Util.truncarDouble(p10.getAll(), 4), 0.00001);
		assertEquals(0.2000, Util.truncarDouble(p10.get(301), 4), 0.00001);
		assertEquals(0.7000, Util.truncarDouble(p10.get(302), 4), 0.00001);
		assertEquals(0.0000, Util.truncarDouble(p10.get(303), 4), 0.00001);

	}

	@Test
	public void testarPrecR() {
		TrecQRelFile tqrel = new TrecQRelFile("trec/qrels.test");
		TrecQResultFile tqres = new TrecQResultFile("trec/results.test");

		TrecMetric pR = new TPrecR(tqrel.getLines(), tqres.getLines());

		assertEquals(0.2174, Util.truncarDouble(pR.getAll(), 4), 0.00001);
		assertEquals(0.1456, Util.truncarDouble(pR.get(301), 4), 0.00001);
		assertEquals(0.5065, Util.truncarDouble(pR.get(302), 4), 0.00001);
		assertEquals(0.0000, Util.truncarDouble(pR.get(303), 4), 0.00001);

	}

	@Test
	public void testarMap() {
		TrecQRelFile tqrel = new TrecQRelFile("trec/qrels.test");
		TrecQResultFile tqres = new TrecQResultFile("trec/results.test");

		TrecMetric pMap = new TMap(tqrel.getLines(), tqres.getLines());

		assertEquals(0.1785, Util.truncarDouble(pMap.getAll(), 4), 0.00001);
		assertEquals(0.0324, Util.truncarDouble(pMap.get(301), 4), 0.00001);
		assertEquals(0.4175, Util.truncarDouble(pMap.get(302), 4), 0.00001);
		assertEquals(0.0858, Util.truncarDouble(pMap.get(303), 4), 0.00001);

	}

	@Test
	public void testarNRuns() throws Exception {
		TrecQuery query = new TrecQuery(1, "ação direta inconstitucional medida liminar cautelar");
		Map<String, TrecQResultSet> resultados = new LinkedHashMap<>();
		
		//Nostem
		ColecaoSolr colecao = new ColecaoSolr("asg_nostem");

		TrecQResultSet noStemResult = Util.popularTrecQResultSet(colecao, query);
		resultados.put(colecao.getNome(), noStemResult);

		//Rslp
		colecao = new ColecaoSolr("asg_rslp");
		TrecQResultSet rslpResult = Util.popularTrecQResultSet(colecao, query);

		resultados.put(colecao.getNome(), rslpResult);
		
		//Nruns
		
		NRuns judgement = new NRuns(resultados);
		
		TrecQRelSet relevantes = judgement.getRelevants();		
		
		Util.salvarResultados("trec/resultados_trectest", resultados);
		relevantes.export("trec/qrels_trectest");
		
		TMap map = new TMap(relevantes, noStemResult);
		TPrec10 prec10 = new TPrec10(relevantes, noStemResult);
		TPrecR precR = new TPrecR(relevantes, noStemResult);
		
		assertEquals(1, Util.truncarDouble(map.getAll(), 4), 0.00001);
		assertEquals(1, Util.truncarDouble(prec10.getAll(), 4), 0.00001);
		assertEquals(1, Util.truncarDouble(precR.getAll(), 4), 0.00001);
		
		map = new TMap(relevantes, rslpResult);
		prec10 = new TPrec10(relevantes, rslpResult);
		precR = new TPrecR(relevantes, rslpResult);
		
		assertEquals(0.7255, Util.truncarDouble(map.getAll(), 4), 0.00001);
		assertEquals(0.8000, Util.truncarDouble(prec10.getAll(), 4), 0.00001);
		assertEquals(0.6667, Util.truncarDouble(precR.getAll(), 4), 0.00001);
		
	}
	
	@Test
	public void carregarQuery() {
		TQueryFile queries = new TQueryFile("queries/asg.xml");
		
		assertEquals(100, queries.getLines().size());
	}
	
	@Test
	public void testarMetricasAsg() {
		TrecQRelFile tqrel = new TrecQRelFile("trec/qrels_asg");
		
		//NoStem
		TrecQResultFile tqres = new TrecQResultFile("trec/resultados_asg_nostem");
		TrecMetric metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8367, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8099, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8380, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		
		//Porter
		tqres = new TrecQResultFile("trec/resultados_asg_porter");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7640, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7196, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7200, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

		//RSLP
		tqres = new TrecQResultFile("trec/resultados_asg_rslp");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.6394, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.5776, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.5730, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

		//RSLP-S
		tqres = new TrecQResultFile("trec/resultados_asg_rslps");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8152, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7808, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7930, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		
		//UniNE
		tqres = new TrecQResultFile("trec/resultados_asg_unine");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7861, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7430, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7440, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

	}
	
	@Test
	public void testarMetricasDsg() {
		TrecQRelFile tqrel = new TrecQRelFile("trec/qrels_dsg");
		
		//NoStem
		TrecQResultFile tqres = new TrecQResultFile("trec/resultados_dsg_nostem");
		TrecMetric metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8935, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8800, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7870, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		
		//Porter
		tqres = new TrecQResultFile("trec/resultados_dsg_porter");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8010, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7293, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7030, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

		//RSLP
		tqres = new TrecQResultFile("trec/resultados_dsg_rslp");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7141, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.6466, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.6100, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

		//RSLP-S
		tqres = new TrecQResultFile("trec/resultados_dsg_rslps");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8589, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8283, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7500, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		
		//UniNE
		tqres = new TrecQResultFile("trec/resultados_dsg_unine");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8803, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8403, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7700, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

	}
	
	@Test
	public void testarMetricasAtr() {
		TrecQRelFile tqrel = new TrecQRelFile("trec/qrels_atr");
		
		//NoStem
		TrecQResultFile tqres = new TrecQResultFile("trec/resultados_atr_nostem");
		TrecMetric metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7718, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7595, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7090, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		
		//Porter
		tqres = new TrecQResultFile("trec/resultados_atr_porter");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7444, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.6946, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.6540, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

		//RSLP
		tqres = new TrecQResultFile("trec/resultados_atr_rslp");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7242, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.6618, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.6150, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

		//RSLP-S
		tqres = new TrecQResultFile("trec/resultados_atr_rslps");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7972, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7577, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7170, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		
		//UniNE
		tqres = new TrecQResultFile("trec/resultados_atr_unine");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8247, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7699, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7360, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

	}
	

	@Test
	public void testarMetricasDtr() {
		TrecQRelFile tqrel = new TrecQRelFile("trec/qrels_dtr");
		
		//NoStem
		TrecQResultFile tqres = new TrecQResultFile("trec/resultados_dtr_nostem");
		TrecMetric metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8744, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8610, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8130, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		
		//Porter
		tqres = new TrecQResultFile("trec/resultados_dtr_porter");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8652, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8166, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7790, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

		//RSLP
		tqres = new TrecQResultFile("trec/resultados_dtr_rslp");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7668, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7011, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.6750, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

		//RSLP-S
		tqres = new TrecQResultFile("trec/resultados_dtr_rslps");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8716, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8201, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7880, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		
		//UniNE
		tqres = new TrecQResultFile("trec/resultados_dtr_unine");
		metrica = new TMap(tqrel.getLines(), tqres.getLines());
		assertEquals(0.8553, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrecR(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7983, Util.truncarDouble(metrica.getAll(), 4), 0.00001);
		metrica = new TPrec10(tqrel.getLines(), tqres.getLines());
		assertEquals(0.7670, Util.truncarDouble(metrica.getAll(), 4), 0.00001);

	}
	

}
