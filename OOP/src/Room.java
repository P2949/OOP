
/* Room.java
 * 
 * Represents a room in a building with attributes such as room number, capacity, occupancy status, type, and building name.
 */
import java.util.LinkedList;
import java.util.List;

public class Room {

    private String roomNumber;
    private int capacity;
    private boolean isOccupied;
    private String type;
    private String building;
    private List<Group> sessions = new LinkedList<>();

    public Room(String roomNumber, int capacity, String type, String building) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.type = type;
        this.building = building;
        this.isOccupied = false;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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
}
