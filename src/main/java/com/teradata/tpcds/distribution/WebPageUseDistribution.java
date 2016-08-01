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

package com.teradata.tpcds.distribution;

import com.teradata.tpcds.random.RandomNumberStream;

import static com.teradata.tpcds.distribution.StringValuesDistribution.buildStringValuesDistribution;

public class WebPageUseDistribution
{
    private static final StringValuesDistribution WEB_PAGE_USE_DISTRIBUTION = buildStringValuesDistribution("web_page_use.dst", 1, 1);

    private WebPageUseDistribution() {}

    public static String pickRandomWebPageUseType(RandomNumberStream stream)
    {
        return WEB_PAGE_USE_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }
}
