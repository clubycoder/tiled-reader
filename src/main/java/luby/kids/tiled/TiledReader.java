package luby.kids.tiled;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TiledReader {
    private static final Logger logger = Logger.getLogger(TiledReader.class.getName());

    private DocumentBuilderFactory factory = null;

    // Constructor
    public TiledReader() {
        factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setExpandEntityReferences(false);
    }

    // Read TMX Maps
    public MapWrapper readTMX(String filename) throws IOException {
        return readTMX(new File(filename));
    }
    public MapWrapper readTMX(File file) throws IOException {
        logger.log(Level.INFO, "Reading TMX " + file.getCanonicalPath());
        InputStream stream = new FileInputStream(file);
        try {
            return readTMX(stream);
        } finally {
            stream.close();
        }
    }
    public MapWrapper readTMX(InputStream stream) throws IOException {
        Map map = readFirstTag(stream, "map", Map.class);
        logger.log(Level.INFO, "Read TMX: " +
                "version = " + (map.getVersion() != null ? map.getVersion() : "unknown") + ", " +
                "tiled-version = " + (map.getTiledversion() != null ? map.getTiledversion() : "unknown") + ", " +
                "width x height = " + map.getWidth() + "x" + map.getHeight() + ", " +
                "tile-width x tile-height = " + map.getTilewidth() + "x" + map.getTileheight()
        );
        return new MapWrapper(map);
    }

    // Read TSX Tile Sets
    public TileSetWrapper readTSX(String filename) throws IOException {
        return readTSX(new File(filename));
    }

    public TileSetWrapper readTSX(File file) throws IOException {
        logger.log(Level.INFO, "Reading TSX " + file.getCanonicalPath());
        InputStream stream = new FileInputStream(file);
        try {
            return readTSX(stream);
        } finally {
            stream.close();
        }
    }

    public TileSetWrapper readTSX(InputStream stream) throws IOException {
        TileSet tileSet = readFirstTag(stream, "tileset", TileSet.class);
        logger.log(Level.INFO, "Read TSX: " +
                "name = " + (tileSet.getName() != null ? tileSet.getName() : "unnamed") + ", " +
                "tile-count = " + (tileSet.getTilecount() != null ? tileSet.getTilecount() : "unknown") + ", " +
                "tile-width x tile-height = " + tileSet.getTilewidth() + "x" + tileSet.getTileheight() + ", " +
                "tiles-offset = (" + (tileSet.getTileoffset() != null ? tileSet.getTileoffset().getX() + ", " + tileSet.getTileoffset().getY() : "0, 0") + "), " +
                "tile-margin = " + (tileSet.getMargin() != null ? tileSet.getMargin() : "0") + ", " +
                "tile-spacing = " + (tileSet.getSpacing() != null ? tileSet.getSpacing() : "0") + ", " +
                "tile-set-image = " + (tileSet.getImage() != null ? tileSet.getImage().getSource() : "none")
        );
        return new TileSetWrapper(tileSet);
    }

    // Generic tag reading
    private <T> T readFirstTag(InputStream stream, String tagName, Class<T> type) throws IOException {
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (Exception e) {
            throw new IOException("Failed to setup XML parsing", e);
        }

        try {
            Document doc = builder.parse(stream, ".");

            NodeList nodes = doc.getElementsByTagName(tagName);
            if (nodes != null && nodes.getLength() == 1) {
                return readTag(nodes.item(0), type);
            } else {
                throw new IllegalArgumentException("Malformed stream");
            }
        } catch (Exception e) {
            throw new IOException("Parse error", e);
        }
    }

    private <T> T readTag(Node node, Class<T> type) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(type);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        JAXBElement<T> element = unmarshaller.unmarshal(node, type);
        return element.getValue();
    }

    // Utils
}
