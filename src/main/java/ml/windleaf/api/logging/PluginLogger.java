package ml.windleaf.api.logging;

import ml.windleaf.api.logging.format.NameFormat;

public class PluginLogger {
    private final String name;
    private final NameFormat nameFormat;

    public PluginLogger(String name) {
        this.name = name;
        this.nameFormat = NameFormat.SQUARE_BRACKETS;
    }

    public PluginLogger(String name, NameFormat nameFormat) {
        this.name = name;
        this.nameFormat = nameFormat;
    }
}
