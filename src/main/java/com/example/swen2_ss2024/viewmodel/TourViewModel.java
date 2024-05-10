package com.example.swen2_ss2024.viewmodel;
import com.example.swen2_ss2024.models.Tour ;
import com.example.swen2_ss2024.event.Event ;
import com.example.swen2_ss2024.event.ObjectSubscriber ;
import com.example.swen2_ss2024.event.Publisher ;
import com.example.swen2_ss2024.service.NewTourService;
import com.example.swen2_ss2024.service.TourListService ;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class TourViewModel {
    private final NewTourService newTourService;

    public TourViewModel(Publisher publisher) {
        this.newTourService = new NewTourService();
    }

    public void onAdd() {
        newTourService.loadFXML("add-tour-view.fxml");
    }

    public void onDelete() {
        System.out.println("Delete action triggered");
    }

    public void onMore() {
        System.out.println("More options");
    }
}
