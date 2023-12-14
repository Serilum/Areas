package com.natamus.areas.integrations;

import com.natamus.areas.data.AreaVariables;
import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.Marker;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.ShapeMarker;
import de.bluecolored.bluemap.api.math.Color;
import de.bluecolored.bluemap.api.math.Shape;

public class BlueMapIntegration {
    public static void updateBlueMap() {
        try {
            BlueMapAPI.getInstance().ifPresent(BlueMapIntegration::updateBlueMap);
        } catch (NoClassDefFoundError | IllegalStateException ignore) {
            System.out.println("BlueMap not loaded");
        }
    }

    public static void updateBlueMap(BlueMapAPI api) {
        final String areasId = "serilum-areas";
        final String areasLabel = "Areas";

        AreaVariables.areaObjects.forEach((level, areaHashMap) -> {
            final String worldId = level.dimension().location().getPath();
            final BlueMapMap map = api.getMaps().stream().filter(map1 -> worldId.contains(map1.getId())).findFirst().orElse(null);
            if (map == null) {
                return;
            }

            MarkerSet markerSet = MarkerSet.builder().label(areasLabel).build();
            markerSet.getMarkers().clear();
            map.getMarkerSets().put(areasId, markerSet);

            areaHashMap.forEach((blockPos, area) -> {
                Color fillColor = new Color(255, 255, 255, 0.5F);
                Color lineColor = new Color(0, 0, 0, 1.0F);
                if (area.customRGB != null && !area.customRGB.isEmpty()) {
                    String[] splits = area.customRGB.split(",");
                    java.awt.Color newColor = new java.awt.Color(Integer.parseInt(splits[0]), Integer.parseInt(splits[1]), Integer.parseInt(splits[2]));
                    fillColor = new Color(newColor.getRGB(), 0.5F);
                    lineColor = new Color(newColor.darker().getRGB());
                }

                final Marker areaMarker = ShapeMarker.builder()
                        .shape(Shape.createCircle(area.location.getX(), area.location.getZ(), area.radius, 20), level.getSeaLevel())
                        .centerPosition()
                        .label(area.areaName)
                        .depthTestEnabled(false)
                        .fillColor(fillColor)
                        .lineColor(lineColor)
                        .build();

                String markerId = "areas_" + worldId + "_" + area.areaName + "_" + blockPos.toShortString();
                markerSet.getMarkers().put(markerId, areaMarker);
            });
        });
    }
}
