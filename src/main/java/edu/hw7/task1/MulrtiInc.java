package edu.hw7.task1;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;

public class MulrtiInc {
    @Getter private AtomicInteger value;

   public void inc() {
       value.incrementAndGet();
   }
}
