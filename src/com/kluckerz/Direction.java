package com.kluckerz;

/**
 * Direction enum to specify view independent directions.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    /**
     * Translates the direction depending on the view.
     * @param view The view direction (onto the moving object).
     * @return The translated direction.
     */
    public Direction translateFromView(final Direction view) {
        switch(view) {
            default:
            case NORTH:
                return this;
            case EAST:
                switch(this) {
                    case NORTH:
                        return EAST;
                    case EAST:
                        return SOUTH;
                    case SOUTH:
                        return WEST;
                    case WEST:
                        return NORTH;
                }
            case SOUTH:
                switch(this) {
                    case NORTH:
                        return SOUTH;
                    case EAST:
                        return WEST;
                    case SOUTH:
                        return NORTH;
                    case WEST:
                        return EAST;
                }
                break;
            case WEST:
                switch(this) {
                    case NORTH:
                        return WEST;
                    case EAST:
                        return NORTH;
                    case SOUTH:
                        return EAST;
                    case WEST:
                        return SOUTH;
                }
                break;
        }
        return null;
    }

}
