import com.github.km127pl.promises.Promise;
import org.testng.annotations.Test;

public class KPromiseTest {

	public static void main(String[] args) {
		System.out.println("Hello, world! - Sent from Main thread");
		new Promise<>(() -> {
			System.out.println("Hello, World! " + Thread.currentThread().getName());
		}).then_(() -> {
			System.out.println("Hello, World! (again) " + Thread.currentThread().getName());
			Promise.sleep(1000);
		}).finally_(() -> {
			System.out.println("Goodbye, World! " + Thread.currentThread().getName());
		});
		System.out.println("Goodbye, world! - Sent from Main thread");

		Promise<String> promise = new Promise<>((String s) -> {
			System.out.println("Hello, World! #" + Thread.currentThread().getName());
			return "Hello, World!";
		});

		promise.then_((String s) -> {
			System.out.println(s + " (again) #" + Thread.currentThread().getName());
			Promise.sleep(1000);
			return s;
		}).finally_((String s) -> {
			System.out.println("Goodbye, World! #" + Thread.currentThread().getName());
			return s;
		});
	}

	@Test
	public void test() {
		main(null);
	}


}
