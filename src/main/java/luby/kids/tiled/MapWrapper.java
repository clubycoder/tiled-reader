package luby.kids.tiled;

import java.io.IOException;

public class MapWrapper {
    private Map map;
    public Map getMap() {
        return this.map;
    }
    public void setMap(Map map) {
        this.map = map;
    }

    private TileLayerWrapper[] tileLayers;
    public TileLayerWrapper[] getTileLayers() {
        return this.tileLayers;
    }
    public void setTileLayers(TileLayerWrapper[] tileLayers) {
        this.tileLayers = tileLayers;
    }

    public MapWrapper(Map map) throws IOException {
        setMap(map);
        setTileLayers(readTileLayers());
    }

    private TileLayerWrapper[] readTileLayers() throws IOException {
        TileLayerWrapper[] tileLayers = new TileLayerWrapper[getMap().getLayer().size()];
        for (int layerNum = 0; layerNum < tileLayers.length; layerNum++) {
            tileLayers[layerNum] = new TileLayerWrapper(getMap().getLayer().get(layerNum));
        }
        return tileLayers;
    }
}
