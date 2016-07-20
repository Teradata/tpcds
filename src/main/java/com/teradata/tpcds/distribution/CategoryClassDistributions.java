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

import com.google.common.collect.ImmutableList;
import com.teradata.tpcds.distribution.DistributionUtils.WeightsBuilder;
import com.teradata.tpcds.random.RandomNumberStream;

import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.teradata.tpcds.distribution.CategoryClassDistributions.CategoryClassDistribution.buildCategoryClassDistribution;
import static com.teradata.tpcds.distribution.DistributionUtils.getDistributionIterator;
import static com.teradata.tpcds.distribution.DistributionUtils.getListFromCommaSeparatedValues;
import static java.lang.Integer.parseInt;

public final class CategoryClassDistributions
{
    private static final List<CategoryClassDistribution> CATEGORY_CLASS_DISTRIBUTIONS =
            ImmutableList.of(buildCategoryClassDistribution("women_class.dst"),
                    buildCategoryClassDistribution("men_class.dst"),
                    buildCategoryClassDistribution("children_class.dst"),
                    buildCategoryClassDistribution("shoe_class.dst"),
                    buildCategoryClassDistribution("music_class.dst"),
                    buildCategoryClassDistribution("jewelry_class.dst"),
                    buildCategoryClassDistribution("home_class.dst"),
                    buildCategoryClassDistribution("sport_class.dst"),
                    buildCategoryClassDistribution("book_class.dst"),
                    buildCategoryClassDistribution("electronic_class.dst"));

    public static CategoryClass pickRandomCategoryClass(int categoryId, RandomNumberStream stream)
    {
        checkArgument(categoryId < CATEGORY_CLASS_DISTRIBUTIONS.size(), "categoryId %s is not less than %s", categoryId, CATEGORY_CLASS_DISTRIBUTIONS.size());
        return CATEGORY_CLASS_DISTRIBUTIONS.get(categoryId).pickRandomCategoryClass(stream);
    }

    private CategoryClassDistributions() {}

    public static class CategoryClassDistribution
    {
        private final ImmutableList<String> names;
        private final ImmutableList<Integer> brandCounts;
        private final ImmutableList<Integer> weights;

        public CategoryClassDistribution(ImmutableList<String> names, ImmutableList<Integer> brandCounts, ImmutableList<Integer> weights)
        {
            this.names = names;
            this.brandCounts = brandCounts;
            this.weights = weights;
        }

        public static CategoryClassDistribution buildCategoryClassDistribution(String filename)
        {
            ImmutableList.Builder<String> namesBuilder = ImmutableList.builder();
            ImmutableList.Builder<Integer> brandCountsBuilder = ImmutableList.builder();
            WeightsBuilder weightsBuilder = new WeightsBuilder();

            Iterator<List<String>> iterator = getDistributionIterator(filename);
            while (iterator.hasNext()) {
                List<String> fields = iterator.next();
                checkState(fields.size() == 2, "Expected line to contain 2 parts but it contains %d: %s", fields.size(), fields);

                List<String> values = getListFromCommaSeparatedValues(fields.get(0));
                checkState(values.size() == 2, "Expected line to contain 2 values, but it contained %d, %s", values.size(), values);

                namesBuilder.add(values.get(0));
                brandCountsBuilder.add(parseInt(values.get(1)));

                List<String> weights = getListFromCommaSeparatedValues(fields.get(1));
                checkState(weights.size() == 1, "Expected line to contain %d weights, but it contained %d, %s", 1, weights.size(), values);
                weightsBuilder.computeAndAddNextWeight(parseInt(weights.get(0)));
            }

            return new CategoryClassDistribution(namesBuilder.build(),
                    brandCountsBuilder.build(),
                    weightsBuilder.build());
        }

        public CategoryClass pickRandomCategoryClass(RandomNumberStream stream)
        {
            int index = DistributionUtils.pickRandomIndex(weights, stream);
            return new CategoryClass(index + 1, names.get(index), brandCounts.get(index));
        }
    }

    public static class CategoryClass
    {
        private final int id;
        private final String name;
        private final int brandCount;

        public CategoryClass(int id, String name, int brandCount)
        {
            this.id = id;
            this.name = name;
            this.brandCount = brandCount;
        }

        public int getBrandCount()
        {
            return brandCount;
        }

        public String getName()
        {
            return name;
        }

        public long getId()
        {
            return id;
        }
    }
}
