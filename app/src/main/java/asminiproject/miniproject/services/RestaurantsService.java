package asminiproject.miniproject.services;

import java.util.List;

import asminiproject.miniproject.controllers.DataBaseController;
import asminiproject.miniproject.dc.Restaurant;

public class RestaurantsService {
    private List<Restaurant> _restaurants;

    public RestaurantsService() {
        _restaurants = DataBaseController.getDataBaseController().getRestaurants();
    }

    public List<Restaurant> getRestaurants() { return _restaurants; }
}
