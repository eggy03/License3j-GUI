package io.github.eggy03.application.ui.utility;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Insets;
import java.util.Objects;

/**
 * Utility API for applying predefined {@link UIManager} configurations and
 * minor visual customizations, globally across Swing Look and Feels (LAF).
 *
 * <p><strong>Must be invoked <strong>before</strong> any Swing components are created or laid out</strong></p>
 *
 * <p><strong>Threading note:</strong> Should be called on the EDT during application
 * initialization to avoid inconsistent UI state.</p>
 */
public class GlobalUISettings {

    private static final Logger log = LoggerFactory.getLogger(GlobalUISettings.class);
    private static final String DEFAULT_LAF = "com.formdev.flatlaf.themes.FlatMacDarkLaf";

    /**
     * Creates a configuration instance using the default FlatLaf theme:
     * {@code com.formdev.flatlaf.themes.FlatMacDarkLaf}.
     *
     * <p>If the Look and Feel cannot be applied, the error is logged and the
     * application continues with the existing/default LAF.</p>
     *
     * @see #GlobalUISettings(String)
     */
    public GlobalUISettings() {
        try {
            UIManager.setLookAndFeel(DEFAULT_LAF);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            log.error("Default LAF [{}] cannot be applied", DEFAULT_LAF);
            log.debug("Stack trace for LAF failure", e);
        }
    }

    /**
     * Creates a configuration instance with a custom Look and Feel.
     *
     * @param lookAndFeel fully qualified class name of the desired LAF
     * @throws NullPointerException if {@code lookAndFeel} is {@code null}
     *
     *                              <p>If the Look and Feel cannot be applied, the error is logged and the
     *                              application continues with the existing/default LAF.</p>
     */
    public GlobalUISettings(@NonNull String lookAndFeel) {
        try {
            UIManager.setLookAndFeel(Objects.requireNonNull(lookAndFeel, "LAF name cannot be null"));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            log.error("LAF [{}] cannot be applied", lookAndFeel);
            log.debug("Stack trace for LAF failure", e);
        }
    }

    /**
     * Enables highly rounded UI components by modifying arc-related properties.
     *
     * <p>This method adjusts global UI defaults to create a "pill-shaped"
     * appearance for supported components such as buttons, text fields,
     * progress bars, and scrollbars.</p>
     *
     * @return the current instance for method chaining
     */
    public GlobalUISettings enableRoundComponents() {
        UIManager.put("Button.arc", 999);
        UIManager.put("Component.arc", 999);
        UIManager.put("ProgressBar.arc", 999);
        UIManager.put("TextComponent.arc", 999);

        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

        return this;
    }

    /**
     * Enables or disables tab separators in {@code JTabbedPane}.
     *
     * @param value {@code true} to show separators with full height, {@code false} to hide them.
     *              <p>Applies globally to all tabbed panes.</p>
     */
    public void enableTabSeparators(boolean value) {
        UIManager.put("TabbedPane.showTabSeparators", value);
        UIManager.put("TabbedPane.tabSeparatorsFullHeight", value);
    }
}
