package hu.bme.viaum105.subtitle;

import java.io.File;

public abstract class SubtitleHelper {

    /**
     * Felirat beolvasása fájlból
     * 
     * @param subtitleContainerDir
     * @param relativePath
     * @return
     */
    public static SubtitleData readSubtitle(File subtitleContainerDir, String relativePath) {
	// TODO
	return null;
    }

    /**
     * Felirat kiírása fájlba
     * 
     * @param subtitleContainerDir
     * @param relativePath
     * @param subtitleData
     */
    public static void writeSubtitle(File subtitleContainerDir, String relativePath, SubtitleData subtitleData) {
	// TODO
    }

    private SubtitleHelper() {
	throw new UnsupportedOperationException("This class should not be instantiated");
    }

}
