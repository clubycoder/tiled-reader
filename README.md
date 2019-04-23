# Java Taled Tileset & Map Reader
  
Minimalistic Java reader for Tiled (https://www.mapeditor.org) TMX & TSX files

## Status

[![Build Status](https://travis-ci.org/clubycoder/tiled-reader.svg?branch=master)](https://travis-ci.org/clubycoder/tiled-reader)

## Implementation

The majority of the work is done by generating XML reader classes from
the [Tiled XSD](https://raw.githubusercontent.com/bjorn/tiled/master/util/java/libtiled-java/src/main/resources/map.xsd).

Using the generated classes there are a couple of wrapper classes that
add in the logic for parsing each layers tile IDs and normalizing the
tileset image offsets.

## Usage

For example usage see:

[TSX Test](src/test/java/luby/kids/tiled/TiledTMXReaderTest.java) &
[TMX Test](src/test/java/luby/kids/tiled/TiledTMXReaderTest.java)

## Maven

The build is deployed to bintray.

```xml
  <repositories>
    <repository>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
      <id>bintray-clubycoder-kids</id>
      <name>bintray-clubycoder-kids</name>
      <url>https://dl.bintray.com/clubycoder/kids</url>
    </repository>
  </repositories>
```
```xml
  <dependencies>
    <dependency>
      <groupId>luby.kids</groupId>
      <artifactId>tiled-reader</artifactId>
      <version>0.1.0</version>
    </dependency>
  </dependencies>
```
