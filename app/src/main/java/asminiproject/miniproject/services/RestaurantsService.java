package asminiproject.miniproject.services;

import java.util.List;

import asminiproject.miniproject.controllers.DataBaseController;
import asminiproject.miniproject.dc.Restaurant;

public class RestaurantsService {
    private static RestaurantsService _instance;
    private List<Restaurant> _restaurants;

    private RestaurantsService() {
        _instance = this;
        _restaurants = DataBaseController.getDataBaseController().getRestaurants();
    }

    public static RestaurantsService getInstance() {
        if (RestaurantsService._instance == null) {
            new RestaurantsService();
        }

        return _instance;
    }

    public List<Restaurant> getRestaurants() { return _restaurants; }
    public Restaurant getRestaurantById(int id) {
        return _restaurants.get(id);
    }
}
