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

package opennlp.tools.formats.masc;

import java.io.FileFilter;
import java.io.IOException;

import opennlp.tools.cmdline.ArgumentParser;
import opennlp.tools.cmdline.CmdLineUtil;
import opennlp.tools.cmdline.StreamFactoryRegistry;
import opennlp.tools.cmdline.params.BasicFormatParams;
import opennlp.tools.commons.Internal;
import opennlp.tools.formats.AbstractSampleStreamFactory;
import opennlp.tools.tokenize.TokenSample;
import opennlp.tools.util.ObjectStream;

/**
 * <b>Note:</b> Do not use this class, internal use only!
 *
 * @see TokenSample
 * @see MascTokenSampleStream
 */
@Internal
public class MascTokenSampleStreamFactory extends
        AbstractSampleStreamFactory<TokenSample, MascTokenSampleStreamFactory.Parameters>
        implements Masc {

  public interface Parameters extends BasicFormatParams {
    @ArgumentParser.ParameterDescription(valueName = "sentencesPerSample",
            description = "number of sentences per sample")
    String getSentencesPerSample();

    @ArgumentParser.ParameterDescription(valueName = "recurrentSearch",
            description = "search through files recursively")
    Boolean getRecurrentSearch();

    @ArgumentParser.ParameterDescription(valueName = "fileFilterString",
            description = "only include files which contain a given string in their name")
    String getFileFilter();

  }

  protected MascTokenSampleStreamFactory(Class<Parameters> params) {
    super(params);
  }

  public static void registerFactory() {
    StreamFactoryRegistry.registerFactory(TokenSample.class, MASC_FORMAT,
        new MascTokenSampleStreamFactory(Parameters.class));
  }

  @Override
  public ObjectStream<TokenSample> create(String[] args) {
    if (args == null) {
      throw new IllegalArgumentException("Passed args must not be null!");
    }
    Parameters params = ArgumentParser.parse(args, Parameters.class);
    try {
      FileFilter fileFilter = pathname -> pathname.getName().contains(params.getFileFilter());

      return new MascTokenSampleStream(
          new MascDocumentStream(params.getData(), params.getRecurrentSearch(), fileFilter));
    } catch (IOException e) {
      // That will throw an exception
      CmdLineUtil.handleCreateObjectStreamError(e);
    }
    return null;
  }

}

