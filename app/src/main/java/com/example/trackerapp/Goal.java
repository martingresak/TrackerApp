package com.example.trackerapp;

import com.google.firebase.database.DatabaseReference;

interface Goal {

    public void setCurrentProgress(double currentProgress);
    public double getCurrentProgress();
    public double getMaxProgress();
    public void setName(String name);
    public String getName();
    public String getId();

}


class StarGoal implements Goal {
    private double currentProgress;
    private String name, id;
    private final double maxProgress = 5.0;

    public StarGoal() {
    }

    public StarGoal(String id, String name, double currentProgress) {
        this.currentProgress = currentProgress;
        this.id = id;
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

    @Override
    public String getId() {
        return id;
    }
}

class AmountGoal implements Goal {
    private double currentProgress;
    private String name, id;
    private double maxProgress;


    public AmountGoal() {
    }

    public AmountGoal(String id, String name, double currentProgress, double maxProgress) {
        this.id = id;
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

    @Override
    public String getId() {
        return null;
    }
}
