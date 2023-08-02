package shadow.doctool.tag;

import shadow.doctool.DocumentationException;
import shadow.doctool.tag.ParserManager.ArgDescriptionParser;
import shadow.doctool.tag.ParserManager.DelimitedParser;
import shadow.doctool.tag.ParserManager.TagParser;

import java.util.*;

/** Contains all recognized tag types and their associated properties */
public class TagManager {
  public static class InlineTag {
    private final InlineTagType type;
    private final List<String> arguments;

    public InlineTag(InlineTagType type, List<String> arguments) {
      this.type = type;
      this.arguments = new ArrayList<>(arguments);
    }

    public InlineTagType getType() {
      return type;
    }

    public List<String> getArgs() {
      return Collections.unmodifiableList(arguments);
    }

    public String getArg(int index) {
      return arguments.get(index);
    }
  }

  public enum BlockTagType {
    AUTHOR("author", new DelimitedParser(',')),
    PARAM("param", new ArgDescriptionParser(1, true)),
    RETURN("return", new ArgDescriptionParser(0, true)),
    SEE_DOC("seeDoc", new ArgDescriptionParser(1, true)),
    SEE_URL("seeUrl", new ArgDescriptionParser(1, true)),
    THROWS("throws", new ArgDescriptionParser(1, true)),
    UNUSED("unused", new ArgDescriptionParser(0, false)),

  /* End of tag list */ ;

    public static final Map<String, BlockTagType> tagNames;

    static {
      tagNames = new HashMap<>();
      for (BlockTagType type : BlockTagType.values()) tagNames.put(type.getName(), type);
    }

    public static BlockTagType getType(String name) {
      return tagNames.getOrDefault(name, null);
    }

    private final String name;
    private final TagParser parser; // No parser by default

    BlockTagType(String name, TagParser parser) {
      this.name = name;
      this.parser = parser;
    }

    public List<String> parse(String text) throws DocumentationException {
      if (parser == null) {
        List<String> list = new ArrayList<>();
        list.add(text);
        return list;
      } else {
        return parser.parse(text);
      }
    }

    public String getName() {
      return name;
    }
  }

  // CODE: Takes any amount and type of text, places it all within HTML code tags
  // LINK: Takes a URL or other linkable path, followed by any amount of display text
  // DOCLINK: Takes a qualified type or package name, followed by any amount of display text
  public enum InlineTagType {
    // This is a special tag representing normal text
    PLAIN_TEXT,

    // Regular inline tags
    CODE("code"),
    BOLD("bold"),
    ITALICS("italics"),
    UNDERLINE("underline"),
    SUPERSCRIPT("superscript"),
    SUBSCRIPT("subscript"),
    LINK_URL("linkUrl", new ArgDescriptionParser(1, true)), // TODO: Support mouseover text
    LINK_DOC("linkDoc", new ArgDescriptionParser(1, true)), // TODO: Support mouseover text

  /* End of tag list */ ;

    public static final Map<String, InlineTagType> tagNames;

    static {
      tagNames = new HashMap<>();
      for (InlineTagType type : InlineTagType.values()) tagNames.put(type.getName(), type);
    }

    public static InlineTagType getType(String name) {
      return tagNames.getOrDefault(name, null);
    }

    private String name = null;
    private TagParser parser = null; // No parser by default

    InlineTagType() {}

    InlineTagType(String name) {
      this.name = name;
    }

    InlineTagType(String name, TagParser parser) {
      this.parser = parser;
      this.name = name;
    }

    public List<String> parse(String text) throws DocumentationException {
      if (parser == null) {
        List<String> list = new ArrayList<>();
        list.add(text);
        return list;
      } else {
        return parser.parse(text);
      }
    }

    public InlineTag build(String text) throws DocumentationException {
      return new InlineTag(this, parse(text));
    }

    public String getName() {
      return name;
    }
  }
}
