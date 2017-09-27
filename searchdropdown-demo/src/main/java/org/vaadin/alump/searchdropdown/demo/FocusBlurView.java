package org.vaadin.alump.searchdropdown.demo;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.alump.searchdropdown.SimpleSearchDropDown;
import org.vaadin.alump.searchdropdown.SimpleSuggestionProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * View that shows the use of focus, blur and tabIndex
 */
public class FocusBlurView extends VerticalLayout implements View {
    public final static String VIEW_NAME = "focus-blur";
    private Navigator navigator;

    public static final String VALUES[] = { "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel",
            "india", "juliett", "kilo", "lima", "mike", "november", "oscar", "papa", "quebec", "romea", "sierra",
            "tango", "uniform", "victor", "whiskey", "xray", "yankee", "zulu" };

    public FocusBlurView() {
        setWidth(100, Unit.PERCENTAGE);
        setMargin(true);
        setSpacing(true);

        SimpleSearchDropDown simpleSearch = new SimpleSearchDropDown(provider);

        addComponent(new MenuButton(e -> navigator.navigateTo(MenuView.VIEW_NAME)));

        addComponent(new Label("Select the desired tab index"));
        // The tab index decides in which order an element will be focused when using the Tab key
        // -1 = not focused, 0 = default order, otherwise according to tab index
        HorizontalLayout tabIndexButtonWrapper = new HorizontalLayout();
        tabIndexButtonWrapper.addComponent(new Button("-1", e -> {
            simpleSearch.setTabIndex(-1);
        }));
        tabIndexButtonWrapper.addComponent(new Button("0", e -> {
            simpleSearch.setTabIndex(0);
        }));
        tabIndexButtonWrapper.addComponent(new Button("1", e -> {
            simpleSearch.setTabIndex(1);
        }));
        addComponent(tabIndexButtonWrapper);

        // Button for focusing
        addComponent(new Label("Press to focus"));
        addComponent(new Button("Focus", e -> {
            simpleSearch.focus();
        }));

        // Notifications on focus
        simpleSearch.addFocusListener(e -> Notification.show("Focused! "));
        simpleSearch.addBlurListener(e -> Notification.show("Blurred!"));

        simpleSearch.setWidth(500, Unit.PIXELS);
        simpleSearch.setPlaceHolder("Search Phonetic Alphabets");
        addComponent(simpleSearch);
    }

    SimpleSuggestionProvider provider = (query) -> {
        // For empty query, do not provide any suggestions
        final String cleaned = query.toLowerCase().trim();
        if(cleaned.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        // Normally here you would perform database or REST query or queries, to find suitable suggestions.
        // Simple API is always synchronous, so when you want to go to asynchronous use base class.
        return Arrays.stream(VALUES).filter(v -> v.contains(cleaned)).collect(Collectors.toList());
    };

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
