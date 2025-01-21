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

package opennlp.tools.formats.ad;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import opennlp.tools.cmdline.ObjectStreamFactory;
import opennlp.tools.cmdline.StreamFactoryRegistry;
import opennlp.tools.cmdline.TerminateToolException;
import opennlp.tools.formats.AbstractSampleStreamFactory;
import opennlp.tools.formats.AbstractSampleStreamFactoryTest;
import opennlp.tools.postag.POSSample;
import opennlp.tools.util.ObjectStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ADPOSSampleStreamFactoryTest extends
        AbstractSampleStreamFactoryTest<POSSample, ADPOSSampleStreamFactory.Parameters> {

  private static final String SAMPLE_01 = "ad.sample";
  
  // SUT
  private ADPOSSampleStreamFactory factory;

  private String sampleFileFullPath;

  @Override
  protected AbstractSampleStreamFactory<POSSample, ADPOSSampleStreamFactory.Parameters>
      getFactory() {
    return factory;
  }

  @Override
  protected String getDataFilePath() {
    return sampleFileFullPath;
  }

  @BeforeAll
  static void initEnv() {
    ADPOSSampleStreamFactory.registerFactory();
  }

  @BeforeEach
  void setUp() {
    ObjectStreamFactory<POSSample, ADPOSSampleStreamFactory.Parameters> f =
            StreamFactoryRegistry.getFactory(POSSample.class, "ad");
    assertInstanceOf(ADPOSSampleStreamFactory.class, f);
    factory = (ADPOSSampleStreamFactory) f;
    assertEquals(ADPOSSampleStreamFactory.Parameters.class, factory.getParameters());
    sampleFileFullPath = getResourceWithoutPrefix(
            FORMAT_SAMPLE_DIR + "ad/" + SAMPLE_01).getPath();
  }

  @ParameterizedTest
  @ValueSource(strings = {"true", "false"})
  void testCreateWithValidParameter(String expandME) throws IOException {
    try (ObjectStream<POSSample> stream = factory.create(new String[]
        {"-expandME", expandME, "-lang", "por", "-encoding", "UTF-8",
         "-data", sampleFileFullPath})) {
      POSSample sample = stream.read();
      assertNotNull(sample);
    }
  }

  /*
   * Note: Overriding this test case, as more params are required!
   */
  @Test
  @Override
  protected void testCreateWithInvalidDataFilePath() {
    assertThrows(TerminateToolException.class, () -> {
      try (ObjectStream<POSSample> stream = factory.create(new String[]
          {"-expandME", "false", "-lang", "por", "-encoding", "UTF-8",
           "-data", sampleFileFullPath + "xyz"})) {
        POSSample sample = stream.read();
        assertNotNull(sample);
      }
    });
  }

}
