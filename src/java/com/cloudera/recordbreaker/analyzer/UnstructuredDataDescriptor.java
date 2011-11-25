/*
 * Copyright (c) 2011, Cloudera, Inc. All Rights Reserved.
 *
 * Cloudera, Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */
package com.cloudera.recordbreaker.analyzer;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

/********************************************************************
 * <code>UnstructuredDataDescriptor</code> holds no structured data.
 * It doesn't have a schema to recover.
 *
 * @author "Michael Cafarella"
 * @version 1.0
 * @since 1.0
 * @see DataDescriptor
 ********************************************************************/
public class UnstructuredDataDescriptor implements DataDescriptor {
  File f;
  public UnstructuredDataDescriptor(File f) {
    this.f = f;
  }

  public File getFilename() {
    return this.f;
  }

  public String getFileTypeIdentifier() {
    return "unstructured";
  }

  public List<SchemaDescriptor> getSchemaDescriptor() {
    return new ArrayList<SchemaDescriptor>();
  }
}