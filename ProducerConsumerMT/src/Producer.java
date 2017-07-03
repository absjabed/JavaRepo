
public class Producer implements Runnable {

    private SharedQueue<Product> sharedBuffer;

    public Producer(SharedQueue<Product> sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            try {
                produce(new Product(i));

                System.out.println("Produced: " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void produce(Product p) throws InterruptedException {

        /* wait if buffer is full */
        while (sharedBuffer.isFull()) {
            synchronized (sharedBuffer) {
                System.out.println("Buffer is full. " + Thread.currentThread().getName() + " is waiting...");

                sharedBuffer.wait();
            }
        }

        /* otherwise produce and notify consumers */
        synchronized (sharedBuffer) {
            sharedBuffer.add(p);

            sharedBuffer.notifyAll();
        }
    } 

}
