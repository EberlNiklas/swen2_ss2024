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
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TourViewModel implements ObjectSubscriber{
    private final NewTourService newTourService;


    public void onAdd() {
        newTourService.loadFXML("add-tour-view.fxml");
    }

    public void onMore() {
        System.out.println("More options");
    }

    private final ObservableList<String> tourList = FXCollections.observableArrayList();

    private final IntegerProperty index = new SimpleIntegerProperty();

    private TourListService tourListService;

    private Publisher publisher;

    public TourViewModel(Publisher publisher, TourListService tourListService){
        this.publisher = publisher;

        this.tourListService = tourListService;

        this.newTourService = new NewTourService();

        this.publisher.subscribe(Event.TOUR_ADDED, this);

        this.index.addListener((obs, oldVal, newVal) -> selectTour(newVal.intValue()));
    }

    private void selectTour(int index){
        if (index == -1){
            System.out.println("You didn´t select a tour!");
        } else {
            System.out.println("Tour: " + tourList.get(index));
        }
    }

    private void addToList(String tour){
        tourList.add(tour);
    }

    public ObservableList<String> getTourList(){
        return tourList;
    }

    public IntegerProperty selectIndex(){
        return index;
    }

    public void delete(){
        int number = index.get();
        if(number >= 0 && number < tourList.size()){
            String tour = tourList.get(number);
            if(tourListService.deleteTourByName(tour)){
                tourList.remove(number);
            }
        }
    }

    @Override
    public void notify(Object message) {
        if (message instanceof Tour) {
            Tour tour = (Tour) message;
            addToList(tour.getName());
            tourListService.addTour(tour);  // Assuming you also want to add the tour to a service-managed list
        }
    }
}
