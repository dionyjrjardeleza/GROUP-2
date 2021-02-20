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
import com.oopclass.breadapp.models.MTReservation;
import com.oopclass.breadapp.services.impl.MTReservationService;
import com.oopclass.breadapp.views.FxmlView;
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
public class MTReservationController implements Initializable {

    @FXML
    private Label mtreservationId;

    @FXML
    private TextField fullName;
    
    @FXML
    private TextField contactNumber;
    
    @FXML
    private TextField address;
    
    @FXML
    private DatePicker doa;

    @FXML
    private Button saveReservation;

    @FXML
    private Button editReservation;
    
    @FXML
    private Button deleteReservation;
    
    @FXML
    private Button openAppointment;
    
    @FXML
    private Button openCancelation;
    

    @FXML
    private TableView<MTReservation> dataTable;

    @FXML
    private TableColumn<MTReservation, Long> colId;

    @FXML
    private TableColumn<MTReservation, String> colFullName;
    
    @FXML
    private TableColumn<MTReservation, String> colContactNumber;
    
    @FXML
    private TableColumn<MTReservation, String> colAddress;
    
    @FXML
    private TableColumn<MTReservation, LocalDate> colDOA;
    
    @FXML
    private TableColumn<MTReservation, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private MTReservationService mtreservationService;

    private ObservableList<MTReservation> mtreservationList = FXCollections.observableArrayList();

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    @FXML
    private void openAppointment(ActionEvent event) {
        stageManager.switchScene(FxmlView.MTAPPOINTMENT);
    }
    
    @FXML
    private void openCancelation(ActionEvent event) {
        stageManager.switchScene(FxmlView.CANCELATION);
    }
    @FXML
    private void saveReservations(ActionEvent event) {

        if (validate("Full Name", getFullName(), "(?:\\s*[a-zA-Z0-9,_\\.\\077\\0100\\*\\+\\&\\#\\'\\~\\;\\-\\!\\@\\;]{2,}\\s*)*")) {

            if (mtreservationId.getText() == null || "".equals(mtreservationId.getText())) {
                if (true) {
                    MTReservation mtreservation = new MTReservation();
                    mtreservation.setFullName(getFullName());
                    mtreservation.setContactNumber(getContactNumber());
                    mtreservation.setAddress(getAddress());
                    mtreservation.setDoa(getDoa());
                    MTReservation newMtreservation = mtreservationService.save(mtreservation);
                    saveAlert(newMtreservation);
                }

            } else {
                MTReservation mtreservation = mtreservationService.find(Long.parseLong(mtreservationId.getText()));
                mtreservation.setFullName(getFullName());
                mtreservation.setContactNumber(getContactNumber());
                mtreservation.setAddress(getAddress());
                mtreservation.setDoa(getDoa());
                MTReservation updatedMtreservation = mtreservationService.update(mtreservation);
                updateAlert(updatedMtreservation);
            }

            clearFields();
            loadMightyteaDetails();
        }

    }

    @FXML
    private void deleteMightyteas(ActionEvent event) {
        List<MTReservation> mtreservations = dataTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            mtreservationService.deleteInBatch(mtreservations);
        }

        loadMightyteaDetails();
    }

    private void clearFields() {
        mtreservationId.setText(null);
        fullName.clear();
        contactNumber.clear();
        address.clear();
        doa.getEditor().clear();
    }
        

    private void saveAlert(MTReservation mtreservation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mightytea saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The mtreservation " + mtreservation.getFullName() + " has been created with ID: " + mtreservation.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(MTReservation mtreservation) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mightytea updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The mtreservation " + mtreservation.getFullName() + " has been updated.");
        alert.showAndWait();
    }


    public String getFullName() {
        return fullName.getText();
    }
    
    public String getContactNumber() {
        return contactNumber.getText();
    }
    
      public String getAddress() {
        return address.getText();
    }
     
     public  LocalDate getDoa() {
        return doa.getValue();
     }
    

    /*
	 *  Set All mtreservationTable column properties
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

    Callback<TableColumn<MTReservation, Boolean>, TableCell<MTReservation, Boolean>> cellFactory
            = new Callback<TableColumn<MTReservation, Boolean>, TableCell<MTReservation, Boolean>>() {
        @Override
        public TableCell<MTReservation, Boolean> call(final TableColumn<MTReservation, Boolean> param) {
            final TableCell<MTReservation, Boolean> cell = new TableCell<MTReservation, Boolean>() {
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
                            MTReservation mtreservation = getTableView().getItems().get(getIndex());
                            updateMightytea(mtreservation);
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

                private void updateMightytea(MTReservation mtreservation) {
                    mtreservationId.setText(Long.toString(mtreservation.getId()));
                    fullName.setText(mtreservation.getFullName());
                    contactNumber.setText(mtreservation.getContactNumber());
                    address.setText(mtreservation.getAddress());
                    doa.setValue(mtreservation.getDoa());
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All mtreservations to observable list and update table
     */
    private void loadMightyteaDetails() {
        mtreservationList.clear();
        mtreservationList.addAll(mtreservationService.findAll());

        dataTable.setItems(mtreservationList);
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

        dataTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all mtreservations into table
        loadMightyteaDetails();
    }
}

