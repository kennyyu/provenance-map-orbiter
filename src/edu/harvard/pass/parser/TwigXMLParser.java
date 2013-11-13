package edu.harvard.pass.parser;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import edu.harvard.pass.PMeta;
import edu.harvard.util.ParserException;
import edu.harvard.util.ParserFormatException;
import edu.harvard.util.Utils;

public class TwigXMLParser implements Parser {

	public TwigXMLParser() {
	}

	@Override
	public void initialize(URI uri) throws ParserException {
		if (!accepts(uri)) throw new ParserFormatException();
	}

	@Override
	public void parse(URI uri, ParserHandler handler) throws ParserException {
		if (!"file".equals(uri.getScheme())) throw new ParserFormatException();
		PMeta meta = PMeta.PASS();
		meta.setCaseSensitive(false);
		handler.setMeta(meta);
		File file = new File(uri);
		Builder builder = new Builder();
		try {
			handler.beginParsing();
			Document doc = builder.build(file);
			parseDocument(doc, handler);
			handler.endParsing();
		} catch (ValidityException e) {
			throw new ParserException(e);
		} catch (ParsingException e) {
			throw new ParserException(e);
		} catch (IOException e) {
			throw new ParserException(e);
		}
	}

	private void parseDocument(Document doc, ParserHandler handler) throws ParserException {
		Element passdata = doc.getRootElement();
		Elements provenances = passdata.getChildElements();
		for (int i = 0; i < provenances.size(); i++) {
			Element provenance = provenances.get(i);
			String pnode = provenance.getAttributeValue("pnode").trim();
			String version = provenance.getAttributeValue("version").trim();
			Elements records = provenance.getChildElements();
			for (int r = 0; r < records.size(); r++) {
				Element record = records.get(r);
				Element recordType = record.getFirstChildElement("record-type");
				String type = recordType.getValue().trim();
				Element recordData = record.getFirstChildElement("record-data");
				Element data = recordData.getFirstChildElement("data");
				Element xref = recordData.getFirstChildElement("xref");
				if (data == null && xref == null) {
					// UNLINK case
					handler.loadTripleAttribute(
						pnode + "." + version,
						type,
						"");
				} else if (data != null) {
					String value = data.getValue().trim();
					handler.loadTripleAttribute(
						pnode + "." + version,
						type,
						value);
				} else if (xref != null) {
					String otherpnode = xref.getAttributeValue("pnode").trim();
					String otherversion = xref.getAttributeValue("version").trim();
					handler.loadTripleAncestry(
						pnode + "." + version,
						type,
						otherpnode + "." + otherversion);
				}
			}
		}
	}

	@Override
	public boolean accepts(URI uri) {
		if (!"file".equals(uri.getScheme())) return false;
		File file = new File(uri);
		String ext = Utils.getExtension(file);
		
		if ("twigxml".equals(ext)) return true;
		
		return false;
	}

}
