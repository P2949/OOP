
/* Room.java
 *
 * Represents a room in a building with attributes such as room number, capacity, occupancy status, type, and building name.
 */
package models;
import java.util.LinkedList;
import java.util.List;

public class Room implements CSVModel {

    private final List<Group> sessions = new LinkedList<>();
    private String roomNumber;
    private int capacity;
    private boolean isOccupied;
    private RoomType type;
    private String building;

    public Room(String roomNumber, int capacity, RoomType type, String building) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.type = type;
        this.building = building;
        this.isOccupied = false;
    }

    public enum RoomType {
        CLASSROOM, LABORATORY
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public void SetroomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void Setcapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public List<Group> getSessions() {
        return new LinkedList<>(sessions);
    }

    public void addSession(Group session) {
        this.sessions.add(session);
    }

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
