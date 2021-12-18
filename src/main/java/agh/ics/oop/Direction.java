package agh.ics.oop;

import java.util.Objects;

public class Direction {
    private int direction;

    public Direction(int direction){
        direction = direction % 8;
        if (direction < 0) direction += 8;
        this.direction = direction;
    }

//    public turn(int )


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction1 = (Direction) o;
        return direction == direction1.direction;
    }
}