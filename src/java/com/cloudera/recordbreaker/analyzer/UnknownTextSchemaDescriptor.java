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
import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;

/************************************************************************
 * <code>AvroSchemaDescriptor</code> returns Avro-specific Schema data.
 *
 * @author "Michael Cafarella"
 * @version 1.0
 * @since 1.0
 * @see SchemaDescriptor
 *************************************************************************/
public class UnknownTextSchemaDescriptor extends AvroSchemaDescriptor {
  public UnknownTextSchemaDescriptor(FileSystem fs, Path p) throws IOException {
    super(fs, p);
  }
  /**
   * @return a <code>String</code> that annotates the schema
   */
  public String getSchemaSourceDescription() {
    return "recordbreaker-recovered";
  }
}