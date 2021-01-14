package com.example.trackerapp;

import com.google.firebase.database.DatabaseReference;

interface Goal {

    void setCurrentProgress(double currentProgress);
    double getCurrentProgress();
    double getMaxProgress();
    void setName(String name);
    String getName();

}


class StarGoal implements Goal {
    private double currentProgress;
    private String name;
    private final double maxProgress = 5.0;

    public StarGoal() {
    }

    public StarGoal(String name, double currentProgress) {
        this.currentProgress = currentProgress;
        this.name = name;
    }




    @Override
    public double getMaxProgress() {
        return maxProgress;
    }

    @Override
    public void setCurrentProgress(double currentProgress) {
        this.currentProgress = Math.min(currentProgress, maxProgress);
    }

    @Override
    public double getCurrentProgress() {
        return currentProgress;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}

class AmountGoal implements Goal {
    private double currentProgress;
    private String name, id;
    private double maxProgress;


    public AmountGoal() {
    }

    public AmountGoal(String name, double currentProgress, double maxProgress) {
        this.name = name;
        this.currentProgress = currentProgress;
        this.maxProgress = maxProgress;

    }

    public void setMaxProgress(double maxProgress){
        this.maxProgress = maxProgress;
    }

    @Override
    public double getMaxProgress() {
        return maxProgress;
    }

    @Override
    public void setCurrentProgress(double currentProgress) {
        this.currentProgress = Math.min(currentProgress, maxProgress);
    }

    @Override
    public double getCurrentProgress() {
        return currentProgress;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
