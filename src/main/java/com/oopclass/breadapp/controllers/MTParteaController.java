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
import com.oopclass.breadapp.models.MTPartea;
import com.oopclass.breadapp.services.impl.MTParteaService;
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
public class MTParteaController implements Initializable {

    @FXML
    private Label mtparteaId;

    @FXML
    private TextField fullName;

    @FXML
    private TextField contactNumber;

    @FXML
    private TextField address;

    @FXML
    private DatePicker doa;

    @FXML
    private TextField pkgName;

    @FXML
    private Button saveReservation;

    @FXML
    private Button reset;

    @FXML
    private Button deleteMightytea;
    
    @FXML
    private Button openDelivery;

    @FXML
    private TableView<MTPartea> dataTable;

    @FXML
    private TableColumn<MTPartea, String> colId;

    @FXML
    private TableColumn<MTPartea, String> colFullName;

    @FXML
    private TableColumn<MTPartea, String> colContactNumber;

    @FXML
    private TableColumn<MTPartea, String> colAddress;

    @FXML
    private TableColumn<MTPartea, LocalDate> colDOA;

    @FXML
    private TableColumn<MTPartea, String> colPackage;

    @FXML
    private TableColumn<MTPartea, Boolean> colEdit;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private MTParteaService mtparteaService;

    private ObservableList<MTPartea> mtparteaList = FXCollections.observableArrayList();

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }
    
    @FXML
    private void openDelivery(ActionEvent event){
        stageManager.switchScene(FxmlView.MTDelivery);
    }

    @FXML
    private void saveReservations(ActionEvent event) {

        if (validate("Full Name", getFullName(), "(?:\\s*[a-zA-Z0-9,_\\.\\077\\0100\\*\\+\\&\\#\\'\\~\\;\\-\\!\\@\\;]{2,}\\s*)*")) {

            if (mtparteaId.getText() == null || "".equals(mtparteaId.getText())) {
                if (true) {
                    MTPartea mtpartea = new MTPartea();
                    mtpartea.setFullName(getFullName());
                    mtpartea.setContactNumber(getContactNumber());
                    mtpartea.setAddress(getAddress());
                    mtpartea.setDoa(getDoa());
                    mtpartea.setPkgName(getPkgName());
                    MTPartea newMtreservation = mtparteaService.save(mtpartea);
                    saveAlert(newMtreservation);
                }

            } else {
                MTPartea mtpartea = mtparteaService.find(Long.parseLong(mtparteaId.getText()));
                mtpartea.setFullName(getFullName());
                mtpartea.setContactNumber(getContactNumber());
                mtpartea.setAddress(getAddress());
                mtpartea.setDoa(getDoa());
                mtpartea.setPkgName(getPkgName());
                MTPartea updatedMtreservation = mtparteaService.update(mtpartea);
                updateAlert(updatedMtreservation);
            }

            clearFields();
            loadMightyteaDetails();
        }

    }

    @FXML
    private void deleteMightyteas(ActionEvent event) {
        List<MTPartea> mtparteas = dataTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            mtparteaService.deleteInBatch(mtparteas);
        }

        loadMightyteaDetails();
    }

    private void clearFields() {
        mtparteaId.setText(null);
        fullName.clear();
        contactNumber.clear();
        address.clear();
        pkgName.clear();
        doa.getEditor().clear();
    }
        

    private void saveAlert(MTPartea mtpartea) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mightytea saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The mtpartea " + mtpartea.getFullName() + " has been created with ID: " + mtpartea.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(MTPartea mtpartea) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mightytea updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The mtpartea " + mtpartea.getFullName() + " has been updated.");
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
     
      public String getPkgName() {
        return pkgName.getText();
    }
    

    /*
	 *  Set All mtparteaTable column properties
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
        colPackage.setCellValueFactory(new PropertyValueFactory<>("pkgName"));
        colEdit.setCellFactory(cellFactory);
        
    }

    Callback<TableColumn<MTPartea, Boolean>, TableCell<MTPartea, Boolean>> cellFactory
            = new Callback<TableColumn<MTPartea, Boolean>, TableCell<MTPartea, Boolean>>() {
        @Override
        public TableCell<MTPartea, Boolean> call(final TableColumn<MTPartea, Boolean> param) {
            final TableCell<MTPartea, Boolean> cell = new TableCell<MTPartea, Boolean>() {
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
                            MTPartea mtpartea = getTableView().getItems().get(getIndex());
                            updateMightytea(mtpartea);
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

                private void updateMightytea(MTPartea mtpartea) {
                    mtparteaId.setText(Long.toString(mtpartea.getId()));
                    fullName.setText(mtpartea.getFullName());
                    contactNumber.setText(mtpartea.getContactNumber());
                    address.setText(mtpartea.getAddress());
                    pkgName.setText(mtpartea.getPkgName());
                    doa.setValue(mtpartea.getDoa());
                }
            };
            return cell;
        }
    };

    /*
	 *  Add All mtparteas to observable list and update table
     */
    private void loadMightyteaDetails() {
        mtparteaList.clear();
        mtparteaList.addAll(mtparteaService.findAll());

        dataTable.setItems(mtparteaList);
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

        // Add all mtparteas into table
        loadMightyteaDetails();
    }
}

