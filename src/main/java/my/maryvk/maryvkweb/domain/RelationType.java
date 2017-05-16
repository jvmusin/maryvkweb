package my.maryvk.maryvkweb.domain;

public enum RelationType {

    FRIEND(0),
    FOLLOWER(1);

    private final int id;
    private static final RelationType[] types = {FRIEND, FOLLOWER};

    RelationType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static RelationType get(int id) {
        return types[id];
    }

}