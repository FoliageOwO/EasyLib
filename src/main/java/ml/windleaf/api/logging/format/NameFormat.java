package ml.windleaf.api.logging.format;

public enum NameFormat {
    EMPTY("{}"),
    EMPTY_BOLD("");

    public final String content;

    NameFormat(String content) {
        this.content = content;
    }
}
