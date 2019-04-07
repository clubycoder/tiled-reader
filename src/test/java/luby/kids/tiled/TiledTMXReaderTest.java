package luby.kids.tiled;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import org.junit.Test;

public class TiledTMXReaderTest {
    private static final Logger logger = Logger.getLogger(TiledTMXReaderTest.class.getName());

    @Test
    public void testEmptyTMX() throws Exception {
        TiledReader reader = new TiledReader();

        // From filename
        MapWrapper map1 = reader.readTMX("src/test/resources/map-empty.tmx");
        assertEquals("1.2.3", map1.getMap().getTiledversion());

        // From File
        MapWrapper map2 = reader.readTMX(new File("src/test/resources/map-empty.tmx"));
        assertEquals("1.2.3", map2.getMap().getTiledversion());

        // From InputStream
        MapWrapper map3 = reader.readTMX(getClass().getClassLoader().getResourceAsStream("map-empty.tmx"));
        assertEquals("1.2.3", map3.getMap().getTiledversion());
    }

    @Test
    public void testMap1() throws Exception {
        TiledReader reader = new TiledReader();

        MapWrapper map = reader.readTMX(getClass().getClassLoader().getResourceAsStream("map-test1.tmx"));
        assertEquals("1.2.3", map.getMap().getTiledversion());
        assertEquals(16, map.getMap().getWidth());
        assertEquals(16, map.getMap().getHeight());
        assertEquals(64, map.getMap().getTilewidth());
        assertEquals(64, map.getMap().getTilewidth());
        assertEquals(RenderOrder.LEFT_DOWN, map.getMap().getRenderorder());

        // Tileset references
        assertEquals(4, map.getMap().getTileset().size());
        TileSet tileSet1 = map.getMap().getTileset().get(0);
        assertEquals("tileset1.tsx", tileSet1.getSource());
        assertEquals(1, tileSet1.getFirstgid().intValue());
        TileSet tileSet2 = map.getMap().getTileset().get(1);
        assertEquals("tileset-singles1.tsx", tileSet2.getSource());
        assertEquals(5, tileSet2.getFirstgid().intValue());
        TileSet tileSet3 = map.getMap().getTileset().get(2);
        assertEquals("tileset2.tsx", tileSet3.getSource());
        assertEquals(7, tileSet3.getFirstgid().intValue());
        TileSet tileSet4 = map.getMap().getTileset().get(3);
        assertEquals("tileset-animated1.tsx", tileSet4.getSource());
        assertEquals(11, tileSet4.getFirstgid().intValue());

        // Layers
        assertEquals(2, map.getMap().getLayer().size());
        TileLayer layer1 = map.getMap().getLayer().get(0);
        assertEquals("ground", layer1.getName());
        assertEquals(Encoding.CSV, layer1.getData().getEncoding());
        TileLayer layer2 = map.getMap().getLayer().get(1);
        assertEquals("decorations", layer2.getName());
        assertEquals(Encoding.CSV, layer2.getData().getEncoding());

        // Layer Wrappers
        assertEquals(2, map.getTileLayers().length);
        TileLayerWrapper tileLayer1 = map.getTileLayers()[0];
        assertTrue(Arrays.deepEquals(new int[][] {
                {2,3,3,3,2,1,1,1,0,0,0,0,0,0,0,0},
                {4,2,3,2,4,1,1,1,0,0,0,0,0,0,0,0},
                {4,4,2,4,4,1,1,1,0,0,0,0,0,0,0,0},
                {4,2,3,2,4,1,1,1,0,0,0,0,0,0,0,0},
                {2,3,3,3,2,1,1,1,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
                {1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        }, tileLayer1.getTileIds()));
        TileLayerWrapper tileLayer2 = map.getTileLayers()[1];
        assertTrue(Arrays.deepEquals(new int[][] {
                {5,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0},
                {0,6,0,6,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,6,0,6,0,0,0,0,0,0,0,0,0,0,0,0},
                {5,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,8,0,9,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,11,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,9,0,10,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        }, tileLayer2.getTileIds()));

        // Objects
        assertEquals(1, map.getMap().getObjectgroup().size());
        ObjectGroup objects1 = map.getMap().getObjectgroup().get(0);
        assertEquals("objects", objects1.getName());
        assertEquals(2, objects1.getObject().size());
        luby.kids.tiled.Object object1 = objects1.getObject().get(0);
        assertEquals("spawnPoint1", object1.getName());
        assertEquals("spawnPoint", object1.getProperties().getProperty().get(0).getName());
        assertEquals("bool", object1.getProperties().getProperty().get(0).getType().value());
        assertEquals("true", object1.getProperties().getProperty().get(0).getValue());
        luby.kids.tiled.Object object2 = objects1.getObject().get(1);
        assertEquals("goal1", object2.getName());
        assertEquals("goal", object2.getProperties().getProperty().get(0).getName());
        assertEquals("bool", object2.getProperties().getProperty().get(0).getType().value());
        assertEquals("true", object2.getProperties().getProperty().get(0).getValue());
    }
}