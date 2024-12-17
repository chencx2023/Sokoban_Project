package view.level;

import model.MapMatrix;

public class Level {
    private MapMatrix mapMatrix;
    private int id;

    public Level(MapMatrix mapMatrix){
        id++;
        this.mapMatrix = mapMatrix;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MapMatrix getMapMatrix() {
        return mapMatrix;
    }

    public void setMapMatrix(MapMatrix mapMatrix) {
        this.mapMatrix = mapMatrix;
    }
}
