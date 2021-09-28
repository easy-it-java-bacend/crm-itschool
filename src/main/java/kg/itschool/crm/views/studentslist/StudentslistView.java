package kg.itschool.crm.views.studentslist;

import java.util.Optional;

import kg.itschool.crm.data.entity.Student;
import kg.itschool.crm.data.service.StudentService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import kg.itschool.crm.views.MainLayout;
import javax.annotation.security.RolesAllowed;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.component.textfield.TextField;

@PageTitle("Students list")
@Route(value = "students-list/:studentID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("user")
public class StudentslistView extends Div implements BeforeEnterObserver {

    private final String STUDENT_ID = "studentID";
    private final String STUDENT_EDIT_ROUTE_TEMPLATE = "students-list/%d/edit";

    private Grid<Student> grid = new Grid<>(Student.class, false);

    private TextField studentID;
    private TextField firstName;
    private TextField lastName;
    private TextField debt;
    private TextField status;
    private TextField phone;
    private TextField group;
    private TextField email;
    private DatePicker dateOfBirth;
    private TextField gender;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Student> binder;

    private Student student;

    private StudentService studentService;

    public StudentslistView(@Autowired StudentService studentService) {
        this.studentService = studentService;
        addClassNames("studentslist-view", "flex", "flex-col", "h-full");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("studentID").setAutoWidth(true);
        grid.addColumn("firstName").setAutoWidth(true);
        grid.addColumn("lastName").setAutoWidth(true);
        grid.addColumn("debt").setAutoWidth(true);
        grid.addColumn("status").setAutoWidth(true);
        grid.addColumn("phone").setAutoWidth(true);
        grid.addColumn("group").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("dateOfBirth").setAutoWidth(true);
        grid.addColumn("gender").setAutoWidth(true);
        grid.setItems(query -> studentService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(STUDENT_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(StudentslistView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Student.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(studentID).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("studentID");
        binder.forField(debt).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("debt");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.student == null) {
                    this.student = new Student();
                }
                binder.writeBean(this.student);

                studentService.update(this.student);
                clearForm();
                refreshGrid();
                Notification.show("Student details stored.");
                UI.getCurrent().navigate(StudentslistView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the student details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> studentId = event.getRouteParameters().getInteger(STUDENT_ID);
        if (studentId.isPresent()) {
            Optional<Student> studentFromBackend = studentService.get(studentId.get());
            if (studentFromBackend.isPresent()) {
                populateForm(studentFromBackend.get());
            } else {
                Notification.show(String.format("The requested student was not found, ID = %d", studentId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(StudentslistView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        studentID = new TextField("Student ID");
        firstName = new TextField("First Name");
        lastName = new TextField("Last Name");
        debt = new TextField("Debt");
        status = new TextField("Status");
        phone = new TextField("Phone");
        group = new TextField("Group");
        email = new TextField("Email");
        dateOfBirth = new DatePicker("Date Of Birth");
        gender = new TextField("Gender");
        Component[] fields = new Component[]{studentID, firstName, lastName, debt, status, phone, group, email,
                dateOfBirth, gender};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Student value) {
        this.student = value;
        binder.readBean(this.student);

    }
}
