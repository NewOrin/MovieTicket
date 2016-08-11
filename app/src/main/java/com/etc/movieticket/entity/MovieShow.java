package com.etc.movieticket.entity;

import java.io.Serializable;

public class MovieShow implements Serializable{
        private String endTime;
        private String place;
        private String price;
        private String startTime;
        private String type;

        public MovieShow() {
        }

        public MovieShow(String endTime, String place, String price, String startTime, String type) {
            this.endTime = endTime;
            this.place = place;
            this.price = price;
            this.startTime = startTime;
            this.type = type;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }