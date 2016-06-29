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

public final class TopDomainsDistribution
{
    private static final StringValuesDistribution TOP_DOMAINS_DISTRIBUTION = buildStringValuesDistribution("top_domains.dst", 1, 1);

    private TopDomainsDistribution() {}

    public static String pickRandomTopDomain(RandomNumberStream stream)
    {
        return TOP_DOMAINS_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }
}
