package com.oopclass.breadapp.views;

import java.util.ResourceBundle;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
public enum FxmlView {

    
    MTPartea {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("mtpartea.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/MTPartea.fxml";
        }
        } , MTScheduleMeeting {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("mtschedulemeeting.title");
        }

        @Override
        public String getFxmlFile() {
           return "/fxml/MTScheduleMeeting.fxml";
        }
        
        }, MTDelivery {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("mtdelivery.title");
        }

        @Override
        public String getFxmlFile() {
           return "/fxml/MTDelivery.fxml";
        }
};

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
