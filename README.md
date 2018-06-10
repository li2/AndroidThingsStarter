[Android Things Peripheral I/O - Codelabs](https://codelabs.developers.google.com/codelabs/androidthings-peripherals/index.html)

[Rainbow Hat driver for Androi] Things](https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat):
This driver provides easy access to the peripherals available on the Rainbow Hat for Android Thing.

```java
// java
private GpioCallback mCallback = new GpioCallback() {
    @Override
    public boolean onGpioEdge(Gpio gpio) {
        try {
            Log.i(TAG, "GPIO changed, button " + gpio.getValue());
        } catch (IOException e) {
            Log.w(TAG, "Error reading GPIO");
        }

        // Return true to keep callback active.
        return true;
    }
};
```

```kotlin
// kotlin
    var mCallback2 = object : GpioCallback {
        override fun onGpioEdge(gpio: Gpio): Boolean {
            try {
                Log.i(TAG, "GPIO changed, button " + gpio.getValue())
            } catch (e: IOException) {
                Log.w(TAG, "Error reading GPIO", e)
            }
            return true
        }
    }
```

```kotlin
// kotlin & lambda
    private var mCallback = GpioCallback { gpio ->
        try {
            Log.i(TAG, "GPIO changed, button " + gpio.value)
        } catch (e: IOException) {
            Log.w(TAG, "Error reading GPIO", e)
        }
        true
    }
```