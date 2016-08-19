/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teradata.tpcds.column.generator;

import com.teradata.tpcds.Table;
import com.teradata.tpcds.random.RandomNumberStream;

/**
 * GeneratorColumns are columns that are used only within the context of the
 * generator logic. The Enums that implement this interface may include columns
 * that are not user visible and will sometimes omit columns that are user visible
 * (because those get derived from other columns).
 */
public interface GeneratorColumn
{
    Table getTable();

    RandomNumberStream getRandomNumberStream();

    int getGlobalColumnNumber();

    String getName();
}
