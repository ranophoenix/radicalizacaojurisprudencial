package experimento.trec;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import experimento.ExperimentoException;

public class TQueryFile {
	private List<TrecQuery> queries = new LinkedList<>();

	public List<TrecQuery> getLines() {
		return Collections.unmodifiableList(queries);
	}

	public TQueryFile(String filename) throws ExperimentoException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(filename);
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());

					String query = node.getChildNodes().item(0).getNodeValue();

					queries.add(new TrecQuery(id, query));
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ExperimentoException(e);
		}

	}

}
