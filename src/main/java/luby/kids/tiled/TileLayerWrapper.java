package luby.kids.tiled;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class TileLayerWrapper {
    private static final Logger logger = Logger.getLogger(TileLayerWrapper.class.getName());

    private TileLayer layer;
    public TileLayer getLayer() {
        return this.layer;
    }
    public void setLayer(TileLayer layer) {
        this.layer = layer;
    }

    private int[][] tileIds;
    public int[][] getTileIds() {
        return tileIds;
    }
    public void setTileIds(int[][] tileIds) {
        this.tileIds = tileIds;
    }

    public TileLayerWrapper(TileLayer layer) throws IOException {
        setLayer(layer);
        setTileIds(readTileIds());
    }

    private int[][] readTileIds() throws IOException {
        int[][] tileIds = new int[layer.getHeight()][layer.getWidth()];
        // Fill with 0s
        for (int y = 0; y < layer.getHeight(); y++) {
            for (int x = 0; x < layer.getWidth(); x++) {
                tileIds[y][x] = 0;
            }
        }
        Data data = layer.getData();
        if (data != null) {
            Encoding encoding = (data.getEncoding() != null ? data.getEncoding() : Encoding.CSV);
            Compression compression = data.getCompression();
            String dataValue = data.getValue().trim();
            int[] parsedTileIds = new int[tileIds.length * tileIds[0].length];
            for (int idNum = 0; idNum < parsedTileIds.length; idNum++) {
                parsedTileIds[idNum] = 0;
            }
            switch (encoding) {
                case BASE_64: {
                    InputStream compressedStream = new ByteArrayInputStream(Base64.getDecoder().decode(dataValue));
                    InputStream uncompressedStream;
                    if (compression != null) {
                        switch (compression) {
                            case ZLIB: {
                                uncompressedStream = new InflaterInputStream(compressedStream);
                            } break;
                            default: {
                                uncompressedStream = new GZIPInputStream(compressedStream);
                            } break;
                        }
                    } else {
                        uncompressedStream = compressedStream;
                    }
                    for (int idNum = 0; idNum < parsedTileIds.length; idNum++) {
                        int tileId = 0;
                        tileId |= uncompressedStream.read();
                        tileId |= uncompressedStream.read() << Byte.SIZE;
                        tileId |= uncompressedStream.read() << Byte.SIZE * 2;
                        tileId |= uncompressedStream.read() << Byte.SIZE * 3;
                        parsedTileIds[idNum] = tileId;
                    }
                } break;
                case CSV: {
                    int idNum = 0;
                    for (String idStr : dataValue.split("[\\s]*,[\\s]*")) {
                        parsedTileIds[idNum] = Integer.parseInt(idStr);
                        idNum++;
                    }
                } break;
            }
            int idNum = 0;
            for (int y = 0; y < layer.getHeight(); y++) {
                for (int x = 0; x < layer.getWidth(); x++) {
                    tileIds[y][x] = parsedTileIds[idNum];
                    idNum++;
                }
            }
        }
        return tileIds;
    }
}
