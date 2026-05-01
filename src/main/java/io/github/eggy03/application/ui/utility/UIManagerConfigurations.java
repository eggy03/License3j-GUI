package io.github.eggy03.application.ui.utility;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Insets;
import java.util.Objects;

// custom UI manager configurations that allow slight changes to the LAF
// Applies on all LAFs
// Invoke this class only AFTER you've used UIManager.setLookAndFeel() and BEFORE you have laid out your components
@Slf4j
public class UIManagerConfigurations {

	public UIManagerConfigurations enableRoundComponents() {
		UIManager.put( "Button.arc", 999 );
		UIManager.put( "Component.arc", 999 );
		UIManager.put( "ProgressBar.arc", 999 );
		UIManager.put( "TextComponent.arc", 999 );
		
		UIManager.put( "ScrollBar.thumbArc", 999 );
		UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
		
		UIManager.put( "ScrollBar.trackArc", 999 );
		UIManager.put( "ScrollBar.thumbArc", 999 );
		UIManager.put( "ScrollBar.trackInsets", new Insets( 2, 4, 2, 4 ) );
		UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );

		return this;
	}
	
	public UIManagerConfigurations enableTabSeparators(boolean value) {
		UIManager.put( "TabbedPane.showTabSeparators", value );
		UIManager.put( "TabbedPane.tabSeparatorsFullHeight", value );

		return this;
	}

	public void setLookAndFeel(@NonNull String lookAndFeel) {
		try {
			UIManager.setLookAndFeel(Objects.requireNonNull(lookAndFeel, "LAF name cannot be null"));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			log.error("LAF cannot be applied", e);
		}
	}
}
