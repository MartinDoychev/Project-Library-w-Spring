package enums;

public enum BookAccess {
    AVAILABLE, STAGED, ANNOUNCED;

    public static BookAccess getBookAccess(long accessID) {
        return switch ((int) accessID) {
            case 2 -> BookAccess.STAGED;
            case 3 -> BookAccess.ANNOUNCED;
            default -> BookAccess.AVAILABLE;
        };
    }

    public static int getBookAccessID(BookAccess access) {
        return switch (access) {
            case STAGED -> 2;
            case ANNOUNCED -> 3;
            default -> 1;
        };
    }
}
