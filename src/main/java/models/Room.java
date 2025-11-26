
/* Room.java
 *
 * Represents a room in a building with attributes such as room number, capacity, occupancy status, type, and building name.
 */
package models;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Room.
 */
public class Room implements CSVModel {

    private final List<Group> sessions = new LinkedList<>();
    private String roomNumber;
    private int capacity;
    private boolean isOccupied;
    private RoomType type;
    private String building;

    /**
     * Instantiates a new Room.
     *
     * @param roomNumber the room number
     * @param capacity   the capacity
     * @param type       the type
     * @param building   the building
     */
    public Room(String roomNumber, int capacity, RoomType type, String building) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.type = type;
        this.building = building;
        this.isOccupied = false;
    }

    /**
     * The enum Room type.
     */
    public enum RoomType {
        /**
         * Classroom room type.
         */
        CLASSROOM,
        /**
         * Laboratory room type.
         */
        LABORATORY
    }

    /**
     * Gets room number.
     *
     * @return the room number
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public RoomType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(RoomType type) {
        this.type = type;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Is occupied boolean.
     *
     * @return the boolean
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Sets occupied.
     *
     * @param occupied the occupied
     */
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     * Set a room number.
     *
     * @param roomNumber the room number
     */
    public void SetRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Set capacity.
     *
     * @param capacity the capacity
     */
    public void SetCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets building.
     *
     * @return the building
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Sets building.
     *
     * @param building the building
     */
    public void setBuilding(String building) {
        this.building = building;
    }

    /**
     * Gets sessions.
     *
     * @return the sessions
     */
    public List<Group> getSessions() {
        return new LinkedList<>(sessions);
    }

    /**
     * Add session.
     *
     * @param session the session
     */
    public void addSession(Group session) {
        this.sessions.add(session);
    }

    /**
     * Remove session.
     *
     * @param session the session
     */
    public void removeSession(Group session) {
        this.sessions.remove(session);
    }

    @Override
    public String[] toCSVRow() {
        return new String[]{roomNumber, String.valueOf(capacity), type.name(), building, String.valueOf(isOccupied)};
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"roomNumber", "capacity", "type", "building", "isOccupied"};
    }
}
