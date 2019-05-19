package core.index;


import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefBuilder;
import org.apache.lucene.util.IntsRef;
import org.apache.lucene.util.IntsRefBuilder;
import org.apache.lucene.util.fst.*;

import java.io.IOException;

// 1、输入有序情况下，可用于构建最优的FST (fast and low memory overhead)
// 2、可用于替代sortedSet（term => Void）或者sortedMap/字典（term => ordinal）
public class FstCase {

  // 等价于sortedMap/字典
  public static FST<Long> buildFst() throws IOException {
    String inputValues[] = {"cat", "dog", "dogs"};
    long outputValues[] = {5, 7, 12};
    PositiveIntOutputs outputs = PositiveIntOutputs.getSingleton();
    Builder<Long> builder = new Builder<>(FST.INPUT_TYPE.BYTE1, outputs);

    IntsRefBuilder scratchInts = new IntsRefBuilder();
    for (int i = 0; i < inputValues.length; i++) {
      BytesRef scratchBytes = new BytesRef(inputValues[i]);
      builder.add(Util.toIntsRef(scratchBytes, scratchInts), outputValues[i]);
    }
    return builder.finish();
  }

  public static void main(String[] args) throws IOException {
    FST<Long> fst = FstCase.buildFst();

    // term => ordinal
    Long value = Util.get(fst, new BytesRef("dog"));
    System.out.println(value); // 7

    // ordinal => term
    IntsRef key = Util.getByOutput(fst, 12);
    System.out.println(Util.toBytesRef(key, new BytesRefBuilder()).utf8ToString()); // dogs

    // iterator
    BytesRefFSTEnum<Long> iterator = new BytesRefFSTEnum<>(fst);
    while (iterator.next() != null) {
      BytesRefFSTEnum.InputOutput<Long> entry = iterator.current();
      System.out.println(String.format("term = %s, ordinal = %d",
          entry.input.utf8ToString(), entry.output));
    }

  }
}
