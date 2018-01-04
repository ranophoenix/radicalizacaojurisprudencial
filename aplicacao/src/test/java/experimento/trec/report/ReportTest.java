package experimento.trec.report;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import experimento.stemmer.NoStem;
import experimento.stemmer.Porter;
import experimento.stemmer.RSLP;
import experimento.stemmer.RSLPS;
import experimento.stemmer.UniNE;
import experimento.trec.TQueryFile;
import experimento.trec.TrecQRel;
import experimento.trec.TrecQRelFile;
import experimento.trec.TrecQRelSet;
import experimento.trec.TrecQResult;
import experimento.trec.TrecQResultFile;
import experimento.trec.TrecQResultSet;
import experimento.trec.metric.TMap;
import experimento.trec.metric.TPrec10;
import experimento.trec.metric.TPrecR;
import experimento.trec.metric.TrecMetric;
import experimento.util.Util;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;

public class ReportTest {

	@SuppressWarnings("unchecked")
	public static boolean isRelevant(TemplateModel relevantsModel, TrecQResult result) throws TemplateModelException {
		List<TrecQRel> relevants = (List<TrecQRel>) DeepUnwrap.unwrap(relevantsModel);
		TrecQRel rel = new TrecQRel(result.getQid(), result.getQ0(), result.getDocno(), 0);
		return relevants.contains(rel);
	}

	public static String aplicarAlgoritmo(String algoritmo, String texto) {
		String normalizado = Util.normalizar(texto);
		try {
		switch (algoritmo) {
		case "NoStem":
			return new NoStem().stem(normalizado).toString();
		case "Porter":
			return new Porter().stem(normalizado).toString();
		case "RSLP":
			return new RSLP().stem(normalizado).toString();
		case "RSLP-S":
			return new RSLPS().stem(normalizado).toString();
		case "UniNE":
			return new UniNE().stem(normalizado).toString();			
		default:
			return texto;
		}
		} catch (Exception e) {
			System.out.println(normalizado);
		}
		return "";

	}

	@Test
	public void gerarRelatorioAsg() throws Exception {
		TrecQRelFile tqrel = new TrecQRelFile("trec/qrels_asg");

		FreeMarkerReportBuilder report = new FreeMarkerReportBuilder("Coleção ASG", tqrel.getLines());
		report.comQueries("asg");

		// NoStem
		TrecQResultFile tqres = new TrecQResultFile("trec/resultados_asg_nostem");

		report.comAlgoritmo("NoStem", tqres.getLines());

		// Porter
		tqres = new TrecQResultFile("trec/resultados_asg_porter");

		report.comAlgoritmo("Porter", tqres.getLines());

		// RSLP
		tqres = new TrecQResultFile("trec/resultados_asg_rslp");

		report.comAlgoritmo("RSLP", tqres.getLines());

		// RSLP-S
		tqres = new TrecQResultFile("trec/resultados_asg_rslps");

		report.comAlgoritmo("RSLP-S", tqres.getLines());

		// UniNE
		tqres = new TrecQResultFile("trec/resultados_asg_unine");

		report.comAlgoritmo("UniNE", tqres.getLines());

		File arquivo = new File("trec/report_asg.html");

		report.build(arquivo);

		assertTrue(arquivo.exists());

	}

	class FreeMarkerReportBuilder {
		private Map<String, Object> root = new HashMap<>();
		private Map<String, Map<String, Object>> algoritmos = new LinkedHashMap<>();
		private TrecQRelSet relevants;

		public FreeMarkerReportBuilder(String nomeDaColecao, TrecQRelSet relevants) {
			root.put("nome", nomeDaColecao);
			this.relevants = relevants;
			root.put("relevantes", relevants);
			Map<Integer, List<TrecQRel>> relevantesPorConsulta = relevants.stream()
					.collect(Collectors.groupingBy(TrecQRel::getQid));
			root.put("totalDeQueries", relevantesPorConsulta.keySet().size());
			root.put("relevantesPorConsulta", relevantesPorConsulta);
		}

		public void build(File arquivo) throws Exception {
			root.put("algoritmos", algoritmos);

			BeansWrapper wrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_25).build();
			TemplateHashModel staticModels = wrapper.getStaticModels();
			TemplateHashModel util = (TemplateHashModel) staticModels.get("experimento.trec.report.ReportTest");

			root.put("Util", util);

			Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
			cfg.setDefaultEncoding("ISO-8859-1");
			cfg.setDirectoryForTemplateLoading(new File("trec"));
			cfg.setAPIBuiltinEnabled(true);
			Template template = cfg.getTemplate("template_report.ftl");

			Writer out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(arquivo), Charset.forName("ISO-8859-1")));

			template.process(root, out);

			out.close();

		}

		public FreeMarkerReportBuilder comAlgoritmo(String algoritmo, TrecQResultSet result) {
			Map<String, Object> metricas = new HashMap<>();
			Map<Integer, List<TrecQResult>> resultadoPorConsulta = result.stream()
					.collect(Collectors.groupingBy(TrecQResult::getQid));
			metricas.put("ROWS", resultadoPorConsulta);
			TrecMetric metrica = new TMap(relevants, result);
			metricas.put("MAP", metrica);
			metrica = new TPrecR(relevants, result);
			metricas.put("MRP", metrica);
			metrica = new TPrec10(relevants, result);
			metricas.put("MPC(10)", metrica);

			algoritmos.put(algoritmo, metricas);

			return this;
		}

		public FreeMarkerReportBuilder comQueries(String nomeColecao) {
			TQueryFile consultas = new TQueryFile("queries/" + nomeColecao + ".xml");

			consultas.getLines().forEach(q -> {
				root.put("q" + q.getQid(), q.getQuery());
			});

			return this;
		}

	}

}
