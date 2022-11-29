# KPromise

KPromise is a simple promise library for Java.

## Usage

### A basic Promise
```java
Promise<String> promise = new Promise<>(() -> {
    /* 
        code that returns a String 
        can be a long running task,
        this code will be executed in a separate thread
    */
    return "Hello World";
});
```

### A basic Promise executed after a delay
```java
Promise<String> promise = new Promise<>(() -> {
    /* 
        code that returns a String 
        can be a long running task,
        this code will be executed in a separate thread
    */
    return "Hello World";
}, 1000); // delay in milliseconds, here - 1 second
```

### A basic Promise with a callback
```java
Promise<String> promise = new Promise<>(() -> {
    /* 
        code that returns a String 
        can be a long running task,
        this code will be executed in a separate thread
    */
    return "Hello World";
}, 1000, (result) -> {
    // this code will be executed after the promise is resolved
    System.out.println(result);
});
```

