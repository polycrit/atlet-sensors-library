package com.atletsensorslibrary;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class Accelerometer extends ReactContextBaseJavaModule implements SensorEventListener {

  private final SensorManager sensorManager;
  private final Sensor sensor;
  private final ReactApplicationContext reactContext;
  private double lastReading = (double) System.currentTimeMillis();
  private int listenerCount = 0;
  private int updateInterval = 1000;

  Accelerometer(ReactApplicationContext context) {
    super(context);
    this.reactContext = context;
    this.sensorManager = (SensorManager) this.reactContext.getSystemService(context.SENSOR_SERVICE);
    this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
  }

  @ReactMethod
  public void isAvailableAsync(Promise promise) {
    if (this.sensor == null) {
      promise.reject(new Exception("Accelerometer is inavailable!"));
    } else {
      promise.resolve(null);
    }
  }

  @ReactMethod
  public void setUpdateInterval(int interval) {
    this.updateInterval = interval;
  }

  @ReactMethod
  public void startUpdates() {
    this.sensorManager.registerListener(this, this.sensor, this.updateInterval * 1000);
  }

  @ReactMethod
  public void stopUpdates() {
    this.sensorManager.unregisterListener(this);
  }

  @ReactMethod
  public void addListener(String eventName) {
    if (this.listenerCount <= 0) {
      startUpdates();
    }

    listenerCount++;
  }

  @ReactMethod
  public void removeListeners(Integer count) {
    listenerCount -= count;

    if (this.sensorManager != null && listenerCount == 0) {
      stopUpdates();
    }
  }

  private void sendEvent(String eventName,
      @Nullable WritableMap params) {
    this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
  }

  @Override
  public void onSensorChanged(SensorEvent event) {

    if (this.listenerCount <= 0)
      return;
    if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
      return;

    double tempMs = (double) System.currentTimeMillis();

    if (tempMs - lastReading >= this.updateInterval) {
      this.lastReading = tempMs;
      WritableMap map = Arguments.createMap();
      map.putDouble("x", event.values[0]);
      map.putDouble("y", event.values[1]);
      map.putDouble("z", event.values[2]);
      map.putDouble("timestamp", event.timestamp);

      this.sendEvent("accelerometer", map);
    }
  }

  @Override
  public String getName() {
    return "Accelerometer";
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }
}