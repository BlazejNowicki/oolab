package agh.ics.oop;

import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    private final SortedSet<MapBoudnaryItem> axisX = new TreeSet<>();
    private final SortedSet<MapBoudnaryItem> axisY = new TreeSet<>();

    public void addObjectToTrack(AbstractMapObject obj){
        Vector2d position = obj.getPosition();
        axisX.add(new MapBoudnaryItem(position.x, position.y));
        axisY.add(new MapBoudnaryItem(position.y, position.x));

        if (obj instanceof Animal){
            ((Animal) obj).addObserver(this);
        }
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        axisX.remove(new MapBoudnaryItem(oldPosition.x, oldPosition.y));
        axisX.add(new MapBoudnaryItem(newPosition.x, newPosition.y));
        axisY.remove(new MapBoudnaryItem(oldPosition.y, oldPosition.x));
        axisY.add(new MapBoudnaryItem(newPosition.y, newPosition.x));
    }

    public Vector2d getLowerBound(){
        return new Vector2d(this.axisX.first().first_axis, this.axisY.first().first_axis);
    }

    public Vector2d getUpperBound(){
        return new Vector2d(this.axisX.last().first_axis, this.axisY.last().first_axis);
    }
}
