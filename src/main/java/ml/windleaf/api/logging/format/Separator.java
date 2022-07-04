package ml.windleaf.api.logging.format;

public enum Separator {
    EMPTY(" "),
    HYPHEN(" - "),
    DELIMITER(" | "),
    SINGLE_ARROW(" > "),
    TRI_ARROW(" >>> "),
    ;

    public final String content;
    public final Boolean bold;

    Separator(String content) {
        this.content = content;
        this.bold = false;
    }

    Separator(String content, Boolean bold) {
        this.content = content;
        this.bold = bold;
    }
}
