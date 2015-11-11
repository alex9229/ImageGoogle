package alex.imagegoogle.db;

/**
 * Сreate by alex
 */

public interface IDBStrings {


    String DataBaseName = "image.db";

    /**
     * The CREATe_ table_ Images.
     */
    String CREATE_TABLE_IMAGES = "CREATE TABLE `IMAGES` (\n" +
            "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`thumbUrl`\tTEXT NOT NULL UNIQUE,\n" +
            "\t`title`\tTEXT\n" +
            ");";


    int DB_VERSION = 1;


}
