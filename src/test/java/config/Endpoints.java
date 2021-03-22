package config;

public enum Endpoints {
    USERS("/users"), REGISTER("/register"), LOGIN("/login");
    String path;

    Endpoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String addPath(String additionalPath) {
        return path + additionalPath;
    }
}
