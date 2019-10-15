/*
 * Copyright © 2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.teradata.sink;

import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.plugin.db.batch.sink.CommonFieldsValidator;

import java.sql.Types;

/**
 * Teradata validator for DB fields.
 */
public class TeradataFieldsValidator extends CommonFieldsValidator {

  @Override
  public boolean isFieldCompatible(Schema.Type fieldType, Schema.LogicalType fieldLogicalType, int sqlType) {
    // In Teradata FLOAT and DOUBLE are same types
    if (fieldType == Schema.Type.DOUBLE && sqlType == Types.FLOAT) {
      return true;
    }

    return super.isFieldCompatible(fieldType, fieldLogicalType, sqlType);
  }
}
