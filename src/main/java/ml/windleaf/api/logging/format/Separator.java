package ml.windleaf.api.logging.format;

public enum Separator {
    EMPTY(" "),
    HYPHEN(" - "),
    DELIMITER(" | "),
    SINGLE_ARROW(" > "),
    TRI_ARROW(" >>> ")
    ;

    public final String content;

    Separator(String content) {
        this.content = content;
    }
}
