package com.github.km127pl.promises;

import java.util.concurrent.*;
import java.util.function.Function;

public class Promise<T> implements Future<T> {

	private final ExecutorService executor = Executors.newFixedThreadPool(1); // we only need one thread, I hope
	private final Future<T> future;

	/**
	 * Creates a new Promise
	 * @param function The function to execute
	 * @param input The input to the function
	 */
	public Promise(Function<T, T> function, T input) {
		future = executor.submit(() -> function.apply(input));
	}

	// exception for promises with no input

	/**
	 * Creates a new Promise
	 * @param function The function to execute
	 */
	public Promise(Function<T, T> function) {
		future = executor.submit(() -> function.apply(null));
	}

	// exception for promises with no input and a function with no return

	/**
	 * Creates a new Promise
	 * @param function The function to execute
	 */
	public Promise(Runnable function) {
		future = executor.submit(() -> {
			function.run();
			return null;
		});
	}

	/**
	 * The then function
	 * @param function The function to execute
	 * @return the same promise
	 */
	public Promise<T> then_(Function<T, T> function) {
		return new Promise<T>((T input) -> {
			try {
				return function.apply(future.get());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}, null);
	}

	// exception for promises with no input

	/**
	 * The then function
	 * @param function The function to execute
	 * @return the same promise
	 */
	public Promise<T> then_(Runnable function) {
		return new Promise<T>((T input) -> {
			try {
				future.get();
				function.run();
				return null;
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}, null);
	}

	/**
	 * The finally_ function
	 * @param function The function to execute
	 * @return the same promise
	 */
	public Promise<T> finally_(Function<T, T> function) {
		return new Promise<T>((T input) -> {
			try {
				return function.apply(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}, null);
	}

	// exception for promises with no input

	/**
	 * The finally_ function
	 * @param function The function to execute
	 * @return the same promise
	 */
	public Promise<T> finally_(Runnable function) {
		return new Promise<T>((T input) -> {
			try {
				future.get();
				function.run();
				return null;
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}, null);
	}

	/**
	 * Cancels the promise
	 * @param mayInterruptIfRunning {@code true} if the thread
	 * executing this task should be interrupted (if the thread is
	 * known to the implementation); otherwise, in-progress tasks are
	 * allowed to complete
	 * @return {@code false} if the task could not be cancelled,
	 */
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return future.cancel(mayInterruptIfRunning);
	}

	/**
	 * Indicates whether this task was cancelled before it completed
	 * @return {@code true} if this task was cancelled before it
	 */
	@Override
	public boolean isCancelled() {
		return future.isCancelled();
	}

	/**
	 * Indicates whether this task completed
	 * @return {@code true} if this task completed
	 */
	@Override
	public boolean isDone() {
		return future.isDone();
	}

	/**
	 * Gets the result of the computation
	 * @return the computed result
	 */
	@Override
	public T get() {
		try {
			return future.get();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the result of the computation
	 * @param timeout the maximum time to wait for the result
	 * @param unit the time unit of the timeout argument (not null)
	 * @return the computed result
	 */
	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return future.get(timeout, unit);
	}

	/**
	 * Sleeps for a specified amount of time
	 * @param millis The amount of time to sleep
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
