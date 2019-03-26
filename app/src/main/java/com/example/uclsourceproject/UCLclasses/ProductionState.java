package com.example.uclsourceproject.UCLclasses;

public class ProductionState {

    //羊的Id 编号
    private int productionId;

    //健康状况
    private String healthState;

    //每日步数
    private int daySteps;

    //体温
    private int dayTemperature;

    public ProductionState(int productionId, String healthState, int daySteps, int dayTemperature) {
        this.productionId = productionId;
        this.healthState = healthState;
        this.daySteps = daySteps;
        this.dayTemperature = dayTemperature;
    }

    public int getProductionId() {
        return productionId;
    }

    public void setProductionId(int productionId) {
        this.productionId = productionId;
    }

    public String getHealthState() {
        return healthState;
    }

    public void setHealthState(String healthState) {
        this.healthState = healthState;
    }

    public int getDaySteps() {
        return daySteps;
    }

    public void setDaySteps(int daySteps) {
        this.daySteps = daySteps;
    }

    public int getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(int dayTemperature) {
        this.dayTemperature = dayTemperature;
    }
}
