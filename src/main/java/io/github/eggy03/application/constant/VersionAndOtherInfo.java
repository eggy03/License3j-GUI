package io.github.eggy03.application.constant;

public class VersionAndOtherInfo {

    public static final String APP_AUTHOR = "Egg-03";
    public static final String APP_VERSION = "2.0.0-alpha1";
    public static final String LICENSE3J_AUTHOR = "Peter Verhas";
    public static final String LICENSE3J_VERSION = "3.3.0";
    public static final String ABOUT = """
            <h2>About License3J GUI</h2>
            <p>
            License3J GUI is a Graphical User Interface developed by Egg-03 for the free and open source License3j library.
            The source code is based on the License3j-REPL Application. This application provides a user-friendly interface
            to create, view, and validate license files, generate keys for signing the licenses, using the powerful features
            of the License3j library.
            </p>
            
            <h2>About License3J</h2>
            <p>
            License3j is a free and open-source Java library to manage license files in Java programs.
            A license file is a special electronically signed configuration file. The library can create
            (including signing), read, and verify such license files.
            Learn more about License3j on GitHub.
            </p>
            
            <h2>Licensing</h2>
            <p>
            Both License3J and the GUI application are licensed under the Apache 2.0 License.
            </p>
            
            <h2>Other Libraries Used</h2>
            <ul>
                <li>TinyLog</li>
                <li>MigLayout</li>
                <li>Apache Commons IO</li>
                <li>FlatLaf</li>
                <li>Flexmark</li>
            </ul>
            """;

    private VersionAndOtherInfo() {
        throw new IllegalStateException("Utility Class");
    }
}
