package com.example.automotiveapp.viewmodel;

import android.app.Application;
import android.car.Car;
import android.car.CarNotConnectedException;
import android.car.drivingstate.CarUxRestrictionsManager;
import android.car.hardware.property.CarPropertyManager;
import android.hardware.automotive.vehicle.V2_0.VehicleProperty;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class CarPropertyViewModel extends AndroidViewModel {


    private  Car car;

    private  CarPropertyLiveData<Float> speed;
    private CarPropertyLiveData<Boolean>  parkingBrake;


    private CarPropertyLiveData<Integer> mSeatTemp;
    private CarPropertyLiveData<Integer> mSteeringWheelHeat;
    private CarPropertyLiveData<Integer> mSeatVantilation;

    public CarPropertyViewModel(@NonNull Application application) {
        super(application);
        try {
            car = Car.createCar(application.getApplicationContext());
            CarPropertyManager propertyManager = (CarPropertyManager) car.getCarManager(Car.PROPERTY_SERVICE);
            speed = new CarPropertyLiveData<>(propertyManager, VehicleProperty.PERF_VEHICLE_SPEED);
            mSeatTemp = new CarPropertyLiveData<>(propertyManager, VehicleProperty.HVAC_SEAT_TEMPERATURE);
            mSteeringWheelHeat = new CarPropertyLiveData<>(propertyManager, VehicleProperty.HVAC_STEERING_WHEEL_HEAT);
            mSeatVantilation = new CarPropertyLiveData<>(propertyManager, VehicleProperty.HVAC_SEAT_VENTILATION);


            parkingBrake = new CarPropertyLiveData<>(propertyManager, VehicleProperty.PARKING_BRAKE_ON);
        } catch (CarNotConnectedException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        // Calling disconnect on the car object frees up resources and removes callbacks.
        car.disconnect();
    }


    public CarPropertyLiveData<Float> getSpeed() {
        return speed;
    }


    public CarPropertyLiveData<Boolean> getParkingBrake() {
        return parkingBrake;
    }

    public CarPropertyLiveData<Integer> getSeatTemp() {
        return mSeatTemp;
    }

    public CarPropertyLiveData<Integer> getSteeringWheelHeat() {
        return mSteeringWheelHeat;
    }

    public CarPropertyLiveData<Integer> getSeatVantilation() {
        return mSeatVantilation;
    }



}
