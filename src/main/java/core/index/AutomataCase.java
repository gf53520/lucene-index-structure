package core.index;

import org.apache.lucene.util.automaton.*;

import java.io.IOException;

// Deterministic Finite Automaton
// lucene内部自己实现了一套正则到DFA的转换，默认最大容许状态数为10000；
public class AutomataCase {

  public static void main(String[] args) throws IOException {
    RegExp regExp = new RegExp("dog.*");
    Automaton automaton = regExp.toAutomaton();
    CharacterRunAutomaton run = new CharacterRunAutomaton(automaton);

    System.out.println("Match: " + run.run("dog"));
    System.out.println("Match: " + run.run("dogs"));
    System.out.println("No-Match: " + run.run("do"));

  }
}
