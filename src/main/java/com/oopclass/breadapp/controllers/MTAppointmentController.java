package com.oopclass.breadapp.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.oopclass.breadapp.config.StageManager;
import com.oopclass.breadapp.models.MTAppointment;
import com.oopclass.breadapp.services.impl.MTAppointmentService;
import javafx.application.Platform;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Controller
public class MTAppointmentController implements Initializable {

    @FXML
    private Label mtappointmentId;

    @FXML
    private TextField mtfullName;

    @FXML
    private TextField mtcontactNumber;

    @FXML
    private TextField mtaddress;

    @FXML
    private DatePicker mtdoa;

    @FXML
    private Button addAppointment;

    @FXML
    private Button deleteAppointment;

    

    @FXML
    private TableView<MTAppointment> mtDataTable;

    @FXML
    private TableColumn<MTAppointment, Long> colId;

    @FXML
    private TableColumn<MTAppointment, String> colFullName;
    
    @FXML
    private TableColumn<MTAppointment, String> colContactNumber;
    
    @FXML
    private TableColumn<MTAppointment, String> colAddress;
    
    @FXML
    private TableColumn<MTAppointment, LocalDate> colDOA;
    
    @FXML
    private TableColumn<MTAppointment, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private MTAppointmentService mtappointmentService;

    private ObservableList<MTAppointment> mtappointmentList = FXCollections.observableArrayList();

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void addAppointments(ActionEvent event) {

//        if (validate("mtfull Name", getFullName(), "(?:\\s*[a-zA-Z0-9,_\\.\\077\\0100\\*\\+\\&\\#\\'\\~\\;\\-\\!\\@\\;]{2,}\\s*)*")) {

            if (mtappointmentId.getText() == null || "".equals(mtappointmentId.getText())) {
                if (true) {
                    MTAppointment mtappointment = new MTAppointment();
                    mtappointment.setFullName(getFullName());
                    mtappointment.setContactNumber(getContactNumber());
                    mtappointment.setAddress(getAddress());
                    mtappointment.setDoa(getDoa());
                    MTAppointment newMtappointment = mtappointmentService.save(mtappointment);
                    saveAlert(newMtappointment);
                }

            } else {
                MTAppointment mtappointment = mtappointmentService.find(Long.parseLong(mtappointmentId.getText()));
                mtappointment.setFullName(getFullName());
                mtappointment.setContactNumber(getContactNumber());
                mtappointment.setAddress(getAddress());
                mtappointment.setDoa(getDoa());
                MTAppointment updatedMtappointment = mtappointmentService.update(mtappointment);
                updateAlert(updatedMtappointment);
            }

            clearFields();
            loadMightyteaDetails();
//        }

    }

    @FXML
   private void deleteAppointments(ActionEvent event) {
        List<MTAppointment> mtappointments = mtDataTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            mtappointmentService.deleteInBatch(mtappointments);
        }

        loadMightyteaDetails();
    }

    private void clearFields() {
        mtappointmentId.setText(null);
        mtfullName.clear();
        mtcontactNumber.clear();
        mtaddress.clear();
        mtdoa.getEditor().clear();
    }
        

    private void saveAlert(MTAppointment mtappointment) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mightytea saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The mtappointment " + mtappointment.getFullName() + " has been created with ID: " + mtappointment.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(MTAppointment mtappointment) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mightytea updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The mtappointment " + mtappointment.getFullName() + " has been updated.");
        alert.showAndWait();
    }


    public String getFullName() {
        return mtfullName.getText();
    }
    
    public String getContactNumber() {
        return mtcontactNumber.getText();
    }
    
      public String getAddress() {
        return mtaddress.getText();
    }
     
     public  LocalDate getDoa() {
        return mtdoa.getValue();
     }
    

    /*
	 *  Set All mtappointmentTable column properties
     */
    private void setColumnProperties() {
        /* Override date format in table
		 * colDOB.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDate>() {
			 String pattern = "dd/MM/yyyy";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 }));*/

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOA.setCellValueFactory(new PropertyValueFactory<>("doa"));
        colEdit.setCellFactory(cellFactory);
        
    }

    Callback<TableColumn<MTAppointment, Boolean>, TableCell<MTAppointment, Boolean>> cellFactory
            = new Callback<TableColumn<MTAppointment, Boolean>, TableCell<MTAppointment, Boolean>>() {
        @Override
        public TableCell<MTAppointment, Boolean> call(final TableColumn<MTAppointment, Boolean> param) {
            final TableCell<MTAppointment, Boolean> cell = new TableCell<MTAppointment, Boolean>() {
                Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                final Button btnEdit = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnEdit.setOnAction(e -> {
                            MTAppointment mtappointment = getTableView().getItems().get(getIndex());
                            updateMightytea(mtappointment);
                        });

                        btnEdit.setStyle("-fx-background-color: transparent;");
                        ImageView iv = new ImageView();
                        iv.setImage(imgEdit);
                        iv.setPreserveRatio(true);
                        iv.setSmooth(true);
                        iv.setCache(true);
                        btnEdit.setGraphic(iv);

                        setGraphic(btnEdit);
                        setAlignment(Pos.CENTER);
                        setText(null);
                    }
                }

                private void updateMightytea(MTAppointment mtappointment) {
                    mtappointmentId.setText(Long.toString(mtappointment.getId()));
                    mtfullName.setText(mtappointment.getFullName());
                    mtcontactNumber.setText(mtappointment.getContactNumber());
                    mtaddress.setText(mtappointment.getAddress());
                    mtdoa.setValue(mtappointment.getDoa());
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All mtappointments to observable list and update table
     */
    private void loadMightyteaDetails() {
        mtappointmentList.clear();
        mtappointmentList.addAll(mtappointmentService.findAll());

        mtDataTable.setItems(mtappointmentList);
    }

    /*
	 * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mtDataTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all mtappointments into table
        loadMightyteaDetails();
    }
}

