package nye.progtech.model;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MapVO {

    public static MapVOBuilder builder() {
        return new MapVOBuilder();
    }

    private final int mapSize;
    private final int[][] map;


    public MapVO(int mapSize, int[][] map) {
        this.mapSize = mapSize;
        this.map = deepcopy(map);
    }
    public int getMapSize() {
        return mapSize;
    }

    public int[][] getMap() {
        return deepcopy(map);
    }

    private int[][] deepcopy(int[][] array) {
        int[][] result = null;

        if (array != null) {
            result = new int[array.length][];
            for (int i = 0; i < array.length; i++) {
                result[i] = Arrays.copyOf(array[i], array[i].length);
            }
        }
        return result;
    }



    public static final class MapVOBuilder {
        private int mapSize;
        private int[][] map;

        private MapVOBuilder(){
        }

        public static MapVOBuilder builder() {
            return new MapVOBuilder();
        }

        public MapVOBuilder withMapSize(int mapSize){
            this.mapSize = mapSize;
            return this;
        }

        public MapVOBuilder withMap(int[][] map) {
            this.map = map;
            return this;
        }

        public MapVO build() {
            return new MapVO(mapSize, map);
        }

    }
}
