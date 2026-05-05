package io.github.eggy03.application.constant;

@SuppressWarnings("unused")
public class VersionAndOtherInfo {

    public static final String APP_AUTHOR = "Sayan Bhattacharya";
    public static final String APP_VERSION = "2.0.0-alpha1";
    public static final String LICENSE3J_AUTHOR = "Peter Verhas";
    public static final String LICENSE3J_VERSION = "3.3.0";
    public static final String ABOUT = """
            <h2>About License3J GUI</h2>
            <p>
            License3J GUI is a Graphical User Interface developed for the free and open source License3j library.
            The design and functionality of this app is heavily inspired from the License3j-REPL Application.
            It is recommended that you check out the REPL Application and read a bit about License3j itself,
            to have an understanding of how to use this application.
            </p>
            <p>
            License3j GUI binaries are not signed. Please be careful when downloading from unofficial sources.
            
            <h2>Libraries Used</h2>
            <ul>
                <li>License3j</li>
                <li>SLF4J</li>
                <li>TinyLog</li>
                <li>MigLayout</li>
                <li>FlatLaf</li>
                <li>Apache Commons IO</li>
                <li>JSpecify</li>
            </ul>
            """;

    private VersionAndOtherInfo() {
        throw new IllegalStateException("Utility Class");
    }
}
