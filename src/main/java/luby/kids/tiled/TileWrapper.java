package luby.kids.tiled;

public class TileWrapper {
    private Tile tile;
    public Tile getTile() {
        return this.tile;
    }
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    private int width;
    public int getWidth() {
        return this.width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    private int height;
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    private TileOffset offset;
    public TileOffset getOffset() {
        return this.offset;
    }
    public void setOffset(TileOffset offset) {
        this.offset = offset;
    }

    public TileWrapper(Tile tile) {
        setTile(tile);
        setWidth(tile.getImage() != null && tile.getImage().getWidth() != null ? tile.getImage().getWidth() : 0);
        setHeight(tile.getImage() != null && tile.getImage().getHeight() != null ? tile.getImage().getHeight() : 0);
    }
}
