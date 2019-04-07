package luby.kids.tiled;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import org.junit.Test;

public class TiledTSXReaderTest {
    private static final Logger logger = Logger.getLogger(TiledTSXReaderTest.class.getName());

    @Test
    public void testEmptyTSX() throws Exception {
        TiledReader reader = new TiledReader();

        // From filename
        TileSetWrapper tileSet1 = reader.readTSX("src/test/resources/tileset-empty.tsx");
        assertEquals("Empty", tileSet1.getTileSet().getName());

        // From File
        TileSetWrapper tileSet2 = reader.readTSX(new File("src/test/resources/tileset-empty.tsx"));
        assertEquals("Empty", tileSet2.getTileSet().getName());

        // From InputStream
        TileSetWrapper tileSet3 = reader.readTSX(getClass().getClassLoader().getResourceAsStream("tileset-empty.tsx"));
        assertEquals("Empty", tileSet3.getTileSet().getName());
    }

    @Test
    public void testTileSet1() throws Exception {
        TiledReader reader = new TiledReader();

        TileSetWrapper tileSet = reader.readTSX(getClass().getClassLoader().getResourceAsStream("tileset1.tsx"));
        assertEquals("tileset1", tileSet.getTileSet().getName());
        assertEquals(4, tileSet.getTileSet().getTilecount().intValue());
        assertEquals(64, tileSet.getTileSet().getTilewidth());
        assertEquals(64, tileSet.getTileSet().getTileheight());
        assertEquals("tileset1.png", tileSet.getTileSet().getImage().getSource());

        // Tile Wrappers
        assertEquals(4, tileSet.getTiles().size());
        TileWrapper tile1 = tileSet.getTiles().get(0);
        assertEquals(0, tile1.getTile().getId().intValue());
        assertEquals("tileset1.png", tile1.getTile().getImage().getSource());
        assertEquals(0, tile1.getOffset().getX().intValue());
        assertEquals(0, tile1.getOffset().getY().intValue());
        assertEquals(64, tile1.getWidth());
        assertEquals(64, tile1.getHeight());
        TileWrapper tile2 = tileSet.getTiles().get(1);
        assertEquals(1, tile2.getTile().getId().intValue());
        assertEquals("tileset1.png", tile2.getTile().getImage().getSource());
        assertEquals(64, tile2.getOffset().getX().intValue());
        assertEquals(0, tile2.getOffset().getY().intValue());
        assertEquals(64, tile2.getWidth());
        assertEquals(64, tile2.getHeight());
        TileWrapper tile3 = tileSet.getTiles().get(2);
        assertEquals(2, tile3.getTile().getId().intValue());
        assertEquals("tileset1.png", tile3.getTile().getImage().getSource());
        assertEquals(0, tile3.getOffset().getX().intValue());
        assertEquals(64, tile3.getOffset().getY().intValue());
        assertEquals(64, tile3.getWidth());
        assertEquals(64, tile3.getHeight());
        TileWrapper tile4 = tileSet.getTiles().get(3);
        assertEquals(3, tile4.getTile().getId().intValue());
        assertEquals("tileset1.png", tile4.getTile().getImage().getSource());
        assertEquals(64, tile4.getOffset().getX().intValue());
        assertEquals(64, tile4.getOffset().getY().intValue());
        assertEquals(64, tile4.getWidth());
        assertEquals(64, tile4.getHeight());
    }

    @Test
    public void testTileSetSingles1() throws Exception {
        TiledReader reader = new TiledReader();

        TileSetWrapper tileSet = reader.readTSX(getClass().getClassLoader().getResourceAsStream("tileset-singles1.tsx"));
        assertEquals("tileset-singles1", tileSet.getTileSet().getName());
        assertEquals(2, tileSet.getTileSet().getTilecount().intValue());
        assertEquals(64, tileSet.getTileSet().getTilewidth());
        assertEquals(128, tileSet.getTileSet().getTileheight());

        // Tile Wrappers
        assertEquals(2, tileSet.getTiles().size());
        TileWrapper tile1 = tileSet.getTiles().get(0);
        assertEquals(0, tile1.getTile().getId().intValue());
        assertEquals("single-tile1.png", tile1.getTile().getImage().getSource());
        assertEquals(0, tile1.getOffset().getX().intValue());
        assertEquals(0, tile1.getOffset().getY().intValue());
        assertEquals(64, tile1.getWidth());
        assertEquals(128, tile1.getHeight());
        TileWrapper tile2 = tileSet.getTiles().get(1);
        assertEquals(1, tile2.getTile().getId().intValue());
        assertEquals("single-tile2.png", tile2.getTile().getImage().getSource());
        assertEquals(0, tile2.getOffset().getX().intValue());
        assertEquals(0, tile2.getOffset().getY().intValue());
        assertEquals(64, tile2.getWidth());
        assertEquals(128, tile2.getHeight());
    }

    @Test
    public void testTileSetAnimated1() throws Exception {
        TiledReader reader = new TiledReader();

        TileSetWrapper tileSet = reader.readTSX(getClass().getClassLoader().getResourceAsStream("tileset-animated1.tsx"));
        assertEquals("tileset-animated1", tileSet.getTileSet().getName());
        assertEquals(6, tileSet.getTileSet().getTilecount().intValue());
        assertEquals(64, tileSet.getTileSet().getTilewidth());
        assertEquals(64, tileSet.getTileSet().getTileheight());
        assertEquals("tileset-animated1.png", tileSet.getTileSet().getImage().getSource());

        // Tile Wrappers
        assertEquals(6, tileSet.getTiles().size());
        for (int tileId = 0; tileId < 6; tileId++) {
            TileWrapper tile = tileSet.getTiles().get(tileId);
            assertEquals(tileId, tile.getTile().getId().intValue());
            assertEquals("tileset-animated1.png", tile.getTile().getImage().getSource());
            assertEquals(tileId * 64, tile.getOffset().getX().intValue());
            assertEquals(0, tile.getOffset().getY().intValue());
            assertEquals(64, tile.getWidth());
            assertEquals(64, tile.getHeight());
        }

        // Animation
        TileWrapper tile1 = tileSet.getTiles().get(0);
        Animation animation1 = tile1.getTile().getAnimation();
        assertEquals(6, animation1.getFrame().size());
        assertEquals(0, animation1.getFrame().get(0).getTileid().intValue());
        assertEquals(200, animation1.getFrame().get(0).getDuration().intValue());
        assertEquals(1, animation1.getFrame().get(1).getTileid().intValue());
        assertEquals(200, animation1.getFrame().get(1).getDuration().intValue());
        assertEquals(2, animation1.getFrame().get(2).getTileid().intValue());
        assertEquals(200, animation1.getFrame().get(2).getDuration().intValue());
        assertEquals(3, animation1.getFrame().get(3).getTileid().intValue());
        assertEquals(200, animation1.getFrame().get(3).getDuration().intValue());
        assertEquals(4, animation1.getFrame().get(4).getTileid().intValue());
        assertEquals(200, animation1.getFrame().get(4).getDuration().intValue());
        assertEquals(5, animation1.getFrame().get(5).getTileid().intValue());
        assertEquals(200, animation1.getFrame().get(5).getDuration().intValue());
    }
}
