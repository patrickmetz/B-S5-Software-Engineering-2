package org.hbrs.se2.project.hellocar.views.jobAdvertisement;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.dtos.account.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.services.search.JobAdvertisementSearch;
import org.hbrs.se2.project.hellocar.services.search.JobAdvertisementSearchProxy;
import org.hbrs.se2.project.hellocar.services.search.impl.JobAdvertisementSearchImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.views.AppView;
import org.hbrs.se2.project.hellocar.views.account.LoginView;

import java.util.List;
import java.util.function.Consumer;

@Route(value = Globals.Pages.JOB_AD_LIST, layout = AppView.class)
@RouteAlias("joblist")
@CssImport("./styles/views/jobAdvertisement/jobAdvertisement.css")
@PageTitle("Show Jobs")
public class JobAdvertisementListView extends Div {

    private List<JobAdvertisementDTO> jobAdvertisementList;
    private ManageUserControl manageUserControl;

    public JobAdvertisementListView(
            ManageJobAdvertisementControl jobAdvertisementControl,
            ManageUserControl manageUserControl
    ) {
        this.manageUserControl = manageUserControl;
        jobAdvertisementList = jobAdvertisementControl.readAllJobAdvertisements();

        addClassName("job-advertisement-list-view");

        add(this.createTitle());
        add(this.createGridTable());
    }

    private Component createGridTable() {
        Grid<JobAdvertisementDTO> grid = new Grid<>();

        ListDataProvider<JobAdvertisementDTO> dataProvider = new ListDataProvider<>(
                jobAdvertisementList);
        grid.setDataProvider(dataProvider);

        Grid.Column<JobAdvertisementDTO> idColumn = grid
                .addColumn(JobAdvertisementDTO::getJobAdvertisementId)
                .setWidth("65px")
                .setFlexGrow(0)
                .setHeader("ID")
                .setSortable(true);

        Grid.Column<JobAdvertisementDTO> jobTitleColumn = grid
                .addColumn(JobAdvertisementDTO::getJobTitle)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Title")
                .setSortable(true);

        Grid.Column<JobAdvertisementDTO> companyColumn = grid
                .addColumn((advertisement) -> {
                    JobPortalUserDTO user = manageUserControl.readUser(advertisement.getId());
                    if(user != null) {
                        if(user instanceof CompanyDTO) {
                            CompanyDTO company = (CompanyDTO) user;
                            return company.getCompanyName();
                        } else {
                            return user.getFirstName() + " " + user.getLastName();
                        }
                    } else {
                        return "";
                    }
                })
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Company")
                .setSortable(true);

        Grid.Column<JobAdvertisementDTO> beginDateColumn = grid
                .addColumn(new LocalDateRenderer<>(JobAdvertisementDTO::getBegin, "yyyy-MM-dd"))
                .setComparator(JobAdvertisementDTO::getBegin)
                .setSortable(true)
                .setHeader("Begins");

        Grid.Column<JobAdvertisementDTO> jobTypeColumn = grid
                .addColumn(JobAdvertisementDTO::getJobType)
                .setHeader("Type")
                .setSortable(true);

        Grid.Column<JobAdvertisementDTO> tagsColumn = grid
                .addColumn(JobAdvertisementDTO::getTags)
                .setHeader("Tags");

        Grid.Column<JobAdvertisementDTO> applyColumn = grid
                .addComponentColumn(advertisement -> {
                    Button applyButton = new Button("Apply");
                    applyButton.addClickListener(e -> {
                        UI.getCurrent().navigate("apply/" + advertisement.getJobAdvertisementId());
                    });
                    return applyButton;
                })
                .setWidth("120px")
                .setFlexGrow(0)
                .setHeader("Actions");

        HeaderRow filterRow = grid.appendHeaderRow();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        JobAdvertisementSearchProxy jobAdvertisementSearch = new JobAdvertisementSearchProxy(dataProvider, manageUserControl);

        // Set filters
        filterRow.getCell(jobTitleColumn).setComponent(createFilterInput(jobAdvertisementSearch::setJobTitle));
        filterRow.getCell(companyColumn).setComponent(createFilterInput(jobAdvertisementSearch::setCompany));
        filterRow.getCell(jobTypeColumn).setComponent(createFilterInput(jobAdvertisementSearch::setJobType));
        filterRow.getCell(tagsColumn).setComponent(createFilterInput(jobAdvertisementSearch::setTags));

        return grid;
    }

    private TextField createFilterInput(Consumer<String> valueChangeConsumer) {
        TextField field = new TextField();
        field.addValueChangeListener(event -> valueChangeConsumer.accept(event.getValue()));
        field.setValueChangeMode(ValueChangeMode.EAGER);
        field.setSizeFull();
        field.setPlaceholder("Filter");
        return field;
    }

    private Component createTitle() {
        return new H3("Search for Jobs");
    }


};
