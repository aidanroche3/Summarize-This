package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the FileTypeVisitor class and its associated methods
 */
class FileTypeVisitorTest {

  ArrayList<String> onlyMd;
  ArrayList<String> withPdf;
  Path path;
  Path arraysPath;
  File arrays;
  File notes;
  File test;
  File vectors;
  File duedates;
  File java;
  ArrayList<File> mdFiles;
  ArrayList<File> bothFiles;
  FileTypeVisitor md;
  FileTypeVisitor both;
  FileVisitResult result;
  BasicFileAttributes attrs;

  /**
   * Sets the initial values used for testing
   *
   * @throws IOException on readAttributes
   */
  @BeforeEach
  public void setup() throws IOException {
    onlyMd = new ArrayList<>(List.of(".md"));
    withPdf = new ArrayList<>(Arrays.asList(".md", ".pdf"));
    path = Path.of("notes-root");
    arraysPath = Path.of("notes-root/arrays.md");
    arrays = arraysPath.toFile();
    notes = Path.of("notes-root/notes.pdf").toFile();
    test = Path.of("notes-root/test.md").toFile();
    vectors = Path.of("notes-root/vectors.md").toFile();
    duedates = Path.of("notes-root/lecture notes/duedates.pdf").toFile();
    java = Path.of("notes-root/lecture notes/java.md").toFile();
    mdFiles = new ArrayList<>(Arrays.asList(arrays, java, test, vectors));
    bothFiles = new ArrayList<>(Arrays.asList(arrays, duedates, java, notes, test, vectors));
    md = new FileTypeVisitor(onlyMd);
    both = new FileTypeVisitor(withPdf);
    result = FileVisitResult.CONTINUE;
    attrs = Files.readAttributes(arraysPath, BasicFileAttributes.class);
  }

  /**
   * Tests the getFiles method
   *
   * @throws IOException on walkFileTree
   */
  @Test
  public void testGetFiles() throws IOException {
    assertThrows(IllegalStateException.class, () -> md.getFiles());
    Files.walkFileTree(path, md);
    assertEquals(mdFiles, md.getFiles());
    Files.walkFileTree(path, both);
    assertEquals(bothFiles, both.getFiles());
  }

  /**
   * Tests the visit methods inherited from FileVisitor
   *
   */
  @Test
  public void testVisit() {
    assertEquals(result, md.preVisitDirectory(path, attrs));
    assertEquals(result, md.postVisitDirectory(path, new IOException()));
    assertEquals(result, md.visitFileFailed(path, new IOException()));
    assertEquals(result, md.visitFile(path, attrs));
  }
}