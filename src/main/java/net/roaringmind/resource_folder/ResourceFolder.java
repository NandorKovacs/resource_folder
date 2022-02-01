package net.roaringmind.resource_folder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

public class ResourceFolder {
  public static void resourceWalker() throws URISyntaxException, IOException {
    URL url = ResourceFolder.class.getResource("/many_files");
    Path myPath;
    if (url.toURI().getScheme().equals("jar")) {
      FileSystem fileSystem = FileSystems.newFileSystem(url.toURI(), Collections.<String, Object>emptyMap());
      myPath = fileSystem.getPath("/");
    } else {
      myPath = Paths.get(url.toURI());
    }
    Stream<Path> walk = Files.walk(myPath, 1);
    for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
      File f = it.next().toFile();
      if (f.isDirectory()) {
        continue;
      }
      System.out.println(Files.readString(f.toPath()));
    }
    walk.close();
  }

  public static void main(String[] args) throws URISyntaxException, IOException {
    resourceWalker();
  }
}
