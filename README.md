# atlet-sensors-library

Module for interacting with built-in sensors

## Installation

```sh
npm install atlet-sensors-library
```

## Usage

```js
import { Accelerometer } from 'atlet-sensors-library';

// ...

const eventEmitter = new NativeEventEmitter(Accelerometer);

const eventListener = eventEmitter.addListener('accelerometer', (event) => {
  console.log(event);
});

// ...

Accelerometer.removeListeners(1);
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
