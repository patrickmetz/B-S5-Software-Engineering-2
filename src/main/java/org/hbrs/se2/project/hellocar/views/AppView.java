package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.hbrs.se2.project.hellocar.control.AuthorizationControl;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Utils;
import org.hbrs.se2.project.hellocar.views.account.UpdateCompanyView;
import org.hbrs.se2.project.hellocar.views.account.UpdateStudentView;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@CssImport("./styles/views/main/main-view.css")
@Route("main")
@PWA(name = "HelloCar", shortName = "HelloCar", enableInstallPrompt = false)
@JsModule("./styles/shared-styles.js")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class AppView extends AppLayout implements BeforeEnterObserver {

    private Tabs menu;
    private H1 viewTitle;
    private HorizontalLayout profile;
    private Image avatar;
    private RouterLink accountLink;

    private AuthorizationControl authorizationControl;

    public AppView() {
        if (getCurrentUser() == null) {
            System.out.println("LOG: In Constructor of App View - No User given!");
        } else {
            setUpUI();
        }
    }

    public void setUpUI() {
        // Anzeige des Toggles über den Drawer
        setPrimarySection(Section.DRAWER);

        // Erstellung der horizontalen Statusleiste (Header)
        addToNavbar(true, createHeaderContent());

        // Erstellung der vertikalen Navigationsleiste (Drawer)
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private boolean checkIfUserIsLoggedIn() {
        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        UserDTO userDTO = this.getCurrentUser();
        if (userDTO == null) {
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
            return false;
        }
        return true;
    }

    /**
     * Erzeugung der horizontalen Leiste (Header).
     * @return
     */
    private Component createHeaderContent() {
        // Ein paar Grund-Einstellungen. Alles wird in ein horizontales Layout gesteckt.
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode( FlexComponent.JustifyContentMode.EVENLY );

        // Hinzufügen des Toogle ('Big Mac') zum Ein- und Ausschalten des Drawers
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        viewTitle.setWidthFull();
        layout.add( viewTitle );

        // Interner Layout
        HorizontalLayout topRightPanel = new HorizontalLayout();
        topRightPanel.setWidthFull();
        topRightPanel.setPadding(true);
        topRightPanel.setJustifyContentMode( FlexComponent.JustifyContentMode.END );
        topRightPanel.setAlignItems( FlexComponent.Alignment.CENTER );

        // Href und Text wird später ersetzt, wenn die Navigation stattfindet
        profile = new HorizontalLayout();
        profile.setId("update-account-profile");
        profile.setAlignItems(FlexComponent.Alignment.CENTER);
        profile.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        avatar = new Image("images/default-avatar.png", "Avatar logo");
        avatar.setId("update-account-profile-image");
        accountLink = new RouterLink(this.getCurrentUserNameOfUser(), UpdateStudentView.class);
        accountLink.setId("update-account-link");

        topRightPanel.add(profile);

        // Logout-Button am rechts-oberen Rand.
        MenuBar bar = new MenuBar();
        MenuItem item = bar.addItem("Logout" , e -> logoutUser());
        item.getElement().getStyle().set("cursor", "pointer");
        item.setId("edit-logout");
        topRightPanel.add(bar);

        layout.add( topRightPanel );
        return layout;
    }

    private void logoutUser() {
        UI ui = this.getUI().get();
        ui.getSession().close();
        ui.navigate(Globals.Pages.MAIN_VIEW);
    }

    /**
     * Hinzufügen der vertikalen Leiste (Drawer)
     * Diese besteht aus dem Logo ganz oben links sowie den Menu-Einträgen (menu items).
     * Die Menu Items sind zudem verlinkt zu den internen Tab-Components.
     * @param menu
     * @return
     */
    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        HorizontalLayout logoLayout = new HorizontalLayout();

        // Hinzufügen des Logos
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Image image = new Image("images/logo.png", "HelloCar logo");
        image.getElement().getStyle().set("cursor", "pointer");
        image.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.SHOW_CARS));

        logoLayout.add(image);

        // Hinzufügen des Menus inklusive der Tabs
        layout.add(logoLayout, menu);
        return layout;
    }

    /**
     * Erzeugung des Menu auf der vertikalen Leiste (Drawer)
     * @return
     */
    private Tabs createMenu() {

        // Anlegen der Grundstruktur
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");

        // Anlegen der einzelnen Menuitems
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
       // Abholung der Referenz auf den Authorisierungs-Service
       authorizationControl = new AuthorizationControl();

       // Jeder User sollte Autos sehen können, von daher wird dieser schon mal erzeugt und
       // und dem Tabs-Array hinzugefügt. In der Methode createTab wird ein (Key, Value)-Pair übergeben:
        // Key: der sichtbare String des Menu-Items
        // Value: Die UI-Component, die nach dem Klick auf das Menuitem angezeigt wird.
       Tab[] tabs = new Tab[]{ createTab( "Show Cars", ShowCarsView.class) };

       // Falls er Admin-Rechte hat, sollte der User auch Autos hinzufügen können
       // (Alternative: Verwendung der Methode 'isUserisAllowedToAccessThisFeature')
       if ( this.authorizationControl.isUserInRole( this.getCurrentUser() , Globals.Roles.ADMIN ) ) {
           System.out.println("User is Admin!");
           tabs = Utils.append( tabs , createTab("Enter Car", EnterCarView.class)  );
       }

       // ToDo für die Teams: Weitere Tabs aus ihrem Projekt hier einfügen!

        /*if (this.authorizationControl.isUserInRole(this.getCurrentUser(), Globals.Roles.STUDENT)) {
            System.out.println("User is Student");

            tabs = Utils.append(tabs, createTab("Update Account", UpdateStudentView.class));
        }
        else if (this.authorizationControl.isUserInRole(this.getCurrentUser(), Globals.Roles.COMPANY)) {
            System.out.println("User is Company");

            tabs = Utils.append(tabs, createTab("Update Account", UpdateCompanyView.class));
        }*/

       return tabs;
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        if ( !checkIfUserIsLoggedIn() ) return;

        // Der aktuell-selektierte Tab wird gehighlighted.
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

        // Setzen des aktuellen Names des Tabs
        viewTitle.setText(getCurrentPageTitle());

        // Setzen des Usernamens von dem aktuell eingeloggten Benutzer
        if (this.authorizationControl.isUserInRole(this.getCurrentUser(), Globals.Roles.STUDENT)) {
            System.out.println("User is Student");
            accountLink.setRoute(UpdateStudentView.class);
            profile.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.UPDATE_STUDENT_VIEW));
            profile.add(avatar, accountLink);
        }
        else if (this.authorizationControl.isUserInRole(this.getCurrentUser(), Globals.Roles.COMPANY)) {
            System.out.println("User is Company");
            accountLink.setRoute(UpdateCompanyView.class);
            profile.addClickListener(e -> UI.getCurrent().navigate(Globals.Pages.UPDATE_COMPANY_VIEW));
            profile.add(avatar, accountLink);
        }
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    private String getCurrentNameOfUser() {
        return getCurrentUser().getFirstName() + " " + getCurrentUser().getLastName();
    }

    private String getCurrentUserNameOfUser() {
        return getCurrentUser().getUserid();
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }


    @Override
    /**
     * Methode wird vor der eigentlichen Darstellung der UI-Components aufgerufen.
     * Hier kann man die finale Darstellung noch abbrechen, wenn z.B. der Nutzer nicht eingeloggt ist
     * Dann erfolgt hier ein ReDirect auf die Login-Seite. Eine Navigation (Methode navigate)
     * ist hier nicht möglich, da die finale Navigation noch nicht stattgefunden hat.
     * Diese Methode in der AppLayout sichert auch den un-authorisierten Zugriff auf die innerliegenden
     * Views (hier: ShowCarsView und EnterCarView) ab.
     *
     */
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (getCurrentUser() == null){
            beforeEnterEvent.rerouteTo(Globals.Pages.LOGIN_VIEW);
        }

    }
}
