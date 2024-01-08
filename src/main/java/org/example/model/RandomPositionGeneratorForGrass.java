package org.example.model;

import java.util.*;

public class RandomPositionGeneratorForGrass implements Iterable<Vector2d>{
    List<Vector2d> positions = new ArrayList<>();
    final Map<Vector2d, MapField> fields;
    public RandomPositionGeneratorForGrass(Map<Vector2d, MapField> fields, int maxWidth, int maxHeight, int grassCount){
        this.fields = fields;
        generateRandomPositions(maxWidth, maxHeight, grassCount);
    }

    private void generateRandomPositions(int width, int height, int grassCount){
        List<Vector2d> positionsList = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Vector2d position = new Vector2d(j, i);
                if (fields.get(position).grass == null){
                    if (fields.get(position).preferred){
                        positionsList.add(position);
                        positionsList.add(position);
                        positionsList.add(position);
                    }
                    positionsList.add(position);
                }
            }
        }

        Collections.shuffle(positionsList);

        int i = 0;
        while (i < grassCount && i < fields.size() && !positionsList.isEmpty()){
            Vector2d position = positionsList.get(0);
            this.positions.add(positionsList.get(0));
            positionsList.removeAll(Collections.singleton(position));
            i++;
        }
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<Vector2d>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < positions.size();
            }

            @Override
            public Vector2d next() {
                return positions.get(currentIndex++);
            }
        };
    }
}