package asminiproject.miniproject.services;

import asminiproject.miniproject.controllers.DataBaseController;
import asminiproject.miniproject.dc.Review;

public class ReviewsService {
    private static ReviewsService _instance;

    private DataBaseController _databaseController;

    public ReviewsService() {
        _instance = this;
        _databaseController = DataBaseController.getInstance();
    }

    public static ReviewsService getInstance() {
        if (ReviewsService._instance == null) {
            new ReviewsService();
        }

        return _instance;
    }

    public boolean addReview(Review review) {
        return _databaseController.addReview(review);
    }
}
