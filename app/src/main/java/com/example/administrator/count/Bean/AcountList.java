package com.example.administrator.count.Bean;

import java.util.List;

public class AcountList {
    List<Acount> list;

    public List<Acount> getList() {
        return list;
    }

    public void setList(List<Acount> list) {
        this.list = list;
    }

    public static class Acount{
        private String brand;
        private String car_id;
        private String carnum;
        private String carower;
        private String money;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCar_id() {
            return car_id;
        }

        public void setCar_id(String car_id) {
            this.car_id = car_id;
        }

        public String getCarnum() {
            return carnum;
        }

        public void setCarnum(String carnum) {
            this.carnum = carnum;
        }

        public String getCarower() {
            return carower;
        }

        public void setCarower(String carower) {
            this.carower = carower;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
