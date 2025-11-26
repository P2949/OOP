package controllers;

import models.*;
import models.Module;
import org.jetbrains.annotations.Nullable;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads and reconstructs model objects from CSV files.
 * <p>
 * it's so cursed, there are so many if's, so many conditions...
 */

public class DataLoader {
    private final DatabaseController dbController;

    // Caches to avoid duplicate object creation
    private final Map<String, Room> roomCache = new HashMap<>();
    private final Map<String, Lecturer> lecturerCache = new HashMap<>();
    private final Map<String, Module> moduleCache = new HashMap<>();
    private final Map<String, Program> programCache = new HashMap<>();
    private final Map<Integer, Student> studentCache = new HashMap<>();
    private final Map<String, Session> sessionCache = new HashMap<>();
    private final Map<String, Group> groupCache = new HashMap<>();

    public DataLoader(DatabaseController dbController) {
        this.dbController = dbController;
    }

    /**
     * Load all data from CSV files.
     *
     * @throws Exception if loading fails
     */
    public void loadAllData() throws Exception {
        System.out.println("Loading data from CSV files...");

        // Load in dependency order
        loadRooms();
        loadLecturers();
        loadModules();
        loadPrograms();
        loadSessions();
        loadGroups();
        loadStudents();

        System.out.println("Data loading complete!");
    }

    /**
     * Load rooms from Room.csv
     */
    private void loadRooms() throws Exception {
        List<String[]> rows = dbController.loadAllFromCSV("Room");
        for (String[] row : rows) {
            if (row.length >= 5) { //just in case something was saved incorrectly
                String roomNumber = row[0];
                int capacity = Integer.parseInt(row[1]);
                Room.RoomType type = Room.RoomType.valueOf(row[2]);
                String building = row[3];
                boolean isOccupied = Boolean.parseBoolean(row[4]);

                Room room = new Room(roomNumber, capacity, type, building);
                room.setOccupied(isOccupied);
                roomCache.put(roomNumber, room);
            }
        }
        System.out.println("Loaded " + roomCache.size() + " rooms");
    }

    /**
     * Load lecturers from Lecturer.csv
     */
    private void loadLecturers() throws Exception {
        List<String[]> rows = dbController.loadAllFromCSV("Lecturer");
        for (String[] row : rows) {
            if (row.length >= 4) {
                String staffID = row[0];
                String name = row[1];
                int age = Integer.parseInt(row[2]);
                String department = row[3];

                Lecturer lecturer = new Lecturer(name, age, staffID, department);
                lecturerCache.put(staffID, lecturer);
            }
        }
        System.out.println("Loaded " + lecturerCache.size() + " lecturers");
    }

    /**
     * Load modules from Module.csv
     */
    private void loadModules() throws Exception {
        List<String[]> rows = dbController.loadAllFromCSV("Module");
        for (String[] row : rows) {
            if (row.length >= 5) {
                String moduleCode = row[0];
                String moduleName = row[1];
                String lecturerID = row[2];
                int lengthInWeeks = Integer.parseInt(row[3]);
                int startWeek = Integer.parseInt(row[4]);

                Lecturer lecturer = lecturerCache.get(lecturerID);
                if (lecturer != null) {
                    Module module = new Module(moduleCode, moduleName, lecturer, lengthInWeeks, startWeek);
                    moduleCache.put(moduleCode, module);
                }
            }
        }
        System.out.println("Loaded " + moduleCache.size() + " modules");
    }

    /**
     * Load programs from Program.csv
     */
    private void loadPrograms() throws Exception {
        List<String[]> rows = dbController.loadAllFromCSV("Program");
        for (String[] row : rows) {
            if (row.length >= 3) {
                String programName = row[0];
                int totalModules = Integer.parseInt(row[1]);
                String moduleCodes = row[2];

                Program program = new Program(programName, totalModules);

                // Add modules to the program
                if (!moduleCodes.isEmpty()) {
                    String[] codes = moduleCodes.split(",");
                    for (String code : codes) {
                        Module module = moduleCache.get(code);
                        if (module != null) {
                            program.addModuleTaught(module);
                        }
                    }
                }

                programCache.put(programName, program);
            }
        }
        System.out.println("Loaded " + programCache.size() + " programs");
    }

    /**
     * Load sessions from Lecture.csv, Laboratory.csv, Tutorial.csv
     */
    private void loadSessions() throws Exception {
        loadSessionType("Lecture");
        loadSessionType("Laboratory");
        loadSessionType("Tutorial");
        System.out.println("Loaded " + sessionCache.size() + " sessions");
    }

    private void loadSessionType(String sessionType) throws Exception {
        List<String[]> rows = dbController.loadAllFromCSV(sessionType);
        for (String[] row : rows) {
            if (row.length >= 9) {
                String moduleCode = row[1];
                String lecturerID = row[2];
                String roomNumber = row[3];
                Session.Day day = Session.Day.valueOf(row[4]);
                int startWeek = Integer.parseInt(row[5]);
                LocalTime startTime = LocalTime.parse(row[6]);
                LocalTime endTime = LocalTime.parse(row[7]);
                int endWeek = Integer.parseInt(row[8]);

                Module module = moduleCache.get(moduleCode);
                Lecturer lecturer = lecturerCache.get(lecturerID);
                Room room = roomCache.get(roomNumber);

                if (module != null && lecturer != null && room != null) {
                    Session session;
                    switch (sessionType) {
                        case "Lecture" ->
                                session = new Lecture(module, lecturer, room, day, startWeek, startTime, endTime, endWeek);
                        case "Laboratory" ->
                                session = new Laboratory(module, lecturer, room, day, startWeek, startTime, endTime, endWeek);
                        case "Tutorial" ->
                                session = new Tutorial(module, lecturer, room, day, startWeek, startTime, endTime, endWeek);
                        default -> session = null;
                    }
                    //in case somehow this method is called with session not being a lec,lab or tut.
                    if (session != null) {
                        String sessionKey = sessionType + ":" + moduleCode + ":" + day + ":" + startTime + ":" + roomNumber;
                        sessionCache.put(sessionKey, session);
                    }
                }
            }
        }
    }

    /**
     * Load groups from Group.csv
     */
    private void loadGroups() throws Exception {
        List<String[]> rows = dbController.loadAllFromCSV("Group");
        for (String[] row : rows) {
            if (row.length >= 2) {
                char groupID = row[0].charAt(0);
                String sessionKey = row[1];

                Session session = sessionCache.get(sessionKey);
                if (session != null) {
                    Group group = new Group(groupID, session);
                    groupCache.put(groupID + ":" + sessionKey, group);
                }
            }
        }
        System.out.println("Loaded " + groupCache.size() + " groups");
    }

    /**
     * Load students from Student.csv
     */
    private void loadStudents() throws Exception {
        List<String[]> rows = dbController.loadAllFromCSV("Student");
        for (String[] row : rows) {
            if (row.length >= 6) {
                int studentID = Integer.parseInt(row[0]);
                String name = row[1];
                int age = Integer.parseInt(row[2]);
                String programName = row[3];
                int yearOfStudy = Integer.parseInt(row[4]);
                String groupData = row[5];

                Program program = programCache.get(programName);
                if (program != null) {
                    Student student = new Student(name, age, program, yearOfStudy);
                    student.setStudentID(studentID);

                    // Add groups
                    if (!groupData.isEmpty()) {
                        String[] groups = groupData.split(",");
                        for (String groupInfo : groups) {
                            String[] parts = groupInfo.split(":");
                            if (parts.length >= 6) {
                                // this might legit be wrong, I just kinda guessed based on what we have on the csv file right now
                                String groupKey = parts[0] + ":" + parts[1] + ":" + parts[2] + ":" + parts[3] + ":" + parts[4] + ":" + parts[5];
                                Group group = findGroupByKey(parts, groupKey);
                                if (group != null) {
                                    try {
                                        student.addGroup(group);
                                    } catch (IllegalArgumentException ignored) {
                                        // Group assignment might not work
                                    }
                                }
                            }
                        }
                    }

                    studentCache.put(studentID, student);
                }
            }
        }
        System.out.println("Loaded " + studentCache.size() + " students");
    }

    private @Nullable Group findGroupByKey(String[] parts, String fullKey) {
        // Try to find a group from the cache
        for (Map.Entry<String, Group> entry : groupCache.entrySet()) {
            if (entry.getKey().contains(parts[0])) {
                Session session = entry.getValue().getSession();
                //hopefully everything matches with another one, and it gets happy, this is probably overkill tho
                if (session.getClass().getSimpleName().equals(parts[1]) && session.getModule().getModuleCode().equals(parts[2]) && session.getDay().name().equals(parts[3])
                        && session.getStartTime().toString().equals(parts[4]) && session.getRoom().getRoomNumber().equals(parts[5])) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    // Getters for loaded data in case we need it
    public Collection<Room> getRooms() {
        return roomCache.values();
    }

    public Collection<Lecturer> getLecturers() {
        return lecturerCache.values();
    }

    public Collection<Module> getModules() {
        return moduleCache.values();
    }

    public Collection<Program> getPrograms() {
        return programCache.values();
    }

    public Collection<Student> getStudents() {
        return studentCache.values();
    }

    public Collection<Session> getSessions() {
        return sessionCache.values();
    }

    public Collection<Group> getGroups() {
        return groupCache.values();
    }
}
