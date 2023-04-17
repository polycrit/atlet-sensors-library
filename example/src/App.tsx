import { StyleSheet, View, Text, NativeEventEmitter } from 'react-native';
import { Accelerometer } from 'atlet-sensors-library';
import React, { useEffect } from 'react';

export default function App() {
  useEffect(() => {
    const eventEmitter = new NativeEventEmitter(Accelerometer);
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const eventListener = eventEmitter.addListener('accelerometer', (event) => {
      console.log(event);
    });

    return () => Accelerometer.removeListeners(1);
  });

  return (
    <View style={styles.container}>
      <Text>Accelerometer</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
