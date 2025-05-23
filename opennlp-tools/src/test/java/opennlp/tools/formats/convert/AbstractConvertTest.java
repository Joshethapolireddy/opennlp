/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opennlp.tools.formats.convert;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import opennlp.tools.AbstractTempDirTest;
import opennlp.tools.formats.DirectorySampleStream;

public abstract class AbstractConvertTest extends AbstractTempDirTest {

  protected DirectorySampleStream sampleStream;
  protected List<String> sentences;

  @BeforeEach
  public void setUp() throws IOException {
    final String sentence1 = "This is a sentence.";
    final String sentence2 = "This is another sentence.";

    sentences = Arrays.asList(sentence1, sentence2);
    sampleStream = new DirectorySampleStream(tempDir.toFile(), null, false);

    Path tempFile1 = tempDir.resolve("tempFile1");
    Files.writeString(tempFile1, sentence1, StandardCharsets.UTF_8, StandardOpenOption.CREATE);

    Path tempFile2 = tempDir.resolve("tempFile2");
    Files.writeString(tempFile2, sentence2, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
  }
}
