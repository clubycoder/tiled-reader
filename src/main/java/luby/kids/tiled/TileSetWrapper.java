package luby.kids.tiled;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TileSetWrapper {
    private TileSet tileSet;
    public TileSet getTileSet() {
        return this.tileSet;
    }
    public void setTileSet(TileSet tileSet) {
        this.tileSet = tileSet;
    }

    private Map<Integer, TileWrapper> tiles;
    public Map<Integer, TileWrapper> getTiles() {
        return this.tiles;
    }
    public void setTiles(Map<Integer, TileWrapper> tiles) {
        this.tiles = tiles;
    }

    public TileSetWrapper(TileSet tileSet) throws IOException {
        setTileSet(tileSet);
        setTiles(readTiles());
    }

    private Map<Integer, TileWrapper> readTiles() throws IOException {
        Map<Integer, TileWrapper> tiles = new HashMap<>();
        int tileWidth = tileSet.getTilewidth();
        int tileHeight = tileSet.getTileheight();
        Image image = tileSet.getImage();
        if (image != null && image.getSource() != null) {
            TileOffsetIterator offsetIterator = new TileOffsetIterator(
                    image.getWidth(), image.getHeight(),
                    tileWidth, tileHeight,
                    (tileSet.getTileoffset() != null && tileSet.getTileoffset().getX() != null ? tileSet.getTileoffset().getX() : 0),
                    (tileSet.getTileoffset() != null && tileSet.getTileoffset().getY() != null ? tileSet.getTileoffset().getY() : 0),
                    (tileSet.getMargin() != null ? tileSet.getMargin() : 0),
                    (tileSet.getSpacing() != null ? tileSet.getSpacing() : 0)
            );
            int tileId = 0;
            while (offsetIterator.hasNext()) {
                TileOffset offset = offsetIterator.next();
                Tile tile = new Tile();
                tile.setId(tileId);
                tile.setImage(image);
                TileWrapper tileWrapper = new TileWrapper(tile);
                tileWrapper.setOffset(offset);
                tileWrapper.setWidth(tileWidth);
                tileWrapper.setHeight(tileHeight);
                tiles.put(tileId, tileWrapper);
                tileId++;
            }
        }
        TileOffset offset = new TileOffset();
        offset.setX(0);
        offset.setY(0);
        for (Tile tile : tileSet.getTile()) {
            if (tile.getId() != null) {
                if (tiles.containsKey(tile.getId())) {
                    TileWrapper tileWrapper = tiles.get(tile.getId());
                    tile.setImage(tile.getImage() != null ? tile.getImage() : tileWrapper.getTile().getImage());
                    tileWrapper.setTile(tile);
                } else {
                    TileWrapper tileWrapper = new TileWrapper(tile);
                    tileWrapper.setOffset(offset);
                    tiles.put(tile.getId(), tileWrapper);
                }
            }
        }
        return tiles;
    }
}
