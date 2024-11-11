package service;

import jakarta.enterprise.context.ApplicationScoped;
import model.Alert;

import java.util.List;

@ApplicationScoped
public class AlertService {

    public List<Alert> getAllOpenAlerts() {
        return Alert.find("markedHasRead", false).list();
    }

}
