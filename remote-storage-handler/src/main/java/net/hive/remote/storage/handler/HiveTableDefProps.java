package net.hive.remote.storage.handler;


public enum HiveTableDefProps {

    // @formatter:off

    /**
     * Table name that hive will use in queries
     */
    TABLE_NAME("name", ""),

    /**
     * Columns types
     */
    COLUMNS_TYPES("columns.types", ":"),

    /**
     * Columns names
     */
    COLUMNS_NAMES("columns", ","),

    /**
     * Columns comments
     */
    COLUMNS_COMMENTS("columns.comments", ","),

    /**
     * Projection columns
     */
    READCOLUMN_NAMES("hive.io.file.readcolumn.names", ","),

    /**
     * Hive query
     */
    HIVE_QUERY("hive.query.string", ""),

    /**
     *
     */
    SERIALIZATION_DDL("serialization.ddl", ",");

    // @formatter:on

    /**
     * Holds Hive's table definition property name
     */
    private final String propertyName;

    /**
     * Holds Hive's table definition property values separator
     */
    private final String separator;

    /**
     * Constructs a new enum element with the given propertyName
     *
     * @param propertyName the related property name for the enum element.
     * @param separator
     */
    private HiveTableDefProps(final String propertyName, final String separator) {
        this.propertyName = propertyName;
        this.separator = separator;
    }

    /**
     * Getter for propertyName
     *
     * @return the related property name for the enum element.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Getter for separator
     *
     * @return the property values separator for the enum element.
     */
    public String getSeparator() {
        return separator;
    }
}