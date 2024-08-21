package com.samdev.concurrency;
import java.util.concurrent.Semaphore;


public class PrintInOrder {
    //Suppose we have a class:
    //
    //public class Foo {
    //  public void first() { print("first"); }
    //  public void second() { print("second"); }
    //  public void third() { print("third"); }
    //}
    //The same instance of Foo will be passed to three different threads. Thread A will call first(), thread B will call second(), and thread C will call third(). Design a mechanism and modify the program to ensure that second() is executed after first(), and third() is executed after second().
    //
    //Note:
    //
    //We do not know how the threads will be scheduled in the operating system, even though the numbers in the input seem to imply the ordering. The input format you see is mainly to ensure our tests' comprehensiveness.
    //
    //
    //
    //Example 1:
    //
    //Input: nums = [1,2,3]
    //Output: "firstsecondthird"
    //Explanation: There are three threads being fired asynchronously. The input [1,2,3] means thread A calls first(), thread B calls second(), and thread C calls third(). "firstsecondthird" is the correct output.
    //Example 2:
    //
    //Input: nums = [1,3,2]
    //Output: "firstsecondthird"
    //Explanation: The input [1,3,2] means thread A calls first(), thread B calls third(), and thread C calls second(). "firstsecondthird" is the correct output.


    private Semaphore secondSemaphore;
    private Semaphore thirdSemaphore;

    public PrintInOrder() {
        secondSemaphore = new Semaphore(0); // initially locked
        thirdSemaphore = new Semaphore(0);  // initially locked
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        // Release the second() method to run
        secondSemaphore.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        // Wait for first() to finish
        secondSemaphore.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        // Release the third() method to run
        thirdSemaphore.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        // Wait for second() to finish
        thirdSemaphore.acquire();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }


    public static void main(String[] args) {
        PrintInOrder foo = new PrintInOrder();

        Thread thread1 = new Thread(() -> {
            try {
                foo.first(() -> System.out.print("first"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                foo.second(() -> System.out.print("second"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                foo.third(() -> System.out.print("third"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread2.start();
        thread3.start();
        thread1.start();
    }


}
