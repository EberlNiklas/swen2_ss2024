package com.example.swen2_ss2024;

import com.example.swen2_ss2024.event.Publisher;
import com.example.swen2_ss2024.view.SearchBarView;
import com.example.swen2_ss2024.viewmodel.SearchViewModel;

public class ViewFactory {

    private static ViewFactory instance;

    private final Publisher publisher;

    private final SearchViewModel searchViewModel;

    private ViewFactory() {
        publisher = new Publisher();

        searchViewModel = new SearchViewModel(publisher);
    }

    public static ViewFactory getInstance() {
        if (null == instance) {
            instance = new ViewFactory();
        }

        return instance;
    }

    public Object create(Class<?> viewClass) {
        if (SearchBarView.class == viewClass) {
            return new SearchBarView(searchViewModel);
        }

        throw new IllegalArgumentException("Unknown view class: " + viewClass);
    }
}
