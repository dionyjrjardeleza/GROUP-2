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
import com.oopclass.breadapp.models.MTScheduleMeeting;
import com.oopclass.breadapp.services.impl.MTScheduleMeetingService;
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

@Controller
public class MTScheduleMeetingController implements Initializable {

      @FXML
    private Label mtschedulemeetingId;

    @FXML
    private TextField mtfullName;

    @FXML
    private TextField mtcontactNumber;

    @FXML
    private TextField mtaddress;

    @FXML
    private DatePicker mtdoa;

    @FXML
    private RadioButton approve;

    @FXML
    private RadioButton denied;

    @FXML
    private Button add;

    @FXML
    private Button reset;

    @FXML
    private Button delete;
    
    @FXML
    private Button openPartea;
    
    @FXML
    private TableView<MTScheduleMeeting> mtDataTable;

    @FXML
    private TableColumn<MTScheduleMeeting, Long> colId;

    @FXML
    private TableColumn<MTScheduleMeeting, String> colFullName;

    @FXML
    private TableColumn<MTScheduleMeeting, String> colContactNumber;

    @FXML
    private TableColumn<MTScheduleMeeting, String> colAddress;

    @FXML
    private TableColumn<MTScheduleMeeting, LocalDate> colDOA;

    @FXML
    private TableColumn<MTScheduleMeeting, String> colAnswer;

    @FXML
    private TableColumn<MTScheduleMeeting, Boolean> colEdit;
    
    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private MTScheduleMeetingService mtschedulemeetingService;

    private ObservableList<MTScheduleMeeting> mtschedulemeetingList = FXCollections.observableArrayList();

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    @FXML
    private void openPartea(ActionEvent event){
        stageManager.switchScene(FxmlView.MTPartea);
    }
    

    @FXML
    private void addAppointments(ActionEvent event) {

//        if (validate("mtfull Name", getFullName(), "(?:\\s*[a-zA-Z0-9,_\\.\\077\\0100\\*\\+\\&\\#\\'\\~\\;\\-\\!\\@\\;]{2,}\\s*)*")) {

            if (mtschedulemeetingId.getText() == null || "".equals(mtschedulemeetingId.getText())) {
                if (true) {
                    MTScheduleMeeting mtschedulemeeting = new MTScheduleMeeting();
                    mtschedulemeeting.setFullName(getFullName());
                    mtschedulemeeting.setContactNumber(getContactNumber());
                    mtschedulemeeting.setAddress(getAddress());
                    mtschedulemeeting.setDoa(getDoa());
                    mtschedulemeeting.setAnswer(getAnswer());
                    MTScheduleMeeting newMTschedulemeeting = mtschedulemeetingService.save(mtschedulemeeting);
                    saveAlert(newMTschedulemeeting);
                }

            } else {
                MTScheduleMeeting mtschedulemeeting = mtschedulemeetingService.find(Long.parseLong(mtschedulemeetingId.getText()));
                mtschedulemeeting.setFullName(getFullName());
                mtschedulemeeting.setContactNumber(getContactNumber());
                mtschedulemeeting.setAddress(getAddress());
                mtschedulemeeting.setDoa(getDoa());
                mtschedulemeeting.setAnswer(getAnswer());
                MTScheduleMeeting updatedMTschedulemeeting = mtschedulemeetingService.update(mtschedulemeeting);
                updateAlert(updatedMTschedulemeeting);
            }

            clearFields();
            loadMightyteaDetails();
//        }

    }

    @FXML
   private void deleteAppointments(ActionEvent event) {
        List<MTScheduleMeeting> mtschedulemeetings = mtDataTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            mtschedulemeetingService.deleteInBatch(mtschedulemeetings);
        }

        loadMightyteaDetails();
    }

    private void clearFields() {
        mtschedulemeetingId.setText(null);
        mtfullName.clear();
        mtcontactNumber.clear();
        mtaddress.clear();
        mtdoa.getEditor().clear();
    }
        

    private void saveAlert(MTScheduleMeeting mtschedulemeeting) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mightytea saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The mtschedulemeeting " + mtschedulemeeting.getFullName() + " has been created with ID: " + mtschedulemeeting.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(MTScheduleMeeting mtschedulemeeting) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mightytea updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The mtschedulemeeting " + mtschedulemeeting.getFullName() + " has been updated.");
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
     
     public String getAnswer() {
        return approve.isSelected()? "Approve":"Denied";
     } 
    

    /*
	 *  Set All mtschedulemeetingTable column properties
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
        colAnswer.setCellValueFactory(new PropertyValueFactory<>("answer"));
        colEdit.setCellFactory(cellFactory);
        
    }

    Callback<TableColumn<MTScheduleMeeting, Boolean>, TableCell<MTScheduleMeeting, Boolean>> cellFactory
            = new Callback<TableColumn<MTScheduleMeeting, Boolean>, TableCell<MTScheduleMeeting, Boolean>>() {
        @Override
        public TableCell<MTScheduleMeeting, Boolean> call(final TableColumn<MTScheduleMeeting, Boolean> param) {
            final TableCell<MTScheduleMeeting, Boolean> cell = new TableCell<MTScheduleMeeting, Boolean>() {
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
                            MTScheduleMeeting mtschedulemeeting = getTableView().getItems().get(getIndex());
                            updateMightytea(mtschedulemeeting);
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

                private void updateMightytea(MTScheduleMeeting mtschedulemeeting) {
                    mtschedulemeetingId.setText(Long.toString(mtschedulemeeting.getId()));
                    mtfullName.setText(mtschedulemeeting.getFullName());
                    mtcontactNumber.setText(mtschedulemeeting.getContactNumber());
                    mtaddress.setText(mtschedulemeeting.getAddress());
                    mtdoa.setValue(mtschedulemeeting.getDoa());
                    if(mtschedulemeeting.getAnswer().equals("Approve")){
                    approve.setSelected(true);
                 } else {
                    denied.setSelected(true);
                }
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All mtschedulemeetings to observable list and update table
     */
    private void loadMightyteaDetails() {
        mtschedulemeetingList.clear();
        mtschedulemeetingList.addAll(mtschedulemeetingService.findAll());

        mtDataTable.setItems(mtschedulemeetingList);
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

        // Add all mtschedulemeetings into table
        loadMightyteaDetails();
    }
}

