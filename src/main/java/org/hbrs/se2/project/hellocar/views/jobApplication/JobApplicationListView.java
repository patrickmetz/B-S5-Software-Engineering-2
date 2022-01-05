package org.hbrs.se2.project.hellocar.views.jobApplication;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.hbrs.se2.project.hellocar.control.ManageJobAdvertisementControl;
import org.hbrs.se2.project.hellocar.control.ManageJobApplicationControl;
import org.hbrs.se2.project.hellocar.control.ManageUserControl;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.dtos.JobApplicationDTO;
import org.hbrs.se2.project.hellocar.dtos.account.JobPortalUserDTO;
import org.hbrs.se2.project.hellocar.services.search.JobAdvertisementSearch;
import org.hbrs.se2.project.hellocar.services.search.JobAdvertisementSearchProxy;
import org.hbrs.se2.project.hellocar.services.search.JobApplicationSearchProxy;
import org.hbrs.se2.project.hellocar.services.search.impl.JobAdvertisementSearchImpl;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.views.AppView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Route(value = Globals.Pages.JOB_APPLICATION_LIST, layout = AppView.class)
@RouteAlias("applicationlist")
@CssImport("./styles/views/jobApplication/jobApplication.css")
@PageTitle("Show Job Applications")
public class JobApplicationListView extends Div {

    private List<JobApplicationDTO> jobApplicationList;
    private ManageUserControl manageUserControl;
    private ManageJobApplicationControl manageJobApplicationControl;
    private ManageJobAdvertisementControl manageJobAdvertisementControl;

    public JobApplicationListView(
            ManageJobApplicationControl manageJobApplicationControl,
            ManageUserControl manageUserControl,
            ManageJobAdvertisementControl manageJobAdvertisementControl) {
        this.manageJobApplicationControl = manageJobApplicationControl;
        this.manageUserControl = manageUserControl;
        this.manageJobAdvertisementControl = manageJobAdvertisementControl;
        this.jobApplicationList = filterApplicationsForUser(manageJobApplicationControl.readAllJobApplications());

        addClassName("job-application-list-view");

        add(this.createTitle());
        add(this.createGridTable());
    }

    private List<JobApplicationDTO> filterApplicationsForUser(List<JobApplicationDTO> applications) {
        JobPortalUserDTO user = this.getCurrentUser();
        if(user == null) return new ArrayList<>();
        return applications
                .stream()
                .filter(a ->  {
                    JobAdvertisementDTO advertisement = manageJobAdvertisementControl.readJobAdvertisement(a.getJobAdvertisementId());
                    return advertisement.getId() == user.getId();
                })
                .collect(Collectors.toList());
    }

    private JobPortalUserDTO getCurrentUser() {
        return (JobPortalUserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    private Component createGridTable() {
        Grid<JobApplicationDTO> grid = new Grid<>();

        ListDataProvider<JobApplicationDTO> dataProvider = new ListDataProvider<>(
                jobApplicationList);
        grid.setDataProvider(dataProvider);

        Grid.Column<JobApplicationDTO> idColumn = grid
                .addColumn(JobApplicationDTO::getJobApplicationId)
                .setWidth("65px")
                .setFlexGrow(0)
                .setHeader("ID")
                .setSortable(true);

        Grid.Column<JobApplicationDTO> advertisementTitleColumn = grid
                .addColumn((application) -> {
                    JobAdvertisementDTO advertisement = this.manageJobAdvertisementControl.readJobAdvertisement(application.getJobAdvertisementId());
                    return advertisement.getJobTitle();
                })
                .setAutoWidth(true)
                .setHeader("Job Title")
                .setSortable(true);

        Grid.Column<JobApplicationDTO> applicantFirstNameColumn = grid
                .addColumn((application) -> {
                    JobPortalUserDTO jobUser = this.manageUserControl.readUser(application.getId());
                    return jobUser.getFirstName();
                })
                .setAutoWidth(true)
                .setHeader("Applicant First Name")
                .setSortable(true);

        Grid.Column<JobApplicationDTO> applicantLastNameColumn = grid
                .addColumn((application) -> {
                    JobPortalUserDTO jobUser = this.manageUserControl.readUser(application.getId());
                    return jobUser.getLastName();
                })
                .setAutoWidth(true)
                .setHeader("Applicant Last Name")
                .setSortable(true);

        Grid.Column<JobApplicationDTO> viewColumn = grid
                .addComponentColumn(application -> {
                    Button viewButton = new Button("View");
                    viewButton.addClickListener(e -> {

                    });
                    return viewButton;
                })
                .setWidth("105px")
                .setFlexGrow(0)
                .setHeader("Actions");

        Grid.Column<JobApplicationDTO> deleteColumn = grid
                .addComponentColumn(application -> {
                    Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
                    deleteButton.addClickListener(e -> {
                        manageJobApplicationControl.deleteJobApplication(application.getJobApplicationId());
                        dataProvider.getItems().remove(application);
                        dataProvider.refreshAll();
                    });
                    deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
                    return deleteButton;
                })
                .setWidth("80px")
                .setFlexGrow(0);

        HeaderRow filterRow = grid.appendHeaderRow();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        JobApplicationSearchProxy jobApplicationSearchProxy = new JobApplicationSearchProxy(
                dataProvider,
                manageUserControl,
                manageJobAdvertisementControl);

        // Set filters
        filterRow.getCell(advertisementTitleColumn).setComponent(createFilterInput(jobApplicationSearchProxy::setJobTitle));
        filterRow.getCell(applicantFirstNameColumn).setComponent(createFilterInput(jobApplicationSearchProxy::setApplicantFirstName));
        filterRow.getCell(applicantLastNameColumn).setComponent(createFilterInput(jobApplicationSearchProxy::setApplicantLastName));

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
        return new H3("Search for Job Applications");
    }


};
