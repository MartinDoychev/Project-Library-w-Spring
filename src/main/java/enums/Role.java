package enums;

public enum Role {
    READER, AUTHOR, ADMIN;
    public static Role getUserRole(String role) {
        return switch (role.toUpperCase()) {
            case "AUTHOR" -> Role.AUTHOR;
            case "ADMIN" -> Role.ADMIN;
            default -> Role.READER;
        };
    }
    public static int getUserRoleID(Role role) {
        return switch (role) {
            case AUTHOR -> 2;
            case ADMIN -> 3;
            default -> 1;
        };
    }
}