abstract class Session {
    private Module module;
    private Lecturer lecturer;

    public Session(Module module, Lecturer lecturer) {
        this.module = module;
        this.lecturer = lecturer;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Module getModule() {
        return module;
    }
    public void setModule(Module module) {
        this.module = module;
    }
}
