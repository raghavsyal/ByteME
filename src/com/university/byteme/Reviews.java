package com.university.byteme;

public class Reviews {
    private String reviews;

    public Reviews(String reviews) {
        this.reviews = reviews;
    }

    public String getReviews() {
        return reviews;
    }

    public void printReviews(){
        System.out.println("Reviews: " + reviews);
    }
}


