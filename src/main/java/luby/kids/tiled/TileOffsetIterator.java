package luby.kids.tiled;

import java.util.Iterator;

public class TileOffsetIterator implements Iterator<TileOffset> {
	private int tileWidth;
	private int tileHeight;
	private int tileMargin;
	private int tileSpacing;
	
	private int imageWidth;
	private int imageHeight;
	
	private int nextX = 0;
	private int nextY = 0;
	
	public TileOffsetIterator(
			int imageWidth, int imageHeight,
			int width, int height,
			int offsetX, int offsetY,
			int margin, int space
	) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.tileWidth = width;
		this.tileHeight = height;
		this.tileMargin = margin;
		this.tileSpacing = space;
		
		this.nextX = offsetX + tileMargin;
		this.nextY = offsetY + tileMargin;
	}
	
	public void setTileOffset(int tileOffsetX, int tileOffsetY) {
		this.nextX = tileMargin + tileOffsetX;
		this.nextY = tileMargin + tileOffsetY;
	}

	@Override
	public boolean hasNext() {
		return (nextY + tileHeight + tileMargin <= imageHeight);
	}

	@Override
	public TileOffset next() {
		if (nextY + tileHeight + tileMargin <= imageHeight) {

			TileOffset tileOffset = new TileOffset();
			tileOffset.setX(nextX);
			tileOffset.setY(nextY);

			nextX += tileWidth + tileSpacing;
			if (nextX + tileWidth + tileMargin > imageWidth) {
				nextX = tileMargin;
				nextY += tileHeight + tileSpacing;
			}

			return tileOffset;
		}

		return null;
	}
}
