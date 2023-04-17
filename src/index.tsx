import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'atlet-sensors-library' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const Accelerometer = NativeModules.Accelerometer
  ? NativeModules.Accelerometer
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const Gravity = NativeModules.Gravity
  ? NativeModules.Gravity
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const Gyroscope = NativeModules.Gyroscope
  ? NativeModules.Gyroscope
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const Magnetometer = NativeModules.Magnetometer
  ? NativeModules.Magnetometer
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export { Accelerometer, Gravity, Gyroscope, Magnetometer };
