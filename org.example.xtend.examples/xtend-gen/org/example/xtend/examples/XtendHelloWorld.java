package org.example.xtend.examples;

import java.util.ArrayList;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class XtendHelloWorld {
  public interface MyInnerInterface {
    static final String s = "s";
  }

  public static void main(final String[] args) {
    final ArrayList<String> list = CollectionLiterals.<String>newArrayList("a", "b", "c");
    InputOutput.<String>println(IterableExtensions.<String>head(list));
    InputOutput.<String>println(IterableExtensions.<String>last(list));
  }
}
