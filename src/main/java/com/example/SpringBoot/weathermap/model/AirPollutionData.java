package com.example.SpringBoot.weathermap.model;

import java.util.List;

public class AirPollutionData {
    private List<AirQualityData> list;

    public List<AirQualityData> getList() {
        return list;
    }

    public void setList(List<AirQualityData> list) {
        this.list = list;
    }

    public static class AirQualityData {
        private Components components;
        private Main main;
        private long dt;

        public Components getComponents() {
            return components;
        }

        public void setComponents(Components components) {
            this.components = components;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }
    }

    public static class Components {
        private double co;
        private double no2;
        private double o3;
        private double pm2_5;
        private double pm10;

        public double getCo() {
            return co;
        }

        public void setCo(double co) {
            this.co = co;
        }

        public double getNo2() {
            return no2;
        }

        public void setNo2(double no2) {
            this.no2 = no2;
        }

        public double getO3() {
            return o3;
        }

        public void setO3(double o3) {
            this.o3 = o3;
        }

        public double getPm2_5() {
            return pm2_5;
        }

        public void setPm2_5(double pm2_5) {
            this.pm2_5 = pm2_5;
        }

        public double getPm10() {
            return pm10;
        }

        public void setPm10(double pm10) {
            this.pm10 = pm10;
        }
    }

    public static class Main {
        private int aqi;

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }
    }
}