package experimento.colecao;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import experimento.ExperimentoException;

public class LeitorXMLFeeder {
	private Colecao colecao;
	protected final Logger log = Logger.getLogger(this.getClass().getName());
	
	public LeitorXMLFeeder(Colecao colecao) {
		this.colecao = colecao;
	}

	public void processar(Consumer<Documento> callback) throws ExperimentoException {
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get("feeders/" + colecao.getAlias()), "*.xml")) {
			dirStream.forEach(path -> readXML(path, callback));
		} catch (IOException e) {
			throw new ExperimentoException("Erro ao ler XML.", e);
		}
	}

	protected void readXML(Path path, Consumer<Documento> processaDocumento) throws ExperimentoException {
		String campo = "";
		String chave = "";
		boolean doc = false;
		StringBuilder textoDocumento = new StringBuilder();

		try {
			log.info("Processando arquivo: " + path);
			XMLInputFactory factory = XMLInputFactory.newInstance();			
			XMLEventReader eventReader = factory.createXMLEventReader(Files.newInputStream(path), "ISO-8859-1");

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				switch (event.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					StartElement startElement = event.asStartElement();
					String qName = startElement.getName().getLocalPart();
					if (qName.equalsIgnoreCase("doc")) {
						doc = true;
					} else if (qName.equalsIgnoreCase("field")) {
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement.getAttributes();
						String name = attributes.next().getValue();
						campo = name;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					if (doc) {
						if (campo != "") {
							Characters caracteres = event.asCharacters();
							if (colecao.getCampoChave().equals(campo)) {
								chave = caracteres.getData();
							} else if (colecao.getCamposTextuais().contains(campo)) {
								String texto = caracteres.getData();
								if (texto != null && !"".equals(texto))
									textoDocumento.append(texto);
							}
							campo = "";
						}
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					EndElement endElement = event.asEndElement();
					/*
					 * Não processa documentos com o texto vazio. ASG - 0
					 * documentos sem texto, DSG - 98 documentos sem texto, ATR
					 * - 0 documentos sem texto, DTR - 2 documentos sem texto
					 */
					if (endElement.getName().getLocalPart().equalsIgnoreCase("doc")) {
						String texto = textoDocumento.toString().trim();
						if (!"".equals(texto)) {
							Documento documento = new Documento(chave, texto);
							processaDocumento.accept(documento);
						}
						textoDocumento = new StringBuilder();
						doc = false;
						chave = "";
					}
					break;
				}
			}
		} catch (Exception e) {
			throw new ExperimentoException("Erro durante a leitura dos feeders.", e);
		} 
	}

}
