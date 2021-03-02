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
import com.oopclass.breadapp.models.MTDelivery;
import com.oopclass.breadapp.services.impl.MTDeliveryService;
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
public class MTDeliveryController implements Initializable {
    
    @FXML
    private Label mtdeliveryId;

    @FXML
    private Button reset;

    @FXML
    private Button delete;

    @FXML
    private Button addCustomer;
    
    
    @FXML
    private Button openSchedule;
    

    @FXML
    private TextField address;

    @FXML
    private TextField contactNumber;

    @FXML
    private TextField riderName;

    @FXML
    private TextField customerName;

    @FXML
    private DatePicker delivery;

    @FXML
    private TableView<MTDelivery> deliveryTable;

    @FXML
    private TableColumn<MTDelivery, String> colId;

    @FXML
    private TableColumn<MTDelivery, String> colCustomerName;

    @FXML
    private TableColumn<MTDelivery, String> colRiderName;

    @FXML
    private TableColumn<MTDelivery, String> colContactNumber;

    @FXML
    private TableColumn<MTDelivery, String> colAddress;

    @FXML
    private TableColumn<MTDelivery, LocalDate> colDelivery;

    @FXML
    private TableColumn<MTDelivery, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private MTDeliveryService mtdeliveryService;

    private ObservableList<MTDelivery> mtdeliveryList = FXCollections.observableArrayList();

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    
    @FXML
    private void openSchedule(ActionEvent event) {
        stageManager.switchScene(FxmlView.MTScheduleMeeting);
    }

    @FXML
    private void addCustomers(ActionEvent event) {

        if (validate("Customer Name", getCustomerName(), "(?:\\s*[a-zA-Z0-9,_\\.\\077\\0100\\*\\+\\&\\#\\'\\~\\;\\-\\!\\@\\;]{2,}\\s*)*")) {

            if (mtdeliveryId.getText() == null || "".equals(mtdeliveryId.getText())) {
                if (true) {
                    MTDelivery mtdelivery = new MTDelivery();
                    mtdelivery.setCustomerName(getCustomerName());
                    mtdelivery.setRiderName(getRiderName());
                    mtdelivery.setAddress(getAddress());
                    mtdelivery.setContactNumber(getContactNumber());
                    mtdelivery.setDeliverySchedule(getDeliverySchedule());
                    MTDelivery newMtdelivery = mtdeliveryService.save(mtdelivery);
                    saveAlert(newMtdelivery);
                }

            } else {
                MTDelivery mtdelivery = mtdeliveryService.find(Long.parseLong(mtdeliveryId.getText()));
                mtdelivery.setCustomerName(getCustomerName());
                mtdelivery.setRiderName(getRiderName());
                mtdelivery.setContactNumber(getContactNumber());
                mtdelivery.setAddress(getAddress());
                mtdelivery.setDeliverySchedule(getDeliverySchedule());
                MTDelivery newMtdelivery = mtdeliveryService.save(mtdelivery);
                MTDelivery updatedMtdelivery = mtdeliveryService.update(mtdelivery);
                updateAlert(updatedMtdelivery);
            }

            clearFields();
            loadMightyteaDetails();
        }

    }

    @FXML
    private void deleteCustomers(ActionEvent event) {
        List<MTDelivery> mtdeliverys = deliveryTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            mtdeliveryService.deleteInBatch(mtdeliverys);
        }

        loadMightyteaDetails();
    }

    private void clearFields() {
        mtdeliveryId.setText(null);
        customerName.clear();
        riderName.clear();
        contactNumber.clear();
        address.clear();
        delivery.getEditor().clear();
    }

    private void saveAlert(MTDelivery mtdelivery) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mightytea saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The mtdelivery " + mtdelivery.getCustomerName() + " has been created with ID: " + mtdelivery.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(MTDelivery mtdelivery) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mightytea updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The mtdelivery " + mtdelivery.getCustomerName() + " has been updated.");
        alert.showAndWait();
    }

    public String getCustomerName() {
        return customerName.getText();
    }

    public String getRiderName() {
        return riderName.getText();
    }

    public String getContactNumber() {
        return contactNumber.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public LocalDate getDeliverySchedule() {
        return delivery.getValue();
    }


    /*
	 *  Set All mtdeliveryTable column properties
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
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colRiderName.setCellValueFactory(new PropertyValueFactory<>("riderName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colDelivery.setCellValueFactory(new PropertyValueFactory<>("deliverySchedule"));
        colEdit.setCellFactory(cellFactory); 

    }

    Callback<TableColumn<MTDelivery, Boolean>, TableCell<MTDelivery, Boolean>> cellFactory
            = new Callback<TableColumn<MTDelivery, Boolean>, TableCell<MTDelivery, Boolean>>() {
        @Override
        public TableCell<MTDelivery, Boolean> call(final TableColumn<MTDelivery, Boolean> param) {
            final TableCell<MTDelivery, Boolean> cell = new TableCell<MTDelivery, Boolean>() {
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
                            MTDelivery mtdelivery = getTableView().getItems().get(getIndex());
                            updateMightytea(mtdelivery);
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

                private void updateMightytea(MTDelivery mtdelivery) {
                    mtdeliveryId.setText(Long.toString(mtdelivery.getId()));
                    customerName.setText(mtdelivery.getCustomerName());
                    contactNumber.setText(mtdelivery.getContactNumber());
                    riderName.setText(mtdelivery.getRiderName());
                    address.setText(mtdelivery.getAddress());
                    delivery.setValue(mtdelivery.getDeliverySchedule());
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All mtdeliverys to observable list and update table
     */
    private void loadMightyteaDetails() {
        mtdeliveryList.clear();
        mtdeliveryList.addAll(mtdeliveryService.findAll());

        deliveryTable.setItems(mtdeliveryList);
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

        deliveryTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setColumnProperties();

        // Add all mtdeliverys into table
        loadMightyteaDetails();
    }
}
