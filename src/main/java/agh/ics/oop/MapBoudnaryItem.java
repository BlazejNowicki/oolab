package agh.ics.oop;

public class MapBoudnaryItem implements Comparable<MapBoudnaryItem>{
    public final int first_axis, second_axis;

    public MapBoudnaryItem(int first_axis, int second_axis){
        this.first_axis = first_axis;
        this.second_axis = second_axis;
    }

    @Override
    public int compareTo(MapBoudnaryItem o) {
        if(this.first_axis - o.first_axis != 0) {
            return this.first_axis - o.first_axis;
        } else if(this.second_axis - o.second_axis != 0) {
            return this.second_axis - o.second_axis;
        } else {
            return 0;
        }
    }

}
