package models;

/**
 * The interface Csv model.
 */
public interface CSVModel {
    /**
     * To csv row string [ ].
     *
     * @return the string [ ]
     */
    String[] toCSVRow();

    /**
     * Get csv header string [ ].
     *
     * @return the string [ ]
     */
    String[] getCSVHeader();
}
