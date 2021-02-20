package com.oopclass.breadapp.views;

import java.util.ResourceBundle;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
public enum FxmlView {

    USER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("user.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/User.fxml";
        }
    } , PRODUCT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("product.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Product.fxml";
        }
    }, MTRESERVATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("mtreservation.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/MTReservation.fxml";
        }
        } , MTAPPOINTMENT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("mtappointment.title");
        }

        @Override
        public String getFxmlFile() {
           return "/fxml/MTAppointment.fxml";
        }
        
        }
        , CANCELATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("cancelation.title");
        }

        @Override
        public String getFxmlFile() {
           return "/fxml/Cancelation.fxml";
        }
        
        };

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
